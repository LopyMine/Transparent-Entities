package net.lopymine.ms.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.*;
import net.minecraft.client.render.*;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import net.lopymine.ms.render.TransparencyManager;

import java.util.*;
import java.util.stream.Collectors;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {

	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/WorldRenderer;renderEntities(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider$Immediate;Lnet/minecraft/client/render/Camera;Lnet/minecraft/client/render/RenderTickCounter;Ljava/util/List;)V"), method = "method_62214")
	private void separateEntityRendering(WorldRenderer instance, MatrixStack matrixStack, Immediate immediate, Camera camera, RenderTickCounter renderTickCounter, List<Entity> list, Operation<Void> original) {
		Map<Boolean, List<Entity>> entities = list.stream().collect(Collectors.partitioningBy(TransparencyManager::isTransparency));

		List<Entity> solidEntities = entities.get(false);
		original.call(instance, matrixStack, immediate, camera, renderTickCounter, solidEntities);

		List<Entity> transparencyEntities = TransparencyManager.sortEntitiesByDistance(entities.get(true));
		original.call(instance, matrixStack, immediate, camera, renderTickCounter, transparencyEntities);
	}

}
