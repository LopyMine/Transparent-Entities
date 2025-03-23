package net.lopymine.te.client.command;

import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;

import net.lopymine.te.TransparentEntities;
import net.lopymine.te.client.command.favorite.FavoriteCommands;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class TransparentEntitiesCommandManager {

	public static void register() {
		ClientCommandRegistrationCallback.EVENT.register((dispatcher, access) -> {
			dispatcher.register(literal(TransparentEntities.MOD_ID).then(FavoriteCommands.get()));
		});
	}

}
