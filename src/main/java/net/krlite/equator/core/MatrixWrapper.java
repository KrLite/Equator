package net.krlite.equator.core;

import net.minecraft.client.util.math.MatrixStack;

/**
 * A wrapper to combine a {@link MatrixStack} and four vertices' coordinate together.
 */
public class MatrixWrapper {
    private final MatrixStack matrixStack;
    private final float xLU, xLD, xRD, xRU;
    private final float yLU, yLD, yRD, yRU;

    private float lerp(float value, float target, float delta) {
        return value + (target - value) * delta;
    }

    private float half(float value, float target) {
        return (value + target) / 2.0F;
    }

    /**
     * Creates a new {@link MatrixWrapper}.
     * @param matrixStack   The {@link MatrixStack} to be wrapped.
     * @param xLU           The x coordinate of the left upper vertex.
     * @param yLU           The y coordinate of the left upper vertex.
     * @param xLD           The x coordinate of the left down vertex.
     * @param yLD           The y coordinate of the left down vertex.
     * @param xRD           The x coordinate of the right down vertex.
     * @param yRD           The y coordinate of the right down vertex.
     * @param xRU           The x coordinate of the right upper vertex.
     * @param yRU           The y coordinate of the right upper vertex.
     */
    public MatrixWrapper(
            MatrixStack matrixStack,
            float xLU, float yLU,
            float xLD, float yLD,
            float xRD, float yRD,
            float xRU, float yRU
    ) {
        this.matrixStack = matrixStack;
        this.xLU = xLU;
        this.yLU = yLU;
        this.xLD = xLD;
        this.yLD = yLD;
        this.xRD = xRD;
        this.yRD = yRD;
        this.xRU = xRU;
        this.yRU = yRU;
    }

    /**
     * Creates a new {@link MatrixWrapper} by a square.
     * @param matrixStack   The {@link MatrixStack} to be wrapped.
     * @param xBegin        The x coordinate of the beginning vertex.
     * @param yBegin        The y coordinate of the beginning vertex.
     * @param xEnd          The x coordinate of the ending vertex.
     * @param yEnd          The y coordinate of the ending vertex.
     */
    public MatrixWrapper(
            MatrixStack matrixStack,
            float xBegin,   float yBegin,
            float xEnd,     float yEnd
    ) {
        this(
                matrixStack,
                xBegin, yBegin,
                xBegin, yEnd,
                xEnd,   yEnd,
                xEnd,   yBegin
        );
    }

    /**
     * @return  The wrapped {@link MatrixStack}.
     */
    public MatrixStack matrixStack() {
        return this.matrixStack;
    }

    /**
     * @return  The x coordinate of the left upper vertex.
     */
    public float xLU() {
        return this.xLU;
    }

    /**
     * @return  The x coordinate of the left down vertex.
     */
    public float xLD() {
        return this.xLD;
    }

    /**
     * @return  The x coordinate of the right down vertex.
     */
    public float xRD() {
        return this.xRD;
    }

    /**
     * @return  The x coordinate of the right upper vertex.
     */
    public float xRU() {
        return this.xRU;
    }

    /**
     * @return  The y coordinate of the left upper vertex.
     */
    public float yLU() {
        return this.yLU;
    }

    /**
     * @return  The y coordinate of the left down vertex.
     */
    public float yLD() {
        return this.yLD;
    }

    /**
     * @return  The y coordinate of the right down vertex.
     */
    public float yRD() {
        return this.yRD;
    }

    /**
     * @return  The y coordinate of the right upper vertex.
     */
    public float yRU() {
        return this.yRU;
    }

    public String toString() {
        return toString(false);
    }

    /**
     * @param matrix    Whether to use the matrix form.
     */
    public String toString(boolean matrix) {
        if ( matrix ){
            return getClass().getName() + " ->\n"
                    + "[" + this.xLU + "," + this.yLU + "],"
                    + "[" + this.xRU + "," + this.yRU + "]\n"
                    + "[" + this.xLD + "," + this.yLD + "],"
                    + "[" + this.xRD + "," + this.yRD + "]";
        } else {
            return getClass().getName()
                    + "{LU=[" + this.xLU + "," + this.yLU + "],"
                    + "LD=[" + this.xLD + "," + this.yLD + "],"
                    + "RD=[" + this.xRD + "," + this.yRD + "],"
                    + "RU=[" + this.xRU + "," + this.yRU + "]}";
        }
    }

    /**
     * Process two {@link MatrixWrapper}s to the minimal.
     * @param wrapper   The {@link MatrixWrapper} to be processed.
     * @return          The minimal {@link MatrixWrapper}.
     */
    public MatrixWrapper minimal(MatrixWrapper wrapper) {
        return new MatrixWrapper(
                matrixStack,
                Math.min(this.xLU, wrapper.xLU),
                Math.min(this.yLU, wrapper.yLU),
                Math.min(this.xLD, wrapper.xLD),
                Math.min(this.yLD, wrapper.yLD),
                Math.min(this.xRD, wrapper.xRD),
                Math.min(this.yRD, wrapper.yRD),
                Math.min(this.xRU, wrapper.xRU),
                Math.min(this.yRU, wrapper.yRU)
        );
    }

    /**
     * Process two {@link MatrixWrapper}s to the maximal.
     * @param wrapper   The {@link MatrixWrapper} to be processed.
     * @return          The maximal {@link MatrixWrapper}.
     */
    public MatrixWrapper maximal(MatrixWrapper wrapper) {
        return new MatrixWrapper(
                matrixStack,
                Math.max(this.xLU, wrapper.xLU),
                Math.max(this.yLU, wrapper.yLU),
                Math.max(this.xLD, wrapper.xLD),
                Math.max(this.yLD, wrapper.yLD),
                Math.max(this.xRD, wrapper.xRD),
                Math.max(this.yRD, wrapper.yRD),
                Math.max(this.xRU, wrapper.xRU),
                Math.max(this.yRU, wrapper.yRU)
        );
    }

    /**
     * Swap the wrapped {@link MatrixStack}.
     * @param matrixStack   The dedicated {@link MatrixStack}.
     * @return              The new {@link MatrixWrapper}.
     */
    public MatrixWrapper swap(MatrixStack matrixStack) {
        return new MatrixWrapper(
                matrixStack,
                this.xLU, this.yLU,
                this.xLD, this.yLD,
                this.xRD, this.yRD,
                this.xRU, this.yRU
        );
    }

    /**
     * Blends two {@link MatrixWrapper}s to the average.
     * @param wrapper   The {@link MatrixWrapper} to be blended.
     * @return          The average {@link MatrixWrapper}.
     */
    public MatrixWrapper blend(MatrixWrapper wrapper) {
        return blend(wrapper, 0.5F);
    }

    /**
     * Blends two {@link MatrixWrapper}s.
     * @param wrapper   The {@link MatrixWrapper} to be blended.
     * @param delta     The blending index.
     * @return          The blended {@link MatrixWrapper}.
     */
    public MatrixWrapper blend(MatrixWrapper wrapper, float delta) {
        return new MatrixWrapper(
                matrixStack,
                lerp(this.xLU, wrapper.xLU, delta),
                lerp(this.yLU, wrapper.yLU, delta),
                lerp(this.xLD, wrapper.xLD, delta),
                lerp(this.yLD, wrapper.yLD, delta),
                lerp(this.xRD, wrapper.xRD, delta),
                lerp(this.yRD, wrapper.yRD, delta),
                lerp(this.xRU, wrapper.xRU, delta),
                lerp(this.yRU, wrapper.yRU, delta)
        );
    }

    /**
     * Stretch the {@link MatrixWrapper} from the left upper point.
     * @param x The x coordinate of the target left upper point.
     * @param y The y coordinate of the target left upper point.
     */
    public MatrixWrapper stretchLU(float x, float y) {
        return stretchLU(x, y, 1);
    }

    /**
     * Stretch the {@link MatrixWrapper} from the left lower point.
     * @param x The x coordinate of the target left lower point.
     * @param y The y coordinate of the target left lower point.
     */
    public MatrixWrapper stretchLD(float x, float y) {
        return stretchLD(x, y, 1);
    }

    /**
     * Stretch the {@link MatrixWrapper} from the right lower point.
     * @param x The x coordinate of the target right lower point.
     * @param y The y coordinate of the target right lower point.
     */
    public MatrixWrapper stretchRD(float x, float y) {
        return stretchRD(x, y, 1);
    }

    /**
     * Stretch the {@link MatrixWrapper} from the right upper point.
     * @param x The x coordinate of the target right upper point.
     * @param y The y coordinate of the target right upper point.
     */
    public MatrixWrapper stretchRU(float x, float y) {
        return stretchRU(x, y, 1);
    }

    /**
     * Stretches the {@link MatrixWrapper} from the left upper point.
     * @param x     The x coordinate of the target left upper point.
     * @param y     The y coordinate of the target left upper point.
     * @param delta The stretching index.
     * @return      The stretched {@link MatrixWrapper}.
     */
    public MatrixWrapper stretchLU(float x, float y, float delta) {
        return blend(
                new MatrixWrapper(
                        matrixStack, x, y,
                        half(this.xLD, x), half(this.yLD, y),
                        this.xRD, this.yRD,
                        half(this.xRU, x), half(this.yRU, y)
                ), delta
        );
    }

    /**
     * Stretches the {@link MatrixWrapper} from the left down point.
     * @param x     The x coordinate of the target left down point.
     * @param y     The y coordinate of the target left down point.
     * @param delta The stretching index.
     * @return      The stretched {@link MatrixWrapper}.
     */
    public MatrixWrapper stretchLD(float x, float y, float delta) {
        return blend(
                new MatrixWrapper(
                        matrixStack,
                        half(this.xLU, x), half(this.yLU, y),
                        x, y,
                        half(this.xRD, x), half(this.yRD, y),
                        this.xRU, this.yRU
                ), delta
        );
    }

    /**
     * Stretches the {@link MatrixWrapper} from the right down point.
     * @param x     The x coordinate of the target right down point.
     * @param y     The y coordinate of the target right down point.
     * @param delta The stretching index.
     * @return      The stretched {@link MatrixWrapper}.
     */
    public MatrixWrapper stretchRD(float x, float y, float delta) {
        return blend(
                new MatrixWrapper(
                        matrixStack,
                        this.xLU, this.yLU,
                        half(this.xLD, x), half(this.yLD, y),
                        x, y,
                        half(this.xRU, x), half(this.yRU, y)
                ), delta
        );
    }

    /**
     * Stretches the {@link MatrixWrapper} from the right upper point.
     * @param x     The x coordinate of the target right upper point.
     * @param y     The y coordinate of the target right upper point.
     * @param delta The stretching index.
     * @return      The stretched {@link MatrixWrapper}.
     */
    public MatrixWrapper stretchRU(float x, float y, float delta) {
        return blend(
                new MatrixWrapper(
                        matrixStack,
                        half(this.xLU, x), half(this.yLU, y),
                        this.xLD, this.yLD,
                        half(this.xRD, x), half(this.yRD, y),
                        x, y
                ), delta
        );
    }

    /**
     * Stretches the {@link MatrixWrapper} from the upper side.
     * @param y The target y coordinate.
     */
    public MatrixWrapper stretchTop(float y) {
        return stretchTop(y, 1);
    }

    /**
     * Stretches the {@link MatrixWrapper} from the lower side.
     * @param y The target y coordinate.
     */
    public MatrixWrapper stretchBottom(float y) {
        return stretchBottom(y, 1);
    }

    /**
     * Stretches the {@link MatrixWrapper} from the left side.
     * @param x The target x coordinate.
     */
    public MatrixWrapper stretchLeft(float x) {
        return stretchLeft(x, 1);
    }

    /**
     * Stretches the {@link MatrixWrapper} from the right side.
     * @param x The target x coordinate.
     */
    public MatrixWrapper stretchRight(float x) {
        return stretchRight(x, 1);
    }

    /**
     * Stretches the {@link MatrixWrapper} from the upper side.
     * @param y     The target y coordinate.
     * @param delta The stretching index.
     * @return      The stretched {@link MatrixWrapper}.
     */
    public MatrixWrapper stretchTop(float y, float delta) {
        return blend(
                new MatrixWrapper(
                        matrixStack,
                        xLU, y,
                        xLD, yLD,
                        xRD, yRD,
                        xRU, y
                ), delta
        );
    }

    /**
     * Stretches the {@link MatrixWrapper} from the lower side.
     * @param y     The target y coordinate.
     * @param delta The stretching index.
     * @return      The stretched {@link MatrixWrapper}.
     */
    public MatrixWrapper stretchBottom(float y, float delta) {
        return blend(
                new MatrixWrapper(
                        matrixStack,
                        xLU, yLU,
                        xLD, y,
                        xRD, y,
                        xRU, yRU
                ), delta
        );
    }

    /**
     * Stretches the {@link MatrixWrapper} from the left side.
     * @param x     The target x coordinate.
     * @param delta The stretching index.
     * @return      The stretched {@link MatrixWrapper}.
     */
    public MatrixWrapper stretchLeft(float x, float delta) {
        return blend(
                new MatrixWrapper(
                        matrixStack,
                        x,   yLU,
                        x,   yLD,
                        xRD, yRD,
                        xRU, yRU
                ), delta
        );
    }

    /**
     * Stretches the {@link MatrixWrapper} from the right side.
     * @param x     The target x coordinate.
     * @param delta The stretching index.
     * @return      The stretched {@link MatrixWrapper}.
     */
    public MatrixWrapper stretchRight(float x, float delta) {
        return blend(
                new MatrixWrapper(
                        matrixStack,
                        xLU, yLU,
                        xLD, yLD,
                        x,   yRD,
                        x,   yRU
                ), delta
        );
    }

    /**
     * Squeezes the upper side of the {@link MatrixWrapper}.
     * @param delta The squeezing index.
     * @return      The squeezed {@link MatrixWrapper}.
     */
    public MatrixWrapper squeezeTop(float delta) {
        return new MatrixWrapper(
                matrixStack,
                xLU, lerp(yLU, yLD, delta),
                xLD, yLD,
                xRD, yRD,
                xRU, lerp(yRU, yRD, delta)
        );
    }

    /**
     * Squeezes the lower side of the {@link MatrixWrapper}.
     * @param delta The squeezing index.
     * @return      The squeezed {@link MatrixWrapper}.
     */
    public MatrixWrapper squeezeBottom(float delta) {
        return new MatrixWrapper(
                matrixStack, xLU, yLU,
                xLD, lerp(yLD, yLU, delta),
                xRD, lerp(yRD, yRU, delta),
                xRU, yRU
        );
    }

    /**
     * Squeezes the left side of the {@link MatrixWrapper}.
     * @param delta The squeezing index.
     * @return      The squeezed {@link MatrixWrapper}.
     */
    public MatrixWrapper squeezeLeft(float delta) {
        return new MatrixWrapper(
                matrixStack,
                lerp(xLU, xRU, delta), yLU,
                lerp(xLD, xRD, delta), yLD,
                xRD, yRD,
                xRU, yRU
        );
    }

    /**
     * Squeezes the right side of the {@link MatrixWrapper}.
     * @param delta The squeezing index.
     * @return      The squeezed {@link MatrixWrapper}.
     */
    public MatrixWrapper squeezeRight(float delta) {
        return new MatrixWrapper(
                matrixStack, xLU, yLU,
                xLD, yLD,
                lerp(xRD, xLD, delta), yRD,
                lerp(xRU, xLU, delta), yRU
        );
    }

    /**
     * Flips the {@link MatrixWrapper} horizontally.
     */
    public MatrixWrapper flipHorizontal() {
        return flipHorizontal(1);
    }

    /**
     * Flips the {@link MatrixWrapper} vertically.
     */
    public MatrixWrapper flipVertical() {
        return flipVertical(1);
    }

    /**
     * Flips the {@link MatrixWrapper} diagonally from left upper to right lower.
     */
    public MatrixWrapper flipDiagonalLURD() {
        return flipDiagonalLURD(1);
    }

    /**
     * Flips the {@link MatrixWrapper} diagonally from right upper to left lower.
     */
    public MatrixWrapper flipDiagonalRULD() {
        return flipDiagonalRULD(1);
    }

    /**
     * Flips the {@link MatrixWrapper} horizontally.
     * @param delta The flipping index.
     * @return      The flipped {@link MatrixWrapper}.
     */
    public MatrixWrapper flipHorizontal(float delta) {
        return blend(
                new MatrixWrapper(
                        matrixStack,
                        xRU, yRU,
                        xRD, yRD,
                        xLD, yLD,
                        xLU, yLU
                ), delta
        );
    }

    /**
     * Flips the {@link MatrixWrapper} vertically.
     * @param delta The flipping index.
     * @return      The flipped {@link MatrixWrapper}.
     */
    public MatrixWrapper flipVertical(float delta) {
        return blend(
                new MatrixWrapper(
                        matrixStack,
                        xLD, yLD,
                        xLU, yLU,
                        xRU, yRU,
                        xRD, yRD
                ), delta
        );
    }

    /**
     * Flips the {@link MatrixWrapper} diagonally from left upper to right lower.
     * @param delta The flipping index.
     * @return      The flipped {@link MatrixWrapper}.
     */
    public MatrixWrapper flipDiagonalLURD(float delta) {
        return blend(
                new MatrixWrapper(
                        matrixStack,
                        xRD, yRD,
                        xLD, yLD,
                        xLU, yLU,
                        xRU, yRU
                ), delta
        );
    }

    /**
     * Flips the {@link MatrixWrapper} diagonally from right upper to left lower.
     * @param delta The flipping index.
     * @return      The flipped {@link MatrixWrapper}.
     */
    public MatrixWrapper flipDiagonalRULD(float delta) {
        return blend(
                new MatrixWrapper(
                        matrixStack,
                        xLU, yLU,
                        xRU, yRU,
                        xRD, yRD,
                        xLD, yLD
                ), delta
        );
    }
}
