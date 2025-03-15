package net.lopymine.ms.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.*;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.render.entity.*;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import net.lopymine.ms.client.MoreSpaceClient;
import net.lopymine.ms.render.TransparencyManager;
import net.lopymine.ms.utils.ArgbUtils;

@Mixin(EntityRenderDispatcher.class)
public class EntityRenderDispatcherShadowMixin {

	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/EntityRenderer;getShadowOpacity(Lnet/minecraft/client/render/entity/state/EntityRenderState;)F"), method = "render(Lnet/minecraft/entity/Entity;DDDFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/EntityRenderer;)V")
	private float generated(EntityRenderer instance, EntityRenderState entityRenderState, Operation<Float> original, @Local(argsOnly = true) Entity entity) {
		float call = original.call(instance, entityRenderState);
		if (!MoreSpaceClient.getConfig().isHideShadowEnabled() || !MoreSpaceClient.getConfig().isModEnabled()) {
			return call;
		}
		int originalColor = ArgbUtils.getArgb((int) (call * 255F), 255, 255, 255);
		return ArgbUtils.getAlpha(TransparencyManager.getTranslucentArgb(entity, originalColor)) / 255F; // todo port it to old versions
	}

}
