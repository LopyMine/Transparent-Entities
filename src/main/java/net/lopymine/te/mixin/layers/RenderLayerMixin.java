package net.lopymine.te.mixin.layers;

import net.lopymine.te.transparency.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RenderLayer.class)
public class RenderLayerMixin {

	@Inject(at = @At("RETURN"), method = {
			"getEntityCutout",
			"getEntitySmoothCutout",
			"getEntitySolid",
			/*? if >=1.21.2 {*/ "getEntitySolidZOffsetForward", /*?}*/
			"getEntityNoOutline",
			"getEntityTranslucentEmissiveNoOutline",
	}, cancellable = true)
	private static void swapRenderLayer(Identifier texture, CallbackInfoReturnable<RenderLayer> cir) {
		cir.setReturnValue(TransparencyLayers.getLayer(texture, cir::getReturnValue));
	}

	@Inject(at = @At("RETURN"), method = {
			"getEntityCutoutNoCull(Lnet/minecraft/util/Identifier;Z)Lnet/minecraft/client/render/RenderLayer;",
			"getEntityCutoutNoCullZOffset(Lnet/minecraft/util/Identifier;Z)Lnet/minecraft/client/render/RenderLayer;",
			"getEntityTranslucent(Lnet/minecraft/util/Identifier;Z)Lnet/minecraft/client/render/RenderLayer;",
			"getEntityTranslucentEmissive(Lnet/minecraft/util/Identifier;Z)Lnet/minecraft/client/render/RenderLayer;"
	}, cancellable = true)
	private static void swapRenderLayerWithBoolean(Identifier texture, boolean affectsOutline, CallbackInfoReturnable<RenderLayer> cir) {
		cir.setReturnValue(TransparencyLayers.getLayer(texture, cir::getReturnValue));
	}

	//? if >=1.21.5 {
	@Inject(at = @At("RETURN"), method = {
			"getOpaqueParticle"
	}, cancellable = true)
	private static void swapParticleRenderLayer(Identifier texture, CallbackInfoReturnable<RenderLayer> cir) {
		cir.setReturnValue(TransparencyLayers.getLayer(texture, cir::getReturnValue));
	}
	//?}

}
