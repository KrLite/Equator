package net.krlite.equator.color.core;

import com.scrtwpns.Mixbox;
import net.krlite.equator.core.ShortStringable;
import net.krlite.equator.core.SimpleOperations;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.awt.*;
import java.util.Arrays;
import java.util.stream.Stream;

public interface BasicRGBA<C extends BasicRGBA<C>> extends ShortStringable, SimpleOperations {
	double getRed();

	double getGreen();

	double getBlue();

	double getAlpha();

	default int getRedInt() {
		return clampValue((int) (getRed() * 255));
	}

	default int getGreenInt() {
		return clampValue((int) (getGreen() * 255));
	}

	default int getBlueInt() {
		return clampValue((int) (getBlue() * 255));
	}

	default int getAlphaInt() {
		return clampValue((int) (getAlpha() * 255));
	}

	default float getRedFloat() {
		return (float) getRed();
	}

	default float getGreenFloat() {
		return (float) getGreen();
	}

	default float getBlueFloat() {
		return (float) getBlue();
	}

	default float getAlphaFloat() {
		return (float) getAlpha();
	}

	default boolean isTransparent() {
		return getAlpha() == 0;
	}

	default boolean isTranslucent() {
		return getAlpha() > 0 && getAlpha() < 1;
	}

	default boolean isOpaque() {
		return getAlpha() == 1;
	}

	default Color toColor() {
		return new Color(getRedInt(), getGreenInt(), getBlueInt(), getAlphaInt());
	}

	default int toColorInt() {
		return toColor().getRGB();
	}

	default String toColorHex() {
		return Integer.toHexString(toColorInt());
	}

	default boolean hasColor() {
		return true;
	}

	C withRed(double red);

	C withGreen(double green);

	C withBlue(double blue);

	C withAlpha(@Range(from = 0, to = 255) int alpha);

	C withOpacity(double opacity);

	default C brighter() {
		return withRed(blendValue(getRed(), 1, 0.1))
					   .withGreen(blendValue(getGreen(), 1, 0.1))
					   .withBlue(blendValue(getBlue(), 1, 0.1));
	}

	default C dimmer() {
		return withRed(blendValue(getRed(), 0, 0.1))
					   .withGreen(blendValue(getGreen(), 0, 0.1))
					   .withBlue(blendValue(getBlue(), 0, 0.1));
	}

	default C moreTranslucent() {
		return withOpacity(blendValue(getAlpha(), 1, 0.1));
	}

	default C lessTranslucent(){
		return withOpacity(blendValue(getAlpha(), 0, 0.1));
	}

	default C opaque() {
		return withOpacity(1);
	}

	default C transparent() {
		return withOpacity(0);
	}

	default C halfOpaque() {
		return withOpacity(blendValue(getAlpha(), 1, 0.5));
	}

	default C halfTransparent() {
		return withOpacity(blendValue(getAlpha(), 0, 0.5));
	}

	default C lighter() {
		return withRed(Math.max(Math.sqrt(getRed()), 0.73))
					   .withGreen(Math.max(Math.sqrt(getGreen()), 0.73))
					   .withBlue(Math.max(Math.sqrt(getBlue()), 0.73));
	}

	default C darker() {
		return withRed(Math.min(Math.pow(getRed(), 2), 0.73))
					   .withGreen(Math.min(Math.pow(getGreen(), 2), 0.73))
					   .withBlue(Math.min(Math.pow(getBlue(), 2), 0.73));
	}

	default BasicRGBA<?> blend(@NotNull BasicRGBA<?> another, double ratio) {
		if (!another.hasColor()) {
			if (!hasColor()) return this;
			return withOpacity(blendValue(another.getAlpha(), getAlpha(), ratio));
		}
		if (!hasColor())
			return withOpacity(blendValue(getAlpha(), another.getAlpha(), ratio));
		return withRed(blendValue(getRed(), another.getRed(), ratio))
					   .withGreen(blendValue(getGreen(), another.getGreen(), ratio))
					   .withBlue(blendValue(getBlue(), another.getBlue(), ratio))
					   .withOpacity(blendValue(getAlpha(), another.getAlpha(), ratio));
	}

	default BasicRGBA<?> blend(@NotNull BasicRGBA<?> another) {
		return blend(another, 0.5);
	}

	default BasicRGBA<?> mix(@NotNull BasicRGBA<?> another, double ratio) {
		if (!another.hasColor()) {
			if (!hasColor()) return this;
			return withOpacity(blendValue(another.getAlpha(), getAlpha(), ratio));
		}
		if (!hasColor())
			return withOpacity(blendValue(getAlpha(), another.getAlpha(), ratio));
		float[] mixed = Mixbox.lerpFloat(new float[]{getRedFloat(), getGreenFloat(), getBlueFloat(), getAlphaFloat()},
				new float[]{another.getRedFloat(), another.getGreenFloat(), another.getBlueFloat(), another.getAlphaFloat()},
				(float) ratio);
		return withRed(mixed[0]).withGreen(mixed[1]).withBlue(mixed[2]).withOpacity(mixed[3]);
	}

	default BasicRGBA<?> mix(@NotNull BasicRGBA<?> another) {
		return mix(another, 0.5F);
	}

	default BasicRGBA<?> blendOrMix(@NotNull BasicRGBA<?> another, double ratio, boolean pigmentMix) {
		return pigmentMix ? mix(another, ratio) : blend(another, ratio);
	}

	default BasicRGBA<?> average(@NotNull BasicRGBA<?>... others) {
		if (others.length == 0)
			return this;
		return withRed(Stream.concat(Stream.of(this), Arrays.stream(others))
							   .filter(BasicRGBA::hasColor)
							   .mapToDouble(BasicRGBA::getRed)
							   .average()
							   .orElse(0))
					   .withGreen(Stream.concat(Stream.of(this), Arrays.stream(others))
										  .filter(BasicRGBA::hasColor)
										  .mapToDouble(BasicRGBA::getGreen)
										  .average()
										  .orElse(0))
					   .withBlue(Stream.concat(Stream.of(this), Arrays.stream(others))
										 .filter(BasicRGBA::hasColor)
										 .mapToDouble(BasicRGBA::getBlue)
										 .average()
										 .orElse(0))
					   .withOpacity(hasColor() ? Stream.concat(Stream.of(this), Arrays.stream(others))
														 .mapToDouble(BasicRGBA::getAlpha)
														 .average()
														 .orElse(0) : 0);
	}

	default BasicRGBA<?> orElse(@NotNull BasicRGBA<?> another) {
		return hasColor() ? this : another;
	}
}
