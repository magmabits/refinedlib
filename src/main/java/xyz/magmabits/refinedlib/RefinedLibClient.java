package xyz.magmabits.refinedlib;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import xyz.magmabits.refinedlib.block.ModBlocks;

public class RefinedLibClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.EYALEGO_PLUSHIE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MAGMA_PLUSHIE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MICKEYNNA_PLUSHIE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.RYN_PLUSHIE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SLIME_PLUSHIE, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.SPUMCH_PLUSHIE, RenderLayer.getCutout());
    }
}
