package net.lopymine.te.render;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.*;

import net.fabricmc.loader.api.FabricLoader;

import net.lopymine.te.client.TransparentEntitiesClient;
import net.lopymine.te.config.TransparentEntitiesConfig;
import net.lopymine.te.utils.ArgbUtils;

import java.util.*;

public class TransparencyManager {

	public static int getTranslucentArgb(Entity entity, int original) {
		if (!TransparentEntitiesClient.getConfig().isModEnabled()) {
			return original;
		}

		MinecraftClient client = MinecraftClient.getInstance();
		ClientPlayerEntity player = client.player;
		ClientWorld world = client.world;
		Vec3d cameraPos = client.gameRenderer.getCamera().getPos();
		Vec3d entityPos = entity.getPos();

		if (player == null || world == null) {
			return original;
		}

		if (player.equals(entity)) {
			return getColorForYourself(original);
		}

		if (entity.isInvisibleTo(player)) {
			return original;
		}

		// player teammate check

		return ArgbUtils.swapAlpha(original, getAlpha(cameraPos, entityPos));
	}

	private static int getAlpha(Vec3d cameraPos, Vec3d entityPos) {
		TransparentEntitiesConfig config = TransparentEntitiesClient.getConfig();
		float hidingActivationDistance = config.getHidingActivationDistance();
		float fullHidingDistance = config.getFullHidingDistance();
		float minHidingValue = config.getMinHidingValue();
		float distance = calculateDistance(cameraPos, entityPos);

		if (hidingActivationDistance <= 0F) {
			return 255;
		}

		float a = hidingActivationDistance - fullHidingDistance;
		float b = distance - fullHidingDistance;

		if (a <= 0F || b <= 0F) {
			return (int) (minHidingValue * 255F);
		}

		if (b >= a) {
			return 255;
		}

		return (int) (Math.clamp((minHidingValue + ((1F - minHidingValue) * (b / a))), 0F, 1F) * 255F);
	}

	private static int getColorForYourself(int original) {
		if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
			return ArgbUtils.swapAlpha(original, (int) (255F * TransparentEntitiesClient.getConfig().minHidingValue));
		}
		return original;
	}

	public static float calculateDistance(Vec3d cameraPos, Vec3d entityPos) {
		float f = (float)(cameraPos.getX() - entityPos.getX());
		float g = (float)(cameraPos.getY() - entityPos.getY());
		float h = (float)(cameraPos.getZ() - entityPos.getZ());
		return MathHelper.sqrt(f * f + g * g + h * h);
	}

	public static boolean isTransparency(Entity entity) {
		if (!TransparentEntitiesClient.getConfig().isModEnabled()) {
			return false;
		}

		MinecraftClient client = MinecraftClient.getInstance();
		ClientPlayerEntity player = client.player;
		ClientWorld world = client.world;
		Vec3d cameraPos = client.gameRenderer.getCamera().getPos();
		Vec3d entityPos = entity.getPos();

		if (player == null || world == null) {
			return false;
		}

		if (player.equals(entity)) {
			return ArgbUtils.getAlpha(getColorForYourself(-1)) != 255;
		}

		float hidingActivationDistance = TransparentEntitiesClient.getConfig().hidingActivationDistance;
		float distance = TransparencyManager.calculateDistance(cameraPos, entityPos);

		return (distance - hidingActivationDistance) < 0F;
	}

	public static List<Entity> sortEntitiesByDistance(List<Entity> entities) {
		Vec3d cameraPos = MinecraftClient.getInstance().gameRenderer.getCamera().getPos();

		return entities.stream()
				.map(entity -> Map.entry(entity, calculateDistance(cameraPos, entity.getPos())))
				.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
				.map(Map.Entry::getKey)
				.toList();
	}

	public static boolean canRenderTransparencyShadow(Entity entity) {
		TransparentEntitiesConfig config = TransparentEntitiesClient.getConfig();
		return config.isHideShadowEnabled() && config.isModEnabled() && !config.getFavoriteEntities().containsKey(entity.getUuid());
	}
}
