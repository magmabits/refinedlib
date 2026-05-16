package xyz.magmabits.refinedlib.mixin;

import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import xyz.magmabits.refinedlib.utils.SupporterUtils;

@Mixin(EntityRenderer.class)
public abstract class PlayerNametagMixin<T extends Entity> {
    @ModifyVariable(method = "renderLabelIfPresent", at = @At("HEAD"), argsOnly = true, index = 2)
    public Text refinedLib$applyNametag(Text text, T entity) {
        if (text == null || !(entity instanceof PlayerEntity player)) return text;
        return SupporterUtils.getSupporterStylisedName(player.getUuid(), text);
    }
}