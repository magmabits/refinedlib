package xyz.magmabits.refinedlib.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import xyz.magmabits.refinedlib.utils.ColorableItem;
import xyz.magmabits.refinedlib.utils.TextUtils;

public class DiceItem extends Item implements ColorableItem {
    public DiceItem(Settings settings) {
        super(settings);
    }

    @Override
    public Text getName(ItemStack stack) {
        return TextUtils.applyTextGradient(Text.translatable(this.getTranslationKey()).getString(), 0xCA5A39, 0xEAC7A4);
    }

    @Override
    public int startColor(ItemStack itemStack) {
        return 0xFFeac7a4;
    }

    @Override
    public int endColor(ItemStack itemStack) {
        return 0xFFCA5A39;
    }

    @Override
    public int backgroundColor(ItemStack itemStack) {
        return 0xF000FF00;
    }
}
