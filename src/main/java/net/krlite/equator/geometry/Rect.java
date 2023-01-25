package net.krlite.equator.geometry;

import net.krlite.equator.base.HashCodeComparable;
import net.krlite.equator.color.PreciseColor;
import net.krlite.equator.color.core.BasicRGBA;
import net.krlite.equator.core.Operatable;
import net.krlite.equator.core.ShortStringable;
import net.krlite.equator.core.SimpleOperations;
import net.minecraft.client.MinecraftClient;
import org.jetbrains.annotations.Range;

import java.awt.*;
import java.util.function.Function;

public class Rect extends HashCodeComparable implements ShortStringable, SimpleOperations {
	public static Rect fullScreen() {
		return new Rect(0, 0, MinecraftClient.getInstance().getWindow().getScaledWidth(), MinecraftClient.getInstance().getWindow().getScaledHeight());
	}

	public static Rect centerScreen(double width, double height) {
		return new Rect(MinecraftClient.getInstance().getWindow().getScaledWidth() / 2.0 - width / 2,
				MinecraftClient.getInstance().getWindow().getScaledHeight() / 2.0 - height / 2, width, height);
	}

	public static Rect centerScreen(double size) {
		return centerScreen(size, size);
	}

	public static Rect scaledScreen(double x, double y) {
		return centerScreen(x * MinecraftClient.getInstance().getWindow().getScaledWidth(),
				y * MinecraftClient.getInstance().getWindow().getScaledHeight());
	}

	public static Rect scaledScreen(double scale) {
		return scaledScreen(scale, scale);
	}

	protected final Node leftTop, leftBottom, rightBottom, rightTop;

	public Node getTop() {
		return getLeftTop().interpolate(getRightTop(), 0.5);
	}

	public Node getLeftTop() {
		return leftTop;
	}

	public Node getLeft() {
		return getLeftTop().interpolate(getLeftBottom(), 0.5);
	}

	public Node getLeftBottom() {
		return leftBottom;
	}

	public Node getBottom() {
		return getLeftBottom().interpolate(getRightBottom(), 0.5);
	}

	public Node getRightBottom() {
		return rightBottom;
	}

	public Node getRight() {
		return getRightBottom().interpolate(getRightTop(), 0.5);
	}

	public Node getRightTop() {
		return rightTop;
	}

	public Node getCenter() {
		return new Node(getX() + getWidth() / 2, getY() + getHeight() / 2);
	}

	public double getX() {
		return getLeftTop().getX();
	}

	public double getY() {
		return getLeftTop().getY();
	}

	public double getCenterX() {
		return getX() + getWidth() / 2;
	}

	public double getCenterY() {
		return getY() + getHeight() / 2;
	}

	public double getWidth() {
		return getLeftTop().distanceTo(getRightTop());
	}

	public double getHeight() {
		return getLeftTop().distanceTo(getLeftBottom());
	}

	public double getArea() {
		return getWidth() * getHeight();
	} // Except in the gradiant painters, the rect will always be a 'Rectangle' instead of a 'Quadrangle'

	protected Rect(Node leftTop, Node leftBottom, Node rightBottom, Node rightTop) {
		this.leftTop = leftTop;
		this.leftBottom = leftBottom;
		this.rightBottom = rightBottom;
		this.rightTop = rightTop;
	}

	public Rect(double x, double y, double width, double height) {
		this(new Node(x, y), new Node(x, y + height), new Node(x + width, y + height), new Node(x + width, y));
	}

	public Rect(Node origin, double width, double height) {
		this(origin.getX(), origin.getY(), width, height);
	}

	public Tinted tint(BasicRGBA<?> leftTop, BasicRGBA<?> leftBottom, BasicRGBA<?> rightBottom, BasicRGBA<?> rightTop) {
		return new Tinted(leftTop, leftBottom, rightBottom, rightTop);
	}

	public Tinted tint(BasicRGBA<?> tint) {
		return tint(tint, tint, tint, tint);
	}

	public Tinted tint(PreciseColor leftTop, PreciseColor leftBottom, PreciseColor rightBottom, PreciseColor rightTop) {
		return new Tinted(leftTop, leftBottom, rightBottom, rightTop);
	}

	public Tinted tint(PreciseColor tint) {
		return tint(tint, tint, tint, tint);
	}

	public double distanceTo(Node node) {
		if (contains(node)) return 0;
		double distance = Double.MAX_VALUE;
		distance = Math.min(distance, getLeftTop().distanceTo(node));
		distance = Math.min(distance, getLeftBottom().distanceTo(node));
		distance = Math.min(distance, getRightBottom().distanceTo(node));
		distance = Math.min(distance, getRightTop().distanceTo(node));
		return distance;
	}

	public boolean contains(Node node) {
		return node.crossWith(getLeftBottom(), getLeftTop()) * node.crossWith(getRightTop(), getRightBottom()) >= 0
				&& node.crossWith(getLeftTop(), getRightTop()) * node.crossWith(getRightBottom(), getLeftBottom()) >= 0;
	}

	public Rect min(Rect another) {
		return new Rect(getLeftTop().min(another.getLeftTop()), getLeftBottom().min(another.getLeftBottom()),
				getRightBottom().min(another.getRightBottom()), getRightTop().min(another.getRightTop()));
	}

	public Rect max(Rect another) {
		return new Rect(getLeftTop().max(another.getLeftTop()), getLeftBottom().max(another.getLeftBottom()),
				getRightBottom().max(another.getRightBottom()), getRightTop().max(another.getRightTop()));
	}

	public Rect shift(double x, double y) {
		return new Rect(getLeftTop().shift(x, y), getLeftBottom().shift(x, y), getRightBottom().shift(x, y), getRightTop().shift(x, y));
	}

	public Rect shift(Node another) {
		return shift(another.getX(), another.getY());
	}

	public Rect scaleBy(Node origin, double scale) {
		return new Rect(origin.scale(getLeftTop(), scale), origin.scale(getLeftBottom(), scale),
				origin.scale(getRightBottom(), scale), origin.scale(getRightTop(), scale));
	}

	public Rect scaleByCenter(double scale) {
		return scaleBy(getCenter(), scale);
	}

	public Rect interpolate(Rect another, double ratio) {
		return new Rect(getLeftTop().interpolate(another.getLeftTop(), ratio), getLeftBottom().interpolate(another.getLeftBottom(), ratio),
				getRightBottom().interpolate(another.getRightBottom(), ratio), getRightTop().interpolate(another.getRightTop(), ratio));
	}

	public Rect rotateBy(Node origin, double angle) {
		return new Rect(origin.rotate(getLeftTop(), angle), origin.rotate(getLeftBottom(), angle),
				origin.rotate(getRightBottom(), angle), origin.rotate(getRightTop(), angle));
	}

	public Rect rotateByTop(double angle) {
		return rotateBy(getTop(), angle);
	}

	public Rect rotateByLeftTop(double angle) {
		return rotateBy(getLeftTop(), angle);
	}

	public Rect rotateByLeft(double angle) {
		return rotateBy(getLeft(), angle);
	}

	public Rect rotateByLeftBottom(double angle) {
		return rotateBy(getLeftBottom(), angle);
	}

	public Rect rotateByBottom(double angle) {
		return rotateBy(getBottom(), angle);
	}

	public Rect rotateByRightBottom(double angle) {
		return rotateBy(getRightBottom(), angle);
	}

	public Rect rotateByRight(double angle) {
		return rotateBy(getRight(), angle);
	}

	public Rect rotateByRightTop(double angle) {
		return rotateBy(getRightTop(), angle);
	}

	public Rect rotateByCenter(double angle) {
		return rotateBy(getCenter(), angle);
	}

	public Rect squeezeTop(double ratio) {
		return new Rect(getLeftTop().interpolate(getLeftBottom(), ratio), getLeftBottom(),
				getRightBottom(), getRightTop().interpolate(getRightBottom(), ratio));
	}

	public Rect squeezeBottom(double ratio) {
		return new Rect(getLeftTop(), getLeftBottom().interpolate(getLeftTop(), ratio),
				getRightBottom().interpolate(getRightTop(), ratio), getRightTop());
	}

	public Rect squeezeLeft(double ratio) {
		return new Rect(getLeftTop().interpolate(getRightTop(), ratio), getLeftBottom().interpolate(getRightBottom(), ratio),
				getRightBottom(), getRightTop());
	}

	public Rect topHalf() {
		return squeezeBottom(0.5);
	}

	public Rect bottomHalf() {
		return squeezeTop(0.5);
	}

	public Rect leftHalf() {
		return squeezeRight(0.5);
	}

	public Rect rightHalf() {
		return squeezeLeft(0.5);
	}

	public Rect squeezeRight(double ratio) {
		return new Rect(getLeftTop(), getLeftBottom(),
				getRightBottom().interpolate(getLeftBottom(), ratio), getRightTop().interpolate(getLeftTop(), ratio));
	}

	public Node meshNode(double u, double v) {
		return getLeftTop().interpolate(getRightTop(), u).interpolate(getLeftBottom().interpolate(getRightBottom(), u), v);
	}

	public Node meshNode(double uv) {
		return meshNode(uv, uv);
	}

	public Rect mesh(double uBegin, double vBegin, double uEnd, double vEnd) {
		return new Rect(meshNode(uBegin, vBegin), meshNode(uBegin, vEnd), meshNode(uEnd, vEnd), meshNode(uEnd, vBegin));
	}

	public Rect mesh(double uvBegin, double uvEnd) {
		return mesh(uvBegin, uvBegin, uvEnd, uvEnd);
	}

	public Rect meshByGrid(int xGrid, int yGrid, int xStep, int yStep) {
		xGrid = Math.max(xGrid, 1);
		yGrid = Math.max(yGrid, 1);
		xStep = Math.min(Math.max(xStep, 1), xGrid);
		yStep = Math.min(Math.max(yStep, 1), yGrid);
		return mesh((double) (xStep - 1) / xGrid, (double) (yStep - 1) / yGrid,
				(double) xStep / xGrid, (double) yStep / yGrid);
	}

	public Rect meshByGrid(int grid, int step) {
		return meshByGrid(grid, grid, step, step);
	}

	public class Tinted implements BasicRGBA<Tinted>, Operatable<Rect, Tinted> {
		@Deprecated
		public static Tinted of(Node.Tinted leftTop, Node.Tinted leftBottom, Node.Tinted rightBottom, Node.Tinted rightTop) {
			return new Rect(leftTop.getNode(), leftBottom.getNode(), rightBottom.getNode(), rightTop.getNode())
						   .new Tinted(leftTop, leftBottom, rightBottom, rightTop);
		}

		public static Tinted of(Rect rect, BasicRGBA<?> leftTop, BasicRGBA<?> leftBottom, BasicRGBA<?> rightBottom, BasicRGBA<?> rightTop) {
			return rect.tint(leftTop, leftBottom, rightBottom, rightTop);
		}

		public static Tinted of(Rect rect, BasicRGBA<?> tint) {
			return rect.tint(tint);
		}

		protected final PreciseColor leftTop, leftBottom, rightBottom, rightTop;

		public PreciseColor getTop() {
			return PreciseColor.of(leftTop.blend(rightTop, 0.5));
		}

		public PreciseColor getLeftTop() {
			return leftTop;
		}

		public PreciseColor getLeft() {
			return PreciseColor.of(leftTop.blend(leftBottom, 0.5));
		}

		public PreciseColor getLeftBottom() {
			return leftBottom;
		}

		public PreciseColor getBottom() {
			return PreciseColor.of(leftBottom.blend(rightBottom, 0.5));
		}

		public PreciseColor getRightBottom() {
			return rightBottom;
		}

		public PreciseColor getRight() {
			return PreciseColor.of(rightTop.blend(rightBottom, 0.5));
		}

		public PreciseColor getRightTop() {
			return rightTop;
		}

		public PreciseColor getCenter() {
			return PreciseColor.of(leftTop.average(leftBottom, rightBottom, rightTop));
		}

		public PreciseColor getTopNode() {
			return getLeftTopNode().interpolate(getRightTopNode(), 0.5);
		}

		public Node.Tinted getLeftTopNode() {
			return getRect().getLeftTop().tint(getLeftTop());
		}

		public PreciseColor getLeftNode() {
			return getLeftTopNode().interpolate(getLeftBottomNode(), 0.5);
		}

		public Node.Tinted getLeftBottomNode() {
			return getRect().getLeftBottom().tint(getLeftBottom());
		}

		public PreciseColor getBottomNode() {
			return getLeftBottomNode().interpolate(getRightBottomNode(), 0.5);
		}

		public Node.Tinted getRightBottomNode() {
			return getRect().getRightBottom().tint(getRightBottom());
		}

		public PreciseColor getRightNode() {
			return getRightTopNode().interpolate(getRightBottomNode(), 0.5);
		}

		public Node.Tinted getRightTopNode() {
			return getRect().getRightTop().tint(getRightTop());
		}

		public Node.Tinted getCenterNode() {
			return getRect().getCenter().new Tinted(leftTop.average(leftBottom, rightBottom, rightTop));
		}

		public double getX() {
			return getLeftTopNode().getX();
		}

		public double getY() {
			return getLeftTopNode().getY();
		}

		public double getCenterX() {
			return getRect().getCenterX();
		}

		public double getCenterY() {
			return getRect().getCenterY();
		}

		public double getWidth() {
			return getRect().getWidth();
		}

		public double getHeight() {
			return getRect().getHeight();
		}

		public double getArea() {
			return getRect().getArea();
		}

		@Override
		public double getRed() {
			return getCenterNode().getRed();
		}

		@Override
		public double getGreen() {
			return getCenterNode().getGreen();
		}

		@Override
		public double getBlue() {
			return getCenterNode().getBlue();
		}

		@Override
		public double getAlpha() {
			return getCenterNode().getAlpha();
		}

		public Rect getRect() {
			return Rect.this;
		}

		public Tinted(BasicRGBA<?> leftTop, BasicRGBA<?> leftBottom, BasicRGBA<?> rightBottom, BasicRGBA<?> rightTop) {
			this.leftTop = PreciseColor.of(leftTop);
			this.leftBottom = PreciseColor.of(leftBottom);
			this.rightBottom = PreciseColor.of(rightBottom);
			this.rightTop = PreciseColor.of(rightTop);
		}

		public Tinted(BasicRGBA<?> tint) {
			this(tint, tint, tint, tint);
		}

		public Tinted(PreciseColor leftTop, PreciseColor leftBottom, PreciseColor rightBottom, PreciseColor rightTop) {
			this.leftTop = leftTop;
			this.leftBottom = leftBottom;
			this.rightBottom = rightBottom;
			this.rightTop = rightTop;
		}

		public Tinted(PreciseColor tint) {
			this(tint, tint, tint, tint);
		}

		public Tinted(Color leftTop, Color leftBottom, Color rightBottom, Color rightTop) {
			this(PreciseColor.of(leftTop), PreciseColor.of(leftBottom), PreciseColor.of(rightBottom), PreciseColor.of(rightTop));
		}

		public Tinted(Color tint) {
			this(PreciseColor.of(tint));
		}

		public double distanceTo(Node node) {
			return getRect().distanceTo(node);
		}

		public boolean contains(Node node) {
			return getRect().contains(node);
		}

		public boolean allHasColor() {
			return getLeftTop().hasColor() && getLeftBottom().hasColor() && getRightBottom().hasColor() && getRightTop().hasColor();
		}

		public boolean noneHasColor() {
			return !getLeftTop().hasColor() && !getLeftBottom().hasColor() && !getRightBottom().hasColor() && !getRightTop().hasColor();
		}

		@Override
		public boolean hasColor() {
			return !noneHasColor();
		}

		@Override
		public Tinted withRed(double red) {
			return new Tinted(getLeftTop().withRed(red), getLeftBottom().withRed(red),
					getRightBottom().withRed(red), getRightTop().withRed(red));
		}

		@Override
		public Tinted withGreen(double green) {
			return new Tinted(getLeftTop().withGreen(green), getLeftBottom().withGreen(green),
					getRightBottom().withGreen(green), getRightTop().withGreen(green));
		}

		@Override
		public Tinted withBlue(double blue) {
			return new Tinted(getLeftTop().withBlue(blue), getLeftBottom().withBlue(blue),
					getRightBottom().withBlue(blue), getRightTop().withBlue(blue));
		}

		@Override
		public Tinted withAlpha(@Range(from = 0, to = 255) int alpha) {
			return new Tinted(getLeftTop().withAlpha(alpha), getLeftBottom().withAlpha(alpha),
					getRightBottom().withAlpha(alpha), getRightTop().withAlpha(alpha));
		}

		@Override
		public Tinted withOpacity(double opacity) {
			return new Tinted(getLeftTop().withOpacity(opacity), getLeftBottom().withOpacity(opacity),
					getRightBottom().withOpacity(opacity), getRightTop().withOpacity(opacity));
		}

		public Tinted cut() {
			return new Tinted(
					getLeftTop().orElse(getLeftTop().average(getLeftBottom(), getRightBottom(), getRightTop())),
					getLeftBottom().orElse(getLeftBottom().average(getRightBottom(), getRightTop(), getLeftTop())),
					getRightBottom().orElse(getRightBottom().average(getRightTop(), getLeftTop(), getLeftBottom())),
					getRightTop().orElse(getRightTop().average(getLeftTop(), getLeftBottom(), getRightBottom()))
			);
		}

		public Tinted cut(Tinted fallback) {
			return new Tinted(
					getLeftTop().orElse(fallback.getLeftTop()),
					getLeftBottom().orElse(fallback.getLeftBottom()),
					getRightBottom().orElse(fallback.getRightBottom()),
					getRightTop().orElse(fallback.getRightTop())
			);
		}

		public Tinted cut(BasicRGBA<?> fallback) {
			return new Tinted(
					getLeftTop().orElse(fallback),
					getLeftBottom().orElse(fallback),
					getRightBottom().orElse(fallback),
					getRightTop().orElse(fallback)
			);
		}

		public Tinted interpolate(Tinted another, double ratio) {
			return Tinted.of(
					getLeftTopNode().interpolate(another.getLeftTopNode(), ratio),
					getLeftBottomNode().interpolate(another.getLeftBottomNode(), ratio),
					getRightBottomNode().interpolate(another.getRightBottomNode(), ratio),
					getRightTopNode().interpolate(another.getRightTopNode(), ratio)
			);
		}

		public Node.Tinted meshNode(double u, double v) {
			return getRect().meshNode(u, v)
						   .tint(getLeftTop().blend(getRightTop(), u).blend(getLeftBottom().blend(getRightBottom(), u), v));
		}

		public Node.Tinted meshNode(double uv) {
			return meshNode(uv, uv);
		}

		public Tinted mesh(double uBegin, double vBegin, double uEnd, double vEnd) {
			return new Tinted(meshNode(uBegin, vBegin), meshNode(uBegin, vEnd), meshNode(uEnd, vEnd), meshNode(uEnd, vBegin));
		}

		public Tinted mesh(double uvBegin, double uvEnd) {
			return mesh(uvBegin, uvBegin, uvEnd, uvEnd);
		}

		public Tinted meshByGrid(int xGrid, int yGrid, int xStep, int yStep) {
			xGrid = Math.max(xGrid, 1);
			yGrid = Math.max(yGrid, 1);
			xStep = Math.min(Math.max(xStep, 1), xGrid);
			yStep = Math.min(Math.max(yStep, 1), yGrid);
			return mesh((double) (xStep - 1) / xGrid, (double) (yStep - 1) / yGrid,
					(double) xStep / xGrid, (double) yStep / yGrid);
		}

		public Tinted meshByGrid(int grid, int step) {
			return meshByGrid(grid, grid, step, step);
		}

		public Tinted swap(Rect rect) {
			return rect.tint(getLeftTop(), getLeftBottom(), getRightBottom(), getRightTop());
		}

		@Override
		public Tinted operate(Function<Rect, Rect> operation) {
			return swap(operation.apply(getRect()));
		}

		@Override
		public String toShortString() {
			return getLeftTopNode().toShortString() + "-" +
						   getLeftBottomNode().toShortString() + "-" +
						   getRightBottomNode().toShortString() + "-" +
						   getRightTopNode().toShortString();
		}

		@Override
		public String toString() {
			return getClass().getSimpleName() + "[" +
						   getLeftTopNode().toString() + ", " +
						   getLeftBottomNode().toString() + ", " +
						   getRightBottomNode().toString() + ", " +
						   getRightTopNode().toString() + "]";
		}
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
