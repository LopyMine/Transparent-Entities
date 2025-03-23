package net.lopymine.te.mixin.features;

import net.minecraft.entity.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.lopymine.te.client.TransparentEntitiesClient;
import net.lopymine.te.render.TransparencyManager;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

	@Inject(at = @At("RETURN"), method = "canHit", cancellable = true)
	private void disableHitIfNear(CallbackInfoReturnable<Boolean> cir) {
		if (!TransparentEntitiesClient.getConfig().isClickThroughTranslucentPlayersEnabled() || !TransparentEntitiesClient.getConfig().isModEnabled()) {
			return;
		}

		LivingEntity livingEntity = (LivingEntity) (Object) this;
		if (TransparencyManager.isTransparency(livingEntity)) {
			cir.setReturnValue(false);
		}
	}

}
