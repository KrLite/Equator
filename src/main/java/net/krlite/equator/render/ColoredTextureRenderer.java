package net.krlite.equator.render;

import com.mojang.blaze3d.systems.RenderSystem;
import net.krlite.equator.base.sprite.IdentifierSprite;
import net.krlite.equator.base.MatrixWrapper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix4f;

import java.awt.*;

public class ColoredTextureRenderer extends DrawableHelper {
    public ColoredTextureRenderer() {

    }

    /**
     * Renders a centered colorized texture in square by the
     * default size of 256 pixels, a simple version of
     *
     * {@link
     * #renderCenteredColoredTexture(Identifier, Color, MatrixStack, float, float, int, int, int, int, int, int)
     * renderCenteredColoredTexture.}
     */
    public void renderCenteredColoredTexture(
            Identifier identifier, Color color,
            MatrixStack matrixStack,
            float xCentered,    float yCentered,
            int x,              int y,
            int width,          int height
    ) {
        renderCenteredColoredTexture(
                identifier, color,
                matrixStack,
                xCentered,  yCentered,
                x,          y,
                width,      height,
                256, 256
        );
    }

    /**
     * Renders a colorized texture in square by the default
     * size of 256 pixels, a simple version of
     *
     * {@link
     * #renderPositionedColoredTexture(Identifier, Color, MatrixStack, float, float, int, int, int, int, int, int)
     * renderPositionedColoredTexture.}
     */
    public void renderPositionedColoredTexture(
            Identifier identifier, Color color,
            MatrixStack matrixStack,
            float xPos, float yPos,
            int x,      int y,
            int width,  int height
    ) {
        renderPositionedColoredTexture(
                identifier, color,
                matrixStack,
                xPos,   yPos,
                x,      y,
                width,  height,
                256, 256
        );
    }

    /**
     * Renders a centered colorized texture in square, a simple
     * version of
     *
     * {@link
     * #renderColoredTexture(Identifier, Color, MatrixStack, float, float, float, float, float, float, float, float)
     * renderColoredTexture.}
     *
     * @param xCentered     x center,
     * @param yCentered     y center;
     * @param width         width in the target texture,
     * @param height        height in the target texture.
     */
    public void renderCenteredColoredTexture(
            IdentifierSprite identifierSprite,
            Color color,
            MatrixStack matrixStack,
            float xCentered,    float yCentered,
            int width,          int height
    ) {
        float xPos = xCentered - width / 2.0F, yPos = yCentered + height / 2.0F;

        renderColoredTexture(
                identifierSprite, color,
                matrixStack,
                xPos, yPos, xPos + width, yPos + height
        );
    }

    /**
     * Renders a centered colorized texture in square, a simple
     * version of
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
     */
    public void renderCenteredColoredTexture(
            Identifier identifier, Color color,
            MatrixStack matrixStack,
            float xCentered,    float yCentered,
            int x,              int y,
            int width,          int height,
            int textureWidth,   int textureHeight
    ) {
        float xPos = xCentered - width / 2.0F, yPos = yCentered + height / 2.0F;

        renderColoredTexture(
                identifier, color,
                matrixStack,
                xPos, yPos, xPos + width, yPos + height,
                (float) x / textureWidth, (float) y / textureHeight,
                (float) (x + width) / textureWidth, (float) (y + height) / textureHeight
        );
    }

    /**
     * Renders a colorized texture in square, a simple version of
     *
     * {@link
     * #renderColoredTexture(Identifier, Color, MatrixStack, float, float, float, float, float, float, float, float)
     * renderColoredTexture.}
     *
     * @param xPos          x left-up,
     * @param yPos          y left-up;
     * @param width         width,
     * @param height        height.
     */
    public void renderPositionedColoredTexture(
            IdentifierSprite identifierSprite,
            Color color,
            MatrixStack matrixStack,
            float xPos, float yPos,
            int width,  int height
    ) {
        renderColoredTexture(
                identifierSprite, color,
                matrixStack,
                xPos, yPos, xPos + width, yPos + height

        );
    }

    /**
     * Renders a colorized texture in square, a simple version of
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
     */
    public void renderPositionedColoredTexture(
            Identifier identifier, Color color,
            MatrixStack matrixStack,
            float xPos,         float yPos,
            int x,              int y,
            int width,          int height,
            int textureWidth,   int textureHeight
    ) {
        renderColoredTexture(
                identifier, color,
                matrixStack,
                xPos, yPos, xPos + width, yPos + height,
                (float) x / textureWidth, (float) y / textureHeight,
                (float) (x + width) / textureWidth, (float) (y + height) / textureHeight
        );
    }

    /**
     * Renders a colorized camera overlay.
     *
     * @see
     * #renderColoredTexture(Identifier, Color, MatrixStack, float, float, float, float, float, float, float, float)
     * renderColoredTexture
     */
    public void renderColoredOverlay(Identifier identifier, Color color, MatrixStack matrixStack) {
        renderColoredTexture(
                identifier, color,
                matrixStack,
                0.0F, 0.0F,
                MinecraftClient.getInstance().getWindow().getScaledWidth(),
                MinecraftClient.getInstance().getWindow().getScaledHeight(),
                0.0F, 0.0F, 1.0F, 1.0F
        );
    }

    public void renderScaledColoredOverlay(
            Identifier identifier, Color color,
            MatrixStack matrixStack,
            int textureSize
    ) {
        renderScaledColoredOverlay(
                identifier, color,
                matrixStack,
                textureSize, textureSize
        );
    }

    public void renderScaledColoredOverlay(
            Identifier identifier, Color color,
            MatrixStack matrixStack,
            int textureWidth, int textureHeight
    ) {
        float
                scaled = (float) MinecraftClient.getInstance().getWindow().getScaledHeight() / (float) MinecraftClient.getInstance().getWindow().getScaledWidth(),
                clamped = (float) textureHeight / (float) textureWidth;

        renderColoredTexture(
                identifier, color,
                matrixStack,
                0, 0,
                MinecraftClient.getInstance().getWindow().getScaledWidth(), MinecraftClient.getInstance().getWindow().getScaledHeight(),
                (1 - Math.min(clamped / scaled, 1)) / 2, (1 - Math.min(scaled / clamped, 1)) / 2,
                (1 + Math.min(clamped / scaled, 1)) / 2, (1 + Math.min(scaled / clamped, 1)) / 2
        );
    }

    /**
     * Renders a colorized camera overlay in a fixed style
     * (texture will split into four quarters and render separately).
     *
     * @see
     * #renderColoredTexture(Identifier, Color, MatrixStack, float, float, float, float, float, float, float, float)
     * renderColoredTexture
     */
    public void renderFixedColoredOverlay(Identifier identifier, Color color, MatrixStack matrixStack) {
        float
                quarterSize = Math.min(
                        MinecraftClient.getInstance().getWindow().getScaledWidth() / 2.0F,
                        MinecraftClient.getInstance().getWindow().getScaledHeight() / 2.0F
                ),
                width = MinecraftClient.getInstance().getWindow().getScaledWidth(),
                height = MinecraftClient.getInstance().getWindow().getScaledHeight();

        // Left Up Quarter
        renderColoredTexture(
                identifier, color,
                matrixStack,
                0.0F, 0.0F,
                quarterSize, quarterSize,
                0.0F, 0.0F, 0.5F, 0.5F
        );

        // Left Fixer
        renderColoredTexture(
                identifier, color,
                matrixStack,
                0.0F, quarterSize,
                quarterSize, height - quarterSize,
                0.0F, 0.5F, 0.5F, 0.5F
        );

        // Left Down Quarter
        renderColoredTexture(
                identifier, color,
                matrixStack,
                0.0F, height - quarterSize,
                quarterSize, height,
                0.0F, 0.5F, 0.5F, 1.0F
        );

        // Middle Fixer
        renderColoredTexture(
                identifier, color,
                matrixStack,
                quarterSize, 0.0F,
                width - quarterSize, height,
                0.5F, 0.0F, 0.5F, 1.0F
        );

        // Right Down Quarter
        renderColoredTexture(
                identifier, color,
                matrixStack,
                width - quarterSize, height - quarterSize,
                width, height,
                0.5F, 0.5F, 1.0F, 1.0F
        );

        // Right Fixer
        renderColoredTexture(
                identifier, color,
                matrixStack,
                width - quarterSize, quarterSize,
                width, height - quarterSize,
                0.5F, 0.5F, 1.0F, 0.5F
        );

        // Right Up Quarter
        renderColoredTexture(
                identifier, color,
                matrixStack,
                width - quarterSize, 0.0F,
                width, quarterSize,
                0.5F, 0.0F, 1.0F, 0.5F
        );
    }

    /**
     * Renders a wrapped colorized texture in square.
     *
     * @see
     * #renderColoredTexture(Identifier, Color, Matrix4f, float, float, float, float, float, float, float, float, float, float, float, float)
     * renderColoredTexture
     */
    public void renderWrappedTexture(
            IdentifierSprite identifierSprite,
            Color color,
            MatrixStack matrixStack,
            MatrixWrapper matrixWrapper
    ) {
        renderColoredTexture(
                identifierSprite.getIdentifier(), color,
                matrixStack.peek().getPositionMatrix(),
                matrixWrapper.xLU(), matrixWrapper.yLU(),
                matrixWrapper.xLD(), matrixWrapper.yLD(),
                matrixWrapper.xRD(), matrixWrapper.yRD(),
                matrixWrapper.xRU(), matrixWrapper.yRU(),
                identifierSprite.uBegin(),
                identifierSprite.vBegin(),
                identifierSprite.uEnd(),
                identifierSprite.vEnd()
        );
    }

    /**
     * Renders a wrapped colorized texture in square.
     *
     * @see
     * #renderColoredTexture(Identifier, Color, Matrix4f, float, float, float, float, float, float, float, float, float, float, float, float)
     * renderColoredTexture
     */
    public void renderWrappedTexture(
            Identifier identifier, Color color,
            MatrixStack matrixStack,
            MatrixWrapper matrixWrapper,
            float uBegin,   float vBegin,
            float uEnd,     float vEnd
    ) {
        renderColoredTexture(
                identifier, color,
                matrixStack.peek().getPositionMatrix(),
                matrixWrapper.xLU(), matrixWrapper.yLU(),
                matrixWrapper.xLD(), matrixWrapper.yLD(),
                matrixWrapper.xRD(), matrixWrapper.yRD(),
                matrixWrapper.xRU(), matrixWrapper.yRU(),
                uBegin, vBegin,
                uEnd,   vEnd
        );
    }

    /**
     * Renders a colorized texture in square.
     *
     * @see
     * #renderColoredTexture(Identifier, Color, Matrix4f, float, float, float, float, float, float, float, float, float, float, float, float)
     * renderColoredTexture
     */
    public void renderColoredTexture(
            IdentifierSprite identifierSprite,
            Color color,
            MatrixStack matrixStack,
            float xBegin, float yBegin,
            float xEnd, float yEnd
    ) {
        renderColoredTexture(
                identifierSprite.getIdentifier(), color,
                matrixStack.peek().getPositionMatrix(),
                xBegin, yBegin,
                xBegin, yEnd,
                xEnd,   yEnd,
                xEnd,   yBegin,
                identifierSprite.uBegin(),
                identifierSprite.vBegin(),
                identifierSprite.uEnd(),
                identifierSprite.vEnd()
        );
    }

    /**
     * Renders a colorized texture in square.
     *
     * @see
     * #renderColoredTexture(Identifier, Color, Matrix4f, float, float, float, float, float, float, float, float, float, float, float, float)
     * renderColoredTexture
     */
    public void renderColoredTexture(
            Identifier identifier, Color color,
            MatrixStack matrixStack,
            float xBegin,   float yBegin,
            float xEnd,     float yEnd,
            float uBegin,   float vBegin,
            float uEnd,     float vEnd
    ) {
        renderColoredTexture(
                identifier, color,
                matrixStack.peek().getPositionMatrix(),
                xBegin, yBegin,
                xBegin, yEnd,
                xEnd,   yEnd,
                xEnd,   yBegin,
                uBegin, vBegin,
                uEnd,   vEnd
        );
    }

    /**
     * Renders a colorized texture in four specified vertices.
     *
     * @param identifier    identifier of the target texture.
     * @param color         target Java Color, applies on the texture.
     * @param xLU           x left-up,
     * @param yLU           y left-up;
     * @param xLD           x left-down,
     * @param yLD           y left-down;
     * @param xRD           x right-down,
     * @param yRD           y right-down;
     * @param xRU           x right-up,
     * @param yRU           y right-up.
     * @param uBegin        texture u begin,
     * @param vBegin        texture v begin;
     * @param uEnd          texture u end,
     * @param vEnd          texture v end.
     */
    private static void renderColoredTexture(
            Identifier identifier, Color color,
            Matrix4f matrix4f,
            float xLU,      float yLU,
            float xLD,      float yLD,
            float xRD,      float yRD,
            float xRU,      float yRU,
            float uBegin,   float vBegin,
            float uEnd,     float vEnd
    ) {
        int
                r = color.getRed(),
                g = color.getGreen(),
                b = color.getBlue(),
                a = color.getAlpha();

        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(r / 255.0F, g / 255.0F, b / 255.0F, a / 255.0F);
        RenderSystem.setShaderTexture(0, identifier);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();

        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
        bufferBuilder
                .vertex(matrix4f, xLD, yLD, -90.0F)
                .texture(uBegin, vEnd)
                .color(r, g, b, a)
                .next();

        bufferBuilder
                .vertex(matrix4f, xRD, yRD, -90.0F)
                .texture(uEnd, vEnd)
                .color(r, g, b, a)
                .next();

        bufferBuilder
                .vertex(matrix4f, xRU, yRU, -90.0F)
                .texture(uEnd, vBegin)
                .color(r, g, b, a)
                .next();

        bufferBuilder
                .vertex(matrix4f, xLU, yLU, -90.0F)
                .texture(uBegin, vBegin)
                .color(r, g, b, a)
                .next();
        bufferBuilder.end();
        tessellator.draw();

        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }
}
