package net.lopymine.ms.compat.sodium.mixin;
//
//import com.bawnorton.mixinsquared.TargetHandler;
//import com.llamalad7.mixinextras.injector.wrapoperation.*;
//import net.caffeinemc.mods.sodium.api.util.*;
//import net.caffeinemc.mods.sodium.api.vertex.buffer.VertexBufferWriter;
//import net.caffeinemc.mods.sodium.client.model.quad.ModelQuadView;
//import net.minecraft.client.render.block.BlockModelRenderer;
//import net.minecraft.client.util.math.MatrixStack.Entry;
//import net.minecraft.entity.Entity;
//import org.spongepowered.asm.mixin.*;
//import org.spongepowered.asm.mixin.injection.At;
//
//import net.lopymine.ms.entity.EntityCaptures;
//import net.lopymine.ms.render.TransparencyManager;
//import net.lopymine.ms.utils.ArgbUtils;
//
//@Pseudo
//@Mixin(value = BlockModelRenderer.class, priority = 2000)
//public class ModelBlockRendererMixinMixin {
//
//	@Dynamic
//	@TargetHandler(
//			mixin = "net.caffeinemc.mods.sodium.mixin.features.render.model.block.ModelBlockRendererMixin",
//			name = "renderQuads"
//	)
//	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/caffeinemc/mods/sodium/client/render/immediate/model/BakedModelEncoder;writeQuadVertices(Lnet/caffeinemc/mods/sodium/api/vertex/buffer/VertexBufferWriter;Lnet/minecraft/client/util/math/MatrixStack$Entry;Lnet/caffeinemc/mods/sodium/client/model/quad/ModelQuadView;IIIZ)V"), method = "@MixinSquared:Handler")
//	private static void generated(VertexBufferWriter writer, Entry entry, ModelQuadView view, int color, int light, int overlay, boolean colorize, Operation<Void> original) {
//		Entity entity = EntityCaptures.MAIN.getEntity();
//		if (entity == null) {
//			original.call(writer, entry, view, color, light, overlay, colorize);
//			return;
//		}
//
//		int a = ColorABGR.unpackAlpha(color);
//		int r = ColorABGR.unpackRed(color);
//		int g = ColorABGR.unpackGreen(color);
//		int b = ColorABGR.unpackBlue(color);
//
//		int originalArgb = ArgbUtils.getArgb(a, r, g, b);
//
//		int translucentColor = ColorARGB.toABGR(TransparencyManager.getTranslucentArgb(entity, originalArgb));
//
//		original.call(writer, entry, view, translucentColor, light, overlay, colorize);
//	}
//
//}
