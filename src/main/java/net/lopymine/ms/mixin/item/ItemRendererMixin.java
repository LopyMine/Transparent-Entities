package net.lopymine.ms.mixin.item;

import com.llamalad7.mixinextras.injector.wrapoperation.*;
import net.minecraft.client.render.*;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;

import net.lopymine.ms.entity.EntityCaptures;
import net.lopymine.ms.render.*;
import net.lopymine.ms.utils.*;

//? if <=1.21.3 {
/*import net.minecraft.item.ItemStack;
*///?}

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {

	//? if <=1.21.1 {
	/*@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/RenderLayers;getItemLayer(Lnet/minecraft/item/ItemStack;Z)Lnet/minecraft/client/render/RenderLayer;"), method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V")
	private RenderLayer modifyLayer(ItemStack stack, boolean direct, Operation<RenderLayer> original) {
		return MoreSpaceLayers.getLayer(() -> original.call(stack, direct));
	}
	*///?} elif <=1.21.3 {
	/*@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/RenderLayers;getItemLayer(Lnet/minecraft/item/ItemStack;)Lnet/minecraft/client/render/RenderLayer;"), method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ModelTransformationMode;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;Z)V")
	private RenderLayer modifyLayer(ItemStack stack, Operation<RenderLayer> original) {
		return MoreSpaceLayers.getLayer(() -> original.call(stack));
	}
	*///?} else {
	@ModifyVariable(at = @At("HEAD"), method = "renderItem(Lnet/minecraft/item/ModelTransformationMode;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;II[ILnet/minecraft/client/render/model/BakedModel;Lnet/minecraft/client/render/RenderLayer;Lnet/minecraft/client/render/item/ItemRenderState$Glint;)V", argsOnly = true, index = 7)
	private static RenderLayer modifyLayer(RenderLayer value) {
		return MoreSpaceLayers.getItemLayer(value);
	}
	//?}

	//? if <=1.21.1 {
	/*@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/ColorHelper$Argb;getAlpha(I)I"), method = "renderBakedItemQuads")
	*///?} else {
	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/ColorHelper;getAlpha(I)I"), method = "renderBakedItemQuads")
	//?}
	private /*? if >=1.21.4 {*/ static /*?}*/ int generated(int i, Operation<Integer> original) {
		Entity entity = EntityCaptures.MAIN.getEntity();
		if (entity == null) {
			return original.call(i);
		}
		return ArgbUtils.getAlpha(TransparencyManager.getTranslucentArgb(entity, i));
	}

}

