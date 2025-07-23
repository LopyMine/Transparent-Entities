package net.lopymine.te.gui;

import lombok.*;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ButtonTextures;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;

import net.lopymine.te.TransparentEntities;
import net.lopymine.te.client.TransparentEntitiesClient;
import net.lopymine.te.config.TransparentEntitiesConfig;
import net.lopymine.te.config.data.EntityData;

import java.util.*;

@Getter
@Setter
public class FavoriteButtonWidget extends TexturedButtonWidget {

	private static final ButtonTextures TEXTURES = new ButtonTextures(
			TransparentEntities.id("favorite_button_enabled"),
			TransparentEntities.id("favorite_button_disabled"),
			TransparentEntities.id("favorite_button_enabled_hovered"),
			TransparentEntities.id("favorite_button_disabled_hovered")
	);

	private boolean enabled;

	public FavoriteButtonWidget(UUID uuid, String name) {
		super(0, 0, 20, 20, TEXTURES, (button) -> {
			if (!(button instanceof FavoriteButtonWidget widget)) {
				return;
			}
			TransparentEntitiesConfig config = TransparentEntitiesClient.getConfig();
			HashMap<UUID, EntityData> favoriteEntities = config.getFavoriteEntities();
			EntityData playerData = new EntityData(true, name, uuid);

			if (widget.isEnabled()) {
				favoriteEntities.put(playerData.getUuid(), playerData);
			} else {
				favoriteEntities.remove(uuid);
			}

			config.save();
		});
	}

	public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
		Identifier identifier = this.textures.get(this.isEnabled(), this.isHovered());
		context.drawGuiTexture(
				/*? if >=1.21.6 {*/ /*net.minecraft.client.gl.RenderPipelines.GUI_TEXTURED,
				*//*?} elif >=1.21.2 {*/ net.minecraft.client.render.RenderLayer::getGuiTextured,
				 /*?}*/
				identifier,
				this.getX(),
				this.getY(),
				this.width,
				this.height
		);
	}

	@Override
	public void onPress() {
		this.enabled = !this.enabled;
		super.onPress();
	}
}
