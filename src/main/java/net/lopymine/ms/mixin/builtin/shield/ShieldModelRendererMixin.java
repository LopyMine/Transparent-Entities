package net.lopymine.ms.mixin.builtin.shield;

//? if >=1.21.4 {

import com.llamalad7.mixinextras.injector.wrapoperation.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.ShieldEntityModel;
import net.minecraft.client.render.item.model.special.ShieldModelRenderer;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import net.lopymine.ms.render.TransparencyManager;
import net.lopymine.ms.render.MoreSpaceLayers;

@Mixin(ShieldModelRenderer.class)
public class ShieldModelRendererMixin {

	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/model/ShieldEntityModel;getLayer(Lnet/minecraft/util/Identifier;)Lnet/minecraft/client/render/RenderLayer;"), method = "render(Lnet/minecraft/component/ComponentMap;Lnet/minecraft/item/ModelTransformationMode;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IIZ)V")
	private RenderLayer generated(ShieldEntityModel instance, Identifier identifier, Operation<RenderLayer> original) {
		return MoreSpaceLayers.getLayer(identifier, () -> original.call(instance, identifier));
	}

}
//?}
