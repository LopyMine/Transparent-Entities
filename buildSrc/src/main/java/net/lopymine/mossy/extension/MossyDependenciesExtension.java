package net.lopymine.mossy.extension;

import lombok.Getter;
import org.gradle.api.tasks.Input;

@Getter
public class MossyDependenciesExtension {

	@Input
	String minecraft;

	@Input
	String mappings;

	@Input
	String fabricApi;

	@Input
	String fabricLoader;

	@Input
	String lombok;
}
