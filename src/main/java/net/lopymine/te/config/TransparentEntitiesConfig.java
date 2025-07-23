package net.lopymine.te.config;

import com.google.gson.*;
import lombok.*;
import net.lopymine.te.config.distance.TransparencyDistance;
import net.lopymine.te.config.entity.*;
import net.lopymine.te.utils.CodecUtils;
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

	public static final EntityIdentifier PARTICLES_ID = CustomEntityIdentifier.of("particles");
	public static final EntityIdentifier ENTITY_NAME_ID = CustomEntityIdentifier.of("entity_name");

	public static final Codec<TransparentEntitiesConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			option("transparency_distance", TransparencyDistance.getNewInstance(), TransparencyDistance.CODEC, TransparentEntitiesConfig::getTransparencyDistance),
			option("min_hiding_value", 0.2D, Codec.DOUBLE, TransparentEntitiesConfig::getMinHidingValue),
			option("mod_enabled", true, Codec.BOOL, TransparentEntitiesConfig::isModEnabled),
			option("hide_shadow_enabled", true, Codec.BOOL, TransparentEntitiesConfig::isHideShadowEnabled),
			option("hide_head_when_wearing_something", false, Codec.BOOL, TransparentEntitiesConfig::isHideHeadWhenWearingSomething),
			option("hide_entities", getStandardHideEntitiesSet(), Identifier.CODEC, TransparentEntitiesConfig::getHideEntities),
			option("favorite_entities", new HashMap<>(), Uuids.CODEC, EntityData.CODEC, TransparentEntitiesConfig::getFavoriteEntities)
	).apply(instance, TransparentEntitiesConfig::new));

	private static final File CONFIG_FILE = FabricLoader.getInstance().getConfigDir().resolve(TransparentEntities.MOD_ID + ".json5").toFile();
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	private static final Logger LOGGER = LoggerFactory.getLogger(TransparentEntities.MOD_NAME + "/Config");

	public TransparencyDistance transparencyDistance;
	public double minHidingValue;
	private boolean modEnabled;
	private boolean hideShadowEnabled;
	private boolean hideHeadWhenWearingSomething;
	private HashSet<Identifier> hideEntities;
	private HashMap<UUID, EntityData> favoriteEntities;

	private static HashSet<Identifier> getStandardHideEntitiesSet() {
		HashSet<Identifier> identifiers = new HashSet<>();
		identifiers.add(Registries.ENTITY_TYPE.getId(EntityType.PLAYER));
		return identifiers;
	}

	public static TransparentEntitiesConfig getNewInstance() {
		return CodecUtils.parseNewInstanceHacky(CODEC);
	}

	public static TransparentEntitiesConfig getInstance() {
		return TransparentEntitiesConfig.read();
	}

	private static @NotNull TransparentEntitiesConfig create() {
		TransparentEntitiesConfig config = TransparentEntitiesConfig.getNewInstance();
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

			Float fullHidingDistance = null;
			Float hidingActivationDistance = null;
			JsonElement jsonElement = JsonParser.parseReader(reader);
			if (jsonElement.isJsonObject()) {
				JsonObject object = jsonElement.getAsJsonObject();
				if (object.has("full_hiding_distance")) {
					fullHidingDistance = object.get("full_hiding_distance").getAsFloat();
				}
				if (object.has("hiding_activation_distance")) {
					hidingActivationDistance = object.get("hiding_activation_distance").getAsFloat();
				}
			}
			TransparentEntitiesConfig config = CODEC.decode(JsonOps.INSTANCE, jsonElement)/*? if >=1.20.5 {*/.getOrThrow()/*?} else {*//*.getOrThrow(false, LOGGER::error)*//*?}*/.getFirst();

			TransparencyDistance distance = config.getTransparencyDistance();
			if (fullHidingDistance != null) {
				distance.setFullHidingDistanceXZ(fullHidingDistance);
				distance.setFullHidingDistanceY(fullHidingDistance);
			}
			if (hidingActivationDistance != null) {
				distance.setHidingActivationDistanceXZ(hidingActivationDistance);
				distance.setHidingActivationDistanceY(hidingActivationDistance);
			}

			return config;
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
