package net.lopymine.ms.mixin.builtin.chest;

//? if >=1.21.4 {

/*import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.item.model.special.ChestModelRenderer;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

import net.lopymine.ms.manager.HidingManager;

import java.util.function.Function;

@Mixin(ChestModelRenderer.class)
public class ChestModelRendererMixin {

	@ModifyArg(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/SpriteIdentifier;getVertexConsumer(Lnet/minecraft/client/render/VertexConsumerProvider;Ljava/util/function/Function;)Lnet/minecraft/client/render/VertexConsumer;"), method = "render", index = 1)
	private Function<Identifier, RenderLayer> generated(Function<Identifier, RenderLayer> layerFactory) {
		return HidingManager.INSTANCE.getLayer(layerFactory);
	}

}
*///?}
