package net.lopymine.te.transparency;

import net.lopymine.te.config.distance.TransparencyDistance;
import net.lopymine.te.config.distance.TransparencyDistance.Distance;
import net.lopymine.te.thing.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.*;

import net.fabricmc.loader.api.FabricLoader;

import net.lopymine.te.client.TransparentEntitiesClient;
import net.lopymine.te.config.TransparentEntitiesConfig;
import net.lopymine.te.utils.ArgbUtils;

import java.util.*;

public class TransparencyManager {

	public static int getArgbColorFromAnyCapturedThing(int originalArgb) {
		Entity entity = ThingCaptures.CURRENT_RENDERING_ENTITY.get();
		Particle particle = ThingCaptures.CURRENT_RENDERING_PARTICLE.get();
		if (entity != null) {
			return ThingCaptures.CURRENT_RENDERING_ENTITY.getArgbColor(entity, originalArgb);
		} else if (particle != null) {
			return ThingCaptures.CURRENT_RENDERING_PARTICLE.getArgbColor(particle, originalArgb);
		}
		return originalArgb;
	}

	public static int getAlpha(Vec3d cameraPos, Vec3d thingPos) {
		TransparentEntitiesConfig config = TransparentEntitiesClient.getConfig();
		TransparencyDistance transparencyDistance = config.getTransparencyDistance();
		double minHidingValue = config.getMinHidingValue();

		Distance distance = transparencyDistance.getDistance(cameraPos, thingPos);

		if (distance == null || distance.hidingActivationDistance() <= 0F) {
			return 255;
		}

		float a = distance.hidingActivationDistance() - distance.fullHidingDistance();
		float b = distance.distance() - distance.fullHidingDistance();

		if (a <= 0F || b <= 0F) {
			return (int) (minHidingValue * 255F);
		}

		if (b >= a) {
			return 255;
		}

		return (int) (Math.clamp((minHidingValue + ((1F - minHidingValue) * (b / a))), 0F, 1F) * 255F);
	}

	public static int getColorForYourself(int original) {
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
		TransparentEntitiesConfig config = TransparentEntitiesClient.getConfig();
		if (!config.isModEnabled()) {
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

		TransparencyDistance transparencyDistance = config.getTransparencyDistance();
		Distance distance = transparencyDistance.getDistance(cameraPos, entityPos);

		if (distance == null || distance.hidingActivationDistance() <= 0F) {
			return false;
		}

		return (distance.distance() - distance.hidingActivationDistance()) < 0F;
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
