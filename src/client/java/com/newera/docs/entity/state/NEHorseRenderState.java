package com.newera.docs.entity.state;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.animal.equine.Variant;
import net.minecraft.world.entity.animal.equine.Markings;

public class NEHorseRenderState extends LivingEntityRenderState {

    public boolean isBaby;
    public boolean hasChest;
    public boolean isSaddled;
    public net.minecraft.world.item.ItemStack armor = net.minecraft.world.item.ItemStack.EMPTY;
    public Variant variant;
    public Markings markings;

    public final AnimationState sitAnimationState = new AnimationState();
    public final AnimationState sittingHoldAnimationState = new AnimationState();
    public final AnimationState sleepAnimationState = new AnimationState();
    public final AnimationState sleepingHoldAnimationState = new AnimationState();
    public final AnimationState wakeUpAnimationState = new AnimationState();
    public final AnimationState standAnimationState = new AnimationState();
    public final AnimationState walkAnimationState = new AnimationState();
    public final AnimationState grazingAnimationState = new AnimationState();
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState rearAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();
}

