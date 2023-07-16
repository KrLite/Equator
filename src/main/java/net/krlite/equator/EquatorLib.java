package net.krlite.equator;

import ladysnake.satin.api.event.ShaderEffectRenderCallback;
import ladysnake.satin.api.managed.ManagedShaderEffect;
import ladysnake.satin.api.managed.ShaderEffectManager;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EquatorLib implements ModInitializer {
	public static final String MOD_ID = "equator";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final boolean DEBUG = true;
	public static ShaderProgram positionPigmentProgram;

	@Override
	public void onInitialize() {
	}
}
