package phoupraw.mcmod.torches_in_water.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import phoupraw.mcmod.torches_in_water.constant.TiWBlocks;

public final class TiWClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), TiWBlocks.GLOW_INK_TORCH);
    }
}
