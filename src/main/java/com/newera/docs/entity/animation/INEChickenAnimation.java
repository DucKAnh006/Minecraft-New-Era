package com.newera.docs.entity.animation;

import net.minecraft.world.entity.AnimationState;

public interface INEChickenAnimation extends INEAnimal {

    AnimationState getSitAnimationState();
    AnimationState getSittingHoldAnimationState();
    AnimationState getSleepAnimationState();
    AnimationState getSleepingHoldAnimationState();
    AnimationState getWakeUpAnimationState();
    AnimationState getStandAnimationState();
    AnimationState getGrazingAnimationState();
    AnimationState getIdleAnimationState();

    AnimationState getFlyingAnimationState();
    AnimationState getTakeOffAnimationState();
    AnimationState getOnAirAnimationState();
    AnimationState getLandingAnimationState();
}

