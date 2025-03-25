package net.lopymine.te.render;

//? if >=1.21.5 {
/*import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.render.VertexFormats;

import com.mojang.blaze3d.pipeline.*;
import com.mojang.blaze3d.vertex.VertexFormat.DrawMode;

import static net.minecraft.client.gl.RenderPipelines.MATRICES_COLOR_FOG_LIGHT_DIR_SNIPPET;
*///?}

public class TransparencyRenderPipelines {

	//? if >=1.21.5 {
	/*public static final RenderPipeline RENDER_TYPE_ITEM_ENTITY_TRANSLUCENT_NO_CULL =
			RenderPipeline.builder(MATRICES_COLOR_FOG_LIGHT_DIR_SNIPPET)
					.withLocation("pipeline/item_entity_translucent_cull")
					.withVertexShader("core/rendertype_item_entity_translucent_cull")
					.withFragmentShader("core/rendertype_item_entity_translucent_cull")
					.withSampler("Sampler0")
					.withSampler("Sampler2")
					.withBlend(BlendFunction.TRANSLUCENT)
					.withCull(false)
					.withVertexFormat(VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL, DrawMode.QUADS)
					.build();

	public static void register() {
		RenderPipelines.register(RENDER_TYPE_ITEM_ENTITY_TRANSLUCENT_NO_CULL);
	}
	*///?} else {
	public static void register() {

	}
	//?}

}
