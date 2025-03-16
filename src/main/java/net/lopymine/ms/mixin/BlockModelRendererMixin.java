package net.lopymine.ms.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.block.BlockModelRenderer;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.util.math.MatrixStack.Entry;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import net.lopymine.ms.entity.EntityCaptures;
import net.lopymine.ms.render.TransparencyManager;
import net.lopymine.ms.utils.ArgbUtils;

@Mixin(BlockModelRenderer.class)
public class BlockModelRendererMixin {

	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/VertexConsumer;quad(Lnet/minecraft/client/util/math/MatrixStack$Entry;Lnet/minecraft/client/render/model/BakedQuad;FFFFII)V"), method = "renderQuads")
	private static void generated(VertexConsumer instance, Entry matrixEntry, BakedQuad quad, float red, float green, float blue, float a, int i, int j, Operation<Void> original) {
		Entity entity = EntityCaptures.MAIN.getEntity();
		if (entity == null) {
			original.call(instance, matrixEntry, quad, red, green, blue, a, i, j);
			return;
		}
		int originalArgb = ArgbUtils.getArgb((int)(a * 255F), (int) (red * 255F), (int) (green * 255F), (int) (blue * 255F));
		float alpha = ArgbUtils.getAlpha(TransparencyManager.getTranslucentArgb(entity, originalArgb)) / 255F;
		original.call(instance, matrixEntry, quad, red, green, blue, alpha, i, j);
	}

}
