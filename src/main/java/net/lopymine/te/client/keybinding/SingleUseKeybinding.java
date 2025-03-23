package net.lopymine.te.client.keybinding;

import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil.Type;

import net.lopymine.te.TransparentEntities;

public class SingleUseKeybinding extends KeyBinding {
    private boolean wasPressed = false;

    public SingleUseKeybinding(String name, Type type, int code) {
        super("%s.keybinding.%s.name".formatted(TransparentEntities.MOD_ID, name), type, code, TransparentEntitiesKeybindingManager.TITLE_KEY);
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
