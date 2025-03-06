package net.lopymine.mossy.tasks;

import lombok.experimental.ExtensionMethod;
import org.gradle.api.*;
import org.gradle.api.tasks.*;

import net.lopymine.mossy.MossyPlugin;

import java.io.*;

@ExtensionMethod(MossyPlugin.class)
public class RegenerateRunConfigsTask extends Delete {

	@TaskAction
	public void regenerate() {
		Project project = this.getProject();
		delete(project.getRootFile(".idea/runConfigurations"));
	}
}
