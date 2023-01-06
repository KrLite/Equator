package net.krlite.equator.geometry.core;

import net.krlite.equator.core.ShortStringable;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public interface ILine<L extends ILine<L, N>, N extends INode<N>> extends ShortStringable {
	/*
	 * ATTRIBUTES
	 */

	double fA();

	double fB();

	double fC();

	default boolean isValid() {
		return fA() != 0 || fB() != 0;
	}

	default <AN extends INode<AN>> double distance(AN node) {
		return Math.abs(fA() * node.x() + fB() * node.y() + fC()) / Math.sqrt(Math.pow(fA(), 2) + Math.pow(fB(), 2));
	}

	default <AN extends INode<AN>> boolean onLine(AN node) {
		return distance(node) == 0;
	}

	default <AN extends INode<AN>> AN project(AN node) {
		if (onLine(node)) {
			return node;
		} else if (fA() == 0) {
			return node.createNode(node.x(), -fC() / fB());
		} else if (fB() == 0) {
			return node.createNode(-fC() / fA(), node.y());
		} else {
			double x = (fB() * (fB() * node.x() - fA() * node.y()) - fA() * fC()) / (Math.pow(fA(), 2) + Math.pow(fB(), 2));
			double y = (fA() * (-fB() * node.x() + fA() * node.y()) - fB() * fC()) / (Math.pow(fA(), 2) + Math.pow(fB(), 2));
			return node.createNode(x, y);
		}
	}

	default Optional<Double> slope() {
		if (fB() == 0) {
			return Optional.empty();
		}
		return Optional.of(-fA() / fB());
	}

	default <AL extends ILine<AL, AN>, AN extends INode<AN>> boolean parallel(AL another) {
		return fA() * another.fB() == fB() * another.fA();
	}

	@Nullable
	default <AL extends ILine<AL, AN>, AN extends INode<AN>> N intersection(AL another) {
		if (parallel(another)) {
			return null;
		}
		double x = (fB() * another.fC() - another.fB() * fC()) / (fA() * another.fB() - another.fA() * fB());
		double y = (another.fA() * fC() - fA() * another.fC()) / (fA() * another.fB() - another.fA() * fB());
		return createNode(x, y);
	}

	/*
	 * OBJECT METHODS
	 */

	N createNode(double x, double y);

	L createLine(double a, double b, double c);

	default L createLine(N center, double clockwiseDegree) {
		double a = Math.sin(clockwiseDegree);
		double b = -Math.cos(clockwiseDegree);
		double c = -a * center.x() - b * center.y();
		return createLine(a, b, c);
	}

	default L createLine(N center, N another) {
		return createLine(center, center.clockwiseDegree(another));
	}

	default L selfLine() {
		return createLine(fA(), fB(), fC());
	}

	default <AN extends INode<AN>> L pass(AN node) {
		return createLine(fA(), fB(), -fA() * node.x() - fB() * node.y());
	}

	default L shift(double distance, double clockwiseDegree) {
		double a = fA() + Math.sin(clockwiseDegree);
		double b = fB() - Math.cos(clockwiseDegree);
		double c = fC() - distance;
		return createLine(a, b, c);
	}

	default <AL extends ILine<AL, AN>, AN extends INode<AN>> L interpolate(AL another, double ratio) {
		double a = fA() * (1 - ratio) + another.fA() * ratio;
		double b = fB() * (1 - ratio) + another.fB() * ratio;
		double c = fC() * (1 - ratio) + another.fC() * ratio;
		return createLine(a, b, c);
	}

	default <AN extends INode<AN>> L rotateBy(AN center, double clockwiseDegree) {
		double a = fA() * Math.cos(clockwiseDegree) + fB() * Math.sin(clockwiseDegree);
		double b = -fA() * Math.sin(clockwiseDegree) + fB() * Math.cos(clockwiseDegree);
		double c = -a * center.x() - b * center.y();
		return createLine(a, b, c);
	}

	default <AN extends INode<AN>> L flipBy(AN center) {
		double a = -fA();
		double b = -fB();
		double c = -a * center.x() - b * center.y();
		return createLine(a, b, c);
	}

	default <AL extends ILine<AL, AN>, AN extends INode<AN>> L flipBy(AL another) {
		double a = fA() * another.fA() + fB() * another.fB();
		double b = fA() * another.fB() - fB() * another.fA();
		double c = -a * another.fA() - b * another.fB();
		return createLine(a, b, c);
	}

	default <AL extends ILine<AL, AN>, AN extends INode<AN>> AL flip(AL another) {
		return another.flipBy(selfLine());
	}

	default <AN extends INode<AN>> AN flip(AN node) {
		return project(node).scale(node, 2);
	}
}
