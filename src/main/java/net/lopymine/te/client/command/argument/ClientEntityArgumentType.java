package net.lopymine.te.client.command.argument;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.*;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.Entity;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.*;
import com.mojang.brigadier.suggestion.*;

import net.lopymine.te.client.command.CommandTextBuilder;
import net.lopymine.te.config.data.EntityData;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import org.jetbrains.annotations.NotNull;

public class ClientEntityArgumentType implements ArgumentType<EntityData> {

	public static final DynamicCommandExceptionType FAILED_PARSING = new DynamicCommandExceptionType(o -> CommandTextBuilder.startBuilder("argument.entity_data.exception.failed_parsing", o).build());
	public static final DynamicCommandExceptionType UNKNOWN_ENTITY = new DynamicCommandExceptionType(o -> CommandTextBuilder.startBuilder("argument.entity_data.exception.unknown_entity", o).build());
	private static final Collection<String> EXAMPLES = Arrays.asList("LopyMine", "Sniffer[7b829ed5-9b74-428f-9b4d-ede06975f123]");

	private ClientEntityArgumentType() {

	}

	public static @NotNull ClientEntityArgumentType entity() {
		return new ClientEntityArgumentType();
	}

	public static <S> EntityData getEntity(String name, @NotNull CommandContext<S> context) {
		return context.getArgument(name, EntityData.class);
	}

	@Override
	public EntityData parse(@NotNull StringReader reader) throws CommandSyntaxException {
		String s = this.readString(reader);
		if (!s.contains("[") || !s.contains("]")) {
			return this.parsePlayerData(s, reader);
		}
		return this.parseEntityData(s, reader);
	}

	private String readString(@NotNull StringReader reader) {
		final int start = reader.getCursor();
		while (reader.canRead() && iaAllowed(reader.peek())) {
			reader.skip();
		}
		return reader.getString().substring(start, reader.getCursor());
	}

	private static boolean iaAllowed(final char c) {
		return c >= '0' && c <= '9'
				|| c >= 'A' && c <= 'Z'
				|| c >= 'a' && c <= 'z'
				|| c == '_' || c == '-'
				|| c == '.' || c == '+'
				|| c == '[' || c == ']';
	}

	private EntityData parseEntityData(String id, StringReader reader) throws CommandSyntaxException {
		try {
			int start = id.indexOf("[");
			int end = id.indexOf("]");
			String name = id.substring(0, start);
			UUID uuid = UUID.fromString(id.substring(start + 1, end));
			return new EntityData(false, name, uuid);
		} catch (Exception e) {
			throw FAILED_PARSING.createWithContext(reader, reader.getString());
		}
	}

	private EntityData parsePlayerData(String nickname, StringReader reader) throws CommandSyntaxException {
		MinecraftClient client = MinecraftClient.getInstance();
		ClientPlayNetworkHandler networkHandler = client.getNetworkHandler();
		if (networkHandler == null) {
			throw FAILED_PARSING.createWithContext(reader, reader.getString());
		}

		for (PlayerListEntry entry : networkHandler.getPlayerList()) {
			GameProfile profile = entry.getProfile();
			String name = profile.getName();
			if (nickname.equals(name)) {
				return new EntityData(true, profile.getName(), profile.getId());
			}
		}

		UUID inputUuid = UUID.fromString(nickname);
		for (PlayerListEntry entry : networkHandler.getPlayerList()) {
			GameProfile profile = entry.getProfile();
			UUID uuid = profile.getId();
			if (inputUuid.equals(uuid)) {
				return new EntityData(true, profile.getName(), profile.getId());
			}
		}

		throw UNKNOWN_ENTITY.createWithContext(reader, reader.getString());
	}

	@Override
	public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
		MinecraftClient client = MinecraftClient.getInstance();
		ClientPlayNetworkHandler networkHandler = client.getNetworkHandler();
		if (networkHandler == null) {
			return Suggestions.empty();
		}

		ClientPlayerEntity player = client.player;

		List<String> list = new ArrayList<>(networkHandler.getPlayerList()
				.stream()
				.map(PlayerListEntry::getProfile)
				.filter((profile) -> player == null || !profile.equals(player.getGameProfile()))
				.map(GameProfile::getName)
				.toList());

		Entity targetedEntity = client.targetedEntity;
		if (targetedEntity != null) {
			list.add(0, "%s[%s]".formatted(targetedEntity.getName().getString(), targetedEntity.getUuid()));
		}

		return CommandSource.suggestMatching(list, builder);
	}

	@Override
	public Collection<String> getExamples() {
		return EXAMPLES;
	}
}
