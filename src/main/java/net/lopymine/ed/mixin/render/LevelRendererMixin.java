package net.lopymine.ed.mixin.render;

import net.lopymine.ed.dithering.vanilla.VanillaDitheringDataBuffer;
import net.minecraft.client.renderer.LevelRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {

	@Inject(at = @At("HEAD"), method = "renderLevel")
	private void updateDitheringBuffer(CallbackInfo ci) {
		VanillaDitheringDataBuffer.update();
	}

}
