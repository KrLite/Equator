package net.krlite.equator.base.sprite;

import net.krlite.equator.base.color.PreciseColor;
import net.krlite.equator.base.geometry.Rect;
import net.krlite.equator.render.Equator;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public record IdentifierSprite(@NotNull Identifier identifier, float uBegin, float vBegin, float uEnd, float vEnd) {
    /**
     * Creates a full sized {@link IdentifierSprite} from an {@link Identifier}.
     * @param identifier    The dedicated {@link Identifier}.
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

    // === Renderer ===
    public IdentifierSprite rect(@NotNull MatrixStack matrixStack, @NotNull Rect rect, @NotNull PreciseColor textureColor) {
        new Equator.Renderer(matrixStack, this).rect(rect, textureColor);
        return this;
    }

    public IdentifierSprite rect(@NotNull MatrixStack matrixStack, @NotNull Rect rect) {
        return rect(matrixStack, rect, PreciseColor.WHITE);
    }

    public IdentifierSprite rect(@NotNull MatrixStack matrixStack, double x, double y, double width, double height, @NotNull PreciseColor textureColor) {
        return rect(matrixStack, new Rect(x, y, width, height), textureColor);
    }

    public IdentifierSprite rect(@NotNull MatrixStack matrixStack, double x, double y, double width, double height) {
        return rect(matrixStack, new Rect(x, y, width, height), PreciseColor.WHITE);
    }

    public IdentifierSprite overlay(@NotNull MatrixStack matrixStack, @NotNull PreciseColor textureColor) {
        new Equator.Renderer(matrixStack, this).overlay(textureColor);
        return this;
    }

    public IdentifierSprite overlay(@NotNull MatrixStack matrixStack) {
        return overlay(matrixStack, PreciseColor.WHITE);
    }

    public IdentifierSprite fixedOverlay(@NotNull MatrixStack matrixStack, @NotNull PreciseColor textureColor) {
        new Equator.Renderer(matrixStack, this).fixedOverlay(textureColor);
        return this;
    }

    public IdentifierSprite fixedOverlay(@NotNull MatrixStack matrixStack) {
       return fixedOverlay(matrixStack, PreciseColor.WHITE);
    }

    public IdentifierSprite scaledOverlay(@NotNull MatrixStack matrixStack, @NotNull PreciseColor textureColor, float aspectRatio) {
        new Equator.Renderer(matrixStack, this).scaledOverlay(textureColor, aspectRatio);
        return this;
    }

    public IdentifierSprite scaledOverlay(@NotNull MatrixStack matrixStack, float aspectRatio) {
        return scaledOverlay(matrixStack, PreciseColor.WHITE, aspectRatio);
    }

    public IdentifierSprite scaledOverlay(@NotNull MatrixStack matrixStack) {
        return scaledOverlay(matrixStack, PreciseColor.WHITE, 1);
    }

    public IdentifierSprite clampedOverlay(@NotNull MatrixStack matrixStack, @NotNull PreciseColor textureColor, float aspectRatio) {
        new Equator.Renderer(matrixStack, this).clampedOverlay(textureColor, aspectRatio);
        return this;
    }

    public IdentifierSprite clampedOverlay(@NotNull MatrixStack matrixStack, float aspectRatio) {
        return clampedOverlay(matrixStack, PreciseColor.WHITE, aspectRatio);
    }

    public IdentifierSprite clampedOverlay(@NotNull MatrixStack matrixStack) {
        return clampedOverlay(matrixStack, PreciseColor.WHITE, 1);
    }

    public IdentifierSprite tiledOverlay(@NotNull MatrixStack matrixStack, @NotNull PreciseColor textureColor, float aspectRatio) {
        new Equator.Renderer(matrixStack, this).tiledOverlay(textureColor, aspectRatio);
        return this;
    }

    public IdentifierSprite tiledOverlay(@NotNull MatrixStack matrixStack, float aspectRatio) {
        return tiledOverlay(matrixStack, PreciseColor.WHITE, aspectRatio);
    }

    public IdentifierSprite tiledOverlay(@NotNull MatrixStack matrixStack) {
        return tiledOverlay(matrixStack, PreciseColor.WHITE, 1);
    }

    public IdentifierSprite tiledBackground(@NotNull MatrixStack matrixStack, @NotNull PreciseColor textureColor, float aspectRatio, float contraction, float uOffset, float vOffset) {
        new Equator.Renderer(matrixStack, this).tiledBackground(textureColor, aspectRatio, contraction, uOffset, vOffset);
        return this;
    }

    public IdentifierSprite tiledBackground(@NotNull MatrixStack matrixStack, @NotNull PreciseColor textureColor, float aspectRatio, float contraction) {
        return tiledBackground(matrixStack, textureColor, aspectRatio, contraction, 0, 0);
    }

    public IdentifierSprite tiledBackground(@NotNull MatrixStack matrixStack, @NotNull PreciseColor textureColor, float aspectRatio) {
        return tiledBackground(matrixStack, textureColor, aspectRatio, 7);
    }

    public IdentifierSprite tiledBackground(@NotNull MatrixStack matrixStack, @NotNull PreciseColor textureColor) {
        return tiledBackground(matrixStack, textureColor, 1);
    }

    public IdentifierSprite tiledBackground(@NotNull MatrixStack matrixStack) {
        return tiledBackground(matrixStack, PreciseColor.WHITE);
    }

    @Override
    public String toString() {
        return "IdentifierSprite" + "{" +
                "identifier=" + identifier +
                ", uBegin=" + uBegin +
                ", vBegin=" + vBegin +
                ", uEnd=" + uEnd +
                ", vEnd=" + vEnd +
                "}";
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier, uBegin, vBegin, uEnd, vEnd);
    }
}
