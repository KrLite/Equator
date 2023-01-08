package net.krlite.equator.geometry;

import net.krlite.equator.core.FieldFormattable;
import net.krlite.equator.core.HashCodeComparable;
import net.krlite.equator.geometry.core.ILine;

public class Line extends HashCodeComparable implements ILine<Line, Node>, FieldFormattable {

	/*
	 * FIELDS
	 */

	private final double fA, fB, fC;

	/*
	 * CONSTRUCTORS
	 */

	public Line(double fA, double fB, double fC) {
		this.fA = fA;
		this.fB = fB;
		this.fC = fC;
	}

	public Line(Node n1, Node n2) {
		this(n1.y() - n2.y(), n2.x() - n1.x(), n1.x() * n2.y() - n2.x() * n1.y());
	}

	/*
	 * ATTRIBUTES
	 */

	@Override
	public double fA() {
		return fA;
	}

	@Override
	public double fB() {
		return fB;
	}

	@Override
	public double fC() {
		return fC;
	}

	/*
	 * OBJECT METHODS
	 */

	@Override
	public Node createNode(double x, double y) {
		return new Node(x, y);
	}

	@Override
	public Line createLine(double a, double b, double c) {
		return new Line(a, b, c);
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
		return getClass().getSimpleName() + "(" + formatFields(false) + ")";
	}
}
