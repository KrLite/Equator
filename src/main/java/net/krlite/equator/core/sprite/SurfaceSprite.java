package net.krlite.equator.core.sprite;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

/**
 * A sprite that defines a 2D array set of {@link IdentifierSprite}s.
 */
public class SurfaceSprite {
    private final Identifier identifier;
    private final int stepX, stepY;

    /**
     * Creates a {@link SurfaceSprite} by splitting an {@link Identifier} into pieces.
     * @param identifier    The dedicated {@link Identifier}.
     * @param stepX         The number of pieces horizontally.
     * @param stepY         The number of pieces vertically.
     */
    public SurfaceSprite(Identifier identifier, int stepX, int stepY) {
        this.identifier = identifier;
        this.stepX = stepX;
        this.stepY = stepY;
    }

    /**
     * Creates a {@link SurfaceSprite} by splitting an {@link Identifier} into square pieces.
     * @param identifier    The dedicated {@link Identifier}.
     * @param step          The number of pieces both horizontally and vertically.
     */
    public SurfaceSprite(Identifier identifier, int step) {
        this.identifier = identifier;
        stepX = step;
        stepY = step;
    }

    /**
     * Gets the dedicated {@link Identifier}.
     */
    public Identifier identifier() {
        return this.identifier;
    }

    /**
     * Gets a piece of the {@link SurfaceSprite} on diagonal.
     * @param index The index of the target piece on diagonal.
     * @return      The piece as an {@link IdentifierSprite}.
     */
    public IdentifierSprite get(int index) {
        return get(index, index);
    }

    /**
     * Gets a piece of the {@link SurfaceSprite}.
     * @param indexX    The index of the target piece horizontally.
     * @param indexY    The index of the target piece vertically.
     * @return          The piece as an {@link IdentifierSprite}.
     */
    public IdentifierSprite get(int indexX, int indexY) {
        float
                uBegin = (float) (MathHelper.clamp(indexX, 1, stepX) - 1) / this.stepX,
                uEnd = (float) MathHelper.clamp(indexX, 1, stepX) / this.stepX,
                vBegin = (float) (MathHelper.clamp(indexY, 1, stepY) - 1) / this.stepY,
                vEnd = (float) MathHelper.clamp(indexY, 1, stepY) / this.stepY;

        return new IdentifierSprite(
                identifier,
                uBegin, vBegin, uEnd, vEnd
        );
    }
}
