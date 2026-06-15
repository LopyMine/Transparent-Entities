package net.lopymine.ed.mixin.pipeline;

import com.mojang.blaze3d.pipeline.*;
import com.mojang.blaze3d.platform.PolygonMode;
import com.mojang.blaze3d.vertex.VertexFormat;
import java.util.*;
import net.minecraft.client.renderer.ShaderDefines;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@SuppressWarnings("unused")
@Mixin(RenderPipeline.Builder.class)
public interface RenderPipelineBuilderAccessor {

	@Accessor("location")
	Optional<Identifier> getLocation();

	@Accessor("fragmentShader")
	Optional<Identifier> getFragmentShader();

	@Accessor("vertexShader")
	Optional<Identifier> getVertexShader();

	@Accessor("definesBuilder")
	Optional<ShaderDefines.Builder> getDefinesBuilder();

	@Accessor("samplers")
	Optional<List<String>> getSamplers();

	@Accessor("uniforms")
	Optional<List<RenderPipeline.UniformDescription>> getUniforms();

	@Accessor("depthStencilState")
	Optional<DepthStencilState> getDepthStencilState();

	@Accessor("polygonMode")
	Optional<PolygonMode> getPolygonMode();

	@Accessor("cull")
	Optional<Boolean> getCull();

	@Accessor("colorTargetState")
	Optional<ColorTargetState> getColorTargetState();

	@Accessor("vertexFormat")
	Optional<VertexFormat> getVertexFormat();

	@Accessor("vertexFormatMode")
	Optional<VertexFormat.Mode> getVertexFormatMode();


//	@Accessor("location")
//	Optional<Identifier> getLocation();
//
//	@Accessor("fragmentShader")
//	Optional<Identifier> getFragmentShader();
//
//	@Accessor("vertexShader")
//	Optional<Identifier> getVertexShader();
//
//	@Accessor("definesBuilder")
//	Optional<ShaderDefines.Builder> getDefinesBuilder();
//
//	@Accessor("samplers")
//	Optional<List<String>> getSamplers();
//
//	@Accessor("uniforms")
//	Optional<List<UniformDescription>> getUniforms();
//
//	@Accessor("depthTestFunction")
//	Optional<DepthTestFunction> getDepthTestFunction();
//
//	@Accessor("polygonMode")
//	Optional<PolygonMode> getPolygonMode();
//
//	@Accessor("cull")
//	Optional<Boolean> getCull();
//
//	@Accessor("writeColor")
//	Optional<Boolean> getWriteColor();
//
//	@Accessor("writeAlpha")
//	Optional<Boolean> getWriteAlpha();
//
//	@Accessor("writeDepth")
//	Optional<Boolean> getWriteDepth();
//
//	@Accessor("colorLogic")
//	Optional<LogicOp> getColorLogic();
//
//	@Accessor("blendFunction")
//	Optional<BlendFunction> getBlendFunction();
//
//	@Accessor("vertexFormat")
//	Optional<VertexFormat> getVertexFormat();
//
//	@Accessor("vertexFormatMode")
//	Optional<VertexFormat.Mode> getVertexFormatMode();
//
//	@Accessor("depthBiasScaleFactor")
//	float getDepthBiasScaleFactor();
//
//	@Accessor("depthBiasConstant")
//	float getDepthBiasConstant();
}
