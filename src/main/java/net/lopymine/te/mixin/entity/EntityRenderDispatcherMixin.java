package net.lopymine.te.mixin.entity;

import com.llamalad7.mixinextras.injector.wrapoperation.*;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

import net.lopymine.te.transparency.TransparencyRenderer;

//? if >=1.21.2 {
import net.minecraft.client.render.entity.state.EntityRenderState;
//?}

@Mixin(EntityRenderDispatcher.class)
public class EntityRenderDispatcherMixin {

	//? if >=1.21.5 {
	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/EntityRenderDispatcher;render(Lnet/minecraft/client/render/entity/state/EntityRenderState;DDDLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/EntityRenderer;)V"), method = "render(Lnet/minecraft/entity/Entity;DDDFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/EntityRenderer;)V")
	private void handleEntityRendering(EntityRenderDispatcher instance, EntityRenderState state, double x, double y, double z, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, EntityRenderer<?, EntityRenderState> renderer, Operation<Void> original, @Local(argsOnly = true) Entity entity) {
		TransparencyRenderer.handleEntityRendering(entity, () -> original.call(instance, state, x, y, z, matrices, vertexConsumers, light, renderer));
	}
	//?} elif >=1.21.2 {
	/*@SuppressWarnings("all")
	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/EntityRenderer;render(Lnet/minecraft/client/render/entity/state/EntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"), method = "render(Lnet/minecraft/entity/Entity;DDDFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/EntityRenderer;)V")
	private void handleEntityRendering(EntityRenderer instance, EntityRenderState entityRenderState, MatrixStack matrixStack, VertexConsumerProvider provider, int i, Operation<Void> original, @Local(argsOnly = true) Entity entity) {
		TransparencyRenderer.handleEntityRendering(entity, () -> original.call(instance, entityRenderState, matrixStack, provider, i));
	}
	*///?} else {
	/*@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/EntityRenderer;render(Lnet/minecraft/entity/Entity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"), method = "render")
	private void handleEntityRendering(EntityRenderer instance, Entity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, Operation<Void> original) {
		TransparencyRenderer.handleEntityRendering(entity, () -> original.call(instance, entity, yaw, tickDelta, matrices, vertexConsumers, light));

	}
	*///?}

}

