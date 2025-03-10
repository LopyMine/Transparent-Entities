package net.lopymine.ms.mixin;

import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.lopymine.ms.client.MoreSpaceClient;
import net.lopymine.ms.manager.HidingManager;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

	@Inject(at = @At("RETURN"), method = "canHit", cancellable = true)
	private void disableHitIfNear(CallbackInfoReturnable<Boolean> cir) {
		if (!MoreSpaceClient.getConfig().isClickThroughTranslucentPlayersEnabled()) {
			return;
		}
		if (!(((LivingEntity)(Object) this) instanceof PlayerEntity player)) {
			return;
		}
		boolean value = cir.getReturnValueZ();
		cir.setReturnValue(HidingManager.INSTANCE.canHit(player, value));
	}

}
