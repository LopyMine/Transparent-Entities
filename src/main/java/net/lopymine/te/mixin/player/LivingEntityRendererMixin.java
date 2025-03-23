package net.lopymine.te.mixin.player;

import com.llamalad7.mixinextras.injector.wrapoperation.*;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.*;
import net.minecraft.client.render.entity.feature.*;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import net.lopymine.te.render.TransparencyManager;
import net.lopymine.te.utils.ArgbUtils;

//? if >=1.21.2 {
import net.lopymine.te.utils.*;
import net.lopymine.te.client.TransparentEntitiesClient;
import net.minecraft.client.render.entity.state.*;
import net.lopymine.te.entity.EntityCaptures;
//?}

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin {

	//? if <=1.21.1 {
	/*@WrapOperation(method = "render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/model/EntityModel;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;III)V"))
	private void wrapRender(EntityModel<?> instance, MatrixStack matrixStack, VertexConsumer vertexConsumer, int a, int b, int c, Operation<Void> original, @Local(argsOnly = true) LivingEntity livingEntity) {
		float alpha = ArgbUtils.getAlpha(TransparencyManager.getTranslucentArgb(livingEntity, c));
		if (alpha != 0.0F) {
			original.call(instance, matrixStack, vertexConsumer, a, b, c);
		}
	}

	@WrapOperation(method = "render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/feature/FeatureRenderer;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/Entity;FFFFFF)V"))
	private void wrapFeatureRender(FeatureRenderer<?, ?> instance, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, Entity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch, Operation<Void> original) {
		float alpha = ArgbUtils.getAlpha(TransparencyManager.getTranslucentArgb(entity, -1));
		if (alpha != 0.0F) {
			original.call(instance, matrices, vertexConsumers, light, entity, limbAngle, limbDistance, tickDelta, animationProgress, headYaw, headPitch);
		}
	}
	*///?} else {
	@WrapOperation(method = "render(Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/model/EntityModel;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;III)V"))
	private void wrapRender(EntityModel<?> instance, MatrixStack matrixStack, VertexConsumer vertexConsumer, int a, int b, int c, Operation<Void> original, @Local(argsOnly = true) LivingEntityRenderState state) {
		Entity entity = EntityCaptures.MAIN.getEntity();
		if (entity != null && ArgbUtils.getAlpha(TransparencyManager.getTranslucentArgb(entity, -1)) == 0) {
			return;
		}
		original.call(instance, matrixStack, vertexConsumer, a, b, c);
	}

	@WrapOperation(method = "render(Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/feature/FeatureRenderer;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/state/EntityRenderState;FF)V"))
	private void wrapFeatureRender(FeatureRenderer<?, ?> instance, MatrixStack matrixStack, VertexConsumerProvider provider, int i, EntityRenderState entityRenderState, float a, float b, Operation<Void> original, @Local(argsOnly = true) LivingEntityRenderState state) {
		Entity entity = EntityCaptures.MAIN.getEntity();
		if (entity != null && ArgbUtils.getAlpha(TransparencyManager.getTranslucentArgb(entity, -1)) == 0) {
			return;
		}
		original.call(instance, matrixStack, provider, i, entityRenderState, a, b);
	}
	//?}
}