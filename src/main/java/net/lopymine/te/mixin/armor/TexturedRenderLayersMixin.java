package net.lopymine.te.mixin.armor;

import net.lopymine.te.transparency.TransparencyLayers;
import net.minecraft.client.render.*;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TexturedRenderLayers.class)
public class TexturedRenderLayersMixin {

	@Inject(at = @At("RETURN"), method = {
			"getArmorTrims"
	}, cancellable = true)
	private static void swapArmorTrimsRenderLayer(boolean decal, CallbackInfoReturnable<RenderLayer> cir) {
		cir.setReturnValue(TransparencyLayers.getArmorLayer(false, TexturedRenderLayers.ARMOR_TRIMS_ATLAS_TEXTURE, cir::getReturnValue));
	}

}
