package com.newera.docs.sound;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;

public class ModSounds {
    public static final SoundEvent ENDER_DRAGON_BITE = registerSoundEvent("entity.ender_dragon.bite");
    public static final SoundEvent ENDER_DRAGON_BREATH = registerSoundEvent("entity.ender_dragon.breath");
    public static final SoundEvent ENDER_DRAGON_BREATH_CLOSE = registerSoundEvent("entity.ender_dragon.breath_close");
    public static final SoundEvent ENDER_DRAGON_DEATH = registerSoundEvent("entity.ender_dragon.death");
    public static final SoundEvent ENDER_DRAGON_FLAP = registerSoundEvent("entity.ender_dragon.flap");
    public static final SoundEvent ENDER_DRAGON_HURT = registerSoundEvent("entity.ender_dragon.hurt");
    public static final SoundEvent ENDER_DRAGON_IDLE = registerSoundEvent("entity.ender_dragon.idle");
    public static final SoundEvent ENDER_DRAGON_ROAR = registerSoundEvent("entity.ender_dragon.roar");
    public static final SoundEvent ENDER_DRAGON_STEP = registerSoundEvent("entity.ender_dragon.step");
    public static final SoundEvent ENDER_DRAGON_STOMP = registerSoundEvent("entity.ender_dragon.stomp");
    public static final SoundEvent ENDER_DRAGON_WHOOSH = registerSoundEvent("entity.ender_dragon.whoosh");

    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = Identifier.fromNamespaceAndPath("newera", name);
        return Registry.register(BuiltInRegistries.SOUND_EVENT, id, SoundEvent.createVariableRangeEvent(id));
    }

    public static void initialize() {
        System.out.println("Registering ModSounds for newera");
    }
}

