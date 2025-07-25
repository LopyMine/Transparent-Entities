package net.lopymine.te.config.distance;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import lombok.*;
import net.lopymine.te.utils.CodecUtils;
import net.minecraft.util.math.*;
import org.jetbrains.annotations.Nullable;
import static net.lopymine.te.utils.CodecUtils.option;

@Getter
@Setter
@AllArgsConstructor
public class TransparencyDistance {

	public static final Codec<TransparencyDistance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			option("hiding_activation_distance_xz", 4.2D, Codec.DOUBLE, TransparencyDistance::getHidingActivationDistanceXZ),
			option("hiding_activation_distance_y", 4.2D, Codec.DOUBLE, TransparencyDistance::getHidingActivationDistanceY),
			option("full_hiding_distance", 3.0D, Codec.DOUBLE, TransparencyDistance::getFullHidingDistance)
	).apply(instance, TransparencyDistance::new));

	private double hidingActivationDistanceXZ;
	private double hidingActivationDistanceY;
	private double fullHidingDistance;

	public static TransparencyDistance getNewInstance() {
		return CodecUtils.parseNewInstanceHacky(CODEC);
	}

	@Nullable
	public Distance getDistance(Vec3d cameraPos, Vec3d thingPos) {
		float xzDistance = this.calculateXZDistance(cameraPos, thingPos);
		float yDistance = this.calculateYDistance(cameraPos, thingPos);

		if (xzDistance > this.hidingActivationDistanceXZ && yDistance > this.hidingActivationDistanceY) {
			return null;
		}

		float maxXZ = (float) Math.sqrt(this.hidingActivationDistanceXZ * this.hidingActivationDistanceXZ * 2);
		return new Distance(xzDistance, yDistance, maxXZ, (float) this.hidingActivationDistanceY, (float) this.fullHidingDistance);
	}

	private float calculateYDistance(Vec3d cameraPos, Vec3d thingPos) {
		return Math.abs((float) (cameraPos.getY() - thingPos.getY()));
	}

	private float calculateXZDistance(Vec3d cameraPos, Vec3d thingPos) {
		float dx = (float)(cameraPos.getX() - thingPos.getX());
		float dz = (float)(cameraPos.getZ() - thingPos.getZ());
		return MathHelper.sqrt(dx * dx + dz * dz);
	}

	public record Distance(float actualDistanceXZ, float actualDistanceY, float maxXZ, float maxY, float fullHidingDistance) {}

}
