package net.krlite.equator.geometry;

import net.krlite.equator.color.PreciseColor;
import net.krlite.equator.core.FieldFormattable;
import net.krlite.equator.core.HashCodeComparable;
import net.krlite.equator.geometry.core.IRect;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class Rect extends HashCodeComparable implements IRect<Rect, Node>, FieldFormattable {
	/*
	 * BASICS
	 */

	public static Rect ORIGIN() {
		return new Rect(Node.ORIGIN(), Node.ORIGIN());
	}

	public static Rect CENTER() {
		return new Rect(Node.CENTER(), Node.CENTER());
	}

	public static Rect CENTER(double width, double height) {
		return new Rect(Node.CENTER().append(-width / 2, -height / 2), Node.CENTER().append(width / 2, height / 2));
	}

	public static Rect SCALED(double scaling) {
		return SCREEN().interpolate(CENTER(), scaling);
	}

	public static Rect SCREEN() {
		return new Rect(Node.ORIGIN(), Node.FULL());
	}

	/*
	 * FIELDS
	 */

	private final Node upperLeft, lowerLeft, lowerRight, upperRight;

	/*
	 * CONSTRUCTORS
	 */

	public Rect(@NotNull Node upperLeft, @NotNull Node lowerLeft, @NotNull Node lowerRight, @NotNull Node upperRight) {
		this.upperLeft = upperLeft;
		this.lowerLeft = lowerLeft;
		this.lowerRight = lowerRight;
		this.upperRight = upperRight;
	}

	public Rect(@NotNull Node upperLeft, @NotNull Node lowerRight) {
		this(upperLeft, new Node(upperLeft.x(), lowerRight.y()), lowerRight, new Node(lowerRight.x(), upperLeft.y()));
	}

	public Rect(@NotNull Node center, double width, double height) {
		this(
				new Node(center.x() - width / 2, center.y() - height / 2),
				new Node(center.x() + width / 2, center.y() + height / 2)
		);
	}

	public Rect(double x, double y, double width, double height) {
		this(new Node(x, y), new Node(x + width, y + height));
	}

	/*
	 * CONVERSIONS
	 */

	public TintedRect bind(PreciseColor upperLeft, PreciseColor lowerLeft, PreciseColor lowerRight, PreciseColor upperRight) {
		return new TintedRect(
				this.upperLeft.bind(upperLeft),
				this.lowerLeft.bind(lowerLeft),
				this.lowerRight.bind(lowerRight),
				this.upperRight.bind(upperRight)
		);
	}

	public TintedRect bind(PreciseColor tint) {
		return bind(tint, tint, tint, tint);
	}

	/*
	 * ATTRIBUTES
	 */

	@Override
	public Node upperLeft() {
		return upperLeft;
	}

	@Override
	public Node lowerLeft() {
		return lowerLeft;
	}

	@Override
	public Node lowerRight() {
		return lowerRight;
	}

	@Override
	public Node upperRight() {
		return upperRight;
	}

	public Node limit(Node node) {
		if (inRect(node)) {
			return node;
		} else {
			Line
					uL = new Line(center(), upperLeft()),
					lL = new Line(center(), lowerLeft()),
					lR = new Line(center(), lowerRight()),
					uR = new Line(center(), upperRight()),
					line = new Line(center(), node);
			if (uL.onLine(node)) {
				return upperLeft;
			} else if (lL.onLine(node)) {
				return lowerLeft;
			} else if (lR.onLine(node)) {
				return lowerRight;
			} else if (uR.onLine(node)) {
				return upperRight;
			} else if (line.isIncludedBy(uL, lL)) {
				return upperLeft().interpolate(lowerLeft(), uL.positiveInclude(lL) / uL.positiveInclude(line));
			} else if (line.isIncludedBy(lL, lR)) {
				return lowerLeft().interpolate(lowerRight(), lL.positiveInclude(lR) / lL.positiveInclude(line));
			} else if (line.isIncludedBy(lR, uR)) {
				return lowerRight().interpolate(upperRight(), lR.positiveInclude(uR) / lR.positiveInclude(line));
			} else if (line.isIncludedBy(uR, uL)) {
				return upperRight().interpolate(upperLeft(), uR.positiveInclude(uL) / uR.positiveInclude(line));
			} else {
				return center();
			}
		}
	}

	/*
	 * OBJECT METHODS
	 */

	@Override
	public Node createNode(double x, double y) {
		return new Node(x, y);
	}

	@Override
	public Rect createRect(Node upperLeft, Node lowerLeft, Node lowerRight, Node upperRight) {
		return new Rect(upperLeft, lowerLeft, lowerRight, upperRight);
	}

	/*
	 * CLASS PROPERTIES
	 */

	@Override
	public String toString() {
		return getClass().getSimpleName() + "[" + formatFields() + "]";
	}

	public String toString(boolean matrix) {
		if (!matrix) return toString();
		return getClass().getSimpleName() + ":\n" +
					   "[" + upperLeft.toString() + ", " + upperRight.toString() + "],\n" +
					   "[" + lowerLeft.toString() + ", " + lowerRight.toString() + "]";
	}

	public String toShortString() {
		return "[" + formatFields(false) + "]";
	}
}
