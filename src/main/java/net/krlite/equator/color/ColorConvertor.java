package net.krlite.equator.color;

@Deprecated
public class ColorConvertor {
	public PreciseColor fromHSB(double hue, double saturation, double brightness) {
		if (saturation == 0.0F) {
			return new PreciseColor(brightness);
		} else {
			double hi = (hue - Math.floor(hue)) * 6.0, h = hi - Math.floor(hi);
			double p = brightness * (1 - saturation);
			double q = brightness * (1 - saturation * h);
			double t = brightness * (1 - saturation * (1 - h));
			return switch ((int) hi) {
				case 0 -> new PreciseColor(brightness, t, p);
				case 1 -> new PreciseColor(q, brightness, p);
				case 2 -> new PreciseColor(p, brightness, t);
				case 3 -> new PreciseColor(p, q, brightness);
				case 4 -> new PreciseColor(t, p, brightness);
				case 5 -> new PreciseColor(brightness, p, q);
				default -> throw new IllegalStateException("Unexpected value: " + (int) hi);
			};
		}
	}

	public double getHue(PreciseColor color) {
		double r = color.getRed(), g = color.getGreen(), b = color.getBlue();
		double max = Math.max(r, Math.max(g, b)), min = Math.min(r, Math.min(g, b));
		double hue = 0;
		if (max == min) {
			hue = 0;
		} else if (max == r) {
			hue = (g - b) / (max - min);
		} else if (max == g) {
			hue = 2 + (b - r) / (max - min);
		} else if (max == b) {
			hue = 4 + (r - g) / (max - min);
		}
		hue /= 6;
		if (hue < 0) {
			hue += 1;
		}
		return hue;
	}

	public double getSaturation(PreciseColor color) {
		double r = color.getRed(), g = color.getGreen(), b = color.getBlue();
		double max = Math.max(r, Math.max(g, b)), min = Math.min(r, Math.min(g, b));
		if (max == 0) {
			return 0;
		}
		return (max - min) / max;
	}

	public double getBrightness(PreciseColor color) {
		double r = color.getRed(), g = color.getGreen(), b = color.getBlue();
		return Math.max(r, Math.max(g, b));
	}
}
