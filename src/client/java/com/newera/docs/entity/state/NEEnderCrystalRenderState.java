package com.newera.docs.entity.state;

import net.minecraft.client.renderer.entity.state.EndCrystalRenderState;
import net.minecraft.world.entity.AnimationState;

public class NEEnderCrystalRenderState extends EndCrystalRenderState {
    public final AnimationState sleepAnimationState = new AnimationState();
    public final AnimationState wakeUpAnimationState = new AnimationState();
    public final AnimationState baseAnimationState = new AnimationState();
    public final AnimationState idleAnimationState = new AnimationState();
    public int crystalState = 0;
}
