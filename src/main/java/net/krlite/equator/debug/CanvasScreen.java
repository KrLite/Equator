package net.krlite.equator.debug;

import net.krlite.equator.EquatorLib;
import net.krlite.equator.color.PreciseColor;
import net.krlite.equator.geometry.Node;
import net.krlite.equator.geometry.Rect;
import net.krlite.equator.math.EasingFunctions;
import net.krlite.equator.render.Equator;
import net.krlite.equator.render.sprite.IdentifierSprite;
import net.krlite.equator.util.IdentifierBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class CanvasScreen extends Screen {
	public static final IdentifierSprite FLASH = IdentifierBuilder.sprite(EquatorLib.MOD_ID, "debug", "flash");
	public static final IdentifierSprite GRAYSCALE = IdentifierBuilder.sprite(EquatorLib.MOD_ID, "debug", "grayscale");

	private final Screen parent;

	public CanvasScreen(Screen parent) {
		super(Text.literal("Canvas"));
		this.parent = parent;
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float delta) {
		renderBackground(matrixStack);
		renderCanvas(matrixStack, mouseX, mouseY, delta);
	}

	@Override
	public void renderBackground(MatrixStack matrixStack) {
		new Equator.Painter(matrixStack).overlay(PreciseColor.WHITE);
	}

	public void renderCanvas(MatrixStack matrixStack, int mouseX, int mouseY, float delta) {
		new Equator.Renderer(matrixStack, GRAYSCALE).tiledBackground(PreciseColor.WHITE, 0.5F,
				(float) EasingFunctions.sinNormal() + 1, (float) EasingFunctions.sinNormal(), (float) EasingFunctions.cosNormal());

		new Equator.Renderer(matrixStack, FLASH).renderRect(Rect.centerScreen(50).rotateByCenter(Math.toDegrees(EasingFunctions.cos())));

		new Equator.Painter(matrixStack).paintLine(Node.leftTopScreen().interpolate(Node.topScreen(), EasingFunctions.sinNormal()).tint(PreciseColor.CYAN),
				Node.rightBottomScreen().interpolate(Node.bottomScreen(), EasingFunctions.cosNormal()).tint(PreciseColor.MAGENTA), 5);
	}

	@Override
	public void close() {
		if (client != null) client.setScreen(parent);
	}
}
