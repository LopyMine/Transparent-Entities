package net.lopymine.te.mixin.layers;

import net.minecraft.client.render.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.lopymine.te.render.TransparencyLayers;

@Mixin(TexturedRenderLayers.class)
public class TexturedRenderLayersMixin {

	@Inject(at = @At("RETURN"), method = {
			"getEntityCutout",
			"getEntitySolid"
	}, cancellable = true)
	private static void swapRenderLayer(CallbackInfoReturnable<RenderLayer> cir) {
		cir.setReturnValue(TransparencyLayers.getLayer(cir::getReturnValue));
	}

}

