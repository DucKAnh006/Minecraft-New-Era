package com.newera.docs.entity.state;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

public class NEEnderDragonRenderState extends LivingEntityRenderState {
    public final AnimationState onGroundPoseAnimationState = new AnimationState();
    public final AnimationState hoverPoseAnimationState = new AnimationState();
    public final AnimationState onAirPoseAnimationState = new AnimationState();
    public final AnimationState walkAnimationState = new AnimationState();
    public final AnimationState flyAnimationState = new AnimationState();
    public final AnimationState biteAnimationState = new AnimationState();
    public final AnimationState decelerateAnimationState = new AnimationState();
    public final AnimationState landingAnimationState = new AnimationState();
    public final AnimationState roarAnimationState = new AnimationState();
    public final AnimationState pounceLeftAnimationState = new AnimationState();
    public final AnimationState pounceRightAnimationState = new AnimationState();
    public final AnimationState stompAnimationState = new AnimationState();
    public final AnimationState breathCloseAnimationState = new AnimationState();
    public final AnimationState breathAnimationState = new AnimationState();
    public final AnimationState takeoffAnimationState = new AnimationState();
    public final AnimationState fallingAnimationState = new AnimationState();
    public final AnimationState lookingAnimationState = new AnimationState();
    public final AnimationState rollAnimationState = new AnimationState();
    public final AnimationState accelerateAnimationState = new AnimationState();
    public final AnimationState roarHoverAnimationState = new AnimationState();
    public final AnimationState hoverBreathAnimationState = new AnimationState();
    public final AnimationState flyBreathAnimationState = new AnimationState();
    public final AnimationState flyLongBreathAnimationState = new AnimationState();
    public final AnimationState teleportAnimationState = new AnimationState();
    public final AnimationState roarCurseAnimationState = new AnimationState();
    public final AnimationState deathAnimationState = new AnimationState();

    public float lookYaw = 0.0F;
    public float lookPitch = 0.0F;
    public float flightRoll = 0.0F;
    public float flightTurnX = 0.0F;
    public float flightPitch = 0.0F;
    public int basePoseType = 0; // 0 = ON_AIR, 1 = HOVER, 2 = ON_GROUND
    public net.minecraft.world.phys.Vec3 beamOffset;
}
