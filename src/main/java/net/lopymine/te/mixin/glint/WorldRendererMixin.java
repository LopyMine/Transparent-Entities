package net.lopymine.te.mixin.glint;

import com.llamalad7.mixinextras.injector.wrapoperation.*;
import net.lopymine.te.transparency.layers.TransparencyArmorLayer;
import net.minecraft.client.render.*;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {

	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/VertexConsumerProvider$Immediate;draw(Lnet/minecraft/client/render/RenderLayer;)V"), method = /*? if >=1.21.2 {*/ "method_62214" /*?} else {*/ /*"render" *//*?}*/)
	private void wrapOperation(Immediate instance, RenderLayer layer, Operation<Void> original) {
		original.call(instance, layer);
		if (layer == RenderLayer.getGlintTranslucent()) {
			original.call(instance, TransparencyArmorLayer.ARMOR_GLINT_TRANSLUCENT);
		}
	}

}
