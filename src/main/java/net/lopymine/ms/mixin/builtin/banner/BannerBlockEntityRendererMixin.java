package net.lopymine.ms.mixin.builtin.banner;

import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.BannerBlockEntityRenderer;
import net.minecraft.util.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

import net.lopymine.ms.manager.HidingManager;

import java.util.function.Function;

@Mixin(BannerBlockEntityRenderer.class)
public class BannerBlockEntityRendererMixin {

	//? if <=1.21.3 {
	@ModifyArg(method = "render(Lnet/minecraft/block/entity/BannerBlockEntity;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;II)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/SpriteIdentifier;getVertexConsumer(Lnet/minecraft/client/render/VertexConsumerProvider;Ljava/util/function/Function;)Lnet/minecraft/client/render/VertexConsumer;"), index = 1)
	private Function<Identifier, RenderLayer> modifyBaseBannerRenderLayer(Function<Identifier, RenderLayer> layerFactory) {
	    return HidingManager.INSTANCE.getLayer(layerFactory);
	}
	//?} else {
	/*@ModifyArg(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IIFLnet/minecraft/client/render/block/entity/model/BannerBlockModel;Lnet/minecraft/client/render/block/entity/model/BannerFlagBlockModel;FLnet/minecraft/util/DyeColor;Lnet/minecraft/component/type/BannerPatternsComponent;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/SpriteIdentifier;getVertexConsumer(Lnet/minecraft/client/render/VertexConsumerProvider;Ljava/util/function/Function;)Lnet/minecraft/client/render/VertexConsumer;"), index = 1)
	private static Function<Identifier, RenderLayer> modifyBaseBannerRenderLayer(Function<Identifier, RenderLayer> layerFactory) {
		return HidingManager.INSTANCE.getLayer(layerFactory);
	}
	*///?}

	//? if <=1.21.1 {
	@ModifyArg(method = "renderCanvas(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/model/ModelPart;Lnet/minecraft/client/util/SpriteIdentifier;ZLnet/minecraft/util/DyeColor;Lnet/minecraft/component/type/BannerPatternsComponent;Z)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/SpriteIdentifier;getVertexConsumer(Lnet/minecraft/client/render/VertexConsumerProvider;Ljava/util/function/Function;Z)Lnet/minecraft/client/render/VertexConsumer;"), index = 1)
	private static Function<Identifier, RenderLayer> modifyCanvasRenderLayer(Function<Identifier, RenderLayer> layerFactory) {
	    return HidingManager.INSTANCE.getLayer(layerFactory);
	}
	//?} else {
	/*@ModifyArg(method = "renderCanvas(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/model/ModelPart;Lnet/minecraft/client/util/SpriteIdentifier;ZLnet/minecraft/util/DyeColor;Lnet/minecraft/component/type/BannerPatternsComponent;ZZ)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/SpriteIdentifier;getVertexConsumer(Lnet/minecraft/client/render/VertexConsumerProvider;Ljava/util/function/Function;ZZ)Lnet/minecraft/client/render/VertexConsumer;"), index = 1)
	private static Function<Identifier, RenderLayer> modifyCanvasRenderLayer(Function<Identifier, RenderLayer> layerFactory) {
		return HidingManager.INSTANCE.getLayer(layerFactory);
	}
	*///?}

	@ModifyArg(method = "renderLayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/SpriteIdentifier;getVertexConsumer(Lnet/minecraft/client/render/VertexConsumerProvider;Ljava/util/function/Function;)Lnet/minecraft/client/render/VertexConsumer;"), index = 1)
	private static Function<Identifier, RenderLayer> modifyLayerRenderLayer(Function<Identifier, RenderLayer> layerFactory) {
		return HidingManager.INSTANCE.getLayer(layerFactory);
	}

}
