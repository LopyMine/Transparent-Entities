package net.lopymine.ms.mixin.armor;

//? if >=1.21.2 {
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.render.entity.equipment.EquipmentModel.LayerType;
import net.minecraft.client.render.entity.equipment.EquipmentRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.util.*;
import org.spongepowered.asm.mixin.Mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.*;
import net.minecraft.client.render.*;
import org.spongepowered.asm.mixin.injection.*;

import net.lopymine.ms.client.MoreSpaceClient;
import net.lopymine.ms.render.TransparencyManager;
import net.lopymine.ms.render.MoreSpaceLayers;

@Mixin(EquipmentRenderer.class)
public class EquipmentRendererMixin {

	//? if <=1.21.3 {
	/*@WrapOperation(method = "render(Lnet/minecraft/item/equipment/EquipmentModel$LayerType;Lnet/minecraft/util/Identifier;Lnet/minecraft/client/model/Model;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/util/Identifier;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/RenderLayer;getArmorCutoutNoCull(Lnet/minecraft/util/Identifier;)Lnet/minecraft/client/render/RenderLayer;"))
	private RenderLayer renderTransparencyArmorParts(Identifier identifier, Operation<RenderLayer> original) {
		return MoreSpaceLayers.getLayer(identifier, () -> original.call(identifier));
	}

	@WrapOperation(method = "render(Lnet/minecraft/item/equipment/EquipmentModel$LayerType;Lnet/minecraft/util/Identifier;Lnet/minecraft/client/model/Model;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/util/Identifier;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/ItemRenderer;getArmorGlintConsumer(Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/render/RenderLayer;Z)Lnet/minecraft/client/render/VertexConsumer;"))
	private VertexConsumer bbb(VertexConsumerProvider provider, RenderLayer layer, boolean glint, Operation<VertexConsumer> original) {
		if (MoreSpaceClient.getConfig().isModEnabled()) {
			return original.call(provider, layer, glint);
		}
		return ItemRenderer.getItemGlintConsumer(provider, layer, false, glint);
	}

	@WrapOperation(method = "render(Lnet/minecraft/item/equipment/EquipmentModel$LayerType;Lnet/minecraft/util/Identifier;Lnet/minecraft/client/model/Model;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/util/Identifier;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/TexturedRenderLayers;getArmorTrims(Z)Lnet/minecraft/client/render/RenderLayer;"))
	private RenderLayer renderTransparencyTrim(boolean bl, Operation<RenderLayer> original) {
		return MoreSpaceLayers.getLayer(TexturedRenderLayers.ARMOR_TRIMS_ATLAS_TEXTURE, () -> original.call(bl));
	}
	*///?} else {
	@WrapOperation(method = "render(Lnet/minecraft/client/render/entity/equipment/EquipmentModel$LayerType;Lnet/minecraft/registry/RegistryKey;Lnet/minecraft/client/model/Model;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/util/Identifier;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/RenderLayer;getArmorCutoutNoCull(Lnet/minecraft/util/Identifier;)Lnet/minecraft/client/render/RenderLayer;"))
	private RenderLayer renderTransparencyArmorParts(Identifier texture, Operation<RenderLayer> original, @Local(argsOnly = true) LayerType layerType) {
		return MoreSpaceLayers.getLayer(layerType, texture, () -> original.call(texture));
	}

//	@WrapOperation(method = "render(Lnet/minecraft/client/render/entity/equipment/EquipmentModel$LayerType;Lnet/minecraft/registry/RegistryKey;Lnet/minecraft/client/model/Model;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/util/Identifier;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/ItemRenderer;getArmorGlintConsumer(Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/client/render/RenderLayer;Z)Lnet/minecraft/client/render/VertexConsumer;"))
//	private VertexConsumer bbb(VertexConsumerProvider provider, RenderLayer layer, boolean glint, Operation<VertexConsumer> original) {
//		if (!MoreSpaceClient.getConfig().isModEnabled()) {
//			return original.call(provider, layer, glint);
//		}
//		return original.call(provider, layer, glint);
//	}

	@WrapOperation(method = "render(Lnet/minecraft/client/render/entity/equipment/EquipmentModel$LayerType;Lnet/minecraft/registry/RegistryKey;Lnet/minecraft/client/model/Model;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/util/Identifier;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/TexturedRenderLayers;getArmorTrims(Z)Lnet/minecraft/client/render/RenderLayer;"))
	private RenderLayer renderTransparencyTrim(boolean bl, Operation<RenderLayer> original) {
		return MoreSpaceLayers.getLayer(TexturedRenderLayers.ARMOR_TRIMS_ATLAS_TEXTURE, () -> original.call(bl));
	}
	//?}

}
//?}
