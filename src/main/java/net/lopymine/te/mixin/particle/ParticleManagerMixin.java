package net.lopymine.te.mixin.particle;

import com.llamalad7.mixinextras.injector.wrapoperation.*;
import net.lopymine.te.transparency.TransparencyRenderer;
import net.minecraft.client.particle.*;
import net.minecraft.client.render.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ParticleManager.class)
public class ParticleManagerMixin {

	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/particle/Particle;buildGeometry(Lnet/minecraft/client/render/VertexConsumer;Lnet/minecraft/client/render/Camera;F)V"), method = "renderParticles")
	private void handleParticleRendering(Particle instance, VertexConsumer vertexConsumer, Camera camera, float v, Operation<Void> original) {
		TransparencyRenderer.handleParticleRendering(instance, () -> original.call(instance, vertexConsumer, camera, v));
	}

}
