package net.lopymine.ed.entrypoint;

//? if fabric {

import net.fabricmc.api.ClientModInitializer;
import net.lopymine.ed.client.EntityDitheringClient;

public class ClientEntrypoint implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		EntityDitheringClient.onInitializeClient();
	}
}

//?} elif neoforge {
/*import java.util.*;
import net.lopymine.ed.EntityDithering;
import net.lopymine.ed.client.EntityDitheringClient;
import net.lopymine.ed.modmenu.ModMenuIntegration;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(value = EntityDithering.MOD_ID, dist = Dist.CLIENT)
public class ClientEntrypoint {

	public ClientEntrypoint(ModContainer container) {
		EntityDitheringClient.onInitializeClient();
		ModMenuIntegration integration = new ModMenuIntegration();
		integration.register(container);
	}

}

*///?}
