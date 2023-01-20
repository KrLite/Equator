package net.krlite.equator.base;

import java.util.Objects;

public abstract class HashCodeComparable {
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		HashCodeComparable that = (HashCodeComparable) o;
		return hashCode() == that.hashCode();
	}

	@Override
	public int hashCode() {
		return Objects.hash((Object) getClass().getDeclaredFields());
	}
}
