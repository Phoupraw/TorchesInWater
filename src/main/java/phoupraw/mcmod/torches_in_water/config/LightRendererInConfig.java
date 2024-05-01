package phoupraw.mcmod.torches_in_water.config;

import dev.isxander.yacl3.gui.image.ImageRenderer;
import net.minecraft.block.LightBlock;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.Items;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.IntSupplier;

public interface LightRendererInConfig extends RendererInConfig {
    static CompletableFuture<Optional<ImageRenderer>> of(IntSupplier light) {return CompletableFuture.completedFuture(Optional.of((LightRendererInConfig) light::getAsInt));}
    @Override
    default int render(DrawContext graphics, int x, int y, int renderWidth, float tickDelta) {
        graphics.drawItem(LightBlock.addNbtForLevel(Items.LIGHT.getDefaultStack(), getLight()), x, y);
        return 16;
    }
    int getLight();
}
