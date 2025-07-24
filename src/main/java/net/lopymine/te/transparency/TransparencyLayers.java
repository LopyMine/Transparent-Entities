package net.lopymine.te.transparency;

import net.lopymine.te.transparency.layers.*;
import net.minecraft.client.render.*;
import net.minecraft.entity.Entity;
import net.minecraft.util.*;

import net.lopymine.te.client.TransparentEntitiesClient;
import net.lopymine.te.config.TransparentEntitiesConfig;
import net.lopymine.te.thing.ThingCaptures;

import java.util.function.*;

public class TransparencyLayers {

	public static RenderLayer getArmorLayer(boolean cull, Identifier texture, Supplier<RenderLayer> original) {
		if (canReplaceRenderLayer()) {
			if (cull) {
				return RenderLayer.getItemEntityTranslucentCull(texture);
			} else {
				return TransparencyItemEntityNoCullLayer.ITEM_ENTITY_TRANSLUCENT_NO_CULL.apply(texture);
			}
		}
		return original.get();
	}

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

	public static RenderLayer getItemLayer(Supplier<RenderLayer> original) {
		if (canReplaceRenderLayer()) {
			return TexturedRenderLayers.getItemEntityTranslucentCull();
		}
		return original.get();
	}

	public static RenderLayer getArmorGlintLayer(Supplier<RenderLayer> original) {
		if (canReplaceRenderLayer() && TransparentEntitiesClient.getConfig().isShowGlintWhenTranslucent()) {
			return TransparencyArmorLayer.ARMOR_GLINT_TRANSLUCENT;
		}
		return original.get();
	}

	//? if >=1.21.5 {
	public static RenderLayer getParticleLayer(Identifier texture, Supplier<RenderLayer> original) {
		if (canReplaceRenderLayer()) {
			return RenderLayer.getTranslucentParticle(texture);
		}
		return original.get();
	}
	//?}

	//? if >=1.21.6 {
	/*public static RenderLayer getLayerWithRippedOutTexture(RenderLayer layerToRip) {
		if (canReplaceRenderLayer()) {
			Identifier texture = net.lopymine.te.utils.RenderLayerUtils.ripTextureIfPresent(layerToRip);
			if (texture == null) {
				return layerToRip;
			}
			return RenderLayer.getItemEntityTranslucentCull(texture);
		}
		return layerToRip;
	}
	*///?}

	public static boolean canReplaceRenderLayer() {
		TransparentEntitiesConfig config = TransparentEntitiesClient.getConfig();
		if (config == null) {
			return false;
		}
		if (!config.isModEnabled()) {
			return false;
		}
		Entity entity = ThingCaptures.CURRENT_RENDERING_ENTITY.get();
		if (entity == null) {
			return false;
		}
		return TransparencyManager.isTransparency(entity);
	}
}
