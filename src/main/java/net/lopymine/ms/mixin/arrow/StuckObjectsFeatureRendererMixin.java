package net.lopymine.ms.mixin.arrow;

//? if >=1.21.2 {

import com.llamalad7.mixinextras.injector.wrapoperation.*;
import net.minecraft.client.model.Model;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.feature.StuckObjectsFeatureRenderer;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import net.lopymine.ms.render.TransparencyManager;
import net.lopymine.ms.render.MoreSpaceLayers;

@Mixin(StuckObjectsFeatureRenderer.class)
public class StuckObjectsFeatureRendererMixin {

	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/Model;getLayer(Lnet/minecraft/util/Identifier;)Lnet/minecraft/client/render/RenderLayer;"), method = "renderObject")
	private RenderLayer generated(Model instance, Identifier texture, Operation<RenderLayer> original) {
		return MoreSpaceLayers.getLayer(texture, () -> original.call(instance, texture));
	}

}
//?}
