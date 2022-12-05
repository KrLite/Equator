package net.krlite.equator.core.sprite;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

/**
 * A sprite that defines a vertical set of {@link IdentifierSprite}s.
 */
public class VerticalSprite {
    private final Identifier identifier;
    private final int step;

    /**
     * Creates a {@link VerticalSprite} by splitting an {@link Identifier} vertically into slices.
     * @param identifier    The dedicated {@link Identifier}.
     * @param step          The number of slices.
     */
    public VerticalSprite(Identifier identifier, int step) {
        this.identifier = identifier;
        this.step = step;
    }

    /**
     * Creates a {@link VerticalSprite} by splitting an {@link Identifier} vertically into square slices.
     * @param identifier    The dedicated {@link Identifier}.
     * @param textureWidth  The width of the texture.
     * @param textureHeight The height of the texture.
     */
    public VerticalSprite(Identifier identifier, int textureWidth, int textureHeight) {
        this.identifier = identifier;
        step = textureHeight / textureWidth;
    }

    /**
     * Gets the dedicated {@link Identifier}.
     */
    public Identifier identifier() {
        return this.identifier;
    }

    /**
     * Gets a slice of the {@link VerticalSprite}.
     * @param index The index of the target slice.
     * @return      The slice as an {@link IdentifierSprite}.
     */
    public IdentifierSprite get(int index) {
        float
                vBegin = (float) (MathHelper.clamp(index, 1, this.step) - 1) / this.step,
                vEnd = (float) MathHelper.clamp(index, 1, this.step) / this.step;

        return new IdentifierSprite(
                this.identifier,
                0, vBegin, 1, vEnd
        );
    }
}
