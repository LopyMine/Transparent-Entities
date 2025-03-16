package net.lopymine.ms.mixin;

//? if >=1.21.2 {

import com.llamalad7.mixinextras.injector.wrapoperation.*;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.*;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import net.lopymine.ms.render.MoreSpaceRenderer;

@Mixin(EntityRenderDispatcher.class)
public class EntityRenderDispatcherMixin {

	@SuppressWarnings("all")
	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/EntityRenderer;render(Lnet/minecraft/client/render/entity/state/EntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"), method = "render(Lnet/minecraft/entity/Entity;DDDFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/EntityRenderer;)V")
	private void generated(EntityRenderer instance, EntityRenderState entityRenderState, MatrixStack matrixStack, VertexConsumerProvider provider, int i, Operation<Void> original, @Local(argsOnly = true) Entity entity) {
		MoreSpaceRenderer.handleEntityRendering(entity, () -> original.call(instance, entityRenderState, matrixStack, provider, i));
	}

}
//?}
