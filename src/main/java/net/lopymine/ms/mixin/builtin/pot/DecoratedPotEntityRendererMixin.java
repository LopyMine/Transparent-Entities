package net.lopymine.ms.mixin.builtin.pot;

import com.llamalad7.mixinextras.injector.wrapoperation.*;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.DecoratedPotBlockEntityRenderer;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

import net.lopymine.ms.render.TransparencyManager;
import net.lopymine.ms.render.MoreSpaceLayers;

import java.util.function.Function;

@Mixin(DecoratedPotBlockEntityRenderer.class)
public class DecoratedPotEntityRendererMixin {

	//? if <=1.21.3 {
	/*@WrapOperation(method = "render(Lnet/minecraft/block/entity/DecoratedPotBlockEntity;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;II)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/SpriteIdentifier;getVertexConsumer(Lnet/minecraft/client/render/VertexConsumerProvider;Ljava/util/function/Function;)Lnet/minecraft/client/render/VertexConsumer;"))
	private VertexConsumer wrapRenderLayer(SpriteIdentifier instance, VertexConsumerProvider vertexConsumers, Function<Identifier, RenderLayer> layerFactory, Operation<VertexConsumer> original) {
		return original.call(instance, vertexConsumers, MoreSpaceLayers.getLayer(layerFactory));
	}
	*///?} else {
	@ModifyArg(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/block/entity/Sherds;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/SpriteIdentifier;getVertexConsumer(Lnet/minecraft/client/render/VertexConsumerProvider;Ljava/util/function/Function;)Lnet/minecraft/client/render/VertexConsumer;"), index = 1)
	private Function<Identifier, RenderLayer> wrapRenderLayer(Function<Identifier, RenderLayer> layerFactory) {
		return MoreSpaceLayers.getLayer(layerFactory);
	}
	//?}

    @ModifyArg(method = "renderDecoratedSide", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/SpriteIdentifier;getVertexConsumer(Lnet/minecraft/client/render/VertexConsumerProvider;Ljava/util/function/Function;)Lnet/minecraft/client/render/VertexConsumer;"), index = 1)
    private Function<Identifier, RenderLayer> wrapDecoratedSideRenderLayer(Function<Identifier, RenderLayer> layerFactory) {
	    return MoreSpaceLayers.getLayer(layerFactory);
    }

}
