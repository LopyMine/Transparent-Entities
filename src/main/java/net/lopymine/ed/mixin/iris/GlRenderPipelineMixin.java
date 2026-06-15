package net.lopymine.ed.mixin.iris;

import com.mojang.blaze3d.opengl.*;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.lopymine.ed.compat.LoadedMods;
import net.lopymine.ed.config.EntityDitheringConfig;
import net.lopymine.ed.utils.pipeline.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GlRenderPipeline.class)
public class GlRenderPipelineMixin {

	@Shadow @Final private RenderPipeline info;

	@Inject(method = "program", at = @At("RETURN"), cancellable = true)
	private void entityDithering$swapDithering(CallbackInfoReturnable<GlProgram> cir) {
		DitheringMarker thing = (DitheringMarker) this.info;
		if (!thing.entityDithering$isDithering()) {
			return;
		}
		if (!EntityDitheringConfig.getInstance().isModEnabled()) {
			return;
		}
		if (!LoadedMods.IRIS_LOADED) {
			return;
		}
		GlProgram ditheringProgram = ((ProgramContainer) cir.getReturnValue()).entityDithering$get();
		if (ditheringProgram != null) {
			cir.setReturnValue(ditheringProgram);
		}
	}
}
