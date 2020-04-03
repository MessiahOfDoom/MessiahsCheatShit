package de.schneider_oliver.cheatshit.modules;

import de.schneider_oliver.cheatshit.Config;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.util.math.Vec3d;

public class BoatFly {
	
	public static BoatEntity.Location location = BoatEntity.Location.IN_WATER;
	
	public static void boatFly(MinecraftClient client) {
		ClientPlayerEntity player = client.player;
		if(Config.isBoatFlyActive && isInBoat(player)) {
			BoatEntity boat = (BoatEntity) player.getVehicle();
			if(client.options.keyJump.isPressed()) {
				boat.onGround = false;
				boat.setVelocity(boat.getVelocity().add(0, Config.boatSpeedVert - boat.getVelocity().y, 0));
			}
			if(!isBoatInWater()) {
				double fw = player.input.movementForward;
				double sw = player.input.movementSideways;
				float yaw = player.bodyYaw;
				if(fw == 0 && sw == 0)boat.setVelocity(new Vec3d(0, boat.getVelocity().y, 0));
				else boat.setVelocity(new Vec3d((fw * Config.boatSpeedHor * Math.cos(Math.toRadians(yaw + 90.0F)) + sw * Config.boatSpeedHor * Math.sin(Math.toRadians(yaw + 90.0F))), boat.getVelocity().y, (fw * Config.boatSpeedHor * Math.sin(Math.toRadians(yaw + 90.0F)) - sw * Config.boatSpeedHor * Math.cos(Math.toRadians(yaw + 90.0F)))));
				boat.yaw = player.yaw;
			}
			
		}
	}
	
	
	public static boolean isBoatInWater() {
		return location.equals(BoatEntity.Location.IN_WATER) || location.equals(BoatEntity.Location.UNDER_WATER) || location.equals(BoatEntity.Location.UNDER_FLOWING_WATER);
	}
	
	public static boolean isInBoat(PlayerEntity player) {
		return player != null && player.hasVehicle() && player.getVehicle() instanceof BoatEntity;
	}
}
