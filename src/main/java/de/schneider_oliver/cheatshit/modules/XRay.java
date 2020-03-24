package de.schneider_oliver.cheatshit.modules;

import de.schneider_oliver.cheatshit.Config;
import de.schneider_oliver.cheatshit.ModMain;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.TranslatableText;

public class XRay {
	

	private static boolean wasXRayPressedLastTick = false;
	
	public static void toggleXray(MinecraftClient client) {
		if(ModMain.bindingToggleXRay.isPressed()) {
			if(!wasXRayPressedLastTick) {
				wasXRayPressedLastTick = true;
				Config.isXRayActive = !Config.isXRayActive;
				client.worldRenderer.reload();
			}
			
		}else {
			wasXRayPressedLastTick = false;
		}
	}
	
	public static boolean isBlockInList(BlockState state) {
		
		String internalName = state.getBlock().getName() instanceof TranslatableText ? ((TranslatableText)state.getBlock().getName()).getKey() : state.getBlock().getName().asString();
		for(String s: Config.xrayFilter.split(";")) {
			if(s.length() > 0) {
				if(internalName.matches("^.*" + s + ".*$"))return true;
			}
		}
		return false;
	}

}
