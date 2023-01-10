package net.krlite.equator.geometry;

import net.krlite.equator.core.FieldFormattable;
import net.krlite.equator.core.HashCodeComparable;
import net.krlite.equator.geometry.core.ILine;

public class Line extends HashCodeComparable implements ILine<Line, Node>, FieldFormattable {

	/*
	 * FIELDS
	 */

	/**
	 * ax + by + c = 0
	 */
	private final double a, b, c;

	/*
	 * CONSTRUCTORS
	 */

	public Line(double a, double b, double c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}

	public Line(Node n1, Node n2) {
		this(n1.y() - n2.y(), n2.x() - n1.x(), n1.x() * n2.y() - n2.x() * n1.y());
	}

	public Line(double k, double b) {
		this(k, -1, b);
	}

	/*
	 * ATTRIBUTES
	 */

	@Override
	public double a() {
		return a;
	}

	@Override
	public double b() {
		return b;
	}

	@Override
	public double c() {
		return c;
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
