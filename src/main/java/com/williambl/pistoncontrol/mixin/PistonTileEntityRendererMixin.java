package com.williambl.pistoncontrol.mixin;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.williambl.pistoncontrol.TileEntityHooks;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.PistonTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.tileentity.PistonTileEntity;
import net.minecraft.tileentity.TileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PistonTileEntityRenderer.class)
public class PistonTileEntityRendererMixin {

    @Inject(
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/tileentity/PistonTileEntityRenderer;func_228876_a_(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Lcom/mojang/blaze3d/matrix/MatrixStack;Lnet/minecraft/client/renderer/IRenderTypeBuffer;Lnet/minecraft/world/World;ZI)V"),
            method = "render"
    )
    private void renderTileEntity(PistonTileEntity pistonTileEntity, float f, MatrixStack matrixStack, IRenderTypeBuffer vertexConsumerProvider, int i, int j, CallbackInfo ci) {
        if (pistonTileEntity.getPistonState().hasTileEntity()) {
            TileEntity be = pistonTileEntity.getPistonState().createTileEntity(pistonTileEntity.getWorld());
            if (be != null && TileEntityRendererDispatcher.instance.getRenderer(be) != null) {
                be.setWorldAndPos(pistonTileEntity.getWorld(), pistonTileEntity.getPos());
                ((TileEntityHooks)be).setCachedState(pistonTileEntity.getPistonState());
                TileEntityRendererDispatcher.instance.renderTileEntity(be, f, matrixStack, vertexConsumerProvider);
                be.remove();
            }
        }
    }
}
