package net.krlite.equator.base.geometry;

import net.krlite.equator.base.color.PreciseColor;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class TintedNode extends Node {
	@NotNull
	public final PreciseColor nodeColor;

	public TintedNode(Node node, @NotNull PreciseColor nodeColor) {
		super(node.x, node.y);
		this.nodeColor = nodeColor;
	}

	public TintedNode(double x, double y, @NotNull PreciseColor nodeColor) {
		super(x, y);
		this.nodeColor = nodeColor;
	}

	public TintedNode(Node node) {
		this(node, PreciseColor.TRANSPARENT);
	}

	public TintedNode(double x, double y) {
		this(x, y, PreciseColor.TRANSPARENT);
	}

	public TintedNode() {
		this(0, 0);
	}

	public TintedNode replace(@NotNull Node other) {
		return new TintedNode(other, nodeColor);
	}

	public TintedNode replace(@NotNull PreciseColor other) {
		return new TintedNode(this, other);
	}

	public TintedNode blend(@NotNull PreciseColor other, double ratio) {
		return new TintedNode(this, nodeColor.blend(other, ratio));
	}

	public TintedNode blend(@NotNull PreciseColor other) {
		return blend(other, 0.5);
	}

	public TintedNode blendAll(@NotNull PreciseColor... others) {
		return new TintedNode(this, nodeColor.blendAll(others));
	}

	public TintedNode shift(double x, double y) {
		return new TintedNode(this.x + x, this.y + y, nodeColor);
	}

	public TintedNode shiftBy(@NotNull Node other) {
		return shift(other.x, other.y);
	}

	public TintedNode shiftTo(@NotNull Node other) {
		return new TintedNode(other, nodeColor);
	}

	public TintedNode scale(@NotNull Node other, double x, double y) {
		return replace(super.scale(other, x, y));
	}

	public TintedNode scale(@NotNull Node other, double scale) {
		return replace(super.scale(other, scale));
	}

	public TintedNode scale(@NotNull Node other) {
		return replace(super.scale(other));
	}

	public TintedNode interpolate(@NotNull TintedNode other, double ratio) {
		return new TintedNode(super.scale(other, ratio), nodeColor.blend(other.nodeColor, ratio));
	}

	public TintedNode interpolate(@NotNull TintedNode other) {
		return interpolate(other, 0.5);
	}

	public TintedNode rotate(@NotNull Node origin, double clockwiseDegree) {
		return replace(super.rotate(origin, clockwiseDegree));
	}

	public boolean hasColor() {
		return nodeColor.hasColor();
	}

	public TintedNode orElse(PreciseColor fallback) {
		return replace(nodeColor.orElse(fallback));
	}

	public boolean isTranslucent() {
		return nodeColor.isTranslucent();
	}

	public boolean isTransparent() {
		return nodeColor.isTransparent();
	}

	public boolean isOpaque() {
		return nodeColor.isOpaque();
	}

	public TintedNode withAlpha(int alpha) {
		return new TintedNode(this, nodeColor.hasColor() ? nodeColor.withAlpha(alpha) : PreciseColor.TRANSPARENT);
	}

	public TintedNode withOpacity(double opacity) {
		return new TintedNode(this, nodeColor.hasColor() ? nodeColor.withOpacity(opacity) : PreciseColor.TRANSPARENT);
	}

	public TintedNode brighter() {
		return new TintedNode(this, nodeColor.brighter());
	}

	public TintedNode dimmer() {
		return new TintedNode(this, nodeColor.dimmer());
	}

	public TintedNode moreTranslucent() {
		return new TintedNode(this, nodeColor.moreTranslucent());
	}

	public TintedNode lessTranslucent() {
		return new TintedNode(this, nodeColor.lessTranslucent());
	}

	public TintedNode transparent() {
		return new TintedNode(this, nodeColor.transparent());
	}

	public TintedNode opaque() {
		return new TintedNode(this, nodeColor.opaque());
	}

	public TintedNode halfTranslucent() {
		return new TintedNode(this, nodeColor.halfTransparent());
	}

	public TintedNode halfOpaque() {
		return new TintedNode(this, nodeColor.halfOpaque());
	}

	public TintedNode lighter() {
		return new TintedNode(this, nodeColor.lighter());
	}

	public TintedNode darker() {
		return new TintedNode(this, nodeColor.darker());
	}

	public boolean equals(@NotNull TintedNode other) {
		return hashCode() == other.hashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) return true;
		if (other instanceof TintedNode) return equals((TintedNode) other);
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y, nodeColor);
	}

	@Override
	public String toString() {
		return getClass().getName() + "{" + "x=" + x + ", y=" + y + ", nodeColor=" + nodeColor.toShortString() + "}";
	}

	@Override
	public String toShortString() {
		return "TintedNode" + "(" + x + ", " + y + ", " + nodeColor.toShortString() + ")";
	}
}
