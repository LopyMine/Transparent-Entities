package net.lopymine.ed.mixin.iris;

import com.mojang.blaze3d.opengl.GlProgram;
import net.lopymine.ed.utils.pipeline.ProgramContainer;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;

@Mixin(GlProgram.class)
public class RenderPipelineMixin implements ProgramContainer {

	@Unique
	private GlProgram entityDithering$program;

	@Override
	public @Nullable GlProgram entityDithering$get() {
		return this.entityDithering$program;
	}

	@Override
	public void entityDithering$set(@Nullable GlProgram program) {
		this.entityDithering$program = program;
	}
}
