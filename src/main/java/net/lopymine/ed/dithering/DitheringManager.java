package net.lopymine.ed.dithering;

import java.util.*;
import net.lopymine.ed.config.EntityDitheringConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import org.jetbrains.annotations.Nullable;

public class DitheringManager {

	private static final Set<EntityType<?>> ENTITY_TYPES_WITH_DITHERING = new HashSet<>();
	private static boolean MY_PLAYER = false;

	public static void loadEntityTypes(EntityDitheringConfig config) {
		ENTITY_TYPES_WITH_DITHERING.clear();

		MY_PLAYER = false;
		for (Identifier hideEntity : config.getHideEntities()) {
			if (hideEntity.equals(EntityDitheringConfig.MY_PLAYER.id())) {
				MY_PLAYER = true;
				continue;
			}
			Optional<Reference<EntityType<?>>> optional = BuiltInRegistries.ENTITY_TYPE.get(hideEntity);
			if (optional.isEmpty()) {
				continue;
			}
			EntityType<?> type = optional.get().value();
			ENTITY_TYPES_WITH_DITHERING.add(type);
		}
	}

	public static boolean shouldDither(EntityType<?> type, @Nullable UUID uuid) {
		LocalPlayer player = Minecraft.getInstance().player;
		if (player != null && player.getUUID().equals(uuid)) {
			return MY_PLAYER;
		}
		if (!ENTITY_TYPES_WITH_DITHERING.contains(type)) {
			return false;
		}
		return true;
	}

}
