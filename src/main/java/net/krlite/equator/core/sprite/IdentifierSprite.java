package net.krlite.equator.core.sprite;

import net.krlite.equator.render.Equator;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * A sprite that defines a part of an identifier.<br />
 * Compatible with {@link Equator the Equator Renderers}.
 */
public class IdentifierSprite {
    private final Identifier identifier;
    private final float uBegin, vBegin, uEnd, vEnd;

    /**
     * Creates an {@link IdentifierSprite}.
     * @param identifier    The dedicated {@link Identifier}.
     * @param uBegin        The beginning of the u coordinate.
     * @param vBegin        The beginning of the v coordinate.
     * @param uEnd          The end of the u coordinate.
     * @param vEnd          The end of the v coordinate.
     */
    public IdentifierSprite(Identifier identifier, float uBegin, float vBegin, float uEnd, float vEnd) {
        this.identifier = identifier;
        this.uBegin = uBegin;
        this.vBegin = vBegin;
        this.uEnd = uEnd;
        this.vEnd = vEnd;
    }

    /**
     * Creates an {@link IdentifierSprite} of the real texture size in square.
     * @param identifier    The dedicated {@link Identifier}.
     * @param textureSize   The size of the texture.
     * @param x             The x coordinate of the left upper point of the sprite in the texture.
     * @param y             The y coordinate of the left upper point of the sprite in the texture.
     * @param width         The width of the sprite in the texture.
     * @param height        The height of the sprite in the texture.
     */
    public IdentifierSprite(Identifier identifier, int textureSize, int x, int y, int width, int height) {
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
    public IdentifierSprite(Identifier identifier, int textureWidth, int textureHeight, int x, int y, int width, int height) {
        this(
                identifier,
                (float) x / textureWidth, (float) y / textureHeight,
                (float) (x + width) / textureWidth, (float) (y + height) / textureHeight
        );
    }

    /**
     * Creates a full sized {@link IdentifierSprite} from an {@link Identifier}.
     * @param identifier    The dedicated {@link Identifier}.
     */
    @Contract("_ -> new")
    public static @NotNull IdentifierSprite of(Identifier identifier) {
        return new IdentifierSprite(identifier, 0, 0, 1, 1);
    }

    /**
     * Gets the dedicated {@link Identifier}.
     */
    public Identifier identifier() {
        return this.identifier;
    }

    public float uBegin() {
        return this.uBegin;
    }

    public float vBegin() {
        return this.vBegin;
    }

    public float uEnd() {
        return this.uEnd;
    }

    public float vEnd() {
        return this.vEnd;
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
}
