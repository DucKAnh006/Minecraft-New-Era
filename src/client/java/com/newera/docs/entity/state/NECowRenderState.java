package com.newera.docs.entity.state;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.animal.cow.CowVariant;
import org.jspecify.annotations.Nullable;

public class NECowRenderState extends LivingEntityRenderState {

    @Nullable
    public CowVariant variant;
    public boolean isBaby;

    public final AnimationState sitAnimationState = new AnimationState();
    public final AnimationState sittingHoldAnimationState = new AnimationState();
    public final AnimationState sleepAnimationState = new AnimationState();
    public final AnimationState sleepingHoldAnimationState = new AnimationState();
    public final AnimationState wakeUpAnimationState = new AnimationState();
    public final AnimationState standAnimationState = new AnimationState();
    public final AnimationState grazingAnimationState = new AnimationState();
    public final AnimationState idleAnimationState = new AnimationState();
}