package com.newera.docs.entity.animation;

import net.minecraft.world.entity.AnimationState;

public interface INEEnderDragonAnimation {
    AnimationState getOnGroundPoseAnimationState();
    AnimationState getHoverPoseAnimationState();
    AnimationState getOnAirPoseAnimationState();
    AnimationState getWalkAnimationState();
    AnimationState getFlyAnimationState();
    AnimationState getBiteAnimationState();
    AnimationState getDecelerateAnimationState();
    AnimationState getLandingAnimationState();
    AnimationState getRoarAnimationState();
    AnimationState getPounceLeftAnimationState();
    AnimationState getPounceRightAnimationState();
    AnimationState getStompAnimationState();
    AnimationState getBreathCloseAnimationState();
    AnimationState getBreathAnimationState();
    AnimationState getTakeoffAnimationState();
    AnimationState getFallingAnimationState();
    AnimationState getLookingAnimationState();
    AnimationState getRollAnimationState();
    AnimationState getAccelerateAnimationState();
    AnimationState getRoarHoverAnimationState();
    AnimationState getHoverBreathAnimationState();
    AnimationState getFlyBreathAnimationState();
    AnimationState getFlyLongBreathAnimationState();
    AnimationState getTeleportAnimationState();
    AnimationState getRoarCurseAnimationState();
    AnimationState getDeathAnimationState();

    float getSmoothLookYaw();
    float getSmoothLookPitch();
    float getSmoothFlightRoll();
    
    int getFightStage();
    void setFightStage(int stage);
    
    int getForcedBasePose();
    void setForcedBasePose(int pose);
    
    void triggerClientAction(byte actionByte);
}
