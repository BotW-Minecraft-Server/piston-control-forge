package com.williambl.pistoncontrol.mixin;

import com.williambl.pistoncontrol.PistonBlockHooks;
import net.minecraft.block.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.PistonTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;
import java.util.Map;

@Mixin(PistonBlock.class)
public class PistonBlockMixin extends Block implements PistonBlockHooks {

    private CompoundNBT tmpTileEntityTag = null;

    public PistonBlockMixin(Properties settings) {
        super(settings);
    }

    @Redirect(
            at = @At(value = "FIELD", target = "Lnet/minecraft/block/Blocks;OBSIDIAN:Lnet/minecraft/block/Block;", opcode = Opcodes.GETSTATIC),
            method = "canPush"
    )
    private static Block getObsidianBlock() {
        return null;
    }

    @Redirect(
            at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;getBlockHardness(Lnet/minecraft/world/IBlockReader;Lnet/minecraft/util/math/BlockPos;)F"),
            method = "canPush"
    )
    private static float getHardnessValue(BlockState blockState, IBlockReader world, BlockPos pos) {
        return 0;
    }

    @Inject(
            at = @At(value = "RETURN", ordinal = 7),
            method = "canPush",
            cancellable = true
    )
    private static void hasTileEntity(BlockState blockStateIn, World worldIn, BlockPos pos, Direction facing, boolean destroyBlocks, Direction p_185646_5_, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }

    @Inject(
            at = @At(
                    value = "INVOKE_ASSIGN",
                    target = "net/minecraft/world/World.getBlockState(Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/BlockState;",
                    ordinal = 3
            ),
            method = "doMove",
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void saveBEs(World world, BlockPos pos, Direction dir, boolean retract, CallbackInfoReturnable<Boolean> cir, BlockPos blockPos, PistonBlockStructureHelper pistonHandler, Map map, List list, List list2, List list3, int j, BlockState[] blockStates, Direction direction, int l, BlockPos blockPos4) {
        TileEntity be = world.getTileEntity(blockPos4);
        world.removeTileEntity(blockPos4);
        if (be != null) {
            tmpTileEntityTag = be.serializeNBT();
        }
    }

    @Redirect(
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/MovingPistonBlock;createTilePiston(Lnet/minecraft/block/BlockState;Lnet/minecraft/util/Direction;ZZ)Lnet/minecraft/tileentity/TileEntity;",
                    ordinal = 0
            ),
            method = "doMove"
    )
    private TileEntity createTileEntityPiston(BlockState pushedBlock, Direction dir, boolean extending, boolean bl) {
        PistonTileEntity be = (PistonTileEntity) MovingPistonBlock.createTilePiston(pushedBlock, dir, extending, bl);
        blockEntityTags.put(be, tmpTileEntityTag);
        return be;
    }

}
