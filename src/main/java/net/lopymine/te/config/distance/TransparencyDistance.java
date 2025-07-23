package net.lopymine.te.config.distance;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.*;
import net.lopymine.te.utils.CodecUtils;
import net.minecraft.util.math.*;
import static net.lopymine.te.utils.CodecUtils.option;

@Getter
@Setter
@AllArgsConstructor
public class TransparencyDistance {

	public static final Codec<TransparencyDistance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			option("hiding_activation_distance_xz", 4.2D, Codec.DOUBLE, TransparencyDistance::getHidingActivationDistanceXZ),
			option("hiding_activation_distance_y", 4.2D, Codec.DOUBLE, TransparencyDistance::getHidingActivationDistanceY),
			option("full_hiding_distance_xz", 3.0D, Codec.DOUBLE, TransparencyDistance::getFullHidingDistanceXZ),
			option("full_hiding_distance_y", 3.0D, Codec.DOUBLE, TransparencyDistance::getFullHidingDistanceY)
	).apply(instance, TransparencyDistance::new));

	private double hidingActivationDistanceXZ;
	private double hidingActivationDistanceY;
	private double fullHidingDistanceXZ;
	private double fullHidingDistanceY;

	public static TransparencyDistance getNewInstance() {
		return CodecUtils.parseNewInstanceHacky(CODEC);
	}

	public Distance getDistance(Vec3d cameraPos, Vec3d thingPos) {
		float xzDistance = this.calculateXZDistance(cameraPos, thingPos);
		float yDistance = this.calculateYDistance(cameraPos, thingPos);
		if (xzDistance < yDistance) {
			return new Distance(xzDistance, this.hidingActivationDistanceXZ, this.fullHidingDistanceXZ);
		}
		return new Distance(yDistance, this.hidingActivationDistanceY, this.fullHidingDistanceY);
	}

	private float calculateYDistance(Vec3d cameraPos, Vec3d thingPos) {
		return (float) (cameraPos.getY() - thingPos.getY());
	}

	private float calculateXZDistance(Vec3d cameraPos, Vec3d thingPos) {
		float f = (float)(cameraPos.getX() - thingPos.getX());
		float h = (float)(cameraPos.getZ() - thingPos.getZ());
		return MathHelper.sqrt(f * f + h * h);
	}

	public record Distance(float distance, double hidingActivationDistance, double fullHidingDistance) {}
}
