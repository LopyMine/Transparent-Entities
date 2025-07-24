package net.lopymine.te.mixin.glint;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.lopymine.te.transparency.layers.TransparencyArmorLayer;
import net.minecraft.client.render.*;
import net.minecraft.client.util.BufferAllocator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BufferBuilderStorage.class)
public class BufferBuilderStorageMixin {

	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/RenderLayer;getEntityGlint()Lnet/minecraft/client/render/RenderLayer;"), method = "method_54639")
	private void addCustomGlint(Object2ObjectLinkedOpenHashMap<RenderLayer, BufferAllocator> map, CallbackInfo ci) {
		RenderLayer layer = TransparencyArmorLayer.ARMOR_GLINT_TRANSLUCENT;
		map.put(layer, new BufferAllocator(layer.getExpectedBufferSize()));
	}

}
