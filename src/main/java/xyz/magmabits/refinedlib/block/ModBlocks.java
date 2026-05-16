package xyz.magmabits.refinedlib.block;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import xyz.magmabits.refinedlib.RefinedLib;

public class ModBlocks {
    public static final Block EYALEGO_PLUSHIE = registerBlock("eyalego_plushie", new EyalegoPlushie(AbstractBlock.Settings.copy(Blocks.BLACK_WOOL).nonOpaque()));
    public static final Block MAGMA_PLUSHIE = registerBlock("magma_plushie", new MagmaPlushie(AbstractBlock.Settings.copy(Blocks.GRAY_WOOL).nonOpaque()));
    public static final Block MICKEYNNA_PLUSHIE = registerBlock("mickeynna_plushie", new MickeynnaPlushie(AbstractBlock.Settings.copy(Blocks.MAGENTA_WOOL).strength(1f).nonOpaque()));
    public static final Block RYN_PLUSHIE = registerBlock("ryn_plushie", new RynPlushie(AbstractBlock.Settings.copy(Blocks.WHITE_WOOL).nonOpaque()));
    public static final Block SLIME_PLUSHIE = registerBlock("slime_plushie", new SlimePlushie(AbstractBlock.Settings.copy(Blocks.LIGHT_BLUE_WOOL).nonOpaque()));
    public static final Block SPUMCH_PLUSHIE = registerBlock("spumch_plushie", new SpumchPlushie(AbstractBlock.Settings.copy(Blocks.RED_WOOL).nonOpaque()));

    public static void registerModBlocks() {
        RefinedLib.LOGGER.info("Registering Plushies for RefinedLib.");

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> {
            entries.add(EYALEGO_PLUSHIE);
            entries.add(MAGMA_PLUSHIE);
            entries.add(MICKEYNNA_PLUSHIE);
            entries.add(RYN_PLUSHIE);
            entries.add(SLIME_PLUSHIE);
            entries.add(SPUMCH_PLUSHIE);
        });
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, Identifier.of(RefinedLib.MOD_ID, name), new BlockItem(block, new Item.Settings()));
    }

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(RefinedLib.MOD_ID, name), block);
    }
}
