package net.lopymine.ed.client;

import lombok.*;
import net.lopymine.ed.EntityDithering;
import net.lopymine.mossylib.logger.MossyLogger;
import net.lopymine.ed.config.EntityDitheringConfig;

public class EntityDitheringClient {

	public static final MossyLogger LOGGER = EntityDithering.LOGGER.extend("Client");

	public static void onInitializeClient() {
		LOGGER.info("{} Client Initialized", EntityDithering.MOD_NAME);
	}
}
