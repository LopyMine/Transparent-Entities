package net.lopymine.te.config.entity;

import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public interface EntityIdentifier {

	Identifier id();

	Text name();

	static EntityIdentifier of(Identifier id, Text name) {
		return new EntityIdentifier() {
			@Override
			public Identifier id() {
				return id;
			}

			@Override
			public Text name() {
				return name;
			}
		};
	}

}
