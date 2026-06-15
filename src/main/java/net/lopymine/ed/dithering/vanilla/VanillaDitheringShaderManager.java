package net.lopymine.ed.dithering.vanilla;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.pipeline.RenderPipeline.Builder;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import net.lopymine.ed.EntityDithering;
import net.lopymine.ed.utils.pipeline.BuilderCopyDithering;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.Nullable;

public class VanillaDitheringShaderManager {

	private static final Map<RenderPipeline, RenderPipeline> MAP = new IdentityHashMap<>();

	public static void createDitheringPipeline(RenderPipeline originalPipeline, Builder originalBuilder) {
		Builder builder = RenderPipeline.builder();
		((BuilderCopyDithering) builder).entityDithering$copyDithering(originalBuilder);
		RenderPipeline createdPipeline = builder.build();
		RenderPipelines.register(createdPipeline);
		MAP.put(originalPipeline, createdPipeline);
	}

	@Nullable
	public static RenderPipeline getDitheringPipeline(RenderPipeline original) {
		return MAP.get(original);
	}

	private static final Map<Identifier, Identifier> SHADER_IDS = new ConcurrentHashMap<>();

	public static Identifier createDitheringShaderId(Identifier base) {
		Identifier createdId = EntityDithering.id("dithering/" + base.getNamespace() + "/" + base.getPath());
		SHADER_IDS.put(createdId, base);
		return createdId;
	}

	@Nullable
	public static Identifier getDitheringShaderId(Identifier syntheticId) {
		return SHADER_IDS.get(syntheticId);
	}

}
