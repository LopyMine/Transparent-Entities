package net.lopymine.te.mixin.armor;

//? if >=1.21.2 {
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.render.entity.equipment.EquipmentRenderer;
import net.minecraft.util.*;
import org.spongepowered.asm.mixin.Mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.*;
import net.minecraft.client.render.*;
import org.spongepowered.asm.mixin.injection.*;

import net.lopymine.te.transparency.TransparencyLayers;

//? if >=1.21.4 {
import net.minecraft.client.render.entity.equipment.EquipmentModel.LayerType;
//?} elif >=1.21.2 {
/*import net.minecraft.item.equipment.EquipmentModel.LayerType;
*///?}

@Mixin(EquipmentRenderer.class)
public class EquipmentRendererMixin {

	//? if <=1.21.3 {
	/*@WrapOperation(method = "render(Lnet/minecraft/item/equipment/EquipmentModel$LayerType;Lnet/minecraft/util/Identifier;Lnet/minecraft/client/model/Model;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/util/Identifier;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/RenderLayer;getArmorCutoutNoCull(Lnet/minecraft/util/Identifier;)Lnet/minecraft/client/render/RenderLayer;"))
	private RenderLayer swapArmorLayer(Identifier identifier, Operation<RenderLayer> original, @Local(argsOnly = true) LayerType layerType) {
		return TransparencyLayers.getArmorLayer(layerType != LayerType.WINGS, identifier, () -> original.call(identifier));
	}

	@WrapOperation(method = "render(Lnet/minecraft/item/equipment/EquipmentModel$LayerType;Lnet/minecraft/util/Identifier;Lnet/minecraft/client/model/Model;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/util/Identifier;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/TexturedRenderLayers;getArmorTrims(Z)Lnet/minecraft/client/render/RenderLayer;"))
	private RenderLayer swapTrimLayer(boolean bl, Operation<RenderLayer> original, @Local(argsOnly = true) LayerType layerType) {
		return TransparencyLayers.getArmorLayer(layerType != LayerType.WINGS, TexturedRenderLayers.ARMOR_TRIMS_ATLAS_TEXTURE, () -> original.call(bl));
	}
	*///?} else {
	@WrapOperation(method = "render(Lnet/minecraft/client/render/entity/equipment/EquipmentModel$LayerType;Lnet/minecraft/registry/RegistryKey;Lnet/minecraft/client/model/Model;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/util/Identifier;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/RenderLayer;getArmorCutoutNoCull(Lnet/minecraft/util/Identifier;)Lnet/minecraft/client/render/RenderLayer;"))
	private RenderLayer swapArmorLayer(Identifier texture, Operation<RenderLayer> original, @Local(argsOnly = true) LayerType layerType) {
		return TransparencyLayers.getArmorLayer(layerType != LayerType.WINGS, texture, () -> original.call(texture));
	}

	@WrapOperation(method = "render(Lnet/minecraft/client/render/entity/equipment/EquipmentModel$LayerType;Lnet/minecraft/registry/RegistryKey;Lnet/minecraft/client/model/Model;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/util/Identifier;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/TexturedRenderLayers;getArmorTrims(Z)Lnet/minecraft/client/render/RenderLayer;"))
	private RenderLayer swapTrimLayer(boolean bl, Operation<RenderLayer> original, @Local(argsOnly = true) LayerType layerType) {
		return TransparencyLayers.getArmorLayer(layerType != LayerType.WINGS, TexturedRenderLayers.ARMOR_TRIMS_ATLAS_TEXTURE, () -> original.call(bl));
	}
	//?}

}
//?}
