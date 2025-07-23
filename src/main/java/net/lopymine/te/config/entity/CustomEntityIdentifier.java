package net.lopymine.te.config.entity;

import net.lopymine.te.TransparentEntities;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public record CustomEntityIdentifier(Identifier id, Text name) implements EntityIdentifier {

	public static CustomEntityIdentifier of(String id) {
		return new CustomEntityIdentifier(TransparentEntities.id("custom_entities." + id), TransparentEntities.text("custom_entities." + id));
	}
}
