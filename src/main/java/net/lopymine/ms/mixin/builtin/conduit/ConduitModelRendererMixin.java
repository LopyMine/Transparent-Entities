package net.lopymine.ms.mixin.builtin.conduit;

//? if >=1.21.4 {

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.item.model.special.ConduitModelRenderer;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

import net.lopymine.ms.render.TransparencyManager;
import net.lopymine.ms.render.MoreSpaceLayers;

import java.util.function.Function;

@Mixin(ConduitModelRenderer.class)
public class ConduitModelRendererMixin {

	@ModifyArg(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/SpriteIdentifier;getVertexConsumer(Lnet/minecraft/client/render/VertexConsumerProvider;Ljava/util/function/Function;)Lnet/minecraft/client/render/VertexConsumer;"), method = "render", index = 1)
	private Function<Identifier, RenderLayer> generated(Function<Identifier, RenderLayer> layerFactory) {
		return MoreSpaceLayers.getLayer(layerFactory);
	}

}
//?}
