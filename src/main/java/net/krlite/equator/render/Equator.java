package net.krlite.equator.render;

import com.mojang.blaze3d.systems.RenderSystem;
import net.krlite.equator.color.PreciseColor;
import net.krlite.equator.color.PreciseColors;
import net.krlite.equator.core.HashCodeComparable;
import net.krlite.equator.geometry.Rect;
import net.krlite.equator.geometry.TintedNode;
import net.krlite.equator.geometry.TintedRect;
import net.krlite.equator.render.sprite.IdentifierSprite;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * <h2>Equator</h2>
 * A class that provides a set of methods to draw colors, shapes and sprites on the screen.
 */
public class Equator extends HashCodeComparable {
	/**
	 * <h2>Renderer</h2>
	 * <h3>Renders the Sprites</h3>
	 * @param matrixStack		The matrix stack to render the sprite on.
	 * @param identifierSprite	The sprite to render.
	 */
	protected record Renderer(@NotNull MatrixStack matrixStack, @NotNull IdentifierSprite identifierSprite) {
		public Renderer swap(@NotNull MatrixStack matrixStack) {
			return new Renderer(matrixStack, identifierSprite);
		}

		public Renderer swap(@NotNull IdentifierSprite identifierSprite) {
			return new Renderer(matrixStack, identifierSprite);
		}

		public Renderer rect(@NotNull Rect rect, @NotNull PreciseColor textureColor) {
			Tessellator tessellator = prepare(textureColor);
			BufferBuilder builder = tessellator.getBuffer();
			builder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);

			renderTexturedRect(builder, rect, textureColor);

			cleanup(tessellator);
			return this;
		}

		public Renderer rect(@NotNull Rect rect) {
			return rect(rect, PreciseColor.WHITE);
		}

		public Renderer rect(double x, double y, double width, double height, @NotNull PreciseColor textureColor) {
			return rect(new Rect(x, y, width, height), textureColor);
		}

		public Renderer rect(double x, double y, double width, double height) {
			return rect(x, y, width, height, PreciseColor.WHITE);
		}

		public Renderer centeredRect(double xCentered, double yCentered, double width, double height, @NotNull PreciseColor textureColor) {
			return rect(xCentered - width / 2, yCentered - height / 2, width, height, textureColor);
		}

		public Renderer centeredRect(double xCentered, double yCentered, double width, double height) {
			return centeredRect(xCentered, yCentered, width, height, PreciseColor.WHITE);
		}

		public Renderer overlay(@NotNull PreciseColor textureColor) {
			return rect(Rect.full(), textureColor);
		}

		public Renderer overlay() {
			return overlay(PreciseColor.WHITE);
		}

		public Renderer fixedOverlay(@NotNull PreciseColor textureColor) {
			int
					width = MinecraftClient.getInstance().getWindow().getScaledWidth(),
					height = MinecraftClient.getInstance().getWindow().getScaledHeight();

			double fixedSize = Math.min(width / 2.0, height / 2.0);

			// Upper
			if (width > height)
				swap(identifierSprite.mask(0.5F, 0, 0.5F, 0.5F))
						.rect(fixedSize, 0, width - fixedSize * 2, fixedSize, textureColor);
			// Left upper
			swap(identifierSprite.mask(0, 0, 0.5F, 0.5F))
					.rect(0, 0, fixedSize, fixedSize, textureColor);

			// Left
			if (height > width)
				swap(identifierSprite.mask(0, 0.5F, 0.5F, 0.5F))
						.rect(0, fixedSize, fixedSize, height - fixedSize * 2, textureColor);
			// Left lower
			swap(identifierSprite.mask(0, 0.5F, 0.5F, 1))
					.rect(0, height - fixedSize, fixedSize, fixedSize, textureColor);

			// Lower
			if (width > height)
				swap(identifierSprite.mask(0.5F, 0.5F, 0.5F, 1))
						.rect(fixedSize, height - fixedSize, width - fixedSize * 2, fixedSize, textureColor);
			// Right lower
			swap(identifierSprite.mask(0.5F, 0.5F, 1, 1))
					.rect(width - fixedSize, height - fixedSize, fixedSize, fixedSize, textureColor);

			// Right
			if (height > width)
				swap(identifierSprite.mask(0.5F, 0.5F, 1, 0.5F))
						.rect(width - fixedSize, fixedSize, fixedSize, height - fixedSize * 2, textureColor);
			// Right upper
			swap(identifierSprite.mask(0.5F, 0, 1, 0.5F))
					.rect(width - fixedSize, 0, fixedSize, fixedSize, textureColor);

			// Center
			if (width != height)
				swap(identifierSprite.mask(0.5F, 0.5F, 0.5F, 0.5F))
						.rect(fixedSize, fixedSize, width - fixedSize * 2, height - fixedSize * 2, textureColor);

			return this;
		}

		public Renderer fixedOverlay() {
			return fixedOverlay(PreciseColor.WHITE);
		}

		public Renderer scaledOverlay(@NotNull PreciseColor textureColor, float aspectRatio) {
        	float screenAspectRatio = (float) MinecraftClient.getInstance().getWindow().getScaledHeight() / (float) MinecraftClient.getInstance().getWindow().getScaledWidth();

			return swap(identifierSprite.mask(
						(1 - Math.min(aspectRatio / screenAspectRatio, 1)) / 2, (1 - Math.min(screenAspectRatio / aspectRatio, 1)) / 2,
                        	(1 + Math.min(aspectRatio / screenAspectRatio, 1)) / 2, (1 + Math.min(screenAspectRatio / aspectRatio, 1)) / 2
					)).overlay(textureColor);
		}

		public Renderer scaledOverlay(@NotNull PreciseColor textureColor) {
			return scaledOverlay(textureColor, 1);
		}

		public Renderer scaledOverlay(float aspectRatio) {
			return scaledOverlay(PreciseColor.WHITE, aspectRatio);
		}

		public Renderer scaledOverlay() {
			return scaledOverlay(PreciseColor.WHITE);
		}

		public Renderer scaledOverlay(@NotNull PreciseColor textureColor, double width, double height) {
			return scaledOverlay(textureColor, (float) (height / width));
		}

		public Renderer scaledOverlay(double width, double height) {
			return scaledOverlay(PreciseColor.WHITE, width, height);
		}

		public Renderer clampedOverlay(@NotNull PreciseColor textureColor, float aspectRatio) {
			float screenAspectRatio = (float) MinecraftClient.getInstance().getWindow().getScaledHeight() / (float) MinecraftClient.getInstance().getWindow().getScaledWidth();

			return rect(Rect.ofScaled(
					Math.max(0, (1 - Math.min(screenAspectRatio / aspectRatio, 1)) / 2),
					Math.max(0, (1 - Math.min(aspectRatio / screenAspectRatio, 1)) / 2),
					Math.min(1, (1 + Math.min(screenAspectRatio / aspectRatio, 1)) / 2),
					Math.min(1, (1 + Math.min(aspectRatio / screenAspectRatio, 1)) / 2)
			), textureColor);
		}

		public Renderer clampedOverlay(@NotNull PreciseColor textureColor) {
			return clampedOverlay(textureColor, 1);
		}

		public Renderer clampedOverlay(float aspectRatio) {
			return clampedOverlay(PreciseColor.WHITE, aspectRatio);
		}

		public Renderer clampedOverlay() {
			return clampedOverlay(PreciseColor.WHITE);
		}

		public Renderer clampedOverlay(@NotNull PreciseColor textureColor, double width, double height) {
			return clampedOverlay(textureColor, (float) (height / width));
		}

		public Renderer clampedOverlay(double width, double height) {
			return clampedOverlay(PreciseColor.WHITE, width, height);
		}

		public Renderer tiledOverlay(@NotNull PreciseColor textureColor, float aspectRatio) {
			float screenAspectRatio = (float) MinecraftClient.getInstance().getWindow().getScaledHeight() / (float) MinecraftClient.getInstance().getWindow().getScaledWidth();

			return swap(identifierSprite.mask(
					(1 - Math.max(aspectRatio / screenAspectRatio, 1)) / 2, (1 - Math.max(screenAspectRatio / aspectRatio, 1)) / 2,
					(1 + Math.max(aspectRatio / screenAspectRatio, 1)) / 2, (1 + Math.max(screenAspectRatio / aspectRatio, 1)) / 2
			)).overlay(textureColor);
		}

		public Renderer tiledOverlay(@NotNull PreciseColor textureColor) {
			return tiledOverlay(textureColor, 1);
		}

		public Renderer tiledOverlay(float aspectRatio) {
			return tiledOverlay(PreciseColor.WHITE, aspectRatio);
		}

		public Renderer tiledOverlay() {
			return tiledOverlay(PreciseColor.WHITE);
		}

		public Renderer tiledOverlay(@NotNull PreciseColor textureColor, double width, double height) {
			return tiledOverlay(textureColor, (float) (height / width));
		}

		public Renderer tiledOverlay(double width, double height) {
			return tiledOverlay(PreciseColor.WHITE, width, height);
		}

		public Renderer tiledBackground(@NotNull PreciseColor textureColor, float aspectRatio, float contraction, float uOffset, float vOffset) {
			int
					width = MinecraftClient.getInstance().getWindow().getScaledWidth(),
					height = MinecraftClient.getInstance().getWindow().getScaledHeight();

			float scale = aspectRatio / ((float) height / (float) width), ratio = -0.5F * contraction, u = ratio, v = ratio;

			if(scale > 1) u *= scale;
			else v /= scale;

			return swap(identifierSprite.mask(
					0.5F + u + uOffset, 0.5F + v + vOffset, 0.5F - u + uOffset, 0.5F - v + vOffset
			)).overlay(textureColor);
		}

		public Renderer tiledBackground(@NotNull PreciseColor textureColor, float aspectRatio, float contraction) {
			return tiledBackground(textureColor, aspectRatio, contraction, 0, 0);
		}

		public Renderer tiledBackground(@NotNull PreciseColor textureColor, float aspectRatio) {
			return tiledBackground(textureColor, aspectRatio, 7);
		}

		public Renderer tiledBackground(@NotNull PreciseColor textureColor) {
			return tiledBackground(textureColor, 1);
		}

		public Renderer tiledBackground() {
			return tiledBackground(PreciseColor.WHITE);
		}

		// === Utilities ===
		private Tessellator prepare() {
			return prepare(PreciseColor.WHITE);
		}

		private Tessellator prepare(@NotNull PreciseColor shaderColor) {
			RenderSystem.disableDepthTest();
			RenderSystem.depthMask(false);

			RenderSystem.enableTexture();
			RenderSystem.enableBlend();

			RenderSystem.defaultBlendFunc();
			RenderSystem.setShader(GameRenderer::getPositionTexColorProgram);
			RenderSystem.setShaderColor(shaderColor.redFloat(), shaderColor.greenFloat(), shaderColor.blueFloat(), shaderColor.alphaFloat());
			RenderSystem.setShaderTexture(0, identifierSprite.identifier());

			return Tessellator.getInstance();
		}

		private void cleanup(@NotNull Tessellator tessellator) {
			tessellator.draw();
        	RenderSystem.depthMask(true);
			RenderSystem.enableDepthTest();

			RenderSystem.setShaderColor(1, 1, 1, 1);
		}

		private void renderVertex(@NotNull BufferBuilder builder, @NotNull TintedNode vertex, float u, float v) {
			builder.vertex(matrixStack.peek().getPositionMatrix(), (float) vertex.x, (float) vertex.y, 0)
					.texture(u, v)
					.color(
							vertex.nodeColor.redFloat(), vertex.nodeColor.greenFloat(),
							vertex.nodeColor.blueFloat(), vertex.nodeColor.alphaFloat()
					).next();
		}

		private void renderTexturedRect(@NotNull BufferBuilder builder, @NotNull Rect rect, @NotNull PreciseColor textureColor) {
			renderVertex(builder, rect.upperRight.bind(textureColor), identifierSprite.uEnd(), identifierSprite.vBegin());
			renderVertex(builder, rect.upperLeft.bind(textureColor), identifierSprite.uBegin(), identifierSprite.vBegin());
			renderVertex(builder, rect.lowerLeft.bind(textureColor), identifierSprite.uBegin(), identifierSprite.vEnd());
			renderVertex(builder, rect.lowerRight.bind(textureColor), identifierSprite.uEnd(), identifierSprite.vEnd());
		}

		private void renderFixedTexturedRect(
				@NotNull BufferBuilder builder, @NotNull Rect rect, @NotNull PreciseColor textureColor,
				float uBegin, float vBegin, float uEnd, float vEnd
		) {
			renderVertex(builder, rect.upperRight.bind(textureColor), uEnd, vBegin);
			renderVertex(builder, rect.upperLeft.bind(textureColor), uBegin, vBegin);
			renderVertex(builder, rect.lowerLeft.bind(textureColor), uBegin, vEnd);
			renderVertex(builder, rect.lowerRight.bind(textureColor), uEnd, vEnd);
		}
	}

	/**
	 * <h2>Drawer</h2>
	 * <h3>Draws the Colors and Shapes</h3>
	 * @param matrixStack	The MatrixStack to draw on.
	 */
	protected record Drawer(@NotNull MatrixStack matrixStack) {
		public Drawer swap(@NotNull MatrixStack matrixStack) {
			return new Drawer(matrixStack);
		}

		@Contract("_ -> this")
		public Drawer tintedRect(@NotNull TintedRect tintedRect) {
			Tessellator tessellator = prepare();
			BufferBuilder builder = tessellator.getBuffer();
			builder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);

			drawTintedRect(builder, tintedRect.cut());

			cleanup(tessellator);
			return this;
		}

		public Drawer tintedRect(@NotNull Rect rect, @NotNull PreciseColor lu, @NotNull PreciseColor ld, @NotNull PreciseColor rd, @NotNull PreciseColor ru) {
			return tintedRect(new TintedRect(rect, lu, ld, rd, ru));
		}

		public Drawer tintedRect(@NotNull Rect rect, @NotNull PreciseColor fillColor) {
			return tintedRect(rect, fillColor, fillColor, fillColor, fillColor);
		}

		public Drawer tintedRect(@NotNull PreciseColor preciseColor) {
			return tintedRect(Rect.full(), preciseColor);
		}

		public Drawer point(@NotNull TintedNode tintedNode, double size) {
			new TintedRect(new Rect(tintedNode.x - size / 2, tintedNode.y - size / 2, size, size), tintedNode.nodeColor)
					.draw(matrixStack);
			return this;
		}

		public Drawer missingTexture(@NotNull Rect rect) {
			tintedRect(rect.grid(2, 2, 1, 1), PreciseColors.MINECRAFT_MISSING_TEXTURE_PURPLE);
			tintedRect(rect.grid(2, 2, 1, 2), PreciseColors.MINECRAFT_MISSING_TEXTURE_BLACK);
			tintedRect(rect.grid(2, 2, 2, 1), PreciseColors.MINECRAFT_MISSING_TEXTURE_BLACK);
			tintedRect(rect.grid(2, 2, 2, 2), PreciseColors.MINECRAFT_MISSING_TEXTURE_PURPLE);
			return this;
		}

		public Drawer point(@NotNull TintedNode tintedNode) {
			return point(tintedNode, 1);
		}

		public Drawer line(@NotNull TintedNode start, @NotNull TintedNode end, double boldness) {
			double direction = start.clockwiseDegree(end);
			new TintedRect(
					start.rotate(start.shift(0, -boldness / 2), direction),
					start.rotate(start.shift(0, boldness / 2), direction),
					end.rotate(end.shift(0, boldness / 2), direction),
					end.rotate(end.shift(0, -boldness / 2), direction)
			).draw(matrixStack);
			return this;
		}

		public Drawer line(@NotNull TintedNode start, @NotNull TintedNode end) {
			return line(start, end, 1);
		}

		public Drawer verticalGradiant(@NotNull Rect rect, @NotNull PreciseColor upper, @NotNull PreciseColor lower) {
			return tintedRect(rect, upper, lower, lower, upper);
		}

		public Drawer horizontalGradiant(@NotNull Rect rect, @NotNull PreciseColor left, @NotNull PreciseColor right) {
			return tintedRect(rect, left, left, right, right);
		}

		public Drawer verticalGradiant(@NotNull PreciseColor upper, @NotNull PreciseColor lower) {
			return verticalGradiant(Rect.full(), upper, lower);
		}

		public Drawer horizontalGradiant(@NotNull PreciseColor left, @NotNull PreciseColor right) {
			return horizontalGradiant(Rect.full(), left, right);
		}

		@Contract("_, _ -> this")
		public Drawer verticalGradiant(@NotNull TintedRect tintedRect, double upperToLowerAttenuation) {
			upperToLowerAttenuation = nonLinearProjection(upperToLowerAttenuation);
			tintedRect = tintedRect.cut();

			if (tintedRect.upperLeft.distance(tintedRect.lowerLeft) <= 15 && tintedRect.upperRight.distance(tintedRect.lowerRight) <= 15) {
				tintedRect(tintedRect);
				return this;
			}

			verticalGradiant(tintedRect.squeezeFromBottom(0.5).swap(
					tintedRect.upperLeft.nodeColor, tintedRect.lowerLeft.nodeColor.blend(tintedRect.upperLeft.nodeColor, upperToLowerAttenuation),
					tintedRect.lowerRight.nodeColor.blend(tintedRect.upperRight.nodeColor, upperToLowerAttenuation), tintedRect.upperRight.nodeColor
			), upperToLowerAttenuation);
			verticalGradiant(tintedRect.squeezeFromTop(0.5).swap(
					tintedRect.upperLeft.nodeColor.blend(tintedRect.lowerLeft.nodeColor, 1 - upperToLowerAttenuation), tintedRect.lowerLeft.nodeColor,
					tintedRect.lowerRight.nodeColor, tintedRect.upperRight.nodeColor.blend(tintedRect.lowerRight.nodeColor, 1 - upperToLowerAttenuation)
			), upperToLowerAttenuation);
			return this;
		}

		@Contract("_, _ -> this")
		public Drawer horizontalGradiant(@NotNull TintedRect tintedRect, double leftToRightAttenuation) {
			leftToRightAttenuation = nonLinearProjection(leftToRightAttenuation);
			tintedRect = tintedRect.cut();

			if (tintedRect.upperLeft.distance(tintedRect.upperRight) <= 15 && tintedRect.lowerLeft.distance(tintedRect.lowerRight) <= 15) {
				tintedRect(tintedRect);
				return this;
			}

			horizontalGradiant(tintedRect.squeezeFromRight(0.5).swap(
					tintedRect.upperLeft.nodeColor, tintedRect.lowerLeft.nodeColor,
					tintedRect.lowerRight.nodeColor.blend(tintedRect.lowerLeft.nodeColor, leftToRightAttenuation),
					tintedRect.upperRight.nodeColor.blend(tintedRect.upperLeft.nodeColor, leftToRightAttenuation)
			), leftToRightAttenuation);
			horizontalGradiant(tintedRect.squeezeFromLeft(0.5).swap(
					tintedRect.upperLeft.nodeColor.blend(tintedRect.upperRight.nodeColor, 1 - leftToRightAttenuation),
					tintedRect.lowerLeft.nodeColor.blend(tintedRect.lowerRight.nodeColor, 1 - leftToRightAttenuation),
					tintedRect.lowerRight.nodeColor, tintedRect.upperRight.nodeColor
			), leftToRightAttenuation);
			return this;
		}

		public Drawer rectShadowWithScissor(@NotNull TintedRect tintedRect, @NotNull TintedRect scissor) {
			tintedRect = tintedRect.cut();
			scissor = scissor.cut();
			// Upper
			tintedRect(new TintedRect(tintedRect.upperLeft, scissor.upperLeft, scissor.upperRight, tintedRect.upperRight));
			// Lower
			tintedRect(new TintedRect(scissor.lowerLeft, tintedRect.lowerLeft, tintedRect.lowerRight, scissor.lowerRight));
			// Left
			tintedRect(new TintedRect(tintedRect.upperLeft, tintedRect.lowerLeft, scissor.lowerLeft, scissor.upperLeft));
			// Right
			tintedRect(new TintedRect(scissor.upperRight, scissor.lowerRight, tintedRect.lowerRight, tintedRect.upperRight));
			return this;
		}

		public Drawer rectShadowWithScissor(@NotNull TintedRect scissor) {
			return rectShadowWithScissor(new TintedRect(), scissor);
		}

		public Drawer rectShadowWithScissor(@NotNull TintedRect tintedRect, @NotNull TintedRect scissor, double attenuation) {
			tintedRect = tintedRect.cut();
			scissor = scissor.cut();
			// Upper
			verticalGradiant(new TintedRect(tintedRect.upperLeft, scissor.upperLeft, scissor.upperRight, tintedRect.upperRight), 1 - attenuation);
			// Lower
			verticalGradiant(new TintedRect(scissor.lowerLeft, tintedRect.lowerLeft, tintedRect.lowerRight, scissor.lowerRight), attenuation);
			// Left
			horizontalGradiant(new TintedRect(tintedRect.upperLeft, tintedRect.lowerLeft, scissor.lowerLeft, scissor.upperLeft), 1 - attenuation);
			// Right
			horizontalGradiant(new TintedRect(scissor.upperRight, scissor.lowerRight, tintedRect.lowerRight, tintedRect.upperRight), attenuation);
			return this;
		}

		public Drawer rectShadowWithScissor(@NotNull TintedRect scissor, double attenuation) {
			return rectShadowWithScissor(new TintedRect(), scissor, attenuation);
		}

		public Drawer rectShadow(@NotNull TintedRect outer, @NotNull TintedRect inner, double attenuation) {
			rectShadowWithScissor(outer, inner, attenuation);
			return tintedRect(inner);
		}

		public Drawer rectShadow(@NotNull TintedRect outer, @NotNull TintedRect inner) {
			rectShadowWithScissor(outer, inner);
			return tintedRect(inner);
		}

		public Drawer rectShadow(@NotNull TintedRect inner, double attenuation) {
			return rectShadow(new TintedRect(), inner, attenuation);
		}

		public Drawer rectShadow(@NotNull TintedRect inner) {
			return rectShadow(new TintedRect(), inner);
		}

		// === Utilities ===
		private Tessellator prepare() {
			RenderSystem.disableTexture();
			RenderSystem.enableBlend();

			RenderSystem.defaultBlendFunc();
			RenderSystem.setShader(GameRenderer::getPositionColorProgram);

			return Tessellator.getInstance();
		}

		private void cleanup(@NotNull Tessellator tessellator) {
			tessellator.draw();
			RenderSystem.enableTexture();
		}

		private double nonLinearProjection(double value) {
			return 0.5 + Math.sin(MathHelper.clamp(value, 0, 1) * Math.PI - Math.PI / 2) * 0.3;
		}

		private void drawVertex(@NotNull BufferBuilder builder, @NotNull TintedNode vertex) {
			builder.vertex(matrixStack.peek().getPositionMatrix(), (float) vertex.x, (float) vertex.y, 0)
					.color(
							vertex.nodeColor.redFloat(), vertex.nodeColor.greenFloat(),
							vertex.nodeColor.blueFloat(), vertex.nodeColor.alphaFloat()
					).next();
		}

		private void drawTintedRect(@NotNull BufferBuilder builder, @NotNull TintedRect tintedRect) {
			if (!tintedRect.anyNodeHasColor()) return;
			drawVertex(builder, tintedRect.upperRight);
			drawVertex(builder, tintedRect.upperLeft);
			drawVertex(builder, tintedRect.lowerLeft);
			drawVertex(builder, tintedRect.lowerRight);
		}

		private void drawTintedRect(@NotNull BufferBuilder builder, @NotNull Rect rect, @NotNull PreciseColor fillColor) {
			drawTintedRect(builder, rect, fillColor, fillColor, fillColor, fillColor);
		}

		private void drawTintedRect(@NotNull BufferBuilder builder, @NotNull Rect rect, @NotNull PreciseColor lu, @NotNull PreciseColor ld, @NotNull PreciseColor rd, @NotNull PreciseColor ru) {
			drawTintedRect(builder, new TintedRect(rect, lu, ld, rd, ru));
		}
	}
}
