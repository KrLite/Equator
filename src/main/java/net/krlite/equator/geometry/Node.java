package net.krlite.equator.geometry;

import net.krlite.equator.color.base.AbstractPreciseColor;
import net.krlite.equator.geometry.base.AbstractNode;

public class Node extends AbstractNode<Node> {
	@Override
	protected Node child(double abscissa, double ordinate) {
		return new Node(abscissa, ordinate);
	}

	public Node(double abscissa, double ordinate) {
		super(abscissa, ordinate);
	}

	public Tinted bind(AbstractPreciseColor<?> tint) {
		return new Tinted(tint);
	}

	public class Tinted extends AbstractTinted {
		public static Tinted of(Node node, AbstractPreciseColor<?> tint) {
			return node.new Tinted(tint);
		}

		public static Tinted of(double abscissa, double ordinate, AbstractPreciseColor<?> tint) {
			return new Node(abscissa, ordinate).new Tinted(tint);
		}

		@Override
		protected AbstractNode<Node>.AbstractTinted child(double red, double green, double blue, double alpha) {
			return new Tinted(red, green, blue, alpha);
		}

		public Tinted(double red, double green, double blue, double alpha) {
			super(red, green, blue, alpha);
		}

		public Tinted(AbstractPreciseColor<?> tint) {
			super(tint);
		}
	}
}
