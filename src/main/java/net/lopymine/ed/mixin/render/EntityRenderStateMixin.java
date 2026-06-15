package net.lopymine.ed.mixin.render;

import java.util.UUID;
import net.lopymine.ed.utils.render.RenderStateWithUUID;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;

@Mixin(EntityRenderState.class)
public class EntityRenderStateMixin implements RenderStateWithUUID {

	@Unique
	@Nullable
	private UUID entityDithering$uuid;

	@Override
	public void entityDithering$setUUID(@Nullable UUID uuid) {
		this.entityDithering$uuid = uuid;
	}

	@Override
	public UUID entityDithering$getUUID() {
		return this.entityDithering$uuid;
	}
}
