package net.lopymine.ed.modmenu;

import net.lopymine.mossylib.modmenu.AbstractModMenuIntegration;
import net.lopymine.ed.EntityDithering;
import net.lopymine.ed.yacl.YACLConfigurationScreen;
import net.minecraft.client.gui.screens.Screen;

public class ModMenuIntegration extends AbstractModMenuIntegration {

	@Override
	protected String getModId() {
		return EntityDithering.MOD_ID;
	}

	@Override
	protected Screen createConfigScreen(Screen screen) {
		return YACLConfigurationScreen.createScreen(screen);
	}
}
