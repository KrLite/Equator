package net.krlite.equator.util;

import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

public class IdentifierBuilder {
	public static Identifier id(@NotNull String namespace, @NotNull String... paths) {
		return new Identifier(namespace, String.join("/", paths));
	}

	public static Identifier png(@NotNull String namespace, @NotNull String... paths) {
		return new Identifier(namespace, "textures/" + String.join("/", paths) + ".png");
	}

	public static Text localization(@NotNull String prefix, @NotNull String namespace, @NotNull String... paths) {
		return Text.translatable(translationKey(prefix, namespace, paths));
	}

	public static String translationKey(@NotNull String prefix, @NotNull String namespace, @NotNull String... paths) {
		return String.join(".", prefix, namespace, String.join(".", paths));
	}

	public record Specified(@NotNull String namespace) {
		public Identifier id(@NotNull String... paths) {
			return IdentifierBuilder.id(namespace, paths);
		}

		public Identifier png(@NotNull String... paths) {
			return IdentifierBuilder.png(namespace, paths);
		}

		public Text localization(@NotNull String prefix, @NotNull String... paths) {
			return IdentifierBuilder.localization(prefix, namespace, paths);
		}

		public String translationKey(@NotNull String prefix, @NotNull String... paths) {
			return IdentifierBuilder.translationKey(prefix, namespace, paths);
		}

		@Override
		public String toString() {
			return "IdentifierBuilder" + "{namespace='" + namespace + "'}";
		}
	}
}
