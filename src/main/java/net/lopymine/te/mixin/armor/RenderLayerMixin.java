package net.lopymine.te.mixin.armor;

import net.lopymine.te.transparency.TransparencyLayers;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RenderLayer.class)
public class RenderLayerMixin {

	@Inject(at = @At("RETURN"), method = {
			"getArmorCutoutNoCull"
	}, cancellable = true)
	private static void swapArmorRenderLayer(Identifier texture, CallbackInfoReturnable<RenderLayer> cir) {
		cir.setReturnValue(TransparencyLayers.getArmorLayer(false, texture, cir::getReturnValue));
	}

	@Inject(at = @At("RETURN"), method = "getArmorEntityGlint", cancellable = true)
	private static void swapArmorGlint(CallbackInfoReturnable<RenderLayer> cir) {
		cir.setReturnValue(TransparencyLayers.getArmorGlintLayer(cir::getReturnValue));
	}

}
