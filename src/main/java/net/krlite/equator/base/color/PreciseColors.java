package net.krlite.equator.base.color;

@SuppressWarnings("unused")
public class PreciseColors {
	// === Translucent basic ===
	public static final PreciseColor TRANSLUCENT_WHITE = new PreciseColor(1, 1, 1, 0.5);
	public static final PreciseColor TRANSLUCENT_BLACK = new PreciseColor(0, 0, 0, 0.5);
	public static final PreciseColor TRANSLUCENT_RED = new PreciseColor(1, 0, 0, 0.5);
	public static final PreciseColor TRANSLUCENT_GREEN = new PreciseColor(0, 1, 0, 0.5);
	public static final PreciseColor TRANSLUCENT_BLUE = new PreciseColor(0, 0, 1, 0.5);
	public static final PreciseColor TRANSLUCENT_YELLOW = new PreciseColor(1, 1, 0, 0.5);
	public static final PreciseColor TRANSLUCENT_MAGENTA = new PreciseColor(1, 0, 1, 0.5);
	public static final PreciseColor TRANSLUCENT_CYAN = new PreciseColor(0, 1, 1, 0.5);

	// === Grayscale ===
	public static final PreciseColor NEAR_WHITE = new PreciseColor(0.95);
	public static final PreciseColor LIGHT_GRAY = new PreciseColor(0.75);
	public static final PreciseColor GRAY = new PreciseColor(0.5);
	public static final PreciseColor DARK_GRAY = new PreciseColor(0.25);
	public static final PreciseColor NEAR_BLACK = new PreciseColor(0.05);

	// === Normal ===
	public static final PreciseColor GOLD = new PreciseColor(1, 0.85, 0);
	public static final PreciseColor SILVER = new PreciseColor(0.65, 0.66, 0.67);
	public static final PreciseColor LIME = new PreciseColor(0.75, 1, 0);
	public static final PreciseColor ORANGE = new PreciseColor(1, 0.65, 0);
	public static final PreciseColor BROWN = new PreciseColor(0.65, 0.16, 0.16);
	public static final PreciseColor PURPLE = new PreciseColor(0.5, 0, 0.5);
	public static final PreciseColor PINK = new PreciseColor(1, 0.75, 0.8);

	// === Dark basic ===
	public static final PreciseColor DARK_RED = new PreciseColor(0.5, 0, 0);
	public static final PreciseColor DARK_GREEN = new PreciseColor(0, 0.5, 0);
	public static final PreciseColor DARK_BLUE = new PreciseColor(0, 0, 0.5);
	public static final PreciseColor DARK_YELLOW = new PreciseColor(0.5, 0.5, 0);
	public static final PreciseColor DARK_MAGENTA = new PreciseColor(0.5, 0, 0.5);
	public static final PreciseColor DARK_CYAN = new PreciseColor(0, 0.5, 0.5);

	// === Light basic ===
	public static final PreciseColor LIGHT_RED = new PreciseColor(1, 0.5, 0.5);
	public static final PreciseColor LIGHT_GREEN = new PreciseColor(0.5, 1, 0.5);
	public static final PreciseColor LIGHT_BLUE = new PreciseColor(0.5, 0.5, 1);
	public static final PreciseColor LIGHT_YELLOW = new PreciseColor(1, 1, 0.5);
	public static final PreciseColor LIGHT_MAGENTA = new PreciseColor(1, 0.5, 1);
	public static final PreciseColor LIGHT_CYAN = new PreciseColor(0.5, 1, 1);

	// === Dark normal ===
	public static final PreciseColor DARK_GOLD = new PreciseColor(0.5, 0.425, 0);
	public static final PreciseColor DARK_SILVER = new PreciseColor(0.325, 0.33, 0.335);
	public static final PreciseColor DARK_LIME = new PreciseColor(0.375, 0.5, 0);
	public static final PreciseColor DARK_PURPLE = new PreciseColor(0.5, 0, 0.5);
	public static final PreciseColor DARK_ORANGE = new PreciseColor(1, 0.5, 0);
	public static final PreciseColor DARK_BROWN = new PreciseColor(0.4, 0.25, 0.12);
	public static final PreciseColor DARK_PINK = new PreciseColor(1, 0.41, 0.71);

	// === Light normal ===
	public static final PreciseColor LIGHT_GOLD = new PreciseColor(1, 0.7, 0.2);
	public static final PreciseColor LIGHT_SILVER = new PreciseColor(0.8, 0.8, 0.8);
	public static final PreciseColor LIGHT_LIME = new PreciseColor(0.8, 1, 0.2);
	public static final PreciseColor LIGHT_PURPLE = new PreciseColor(0.93, 0.51, 0.93);
	public static final PreciseColor LIGHT_ORANGE = new PreciseColor(1, 0.78, 0.56);
	public static final PreciseColor LIGHT_BROWN = new PreciseColor(0.7, 0.6, 0.5);
	public static final PreciseColor LIGHT_PINK = new PreciseColor(1, 0.71, 0.76);

	// === Minecraft ===
	// Mojang
	public static final PreciseColor MOJANG_RED = new PreciseColor(1, 0, 0.5);
	// Foreground
	public static final PreciseColor MINECOIN_GOLD = PreciseColor.of(0xDDD605);
	public static final PreciseColor MINECRAFT_BLACK = PreciseColor.of(0x000000);
	public static final PreciseColor MINECRAFT_DARK_BLUE = PreciseColor.of(0x0000AA);
	public static final PreciseColor MINECRAFT_DARK_GREEN = PreciseColor.of(0x00AA00);
	public static final PreciseColor MINECRAFT_DARK_AQUA = PreciseColor.of(0x00AAAA);
	public static final PreciseColor MINECRAFT_DARK_RED = PreciseColor.of(0xAA0000);
	public static final PreciseColor MINECRAFT_DARK_PURPLE = PreciseColor.of(0xAA00AA);
	public static final PreciseColor MINECRAFT_GOLD = PreciseColor.of(0xFFAA00);
	public static final PreciseColor MINECRAFT_GRAY = PreciseColor.of(0xAAAAAA);
	public static final PreciseColor MINECRAFT_DARK_GRAY = PreciseColor.of(0x555555);
	public static final PreciseColor MINECRAFT_BLUE = PreciseColor.of(0x5555FF);
	public static final PreciseColor MINECRAFT_GREEN = PreciseColor.of(0x55FF55);
	public static final PreciseColor MINECRAFT_AQUA = PreciseColor.of(0x55FFFF);
	public static final PreciseColor MINECRAFT_RED = PreciseColor.of(0xFF5555);
	public static final PreciseColor MINECRAFT_LIGHT_PURPLE = PreciseColor.of(0xFF55FF);
	public static final PreciseColor MINECRAFT_YELLOW = PreciseColor.of(0xFFFF55);
	public static final PreciseColor MINECRAFT_WHITE = PreciseColor.of(0xFFFFFF);
	// Background
	public static final PreciseColor MINECOIN_BACKGROUND_GOLD = PreciseColor.of(0x373501);
	public static final PreciseColor MINECRAFT_BACKGROUND_BLACK = PreciseColor.of(0x000000);
	public static final PreciseColor MINECRAFT_BACKGROUND_DARK_BLUE = PreciseColor.of(0x00002A);
	public static final PreciseColor MINECRAFT_BACKGROUND_DARK_GREEN = PreciseColor.of(0x002A00);
	public static final PreciseColor MINECRAFT_BACKGROUND_DARK_AQUA = PreciseColor.of(0x002A2A);
	public static final PreciseColor MINECRAFT_BACKGROUND_DARK_RED = PreciseColor.of(0x2A0000);
	public static final PreciseColor MINECRAFT_BACKGROUND_DARK_PURPLE = PreciseColor.of(0x2A002A);
	public static final PreciseColor MINECRAFT_BACKGROUND_GOLD = PreciseColor.of(0x2A2A00);
	public static final PreciseColor MINECRAFT_BACKGROUND_GOLD_BEDROCK = PreciseColor.of(0x402A00);
	public static final PreciseColor MINECRAFT_BACKGROUND_GRAY = PreciseColor.of(0x2A2A2A);
	public static final PreciseColor MINECRAFT_BACKGROUND_DARK_GRAY = PreciseColor.of(0x151515);
	public static final PreciseColor MINECRAFT_BACKGROUND_BLUE = PreciseColor.of(0x15153F);
	public static final PreciseColor MINECRAFT_BACKGROUND_GREEN = PreciseColor.of(0x153F15);
	public static final PreciseColor MINECRAFT_BACKGROUND_AQUA = PreciseColor.of(0x153F3F);
	public static final PreciseColor MINECRAFT_BACKGROUND_RED = PreciseColor.of(0x3F1515);
	public static final PreciseColor MINECRAFT_BACKGROUND_LIGHT_PURPLE = PreciseColor.of(0x3F153F);
	public static final PreciseColor MINECRAFT_BACKGROUND_YELLOW = PreciseColor.of(0x3F3F15);
	public static final PreciseColor MINECRAFT_BACKGROUND_WHITE = PreciseColor.of(0x3F3F3F);

	// === Fashionable ===
	public static final PreciseColor TURQUOISE = new PreciseColor(0.25, 0.88, 0.82);
	public static final PreciseColor TEAL = new PreciseColor(0, 0.5, 0.5);
	public static final PreciseColor OLIVE = new PreciseColor(0.5, 0.5, 0);
	public static final PreciseColor NAVY = new PreciseColor(0, 0, 0.5);
	public static final PreciseColor ROSE = new PreciseColor(1, 0, 0.5);
	public static final PreciseColor VIOLET = new PreciseColor(0.93, 0.51, 0.93);
	public static final PreciseColor INDIGO = new PreciseColor(0.29, 0, 0.51);
	public static final PreciseColor MAROON = new PreciseColor(0.76, 0.125, 0.27);
	public static final PreciseColor LAVENDER = new PreciseColor(0.9, 0.9, 0.98);
	public static final PreciseColor MINT = new PreciseColor(0.62, 1, 0.62);
	public static final PreciseColor PEACH = new PreciseColor(1, 0.85, 0.73);
	public static final PreciseColor COBALT = new PreciseColor(0, 0.28, 0.67);
	public static final PreciseColor PLUM = new PreciseColor(0.5, 0, 0.5);
	public static final PreciseColor MUSTARD = new PreciseColor(1, 0.87, 0.37);
	public static final PreciseColor CHOCOLATE = new PreciseColor(0.48, 0.25, 0);
	public static final PreciseColor TAN = new PreciseColor(0.82, 0.71, 0.55);
	public static final PreciseColor CERISE = new PreciseColor(0.87, 0.19, 0.39);
	public static final PreciseColor LILAC = new PreciseColor(0.78, 0.63, 0.78);
	public static final PreciseColor CREAM = new PreciseColor(1, 0.99, 0.82);
	public static final PreciseColor LEMON = new PreciseColor(1, 0.97, 0);
	public static final PreciseColor AUBERGINE = new PreciseColor(0.37, 0.25, 0.31);
	public static final PreciseColor MAUVE = new PreciseColor(0.87, 0.63, 0.87);
	public static final PreciseColor FUCHSIA = new PreciseColor(1, 0, 1);
	public static final PreciseColor LIME_GREEN = new PreciseColor(0.2, 0.8, 0.2);
	public static final PreciseColor ROYAL_BLUE = new PreciseColor(0.25, 0.41, 0.88);
	public static final PreciseColor BURGUNDY = new PreciseColor(0.5, 0, 0);
	public static final PreciseColor BEIGE = new PreciseColor(0.96, 0.96, 0.86);
	public static final PreciseColor SALMON = new PreciseColor(1, 0.55, 0.41);
	public static final PreciseColor OLIVE_GREEN = new PreciseColor(0.33, 0.42, 0.18);
	public static final PreciseColor BRIGHT_GREEN = new PreciseColor(0.4, 1, 0.4);
	public static final PreciseColor DARK_TURQUOISE = new PreciseColor(0, 0.81, 0.82);
	public static final PreciseColor PALE_BLUE = new PreciseColor(0.69, 0.88, 0.9);
	public static final PreciseColor KHAKI = new PreciseColor(0.76, 0.69, 0.57);
	public static final PreciseColor VIOLET_RED = new PreciseColor(0.81, 0.13, 0.56);
	public static final PreciseColor AZURE = new PreciseColor(0, 0.5, 1);
	public static final PreciseColor BRIGHT_LIME_GREEN = new PreciseColor(0.8, 1, 0.2);
	public static final PreciseColor PALE_GREEN = new PreciseColor(0.6, 0.98, 0.6);
	public static final PreciseColor CARMINE = new PreciseColor(0.59, 0, 0.09);
	public static final PreciseColor BRIGHT_YELLOW = new PreciseColor(1, 1, 0.2);
	public static final PreciseColor BRIGHT_PINK = new PreciseColor(1, 0, 0.5);
	public static final PreciseColor NORD_GREEN = new PreciseColor(0.31, 0.31, 0.18);
	public static final PreciseColor NEON_CYAN = new PreciseColor(0.13, 1, 0.81);
	public static final PreciseColor NEON_CRIMSON = new PreciseColor(1, 0.13, 0.13);
	public static final PreciseColor NEON_MINT = new PreciseColor(0.13, 1, 0.69);
	public static final PreciseColor NEON_PURPLE = new PreciseColor(0.63, 0.13, 1);
	public static final PreciseColor NEON_GOLD = new PreciseColor(1, 0.81, 0.13);
	public static final PreciseColor GOLDENROD = new PreciseColor(0.85, 0.65, 0.13);
	public static final PreciseColor SLATE_BLUE = new PreciseColor(0.42, 0.35, 0.8);
	public static final PreciseColor SLATE_GRAY = new PreciseColor(0.44, 0.5, 0.56);
	public static final PreciseColor SLATE_GREEN = new PreciseColor(0.31, 0.5, 0.44);
	public static final PreciseColor TIN = new PreciseColor(0.8, 0.8, 0.8);
}
