package net.lopymine.te.mixin.entity;

import com.llamalad7.mixinextras.injector.*;
import com.llamalad7.mixinextras.injector.wrapoperation.*;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.render.entity.*;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import net.lopymine.te.entity.EntityCaptures;
import net.lopymine.te.render.TransparencyManager;
import net.lopymine.te.utils.ArgbUtils;

//? if >=1.21.4 {
import net.minecraft.client.render.entity.state.EntityRenderState;
//?}

@Mixin(EntityRenderDispatcher.class)
public class EntityRenderDispatcherShadowMixin {

	//? if >=1.21.5 {
	/*@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/EntityRenderer;getShadowOpacity(Lnet/minecraft/client/render/entity/state/EntityRenderState;)F"), method = "render(Lnet/minecraft/client/render/entity/state/EntityRenderState;DDDLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/EntityRenderer;)V")
	private float generated222(EntityRenderer instance, EntityRenderState entityRenderState, Operation<Float> original) {
		float call = original.call(instance, entityRenderState);
		Entity entity = EntityCaptures.MAIN.getEntity();
		if (entity == null || !TransparencyManager.canRenderTransparencyShadow(entity)) {
			return call;
		}
		int originalColor = ArgbUtils.getArgb((int) (call * 255F), 255, 255, 255);
		return ArgbUtils.getAlpha(TransparencyManager.getTranslucentArgb(entity, originalColor)) / 255F;
	}
	*///?} elif >=1.21.4 {
	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/EntityRenderer;getShadowOpacity(Lnet/minecraft/client/render/entity/state/EntityRenderState;)F"), method = "render(Lnet/minecraft/entity/Entity;DDDFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/EntityRenderer;)V")
	private float generated(EntityRenderer instance, EntityRenderState entityRenderState, Operation<Float> original, @Local(argsOnly = true) Entity entity) {
		float call = original.call(instance, entityRenderState);
		if (!TransparencyManager.canRenderTransparencyShadow(entity)) {
			return call;
		}
		int originalColor = ArgbUtils.getArgb((int) (call * 255F), 255, 255, 255);
		return ArgbUtils.getAlpha(TransparencyManager.getTranslucentArgb(entity, originalColor)) / 255F;
	}
	//?} elif >=1.21.3 {
	/*@ModifyExpressionValue(at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/entity/EntityRenderer;shadowOpacity:F"), method = "render(Lnet/minecraft/entity/Entity;DDDFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/EntityRenderer;)V")
	private float generated(float originalAlpha, @Local(argsOnly = true) Entity entity) {
		if (!TransparencyManager.canRenderTransparencyShadow(entity)) {
			return originalAlpha;
		}
		int originalColor = ArgbUtils.getArgb((int) (originalAlpha * 255F), 255, 255, 255);
		return ArgbUtils.getAlpha(TransparencyManager.getTranslucentArgb(entity, originalColor)) / 255F;
	}
	*///?} elif >=1.21.2 {
	/*@ModifyExpressionValue(at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/entity/EntityRenderer;shadowOpacity:F"), method = "render(Lnet/minecraft/entity/Entity;DDDFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/EntityRenderer;)V")
	private float generated(float originalAlpha, @Local(argsOnly = true) Entity entity) {
		if (!TransparencyManager.canRenderTransparencyShadow(entity)) {
			return originalAlpha;
		}
		int originalColor = ArgbUtils.getArgb((int) (originalAlpha * 255F), 255, 255, 255);
		return ArgbUtils.getAlpha(TransparencyManager.getTranslucentArgb(entity, originalColor)) / 255F;
	}
	*///?} else {
	/*@ModifyExpressionValue(at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/entity/EntityRenderer;shadowOpacity:F"), method = "render")
	private float generated(float originalAlpha, @Local(argsOnly = true) Entity entity) {
		if (!TransparencyManager.canRenderTransparencyShadow(entity)) {
			return originalAlpha;
		}
		int originalColor = ArgbUtils.getArgb((int) (originalAlpha * 255F), 255, 255, 255);
		return ArgbUtils.getAlpha(TransparencyManager.getTranslucentArgb(entity, originalColor)) / 255F;
	}
	*///?}

}
