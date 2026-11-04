package com.newera.docs.entity.state;

import com.newera.docs.entity.variant.NESheepVariant;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.item.DyeColor;

public class NESheepRenderState extends LivingEntityRenderState {
    public net.minecraft.core.Holder<NESheepVariant> variant;
    public boolean isSheared;
    public DyeColor color;

    public final AnimationState sitAnimationState = new AnimationState();
    public final AnimationState sittingHoldAnimationState = new AnimationState();
    public final AnimationState sleepAnimationState = new AnimationState();
    public final AnimationState sleepingHoldAnimationState = new AnimationState();
    public final AnimationState wakeUpAnimationState = new AnimationState();
    public final AnimationState standAnimationState = new AnimationState();
    public final AnimationState grazingAnimationState = new AnimationState();
    public final AnimationState idleAnimationState = new AnimationState();
}
