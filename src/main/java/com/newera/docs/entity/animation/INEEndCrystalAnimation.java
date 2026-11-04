package com.newera.docs.entity.animation;

import net.minecraft.world.entity.AnimationState;

public interface INEEndCrystalAnimation {
    int STATE_SLEEP = 0;
    int STATE_WAKING_UP = 1;
    int STATE_AWAKE = 2;

    AnimationState getSleepAnimationState();
    AnimationState getWakeUpAnimationState();
    AnimationState getBaseAnimationState();
    AnimationState getIdleAnimationState();

    int getCrystalState();
    void setCrystalState(int state);
    void triggerWakeUp();
}
