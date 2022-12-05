package net.krlite.equator;

import net.fabricmc.api.ModInitializer;
import net.krlite.equator.render.Equator;
import net.krlite.equator.util.IdentifierBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EquatorLib implements ModInitializer {
	public static final String MOD_ID = "equator";
	public static final Logger LOGGER = LoggerFactory.getLogger("Equator Lib");

	@Override
	public void onInitialize() {
		LOGGER.info("Equator Lib initialized. 🪐");
	}
}
