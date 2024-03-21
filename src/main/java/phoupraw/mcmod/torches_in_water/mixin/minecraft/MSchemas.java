package phoupraw.mcmod.torches_in_water.mixin.minecraft;

import net.minecraft.datafixer.Schemas;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Schemas.class)
abstract class MSchemas {
    //@Shadow
    //@Final
    //private static BiFunction<Integer, Schema, Schema> EMPTY_IDENTIFIER_NORMALIZE;
    //@Shadow
    //private static UnaryOperator<String> replacing(String old, String current) {
    //    throw new IllegalStateException();
    //}
    //@Inject(method = "build", at = @At("RETURN"))
    //private static void append(DataFixerBuilder builder, CallbackInfo ci) {
    //    Schema schema = builder.addSchema(3460, EMPTY_IDENTIFIER_NORMALIZE);
    //    String sId = "aquatictorches:aquatic_torch";
    //    String name = sId + " -> " + TiWIDs.GLOW_INK_TORCH;
    //    UnaryOperator<String> replacing = replacing(sId, TiWIDs.GLOW_INK_TORCH.toString());
    //    builder.addFixer(BlockNameFix.create(schema, name, replacing));
    //    builder.addFixer(ItemNameFix.create(schema, name, replacing));
    //    TiW.LOGGER.info(name + " data fixer upper is added");
    //}
}
