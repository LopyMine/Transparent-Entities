package net.lopymine.ed.mixin.pipeline;

import com.mojang.blaze3d.pipeline.*;
import com.mojang.blaze3d.pipeline.RenderPipeline.*;
import com.mojang.blaze3d.platform.*;
import com.mojang.blaze3d.shaders.UniformType;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import java.util.*;
import net.lopymine.ed.EntityDithering;
import net.lopymine.ed.client.EntityDitheringClient;
import net.lopymine.ed.dithering.vanilla.VanillaDitheringTargets;
import net.lopymine.ed.dithering.vanilla.VanillaDitheringShaderManager;
import net.lopymine.ed.utils.pipeline.BuilderCopyDithering;
import net.minecraft.client.renderer.*;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
@Mixin(RenderPipeline.Builder.class)
public abstract class RenderPipelineBuilderMixin implements BuilderCopyDithering {

	@Shadow private Optional<Identifier> location;
	@Shadow private Optional<Identifier> fragmentShader;
	@Shadow private Optional<Identifier> vertexShader;
	@Shadow private Optional<ShaderDefines.Builder> definesBuilder;
	@Shadow private Optional<List<String>> samplers;
	@Shadow private Optional<List<UniformDescription>> uniforms;
	@Shadow private Optional<DepthStencilState> depthStencilState;
	@Shadow private Optional<PolygonMode> polygonMode;
	@Shadow private Optional<Boolean> cull;
	@Shadow private Optional<ColorTargetState> colorTargetState;
	@Shadow private Optional<VertexFormat> vertexFormat;
	@Shadow private Optional<Mode> vertexFormatMode;

	@Shadow public abstract Builder withUniform(String name, UniformType type);

	@Inject(at = @At("RETURN"), method = "build")
	private void entityDithering$createDitheringCopy(CallbackInfoReturnable<RenderPipeline> cir) {
		RenderPipeline pipeline = cir.getReturnValue();
		if (VanillaDitheringTargets.isTarget(pipeline.getFragmentShader())) {
			VanillaDitheringShaderManager.createDitheringPipeline(pipeline, (Builder) (Object) this);
		}
	}

	@Override
	public void entityDithering$copyDithering(Builder builder) {
		RenderPipelineBuilderAccessor accessor = (RenderPipelineBuilderAccessor) builder;

		this.location = accessor.getLocation().map((id) -> EntityDithering.id(id.getPath() + "/dithering"));
		this.fragmentShader = Optional.of(VanillaDitheringShaderManager.createDitheringShaderId(accessor.getFragmentShader().orElseThrow()));
		this.vertexShader = accessor.getVertexShader();
		this.definesBuilder = accessor.getDefinesBuilder();
		this.samplers = accessor.getSamplers().map(ArrayList::new);
		this.uniforms = accessor.getUniforms().map(ArrayList::new);
		this.withUniform("DitheringData", UniformType.UNIFORM_BUFFER);

		this.depthStencilState = accessor.getDepthStencilState();
		this.polygonMode = accessor.getPolygonMode();
		this.cull = accessor.getCull();
		this.colorTargetState = accessor.getColorTargetState();
		this.vertexFormat = accessor.getVertexFormat();
		this.vertexFormatMode = accessor.getVertexFormatMode();

		EntityDitheringClient.LOGGER.info("Copied RenderPipeline: \"{}\"", this.location);
	}
}
