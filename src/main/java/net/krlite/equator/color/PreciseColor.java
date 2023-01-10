package net.krlite.equator.color;

import net.krlite.equator.color.core.IPreciseColor;
import net.krlite.equator.core.FieldFormattable;
import net.krlite.equator.core.HashCodeComparable;
import net.krlite.equator.geometry.Node;
import net.krlite.equator.geometry.Rect;
import net.krlite.equator.geometry.TintedNode;
import net.krlite.equator.geometry.TintedRect;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.awt.*;
import java.util.Arrays;

public class PreciseColor extends HashCodeComparable implements FieldFormattable, IPreciseColor<PreciseColor> {
	/*
	 * BASICS
	 */

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

	public static PreciseColor average(@NotNull PreciseColor... others) {
		return new PreciseColor(
				Arrays.stream(others).mapToDouble(PreciseColor::red).average().orElse(0),
				Arrays.stream(others).mapToDouble(PreciseColor::green).average().orElse(0),
				Arrays.stream(others).mapToDouble(PreciseColor::blue).average().orElse(0),
				Arrays.stream(others).mapToDouble(PreciseColor::alpha).average().orElse(0)
		);
	}

	/*
	 * FIELDS
	 */

	protected final double red, green, blue, alpha;
	protected final boolean transparent;

	/*
	 * STATIC CONSTRUCTORS
	 */

	public static PreciseColor from(@Nullable Color color) {
		if (color == null) return TRANSPARENT;
		return new PreciseColor(color.getRed() / 255.0, color.getGreen() / 255.0, color.getBlue() / 255.0, color.getAlpha() / 255.0);
	}

	public static PreciseColor from(@Nullable String hexColor) {
		if (hexColor == null || hexColor.isEmpty()) return TRANSPARENT;
		return PreciseColor.from(Color.decode(hexColor));
	}

	public static PreciseColor from(@Range(from = 0x0, to = 0xFFFFFFFFL) long integerColor) {
		return new PreciseColor(
				((integerColor >> 16) & 0xFF) / 255.0,
				((integerColor >> 8) & 0xFF) / 255.0,
				(integerColor & 0xFF) / 255.0,
				integerColor > 0xFFFFFF ? ((integerColor >> 24) & 0xFF) / 255.0 : 0xFF
		);
	}

	public static PreciseColor from(
			@Range(from = 0, to = 255) int red, @Range(from = 0, to = 255) int green,
			@Range(from = 0, to = 255) int blue, @Range(from = 0, to = 255) int alpha
	) {
		return new PreciseColor(red / 255.0, green / 255.0, blue / 255.0, alpha / 255.0);
	}

	public static PreciseColor from(
			@Range(from = 0, to = 255) int red, @Range(from = 0, to = 255) int green,
			@Range(from = 0, to = 255) int blue
	) {
		return new PreciseColor(red / 255.0, green / 255.0, blue / 255.0);
	}

	public static PreciseColor grayscale(@Range(from = 0, to = 255) int grayscale) {
		return new PreciseColor(grayscale / 255.0);
	}

	/*
	 * CONSTRUCTORS
	 */

	protected PreciseColor(double red, double green, double blue, double alpha, boolean transparent) {
		this.red = clampValue(red);
		this.green = clampValue(green);
		this.blue = clampValue(blue);
		this.alpha = clampValue(alpha);
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

	/*
	 * CONVERSIONS
	 */

	public TintedNode bind(Node node) {
		return node.bind(this);
	}

	public TintedRect bind(Rect rect) {
		return rect.bind(this);
	}

	/*
	 * ATTRIBUTES
	 */

	@Override
	public double red() {
		return red;
	}

	@Override
	public double green() {
		return green;
	}

	@Override
	public double blue() {
		return blue;
	}

	@Override
	public double alpha() {
		return alpha;
	}

	/*
	 * OBJECT METHODS
	 */

	@Override
	public PreciseColor createPreciseColor(double r, double g, double b, double a) {
		return new PreciseColor(r, g, b, a);
	}

	@Override
	public PreciseColor withAlpha(@Range(from = 0, to = 255) int alpha) {
		if (transparent) {
			return TRANSPARENT;
		}
		return IPreciseColor.super.withAlpha(alpha);
	}

	@Override
	public PreciseColor withOpacity(double opacity) {
		if (transparent) {
			return TRANSPARENT;
		}
		return IPreciseColor.super.withOpacity(opacity);
	}

	@Override
	public PreciseColor brighter() {
		if (transparent) {
			return TRANSPARENT;
		}
		return IPreciseColor.super.brighter();
	}

	@Override
	public PreciseColor dimmer() {
		if (transparent) {
			return TRANSPARENT;
		}
		return IPreciseColor.super.dimmer();
	}

	@Override
	public PreciseColor moreTranslucent() {
		if (transparent) {
			return TRANSPARENT;
		}
		return IPreciseColor.super.moreTranslucent();
	}

	@Override
	public PreciseColor lessTranslucent() {
		if (transparent) {
			return TRANSPARENT;
		}
		return IPreciseColor.super.lessTranslucent();
	}

	@Override
	public PreciseColor transparent() {
		if (transparent) {
			return TRANSPARENT;
		}
		return IPreciseColor.super.transparent();
	}

	@Override
	public PreciseColor opaque() {
		if (transparent) {
			return TRANSPARENT;
		}
		return IPreciseColor.super.opaque();
	}

	@Override
	public PreciseColor halfTransparent() {
		if (transparent) {
			return TRANSPARENT;
		}
		return IPreciseColor.super.halfTransparent();
	}

	@Override
	public PreciseColor halfOpaque() {
		if (transparent) {
			return TRANSPARENT;
		}
		return IPreciseColor.super.halfOpaque();
	}

	/*
	 * PROPERTIES
	 */

	@Override
	public String toString() {
		return getClass().getSimpleName() + "{" +
					   (hasColor() ? formatFields("transparent") : "transparent") + "}";
	}

	@Override
	public String toShortString() {
		return "[" + (hasColor() ? formatFields("transparent") : "transparent") + "]";
	}
}
