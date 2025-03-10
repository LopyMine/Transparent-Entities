package net.lopymine.ms.mixin;

import net.minecraft.client.model.ModelPart;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

import net.lopymine.ms.manager.HidingManager;
import net.lopymine.ms.entity.EntityCaptures;

@Mixin(ModelPart.class)
public class ModelPartMixin {

	@ModifyArg(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;III)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelPart;renderCuboids(Lnet/minecraft/client/util/math/MatrixStack$Entry;Lnet/minecraft/client/render/VertexConsumer;III)V"), index = 4)
	private int render(int argb) {
		Entity entity = EntityCaptures.MAIN.getEntity();
		if (entity != null) {
			return HidingManager.INSTANCE.getAlpha(entity, argb);
		}
		return argb;
	}

}