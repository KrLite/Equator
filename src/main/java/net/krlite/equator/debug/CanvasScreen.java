package net.krlite.equator.debug;

import net.krlite.equator.EquatorLib;
import net.krlite.equator.render.sprite.IdentifierSprite;
import net.krlite.equator.util.IdentifierBuilder;
import net.krlite.equator.util.Timer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;

public class CanvasScreen extends Screen {
	public static final IdentifierSprite FLASH = IdentifierBuilder.sprite(EquatorLib.MOD_ID, "debug", "flash");
	public static final IdentifierSprite GRAYSCALE = IdentifierBuilder.sprite(EquatorLib.MOD_ID, "debug", "grayscale");
	public static final IdentifierSprite BUBBLE = IdentifierBuilder.sprite(EquatorLib.MOD_ID, "debug", "bubble");

	private final Screen parent;
	private final Timer bounce = new Timer(2000), swing = new Timer(1900);

	public CanvasScreen(Screen parent) {
		super(new LiteralText("Canvas"));
		this.parent = parent;
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float delta) {
		renderCanvas(matrixStack, mouseX, mouseY, delta);
	}

	public void renderCanvas(MatrixStack matrixStack, int mouseX, int mouseY, float delta) {
	}

	@Override
	public void close() {
		if (client != null) client.setScreen(parent);
	}
}
