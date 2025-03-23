package net.lopymine.te.config.data;

import lombok.*;
import net.minecraft.util.Uuids;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.*;

import org.jetbrains.annotations.*;

import static net.lopymine.te.utils.CodecUtils.option;

@Getter
@AllArgsConstructor
public class EntityData {

	private boolean playerData;
	@NotNull
	private String name;
	@NotNull
	private UUID uuid;

	public static final String UNKNOWN = "Unknown";

	public static final Codec<EntityData> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
			option("player_data", false, Codec.BOOL, EntityData::isPlayerData),
			option("name", UNKNOWN, Codec.STRING, EntityData::getName),
			option("uuid", UUID.randomUUID(), Uuids.CODEC, EntityData::getUuid)
	).apply(instance, EntityData::new));

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof EntityData data)) {
			return false;
		}
		return Objects.equals(this.getUuid(), data.getUuid());
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.getUuid());
	}

	@Override
	public String toString() {
		if (this.isPlayerData()) {
			return this.getName();
		}
		return "%s[%s]".formatted(this.getName(), this.getUuid());
	}
}
