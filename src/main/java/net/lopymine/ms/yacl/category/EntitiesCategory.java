package net.lopymine.ms.yacl.category;

import dev.isxander.yacl3.api.*;
import lombok.experimental.ExtensionMethod;
import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.*;
import net.minecraft.util.*;

import net.lopymine.ms.MoreSpace;
import net.lopymine.ms.config.MoreSpaceConfig;
import net.lopymine.ms.yacl.custom.base.*;
import net.lopymine.ms.yacl.custom.extension.SimpleOptionExtension;

import java.lang.reflect.*;
import java.util.*;
import java.util.Map.Entry;

@ExtensionMethod(SimpleOptionExtension.class)
public class EntitiesCategory {

	public static ConfigCategory get(MoreSpaceConfig defConfig, MoreSpaceConfig config) {
		SimpleCategory simpleCategory = SimpleCategory.startBuilder("entities");
		HashSet<Identifier> hideEntities = config.getHideEntities();
		HashSet<Identifier> defHideEntities = defConfig.getHideEntities();

		simpleCategory.options(SimpleOption.label("empty"));
		simpleCategory.options(SimpleOption.label("entities.info"));
		simpleCategory.options(SimpleOption.label("entities.info.beta"));
		simpleCategory.options(SimpleOption.label("empty"));

		List<Entry<RegistryKey<EntityType<?>>, EntityType<?>>> list = new ArrayList<>(Registries.ENTITY_TYPE.getEntrySet());

		list.sort((a, b) -> {
			Identifier idA = a.getKey().getValue();
			Identifier idB = b.getKey().getValue();
			return idA.compareTo(idB);
		});

		for (Entry<RegistryKey<EntityType<?>>, EntityType<?>> entry : list) {
			Identifier id = entry.getKey().getValue();
			EntityType<?> entityType = entry.getValue();

			Option<Boolean> option = SimpleOption.<Boolean>startBuilder("hide_entity")
					.withBinding(defHideEntities.contains(id), () -> hideEntities.contains(id), (bl) -> {
						if (bl) {
							hideEntities.add(id);
						} else {
							hideEntities.remove(id);
						}
					}, true)
					.withController()
					.getOptionBuilder()
					.name(MoreSpace.text("modmenu.option.hide_entity.name", entityType.getName()))
					.description(
							OptionDescription.createBuilder()
								.text(MoreSpace.text("modmenu.option.hide_entity.description", entityType.getName()))
								.build()
					)
					.build();

			simpleCategory.options(option);
		}

		return simpleCategory.build();
	}

}
