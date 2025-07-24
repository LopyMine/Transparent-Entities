package net.lopymine.te.mixin.nickname;

import net.lopymine.te.transparency.TransparencyRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin {

	//? if >=1.21.2 {
	@Inject(at = @At("HEAD"), method = "renderLabelIfPresent")
	private void handleBeforeLabelRendering(net.minecraft.client.render.entity.state.EntityRenderState state, Text text, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
		TransparencyRenderer.handleLabelRenderingOne();
	}

	@Inject(at = @At("TAIL"), method = "renderLabelIfPresent")
	private void handleAfterLabelRendering(net.minecraft.client.render.entity.state.EntityRenderState state, Text text, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
		TransparencyRenderer.handleLabelRenderingTwo();
	}
	//?} else {
	/*@Inject(at = @At("HEAD"), method = "renderLabelIfPresent")
	private void handleLabelRenderingOne(net.minecraft.entity.Entity entity, Text text, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, float tickDelta, CallbackInfo ci) {
		TransparencyRenderer.handleLabelRenderingOne();
	}

	@Inject(at = @At("TAIL"), method = "renderLabelIfPresent")
	private void handleLabelRenderingTwo(net.minecraft.entity.Entity entity, Text text, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, float tickDelta, CallbackInfo ci) {
		TransparencyRenderer.handleLabelRenderingTwo();
	}
	*///?}

}
