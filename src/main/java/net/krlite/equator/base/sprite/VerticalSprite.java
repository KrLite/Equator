package net.krlite.equator.base.sprite;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

import java.util.Objects;

/**
 * A sprite that defines a vertical set of {@link IdentifierSprite}s.
 */
public record VerticalSprite(Identifier identifier, int step) {
    /**
     * Creates a {@link VerticalSprite} by splitting an {@link Identifier} vertically into slices.
     * @param identifier    The dedicated {@link Identifier}.
     * @param step          The number of slices.
     */
    public VerticalSprite {}

    /**
     * Creates a {@link VerticalSprite} by splitting an {@link Identifier} vertically into square slices.
     * @param identifier    The dedicated {@link Identifier}.
     * @param textureWidth  The width of the texture.
     * @param textureHeight The height of the texture.
     */
    public VerticalSprite(Identifier identifier, int textureWidth, int textureHeight) {
        this(identifier, textureHeight / textureWidth);
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

    @Override
    public String toString() {
        return "VerticalSprite" + "{" +
                "identifier=" + identifier +
                ", step=" + step +
                "}";
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, step);
    }
}
