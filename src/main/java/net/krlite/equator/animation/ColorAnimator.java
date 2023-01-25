package net.krlite.equator.animation;

import net.krlite.equator.animation.base.ValueAnimator;
import net.krlite.equator.color.core.BasicRGBA;

/**
 * @see ValueAnimator
 * @see BasicRGBA
 */
public class ColorAnimator extends ValueAnimator<BasicRGBA<?>> {
	/**
	 * Creates a new value animator.
	 *
	 * @param start The animator's start value.
	 * @param end   The animator's end value.
	 * @param delta The animator's animation ratio.
	 */
	public ColorAnimator(BasicRGBA<?> start, BasicRGBA<?> end, double delta) {
		super(start, end, delta);
	}

	/**
	 * Creates a new value animator with the default
	 * animation ratio of 0.075.
	 *
	 * @param start The animator's start value.
	 * @param end   The animator's end value.
	 */
	public ColorAnimator(BasicRGBA<?> start, BasicRGBA<?> end) {
		super(start, end);
	}

	public ColorAnimator(BasicRGBA<?> start, double delta) {
		this(start, start.transparent(), delta);
	}

	public ColorAnimator(BasicRGBA<?> start) {
		this(start, 0.075);
	}

	@Override
	public BasicRGBA<?> queue() {
		return value = value.blend(target, delta);
	}

	@Override
	public boolean isFinished() {
		return Math.abs(target.getRed() - value.getRed()) < 0.001 || Math.abs(target.getGreen() - value.getGreen()) < 0.001 ||
					   Math.abs(target.getBlue() - value.getBlue()) < 0.001 || Math.abs(target.getAlpha() - value.getAlpha()) < 0.001;
	}

	public boolean isNearFinished() {
		return Math.abs(target.getRed() - value.getRed()) < 0.1 || Math.abs(target.getGreen() - value.getGreen()) < 0.1 ||
					   Math.abs(target.getBlue() - value.getBlue()) < 0.1 || Math.abs(target.getAlpha() - value.getAlpha()) < 0.1;
	}
}
