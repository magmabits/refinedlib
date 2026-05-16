package xyz.magmabits.refinedlib.block;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class MickeynnaPlushie extends PlushieBlock{
    protected MickeynnaPlushie(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType options) {
        tooltip.add(Text.translatable("tooltip.refinedlib.mickeynna_plushie").formatted(Formatting.DARK_GRAY, Formatting.ITALIC));
    }
}
