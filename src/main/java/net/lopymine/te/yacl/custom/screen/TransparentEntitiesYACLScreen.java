package net.lopymine.te.yacl.custom.screen;

import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.utils.OptionUtils;
import dev.isxander.yacl3.gui.*;
import lombok.Getter;
import net.minecraft.client.gui.screen.Screen;

@Getter
public class TransparentEntitiesYACLScreen extends YACLScreen {

	public TransparentEntitiesYACLScreen(YetAnotherConfigLib config, Screen parent) {
		super(config, parent);
	}

	@Override
	public void finishOrSave() {
		super.finishOrSave();
		super.close();
	}

	@Override
	public void cancelOrReset() {
		OptionUtils.forEachOptions(this.config, Option::requestSetDefault);
	}

	@Override
	public void close() {
		super.finishOrSave();
		super.close();
	}

	@Override
	public boolean shouldCloseOnEsc() {
		return true;
	}
}
