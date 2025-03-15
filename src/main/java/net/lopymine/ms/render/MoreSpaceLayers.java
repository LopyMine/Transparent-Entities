package net.lopymine.ms.render;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;

import net.lopymine.ms.client.MoreSpaceClient;
import net.lopymine.ms.entity.EntityCaptures;

import java.util.function.*;

public class MoreSpaceLayers {

	public static RenderLayer getLayer(Identifier texture, Supplier<RenderLayer> original) {
		if (canReplaceRenderLayer()) {
			return RenderLayer.getItemEntityTranslucentCull(texture);
		}
		return original.get();
	}

	public static RenderLayer getLayer(RenderLayer original) {
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

	public static Function<Identifier, RenderLayer> getLayer(Function<Identifier, RenderLayer> original) {
		if (canReplaceRenderLayer()) {
			return RenderLayer::getItemEntityTranslucentCull;
		}
		return original;
	}

	private static boolean canReplaceRenderLayer() {
		if (!MoreSpaceClient.getConfig().isModEnabled()) {
			return false;
		}
		Entity entity = EntityCaptures.MAIN.getEntity();
		if (entity == null) {
			return false;
		}
		return TransparencyManager.isTransparency(entity);
	}
}
