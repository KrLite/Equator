package net.krlite.equator.base.geometry;

import net.krlite.equator.base.color.PreciseColor;
import net.krlite.equator.render.Equator;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.Objects;

public class TintedRect {
	public final TintedNode lu, ld, rd, ru;

	public TintedRect(@NotNull TintedNode lu, @NotNull TintedNode ld, @NotNull TintedNode rd, @NotNull TintedNode ru) {
		this.lu = lu;
		this.ld = ld;
		this.rd = rd;
		this.ru = ru;
	}

	public TintedRect(
			@NotNull Rect rect,
			@NotNull PreciseColor lu, @NotNull PreciseColor ld,
			@NotNull PreciseColor rd, @NotNull PreciseColor ru
	) {
		this(
				new TintedNode(rect.lu, lu),
				new TintedNode(rect.ld, ld),
				new TintedNode(rect.rd, rd),
				new TintedNode(rect.ru, ru)
		);
	}

	public TintedRect(
			@NotNull Rect rect,
			@NotNull Color lu, @NotNull Color ld,
			@NotNull Color rd, @NotNull Color ru
	) {
		this(rect, PreciseColor.of(lu), PreciseColor.of(ld), PreciseColor.of(rd), PreciseColor.of(ru));
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
			@NotNull PreciseColor lu, @NotNull PreciseColor ld,
			@NotNull PreciseColor rd, @NotNull PreciseColor ru
	) {
		this(Rect.full(), lu, ld, rd, ru);
	}

	public TintedRect(
			@Nullable Color lu, @Nullable Color ld,
			@Nullable Color rd, @Nullable Color ru
	) {
		this(Rect.full(), lu, ld, rd, ru);
	}

	public TintedRect(@NotNull PreciseColor preciseColor) {
		this(Rect.full(), preciseColor);
	}

	public TintedRect(@Nullable Color color) {
		this(Rect.full(), color);
	}

	public TintedRect() {
		this(Rect.full());
	}

	public TintedRect min(@NotNull TintedRect other) {
		return new TintedRect(
				new TintedNode(Math.min(lu.x, other.lu.x), Math.min(lu.y, other.lu.y), lu.nodeColor),
				new TintedNode(Math.max(ld.x, other.ld.x), Math.max(ld.y, other.ld.y), ld.nodeColor),
				new TintedNode(Math.max(rd.x, other.rd.x), Math.max(rd.y, other.rd.y), rd.nodeColor),
				new TintedNode(Math.min(ru.x, other.ru.x), Math.min(ru.y, other.ru.y), ru.nodeColor)
		);
	}

	public TintedRect max(@NotNull TintedRect other) {
		return new TintedRect(
				new TintedNode(Math.max(lu.x, other.lu.x), Math.max(lu.y, other.lu.y), lu.nodeColor),
				new TintedNode(Math.min(ld.x, other.ld.x), Math.min(ld.y, other.ld.y), ld.nodeColor),
				new TintedNode(Math.min(rd.x, other.rd.x), Math.min(rd.y, other.rd.y), rd.nodeColor),
				new TintedNode(Math.max(ru.x, other.ru.x), Math.max(ru.y, other.ru.y), ru.nodeColor)
		);
	}

	public Rect toRect() {
		return new Rect(lu, ld, rd, ru);
	}

	public boolean visible() {
		return anyNodeHasColor() && !allNodesAreTransparent();
	}

	public boolean allNodesHaveColor() {
		return lu.nodeColor.hasColor() && ld.nodeColor.hasColor() && rd.nodeColor.hasColor() && ru.nodeColor.hasColor();
	}

	public boolean anyNodeHasColor() {
		return lu.nodeColor.hasColor() || ld.nodeColor.hasColor() || rd.nodeColor.hasColor() || ru.nodeColor.hasColor();
	}

	public boolean allNodesAreTranslucent() {
		return lu.nodeColor.isTranslucent() && ld.nodeColor.isTranslucent() && rd.nodeColor.isTranslucent() && ru.nodeColor.isTranslucent();
	}

	public boolean anyNodeIsTranslucent() {
		return lu.nodeColor.isTranslucent() || ld.nodeColor.isTranslucent() || rd.nodeColor.isTranslucent() || ru.nodeColor.isTranslucent();
	}

	public boolean allNodesAreTransparent() {
		return lu.nodeColor.isTransparent() && ld.nodeColor.isTransparent() && rd.nodeColor.isTransparent() && ru.nodeColor.isTransparent();
	}

	public boolean anyNodeIsTransparent() {
		return lu.nodeColor.isTransparent() || ld.nodeColor.isTransparent() || rd.nodeColor.isTransparent() || ru.nodeColor.isTransparent();
	}

	public boolean allNodesAreOpaque() {
		return lu.nodeColor.isOpaque() && ld.nodeColor.isOpaque() && rd.nodeColor.isOpaque() && ru.nodeColor.isOpaque();
	}

	public boolean anyNodeIsOpaque() {
		return lu.nodeColor.isOpaque() || ld.nodeColor.isOpaque() || rd.nodeColor.isOpaque() || ru.nodeColor.isOpaque();
	}

	public TintedRect withAlpha(int aLu, int aLd, int aRd, int aRu) {
		return new TintedRect(
				lu.withAlpha(aLu),
				ld.withAlpha(aLd),
				rd.withAlpha(aRd),
				ru.withAlpha(aRu)
		);
	}

	public TintedRect withOpacity(double oLu, double oLd, double oRd, double oRu) {
		return new TintedRect(
				lu.withOpacity(oLu),
				ld.withOpacity(oLd),
				rd.withOpacity(oRd),
				ru.withOpacity(oRu)
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
				lu.brighter(),
				ld.brighter(),
				rd.brighter(),
				ru.brighter()
		);
	}

	public TintedRect dimmer() {
		return new TintedRect(
				lu.dimmer(),
				ld.dimmer(),
				rd.dimmer(),
				ru.dimmer()
		);
	}

	public TintedRect translucent() {
		return new TintedRect(
				lu.moreTranslucent(),
				ld.moreTranslucent(),
				rd.moreTranslucent(),
				ru.moreTranslucent()
		);
	}

	public TintedRect transparent() {
		return new TintedRect(
				lu.transparent(),
				ld.transparent(),
				rd.transparent(),
				ru.transparent()
		);
	}

	public TintedRect opaque() {
		return new TintedRect(
				lu.opaque(),
				ld.opaque(),
				rd.opaque(),
				ru.opaque()
		);
	}

	public TintedRect halfTransparent() {
		return new TintedRect(
				lu.halfTranslucent(),
				ld.halfTranslucent(),
				rd.halfTranslucent(),
				ru.halfTranslucent()
		);
	}

	public TintedRect halfOpaque() {
		return new TintedRect(
				lu.halfOpaque(),
				ld.halfOpaque(),
				rd.halfOpaque(),
				ru.halfOpaque()
		);
	}

	public TintedRect lighter() {
		return new TintedRect(
				lu.lighter(),
				ld.lighter(),
				rd.lighter(),
				ru.lighter()
		);
	}

	public TintedRect darker() {
		return new TintedRect(
				lu.darker(),
				ld.darker(),
				rd.darker(),
				ru.darker()
		);
	}

	public PreciseColor getAverageColor() {
		return PreciseColor.average(lu.nodeColor, ld.nodeColor, rd.nodeColor, ru.nodeColor);
	}

	public TintedRect cut() {
		return swap(
				lu.nodeColor.orElse(PreciseColor.average(ld.nodeColor, ru.nodeColor).transparent()),
				ld.nodeColor.orElse(PreciseColor.average(lu.nodeColor, rd.nodeColor).transparent()),
				rd.nodeColor.orElse(PreciseColor.average(ld.nodeColor, ru.nodeColor).transparent()),
				ru.nodeColor.orElse(PreciseColor.average(lu.nodeColor, rd.nodeColor).transparent())
		);
	}

	public TintedRect cut(TintedRect fallback) {
		return swap(
				lu.nodeColor.orElse(fallback.lu.nodeColor), ld.nodeColor.orElse(fallback.ld.nodeColor),
				rd.nodeColor.orElse(fallback.rd.nodeColor), ru.nodeColor.orElse(fallback.ru.nodeColor)
		);
	}

	public TintedRect swap(@NotNull Rect rect) {
		return new TintedRect(rect, lu.nodeColor, ld.nodeColor, rd.nodeColor, ru.nodeColor);
	}

	public TintedRect swap(@NotNull TintedNode lu, @NotNull TintedNode ld, @NotNull TintedNode rd, @NotNull TintedNode ru) {
		return new TintedRect(lu, ld, rd, ru);
	}

	public TintedRect swap(@NotNull PreciseColor lu, @NotNull PreciseColor ld, @NotNull PreciseColor rd, @NotNull PreciseColor ru) {
		return new TintedRect(
				this.lu.swap(lu),
				this.ld.swap(ld),
				this.rd.swap(rd),
				this.ru.swap(ru)
		);
	}

	public TintedRect swap(@NotNull PreciseColor other) {
		return swap(other, other, other, other);
	}

	public boolean contains(@NotNull Node node) {
		return toRect().contains(node);
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

	public TintedRect scale(double x, double y) {
		return scale(toRect().center(), x, y);
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
				lu.interpolate(other.lu, ratio),
				ld.interpolate(other.ld, ratio),
				rd.interpolate(other.rd, ratio),
				ru.interpolate(other.ru, ratio)
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
		return interpolate(new TintedRect(lu, lu, rd, lu), ratio);
	}

	public TintedRect stretchLd(@NotNull TintedNode ld, double ratio) {
		return interpolate(new TintedRect(ld, ld, ld, ru), ratio);
	}

	public TintedRect stretchRd(@NotNull TintedNode rd, double ratio) {
		return interpolate(new TintedRect(lu, rd, rd, rd), ratio);
	}

	public TintedRect stretchRu(@NotNull TintedNode ru, double ratio) {
		return interpolate(new TintedRect(ru, ld, ru, ru), ratio);
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
		return interpolate(new TintedRect(new TintedNode(lu.x, y, lu.nodeColor), ld, rd, new TintedNode(ru.x, y, ru.nodeColor)), ratio);
	}

	public TintedRect stretchFromBottom(double y, double ratio) {
		return interpolate(new TintedRect(lu, new TintedNode(ld.x, y, ld.nodeColor), new TintedNode(rd.x, y, rd.nodeColor), ru), ratio);
	}

	public TintedRect stretchFromLeft(double x, double ratio) {
		return interpolate(new TintedRect(new TintedNode(x, lu.y, lu.nodeColor), new TintedNode(x, ld.y, ld.nodeColor), rd, ru), ratio);
	}

	public TintedRect stretchFromRight(double x, double ratio) {
		return interpolate(new TintedRect(lu, ld, new TintedNode(x, rd.y, ld.nodeColor), new TintedNode(x, ru.y, ru.nodeColor)), ratio);
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
		return interpolate(new TintedRect(ru, rd, ld, lu), ratio);
	}

	public TintedRect flipVertical(double ratio) {
		return interpolate(new TintedRect(ld, lu, ru, rd), ratio);
	}

	public TintedRect flipDiagonalLuRd(double ratio) {
		return interpolate(new TintedRect(rd, ld, lu, ru), ratio);
	}

	public TintedRect flipDiagonalRuLd(double ratio) {
		return interpolate(new TintedRect(lu, ru, rd, ld), ratio);
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
		return drawFixedShadow(matrixStack, shadowRect.lu.nodeColor, shadowRect.ld.nodeColor, shadowRect.rd.nodeColor, shadowRect.ru.nodeColor, ratio, clockwiseDegree);
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
		return drawFixedShadow(matrixStack, lu, ld, rd, ru, ratio, center().getClockwiseDegreeIncludeNegativeY());
	}

	public TintedRect drawFocusedShadow(MatrixStack matrixStack, TintedRect shadowRect, double ratio) {
		return drawFocusedShadow(matrixStack, shadowRect.lu.nodeColor, shadowRect.ld.nodeColor, shadowRect.rd.nodeColor, shadowRect.ru.nodeColor, ratio);
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
		return Objects.hash(lu, ld, rd, ru);
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
		return getClass().getName() + "[" + ", lu=" + lu.toString() + ", ld=" + ld.toString() + ", rd=" + rd.toString() + ", ru=" + ru.toString() + "]";
	}

	public String toString(boolean matrix) {
		if (!matrix) return toString();
		return getClass().getName() + ":\n" +
					   "[" + lu.toString() + ", " + ru.toString() + "],\n" +
					   "[" + ld.toString() + ", " + rd.toString() + "]";
	}

	public String toShortString() {
		return "TintedRect[" + lu.toShortString() + ", " + ld.toShortString() + ", " + rd.toShortString() + ", " + ru.toShortString() + "]";
	}
}
