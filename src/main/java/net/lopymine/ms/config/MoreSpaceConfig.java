package net.lopymine.ms.config;

import com.google.gson.*;
import lombok.*;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.util.*;
import net.minecraft.util.dynamic.Codecs;
import org.slf4j.*;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.loader.api.FabricLoader;

import net.lopymine.ms.MoreSpace;
import net.lopymine.ms.client.MoreSpaceClient;
import net.lopymine.ms.utils.CodecUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import org.jetbrains.annotations.NotNull;

import static net.lopymine.ms.utils.CodecUtils.option;

@Getter
@Setter
@AllArgsConstructor
public class MoreSpaceConfig {

	public static final Codec<MoreSpaceConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			option("hiding_activation_distance", 3.5F, Codec.FLOAT, MoreSpaceConfig::getHidingActivationDistance),
			option("full_hiding_distance", 2.8F, Codec.FLOAT, MoreSpaceConfig::getFullHidingDistance),
			option("min_hiding_value", 0.0F, Codec.FLOAT, MoreSpaceConfig::getMinHidingValue),
			option("mod_enabled", true, Codec.BOOL, MoreSpaceConfig::isModEnabled),
			option("click_through_translucent_players_enabled", false, Codec.BOOL, MoreSpaceConfig::isClickThroughTranslucentPlayersEnabled),
			option("hide_shadow_enabled", true, Codec.BOOL, MoreSpaceConfig::isHideShadowEnabled),
			option("favorite_players", new HashSet<>(), Uuids.CODEC, MoreSpaceConfig::getFavoritePlayers),
			option("hide_entities", getStandardHideEntitiesSet(), Identifier.CODEC, MoreSpaceConfig::getHideEntities)
	).apply(instance, MoreSpaceConfig::new));

	private static final File CONFIG_FILE = FabricLoader.getInstance().getConfigDir().resolve(MoreSpace.MOD_ID + ".json5").toFile();
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	private static final Logger LOGGER = LoggerFactory.getLogger(MoreSpace.MOD_NAME + "/Config");

	public float hidingActivationDistance;
	public float fullHidingDistance;
	public float minHidingValue;
	private boolean modEnabled;
	private boolean clickThroughTranslucentPlayersEnabled;
	private boolean hideShadowEnabled;
	private HashSet<UUID> favoritePlayers;
	private HashSet<Identifier> hideEntities;

	public MoreSpaceConfig() {
		this.hidingActivationDistance              = 3.5F;
		this.fullHidingDistance                    = 2.8F;
		this.minHidingValue                        = 0.0F;
		this.modEnabled                            = true;
		this.clickThroughTranslucentPlayersEnabled = false;
		this.hideShadowEnabled                     = true;
		this.favoritePlayers                       = new HashSet<>();
		this.hideEntities                          = getStandardHideEntitiesSet();
	}

	private static HashSet<Identifier> getStandardHideEntitiesSet() {
		HashSet<Identifier> identifiers = new HashSet<>();
		identifiers.add(Registries.ENTITY_TYPE.getId(EntityType.PLAYER));
		return identifiers;
	}

	public static MoreSpaceConfig getInstance() {
		return MoreSpaceConfig.read();
	}

	private static @NotNull MoreSpaceConfig create() {
		MoreSpaceConfig config = new MoreSpaceConfig();
		try (FileWriter writer = new FileWriter(CONFIG_FILE, StandardCharsets.UTF_8)) {
			String json = GSON.toJson(CODEC.encode(config, JsonOps.INSTANCE, JsonOps.INSTANCE.empty())/*? if >=1.20.5 {*/.getOrThrow());/*?} else*//*.getOrThrow(false, LOGGER::error));*/
			writer.write(json);
		} catch (Exception e) {
			LOGGER.error("Failed to create config", e);
		}
		return config;
	}

	private static MoreSpaceConfig read() {
		if (!CONFIG_FILE.exists()) {
			return MoreSpaceConfig.create();
		}

		try (FileReader reader = new FileReader(CONFIG_FILE, StandardCharsets.UTF_8)) {
			return CODEC.decode(JsonOps.INSTANCE, JsonParser.parseReader(reader))/*? if >=1.20.5 {*/.getOrThrow()/*?} else {*//*.getOrThrow(false, LOGGER::error)*//*?}*/.getFirst();
		} catch (Exception e) {
			LOGGER.error("Failed to read config", e);
		}
		return MoreSpaceConfig.create();
	}

	public void save() {
		MoreSpaceClient.setConfig(this);
		CompletableFuture.runAsync(() -> {
			try (FileWriter writer = new FileWriter(CONFIG_FILE, StandardCharsets.UTF_8)) {
				String json = GSON.toJson(CODEC.encode(this, JsonOps.INSTANCE, JsonOps.INSTANCE.empty())/*? if >=1.20.5 {*/.getOrThrow());/*?} else*//*.getOrThrow(false, LOGGER::error));*/
				writer.write(json);
			} catch (Exception e) {
				LOGGER.error("Failed to save config", e);
			}
		});
	}
}
