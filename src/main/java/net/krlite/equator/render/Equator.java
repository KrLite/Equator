package net.krlite.equator.render;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.krlite.equator.EquatorLib;
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
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.render.*;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaterniond;
import org.joml.Quaterniondc;

/**
 * <h2>Equator</h2>
 * A class that provides a set of methods to draw colors, shapes and sprites on the screen.
 */
public class Equator {
	public record Renderer(@NotNull MatrixStack matrixStack, @NotNull IdentifierSprite identifierSprite)
			implements ShortStringable, Cloneable {
		@Contract("_ -> new")
		public @NotNull Renderer swap(@NotNull MatrixStack matrixStack) {
			return new Renderer(matrixStack, identifierSprite);
		}

		@Contract("_ -> new")
		public @NotNull Renderer swap(@NotNull IdentifierSprite identifierSprite) {
			return new Renderer(matrixStack, identifierSprite);
		}

		public Renderer render(@NotNull Rect.Tinted tinted) {
			Tessellator tessellator = prepare(tinted.getCenterNode());
			BufferBuilder builder = tessellator.getBuffer();
			builder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);

			render(builder, tinted);

			cleanup(tessellator);
			return this;
		}

		public Renderer render(@NotNull Rect rect) {
			return render(rect.tint(PreciseColor.WHITE));
		}

		public Renderer render(double x, double y, double width, double height, @NotNull BasicRGBA<?> tint) {
			return render(new Rect(x, y, width, height).tint(tint));
		}

		public Renderer render(Node leftTopVertex, double width, double height, @NotNull BasicRGBA<?> tint) {
			return render(new Rect(leftTopVertex, width, height).tint(tint));
		}

		public Renderer render(Node leftTopVertex, double width, double height) {
			return render(leftTopVertex, width, height, PreciseColor.WHITE);
		}

		public Renderer renderCentered(Node center, double width, double height, @NotNull BasicRGBA<?> tint) {
			return render(center.getX() - width / 2, center.getY() - height / 2, width, height, tint);
		}

		public Renderer renderCentered(Node center, double width, double height) {
			return renderCentered(center, width, height, PreciseColor.WHITE);
		}

		public Renderer renderOverlay(@NotNull BasicRGBA<?> tint) {
			return render(Rect.fullScreen().tint(tint));
		}

		public Renderer renderOverlay() {
			return renderOverlay(PreciseColor.WHITE);
		}

		public Renderer renderFixedOverlay(@NotNull BasicRGBA<?> tint) {
			int width = MinecraftClient.getInstance().getWindow().getScaledWidth(),
					height = MinecraftClient.getInstance().getWindow().getScaledHeight();

			double fixedSize = Math.min(width / 2.0, height / 2.0);

			// Upper
			if (width > height)
				swap(identifierSprite.mask(0.5F, 0, 0.5F, 0.5F))
						.render(fixedSize, 0, width - fixedSize * 2, fixedSize, tint);
			// Left upper
			swap(identifierSprite.mask(0, 0, 0.5F, 0.5F))
					.render(0, 0, fixedSize, fixedSize, tint);

			// Left
			if (height > width)
				swap(identifierSprite.mask(0, 0.5F, 0.5F, 0.5F))
						.render(0, fixedSize, fixedSize, height - fixedSize * 2, tint);
			// Left lower
			swap(identifierSprite.mask(0, 0.5F, 0.5F, 1))
					.render(0, height - fixedSize, fixedSize, fixedSize, tint);

			// Lower
			if (width > height)
				swap(identifierSprite.mask(0.5F, 0.5F, 0.5F, 1))
						.render(fixedSize, height - fixedSize, width - fixedSize * 2, fixedSize, tint);
			// Right lower
			swap(identifierSprite.mask(0.5F, 0.5F, 1, 1))
					.render(width - fixedSize, height - fixedSize, fixedSize, fixedSize, tint);

			// Right
			if (height > width)
				swap(identifierSprite.mask(0.5F, 0.5F, 1, 0.5F))
						.render(width - fixedSize, fixedSize, fixedSize, height - fixedSize * 2, tint);
			// Right upper
			swap(identifierSprite.mask(0.5F, 0, 1, 0.5F))
					.render(width - fixedSize, 0, fixedSize, fixedSize, tint);

			// Center
			if (width != height)
				swap(identifierSprite.mask(0.5F, 0.5F, 0.5F, 0.5F))
						.render(fixedSize, fixedSize, width - fixedSize * 2, height - fixedSize * 2, tint);

			return this;
		}

		public Renderer renderFixedOverlay() {
			return renderFixedOverlay(PreciseColor.WHITE);
		}

		public Renderer renderScaledOverlay(@NotNull BasicRGBA<?> tint, float aspectRatio) {
        	float screenAspectRatio = (float) MinecraftClient.getInstance().getWindow().getScaledHeight() /
											  (float) MinecraftClient.getInstance().getWindow().getScaledWidth();

			return swap(identifierSprite.mask(
						(1 - Math.min(aspectRatio / screenAspectRatio, 1)) / 2, (1 - Math.min(screenAspectRatio / aspectRatio, 1)) / 2,
                        	(1 + Math.min(aspectRatio / screenAspectRatio, 1)) / 2, (1 + Math.min(screenAspectRatio / aspectRatio, 1)) / 2
					)).renderOverlay(tint);
		}

		public Renderer renderScaledOverlay(float aspectRatio) {
			return renderScaledOverlay(PreciseColor.WHITE, aspectRatio);
		}

		public Renderer renderScaledOverlay(@NotNull BasicRGBA<?> tint, double width, double height) {
			return renderScaledOverlay(tint, (float) (height / width));
		}

		public Renderer renderScaledOverlay(double width, double height) {
			return renderScaledOverlay(PreciseColor.WHITE, width, height);
		}

		public Renderer renderClampedOverlay(@NotNull BasicRGBA<?> tint, float aspectRatio) {
			float screenAspectRatio = (float) MinecraftClient.getInstance().getWindow().getScaledHeight() /
											  (float) MinecraftClient.getInstance().getWindow().getScaledWidth();

			return render(Rect.scaledScreen(
					Math.min(1, (1 + Math.min(screenAspectRatio / aspectRatio, 1))),
					Math.min(1, (1 + Math.min(aspectRatio / screenAspectRatio, 1)))
			).tint(tint));
		}

		public Renderer renderClampedOverlay(float aspectRatio) {
			return renderClampedOverlay(PreciseColor.WHITE, aspectRatio);
		}

		public Renderer renderClampedOverlay(@NotNull BasicRGBA<?> tint, double width, double height) {
			return renderClampedOverlay(tint, (float) (height / width));
		}

		public Renderer renderClampedOverlay(double width, double height) {
			return renderClampedOverlay(PreciseColor.WHITE, width, height);
		}

		public Renderer renderTiledOverlay(@NotNull BasicRGBA<?> tint, float aspectRatio) {
			float screenAspectRatio = (float) MinecraftClient.getInstance().getWindow().getScaledHeight() /
											  (float) MinecraftClient.getInstance().getWindow().getScaledWidth();

			return swap(identifierSprite.mask(
					(1 - Math.max(aspectRatio / screenAspectRatio, 1)) / 2, (1 - Math.max(screenAspectRatio / aspectRatio, 1)) / 2,
					(1 + Math.max(aspectRatio / screenAspectRatio, 1)) / 2, (1 + Math.max(screenAspectRatio / aspectRatio, 1)) / 2
			)).renderOverlay(tint);
		}

		public Renderer renderTiledOverlay(float aspectRatio) {
			return renderTiledOverlay(PreciseColor.WHITE, aspectRatio);
		}

		public Renderer renderTiledOverlay(@NotNull BasicRGBA<?> tint, double width, double height) {
			return renderTiledOverlay(tint, (float) (height / width));
		}

		public Renderer renderTiledOverlay(double width, double height) {
			return renderTiledOverlay(PreciseColor.WHITE, width, height);
		}

		public Renderer renderTiledBackground(@NotNull BasicRGBA<?> tint, float aspectRatio, float contraction, float uOffset, float vOffset) {
			int
					width = MinecraftClient.getInstance().getWindow().getScaledWidth(),
					height = MinecraftClient.getInstance().getWindow().getScaledHeight();

			float scale = aspectRatio / ((float) height / (float) width), ratio = -0.5F * contraction, u = ratio, v = ratio;

			if(scale > 1) u *= scale;
			else v /= scale;

			return swap(identifierSprite.mask(
					0.5F + u + uOffset, 0.5F + v + vOffset, 0.5F - u + uOffset, 0.5F - v + vOffset
			)).renderOverlay(tint);
		}

		public Renderer renderTiledBackground(@NotNull BasicRGBA<?> tint, float aspectRatio) {
			return renderTiledBackground(tint, aspectRatio, 7.5F, 0, 0);
		}

		public Renderer renderTiledBackground(float aspectRatio) {
			return renderTiledBackground(PreciseColor.WHITE, aspectRatio);
		}

		public Renderer renderTiledBackground(@NotNull BasicRGBA<?> tint, double width, double height) {
			return renderTiledBackground(tint, (float) (height / width), (float) Math.max(width / MinecraftClient.getInstance().getWindow().getScaledWidth(), height / MinecraftClient.getInstance().getWindow().getScaledHeight()), 0, 0);
		}

		public Renderer renderTiledBackground(double width, double height) {
			return renderTiledBackground(PreciseColor.WHITE, width, height);
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

		private void render(@NotNull BufferBuilder builder, @NotNull Rect.Tinted tinted) {
			renderVertex(builder, tinted.getRightTopNode(), identifierSprite.uEnd(), identifierSprite.vBegin());
			renderVertex(builder, tinted.getLeftTopNode(), identifierSprite.uBegin(), identifierSprite.vBegin());
			renderVertex(builder, tinted.getLeftBottomNode(), identifierSprite.uBegin(), identifierSprite.vEnd());
			renderVertex(builder, tinted.getRightBottomNode(), identifierSprite.uEnd(), identifierSprite.vEnd());
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
		@Contract("_ -> new")
		public @NotNull Painter swap(@NotNull MatrixStack matrixStack) {
			return new Painter(matrixStack);
		}

		@Contract("_ -> this")
		public Painter paint(@NotNull Rect.Tinted tinted) {
			Tessellator tessellator = prepare();
			BufferBuilder builder = tessellator.getBuffer();
			builder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);

			paint(builder, tinted.cut());

			cleanup(tessellator);
			return this;
		}

		public Painter paintSimpleVerticalGradiant(Rect rect, BasicRGBA<?> top, BasicRGBA<?> bottom) {
			return paint(rect.tint(top, bottom, bottom, top));
		}

		public Painter paintSimpleHorizontalGradiant(Rect rect, BasicRGBA<?> left, BasicRGBA<?> right) {
			return paint(rect.tint(left, left, right, right));
		}

		public Painter paintOverlay(@Nullable BasicRGBA<?> tint) {
			return tint == null ? this : paint(Rect.fullScreen().tint(tint));
		}

		public Painter paintPoint(@NotNull Node.Tinted tinted, double size) {
			return paint(new Rect(tinted.getX() - size / 2, tinted.getY() - size / 2, size, size).tint(tinted));
		}

		public Painter paintPoint(@NotNull Node.Tinted tinted) {
			return paintPoint(tinted, 1);
		}

		public Painter paintLine(@NotNull Node.Tinted start, @NotNull Node.Tinted end, double boldness, boolean pigmentMix) {
			double angle = start.angleTo(end);
			if (!pigmentMix) {
				return paint(Rect.Tinted.of(
						start.operate(node -> node.rotate(node.shift(0, -boldness / 2), angle)),
						start.operate(node -> node.rotate(node.shift(0, boldness / 2), angle)),
						end.operate(node -> node.rotate(node.shift(0, boldness / 2), angle)),
						end.operate(node -> node.rotate(node.shift(0, -boldness / 2), angle))
				));
			} else {
				return paintHorizontalGradiant(new Rect(start.getNode(), start.distanceTo(end), boldness).shift(0, -boldness / 2.0)
													   .rotateBy(start.getNode(), angle), start, end, true);
			}
		}

		public Painter paintLine(@NotNull Node.Tinted start, @NotNull Node.Tinted end, boolean pigmentMix) {
			return paintLine(start, end, 1, pigmentMix);
		}

		public Painter paintMissingTexture(@NotNull Rect rect) {
			return paint(rect.meshByGrid(2, 2, 1, 1).tint(PreciseColors.MINECRAFT_MISSING_TEXTURE_PURPLE))
						   .paint(rect.meshByGrid(2, 2, 1, 2).tint(PreciseColors.MINECRAFT_MISSING_TEXTURE_BLACK))
						   .paint(rect.meshByGrid(2, 2, 2, 1).tint(PreciseColors.MINECRAFT_MISSING_TEXTURE_BLACK))
						   .paint(rect.meshByGrid(2, 2, 2, 2).tint(PreciseColors.MINECRAFT_MISSING_TEXTURE_PURPLE));
		}

		public static final double MIN_GRADIANT_AREA = 50;

		public Painter paintVerticalGradiant(@NotNull Rect.Tinted tinted, double upperToLowerAttenuation, boolean pigmentMix) {
			//upperToLowerAttenuation = nonLinearProjection(upperToLowerAttenuation);
			if (tinted.getArea() <= MIN_GRADIANT_AREA) return paint(tinted);

			return paintVerticalGradiant(tinted.getRect().topHalf().tint(
					tinted.getLeftTop(), tinted.getLeftBottom().blendOrMix(tinted.getLeftTop(), upperToLowerAttenuation, pigmentMix),
					tinted.getRightBottom().blendOrMix(tinted.getRightTop(), upperToLowerAttenuation, pigmentMix), tinted.getRightTop()
			), upperToLowerAttenuation, pigmentMix)
						   .paintVerticalGradiant(tinted.getRect().bottomHalf().tint(
								   tinted.getLeftTop().blendOrMix(tinted.getLeftBottom(), 1 - upperToLowerAttenuation, pigmentMix), tinted.getLeftBottom(),
								   tinted.getRightBottom(), tinted.getRightTop().blendOrMix(tinted.getRightBottom(), 1 - upperToLowerAttenuation, pigmentMix)
						   ), upperToLowerAttenuation, pigmentMix);
		}

		public Painter paintHorizontalGradiant(@NotNull Rect.Tinted tinted, double leftToRightAttenuation, boolean pigmentMix) {
			//leftToRightAttenuation = nonLinearProjection(leftToRightAttenuation);
			if (tinted.getArea() <= MIN_GRADIANT_AREA) return paint(tinted);

			return paintHorizontalGradiant(tinted.getRect().leftHalf().tint(
					tinted.getLeftTop(), tinted.getLeftBottom(),
					tinted.getRightBottom().blendOrMix(tinted.getLeftBottom(), leftToRightAttenuation, pigmentMix),
					tinted.getRightTop().blendOrMix(tinted.getLeftTop(), leftToRightAttenuation, pigmentMix)
			), leftToRightAttenuation, pigmentMix)
						   .paintHorizontalGradiant(tinted.getRect().rightHalf().tint(
								   tinted.getLeftTop().blendOrMix(tinted.getRightTop(), 1 - leftToRightAttenuation, pigmentMix),
								   tinted.getLeftBottom().blendOrMix(tinted.getRightBottom(), 1 - leftToRightAttenuation, pigmentMix),
								   tinted.getRightBottom(), tinted.getRightTop()
						   ), leftToRightAttenuation, pigmentMix);
		}

		public Painter paintVerticalGradiant(@NotNull Rect rect, @NotNull BasicRGBA<?> upper, @NotNull BasicRGBA<?> lower, boolean pigmentMix) {
			return paintVerticalGradiant(rect.tint(upper, lower, lower, upper).cut(), 0.5, pigmentMix);
		}

		public Painter paintHorizontalGradiant(@NotNull Rect rect, @NotNull BasicRGBA<?> left, @NotNull BasicRGBA<?> right, boolean pigmentMix) {
			return paintHorizontalGradiant(rect.tint(left, left, right, right).cut(), 0.5, pigmentMix);
		}

		public Painter paintRectShadowWithScissor(@NotNull Rect.Tinted tinted, @NotNull Rect.Tinted scissor, double attenuation, boolean pigmentMix) {
			// Upper
			return paintVerticalGradiant(Rect.Tinted.of(tinted.getLeftTopNode(), scissor.getLeftTopNode(), scissor.getRightTopNode(), tinted.getRightTopNode()).cut(), 1 - attenuation, pigmentMix)
						   // Lower
						   .paintVerticalGradiant(Rect.Tinted.of(scissor.getLeftBottomNode(), tinted.getLeftBottomNode(), tinted.getRightBottomNode(), scissor.getRightBottomNode()).cut(), attenuation, pigmentMix)
						   // Left
						   .paintHorizontalGradiant(Rect.Tinted.of(tinted.getLeftTopNode(), tinted.getLeftBottomNode(), scissor.getLeftBottomNode(), scissor.getLeftTopNode()).cut(), 1 - attenuation, pigmentMix)
						   // Right
						   .paintHorizontalGradiant(Rect.Tinted.of(scissor.getRightTopNode(), scissor.getRightBottomNode(), tinted.getRightBottomNode(), tinted.getRightTopNode()).cut(), attenuation, pigmentMix);
		}

		public Painter paintRectShadow(@NotNull Rect.Tinted outer, @NotNull Rect.Tinted inner, double attenuation, boolean pigmentMix) {
			return paintRectShadowWithScissor(outer, inner, attenuation, pigmentMix).paint(inner);
		}

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

		private void paintVertex(@NotNull BufferBuilder builder, @NotNull Node.Tinted vertex) {
			builder.vertex(matrixStack.peek().getPositionMatrix(), (float) vertex.getX(), (float) vertex.getY(), 0)
					.color(vertex.getRedFloat(), vertex.getGreenFloat(),
							vertex.getBlueFloat(), vertex.getAlphaFloat()).next();
		}

		private void paint(@NotNull BufferBuilder builder, @NotNull Rect.Tinted tinted) {
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

	public record Writer(@NotNull MatrixStack matrixStack) implements ShortStringable, Cloneable {
		public Writer write(@NotNull Text text, @NotNull BasicRGBA<?> tint, @NotNull Vec3d pos, float scale, boolean shadow) {
			matrixStack.push();
			matrixStack.translate(0, 0, pos.z);
			matrixStack.scale(scale, scale, 1);
			if (shadow)
				MinecraftClient.getInstance().textRenderer
						.drawWithShadow(matrixStack, text.asOrderedText(), (float) pos.x, (float) pos.y, tint.toColorInt());
			else MinecraftClient.getInstance().textRenderer.draw(matrixStack, text.asOrderedText(), (float) pos.x, (float) pos.y, tint.toColorInt());
			matrixStack.pop();
			return this;
		}

		public Writer write(Text text, BasicRGBA<?> tint, Vec3d pos, float scale) {
			return write(text, tint, pos, scale, true);
		}

		public Writer write(Text text, BasicRGBA<?> tint, Vec3d pos) {
			return write(text, tint, pos, 1);
		}

		public Writer writeCentered(@NotNull Text text, @NotNull BasicRGBA<?> tint, @NotNull Vec3d pos, float scale, boolean shadow) {
			return write(text, tint, pos.subtract(MinecraftClient.getInstance().textRenderer.getWidth(text) / 2F,
					MinecraftClient.getInstance().textRenderer.fontHeight / 2F, 0), scale, shadow);
		}

		public Writer writeCentered(Text text, BasicRGBA<?> tint, Vec3d pos, float scale) {
			return writeCentered(text, tint, pos, scale, true);
		}

		public Writer writeCentered(Text text, BasicRGBA<?> tint, Vec3d pos) {
			return writeCentered(text, tint, pos, 1);
		}

		@Override
		public Writer clone() {
			try {
				return (Writer) super.clone();
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

	private static void applyModelView(@NotNull MatrixStack matrixStack, @NotNull Quaterniondc quaternion) {
		matrixStack.scale(1, -1, 1);
		matrixStack.scale((float) (16 * quaternion.w()), (float) (16 * quaternion.w()), (float) (16 * quaternion.w()));
		matrixStack.multiply(QuaternionAdapter.toFloat(quaternion));

		RenderSystem.applyModelViewMatrix();
	}

	public record ItemModel(@NotNull ItemStack itemStack) implements ShortStringable, Cloneable {
		@Contract("_, _, _ -> this")
		public ItemModel render(@NotNull Vec3d pos, boolean leftHanded, @NotNull Quaterniondc quaternion) {
			BakedModel bakedModel = MinecraftClient.getInstance().getItemRenderer().getModel(itemStack, null, null, 0);
			prepareModel();
			MatrixStack matrixStack = RenderSystem.getModelViewStack();

			matrixStack.push();
			matrixStack.translate(pos.x, pos.y, 100 + pos.z);
			matrixStack.translate(8 * quaternion.w(), 8 * quaternion.w(), 0);
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

		public ItemModel render(Vec3d pos, Quaterniondc quaternion) {
			return render(pos, false, quaternion);
		}

		public ItemModel render(Vec3d pos, int size) {
			return render(pos, false, new Quaterniond(0, 0, 0, size / 16.0));
		}

		public ItemModel render(Vec3d pos) {
			return render(pos, false, new Quaterniond(0, 0, 0, 1));
		}

		public ItemModel render(@NotNull Node leftTopVertex, boolean leftHanded, Quaterniondc quaternion) {
			return render(leftTopVertex.toVec3d(), leftHanded, quaternion);
		}

		public ItemModel render(@NotNull Node leftTopVertex, Quaterniondc quaternion) {
			return render(leftTopVertex.toVec3d(), quaternion);
		}

		public ItemModel render(@NotNull Node leftTopVertex, int size) {
			return render(leftTopVertex.toVec3d(), size);
		}

		public ItemModel render(@NotNull Node leftTopVertex) {
			return render(leftTopVertex.toVec3d());
		}

		public ItemModel renderCentered(@NotNull Vec3d pos, boolean leftHanded, Quaterniondc quaternion) {
			return render(pos.add(-8 * quaternion.w(), -8 * quaternion.w(), 0), leftHanded, quaternion);
		}

		public ItemModel renderCentered(Vec3d pos, Quaterniondc quaternion) {
			return renderCentered(pos, false, quaternion);
		}

		public ItemModel renderCentered(Vec3d pos, int size) {
			return renderCentered(pos, new Quaterniond(0, 0, 0, size / 16.0));
		}

		public ItemModel renderCentered(Vec3d pos) {
			return renderCentered(pos, new Quaterniond(0, 0, 0, 1));
		}

		public ItemModel renderCentered(@NotNull Node centerVertex, boolean leftHanded, Quaterniondc quaternion) {
			return renderCentered(centerVertex.toVec3d(), leftHanded, quaternion);
		}

		public ItemModel renderCentered(@NotNull Node centerVertex, Quaterniondc quaternion) {
			return renderCentered(centerVertex.toVec3d(), quaternion);
		}

		public ItemModel renderCentered(@NotNull Node centerVertex, int size) {
			return renderCentered(centerVertex.toVec3d(), size);
		}

		public ItemModel renderCentered(@NotNull Node centerVertex) {
			return renderCentered(centerVertex.toVec3d());
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

	public record BlockModel(@NotNull BlockState blockState) implements ShortStringable, Cloneable {
		@Contract("_, _ -> this")
		public BlockModel render(@NotNull Vec3d pos, @NotNull Quaterniondc quaternion) {
			prepareModel();
			MatrixStack matrixStack = RenderSystem.getModelViewStack();

			matrixStack.push();
			matrixStack.translate(pos.x, pos.y, 100 + pos.z);
			matrixStack.translate(8 * quaternion.w(), 8 * quaternion.w(), 8 * quaternion.w());
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
			return render(pos, new Quaterniond(0, 0, 0, size / 16.0));
		}

		public BlockModel render(Vec3d pos) {
			return render(pos, new Quaterniond(0, 0, 0, 1));
		}

		public BlockModel render(@NotNull Node leftTopVertex, Quaterniondc quaternion) {
			return render(leftTopVertex.toVec3d(), quaternion);
		}

		public BlockModel render(@NotNull Node leftTopVertex, int size) {
			return render(leftTopVertex.toVec3d(), size);
		}

		public BlockModel render(@NotNull Node leftTopVertex) {
			return render(leftTopVertex.toVec3d());
		}

		public BlockModel renderCentered(@NotNull Vec3d pos, Quaterniondc quaternion) {
			return render(pos.add(-8 * quaternion.w(), -8 * quaternion.w(), 0), quaternion);
		}

		public BlockModel renderCentered(Vec3d pos, int size) {
			return renderCentered(pos, new Quaterniond(0, 0, 0, size / 16.0));
		}

		public BlockModel renderCentered(Vec3d pos) {
			return renderCentered(pos, new Quaterniond(0, 0, 0, 1));
		}

		public BlockModel renderCentered(@NotNull Node centerVertex, Quaterniondc quaternion) {
			return renderCentered(centerVertex.toVec3d(), quaternion);
		}

		public BlockModel renderCentered(@NotNull Node centerVertex, int size) {
			return renderCentered(centerVertex.toVec3d(), size);
		}

		public BlockModel renderCentered(@NotNull Node centerVertex) {
			return renderCentered(centerVertex.toVec3d());
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
