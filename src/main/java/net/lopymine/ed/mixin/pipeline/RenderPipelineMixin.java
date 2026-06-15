package net.lopymine.ed.mixin.pipeline;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.lopymine.ed.utils.pipeline.DitheringMarker;
import org.spongepowered.asm.mixin.*;

@Mixin(RenderPipeline.class)
public class RenderPipelineMixin implements DitheringMarker {

	@Unique
	private boolean entityDithering$bl;

	@Override
	public void entityDithering$setDithering(boolean bl) {
		this.entityDithering$bl = bl;
	}

	@Override
	public boolean entityDithering$isDithering() {
		return this.entityDithering$bl;
	}
}
