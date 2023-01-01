package net.krlite.equator.math;

import net.krlite.equator.util.Timer;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;

/**
 * A class that provides different kinds of easing functions.
 */
public class EasingFunctions {
    // === Utilities ===
    /**
     * Clamps the value between zero and one.
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
        return pow(value, 2);
    }

    /**
     * Powers the value by an integer.
     * @param value The dedicated value.
     * @param exp   The power.
     */
    private static double pow(double value, int exp) {
        return Math.pow(value, exp);
    }

    // === Basic functions ===
    public static double sin() {
        return Math.sin(System.currentTimeMillis() / 1000.0);
    }

    public static double cos() {
        return Math.cos(System.currentTimeMillis() / 1000.0);
    }

    public static double tan() {
        return Math.tan(System.currentTimeMillis() / 1000.0);
    }

    public static double sinPositive() {
        return Math.abs(sin());
    }

    public static double cosPositive() {
        return Math.abs(cos());
    }

    public static double sinNormal() {
        return sin() / 2 + 0.5;
    }

    public static double cosNormal() {
        return cos() / 2 + 0.5;
    }

    public static double tanReciprocal() {
        return 1 / tan();
    }

    // === Easing functions ===
    // Linear f(x)=x
    public static class Linear {
        /**
         * <code>f(x)=x</code><br />
         * Linear easing function taking zero as the origin.
         *
         * @param timer The dedicated {@link Timer}.
         * @param shift The distance to shift the value.
         * @return      The eased linear value.
         */
        public static double ease(@NotNull Timer timer, double shift) {
            return ease(timer.queueAsPercentage(), 0, shift);
        }

        /**
         * <code>f(x)=x</code><br />
         * Linear easing function, a simple version of
         * {@link #ease(double, double, double, double) easeLinear.}
         *
         * @param progressAsPercentage  Current progress as percentage value between zero and one.
         * @param origin                The original value.
         * @param shift                 The distance to shift the value.
         * @return                      The linear eased value.
         */
        public static double ease(double progressAsPercentage, double origin, double shift) {
            return shift * clamp(progressAsPercentage) + origin;
        }

        /**
         * <code>f(x)=x</code><br />
         * Linear easing function.
         * 
         * @param progress  Current progress.
         * @param origin    The original value.
         * @param shift     The distance to shift the value.
         * @param duration  The duration time.
         * @return          The linear eased value.
         */
        public static double ease(double progress, double origin, double shift, double duration) {
            return shift * clamp(progress / duration) + origin;
        }
    }

    // Quadratic f(x)=x^2
    public static class Quadratic {
        /**
         * <code>f(x)=x^2</code><br />
         * Quadratic easing function taking zero as the origin.
         * 
         * @param timer The dedicated {@link Timer}.
         * @param shift The distance to shift the value.
         * @return      The eased quadratic value.
         */
        public static double ease(@NotNull Timer timer, double shift) {
            return ease(timer.queue(), 0, shift, timer.lasting());
        }

        /**
         * <code>f(x)=x^2</code><br />
         * Quadratic easing function in taking zero as the origin.
         * 
         * @param timer The dedicated {@link Timer}.
         * @param shift The distance to shift the value.
         * @return      The eased quadratic value.
         */
        public static double easeIn(@NotNull Timer timer, double shift) {
            return easeIn(timer.queue(), 0, shift, timer.lasting());
        }

        /**
         * <code>f(x)=x^2</code><br />
         * Quadratic easing function out taking zero as the origin.
         * @param timer The dedicated {@link Timer}.
         * @param shift The distance to shift the value.
         * @return      The eased quadratic value.
         */
        public static double easeOut(@NotNull Timer timer, double shift) {
            return easeOut(timer.queue(), 0, shift, timer.lasting());
        }
        
        /**
         * <code>f(x)=x^2</code><br />
         * Quadratic easing function.
         * 
         * @param progress  Current progress.
         * @param origin    The original value.
         * @param shift     The distance to shift the value.
         * @param duration  The duration time.
         * @return          The quadratic eased value.
         */
        public static double ease(double progress, double origin, double shift, double duration) {
            return (progress /= (duration / 2)) < 1
                           ? shift / 2 * clamp(pow(progress)) + origin
                           : -shift / 2 * clamp((--progress) * (progress - 2) - 1) + origin;
        }

        /**
         * <code>f(x)=x^2</code><br />
         * Quadratic easing function in.
         * 
         * @param progress  Current progress.
         * @param origin    The original value.
         * @param shift     The distance to shift the value.
         * @param duration  The duration time.
         * @return          The quadratic in eased value.
         */
        public static double easeIn(double progress, double origin, double shift, double duration) {
            return shift * clamp((progress /= duration) * progress) + origin;
        }

        /**
         * <code>f(x)=x^2</code><br />
         * Quadratic easing function out.
         *
         * @param progress  Current progress.
         * @param origin    The original value.
         * @param shift     The distance to shift the value.
         * @param duration  The duration time.
         * @return          The quadratic out eased value.
         */
        public static double easeOut(double progress, double origin, double shift, double duration) {
            return -shift * clamp((progress /= duration) * (progress - 2)) + origin;
        }
    }

    // Cubic f(x)=x^3
    public static class Cubic {
        /**
         * <code>f(x)=x^3</code><br />
         * Cubic easing function taking zero as the origin.
         *
         * @param timer The dedicated {@link Timer}.
         * @param shift The distance to shift the value.
         * @return      The eased cubic value.
         */
        public static double ease(@NotNull Timer timer, double shift) {
            return ease(timer.queue(), 0, shift, timer.lasting());
        }

        /**
         * <code>f(x)=x^3</code><br />
         * Cubic easing function in taking zero as the origin.
         *
         * @param timer The dedicated {@link Timer}.
         * @param shift The distance to shift the value.
         * @return      The eased cubic value.
         */
        public static double easeIn(@NotNull Timer timer, double shift) {
            return easeIn(timer.queue(), 0, shift, timer.lasting());
        }

        /**
         * <code>f(x)=x^3</code><br />
         * Cubic easing function out taking zero as the origin.
         *
         * @param timer The dedicated {@link Timer}.
         * @param shift The distance to shift the value.
         * @return      The eased cubic value.
         */
        public static double easeOut(@NotNull Timer timer, double shift) {
            return easeOut(timer.queue(), 0, shift, timer.lasting());
        }
        
        /**
         * <code>f(x)=x^3</code><br />
         * Easing cubic function.
         *
         * @param progress  Current progress.
         * @param origin    The original value.
         * @param shift     The distance to shift the value.
         * @param duration  The duration time.
         * @return          The cubic eased value.
         */
        public static double ease(double progress, double origin, double shift, double duration) {
            return (progress /= (duration / 2)) < 1
                           ? shift / 2 * clamp(pow(progress, 3)) + origin
                           : -shift / 2 * clamp((progress -= 2) * pow(progress) + 2) + origin;
        }

        /**
         * <code>f(x)=x^3</code><br />
         * Easing cubic function in.
         *
         * @param progress  Current progress.
         * @param origin    The original value.
         * @param shift     The distance to shift the value.
         * @param duration  The duration time.
         * @return          The cubic in eased value.
         */
        public static double easeIn(double progress, double origin, double shift, double duration) {
            return shift * clamp((progress /= duration) * pow(progress)) + origin;
        }

        /**
         * <code>f(x)=x^3</code><br />
         * Easing cubic function out.
         *
         * @param progress  Current progress.
         * @param origin    The original value.
         * @param shift     The distance to shift the value.
         * @param duration  The duration time.
         * @return          The cubic out eased value.
         */
        public static double easeOut(double progress, double origin, double shift, double duration) {
            return -shift * clamp((progress = progress / duration - 1) * pow(progress) + 1) + origin;
        }
    }

    // Quartic f(x)=x^4
    public static class Quartic {
        /**
         * <code>f(x)=x^4</code><br />
         * Quartic easing function taking zero as the origin.
         *
         * @param timer The dedicated {@link Timer}.
         * @param shift The distance to shift the value.
         * @return      The eased quartic value.
         */
        public static double ease(@NotNull Timer timer, double shift) {
            return ease(timer.queue(), 0, shift, timer.lasting());
        }

        /**
         * <code>f(x)=x^4</code><br />
         * Quartic easing function in taking zero as the origin.
         *
         * @param timer The dedicated {@link Timer}.
         * @param shift The distance to shift the value.
         * @return      The eased quartic value.
         */
        public static double easeIn(@NotNull Timer timer, double shift) {
            return easeIn(timer.queue(), 0, shift, timer.lasting());
        }

        /**
         * <code>f(x)=x^4</code><br />
         * Quartic easing function out taking zero as the origin.
         *
         * @param timer The dedicated {@link Timer}.
         * @param shift The distance to shift the value.
         * @return      The eased quartic value.
         */
        public static double easeOut(@NotNull Timer timer, double shift) {
            return easeOut(timer.queue(), 0, shift, timer.lasting());
        }

        /**
         * <code>f(x)=x^4</code><br />
         * Easing quartic function.
         *
         * @param progress  Current progress.
         * @param origin    The original value.
         * @param shift     The distance to shift the value.
         * @param duration  The duration time.
         * @return          The quartic eased value.
         */
        public static double ease(double progress, double origin, double shift, double duration) {
            return (progress /= (duration / 2)) < 1
                           ? shift / 2 * clamp(pow(progress, 4)) + origin
                           : -shift / 2 * clamp((progress -= 2) * pow(progress, 3) - 2) + origin;
        }

        /**
         * <code>f(x)=x^4</code><br />
         * Easing quartic function in.
         *
         * @param progress  Current progress.
         * @param origin    The original value.
         * @param shift     The distance to shift the value.
         * @param duration  The duration time.
         * @return          The quartic in eased value.
         */
        public static double easeIn(double progress, double origin, double shift, double duration) {
            return shift * clamp((progress /= duration) * pow(progress, 3)) + origin;
        }

        /**
         * <code>f(x)=x^4</code><br />
         * Easing quartic function out.
         *
         * @param progress  Current progress.
         * @param origin    The original value.
         * @param shift     The distance to shift the value.
         * @param duration  The duration time.
         * @return          The quartic out eased value.
         */
        public static double easeOut(double progress, double origin, double shift, double duration) {
            return -shift * clamp((progress = progress / duration - 1) * pow(progress, 3) - 1) + origin;
        }
    }

    // Quintic f(x)=x^5
    public static class Quintic {
        /**
         * <code>f(x)=x^5</code><br />
         * Quintic easing function taking zero as the origin.
         *
         * @param timer The dedicated {@link Timer}.
         * @param shift The distance to shift the value.
         * @return      The eased quintic value.
         */
        public static double ease(@NotNull Timer timer, double shift) {
            return ease(timer.queue(), 0, shift, timer.lasting());
        }

        /**
         * <code>f(x)=x^5</code><br />
         * Quintic easing function in taking zero as the origin.
         *
         * @param timer The dedicated {@link Timer}.
         * @param shift The distance to shift the value.
         * @return      The eased quintic value.
         */
        public static double easeIn(@NotNull Timer timer, double shift) {
            return easeIn(timer.queue(), 0, shift, timer.lasting());
        }

        /**
         * <code>f(x)=x^5</code><br />
         * Quintic easing function out taking zero as the origin.
         *
         * @param timer The dedicated {@link Timer}.
         * @param shift The distance to shift the value.
         * @return      The eased quintic value.
         */
        public static double easeOut(@NotNull Timer timer, double shift) {
            return easeOut(timer.queue(), 0, shift, timer.lasting());
        }

        /**
         * <code>f(x)=x^5</code><br />
         * Easing quintic function.
         *
         * @param progress  Current progress.
         * @param origin    The original value.
         * @param shift     The distance to shift the value.
         * @param duration  The duration time.
         * @return          The quintic eased value.
         */
        public static double ease(double progress, double origin, double shift, double duration) {
            return (progress /= (duration / 2)) < 1
                           ? shift / 2 * clamp(pow(progress, 4)) + origin
                           : -shift / 2 * clamp((progress -= 2) * pow(progress, 3) - 2) + origin;
        }

        /**
         * <code>f(x)=x^5</code><br />
         * Easing quintic function in.
         *
         * @param progress  Current progress.
         * @param origin    The original value.
         * @param shift     The distance to shift the value.
         * @param duration  The duration time.
         * @return          The quintic in eased value.
         */
        public static double easeIn(double progress, double origin, double shift, double duration) {
            return shift * clamp((progress /= duration) * pow(progress, 3)) + origin;
        }

        /**
         * <code>f(x)=x^5</code><br />
         * Easing quintic function out.
         *
         * @param progress  Current progress.
         * @param origin    The original value.
         * @param shift     The distance to shift the value.
         * @param duration  The duration time.
         * @return          The quintic out eased value.
         */
        public static double easeOut(double progress, double origin, double shift, double duration) {
            return -shift * clamp((progress = progress / duration - 1) * pow(progress, 3) - 1) + origin;
        }
    }



    // Sinusoidal f(x)=sin(x)
    public static class Sinusoidal {
        /**
         * <code>f(x)=sin(x)</code><br />
         * Sinusoidal easing function taking zero as the origin.
         *
         * @param timer The dedicated {@link Timer}.
         * @param shift The distance to shift the value.
         * @return      The eased sinusoidal value.
         */
        public static double ease(@NotNull Timer timer, double shift) {
            return ease(timer.queue(), 0, shift, timer.lasting());
        }

        /**
         * <code>f(x)=sin(x)</code><br />
         * Sinusoidal easing function in taking zero as the origin.
         *
         * @param timer The dedicated {@link Timer}.
         * @param shift The distance to shift the value.
         * @return      The eased sinusoidal value.
         */
        public static double easeIn(@NotNull Timer timer, double shift) {
            return easeIn(timer.queue(), 0, shift, timer.lasting());
        }

        /**
         * <code>f(x)=sin(x)</code><br />
         * Sinusoidal easing function out taking zero as the origin.
         *
         * @param timer The dedicated {@link Timer}.
         * @param shift The distance to shift the value.
         * @return      The eased sinusoidal value.
         */
        public static double easeOut(@NotNull Timer timer, double shift) {
            return easeOut(timer.queue(), 0, shift, timer.lasting());
        }

        /**
         * <code>f(x)=sin(x)</code><br />
         * Easing sinusoidal function.
         *
         * @param progress  Current progress.
         * @param origin    The original value.
         * @param shift     The distance to shift the value.
         * @param duration  The duration time.
         * @return          The sinusoidal eased value.
         */
        public static double ease(double progress, double origin, double shift, double duration) {
            return -shift / 2 * Math.cos(Math.PI * clamp(progress / duration) - 1) + origin;
        }

        /**
         * <code>f(x)=sin(x)</code><br />
         * Easing sinusoidal function in.
         *
         * @param progress  Current progress.
         * @param origin    The original value.
         * @param shift     The distance to shift the value.
         * @param duration  The duration time.
         * @return          The sinusoidal in eased value.
         */
        public static double easeIn(double progress, double origin, double shift, double duration) {
            return -shift * (Math.cos(clamp(progress / duration) * (Math.PI / 2)) - 1) + origin;
        }

        /**
         * <code>f(x)=sin(x)</code><br />
         * Easing sinusoidal function out.
         *
         * @param progress  Current progress.
         * @param origin    The original value.
         * @param shift     The distance to shift the value.
         * @param duration  The duration time.
         * @return          The sinusoidal out eased value.
         */
        public static double easeOut(double progress, double origin, double shift, double duration) {
            return shift * Math.cos(clamp(progress / duration) * (Math.PI / 2)) + origin;
        }
    }

    // Exponential f(x)=2^x
    public static class Exponential {
        /**
         * <code>f(x)=2^x</code><br />
         * Exponential easing function taking zero as the origin.
         *
         * @param timer The dedicated {@link Timer}.
         * @param shift The distance to shift the value.
         * @return      The eased exponential value.
         */
        public static double ease(@NotNull Timer timer, double shift) {
            return ease(timer.queue(), 0, shift, timer.lasting());
        }

        /**
         * <code>f(x)=2^x</code><br />
         * Exponential easing function in taking zero as the origin.
         *
         * @param timer The dedicated {@link Timer}.
         * @param shift The distance to shift the value.
         * @return      The eased exponential value.
         */
        public static double easeIn(@NotNull Timer timer, double shift) {
            return easeIn(timer.queue(), 0, shift, timer.lasting());
        }

        /**
         * <code>f(x)=2^x</code><br />
         * Exponential easing function out taking zero as the origin.
         *
         * @param timer The dedicated {@link Timer}.
         * @param shift The distance to shift the value.
         * @return      The eased exponential value.
         */
        public static double easeOut(@NotNull Timer timer, double shift) {
            return easeOut(timer.queue(), 0, shift, timer.lasting());
        }

        /**
         * <code>f(x)=2^x</code><br />
         * Easing exponential function.
         *
         * @param progress  Current progress.
         * @param origin    The original value.
         * @param shift     The distance to shift the value.
         * @param duration  The duration time.
         * @return          The exponential eased value.
         */
        public static double ease(double progress, double origin, double shift, double duration) {
            return progress == 0
                           ? origin
                           : progress == duration
                                     ? origin + shift
                                     : (progress /= (duration / 2)) < 1
                                               ? shift / 2 * clamp(Math.pow(2, 10 * (progress - 1))) + origin
                                               : shift / 2 * clamp(-Math.pow(2, -10 * --progress) + 2) + origin;
        }

        /**
         * <code>f(x)=2^x</code><br />
         * Easing exponential function in.
         *
         * @param progress  Current progress.
         * @param origin    The original value.
         * @param shift     The distance to shift the value.
         * @param duration  The duration time.
         * @return          The exponential in eased value.
         */
        public static double easeIn(double progress, double origin, double shift, double duration) {
            return progress == 0 ? origin : shift * clamp(Math.pow(2, 10 * (progress / duration - 1))) + origin;
        }

        /**
         * <code>f(x)=2^x</code><br />
         * Easing exponential function out.
         *
         * @param progress  Current progress.
         * @param origin    The original value.
         * @param shift     The distance to shift the value.
         * @param duration  The duration time.
         * @return          The exponential out eased value.
         */
        public static double easeOut(double progress, double origin, double shift, double duration) {
            return progress == duration ? origin + shift : shift * clamp(-Math.pow(2, -10 * progress / duration) + 1) + origin;
        }
    }

    // Circular
    public static class Circular {
        /**
         * Circular easing function taking zero as the origin.
         *
         * @param timer The dedicated {@link Timer}.
         * @param shift The distance to shift the value.
         * @return      The eased circular value.
         */
        public static double ease(@NotNull Timer timer, double shift) {
            return ease(timer.queue(), 0, shift, timer.lasting());
        }

        /**
         * Circular easing function in taking zero as the origin.
         *
         * @param timer The dedicated {@link Timer}.
         * @param shift The distance to shift the value.
         * @return      The eased circular value.
         */
        public static double easeIn(@NotNull Timer timer, double shift) {
            return easeIn(timer.queue(), 0, shift, timer.lasting());
        }

        /**
         * Circular easing function out taking zero as the origin.
         *
         * @param timer The dedicated {@link Timer}.
         * @param shift The distance to shift the value.
         * @return      The eased circular value.
         */
        public static double easeOut(@NotNull Timer timer, double shift) {
            return easeOut(timer.queue(), 0, shift, timer.lasting());
        }

        /**
         * Easing circular function.
         *
         * @param progress  Current progress.
         * @param origin    The original value.
         * @param shift     The distance to shift the value.
         * @param duration  The duration time.
         * @return          The circular eased value.
         */
        public static double ease(double progress, double origin, double shift, double duration) {
            return (progress /= (duration / 2)) < 1
                           ? -shift / 2 * clamp(Math.sqrt(1 - pow(progress)) - 1) + origin
                           : shift / 2 * clamp(Math.sqrt(1 - (progress -= 2) * progress) + 1) + origin;
        }

        /**
         * Easing circular function in.
         *
         * @param progress  Current progress.
         * @param origin    The original value.
         * @param shift     The distance to shift the value.
         * @param duration  The duration time.
         * @return          The circular in eased value.
         */
        public static double easeIn(double progress, double origin, double shift, double duration) {
            return -shift * clamp(Math.sqrt(1 - (progress /= duration) * progress) - 1) + origin;
        }

        /**
         * Easing circular function out.
         *
         * @param progress  Current progress.
         * @param origin    The original value.
         * @param shift     The distance to shift the value.
         * @param duration  The duration time.
         * @return          The circular out eased value.
         */
        public static double easeOut(double progress, double origin, double shift, double duration) {
            return shift * clamp(Math.sqrt(1 - (progress = progress / duration - 1) * progress)) + origin;
        }
    }



    // Elastic
    public static class Elastic {
        /**
         * Elastic easing function taking zero as the origin.
         *
         * @param timer The dedicated {@link Timer}.
         * @param shift The distance to shift the value.
         * @return      The eased elastic value.
         */
        public static double ease(@NotNull Timer timer, double shift) {
            return ease(timer.queue(), 0, shift, timer.lasting());
        }

        /**
         * Elastic easing function in taking zero as the origin.
         *
         * @param timer The dedicated {@link Timer}.
         * @param shift The distance to shift the value.
         * @return      The eased elastic value.
         */
        public static double easeIn(@NotNull Timer timer, double shift) {
            return easeIn(timer.queue(), 0, shift, timer.lasting());
        }

        /**
         * Elastic easing function out taking zero as the origin.
         *
         * @param timer The dedicated {@link Timer}.
         * @param shift The distance to shift the value.
         * @return      The eased elastic value.
         */
        public static double easeOut(@NotNull Timer timer, double shift) {
            return easeOut(timer.queue(), 0, shift, timer.lasting());
        }

        /**
         * Easing elastic function.
         *
         * @param progress  Current progress.
         * @param origin    The original value.
         * @param shift     The distance to shift the value.
         * @param duration  The duration time.
         * @return          The elastic eased value.
         */
        public static double ease(double progress, double origin, double shift, double duration) {
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
         * Easing elastic function in.
         *
         * @param progress  Current progress.
         * @param origin    The original value.
         * @param shift     The distance to shift the value.
         * @param duration  The duration time.
         * @return          The elastic in eased value.
         */
        public static double easeIn(double progress, double origin, double shift, double duration) {
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
         * Easing elastic function out.
         *
         * @param progress  Current progress.
         * @param origin    The original value.
         * @param shift     The distance to shift the value.
         * @param duration  The duration time.
         * @return          The elastic out eased value.
         */
        public static double easeOut(double progress, double origin, double shift, double duration) {
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
    }

    // Back
    public static class Back {
        /**
         * Back easing function taking zero as the origin.
         *
         * @param timer The dedicated {@link Timer}.
         * @param shift The distance to shift the value.
         * @return      The eased back value.
         */
        public static double ease(@NotNull Timer timer, double shift) {
            return ease(timer.queue(), 0, shift, timer.lasting());
        }
        
        /**
         * Back easing function in taking zero as the origin.
         *
         * @param timer The dedicated {@link Timer}.
         * @param shift The distance to shift the value.
         * @return      The eased back value.
         */
        public static double easeIn(@NotNull Timer timer, double shift) {
            return easeIn(timer.queue(), 0, shift, timer.lasting());
        }
        
        /**
         * Back easing function out taking zero as the origin.
         *
         * @param timer The dedicated {@link Timer}.
         * @param shift The distance to shift the value.
         * @return      The eased back value.
         */
        public static double easeOut(@NotNull Timer timer, double shift) {
            return easeOut(timer.queue(), 0, shift, timer.lasting());
        }

        /**
         * Easing back function.
         *
         * @param progress  Current progress.
         * @param origin    The original value.
         * @param shift     The distance to shift the value.
         * @param duration  The duration time.
         * @return          The back eased value.
         */
        public static double ease(double progress, double origin, double shift, double duration) {
            return shift * (progress /= duration) * progress * (2.70158 * progress - 1.70158) + origin;
        }

        /**
         * Easing back function in.
         *
         * @param progress  Current progress.
         * @param origin    The original value.
         * @param shift     The distance to shift the value.
         * @param duration  The duration time.
         * @return          The back in eased value.
         */
        public static double easeIn(double progress, double origin, double shift, double duration) {
            return shift * (progress = progress / duration - 1) * progress * (2.70158 * progress + 1.70158) + 1 + origin;
        }

        /**
         * Easing back function out.
         *
         * @param progress  Current progress.
         * @param origin    The original value.
         * @param shift     The distance to shift the value.
         * @param duration  The duration time.
         * @return          The back out eased value.
         */
        public static double easeOut(double progress, double origin, double shift, double duration) {
            return (progress /= (duration / 2)) < 1
                           ? shift / 2 * (pow(progress) * ((1.70158 * 1.525 + 1) * progress - 1.70158 * 1.525)) + origin
                           : shift / 2 * ((progress -= 2) * progress * ((1.70158 * 1.525 + 1) * progress + 1.70158 * 1.525) + 2) + origin;
        }
    }

    // Bounce
    public static class Bounce {
        /**
         * Bounce easing function taking zero as the origin.
         *
         * @param timer The dedicated {@link Timer}.
         * @param shift The distance to shift the value.
         * @return      The eased bounce value.
         */
        public static double ease(@NotNull Timer timer, double shift) {
            return ease(timer.queue(), 0, shift, timer.lasting());
        }
        
        /**
         * Bounce easing function in taking zero as the origin.
         *
         * @param timer The dedicated {@link Timer}.
         * @param shift The distance to shift the value.
         * @return      The eased bounce value.
         */
        public static double easeIn(@NotNull Timer timer, double shift) {
            return easeIn(timer.queue(), 0, shift, timer.lasting());
        }
        
        /**
         * Bounce easing function out taking zero as the origin.
         *
         * @param timer The dedicated {@link Timer}.
         * @param shift The distance to shift the value.
         * @return      The eased bounce value.
         */
        public static double easeOut(@NotNull Timer timer, double shift) {
            return easeOut(timer.queue(), 0, shift, timer.lasting());
        }

        /**
         * Easing bounce function.
         *
         * @param progress  Current progress.
         * @param origin    The original value.
         * @param shift     The distance to shift the value.
         * @param duration  The duration time.
         * @return          The bounce eased value.
         */
        public static double ease(double progress, double origin, double shift, double duration) {
            return (progress < duration / 2)
                           ? easeIn(progress * 2, 0, shift, duration) * 0.5 + origin
                           : easeOut(progress * 2 - duration, 0, shift, duration) * 0.5 + origin + shift * 0.5;
        }

        /**
         * Easing bounce function in.
         *
         * @param progress  Current progress.
         * @param origin    The original value.
         * @param shift     The distance to shift the value.
         * @param duration  The duration time.
         * @return          The bounce in eased value.
         */
        public static double easeIn(double progress, double origin, double shift, double duration) {
            return shift - easeOut(duration - progress, 0, shift, duration) + origin;
        }

        /**
         * Easing bounce function out.
         *
         * @param progress  Current progress.
         * @param origin    The original value.
         * @param shift     The distance to shift the value.
         * @param duration  The duration time.
         * @return          The bounce out eased value.
         */
        public static double easeOut(double progress, double origin, double shift, double duration) {
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
}
