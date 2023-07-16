package net.krlite.equator.color;

import net.krlite.equator.base.HashCodeComparable;
import net.krlite.equator.color.core.BasicRGBA;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.awt.*;

public class PreciseColor extends HashCodeComparable implements BasicRGBA<PreciseColor> {
	public static final PreciseColor WHITE = new PreciseColor(1, 1, 1);
	public static final PreciseColor BLACK = new PreciseColor(0, 0, 0);
	public static final PreciseColor RED = new PreciseColor(1, 0, 0);
	public static final PreciseColor GREEN = new PreciseColor(0, 1, 0);
	public static final PreciseColor BLUE = new PreciseColor(0, 0, 1);
	public static final PreciseColor YELLOW = new PreciseColor(1, 1, 0);
	public static final PreciseColor MAGENTA = new PreciseColor(1, 0, 1);
	public static final PreciseColor CYAN = new PreciseColor(0, 1, 1);

	/**
	 * A transparent color, which will only participate in alpha blending.
	 */
	public static final PreciseColor TRANSPARENT = new PreciseColor(0, 0, 0, 0, true);

	protected final boolean transparent;

	public static PreciseColor of(@Nullable String hexString) {
		if (hexString == null || hexString.isEmpty()) return TRANSPARENT;
		return new PreciseColor(Color.decode(hexString));
	}

	public static PreciseColor of(@Range(from = 0x0, to = 0xFFFFFFFFL) long colorCode) {
		return PreciseColor.of(
				(int) ((colorCode >> 16) & 0xFF),
				(int) ((colorCode >> 8) & 0xFF),
				(int) (colorCode & 0xFF),
				colorCode > 0xFFFFFF ? (int) ((colorCode >> 24) & 0xFF) : 0xFF
		);
	}

	public static PreciseColor of(
			@Range(from = 0, to = 255) int red, @Range(from = 0, to = 255) int green,
			@Range(from = 0, to = 255) int blue, @Range(from = 0, to = 255) int alpha
	) {
		return new PreciseColor(red / 255.0, green / 255.0, blue / 255.0, alpha / 255.0);
	}

	public static PreciseColor of(
			@Range(from = 0, to = 255) int red, @Range(from = 0, to = 255) int green,
			@Range(from = 0, to = 255) int blue
	) {
		return new PreciseColor(red / 255.0, green / 255.0, blue / 255.0);
	}

	public static PreciseColor ofGrayscaleInt(@Range(from = 0, to = 255) int grayscale) {
		return new PreciseColor(grayscale / 255.0);
	}

	protected final double red, green, blue, alpha;

	@Override
	public double getRed() {
		return red;
	}

	@Override
	public double getGreen() {
		return green;
	}

	@Override
	public double getBlue() {
		return blue;
	}

	@Override
	public double getAlpha() {
		return alpha;
	}

	public PreciseColor(double red, double green, double blue, double alpha, boolean transparent) {
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.alpha = alpha;
		this.transparent = transparent;
	}

	public PreciseColor(double red, double green, double blue, double alpha) {
		this(red, green, blue, alpha, false);
	}

	public PreciseColor(double red, double green, double blue) {
		this(red, green, blue, 1);
	}

	public PreciseColor(double grayscale) {
		this(grayscale, grayscale, grayscale);
	}

	public PreciseColor(BasicRGBA<?> color) {
		this(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
	}

	public PreciseColor(@Nullable Color color) {
		this(color == null ? 0 : color.getRed() / 255.0, color == null ? 0 : color.getGreen() / 255.0, color == null ? 0 : color.getBlue() / 255.0, color == null ? 0 : color.getAlpha() / 255.0, color == null);
	}

	@Override
	public boolean hasColor() {
		return !transparent;
	}

	@Override
	public PreciseColor withRed(double red) {
		return new PreciseColor(red, getGreen(), getBlue(), getAlpha());
	}

	@Override
	public PreciseColor withGreen(double green) {
		return new PreciseColor(getRed(), green, getBlue(), getAlpha());
	}

	@Override
	public PreciseColor withBlue(double blue) {
		return new PreciseColor(getRed(), getGreen(), blue, getAlpha());
	}

	@Override
	public PreciseColor withAlpha(@Range(from = 0, to = 255) int alpha) {
		return new PreciseColor(getRed(), getGreen(), getBlue(), clampValue(alpha) / 255.0);
	}

	@Override
	public PreciseColor withOpacity(double opacity) {
		return new PreciseColor(getRed(), getGreen(), getBlue(), clampValue(opacity));
	}

	@Override
	public String toShortString() {
		return "<" + (hasColor() ? formatFields(false, "transparent") : "transparent") + ">";
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "<" + (hasColor() ? formatFields("transparent") : "transparent") + ">";
	}
}
