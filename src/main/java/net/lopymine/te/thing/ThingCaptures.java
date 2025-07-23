package net.lopymine.te.thing;

import net.lopymine.te.client.TransparentEntitiesClient;
import net.lopymine.te.transparency.TransparencyManager;
import net.lopymine.te.utils.ArgbUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.particle.Particle;
import net.minecraft.entity.Entity;

import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.*;

public class ThingCaptures<T> {

	public static final ThingCaptures<Particle> CURRENT_RENDERING_PARTICLE = new ThingCaptures<>((particle, originalArgb) -> {
		MinecraftClient client = MinecraftClient.getInstance();
		Vec3d cameraPos = client.gameRenderer.getCamera().getPos();
		Vec3d particlePos = new Vec3d(particle.x, particle.y, particle.z);
		return ArgbUtils.swapAlpha(originalArgb, TransparencyManager.getAlpha(cameraPos, particlePos));
	});

	public static final ThingCaptures<Entity> CURRENT_RENDERING_ENTITY = new ThingCaptures<>((entity, originalArgb) -> {
		MinecraftClient client = MinecraftClient.getInstance();
		ClientPlayerEntity player = client.player;
		Vec3d cameraPos = client.gameRenderer.getCamera().getPos();
		Vec3d entityPos = entity.getPos();

		if (player == null) {
			return originalArgb;
		}

		if (player.equals(entity)) {
			return TransparencyManager.getColorForYourself(originalArgb);
		}

		return ArgbUtils.swapAlpha(originalArgb, TransparencyManager.getAlpha(cameraPos, entityPos));
	});

	private final ThreadLocal<ThingContainer<T>> container = ThreadLocal.withInitial(ThingContainer::new);
	private final ColorGetter<T> colorGetter;

	public ThingCaptures(ColorGetter<T> colorGetter) {
		this.colorGetter = colorGetter;
	}

	public void set(T thing) {
		this.container.get().set(thing);
	}

	@Nullable
	public T get() {
		return this.container.get().get();
	}

	@NotNull
	public ThingContainer<T> getContainer() {
		return this.container.get();
	}

	public void setEnabled(boolean enabled) {
		this.container.get().setEnabled(enabled);
	}

	public boolean isEnabled() {
		return this.container.get().isEnabled();
	}

	public void clear() {
		this.container.get().set(null);
	}

	public int getArgbColor(int originalArgb) {
		if (!TransparentEntitiesClient.getConfig().isModEnabled()) {
			return originalArgb;
		}
		T thing = this.get();
		if (thing == null) {
			return originalArgb;
		}
		return this.colorGetter.get(thing, originalArgb);
	}

	public int getArgbColor(@Nullable T thing, int originalArgb) {
		if (!TransparentEntitiesClient.getConfig().isModEnabled()) {
			return originalArgb;
		}
		if (thing == null) {
			return originalArgb;
		}
		return this.colorGetter.get(thing, originalArgb);
	}

	@FunctionalInterface
	public interface ColorGetter<T> {

		int get(T thing, int originalArgb);

	}
}
