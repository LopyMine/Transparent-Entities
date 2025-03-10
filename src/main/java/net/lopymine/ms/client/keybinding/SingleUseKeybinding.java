package net.lopymine.ms.client.keybinding;

import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil.Type;

import net.lopymine.ms.MoreSpace;

public class SingleUseKeybinding extends KeyBinding {
    private boolean wasPressed = false;

    public SingleUseKeybinding(String name, Type type, int code) {
        super("%s.keybinding.%s.name".formatted(MoreSpace.MOD_ID, name), type, code, "%s.keybinding.name".formatted(MoreSpace.MOD_ID));
    }

	@SuppressWarnings("all")
    public boolean runOnceIfPressed(Runnable runnable) {
        boolean isPressed = this.isPressed();
        if (isPressed && !this.wasPressed) {
            runnable.run();
	        this.wasPressed = true;
            return true;
        }
		if (!isPressed && this.wasPressed) {
			this.wasPressed = false;
		}
		return false;
    }
}
