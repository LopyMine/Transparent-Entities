package net.lopymine.te.compat.sodium.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.*;
import net.caffeinemc.mods.sodium.api.vertex.attributes.common.ColorAttribute;
import net.lopymine.te.transparency.TransparencyManager;
import net.lopymine.te.transparency.TransparencyLayers;
import net.lopymine.te.thing.ThingCaptures;
import net.lopymine.te.utils.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;

@Pseudo
@Mixin(ColorAttribute.class)
public class ColorAttributeMixin {

	@Dynamic
	@WrapOperation(at = @At(value = "INVOKE", target = "Lorg/lwjgl/system/MemoryUtil;memPutInt(JI)V", remap = false), method = "set", remap = false)
	private static void swapColor(long ptr, int color, Operation<Void> original) {
		int a = AbgrUtils.getAlpha(color);
		int r = AbgrUtils.getRed(color);
		int g = AbgrUtils.getGreen(color);
		int b = AbgrUtils.getBlue(color);

		int originalArgb = ArgbUtils.getArgb(a, r, g, b);

		int abgr = AbgrUtils.toAbgr(TransparencyManager.getArgbColorFromAnyCapturedThing(originalArgb));
		original.call(ptr, abgr);
	}
}
