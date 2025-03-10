package net.lopymine.ms.manager;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.AbstractTeam;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

import net.fabricmc.loader.api.FabricLoader;

import net.lopymine.ms.client.MoreSpaceClient;
import net.lopymine.ms.config.MoreSpaceConfig;
import net.lopymine.ms.entity.EntityCaptures;
import net.lopymine.ms.utils.*;

import java.util.function.*;
import org.jetbrains.annotations.NotNull;

public class HidingManager {

    public static final HidingManager INSTANCE = new HidingManager();

    private HidingManager() {
    }

    public int getAlpha(@NotNull Entity entity, int argb) {
        if (!MoreSpaceClient.getConfig().isModEnabled()) {
            return argb;
        }
        PlayerEntity player = MinecraftClient.getInstance().player;
        Entity camera = MinecraftClient.getInstance().cameraEntity;
        if (player == null || camera == null) {
            return argb;
        }
        if (!(entity instanceof PlayerEntity anotherPlayer)) {
            return argb;
        }
        if (player.equals(anotherPlayer)) {
			if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
				return ArgbUtils.swapAlpha(argb, (int) (255 * MoreSpaceClient.getConfig().minHidingValue)); // FOR TESTING ON YOURSELF
			}
			return argb;
        }
        AbstractTeam abstractTeam = anotherPlayer.getScoreboardTeam();
        boolean b = anotherPlayer.isInvisible() && player.isSpectator();
        boolean bl = anotherPlayer.isInvisible() && (abstractTeam != null && player.getScoreboardTeam() == abstractTeam && abstractTeam.shouldShowFriendlyInvisibles());
        if (b || bl) {
			return ArgbUtils.swapAlpha(argb, 28);
        }
        if (anotherPlayer.isInvisible()) {
	        return ArgbUtils.swapAlpha(argb, 0);
        }
	    int alpha = this.getAlpha(camera, anotherPlayer, argb);
	    int clamp = MathHelper.clamp(alpha, 0, 255);
	    int i = ArgbUtils.swapAlpha(argb, clamp);
	    return i;
    }

	public boolean canHit(Entity entity, boolean original) {
		Entity camera = MinecraftClient.getInstance().cameraEntity;
		if (camera == null) {
			return original;
		}
		if (isTransparent(entity, camera)) {
			return false;
		}
		return original;
	}

	private boolean isTransparent(Entity entity, Entity camera) {
		MoreSpaceConfig config = MoreSpaceClient.getConfig();
		if (entity.equals(camera)) {
			return config.getMinHidingValue() != 1.0F;
		}
		float hidingActivationDistance = config.getHidingActivationDistance();
		float fullInvisibilityDistance = config.getFullHidingDistance();
		float distance = camera.distanceTo(entity);

		return distance <= fullInvisibilityDistance || distance <= hidingActivationDistance;
	}

	private int getAlpha(@NotNull Entity camera, PlayerEntity anotherPlayer, int original) {
	    MoreSpaceConfig config = MoreSpaceClient.getConfig();
	    float hidingActivationDistance = config.getHidingActivationDistance();
        float fullInvisibilityDistance = config.getFullHidingDistance();
        float distance = camera.distanceTo(anotherPlayer);

        if (distance <= fullInvisibilityDistance) {
            return 0;
        } else if (distance <= hidingActivationDistance) {
	        float value = (distance - fullInvisibilityDistance) / (hidingActivationDistance - fullInvisibilityDistance);
	        return (int) (Math.clamp(value, config.getMinHidingValue(), 1.0F) * 255);
        }
        return ArgbUtils.getAlpha(original);
    }

	public RenderLayer getLayer(Identifier texture, Supplier<RenderLayer> original) {
		if (this.cannotReplaceRenderLayer()) {
			return original.get();
		}
		return RenderLayer.getItemEntityTranslucentCull(texture);
	}

	private boolean cannotReplaceRenderLayer() {
		if (!MoreSpaceClient.getConfig().isModEnabled()) {
			return true;
		}
		Entity entity = EntityCaptures.MAIN.getEntity();
		if (entity == null) {
			return true;
		}
		Entity camera = MinecraftClient.getInstance().cameraEntity;
		if (camera == null) {
			return true;
		}
		return !this.isTransparent(entity, camera);
	}

	public RenderLayer getLayer(RenderLayer original) {
		if (this.cannotReplaceRenderLayer()) {
			return original;
		}
		return TexturedRenderLayers.getItemEntityTranslucentCull();
	}

	public RenderLayer getLayer(Supplier<RenderLayer> original) {
		if (this.cannotReplaceRenderLayer()) {
			return original.get();
		}
		return TexturedRenderLayers.getItemEntityTranslucentCull();
	}

	public Function<Identifier, RenderLayer> getLayer(Function<Identifier, RenderLayer> original) {
		if (this.cannotReplaceRenderLayer()) {
			return original;
		}
		return RenderLayer::getItemEntityTranslucentCull;
	}
}
