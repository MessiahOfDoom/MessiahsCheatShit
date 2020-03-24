package de.schneider_oliver.cheatshit.modules;

import de.schneider_oliver.cheatshit.Config;
import de.schneider_oliver.cheatshit.ModMain;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.entity.player.PlayerEntity;

public class Spectate {


	private static boolean wasSpectateActiveLastTick = false;

	private static PlayerEntity viewingEntity;
	
	public static void toggleSpectate(MinecraftClient client) {
		if(ModMain.bindingToggleSpectate.isPressed() && client.currentScreen == null) {
			KeyBinding.unpressAll();
			Config.isSpectateActive = !Config.isSpectateActive;
		}
	}
	
	public static void spectate(MinecraftClient client) {
		if(Config.isSpectateActive && !client.player.isSpectator()) {
			client.world.getPlayers().stream().filter(s -> s.getName().asString().matches("^.*" + Config.spectateName + ".*$")).findFirst().ifPresent(player -> viewingEntity = player);
			if(viewingEntity != null) {
				client.setCameraEntity(viewingEntity);
				wasSpectateActiveLastTick = true;
			}else {
				Config.isSpectateActive = false;
			}
		}else if(wasSpectateActiveLastTick) {
			client.setCameraEntity(client.player);
		}
	}
	
}
