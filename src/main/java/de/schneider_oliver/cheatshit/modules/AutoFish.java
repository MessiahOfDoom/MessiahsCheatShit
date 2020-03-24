package de.schneider_oliver.cheatshit.modules;

import net.minecraft.client.MinecraftClient;
import net.minecraft.item.FishingRodItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

public class AutoFish {

	public static boolean shouldRecast = false;
	public static int recastTicks = 0;
	public static int checkCastTicks = 30;
	public static Hand recastHand = Hand.MAIN_HAND;
	public static boolean shouldCheckCast = false;
	
	public static void autoFish(MinecraftClient client) {
		if(client.player != null) {
			if(shouldCheckCast) {
				if(client.player.fishHook != null && client.player.fishHook.isSubmergedInWater() && checkCastTicks >= 0) {
					for(Hand hand: Hand.values()) {
						if(client.player.getStackInHand(hand).getItem() instanceof FishingRodItem) {
							ActionResult result = client.interactionManager.interactItem(client.player, client.world, hand);
							if(result.isAccepted()) {
								shouldRecast = client.player.getStackInHand(hand).getDamage() < (client.player.getStackInHand(hand).getMaxDamage() - 2);
								recastHand = hand;
								checkCastTicks = 30;
								shouldCheckCast = false;
								return;
							}
						}
						
					}
				}
				if(checkCastTicks-- < 0) {
					shouldCheckCast = false;
					checkCastTicks = 30;
				}
			}
			if(shouldRecast) {
				if(recastTicks++ > 20) {
					client.interactionManager.interactItem(client.player, client.world, recastHand);
					recastTicks = 0;
					shouldRecast = false;
				}
			}
		}
	}
	
}
