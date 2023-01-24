package net.krlite.equator.annotation;


import java.lang.annotation.Repeatable;

@Repeatable(Sees.class)
public @interface See {
	Class<?> value();
}
