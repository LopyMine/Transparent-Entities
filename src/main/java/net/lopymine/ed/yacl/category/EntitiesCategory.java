package net.lopymine.ed.yacl.category;

import dev.isxander.yacl3.api.*;
import lombok.experimental.ExtensionMethod;
import net.lopymine.mossylib.yacl.api.*;
import net.lopymine.mossylib.yacl.api.SimpleOption.ButtonBuilder;
import net.lopymine.mossylib.yacl.extension.SimpleOptionExtension;
import net.lopymine.ed.EntityDithering;
import net.lopymine.ed.config.EntityDitheringConfig;
import net.lopymine.ed.config.entity.EntityIdentifier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.*;

import java.util.*;
import java.util.Map.Entry;
import net.minecraft.world.entity.EntityType;

@ExtensionMethod(SimpleOptionExtension.class)
public class EntitiesCategory {

	public static SimpleCategory get(EntityDitheringConfig defConfig, EntityDitheringConfig config) {
		SimpleCategory simpleCategory = SimpleCategory.startBuilder("entities");
		HashSet<Identifier> hideEntities = config.getHideEntities();
		HashSet<Identifier> defHideEntities = defConfig.getHideEntities();

		List<Entry<ResourceKey<EntityType<?>>, EntityType<?>>> list = new ArrayList<>(BuiltInRegistries.ENTITY_TYPE.entrySet());

		list.sort((a, b) -> {
			String A = a.getValue().getDescription().getString();
			String B = b.getValue().getDescription().getString();
			return A.compareTo(B);
		});

		List<EntityOption> entityOptions = new ArrayList<>();

		for (Entry<ResourceKey<EntityType<?>>, EntityType<?>> entry : list) {
			Identifier id = entry.getKey().identifier();
			EntityType<?> entityType = entry.getValue();
			Option<Boolean> option = createEntityOption(defHideEntities, hideEntities, id, entityType.getDescription(), simpleCategory);
			entityOptions.add(new EntityOption(option, entityType, id));
		}

		Option<Boolean> yourselfOption = createEntityOption(defHideEntities, hideEntities, EntityDitheringConfig.MY_PLAYER, simpleCategory);

		ButtonBuilder hideAll = SimpleOption.startButtonBuilder("hide_all", (screen, option) -> {
			for (EntityOption o : entityOptions) {
				o.option.requestSet(true);
			}
			yourselfOption.requestSet(true);
		}).withDescription(SimpleContent.NONE);

		ButtonBuilder hidePeaceful = SimpleOption.startButtonBuilder("hide_peaceful", (screen, option) -> {
			for (EntityOption o : entityOptions) {
				if (o.type.isAllowedInPeaceful()) {
					o.option.requestSet(true);
				}
			}
			yourselfOption.requestSet(true);
		}).withDescription(SimpleContent.NONE);

		ButtonBuilder hideAggressive = SimpleOption.startButtonBuilder("hide_aggressive", (screen, option) -> {
			for (EntityOption o : entityOptions) {
				if (!o.type.isAllowedInPeaceful()) {
					o.option.requestSet(true);
				}
			}
			yourselfOption.requestSet(true);
		}).withDescription(SimpleContent.NONE);

		simpleCategory.options(LabelOption.create(Component.literal("")));

		simpleCategory.options(hideAll);
		simpleCategory.options(hidePeaceful);
		simpleCategory.options(hideAggressive);

		for (EntityOption entityOption : entityOptions) {
			simpleCategory.options(entityOption.option);
			if (entityOption.id.getPath().toLowerCase(Locale.ROOT).equals("player")) {
				simpleCategory.options(yourselfOption);
			}
		}

		return simpleCategory;
	}

	private static Option<Boolean> createEntityOption(HashSet<Identifier> defHideEntities, HashSet<Identifier> hideEntities, Identifier id, Component name, SimpleCategory simpleCategory) {
		return createEntityOption(defHideEntities, hideEntities, EntityIdentifier.of(id, name), simpleCategory);
	}

	private static Option<Boolean> createEntityOption(HashSet<Identifier> defHideEntities, HashSet<Identifier> hideEntities, EntityIdentifier entityIdentifier, SimpleCategory simpleCategory) {
		Identifier identifier = entityIdentifier.id();

		Component name = entityIdentifier.id().getPath().equals("player") ?
				EntityDithering.text("modmenu.option.hide_entity.other_players")
				:
				entityIdentifier.name();

		return SimpleOption.<Boolean>startBuilder("hide_entity")
				.withBinding(defHideEntities.contains(identifier), () -> hideEntities.contains(identifier), (bl) -> {
					if (bl) {
						hideEntities.add(identifier);
					} else {
						hideEntities.remove(identifier);
					}
				}, true)
				.withController()
				.getOptionBuilder()
				.name(EntityDithering.text("modmenu.option.hide_entity.name", name))
				.description(
						OptionDescription.createBuilder()
							.text(EntityDithering.text("modmenu.option.hide_entity.description", name))
							.build()
				)
				.build();

	}

	private record EntityOption(Option<Boolean> option, EntityType<?> type, Identifier id) {}

}
