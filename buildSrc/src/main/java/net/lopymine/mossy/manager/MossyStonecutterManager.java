package net.lopymine.mossy.manager;

import dev.kikugie.stonecutter.build.StonecutterBuildExtension;
import java.util.*;
import lombok.experimental.ExtensionMethod;
import org.gradle.api.Project;

import net.lopymine.mossy.MossyPlugin;

import org.jetbrains.annotations.NotNull;

@ExtensionMethod(MossyPlugin.class)
public class MossyStonecutterManager {

	public static void apply(@NotNull Project project, MossyPlugin plugin) {
		StonecutterBuildExtension stonecutter = project.getStonecutter();

		String mcVersion = plugin.getProjectMultiVersion().projectVersion();
		Map<String, String> properties = project.getMossyProperties("data");
		properties.putAll(project.getMossyProperties("build"));
		Map<String, String> dependencies = project.getMossyProperties("dep");
		properties.putAll(dependencies);
		properties.put("java", String.valueOf(plugin.getJavaVersionIndex()));
		properties.put("minecraft", mcVersion);
		properties.put("fabric_api_id", project.getStonecutter().compare("1.19.1", mcVersion) >= 0 ? "fabric" : "fabric-api");
		properties.put("mod_version", project.getVersion().toString());

		properties.forEach((key, value) -> {
			if (Objects.equals(key, "3dskinlayers")) {
				return;
			}
			stonecutter.getSwaps().put(key, getFormatted(value));
		});

		dependencies.forEach((modId, version) -> {
			if (Objects.equals(modId, "3dskinlayers")) {
				return;
			}
			stonecutter.getConstants().put(modId, !version.equals("unknown"));
		});
	}

	private static @NotNull String getFormatted(String modVersion) {
		return "\"%s\";".formatted(modVersion);
	}
}
