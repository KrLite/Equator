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
		implements INode<TintedNode>, IPreciseColor<TintedNode>, FieldFormattable {
	/*
	 * FIELDS
	 */

	@NotNull
	private final Node node;
	@NotNull
	private final PreciseColor tint;

	/*
	 * CONSTRUCTORS
	 */

	public TintedNode(@NotNull Node node, @NotNull PreciseColor tint) {
		this.node = node;
		this.tint = tint;
	}

	public TintedNode(double x, double y, @NotNull PreciseColor tint) {
		this(new Node(x, y), tint);
	}

	public TintedNode(double x, double y, @NotNull Color tint) {
		this(x, y, PreciseColor.from(tint));
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

	public PreciseColor tint() {
		return tint;
	}

	@Override
	public double red() {
		return tint.red();
	}

	@Override
	public double green() {
		return tint.green();
	}

	@Override
	public double blue() {
		return tint.blue();
	}

	@Override
	public double alpha() {
		return tint.alpha();
	}

	/*
	 * OBJECT METHODS
	 */

	protected TintedNode swap(@NotNull Node another) {
		return new TintedNode(another, tint);
	}

	protected TintedNode swap(@NotNull PreciseColor another) {
		return new TintedNode(node, another);
	}

	@Override
	public TintedNode createNode(double x, double y) {
		return new TintedNode(x, y, tint);
	}

	@Override
	public TintedNode createPreciseColor(double r, double g, double b, double a) {
		return new TintedNode(node, new PreciseColor(r, g, b, a));
	}

	@Override
	public TintedNode withAlpha(@Range(from = 0, to = 255) int alpha) {
		return swap(tint.withAlpha(alpha));
	}

	@Override
	public TintedNode withOpacity(double opacity) {
		return swap(tint.withOpacity(opacity));
	}

	@Override
	public TintedNode brighter() {
		return swap(tint.brighter());
	}

	@Override
	public TintedNode dimmer() {
		return swap(tint.dimmer());
	}

	@Override
	public TintedNode moreTranslucent() {
		return swap(tint.moreTranslucent());
	}

	@Override
	public TintedNode lessTranslucent() {
		return swap(tint.lessTranslucent());
	}

	@Override
	public TintedNode transparent() {
		return swap(tint.transparent());
	}

	@Override
	public TintedNode opaque() {
		return swap(tint.opaque());
	}

	@Override
	public TintedNode halfTransparent() {
		return swap(tint.halfTransparent());
	}

	@Override
	public TintedNode halfOpaque() {
		return swap(tint.halfOpaque());
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
