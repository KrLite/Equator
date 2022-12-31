package net.krlite.equator.test;

import net.krlite.equator.base.PreciseColor;
import net.krlite.equator.base.geometry.Node;
import net.krlite.equator.base.geometry.Rect;
import net.krlite.equator.base.geometry.TintedRect;
import net.krlite.equator.render.Equator;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.text.DecimalFormat;

public class TestScreen extends Screen {
	public TestScreen() {
		super(Text.literal("Test Screen"));
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float delta) {
		new Equator.Drawer(matrixStack)
				.tintedRect(new TintedRect(PreciseColor.WHITE))
				.tintedRect(new TintedRect(new Rect(0, MinecraftClient.getInstance().getWindow().getScaledHeight() - 16, MinecraftClient.getInstance().getWindow().getScaledWidth(), 16), PreciseColor.BLACK));

		Rect rect = new Rect(
				MinecraftClient.getInstance().getWindow().getScaledWidth() / 2.0 - 25,
				MinecraftClient.getInstance().getWindow().getScaledHeight() / 2.0 - 25,
				50, 50
		).shiftToCenter(new Node(mouseX, mouseY));

		new TintedRect(rect.shiftToCenter(new Node(mouseX, mouseY)), PreciseColor.MAGENTA)
				.drawFocusedShadow(matrixStack, PreciseColor.MAGENTA.dimmer());
		drawCenteredText(matrixStack, this.textRenderer, Text.literal(rect.center().getClockwiseDegree(new Rect().center()) + " Clockwise Degree"), width / 2, height - 12, PreciseColor.WHITE.toColor().getRGB());
	}
}
