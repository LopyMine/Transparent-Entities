package net.lopymine.ed.mixin.render;

import com.llamalad7.mixinextras.sugar.Local;
import java.util.List;
import net.lopymine.ed.thing.ThingMarks;
import net.lopymine.ed.utils.pipeline.DitheringMarker;
import net.minecraft.client.renderer.SubmitNodeCollection;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SubmitNodeCollection.class)
public class SubmitNodeCollectionMixin {

	@Inject(at = @At("HEAD"), method = "submitModelPart")
	private void enableDitheringForModelPart(CallbackInfo ci, @Local(argsOnly = true) RenderType renderType) {
		boolean bl = Boolean.TRUE.equals(ThingMarks.ENTITY_SUBMITTING.get().getValue());
		if (bl) {
			((DitheringMarker) renderType).entityDithering$setDithering(true);
		}
	}

	@Inject(at = @At("HEAD"), method = "submitModel")
	private void enableDitheringForModel(CallbackInfo ci, @Local(argsOnly = true) RenderType renderType) {
		boolean bl = Boolean.TRUE.equals(ThingMarks.ENTITY_SUBMITTING.get().getValue());
		if (bl) {
			((DitheringMarker) renderType).entityDithering$setDithering(true);
		}
	}

	@Inject(at = @At("HEAD"), method = "submitItem")
	private void enableDitheringForModel(CallbackInfo ci, @Local(argsOnly = true) List<BakedQuad> quads) {
		boolean bl = Boolean.TRUE.equals(ThingMarks.ENTITY_SUBMITTING.get().getValue());
		if (bl) {
			for (BakedQuad quad : quads) {
				RenderType renderType = quad.materialInfo().itemRenderType();
				((DitheringMarker) renderType).entityDithering$setDithering(true);
			}
		}
	}

	@Inject(at = @At("HEAD"), method = "submitBlockModel")
	private void enableDitheringForBlockModel(CallbackInfo ci, @Local(argsOnly = true) RenderType renderType) {
		boolean bl = Boolean.TRUE.equals(ThingMarks.ENTITY_SUBMITTING.get().getValue());
		if (bl) {
			((DitheringMarker) renderType).entityDithering$setDithering(true);
		}
	}

	@Inject(at = @At("HEAD"), method = "submitCustomGeometry")
	private void enableDitheringForCustomGeometry(CallbackInfo ci, @Local(argsOnly = true) RenderType renderType) {
		boolean bl = Boolean.TRUE.equals(ThingMarks.ENTITY_SUBMITTING.get().getValue());
		if (bl) {
			((DitheringMarker) renderType).entityDithering$setDithering(true);
		}
	}

}
