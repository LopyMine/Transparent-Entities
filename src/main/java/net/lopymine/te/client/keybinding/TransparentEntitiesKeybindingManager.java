package net.lopymine.te.client.keybinding;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.InputUtil.Type;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.hit.*;
import org.lwjgl.glfw.GLFW;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;

import net.lopymine.te.TransparentEntities;
import net.lopymine.te.client.TransparentEntitiesClient;
import net.lopymine.te.config.TransparentEntitiesConfig;
import net.lopymine.te.config.data.EntityData;

import java.util.*;
import org.jetbrains.annotations.Nullable;

public class TransparentEntitiesKeybindingManager {

	public static final String TITLE_KEY = "%s.keybinding.name".formatted(TransparentEntities.MOD_ID);

	private static final SingleUseKeybinding TOGGLE_MOD = new SingleUseKeybinding("toggle_mod", Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN);
	private static final SingleUseKeybinding TOGGLE_FAVORITE_ENTITY = new SingleUseKeybinding("toggle_favorite_entity", Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN);

	public static void register() {
        TransparentEntitiesKeybindingManager.registerKeybindings();
		ClientTickEvents.START_CLIENT_TICK.register((client -> {
	        if (TOGGLE_MOD.runOnceIfPressed(() -> {
		        TransparentEntitiesConfig config = TransparentEntitiesClient.getConfig();
		        config.setModEnabled(!config.isModEnabled());
		        sendStatusMessage("toggle_mod", config.isModEnabled());
				config.save();
	        })) {
		        return;
	        }

	        TOGGLE_FAVORITE_ENTITY.runOnceIfPressed(() -> {
		        Entity targetedEntity = getTargetedEntity();
				if (targetedEntity == null) {
					return;
				}

		        UUID uuid = targetedEntity.getUuid();
		        TransparentEntitiesConfig config = TransparentEntitiesClient.getConfig();
		        HashMap<UUID, EntityData> favoriteEntities = config.getFavoriteEntities();
		        if (favoriteEntities.containsKey(uuid)) {
			        EntityData entityData = favoriteEntities.remove(uuid);
			        sendCustomStatusMessage(TransparentEntities.sillyText("command.favorite_entities.remove.success", entityData.getName()));
		        } else {
			        EntityData entityData = new EntityData(targetedEntity instanceof PlayerEntity, targetedEntity.getName().getString(), uuid);
			        favoriteEntities.put(entityData.getUuid(), entityData);
			        sendCustomStatusMessage(TransparentEntities.sillyText("command.favorite_entities.add.success", entityData.getName()));
		        }
				config.save();
	        });
        }));
    }

	private static @Nullable Entity getTargetedEntity() {
		MinecraftClient client = MinecraftClient.getInstance();
		Entity cameraEntity = client.cameraEntity;
		if (cameraEntity == null) {
			return client.targetedEntity;
		}
		float tickDelta = client.getRenderTickCounter()/*? if <=1.21.4 {*/ .getTickDelta(false); /*?} else {*//*.getTickProgress(false);*//*?}*/

		HitResult target = client.gameRenderer.findCrosshairTarget(cameraEntity, 100D, 100D, tickDelta);
		if (!(target instanceof EntityHitResult hitResult)) {
			return client.targetedEntity;
		}

		return hitResult.getEntity();
	}

	public static void sendStatusMessage(String id, Object status, Object... args) {
		InGameHud hud = MinecraftClient.getInstance().inGameHud;
		hud.setOverlayMessage(TransparentEntities.text("keybinding.status.%s.%s".formatted(id, status), args), false);
	}

	public static void sendCustomStatusMessage(Text text) {
		InGameHud hud = MinecraftClient.getInstance().inGameHud;
		hud.setOverlayMessage(text, false);
	}

    private static void registerKeybindings() {
        KeyBindingHelper.registerKeyBinding(TOGGLE_MOD);
        KeyBindingHelper.registerKeyBinding(TOGGLE_FAVORITE_ENTITY);
    }
}
