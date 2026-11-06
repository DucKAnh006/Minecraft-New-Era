package com.newera.docs.entity.state;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.animal.parrot.Parrot;

public class NEParrotRenderState extends LivingEntityRenderState {

    public Parrot.Variant variant;
    public boolean isBaby;
    public float roll;

    public final AnimationState idleGroundAnimationState = new AnimationState();
    public final AnimationState groundToFlyAnimationState = new AnimationState();
    public final AnimationState flyToGroundAnimationState = new AnimationState();
    public final AnimationState idleFlyAnimationState = new AnimationState();

    public final AnimationState flyFlapAnimationState = new AnimationState();
    public final AnimationState glideFlapAnimationState = new AnimationState();

    public final AnimationState walkAnimationState = new AnimationState();
    public final AnimationState sitAnimationState = new AnimationState();
    public final AnimationState sittingAnimationState = new AnimationState();
    public final AnimationState standAnimationState = new AnimationState();
    public final AnimationState sleepAnimationState = new AnimationState();
    public final AnimationState sleepingAnimationState = new AnimationState();
    public final AnimationState wakeUpAnimationState = new AnimationState();
    public final AnimationState danceAnimationState = new AnimationState();
}

