package net.lopymine.te.utils;

import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import net.lopymine.te.TransparentEntities;
import net.lopymine.te.yacl.custom.utils.SimpleContent;

import java.util.function.Function;

public class ModMenuUtils {

	public static String getOptionKey(String optionId) {
		return String.format("modmenu.option.%s", optionId);
	}

	public static Text getLabel(String optionId) {
		return TransparentEntities.text(String.format("modmenu.label.%s", optionId));
	}

	public static String getCategoryKey(String categoryId) {
		return String.format("modmenu.category.%s", categoryId);
	}

	public static String getGroupKey(String groupId) {
		return String.format("modmenu.group.%s", groupId);
	}

	public static Text getName(String key) {
		return TransparentEntities.text(key + ".name");
	}

	public static Text getDescription(String key) {
		return TransparentEntities.text(key + ".description");
	}

	public static Identifier getContentId(SimpleContent content, String contentId) {
		return TransparentEntities.id(String.format("textures/config/%s.%s", contentId, content.getFileExtension()));
	}

	public static Text getModTitle() {
		return TransparentEntities.text("modmenu.title");
	}

	public static Function<Boolean, Text> getEnabledOrDisabledFormatter() {
		return state -> TransparentEntities.text("modmenu.formatter.enabled_or_disabled." + state);
	}

	public static Text getNoConfigScreenMessage() {
		return TransparentEntities.text("modmenu.no_config_library_screen.message");
	}

	public static Text getOldConfigScreenMessage(String version) {
		return TransparentEntities.text("modmenu.old_config_library_screen.message", version, TransparentEntities.YACL_DEPEND_VERSION);
	}
}
