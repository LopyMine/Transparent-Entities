package net.lopymine.ed.config.entity;

import net.lopymine.ed.EntityDithering;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public record CustomEntityIdentifier(Identifier id, Component name) implements EntityIdentifier {

	public static CustomEntityIdentifier of(String id) {
		return new CustomEntityIdentifier(EntityDithering.id("custom_entities." + id), EntityDithering.text("custom_entities." + id));
	}
}
