package phoupraw.mcmod.torches_in_water.config;

import dev.isxander.yacl3.gui.image.ImageRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public record ItemRendererInConfig(Supplier<ItemStack> itemStack) implements RendererInConfig {
    public static CompletableFuture<Optional<ImageRenderer>> of(Supplier<ItemStack> itemStack) {
        return CompletableFuture.completedFuture(Optional.of(new ItemRendererInConfig(itemStack)));
    }
    @Override
    public int render(DrawContext graphics, int x, int y, int renderWidth, float tickDelta) {
        graphics.drawItem(itemStack().get(), x, y);
        return 16;
    }
}
