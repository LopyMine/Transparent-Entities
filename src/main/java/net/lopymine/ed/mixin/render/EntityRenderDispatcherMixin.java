package net.lopymine.ed.mixin.render;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import net.lopymine.ed.dithering.DitheringManager;
import net.lopymine.ed.thing.ThingMarks;
import net.lopymine.ed.utils.render.RenderStateWithUUID;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

@Mixin(EntityRenderDispatcher.class)
public class EntityRenderDispatcherMixin {

	@Inject(at = @At("RETURN"), method = "extractEntity")
	private void inject(CallbackInfoReturnable<EntityRenderState> cir, @Local(argsOnly = true) Entity entity) {
		((RenderStateWithUUID) cir.getReturnValue()).entityDithering$setUUID(entity.getUUID());
	}

	@Inject(at = @At("HEAD"), method = "submit")
	private void markEntity1(EntityRenderState entityRenderState, CameraRenderState cameraRenderState, double d, double e, double f, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CallbackInfo ci) {
		RenderStateWithUUID state = (RenderStateWithUUID) entityRenderState;
		boolean bl = DitheringManager.shouldDither(entityRenderState.entityType, state.entityDithering$getUUID());
		state.entityDithering$setUUID(null);
		ThingMarks.ENTITY_SUBMITTING.get().setValue(bl);
	}

	@Inject(at = @At("TAIL"), method = "submit")
	private void markEntity2(EntityRenderState entityRenderState, CameraRenderState cameraRenderState, double d, double e, double f, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CallbackInfo ci) {
		ThingMarks.ENTITY_SUBMITTING.get().setValue(false);
	}

}
