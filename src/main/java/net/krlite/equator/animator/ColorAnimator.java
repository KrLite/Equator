package net.krlite.equator.animator;

import net.krlite.equator.animation.base.ValueAnimator;
import net.krlite.equator.annotation.See;
import net.krlite.equator.color.core.BasicRGBA;

@See(ValueAnimator.class)
public class ColorAnimator extends ValueAnimator<BasicRGBA<?>> {
	public ColorAnimator(BasicRGBA<?> start, BasicRGBA<?> end, double delta) {
		super(start, end, delta);
	}

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
