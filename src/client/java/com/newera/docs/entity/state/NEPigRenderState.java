package com.newera.docs.entity.state;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

import net.minecraft.world.entity.animal.pig.PigVariant;
import org.jspecify.annotations.Nullable;

public class NEPigRenderState extends LivingEntityRenderState {

    @Nullable
    public PigVariant variant;

    public boolean isBaby;
    public boolean isSaddled;
    public net.minecraft.world.item.ItemStack saddle = net.minecraft.world.item.ItemStack.EMPTY;

    public final AnimationState sitAnimationState = new AnimationState();
    public final AnimationState sittingHoldAnimationState = new AnimationState();
    public final AnimationState sleepAnimationState = new AnimationState();
    public final AnimationState sleepingHoldAnimationState = new AnimationState();
    public final AnimationState wakeUpAnimationState = new AnimationState();
    public final AnimationState standAnimationState = new AnimationState();
    public final AnimationState grazingAnimationState = new AnimationState();
    public final AnimationState idleAnimationState = new AnimationState();
}

