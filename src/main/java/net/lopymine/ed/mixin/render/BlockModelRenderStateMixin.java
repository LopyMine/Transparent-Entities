package net.lopymine.ed.mixin.render;

import com.llamalad7.mixinextras.sugar.Local;
import net.lopymine.ed.thing.ThingMarks;
import net.lopymine.ed.utils.pipeline.DitheringMarker;
import net.minecraft.client.renderer.block.BlockModelRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Debug(export = true)
@Mixin(value = BlockModelRenderState.class, priority = 802)
public class BlockModelRenderStateMixin {

	@Inject(at = @At("HEAD"), method = "submitModel")
	private void enableDitheringForBlockModel(CallbackInfo ci, @Local(argsOnly = true) RenderType renderType) {
		boolean bl = Boolean.TRUE.equals(ThingMarks.ENTITY_SUBMITTING.get().getValue());
		if (bl && renderType != null) {
			((DitheringMarker) renderType).entityDithering$setDithering(true);
		}
	}

}
