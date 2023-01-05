package net.krlite.equator.geometry;

import net.krlite.equator.color.PreciseColor;
import net.krlite.equator.core.FieldFormattable;
import net.krlite.equator.core.HashCodeComparable;
import net.krlite.equator.geometry.core.INode;
import net.minecraft.client.MinecraftClient;

/**
 * <h2>Node</h2>
 * A class that represents a point which is constrained
 * with abscissa and ordinate.
 */
public class Node extends HashCodeComparable implements FieldFormattable, INode<Node> {
	/*
	 * BASICS
	 */

	/**
	 * The origin, or the point where the abscissa and ordinate are 0.
	 * @return The origin.
	 */
	public static Node ORIGIN() {
		return new Node(0, 0);
	}

	/**
	 * The center of the screen.
	 * @return The center of the screen.
	 */
	public static Node CENTER() {
		return new Node(MinecraftClient.getInstance().getWindow().getScaledWidth() / 2.0, MinecraftClient.getInstance().getWindow().getScaledHeight() / 2.0);
	}

	/**
	 * The top vertex of the screen.
	 * @return	The top vertex of the screen.
	 */
	public static Node FULL() {
		return new Node(MinecraftClient.getInstance().getWindow().getScaledWidth(), MinecraftClient.getInstance().getWindow().getScaledHeight());
	}

	/*
	 * FIELDS
	 */

	private final double x, y;

	/*
	 * CONSTRUCTORS
	 */

	/**
	 * Creates a {@link Node} with the given abscissa and ordinate.
	 *
	 * @param x The node's abscissa.
	 * @param y The node's ordinate.
	 */
	public Node(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/*
	 * CONVERSIONS
	 */

	/**
	 * Binds the node with a {@link PreciseColor}.
	 * @param nodeColor	The {@link PreciseColor} to bind the node with.
	 * @return			The {@link TintedNode} that represents the
	 * 					bound node.
	 */
	public TintedNode bind(PreciseColor nodeColor) {
		return new TintedNode(this, nodeColor);
	}

	/*
	 * ATTRIBUTES
	 */

	@Override
	public double x() {
		return x;
	}

	@Override
	public double y() {
		return y;
	}

	/**
	 * Calculates the <strong>clockwise</strong> angle between this
	 * node and the screen center.
	 * @return	The <strong>clockwise</strong> angle between this
	 * 			node and the screen center.
	 */
	public double clockwiseDegree() {
		return clockwiseDegree(Rect.SCREEN().center());
	}

	/**
	 * Calculates the <strong>clockwise</strong> angle including
	 * <strong>negative ordinate axis</strong> between this node and
	 * the screen center.
	 * @return	The <strong>clockwise</strong> angle including
	 * 			<strong>negative ordinate axis</strong>.
	 */
	public double clockwiseDegreeIncludeNegativeOrdinate() {
		return clockwiseDegreeIncludeNegativeOrdinate(Rect.SCREEN().center());
	}

	/*
	 * OBJECT OPERATIONS
	 */

	@Override
	public Node createNode(double x, double y) {
		return new Node(x, y);
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
