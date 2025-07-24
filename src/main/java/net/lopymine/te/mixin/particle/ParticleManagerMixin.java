package net.lopymine.te.mixin.particle;

import com.llamalad7.mixinextras.injector.wrapoperation.*;
import net.lopymine.te.client.TransparentEntitiesClient;
import net.lopymine.te.config.TransparentEntitiesConfig;
import net.lopymine.te.transparency.*;
import net.minecraft.client.particle.*;
import net.minecraft.client.render.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ParticleManager.class)
public class ParticleManagerMixin {

	/*? if >=1.21.4 {*/
	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/particle/Particle;render(Lnet/minecraft/client/render/VertexConsumer;Lnet/minecraft/client/render/Camera;F)V"), method = "renderParticles(Lnet/minecraft/client/render/Camera;FLnet/minecraft/client/render/VertexConsumerProvider$Immediate;Lnet/minecraft/client/particle/ParticleTextureSheet;Ljava/util/Queue;)V")
	private static void handleParticleRendering(Particle instance, VertexConsumer vertexConsumer, Camera camera, float v, Operation<Void> original) {
		TransparencyRenderer.handleParticleRendering(instance, () -> original.call(instance, vertexConsumer, camera, v));
	}
	/*?} else {*/
	/*@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/particle/Particle;buildGeometry(Lnet/minecraft/client/render/VertexConsumer;Lnet/minecraft/client/render/Camera;F)V"), method = "renderParticles")
	private void handleParticleRendering(Particle instance, VertexConsumer vertexConsumer, Camera camera, float v, Operation<Void> original) {
		TransparencyRenderer.handleParticleRendering(instance, () -> original.call(instance, vertexConsumer, camera, v));
	}
	*//*?}*/

	//? if >=1.21.4 {
	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/particle/ParticleTextureSheet;renderType()Lnet/minecraft/client/render/RenderLayer;"), method = "renderParticles(Lnet/minecraft/client/render/Camera;FLnet/minecraft/client/render/VertexConsumerProvider$Immediate;Lnet/minecraft/client/particle/ParticleTextureSheet;Ljava/util/Queue;)V")
	private static RenderLayer swapOpaqueParticleRenderLayer(ParticleTextureSheet instance, Operation<RenderLayer> original) {
		if (TransparentEntitiesClient.getConfig().isModEnabled() && instance == ParticleTextureSheet.PARTICLE_SHEET_OPAQUE) {
			return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT.renderType();
		}
		return original.call(instance);
	}
	//?}

}
