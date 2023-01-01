package net.krlite.equator.debug;

import net.krlite.equator.base.color.PreciseColor;
import net.krlite.equator.base.color.PreciseColors;
import net.krlite.equator.base.geometry.Rect;
import net.krlite.equator.base.geometry.TintedRect;
import net.krlite.equator.util.Pusher;
import net.krlite.equator.util.Timer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.glfw.GLFW;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Iterator;

public class ColorspaceScreen extends Screen {
	private final Iterator<Field> fieldIterator;
	private Timer timer;
	private final Pusher pusher = new Pusher();
	private PreciseColor current;
	private String currentName;
	private boolean isPaused, isSpeedUp;

	public ColorspaceScreen() {
		this(350);
	}

	public ColorspaceScreen(long speed) {
		super(Text.literal("Colorspace"));
		this.fieldIterator = Arrays.stream(PreciseColors.class.getFields()).iterator();
		this.timer = new Timer(501 - MathHelper.clamp(speed, 1, 500));
		this.current = PreciseColor.BLACK;
		this.currentName = "";
		this.isPaused = this.isSpeedUp = false;
	}

	@Override
	public void tick() {
		super.tick();
		if (!isPaused && (timer.isDone() || (isSpeedUp && timer.queueAsPercentage() > 0.25))) pusher.push(() -> timer = timer.reset());
	}

	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
		if (keyCode == GLFW.GLFW_KEY_SPACE) isPaused = !isPaused;
		if (keyCode == GLFW.GLFW_KEY_LEFT_SHIFT) isSpeedUp = true;
		return super.keyPressed(keyCode, scanCode, modifiers);
	}

	@Override
	public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
		if (keyCode == GLFW.GLFW_KEY_LEFT_SHIFT) isSpeedUp = false;
		return super.keyReleased(keyCode, scanCode, modifiers);
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		if (mouseY <= MinecraftClient.getInstance().getWindow().getScaledHeight() - 16) pusher.push();
		return super.mouseClicked(mouseX, mouseY, button);
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float delta) {
		if (!fieldIterator.hasNext()) close();

		super.renderBackground(matrixStack);

		if (pusher.pull()) {
			Field field = fieldIterator.next();

			if (field.getType() != PreciseColor.class)  fieldIterator.remove();

			try {
				current = (PreciseColor) field.get(null);
				currentName = field.getName();
			} catch (IllegalAccessException exception) {
				close();
			}
		}

		new TintedRect(current).draw(matrixStack);
		new TintedRect(
				new Rect(0, MinecraftClient.getInstance().getWindow().getScaledHeight() - 16, MinecraftClient.getInstance().getWindow().getScaledWidth(), 16),
				PreciseColor.BLACK).draw(matrixStack
		);

		drawTextWithShadow(
				matrixStack, textRenderer,
				Text.literal(currentName), 2,
				MinecraftClient.getInstance().getWindow().getScaledHeight() - 12, 0xFFFFFF
		);

		drawTextWithShadow(
				matrixStack, textRenderer,
				Text.literal(current.toPerfectString()), MinecraftClient.getInstance().getWindow().getScaledWidth() - 2 - textRenderer.getWidth(current.toPerfectString()),
				MinecraftClient.getInstance().getWindow().getScaledHeight() - 12, 0xFFFFFF
		);

		if (isSpeedUp && !isPaused) drawCenteredText(matrixStack, textRenderer, Text.literal("§b×4"), MinecraftClient.getInstance().getWindow().getScaledWidth() / 2, MinecraftClient.getInstance().getWindow().getScaledHeight() - 12, 0xFFFFFF);

		if (isPaused) drawCenteredText(matrixStack, textRenderer, Text.literal("PAUSED"), MinecraftClient.getInstance().getWindow().getScaledWidth() / 2, MinecraftClient.getInstance().getWindow().getScaledHeight() - 12, 0xFFFFFF);
	}
}
