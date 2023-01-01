package net.krlite.equator.mixin.client;

import net.krlite.equator.base.geometry.Node;
import net.krlite.equator.base.geometry.Rect;
import net.krlite.equator.math.EasingFunctions;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class TitleScreenMixin {
	@Inject(method = "render", at = @At("TAIL"))
	private void render(MatrixStack matrixStack, int mouseX, int mouseY, float delta, CallbackInfo ci) {
		new Rect(
				new Node(0, 0),
				new Node(0, MinecraftClient.getInstance().getWindow().getScaledHeight() / 2.0 + 30 * EasingFunctions.sin()),
				new Node(MinecraftClient.getInstance().getWindow().getScaledWidth(), MinecraftClient.getInstance().getWindow().getScaledHeight() / 2.0 - 30 * EasingFunctions.sin()),
				new Node(MinecraftClient.getInstance().getWindow().getScaledWidth(), 0)
		).missingTexture(matrixStack).rotate(Rect.full().center(), 180).missingTexture(matrixStack);
	}
}
