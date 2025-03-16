package net.lopymine.ms.mixin;

import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.lopymine.ms.client.MoreSpaceClient;
import net.lopymine.ms.render.TransparencyManager;
import net.lopymine.ms.render.MoreSpaceLayers;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

	@Inject(at = @At("RETURN"), method = "canHit", cancellable = true)
	private void disableHitIfNear(CallbackInfoReturnable<Boolean> cir) {
		if (!MoreSpaceClient.getConfig().isClickThroughTranslucentPlayersEnabled() || !MoreSpaceClient.getConfig().isModEnabled()) {
			return;
		}
		LivingEntity livingEntity = (LivingEntity) (Object) this;
		if (TransparencyManager.isTransparency(livingEntity)) {
			cir.setReturnValue(false);
		}
	}

}
