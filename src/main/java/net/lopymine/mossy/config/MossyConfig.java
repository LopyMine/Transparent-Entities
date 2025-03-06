package net.lopymine.mossy.config;

import com.google.gson.*;
import lombok.*;
import org.slf4j.*;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.loader.api.FabricLoader;

import net.lopymine.mossy.Mossy;
import net.lopymine.mossy.client.MossyClient;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import org.jetbrains.annotations.NotNull;

import static net.lopymine.mossy.utils.CodecUtils.option;
import static net.lopymine.mossy.utils.CodecUtils.optional;

@Getter
@Setter
@AllArgsConstructor
public class MossyConfig {

	public static final Codec<MossyConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			option("mossy", Codec.BOOL, MossyConfig::isMossy),
			optional("secret", 0.0F, Codec.FLOAT, MossyConfig::getSecret)
	).apply(instance, MossyConfig::new));

	private static final File CONFIG_FILE = FabricLoader.getInstance().getConfigDir().resolve(Mossy.MOD_ID + ".json5").toFile();
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	private static final Logger LOGGER = LoggerFactory.getLogger(Mossy.MOD_NAME + "/Config");
	private boolean mossy;
	private float secret;

	public MossyConfig() {
		this.mossy  = false;
		this.secret = 0.0F;
	}

	public static MossyConfig getInstance() {
		return MossyConfig.read();
	}

	private static @NotNull MossyConfig create() {
		MossyConfig config = new MossyConfig();
		try (FileWriter writer = new FileWriter(CONFIG_FILE, StandardCharsets.UTF_8)) {
			String json = GSON.toJson(CODEC.encode(config, JsonOps.INSTANCE, JsonOps.INSTANCE.empty())/*? if >=1.20.5 {*/.getOrThrow());/*?} else*//*.getOrThrow(false, LOGGER::error));*/
			writer.write(json);
		} catch (Exception e) {
			LOGGER.error("Failed to create config", e);
		}
		return config;
	}

	private static MossyConfig read() {
		if (!CONFIG_FILE.exists()) {
			return MossyConfig.create();
		}

		try (FileReader reader = new FileReader(CONFIG_FILE, StandardCharsets.UTF_8)) {
			return CODEC.decode(JsonOps.INSTANCE, JsonParser.parseReader(reader))/*? if >=1.20.5 {*/.getOrThrow()/*?} else {*//*.getOrThrow(false, LOGGER::error)*//*?}*/.getFirst();
		} catch (Exception e) {
			LOGGER.error("Failed to read config", e);
		}
		return MossyConfig.create();
	}

	public void save() {
		MossyClient.setConfig(this);
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
