package net.krlite.equator.geometry;

import net.krlite.equator.color.PreciseColor;
import net.krlite.equator.core.HashCodeComparable;
import net.krlite.equator.geometry.core.IRect;
import net.krlite.equator.render.Equator;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.Objects;

public class TintedRect extends HashCodeComparable implements IRect<TintedRect, TintedNode> {
	/*
	 * FIELDS
	 */
	
	public final TintedNode upperLeft, lowerLeft, lowerRight, upperRight;
	
	/*
	 * CONSTRUCTORS
	 */

	public TintedRect(@NotNull TintedNode upperLeft, @NotNull TintedNode lowerLeft, @NotNull TintedNode lowerRight, @NotNull TintedNode upperRight) {
		this.upperLeft = upperLeft;
		this.lowerLeft = lowerLeft;
		this.lowerRight = lowerRight;
		this.upperRight = upperRight;
	}

	public TintedRect(
			@NotNull Rect rect,
			@NotNull PreciseColor upperLeft, @NotNull PreciseColor lowerLeft,
			@NotNull PreciseColor lowerRight, @NotNull PreciseColor upperRight
	) {
		this(
				new TintedNode(rect.upperLeft, upperLeft),
				new TintedNode(rect.lowerLeft, lowerLeft),
				new TintedNode(rect.lowerRight, lowerRight),
				new TintedNode(rect.upperRight, upperRight)
		);
	}

	public TintedRect(
			@NotNull Rect rect,
			@NotNull Color upperLeft, @NotNull Color lowerLeft,
			@NotNull Color lowerRight, @NotNull Color upperRight
	) {
		this(rect, PreciseColor.of(upperLeft), PreciseColor.of(lowerLeft), PreciseColor.of(lowerRight), PreciseColor.of(upperRight));
	}

	public TintedRect(@NotNull Rect rect, @NotNull PreciseColor preciseColor) {
		this(rect, preciseColor, preciseColor, preciseColor, preciseColor);
	}

	public TintedRect(@NotNull Rect rect, @Nullable Color color) {
		this(rect, PreciseColor.of(color));
	}

	public TintedRect(@NotNull Rect rect) {
		this(rect, PreciseColor.TRANSPARENT);
	}

	public TintedRect(
			@NotNull PreciseColor upperLeft, @NotNull PreciseColor lowerLeft,
			@NotNull PreciseColor lowerRight, @NotNull PreciseColor upperRight
	) {
		this(Rect.SCREEN(), upperLeft, lowerLeft, lowerRight, upperRight);
	}

	public TintedRect(
			@Nullable Color upperLeft, @Nullable Color lowerLeft,
			@Nullable Color lowerRight, @Nullable Color upperRight
	) {
		this(Rect.SCREEN(), upperLeft, lowerLeft, lowerRight, upperRight);
	}

	public TintedRect(@NotNull PreciseColor preciseColor) {
		this(Rect.SCREEN(), preciseColor);
	}

	public TintedRect(@Nullable Color color) {
		this(Rect.SCREEN(), color);
	}

	public TintedRect() {
		this(Rect.SCREEN());
	}
	
	/*
	 * CONVERSIONS
	 */
	
	/*
	 * ATTRIBUTES
	 */

	public PreciseColor meshColor(double abscissa, double ordinate) {
		abscissa = Math.max(0, Math.min(1, abscissa));
		ordinate = Math.max(0, Math.min(1, ordinate));

		return upperLeft.nodeColor().blend(upperRight.nodeColor(), abscissa)
					   .blend(lowerLeft.nodeColor().blend(lowerRight.nodeColor(), abscissa), ordinate);
	}
	
	/*
	 * OBJECT METHODS
	 */

	@Override
	public TintedNode createNode(double x, double y) {
		return new TintedNode(x, y, PreciseColor.TRANSPARENT);
	}

	public Rect toRect() {
		return new Rect(upperLeft, lowerLeft, lowerRight, upperRight);
	}

	public boolean visible() {
		return anyNodeHasColor() && !allNodesAreTransparent();
	}

	public boolean allNodesHaveColor() {
		return upperLeft.nodeColor.hasColor() && lowerLeft.nodeColor.hasColor() && lowerRight.nodeColor.hasColor() && upperRight.nodeColor.hasColor();
	}

	public boolean anyNodeHasColor() {
		return upperLeft.nodeColor.hasColor() || lowerLeft.nodeColor.hasColor() || lowerRight.nodeColor.hasColor() || upperRight.nodeColor.hasColor();
	}

	public boolean allNodesAreTranslucent() {
		return upperLeft.nodeColor.isTranslucent() && lowerLeft.nodeColor.isTranslucent() && lowerRight.nodeColor.isTranslucent() && upperRight.nodeColor.isTranslucent();
	}

	public boolean anyNodeIsTranslucent() {
		return upperLeft.nodeColor.isTranslucent() || lowerLeft.nodeColor.isTranslucent() || lowerRight.nodeColor.isTranslucent() || upperRight.nodeColor.isTranslucent();
	}

	public boolean allNodesAreTransparent() {
		return upperLeft.nodeColor.isTransparent() && lowerLeft.nodeColor.isTransparent() && lowerRight.nodeColor.isTransparent() && upperRight.nodeColor.isTransparent();
	}

	public boolean anyNodeIsTransparent() {
		return upperLeft.nodeColor.isTransparent() || lowerLeft.nodeColor.isTransparent() || lowerRight.nodeColor.isTransparent() || upperRight.nodeColor.isTransparent();
	}

	public boolean allNodesAreOpaque() {
		return upperLeft.nodeColor.isOpaque() && lowerLeft.nodeColor.isOpaque() && lowerRight.nodeColor.isOpaque() && upperRight.nodeColor.isOpaque();
	}

	public boolean anyNodeIsOpaque() {
		return upperLeft.nodeColor.isOpaque() || lowerLeft.nodeColor.isOpaque() || lowerRight.nodeColor.isOpaque() || upperRight.nodeColor.isOpaque();
	}

	public TintedRect withAlpha(int aLu, int aLd, int aRd, int aRu) {
		return new TintedRect(
				upperLeft.withAlpha(aLu),
				lowerLeft.withAlpha(aLd),
				lowerRight.withAlpha(aRd),
				upperRight.withAlpha(aRu)
		);
	}

	public TintedRect withOpacity(double oLu, double oLd, double oRd, double oRu) {
		return new TintedRect(
				upperLeft.withOpacity(oLu),
				lowerLeft.withOpacity(oLd),
				lowerRight.withOpacity(oRd),
				upperRight.withOpacity(oRu)
		);
	}

	public TintedRect withAlpha(int alpha) {
		return withAlpha(alpha, alpha, alpha, alpha);
	}

	public TintedRect withOpacity(double opacity) {
		return withOpacity(opacity, opacity, opacity, opacity);
	}

	public TintedRect brighter() {
		return new TintedRect(
				upperLeft.brighter(),
				lowerLeft.brighter(),
				lowerRight.brighter(),
				upperRight.brighter()
		);
	}

	public TintedRect dimmer() {
		return new TintedRect(
				upperLeft.dimmer(),
				lowerLeft.dimmer(),
				lowerRight.dimmer(),
				upperRight.dimmer()
		);
	}

	public TintedRect translucent() {
		return new TintedRect(
				upperLeft.moreTranslucent(),
				lowerLeft.moreTranslucent(),
				lowerRight.moreTranslucent(),
				upperRight.moreTranslucent()
		);
	}

	public TintedRect transparent() {
		return new TintedRect(
				upperLeft.transparent(),
				lowerLeft.transparent(),
				lowerRight.transparent(),
				upperRight.transparent()
		);
	}

	public TintedRect opaque() {
		return new TintedRect(
				upperLeft.opaque(),
				lowerLeft.opaque(),
				lowerRight.opaque(),
				upperRight.opaque()
		);
	}

	public TintedRect halfTransparent() {
		return new TintedRect(
				upperLeft.halfTranslucent(),
				lowerLeft.halfTranslucent(),
				lowerRight.halfTranslucent(),
				upperRight.halfTranslucent()
		);
	}

	public TintedRect halfOpaque() {
		return new TintedRect(
				upperLeft.halfOpaque(),
				lowerLeft.halfOpaque(),
				lowerRight.halfOpaque(),
				upperRight.halfOpaque()
		);
	}

	public TintedRect lighter() {
		return new TintedRect(
				upperLeft.lighter(),
				lowerLeft.lighter(),
				lowerRight.lighter(),
				upperRight.lighter()
		);
	}

	public TintedRect darker() {
		return new TintedRect(
				upperLeft.darker(),
				lowerLeft.darker(),
				lowerRight.darker(),
				upperRight.darker()
		);
	}

	public PreciseColor getAverageColor() {
		return PreciseColor.average(upperLeft.nodeColor, lowerLeft.nodeColor, lowerRight.nodeColor, upperRight.nodeColor);
	}

	public TintedRect cut() {
		return swap(
				upperLeft.nodeColor.orElse(PreciseColor.average(lowerLeft.nodeColor, upperRight.nodeColor).transparent()),
				lowerLeft.nodeColor.orElse(PreciseColor.average(upperLeft.nodeColor, lowerRight.nodeColor).transparent()),
				lowerRight.nodeColor.orElse(PreciseColor.average(lowerLeft.nodeColor, upperRight.nodeColor).transparent()),
				upperRight.nodeColor.orElse(PreciseColor.average(upperLeft.nodeColor, lowerRight.nodeColor).transparent())
		);
	}

	public TintedRect cut(TintedRect fallback) {
		return swap(
				upperLeft.nodeColor.orElse(fallback.upperLeft.nodeColor), lowerLeft.nodeColor.orElse(fallback.lowerLeft.nodeColor),
				lowerRight.nodeColor.orElse(fallback.lowerRight.nodeColor), upperRight.nodeColor.orElse(fallback.upperRight.nodeColor)
		);
	}

	public TintedRect swap(@NotNull Rect rect) {
		return new TintedRect(rect, upperLeft.nodeColor, lowerLeft.nodeColor, lowerRight.nodeColor, upperRight.nodeColor);
	}

	public TintedRect swap(@NotNull TintedNode lu, @NotNull TintedNode ld, @NotNull TintedNode rd, @NotNull TintedNode ru) {
		return new TintedRect(lu, ld, rd, ru);
	}

	public TintedRect swap(@NotNull PreciseColor lu, @NotNull PreciseColor ld, @NotNull PreciseColor rd, @NotNull PreciseColor ru) {
		return new TintedRect(
				this.upperLeft.swap(lu),
				this.lowerLeft.swap(ld),
				this.lowerRight.swap(rd),
				this.upperRight.swap(ru)
		);
	}

	public TintedRect swap(@NotNull PreciseColor other) {
		return swap(other, other, other, other);
	}

	public boolean contains(@NotNull Node node) {
		return toRect().inRect(node);
	}

	public TintedNode center() {
		return new TintedNode(toRect().center(), getAverageColor());
	}

	public TintedRect shift(double x, double y) {
		return swap(toRect().shift(x, y));
	}

	public TintedRect shiftBy(@NotNull Node node) {
		return swap(toRect().shiftBy(node));
	}

	public TintedRect shiftToCenter(@NotNull Node node) {
		return swap(toRect().shiftToCenter(node));
	}

	public TintedRect shiftToCenter(double x, double y) {
		return swap(toRect().shiftToCenter(x, y));
	}

	public TintedRect scale(double scale) {
		return scale(toRect().center(), scale);
	}

	public TintedRect scale(double abscissa, double ordinate) {
		return scale(toRect().center(), abscissa, ordinate);
	}

	public TintedRect scale(@NotNull Node origin, double scale) {
		return swap(toRect().scale(origin, scale));
	}

	public TintedRect scale(@NotNull Node origin, double x, double y) {
		return swap(toRect().scale(origin, x, y));
	}

	public TintedRect expand(double x, double y) {
		return swap(toRect().expand(x, y));
	}

	public TintedRect expand(double expand) {
		return expand(expand, expand);
	}

	public TintedRect blendColor(@NotNull TintedRect other) {
		return blendColor(other, 0.5);
	}

	public TintedRect blendColor(@NotNull TintedRect other, double ratio) {
		return interpolate(other.swap(toRect()), ratio);
	}

	public TintedRect interpolate(@NotNull TintedRect other, double ratio) {
		return swap(
				upperLeft.interpolate(other.upperLeft, ratio),
				lowerLeft.interpolate(other.lowerLeft, ratio),
				lowerRight.interpolate(other.lowerRight, ratio),
				upperRight.interpolate(other.upperRight, ratio)
		);
	}

	public TintedRect interpolate(@NotNull TintedRect other) {
		return interpolate(other, 0.5);
	}

	public TintedRect rotate(@NotNull Node origin, double clockwiseDegree) {
		return swap(toRect().rotate(origin, clockwiseDegree));
	}

	public TintedRect rotate(double clockwiseDegree) {
		return swap(toRect().rotate(clockwiseDegree));
	}

	public TintedRect stretchLu(@NotNull TintedNode lu, double ratio) {
		return interpolate(new TintedRect(lu, lu, lowerRight, lu), ratio);
	}

	public TintedRect stretchLd(@NotNull TintedNode ld, double ratio) {
		return interpolate(new TintedRect(ld, ld, ld, upperRight), ratio);
	}

	public TintedRect stretchRd(@NotNull TintedNode rd, double ratio) {
		return interpolate(new TintedRect(upperLeft, rd, rd, rd), ratio);
	}

	public TintedRect stretchRu(@NotNull TintedNode ru, double ratio) {
		return interpolate(new TintedRect(ru, lowerLeft, ru, ru), ratio);
	}

	public TintedRect stretchLu(@NotNull TintedNode lu) {
		return stretchLu(lu, 1);
	}

	public TintedRect stretchLd(@NotNull TintedNode ld) {
		return stretchLd(ld, 1);
	}

	public TintedRect stretchRd(@NotNull TintedNode rd) {
		return stretchRd(rd, 1);
	}

	public TintedRect stretchRu(@NotNull TintedNode ru) {
		return stretchRu(ru, 1);
	}

	public TintedRect stretchFromTop(double y, double ratio) {
		return interpolate(new TintedRect(new TintedNode(upperLeft.x, y, upperLeft.nodeColor), lowerLeft, lowerRight, new TintedNode(upperRight.x, y, upperRight.nodeColor)), ratio);
	}

	public TintedRect stretchFromBottom(double y, double ratio) {
		return interpolate(new TintedRect(upperLeft, new TintedNode(lowerLeft.x, y, lowerLeft.nodeColor), new TintedNode(lowerRight.x, y, lowerRight.nodeColor), upperRight), ratio);
	}

	public TintedRect stretchFromLeft(double x, double ratio) {
		return interpolate(new TintedRect(new TintedNode(x, upperLeft.y, upperLeft.nodeColor), new TintedNode(x, lowerLeft.y, lowerLeft.nodeColor), lowerRight, upperRight), ratio);
	}

	public TintedRect stretchFromRight(double x, double ratio) {
		return interpolate(new TintedRect(upperLeft, lowerLeft, new TintedNode(x, lowerRight.y, lowerLeft.nodeColor), new TintedNode(x, upperRight.y, upperRight.nodeColor)), ratio);
	}

	public TintedRect stretchFromTop(double y) {
		return stretchFromTop(y, 1);
	}

	public TintedRect stretchFromBottom(double y) {
		return stretchFromBottom(y, 1);
	}

	public TintedRect stretchFromLeft(double x) {
		return stretchFromLeft(x, 1);
	}

	public TintedRect stretchFromRight(double x) {
		return stretchFromRight(x, 1);
	}

	public TintedRect squeezeFromTop(double ratio) {
		return swap(toRect().squeezeFromTop(ratio));
	}

	public TintedRect squeezeFromBottom(double ratio) {
		return swap(toRect().squeezeFromBottom(ratio));
	}

	public TintedRect squeezeFromLeft(double ratio) {
		return swap(toRect().squeezeFromLeft(ratio));
	}

	public TintedRect squeezeFromRight(double ratio) {
		return swap(toRect().squeezeFromRight(ratio));
	}

	public TintedRect flipHorizontal(double ratio) {
		return interpolate(new TintedRect(upperRight, lowerRight, lowerLeft, upperLeft), ratio);
	}

	public TintedRect flipVertical(double ratio) {
		return interpolate(new TintedRect(lowerLeft, upperLeft, upperRight, lowerRight), ratio);
	}

	public TintedRect flipDiagonalLuRd(double ratio) {
		return interpolate(new TintedRect(lowerRight, lowerLeft, upperLeft, upperRight), ratio);
	}

	public TintedRect flipDiagonalRuLd(double ratio) {
		return interpolate(new TintedRect(upperLeft, upperRight, lowerRight, lowerLeft), ratio);
	}

	public TintedRect flipHorizontal() {
		return flipHorizontal(1);
	}

	public TintedRect flipVertical() {
		return flipVertical(1);
	}

	public TintedRect flipDiagonalLuRd() {
		return flipDiagonalLuRd(1);
	}

	public TintedRect flipDiagonalRuLd() {
		return flipDiagonalRuLd(1);
	}

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

	@Override
	public int hashCode() {
		return Objects.hash(upperLeft, lowerLeft, lowerRight, upperRight);
	}

	public boolean equals(@NotNull TintedRect other) {
		return hashCode() == other.hashCode();
	}

	@Override
	public boolean equals(@NotNull Object other) {
		if (other instanceof TintedRect) return equals((TintedRect) other);
		return false;
	}

	@Override
	public String toString() {
		return getClass().getName() + "[" + ", lu=" + upperLeft.toString() + ", ld=" + lowerLeft.toString() + ", rd=" + lowerRight.toString() + ", ru=" + upperRight.toString() + "]";
	}

	public String toString(boolean matrix) {
		if (!matrix) return toString();
		return getClass().getName() + ":\n" +
					   "[" + upperLeft.toString() + ", " + upperRight.toString() + "],\n" +
					   "[" + lowerLeft.toString() + ", " + lowerRight.toString() + "]";
	}

	public String toShortString() {
		return "TintedRect[" + upperLeft.toShortString() + ", " + lowerLeft.toShortString() + ", " + lowerRight.toShortString() + ", " + upperRight.toShortString() + "]";
	}
}
