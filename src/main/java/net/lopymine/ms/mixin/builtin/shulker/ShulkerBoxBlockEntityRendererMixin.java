package net.lopymine.ms.mixin.builtin.shulker;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.ShulkerBoxBlockEntityRenderer;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

import net.lopymine.ms.render.TransparencyManager;
import net.lopymine.ms.render.MoreSpaceLayers;

import java.util.function.Function;

@Mixin(ShulkerBoxBlockEntityRenderer.class)
public class ShulkerBoxBlockEntityRendererMixin {

	//? if <=1.21.3 {
	/*@ModifyArg(method = "render(Lnet/minecraft/block/entity/ShulkerBoxBlockEntity;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;II)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/SpriteIdentifier;getVertexConsumer(Lnet/minecraft/client/render/VertexConsumerProvider;Ljava/util/function/Function;)Lnet/minecraft/client/render/VertexConsumer;"), index = 1)
	private Function<Identifier, RenderLayer> modifyRenderLayer(Function<Identifier, RenderLayer> layerFactory) {
		return MoreSpaceLayers.getLayer(layerFactory);
	}
	*///?} else {
	@ModifyArg(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/util/math/Direction;FLnet/minecraft/client/util/SpriteIdentifier;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/SpriteIdentifier;getVertexConsumer(Lnet/minecraft/client/render/VertexConsumerProvider;Ljava/util/function/Function;)Lnet/minecraft/client/render/VertexConsumer;"), index = 1)
	private Function<Identifier, RenderLayer> modifyRenderLayer(Function<Identifier, RenderLayer> layerFactory) {
		return MoreSpaceLayers.getLayer(layerFactory);
	}
	//?}

}
