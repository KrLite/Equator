package net.krlite.equator.render;

import com.mojang.blaze3d.systems.RenderSystem;
import net.krlite.equator.base.color.PreciseColor;
import net.krlite.equator.base.geometry.Rect;
import net.krlite.equator.base.geometry.TintedNode;
import net.krlite.equator.base.geometry.TintedRect;
import net.krlite.equator.base.sprite.IdentifierSprite;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class Equator {
	public record Renderer(MatrixStack matrixStack, IdentifierSprite identifierSprite) {
		public Renderer rect(Rect rect, PreciseColor textureColor) {
			Tessellator tessellator = prepare(textureColor);
			BufferBuilder builder = tessellator.getBuffer();
			builder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);

			renderTexturedRect(builder, rect, textureColor);

			cleanup(tessellator);
			return this;
		}

		public Renderer rect(Rect rect) {
			return rect(rect, PreciseColor.WHITE);
		}

		public Renderer rect(double x, double y, double width, double height, PreciseColor textureColor) {
			return rect(new Rect(x, y, width, height), textureColor);
		}

		public Renderer rect(double x, double y, double width, double height) {
			return rect(x, y, width, height, PreciseColor.WHITE);
		}

		public Renderer centeredRect(double xCentered, double yCentered, double width, double height, PreciseColor textureColor) {
			return rect(xCentered - width / 2, yCentered - height / 2, width, height, textureColor);
		}

		public Renderer centeredRect(double xCentered, double yCentered, double width, double height) {
			return centeredRect(xCentered, yCentered, width, height, PreciseColor.WHITE);
		}

		// === Utilities ===
		private Tessellator prepare() {
			return prepare(PreciseColor.WHITE);
		}

		private Tessellator prepare(PreciseColor shaderColor) {
			RenderSystem.disableDepthTest();
			RenderSystem.depthMask(false);

			RenderSystem.enableTexture();
			RenderSystem.enableBlend();

			RenderSystem.setShader(GameRenderer::getPositionTexProgram);
			RenderSystem.setShaderColor(shaderColor.redFloat(), shaderColor.greenFloat(), shaderColor.blueFloat(), shaderColor.alphaFloat());
			RenderSystem.setShaderTexture(0, identifierSprite.identifier());

			return Tessellator.getInstance();
		}

		private void cleanup(Tessellator tessellator) {
			tessellator.draw();
        	RenderSystem.depthMask(true);
			RenderSystem.enableDepthTest();

			RenderSystem.setShaderColor(1, 1, 1, 1);
		}

		private void renderVertex(BufferBuilder builder, TintedNode vertex, float u, float v) {
			builder.vertex(matrixStack.peek().getPositionMatrix(), (float) vertex.x, (float) vertex.y, 0)
					.texture(u, v)
					.color(
							vertex.nodeColor.redFloat(), vertex.nodeColor.greenFloat(),
							vertex.nodeColor.blueFloat(), vertex.nodeColor.alphaFloat()
					).next();
		}

		private void renderTexturedRect(BufferBuilder builder, Rect rect, PreciseColor textureColor) {
			renderVertex(builder, rect.ru.bound(textureColor), identifierSprite.uBegin(), identifierSprite.vEnd());
			renderVertex(builder, rect.lu.bound(textureColor), identifierSprite.uBegin(), identifierSprite.vBegin());
			renderVertex(builder, rect.ld.bound(textureColor), identifierSprite.uEnd(), identifierSprite.vBegin());
			renderVertex(builder, rect.rd.bound(textureColor), identifierSprite.uEnd(), identifierSprite.vEnd());
		}
	}

	public record Drawer(MatrixStack matrixStack) {
		public Drawer tintedRect(TintedRect tintedRect) {
			Tessellator tessellator = prepare();
			BufferBuilder builder = tessellator.getBuffer();
			builder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);

			drawTintedRect(builder, tintedRect.cut());

			cleanup(tessellator);
			return this;
		}

		public Drawer tintedRect(Rect rect, PreciseColor lu, PreciseColor ld, PreciseColor rd, PreciseColor ru) {
			return tintedRect(new TintedRect(rect, lu, ld, rd, ru));
		}

		public Drawer tintedRect(Rect rect, PreciseColor fillColor) {
			return tintedRect(rect, fillColor, fillColor, fillColor, fillColor);
		}

		public Drawer tintedRect(PreciseColor preciseColor) {
			return tintedRect(new Rect(), preciseColor);
		}

		public Drawer verticalGradiant(Rect rect, PreciseColor upper, PreciseColor lower) {
			return tintedRect(rect, upper, lower, lower, upper);
		}

		public Drawer horizontalGradiant(Rect rect, PreciseColor left, PreciseColor right) {
			return tintedRect(rect, left, left, right, right);
		}

		public Drawer verticalGradiant(PreciseColor upper, PreciseColor lower) {
			return verticalGradiant(new Rect(), upper, lower);
		}

		public Drawer horizontalGradiant(PreciseColor left, PreciseColor right) {
			return horizontalGradiant(new Rect(), left, right);
		}

		public Drawer verticalGradiant(TintedRect tintedRect, double upperToLowerAttenuation) {
			upperToLowerAttenuation = nonLinearProjection(upperToLowerAttenuation);
			tintedRect = tintedRect.cut();

			if (tintedRect.lu.distance(tintedRect.ld) <= 15 && tintedRect.ru.distance(tintedRect.rd) <= 15) {
				tintedRect(tintedRect);
				return this;
			}

			verticalGradiant(tintedRect.squeezeFromBottom(0.5).replace(
					tintedRect.lu.nodeColor, tintedRect.ld.nodeColor.blend(tintedRect.lu.nodeColor, upperToLowerAttenuation),
					tintedRect.rd.nodeColor.blend(tintedRect.ru.nodeColor, upperToLowerAttenuation), tintedRect.ru.nodeColor
			), upperToLowerAttenuation);
			verticalGradiant(tintedRect.squeezeFromTop(0.5).replace(
					tintedRect.lu.nodeColor.blend(tintedRect.ld.nodeColor, 1 - upperToLowerAttenuation), tintedRect.ld.nodeColor,
					tintedRect.rd.nodeColor, tintedRect.ru.nodeColor.blend(tintedRect.rd.nodeColor, 1 - upperToLowerAttenuation)
			), upperToLowerAttenuation);
			return this;
		}

		public Drawer horizontalGradiant(TintedRect tintedRect, double leftToRightAttenuation) {
			leftToRightAttenuation = nonLinearProjection(leftToRightAttenuation);
			tintedRect = tintedRect.cut();

			if (tintedRect.lu.distance(tintedRect.ru) <= 15 && tintedRect.ld.distance(tintedRect.rd) <= 15) {
				tintedRect(tintedRect);
				return this;
			}

			horizontalGradiant(tintedRect.squeezeFromRight(0.5).replace(
					tintedRect.lu.nodeColor, tintedRect.ld.nodeColor,
					tintedRect.rd.nodeColor.blend(tintedRect.ld.nodeColor, leftToRightAttenuation),
					tintedRect.ru.nodeColor.blend(tintedRect.lu.nodeColor, leftToRightAttenuation)
			), leftToRightAttenuation);
			horizontalGradiant(tintedRect.squeezeFromLeft(0.5).replace(
					tintedRect.lu.nodeColor.blend(tintedRect.ru.nodeColor, 1 - leftToRightAttenuation),
					tintedRect.ld.nodeColor.blend(tintedRect.rd.nodeColor, 1 - leftToRightAttenuation),
					tintedRect.rd.nodeColor, tintedRect.ru.nodeColor
			), leftToRightAttenuation);
			return this;
		}

		public Drawer rectShadowWithScissor(TintedRect tintedRect, TintedRect scissor) {
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

		public Drawer rectShadowWithScissor(TintedRect scissor) {
			return rectShadowWithScissor(new TintedRect(), scissor);
		}

		public Drawer rectShadowWithScissor(TintedRect tintedRect, TintedRect scissor, double attenuation) {
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

		public Drawer rectShadowWithScissor(TintedRect scissor, double attenuation) {
			return rectShadowWithScissor(new TintedRect(), scissor, attenuation);
		}

		public Drawer rectShadow(TintedRect outer, TintedRect inner) {
			rectShadowWithScissor(outer, inner);
			return tintedRect(inner);
		}

		public Drawer rectShadow(TintedRect outer, TintedRect inner, double attenuation) {
			rectShadowWithScissor(outer, inner, attenuation);
			return tintedRect(inner);
		}

		public Drawer rectShadow(TintedRect inner) {
			return rectShadow(new TintedRect(), inner);
		}

		public Drawer rectShadow(TintedRect inner, double attenuation) {
			return rectShadow(new TintedRect(), inner, attenuation);
		}

		// === Utilities ===
		private Tessellator prepare() {
			RenderSystem.disableTexture();
			RenderSystem.enableBlend();

			RenderSystem.setShader(GameRenderer::getPositionColorProgram);

			return Tessellator.getInstance();
		}

		private void cleanup(Tessellator tessellator) {
			tessellator.draw();
			RenderSystem.enableTexture();
		}

		private double nonLinearProjection(double value) {
			return 0.5 + Math.sin(MathHelper.clamp(value, 0, 1) * Math.PI - Math.PI / 2) * 0.3;
		}

		private void drawVertex(BufferBuilder builder, TintedNode vertex) {
			builder.vertex(matrixStack.peek().getPositionMatrix(), (float) vertex.x, (float) vertex.y, 0)
					.color(
							vertex.nodeColor.redFloat(), vertex.nodeColor.greenFloat(),
							vertex.nodeColor.blueFloat(), vertex.nodeColor.alphaFloat()
					).next();
		}

		private void drawTintedRect(BufferBuilder builder, TintedRect tintedRect) {
			if (!tintedRect.anyNodeHasColor()) return;
			drawVertex(builder, tintedRect.ru);
			drawVertex(builder, tintedRect.lu);
			drawVertex(builder, tintedRect.ld);
			drawVertex(builder, tintedRect.rd);
		}

		private void drawTintedRect(BufferBuilder builder, Rect rect, PreciseColor fillColor) {
			drawTintedRect(builder, rect, fillColor, fillColor, fillColor, fillColor);
		}

		private void drawTintedRect(BufferBuilder builder, Rect rect, PreciseColor lu, PreciseColor ld, PreciseColor rd, PreciseColor ru) {
			drawTintedRect(builder, new TintedRect(rect, lu, ld, rd, ru));
		}
	}
}
