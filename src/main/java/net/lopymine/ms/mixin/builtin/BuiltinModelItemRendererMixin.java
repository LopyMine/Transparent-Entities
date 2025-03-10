package net.lopymine.ms.mixin.builtin;

//? if <=1.21.3 {

import com.llamalad7.mixinextras.injector.wrapoperation.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.*;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import net.lopymine.ms.manager.HidingManager;

@Mixin(BuiltinModelItemRenderer.class)
public class BuiltinModelItemRendererMixin {

	@WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/model/ShieldEntityModel;getLayer(Lnet/minecraft/util/Identifier;)Lnet/minecraft/client/render/RenderLayer;"))
	private RenderLayer modifyShieldRenderLayer(ShieldEntityModel instance, Identifier identifier, Operation<RenderLayer> original) {
		return HidingManager.INSTANCE.getLayer(identifier, () -> original.call(instance, identifier));
	}

	@WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/model/TridentEntityModel;getLayer(Lnet/minecraft/util/Identifier;)Lnet/minecraft/client/render/RenderLayer;"))
	private RenderLayer modifyTridentRenderLayer(TridentEntityModel instance, Identifier identifier, Operation<RenderLayer> original) {
		return HidingManager.INSTANCE.getLayer(identifier, () -> original.call(instance, identifier));
	}

}
//?}