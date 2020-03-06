package com.williambl.pistoncontrol;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.PistonTileEntity;

import java.util.HashMap;
import java.util.Map;

public interface PistonBlockHooks {

    Map<PistonTileEntity, CompoundNBT> blockEntityTags = new HashMap<>();

    default Map<PistonTileEntity, CompoundNBT> getTileEntityTags() {
        return blockEntityTags;
    }
}
