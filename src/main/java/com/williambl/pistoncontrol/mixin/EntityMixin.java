package com.williambl.pistoncontrol.mixin;

import com.williambl.pistoncontrol.PistonControl;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin {
    @Inject(
            at = @At("HEAD"),
            method = "getPushReaction",
            remap = false,
            cancellable = true
    )
    private void getPushReaction(CallbackInfoReturnable<PushReaction> info) {
        Entity entity = (Entity) (Object) this;

        if (PistonControl.ENTITY_PISTON_BEHAVIOR_NORMAL.contains(entity.getType())) {
            info.setReturnValue(PushReaction.NORMAL);
        }
        if (PistonControl.PISTON_BEHAVIOR_IGNORE.contains(entity.getType())) {
            info.setReturnValue(PushReaction.IGNORE);
        }
    }
}
