package com.newera.docs.entity.state;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

public class NEWolfRenderState extends LivingEntityRenderState {

    public boolean isBaby;
    public boolean isWet;
    public boolean isAngry;
    public boolean hasArmor;
    public boolean isTame;
    public net.minecraft.world.item.DyeColor collarColor;
    public net.minecraft.resources.Identifier customTexture;
    public net.minecraft.world.entity.animal.wolf.WolfVariant variant;
    public float tailAngle;
    
    public final AnimationState sitAnimationState = new AnimationState();
    public final AnimationState sittingHoldAnimationState = new AnimationState();
    public final AnimationState sleepAnimationState = new AnimationState();
    public final AnimationState sleepingHoldAnimationState = new AnimationState();
    public final AnimationState wakeUpAnimationState = new AnimationState();
    public final AnimationState standAnimationState = new AnimationState();
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState howlAnimationState = new AnimationState();
    public final AnimationState sitHowlingAnimationState = new AnimationState();
}
