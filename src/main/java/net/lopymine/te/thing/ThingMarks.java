package net.lopymine.te.thing;

import lombok.*;
import org.jetbrains.annotations.Nullable;

@Setter
@Getter
public class ThingMarks<T> {

	public static final ThingMarks<Boolean> ENTITY_NAME_RENDERING = new ThingMarks<>();

	private boolean enabled;
	@Nullable
	private T value;

}
