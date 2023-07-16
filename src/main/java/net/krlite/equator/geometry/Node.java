package net.krlite.equator.geometry;

import net.krlite.equator.base.HashCodeComparable;
import net.krlite.equator.color.PreciseColor;
import net.krlite.equator.color.core.BasicRGBA;
import net.krlite.equator.core.Operatable;
import net.krlite.equator.core.ShortStringable;
import net.krlite.equator.core.SimpleOperations;
import net.krlite.equator.math.AngleFunctions;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.Vec3d;

import java.awt.*;
import java.util.function.Function;

public class Node extends HashCodeComparable implements ShortStringable, SimpleOperations {
	public static Node topScreen() {
		return new Node(MinecraftClient.getInstance().getWindow().getScaledWidth() / 2.0, 0);
	}

	public static Node leftTopScreen() {
		return new Node(0, 0);
	}

	public static Node leftScreen() {
		return new Node(0, MinecraftClient.getInstance().getWindow().getScaledHeight() / 2.0);
	}

	public static Node leftBottomScreen() {
		return new Node(0, MinecraftClient.getInstance().getWindow().getScaledHeight());
	}

	public static Node bottomScreen() {
		return new Node(MinecraftClient.getInstance().getWindow().getScaledWidth() / 2.0, MinecraftClient.getInstance().getWindow().getScaledHeight());
	}

	public static Node rightBottomScreen() {
		return new Node(MinecraftClient.getInstance().getWindow().getScaledWidth(),
				MinecraftClient.getInstance().getWindow().getScaledHeight());
	}

	public static Node rightScreen() {
		return new Node(MinecraftClient.getInstance().getWindow().getScaledWidth(),
				MinecraftClient.getInstance().getWindow().getScaledHeight() / 2.0);
	}

	public static Node rightTopScreen() {
		return new Node(MinecraftClient.getInstance().getWindow().getScaledWidth(), 0);
	}

	public static Node centerScreen() {
		return new Node(MinecraftClient.getInstance().getWindow().getScaledWidth() / 2.0,
				MinecraftClient.getInstance().getWindow().getScaledHeight() / 2.0);
	}

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

	public Tinted tint(PreciseColor tint) {
		return new Tinted(tint);
	}

	public Vec3d toVec3d(double z) {
		return new Vec3d(x, y, z);
	}

	public Vec3d toVec3d() {
		return toVec3d(0);
	}

	public double distanceTo(Node another) {
		return Math.sqrt(Math.pow(getX() - another.getX(), 2) + Math.pow(getY() - another.getY(), 2));
	}

	public double angleTo(Node another) {
		return AngleFunctions.negativeToClockwise(Math.toDegrees(Math.atan2(another.getY() - getY(), another.getX() - getX())));
	}

	public double crossWith(Node n1, Node n2) {
		return (n2.getX() - n1.getX()) * (getY() - n1.getY()) - (n2.getY() - n1.getY()) * (getX() - n1.getX());
	}

	public boolean isIn(Rect rect) {
		return rect.contains(this);
	}

	public Node min(Node another) {
		return new Node(Math.min(getX(), another.getX()), Math.min(getY(), another.getY()));
	}

	public Node max(Node another) {
		return new Node(Math.max(getX(), another.getX()), Math.max(getY(), another.getY()));
	}

	public Node shift(double x, double y) {
		return new Node(getX() + x, getY() + y);
	}

	public Node shift(Node another) {
		return shift(another.getX(), another.getY());
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

	public Node rotate(Node another, double angle) {
		angle = Math.toRadians(angle);
		double cos = Math.cos(angle);
		double sin = Math.sin(angle);
		double x = another.getX() - getX();
		double y = another.getY() - getY();
		return new Node(x * cos - y * sin + getX(), x * sin + y * cos + getY());
	}

	public Node rotateBy(Node origin, double angle) {
		return origin.rotate(this, angle);
	}

	public class Tinted extends PreciseColor implements Operatable<Node, Tinted> {
		public static Tinted of(Node node, BasicRGBA<?> tint) {
			return node.new Tinted(tint);
		}

		public static Tinted of(Node node, Color tint) {
			return node.new Tinted(tint);
		}

		public static Tinted of(double x, double y, double red, double green, double blue, double alpha) {
			return new Node(x, y).new Tinted(red, green, blue, alpha);
		}

		public static Tinted of(double x, double y, double red, double green, double blue) {
			return new Node(x, y).new Tinted(red, green, blue);
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
			super(red, green, blue);
		}

		public Tinted(BasicRGBA<?> tint) {
			super(tint);
		}

		public Tinted(Color tint) {
			super(tint);
		}

		public Vec3d toVec3d(double z) {
			return getNode().toVec3d(z);
		}

		public Vec3d toVec3d() {
			return getNode().toVec3d();
		}

		public double distanceTo(Tinted another) {
			return getNode().distanceTo(another.getNode());
		}

		public double angleTo(Tinted another) {
			return getNode().angleTo(another.getNode());
		}

		public double crossWith(Tinted n1, Tinted n2) {
			return getNode().crossWith(n1.getNode(), n2.getNode());
		}

		public boolean isIn(Rect rect) {
			return rect.contains(getNode());
		}

		public Tinted interpolate(Tinted another, double ratio) {
			return getNode().interpolate(another.getNode(), ratio).tint(blend(another, ratio));
		}

		@Override
		public String toShortString() {
			return getNode().toShortString() + "-" + getTint().toShortString();
		}

		@Override
		public String toString() {
			return getClass().getSimpleName() + "{" + getNode().toString() + ", " + getTint().toString() + "}";
		}

		@Override
		public Tinted operate(Function<Node, Node> operation) {
			return operation.apply(getNode()).tint(getTint());
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
