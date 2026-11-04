package com.newera.docs.entity.animation;

import net.minecraft.world.entity.AnimationState;

public interface INEWolfAnimation extends INEAnimal {
    void setBodyState(byte state);
    byte getBodyState();

    AnimationState getSitAnimationState();
    AnimationState getSittingHoldAnimationState();
    AnimationState getSleepAnimationState();
    AnimationState getSleepingHoldAnimationState();
    AnimationState getWakeUpAnimationState();
    AnimationState getStandAnimationState();
    AnimationState getHowlAnimationState();
    AnimationState getSitHowlingAnimationState();
    AnimationState getIdleAnimationState();
}
