package xyz.magmabits.refinedlib.utils;

import net.minecraft.item.ItemStack;

public interface ColorableItem {
    int startColor(ItemStack stack);
    int endColor(ItemStack stack);
    int backgroundColor(ItemStack stack);
}