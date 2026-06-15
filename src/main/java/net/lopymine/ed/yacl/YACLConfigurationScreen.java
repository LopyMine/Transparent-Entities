package net.lopymine.ed.yacl;

import lombok.experimental.ExtensionMethod;
import net.lopymine.ed.yacl.category.*;
import net.lopymine.mossylib.yacl.api.SimpleYACLScreen;
import net.lopymine.mossylib.yacl.extension.SimpleOptionExtension;
import net.lopymine.ed.EntityDithering;
import net.lopymine.ed.config.EntityDitheringConfig;
import net.minecraft.client.gui.screens.Screen;

import net.lopymine.ed.client.EntityDitheringClient;

@ExtensionMethod(SimpleOptionExtension.class)
public class YACLConfigurationScreen {

	private YACLConfigurationScreen() {
		throw new IllegalStateException("Screen class");
	}

	public static Screen createScreen(Screen parent) {
		EntityDitheringConfig defConfig = EntityDitheringConfig.getNewInstance();
		EntityDitheringConfig config = EntityDitheringConfig.getInstance();

		return SimpleYACLScreen.startBuilder(EntityDithering.MOD_ID, parent, config::save)
				.categories(GeneralCategory.get(defConfig, config))
				.categories(EntitiesCategory.get(defConfig, config))
				.build();
	}
}


