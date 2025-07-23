package net.lopymine.te.thing;

import lombok.*;
import net.minecraft.entity.Entity;

import org.jetbrains.annotations.Nullable;

@Getter
@Setter
public class ThingContainer<T> {

	@Nullable
	private T thing;
	private boolean enabled = true;

	public ThingContainer() {
		this.thing = null;
	}

	public void set(@Nullable T entity) {
		if (!this.isEnabled()) {
			return;
		}
		this.thing = entity;
	}

	@Nullable
	public T get() {
		return this.isEnabled() ? this.thing : null;
	}
}
