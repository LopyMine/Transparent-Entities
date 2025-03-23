package net.lopymine.te.utils;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;

import java.util.*;
import org.jetbrains.annotations.*;

public class WorldUtils {

	public static Optional<Entity> getEntity(@Nullable ClientWorld world, @NotNull UUID uuid) {
		if (world == null) {
			return Optional.empty();
		}
		for (Entity entity : world.getEntities()) {
			if (entity.getUuid().equals(uuid)) {
				return Optional.of(entity);
			}
		}
		return Optional.empty();
	}

}
