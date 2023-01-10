package net.krlite.equator.geometry.core;

import net.krlite.equator.core.ShortStringable;
import net.krlite.equator.function.AngleFunctions;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public interface ILine<L extends ILine<L, N>, N extends INode<N>> extends ShortStringable {
	/*
	 * ATTRIBUTES
	 */

	double a();

	double b();

	double c();

	default boolean isValid() {
		return a() != 0 || b() != 0;
	}

	default <AN extends INode<AN>> double distance(AN node) {
		return Math.abs(a() * node.x() + b() * node.y() + c()) / Math.sqrt(Math.pow(a(), 2) + Math.pow(b(), 2));
	}

	default <AN extends INode<AN>> boolean onLine(AN node) {
		return distance(node) == 0;
	}

	default <AN extends INode<AN>> AN project(AN node) {
		if (onLine(node)) {
			return node;
		} else if (a() == 0) {
			return node.createNode(node.x(), -c() / b());
		} else if (b() == 0) {
			return node.createNode(-c() / a(), node.y());
		} else {
			double x = (b() * (b() * node.x() - a() * node.y()) - a() * c()) / (Math.pow(a(), 2) + Math.pow(b(), 2));
			double y = (a() * (-b() * node.x() + a() * node.y()) - b() * c()) / (Math.pow(a(), 2) + Math.pow(b(), 2));
			return node.createNode(x, y);
		}
	}

	default Optional<Double> slope() {
		if (b() == 0) {
			return Optional.empty();
		}
		return Optional.of(-a() / b());
	}

	default double clockwiseDegree() {
		return AngleFunctions.revertClockwise(AngleFunctions.toClockwise(Math.toDegrees(Math.atan2(b(), a())) - 90));
	}

	default <AL extends ILine<AL, AN>, AN extends INode<AN>> double positiveInclude(AL another) {
		return AngleFunctions.positiveIncludePositive(AngleFunctions.clockwiseToPositive(clockwiseDegree()), AngleFunctions.clockwiseToPositive(another.clockwiseDegree()));
	}

	default <AL extends ILine<AL, AN>, AN extends INode<AN>> double negativeInclude(AL another) {
		return AngleFunctions.toNegative(positiveInclude(another));
	}

	default <L1 extends ILine<L1, N1>, N1 extends INode<N1>, L2 extends ILine<L2, N2>, N2 extends INode<N2>> boolean isIncludedBy(L1 l1, L2 l2) {
		return positiveInclude(l1) * positiveInclude(l2) < 0;
	}

	default <AL extends ILine<AL, AN>, AN extends INode<AN>> boolean parallel(AL another) {
		return a() * another.b() == b() * another.a();
	}

	default <AL extends ILine<AL, AN>, AN extends INode<AN>> boolean perpendicular(AL another) {
		return a() * another.a() + b() * another.b() == 0;
	}

	@Nullable
	default <AL extends ILine<AL, AN>, AN extends INode<AN>> N intersect(AL another) {
		if (parallel(another)) {
			return null;
		}
		double x = (b() * another.c() - another.b() * c()) / (a() * another.b() - another.a() * b());
		double y = (another.a() * c() - a() * another.c()) / (a() * another.b() - another.a() * b());
		return createNode(x, y);
	}

	default <AL extends ILine<AL, AN>, AN extends INode<AN>, EN extends INode<EN>> boolean focus(AL another, EN node) {
		return onLine(node) && another.onLine(node);
	}

	/*
	 * OBJECT METHODS
	 */

	N createNode(double x, double y);

	L createLine(double a, double b, double c);

	default <AN extends INode<AN>> L createLine(AN center, double clockwiseDegree) {
		double a = Math.sin(clockwiseDegree);
		double b = -Math.cos(clockwiseDegree);
		double c = -a * center.x() - b * center.y();
		return createLine(a, b, c);
	}

	default <N1 extends INode<N1>, N2 extends INode<N2>> L createLine(N1 center, N2 another) {
		return createLine(center, center.clockwiseDegree(another));
	}

	default L selfLine() {
		return createLine(a(), b(), c());
	}

	default L shift(double distance, double clockwiseDegree) {
		double a = a() + Math.sin(clockwiseDegree);
		double b = b() - Math.cos(clockwiseDegree);
		double c = c() - distance;
		return createLine(a, b, c);
	}

	default <AL extends ILine<AL, AN>, AN extends INode<AN>> L interpolate(AL another, double ratio) {
		double a = a() * (1 - ratio) + another.a() * ratio;
		double b = b() * (1 - ratio) + another.b() * ratio;
		double c = c() * (1 - ratio) + another.c() * ratio;
		return createLine(a, b, c);
	}

	default <AN extends INode<AN>> L buildPerpendicular(AN node) {
		return createLine(node, node.project(selfLine()));
	}

	default <AN extends INode<AN>> L buildParallel(AN node) {
		return createLine(a(), b(), -a() * node.x() - b() * node.y());
	}

	default <AN extends INode<AN>> L rotateBy(AN center, double clockwiseDegree) {
		double a = a() * Math.cos(clockwiseDegree) + b() * Math.sin(clockwiseDegree);
		double b = -a() * Math.sin(clockwiseDegree) + b() * Math.cos(clockwiseDegree);
		double c = -a * center.x() - b * center.y();
		return createLine(a, b, c);
	}

	default <AN extends INode<AN>> L flipBy(AN center) {
		double a = -a();
		double b = -b();
		double c = -a * center.x() - b * center.y();
		return createLine(a, b, c);
	}

	default <AL extends ILine<AL, AN>, AN extends INode<AN>> L flipBy(AL another) {
		double a = a() * another.a() + b() * another.b();
		double b = a() * another.b() - b() * another.a();
		double c = -a * another.a() - b * another.b();
		return createLine(a, b, c);
	}

	default <AL extends ILine<AL, AN>, AN extends INode<AN>> AL flip(AL another) {
		return another.flipBy(selfLine());
	}

	default <AN extends INode<AN>> AN flip(AN node) {
		return project(node).scale(node, 2);
	}
}
