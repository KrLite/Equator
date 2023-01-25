package net.krlite.equator.util.pair;

import net.krlite.equator.color.PreciseColor;
import net.krlite.equator.color.core.BasicRGBA;

import java.awt.*;

public class ColorPair extends Pair<PreciseColor, PreciseColor> {
	/**
	 * Creates a new pair.
	 *
	 * @param first  The first element.
	 * @param second The second element.
	 */
	public ColorPair(BasicRGBA<?> first, BasicRGBA<?> second) {
		super(PreciseColor.of(first), PreciseColor.of(second));
	}

	public ColorPair(Color first, Color second) {
		super(PreciseColor.of(first), PreciseColor.of(second));
	}
}
