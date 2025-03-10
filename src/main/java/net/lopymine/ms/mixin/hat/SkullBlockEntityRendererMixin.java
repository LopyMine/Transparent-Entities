package net.lopymine.ms.mixin.hat;

import com.llamalad7.mixinextras.injector.wrapoperation.*;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.SkullBlockEntityRenderer;
import net.minecraft.util.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import net.lopymine.ms.manager.HidingManager;

@Mixin(SkullBlockEntityRenderer.class)
public class SkullBlockEntityRendererMixin {

	//? if <=1.21.3 {
	@WrapOperation(method = "getRenderLayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/RenderLayer;getEntityCutoutNoCullZOffset(Lnet/minecraft/util/Identifier;)Lnet/minecraft/client/render/RenderLayer;"))
	private static RenderLayer modifySkullRenderLayer(Identifier identifier, Operation<RenderLayer> original) {
		return HidingManager.INSTANCE.getLayer(identifier, () -> original.call(identifier));
	}
	//?} else {
	/*@WrapOperation(method = "getRenderLayer(Lnet/minecraft/block/SkullBlock$SkullType;Lnet/minecraft/component/type/ProfileComponent;Lnet/minecraft/util/Identifier;)Lnet/minecraft/client/render/RenderLayer;", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/RenderLayer;getEntityCutoutNoCullZOffset(Lnet/minecraft/util/Identifier;)Lnet/minecraft/client/render/RenderLayer;"))
	private static RenderLayer modifySkullRenderLayer(Identifier identifier, Operation<RenderLayer> original) {
		return HidingManager.INSTANCE.getLayer(identifier, () -> original.call(identifier));
	}
	*///?}

}
