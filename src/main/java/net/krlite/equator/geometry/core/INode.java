package net.krlite.equator.geometry.core;

import net.krlite.equator.core.ShortStringable;
import net.krlite.equator.function.AngleFunctions;

public interface INode<N extends INode<N>> extends ShortStringable {
	/*
	 * ATTRIBUTES
	 */

	/**
	 * @return	The abscissa of the node.
	 */
	double x();

	/**
	 * @return	The ordinate of the node.
	 */
	double y();

	/**
	 * Calculates the distance between this node and another.
	 * @param another	The node to calculate the distance to.
	 * @return			The distance between this node and the other.
	 */
	default <AN extends INode<AN>> double distance(AN another) {
		return Math.sqrt(Math.pow(x() - another.x(), 2) + Math.pow(y() - another.y(), 2));
	}

	default <AL extends ILine<AL, AN>, AN extends INode<AN>> double distance(AL line) {
		return line.distance(selfNode());
	}

	/**
	 * Calculates the cross production of this node and two others.
	 * @param n1	The first node.
	 * @param n2	The second node.
	 * @return		The cross production of this node and the two
	 * 				others.
	 */
	default <AN extends INode<AN>> double cross(AN n1, AN n2) {
		return (n2.x() - n1.x()) * (y() - n1.y()) - (n2.y() - n1.y()) * (x() - n1.x());
	}

	/**
	 * Calculates the <strong>clockwise</strong> angle between this
	 * node and another.
	 * @param another	The node to calculate the angle to.
	 * @return			The <strong>clockwise</strong> angle between
	 * 					this node and the other.
	 */
	default <AN extends INode<AN>> double clockwiseDegree(AN another) {
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
	default <AN extends INode<AN>> double clockwiseDegreeIncludeNegativeOrdinate(AN another) {
		return AngleFunctions.toClockwise(clockwiseDegree(another) + 90);
	}

	default <L extends ILine<L, AN>, AN extends INode<AN>> N project(L line) {
		return line.project(selfNode());
	}

	default <L extends ILine<L, AN>, AN extends INode<AN>> boolean onLine(L line) {
		return line.onLine(selfNode());
	}

	default <R extends IRect<R, AN>, AN extends INode<AN>> boolean inRect(R rect) {
		return rect.inRect(selfNode());
	}

	/*
	 * OBJECT METHODS
	 */

	N createNode(double x, double y);

	default N selfNode() {
		return createNode(x(), y());
	}

	/**
	 * Adds the node by the relative abscissa and ordinate.
	 * @param x	Rhe abscissa to shift the node by.
	 * @param y	The ordinate to shift the node by.
	 * @return	The shifted node.
	 */
	default N append(double x, double y) {
		return createNode(x() + x, y() + y);
	}

	/**
	 * Adds the node by the relative coordinates of another.
	 * @param another	The node to shift the node by.
	 * @return			The shifted node.
	 */
	default <AN extends INode<AN>> N append(AN another) {
		return append(another.x(), another.y());
	}

	/**
	 * Subtracts the node by the relative abscissa and ordinate.
	 * @param x	Rhe abscissa to shift the node by.
	 * @param y	The ordinate to shift the node by.
	 * @return	The shifted node.
	 */
	default N subtract(double x, double y) {
		return createNode(x() - x, y() - y);
	}

	/**
	 * Subtracts the node by the relative coordinates of another.
	 * @param another	The node to shift the node by.
	 * @return			The shifted node.
	 */
	default <AN extends INode<AN>> N subtract(AN another) {
		return subtract(another.x(), another.y());
	}

	/**
	 * Shifts the node by a <strong>clockwise</strong> angle and
	 * a distance.
	 * @param distance			The distance to shift the node by.
	 * @param clockwiseDegree	The <strong>clockwise</strong>
	 *                          angle to shift the node by.
	 * @return					The shifted node.
	 */
	default N shift(double distance, double clockwiseDegree) {
		return append(
				Math.cos(Math.toRadians(clockwiseDegree)) * distance,
				Math.sin(Math.toRadians(clockwiseDegree)) * distance
		);
	}

	default <AN extends INode<AN>> N min(AN another) {
		return createNode(
				Math.min(x(), another.x()),
				Math.min(y(), another.y())
		);
	}

	default <AN extends INode<AN>> N max(AN another) {
		return createNode(
				Math.max(x(), another.x()),
				Math.max(y(), another.y())
		);
	}

	/**
	 * Scales the node by an origin.
	 * @param origin	The origin to scale the node by.
	 * @param abscissa			The abscissa of the scaling.
	 * @param ordinate			The ordinate of the scaling.
	 * @return			The scaled node.
	 */
	default <AN extends INode<AN>> N scale(AN origin, double abscissa, double ordinate) {
		return createNode(
				(x() - origin.x()) * abscissa + origin.x(),
				(y() - origin.y()) * ordinate + origin.y()
		);
	}

	/**
	 * Scales the node by an origin.
	 * @param origin	The origin to scale the node by.
	 * @param scale		The scaling.
	 * @return			The scaled node.
	 */
	default <AN extends INode<AN>> N scale(AN origin, double scale) {
		return scale(origin, scale, scale);
	}

	/**
	 * Interpolates the node by another.
	 * @param another	The node to interpolate the node by.
	 * @param ratio		The ratio of the interpolation.
	 * @return			The interpolated node.
	 */
	default <AN extends INode<AN>> N interpolate(AN another, double ratio) {
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
	default <AN extends INode<AN>> N rotateBy(AN origin, double clockwiseDegree) {
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
	default <AN extends INode<AN>> AN rotate(AN another, double clockwiseDegree) {
		return another.rotateBy(selfNode(), clockwiseDegree);
	}

	default <AN extends INode<AN>> N flipBy(AN another) {
		return createNode(another.x() * 2 - x(), another.y() * 2 - y());
	}

	default <AN extends INode<AN>> AN flip(AN another) {
		return another.flipBy(selfNode());
	}

	default <L extends ILine<L, N>> N flip(L line) {
		return line.flip(selfNode());
	}
}
