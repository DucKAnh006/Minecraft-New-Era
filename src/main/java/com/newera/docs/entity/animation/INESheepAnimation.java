package com.newera.docs.entity.animation;

import net.minecraft.world.entity.AnimationState;
import com.newera.docs.entity.variant.NESheepVariant;

public interface INESheepAnimation extends INEAnimal {
    AnimationState getSitAnimationState();

    AnimationState getSittingHoldAnimationState();

    AnimationState getSleepAnimationState();

    AnimationState getSleepingHoldAnimationState();

    AnimationState getWakeUpAnimationState();

    AnimationState getStandAnimationState();

    AnimationState getGrazingAnimationState();

    AnimationState getIdleAnimationState();

    net.minecraft.resources.ResourceKey<com.newera.docs.entity.variant.NESheepVariant> getVariantKey();

    void setVariantKey(net.minecraft.resources.ResourceKey<com.newera.docs.entity.variant.NESheepVariant> variantKey);

    net.minecraft.core.Holder<NESheepVariant> getVariant();
}

