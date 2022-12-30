package net.krlite.equator.render;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.krlite.equator.base.PreciseColor;
import net.krlite.equator.base.geometry.Rect;
import net.krlite.equator.base.geometry.TintedNode;
import net.krlite.equator.base.geometry.TintedRect;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;

public class Equator {
	public record Drawer(MatrixStack matrixStack) {
		public Drawer tintedRect(TintedRect tintedRect) {
			Tessellator tessellator = prepare();
			BufferBuilder builder = tessellator.getBuffer();
			builder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);

			drawTintedRect(builder, tintedRect.processTransparentColors());

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

		public Drawer verticalGradiant(TintedRect tintedRect, double upperToLowerRatio) {
			upperToLowerRatio = nonLinearProjection(upperToLowerRatio);
			tintedRect = tintedRect.processTransparentColors();

			if (tintedRect.lu.distance(tintedRect.ld) <= 15 && tintedRect.ru.distance(tintedRect.rd) <= 15) {
				tintedRect(tintedRect);
				return this;
			}

			verticalGradiant(tintedRect.squeezeFromBottom(0.5).replace(
					tintedRect.lu.nodeColor, tintedRect.ld.nodeColor.blend(tintedRect.lu.nodeColor, upperToLowerRatio),
					tintedRect.rd.nodeColor.blend(tintedRect.ru.nodeColor, upperToLowerRatio), tintedRect.ru.nodeColor
			), upperToLowerRatio);
			verticalGradiant(tintedRect.squeezeFromTop(0.5).replace(
					tintedRect.lu.nodeColor.blend(tintedRect.ld.nodeColor, 1 - upperToLowerRatio), tintedRect.ld.nodeColor,
					tintedRect.rd.nodeColor, tintedRect.ru.nodeColor.blend(tintedRect.rd.nodeColor, 1 - upperToLowerRatio)
			), upperToLowerRatio);
			return this;
		}

		public Drawer horizontalGradiant(TintedRect tintedRect, double leftToRightRatio) {
			leftToRightRatio = nonLinearProjection(leftToRightRatio);
			tintedRect = tintedRect.processTransparentColors();

			if (tintedRect.lu.distance(tintedRect.ru) <= 15 && tintedRect.ld.distance(tintedRect.rd) <= 15) {
				tintedRect(tintedRect);
				return this;
			}

			horizontalGradiant(tintedRect.squeezeFromRight(0.5).replace(
					tintedRect.lu.nodeColor, tintedRect.ld.nodeColor,
					tintedRect.rd.nodeColor.blend(tintedRect.ld.nodeColor, leftToRightRatio),
					tintedRect.ru.nodeColor.blend(tintedRect.lu.nodeColor, leftToRightRatio)
			), leftToRightRatio);
			horizontalGradiant(tintedRect.squeezeFromLeft(0.5).replace(
					tintedRect.lu.nodeColor.blend(tintedRect.ru.nodeColor, 1 - leftToRightRatio),
					tintedRect.ld.nodeColor.blend(tintedRect.rd.nodeColor, 1 - leftToRightRatio),
					tintedRect.rd.nodeColor, tintedRect.ru.nodeColor
			), leftToRightRatio);
			return this;
		}

		public Drawer rectGradiantFromMiddleWithScissor(TintedRect tintedRect, TintedRect scissor) {
			tintedRect = tintedRect.pr();
			scissor = scissor.pr();
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

		public Drawer rectGradiantFromMiddleWithScissor(TintedRect scissor) {
			return rectGradiantFromMiddleWithScissor(new TintedRect(), scissor);
		}

		public Drawer rectGradiantFromMiddleWithScissor(TintedRect tintedRect, TintedRect scissor, double expandRatio) {
			tintedRect = tintedRect.pr();
			scissor = scissor.pr();
			// Upper
			verticalGradiant(new TintedRect(tintedRect.lu, scissor.lu, scissor.ru, tintedRect.ru), 1 - expandRatio);
			// Lower
			verticalGradiant(new TintedRect(scissor.ld, tintedRect.ld, tintedRect.rd, scissor.rd), expandRatio);
			// Left
			horizontalGradiant(new TintedRect(tintedRect.lu, tintedRect.ld, scissor.ld, scissor.lu), 1 - expandRatio);
			// Right
			horizontalGradiant(new TintedRect(scissor.ru, scissor.rd, tintedRect.rd, tintedRect.ru), expandRatio);
			return this;
		}

		public Drawer rectGradiantFromMiddleWithScissor(TintedRect scissor, double expandRatio) {
			return rectGradiantFromMiddleWithScissor(new TintedRect(), scissor, expandRatio);
		}

		private Tessellator prepare() {
			RenderSystem.disableTexture();
			RenderSystem.enableBlend();

			RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO);
			RenderSystem.setShader(GameRenderer::getPositionColorProgram);

			return Tessellator.getInstance();
		}

		private double nonLinearProjection(double ratio) {
			return 0.5 + Math.sin(ratio * Math.PI - Math.PI / 2) * 0.3;
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
