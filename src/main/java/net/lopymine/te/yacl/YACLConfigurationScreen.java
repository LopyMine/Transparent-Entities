package net.lopymine.te.yacl;

import lombok.experimental.ExtensionMethod;
import net.minecraft.client.gui.screen.Screen;

import net.lopymine.te.client.TransparentEntitiesClient;
import net.lopymine.te.config.TransparentEntitiesConfig;
import net.lopymine.te.yacl.category.*;
import net.lopymine.te.yacl.custom.extension.SimpleOptionExtension;
import net.lopymine.te.yacl.custom.screen.*;

@ExtensionMethod(SimpleOptionExtension.class)
public class YACLConfigurationScreen {

	private YACLConfigurationScreen() {
		throw new IllegalStateException("Screen class");
	}

	public static Screen createScreen(Screen parent) {
		TransparentEntitiesConfig defConfig = TransparentEntitiesConfig.getNewInstance();
		TransparentEntitiesConfig config = TransparentEntitiesClient.getConfig();

		return SimpleYACLScreen.startBuilder(parent, config::save)
				.categories(GeneralCategory.get(defConfig, config))
				.categories(EntitiesCategory.get(defConfig, config))
				.build();
	}

	public static boolean notOpen(Screen currentScreen) {
		return !(currentScreen instanceof TransparentEntitiesYACLScreen);
	}
}


