package net.lopymine.te.client.command.favorite;

import net.minecraft.command.CommandSource;
import net.minecraft.text.ClickEvent.Action;
import net.minecraft.text.Text;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

import net.lopymine.te.client.TransparentEntitiesClient;
import net.lopymine.te.client.command.CommandTextBuilder;
import net.lopymine.te.client.command.argument.ClientEntityArgumentType;
import net.lopymine.te.config.*;
import net.lopymine.te.config.data.EntityData;

import java.util.*;

import org.jetbrains.annotations.NotNull;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.argument;
import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.literal;

public class FavoriteCommands {

	public static LiteralArgumentBuilder<FabricClientCommandSource> get() {
		return literal("favorite")
				.then(literal("add")
						.then(argument("entity", ClientEntityArgumentType.entity())
								.executes(FavoriteCommands::addFavoriteEntity)))
				.then(literal("remove")
						.then(argument("entity", ClientEntityArgumentType.entity())
								.suggests(getRemoveSuggestions())
								.executes(FavoriteCommands::removeFavoriteEntity)));
	}

	private static @NotNull SuggestionProvider<FabricClientCommandSource> getRemoveSuggestions() {
		return (context, builder) ->
				CommandSource.suggestMatching(TransparentEntitiesClient.getConfig().getFavoriteEntities()
						.values()
						.stream()
						.map(EntityData::toString), builder);
	}

	private static int addFavoriteEntity(CommandContext<FabricClientCommandSource> context) {
		return handleFavoriteEntity(context, true);
	}

	private static int removeFavoriteEntity(CommandContext<FabricClientCommandSource> context) {
		return handleFavoriteEntity(context, false);
	}

	private static int handleFavoriteEntity(CommandContext<FabricClientCommandSource> context, boolean add) {
		EntityData entityData = ClientEntityArgumentType.getEntity("entity", context);
		UUID uuid = entityData.getUuid();
		String name = entityData.getName();

		TransparentEntitiesConfig config = TransparentEntitiesClient.getConfig();
		HashMap<UUID, EntityData> favoriteEntities = config.getFavoriteEntities();

		boolean success = add ? !favoriteEntities.containsKey(uuid) && favoriteEntities.put(uuid, entityData) == null : favoriteEntities.containsKey(uuid) && favoriteEntities.remove(uuid) != null;

		String action = add ? "add" : "remove";
		String result = success ? "success" : "failed";
		String key = String.format("favorite_entities.%s.%s", action, result);

		Text text = CommandTextBuilder.startBuilder(key, name)
				.withClickEvent(Action.COPY_TO_CLIPBOARD, uuid)
				.build();

		context.getSource().sendFeedback(text);

		config.save();

		return Command.SINGLE_SUCCESS;
	}

}
