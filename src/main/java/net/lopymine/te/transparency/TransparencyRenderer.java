package net.lopymine.te.transparency;

import com.mojang.blaze3d.systems.RenderSystem;
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
		/*RenderSystem.enableBlend();
		*///?}
		renderCall.run();
		//? if <=1.21.4 {
		/*RenderSystem.disableBlend();
		*///?}
		ThingCaptures.CURRENT_RENDERING_PARTICLE.clear();
	}

	public static void handleLabelRenderingOne() {
		TransparentEntitiesConfig config = TransparentEntitiesClient.getConfig();
		if (!config.isModEnabled()) {
			return;
		}
		ThingMarks.ENTITY_NAME_RENDERING.setEnabled(true);
		ThingMarks.ENTITY_NAME_RENDERING.setValue(null);
		HashSet<Identifier> hideEntities = config.getHideEntities();
		if (hideEntities.contains(TransparentEntitiesConfig.ENTITY_NAME_ID.id())) {
			return;
		}
		ThingContainer<Entity> container = ThingCaptures.CURRENT_RENDERING_ENTITY.getContainer();
		boolean previousContainerValue = container.isEnabled();
		container.setEnabled(false);
		ThingMarks.ENTITY_NAME_RENDERING.setValue(previousContainerValue);
	}

	public static void handleLabelRenderingTwo() {
		TransparentEntitiesConfig config = TransparentEntitiesClient.getConfig();
		if (!config.isModEnabled()) {
			return;
		}
		ThingMarks.ENTITY_NAME_RENDERING.setEnabled(false);
		Boolean previousValue = ThingMarks.ENTITY_NAME_RENDERING.getValue();
		if (previousValue == null) {
			return;
		}
		ThingCaptures.CURRENT_RENDERING_ENTITY.setEnabled(previousValue);
	}
}
