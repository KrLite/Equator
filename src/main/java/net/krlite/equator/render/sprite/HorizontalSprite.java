package net.krlite.equator.render.sprite;

import net.krlite.equator.core.ShortStringable;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

/**
 * A sprite that defines a horizontal set of {@link IdentifierSprite}s.
 */
public record HorizontalSprite(Identifier identifier, int step) implements ShortStringable, Cloneable {
    /**
     * Creates a {@link HorizontalSprite} by splitting an {@link Identifier} horizontally into slices.
     * @param identifier    The dedicated {@link Identifier}.
     * @param step          The number of slices.
     */
    public HorizontalSprite {}

    /**
     * Creates a {@link HorizontalSprite} by splitting an {@link Identifier} horizontally into square slices.
     * @param identifier    The dedicated {@link Identifier}.
     * @param textureWidth  The width of the texture.
     * @param textureHeight The height of the texture.
     */
    public HorizontalSprite(Identifier identifier, int textureWidth, int textureHeight) {
        this(identifier, textureWidth / textureHeight);
    }

    /**
     * Gets a slice of the {@link HorizontalSprite}.
     * @param index The index of the target slice.
     * @return      The slice as an {@link IdentifierSprite}.
     */
    public IdentifierSprite get(int index) {
        float
                uBegin = (float) (MathHelper.clamp(index, 1, this.step)  - 1) / this.step,
                uEnd = (float) MathHelper.clamp(index, 1, this.step) / this.step;

        return new IdentifierSprite(
                this.identifier,
                uBegin, 0, uEnd, 1
        );
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" + formatFields() + "}";
    }

    @Override
    public HorizontalSprite clone() {
        try {
            return (HorizontalSprite) super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new RuntimeException(cloneNotSupportedException);
        }
    }
}
