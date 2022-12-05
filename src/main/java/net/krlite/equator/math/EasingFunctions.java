package net.krlite.equator.math;

import net.krlite.equator.core.Timer;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;

public class EasingFunctions {
    public static class Func {
        /**
         * An infinite {@link #easeLinear(double, double, double, double) linear} function starting from when Minecraft started.
         */
        public static double linear(double ratio) {
            return ratio * Util.getMeasuringTimeMs();
        }

        /**
         * A {@link Timer} based {@link #easeLinear(double, double, double, double) linear} function starting from the origin.
         */
        public static double linear(@NotNull Timer timer, double shift) {
            return EasingFunctions.easeLinear(timer.getOrigin(), 0, shift, timer.getElapsed());
        }

        /**
         * A {@link Timer} based {@link #easeQuadratic(double, double, double, double) quadratic} function starting from the origin.
         */
        public static double quad(@NotNull Timer timer, double shift) {
            return EasingFunctions.easeQuadratic(timer.getOrigin(), 0, shift, timer.getElapsed());
        }

        /**
         * A {@link Timer} based {@link #easeInQuadratic(double, double, double, double) quadratic in} function starting from the origin.
         */
        public static double inQuad(@NotNull Timer timer, double shift) {
            return EasingFunctions.easeInQuadratic(timer.getOrigin(), 0, shift, timer.getElapsed());
        }

        /**
         * A {@link Timer} based {@link #easeOutQuadratic(double, double, double, double) quadratic out} function starting from the origin.
         */
        public static double outQuad(@NotNull Timer timer, double shift) {
            return EasingFunctions.easeOutQuadratic(timer.getOrigin(), 0, shift, timer.getElapsed());
        }

        /**
         * A {@link Timer} based {@link #easeCubic(double, double, double, double) cubic} function starting from the origin.
         */
        public static double cubic(@NotNull Timer timer, double shift) {
            return EasingFunctions.easeCubic(timer.getOrigin(), 0, shift, timer.getElapsed());
        }

        /**
         * A {@link Timer} based {@link #easeInCubic(double, double, double, double) cubic in} function starting from the origin.
         */
        public static double inCubic(@NotNull Timer timer, double shift) {
            return EasingFunctions.easeInCubic(timer.getOrigin(), 0, shift, timer.getElapsed());
        }

        /**
         * A {@link Timer} based {@link #easeOutCubic(double, double, double, double) cubic out} function starting from the origin.
         */
        public static double outCubic(@NotNull Timer timer, double shift) {
            return EasingFunctions.easeOutCubic(timer.getOrigin(), 0, shift, timer.getElapsed());
        }

        /**
         * A {@link Timer} based {@link #easeQuartic(double, double, double, double) quartic} function starting from the origin.
         */
        public static double quart(@NotNull Timer timer, double shift) {
            return EasingFunctions.easeQuartic(timer.getOrigin(), 0, shift, timer.getElapsed());
        }

        /**
         * A {@link Timer} based {@link #easeInQuartic(double, double, double, double) quartic in} function starting from the origin.
         */
        public static double inQuart(@NotNull Timer timer, double shift) {
            return EasingFunctions.easeInQuartic(timer.getOrigin(), 0, shift, timer.getElapsed());
        }

        /**
         * A {@link Timer} based {@link #easeOutQuartic(double, double, double, double) quartic out} function starting from the origin.
         */
        public static double outQuart(@NotNull Timer timer, double shift) {
            return EasingFunctions.easeOutQuartic(timer.getOrigin(), 0, shift, timer.getElapsed());
        }

        /**
         * A {@link Timer} based {@link #easeQuintic(double, double, double, double) quintic} function starting from the origin.
         */
        public static double quint(@NotNull Timer timer, double shift) {
            return EasingFunctions.easeQuintic(timer.getOrigin(), 0, shift, timer.getElapsed());
        }

        /**
         * A {@link Timer} based {@link #easeInQuintic(double, double, double, double) quintic in} function starting from the origin.
         */
        public static double inQuint(@NotNull Timer timer, double shift) {
            return EasingFunctions.easeInQuintic(timer.getOrigin(), 0, shift, timer.getElapsed());
        }

        /**
         * A {@link Timer} based {@link #easeOutQuintic(double, double, double, double) quintic out} function starting from the origin.
         */
        public static double outQuint(@NotNull Timer timer, double shift) {
            return EasingFunctions.easeOutQuintic(timer.getOrigin(), 0, shift, timer.getElapsed());
        }



        /**
         * A {@link Timer} based {@link #easeSinusoidal(double, double, double, double) sinusoidal} function starting from the origin.
         */
        public static double sin(@NotNull Timer timer, double shift) {
            return EasingFunctions.easeSinusoidal(timer.getOrigin(), 0, shift, timer.getElapsed());
        }

        /**
         * A {@link Timer} based {@link #easeInSinusoidal(double, double, double, double) sinusoidal in} function starting from the origin.
         */
        public static double inSin(@NotNull Timer timer, double shift) {
            return EasingFunctions.easeInSinusoidal(timer.getOrigin(), 0, shift, timer.getElapsed());
        }

        /**
         * A {@link Timer} based {@link #easeOutSinusoidal(double, double, double, double) sinusoidal out} function starting from the origin.
         */
        public static double outSin(@NotNull Timer timer, double shift) {
            return EasingFunctions.easeOutSinusoidal(timer.getOrigin(), 0, shift, timer.getElapsed());
        }

        /**
         * A {@link Timer} based {@link #easeExponential(double, double, double, double) exponential} function starting from the origin.
         */
        public static double expo(@NotNull Timer timer, double shift) {
            return EasingFunctions.easeExponential(timer.getOrigin(), 0, shift, timer.getElapsed());
        }

        /**
         * A {@link Timer} based {@link #easeInExponential(double, double, double, double) exponential in} function starting from the origin.
         */
        public static double inExpo(@NotNull Timer timer, double shift) {
            return EasingFunctions.easeInExponential(timer.getOrigin(), 0, shift, timer.getElapsed());
        }

        /**
         * A {@link Timer} based {@link #easeOutExponential(double, double, double, double) exponential out} function starting from the origin.
         */
        public static double outExpo(@NotNull Timer timer, double shift) {
            return EasingFunctions.easeOutExponential(timer.getOrigin(), 0, shift, timer.getElapsed());
        }

        /**
         * A {@link Timer} based {@link #easeCircular(double, double, double, double) circular} function starting from the origin.
         */
        public static double circ(@NotNull Timer timer, double shift) {
            return EasingFunctions.easeCircular(timer.getOrigin(), 0, shift, timer.getElapsed());
        }

        /**
         * A {@link Timer} based {@link #easeInCircular(double, double, double, double) circular in} function starting from the origin.
         */
        public static double inCirc(@NotNull Timer timer, double shift) {
            return EasingFunctions.easeInCircular(timer.getOrigin(), 0, shift, timer.getElapsed());
        }

        /**
         * A {@link Timer} based {@link #easeOutCircular(double, double, double, double) circular out} function starting from the origin.
         */
        public static double outCirc(@NotNull Timer timer, double shift) {
            return EasingFunctions.easeOutCircular(timer.getOrigin(), 0, shift, timer.getElapsed());
        }

        /**
         * A {@link Timer} based {@link #easeElastic(double, double, double, double) elastic} function starting from the origin.
         */
        public static double elastic(@NotNull Timer timer, double shift) {
            return EasingFunctions.easeElastic(timer.getOrigin(), 0, shift, timer.getElapsed());
        }

        /**
         * A {@link Timer} based {@link #easeInElastic(double, double, double, double) elastic in} function starting from the origin.
         */
        public static double inElastic(@NotNull Timer timer, double shift) {
            return EasingFunctions.easeInElastic(timer.getOrigin(), 0, shift, timer.getElapsed());
        }

        /**
         * A {@link Timer} based {@link #easeOutElastic(double, double, double, double) elastic out} function starting from the origin.
         */
        public static double outElastic(@NotNull Timer timer, double shift) {
            return EasingFunctions.easeOutElastic(timer.getOrigin(), 0, shift, timer.getElapsed());
        }

        /**
         * A {@link Timer} based {@link #easeBack(double, double, double, double) back} function starting from the origin.
         */
        public static double back(@NotNull Timer timer, double shift) {
            return EasingFunctions.easeBack(timer.getOrigin(), 0, shift, timer.getElapsed());
        }

        /**
         * A {@link Timer} based {@link #easeInBack(double, double, double, double) back in} function starting from the origin.
         */
        public static double inBack(@NotNull Timer timer, double shift) {
            return EasingFunctions.easeInBack(timer.getOrigin(), 0, shift, timer.getElapsed());
        }

        /**
         * A {@link Timer} based {@link #easeOutBack(double, double, double, double) back out} function starting from the origin.
         */
        public static double outBack(@NotNull Timer timer, double shift) {
            return EasingFunctions.easeOutBack(timer.getOrigin(), 0, shift, timer.getElapsed());
        }

        /**
         * A {@link Timer} based {@link #easeBounce(double, double, double, double) bounce} function starting from the origin.
         */
        public static double bounce(@NotNull Timer timer, double shift) {
            return EasingFunctions.easeBounce(timer.getOrigin(), 0, shift, timer.getElapsed());
        }

        /**
         * A {@link Timer} based {@link #easeInBounce(double, double, double, double) bounce in} function starting from the origin.
         */
        public static double inBounce(@NotNull Timer timer, double shift) {
            return EasingFunctions.easeInBounce(timer.getOrigin(), 0, shift, timer.getElapsed());
        }

        /**
         * A {@link Timer} based {@link #easeOutBounce(double, double, double, double) bounce out} function starting from the origin.
         */
        public static double outBounce(@NotNull Timer timer, double shift) {
            return EasingFunctions.easeOutBounce(timer.getOrigin(), 0, shift, timer.getElapsed());
        }
    }

    /**
     * Clamps the value between zero and one.
     *
     * @param value The dedicated value.
     */
    private static double clamp(double value) {
        return MathHelper.clamp(value, 0, 1);
    }

    /**
     * Powers the value by 2.
     * @param value The dedicated value.
     */
    private static double pow(double value) {
        return Math.pow(value, 2);
    }

    /**
     * Powers the value by an integer.
     * @param value The dedicated value.
     * @param exp   The power.
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
     * @param progressWithinOne Current progress divide one (clamped between zero and one).
     * @param origin            Original value.
     * @param shift             Distance to original value.
     * @return                  The linear value of current progress.
     */
    public static double easeLinear(double progressWithinOne, double origin, double shift) {
        return shift * clamp(progressWithinOne) + origin;
    }

    /**
     * Easing linear function (^1).
     *
     * @param progress  Current progress.
     * @param origin    The original value.
     * @param shift     Distance to the original value.
     * @param duration  Duration time.
     * @return          The linear value of current progress.
     */
    public static double easeLinear(double progress, double origin, double shift, double duration) {
        return shift * clamp(progress / duration) + origin;
    }

    // Quadratic (^2)

    /**
     * Easing quadratic function (^2).
     *
     * @param progress  Current progress.
     * @param origin    The original value.
     * @param shift     Distance to the original value.
     * @param duration  Duration time.
     * @return          The quadratic value of current progress.
     */
    public static double easeQuadratic(double progress, double origin, double shift, double duration) {
        return (progress /= (duration / 2)) < 1
                ? shift / 2 * clamp(pow(progress)) + origin
                : -shift / 2 * clamp((--progress) * (progress - 2) - 1) + origin;
    }

    /**
     * Easing quadratic function in (^2).
     *
     * @param progress  Current progress.
     * @param origin    The original value.
     * @param shift     Distance to the original value.
     * @param duration  Duration time.
     * @return          The quadratic in value of current progress.
     */
    public static double easeInQuadratic(double progress, double origin, double shift, double duration) {
        return shift * clamp((progress /= duration) * progress) + origin;
    }

    /**
     * Easing quadratic function out (^2).
     *
     * @param progress  Current progress.
     * @param origin    The original value.
     * @param shift     Distance to the original value.
     * @param duration  Duration time.
     * @return          The quadratic out value of current progress.
     */
    public static double easeOutQuadratic(double progress, double origin, double shift, double duration) {
        return -shift * clamp((progress /= duration) * (progress - 2)) + origin;
    }

    // Cubic (^3)

    /**
     * Easing cubic function (^3).
     *
     * @param progress  Current progress.
     * @param origin    The original value.
     * @param shift     Distance to the original value.
     * @param duration  Duration time.
     * @return          The cubic value of current progress.
     */
    public static double easeCubic(double progress, double origin, double shift, double duration) {
        return (progress /= (duration / 2)) < 1
                ? shift / 2 * clamp(pow(progress, 3)) + origin
                : -shift / 2 * clamp((progress -= 2) * pow(progress) + 2) + origin;
    }

    /**
     * Easing cubic function in (^3).
     *
     * @param progress  Current progress.
     * @param origin    The original value.
     * @param shift     Distance to the original value.
     * @param duration  Duration time.
     * @return          The cubic in value of current progress.
     */
    public static double easeInCubic(double progress, double origin, double shift, double duration) {
        return shift * clamp((progress /= duration) * pow(progress)) + origin;
    }

    /**
     * Easing cubic function out (^3).
     *
     * @param progress  Current progress.
     * @param origin    The original value.
     * @param shift     Distance to the original value.
     * @param duration  Duration time.
     * @return          The cubic out value of current progress.
     */
    public static double easeOutCubic(double progress, double origin, double shift, double duration) {
        return -shift * clamp((progress = progress / duration - 1) * pow(progress) + 1) + origin;
    }

    // Quartic (^4)

    /**
     * Easing quartic function (^4).
     *
     * @param progress  Current progress.
     * @param origin    The original value.
     * @param shift     Distance to the original value.
     * @param duration  Duration time.
     * @return          The quartic value of current progress.
     */
    public static double easeQuartic(double progress, double origin, double shift, double duration) {
        return (progress /= (duration / 2)) < 1
                ? shift / 2 * clamp(pow(progress, 4)) + origin
                : -shift / 2 * clamp((progress -= 2) * pow(progress, 3) - 2) + origin;
    }

    /**
     * Easing quartic function in (^4).
     *
     * @param progress  Current progress.
     * @param origin    The original value.
     * @param shift     Distance to the original value.
     * @param duration  Duration time.
     * @return          The quartic in value of current progress.
     */
    public static double easeInQuartic(double progress, double origin, double shift, double duration) {
        return shift * clamp((progress /= duration) * pow(progress, 3)) + origin;
    }

    /**
     * Easing quartic function out (^4).
     *
     * @param progress  Current progress.
     * @param origin    The original value.
     * @param shift     Distance to the original value.
     * @param duration  Duration time.
     * @return          The quartic out value of current progress.
     */
    public static double easeOutQuartic(double progress, double origin, double shift, double duration) {
        return -shift * clamp((progress = progress / duration - 1) * pow(progress, 3) - 1) + origin;
    }

    // Quintic (^5)

    /**
     * Easing quintic function (^5).
     *
     * @param progress  Current progress.
     * @param origin    The original value.
     * @param shift     Distance to the original value.
     * @param duration  Duration time.
     * @return          The quintic value of current progress.
     */
    public static double easeQuintic(double progress, double origin, double shift, double duration) {
        return (progress /= (duration / 2)) < 1
                ? shift / 2 * clamp(pow(progress, 4)) + origin
                : -shift / 2 * clamp((progress -= 2) * pow(progress, 3) - 2) + origin;
    }

    /**
     * Easing quintic function in (^5).
     *
     * @param progress  Current progress.
     * @param origin    The original value.
     * @param shift     Distance to the original value.
     * @param duration  Duration time.
     * @return          The quintic in value of current progress.
     */
    public static double easeInQuintic(double progress, double origin, double shift, double duration) {
        return shift * clamp((progress /= duration) * pow(progress, 3)) + origin;
    }

    /**
     * Easing quintic function out (^5).
     *
     * @param progress  Current progress.
     * @param origin    The original value.
     * @param shift     Distance to the original value.
     * @param duration  Duration time.
     * @return          The quintic out value of current progress.
     */
    public static double easeOutQuintic(double progress, double origin, double shift, double duration) {
        return -shift * clamp((progress = progress / duration - 1) * pow(progress, 3) - 1) + origin;
    }



    // Sinusoidal (sine)

    /**
     * Easing sinusoidal function (sine).
     *
     * @param progress  Current progress.
     * @param origin    The original value.
     * @param shift     Distance to the original value.
     * @param duration  Duration time.
     * @return          The sinusoidal value of current progress.
     */
    public static double easeSinusoidal(double progress, double origin, double shift, double duration) {
        return -shift / 2 * Math.cos(Math.PI * clamp(progress / duration) - 1) + origin;
    }

    /**
     * Easing sinusoidal function in (sine).
     *
     * @param progress  Current progress.
     * @param origin    The original value.
     * @param shift     Distance to the original value.
     * @param duration  Duration time.
     * @return          The sinusoidal in value of current progress.
     */
    public static double easeInSinusoidal(double progress, double origin, double shift, double duration) {
        return -shift * (Math.cos(clamp(progress / duration) * (Math.PI / 2)) - 1) + origin;
    }

    /**
     * Easing sinusoidal function out (sine).
     *
     * @param progress  Current progress.
     * @param origin    The original value.
     * @param shift     Distance to the original value.
     * @param duration  Duration time.
     * @return          The sinusoidal out value of current progress.
     */
    public static double easeOutSinusoidal(double progress, double origin, double shift, double duration) {
        return shift * Math.cos(clamp(progress / duration) * (Math.PI / 2)) + origin;
    }

    // Exponential (2^)

    /**
     * Easing exponential function (2^).
     *
     * @param progress  Current progress.
     * @param origin    The original value.
     * @param shift     Distance to the original value.
     * @param duration  Duration time.
     * @return          The exponential value of current progress.
     */
    public static double easeExponential(double progress, double origin, double shift, double duration) {
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
     * @param progress  Current progress.
     * @param origin    The original value.
     * @param shift     Distance to the original value.
     * @param duration  Duration time.
     * @return          The exponential in value of current progress.
     */
    public static double easeInExponential(double progress, double origin, double shift, double duration) {
        return progress == 0 ? origin : shift * clamp(Math.pow(2, 10 * (progress / duration - 1))) + origin;
    }

    /**
     * Easing exponential function out (2^).
     *
     * @param progress  Current progress.
     * @param origin    The original value.
     * @param shift     Distance to the original value.
     * @param duration  Duration time.
     * @return          The exponential out value of current progress.
     */
    public static double easeOutExponential(double progress, double origin, double shift, double duration) {
        return progress == duration ? origin + shift : shift * clamp(-Math.pow(2, -10 * progress / duration) + 1) + origin;
    }

    // Circular (circle)

    /**
     * Easing circular function (circle).
     *
     * @param progress  Current progress.
     * @param origin    The original value.
     * @param shift     Distance to the original value.
     * @param duration  Duration time.
     * @return          The circular value of current progress.
     */
    public static double easeCircular(double progress, double origin, double shift, double duration) {
        return (progress /= (duration / 2)) < 1
                ? -shift / 2 * clamp(Math.sqrt(1 - pow(progress)) - 1) + origin
                : shift / 2 * clamp(Math.sqrt(1 - (progress -= 2) * progress) + 1) + origin;
    }

    /**
     * Easing circular function in (circle).
     *
     * @param progress  Current progress.
     * @param origin    The original value.
     * @param shift     Distance to the original value.
     * @param duration  Duration time.
     * @return          The circular in value of current progress.
     */
    public static double easeInCircular(double progress, double origin, double shift, double duration) {
        return -shift * clamp(Math.sqrt(1 - (progress /= duration) * progress) - 1) + origin;
    }

    /**
     * Easing circular function out (circle).
     *
     * @param progress  Current progress.
     * @param origin    The original value.
     * @param shift     Distance to the original value.
     * @param duration  Duration time.
     * @return          The circular out value of current progress.
     */
    public static double easeOutCircular(double progress, double origin, double shift, double duration) {
        return shift * clamp(Math.sqrt(1 - (progress = progress / duration - 1) * progress)) + origin;
    }



    // Elastic (exponential decay sine)

    /**
     * Easing elastic function (exponential decay sine).
     *
     * @param progress  Current progress.
     * @param origin    The original value.
     * @param shift     Distance to the original value.
     * @param duration  Duration time.
     * @return          The elastic value of current progress.
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
     * @param progress  Current progress.
     * @param origin    The original value.
     * @param shift     Distance to the original value.
     * @param duration  Duration time.
     * @return          The elastic in value of current progress.
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
     * @param progress  Current progress.
     * @param origin    The original value.
     * @param shift     Distance to the original value.
     * @param duration  Duration time.
     * @return          The elastic out value of current progress.
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
     * @param progress  Current progress.
     * @param origin    The original value.
     * @param shift     Distance to the original value.
     * @param duration  Duration time.
     * @return          The back value of current progress.
     */
    public static double easeBack(double progress, double origin, double shift, double duration) {
        return shift * (progress /= duration) * progress * (2.70158 * progress - 1.70158) + origin;
    }

    /**
     * Easing back function in (resilience).
     *
     * @param progress  Current progress.
     * @param origin    The original value.
     * @param shift     Distance to the original value.
     * @param duration  Duration time.
     * @return          The back in value of current progress.
     */
    public static double easeInBack(double progress, double origin, double shift, double duration) {
        return shift * (progress = progress / duration - 1) * progress * (2.70158 * progress + 1.70158) + 1 + origin;
    }

    /**
     * Easing back function out (resilience).
     *
     * @param progress  Current progress.
     * @param origin    The original value.
     * @param shift     Distance to the original value.
     * @param duration  Duration time.
     * @return          The back out value of current progress.
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
     * @param progress  Current progress.
     * @param origin    The original value.
     * @param shift     Distance to the original value.
     * @param duration  Duration time.
     * @return          The bounce value of current progress.
     */
    public static double easeBounce(double progress, double origin, double shift, double duration) {
        return (progress < duration / 2)
                ? easeInBounce(progress * 2, 0, shift, duration) * 0.5 + origin
                : easeOutBounce(progress * 2 - duration, 0, shift, duration) * 0.5 + origin + shift * 0.5;
    }

    /**
     * Easing bounce function in (gravity).
     *
     * @param progress  Current progress.
     * @param origin    The original value.
     * @param shift     Distance to the original value.
     * @param duration  Duration time.
     * @return          The bounce in value of current progress.
     */
    public static double easeInBounce(double progress, double origin, double shift, double duration) {
        return shift - easeOutBounce(duration - progress, 0, shift, duration) + origin;
    }

    /**
     * Easing bounce function out (gravity).
     *
     * @param progress  Current progress.
     * @param origin    The original value.
     * @param shift     Distance to the original value.
     * @param duration  Duration time.
     * @return          The bounce out value of current progress.
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
