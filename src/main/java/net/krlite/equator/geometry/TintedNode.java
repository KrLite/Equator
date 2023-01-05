package net.krlite.equator.geometry;

import net.krlite.equator.color.PreciseColor;
import net.krlite.equator.color.core.IPreciseColor;
import net.krlite.equator.core.FieldFormattable;
import net.krlite.equator.core.HashCodeComparable;
import net.krlite.equator.geometry.core.INode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.awt.*;

public class TintedNode extends HashCodeComparable
		implements FieldFormattable, INode<TintedNode>, IPreciseColor<TintedNode> {
	/*
	 * FIELDS
	 */

	@NotNull
	private final Node node;
	@NotNull
	private final PreciseColor nodeColor;

	/*
	 * CONSTRUCTORS
	 */

	public TintedNode(@NotNull Node node, @NotNull PreciseColor nodeColor) {
		this.node = node;
		this.nodeColor = nodeColor;
	}

	public TintedNode(double x, double y, @NotNull PreciseColor nodeColor) {
		this(new Node(x, y), nodeColor);
	}

	public TintedNode(double x, double y, @NotNull Color nodeColor) {
		this(x, y, PreciseColor.of(nodeColor));
	}

	/*
	 * CONVERSIONS
	 */

	public Node unBind() {
		return node;
	}

	/*
	 * ATTRIBUTES
	 */

	@Override
	public double x() {
		return node.x();
	}

	@Override
	public double y() {
		return node.y();
	}

	@Override
	public double red() {
		return nodeColor.red();
	}

	@Override
	public double green() {
		return nodeColor.green();
	}

	@Override
	public double blue() {
		return nodeColor.blue();
	}

	@Override
	public double alpha() {
		return nodeColor.alpha();
	}

	/*
	 * OBJECT OPERATIONS
	 */

	protected TintedNode swap(@NotNull Node another) {
		return new TintedNode(another, nodeColor);
	}

	protected TintedNode swap(@NotNull PreciseColor another) {
		return new TintedNode(node, another);
	}

	@Override
	public TintedNode createNode(double x, double y) {
		return new TintedNode(x, y, nodeColor);
	}

	@Override
	public TintedNode createPreciseColor(double r, double g, double b, double a) {
		return new TintedNode(node, new PreciseColor(r, g, b, a));
	}

	@Override
	public TintedNode withAlpha(@Range(from = 0, to = 255) int alpha) {
		return swap(nodeColor.withAlpha(alpha));
	}

	@Override
	public TintedNode withOpacity(double opacity) {
		return swap(nodeColor.withOpacity(opacity));
	}

	@Override
	public TintedNode brighter() {
		return swap(nodeColor.brighter());
	}

	@Override
	public TintedNode dimmer() {
		return swap(nodeColor.dimmer());
	}

	@Override
	public TintedNode moreTranslucent() {
		return swap(nodeColor.moreTranslucent());
	}

	@Override
	public TintedNode lessTranslucent() {
		return swap(nodeColor.lessTranslucent());
	}

	@Override
	public TintedNode transparent() {
		return swap(nodeColor.transparent());
	}

	@Override
	public TintedNode opaque() {
		return swap(nodeColor.opaque());
	}

	@Override
	public TintedNode halfTransparent() {
		return swap(nodeColor.halfTransparent());
	}

	@Override
	public TintedNode halfOpaque() {
		return swap(nodeColor.halfOpaque());
	}

	/*
	 * PROPERTIES
	 */

	@Override
	public String toString() {
		return getClass().getSimpleName() + "{" + formatFields() + "}";
	}

	@Override
	public String toShortString() {
		return "(" + formatFields(false) + ")";
	}
}
