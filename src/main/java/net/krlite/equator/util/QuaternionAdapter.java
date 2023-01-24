package net.krlite.equator.util;

import net.krlite.equator.annotation.Active;
import net.krlite.equator.annotation.See;
import org.joml.Quaterniond;
import org.joml.Quaterniondc;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;

@See(Quaternionfc.class)
@See(Quaterniondc.class)
@Active
public class QuaternionAdapter {
	public static Quaterniond fromEulerDeg(double x, double y, double z, double w) {
		return new Quaterniond(Math.toRadians(x), Math.toRadians(y), Math.toRadians(z), w);
	}

	public static Quaterniond fromEulerDeg(double x, double y, double z) {
		return fromEulerDeg(x, y, z, 1);
	}

	public static Quaternionf fromEulerDeg(float x, float y, float z, float w) {
		return new Quaternionf((float) Math.toRadians(x), (float) Math.toRadians(y), (float) Math.toRadians(z), w);
	}

	public static Quaternionf fromEulerDeg(float x, float y, float z) {
		return fromEulerDeg(x, y, z, 1);
	}

	public static Quaterniond toDouble(Quaternionfc q) {
		return new Quaterniond(q.x(), q.y(), q.z(), q.w());
	}

	public static Quaternionf toFloat(Quaterniondc q) {
		return new Quaternionf(q.x(), q.y(), q.z(), q.w());
	}
}
