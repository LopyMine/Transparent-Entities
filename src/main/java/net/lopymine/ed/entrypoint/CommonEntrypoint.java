package net.lopymine.ed.entrypoint;

//? if fabric {
import net.lopymine.ed.EntityDithering;
import net.fabricmc.api.ModInitializer;

public class CommonEntrypoint implements ModInitializer {

	@Override
	public void onInitialize() {
		EntityDithering.onInitialize();
	}
}

//?} elif neoforge {

/*import net.neoforged.fml.common.Mod;
import net.lopymine.ed.EntityDithering;

@Mod(EntityDithering.MOD_ID)
public class CommonEntrypoint {

	public CommonEntrypoint() {
		EntityDithering.onInitialize();
	}

}

*///?}

