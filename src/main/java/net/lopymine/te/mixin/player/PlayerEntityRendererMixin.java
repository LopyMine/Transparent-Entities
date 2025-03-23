package net.lopymine.te.mixin.player;

import net.minecraft.block.*;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.render.entity.*;
import net.minecraft.client.render.entity.model.*;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.lopymine.te.client.TransparentEntitiesClient;

//? >=1.21.2 {
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.lopymine.te.render.TransparencyManager;
import net.lopymine.te.render.TransparencyLayers;
import net.lopymine.te.entity.EntityCaptures;
//?}

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin extends LivingEntityRenderer<AbstractClientPlayerEntity/*? if >=1.21.2 {*/ , PlayerEntityRenderState /*?}*/, PlayerEntityModel/*? if <=1.21.1 {*/ /*<AbstractClientPlayerEntity> *//*?}*/> {

	public PlayerEntityRendererMixin(Context ctx, PlayerEntityModel/*? if <=1.21.1 {*/ /*<AbstractClientPlayerEntity> *//*?}*/ model, float shadowRadius) {
        super(ctx, model, shadowRadius);
    }

	//? if <=1.21.1 {
	/*@Inject(method = "setModelPose", at = @At(value = "TAIL"))
	private void modifyHeadVisibility(AbstractClientPlayerEntity player, CallbackInfo ci) {
	*///?} else {
	@Inject(at = @At("TAIL"), method = "updateRenderState(Lnet/minecraft/client/network/AbstractClientPlayerEntity;Lnet/minecraft/client/render/entity/state/PlayerEntityRenderState;F)V")
	private void modifyHeadVisibility(AbstractClientPlayerEntity player, PlayerEntityRenderState playerEntityRenderState, float f, CallbackInfo ci) {
	//?}
        if (player.isSpectator()) {
            return;
        }

        ItemStack stack = player.getEquippedStack(EquipmentSlot.HEAD);
        boolean b = stack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof AbstractSkullBlock;
        boolean bl = stack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof CarvedPumpkinBlock;
		EntityModel<?> model = this.getModel();
		if (model instanceof PlayerEntityModel playerEntityModel) {
			boolean visible = !((b || bl) && TransparentEntitiesClient.getConfig().isModEnabled());
			playerEntityModel.head.visible = visible;
			playerEntityModel.hat.visible = visible;
        }
    }
}
