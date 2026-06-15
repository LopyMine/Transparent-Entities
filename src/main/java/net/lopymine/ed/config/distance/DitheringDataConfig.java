package net.lopymine.ed.config.distance;

import com.mojang.serialization.Codec;
import java.util.function.Supplier;
import lombok.*;
import net.lopymine.mossylib.utils.CodecUtils;
import static com.mojang.serialization.Codec.DOUBLE;
import static com.mojang.serialization.codecs.RecordCodecBuilder.create;
import static net.lopymine.mossylib.utils.CodecUtils.option;

@Getter
@Setter
@AllArgsConstructor
public class DitheringDataConfig {

	public static final Codec<DitheringDataConfig> CODEC = create((instance) -> instance.group(
			option("min_value", 0.0, DOUBLE, DitheringDataConfig::getMinValue),
			option("pixel_size", 1.0D, DOUBLE, DitheringDataConfig::getPixelSize),
			option("far", 3.0D, DOUBLE, DitheringDataConfig::getFar),
			option("near", -0.5D, DOUBLE, DitheringDataConfig::getNear)
	).apply(instance, DitheringDataConfig::new));

	private double minValue;
	private double pixelSize;
	private double far;
	private double near;

	public static Supplier<DitheringDataConfig> getNewInstance() {
		return () -> CodecUtils.parseNewInstanceHacky(CODEC);
	}
}
