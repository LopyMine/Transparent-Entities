package net.lopymine.ms.mixin;

import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.*;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.entity.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

import net.lopymine.ms.render.MoreSpaceLayers;

//? if <=1.21.1 {
/*import com.llamalad7.mixinextras.injector.wrapoperation.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;

import net.lopymine.ms.client.MoreSpaceClient;
import net.lopymine.ms.entity.EntityCaptures;
*///?}

//? >=1.21.2 {
import net.minecraft.client.render.entity.state.*;
import net.minecraft.util.Identifier;

import java.util.function.Function;
//?}

@Debug(export = true)
@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin extends EntityRenderer<LivingEntity /*? if >=1.21.2 {*/ , LivingEntityRenderState /*?}*/> {

	protected LivingEntityRendererMixin(Context ctx) {
		super(ctx);
	}

	//? if <=1.21.1 {
	/*@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/LivingEntityRenderer;getRenderLayer(Lnet/minecraft/entity/LivingEntity;ZZZ)Lnet/minecraft/client/render/RenderLayer;"), method = "render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V")
	private RenderLayer swapRenderLayer(LivingEntityRenderer<?, ?> instance, LivingEntity entity, boolean showBody, boolean translucent, boolean showOutline, Operation<RenderLayer> original) {
		return MoreSpaceLayers.getLayer(this.getTexture(entity), () -> original.call(instance, entity, showBody, translucent, showOutline));
	}
	*///?} else {

	@Shadow
	public abstract Identifier getTexture(LivingEntityRenderState par1);

	@Inject(at = @At("RETURN"), method = "getRenderLayer", cancellable = true)
	private void wrapGetRenderLayer(LivingEntityRenderState state, boolean showBody, boolean translucent, boolean showOutline, CallbackInfoReturnable<RenderLayer> cir) {
		cir.setReturnValue(MoreSpaceLayers.getLayer(this.getTexture(state), cir::getReturnValue));
	}
	//?}

}
