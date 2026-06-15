package net.lopymine.ed.config.entity;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public interface EntityIdentifier {

	Identifier id();

	Component name();

	static EntityIdentifier of(Identifier id, Component name) {
		return new EntityIdentifier() {
			@Override
			public Identifier id() {
				return id;
			}

			@Override
			public Component name() {
				return name;
			}
		};
	}

}
