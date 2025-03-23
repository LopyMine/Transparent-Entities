package net.lopymine.te.utils.yacl;

import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.YetAnotherConfigLib.Builder;

public interface BetterYACLScreenBuilder {

	static Builder startBuilder() {
		return ((BetterYACLScreenBuilder) YetAnotherConfigLib.createBuilder()).transparentEntities$enable();
	}

	Builder transparentEntities$enable();
}
