package net.krlite.equator.util;

import net.krlite.equator.core.ShortStringable;
import net.krlite.equator.render.sprite.IdentifierSprite;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * A utility class for building identifiers and sprites.
 */
public class IdentifierBuilder {
	public static boolean checkContains(@NotNull String namespace, @Nullable Identifier identifier) {
		return identifier != null && identifier.getNamespace().equals(namespace);
	}

	public static boolean checkContains(@NotNull Identifier namespace, @Nullable Identifier identifier) {
		return checkContains(namespace.getNamespace(), identifier);
	}

	private static String joinAll(@Nullable CharSequence delimiter, @Nullable String... strings) {
		return Arrays.stream(strings).filter(Objects::nonNull).filter(s -> !s.isEmpty())
					   .collect(Collectors.joining(delimiter != null ? delimiter : ""));
	}

	public static String joinAsPath(@Nullable String... paths) {
		return joinAll("/", paths);
	}

	public static String joinAsId(@Nullable String... paths) {
		return joinAll("_", paths);
	}

	public static Identifier id(@NotNull String namespace, @Nullable String... paths) {
		return new Identifier(namespace, joinAsPath(paths));
	}

	public static Identifier png(@NotNull String namespace, @Nullable String... paths) {
		return new Identifier(namespace, "textures/" + joinAsPath(paths) + ".png");
	}

	public static IdentifierSprite sprite(@NotNull String namespace, @Nullable String... paths) {
		return IdentifierSprite.of(png(namespace, paths));
	}

	public static Text localization(@NotNull String prefix, @NotNull String namespace, @Nullable String... paths) {
		return Text.translatable(translationKey(prefix, namespace, paths));
	}

	public static String translationKey(@NotNull String prefix, @NotNull String namespace, @Nullable String... paths) {
		return String.join(".", prefix, namespace,
				Arrays.stream(paths)
						.filter(Objects::nonNull)
						.filter(p -> !p.isEmpty())
						.collect(Collectors.joining(".")));
	}

	/**
	 * A utility class for building identifiers and sprites, with a default namespace.
	 * @param namespace	The default namespace.
	 */
	public record Specified(@NotNull String namespace) implements ShortStringable {
		public boolean checkContains(@Nullable Identifier identifier) {
			return IdentifierBuilder.checkContains(namespace, identifier);
		}

		public Identifier id(@Nullable String... paths) {
			return IdentifierBuilder.id(namespace, paths);
		}

		public Identifier png(@Nullable String... paths) {
			return IdentifierBuilder.png(namespace, paths);
		}

		public IdentifierSprite sprite(@Nullable String... paths) {
			return IdentifierSprite.of(png(paths));
		}

		public Text localization(@NotNull String prefix, @NotNull String... paths) {
			return IdentifierBuilder.localization(prefix, namespace, paths);
		}

		public String translationKey(@NotNull String prefix, @NotNull String... paths) {
			return IdentifierBuilder.translationKey(prefix, namespace, paths);
		}

		@Override
		public String toString() {
			return getClass().getSimpleName() + "{" + formatFields() + "}";
		}
	}
}
