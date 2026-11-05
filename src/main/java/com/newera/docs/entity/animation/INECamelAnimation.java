package com.newera.docs.entity.animation;

import net.minecraft.world.entity.AnimationState;

public interface INECamelAnimation extends INEAnimal {
    AnimationState getSitAnimationState();

    AnimationState getSittingHoldAnimationState();

    AnimationState getSleepAnimationState();

    AnimationState getSleepingHoldAnimationState();

    AnimationState getWakeUpAnimationState();

    AnimationState getStandAnimationState();

    AnimationState getEatingAnimationState();

    AnimationState getIdleAnimationState();

    AnimationState getDashAnimationState();
}

