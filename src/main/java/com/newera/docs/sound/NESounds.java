package com.newera.docs.sound;

import com.newera.docs.NewEraMod;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;

public class NESounds {
    public static final SoundEvent WOLF_HOWL = registerSoundEvent("entity.wolf.howl");

    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = Identifier.fromNamespaceAndPath(NewEraMod.MOD_ID, name);
        return Registry.register(BuiltInRegistries.SOUND_EVENT, id, SoundEvent.createVariableRangeEvent(id));
    }

    public static void registerSounds() {
        NewEraMod.LOGGER.info("Registering Sounds for " + NewEraMod.MOD_ID);
    }
}
