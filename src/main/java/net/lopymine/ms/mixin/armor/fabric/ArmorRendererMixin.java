package net.lopymine.ms.mixin.armor.fabric;

import com.llamalad7.mixinextras.injector.wrapoperation.*;
import net.minecraft.client.render.*;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;

import net.lopymine.ms.render.TransparencyManager;
import net.lopymine.ms.render.MoreSpaceLayers;

@Mixin(ArmorRenderer.class)
public interface ArmorRendererMixin {

    @WrapOperation(method = "renderPart", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/RenderLayer;getArmorCutoutNoCull(Lnet/minecraft/util/Identifier;)Lnet/minecraft/client/render/RenderLayer;"))
    private static RenderLayer wrapRenderLayer(Identifier texture, Operation<RenderLayer> original) {
	    return MoreSpaceLayers.getLayer(texture, () -> original.call(texture));
    }

}
