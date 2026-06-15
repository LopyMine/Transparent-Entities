package net.lopymine.ed.mixin.iris;

import net.irisshaders.iris.pipeline.IrisRenderingPipeline;
import net.lopymine.ed.dithering.iris.IrisDitheringShaderManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(IrisRenderingPipeline.class)
public class IrisRenderingPipelineMixin {

	@Inject(method = "destroy", at = @At("HEAD"))
	private void entityDithering$clearDitheringShaders(CallbackInfo ci) {
		IrisDitheringShaderManager.clear();
	}
}
