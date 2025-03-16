package net.lopymine.ms.render;

import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import net.lopymine.ms.client.MoreSpaceClient;
import net.lopymine.ms.config.MoreSpaceConfig;
import net.lopymine.ms.entity.EntityCaptures;

import java.util.*;

public class MoreSpaceRenderer {

	public static void handleEntityRendering(Entity entity, Runnable renderCall) {
		MoreSpaceConfig config = MoreSpaceClient.getConfig();
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

		if (entity instanceof PlayerEntity player) {
			HashSet<UUID> favoritePlayers = config.getFavoritePlayers();
			if (favoritePlayers.contains(player.getUuid())) {
				renderCall.run();
				return;
			}
		}

		EntityCaptures.MAIN.setEntity(entity);
		renderCall.run();
		EntityCaptures.MAIN.clearEntity();
	}

}
