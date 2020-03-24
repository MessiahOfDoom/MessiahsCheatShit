package de.schneider_oliver.cheatshit.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import de.schneider_oliver.cheatshit.Config;
import de.schneider_oliver.cheatshit.modules.AutoFish;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

@Mixin(ClientWorld.class)
public class PlaySoundMixin {

	@Inject(at = @At("HEAD"), method = "playSound(Lnet/minecraft/entity/player/PlayerEntity;DDDLnet/minecraft/sound/SoundEvent;Lnet/minecraft/sound/SoundCategory;FF)V")
	public void playSound(PlayerEntity player, double x, double y, double z, SoundEvent sound, SoundCategory category, float volume, float pitch, CallbackInfo info) {
		if(Config.isAutoFishActive) {
			MinecraftClient instance = MinecraftClient.getInstance();
			if(player == instance.player && sound.equals(SoundEvents.ENTITY_FISHING_BOBBER_SPLASH)) {
				if(player.fishHook != null) {
					AutoFish.shouldCheckCast = true;
				}
			}
		}
	}
	
}
