package net.lopymine.te.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.*;
import com.llamalad7.mixinextras.sugar.Local;
import net.lopymine.te.transparency.TransparencyManager;
import net.lopymine.te.utils.*;
import net.minecraft.client.render.BufferBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BufferBuilder.class)
public class BufferBuilderMixin {

	@WrapOperation(at = @At(value = "INVOKE", target = "Lorg/lwjgl/system/MemoryUtil;memPutInt(JI)V", remap = false), method = "putColor")
	private static void swapColorAlpha1(long ptr, int value, Operation<Void> original, @Local(argsOnly = true) int argb) {
		int abgr = AbgrUtils.toAbgr(TransparencyManager.getArgbColorFromAnyCapturedThing(argb));
		original.call(ptr, abgr);
	}

	@WrapOperation(at = @At(value = "INVOKE", target = "Lorg/lwjgl/system/MemoryUtil;memPutByte(JB)V", ordinal = 3, remap = false), method = "color(IIII)Lnet/minecraft/client/render/VertexConsumer;")
	private void swapColorAlpha2(long ptr, byte value, Operation<Void> original) {
		int argb = TransparencyManager.getArgbColorFromAnyCapturedThing(ArgbUtils.getArgb(value, 255, 255, 255));
		byte alpha = (byte) ArgbUtils.getAlpha(argb);
		original.call(ptr, alpha);
	}

}
