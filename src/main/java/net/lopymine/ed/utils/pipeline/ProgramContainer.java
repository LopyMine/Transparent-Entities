package net.lopymine.ed.utils.pipeline;

import com.mojang.blaze3d.opengl.GlProgram;
import org.jetbrains.annotations.Nullable;

public interface ProgramContainer {

	@Nullable GlProgram entityDithering$get();

	void entityDithering$set(@Nullable GlProgram program);

}
