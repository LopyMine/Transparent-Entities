package net.lopymine.ed.gui;

//import lombok.*;
//import net.lopymine.ed.EntityDithering;
//import net.lopymine.ed.config.EntityDitheringConfig;
//import net.minecraft.client.gui.GuiGraphicsExtractor;
//import net.minecraft.client.gui.components.WidgetSprites;
//import net.minecraft.client.gui.components.ImageButton;
//import net.minecraft.client.input.InputWithModifiers;
//import net.minecraft.resources.Identifier;
//
//import net.lopymine.ed.client.EntityDitheringClient;
//import net.lopymine.ed.config.data.EntityData;
//
//import java.util.*;
//
//@Getter
//@Setter
//public class FavoriteButtonWidget extends ImageButton {
//
//	private static final WidgetSprites TEXTURES = new WidgetSprites(
//			EntityDithering.id("favorite_button_enabled"),
//			EntityDithering.id("favorite_button_disabled"),
//			EntityDithering.id("favorite_button_enabled_hovered"),
//			EntityDithering.id("favorite_button_disabled_hovered")
//	);
//
//	private boolean enabled;
//
//	public FavoriteButtonWidget(UUID uuid, String name) {
//		super(0, 0, 20, 20, TEXTURES, (button) -> {
//			if (!(button instanceof FavoriteButtonWidget widget)) {
//				return;
//			}
//			EntityDitheringConfig config = EntityDitheringClient.getConfig();
//			HashMap<UUID, EntityData> favoriteEntities = config.getFavoriteEntities();
//			EntityData playerData = new EntityData(true, name, uuid);
//
//			if (widget.isEnabled()) {
//				favoriteEntities.put(playerData.getUuid(), playerData);
//			} else {
//				favoriteEntities.remove(uuid);
//			}
//
//			config.save();
//		});
//	}
//
//	@Override
//	public void extractContents(GuiGraphicsExtractor context, int mouseX, int mouseY, float delta) {
//		Identifier identifier = this.sprites.get(this.isEnabled(), this.isHovered());
//		context.blitSprite(
//				/*? if >=1.21.6 {*/ net.minecraft.client.renderer.RenderPipelines.GUI_TEXTURED,
//				/*?} elif >=1.21.2 {*/ /*net.minecraft.client.render.RenderLayer::getGuiTextured,
//				 *//*?}*/
//				identifier,
//				this.getX(),
//				this.getY(),
//				this.width,
//				this.height
//		);
//	}
//
//	@Override
//	public void onPress(InputWithModifiers modifiers) {
//		this.enabled = !this.enabled;
//		super.onPress(modifiers);
//	}
//}
