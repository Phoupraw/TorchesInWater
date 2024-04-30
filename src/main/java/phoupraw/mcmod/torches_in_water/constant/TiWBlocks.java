package phoupraw.mcmod.torches_in_water.constant;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import phoupraw.mcmod.torches_in_water.block.GlowInkTorchBlock;
import phoupraw.mcmod.torches_in_water.config.TiWConfig;

public sealed interface TiWBlocks permits InterfaceFinaler {
    Block GLOW_INK_TORCH = r(TiWIDs.GLOW_INK_TORCH, new GlowInkTorchBlock(FabricBlockSettings.copyOf(Blocks.TORCH).luminance(TiWConfig.HANDLER.instance().glowInkTorch_luminance)));
    //Block WATER_GLOW_INK_TORCH = r(TiWIDs.WATER_GLOW_INK_TORCH,new WaterGlowInkTorchBlock(FabricBlockSettings.copyOf(GLOW_INK_TORCH).dropsLike(GLOW_INK_TORCH)));
    private static <T extends Block> T r(Identifier id, T value) {
        return Registry.register(Registries.BLOCK, id, value);
    }
}
