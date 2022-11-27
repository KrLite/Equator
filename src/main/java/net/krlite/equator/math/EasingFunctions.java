package net.krlite.equator.math;

import net.minecraft.util.math.MathHelper;

public class EasingFunctions {
    /**
     * Clamps the value between zero and one.
     *
     * @param value     the dedicated value.
     */
    private static double clamp(double value) {
        return MathHelper.clamp(value, 0, 1);
    }

    /**
     * Powers the value by 2.
     *
     * @param value the dedicated value.
     */
    private static double pow(double value) {
        return Math.pow(value, 2);
    }

    /**
     * Powers the value by an integer.
     *
     * @param value the dedicated value,
     * @param exp   the power.
     */
    private static double pow(double value, int exp) {
        return Math.pow(value, exp);
    }



    // Linear (^1)

    /**
     * Easing linear function (^1), a simple version of
     * {@link
     * #easeLinear(double, double, double, double)
     * easeLinear.}
     *
     * @param progressWithinOne current progress divide one (clamped between zero and one),
     * @param origin            original value,
     * @param shift             distance to original value,
     * @return                  returns the linear value of current progress.
     */
    public static double easeLinear(double progressWithinOne, double origin, double shift) {
        return shift * clamp(progressWithinOne) + origin;
    }

    /**
     * Easing linear function (^1).
     *
     * @param progress  current progress,
     * @param origin    original value,
     * @param shift     distance to original value,
     * @param duration  duration time.
     * @return          returns the linear value of current progress.
     */
    public static double easeLinear(double progress, double origin, double shift, double duration) {
        return shift * clamp(progress / duration) + origin;
    }

    // Quadratic (^2)

    /**
     * Easing quadratic function (^2).
     *
     * @param progress  current progress,
     * @param origin    original value,
     * @param shift     distance to original value,
     * @param duration  duration time.
     * @return          returns the quadratic value of current progress.
     */
    public static double easeQuad(double progress, double origin, double shift, double duration) {
        return (progress /= (duration / 2)) < 1
                ? shift / 2 * clamp(pow(progress)) + origin
                : -shift / 2 * clamp((--progress) * (progress - 2) - 1) + origin;
    }

    /**
     * Easing quadratic function in (^2).
     *
     * @param progress  current progress,
     * @param origin    original value,
     * @param shift     distance to original value,
     * @param duration  duration time.
     * @return          returns the quadratic in value of current progress.
     */
    public static double easeInQuad(double progress, double origin, double shift, double duration) {
        return shift * clamp((progress /= duration) * progress) + origin;
    }

    /**
     * Easing quadratic function out (^2).
     *
     * @param progress  current progress,
     * @param origin    original value,
     * @param shift     distance to original value,
     * @param duration  duration time.
     * @return          returns the quadratic out value of current progress.
     */
    public static double easeOutQuad(double progress, double origin, double shift, double duration) {
        return -shift * clamp((progress /= duration) * (progress - 2)) + origin;
    }

    // Cubic (^3)

    /**
     * Easing cubic function (^3).
     *
     * @param progress  current progress,
     * @param origin    original value,
     * @param shift     distance to original value,
     * @param duration  duration time.
     * @return          returns the cubic value of current progress.
     */
    public static double easeCubic(double progress, double origin, double shift, double duration) {
        return (progress /= (duration / 2)) < 1
                ? shift / 2 * clamp(pow(progress, 3)) + origin
                : -shift / 2 * clamp((progress -= 2) * pow(progress) + 2) + origin;
    }

    /**
     * Easing cubic function in (^3).
     *
     * @param progress  current progress,
     * @param origin    original value,
     * @param shift     distance to original value,
     * @param duration  duration time.
     * @return          returns the cubic in value of current progress.
     */
    public static double easeInCubic(double progress, double origin, double shift, double duration) {
        return shift * clamp((progress /= duration) * pow(progress)) + origin;
    }

    /**
     * Easing cubic function out (^3).
     *
     * @param progress  current progress,
     * @param origin    original value,
     * @param shift     distance to original value,
     * @param duration  duration time.
     * @return          returns the cubic out value of current progress.
     */
    public static double easeOutCubic(double progress, double origin, double shift, double duration) {
        return -shift * clamp((progress = progress / duration - 1) * pow(progress) + 1) + origin;
    }

    // Quartic (^4)

    /**
     * Easing quartic function (^4).
     *
     * @param progress  current progress,
     * @param origin    original value,
     * @param shift     distance to original value,
     * @param duration  duration time.
     * @return          returns the quartic value of current progress.
     */
    public static double easeQuart(double progress, double origin, double shift, double duration) {
        return (progress /= (duration / 2)) < 1
                ? shift / 2 * clamp(pow(progress, 4)) + origin
                : -shift / 2 * clamp((progress -= 2) * pow(progress, 3) - 2) + origin;
    }

    /**
     * Easing quartic function in (^4).
     *
     * @param progress  current progress,
     * @param origin    original value,
     * @param shift     distance to original value,
     * @param duration  duration time.
     * @return          returns the quartic in value of current progress.
     */
    public static double easeInQuart(double progress, double origin, double shift, double duration) {
        return shift * clamp((progress /= duration) * pow(progress, 3)) + origin;
    }

    /**
     * Easing quartic function out (^4).
     *
     * @param progress  current progress,
     * @param origin    original value,
     * @param shift     distance to original value,
     * @param duration  duration time.
     * @return          returns the quartic out value of current progress.
     */
    public static double easeOutQuart(double progress, double origin, double shift, double duration) {
        return -shift * clamp((progress = progress / duration - 1) * pow(progress, 3) - 1) + origin;
    }

    // Quintic (^5)

    /**
     * Easing quintic function (^5).
     *
     * @param progress  current progress,
     * @param origin    original value,
     * @param shift     distance to original value,
     * @param duration  duration time.
     * @return          returns the quintic value of current progress.
     */
    public static double easeQuint(double progress, double origin, double shift, double duration) {
        return (progress /= (duration / 2)) < 1
                ? shift / 2 * clamp(pow(progress, 4)) + origin
                : -shift / 2 * clamp((progress -= 2) * pow(progress, 3) - 2) + origin;
    }

    /**
     * Easing quintic function in (^5).
     *
     * @param progress  current progress,
     * @param origin    original value,
     * @param shift     distance to original value,
     * @param duration  duration time.
     * @return          returns the quintic in value of current progress.
     */
    public static double easeInQuint(double progress, double origin, double shift, double duration) {
        return shift * clamp((progress /= duration) * pow(progress, 3)) + origin;
    }

    /**
     * Easing quintic function out (^5).
     *
     * @param progress  current progress,
     * @param origin    original value,
     * @param shift     distance to original value,
     * @param duration  duration time.
     * @return          returns the quintic out value of current progress.
     */
    public static double easeOutQuint(double progress, double origin, double shift, double duration) {
        return -shift * clamp((progress = progress / duration - 1) * pow(progress, 3) - 1) + origin;
    }



    // Sinusoidal (sine)

    /**
     * Easing sinusoidal function (sine).
     *
     * @param progress  current progress,
     * @param origin    original value,
     * @param shift     distance to original value,
     * @param duration  duration time.
     * @return          returns the sinusoidal value of current progress.
     */
    public static double easeSine(double progress, double origin, double shift, double duration) {
        return -shift / 2 * Math.cos(Math.PI * clamp(progress / duration) - 1) + origin;
    }

    /**
     * Easing sinusoidal function in (sine).
     *
     * @param progress  current progress,
     * @param origin    original value,
     * @param shift     distance to original value,
     * @param duration  duration time.
     * @return          returns the sinusoidal in value of current progress.
     */
    public static double easeInSine(double progress, double origin, double shift, double duration) {
        return -shift * (Math.cos(clamp(progress / duration) * (Math.PI / 2)) - 1) + origin;
    }

    /**
     * Easing sinusoidal function out (sine).
     *
     * @param progress  current progress,
     * @param origin    original value,
     * @param shift     distance to original value,
     * @param duration  duration time.
     * @return          returns the sinusoidal out value of current progress.
     */
    public static double easeOutSine(double progress, double origin, double shift, double duration) {
        return shift * Math.cos(clamp(progress / duration) * (Math.PI / 2)) + origin;
    }

    // Exponential (2^)

    /**
     * Easing exponential function (2^).
     *
     * @param progress  current progress,
     * @param origin    original value,
     * @param shift     distance to original value,
     * @param duration  duration time.
     * @return          returns the exponential value of current progress.
     */
    public static double easeExpo(double progress, double origin, double shift, double duration) {
        return progress == 0
                ? origin
                : progress == duration
                        ? origin + shift
                        : (progress /= (duration / 2)) < 1
                                ? shift / 2 * clamp(Math.pow(2, 10 * (progress - 1))) + origin
                                : shift / 2 * clamp(-Math.pow(2, -10 * --progress) + 2) + origin;
    }

    /**
     * Easing exponential function in (2^).
     *
     * @param progress  current progress,
     * @param origin    original value,
     * @param shift     distance to original value,
     * @param duration  duration time.
     * @return          returns the exponential in value of current progress.
     */
    public static double easeInExpo(double progress, double origin, double shift, double duration) {
        return progress == 0 ? origin : shift * clamp(Math.pow(2, 10 * (progress / duration - 1))) + origin;
    }

    /**
     * Easing exponential function out (2^).
     *
     * @param progress  current progress,
     * @param origin    original value,
     * @param shift     distance to original value,
     * @param duration  duration time.
     * @return          returns the exponential out value of current progress.
     */
    public static double easeOutExpo(double progress, double origin, double shift, double duration) {
        return progress == duration ? origin + shift : shift * clamp(-Math.pow(2, -10 * progress / duration) + 1) + origin;
    }

    // Circular (circle)

    /**
     * Easing circular function (circle).
     *
     * @param progress  current progress,
     * @param origin    original value,
     * @param shift     distance to original value,
     * @param duration  duration time.
     * @return          returns the circular value of current progress.
     */
    public static double easeCirc(double progress, double origin, double shift, double duration) {
        return (progress /= (duration / 2)) < 1
                ? -shift / 2 * clamp(Math.sqrt(1 - pow(progress)) - 1) + origin
                : shift / 2 * clamp(Math.sqrt(1 - (progress -= 2) * progress) + 1) + origin;
    }

    /**
     * Easing circular function in (circle).
     *
     * @param progress  current progress,
     * @param origin    original value,
     * @param shift     distance to original value,
     * @param duration  duration time.
     * @return          returns the circular in value of current progress.
     */
    public static double easeInCirc(double progress, double origin, double shift, double duration) {
        return -shift * clamp(Math.sqrt(1 - (progress /= duration) * progress) - 1) + origin;
    }

    /**
     * Easing circular function out (circle).
     *
     * @param progress  current progress,
     * @param origin    original value,
     * @param shift     distance to original value,
     * @param duration  duration time.
     * @return          returns the circular out value of current progress.
     */
    public static double easeOutCirc(double progress, double origin, double shift, double duration) {
        return shift * clamp(Math.sqrt(1 - (progress = progress / duration - 1) * progress)) + origin;
    }



    // Elastic (exponential decay sine)

    /**
     * Easing elastic function (exponential decay sine).
     *
     * @param progress  current progress,
     * @param origin    original value,
     * @param shift     distance to original value,
     * @param duration  duration time.
     * @return          returns the elastic value of current progress.
     */
    public static double easeElastic(double progress, double origin, double shift, double duration) {
        double s, p = duration * 0.3 * 1.5, a = shift;
        if ( progress == 0 ) return origin;
        if ( (progress /= (duration / 2)) == 2 ) return origin + shift;
        if ( a < Math.abs(shift) ) {
            a = shift;
            s = p / 4;
        } else {
            s = p / (2 * Math.PI) * Math.asin(shift / a);
        }
        if ( progress < 1 ) return -0.5 * (a * Math.pow(2, 10 * progress--) * Math.sin((progress * duration - s) * (2 * Math.PI) / p)) + origin;
        return 0.5 * (a * Math.pow(2, -10 * progress--) * Math.sin((progress * duration - s) * (2 * Math.PI) / p)) + origin + shift;
    }

    /**
     * Easing elastic function in (exponential decay sine).
     *
     * @param progress  current progress,
     * @param origin    original value,
     * @param shift     distance to original value,
     * @param duration  duration time.
     * @return          returns the elastic in value of current progress.
     */
    public static double easeInElastic(double progress, double origin, double shift, double duration) {
        double s, p = duration * 0.3, a = shift;
        if ( progress == 0 ) return origin;
        if ( (progress /= duration) == 1 ) return origin + shift;
        if ( a < Math.abs(shift) ) {
            a = shift;
            s = p / 4;
        } else {
            s = p / (2 * Math.PI) * Math.asin(shift / a);
        }
        return -(a * Math.pow(2, 10 * progress--) * Math.sin((progress * duration - s) * (2 * Math.PI) / p)) + origin;
    }

    /**
     * Easing elastic function out (exponential decay sine).
     *
     * @param progress  current progress,
     * @param origin    original value,
     * @param shift     distance to original value,
     * @param duration  duration time.
     * @return          returns the elastic out value of current progress.
     */
    public static double easeOutElastic(double progress, double origin, double shift, double duration) {
        double s, p = duration * 0.3, a = shift;
        if ( progress == 0 ) return origin;
        if ( (progress /= duration) == 1 ) return origin + shift;
        if ( a < Math.abs(shift) ) {
            a = shift;
            s = p / 4;
        } else {
            s = p / (2 * Math.PI) * Math.asin(shift / a);
        }
        return a * Math.pow(2, 10 * progress) * Math.sin((progress * duration - s) * (2 * Math.PI) / p) + origin + shift;
    }

    // Back (resilience)

    /**
     * Easing back function (resilience).
     *
     * @param progress  current progress,
     * @param origin    original value,
     * @param shift     distance to original value,
     * @param duration  duration time.
     * @return          returns the back value of current progress.
     */
    public static double easeBack(double progress, double origin, double shift, double duration) {
        return shift * (progress /= duration) * progress * (2.70158 * progress - 1.70158) + origin;
    }

    /**
     * Easing back function in (resilience).
     *
     * @param progress  current progress,
     * @param origin    original value,
     * @param shift     distance to original value,
     * @param duration  duration time.
     * @return          returns the back in value of current progress.
     */
    public static double easeInBack(double progress, double origin, double shift, double duration) {
        return shift * (progress = progress / duration - 1) * progress * (2.70158 * progress + 1.70158) + 1 + origin;
    }

    /**
     * Easing back function out (resilience).
     *
     * @param progress  current progress,
     * @param origin    original value,
     * @param shift     distance to original value,
     * @param duration  duration time.
     * @return          returns the back out value of current progress.
     */
    public static double easeOutBack(double progress, double origin, double shift, double duration) {
        return (progress /= (duration / 2)) < 1
                ? shift / 2 * (pow(progress) * ((1.70158 * 1.525 + 1) * progress - 1.70158 * 1.525)) + origin
                : shift / 2 * ((progress -= 2) * progress * ((1.70158 * 1.525 + 1) * progress + 1.70158 * 1.525) + 2) + origin;
    }

    // Bounce (gravity)

    /**
     * Easing bounce function (gravity).
     *
     * @param progress  current progress,
     * @param origin    original value,
     * @param shift     distance to original value,
     * @param duration  duration time.
     * @return          returns the bounce value of current progress.
     */
    public static double easeBounce(double progress, double origin, double shift, double duration) {
        return (progress < duration / 2)
                ? easeInBounce(progress * 2, 0, shift, duration) * 0.5 + origin
                : easeOutBounce(progress * 2 - duration, 0, shift, duration) * 0.5 + origin + shift * 0.5;
    }

    /**
     * Easing bounce function in (gravity).
     *
     * @param progress  current progress,
     * @param origin    original value,
     * @param shift     distance to original value,
     * @param duration  duration time.
     * @return          returns the bounce in value of current progress.
     */
    public static double easeInBounce(double progress, double origin, double shift, double duration) {
        return shift - easeOutBounce(duration - progress, 0, shift, duration) + origin;
    }

    /**
     * Easing bounce function out (gravity).
     *
     * @param progress  current progress,
     * @param origin    original value,
     * @param shift     distance to original value,
     * @param duration  duration time.
     * @return          returns the bounce out value of current progress.
     */
    public static double easeOutBounce(double progress, double origin, double shift, double duration) {
        if ( (progress /= duration) <= (1 / 2.75) ) {
            return shift * 7.5625 * pow(progress) + origin;
        } else if ( progress < (2 / 2.75) ) {
            return shift * (7.5625 * (progress -= (1.5 / 2.75)) * progress + 0.75) + origin;
        } else if ( progress < (2.5 / 2.75) ) {
            return shift * (7.5625 * (progress -= (2.25 / 2.75)) * progress + 0.9375) + origin;
        } else {
            return shift * (7.5625 * (progress -= (2.625 / 2.75)) * progress + 0.984375) + origin;
        }
    }
}
