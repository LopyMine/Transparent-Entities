package net.lopymine.ms.mixin.builtin.trident;

//? if >=1.21.4 {

import com.llamalad7.mixinextras.injector.wrapoperation.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.*;
import net.minecraft.client.render.item.model.special.TridentModelRenderer;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import net.lopymine.ms.render.TransparencyManager;
import net.lopymine.ms.render.MoreSpaceLayers;

@Mixin(TridentModelRenderer.class)
public class TridentModelRendererMixin {

	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/model/TridentEntityModel;getLayer(Lnet/minecraft/util/Identifier;)Lnet/minecraft/client/render/RenderLayer;"), method = "render")
	private RenderLayer generated(TridentEntityModel instance, Identifier identifier, Operation<RenderLayer> original) {
		return MoreSpaceLayers.getLayer(identifier, () -> original.call(instance, identifier));
	}

}
//?}
