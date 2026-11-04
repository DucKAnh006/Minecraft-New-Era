package com.newera.docs.entity.state;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.animal.chicken.ChickenVariant;
import org.jspecify.annotations.Nullable;

public class NEChickenRenderState extends LivingEntityRenderState {

    @Nullable
    public ResourceKey<ChickenVariant> variantKey;

    public boolean isBaby;

    public final AnimationState sitAnimationState = new AnimationState();
    public final AnimationState sittingHoldAnimationState = new AnimationState();
    public final AnimationState sleepAnimationState = new AnimationState();
    public final AnimationState sleepingHoldAnimationState = new AnimationState();
    public final AnimationState wakeUpAnimationState = new AnimationState();
    public final AnimationState standAnimationState = new AnimationState();
    public final AnimationState grazingAnimationState = new AnimationState();
    public final AnimationState idleAnimationState = new AnimationState();
    
    public final AnimationState flyingAnimationState = new AnimationState();
    public final AnimationState takeOffAnimationState = new AnimationState();
    public final AnimationState onAirAnimationState = new AnimationState();
    public final AnimationState landingAnimationState = new AnimationState();
}
