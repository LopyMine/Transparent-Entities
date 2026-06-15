package net.lopymine.ed.dithering.vanilla;

import java.util.Set;
import net.minecraft.resources.Identifier;

public class VanillaDitheringTargets {

	public static final Set<Identifier> FRAGMENT_SHADERS = Set.of(
			Identifier.fromNamespaceAndPath("minecraft", "core/entity"),
			Identifier.fromNamespaceAndPath("minecraft", "core/item")
	);

	public static boolean isTarget(Identifier fragmentShader) {
		return fragmentShader != null && FRAGMENT_SHADERS.contains(fragmentShader);
	}
}
