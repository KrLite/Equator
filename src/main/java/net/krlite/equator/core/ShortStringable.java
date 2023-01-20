package net.krlite.equator.core;

public interface ShortStringable extends FieldFormattable {
	default String toShortString() {
		return "{" + formatFields(false) + "}";
	}
}
