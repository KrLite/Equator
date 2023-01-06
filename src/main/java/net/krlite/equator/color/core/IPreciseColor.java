package net.krlite.equator.color.core;

import net.krlite.equator.core.ShortStringable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.awt.*;
import java.util.Arrays;
import java.util.Objects;

public interface IPreciseColor<C extends IPreciseColor<C>> extends ShortStringable {
	/*
	 * BASICS
	 */

	default int clampValue(int value) {
		return Math.max(0, Math.min(255, value));
	}

	default double clampValue(double value) {
		return Math.max(0, Math.min(1, value));
	}

	default double blendValue(double first, double second, double ratio) {
		return first * (1 - clampValue(ratio)) + second * clampValue(ratio);
	}

	/*
	 * ATTRIBUTES
	 */
	
	double red();

	double green();

	double blue();

	double alpha();

	default int redInt() {
		return clampValue((int) (red() * 255));
	}

	default int greenInt() {
		return clampValue((int) (green() * 255));
	}

	default int blueInt() {
		return clampValue((int) (blue() * 255));
	}

	default int alphaInt() {
		return clampValue((int) (alpha() * 255));
	}

	default float redFloat() {
		return (float) red();
	}

	default float greenFloat() {
		return (float) green();
	}

	default float blueFloat() {
		return (float) blue();
	}

	default float alphaFloat() {
		return (float) alpha();
	}

	default Color toColor() {
		return new Color(redInt(), greenInt(), blueInt(), alphaInt());
	}

	default boolean hasColor() {
		return true;
	}

	default boolean isTransparent() {
		return alpha() == 0;
	}

	default boolean isTranslucent() {
		return alpha() > 0 && alpha() < 1;
	}

	default boolean isOpaque() {
		return alpha() == 1;
	}

	/*
	 * OBJECT METHODS
	 */

	C createPreciseColor(double r, double g, double b, double a);

	default C self() {
		return createPreciseColor(red(), green(), blue(), alpha());
	}

	default <AC extends IPreciseColor<AC>> C blend(@NotNull AC another, double ratio) {
		if (!another.hasColor()) {
			return withOpacity(blendValue(another.alpha(), alpha(), ratio));
		}
		if (!hasColor()) {
			return createPreciseColor(another.red(), another.green(), another.blue(), blendValue(alpha(), another.alpha(), ratio));
		}
		return createPreciseColor(
				blendValue(red(), another.red(), ratio),
				blendValue(green(), another.green(), ratio),
				blendValue(blue(), another.blue(), ratio),
				blendValue(alpha(), another.alpha(), ratio)
		);
	}

	default <AC extends IPreciseColor<AC>> C blend(@NotNull AC another) {
		return blend(another, 0.5);
	}

	default <AC extends IPreciseColor<AC>> C blendAll(@NotNull AC... others) {
		if (others.length == 0) {
			return self();
		}
		return blend(
				createPreciseColor(
						Arrays.stream(others).mapToDouble(IPreciseColor::red).average().orElse(0),
						Arrays.stream(others).mapToDouble(IPreciseColor::green).average().orElse(0),
						Arrays.stream(others).mapToDouble(IPreciseColor::blue).average().orElse(0),
						Arrays.stream(others).mapToDouble(IPreciseColor::alpha).average().orElse(0)
				)
		);
	}

	default C withAlpha(@Range(from = 0, to = 255) int alpha) {
		return createPreciseColor(red(), green(), blue(), clampValue(alpha) / 255.0);
	}

	default C withOpacity(double opacity) {
		return createPreciseColor(red(), green(), blue(), clampValue(opacity));
	}

	default C brighter() {
		return createPreciseColor(
				blendValue(red(), 1, 0.1),
				blendValue(green(), 1, 0.1),
				blendValue(blue(), 1, 0.1),
				alpha()
		);
	}

	default C dimmer() {
		return createPreciseColor(
				blendValue(red(), 0, 0.1),
				blendValue(green(), 0, 0.1),
				blendValue(blue(), 0, 0.1),
				alpha()
		);
	}

	default C moreTranslucent() {
		return createPreciseColor(red(), green(), blue(), blendValue(alpha(), 0, 0.1));
	}

	default C lessTranslucent() {
		return createPreciseColor(red(), green(), blue(), blendValue(alpha(), 1, 0.1));
	}

	default C transparent() {
		return withOpacity(0);
	}

	default C opaque() {
		return withOpacity(1);
	}

	default C halfTransparent() {
		return createPreciseColor(red(), green(), blue(), blendValue(alpha(), 0, 0.5));
	}

	default C halfOpaque() {
		return createPreciseColor(red(), green(), blue(), blendValue(alpha(), 1, 0.5));
	}

	default C lighter() {
		return createPreciseColor(
				Math.max(Math.sqrt(red()), 0.73),
				Math.max(Math.sqrt(green()), 0.73),
				Math.max(Math.sqrt(blue()), 0.73),
				alpha()
		);
	}

	default C darker() {
		return createPreciseColor(
				Math.min(Math.pow(red(), 2), 0.27),
				Math.min(Math.pow(green(), 2), 0.27),
				Math.min(Math.pow(blue(), 2), 0.27),
				alpha()
		);
	}
}
