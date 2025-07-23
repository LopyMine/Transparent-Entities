package net.lopymine.te.mixin.fixes;

import com.llamalad7.mixinextras.injector.wrapoperation.*;
import net.lopymine.te.thing.ThingCaptures;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/Screen;renderWithTooltip(Lnet/minecraft/client/gui/DrawContext;IIF)V"), method = "render")
	private void generated(Screen instance, DrawContext context, int mouseX, int mouseY, float delta, Operation<Void> original) {
		ThingCaptures.CURRENT_RENDERING_ENTITY.setEnabled(false);
		original.call(instance, context, mouseX, mouseY, delta);
		ThingCaptures.CURRENT_RENDERING_ENTITY.setEnabled(true);
	}

}
