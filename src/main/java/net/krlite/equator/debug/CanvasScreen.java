package net.krlite.equator.debug;

import net.krlite.equator.EquatorLib;
import net.krlite.equator.animator.ColorAnimator;
import net.krlite.equator.color.PreciseColor;
import net.krlite.equator.color.PreciseColors;
import net.krlite.equator.geometry.Node;
import net.krlite.equator.geometry.Rect;
import net.krlite.equator.math.EasingFunctions;
import net.krlite.equator.render.Equator;
import net.krlite.equator.render.sprite.IdentifierSprite;
import net.krlite.equator.util.IdentifierBuilder;
import net.krlite.equator.util.Pusher;
import net.krlite.equator.util.SystemClock;
import net.krlite.equator.util.Timer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class CanvasScreen extends Screen {
	public static final IdentifierSprite FLASH = IdentifierBuilder.sprite(EquatorLib.MOD_ID, "debug", "flash");
	public static final IdentifierSprite GRAYSCALE = IdentifierBuilder.sprite(EquatorLib.MOD_ID, "debug", "grayscale");

	private final Screen parent;
	private final Timer bounce = new Timer(1500), swing = new Timer(1875), jump = new Timer(500);
	private final Pusher b = new Pusher(), s = new Pusher(), j = new Pusher();

	public CanvasScreen(Screen parent) {
		super(Text.literal("Canvas"));
		this.parent = parent;
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float delta) {
		renderCanvas(matrixStack, mouseX, mouseY, delta);
	}

	public void renderCanvas(MatrixStack matrixStack, int mouseX, int mouseY, float delta) {
		new Equator.Renderer(matrixStack, GRAYSCALE).tiledBackground(PreciseColor.WHITE, 0.5F,
				(float) EasingFunctions.sinNormal() + 1, (float) EasingFunctions.sinNormal(), (float) EasingFunctions.cosNormal());

		/*
		new Equator.Renderer(matrixStack, FLASH).renderRect(Rect.centerScreen(50).rotateByCenter(Math.toDegrees(EasingFunctions.cos())));

		new Equator.Painter(matrixStack).paintLine(Node.leftTopScreen().interpolate(Node.topScreen(), EasingFunctions.sinNormal()).tint(PreciseColor.CYAN),
				Node.rightBottomScreen().interpolate(Node.bottomScreen(), EasingFunctions.cosNormal()).tint(PreciseColor.MAGENTA), 5);

		new Equator.Painter(matrixStack).rectShadow(Rect.centerScreen(200).tint(PreciseColor.TRANSPARENT),
				Rect.centerScreen(50).tint(PreciseColor.MAGENTA), EasingFunctions.cosNormal());

		 */

		jump.run(() -> j.push(() -> b.pull(bounce::reset)));
		swing.run(s::push);
		bounce.run(() -> s.pull(swing::reset));
		bounce.run(() -> b.push(() -> j.pull(jump::reset)));

		Rect square = Rect.centerScreen(35).rotateByBottom(EasingFunctions.Back.easeOut(swing, 34) + EasingFunctions.Linear.ease(swing, -34));

		if (jump.isPresent()) {
			square = square.shift(0, EasingFunctions.Quadratic.easeOut(jump, -50));
		} else if (bounce.isPresent()) {
			square = square.shift(0, EasingFunctions.Bounce.easeOut(bounce, 50) - 50);
		}

		new Equator.Painter(matrixStack).paintRect(square.tint(PreciseColor.MAGENTA));
	}

	@Override
	public void close() {
		if (client != null) client.setScreen(parent);
	}
}
