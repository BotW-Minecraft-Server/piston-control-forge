package com.williambl.pistoncontrol.mixin;

import com.williambl.pistoncontrol.TileEntityHooks;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(TileEntity.class)
public class TileEntityMixin implements TileEntityHooks {

    @Shadow private BlockState cachedBlockState;

    @Override
    public void setCachedState(BlockState state) {
        cachedBlockState = state;
    }
}
