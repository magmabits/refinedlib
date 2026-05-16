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
        if (original == null) return null;
        String type = SupporterUtils.getSupporterType(entry.getProfile().getId());
        if (type == null) return original;
        int color = switch (type) {
            case "curator"  -> 0x8bcc7f;
            case "moderator" -> 0xcc7f7f;
            case "membership" -> 0x7f81cc;
            default -> 0xcc7fc7;
        };
        return SupporterUtils.getSupporterStylisedName(entry.getProfile().getId(), original, color);
    }
}
