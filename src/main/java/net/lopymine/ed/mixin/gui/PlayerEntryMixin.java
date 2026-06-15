package net.lopymine.ed.mixin.gui;

import net.minecraft.client.gui.screens.social.PlayerEntry;
import org.spongepowered.asm.mixin.*;

@Mixin(PlayerEntry.class)
public class PlayerEntryMixin {

//	@Shadow @Final private List<AbstractWidget> children;
//	@Shadow @Final private UUID id;
//	@Shadow @Final private String playerName;
//	@Unique
//	@Nullable
//	private FavoriteButtonWidget favoriteButton;
//
//	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/social/PlayerEntry;updateHideAndShowButton(Z)V"), method = "<init>")
//	private void init(CallbackInfo ci) {
//		this.favoriteButton = new FavoriteButtonWidget(this.id, this.playerName);
//		MutableComponent text = EntityDithering.text("gui.favorite_button.tooltip");
//		this.favoriteButton.setTooltip(Tooltip.create(text, text));
//		this.favoriteButton.setEnabled(EntityDitheringClient.getConfig().getFavoriteEntities().containsKey(this.id));
//		this.children.add(this.favoriteButton);
//	}
//
//	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/Button;setX(I)V", ordinal = 0), method = "extractContent")
//	private void updatePosX(Button instance, int x, Operation<Void> original) {
//		original.call(instance, x);
//		if (this.favoriteButton == null) {
//			return;
//		}
//		this.favoriteButton.setX(x - 20 - 4);
//	}
//
//	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/Button;setY(I)V", ordinal = 0), method = "extractContent")
//	private void updatePosY(Button instance, int y, Operation<Void> original) {
//		original.call(instance, y);
//		if (this.favoriteButton == null) {
//			return;
//		}
//		this.favoriteButton.setY(y);
//	}
//
//	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/Button;extractRenderState(Lnet/minecraft/client/gui/GuiGraphicsExtractor;IIF)V", ordinal = 0), method = "extractContent")
//	private void updatePosX(Button instance, GuiGraphicsExtractor drawContext, int x, int y, float tickDelta, Operation<Void> original) {
//		original.call(instance, drawContext, x, y, tickDelta);
//		if (this.favoriteButton == null) {
//			return;
//		}
//		this.favoriteButton.extractContents(drawContext, x, y, tickDelta);
//	}

}
