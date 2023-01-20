package net.krlite.equator.color.base;

import net.krlite.equator.base.HashCodeComparable;
import net.krlite.equator.core.ShortStringable;
import net.krlite.equator.core.SimpleOperations;
import org.checkerframework.checker.units.qual.A;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.awt.*;
import java.util.Arrays;
import java.util.stream.Stream;

public abstract class AbstractPreciseColor<C extends AbstractPreciseColor<C>>
		extends HashCodeComparable implements ShortStringable, SimpleOperations {
	protected final double red, green, blue, alpha;

	public double getRed() {
		return red;
	}

	public double getGreen() {
		return green;
	}

	public double getBlue() {
		return blue;
	}

	public double getAlpha() {
		return alpha;
	}

	public int getRedInt() {
		return clampValue((int) (getRed() * 255));
	}

	public int getGreenInt() {
		return clampValue((int) (getGreen() * 255));
	}

	public int getBlueInt() {
		return clampValue((int) (getBlue() * 255));
	}

	public int getAlphaInt() {
		return clampValue((int) (getAlpha() * 255));
	}

	public float getRedFloat() {
		return (float) getRed();
	}

	public float getGreenFloat() {
		return (float) getGreen();
	}

	public float getBlueFloat() {
		return (float) getBlue();
	}

	public float getAlphaFloat() {
		return (float) getAlpha();
	}

	protected AbstractPreciseColor(double red, double green, double blue, double alpha) {
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.alpha = alpha;
	}

	protected abstract C child(double red, double green, double blue, double alpha);

	private C child(AbstractPreciseColor<?> another) {
		return child(another.getRed(), another.getGreen(), another.getBlue(), another.getAlpha());
	}

	private C self() {
		return child(getRed(), getGreen(), getBlue(), getAlpha());
	}

	public Color getColor() {
		return new Color(getRedInt(), getGreenInt(), getBlueInt(), getAlphaInt());
	}

	public int getColorInt() {
		return getColor().getRGB();
	}

	public String getColorHex() {
		return Integer.toHexString(getColorInt());
	}

	public boolean hasColor() {
		return true;
	}

	public boolean isTransparent() {
		return getAlpha() == 0;
	}

	public boolean isTranslucent() {
		return getAlpha() > 0 && getAlpha() < 1;
	}

	public boolean isOpaque() {
		return getAlpha() == 1;
	}

	public C blend(@NotNull AbstractPreciseColor<?> another, double ratio) {
		if (!another.hasColor())
			return withOpacity(blendValue(another.getAlpha(), getAlpha(), ratio));
		if (!hasColor())
			return child(another.withOpacity(blendValue(getAlpha(), another.getAlpha(), ratio)));
		return child(
				blendValue(getRed(), another.getRed(), ratio),
				blendValue(getGreen(), another.getGreen(), ratio),
				blendValue(getBlue(), another.getBlue(), ratio),
				blendValue(getAlpha(), another.getAlpha(), ratio)
		);
	}

	public C blend(@NotNull AbstractPreciseColor<?> another) {
		return blend(another, 0.5);
	}

	public C average(@NotNull AbstractPreciseColor<?>... others) {
		if (others.length == 0)
			return self();
		return child(
				Stream.concat(Arrays.stream(others), Stream.of(self())).mapToDouble(AbstractPreciseColor::getRed).average().orElse(0),
				Stream.concat(Arrays.stream(others), Stream.of(self())).mapToDouble(AbstractPreciseColor::getGreen).average().orElse(0),
				Stream.concat(Arrays.stream(others), Stream.of(self())).mapToDouble(AbstractPreciseColor::getBlue).average().orElse(0),
				Stream.concat(Arrays.stream(others), Stream.of(self())).mapToDouble(AbstractPreciseColor::getAlpha).average().orElse(0)
		);
	}

	public C withAlpha(@Range(from = 0, to = 255) int alpha) {
		return child(getRed(), getGreen(), getBlue(), clampValue(alpha) / 255.0);
	}

	public C withOpacity(double opacity) {
		return child(getRed(), getGreen(), getBlue(), clampValue(opacity));
	}

	public C brighter() {
		return child(
				blendValue(getRed(), 1, 0.1),
				blendValue(getGreen(), 1, 0.1),
				blendValue(getBlue(), 1, 0.1),
				getAlpha()
		);
	}

	public C dimmer() {
		return child(
				blendValue(getRed(), 0, 0.1),
				blendValue(getGreen(), 0, 0.1),
				blendValue(getBlue(), 0, 0.1),
				getAlpha()
		);
	}

	public C moreTranslucent() {
		return withOpacity(blendValue(getAlpha(), 0, 0.1));
	}

	public C lessTranslucent() {
		return withOpacity(blendValue(getAlpha(), 1, 0.1));
	}

	public C transparent() {
		return withOpacity(0);
	}

	public C opaque() {
		return withOpacity(1);
	}

	public C halfTransparent() {
		return withOpacity(blendValue(getAlpha(), 0, 0.5));
	}

	public C halfOpaque() {
		return withOpacity(blendValue(getAlpha(), 1, 0.5));
	}

	public C lighter() {
		return child(
				Math.max(Math.sqrt(getRed()), 0.73),
				Math.max(Math.sqrt(getGreen()), 0.73),
				Math.max(Math.sqrt(getBlue()), 0.73),
				getAlpha()
		);
	}

	public C darker() {
		return child(
				Math.min(Math.pow(getRed(), 2), 0.27),
				Math.min(Math.pow(getGreen(), 2), 0.27),
				Math.min(Math.pow(getBlue(), 2), 0.27),
				getAlpha()
		);
	}
}
