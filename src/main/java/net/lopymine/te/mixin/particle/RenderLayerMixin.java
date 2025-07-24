package net.lopymine.te.mixin.particle;

import net.minecraft.client.render.RenderLayer;
import org.spongepowered.asm.mixin.Mixin;

//? if >=1.21.5 {
import net.lopymine.te.transparency.TransparencyLayers;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
//?}

@Mixin(RenderLayer.class)
public class RenderLayerMixin {

	//? if >=1.21.5 {
	@Inject(at = @At("RETURN"), method = {
			"getOpaqueParticle"
	}, cancellable = true)
	private static void swapParticleRenderLayer(Identifier texture, CallbackInfoReturnable<RenderLayer> cir) {
		cir.setReturnValue(TransparencyLayers.getParticleLayer(texture, cir::getReturnValue));
	}
	//?}

}
