package net.lopymine.ms.yacl;

import dev.isxander.yacl3.api.*;
import lombok.experimental.ExtensionMethod;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import net.lopymine.ms.client.MoreSpaceClient;
import net.lopymine.ms.config.MoreSpaceConfig;
import net.lopymine.ms.utils.ModMenuUtils;
import net.lopymine.ms.yacl.base.*;
import net.lopymine.ms.yacl.extension.SimpleOptionExtension;
import net.lopymine.ms.yacl.screen.*;
import net.lopymine.ms.yacl.utils.*;

import java.util.function.Function;

@ExtensionMethod(SimpleOptionExtension.class)
public class YACLConfigurationScreen {

	private static final Function<Boolean, Text> ENABLED_OR_DISABLE_FORMATTER = ModMenuUtils.getEnabledOrDisabledFormatter();

	private YACLConfigurationScreen() {
		throw new IllegalStateException("Screen class");
	}

	public static Screen createScreen(Screen parent) {
		MoreSpaceConfig defConfig = new MoreSpaceConfig();
		MoreSpaceConfig config = MoreSpaceClient.getConfig();

		return SimpleYACLScreen.startBuilder(parent, config::save)
				.categories(getGeneralCategory(defConfig, config))
				.build();
	}

	private static ConfigCategory getGeneralCategory(MoreSpaceConfig defConfig, MoreSpaceConfig config) {
		return SimpleCategory.startBuilder("general")
				.groups(getMainGroup(defConfig, config))
				.groups(getHidingGroup(defConfig, config))
				.groups(getGameplayGroup(defConfig, config))
				.build();
	}

	private static OptionGroup getMainGroup(MoreSpaceConfig defConfig, MoreSpaceConfig config) {
		return SimpleGroup.startBuilder("main").options(
				SimpleOption.<Boolean>startBuilder("mod_enabled")
						.withBinding(defConfig.isModEnabled(), config::isModEnabled, config::setModEnabled, true)
						.withController(ENABLED_OR_DISABLE_FORMATTER)
						.withDescription(SimpleContent.NONE)
						.build()
		).build();
	}

	private static OptionGroup getHidingGroup(MoreSpaceConfig defConfig, MoreSpaceConfig config) {
		return SimpleGroup.startBuilder("hiding").options(
				SimpleOption.<Float>startBuilder("hiding_activation_distance")
						.withBinding(defConfig.getHidingActivationDistance(), config::getHidingActivationDistance, config::setHidingActivationDistance, true)
						.withController(0.0F, 32.0F, 0.1F)
						.withDescription(SimpleContent.NONE)
						.build(),
				SimpleOption.<Float>startBuilder("full_hiding_distance")
						.withBinding(defConfig.getFullHidingDistance(), config::getFullHidingDistance, config::setFullHidingDistance, true)
						.withController(0.0F, 32.0F, 0.1F)
						.withDescription(SimpleContent.NONE)
						.build(),
				SimpleOption.<Float>startBuilder("min_hiding_value")
						.withBinding(defConfig.getMinHidingValue(), config::getMinHidingValue, config::setMinHidingValue, true)
						.withController(0.0F, 1.0F, 0.1F)
						.withDescription(SimpleContent.NONE)
						.build()
		).build();
	}

	private static OptionGroup getGameplayGroup(MoreSpaceConfig defConfig, MoreSpaceConfig config) {
		return SimpleGroup.startBuilder("gameplay").options(
				SimpleOption.<Boolean>startBuilder("click_through_translucent_players_enabled")
						.withBinding(defConfig.isClickThroughTranslucentPlayersEnabled(), config::isClickThroughTranslucentPlayersEnabled, config::setClickThroughTranslucentPlayersEnabled, true)
						.withController(ENABLED_OR_DISABLE_FORMATTER)
						.withDescription(SimpleContent.NONE)
						.build()
		).build();
	}

	public static boolean notOpen(Screen currentScreen) {
		return !(currentScreen instanceof MoreSpaceYACLScreen);
	}
}


