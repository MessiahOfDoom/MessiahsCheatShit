package de.schneider_oliver.cheatshit.modules;


import de.schneider_oliver.cheatshit.Config;
import net.minecraft.client.MinecraftClient;

public class FullBright {

	private static double prevGamma = 0;
	
	public static void applyFullBright() {
		if(!Config.isFullbrightActive)MinecraftClient.getInstance().options.gamma = prevGamma;
		else {
			prevGamma = MinecraftClient.getInstance().options.gamma;
			MinecraftClient.getInstance().options.gamma = 999;
		}
	}

}
