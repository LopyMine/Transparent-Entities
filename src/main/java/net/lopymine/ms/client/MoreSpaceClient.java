package net.lopymine.ms.client;

import lombok.*;
import org.slf4j.*;

import net.fabricmc.api.ClientModInitializer;

import net.lopymine.ms.MoreSpace;
import net.lopymine.ms.client.keybinding.KeybindingManager;
import net.lopymine.ms.config.MoreSpaceConfig;


public class MoreSpaceClient implements ClientModInitializer {

	public static Logger LOGGER = LoggerFactory.getLogger(MoreSpace.MOD_NAME + "/Client");

	@Setter
	@Getter
	private static MoreSpaceConfig config;

	@Override
	public void onInitializeClient() {
		LOGGER.info("{} Client Initialized", MoreSpace.MOD_NAME);
		MoreSpaceClient.config = MoreSpaceConfig.getInstance();
		KeybindingManager.register();
	}
}
