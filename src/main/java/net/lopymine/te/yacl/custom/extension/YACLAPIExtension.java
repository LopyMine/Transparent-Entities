package net.lopymine.te.yacl.custom.extension;

import dev.isxander.yacl3.api.*;

import net.fabricmc.loader.api.*;
import net.fabricmc.loader.impl.util.version.StringVersion;

import net.lopymine.te.TransparentEntities;

import java.util.*;
import net.lopymine.te.yacl.custom.state.PreviewStateManager;

@SuppressWarnings("all")
public class YACLAPIExtension {

	public static <A> ListOption.Builder<A> bindingE(ListOption.Builder<A> builder, Binding<List<A>> binding, boolean instant) {
		builder.state(instant ? new PreviewStateManager<>(binding) : StateManager.createSimple(binding));
		return builder;
	}

	public static <A> Option.Builder<A> bindingE(Option.Builder<A> builder, Binding<A> binding, boolean instant) {
		builder.stateManager(instant ? new PreviewStateManager<>(binding) : StateManager.createSimple(binding));
		return builder;
	}

	private static Version getCurrentYACLVersion() {
		return FabricLoader.getInstance().getModContainer("yet_another_config_lib_v3").orElseThrow(
				() -> new NoSuchElementException(
						"Failed to find Yet Another Config Lib [YACL], this shouldn't happen! Please report this crash to discord server of %s mod!".formatted(TransparentEntities.MOD_NAME)
				)
		).getMetadata().getVersion();
	}

	private static Version getVersion(String version) {
		try {
			return Version.parse(version);
		} catch (Exception ignored) {
			return new StringVersion("1.0.0");
		}
	}
}
