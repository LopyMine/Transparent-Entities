package net.lopymine.te.client;

import lombok.*;
import org.slf4j.*;

import net.fabricmc.api.ClientModInitializer;

import net.lopymine.te.TransparentEntities;
import net.lopymine.te.client.command.TransparentEntitiesCommandManager;
import net.lopymine.te.client.keybinding.TransparentEntitiesKeybindingManager;
import net.lopymine.te.config.TransparentEntitiesConfig;


public class TransparentEntitiesClient implements ClientModInitializer {

	public static Logger LOGGER = LoggerFactory.getLogger(TransparentEntities.MOD_NAME + "/Client");

	@Setter
	@Getter
	private static TransparentEntitiesConfig config;

	@Override
	public void onInitializeClient() {
		LOGGER.info("{} Client Initialized", TransparentEntities.MOD_NAME);
		TransparentEntitiesClient.config = TransparentEntitiesConfig.getInstance();
		TransparentEntitiesKeybindingManager.register();
		TransparentEntitiesCommandManager.register();
	}
}
