package xyz.magmabits.refinedlib.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import xyz.magmabits.refinedlib.utils.SupporterUtils;

@Mixin(PlayerListHud.class)
public abstract class PlayerListEntryMixin {
    @ModifyReturnValue(method = "applyGameModeFormatting", at = @At("RETURN"))
    public Text refinedLib$applySupporterFormattingToName(Text original, PlayerListEntry entry) {
        return SupporterUtils.getSupporterStylisedName(entry.getProfile().getId(), original);
    }
}
