package net.krlite.equator.annotation;


import java.lang.annotation.Repeatable;

@Active(willMigrate = true)
@Repeatable(Sees.class)
public @interface See {
	Class<?> value();
}
