package net.lopymine.te.compat.sodium.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.*;
import net.caffeinemc.mods.sodium.api.util.*;
import net.caffeinemc.mods.sodium.api.vertex.format.common.EntityVertex;
import net.lopymine.te.entity.EntityCaptures;
import net.lopymine.te.render.TransparencyManager;
import net.lopymine.te.utils.ArgbUtils;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;

@Pseudo
@Mixin(EntityVertex.class)
public class EntityVertexMixin {

	@Dynamic
	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/caffeinemc/mods/sodium/api/vertex/attributes/common/ColorAttribute;set(JI)V", remap = false), method = "write", remap = false )
	private static void swapColor(long ptr, int color, Operation<Void> original) {
		Entity entity = EntityCaptures.MAIN.getEntity();
		if (entity == null) {
			original.call(ptr, color);
			return;
		}

		int a = ColorABGR.unpackAlpha(color);
		int r = ColorABGR.unpackRed(color);
		int g = ColorABGR.unpackGreen(color);
		int b = ColorABGR.unpackBlue(color);

		int originalArgb = ArgbUtils.getArgb(a, r, g, b);

		int abgrColor = ColorARGB.toABGR(TransparencyManager.getTranslucentArgb(entity, originalArgb));
		original.call(ptr, abgrColor);
	}
}
