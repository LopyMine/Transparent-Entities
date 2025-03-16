package net.lopymine.ms.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import net.lopymine.ms.render.MoreSpaceLayers;

@Mixin(FeatureRenderer.class)
public class FeatureRendererMixin {

	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/RenderLayer;getEntityCutoutNoCull(Lnet/minecraft/util/Identifier;)Lnet/minecraft/client/render/RenderLayer;"), method = "renderModel")
	private static RenderLayer generated(Identifier texture, Operation<RenderLayer> original) {
		return MoreSpaceLayers.getLayer(texture, () -> original.call(texture));
	}

}
