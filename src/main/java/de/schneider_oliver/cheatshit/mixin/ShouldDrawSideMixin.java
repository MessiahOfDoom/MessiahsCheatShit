package de.schneider_oliver.cheatshit.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import de.schneider_oliver.cheatshit.Config;
import de.schneider_oliver.cheatshit.modules.XRay;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;

@Mixin(Block.class)
public class ShouldDrawSideMixin {

	@Inject(at = @At("HEAD"), method = "shouldDrawSide(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction;)Z", cancellable = true)
	private static void shouldDrawSide(BlockState state, BlockView view, BlockPos pos, Direction facing, CallbackInfoReturnable<Boolean> info) {
		if(Config.isXRayActive) {
			if(XRay.isBlockInList(state)) {
				info.setReturnValue(true);
			}else {
				info.setReturnValue(false);
			}
		}
		
	}
	
	@Inject(at = @At("HEAD"), method = "getRenderType(Lnet/minecraft/block/BlockState;)Lnet/minecraft/block/BlockRenderType;", cancellable = true)
	private void getRenderType(BlockState state, CallbackInfoReturnable<BlockRenderType> info) {
		if(Config.isXRayActive && !XRay.isBlockInList(state))info.setReturnValue(BlockRenderType.INVISIBLE);
	}
}
