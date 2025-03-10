package net.lopymine.ms.compat.yacl.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.impl.YetAnotherConfigLibImpl;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;

import net.lopymine.ms.utils.yacl.BetterYACLScreenConfig;
import net.lopymine.ms.yacl.screen.MoreSpaceYACLScreen;

@Pseudo
@Mixin(YetAnotherConfigLibImpl.class)
public class YetAnotherConfigLibImplMixin implements BetterYACLScreenConfig {

	@Dynamic
	@Unique
	private boolean enabled;

	@Dynamic
	@ModifyReturnValue(at = @At("RETURN"), method = "generateScreen")
	private Screen swapScreen(Screen original, @Local(argsOnly = true) Screen parent) {
		if (!this.enabled) {
			return original;
		}
		return new MoreSpaceYACLScreen(((YetAnotherConfigLib) this), parent);
	}


	@Override
	public YetAnotherConfigLib moreSpace$enable() {
		this.enabled = true;
		return ((YetAnotherConfigLib) this);
	}
}
