package com.newera.docs.entity.animation;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.animal.feline.CatVariant;
import net.minecraft.world.item.DyeColor;

public interface INECatAnimation {
    default Holder<CatVariant> getVariant() { return null; }
    default boolean isTame() { return false; }
    default DyeColor getCollarColor() { return null; }
    default float getLookWeight() { return 1.0F; }

    AnimationState getSitAnimationState();
    AnimationState getSittingHoldAnimationState();
    AnimationState getSleepAnimationState();
    AnimationState getSleepingHoldAnimationState();
    AnimationState getWakeUpAnimationState();
    AnimationState getStandAnimationState();
    
    AnimationState getStretchAnimationState();
    AnimationState getStalkingAnimationState();
    AnimationState getPounceAnimationState();
    
    AnimationState getIdleAnimationState();
}
