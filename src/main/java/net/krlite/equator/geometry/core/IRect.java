package net.krlite.equator.geometry.core;

import net.krlite.equator.core.ShortStringable;

public interface IRect<R extends IRect<R, N>, N extends INode<N>> extends ShortStringable {
	/*
	 * ATTRIBUTES
	 */

	N upperLeft();

	N lowerLeft();

	N lowerRight();

	N upperRight();

	default <AN extends INode<AN>> boolean inRect(INode<AN> another) {
		return another.cross(lowerLeft(), upperLeft()) * another.cross(upperRight(), lowerRight()) >= 0 &&
				another.cross(upperLeft(), upperRight()) * another.cross(lowerRight(), lowerLeft()) >= 0;
	}

	/*
	 * OBJECT METHODS
	 */

	N createNode(double x, double y);

	R createRect(N upperLeft, N lowerLeft, N lowerRight, N upperRight);

	default R selfRect() {
		return createRect(upperLeft(), lowerLeft(), lowerRight(), upperRight());
	}

	default N center() {
		return createNode(((upperLeft().x() + lowerLeft().x()) / 2 + (upperRight().x()) + lowerRight().x()) / 2,
				((upperLeft().y() + lowerLeft().y()) / 2 + (upperRight().y()) + lowerRight().y()) / 2);
	}

	default R add(double x, double y) {
		return createRect(upperLeft().shift(x, y), lowerLeft().shift(x, y), lowerRight().shift(x, y), upperRight().shift(x, y));
	}

	default <AR extends IRect<AR, AN>, AN extends INode<AN>> R add(AR another) {
		return createRect(upperLeft().add(another.upperLeft()), lowerLeft().add(another.lowerLeft()),
				lowerRight().add(another.lowerRight()), upperRight().add(another.upperRight()));
	}

	default R shift(double distance, double clockwiseDegree) {
		return createRect(upperLeft().shift(distance, clockwiseDegree), lowerLeft().shift(distance, clockwiseDegree),
				lowerRight().shift(distance, clockwiseDegree), upperRight().shift(distance, clockwiseDegree));
	}

	default <AN extends INode<AN>> R shiftCenter(AN center) {
		return add(center.x() - center().x(), center.y() - center().y());
	}

	default <AN extends INode<AN>> R scale(AN origin, double abscissa, double ordinate) {
		return createRect(upperLeft().scale(origin, abscissa, ordinate), lowerLeft().scale(origin, abscissa, ordinate),
				lowerRight().scale(origin, abscissa, ordinate), upperRight().scale(origin, abscissa, ordinate));
	}

	default <AN extends INode<AN>> R scale(AN origin, double scale) {
		return scale(origin, scale, scale);
	}

	default R scale(double abscissa, double ordinate) {
		return scale(center(), abscissa, ordinate);
	}

	default R scale(double scale) {
		return scale(scale, scale);
	}

	default R expand(double x, double y) {
		return createRect(
				createNode(upperLeft().x() - x, upperLeft().y() - y),
				createNode(lowerLeft().x() - x, lowerLeft().y() + y),
				createNode(lowerRight().x() + x, lowerRight().y() + y),
				createNode(upperRight().x() + x, upperRight().y() - y)
		);
	}

	default R expand(double expand) {
		return expand(expand, expand);
	}

	default <AN extends INode<AN>> R rotate(AN origin, double clockwiseDegree) {
		return createRect(upperLeft().rotateBy(origin, clockwiseDegree), lowerLeft().rotateBy(origin, clockwiseDegree),
				lowerRight().rotateBy(origin, clockwiseDegree), upperRight().rotateBy(origin, clockwiseDegree));
	}

	default R rotate(double clockwiseDegree) {
		return rotate(center(), clockwiseDegree);
	}

	default R mesh(int stepAbscissa, int stepOrdinate, int x, int y) {
		if (stepAbscissa == 1 && stepOrdinate == 1) {
			return selfRect();
		}
		stepAbscissa = Math.max(1, stepAbscissa);
		stepOrdinate = Math.max(1, stepOrdinate);
		x = Math.max(0, Math.min(stepAbscissa - 1, x));
		y = Math.max(0, Math.min(stepOrdinate - 1, y));

		N uL = upperLeft().shift(upperLeft().distance(upperRight()) * x / stepAbscissa, upperLeft().clockwiseDegree(upperRight()));
		N lL = lowerLeft().shift(lowerLeft().distance(lowerRight()) * x / stepAbscissa, lowerLeft().clockwiseDegree(lowerRight()));
		N lR = lowerLeft().shift(lowerLeft().distance(lowerRight()) * (x + 1) / stepAbscissa, lowerLeft().clockwiseDegree(lowerRight()));
		N uR = upperLeft().shift(upperLeft().distance(upperRight()) * (x + 1) / stepAbscissa, upperLeft().clockwiseDegree(upperRight()));

		return createRect(
				uL.shift(uL.distance(lL) * y / stepOrdinate, uL.clockwiseDegree(lL)),
				uL.shift(uL.distance(lL) * (y + 1) / stepOrdinate, uL.clockwiseDegree(lL)),
				uR.shift(uR.distance(lR) * (y + 1) / stepOrdinate, uR.clockwiseDegree(lR)),
				uR.shift(uR.distance(lR) * y / stepOrdinate, uR.clockwiseDegree(lR))
		);
	}

	default <AR extends IRect<AR, AN>, AN extends INode<AN>> R min(AR another) {
		return createRect(
				upperLeft().min(another.upperLeft()),
				lowerLeft().max(another.lowerLeft()),
				lowerRight().max(another.lowerRight()),
				upperRight().min(another.upperRight())
		);
	}

	default <AR extends IRect<AR, AN>, AN extends INode<AN>> R max(AR another) {
		return createRect(
				upperLeft().max(another.upperLeft()),
				lowerLeft().min(another.lowerLeft()),
				lowerRight().min(another.lowerRight()),
				upperRight().max(another.upperRight())
		);
	}

	default <AR extends IRect<AR, AN>, AN extends INode<AN>> R interpolate(AR another, double ratio) {
		return createRect(
				upperLeft().interpolate(another.upperLeft(), ratio),
				lowerLeft().interpolate(another.lowerLeft(), ratio),
				lowerRight().interpolate(another.lowerRight(), ratio),
				upperRight().interpolate(another.upperRight(), ratio)
		);
	}
}
