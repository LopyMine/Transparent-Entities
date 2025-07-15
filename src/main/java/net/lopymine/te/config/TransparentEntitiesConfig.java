package net.lopymine.te.config;

import com.google.gson.*;
import lombok.*;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.util.*;
import org.slf4j.*;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.loader.api.FabricLoader;

import net.lopymine.te.TransparentEntities;
import net.lopymine.te.client.TransparentEntitiesClient;
import net.lopymine.te.config.data.EntityData;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import org.jetbrains.annotations.NotNull;

import static net.lopymine.te.utils.CodecUtils.option;

@Getter
@Setter
@AllArgsConstructor
public class TransparentEntitiesConfig {

	public static final Codec<TransparentEntitiesConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			option("hiding_activation_distance", 4.0F, Codec.FLOAT, TransparentEntitiesConfig::getHidingActivationDistance),
			option("full_hiding_distance", 2.8F, Codec.FLOAT, TransparentEntitiesConfig::getFullHidingDistance),
			option("min_hiding_value", 0.2F, Codec.FLOAT, TransparentEntitiesConfig::getMinHidingValue),
			option("mod_enabled", true, Codec.BOOL, TransparentEntitiesConfig::isModEnabled),
			option("hide_shadow_enabled", true, Codec.BOOL, TransparentEntitiesConfig::isHideShadowEnabled),
			option("hide_head_when_wearing_something", false, Codec.BOOL, TransparentEntitiesConfig::isHideHeadWhenWearingSomething),
			option("hide_entities", getStandardHideEntitiesSet(), Identifier.CODEC, TransparentEntitiesConfig::getHideEntities),
			option("favorite_entities", new HashMap<>(), Uuids.CODEC, EntityData.CODEC, TransparentEntitiesConfig::getFavoriteEntities)
	).apply(instance, TransparentEntitiesConfig::new));

	private static final File CONFIG_FILE = FabricLoader.getInstance().getConfigDir().resolve(TransparentEntities.MOD_ID + ".json5").toFile();
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	private static final Logger LOGGER = LoggerFactory.getLogger(TransparentEntities.MOD_NAME + "/Config");

	public float hidingActivationDistance;
	public float fullHidingDistance;
	public float minHidingValue;
	private boolean modEnabled;
	private boolean hideShadowEnabled;
	private boolean hideHeadWhenWearingSomething;
	private HashSet<Identifier> hideEntities;
	private HashMap<UUID, EntityData> favoriteEntities;

	public TransparentEntitiesConfig() {
		this.hidingActivationDistance              = 4.0F;
		this.fullHidingDistance                    = 2.8F;
		this.minHidingValue                        = 0.2F;
		this.modEnabled                            = true;
		this.hideShadowEnabled                     = true;
		this.hideHeadWhenWearingSomething          = false;
		this.favoriteEntities                      = new HashMap<>();
		this.hideEntities                          = getStandardHideEntitiesSet();
	}

	private static HashSet<Identifier> getStandardHideEntitiesSet() {
		HashSet<Identifier> identifiers = new HashSet<>();
		identifiers.add(Registries.ENTITY_TYPE.getId(EntityType.PLAYER));
		return identifiers;
	}

	public static TransparentEntitiesConfig getInstance() {
		return TransparentEntitiesConfig.read();
	}

	private static @NotNull TransparentEntitiesConfig create() {
		TransparentEntitiesConfig config = new TransparentEntitiesConfig();
		try (FileWriter writer = new FileWriter(CONFIG_FILE, StandardCharsets.UTF_8)) {
			String json = GSON.toJson(CODEC.encode(config, JsonOps.INSTANCE, JsonOps.INSTANCE.empty())/*? if >=1.20.5 {*/.getOrThrow());/*?} else*//*.getOrThrow(false, LOGGER::error));*/
			writer.write(json);
		} catch (Exception e) {
			LOGGER.error("Failed to create config", e);
		}
		return config;
	}

	private static TransparentEntitiesConfig read() {
		if (!CONFIG_FILE.exists()) {
			return TransparentEntitiesConfig.create();
		}

		try (FileReader reader = new FileReader(CONFIG_FILE, StandardCharsets.UTF_8)) {
			return CODEC.decode(JsonOps.INSTANCE, JsonParser.parseReader(reader))/*? if >=1.20.5 {*/.getOrThrow()/*?} else {*//*.getOrThrow(false, LOGGER::error)*//*?}*/.getFirst();
		} catch (Exception e) {
			LOGGER.error("Failed to read config", e);
		}
		return TransparentEntitiesConfig.create();
	}

	public void save() {
		TransparentEntitiesClient.setConfig(this);
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
