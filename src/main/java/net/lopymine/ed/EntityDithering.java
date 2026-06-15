package net.lopymine.ed;

import net.lopymine.mossylib.logger.MossyLogger;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;

public class EntityDithering {

	public static final String MOD_NAME = /*$ mod_name*/ "Entity Dithering";
	public static final String MOD_ID = /*$ mod_id*/ "entity_dithering";
	public static final MossyLogger LOGGER = new MossyLogger(MOD_NAME);

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}

	public static MutableComponent text(String path, Object... args) {
		return Component.translatable(String.format("%s.%s", MOD_ID, path), args);
	}

	public static void onInitialize() {
		LOGGER.info("{} Initialized", MOD_NAME);
	}
}