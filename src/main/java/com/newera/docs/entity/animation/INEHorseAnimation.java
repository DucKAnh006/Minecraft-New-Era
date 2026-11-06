package com.newera.docs.entity.animation;

import net.minecraft.world.entity.AnimationState;

public interface INEHorseAnimation extends INEAnimal {
    AnimationState getSitAnimationState();
    AnimationState getSittingHoldAnimationState();
    AnimationState getSleepAnimationState();
    AnimationState getSleepingHoldAnimationState();
    AnimationState getWakeUpAnimationState();
    AnimationState getStandAnimationState();
    AnimationState getWalkAnimationState();
    AnimationState getGrazingAnimationState();
    AnimationState getIdleAnimationState();
    AnimationState getRearAnimationState();
    AnimationState getAttackAnimationState();
}

