package net.krlite.equator.base.geometry;

import net.krlite.equator.base.color.PreciseColor;
import net.krlite.equator.math.AngleSolver;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Node {
	// === Basic ===
	public static final Node ORIGIN = new Node();

	// === Static constructors ===
	public static Node screenLeftLowerVertex() {
		return new Node(0, MinecraftClient.getInstance().getWindow().getScaledHeight());
	}

	public static Node screenRightLowerVertex() {
		return new Node(MinecraftClient.getInstance().getWindow().getScaledWidth(), 0);
	}

	public static Node screenVertex() {
		return new Node(MinecraftClient.getInstance().getWindow().getScaledWidth(), MinecraftClient.getInstance().getWindow().getScaledHeight());
	}

	public static Node screenLeftMidpoint() {
		return new Node(0, MinecraftClient.getInstance().getWindow().getScaledHeight() / 2.0);
	}

	public static Node screenRightMidpoint() {
		return new Node(MinecraftClient.getInstance().getWindow().getScaledWidth(), MinecraftClient.getInstance().getWindow().getScaledHeight() / 2.0);
	}

	public static Node screenTopMidpoint() {
		return new Node(MinecraftClient.getInstance().getWindow().getScaledWidth() / 2.0, MinecraftClient.getInstance().getWindow().getScaledHeight());
	}

	public static Node screenBottomMidpoint() {
		return new Node(MinecraftClient.getInstance().getWindow().getScaledWidth() / 2.0, 0);
	}

	// === Instance ===
	public final double x, y;

	public Node(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Node() {
		this(0, 0);
	}

	public TintedNode bound(PreciseColor nodeColor) {
		return new TintedNode(this, nodeColor);
	}

	public Node min(@NotNull Node other) {
		return new Node(Math.min(x, other.x), Math.min(y, other.y));
	}

	public Node max(@NotNull Node other) {
		return new Node(Math.max(x, other.x), Math.max(y, other.y));
	}

	public Node shift(double x, double y) {
		return new Node(this.x + x, this.y + y);
	}

	public Node shiftBy(@NotNull Node other) {
		return shift(other.x, other.y);
	}

	public Node shiftTo(@NotNull Node other) {
		return other;
	}

	public Node shiftOf(double distance, double clockwiseDegree) {
		return shift(
				Math.cos(Math.toRadians(clockwiseDegree)) * distance,
				Math.sin(Math.toRadians(clockwiseDegree)) * distance
		);
	}

	public Node scale(@NotNull Node other, double x, double y) {
		return new Node((other.x - this.x) * x + this.x, (other.y - this.y) * y + this.y);
	}

	public Node scale(@NotNull Node other, double scale) {
		return scale(other, scale, scale);
	}

	public Node scale(@NotNull Node other) {
		return scale(other, 0.5);
	}

	public Node interpolate(@NotNull Node target, double ratio) {
		return new Node(x + (target.x - x) * ratio, y + (target.y - y) * ratio);
	}

	public Node interpolate(@NotNull Node target) {
		return interpolate(target, 0.5);
	}

	public double distance(@NotNull Node other) {
		return Math.sqrt(Math.pow(x - other.x, 2) + Math.pow(y - other.y, 2));
	}

	public double getCross(@NotNull Node p1, @NotNull Node p2) {
		return (p2.x - p1.x) * (this.y - p1.y) - (this.x - p1.x) * (p2.y - p1.y);
	}

	public Node rotateBy(@NotNull Node origin, double clockwiseDegree) {
		return new Node(
				(this.x - origin.x) * Math.cos(Math.toRadians(clockwiseDegree)) - (this.y - origin.y) * Math.sin(Math.toRadians(clockwiseDegree)) + origin.x,
				(this.x - origin.x) * Math.sin(Math.toRadians(clockwiseDegree)) + (this.y - origin.y) * Math.cos(Math.toRadians(clockwiseDegree)) + origin.y
		);
	}

	public Node rotate(@NotNull Node other, double clockwiseDegree) {
		return other.rotateBy(this, clockwiseDegree);
	}

	public double getClockwiseDegree(@NotNull Node other) {
		return Math.toDegrees(Math.atan2(other.y - y, other.x - x));
	}

	public double getClockwiseDegree() {
		return getClockwiseDegree(Rect.full().center());
	}

	public double getClockwiseDegreeIncludeNegativeY(@NotNull Node other) {
		return AngleSolver.castByClockwise(getClockwiseDegree(other) + 90);
	}

	public double getClockwiseDegreeIncludeNegativeY() {
		return getClockwiseDegreeIncludeNegativeY(Rect.full().center());
	}

	// === Drawers ===
	public Node drawDebug(@NotNull MatrixStack matrixStack, @NotNull PreciseColor nodeColor) {
		return bound(nodeColor).drawDebug(matrixStack);
	}

	public Node drawDebug(@NotNull MatrixStack matrixStack) {
		return drawDebug(matrixStack, PreciseColor.BLACK);
	}

	public Node draw(@NotNull MatrixStack matrixStack, @NotNull PreciseColor nodeColor) {
		return bound(nodeColor).draw(matrixStack);
	}

	public Node draw(@NotNull MatrixStack matrixStack) {
		return draw(matrixStack, PreciseColor.BLACK);
	}

	public boolean equals(@NotNull Node other) {
		return hashCode() == other.hashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) return true;
		if (other instanceof Node) return equals((Node) other);
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	@Override
	public String toString() {
		return getClass().getName() + "{" + "x=" + x + ", y=" + y + "}";
	}

	public String toShortString() {
		return "Node" + "(" + x + ", " + y + ")";
	}
}
