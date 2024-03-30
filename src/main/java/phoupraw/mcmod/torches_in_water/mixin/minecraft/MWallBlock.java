package phoupraw.mcmod.torches_in_water.mixin.minecraft;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.WallBlock;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import phoupraw.mcmod.torches_in_water.block.GlowInkTorchBlock;
import phoupraw.mcmod.torches_in_water.constant.TiWBlocks;

@Mixin(WallBlock.class)
abstract class MWallBlock {
    @WrapOperation(method = "shouldHavePost", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isIn(Lnet/minecraft/registry/tag/TagKey;)Z"))
    private boolean tall(BlockState instance, TagKey<Block> tagKey, Operation<Boolean> original) {
        return instance.isOf(TiWBlocks.GLOW_INK_TORCH) && instance.get(GlowInkTorchBlock.FACING) == Direction.DOWN || original.call(instance, tagKey);
    }
}
