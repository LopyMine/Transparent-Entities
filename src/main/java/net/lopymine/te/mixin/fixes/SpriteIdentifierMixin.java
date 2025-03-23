package net.lopymine.te.mixin.fixes;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Function;
import org.jetbrains.annotations.Nullable;

@Mixin(SpriteIdentifier.class)
public class SpriteIdentifierMixin {

	@Shadow @Nullable private RenderLayer layer;

	@Shadow @Final private Identifier atlas;

	@Inject(at = @At("HEAD"), method = "getRenderLayer", cancellable = true)
	private void generated(Function<Identifier, RenderLayer> layerFactory, CallbackInfoReturnable<RenderLayer> cir) {
		this.layer = layerFactory.apply(this.atlas);
		cir.setReturnValue(this.layer);
	}

}
