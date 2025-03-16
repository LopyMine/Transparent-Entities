package net.lopymine.ms.yacl;

import lombok.experimental.ExtensionMethod;
import net.minecraft.client.gui.screen.Screen;

import net.lopymine.ms.client.MoreSpaceClient;
import net.lopymine.ms.config.MoreSpaceConfig;
import net.lopymine.ms.yacl.category.*;
import net.lopymine.ms.yacl.custom.extension.SimpleOptionExtension;
import net.lopymine.ms.yacl.custom.screen.*;

@ExtensionMethod(SimpleOptionExtension.class)
public class YACLConfigurationScreen {

	private YACLConfigurationScreen() {
		throw new IllegalStateException("Screen class");
	}

	public static Screen createScreen(Screen parent) {
		MoreSpaceConfig defConfig = new MoreSpaceConfig();
		MoreSpaceConfig config = MoreSpaceClient.getConfig();

		return SimpleYACLScreen.startBuilder(parent, config::save)
				.categories(GeneralCategory.get(defConfig, config))
				.categories(EntitiesCategory.get(defConfig, config))
				.build();
	}

	public static boolean notOpen(Screen currentScreen) {
		return !(currentScreen instanceof MoreSpaceYACLScreen);
	}
}


