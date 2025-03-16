package net.lopymine.ms.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.*;
import net.minecraft.client.render.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import net.lopymine.ms.render.MoreSpaceLayers;

@Mixin(RenderLayers.class)
public class RenderLayersMixin {

	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/TexturedRenderLayers;getEntityCutout()Lnet/minecraft/client/render/RenderLayer;"), method = "getEntityBlockLayer")
	private static RenderLayer generated(Operation<RenderLayer> original) {
		return MoreSpaceLayers.getLayer(() -> original.call());
	}

}
