package de.schneider_oliver.cheatshit.modules;

import de.schneider_oliver.cheatshit.Config;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public class NoFall {

	public static void noFall(MinecraftClient client) {
		if(Config.isNofallActive) {
			if(client.player != null && client.player.fallDistance > 2) {
				client.player.networkHandler.sendPacket(new PlayerMoveC2SPacket(true));
			}
		}
	}
	
}
