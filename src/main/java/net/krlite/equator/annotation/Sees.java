package net.krlite.equator.annotation;

@Active(willMigrate = true)
public @interface Sees {
	See[] value();
}
