package net.krlite.equator.render.sprite;

import net.krlite.equator.annotation.See;
import net.krlite.equator.core.ShortStringable;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

import java.util.Objects;

/**
 * A sprite that defines a vertical set of {@link IdentifierSprite}s.
 */
@See(IdentifierSprite.class)
public record VerticalSprite(Identifier identifier, int step) implements ShortStringable, Cloneable {
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
        return getClass().getSimpleName() + "{" + formatFields() + "}";
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, step);
    }

    @Override
    public VerticalSprite clone() {
        try {
            return (VerticalSprite) super.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new RuntimeException(cloneNotSupportedException);
        }
    }
}
