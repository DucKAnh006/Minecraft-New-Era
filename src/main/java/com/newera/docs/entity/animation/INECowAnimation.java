package com.newera.docs.entity.animation;

import net.minecraft.world.entity.AnimationState;

public interface INECowAnimation extends INEAnimal {
    AnimationState getSitAnimationState();

    AnimationState getSittingHoldAnimationState();

    AnimationState getSleepAnimationState();

    AnimationState getSleepingHoldAnimationState();

    AnimationState getWakeUpAnimationState();

    AnimationState getStandAnimationState();

    AnimationState getGrazingAnimationState();

    AnimationState getIdleAnimationState();
}

