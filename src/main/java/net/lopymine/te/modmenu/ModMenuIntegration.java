package net.lopymine.te.modmenu;

import com.terraformersmc.modmenu.api.*;

import net.fabricmc.loader.api.*;

import net.lopymine.te.TransparentEntities;
import net.lopymine.te.client.TransparentEntitiesClient;
import net.lopymine.te.yacl.YACLConfigurationScreen;

public class ModMenuIntegration implements ModMenuApi {

	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		FabricLoader fabricLoader = FabricLoader.getInstance();
		if (fabricLoader.isModLoaded("yet_another_config_lib_v3")) {
			ModContainer modContainer = fabricLoader.getModContainer("yet_another_config_lib_v3").orElseThrow();
			Version version = modContainer.getMetadata().getVersion();
			try {
				Version requestsVersion = Version.parse(TransparentEntities.YACL_DEPEND_VERSION);
				if (version.compareTo(requestsVersion) >= 0) {
					return YACLConfigurationScreen::createScreen;
				}
			} catch (VersionParsingException e) {
				TransparentEntitiesClient.LOGGER.error("Failed to compare YACL version, tell mod author about this error: ", e);
			}
			return parent -> NoConfigLibraryScreen.createScreenAboutOldVersion(parent, version.getFriendlyString());
		}
		return NoConfigLibraryScreen::createScreen;
	}
}
