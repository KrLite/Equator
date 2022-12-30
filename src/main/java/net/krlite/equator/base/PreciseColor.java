package net.krlite.equator.base;

import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.Arrays;
import java.util.Objects;

public class PreciseColor {
	public static final PreciseColor WHITE = new PreciseColor(1, 1, 1, 1);
	public static final PreciseColor BLACK = new PreciseColor(0, 0, 0, 1);
	public static final PreciseColor RED = new PreciseColor(1, 0, 0, 1);
	public static final PreciseColor GREEN = new PreciseColor(0, 1, 0, 1);
	public static final PreciseColor BLUE = new PreciseColor(0, 0, 1, 1);
	public static final PreciseColor YELLOW = new PreciseColor(1, 1, 0, 1);
	public static final PreciseColor MAGENTA = new PreciseColor(1, 0, 1, 1);
	public static final PreciseColor CYAN = new PreciseColor(0, 1, 1, 1);
	public static final PreciseColor TRANSPARENT = new PreciseColor(0, 0, 0, 0, true);

	private static double clampValue(double value) {
		return MathHelper.clamp(value, 0, 1);
	}

	private static int clampValue(int value) {
		return MathHelper.clamp(value, 0, 255);
	}

	private static double blendValue(double first, double second, double ratio) {
		return first * (1 - clampValue(ratio)) + second * clampValue(ratio);
	}

	public static PreciseColor average(@NotNull PreciseColor... preciseColors) {
		return new PreciseColor(
				Arrays.stream(preciseColors).mapToDouble(PreciseColor::red).average().orElse(0),
				Arrays.stream(preciseColors).mapToDouble(PreciseColor::green).average().orElse(0),
				Arrays.stream(preciseColors).mapToDouble(PreciseColor::blue).average().orElse(0),
				Arrays.stream(preciseColors).mapToDouble(PreciseColor::alpha).average().orElse(0)
		);
	}

	public static PreciseColor of(@Nullable Color color) {
		if (color == null) return TRANSPARENT;
		return new PreciseColor(color.getRed() / 255.0, color.getGreen() / 255.0, color.getBlue() / 255.0, color.getAlpha() / 255.0);
	}

	private final double r, g, b, a;
	private final boolean transparent;

	protected PreciseColor(double r, double g, double b, double a, boolean transparent) {
		this.r = clampValue(r);
		this.g = clampValue(g);
		this.b = clampValue(b);
		this.a = clampValue(a);
		this.transparent = transparent;
	}

	public PreciseColor(double r, double g, double b, double a) {
		this(r, g, b, a, false);
	}

	public PreciseColor(double r, double g, double b) {
		this(r, g, b, 1.0);
	}

	public double red() {
		return r;
	}

	public double green() {
		return g;
	}

	public double blue() {
		return b;
	}

	public double alpha() {
		return a;
	}

	public int redInt() {
		return clampValue((int) Math.round(r * 255));
	}

	public int greenInt() {
		return clampValue((int) Math.round(g * 255));
	}

	public int blueInt() {
		return clampValue((int) Math.round(b * 255));
	}

	public int alphaInt() {
		return clampValue((int) Math.round(a * 255));
	}

	public float redFloat() {
		return (float) clampValue(r);
	}

	public float greenFloat() {
		return (float) clampValue(g);
	}

	public float blueFloat() {
		return (float) clampValue(b);
	}

	public float alphaFloat() {
		return (float) clampValue(a);
	}

	public Color toColor() {
		return new Color((float) r, (float) g, (float) b, (float) a);
	}

	public boolean hasColor() {
		return !transparent;
	}

	public PreciseColor orElse(PreciseColor fallback) {
		return hasColor() ? this : fallback;
	}

	public boolean isTranslucent() {
		return a > 0 && a < 1;
	}

	public boolean isTransparent() {
		return a == 0;
	}

	public boolean isOpaque() {
		return a == 1;
	}

	public PreciseColor blend(@NotNull PreciseColor preciseColor, double ratio) {
		if (transparent) return preciseColor.withOpacity(blendValue(preciseColor.a, a, ratio));
		if (preciseColor.transparent) return withOpacity(blendValue(a, preciseColor.a, ratio));
		return new PreciseColor(blendValue(r, preciseColor.r, ratio), blendValue(g, preciseColor.g, ratio),
				blendValue(b, preciseColor.b, ratio), blendValue(a, preciseColor.a, ratio));
	}

	public PreciseColor blend(@NotNull PreciseColor other) {
		return blend(other, 0.5);
	}

	public PreciseColor blendAll(@NotNull PreciseColor... others) {
		return blend(average(others));
	}

	public PreciseColor withAlpha(int alpha) {
		if (transparent) return TRANSPARENT;
		return new PreciseColor(r, g, b, clampValue(alpha) / 255.0);
	}

	public PreciseColor withOpacity(double opacity) {
		if (transparent) return TRANSPARENT;
		return new PreciseColor(r, g, b, clampValue(opacity));
	}

	public PreciseColor brighter() {
		if (transparent) return TRANSPARENT;
		return blend(WHITE, 0.07);
	}

	public PreciseColor dimmer() {
		if (transparent) return TRANSPARENT;
		return blend(BLACK, 0.07);
	}

	public PreciseColor moreTranslucent() {
		if (transparent) return TRANSPARENT;
		return new PreciseColor(r, g, b, blendValue(a, 0, 0.07));
	}

	public PreciseColor lessTranslucent() {
		if (transparent) return TRANSPARENT;
		return new PreciseColor(r, g, b, blendValue(a, 1, 0.07));
	}

	public PreciseColor transparent() {
		if (transparent) return TRANSPARENT;
		return withOpacity(0);
	}

	public PreciseColor opaque() {
		if (transparent) return TRANSPARENT;
		return withOpacity(1);
	}

	public PreciseColor halfTransparent() {
		if (transparent) return TRANSPARENT;
		return withOpacity(a * 0.5);
	}

	public PreciseColor halfOpaque() {
		if (transparent) return TRANSPARENT;
		return withOpacity(a * 2);
	}

	public boolean equals(@NotNull PreciseColor other) {
		return hashCode() == other.hashCode();
	}

	@Override
	public boolean equals(@NotNull Object other) {
		if (this == other) return true;
		if (other instanceof PreciseColor) return equals((PreciseColor) other);
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(r, g, b, a);
	}

	@Override
	public String toString() {
		if (transparent) return getClass().getName() + "{transparent}";
		return getClass().getName() + "{" + "r=" + r + ", g=" + g + ", b=" + b + ", a=" + a + "}";
	}

	public String toShortString() {
		if (transparent) return "PreciseColor{transparent}";
		return "PreciseColor{" + r + ", " + g + ", " + b + ", " + a + "}";
	}
}
