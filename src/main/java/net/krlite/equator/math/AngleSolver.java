package net.krlite.equator.math;

public class AngleSolver {
    public static double positiveIncludeNegative(double srcAngle, double dstAngle) {
        return positiveIncludePositive(srcAngle, -dstAngle);
    }

    public static double positiveIncludePositive(double srcAngle, double dstAngle) {
        return AngleSolver.castByPositive(dstAngle - srcAngle);
    }

    public static double clockwiseToNegative(double angle) {
        return -clockwiseToPositive(angle);
    }

    public static double clockwiseToPositive(double angle) {
        angle = castByClockwise(angle);

        return angle + (angle > 180 ? -360 : 0);
    }

    public static double revert(double angle) {
        angle = cast(angle);

        return angle + (angle > 0 ? -180 : 180);
    }

    public static double revertByClockwise(double angle) {
        return 360 - castByClockwise(angle);
    }

    public static double cast(double angle) {
        return angle % 180;
    }

    public static double castByNegative(double angle) {
        return -castByPositive(angle);
    }

    public static double castByPositive(double angle) {
        angle %= 360;

        return angle + (angle > 180 ? -360 : angle < -180 ? 360 : 0);
    }

    public static double castByClockwise(double angle) {
        return (angle % 360 + 360) % 360;
    }
}
