package com.newera.docs.entity.animation;

import net.minecraft.world.entity.AnimationState;

public interface INEParrotAnimation {
    AnimationState getIdleGroundAnimationState();
    AnimationState getGroundToFlyAnimationState();
    AnimationState getFlyToGroundAnimationState();
    AnimationState getIdleFlyAnimationState();

    AnimationState getFlyFlapAnimationState();
    AnimationState getGlideFlapAnimationState();

    AnimationState getWalkAnimationState();
    AnimationState getSitAnimationState();
    AnimationState getSittingAnimationState();
    AnimationState getStandAnimationState();
    AnimationState getSleepAnimationState();
    AnimationState getSleepingAnimationState();
    AnimationState getWakeUpAnimationState();
    AnimationState getDanceAnimationState();
}

