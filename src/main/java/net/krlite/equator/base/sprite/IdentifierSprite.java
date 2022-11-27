package net.krlite.equator.base.sprite;

import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

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
        this.identifier = identifier;
        uBegin = (float) x / textureSize;
        vBegin = (float) y / textureSize;
        uEnd = (float) (x + width) / textureSize;
        vEnd = (float) (y + height) / textureSize;
    }

    public IdentifierSprite(Identifier identifier, int textureWidth, int textureHeight, int x, int y, int width, int height) {
        this.identifier = identifier;
        uBegin = (float) x / textureWidth;
        vBegin = (float) y / textureHeight;
        uEnd = (float) (x + width) / textureWidth;
        vEnd = (float) (y + height) / textureHeight;
    }

    @Contract("_ -> new")
    public static @NotNull IdentifierSprite of(Identifier identifier) {
        return new IdentifierSprite(identifier, 0, 0, 1, 1);
    }

    public Identifier getIdentifier() {
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
}
