package phoupraw.mcmod.torches_in_water.config;

import dev.isxander.yacl3.gui.image.ImageRenderer;
import net.fabricmc.fabric.api.transfer.v1.client.fluid.FluidVariantRendering;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.client.gui.DrawContext;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public record FluidRendererInConfig(FluidVariant fluid) implements RendererInConfig {
    public static CompletableFuture<Optional<ImageRenderer>> of(FluidVariant fluid) {
        return CompletableFuture.completedFuture(Optional.of(new FluidRendererInConfig(fluid)));
    }
    @Override
    public int render(DrawContext graphics, int x, int y, int renderWidth, float tickDelta) {
        int c = FluidVariantRendering.getColor(fluid);
        graphics.drawSprite(x, y, 0, 16, 16, FluidVariantRendering.getSprite(fluid), c >>> 16 & 0xFF, c >>> 8 & 0xFF, c & 0xFF, c >>> 24 & 0xFF);
        return 16;
    }
}
