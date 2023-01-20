package net.krlite.equator.color;

import net.krlite.equator.color.base.AbstractPreciseColor;
import net.krlite.equator.geometry.Node;
import net.krlite.equator.geometry.base.AbstractNode;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.awt.*;

public class PreciseColor extends AbstractPreciseColor<PreciseColor> {
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

	public static PreciseColor of(@Nullable Color color) {
		if (color == null) return TRANSPARENT;
		return new PreciseColor(color.getRed() / 255.0, color.getGreen() / 255.0, color.getBlue() / 255.0, color.getAlpha() / 255.0);
	}

	public static PreciseColor of(@Nullable String hexString) {
		if (hexString == null || hexString.isEmpty()) return TRANSPARENT;
		return PreciseColor.of(Color.decode(hexString));
	}

	public static PreciseColor of(@Range(from = 0x0, to = 0xFFFFFFFFL) long colorCode) {
		return new PreciseColor(
				((colorCode >> 16) & 0xFF) / 255.0,
				((colorCode >> 8) & 0xFF) / 255.0,
				(colorCode & 0xFF) / 255.0,
				colorCode > 0xFFFFFF ? ((colorCode >> 24) & 0xFF) / 255.0 : 0xFF
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

	@Override
	protected PreciseColor child(double red, double green, double blue, double alpha) {
		return new PreciseColor(red, green, blue, alpha);
	}

	public PreciseColor(double red, double green, double blue, double alpha, boolean transparent) {
		super(red, green, blue, alpha);
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

	public Node.Tinted bind(AbstractNode<?> node) {
		return new Node(node.getAbscissa(), node.getOrdinate()).new Tinted(this);
	}
}
