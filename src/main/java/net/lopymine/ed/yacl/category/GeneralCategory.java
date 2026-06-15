package net.lopymine.ed.yacl.category;

import lombok.experimental.ExtensionMethod;

import net.lopymine.mossylib.yacl.api.*;
import net.lopymine.mossylib.yacl.extension.SimpleOptionExtension;
import net.lopymine.ed.config.EntityDitheringConfig;
import net.lopymine.ed.config.distance.DitheringDataConfig;

@ExtensionMethod(SimpleOptionExtension.class)
public class GeneralCategory {

	public static SimpleCategory get(EntityDitheringConfig defConfig, EntityDitheringConfig config) {
		return SimpleCategory.startBuilder("general")
				.groups(getMainGroup(defConfig, config))
				.groups(getHidingGroup(defConfig, config));
	}

	private static SimpleGroup getMainGroup(EntityDitheringConfig defConfig, EntityDitheringConfig config) {
		return SimpleGroup.startBuilder("main").options(
				SimpleOption.<Boolean>startBuilder("mod_enabled")
						.withBinding(defConfig.isModEnabled(), config::isModEnabled, config::setModEnabled, true)
						.withController()
						.withDescription(SimpleContent.NONE)
		);
	}

	private static SimpleGroup getHidingGroup(EntityDitheringConfig defConfig, EntityDitheringConfig config) {
		DitheringDataConfig defData = defConfig.getDitheringDataConfig();
		DitheringDataConfig data = config.getDitheringDataConfig();
		return SimpleGroup.startBuilder("hiding").options(
				SimpleOption.<Double>startBuilder("pixel_size")
						.withBinding(defData.getPixelSize(), data::getPixelSize, data::setPixelSize, true)
						.withController(0.0D, 32.0D, 0.1D)
						.withDescription(SimpleContent.NONE),
				SimpleOption.<Double>startBuilder("far")
						.withBinding(defData.getFar(), data::getFar, data::setFar, true)
						.withController(0.0D, 32.0D, 0.1D)
						.withDescription(SimpleContent.NONE),
				SimpleOption.<Double>startBuilder("near")
						.withBinding(defData.getNear(), data::getNear, data::setNear, true)
						.withController(-5.0D, 32.0D, 0.1D)
						.withDescription(SimpleContent.NONE),
				SimpleOption.<Double>startBuilder("min_value")
						.withBinding(defData.getMinValue(), data::getMinValue, data::setMinValue, true)
						.withController(0.10D, 1.0D, 0.01D)
						.withDescription(SimpleContent.NONE)
		);
	}

}
