package net.lopymine.ed.dithering.iris;

import com.google.common.collect.ImmutableSet;
import com.google.common.primitives.Ints;
import com.mojang.blaze3d.opengl.GlProgram;
import com.mojang.blaze3d.vertex.VertexFormat;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;
import java.util.function.Supplier;
import net.irisshaders.iris.gl.blending.AlphaTest;
import net.irisshaders.iris.gl.blending.BlendModeOverride;
import net.irisshaders.iris.gl.blending.BufferBlendOverride;
import net.irisshaders.iris.gl.framebuffer.GlFramebuffer;
import net.irisshaders.iris.gl.state.FogMode;
import net.irisshaders.iris.gl.state.ShaderAttributeInputs;
import net.irisshaders.iris.gl.uniform.UniformUpdateFrequency;
import net.irisshaders.iris.pipeline.IrisRenderingPipeline;
import net.irisshaders.iris.pipeline.WorldRenderingPipeline;
import net.irisshaders.iris.pipeline.programs.*;
import net.irisshaders.iris.pipeline.transform.PatchShaderType;
import net.irisshaders.iris.pipeline.transform.TransformPatcher;
import net.irisshaders.iris.shaderpack.loading.ProgramId;
import net.irisshaders.iris.shaderpack.programs.ProgramSource;
import net.irisshaders.iris.uniforms.CommonUniforms;
import net.irisshaders.iris.uniforms.VanillaUniforms;
import net.irisshaders.iris.uniforms.builtin.BuiltinReplacementUniforms;
import net.irisshaders.iris.uniforms.custom.CustomUniforms;
import net.lopymine.ed.client.EntityDitheringClient;
import net.lopymine.ed.config.EntityDitheringConfig;
import net.lopymine.ed.config.distance.DitheringDataConfig;
import net.lopymine.ed.utils.pipeline.ProgramContainer;

public class IrisDitheringShaderManager {

	private static final Map<GlProgram, GlProgram> MAP = new IdentityHashMap<>();

	public static void register(GlProgram original, GlProgram dithering) {
		MAP.put(original, dithering);
		((ProgramContainer) original).entityDithering$set(dithering);
	}

	public static boolean isEntityKey(ShaderKey key) {
		ProgramId id = key.getProgram();
		return id == ProgramId.Entities || id == ProgramId.EntitiesTrans;
	}

	public static void clear() {
		for (Entry<GlProgram, GlProgram> entry : MAP.entrySet()) {
			try {
				GlProgram original = entry.getKey();
				GlProgram dithering = entry.getValue();
				((ProgramContainer) original).entityDithering$set(null);
				dithering.close();
			} catch (Exception ignored) { }
		}
		MAP.clear();
	}

	public static ShaderSupplier wrapWithDithering(
			ShaderSupplier original,
			WorldRenderingPipeline pipeline,
			String name,
			ShaderKey shaderKey,
			ProgramSource source,
			ProgramId programId,
			GlFramebuffer writingToBeforeTranslucent,
			GlFramebuffer writingToAfterTranslucent,
			AlphaTest fallbackAlpha,
			VertexFormat vertexFormat,
			ShaderAttributeInputs inputs,
			IrisRenderingPipeline parent,
			Supplier<ImmutableSet<Integer>> flipped,
			boolean isIntensity,
			boolean isShadowPass,
			boolean isLines,
			CustomUniforms customUniforms) {

		Supplier<GlProgram> originalSupplier = original.shader();

		Supplier<GlProgram> wrapped = () -> {
			GlProgram originalProgram = originalSupplier.get();
			try {
				GlProgram ditheringProgram = buildDithering(pipeline, name, shaderKey, source, programId,
						writingToBeforeTranslucent, writingToAfterTranslucent, fallbackAlpha, vertexFormat,
						inputs, parent, flipped, isIntensity, isShadowPass, isLines, customUniforms);
				if (ditheringProgram != null) {
					register(originalProgram, ditheringProgram);
				}
			} catch (IOException e) {
				throw new RuntimeException("Failed to build dithering copy for shader \"" + name + "\"", e);
			}
			return originalProgram;
		};

		return new ShaderSupplier(original.key(), original.id(), wrapped);
	}

	private static GlProgram buildDithering(
			WorldRenderingPipeline pipeline, String name, ShaderKey shaderKey, ProgramSource source, ProgramId programId,
			GlFramebuffer writingToBeforeTranslucent, GlFramebuffer writingToAfterTranslucent, AlphaTest fallbackAlpha,
			VertexFormat vertexFormat, ShaderAttributeInputs inputs, IrisRenderingPipeline parent,
			Supplier<ImmutableSet<Integer>> flipped, boolean isIntensity, boolean isShadowPass, boolean isLines,
			CustomUniforms customUniforms) throws IOException {

		AlphaTest alpha = source.getDirectives().getAlphaTestOverride().orElse(fallbackAlpha);
		BlendModeOverride blendModeOverride = source.getDirectives().getBlendModeOverride().orElse(programId.getBlendModeOverride());

		Map<PatchShaderType, String> transformed = TransformPatcher.patchVanilla(
				name,
				source.getVertexSource().orElseThrow(RuntimeException::new),
				source.getGeometrySource().orElse(null),
				source.getTessControlSource().orElse(null),
				source.getTessEvalSource().orElse(null),
				source.getFragmentSource().orElseThrow(RuntimeException::new),
				alpha, isLines, shaderKey == ShaderKey.CLOUDS, true, inputs, pipeline.getTextureMap());

		String vertex = transformed.get(PatchShaderType.VERTEX);
		String geometry = transformed.get(PatchShaderType.GEOMETRY);
		String tessControl = transformed.get(PatchShaderType.TESS_CONTROL);
		String tessEval = transformed.get(PatchShaderType.TESS_EVAL);
		String fragment = transformed.get(PatchShaderType.FRAGMENT);

		String ditherFragment = IrisShaderPatcher.patchFragmentShader(fragment);
		if (ditherFragment.equals(fragment)) {
			return null;
		}

		List<BufferBlendOverride> overrides = new ArrayList<>();
		source.getDirectives().getBufferBlendOverrides().forEach((information) -> {
			int index = Ints.indexOf(source.getDirectives().getDrawBuffers(), information.index());
			if (index > -1) {
				overrides.add(new BufferBlendOverride(index, information.blendMode()));
			}
		});

		String ditherName = name + "_dithering";
		PartialShader id = ShaderCreator.link(ditherName, vertex, geometry, tessControl, tessEval, ditherFragment, vertexFormat, false);

		EntityDitheringClient.LOGGER.info("Copied Iris GlProgram: \"{}\"", ditherName);

		return new ExtendedShader(id.getFinally(), ditherName, vertexFormat, tessControl != null || tessEval != null,
				writingToBeforeTranslucent, writingToAfterTranslucent, blendModeOverride, alpha,
				(uniforms) -> {
					CommonUniforms.addDynamicUniforms(uniforms, FogMode.PER_VERTEX);
					customUniforms.assignTo(uniforms);
					BuiltinReplacementUniforms.addBuiltinReplacementUniforms(uniforms);
					VanillaUniforms.addVanillaUniforms(uniforms);

					uniforms.uniform1f(UniformUpdateFrequency.PER_FRAME, "DitherMinValue", () -> ditheringConfig().getMinValue());
					uniforms.uniform1f(UniformUpdateFrequency.PER_FRAME, "DitherPixelSize", () -> ditheringConfig().getPixelSize());
					uniforms.uniform1f(UniformUpdateFrequency.PER_FRAME, "DitherNearDistance", () -> ditheringConfig().getNear());
					uniforms.uniform1f(UniformUpdateFrequency.PER_FRAME, "DitherFarDistance", () -> ditheringConfig().getFar());
				},
				(samplerHolder, imageHolder) -> parent.addGbufferOrShadowSamplers(samplerHolder, imageHolder, flipped, isShadowPass, inputs.hasTex(), inputs.hasLight(), inputs.hasOverlay()),
				isIntensity, parent, overrides, customUniforms);
	}

	private static DitheringDataConfig ditheringConfig() {
		return EntityDitheringConfig.getInstance().getDitheringDataConfig();
	}
}
