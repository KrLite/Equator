package net.krlite.equator.geometry.core;

import net.krlite.equator.core.ShortStringable;
import net.krlite.equator.function.AngleFunctions;

@SuppressWarnings("unchecked")
public interface INode<T extends INode<T>> extends ShortStringable {
	/*
	 * ATTRIBUTES
	 */

	double x();

	double y();

	/**
	 * Calculates the distance between this node and another.
	 * @param another	The node to calculate the distance to.
	 * @return			The distance between this node and the other.
	 */
	default <R extends INode<?>> double distance(R another) {
		return Math.sqrt(Math.pow(x() - another.x(), 2) + Math.pow(y() - another.y(), 2));
	}

	/**
	 * Calculates the cross production of this node and two others.
	 * @param n1	The first node.
	 * @param n2	The second node.
	 * @return		The cross production of this node and the two
	 * 				others.
	 */
	default <R extends INode<?>> double cross(R n1, R n2) {
		return (n2.x() - n1.x()) * (y() - n1.y()) - (n2.y() - n1.y()) * (x() - n1.x());
	}

	/**
	 * Calculates the <strong>clockwise</strong> angle between this
	 * node and another.
	 * @param another	The node to calculate the angle to.
	 * @return			The <strong>clockwise</strong> angle between
	 * 					this node and the other.
	 */
	default <R extends INode<?>> double clockwiseDegree(R another) {
		return Math.toDegrees(Math.atan2(another.y() - y(), another.x() - x()));
	}

	/**
	 * Calculates the <strong>clockwise</strong> angle including
	 * <strong>negative ordinate axis</strong> between this node and
	 * another.
	 * @param another	The node to calculate the angle to.
	 * @return			The <strong>clockwise</strong> angle including
	 * 					<strong>negative ordinate axis</strong>.
	 */
	default <R extends INode<?>> double clockwiseDegreeIncludeNegativeOrdinate(R another) {
		return AngleFunctions.toClockwise(clockwiseDegree(another) + 90);
	}

	/*
	 * OBJECT OPERATIONS
	 */

	T createNode(double x, double y);

	/**
	 * Adds the node by the relative abscissa and ordinate.
	 * @param x	The abscissa to shift the node by.
	 * @param y	The ordinate to shift the node by.
	 * @return	The shifted node.
	 */
	default T add(double x, double y) {
		return createNode(x() + x, y() + y);
	}

	/**
	 * Adds the node by the relative coordinates of another.
	 * @param another	The node to shift the node by.
	 * @return			The shifted node.
	 */
	default <R extends INode<?>> T add(R another) {
		return add(another.x(), another.y());
	}

	/**
	 * Shifts the node by a <strong>clockwise</strong> angle and
	 * a distance.
	 * @param distance			The distance to shift the node by.
	 * @param clockwiseDegree	The <strong>clockwise</strong>
	 *                          angle to shift the node by.
	 * @return					The shifted node.
	 */
	default T shift(double distance, double clockwiseDegree) {
		return add(
				Math.cos(Math.toRadians(clockwiseDegree)) * distance,
				Math.sin(Math.toRadians(clockwiseDegree)) * distance
		);
	}

	/**
	 * Scales the node by an origin.
	 * @param origin	The origin to scale the node by.
	 * @param x			The abscissa of the scaling.
	 * @param y			The ordinate of the scaling.
	 * @return			The scaled node.
	 */
	default <R extends INode<?>> T scale(R origin, double x, double y) {
		return createNode(
				(x() - origin.x()) * x + origin.x(),
				(y() - origin.y()) * y + origin.y()
		);
	}

	/**
	 * Scales the node by an origin.
	 * @param origin	The origin to scale the node by.
	 * @param scale		The scaling.
	 * @return			The scaled node.
	 */
	default <R extends INode<?>> T scale(R origin, double scale) {
		return scale(origin, scale, scale);
	}

	/**
	 * Interpolates the node by another.
	 * @param another	The node to interpolate the node by.
	 * @param ratio		The ratio of the interpolation.
	 * @return			The interpolated node.
	 */
	default <R extends INode<?>> T interpolate(R another, double ratio) {
		return createNode(
				x() + (another.x() - x()) * ratio,
				y() + (another.y() - y()) * ratio
		);
	}

	/**
	 * Rotates the node by a <strong>clockwise</strong> angle and
	 * an origin.
	 * @param origin			The origin to rotate the node by.
	 * @param clockwiseDegree	The <strong>clockwise</strong>
	 *                          angle to rotate the node by.
	 * @return					The rotated node.
	 */
	default <R extends INode<?>> T rotateBy(R origin, double clockwiseDegree) {
		return createNode(
				Math.cos(Math.toRadians(clockwiseDegree)) * (x() - origin.x()) - Math.sin(Math.toRadians(clockwiseDegree)) * (y() - origin.y()) + origin.x(),
				Math.sin(Math.toRadians(clockwiseDegree)) * (x() - origin.x()) + Math.cos(Math.toRadians(clockwiseDegree)) * (y() - origin.y()) + origin.y()
		);
	}

	/**
	 * Rotates another node by a <strong>clockwise</strong> angle.
	 * @param clockwiseDegree	The <strong>clockwise</strong>
	 *                          angle to rotate the other node by.
	 * @return					The rotated node.
	 */
	default <R extends INode<?>> T rotate(R another, double clockwiseDegree) {
		return (T) another.rotateBy(this, clockwiseDegree);
	}
}
