package net.lopymine.te.mixin.glint;

import com.llamalad7.mixinextras.injector.wrapoperation.*;
import net.lopymine.te.client.TransparentEntitiesClient;
import net.lopymine.te.transparency.TransparencyLayers;
import net.minecraft.client.render.*;
import net.minecraft.client.render.item.ItemRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {

	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/RenderLayer;getGlint()Lnet/minecraft/client/render/RenderLayer;"), method = /*? if <=1.21.5 {*/ "getDynamicDisplayGlintConsumer" /*?} else {*/ /*"getSpecialItemGlintConsumer" *//*?}*/)
	private static RenderLayer swapDynamicDisplayGlint(Operation<RenderLayer> original) {
		return TransparencyLayers.canReplaceRenderLayer() && TransparentEntitiesClient.getConfig().isShowGlintWhenTranslucent() ? RenderLayer.getGlintTranslucent() : original.call();
	}

	@Inject(at = @At("HEAD"), method = {"getItemGlintConsumer"/*? if <=1.21.1 {*//*, "getDirectItemGlintConsumer" *//*?}*/}, cancellable = true)
	private static void swapItemGlint(VertexConsumerProvider vertexConsumers, RenderLayer layer, boolean solid, boolean glint, CallbackInfoReturnable<VertexConsumer> cir) {
		if (TransparencyLayers.canReplaceRenderLayer()) {
			if (glint && TransparentEntitiesClient.getConfig().isShowGlintWhenTranslucent()) {
				cir.setReturnValue(VertexConsumers.union(vertexConsumers.getBuffer(RenderLayer.getGlintTranslucent()), vertexConsumers.getBuffer(layer)));
			} else {
				cir.setReturnValue(vertexConsumers.getBuffer(layer));
			}
		}
	}

}
