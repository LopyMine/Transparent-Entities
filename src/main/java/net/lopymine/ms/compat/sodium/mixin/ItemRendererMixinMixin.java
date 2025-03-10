package net.lopymine.ms.compat.sodium.mixin;

import com.bawnorton.mixinsquared.TargetHandler;
import com.llamalad7.mixinextras.injector.wrapoperation.*;
import net.caffeinemc.mods.sodium.api.util.*;
import net.caffeinemc.mods.sodium.api.vertex.buffer.VertexBufferWriter;
import net.caffeinemc.mods.sodium.client.model.quad.ModelQuadView;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack.Entry;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;

import net.lopymine.ms.entity.EntityCaptures;
import net.lopymine.ms.manager.HidingManager;
import net.lopymine.ms.utils.*;

@Pseudo
@Mixin(value = ItemRenderer.class, priority = 1500)
public class ItemRendererMixinMixin {

	@Dynamic
	@TargetHandler(
			mixin = "net.caffeinemc.mods.sodium.mixin.features.render.model.item.ItemRendererMixin",
			name = "renderBakedItemQuads"
	)
	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/caffeinemc/mods/sodium/client/render/immediate/model/BakedModelEncoder;writeQuadVertices(Lnet/caffeinemc/mods/sodium/api/vertex/buffer/VertexBufferWriter;Lnet/minecraft/client/util/math/MatrixStack$Entry;Lnet/caffeinemc/mods/sodium/client/model/quad/ModelQuadView;IIIZ)V"), method = "@MixinSquared:Handler")
	private /*? if >=1.21.4 {*/ /*static *//*?}*/ void generated(VertexBufferWriter writer, Entry entry, ModelQuadView quad, int color, int light, int overlay, boolean bl, Operation<Void> original) {
		Entity entity = EntityCaptures.MAIN.getEntity();

		if (entity == null) {
			original.call(writer, entry, quad, color, light, overlay, bl);
			return;
		}

		int a = ColorABGR.unpackAlpha(color);
		int r = ColorABGR.unpackRed(color);
		int g = ColorABGR.unpackGreen(color);
		int b = ColorABGR.unpackBlue(color);

		int argb = ArgbUtils.getArgb(a, r, g, b);

		original.call(writer, entry, quad, ColorARGB.toABGR(HidingManager.INSTANCE.getAlpha(entity, argb)), light, overlay, bl);
	}

}
