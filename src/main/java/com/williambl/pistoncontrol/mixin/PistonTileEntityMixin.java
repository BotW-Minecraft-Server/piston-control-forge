package com.williambl.pistoncontrol.mixin;

import com.williambl.pistoncontrol.PistonBlockHooks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.PistonTileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PistonTileEntity.class)
public class PistonTileEntityMixin {

    @Inject(
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;neighborChanged(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/Block;Lnet/minecraft/util/math/BlockPos;)V"),
            method = "clearPistonTileEntity"
    )
    private void onFinish(CallbackInfo info) {
        moveBE((PistonTileEntity) (Object) this);
    }

    @Inject(
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;neighborChanged(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/Block;Lnet/minecraft/util/math/BlockPos;)V"),
            method = "tick"
    )
    private void onTick(CallbackInfo info) {
        moveBE((PistonTileEntity) (Object) this);
    }

    private void moveBE(PistonTileEntity thisAsPBE) {
        BlockPos pos = thisAsPBE.getPos();
        World world = thisAsPBE.getWorld();
        if (world == null)
            return;
        BlockState bs = world.getBlockState(pos);
        if (world.isRemote) {
            world.notifyBlockUpdate(pos, bs, bs, 2);
            return;
        }
        CompoundNBT tag = ((PistonBlockHooks) Blocks.PISTON).getTileEntityTags().get(thisAsPBE);
        if (tag == null)
            return;
        tag.putInt("x", pos.getX());
        tag.putInt("y", pos.getY());
        tag.putInt("z", pos.getZ());

        world.getTileEntity(pos).deserializeNBT(tag);
        ((ServerWorld) world).getChunkProvider().markBlockChanged(pos);
    }
}
