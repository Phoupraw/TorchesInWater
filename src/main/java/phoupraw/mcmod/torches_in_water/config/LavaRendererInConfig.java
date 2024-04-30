package phoupraw.mcmod.torches_in_water.config;

import dev.isxander.yacl3.gui.image.ImageRenderer;
import net.fabricmc.fabric.api.transfer.v1.client.fluid.FluidVariantRendering;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.fluid.Fluids;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class LavaRendererInConfig implements RendererInConfig {
    public static final CompletableFuture<Optional<ImageRenderer>> FUTURE = CompletableFuture.completedFuture(Optional.of(new LavaRendererInConfig()));
    @Override
    public int render(DrawContext graphics, int x, int y, int renderWidth, float tickDelta) {
        graphics.drawSprite(x, y, 0, 16, 16, FluidVariantRendering.getSprite(FluidVariant.of(Fluids.LAVA)));
        return 16;
    }
}
