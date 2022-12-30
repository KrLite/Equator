package net.krlite.equator.mixin.client;

import net.krlite.equator.base.PreciseColor;
import net.krlite.equator.base.geometry.Node;
import net.krlite.equator.base.geometry.Rect;
import net.krlite.equator.base.geometry.TintedRect;
import net.krlite.equator.render.Equator;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.text.DecimalFormat;

@Mixin(TitleScreen.class)
public class TitleScreenMixin extends Screen {
	protected TitleScreenMixin(Text title) {
		super(title);
	}

	@Inject(method = "render", at = @At("TAIL"))
	private void render(MatrixStack matrixStack, int mouseX, int mouseY, float delta, CallbackInfo ci) {
		double current = Math.abs(System.currentTimeMillis() % 6000 - 3000) / 3000.0;
		new Equator.Drawer(matrixStack)
				//.tintedRect(PreciseColor.WHITE)
				.rectGradiantFromMiddleWithScissor(
						new TintedRect(new Rect(
								MinecraftClient.getInstance().getWindow().getScaledWidth() / 2.0 - 100, MinecraftClient.getInstance().getWindow().getScaledHeight() / 2.0 - 100,
								200, 200
						), PreciseColor.TRANSPARENT, PreciseColor.CYAN, PreciseColor.CYAN, PreciseColor.TRANSPARENT),
						new TintedRect(new Rect(
								MinecraftClient.getInstance().getWindow().getScaledWidth() / 2.0 - 25, MinecraftClient.getInstance().getWindow().getScaledHeight() / 2.0 - 25,
								50, 50
						), PreciseColor.BLACK), current
				);
		drawCenteredText(matrixStack, this.textRenderer, Text.literal(new DecimalFormat("#.00").format(current)), width / 2, height / 2, 0x000000);
	}
}
