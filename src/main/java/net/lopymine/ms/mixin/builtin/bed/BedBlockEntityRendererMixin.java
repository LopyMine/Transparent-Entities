package net.lopymine.ms.mixin.builtin.bed;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BedBlockEntityRenderer;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

import net.lopymine.ms.render.TransparencyManager;
import net.lopymine.ms.render.MoreSpaceLayers;

import java.util.function.Function;

@Mixin(BedBlockEntityRenderer.class)
public class BedBlockEntityRendererMixin {

    @ModifyArg(method = "renderPart", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/SpriteIdentifier;getVertexConsumer(Lnet/minecraft/client/render/VertexConsumerProvider;Ljava/util/function/Function;)Lnet/minecraft/client/render/VertexConsumer;"), index = 1)
    private Function<Identifier, RenderLayer> modifyRenderLayer(Function<Identifier, RenderLayer> layerFactory) {
	    return MoreSpaceLayers.getLayer(layerFactory);
    }

}
