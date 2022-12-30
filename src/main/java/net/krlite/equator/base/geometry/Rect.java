package net.krlite.equator.base.geometry;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class Rect {
	public final Node lu, ld, rd, ru;

	public Rect(@NotNull Node lu, @NotNull Node ld, @NotNull Node rd, @NotNull Node ru) {
		this.lu = lu;
		this.ld = ld;
		this.rd = rd;
		this.ru = ru;
	}

	public Rect(@NotNull Node lu, @NotNull Node rd) {
		this(lu, new Node(lu.x, rd.y), rd, new Node(rd.x, lu.y));
	}

	public Rect(double x, double y, double width, double height) {
		this(new Node(x, y), new Node(x + width, y + height));
	}

	public Rect() {
		this(new Node(), new Node(MinecraftClient.getInstance().getWindow().getScaledWidth(), MinecraftClient.getInstance().getWindow().getScaledHeight()));
	}

	public Rect min(@NotNull Rect other) {
		return new Rect(
				new Node(Math.min(lu.x, other.lu.x), Math.min(lu.y, other.lu.y)),
				new Node(Math.max(ld.x, other.ld.x), Math.max(ld.y, other.ld.y)),
				new Node(Math.max(rd.x, other.rd.x), Math.max(rd.y, other.rd.y)),
				new Node(Math.min(ru.x, other.ru.x), Math.min(ru.y, other.ru.y))
		);
	}

	public Rect max(@NotNull Rect other) {
		return new Rect(
				new Node(Math.max(lu.x, other.lu.x), Math.max(lu.y, other.lu.y)),
				new Node(Math.min(ld.x, other.ld.x), Math.min(ld.y, other.ld.y)),
				new Node(Math.min(rd.x, other.rd.x), Math.min(rd.y, other.rd.y)),
				new Node(Math.max(ru.x, other.ru.x), Math.max(ru.y, other.ru.y))
		);
	}

	public boolean contains(@NotNull Node node) {
		return node.getCross(ld, lu) * node.getCross(ru, rd) >= 0 && node.getCross(lu, ru) * node.getCross(rd, ld) >= 0;
	}

	public Node center() {
		return new Node(((lu.x + ld.x) / 2 + (ru.x + rd.x) / 2) / 2, ((lu.y + ld.y) / 2 + (ru.y + rd.y) / 2) / 2);
	}

	public Rect shift(double x, double y) {
		return new Rect(lu.shift(x, y), ld.shift(x, y), rd.shift(x, y), ru.shift(x, y));
	}

	public Rect shiftBy(@NotNull Node node) {
		return shift(node.x, node.y);
	}

	public Rect shiftToCenter(@NotNull Node node) {
		return shift(node.x - center().x, node.y - center().y);
	}

	public Rect scale(@NotNull Node origin, double scale) {
		return scale(origin, scale, scale);
	}

	public Rect scale(@NotNull Node origin, double x, double y) {
		return new Rect(origin.scale(lu, x, y), origin.scale(ld, x, y), origin.scale(rd, x, y), origin.scale(ru, x, y));
	}

	public Rect scale(double scale) {
		return scale(center(), scale);
	}

	public Rect scale(double x, double y) {
		return scale(center(), x, y);
	}

	public Rect expand(double x, double y) {
		return new Rect(lu.x - x, lu.y - y, rd.x - lu.x + x * 2, rd.y - lu.y + y * 2);
	}

	public Rect expand(double expand) {
		return expand(expand, expand);
	}

	public Rect interpolate(@NotNull Rect other, double ratio) {
		return new Rect(lu.interpolate(other.lu, ratio), ld.interpolate(other.ld, ratio), rd.interpolate(other.rd, ratio), ru.interpolate(other.ru, ratio));
	}

	public Rect interpolate(@NotNull Rect other) {
		return interpolate(other, 0.5);
	}

	public Rect rotate(@NotNull Node origin, double clockwiseDegree) {
		return new Rect(lu.rotate(origin, clockwiseDegree), ld.rotate(origin, clockwiseDegree), rd.rotate(origin, clockwiseDegree), ru.rotate(origin, clockwiseDegree));
	}

	public Rect rotate(double clockwiseDegree) {
		return rotate(center(), clockwiseDegree);
	}

	public Rect stretchLu(@NotNull Node lu, double ratio) {
		return interpolate(new Rect(lu, ld.interpolate(lu.interpolate(rd)), rd, ru.interpolate(lu.interpolate(ld))), MathHelper.clamp(ratio, 0, 1) * 0.5);
	}

	public Rect stretchLd(@NotNull Node ld, double ratio) {
		return interpolate(new Rect(lu.interpolate(ld.interpolate(rd)), ld, rd.interpolate(ld.interpolate(lu)), ru), MathHelper.clamp(ratio, 0, 1) * 0.5);
	}

	public Rect stretchRd(@NotNull Node rd, double ratio) {
		return interpolate(new Rect(lu, ld.interpolate(rd.interpolate(ru)), rd, ru.interpolate(rd.interpolate(ld))), MathHelper.clamp(ratio, 0, 1) * 0.5);
	}

	public Rect stretchRu(@NotNull Node ru, double ratio) {
		return interpolate(new Rect(lu.interpolate(ru.interpolate(rd)), ld, rd.interpolate(ru.interpolate(lu)), ru), MathHelper.clamp(ratio, 0, 1) * 0.5);
	}

	public Rect stretchLu(@NotNull Node lu) {
		return stretchLu(lu, 1);
	}

	public Rect stretchLd(@NotNull Node ld) {
		return stretchLd(ld, 1);
	}

	public Rect stretchRd(@NotNull Node rd) {
		return stretchRd(rd, 1);
	}

	public Rect stretchRu(@NotNull Node ru) {
		return stretchRu(ru, 1);
	}

	public Rect stretchFromTop(double y, double ratio) {
		return interpolate(new Rect(new Node(lu.x, y), ld, rd, new Node(ru.x, y)), ratio);
	}

	public Rect stretchFromBottom(double y, double ratio) {
		return interpolate(new Rect(lu, new Node(ld.x, y), new Node(rd.x, y), ru), ratio);
	}

	public Rect stretchFromLeft(double x, double ratio) {
		return interpolate(new Rect(new Node(x, lu.y), new Node(x, ld.y), rd, ru), ratio);
	}

	public Rect stretchFromRight(double x, double ratio) {
		return interpolate(new Rect(lu, ld, new Node(x, rd.y), new Node(x, ru.y)), ratio);
	}

	public Rect stretchFromTop(double y) {
		return stretchFromTop(y, 1);
	}

	public Rect stretchFromBottom(double y) {
		return stretchFromBottom(y, 1);
	}

	public Rect stretchFromLeft(double x) {
		return stretchFromLeft(x, 1);
	}

	public Rect stretchFromRight(double x) {
		return stretchFromRight(x, 1);
	}

	public Rect squeezeFromTop(double ratio) {
		return new Rect(lu.interpolate(ld, ratio), ld, rd, ru.interpolate(rd, ratio));
	}

	public Rect squeezeFromBottom(double ratio) {
		return new Rect(lu, ld.interpolate(lu, ratio), rd.interpolate(ru, ratio), ru);
	}

	public Rect squeezeFromLeft(double ratio) {
		return new Rect(lu.interpolate(ru, ratio), ld.interpolate(rd, ratio), rd, ru);
	}

	public Rect squeezeFromRight(double ratio) {
		return new Rect(lu, ld, rd.interpolate(ld, ratio), ru.interpolate(lu, ratio));
	}

	public Rect flipHorizontal(double ratio) {
		return interpolate(new Rect(ru, rd, ld, lu), ratio);
	}

	public Rect flipVertical(double ratio) {
		return interpolate(new Rect(ld, lu, ru, rd), ratio);
	}

	public Rect flipDiagonalLuRd(double ratio) {
		return interpolate(new Rect(rd, ld, lu, ru), ratio);
	}

	public Rect flipDiagonalRuLd(double ratio) {
		return interpolate(new Rect(lu, ru, rd, ld), ratio);
	}

	public Rect flipHorizontal() {
		return flipHorizontal(1);
	}

	public Rect flipVertical() {
		return flipVertical(1);
	}

	public Rect flipDiagonalLuRd() {
		return flipDiagonalLuRd(1);
	}

	public Rect flipDiagonalRuLd() {
		return flipDiagonalRuLd(1);
	}

	public boolean equals(@Nullable Rect other) {
		if (other == null) return false;
		return hashCode() == other.hashCode();
	}

	@Override
	public boolean equals(@Nullable Object other) {
		if (other == null) return false;
		if (other instanceof Rect) return equals((Rect) other);
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(lu, ld, rd, ru);
	}

	@Override
	public String toString() {
		return getClass().getName() + "[" + "lu=" + lu.toString() + ", ld=" + ld.toString() + ", rd=" + rd.toString() + ", ru=" + ru.toString() + "]";
	}

	public String toString(boolean matrix) {
		if (!matrix) return toString();
		return getClass().getName() + ":\n" +
					   "[" + lu.toString() + ", " + ru.toString() + "],\n" +
					   "[" + ld.toString() + ", " + rd.toString() + "]";
	}

	public String toShortString() {
		return "Rect[" + lu.toShortString() + ", " + ld.toShortString() + ", " + rd.toShortString() + ", " + ru.toShortString() + "]";
	}
}
