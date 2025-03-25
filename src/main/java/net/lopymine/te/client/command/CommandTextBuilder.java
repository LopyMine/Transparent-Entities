package net.lopymine.te.client.command;

import net.minecraft.entity.EntityType;
import net.minecraft.text.*;
import net.minecraft.text.HoverEvent.*;

import net.lopymine.te.TransparentEntities;

import java.util.UUID;

@SuppressWarnings("unused")
public class CommandTextBuilder {

	private static final MutableText MOD_ID_TEXT = TransparentEntities.text("command.id");

	private final String key;
	private final MutableText text;

	private CommandTextBuilder(String key, Object... args) {
		this.key  = key;
		this.text = CommandTextBuilder.translatable(key, args);
	}

	private static MutableText translatable(String key, Object... args) {
		for (int i = 0; i < args.length; ++i) {
			Object object = args[i];
			if (!isPrimitive(object) && !(object instanceof Text)) {
				args[i] = String.valueOf(object);
			}
		}

		return TransparentEntities.sillyText("command.%s".formatted(key), args);
	}

	private static boolean isPrimitive(Object object) {
		return object instanceof Number || object instanceof Boolean || object instanceof String;
	}

	public static CommandTextBuilder startBuilder(String key, Object... args) {
		return new CommandTextBuilder(key, args);
	}

	public Text build() {
		return MOD_ID_TEXT.copy().append(" ").append(this.text);
	}
}
