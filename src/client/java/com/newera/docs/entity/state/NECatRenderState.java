package com.newera.docs.entity.state;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.animal.feline.CatVariant;
import net.minecraft.core.Holder;
import net.minecraft.world.item.DyeColor;

public class NECatRenderState extends LivingEntityRenderState {
    public Holder<CatVariant> variant;
    public boolean isTame;
    public DyeColor collarColor;
    public float lookWeight = 1.0F;

    public final AnimationState sitAnimationState = new AnimationState();
    public final AnimationState sittingHoldAnimationState = new AnimationState();
    public final AnimationState sleepAnimationState = new AnimationState();
    public final AnimationState sleepingHoldAnimationState = new AnimationState();
    public final AnimationState wakeUpAnimationState = new AnimationState();
    public final AnimationState standAnimationState = new AnimationState();
    
    public final AnimationState stretchAnimationState = new AnimationState();
    public final AnimationState stalkingAnimationState = new AnimationState();
    public final AnimationState pounceAnimationState = new AnimationState();
    
    public final AnimationState idleAnimationState = new AnimationState();
}
