package net.lopymine.te.render;

import net.minecraft.client.render.*;
import net.minecraft.client.render.RenderLayer.MultiPhaseParameters;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.entity.Entity;
import net.minecraft.util.*;

import net.lopymine.te.client.TransparentEntitiesClient;
import net.lopymine.te.config.TransparentEntitiesConfig;
import net.lopymine.te.entity.EntityCaptures;

import java.util.function.*;

//? if >=1.21.4 {
import net.minecraft.client.render.entity.equipment.EquipmentModel.LayerType;
//?} elif >=1.21.2 {
/*import net.minecraft.item.equipment.EquipmentModel.LayerType;
*///?}

public class TransparencyLayers {

	private static final Function<Identifier, RenderLayer> ITEM_ENTITY_TRANSLUCENT_NO_CULL = Util.memoize((texture) -> {
			MultiPhaseParameters multiPhaseParameters = MultiPhaseParameters.builder().program(RenderLayer.ITEM_ENTITY_TRANSLUCENT_CULL_PROGRAM).texture(new RenderPhase.Texture(texture, /*? if >=1.21.2 {*/ TriState.FALSE /*?} else {*/ /*false *//*?}*/, false)).transparency(RenderLayer.TRANSLUCENT_TRANSPARENCY).cull(RenderLayer.DISABLE_CULLING).target(RenderLayer.ITEM_ENTITY_TARGET).lightmap(RenderLayer.ENABLE_LIGHTMAP).overlay(RenderLayer.ENABLE_OVERLAY_COLOR).writeMaskState(RenderLayer.ALL_MASK).build(true);
			return RenderLayer.of("item_entity_translucent_no_cull", VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL, DrawMode.QUADS, 1536, true, true, multiPhaseParameters);
	});

	//? if >=1.21.2 {
	public static RenderLayer getLayerNoCull(LayerType layerType, Identifier texture, Supplier<RenderLayer> original) {
		if (canReplaceRenderLayer()) {
			if (layerType == LayerType.WINGS) {
				return ITEM_ENTITY_TRANSLUCENT_NO_CULL.apply(texture);
			} else {
				return RenderLayer.getItemEntityTranslucentCull(texture);
			}
		}
		return original.get();
	}
	//?} else {
	/*public static RenderLayer getLayerNoCull(Identifier texture, Supplier<RenderLayer> original) {
		if (canReplaceRenderLayer()) {
			return ITEM_ENTITY_TRANSLUCENT_NO_CULL.apply(texture);
		}
		return original.get();
	}
	*///?}

	public static RenderLayer getLayer(Identifier texture, Supplier<RenderLayer> original) {
		if (canReplaceRenderLayer()) {
			return RenderLayer.getItemEntityTranslucentCull(texture);
		}
		return original.get();
	}

	public static RenderLayer getItemLayer(RenderLayer original) {
		if (canReplaceRenderLayer()) {
			return TexturedRenderLayers.getItemEntityTranslucentCull();
		}
		return original;
	}

	public static RenderLayer getLayer(Supplier<RenderLayer> original) {
		if (canReplaceRenderLayer()) {
			return TexturedRenderLayers.getItemEntityTranslucentCull();
		}
		return original.get();
	}

	private static boolean canReplaceRenderLayer() {
		TransparentEntitiesConfig config = TransparentEntitiesClient.getConfig();
		if (config == null) {
			return false;
		}
		if (!config.isModEnabled()) {
			return false;
		}
		Entity entity = EntityCaptures.MAIN.getEntity();
		if (entity == null) {
			return false;
		}
		return TransparencyManager.isTransparency(entity);
	}
}
