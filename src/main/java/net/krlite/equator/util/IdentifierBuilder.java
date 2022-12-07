package net.krlite.equator.util;

import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A utility class for building identifiers and paths.
 */
public class IdentifierBuilder {
	/**
	 * The internal namespace of the {@link IdentifierBuilder}.
	 */
	private final String namespace;

	/**
	 * The additional path.
	 */
	private final List<String> path;

	/**
	 * Creates an {@link IdentifierBuilder} with the specified namespace.
	 * @param namespace	The namespace of the {@link IdentifierBuilder}.
	 */
	public IdentifierBuilder(String namespace) {
		this.namespace = namespace;
		this.path = new ArrayList<>();
	}

	/**
	 * Creates an {@link IdentifierBuilder} with the specified namespace and paths.
	 * @param namespace	The namespace of the {@link IdentifierBuilder}.
	 * @param paths		The additional paths of the {@link IdentifierBuilder}.
	 */
	public IdentifierBuilder(String namespace, String... paths) {
		this.namespace = namespace;
		this.path = List.of(paths);
	}

	/**
	 * Appends paths to an existing {@link IdentifierBuilder}.
	 * @param identifierBuilder	The {@link IdentifierBuilder} to append the paths to.
	 * @param paths				The paths to append.
	 */
	public IdentifierBuilder(IdentifierBuilder identifierBuilder, String... paths) {
		this.namespace = identifierBuilder.namespace;
		this.path = identifierBuilder.path;
		this.path.addAll(List.of(paths));
	}

	/**
	 * Gets an {@link Identifier} for a texture with the specified paths.
	 * @param pathsAndName	The paths and name of the texture (the last parameter will be the name).
	 * @return				The {@link Identifier} of the texture.
	 */
	public Identifier texture(String... pathsAndName) {
		return new Identifier(
				namespace,
				"textures"
						+ path.stream().map(p -> "/" + p).collect(Collectors.joining()).toLowerCase()
						+ Arrays.stream(pathsAndName).map(p -> "/" + p).collect(Collectors.joining()).toLowerCase()
						+ ".png"
		);
	}

	/**
	 * Gets a {@link Text TranslatableText} with the specified category and keys.
	 * @param category	The category of the {@link Text TranslatableText}.
	 * @param keys		The keys of the {@link Text TranslatableText}.
	 * @return			The {@link Text TranslatableText}.
	 */
	public TranslatableText translatableText(String category, String... keys) {
 		return new TranslatableText(translationKey(category, keys));
	}

	/**
	 * Gets a translation key with the specified category and keys.
	 * @param category	The category of the translation key.
	 * @param keys		The keys of the translation key.
	 * @return			The translation key.
	 */
	public String translationKey(String category, String... keys) {
		return category + "." + namespace
					   + path.stream().map(p -> "." + p).collect(Collectors.joining())
					   + Arrays.stream(keys).map(k -> "." + k).collect(Collectors.joining());
	}
}
