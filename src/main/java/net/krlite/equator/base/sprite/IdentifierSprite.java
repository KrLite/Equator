package net.krlite.equator.base.sprite;

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

    public IdentifierSprite(Identifier identifier, float uBegin, float vBegin, float uEnd, float vEnd) {
        this.identifier = identifier;
        this.uBegin = uBegin;
        this.vBegin = vBegin;
        this.uEnd = uEnd;
        this.vEnd = vEnd;
    }

    public IdentifierSprite(Identifier identifier, int textureSize, int x, int y, int width, int height) {
        this(
                identifier,
                textureSize, textureSize, x, y, width, height
        );
    }

    public IdentifierSprite(Identifier identifier, int textureWidth, int textureHeight, int x, int y, int width, int height) {
        this(
                identifier,
                (float) x / textureWidth, (float) y / textureHeight,
                (float) (x + width) / textureWidth, (float) (y + height) / textureHeight
        );
    }

    @Contract("_ -> new")
    public static @NotNull IdentifierSprite of(Identifier identifier) {
        return new IdentifierSprite(identifier, 0, 0, 1, 1);
    }

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

    public IdentifierSprite getMaskedSprite(float uBegin, float vBegin, float uEnd, float vEnd) {
        return new IdentifierSprite(this.identifier, uBegin, vBegin, uEnd, vEnd);
    }
}
