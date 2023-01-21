package net.krlite.equator.geometry;

import net.krlite.equator.base.HashCodeComparable;
import net.krlite.equator.color.PreciseColor;
import net.krlite.equator.color.core.BasicRGBA;
import net.krlite.equator.core.ShortStringable;
import net.krlite.equator.core.SimpleOperations;

import java.awt.*;
import java.util.function.Function;

public class Node extends HashCodeComparable implements ShortStringable, SimpleOperations {
	protected final double x, y;

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public Node(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Tinted tint(BasicRGBA<?> tint) {
		return new Tinted(tint);
	}

	public double distance(Node another) {
		return Math.sqrt(Math.pow(getX() - another.getX(), 2) + Math.pow(getY() - another.getY(), 2));
	}

	public Node min(Node another) {
		return new Node(Math.min(getX(), another.getX()), Math.min(getY(), another.getY()));
	}

	public Node max(Node another) {
		return new Node(Math.max(getX(), another.getX()), Math.max(getY(), another.getY()));
	}

	public Node shift(Node another) {
		return new Node(getX() + another.getX(), getY() + another.getY());
	}

	public Node shift(double x, double y) {
		return shift(new Node(x, y));
	}

	public Node scale(Node another, double scale) {
		return new Node(getX() + (another.getX() - getX()) * scale, getY() + (another.getY() - getY()) * scale);
	}

	public Node scaleBy(Node origin, double scale) {
		return origin.scale(this, scale);
	}

	public Node interpolate(Node another, double ratio) {
		return new Node(blendValue(getX(), another.getX(), ratio), blendValue(getY(), another.getY(), ratio));
	}

	public Node rotate(Node another, double clockwiseAngle) {
		double angle = Math.toRadians(clockwiseAngle);
		double cos = Math.cos(angle);
		double sin = Math.sin(angle);
		double x = another.getX() - getX();
		double y = another.getY() - getY();
		return new Node(x * cos - y * sin + getX(), x * sin + y * cos + getY());
	}

	public Node rotateBy(Node origin, double clockwiseAngle) {
		return origin.rotate(this, clockwiseAngle);
	}

	public class Tinted extends PreciseColor {
		public static Tinted of(double x, double y, double red, double green, double blue, double alpha) {
			return new Node(x, y).new Tinted(red, green, blue, alpha);
		}

		public static Tinted of(double x, double y, double red, double green, double blue) {
			return new Node(x, y).new Tinted(red, green, blue);
		}

		public static Tinted of(Node node, BasicRGBA<?> tint) {
			return node.new Tinted(tint);
		}

		public static Tinted of(Node node, Color tint) {
			return node.new Tinted(tint);
		}

		public Node getNode() {
			return Node.this;
		}

		public PreciseColor getTint() {
			return this;
		}

		public Tinted(double red, double green, double blue, double alpha) {
			super(red, green, blue, alpha);
		}

		public Tinted(double red, double green, double blue) {
			this(red, green, blue, 1);
		}

		public Tinted(BasicRGBA<?> tint) {
			this(tint.getRed(), tint.getGreen(), tint.getBlue(), tint.getAlpha());
		}

		public Tinted(Color tint) {
			this(PreciseColor.of(tint));
		}

		public Tinted swap(Node another) {
			return another.new Tinted(this);
		}

		public Tinted operate(Function<Node, Node> operation) {
			return swap(operation.apply(getNode()));
		}

		@Override
		public String toShortString() {
			return super.toShortString() + "-" + getNode().toShortString();
		}

		@Override
		public String toString() {
			return getClass().getSimpleName() + "{" + super.toString() + ", " + getNode().toString() + "}";
		}
	}

	@Override
	public String toShortString() {
		return "(" + formatFields(false) + ")";
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "(" + formatFields() + ")";
	}
}
