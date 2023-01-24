package net.krlite.equator.debug;

import net.krlite.equator.EquatorLib;
import net.krlite.equator.color.PreciseColor;
import net.krlite.equator.math.EasingFunctions;
import net.krlite.equator.render.Equator;
import net.krlite.equator.render.sprite.IdentifierSprite;
import net.krlite.equator.util.IdentifierBuilder;
import net.krlite.equator.util.QuaternionAdapter;
import net.krlite.equator.util.SystemClock;
import net.krlite.equator.util.Timer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import org.joml.Quaterniond;

public class CanvasScreen extends Screen {
	public static final IdentifierSprite FLASH = IdentifierBuilder.sprite(EquatorLib.MOD_ID, "debug", "flash");
	public static final IdentifierSprite GRAYSCALE = IdentifierBuilder.sprite(EquatorLib.MOD_ID, "debug", "grayscale");
	public static final IdentifierSprite BUBBLE = IdentifierBuilder.sprite(EquatorLib.MOD_ID, "debug", "bubble");

	private final Screen parent;
	private final Timer bounce = new Timer(2000), swing = new Timer(1900);

	public CanvasScreen(Screen parent) {
		super(Text.literal("Canvas"));
		this.parent = parent;
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float delta) {
		renderCanvas(matrixStack, mouseX, mouseY, delta);
	}

	public void renderCanvas(MatrixStack matrixStack, int mouseX, int mouseY, float delta) {
		new Equator.Renderer(matrixStack, BUBBLE).tiledBackground(PreciseColor.WHITE, 0.5F,
				3, SystemClock.queueElapsed() * 0.01F % 100 / 100, SystemClock.queueElapsed() * 0.01F % 175 / 175);

		/*
		new Equator.Renderer(matrixStack, FLASH).renderRect(Rect.centerScreen(50).rotateByCenter(Math.toDegrees(EasingFunctions.cos())));

		new Equator.Painter(matrixStack).paintLine(Node.leftTopScreen().interpolate(Node.topScreen(), EasingFunctions.sinNormal()).tint(PreciseColor.CYAN),
				Node.rightBottomScreen().interpolate(Node.bottomScreen(), EasingFunctions.cosNormal()).tint(PreciseColor.MAGENTA), 5);

		new Equator.Painter(matrixStack).rectShadow(Rect.centerScreen(200).tint(PreciseColor.TRANSPARENT),
				Rect.centerScreen(50).tint(PreciseColor.MAGENTA), EasingFunctions.cosNormal());

		 */

		bounce.run(() -> {
			bounce.reset();
			swing.reset();
		});

		EasingFunctions.Combined bouncing = new EasingFunctions.Combined()
												  .append(EasingFunctions.Quadratic::easeOut, 5).appendNegate(EasingFunctions.Bounce::easeOut, 15);

		EasingFunctions.Concurred swinging = new EasingFunctions.Concurred(EasingFunctions.Linear::ease,
				(p, o, s, d) -> EasingFunctions.Back.easeOut(p, o, -s, d));

		Equator.BlockModel blockModel = new Equator.BlockModel(Registries.BLOCK.get(IdentifierBuilder.id("minecraft", "diamond_block")).getDefaultState());
		Quaterniond quaternion = QuaternionAdapter.fromEulerDeg(swinging.apply(swing, -6), swinging.apply(swing, 17), swinging.apply(swing, 34));

		blockModel.renderCentered(MinecraftClient.getInstance().getWindow().getScaledWidth() / 2.0, MinecraftClient.getInstance().getWindow().getScaledHeight() / 2.0 - bouncing.apply(bounce, 50), quaternion);
	}

	@Override
	public void close() {
		if (client != null) client.setScreen(parent);
	}
}
