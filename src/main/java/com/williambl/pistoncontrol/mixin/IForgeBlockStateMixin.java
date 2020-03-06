package com.williambl.pistoncontrol.mixin;

import com.williambl.pistoncontrol.PistonControl;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraftforge.common.extensions.IForgeBlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(IForgeBlockState.class)
public interface IForgeBlockStateMixin {
    /**
     * @author Willbl3pic
     *
     * @reason Unfortunately because of Forge's patches, I have to use an overwrite - I can't inject into interfaces.
     */
    @Overwrite(
            remap = false
    )
    default boolean isStickyBlock() {
        return PistonControl.STICKY_BLOCKS.contains(((BlockState) this).getBlock());
    }
}
