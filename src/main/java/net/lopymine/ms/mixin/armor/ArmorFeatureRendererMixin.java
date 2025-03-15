package net.lopymine.ms.mixin.armor;

//? if <=1.21.1 {
/*import com.llamalad7.mixinextras.injector.wrapoperation.*;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

import net.lopymine.ms.render.TransparencyManager;
import net.lopymine.ms.render.MoreSpaceLayers;

@Mixin(ArmorFeatureRenderer.class)
public class ArmorFeatureRendererMixin {

	@WrapOperation(method = "renderArmorParts", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/RenderLayer;getArmorCutoutNoCull(Lnet/minecraft/util/Identifier;)Lnet/minecraft/client/render/RenderLayer;"))
	private RenderLayer renderTransparencyArmorParts(Identifier texture, Operation<RenderLayer> original) {
		return MoreSpaceLayers.getLayer(texture, () -> original.call(texture));
	}

	@WrapOperation(method = "renderTrim", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/TexturedRenderLayers;getArmorTrims(Z)Lnet/minecraft/client/render/RenderLayer;"))
	private RenderLayer renderTransparencyTrim(boolean bl, Operation<RenderLayer> original) {
		return MoreSpaceLayers.getLayer(TexturedRenderLayers.ARMOR_TRIMS_ATLAS_TEXTURE, () -> original.call(bl));
	}
}

*///?}