package net.lopymine.te.utils;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayer.MultiPhase;
import net.minecraft.client.render.RenderPhase.*;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class RenderLayerUtils {

	//? if >=1.21.6 {
	/*@Nullable
	public static Identifier ripTextureIfPresent(RenderLayer layerToRip) {
		if (!(layerToRip instanceof MultiPhase multiPhase)) {
			return null;
		}
		TextureBase textureBase = multiPhase.phases.texture;
		if (!(textureBase instanceof Texture texture)) {
			return null;
		}
		return texture.id.orElse(null);
	}
	*///?}

}
