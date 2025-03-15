package net.lopymine.ms.mixin.parrot;

import com.llamalad7.mixinextras.injector.wrapoperation.*;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.feature.*;
import net.minecraft.client.render.entity.model.*;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

import net.lopymine.ms.render.TransparencyManager;
import net.lopymine.ms.render.MoreSpaceLayers;

@Mixin(ShoulderParrotFeatureRenderer.class)
public class ShoulderParrotFeatureRendererMixin {

	//? if <=1.21.1 {
	/*@WrapOperation(method = "method_17958", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/model/ParrotEntityModel;getLayer(Lnet/minecraft/util/Identifier;)Lnet/minecraft/client/render/RenderLayer;"))
	private RenderLayer wrapRenderLayer(ParrotEntityModel instance, Identifier identifier, Operation<RenderLayer> original) {
		return MoreSpaceLayers.getLayer(identifier, () -> original.call(instance));
	}
	*///?} else {
	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/model/ParrotEntityModel;getLayer(Lnet/minecraft/util/Identifier;)Lnet/minecraft/client/render/RenderLayer;"), method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/state/PlayerEntityRenderState;Lnet/minecraft/entity/passive/ParrotEntity$Variant;FFZ)V")
	private RenderLayer wrapRenderLayer(ParrotEntityModel instance, Identifier identifier, Operation<RenderLayer> original) {
		return MoreSpaceLayers.getLayer(identifier, () -> original.call(instance));
	}
	//?}

}
