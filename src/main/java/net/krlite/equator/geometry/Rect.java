package net.krlite.equator.geometry;

import net.krlite.equator.base.HashCodeComparable;
import net.krlite.equator.core.ShortStringable;
import net.krlite.equator.core.SimpleOperations;

public class Rect extends HashCodeComparable implements ShortStringable, SimpleOperations {
	protected final Node leftTop, leftBottom, rightBottom, rightTop;

	public Node getLeftTop() {
		return leftTop;
	}

	public Node getLeftBottom() {
		return leftBottom;
	}

	public Node getRightBottom() {
		return rightBottom;
	}

	public Node getRightTop() {
		return rightTop;
	}

	public double getX() {
		return leftTop.getX();
	}

	public double getY() {
		return leftTop.getY();
	}

	public double getWidth() {
		return leftTop.distance(rightTop);
	}

	public double getHeight() {
		return leftTop.distance(leftBottom);
	}

	public Rect(double x, double y, double width, double height) {
		this.leftTop = new Node(x, y);
		this.leftBottom = new Node(x, y + height);
		this.rightBottom = new Node(x + width, y + height);
		this.rightTop = new Node(x + width, y);
	}

	public Rect(Node origin, double width, double height) {
		this(origin.getX(), origin.getY(), width, height);
	}

	public Rect(Rect origin, double width, double height) {
		this(origin.getX(), origin.getY(), width, height);
	}

	@Override
	public String toShortString() {
		return "[" + formatFields(false) + "]";
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "[" + formatFields() + "]";
	}
}
