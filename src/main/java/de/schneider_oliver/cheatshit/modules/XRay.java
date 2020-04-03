package de.schneider_oliver.cheatshit.modules;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.schneider_oliver.cheatshit.Config;
import de.schneider_oliver.cheatshit.ModMain;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;

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
		return isBlockInList(state.getBlock());
	}
	
	public static boolean isBlockInList(Block block) {
		String name = block.getName().asString();
		for(String s: Config.xrayFilter.split(";")) {
			if(s.length() > 0) {
				Pattern pattern = Pattern.compile(s, Pattern.CASE_INSENSITIVE);
				Matcher matcher = pattern.matcher(name);
				if(matcher.find())return true;
//				if(name.matches("^.*" + s + ".*$"))return true;
			}
		}
		return false;
	}
	
}
