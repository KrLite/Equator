package net.krlite.equator.mixin;

import net.krlite.equator.debug.CanvasScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MinecraftClient.class)
public class EquatorDebugger {
	@Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;setScreen(Lnet/minecraft/client/gui/screen/Screen;)V", ordinal = 1))
private void onSetScreen(MinecraftClient client, Screen screen) {
		client.setScreen(new CanvasScreen(screen));
	}
}
