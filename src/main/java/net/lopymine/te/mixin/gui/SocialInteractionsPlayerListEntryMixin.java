package net.lopymine.te.mixin.gui;

import com.llamalad7.mixinextras.injector.wrapoperation.*;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.multiplayer.*;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.*;
import net.minecraft.text.MutableText;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.lopymine.te.TransparentEntities;
import net.lopymine.te.client.TransparentEntitiesClient;
import net.lopymine.te.gui.FavoriteButtonWidget;

import java.util.*;
import org.jetbrains.annotations.Nullable;

@Mixin(SocialInteractionsPlayerListEntry.class)
public class SocialInteractionsPlayerListEntryMixin {

	@Shadow @Final private List<ClickableWidget> buttons;
	@Shadow @Final private UUID uuid;
	@Shadow @Final private String name;
	@Unique
	@Nullable
	private FavoriteButtonWidget favoriteButton;

	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/multiplayer/SocialInteractionsPlayerListEntry;setShowButtonVisible(Z)V"), method = "<init>")
	private void init(CallbackInfo ci) {
		this.favoriteButton = new FavoriteButtonWidget(this.uuid, this.name);
		MutableText text = TransparentEntities.text("gui.favorite_button.tooltip");
		this.favoriteButton.setTooltip(Tooltip.of(text, text));
		this.favoriteButton.setEnabled(TransparentEntitiesClient.getConfig().getFavoriteEntities().containsKey(this.uuid));
		this.buttons.add(this.favoriteButton);
	}

	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/ButtonWidget;setX(I)V", ordinal = 0), method = "render")
	private void updatePosX(ButtonWidget instance, int x, Operation<Void> original) {
		original.call(instance, x);
		if (this.favoriteButton == null) {
			return;
		}
		this.favoriteButton.setX(x - 20 - 4);
	}

	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/ButtonWidget;setY(I)V", ordinal = 0), method = "render")
	private void updatePosY(ButtonWidget instance, int y, Operation<Void> original) {
		original.call(instance, y);
		if (this.favoriteButton == null) {
			return;
		}
		this.favoriteButton.setY(y);
	}

	@WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/ButtonWidget;render(Lnet/minecraft/client/gui/DrawContext;IIF)V", ordinal = 0), method = "render")
	private void updatePosX(ButtonWidget instance, DrawContext drawContext, int x, int y, float tickDelta, Operation<Void> original) {
		original.call(instance, drawContext, x, y, tickDelta);
		if (this.favoriteButton == null) {
			return;
		}
		this.favoriteButton.render(drawContext, x, y, tickDelta);
	}

}
