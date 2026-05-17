package xyz.magmabits.refinedlib.sound;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import xyz.magmabits.refinedlib.RefinedLib;

public class ModSounds {
    public static final SoundEvent PLUSHIE_USE = registerSoundEvent("plushie_use");

    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = Identifier.of(RefinedLib.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerSounds() {
        RefinedLib.LOGGER.info("Registering Sounds for RefinedLib.");
    }
}
