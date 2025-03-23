package net.lopymine.ms.mixin;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.lopymine.ms.render.MoreSpaceLayers;

@Mixin(RenderLayer.class)
public class RenderLayerMixin {

	@Inject(at = @At("RETURN"), method = {
			"getEntityCutout",
			"getEntitySmoothCutout",
			"getEntitySolid",
			"getEntitySolidZOffsetForward",
			"getEntityNoOutline",
			"getEntityTranslucentEmissiveNoOutline",
	}, cancellable = true)
	private static void swapRenderLayer(Identifier texture, CallbackInfoReturnable<RenderLayer> cir) {
		cir.setReturnValue(MoreSpaceLayers.getLayer(texture, cir::getReturnValue));
	}

	@Inject(at = @At("RETURN"), method = {
			"getEntityCutoutNoCull(Lnet/minecraft/util/Identifier;Z)Lnet/minecraft/client/render/RenderLayer;",
			"getEntityCutoutNoCullZOffset(Lnet/minecraft/util/Identifier;Z)Lnet/minecraft/client/render/RenderLayer;",
			"getEntityTranslucent(Lnet/minecraft/util/Identifier;Z)Lnet/minecraft/client/render/RenderLayer;",
			"getEntityTranslucentEmissive(Lnet/minecraft/util/Identifier;Z)Lnet/minecraft/client/render/RenderLayer;"
	}, cancellable = true)
	private static void swapRenderLayerBl(Identifier texture, boolean affectsOutline, CallbackInfoReturnable<RenderLayer> cir) {
		cir.setReturnValue(MoreSpaceLayers.getLayer(texture, cir::getReturnValue));
	}

}
