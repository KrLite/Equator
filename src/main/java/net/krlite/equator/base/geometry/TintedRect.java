package net.krlite.equator.base.geometry;

import net.krlite.equator.base.PreciseColor;
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
		this(new Rect(), lu, ld, rd, ru);
	}

	public TintedRect(
			@Nullable Color lu, @Nullable Color ld,
			@Nullable Color rd, @Nullable Color ru
	) {
		this(new Rect(), lu, ld, rd, ru);
	}

	public TintedRect(@NotNull PreciseColor preciseColor) {
		this(new Rect(), preciseColor);
	}

	public TintedRect(@Nullable Color color) {
		this(new Rect(), color);
	}

	public TintedRect() {
		this(new Rect());
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

	public TintedRect withAlpha(int alpha) {
		return new TintedRect(
				lu.withAlpha(alpha),
				ld.withAlpha(alpha),
				rd.withAlpha(alpha),
				ru.withAlpha(alpha)
		);
	}

	public TintedRect withOpacity(double opacity) {
		return new TintedRect(
				lu.withOpacity(opacity),
				ld.withOpacity(opacity),
				rd.withOpacity(opacity),
				ru.withOpacity(opacity)
		);
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

	public PreciseColor getAverageColor() {
		return PreciseColor.average(lu.nodeColor, ld.nodeColor, rd.nodeColor, ru.nodeColor);
	}

	public TintedRect processTransparentColors() {
		return replace(
				lu.nodeColor.orElse(PreciseColor.average(ld.nodeColor, ru.nodeColor).transparent()),
				ld.nodeColor.orElse(PreciseColor.average(lu.nodeColor, rd.nodeColor).transparent()),
				rd.nodeColor.orElse(PreciseColor.average(ld.nodeColor, ru.nodeColor).transparent()),
				ru.nodeColor.orElse(PreciseColor.average(lu.nodeColor, rd.nodeColor).transparent())
		);
	}

	public TintedRect processTransparentColors(TintedRect fallback) {
		return replace(
				lu.nodeColor.orElse(fallback.lu.nodeColor), ld.nodeColor.orElse(fallback.ld.nodeColor),
				rd.nodeColor.orElse(fallback.rd.nodeColor), ru.nodeColor.orElse(fallback.ru.nodeColor)
		);
	}

	public TintedRect pr() {
		return processTransparentColors();
	}

	public TintedRect pr(TintedRect fallback) {
		return processTransparentColors(fallback);
	}

	public TintedRect replace(@NotNull Rect rect) {
		return new TintedRect(rect, lu.nodeColor, ld.nodeColor, rd.nodeColor, ru.nodeColor);
	}

	public TintedRect replace(@NotNull TintedNode lu, @NotNull TintedNode ld, @NotNull TintedNode rd, @NotNull TintedNode ru) {
		return new TintedRect(lu, ld, rd, ru);
	}

	public TintedRect replace(@NotNull PreciseColor lu, @NotNull PreciseColor ld, @NotNull PreciseColor rd, @NotNull PreciseColor ru) {
		return new TintedRect(
				this.lu.replace(lu),
				this.ld.replace(ld),
				this.rd.replace(rd),
				this.ru.replace(ru)
		);
	}

	public TintedRect replace(@NotNull PreciseColor other) {
		return replace(other, other, other, other);
	}

	public boolean contains(@NotNull Node node) {
		return toRect().contains(node);
	}

	public TintedRect shift(double x, double y) {
		return replace(toRect().shift(x, y));
	}

	public TintedRect shiftBy(@NotNull Node node) {
		return replace(toRect().shiftBy(node));
	}

	public TintedRect shiftToCenter(@NotNull Node node) {
		return replace(toRect().shiftToCenter(node));
	}

	public TintedRect scale(double scale) {
		return scale(toRect().center(), scale);
	}

	public TintedRect scale(double x, double y) {
		return scale(toRect().center(), x, y);
	}

	public TintedRect scale(@NotNull Node origin, double scale) {
		return replace(toRect().scale(origin, scale));
	}

	public TintedRect scale(@NotNull Node origin, double x, double y) {
		return replace(toRect().scale(origin, x, y));
	}

	public TintedRect expand(double x, double y) {
		return replace(toRect().expand(x, y));
	}

	public TintedRect expand(double expand) {
		return expand(expand, expand);
	}

	public TintedRect blendColor(@NotNull TintedRect other) {
		return blendColor(other, 0.5);
	}

	public TintedRect blendColor(@NotNull TintedRect other, double ratio) {
		return interpolate(other.replace(toRect()), ratio);
	}

	public TintedRect interpolate(@NotNull TintedRect other, double ratio) {
		return replace(
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
		return replace(toRect().rotate(origin, clockwiseDegree));
	}

	public TintedRect rotate(double clockwiseDegree) {
		return replace(toRect().rotate(clockwiseDegree));
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
		return replace(toRect().squeezeFromTop(ratio));
	}

	public TintedRect squeezeFromBottom(double ratio) {
		return replace(toRect().squeezeFromBottom(ratio));
	}

	public TintedRect squeezeFromLeft(double ratio) {
		return replace(toRect().squeezeFromLeft(ratio));
	}

	public TintedRect squeezeFromRight(double ratio) {
		return replace(toRect().squeezeFromRight(ratio));
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
