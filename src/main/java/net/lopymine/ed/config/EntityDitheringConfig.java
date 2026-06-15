package net.lopymine.ed.config;

import lombok.*;
import net.lopymine.ed.config.entity.*;
import net.lopymine.mossylib.loader.MossyLoader;
import net.lopymine.mossylib.utils.*;
import net.lopymine.ed.EntityDithering;
import net.lopymine.ed.config.distance.DitheringDataConfig;
import net.lopymine.ed.dithering.DitheringManager;
import net.minecraft.core.UUIDUtil;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.core.registries.BuiltInRegistries;
import org.slf4j.*;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.lopymine.ed.config.data.EntityData;

import java.io.*;
import java.util.*;
import java.util.concurrent.CompletableFuture;

import static net.lopymine.mossylib.utils.CodecUtils.option;

@Getter
@Setter
@AllArgsConstructor
public class EntityDitheringConfig {

	public static final EntityIdentifier MY_PLAYER = CustomEntityIdentifier.of("my_player");

	public static final Codec<EntityDitheringConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			option("mod_enabled", true, Codec.BOOL, EntityDitheringConfig::isModEnabled),
			option("hide_entities", getStandardHideEntitiesSet(), Identifier.CODEC, EntityDitheringConfig::getHideEntities),
			option("favorite_entities", new HashMap<>(), UUIDUtil.CODEC, EntityData.CODEC, EntityDitheringConfig::getFavoriteEntities),
			option("dithering_data", DitheringDataConfig.getNewInstance(), DitheringDataConfig.CODEC, EntityDitheringConfig::getDitheringDataConfig)
	).apply(instance, EntityDitheringConfig::new));

	private static final File CONFIG_FILE = MossyLoader.getConfigDir().resolve(EntityDithering.MOD_ID + ".json5").toFile();
	private static final Logger LOGGER = LoggerFactory.getLogger(EntityDithering.MOD_NAME + "/Config");
	private static EntityDitheringConfig INSTANCE;

	private boolean modEnabled;
	private HashSet<Identifier> hideEntities;
	private HashMap<UUID, EntityData> favoriteEntities;
	private DitheringDataConfig ditheringDataConfig;

	private static HashSet<Identifier> getStandardHideEntitiesSet() {
		HashSet<Identifier> identifiers = new HashSet<>();
		identifiers.add(BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.PLAYER));
		return identifiers;
	}

	private EntityDitheringConfig() {
		throw new IllegalArgumentException();
	}

	public static EntityDitheringConfig getInstance() {
		return INSTANCE == null ? reload() : INSTANCE;
	}

	public static EntityDitheringConfig reload() {
		return INSTANCE = read();
	}

	public static EntityDitheringConfig getNewInstance() {
		return CodecUtils.parseNewInstanceHacky(CODEC);
	}

	private static EntityDitheringConfig read() {
		EntityDitheringConfig config = ConfigUtils.readConfig(CODEC, CONFIG_FILE, LOGGER);
		DitheringManager.loadEntityTypes(config);
		return config;
	}

	public void saveAsync() {
		CompletableFuture.runAsync(this::save);
	}

	public void save() {
		DitheringManager.loadEntityTypes(this);
		ConfigUtils.saveConfig(this, CODEC, CONFIG_FILE, LOGGER);
	}
}
