package net.krlite.equator.core;

import java.util.Arrays;
import java.util.Objects;

public interface FieldFormattable {
	default String formatFields(boolean fieldNames, String... excluded) {
		return Arrays.stream(getClass().getDeclaredFields())
					   .peek(field -> field.setAccessible(true))
					   .filter(field -> Arrays.stream(excluded).noneMatch(e -> e.equals(field.getName())))
					   .map(field -> {
						   try {
							   return (fieldNames ? field.getName() + "=" : "") + field.get(this);
						   } catch (IllegalAccessException illegalAccessException) {
							   illegalAccessException.printStackTrace();
						   }
						   return null;
					   }).filter(Objects::nonNull)
					   .reduce((f1, f2) -> f1 + ", " + f2).orElse("");
	}

	default String formatFields(String... excluded) {
		return formatFields(true, excluded);
	}

	default String formatFields(boolean fieldNames) {
		return formatFields(fieldNames, "");
	}

	default String formatFields() {
		return formatFields(true);
	}
}
