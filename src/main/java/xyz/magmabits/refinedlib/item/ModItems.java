package xyz.magmabits.refinedlib.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import xyz.magmabits.refinedlib.RefinedLib;

public class ModItems {
    public static final Item DICE = registerItem("dice", new DiceItem(new Item.Settings()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(RefinedLib.MOD_ID, name), item);
    }

    public static void registerModItems() {
        RefinedLib.LOGGER.info("Registering Mod Items for " + RefinedLib.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(DICE);
        });
    }
}
