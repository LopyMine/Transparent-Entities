package net.lopymine.ed.mixin.render;

import com.llamalad7.mixinextras.injector.wrapoperation.*;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.systems.RenderPass;
import net.lopymine.ed.dithering.vanilla.*;
import net.lopymine.ed.compat.IrisAPI;
import net.lopymine.ed.utils.pipeline.DitheringMarker;
import net.minecraft.client.renderer.rendertype.RenderType;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderType.class)
public class RenderTypeMixin implements DitheringMarker {

	@Unique
	private boolean entityDithering$bl;
	@Unique
	private boolean entityDithering$bl2;
	@Unique
	@Nullable
	private RenderPipeline entityDithering$lastRenderPipeline = null;
	@Unique
	@Nullable
	private RenderPipeline entityDithering$lastDitheringRenderPipeline = null;

	@WrapOperation(at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderPass;setPipeline(Lcom/mojang/blaze3d/pipeline/RenderPipeline;)V"), method = "draw")
	private void swapRenderPipeline(RenderPass instance, RenderPipeline pipeline, Operation<Void> original) {
		((DitheringMarker) pipeline).entityDithering$setDithering(this.entityDithering$bl);
		if (!this.entityDithering$bl) {
			original.call(instance, pipeline);
			return;
		}
		this.entityDithering$bl = false;
		if (IrisAPI.isShaderPackInUse()) {
			this.entityDithering$bl2 = false;
			original.call(instance, pipeline);
			return;
		}
		if (this.entityDithering$lastRenderPipeline == pipeline) {
			original.call(instance, this.entityDithering$lastDitheringRenderPipeline);
			this.entityDithering$bl2 = true;
			return;
		}
		RenderPipeline ditheringPipeline = VanillaDitheringShaderManager.getDitheringPipeline(pipeline);
		if (ditheringPipeline != null) {
			this.entityDithering$lastRenderPipeline          = pipeline;
			this.entityDithering$lastDitheringRenderPipeline = ditheringPipeline;
			original.call(instance, ditheringPipeline);
			this.entityDithering$bl2 = true;
			return;
		}
		original.call(instance, pipeline);
		this.entityDithering$bl2 = false;
	}

	@Inject(
			at = @At(
					value = "INVOKE",
					target = "Lcom/mojang/blaze3d/systems/RenderPass;setUniform(Ljava/lang/String;Lcom/mojang/blaze3d/buffers/GpuBufferSlice;)V"
			),
			method = "draw"
	)
	private void bindDitheringDataUniform(CallbackInfo ci, @Local RenderPass pass) {
		if (!this.entityDithering$bl2) {
			return;
		}
		pass.setUniform("DitheringData", VanillaDitheringDataBuffer.BUFFER);
	}

	@Override
	public void entityDithering$setDithering(boolean bl) {
		this.entityDithering$bl = bl;
	}

	@Override
	public boolean entityDithering$isDithering() {
		return this.entityDithering$bl;
	}
}
