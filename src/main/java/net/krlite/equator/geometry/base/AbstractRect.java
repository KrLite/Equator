package net.krlite.equator.geometry.base;

import net.krlite.equator.base.HashCodeComparable;
import net.krlite.equator.core.ShortStringable;

public abstract class AbstractRect<R extends AbstractRect<R, N>, N extends AbstractNode<N>>
		extends HashCodeComparable implements ShortStringable {
	private final N leftTop, rightBottom;

	public N getLeftTop() {
		return leftTop;
	}

	public N getRightBottom() {
		return rightBottom;
	}

	protected AbstractRect(N leftTop, N rightBottom) {
		this.leftTop = leftTop;
		this.rightBottom = rightBottom;
	}

	protected abstract R child(N leftTop, N rightBottom);

	private R child(AbstractRect<?, ?> another) {
		return child(
				getLeftTop().child(another.getLeftTop().getAbscissa(), another.leftTop.getOrdinate()),
				getRightBottom().child(another.getRightBottom().getAbscissa(), another.getRightBottom().getOrdinate()));
	}

	private R self() {
		return child(getLeftTop(), getRightBottom());
	}

	public abstract class AbstractRotated {
		private final double clockwiseDegree;

		public double getClockwiseDegree() {
			return clockwiseDegree;
		}

		public R getRect() {
			return AbstractRect.this.self();
		}

		protected AbstractRotated(double clockwiseDegree) {
			this.clockwiseDegree = clockwiseDegree;
		}
	}
}
