package de.schneider_oliver.cheatshit.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import de.schneider_oliver.cheatshit.Config;
import net.minecraft.client.network.ClientPlayerInteractionManager;

@Mixin(ClientPlayerInteractionManager.class)
public class GetReachDistanceMixin {

	@Inject(at = @At("HEAD"), method = "getReachDistance()F", cancellable = true)
	private void getReachDistance(CallbackInfoReturnable<Float> info){
		if(Config.isReachActive)info.setReturnValue(7.0F);
	}

}
