package net.krlite.equator.render;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.krlite.equator.base.PreciseColor;
import net.krlite.equator.base.geometry.Rect;
import net.krlite.equator.base.geometry.TintedNode;
import net.krlite.equator.base.geometry.TintedRect;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class Equator {
	public record Drawer(MatrixStack matrixStack) {
		public Drawer tintedRect(TintedRect tintedRect) {
			Tessellator tessellator = prepare();
			BufferBuilder builder = tessellator.getBuffer();
			builder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);

			drawTintedRect(builder, tintedRect.cutTransparentColors());

			cleanup(tessellator);
			return this;
		}

		public Drawer tintedRect(Rect rect, PreciseColor lu, PreciseColor ld, PreciseColor rd, PreciseColor ru) {
			return tintedRect(new TintedRect(rect, lu, ld, rd, ru));
		}

		public Drawer tintedRect(Rect rect, PreciseColor preciseColor) {
			return tintedRect(rect, preciseColor, preciseColor, preciseColor, preciseColor);
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

		// ----- Private methods -----

		private Tessellator prepare() {
			RenderSystem.disableTexture();
			RenderSystem.enableBlend();

			RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO);
			RenderSystem.setShader(GameRenderer::getPositionColorProgram);

			return Tessellator.getInstance();
		}

		private double nonLinearProjection(double value) {
			return 0.5 + Math.sin(MathHelper.clamp(value, 0, 1) * Math.PI - Math.PI / 2) * 0.3;
		}

		private void drawVertex(BufferBuilder builder, TintedNode tintedNode) {
			builder.vertex(matrixStack.peek().getPositionMatrix(), (float) tintedNode.x, (float) tintedNode.y, 0)
					.color(
							tintedNode.nodeColor.redFloat(), tintedNode.nodeColor.greenFloat(),
							tintedNode.nodeColor.blueFloat(), tintedNode.nodeColor.alphaFloat()
					).next();
		}

		private void drawTintedRect(BufferBuilder builder, TintedRect tintedRect) {
			if (!tintedRect.anyNodeHasColor()) return;
			drawVertex(builder, tintedRect.ru);
			drawVertex(builder, tintedRect.lu);
			drawVertex(builder, tintedRect.ld);
			drawVertex(builder, tintedRect.rd);
		}

		private void drawTintedRect(BufferBuilder builder, Rect rect, PreciseColor preciseColor) {
			drawTintedRect(builder, rect, preciseColor, preciseColor, preciseColor, preciseColor);
		}

		private void drawTintedRect(BufferBuilder builder, Rect rect, PreciseColor lu, PreciseColor ld, PreciseColor rd, PreciseColor ru) {
			drawTintedRect(builder, new TintedRect(rect, lu, ld, rd, ru));
		}

		private void cleanup(Tessellator tessellator) {
			tessellator.draw();
			RenderSystem.enableTexture();
		}
	}
}
