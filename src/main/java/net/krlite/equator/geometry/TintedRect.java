package net.krlite.equator.geometry;

import net.krlite.equator.color.PreciseColor;
import net.krlite.equator.core.FieldFormattable;
import net.krlite.equator.core.HashCodeComparable;
import net.krlite.equator.geometry.core.IRect;
import net.krlite.equator.render.Equator;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;

public class TintedRect extends HashCodeComparable
		implements IRect<TintedRect, TintedNode>, FieldFormattable {
	/*
	 * FIELDS
	 */

	@NotNull
	private final TintedNode upperLeft, lowerLeft, lowerRight, upperRight;
	
	/*
	 * CONSTRUCTORS
	 */

	public TintedRect(@NotNull TintedNode upperLeft, @NotNull TintedNode lowerLeft, @NotNull TintedNode lowerRight, @NotNull TintedNode upperRight) {
		this.upperLeft = upperLeft;
		this.lowerLeft = lowerLeft;
		this.lowerRight = lowerRight;
		this.upperRight = upperRight;
	}
	
	/*
	 * CONVERSIONS
	 */

	public Rect unBind() {
		return new Rect(upperLeft.unBind(), lowerLeft.unBind(), lowerRight.unBind(), upperRight.unBind());
	}
	
	/*
	 * ATTRIBUTES
	 */

	@Override
	public TintedNode upperLeft() {
		return upperLeft;
	}

	@Override
	public TintedNode lowerLeft() {
		return lowerLeft;
	}

	@Override
	public TintedNode lowerRight() {
		return lowerRight;
	}

	@Override
	public TintedNode upperRight() {
		return upperRight;
	}

	public PreciseColor meshColor(double abscissa, double ordinate) {
		abscissa = Math.max(0, Math.min(1, abscissa));
		ordinate = Math.max(0, Math.min(1, ordinate));

		return upperLeft.tint().blend(upperRight.tint(), abscissa)
					   .blend(lowerLeft.tint().blend(lowerRight.tint(), abscissa), ordinate);
	}
	
	/*
	 * OBJECT METHODS
	 */

	@Override
	public TintedNode createNode(double x, double y) {
		return new TintedNode(x, y, PreciseColor.TRANSPARENT);
	}

	@Override
	public TintedRect createRect(TintedNode upperLeft, TintedNode lowerLeft, TintedNode lowerRight, TintedNode upperRight) {
		return new TintedRect(upperLeft, lowerLeft, lowerRight, upperRight);
	}

	/*
	 * PROPERTIES
	 */

	@Override
	public String toString() {
		return getClass().getName() + "[" + formatFields() + "]";
	}

	public String toString(boolean matrix) {
		if (!matrix) return toString();
		return getClass().getName() + ":\n" +
					   "[" + upperLeft + ", " + upperRight + "],\n" +
					   "[" + lowerLeft + ", " + lowerRight + "]";
	}

	public String toShortString() {
		return "[" + formatFields(false) + "]";
	}

	/*

	// === Drawers ===
	private final double
			DEFAULT_RADIUS = 120, DEFAULT_ATTENUATION = 0.2,
			DEFAULT_OPACITY = 0.37, DEFAULT_FADED_OPACITY = 0.17,
			FIXED_X = 0, FIXED_Y = 16;

	public TintedRect draw(MatrixStack matrixStack) {
		new Equator.Drawer(matrixStack).tintedRect(this);
		return this;
	}

	public TintedRect drawShadowWithScissor(MatrixStack matrixStack, double radius, double attenuation, double x, double y) {
		new Equator.Drawer(matrixStack).rectShadowWithScissor(expand(radius).shift(x, y).transparent(), this, attenuation);
		return this;
	}

	public TintedRect drawShadowWithScissor(MatrixStack matrixStack, double attenuation, double x, double y) {
		return drawShadowWithScissor(matrixStack, DEFAULT_RADIUS, attenuation, x, y);
	}

	public TintedRect drawShadowWithScissor(MatrixStack matrixStack, double x, double y) {
		return drawShadowWithScissor(matrixStack, DEFAULT_ATTENUATION, x, y);
	}

	public TintedRect drawShadowWithScissor(MatrixStack matrixStack, double attenuation) {
		return drawShadowWithScissor(matrixStack, attenuation, 0, 0);
	}

	public TintedRect drawShadowWithScissor(MatrixStack matrixStack) {
		return drawShadowWithScissor(matrixStack, DEFAULT_ATTENUATION);
	}

	public TintedRect drawShadow(MatrixStack matrixStack, double radius, double attenuation, Node shift) {
		new Equator.Drawer(matrixStack).rectShadow(expand(radius).shiftBy(shift).transparent(), this, attenuation);
		return this;
	}

	public TintedRect drawShadow(MatrixStack matrixStack, double radius, double attenuation) {
		return drawShadow(matrixStack, radius, attenuation, Node.ORIGIN);
	}

	public TintedRect drawShadow(MatrixStack matrixStack, double radius, Node shift) {
		return drawShadow(matrixStack, radius, DEFAULT_ATTENUATION, shift);
	}

	public TintedRect drawShadow(MatrixStack matrixStack, double radius) {
		return drawShadow(matrixStack, radius, Node.ORIGIN);
	}

	public TintedRect drawShadow(MatrixStack matrixStack, Node shift) {
		return drawShadow(matrixStack, DEFAULT_RADIUS, shift);
	}

	public TintedRect drawShadow(MatrixStack matrixStack) {
		return drawShadow(matrixStack, DEFAULT_RADIUS);
	}

	public TintedRect drawFixedShadow(MatrixStack matrixStack, PreciseColor lu, PreciseColor ld, PreciseColor rd, PreciseColor ru, double ratio, double clockwiseDegree) {
		ratio = MathHelper.clamp(ratio, 0, 1);
		new Equator.Drawer(matrixStack)
				.rectShadowWithScissor(
						swap(lu, ld, rd, ru)
								.expand(DEFAULT_RADIUS * ratio)
								.shiftToCenter(center().shift(FIXED_X, FIXED_Y).rotateBy(center(), clockwiseDegree))
								.transparent(),
						swap(
								lu.withOpacity(DEFAULT_FADED_OPACITY * ratio),
								ld.withOpacity(DEFAULT_OPACITY * ratio),
								rd.withOpacity(DEFAULT_OPACITY * ratio),
								ru.withOpacity(DEFAULT_FADED_OPACITY * ratio)
						), DEFAULT_ATTENUATION
				).tintedRect(this);
		return this;
	}

	public TintedRect drawFixedShadow(MatrixStack matrixStack, TintedRect shadowRect, double ratio, double clockwiseDegree) {
		return drawFixedShadow(matrixStack, shadowRect.upperLeft.nodeColor, shadowRect.lowerLeft.nodeColor, shadowRect.lowerRight.nodeColor, shadowRect.upperRight.nodeColor, ratio, clockwiseDegree);
	}

	public TintedRect drawFixedShadow(MatrixStack matrixStack, double ratio, double clockwiseDegree) {
		return drawFixedShadow(matrixStack, this.darker(), ratio, clockwiseDegree);
	}

	public TintedRect drawFixedShadow(MatrixStack matrixStack, double ratio) {
		return drawFixedShadow(matrixStack, this.darker(), ratio, 0);
	}

	public TintedRect drawFixedShadow(MatrixStack matrixStack) {
		return drawFixedShadow(matrixStack, 1);
	}

	public TintedRect drawFocusedShadow(MatrixStack matrixStack, PreciseColor lu, PreciseColor ld, PreciseColor rd, PreciseColor ru, double ratio) {
		return drawFixedShadow(matrixStack, lu, ld, rd, ru, ratio, center().clockwiseDegreeIncludeNegativeOrdinate());
	}

	public TintedRect drawFocusedShadow(MatrixStack matrixStack, TintedRect shadowRect, double ratio) {
		return drawFocusedShadow(matrixStack, shadowRect.upperLeft.nodeColor, shadowRect.lowerLeft.nodeColor, shadowRect.lowerRight.nodeColor, shadowRect.upperRight.nodeColor, ratio);
	}

	public TintedRect drawFocusedShadow(MatrixStack matrixStack, double ratio) {
		return drawFocusedShadow(matrixStack, this.darker(), ratio);
	}

	public TintedRect drawFocusedShadow(MatrixStack matrixStack) {
		return drawFocusedShadow(matrixStack, 1);
	}

	public TintedRect drawFullShadow(MatrixStack matrixStack, double radius, double attenuation) {
		return withOpacity(DEFAULT_FADED_OPACITY, DEFAULT_OPACITY, DEFAULT_OPACITY, DEFAULT_FADED_OPACITY)
					   .drawShadow(matrixStack, radius, attenuation);
	}

	public TintedRect drawFullShadow(MatrixStack matrixStack, double radius) {
		return drawFullShadow(matrixStack, radius, DEFAULT_ATTENUATION);
	}

	public TintedRect drawFullShadow(MatrixStack matrixStack) {
		return drawFullShadow(matrixStack, DEFAULT_RADIUS);
	}

	 */
}
