package net.krlite.equator.render;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.krlite.equator.annotation.See;
import net.krlite.equator.color.PreciseColor;
import net.krlite.equator.color.PreciseColors;
import net.krlite.equator.color.core.BasicRGBA;
import net.krlite.equator.core.ShortStringable;
import net.krlite.equator.geometry.Node;
import net.krlite.equator.geometry.Rect;
import net.krlite.equator.render.sprite.IdentifierSprite;
import net.krlite.equator.util.QuaternionAdapter;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaterniond;
import org.joml.Quaternion;

/**
 * <h2>Equator</h2>
 * A class that provides a set of methods to draw colors, shapes and sprites on the screen.
 */
public class Equator {
	public record Renderer(@NotNull MatrixStack matrixStack, @NotNull IdentifierSprite identifierSprite)
			implements ShortStringable, Cloneable {
		public Renderer swap(@NotNull MatrixStack matrixStack) {
			return new Renderer(matrixStack, identifierSprite);
		}

		public Renderer swap(@NotNull IdentifierSprite identifierSprite) {
			return new Renderer(matrixStack, identifierSprite);
		}

		public Renderer renderRect(@NotNull Rect.Tinted tinted) {
			Tessellator tessellator = prepare(tinted.getCenterNode());
			BufferBuilder builder = tessellator.getBuffer();
			builder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);

			renderRect(builder, tinted);

			cleanup(tessellator);
			return this;
		}

		public Renderer renderRect(@NotNull Rect rect) {
			return renderRect(rect.tint(PreciseColor.WHITE));
		}

		public Renderer renderRect(double x, double y, double width, double height, @NotNull BasicRGBA<?> tint) {
			return renderRect(new Rect(x, y, width, height).tint(tint));
		}

		public Renderer renderRect(double x, double y, double width, double height) {
			return renderRect(x, y, width, height, PreciseColor.WHITE);
		}

		public Renderer centeredRect(double xCentered, double yCentered, double width, double height, @NotNull BasicRGBA<?> tint) {
			return renderRect(xCentered - width / 2, yCentered - height / 2, width, height, tint);
		}

		public Renderer centeredRect(double xCentered, double yCentered, double width, double height) {
			return centeredRect(xCentered, yCentered, width, height, PreciseColor.WHITE);
		}

		public Renderer overlay(@NotNull BasicRGBA<?> tint) {
			return renderRect(Rect.fullScreen().tint(tint));
		}

		public Renderer overlay() {
			return overlay(PreciseColor.WHITE);
		}

		public Renderer fixedOverlay(@NotNull BasicRGBA<?> tint) {
			int width = MinecraftClient.getInstance().getWindow().getScaledWidth(),
					height = MinecraftClient.getInstance().getWindow().getScaledHeight();

			double fixedSize = Math.min(width / 2.0, height / 2.0);

			// Upper
			if (width > height)
				swap(identifierSprite.mask(0.5F, 0, 0.5F, 0.5F))
						.renderRect(fixedSize, 0, width - fixedSize * 2, fixedSize, tint);
			// Left upper
			swap(identifierSprite.mask(0, 0, 0.5F, 0.5F))
					.renderRect(0, 0, fixedSize, fixedSize, tint);

			// Left
			if (height > width)
				swap(identifierSprite.mask(0, 0.5F, 0.5F, 0.5F))
						.renderRect(0, fixedSize, fixedSize, height - fixedSize * 2, tint);
			// Left lower
			swap(identifierSprite.mask(0, 0.5F, 0.5F, 1))
					.renderRect(0, height - fixedSize, fixedSize, fixedSize, tint);

			// Lower
			if (width > height)
				swap(identifierSprite.mask(0.5F, 0.5F, 0.5F, 1))
						.renderRect(fixedSize, height - fixedSize, width - fixedSize * 2, fixedSize, tint);
			// Right lower
			swap(identifierSprite.mask(0.5F, 0.5F, 1, 1))
					.renderRect(width - fixedSize, height - fixedSize, fixedSize, fixedSize, tint);

			// Right
			if (height > width)
				swap(identifierSprite.mask(0.5F, 0.5F, 1, 0.5F))
						.renderRect(width - fixedSize, fixedSize, fixedSize, height - fixedSize * 2, tint);
			// Right upper
			swap(identifierSprite.mask(0.5F, 0, 1, 0.5F))
					.renderRect(width - fixedSize, 0, fixedSize, fixedSize, tint);

			// Center
			if (width != height)
				swap(identifierSprite.mask(0.5F, 0.5F, 0.5F, 0.5F))
						.renderRect(fixedSize, fixedSize, width - fixedSize * 2, height - fixedSize * 2, tint);

			return this;
		}

		public Renderer fixedOverlay() {
			return fixedOverlay(PreciseColor.WHITE);
		}

		public Renderer scaledOverlay(@NotNull BasicRGBA<?> tint, float aspectRatio) {
        	float screenAspectRatio = (float) MinecraftClient.getInstance().getWindow().getScaledHeight() /
											  (float) MinecraftClient.getInstance().getWindow().getScaledWidth();

			return swap(identifierSprite.mask(
						(1 - Math.min(aspectRatio / screenAspectRatio, 1)) / 2, (1 - Math.min(screenAspectRatio / aspectRatio, 1)) / 2,
                        	(1 + Math.min(aspectRatio / screenAspectRatio, 1)) / 2, (1 + Math.min(screenAspectRatio / aspectRatio, 1)) / 2
					)).overlay(tint);
		}

		public Renderer scaledOverlay(@NotNull BasicRGBA<?> tint) {
			return scaledOverlay(tint, 1);
		}

		public Renderer scaledOverlay(float aspectRatio) {
			return scaledOverlay(PreciseColor.WHITE, aspectRatio);
		}

		public Renderer scaledOverlay() {
			return scaledOverlay(PreciseColor.WHITE);
		}

		public Renderer scaledOverlay(@NotNull BasicRGBA<?> tint, double width, double height) {
			return scaledOverlay(tint, (float) (height / width));
		}

		public Renderer scaledOverlay(double width, double height) {
			return scaledOverlay(PreciseColor.WHITE, width, height);
		}

		public Renderer clampedOverlay(@NotNull BasicRGBA<?> tint, float aspectRatio) {
			float screenAspectRatio = (float) MinecraftClient.getInstance().getWindow().getScaledHeight() /
											  (float) MinecraftClient.getInstance().getWindow().getScaledWidth();

			return renderRect(Rect.scaledScreen(
					Math.min(1, (1 + Math.min(screenAspectRatio / aspectRatio, 1))),
					Math.min(1, (1 + Math.min(aspectRatio / screenAspectRatio, 1)))
			).tint(tint));
		}

		public Renderer clampedOverlay(@NotNull BasicRGBA<?> tint) {
			return clampedOverlay(tint, 1);
		}

		public Renderer clampedOverlay(float aspectRatio) {
			return clampedOverlay(PreciseColor.WHITE, aspectRatio);
		}

		public Renderer clampedOverlay() {
			return clampedOverlay(PreciseColor.WHITE);
		}

		public Renderer clampedOverlay(@NotNull BasicRGBA<?> tint, double width, double height) {
			return clampedOverlay(tint, (float) (height / width));
		}

		public Renderer clampedOverlay(double width, double height) {
			return clampedOverlay(PreciseColor.WHITE, width, height);
		}

		public Renderer tiledOverlay(@NotNull BasicRGBA<?> tint, float aspectRatio) {
			float screenAspectRatio = (float) MinecraftClient.getInstance().getWindow().getScaledHeight() /
											  (float) MinecraftClient.getInstance().getWindow().getScaledWidth();

			return swap(identifierSprite.mask(
					(1 - Math.max(aspectRatio / screenAspectRatio, 1)) / 2, (1 - Math.max(screenAspectRatio / aspectRatio, 1)) / 2,
					(1 + Math.max(aspectRatio / screenAspectRatio, 1)) / 2, (1 + Math.max(screenAspectRatio / aspectRatio, 1)) / 2
			)).overlay(tint);
		}

		public Renderer tiledOverlay(@NotNull BasicRGBA<?> tint) {
			return tiledOverlay(tint, 1);
		}

		public Renderer tiledOverlay(float aspectRatio) {
			return tiledOverlay(PreciseColor.WHITE, aspectRatio);
		}

		public Renderer tiledOverlay() {
			return tiledOverlay(PreciseColor.WHITE);
		}

		public Renderer tiledOverlay(@NotNull BasicRGBA<?> tint, double width, double height) {
			return tiledOverlay(tint, (float) (height / width));
		}

		public Renderer tiledOverlay(double width, double height) {
			return tiledOverlay(PreciseColor.WHITE, width, height);
		}

		public Renderer tiledBackground(@NotNull BasicRGBA<?> tint, float aspectRatio, float contraction, float uOffset, float vOffset) {
			int
					width = MinecraftClient.getInstance().getWindow().getScaledWidth(),
					height = MinecraftClient.getInstance().getWindow().getScaledHeight();

			float scale = aspectRatio / ((float) height / (float) width), ratio = -0.5F * contraction, u = ratio, v = ratio;

			if(scale > 1) u *= scale;
			else v /= scale;

			return swap(identifierSprite.mask(
					0.5F + u + uOffset, 0.5F + v + vOffset, 0.5F - u + uOffset, 0.5F - v + vOffset
			)).overlay(tint);
		}

		public Renderer tiledBackground(@NotNull BasicRGBA<?> tint, float aspectRatio, float contraction) {
			return tiledBackground(tint, aspectRatio, contraction, 0, 0);
		}

		public Renderer tiledBackground(@NotNull BasicRGBA<?> tint, float aspectRatio) {
			return tiledBackground(tint, aspectRatio, 7);
		}

		public Renderer tiledBackground(@NotNull BasicRGBA<?> tint) {
			return tiledBackground(tint, 1);
		}

		public Renderer tiledBackground() {
			return tiledBackground(PreciseColor.WHITE);
		}

		private Tessellator prepare() {
			return prepare(PreciseColor.WHITE);
		}

		private Tessellator prepare(@NotNull BasicRGBA<?> shaderColor) {
			RenderSystem.disableDepthTest();
			RenderSystem.depthMask(false);

			RenderSystem.enableTexture();
			RenderSystem.enableBlend();

			RenderSystem.defaultBlendFunc();
			RenderSystem.setShader(GameRenderer::getPositionTexColorProgram);
			RenderSystem.setShaderColor(shaderColor.getRedFloat(), shaderColor.getGreenFloat(),
					shaderColor.getBlueFloat(), shaderColor.getAlphaFloat());
			RenderSystem.setShaderTexture(0, identifierSprite.identifier());

			return Tessellator.getInstance();
		}

		private void cleanup(@NotNull Tessellator tessellator) {
			tessellator.draw();
        	RenderSystem.depthMask(true);
			RenderSystem.enableDepthTest();

			RenderSystem.setShaderColor(1, 1, 1, 1);
		}

		private void renderVertex(@NotNull BufferBuilder builder, @NotNull Node.Tinted vertex, float u, float v) {
			builder.vertex(matrixStack.peek().getPositionMatrix(), (float) vertex.getX(), (float) vertex.getY(), 0)
					.texture(u, v)
					.color(
							vertex.getRedFloat(), vertex.getGreenFloat(),
							vertex.getBlueFloat(), vertex.getAlphaFloat()
					).next();
		}

		private void renderRect(@NotNull BufferBuilder builder, @NotNull Rect.Tinted tinted) {
			renderVertex(builder, tinted.getRightTopNode(), identifierSprite.uEnd(), identifierSprite.vBegin());
			renderVertex(builder, tinted.getLeftTopNode(), identifierSprite.uBegin(), identifierSprite.vBegin());
			renderVertex(builder, tinted.getLeftBottomNode(), identifierSprite.uBegin(), identifierSprite.vEnd());
			renderVertex(builder, tinted.getRightBottomNode(), identifierSprite.uEnd(), identifierSprite.vEnd());
		}

		private void renderFixedRect(
				@NotNull BufferBuilder builder, @NotNull Rect.Tinted tinted,
				float uBegin, float vBegin, float uEnd, float vEnd
		) {
			renderVertex(builder, tinted.getRightTopNode(), uEnd, vBegin);
			renderVertex(builder, tinted.getLeftTopNode(), uBegin, vBegin);
			renderVertex(builder, tinted.getLeftBottomNode(), uBegin, vEnd);
			renderVertex(builder, tinted.getRightBottomNode(), uEnd, vEnd);
		}

		@Override
		public Renderer clone() {
			try {
				return (Renderer) super.clone();
			} catch (CloneNotSupportedException cloneNotSupportedException) {
				throw new RuntimeException(cloneNotSupportedException);
			}
		}
	}

	public record Painter(@NotNull MatrixStack matrixStack) implements ShortStringable, Cloneable {
		public Painter swap(@NotNull MatrixStack matrixStack) {
			return new Painter(matrixStack);
		}

		@Contract("_ -> this")
		public Painter paintRect(@NotNull Rect.Tinted tinted) {
			Tessellator tessellator = prepare();
			BufferBuilder builder = tessellator.getBuffer();
			builder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);

			paintRect(builder, tinted.cut());

			cleanup(tessellator);
			return this;
		}

		public Painter overlay(@Nullable BasicRGBA<?> tint) {
			return tint == null ? this : paintRect(Rect.fullScreen().tint(tint));
		}

		public Painter paintPoint(@NotNull Node.Tinted tinted, double size) {
			return paintRect(new Rect(tinted.getX() - size / 2, tinted.getY() - size / 2, size, size).tint(tinted));
		}

		public Painter paintPoint(@NotNull Node.Tinted tinted) {
			return paintPoint(tinted, 1);
		}

		public Painter paintLine(@NotNull Node.Tinted start, @NotNull Node.Tinted end, double width) {
			double angle = start.angleTo(end);
			return paintRect(Rect.Tinted.of(
					start.operate(node -> node.rotate(node.shift(0, -width / 2), angle)),
					start.operate(node -> node.rotate(node.shift(0, width / 2), angle)),
					end.operate(node -> node.rotate(node.shift(0, width / 2), angle)),
					end.operate(node -> node.rotate(node.shift(0, -width / 2), angle))
			));
		}

		public Painter paintLine(@NotNull Node.Tinted start, @NotNull Node.Tinted end) {
			return paintLine(start, end, 1);
		}

		public Painter missingTexture(@NotNull Rect rect) {
			return paintRect(rect.meshByGrid(2, 2, 1, 1).tint(PreciseColors.MINECRAFT_MISSING_TEXTURE_PURPLE))
						   .paintRect(rect.meshByGrid(2, 2, 1, 2).tint(PreciseColors.MINECRAFT_MISSING_TEXTURE_BLACK))
						   .paintRect(rect.meshByGrid(2, 2, 2, 1).tint(PreciseColors.MINECRAFT_MISSING_TEXTURE_BLACK))
						   .paintRect(rect.meshByGrid(2, 2, 2, 2).tint(PreciseColors.MINECRAFT_MISSING_TEXTURE_PURPLE));
		}

		public Painter verticalGradiant(@NotNull Rect rect, @NotNull BasicRGBA<?> upper, @NotNull BasicRGBA<?> lower) {
			return paintRect(rect.tint(upper, lower, lower, upper));
		}

		public Painter horizontalGradiant(@NotNull Rect rect, @NotNull BasicRGBA<?> left, @NotNull BasicRGBA<?> right) {
			return paintRect(rect.tint(left, left, right, right));
		}

		public Painter verticalGradiant(@NotNull BasicRGBA<?> upper, @NotNull BasicRGBA<?> lower) {
			return verticalGradiant(Rect.fullScreen(), upper, lower);
		}

		public Painter horizontalGradiant(@NotNull BasicRGBA<?> left, @NotNull BasicRGBA<?> right) {
			return horizontalGradiant(Rect.fullScreen(), left, right);
		}

		public static final double MIN_GRADIANT_AREA = 50;

		@Contract("_, _ -> this")
		public Painter verticalGradiant(@NotNull Rect.Tinted tinted, double upperToLowerAttenuation) {
			//upperToLowerAttenuation = nonLinearProjection(upperToLowerAttenuation);
			if (tinted.getArea() <= MIN_GRADIANT_AREA) return paintRect(tinted);

			return verticalGradiant(tinted.getRect().topHalf().tint(
					tinted.getLeftTop(), tinted.getLeftBottom().blend(tinted.getLeftTop(), upperToLowerAttenuation),
					tinted.getRightBottom().blend(tinted.getRightTop(), upperToLowerAttenuation), tinted.getRightTop()
			), upperToLowerAttenuation)
						   .verticalGradiant(tinted.getRect().bottomHalf().tint(
					tinted.getLeftTop().blend(tinted.getLeftBottom(), 1 - upperToLowerAttenuation), tinted.getLeftBottom(),
					tinted.getRightBottom(), tinted.getRightTop().blend(tinted.getRightBottom(), 1 - upperToLowerAttenuation)
						   ), upperToLowerAttenuation);
		}

		@Contract("_, _ -> this")
		public Painter horizontalGradiant(@NotNull Rect.Tinted tinted, double leftToRightAttenuation) {
			//leftToRightAttenuation = nonLinearProjection(leftToRightAttenuation);
			if (tinted.getArea() <= MIN_GRADIANT_AREA) return paintRect(tinted);

			return horizontalGradiant(tinted.getRect().leftHalf().tint(
					tinted.getLeftTop(), tinted.getLeftBottom(),
					tinted.getRightBottom().blend(tinted.getLeftBottom(), leftToRightAttenuation),
					tinted.getRightTop().blend(tinted.getLeftTop(), leftToRightAttenuation)
			), leftToRightAttenuation)
						   .horizontalGradiant(tinted.getRect().rightHalf().tint(
					tinted.getLeftTop().blend(tinted.getRightTop(), 1 - leftToRightAttenuation),
					tinted.getLeftBottom().blend(tinted.getRightBottom(), 1 - leftToRightAttenuation),
					tinted.getRightBottom(), tinted.getRightTop()
						   ), leftToRightAttenuation);
		}

		public Painter rectShadowWithScissor(@NotNull Rect.Tinted tinted, @NotNull Rect.Tinted scissor) {
			// Upper
			return paintRect(Rect.Tinted.of(tinted.getLeftTopNode(), scissor.getLeftTopNode(), scissor.getRightTopNode(), tinted.getRightTopNode()))
						   // Lower
						   .paintRect(Rect.Tinted.of(scissor.getLeftBottomNode(), tinted.getLeftBottomNode(), tinted.getRightBottomNode(), scissor.getRightBottomNode()))
						   // Left
						   .paintRect(Rect.Tinted.of(tinted.getLeftTopNode(), tinted.getLeftBottomNode(), scissor.getLeftBottomNode(), scissor.getLeftTopNode()))
						   // Right
						   .paintRect(Rect.Tinted.of(scissor.getRightTopNode(), scissor.getRightBottomNode(), tinted.getRightBottomNode(), tinted.getRightTopNode()));
		}

		public Painter rectShadowWithScissor(@NotNull Rect.Tinted tinted, @NotNull Rect.Tinted scissor, double attenuation) {
			// Upper
			return verticalGradiant(Rect.Tinted.of(tinted.getLeftTopNode(), scissor.getLeftTopNode(), scissor.getRightTopNode(), tinted.getRightTopNode()).cut(), 1 - attenuation)
						   // Lower
						   .verticalGradiant(Rect.Tinted.of(scissor.getLeftBottomNode(), tinted.getLeftBottomNode(), tinted.getRightBottomNode(), scissor.getRightBottomNode()).cut(), attenuation)
						   // Left
						   .horizontalGradiant(Rect.Tinted.of(tinted.getLeftTopNode(), tinted.getLeftBottomNode(), scissor.getLeftBottomNode(), scissor.getLeftTopNode()).cut(), 1 - attenuation)
						   // Right
						   .horizontalGradiant(Rect.Tinted.of(scissor.getRightTopNode(), scissor.getRightBottomNode(), tinted.getRightBottomNode(), tinted.getRightTopNode()).cut(), attenuation);
		}

		public Painter rectShadowWithScissor(@NotNull Rect.Tinted scissor, double attenuation) {
			return rectShadowWithScissor(Rect.fullScreen().tint(PreciseColor.TRANSPARENT), scissor, attenuation);
		}

		public Painter rectShadowWithScissor(@NotNull Rect.Tinted scissor) {
			return rectShadowWithScissor(Rect.fullScreen().tint(PreciseColor.TRANSPARENT), scissor);
		}

		public Painter rectShadow(@NotNull Rect.Tinted outer, @NotNull Rect.Tinted inner, double attenuation) {
			return rectShadowWithScissor(outer, inner, attenuation).paintRect(inner);
		}

		public Painter rectShadow(@NotNull Rect.Tinted outer, @NotNull Rect.Tinted inner) {
			return rectShadowWithScissor(outer, inner).paintRect(inner);
		}

		public Painter rectShadow(@NotNull Rect.Tinted inner, double attenuation) {
			return rectShadow(Rect.fullScreen().tint(PreciseColor.TRANSPARENT), inner, attenuation);
		}

		public Painter rectShadow(@NotNull Rect.Tinted inner) {
			return rectShadow(Rect.fullScreen().tint(PreciseColor.TRANSPARENT), inner);
		}

		private Tessellator prepare() {
			RenderSystem.disableTexture();
			RenderSystem.enableBlend();

			RenderSystem.defaultBlendFunc();
			RenderSystem.setShader(GameRenderer::getPositionColorShader);
			
			return Tessellator.getInstance();
		}

		private void cleanup(@NotNull Tessellator tessellator) {
			tessellator.draw();
			RenderSystem.enableTexture();
		}

		private double nonLinearProjection(double value) {
			return 0.5 + Math.sin(MathHelper.clamp(value, 0, 1) * Math.PI - Math.PI / 2) * 0.3;
		}

		private void paintVertex(@NotNull BufferBuilder builder, @NotNull Node.Tinted vertex) {
			builder.vertex(matrixStack.peek().getPositionMatrix(), (float) vertex.getX(), (float) vertex.getY(), 0)
					.color(vertex.getRedFloat(), vertex.getGreenFloat(),
							vertex.getBlueFloat(), vertex.getAlphaFloat()).next();
		}

		private void paintRect(@NotNull BufferBuilder builder, @NotNull Rect.Tinted tinted) {
			if (!tinted.allHasColor()) throw new IllegalArgumentException("All vertices must have a color");
			paintVertex(builder, tinted.getRightTopNode());
			paintVertex(builder, tinted.getLeftTopNode());
			paintVertex(builder, tinted.getLeftBottomNode());
			paintVertex(builder, tinted.getRightBottomNode());
		}

		@Override
		public Painter clone() {
			try {
				return (Painter) super.clone();
			} catch (CloneNotSupportedException cloneNotSupportedException) {
				throw new RuntimeException(cloneNotSupportedException);
			}
		}
	}

	@SuppressWarnings("deprecation")
	private static void prepareModel() {
		MinecraftClient.getInstance().getTextureManager().getTexture(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE).setFilter(false, false);
		RenderSystem.setShaderTexture(0, SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE);

		RenderSystem.enableBlend();
		RenderSystem.blendFunc(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA);
		RenderSystem.setShaderColor(1, 1, 1, 1);
	}

	private static void applyModelView(MatrixStack matrixStack, Quaternion quaternion) {
		matrixStack.scale(1, -1, 1);
		matrixStack.scale((16 * quaternion.getW()), (16 * quaternion.getW()), (16 * quaternion.getW()));
		matrixStack.multiply(quaternion);

		RenderSystem.applyModelViewMatrix();
	}

	@See(ItemRenderer.class)
	public record ItemModel(ItemStack itemStack) implements ShortStringable, Cloneable {
		public ItemModel render(Vec3d pos, boolean leftHanded, @See(QuaternionAdapter.class) Quaternion quaternion) {
			BakedModel bakedModel = MinecraftClient.getInstance().getItemRenderer().getModel(itemStack, null, null, 0);
			prepareModel();
			MatrixStack matrixStack = RenderSystem.getModelViewStack();

			matrixStack.push();
			matrixStack.translate(pos.x, pos.y, 100 + pos.z);
			matrixStack.translate(8 * quaternion.getW(), 8 * quaternion.getW(), 0);
			applyModelView(matrixStack, quaternion);

			MatrixStack itemMatrixStack = new MatrixStack();
			VertexConsumerProvider.Immediate immediate = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();

			if (!bakedModel.isSideLit())
				DiffuseLighting.disableGuiDepthLighting();

			MinecraftClient.getInstance().getItemRenderer().renderItem(itemStack, ModelTransformation.Mode.GUI,
					leftHanded, itemMatrixStack, immediate, 0xF000F0, OverlayTexture.DEFAULT_UV, bakedModel);
			immediate.draw();
			RenderSystem.enableDepthTest();

			if (!bakedModel.isSideLit())
				DiffuseLighting.enableGuiDepthLighting();

			matrixStack.pop();
			RenderSystem.applyModelViewMatrix();

			return this;
		}

		public ItemModel render(Vec3d pos, Quaternion quaternion) {
			return render(pos, false, quaternion);
		}

		public ItemModel render(Vec3d pos, int size) {
			return render(pos, false, new Quaternion(0, 0, 0, size / 16F));
		}

		public ItemModel render(Vec3d pos) {
			return render(pos, false, new Quaternion(0, 0, 0, 1));
		}

		public ItemModel render(double x, double y, Quaternion quaternion) {
			return render(new Vec3d(x, y, 0), false, quaternion);
		}

		public ItemModel render(double x, double y, int size) {
			return render(x, y, new Quaternion(0, 0, 0, size / 16F));
		}

		public ItemModel render(double x, double y) {
			return render(x, y, 16);
		}

		public ItemModel render(Node leftTopVertex, boolean leftHanded, Quaternion quaternion) {
			return render(leftTopVertex.toVec3d(), leftHanded, quaternion);
		}

		public ItemModel render(Node leftTopVertex, Quaternion quaternion) {
			return render(leftTopVertex.toVec3d(), quaternion);
		}

		public ItemModel render(Node leftTopVertex, int size) {
			return render(leftTopVertex.getX(), leftTopVertex.getY(), size);
		}

		public ItemModel render(Node leftTopVertex) {
			return render(leftTopVertex.getX(), leftTopVertex.getY());
		}

		public ItemModel renderCentered(Vec3d pos, boolean leftHanded, Quaternion quaternion) {
			return render(pos.add(-8 * quaternion.getW(), -8 * quaternion.getW(), 0), leftHanded, quaternion);
		}

		public ItemModel renderCentered(Vec3d pos, Quaternion quaternion) {
			return renderCentered(pos, false, quaternion);
		}

		public ItemModel renderCentered(Vec3d pos, int size) {
			return renderCentered(pos, new Quaternion(0, 0, 0, size / 16F));
		}

		public ItemModel renderCentered(Vec3d pos) {
			return renderCentered(pos, new Quaternion(0, 0, 0, 1));
		}

		public ItemModel renderCentered(double x, double y, Quaternion quaternion) {
			return renderCentered(new Vec3d(x, y, 0), quaternion);
		}

		public ItemModel renderCentered(double x, double y, int size) {
			return renderCentered(x, y, new Quaternion(0, 0, 0, size / 16F));
		}

		public ItemModel renderCentered(double x, double y) {
			return renderCentered(x, y, 16);
		}

		public ItemModel renderCentered(Node centerVertex, boolean leftHanded, Quaternion quaternion) {
			return renderCentered(centerVertex.toVec3d(), leftHanded, quaternion);
		}

		public ItemModel renderCentered(Node centerVertex, Quaternion quaternion) {
			return renderCentered(centerVertex.toVec3d(), quaternion);
		}

		public ItemModel renderCentered(Node centerVertex, int size) {
			return renderCentered(centerVertex.getX(), centerVertex.getY(), size);
		}

		public ItemModel renderCentered(Node centerVertex) {
			return renderCentered(centerVertex.getX(), centerVertex.getY());
		}

		@Override
		public ItemModel clone() {
			try {
				return (ItemModel) super.clone();
			} catch (CloneNotSupportedException cloneNotSupportedException) {
				throw new RuntimeException(cloneNotSupportedException);
			}
		}
	}

	@See(BlockRenderManager.class)
	public record BlockModel(BlockState blockState) implements ShortStringable, Cloneable {
		public BlockModel render(Vec3d pos, @See(QuaternionAdapter.class) Quaternion quaternion) {
			prepareModel();
			MatrixStack matrixStack = RenderSystem.getModelViewStack();

			matrixStack.push();
			matrixStack.translate(pos.x, pos.y, 100 + pos.z);
			matrixStack.translate(8 * quaternion.getW(), 8 * quaternion.getW(), 8 * quaternion.getW());
			applyModelView(matrixStack, quaternion);

			MatrixStack blockMatrixStack = new MatrixStack();
			blockMatrixStack.translate(-0.5, -0.5, -0.5);
			VertexConsumerProvider.Immediate immediate = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();

			MinecraftClient.getInstance().getBlockRenderManager().renderBlockAsEntity(blockState, blockMatrixStack,
					immediate, 0xF000F0, OverlayTexture.DEFAULT_UV);
			immediate.draw();
			RenderSystem.enableDepthTest();

			matrixStack.pop();
			RenderSystem.applyModelViewMatrix();

			return this;
		}

		public BlockModel render(Vec3d pos, int size) {
			return render(pos, new Quaternion(0, 0, 0, size / 16F));
		}

		public BlockModel render(Vec3d pos) {
			return render(pos, new Quaternion(0, 0, 0, 1));
		}

		public BlockModel render(double x, double y, Quaternion quaternion) {
			return render(new Vec3d(x, y, 0), quaternion);
		}

		public BlockModel render(double x, double y, int size) {
			return render(x, y, new Quaternion(0, 0, 0, size / 16F));
		}

		public BlockModel render(double x, double y) {
			return render(x, y, 16);
		}

		public BlockModel render(Node leftTopVertex, Quaternion quaternion) {
			return render(leftTopVertex.toVec3d(), quaternion);
		}

		public BlockModel render(Node leftTopVertex, int size) {
			return render(leftTopVertex.getX(), leftTopVertex.getY(), size);
		}

		public BlockModel render(Node leftTopVertex) {
			return render(leftTopVertex.getX(), leftTopVertex.getY());
		}

		public BlockModel renderCentered(Vec3d pos, Quaternion quaternion) {
			return render(pos.add(-8 * quaternion.getW(), -8 * quaternion.getW(), 0), quaternion);
		}

		public BlockModel renderCentered(Vec3d pos, int size) {
			return renderCentered(pos, new Quaternion(0, 0, 0, size / 16F));
		}

		public BlockModel renderCentered(Vec3d pos) {
			return renderCentered(pos, new Quaternion(0, 0, 0, 1));
		}

		public BlockModel renderCentered(double x, double y, Quaternion quaternion) {
			return renderCentered(new Vec3d(x, y, 0), quaternion);
		}

		public BlockModel renderCentered(double x, double y, int size) {
			return renderCentered(x, y, new Quaternion(0, 0, 0, size / 16F));
		}

		public BlockModel renderCentered(double x, double y) {
			return renderCentered(x, y, 16);
		}

		public BlockModel renderCentered(Node centerVertex, Quaternion quaternion) {
			return renderCentered(centerVertex.toVec3d(), quaternion);
		}

		public BlockModel renderCentered(Node centerVertex, int size) {
			return renderCentered(centerVertex.getX(), centerVertex.getY(), size);
		}

		public BlockModel renderCentered(Node centerVertex) {
			return renderCentered(centerVertex.getX(), centerVertex.getY());
		}

		@Override
		public BlockModel clone() {
			try {
				return (BlockModel) super.clone();
			} catch (CloneNotSupportedException cloneNotSupportedException) {
				throw new RuntimeException(cloneNotSupportedException);
			}
		}
	}
}
