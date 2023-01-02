package net.krlite.equator;

import net.fabricmc.api.ModInitializer;
import net.krlite.equator.render.sprite.IdentifierSprite;
import net.krlite.equator.util.IdentifierBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EquatorLib implements ModInitializer {
	public static final String MOD_ID = "equator";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	// === Debug ===
	public static final IdentifierSprite FLASH = IdentifierBuilder.sprite(MOD_ID, "flash");
	public static final IdentifierSprite GRAYSCALE = IdentifierBuilder.sprite(MOD_ID, "grayscale");

	@Override
	public void onInitialize() {}
}
