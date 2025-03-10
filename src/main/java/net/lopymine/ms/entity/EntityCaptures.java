package net.lopymine.ms.entity;

import net.minecraft.entity.Entity;

import org.jetbrains.annotations.Nullable;

public class EntityCaptures {

	public static final EntityCaptures MAIN = new EntityCaptures();

	private final ThreadLocal<EntityContainer> container = ThreadLocal.withInitial(EntityContainer::new);

	public void setEntity(Entity entity) {
		this.container.get().setEntity(entity);
	}

	@Nullable
	public Entity getEntity() {
		return this.container.get().getEntity();
	}

	public void setEnabled(boolean enabled) {
		this.container.get().setEnabled(enabled);
	}

	public void clearEntity() {
		this.container.get().setEntity(null);
	}
}
