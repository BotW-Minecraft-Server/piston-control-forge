package com.williambl.pistoncontrol.mixin;

import com.williambl.pistoncontrol.PistonControl;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.PushReaction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockState.class)
public class BlockStateMixin {
    @Inject(
            at = @At("HEAD"),
            method = "getPushReaction",
            cancellable = true
    )
    private void getPushReaction(CallbackInfoReturnable<PushReaction> info) {
        BlockState state = (BlockState) (Object) this;

        if (PistonControl.PISTON_BEHAVIOR_NORMAL.contains(state.getBlock())) {
            info.setReturnValue(PushReaction.NORMAL);
        }
        if (PistonControl.PISTON_BEHAVIOR_DESTROY.contains(state.getBlock())) {
            info.setReturnValue(PushReaction.DESTROY);
        }
        if (PistonControl.PISTON_BEHAVIOR_BLOCK.contains(state.getBlock())) {
            info.setReturnValue(PushReaction.BLOCK);
        }
        if (PistonControl.PISTON_BEHAVIOR_PUSH_ONLY.contains(state.getBlock())) {
            info.setReturnValue(PushReaction.PUSH_ONLY);
        }
    }
}
