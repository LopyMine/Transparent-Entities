package net.lopymine.te.transparency.layers;

import net.minecraft.client.render.*;
import net.minecraft.client.render.item.ItemRenderer;
import static net.minecraft.client.render.RenderPhase.*;

public class TransparencyArmorLayer {

	private static final /*? >=1.21.2 {*/ net.minecraft.util.TriState /*?} else {*/ /*boolean *//*?}*/ STATE = /*? >=1.21.2 {*/ net.minecraft.util.TriState.TRUE /*?} else {*/ /*true *//*?}*/;

	//? if >=1.21.5 {
	public static final RenderLayer ARMOR_GLINT_TRANSLUCENT = RenderLayer.of(
			"transparent_entities_armor_glint_translucent",
			1536,
			net.minecraft.client.gl.RenderPipelines.GLINT,
			RenderLayer.MultiPhaseParameters.builder().texture(new Texture(ItemRenderer.ENTITY_ENCHANTMENT_GLINT, /*? if <=1.21.5 {*/net.minecraft.util.TriState.FALSE,/*?}*/ false))
					.texturing(ARMOR_ENTITY_GLINT_TEXTURING)
					.target(ITEM_ENTITY_TARGET)
					.build(false)
	);
	//?} else {
	/*public static final RenderLayer ARMOR_GLINT_TRANSLUCENT = RenderLayer.of(
			"transparent_entities_armor_glint_translucent",
			VertexFormats.POSITION_TEXTURE,
			net.minecraft.client.render.VertexFormat.DrawMode.QUADS,
			1536,
			RenderLayer.MultiPhaseParameters.builder()
					.program(TRANSLUCENT_GLINT_PROGRAM)
					.texture(new RenderPhase.Texture(ItemRenderer.ENTITY_ENCHANTMENT_GLINT, STATE, false))
					.writeMaskState(COLOR_MASK)
					.cull(DISABLE_CULLING)
					.depthTest(EQUAL_DEPTH_TEST)
					.transparency(GLINT_TRANSPARENCY)
					.texturing(ENTITY_GLINT_TEXTURING)
					.target(ITEM_ENTITY_TARGET)
					.build(false)
	);
	*///?}


}
