package net.lopymine.ms.utils;

import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import net.lopymine.ms.MoreSpace;
import net.lopymine.ms.yacl.utils.SimpleContent;

import java.util.function.Function;

public class ModMenuUtils {

	public static String getOptionKey(String optionId) {
		return String.format("modmenu.option.%s", optionId);
	}

	public static String getCategoryKey(String categoryId) {
		return String.format("modmenu.category.%s", categoryId);
	}

	public static String getGroupKey(String groupId) {
		return String.format("modmenu.group.%s", groupId);
	}

	public static Text getName(String key) {
		return MoreSpace.text(key + ".name");
	}

	public static Text getDescription(String key) {
		return MoreSpace.text(key + ".description");
	}

	public static Identifier getContentId(SimpleContent content, String contentId) {
		return MoreSpace.id(String.format("textures/config/%s.%s", contentId, content.getFileExtension()));
	}

	public static Text getModTitle() {
		return MoreSpace.text("modmenu.title");
	}

	public static Function<Boolean, Text> getEnabledOrDisabledFormatter() {
		return state -> MoreSpace.text("modmenu.formatter.enabled_or_disabled." + state);
	}

	public static Text getNoConfigScreenMessage() {
		return MoreSpace.text("modmenu.no_config_library_screen.message");
	}

	public static Text getOldConfigScreenMessage(String version) {
		return MoreSpace.text("modmenu.old_config_library_screen.message", version, MoreSpace.YACL_DEPEND_VERSION);
	}
}
