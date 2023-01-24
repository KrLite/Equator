package net.krlite.equator.core;

import net.krlite.equator.annotation.Active;

@Active
public interface SimpleOperations {
	default int clampValue(int value) {
		return Math.max(0, Math.min(255, value));
	}

	default double clampValue(double value) {
		return Math.max(0, Math.min(1, value));
	}

	default double blendValue(double first, double second, double ratio) {
		return first * (1 - clampValue(ratio)) + second * clampValue(ratio);
	}
}
