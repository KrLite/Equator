package net.krlite.equator.geometry.base;

import net.krlite.equator.base.HashCodeComparable;
import net.krlite.equator.color.base.AbstractPreciseColor;
import net.krlite.equator.core.ShortStringable;
import net.krlite.equator.core.SimpleOperations;
import net.krlite.equator.geometry.Node;

public abstract class AbstractNode<N extends AbstractNode<N>>
		extends HashCodeComparable implements ShortStringable, SimpleOperations {
	private final double abscissa, ordinate;

	public double getAbscissa() {
		return abscissa;
	}

	public double getOrdinate() {
		return ordinate;
	}

	protected AbstractNode(double abscissa, double ordinate) {
		this.abscissa = abscissa;
		this.ordinate = ordinate;
	}

	protected abstract N child(double abscissa, double ordinate);

	private N child(AbstractNode<?> another) {
		return child(another.getAbscissa(), another.getOrdinate());
	}

	private N self() {
		return child(abscissa, ordinate);
	}

	public boolean onLateral() {
		return abscissa == 0;
	}

	public boolean positiveLateral() {
		return abscissa > 0;
	}

	public boolean onLongitudinal() {
		return ordinate == 0;
	}

	public boolean positiveLongitudinal() {
		return ordinate > 0;
	}

	public double clockwiseDegreeInclude(AbstractNode<?> another) {
		return Math.toDegrees(Math.atan2(another.getOrdinate() - getOrdinate(), another.getAbscissa() - getAbscissa()));
	}

	public N min(AbstractNode<?> another) {
		return child(Math.min(getAbscissa(), another.getAbscissa()), Math.min(getOrdinate(), another.getOrdinate()));
	}

	public N max(AbstractNode<?> another) {
		return child(Math.max(getAbscissa(), another.getAbscissa()), Math.max(getOrdinate(), another.getOrdinate()));
	}

	public N shift(double abscissa, double ordinate) {
		return child(getAbscissa() + abscissa, getOrdinate() + ordinate);
	}

	public N append(AbstractNode<?> another) {
		return shift(another.getAbscissa(), another.getOrdinate());
	}

	public N rotate(AbstractNode<?> another, double clockwiseDegree) {
		return child(
				Math.cos(Math.toRadians(clockwiseDegree)) * (another.getAbscissa() - getAbscissa()) -
						Math.sin(Math.toRadians(clockwiseDegree)) * (another.getOrdinate() - getOrdinate()) + getAbscissa(),
				Math.sin(Math.toRadians(clockwiseDegree)) * (another.getAbscissa() - getAbscissa()) +
						Math.cos(Math.toRadians(clockwiseDegree)) * (another.getOrdinate() - getOrdinate()) + getOrdinate()
		);
	}

	public N rotateBy(AbstractNode<?> origin, double clockwiseDegree) {
		return child(origin.rotate(self(), clockwiseDegree));
	}

	public N scale(AbstractNode<?> another, double ratio) {
		return child(
				(another.getAbscissa() - getAbscissa()) * ratio + getAbscissa(),
				(another.getOrdinate() - getOrdinate()) * ratio + getOrdinate()
		);
	}

	public N scaleBy(AbstractNode<?> origin, double ratio) {
		return child(origin.scale(self(), ratio));
	}

	public N interpolate(AbstractNode<?> another, double ratio) {
		return child(
				blendValue(getAbscissa(), another.getAbscissa(), ratio),
				blendValue(getOrdinate(), another.getOrdinate(), ratio)
		);
	}

	public abstract class AbstractTinted extends AbstractPreciseColor<AbstractTinted> {
		public N getNode() {
			return AbstractNode.this.self();
		}

		protected AbstractTinted(double red, double green, double blue, double alpha) {
			super(red, green, blue, alpha);
		}

		protected AbstractTinted(AbstractPreciseColor<?> tint) {
			this(tint.getRed(), tint.getGreen(), tint.getBlue(), tint.getAlpha());
		}

		@Override
		public String toShortString() {
			return super.toShortString() + "-" + AbstractNode.this.toShortString();
		}
	}

	@Override
	public String toShortString() {
		return "(" + formatFields(false) + ")";
	}
}
