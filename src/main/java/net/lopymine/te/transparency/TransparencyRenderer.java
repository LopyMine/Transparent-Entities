package net.lopymine.te.transparency;

import net.lopymine.te.thing.*;
import net.minecraft.client.particle.Particle;
import net.minecraft.entity.*;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import net.lopymine.te.client.TransparentEntitiesClient;
import net.lopymine.te.config.TransparentEntitiesConfig;

import java.util.*;

public class TransparencyRenderer {

	public static void handleEntityRendering(Entity entity, Runnable renderCall) {
		TransparentEntitiesConfig config = TransparentEntitiesClient.getConfig();
		if (!config.isModEnabled()) {
			renderCall.run();
			return;
		}

		HashSet<Identifier> hideEntities = config.getHideEntities();
		Identifier id = Registries.ENTITY_TYPE.getId(entity.getType());
		if (!hideEntities.contains(id)) {
			renderCall.run();
			return;
		}

		if (config.getFavoriteEntities().containsKey(entity.getUuid())) {
			renderCall.run();
			return;
		}

		ThingCaptures.CURRENT_RENDERING_ENTITY.set(entity);
		renderCall.run();
		ThingCaptures.CURRENT_RENDERING_ENTITY.clear();
	}

	public static void handleParticleRendering(Particle particle, Runnable renderCall) {
		TransparentEntitiesConfig config = TransparentEntitiesClient.getConfig();
		if (!config.isModEnabled()) {
			renderCall.run();
			return;
		}

		HashSet<Identifier> hideEntities = config.getHideEntities();
		if (!hideEntities.contains(TransparentEntitiesConfig.PARTICLES_ID.id())) {
			renderCall.run();
			return;
		}

		ThingCaptures.CURRENT_RENDERING_PARTICLE.set(particle);
		//? if <=1.21.4 {
		/*com.mojang.blaze3d.systems.RenderSystem.enableBlend();
		com.mojang.blaze3d.systems.RenderSystem.blendFuncSeparate(
				com.mojang.blaze3d.platform.GlStateManager.SrcFactor.SRC_ALPHA,
				com.mojang.blaze3d.platform.GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA,
				com.mojang.blaze3d.platform.GlStateManager.SrcFactor.ONE,
				com.mojang.blaze3d.platform.GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA
		);
		*///?}
		renderCall.run();
		ThingCaptures.CURRENT_RENDERING_PARTICLE.clear();
	}

	public static void handleLabelRenderingOne() {
		TransparentEntitiesConfig config = TransparentEntitiesClient.getConfig();
		if (!config.isModEnabled()) {
			return;
		}
		ThingMarks.ENTITY_NAME_RENDERING.get().setEnabled(true);
		ThingMarks.ENTITY_NAME_RENDERING.get().setValue(null);
		HashSet<Identifier> hideEntities = config.getHideEntities();
		if (hideEntities.contains(TransparentEntitiesConfig.ENTITY_NAME_ID.id())) {
			return;
		}
		ThingContainer<Entity> container = ThingCaptures.CURRENT_RENDERING_ENTITY.getContainer();
		boolean previousContainerValue = container.isEnabled();
		container.setEnabled(false);
		ThingMarks.ENTITY_NAME_RENDERING.get().setValue(previousContainerValue);
	}

	public static void handleLabelRenderingTwo() {
		TransparentEntitiesConfig config = TransparentEntitiesClient.getConfig();
		if (!config.isModEnabled()) {
			return;
		}
		ThingMarks.ENTITY_NAME_RENDERING.get().setEnabled(false);
		Boolean previousValue = ThingMarks.ENTITY_NAME_RENDERING.get().getValue();
		if (previousValue == null) {
			return;
		}
		ThingCaptures.CURRENT_RENDERING_ENTITY.setEnabled(previousValue);
	}
}
