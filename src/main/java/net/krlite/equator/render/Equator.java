package net.krlite.equator.render;

import com.mojang.blaze3d.systems.RenderSystem;
import net.krlite.equator.core.sprite.IdentifierSprite;
import net.krlite.equator.core.MatrixWrapper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix4f;

import java.awt.*;

/**
 * <h1>EQUATOR RENDER LIBRARY</h1>
 * Renders colorized fields bounded to sprites.
 * @see     net.minecraft.client.gui.DrawableHelper
 * @author  KrLite
 */
public class Equator extends DrawableHelper {
    private final IdentifierSprite sprite;

    /**
     * Bounds the renderer to an {@link Identifier}.
     * @param identifier    The {@link Identifier} to be bound with.
     */
    public Equator(Identifier identifier) {
        this.sprite = IdentifierSprite.of(identifier);
    }

    /**
     * Bounds the renderer to an {@link IdentifierSprite}.
     * @param sprite        The {@link IdentifierSprite} to be bound with.
     */
    public Equator(IdentifierSprite sprite) {
        this.sprite = sprite;
    }

    /**
     * <h1>EQUATOR RENDER LIBRARY</h1>
     * <h2>FOR COLORS</h2>
     * Renders color fields.
     * @author KrLite
     */
    public static class Colors {
        /**
         * Renders a horizontal gradient color field in a {@link MatrixWrapper}.
         * @param wrapper       The {@link MatrixWrapper} which declares the position and the dedicated {@link MatrixStack}.
         * @param colorLeft     The left {@link Color}.
         * @param colorRight    The right {@link Color}.
         */
        public static void gradientHorizontal(MatrixWrapper wrapper, Color colorLeft, Color colorRight) {
            fill(
                    wrapper,
                    colorLeft, colorLeft,
                    colorRight, colorRight
            );
        }

        /**
         * Renders a vertical gradient color field in a {@link MatrixWrapper}.
         * @param wrapper       The {@link MatrixWrapper} which declares the position and the dedicated {@link MatrixStack}.
         * @param colorUpper    The upper {@link Color}.
         * @param colorLower    The lower {@link Color}.
         */
        public static void gradientVertical(MatrixWrapper wrapper, Color colorUpper, Color colorLower) {
            fill(
                    wrapper,
                    colorUpper, colorLower,
                    colorLower, colorUpper
            );
        }

        /**
         * Renders a color field in a {@link MatrixWrapper}.
         * @param wrapper   The {@link MatrixWrapper} which declares the position and the dedicated {@link MatrixStack}.
         * @param color     The dedicated {@link Color}.
         */
        public static void fill(MatrixWrapper wrapper, Color color) {
            fill(wrapper, color, color, color, color);
        }

        /**
         * Renders a color field in a {@link MatrixWrapper}.
         * @param wrapper           The {@link MatrixWrapper} which declares the position and the dedicated {@link MatrixStack}.
         * @param colorLeftUpper    The left upper {@link Color}.
         * @param colorLeftLower    The left lower {@link Color}.
         * @param colorRightLower   The right lower {@link Color}.
         * @param colorRightUpper   The right upper {@link Color}.
         */
        public static void fill(
                MatrixWrapper wrapper,
                Color colorLeftUpper, Color colorLeftLower,
                Color colorRightLower, Color colorRightUpper
        ) {
            fill(
                    wrapper.matrixStack().peek().getPositionMatrix(),
                    wrapper.xLU(), wrapper.yLU(), wrapper.xLD(), wrapper.yLD(),
                    wrapper.xRD(), wrapper.yRD(), wrapper.xRU(), wrapper.yRU(),
                    colorLeftUpper, colorLeftLower,
                    colorRightLower, colorRightUpper
            );
        }

        /**
         * Renders a color field in four specified vertices.
         * @param matrix4f      The dedicated {@link Matrix4f}.
         * @param xLU           The x coordinate of the left upper vertex.
         * @param yLU           The y coordinate of the left upper vertex.
         * @param xLD           The x coordinate of the left lower vertex.
         * @param yLD           The y coordinate of the left lower vertex.
         * @param xRD           The x coordinate of the right lower vertex.
         * @param yRD           The y coordinate of the right lower vertex.
         * @param xRU           The x coordinate of the right upper vertex.
         * @param yRU           The y coordinate of the right upper vertex.
         * @param colorLU       The {@link Color} of the left upper vertex.
         * @param colorLD       The {@link Color} of the left lower vertex.
         * @param colorRD       The {@link Color} of the right lower vertex.
         * @param colorRU       The {@link Color} of the right upper vertex.
         */
        private static void fill(
                Matrix4f matrix4f,
                float xLU, float yLU,
                float xLD, float yLD,
                float xRD, float yRD,
                float xRU, float yRU,
                Color colorLU, Color colorLD,
                Color colorRD, Color colorRU
        ) {
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferBuilder = tessellator.getBuffer();

            RenderSystem.disableTexture();
            RenderSystem.setShader(GameRenderer::getPositionColorShader);

            bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);

            fillVertex(bufferBuilder, matrix4f, xLD, yLD, colorLD);
            fillVertex(bufferBuilder, matrix4f, xRD, yRD, colorRD);
            fillVertex(bufferBuilder, matrix4f, xRU, yRU, colorRU);
            fillVertex(bufferBuilder, matrix4f, xLU, yLU, colorLU);

            tessellator.draw();

            RenderSystem.enableTexture();
        }

        /**
         * Colors a vertex.
         */
        private static void fillVertex(BufferBuilder bufferBuilder, Matrix4f matrix4f, float x, float y, Color color) {
            bufferBuilder
                    .vertex(matrix4f, x, y, 0.0F)
                    .color(
                            color.getRed() / 255.0F,
                            color.getGreen() / 255.0F,
                            color.getBlue() / 255.0F,
                            color.getAlpha() / 255.0F
                    ).next();
        }
    }

    /**
     * Renders the colorized sprite at the center of a specific position.
     * @param matrixStack   The dedicated {@link MatrixStack}.
     * @param color         The dedicated {@link Color}.
     * @param xCentered     The x coordinate of the center.
     * @param yCentered     The y coordinate of the center.
     * @param width         The dedicated width.
     * @param height        The dedicated height.
     */
    public void renderCentered(
            MatrixStack matrixStack, Color color,
            float xCentered,    float yCentered,
            int width,          int height
    ) {
        float xPos = xCentered - width / 2.0F, yPos = yCentered + height / 2.0F;

        render(
                matrixStack, color,
                xPos, yPos, xPos + width, yPos + height
        );
    }

    /**
     * Renders the colorized sprite at a specific position.
     * @param matrixStack   The dedicated {@link MatrixStack}.
     * @param color         The dedicated {@link Color}.
     * @param xPos          The x coordinate of the left upper point.
     * @param yPos          The y coordinate of the left upper point.
     * @param width         The dedicated width.
     * @param height        The dedicated height.
     */
    public void renderPositioned(
            MatrixStack matrixStack, Color color,
            float xPos,         float yPos,
            int width,          int height
    ) {
        render(
                matrixStack, color,
                xPos, yPos, xPos + width, yPos + height
        );
    }

    /**
     * Renders the colorized sprite as a fullscreen overlay.
     * @param matrixStack   The dedicated {@link MatrixStack}.
     * @param color         The dedicated {@link Color}.
     */
    public void renderOverlay(MatrixStack matrixStack, Color color) {
        render(
                matrixStack, color,
                0.0F, 0.0F,
                MinecraftClient.getInstance().getWindow().getScaledWidth(),
                MinecraftClient.getInstance().getWindow().getScaledHeight()
        );
    }

    /**
     * Renders the colorized sprite as a scaled fullscreen overlay which won't be stretched but kept its scaling factor to fullscreen.
     * @param matrixStack   The dedicated {@link MatrixStack}.
     * @param color         The dedicated {@link Color}.
     * @param width         The width constant of the texture.
     * @param height        The height constant of the texture.
     */
    public void renderScaledOverlay(
            MatrixStack matrixStack, Color color,
            int width, int height
    ) {
        renderScaledOverlay(
                matrixStack, color,
                (float) height / (float) width
        );
    }

    /**
     * Renders the colorized sprite as a scaled fullscreen overlay which won't be stretched but kept its scaling factor to fullscreen.
     * @param matrixStack   The dedicated {@link MatrixStack}.
     * @param color         The dedicated {@link Color}.
     * @param scaling       The scaling factor which equals to the <code>height</code> of the texture divides the <code>width</code> of the texture.
     */
    public void renderScaledOverlay(
            MatrixStack matrixStack, Color color,
            float scaling
    ) {
        float scaled = (float) MinecraftClient.getInstance().getWindow().getScaledHeight() / (float) MinecraftClient.getInstance().getWindow().getScaledWidth();

        new Equator(
                sprite.mask(
                        (1 - Math.min(scaling / scaled, 1)) / 2, (1 - Math.min(scaled / scaling, 1)) / 2,
                        (1 + Math.min(scaling / scaled, 1)) / 2, (1 + Math.min(scaled / scaling, 1)) / 2
                )
        ).renderOverlay(matrixStack, color);
    }

    /**
     * Renders the colorized sprite as a fixed fullscreen overlay which will be stretched from center to ensure both a correct scaling and a complete looking.
     * @param matrixStack   The dedicated {@link MatrixStack}.
     * @param color         The dedicated {@link Color}.
     */
    public void renderFixedOverlay(MatrixStack matrixStack, Color color) {
        float
                quarterSize = Math.min(
                        MinecraftClient.getInstance().getWindow().getScaledWidth() / 2.0F,
                        MinecraftClient.getInstance().getWindow().getScaledHeight() / 2.0F
                ),
                width = MinecraftClient.getInstance().getWindow().getScaledWidth(),
                height = MinecraftClient.getInstance().getWindow().getScaledHeight();

        // Left Up Quarter
        new Equator(
                sprite.mask(0.0F, 0.0F, 0.5F, 0.5F)
        ).render(
                matrixStack, color,
                0.0F, 0.0F,
                quarterSize, quarterSize
        );

        // Left Fixer
        new Equator(
                sprite.mask(0.0F, 0.5F, 0.5F, 0.5F)
        ).render(
                matrixStack, color,
                0.0F, quarterSize,
                quarterSize, height - quarterSize

        );

        // Left Down Quarter
        new Equator(
                sprite.mask(0.0F, 0.5F, 0.5F, 1.0F)
        ).render(
                matrixStack, color,
                0.0F, height - quarterSize,
                quarterSize, height
        );

        // Middle Fixer
        new Equator(
                sprite.mask(0.5F, 0.0F, 0.5F, 1.0F)
        ).render(
                matrixStack, color,
                quarterSize, 0.0F,
                width - quarterSize, height
        );

        // Right Down Quarter
        new Equator(
                sprite.mask(0.5F, 0.5F, 1.0F, 1.0F)
        ).render(
                matrixStack, color,
                width - quarterSize, height - quarterSize,
                width, height
        );

        // Right Fixer
        new Equator(
                sprite.mask(0.5F, 0.5F, 1.0F, 0.5F)
        ).render(
                matrixStack, color,
                width - quarterSize, quarterSize,
                width, height - quarterSize
        );

        // Right Up Quarter
        new Equator(
                sprite.mask(0.5F, 0.0F, 1.0F, 0.5F)
        ).render(
                matrixStack, color,
                width - quarterSize, 0.0F,
                width, quarterSize
        );
    }

    /**
     * Renders the colorized sprite in a {@link MatrixStack} coordinate.
     * @param matrixStack   The dedicated {@link MatrixStack}.
     * @param color         The dedicated {@link Color}.
     * @param xBegin        The x coordinate of the beginning of the sprite.
     * @param yBegin        The y coordinate of the beginning of the sprite.
     * @param xEnd          The x coordinate of the end of the sprite.
     * @param yEnd          The y coordinate of the end of the sprite.
     */
    public void render(
            MatrixStack matrixStack,
            Color color,
            float xBegin,   float yBegin,
            float xEnd,     float yEnd
    ) {
        render(
                new MatrixWrapper(matrixStack, xBegin, yBegin, xEnd, yEnd), color
        );
    }

    /**
     * Renders the colorized sprite in a {@link MatrixWrapper}.
     * @param wrapper   The {@link MatrixWrapper} which declares the position and the dedicated {@link MatrixStack}.
     * @param color     The dedicated {@link Color}.
     */
    public void render(MatrixWrapper wrapper, Color color) {
        render(
                sprite.identifier(),
                wrapper.matrixStack().peek().getPositionMatrix(), color,
                wrapper.xLU(),      wrapper.yLU(),
                wrapper.xLD(),      wrapper.yLD(),
                wrapper.xRD(),      wrapper.yRD(),
                wrapper.xRU(),      wrapper.yRU(),
                sprite.uBegin(),    sprite.vBegin(),
                sprite.uEnd(),      sprite.vEnd()
        );
    }

    /**
     * Renders the colorized sprite in four specified vertices.
     * @param identifier    The {@link Identifier} of the target texture.
     * @param matrix4f      The dedicated {@link Matrix4f}.
     * @param color         The dedicated {@link Color Color}.
     * @param xLU           The x coordinate of the left upper vertex.
     * @param yLU           The y coordinate of the left upper vertex.
     * @param xLD           The x coordinate of the left lower vertex.
     * @param yLD           The y coordinate of the left lower vertex.
     * @param xRD           The x coordinate of the right lower vertex.
     * @param yRD           The y coordinate of the right lower vertex.
     * @param xRU           The x coordinate of the right upper vertex.
     * @param yRU           The y coordinate of the right upper vertex.
     * @param uBegin        The u value of the beginning of the texture.
     * @param vBegin        The v value of the beginning of the texture.
     * @param uEnd          The u value of the end of the texture.
     * @param vEnd          The v value of the end of the texture.
     */
    private void render(
            Identifier identifier,
            Matrix4f matrix4f, Color color,
            float xLU,      float yLU,
            float xLD,      float yLD,
            float xRD,      float yRD,
            float xRU,      float yRU,
            float uBegin,   float vBegin,
            float uEnd,     float vEnd
    ) {
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F);
        RenderSystem.setShaderTexture(0, identifier);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();

        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);

        renderVertex(bufferBuilder, matrix4f, xLD, yLD, uBegin, vEnd, color);
        renderVertex(bufferBuilder, matrix4f, xRD, yRD, uEnd, vEnd, color);
        renderVertex(bufferBuilder, matrix4f, xRU, yRU, uEnd, vBegin, color);
        renderVertex(bufferBuilder, matrix4f, xLU, yLU, uBegin, vBegin, color);

        tessellator.draw();

        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

    /**
     * Colors a textured vertex.
     */
    private void renderVertex(BufferBuilder bufferBuilder, Matrix4f matrix4f, float x, float y, float u, float v, Color color) {
        bufferBuilder
                .vertex(matrix4f, x, y, -90.0F)
                .texture(u, v)
                .color(
                        color.getRed(),
                        color.getGreen(),
                        color.getBlue(),
                        color.getAlpha()
                ).next();
    }
}
