package net.lopymine.te.yacl.category;

import dev.isxander.yacl3.api.*;
import lombok.experimental.ExtensionMethod;

import net.lopymine.te.config.TransparentEntitiesConfig;
import net.lopymine.te.config.distance.TransparencyDistance;
import net.lopymine.te.yacl.custom.base.*;
import net.lopymine.te.yacl.custom.extension.SimpleOptionExtension;
import net.lopymine.te.yacl.custom.utils.SimpleContent;

@ExtensionMethod(SimpleOptionExtension.class)
public class GeneralCategory {

	public static ConfigCategory get(TransparentEntitiesConfig defConfig, TransparentEntitiesConfig config) {
		return SimpleCategory.startBuilder("general")
				.groups(getMainGroup(defConfig, config))
				.groups(getHidingGroup(defConfig, config))
				.build();
	}

	private static OptionGroup getMainGroup(TransparentEntitiesConfig defConfig, TransparentEntitiesConfig config) {
		return SimpleGroup.startBuilder("main").options(
				SimpleOption.<Boolean>startBuilder("mod_enabled")
						.withBinding(defConfig.isModEnabled(), config::isModEnabled, config::setModEnabled, true)
						.withController()
						.withDescription(SimpleContent.NONE)
						.build()
		).build();
	}

	private static OptionGroup getHidingGroup(TransparentEntitiesConfig defConfig, TransparentEntitiesConfig config) {
		TransparencyDistance defDistance = defConfig.getTransparencyDistance();
		TransparencyDistance distance = config.getTransparencyDistance();
		return SimpleGroup.startBuilder("hiding").options(
				SimpleOption.<Double>startBuilder("hiding_activation_distance_xz")
						.withBinding(defDistance.getHidingActivationDistanceXZ(), distance::getHidingActivationDistanceXZ, distance::setHidingActivationDistanceXZ, true)
						.withController(0.0D, 32.0D, 0.1D)
						.withDescription(SimpleContent.NONE)
						.build(),
				SimpleOption.<Double>startBuilder("hiding_activation_distance_y")
						.withBinding(defDistance.getHidingActivationDistanceY(), distance::getHidingActivationDistanceY, distance::setHidingActivationDistanceY, true)
						.withController(0.0D, 32.0D, 0.1D)
						.withDescription(SimpleContent.NONE)
						.build(),
				SimpleOption.<Double>startBuilder("full_hiding_distance")
						.withBinding(defDistance.getFullHidingDistance(), distance::getFullHidingDistance, distance::setFullHidingDistance, true)
						.withController(0.0D, 32.0D, 0.1D)
						.withDescription(SimpleContent.NONE)
						.build(),
				SimpleOption.<Double>startBuilder("min_hiding_value")
						.withBinding(defConfig.getMinHidingValue(), config::getMinHidingValue, config::setMinHidingValue, true)
						.withController(0.10D, 1.0D, 0.01D)
						.withDescription(SimpleContent.NONE)
						.build(),
				SimpleOption.<Boolean>startBuilder("hide_shadow_enabled")
						.withBinding(defConfig.isHideShadowEnabled(), config::isHideShadowEnabled, config::setHideShadowEnabled, true)
						.withController()
						.withDescription(SimpleContent.NONE)
						.build(),
				SimpleOption.<Boolean>startBuilder("hide_head_when_wearing_something")
						.withBinding(defConfig.isHideHeadWhenWearingSomething(), config::isHideHeadWhenWearingSomething, config::setHideHeadWhenWearingSomething, true)
						.withController()
						.withDescription(SimpleContent.NONE)
						.build(),
				SimpleOption.<Boolean>startBuilder("show_glint_when_translucent")
						.withBinding(defConfig.isShowGlintWhenTranslucent(), config::isShowGlintWhenTranslucent, config::setShowGlintWhenTranslucent, true)
						.withController()
						.withDescription(SimpleContent.NONE)
						.build()
		).build();
	}

}
