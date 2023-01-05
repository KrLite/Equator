package net.krlite.equator.render;

import com.mojang.blaze3d.systems.RenderSystem;
import net.krlite.equator.color.PreciseColor;
import net.krlite.equator.color.PreciseColors;
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
public class Equator {
	/**
	 * <h2>Renderer</h2>
	 * <h3>Renders the Sprites</h3>
	 * @param matrixStack		The matrix stack to render the sprite on.
	 * @param identifierSprite	The sprite to render.
	 */
	public record Renderer(@NotNull MatrixStack matrixStack, @NotNull IdentifierSprite identifierSprite) {
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
			renderVertex(builder, rect.ru.bind(textureColor), identifierSprite.uEnd(), identifierSprite.vBegin());
			renderVertex(builder, rect.lu.bind(textureColor), identifierSprite.uBegin(), identifierSprite.vBegin());
			renderVertex(builder, rect.ld.bind(textureColor), identifierSprite.uBegin(), identifierSprite.vEnd());
			renderVertex(builder, rect.rd.bind(textureColor), identifierSprite.uEnd(), identifierSprite.vEnd());
		}

		private void renderFixedTexturedRect(
				@NotNull BufferBuilder builder, @NotNull Rect rect, @NotNull PreciseColor textureColor,
				float uBegin, float vBegin, float uEnd, float vEnd
		) {
			renderVertex(builder, rect.ru.bind(textureColor), uEnd, vBegin);
			renderVertex(builder, rect.lu.bind(textureColor), uBegin, vBegin);
			renderVertex(builder, rect.ld.bind(textureColor), uBegin, vEnd);
			renderVertex(builder, rect.rd.bind(textureColor), uEnd, vEnd);
		}
	}

	/**
	 * <h2>Drawer</h2>
	 * <h3>Draws the Colors and Shapes</h3>
	 * @param matrixStack	The MatrixStack to draw on.
	 */
	public record Drawer(@NotNull MatrixStack matrixStack) {
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

			if (tintedRect.lu.distance(tintedRect.ld) <= 15 && tintedRect.ru.distance(tintedRect.rd) <= 15) {
				tintedRect(tintedRect);
				return this;
			}

			verticalGradiant(tintedRect.squeezeFromBottom(0.5).swap(
					tintedRect.lu.nodeColor, tintedRect.ld.nodeColor.blend(tintedRect.lu.nodeColor, upperToLowerAttenuation),
					tintedRect.rd.nodeColor.blend(tintedRect.ru.nodeColor, upperToLowerAttenuation), tintedRect.ru.nodeColor
			), upperToLowerAttenuation);
			verticalGradiant(tintedRect.squeezeFromTop(0.5).swap(
					tintedRect.lu.nodeColor.blend(tintedRect.ld.nodeColor, 1 - upperToLowerAttenuation), tintedRect.ld.nodeColor,
					tintedRect.rd.nodeColor, tintedRect.ru.nodeColor.blend(tintedRect.rd.nodeColor, 1 - upperToLowerAttenuation)
			), upperToLowerAttenuation);
			return this;
		}

		@Contract("_, _ -> this")
		public Drawer horizontalGradiant(@NotNull TintedRect tintedRect, double leftToRightAttenuation) {
			leftToRightAttenuation = nonLinearProjection(leftToRightAttenuation);
			tintedRect = tintedRect.cut();

			if (tintedRect.lu.distance(tintedRect.ru) <= 15 && tintedRect.ld.distance(tintedRect.rd) <= 15) {
				tintedRect(tintedRect);
				return this;
			}

			horizontalGradiant(tintedRect.squeezeFromRight(0.5).swap(
					tintedRect.lu.nodeColor, tintedRect.ld.nodeColor,
					tintedRect.rd.nodeColor.blend(tintedRect.ld.nodeColor, leftToRightAttenuation),
					tintedRect.ru.nodeColor.blend(tintedRect.lu.nodeColor, leftToRightAttenuation)
			), leftToRightAttenuation);
			horizontalGradiant(tintedRect.squeezeFromLeft(0.5).swap(
					tintedRect.lu.nodeColor.blend(tintedRect.ru.nodeColor, 1 - leftToRightAttenuation),
					tintedRect.ld.nodeColor.blend(tintedRect.rd.nodeColor, 1 - leftToRightAttenuation),
					tintedRect.rd.nodeColor, tintedRect.ru.nodeColor
			), leftToRightAttenuation);
			return this;
		}

		public Drawer rectShadowWithScissor(@NotNull TintedRect tintedRect, @NotNull TintedRect scissor) {
			tintedRect = tintedRect.cut();
			scissor = scissor.cut();
			// Upper
			tintedRect(new TintedRect(tintedRect.lu, scissor.lu, scissor.ru, tintedRect.ru));
			// Lower
			tintedRect(new TintedRect(scissor.ld, tintedRect.ld, tintedRect.rd, scissor.rd));
			// Left
			tintedRect(new TintedRect(tintedRect.lu, tintedRect.ld, scissor.ld, scissor.lu));
			// Right
			tintedRect(new TintedRect(scissor.ru, scissor.rd, tintedRect.rd, tintedRect.ru));
			return this;
		}

		public Drawer rectShadowWithScissor(@NotNull TintedRect scissor) {
			return rectShadowWithScissor(new TintedRect(), scissor);
		}

		public Drawer rectShadowWithScissor(@NotNull TintedRect tintedRect, @NotNull TintedRect scissor, double attenuation) {
			tintedRect = tintedRect.cut();
			scissor = scissor.cut();
			// Upper
			verticalGradiant(new TintedRect(tintedRect.lu, scissor.lu, scissor.ru, tintedRect.ru), 1 - attenuation);
			// Lower
			verticalGradiant(new TintedRect(scissor.ld, tintedRect.ld, tintedRect.rd, scissor.rd), attenuation);
			// Left
			horizontalGradiant(new TintedRect(tintedRect.lu, tintedRect.ld, scissor.ld, scissor.lu), 1 - attenuation);
			// Right
			horizontalGradiant(new TintedRect(scissor.ru, scissor.rd, tintedRect.rd, tintedRect.ru), attenuation);
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
			drawVertex(builder, tintedRect.ru);
			drawVertex(builder, tintedRect.lu);
			drawVertex(builder, tintedRect.ld);
			drawVertex(builder, tintedRect.rd);
		}

		private void drawTintedRect(@NotNull BufferBuilder builder, @NotNull Rect rect, @NotNull PreciseColor fillColor) {
			drawTintedRect(builder, rect, fillColor, fillColor, fillColor, fillColor);
		}

		private void drawTintedRect(@NotNull BufferBuilder builder, @NotNull Rect rect, @NotNull PreciseColor lu, @NotNull PreciseColor ld, @NotNull PreciseColor rd, @NotNull PreciseColor ru) {
			drawTintedRect(builder, new TintedRect(rect, lu, ld, rd, ru));
		}
	}
}
