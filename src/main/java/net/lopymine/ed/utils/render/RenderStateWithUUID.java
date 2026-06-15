package net.lopymine.ed.utils.render;

import java.util.UUID;
import org.jetbrains.annotations.Nullable;

public interface RenderStateWithUUID {

	void entityDithering$setUUID(@Nullable UUID uuid);

	@Nullable UUID entityDithering$getUUID();

}
