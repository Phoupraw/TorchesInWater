package phoupraw.mcmod.torches_in_water.mixin.minecraft;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.Pair;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import phoupraw.mcmod.torches_in_water.api.BlockFluidCallback;

@Mixin(FlowableFluid.class)
abstract class MFlowableFluid extends Fluid {
    private static final ThreadLocal<FluidState> UPDATED_FLUID_STATE = new ThreadLocal<>();
    @ModifyExpressionValue(method = "onScheduledTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/fluid/FlowableFluid;getUpdatedState(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;)Lnet/minecraft/fluid/FluidState;"))
    private FluidState captureUpdatedFluidState(FluidState original) {
        UPDATED_FLUID_STATE.set(original);
        return original;
    }
    @ModifyArgs(method = "onScheduledTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z"))
    private void blockFluid(Args args, World world, BlockPos pos, FluidState state) {
        BlockState blockState = BlockFluidCallback.BLOCK_FLUID.find(world, pos, Pair.of(state, UPDATED_FLUID_STATE.get()));
        UPDATED_FLUID_STATE.remove();
        if (blockState != null) {
            args.set(1, blockState);
        }
    }
}
