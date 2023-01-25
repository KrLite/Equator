package net.krlite.equator.util;

import net.minecraft.util.math.Quaternion;

/**
 * @see Quaternion
 */
public class QuaternionAdapter {
	public static Quaternion fromEulerDeg(double x, double y, double z, double w) {
		return new Quaternion((float) Math.toRadians(x), (float) Math.toRadians(y), (float) Math.toRadians(z), (float) w);
	}

	public static Quaternion fromEulerDeg(double x, double y, double z) {
		return fromEulerDeg(x, y, z, 1);
	}
}
