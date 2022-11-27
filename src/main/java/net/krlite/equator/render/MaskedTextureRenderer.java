package net.krlite.equator.render;

import net.krlite.equator.base.sprite.IdentifierSprite;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import oshi.util.tuples.Pair;

import java.awt.*;

public class MaskedTextureRenderer extends ColoredTextureRenderer {
    public MaskedTextureRenderer() {

    }

    /**
     * Renders a centered colorized texture in a limited square area
     * by the default size of 256 pixels, a simple version of
     *
     * {@link
     * #renderCenteredColoredTexture(Identifier, Color, MatrixStack, float, float, int, int, int, int, int, int, float, float, float, float)
     * renderCenteredColoredTexture.}
     */
    public void renderCenteredColoredTexture(
            Identifier identifier, Color color,
            MatrixStack matrixStack,
            float xCentered,    float yCentered,
            int x,              int y,
            int width,          int height,
            float xMin,         float yMin,
            float xMax,         float yMax
    ) {
        renderCenteredColoredTexture(
                identifier, color,
                matrixStack,
                xCentered,  yCentered,
                x,          y,
                width,      height,
                256, 256,
                xMin,       yMin,
                xMax,       yMax
        );
    }

    /**
     * Renders a colorized texture in a limited square area by the
     * default size of 256 pixels, a simple version of
     *
     * {@link
     * #renderPositionedColoredTexture(Identifier, Color, MatrixStack, float, float, int, int, int, int, int, int, float, float, float, float)
     * renderPositionedColoredTexture.}
     */
    public void renderPositionedColoredTexture(
            Identifier identifier, Color color,
            MatrixStack matrixStack,
            float xPos, float yPos,
            int x,      int y,
            int width,  int height,
            float xMin, float yMin,
            float xMax, float yMax
    ) {
        renderPositionedColoredTexture(
                identifier, color,
                matrixStack,
                xPos,   yPos,
                x,      y,
                width,  height,
                256, 256,
                xMin,   yMin,
                xMax,   yMax
        );
    }

    /**
     * Renders a centered colorized texture in a limited square
     * area, a simple version of
     *
     * {@link
     * #renderColoredTexture(Identifier, Color, MatrixStack, float, float, float, float, float, float, float, float)
     * renderColoredTexture.}
     *
     * @param xCentered     x center,
     * @param yCentered     y center;
     * @param width         width in the target texture,
     * @param height        height in the target texture.
     * @param xMin          minimal allowed x,
     * @param yMin          minimal allowed y;
     * @param xMax          maximal allowed x,
     * @param yMax          maximal allowed y.
     */
    public void renderCenteredColoredTexture(
            IdentifierSprite identifierSprite,
            Color color,
            MatrixStack matrixStack,
            float xCentered,    float yCentered,
            int width,          int height,
            float xMin,         float yMin,
            float xMax,         float yMax
    ) {
        float xPos = xCentered - width / 2.0F, yPos = yCentered + height / 2.0F;

        renderColoredTexture(
                identifierSprite.getIdentifier(), color,
                matrixStack,
                xPos, yPos, xPos + width, yPos + height,
                xMin, yMin, xMax, yMax
        );
    }

    /**
     * Renders a centered colorized texture in a limited square
     * area, a simple version of
     *
     * {@link
     * #renderColoredTexture(Identifier, Color, MatrixStack, float, float, float, float, float, float, float, float)
     * renderColoredTexture.}
     *
     * @param xCentered     x center,
     * @param yCentered     y center;
     * @param x             x left-up in texture uv,
     * @param y             y left-up in texture uv.
     * @param width         width in the target texture,
     * @param height        height in the target texture.
     * @param textureWidth  width of the target texture,
     * @param textureHeight height of the target texture.
     * @param xMin          minimal allowed x,
     * @param yMin          minimal allowed y;
     * @param xMax          maximal allowed x,
     * @param yMax          maximal allowed y.
     */
    public void renderCenteredColoredTexture(
            Identifier identifier, Color color,
            MatrixStack matrixStack,
            float xCentered,    float yCentered,
            int x,              int y,
            int width,          int height,
            int textureWidth,   int textureHeight,
            float xMin,         float yMin,
            float xMax,         float yMax
    ) {
        float xPos = xCentered - width / 2.0F, yPos = yCentered + height / 2.0F;

        renderColoredTexture(
                identifier, color,
                matrixStack,
                xPos, yPos, xPos + width, yPos + height,
                (float) x / textureWidth, (float) y / textureHeight,
                (float) (x + width) / textureWidth, (float) (y + height) / textureHeight,
                xMin, yMin, xMax, yMax
        );
    }

    /**
     * Renders a colorized texture in a limited square area,
     * a simple version of
     *
     * {@link
     * #renderColoredTexture(Identifier, Color, MatrixStack, float, float, float, float, float, float, float, float)
     * renderColoredTexture.}
     *
     * @param xPos          x left-up,
     * @param yPos          y left-up;
     * @param width         width in the target texture,
     * @param height        height in the target texture.
     * @param xMin          minimal allowed x,
     * @param yMin          minimal allowed y;
     * @param xMax          maximal allowed x,
     * @param yMax          maximal allowed y.
     */
    public void renderPositionedColoredTexture(
            IdentifierSprite identifierSprite,
            Color color,
            MatrixStack matrixStack,
            float xPos, float yPos,
            int width,  int height,
            float xMin, float yMin,
            float xMax, float yMax
    ) {
        renderColoredTexture(
                identifierSprite.getIdentifier(), color,
                matrixStack,
                xPos, yPos, xPos + width, yPos + height,
                xMin, yMin, xMax, yMax
        );
    }

    /**
     * Renders a colorized texture in a limited square area,
     * a simple version of
     *
     * {@link
     * #renderColoredTexture(Identifier, Color, MatrixStack, float, float, float, float, float, float, float, float)
     * renderColoredTexture.}
     *
     * @param xPos          x left-up,
     * @param yPos          y left-up;
     * @param x             x left-up in texture uv,
     * @param y             y left-up in texture uv.
     * @param width         width in the target texture,
     * @param height        height in the target texture.
     * @param textureWidth  width of the target texture,
     * @param textureHeight height of the target texture.
     * @param xMin          minimal allowed x,
     * @param yMin          minimal allowed y;
     * @param xMax          maximal allowed x,
     * @param yMax          maximal allowed y.
     */
    public void renderPositionedColoredTexture(
            Identifier identifier, Color color,
            MatrixStack matrixStack,
            float xPos,      float yPos,
            int x,              int y,
            int width,          int height,
            int textureWidth,   int textureHeight,
            float xMin,         float yMin,
            float xMax,         float yMax
    ) {
        renderColoredTexture(
                identifier, color,
                matrixStack,
                xPos, yPos, xPos + width, yPos + height,
                (float) x / textureWidth, (float) y / textureHeight,
                (float) (x + width) / textureWidth, (float) (y + height) / textureHeight,
                xMin, yMin, xMax, yMax
        );
    }

    /**
     * Renders a colorized camera overlay in a limited square area.
     *
     * @see
     * #renderColoredTexture(Identifier, Color, MatrixStack, float, float, float, float, float, float, float, float)
     * renderColoredTexture
     */
    public void renderColoredOverlay(
            Identifier identifier, Color color,
            float xMin, float yMin,
            float xMax, float yMax
    ) {
        renderColoredTexture(
                identifier, color,
                new MatrixStack(),
                0.0F, 0.0F,
                MinecraftClient.getInstance().getWindow().getScaledWidth(),
                MinecraftClient.getInstance().getWindow().getScaledHeight(),
                0.0F, 0.0F, 1.0F, 1.0F,
                xMin, yMin, xMax, yMax
        );
    }

    /**
     * Renders a colorized texture in a limited square area.
     */
    public void renderColoredTexture(
            IdentifierSprite identifierSprite,
            Color color,
            MatrixStack matrixStack,
            float xBegin,   float yBegin,
            float xEnd,     float yEnd,
            float xMin,     float yMin,
            float xMax,     float yMax
    ) {
        renderColoredTexture(
                identifierSprite.getIdentifier(), color,
                matrixStack,
                xBegin, yBegin,
                xEnd,   yEnd,
                identifierSprite.uBegin(),
                identifierSprite.vBegin(),
                identifierSprite.uEnd(),
                identifierSprite.vEnd(),
                xMin,   yMin,
                xMax,   yMax
        );
    }

    /**
     * Renders a colorized texture in a limited square area.
     */
    public void renderColoredTexture(
            Identifier identifier, Color color,
            MatrixStack matrixStack,
            float xBegin,   float yBegin,
            float xEnd,     float yEnd,
            float uBegin,   float vBegin,
            float uEnd,     float vEnd,
            float xMin,     float yMin,
            float xMax,     float yMax
    ) {
        float
                width   = xEnd - xBegin,
                height  = yEnd - yBegin;

        super.renderColoredTexture(
                identifier, color,
                matrixStack,
                Math.max(xBegin, xMin), Math.max(yBegin, yMin),
                Math.min(xEnd, xMax),   Math.min(yEnd, yMax),
                xBegin < xMin
                        ? uBegin + (uEnd - uBegin) * (xMin - xBegin) / width
                        : uBegin,
                yBegin < yMin
                        ? vBegin + (vEnd - vBegin) * (yMin - yBegin) / height
                        : vBegin,
                xEnd > xMax
                        ? uEnd - (uEnd - uBegin) * (xEnd - xMax) / width
                        : vEnd,
                yEnd > yMax
                        ? vEnd + (vEnd - vBegin) * (yEnd - yMax) / height
                        : vEnd
        );
    }
}
