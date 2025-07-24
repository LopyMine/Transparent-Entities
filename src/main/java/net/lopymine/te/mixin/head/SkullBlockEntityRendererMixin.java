//? if >=1.21.6 {
/*package net.lopymine.te.mixin.head;

import com.llamalad7.mixinextras.injector.wrapoperation.*;
import net.lopymine.te.transparency.TransparencyLayers;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.SkullBlockEntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SkullBlockEntityRenderer.class)
public class SkullBlockEntityRendererMixin {

	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/VertexConsumerProvider;getBuffer(Lnet/minecraft/client/render/RenderLayer;)Lnet/minecraft/client/render/VertexConsumer;"), method = "renderSkull")
	private static VertexConsumer swapRenderLayer(VertexConsumerProvider instance, RenderLayer layer, Operation<VertexConsumer> original) {
		return original.call(instance, TransparencyLayers.getLayerWithRippedOutTexture(layer)); // xdd
	}

}
*///?}
