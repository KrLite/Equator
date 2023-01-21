package net.krlite.equator.render.sprite;

import net.krlite.equator.core.ShortStringable;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * A class that represents an {@link Identifier} with specified uvs.
 * @param identifier    The {@link Identifier}, which represents the texture.
 * @param uBegin        The u begin.
 * @param vBegin        The v begin.
 * @param uEnd          The u end.
 * @param vEnd          The v end.
 */
public record IdentifierSprite(@NotNull Identifier identifier, float uBegin, float vBegin, float uEnd, float vEnd)
        implements ShortStringable {
    /**
     * Creates a full sized {@link IdentifierSprite} from an {@link Identifier}.
     * @param identifier    The dedicated {@link Identifier}.
     * @return              The full sized {@link IdentifierSprite}.
     */
    @Contract("_ -> new")
    public static IdentifierSprite of(@NotNull Identifier identifier) {
        return new IdentifierSprite(identifier, 0, 0, 1, 1);
    }

    /**
     * Creates an {@link IdentifierSprite}.
     * @param identifier    The dedicated {@link Identifier}.
     * @param uBegin        The beginning of the u coordinate.
     * @param vBegin        The beginning of the v coordinate.
     * @param uEnd          The end of the u coordinate.
     * @param vEnd          The end of the v coordinate.
     */
    public IdentifierSprite {}

    /**
     * Creates an {@link IdentifierSprite} of the real texture size in square.
     * @param identifier    The dedicated {@link Identifier}.
     * @param textureSize   The size of the texture.
     * @param x             The x coordinate of the left upper point of the sprite in the texture.
     * @param y             The y coordinate of the left upper point of the sprite in the texture.
     * @param width         The width of the sprite in the texture.
     * @param height        The height of the sprite in the texture.
     */
    public IdentifierSprite(@NotNull Identifier identifier, int textureSize, int x, int y, int width, int height) {
        this(
                identifier,
                textureSize, textureSize, x, y, width, height
        );
    }

    /**
     * Creates an {@link IdentifierSprite} of the real texture size.
     * @param identifier    The dedicated {@link Identifier}.
     * @param textureWidth  The width of the texture.
     * @param textureHeight The height of the texture.
     * @param x             The x coordinate of the left upper point of the sprite in the texture.
     * @param y             The y coordinate of the left upper point of the sprite in the texture.
     * @param width         The width of the sprite in the texture.
     * @param height        The height of the sprite in the texture.
     */
    public IdentifierSprite(@NotNull Identifier identifier, int textureWidth, int textureHeight, int x, int y, int width, int height) {
        this(
                identifier,
                (float) x / textureWidth, (float) y / textureHeight,
                (float) (x + width) / textureWidth, (float) (y + height) / textureHeight
        );
    }

    /**
     * Masks the {@link IdentifierSprite} with another {@link IdentifierSprite}.
     * @param sprite    The {@link IdentifierSprite} to mask with.
     * @return          The masked {@link IdentifierSprite}.
     */
    public IdentifierSprite mask(IdentifierSprite sprite) {
        return mask(sprite.uBegin, sprite.vBegin, sprite.uEnd, sprite.vEnd);
    }

    /**
     * Masks the {@link IdentifierSprite}.
     * @param uBegin    The beginning of the u coordinate.
     * @param vBegin    The beginning of the v coordinate.
     * @param uEnd      The ending of the u coordinate.
     * @param vEnd      The ending of the v coordinate.
     * @return          The masked {@link IdentifierSprite}.
     */
    public IdentifierSprite mask(float uBegin, float vBegin, float uEnd, float vEnd) {
        return new IdentifierSprite(this.identifier, uBegin, vBegin, uEnd, vEnd);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" + formatFields() + "}";
    }
}
