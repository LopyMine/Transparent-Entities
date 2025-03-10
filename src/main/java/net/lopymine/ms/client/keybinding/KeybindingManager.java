package net.lopymine.ms.client.keybinding;

import net.minecraft.client.util.InputUtil.Type;
import org.lwjgl.glfw.GLFW;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;

import net.lopymine.ms.client.MoreSpaceClient;
import net.lopymine.ms.config.MoreSpaceConfig;

public class KeybindingManager {

    private static final SingleUseKeybinding TOGGLE_MOD = new SingleUseKeybinding("toggle_mod", Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN);

    public static void register() {
        KeybindingManager.registerKeybindings();

        ClientTickEvents.START_CLIENT_TICK.register((client -> {
            TOGGLE_MOD.runOnceIfPressed(() -> {
	            MoreSpaceConfig config = MoreSpaceClient.getConfig();
	            config.setModEnabled(!config.isModEnabled());
            });
        }));
    }

    private static void registerKeybindings() {
        KeyBindingHelper.registerKeyBinding(TOGGLE_MOD);
    }
}
