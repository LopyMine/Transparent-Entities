package net.lopymine.te.transparency.layers;

import java.util.function.Function;
import net.minecraft.client.render.*;
import net.minecraft.client.render.RenderLayer.MultiPhaseParameters;
import net.minecraft.client.render.RenderPhase.Texture;

import net.minecraft.util.*;

public class TransparencyItemEntityNoCullLayer {

	private static final /*? >=1.21.2 {*/  net.minecraft.util.TriState /*?} else {*/ /*boolean *//*?}*/ STATE = /*? >=1.21.2 {*/ net.minecraft.util.TriState.FALSE /*?} else {*/ /*false *//*?}*/;

	//? if <=1.21.4 {
	/*public static final Function<Identifier, RenderLayer> ITEM_ENTITY_TRANSLUCENT_NO_CULL = Util.memoize((texture) -> {
		MultiPhaseParameters multiPhaseParameters = MultiPhaseParameters.builder().program(RenderLayer.ITEM_ENTITY_TRANSLUCENT_CULL_PROGRAM).texture(new Texture(texture, STATE, false)).transparency(RenderLayer.TRANSLUCENT_TRANSPARENCY).cull(RenderLayer.DISABLE_CULLING).target(RenderLayer.ITEM_ENTITY_TARGET).lightmap(RenderLayer.ENABLE_LIGHTMAP).overlay(RenderLayer.ENABLE_OVERLAY_COLOR).writeMaskState(RenderLayer.ALL_MASK).build(true);
		return RenderLayer.of("item_entity_translucent_no_cull", VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL, net.minecraft.client.render.VertexFormat.DrawMode.QUADS, 1536, true, true, multiPhaseParameters);
	});
	*///?} else {
	public static final Function<Identifier, RenderLayer> ITEM_ENTITY_TRANSLUCENT_NO_CULL = Util.memoize((texture) -> {
		MultiPhaseParameters multiPhaseParameters = MultiPhaseParameters.builder().texture(new Texture(texture, /*? if <=1.21.5 {*/net.minecraft.util.TriState.FALSE,/*?}*/ false)).target(RenderLayer.ITEM_ENTITY_TARGET).lightmap(RenderLayer.ENABLE_LIGHTMAP).overlay(RenderLayer.ENABLE_OVERLAY_COLOR).build(true);
		return RenderLayer.of("item_entity_translucent_no_cull", 1536, true, true, net.lopymine.te.transparency.TransparencyRenderPipelines.RENDER_TYPE_ITEM_ENTITY_TRANSLUCENT_NO_CULL, multiPhaseParameters);
	});
	//?}

}
