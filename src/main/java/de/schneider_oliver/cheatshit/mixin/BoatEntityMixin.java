package de.schneider_oliver.cheatshit.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import de.schneider_oliver.cheatshit.modules.BoatFly;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.world.World;

@Mixin(BoatEntity.class)
public abstract class BoatEntityMixin extends Entity{

	public BoatEntityMixin(EntityType<?> type, World world) {
		super(type, world);
	}

	@Shadow
	private BoatEntity.Location location;
	@Shadow
	private boolean pressingLeft;
	@Shadow
	private boolean pressingRight;
	
	@Inject(at = @At("TAIL"), method = "tick()V")
	public void tick(CallbackInfo info) {
		if(getPassengerList().contains(MinecraftClient.getInstance().player))BoatFly.location = this.location;
	}
	
	@Inject(at = @At("HEAD"), method = "updatePaddles()V")
	private void updatePaddles(CallbackInfo info) {
		if(getPassengerList().contains(MinecraftClient.getInstance().player) && !BoatFly.isBoatInWater()) {
			this.pressingLeft = false;
			this.pressingRight = false;
		}
	}
	
}
