package de.schneider_oliver.cheatshit.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import de.schneider_oliver.cheatshit.Config;
import de.schneider_oliver.cheatshit.modules.XRay;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

@Mixin(BlockState.class)
public class BlockStateMixin {
	
	@Shadow
	public Block getBlock() {return null;};
	
	@Inject(at = @At("HEAD"), method = "getCullingFace(Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction;)Lnet/minecraft/util/shape/VoxelShape;", cancellable = true)
	public void getCullingFace(BlockView view, BlockPos pos, Direction facing, CallbackInfoReturnable<VoxelShape> info) {
		if(Config.isXRayActive && !XRay.isBlockInList(view.getBlockState(pos))) {
			info.setReturnValue(VoxelShapes.empty());
		}
	}
	
	@Inject(at = @At("HEAD"), method = "getLuminance()I", cancellable = true)
	public void getLuminance(CallbackInfoReturnable<Integer> info) {
		if(Config.isXRayActive && XRay.isBlockInList(getBlock())) {
			info.setReturnValue(2);
		}
	}

}
