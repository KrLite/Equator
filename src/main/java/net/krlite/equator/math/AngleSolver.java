package net.krlite.equator.math;

public class AngleSolver {
    /**
     * Solves the included angle between a <b>positive</b> {@link Double} angle and a <b>negative</b> {@link Double} angle.
     * @param srcAngle  The source angle which is <b>positive</b>.
     * @param dstAngle  The destination angle which is <b>negative</b>.
     * @return          The included angle.
     */
    public static double positiveIncludeNegative(double srcAngle, double dstAngle) {
        return positiveIncludePositive(srcAngle, -dstAngle);
    }

    /**
     * Solves the included angle between two <b>positive</b> {@link Double} angles.
     * @param srcAngle  The source angle which is <b>positive</b>.
     * @param dstAngle  The destination angle which is <b>positive</b>.
     * @return          The included angle.
     */
    public static double positiveIncludePositive(double srcAngle, double dstAngle) {
        return AngleSolver.castByPositive(dstAngle - srcAngle);
    }

    /**
     * Converts a <b>clockwise</b> {@link Double} angle to a <b>negative</b> {@link Double} angle.
     * @param angle The angle which is <b>clockwise</b>.
     * @return      The corresponding <b>negative</b> angle.
     */
    public static double clockwiseToNegative(double angle) {
        return -clockwiseToPositive(angle);
    }

    /**
     * Converts a <b>clockwise</b> {@link Double} angle to a <b>positive</b> {@link Double} angle.
     * @param angle The angle which is <b>clockwise</b>.
     * @return      The corresponding <b>positive</b> angle.
     */
    public static double clockwiseToPositive(double angle) {
        angle = castByClockwise(angle);

        return angle + (angle > 180 ? -360 : 0);
    }

    /**
     * Reverts a {@link Double} angle.
     * @param angle The angle to revert.
     * @return      The reverted angle.
     */
    public static double revert(double angle) {
        angle = cast(angle);

        return angle + (angle > 0 ? -180 : 180);
    }

    /**
     * Reverts a {@link Double} angle by <b>clockwise</b>.
     * @param angle The angle to revert.
     * @return      The reverted <b>clockwise</b> angle.
     */
    public static double revertByClockwise(double angle) {
        return 360 - castByClockwise(angle);
    }

    public static double opposite(double angle) {
        return cast(angle + 180);
    }

    public static double oppositeByClockwise(double angle) {
        return castByClockwise(angle + 180);
    }

    /**
     * Takes <b>modulus</b> by <b>180</b> to the {@link Double} angle.
     * @param angle The angle to cast.
     * @return      The cast angle.
     */
    public static double cast(double angle) {
        return angle % 180;
    }

    /**
     * Casts a {@link Double} angle to <b>negative: <code>[180, -180]</code></b>.
     * @param angle The angle to cast.
     * @return      The cast <b>negative</b> angle.
     */
    public static double castByNegative(double angle) {
        return -castByPositive(angle);
    }

    /**
     * Casts a {@link Double} angle to <b>positive: <code>[-180, 180]</code></b>.
     * @param angle The angle to cast.
     * @return      The cast <b>positive</b> angle.
     */
    public static double castByPositive(double angle) {
        angle %= 360;

        return angle + (angle > 180 ? -360 : angle < -180 ? 360 : 0);
    }

    /**
     * Casts a {@link Double} angle to <b>clockwise: <code>[0, 360)</code></b>.
     * @param angle The angle to cast.
     * @return      The cast <b>clockwise</b> angle.
     */
    public static double castByClockwise(double angle) {
        return (angle % 360 + 360) % 360;
    }
}
