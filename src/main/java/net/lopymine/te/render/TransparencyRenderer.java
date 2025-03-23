package net.lopymine.te.render;

import net.minecraft.entity.*;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import net.lopymine.te.client.TransparentEntitiesClient;
import net.lopymine.te.config.TransparentEntitiesConfig;
import net.lopymine.te.entity.EntityCaptures;

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

		EntityCaptures.MAIN.setEntity(entity);
		renderCall.run();
		EntityCaptures.MAIN.clearEntity();
	}

}
