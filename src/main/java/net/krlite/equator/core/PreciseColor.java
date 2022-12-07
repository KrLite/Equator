package net.krlite.equator.core;

import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Objects;

public class PreciseColor {
    private double red;
    private double green;
    private double blue;
    private double alpha;

    /**
     * Creates an RGBA color which every value is precisely stored as {@link Double}.
     * @param red       The independent {@link Double} <b>red</b> value from 0.0 to 1.0.
     * @param green     The independent {@link Double} <b>green</b> value from 0.0 to 1.0.
     * @param blue      The independent {@link Double} <b>blue</b> value from 0.0 to 1.0.
     * @param alpha     The independent {@link Double} <b>alpha</b> value from 0.0 to 1.0.
     */
    public PreciseColor(double red, double green, double blue, double alpha) {
        this.red = Double.isNaN(red) ? Double.NaN : MathHelper.clamp(red, 0, 1);
        this.green = Double.isNaN(green) ? Double.NaN : MathHelper.clamp(green, 0, 1);
        this.blue = Double.isNaN(blue) ? Double.NaN : MathHelper.clamp(blue, 0, 1);
        this.alpha = Double.isNaN(alpha) ? Double.NaN : MathHelper.clamp(alpha, 0, 1);
    }

    /**
     * Creates an RGB color which every value is precisely stored as {@link Double}.
     * @param red   The independent {@link Double} <b>red</b> value from 0.0 to 1.0.
     * @param green The independent {@link Double} <b>green</b> value from 0.0 to 1.0.
     * @param blue  The independent {@link Double} <b>blue</b> value from 0.0 to 1.0.
     */
    public PreciseColor(double red, double green, double blue) {
        this.red = Double.isNaN(red) ? Double.NaN : MathHelper.clamp(red, 0, 1);
        this.green = Double.isNaN(green) ? Double.NaN : MathHelper.clamp(green, 0, 1);
        this.blue = Double.isNaN(blue) ? Double.NaN : MathHelper.clamp(blue, 0, 1);
        alpha = 1;
    }

    /**
     * Creates an RGB translucent/opaque color which every value is precisely stored as {@link Double}.
     * @param red       The independent {@link Double} <b>red</b> value from 0.0 to 1.0.
     * @param green     The independent {@link Double} <b>green</b> value from 0.0 to 1.0.
     * @param blue      The independent {@link Double} <b>blue</b> value from 0.0 to 1.0.
     * @param opaque    Whether the color is opaque or not.
     */
    public PreciseColor(double red, double green, double blue, boolean opaque) {
        this.red = Double.isNaN(red) ? Double.NaN : MathHelper.clamp(red, 0, 1);
        this.green = Double.isNaN(green) ? Double.NaN : MathHelper.clamp(green, 0, 1);
        this.blue = Double.isNaN(blue) ? Double.NaN : MathHelper.clamp(blue, 0, 1);
        alpha = opaque ? 1 : 0;
    }

    /**
     * Creates an RGBA color from an existing {@link Color} which every value is precisely stored as {@link Double}.
     * @param color The dedicated {@link Color}.
     */
    public PreciseColor(@NotNull Color color) {
        red = color.getRed() / 255.0;
        green = color.getGreen() / 255.0;
        blue = color.getBlue() / 255.0;
        alpha = color.getAlpha() / 255.0;
    }

    /**
     * Checks if the value is between zero and one.
     * @param value     The dedicated value.
     * @return          <code>true</code> if the value is in the range of 0 to 1, otherwise <code>false</code>.
     */
    private boolean is(double value) {
        return !Double.isNaN(value) && (value >= 0 && value <= 1);
    }

    /**
     * Interpolate the value by an index.
     * @param value     The original value,
     * @param target    The target value,
     * @param delta     The index.
     * @return          The value interpolated from target by delta.
     */
    private double lerp(double value, double target, double delta) {
        return is(value)
                ? is(target)
                        ? value + (target - value) * delta
                        : value
                : is(target)
                        ? target
                        : -1;
    }

    /**
     * An empty color (with no RGB) which is translucent.
     */
    @Contract(value = " -> new", pure = true)
    public static @NotNull PreciseColor empty() {
        return new PreciseColor(Double.NaN, Double.NaN, Double.NaN, 0);
    }

    /**
     * An empty color (with no RGB) which is opaque.
     */
    @Contract(value = " -> new", pure = true)
    public static @NotNull PreciseColor full() {
        return new PreciseColor(Double.NaN, Double.NaN, Double.NaN, 1);
    }

    /**
     * Copies the current value to a new {@link PreciseColor} without modifying self.
     * @return  A new {@link PreciseColor} which has the same value.
     */
    public PreciseColor independent() {
        return new PreciseColor(this.red, this.green, this.blue, this.alpha);
    }

    /**
     * @return  The red value.
     */
    public double red() {
        return red;
    }

    /**
     * @return  The green value.
     */
    public double green() {
        return green;
    }

    /**
     * @return  The blue value.
     */
    public double blue() {
        return blue;
    }

    /**
     * @return  The alpha value.
     */
    public double alpha() {
        return alpha;
    }

    /**
     * Gets the {@link PreciseColor} as a {@link Color}.
     *
     * @return  The {@link Color} value.
     */
    public Color get() {
        return new Color(
                (float) (is(red) ? red : 0),
                (float) (is(green) ? green : 0),
                (float) (is(blue) ? blue : 0),
                (float) (is(alpha) ? alpha : 0)
        );
    }

    /**
     * Gets the color as an RGBA integer value.
     */
    public int getRGBA() {
        return get().getRGB();
    }

    /**
     * Replaces the red value.
     */
    public PreciseColor castRed(double red) {
        this.red = Double.isNaN(red) ? Double.NaN : MathHelper.clamp(red, 0, 1);
        return this;
    }

    /**
     * Replaces the green value.
     */
    public PreciseColor castGreen(double green) {
        this.green = Double.isNaN(green) ? Double.NaN : MathHelper.clamp(green, 0, 1);
        return this;
    }

    /**
     * Replaces the blue value.
     */
    public PreciseColor castBlue(double blue) {
        this.blue = Double.isNaN(blue) ? Double.NaN : MathHelper.clamp(blue, 0, 1);
        return this;
    }

    /**
     * Replaces the alpha value.
     */
    public PreciseColor castAlpha(double alpha) {
        this.alpha = Double.isNaN(alpha) ? Double.NaN : MathHelper.clamp(alpha, 0, 1);
        return this;
    }

    /**
     * Multiples the red value.
     */
    public PreciseColor multipleRed(double multiplier) {
        return this.castRed(red * multiplier);
    }

    /**
     * Multiples the green value.
     */
    public PreciseColor multipleGreen(double multiplier) {
        return this.castGreen(green * multiplier);
    }

    /**
     * Multiples the blue value.
     */
    public PreciseColor multipleBlue(double multiplier) {
        return this.castBlue(blue * multiplier);
    }

    /**
     * Multiples the alpha value.
     */
    public PreciseColor multipleAlpha(double multiplier) {
        return this.castAlpha(alpha * multiplier);
    }

    /**
     * Blends the color with another {@link PreciseColor}.
     * @param color The dedicated {@link PreciseColor}.
     */
    public void blend(PreciseColor color) {
        blend(color, 0.5);
    }

    /**
     * Blends the color with another {@link PreciseColor}.
     * @param color The dedicated {@link PreciseColor}.
     * @param delta The index.
     */
    public void blend(@NotNull PreciseColor color, double delta) {
        castRed(lerp(this.red, color.red, delta));
        castGreen(lerp(this.green, color.green, delta));
        castBlue(lerp(this.blue, color.blue, delta));
        castAlpha(lerp(this.alpha, color.alpha, delta));
    }

    public String toString() {
        return getClass().getName() + "[r=" + red() + ",g=" + green() + ",b=" + blue() + ",a=" + alpha() + "]";
    }

    /**
     * @return The hexadecimal {@link String} value of the color.
     */
    public String toHexString() {
        return toHexString(false);
    }

    /**
     * @param upperCase Whether to uppercase the string or not.
     * @return The hexadecimal {@link String} value of the color.
     */
    public String toHexString(boolean upperCase) {
        return upperCase ? Integer.toHexString(getRGBA()).toUpperCase() : Integer.toHexString(getRGBA());
    }

    /**
     * Decodes a hex string and gets its dedicated {@link PreciseColor}.
     * @param hexString The hexadecimal string.
     * @return          A {@link PreciseColor} in which the value equals to the hex string.
     */
    public static PreciseColor decode(String hexString) {
        return new PreciseColor(Color.decode(hexString));
    }

    public int hashCode() {
        return Objects.hash(red, green, blue, alpha);
    }
}
