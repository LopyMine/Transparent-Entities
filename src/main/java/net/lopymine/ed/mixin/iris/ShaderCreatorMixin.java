package net.lopymine.ed.mixin.iris;

import com.google.common.collect.ImmutableSet;
import com.mojang.blaze3d.vertex.VertexFormat;
import java.util.function.Supplier;
import net.irisshaders.iris.gl.blending.AlphaTest;
import net.irisshaders.iris.gl.framebuffer.GlFramebuffer;
import net.irisshaders.iris.gl.state.*;
import net.irisshaders.iris.pipeline.*;
import net.irisshaders.iris.pipeline.programs.*;
import net.irisshaders.iris.shaderpack.loading.ProgramId;
import net.irisshaders.iris.shaderpack.programs.ProgramSource;
import net.irisshaders.iris.uniforms.FrameUpdateNotifier;
import net.irisshaders.iris.uniforms.custom.CustomUniforms;
import net.lopymine.ed.dithering.iris.IrisDitheringShaderManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShaderCreator.class)
public class ShaderCreatorMixin {

	@Inject(method = "create", at = @At("RETURN"), cancellable = true)
	private static void entityDithering$createDitheringCopy(
			WorldRenderingPipeline pipeline, String name, ShaderKey shaderKey, ProgramSource source, ProgramId programId,
			GlFramebuffer writingToBeforeTranslucent, GlFramebuffer writingToAfterTranslucent, AlphaTest fallbackAlpha,
			VertexFormat vertexFormat, ShaderAttributeInputs inputs, FrameUpdateNotifier updateNotifier,
			IrisRenderingPipeline parent, Supplier<ImmutableSet<Integer>> flipped, FogMode fogMode, boolean isIntensity,
			boolean isFullbright, boolean isShadowPass, boolean isLines, CustomUniforms customUniforms,
			CallbackInfoReturnable<ShaderSupplier> cir
	) {

		if (!IrisDitheringShaderManager.isEntityKey(shaderKey)) {
			return;
		}

		ShaderSupplier wrapped = IrisDitheringShaderManager.wrapWithDithering(
				cir.getReturnValue(), pipeline, name, shaderKey, source, programId,
				writingToBeforeTranslucent, writingToAfterTranslucent, fallbackAlpha, vertexFormat,
				inputs, parent, flipped, isIntensity, isShadowPass, isLines, customUniforms);

		cir.setReturnValue(wrapped);
	}
}
