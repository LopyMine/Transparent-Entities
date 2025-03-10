package net.lopymine.ms.mixin.ears;

import com.llamalad7.mixinextras.injector.wrapoperation.*;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.feature.Deadmau5FeatureRenderer;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

import net.lopymine.ms.manager.HidingManager;

@Mixin(Deadmau5FeatureRenderer.class)
public class Deadmau5FeatureRendererMixin {

	//? if <=1.21.1 {
	@WrapOperation(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/network/AbstractClientPlayerEntity;FFFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/RenderLayer;getEntitySolid(Lnet/minecraft/util/Identifier;)Lnet/minecraft/client/render/RenderLayer;"))
	private RenderLayer wrapRenderLayer(Identifier identifier, Operation<RenderLayer> original) {
		return HidingManager.INSTANCE.getLayer(identifier, () -> original.call(identifier));
	}
	//?} else {
	/*@WrapOperation(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/state/PlayerEntityRenderState;FF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/RenderLayer;getEntitySolid(Lnet/minecraft/util/Identifier;)Lnet/minecraft/client/render/RenderLayer;"))
	private RenderLayer wrapRenderLayer(Identifier identifier, Operation<RenderLayer> original) {
		return HidingManager.INSTANCE.getLayer(identifier, () -> original.call(identifier));
	}
	*///?}

}
