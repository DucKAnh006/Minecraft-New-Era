package com.newera.docs.entity.boss.dragon;

import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import com.newera.docs.entity.animation.INEEnderDragonAnimation;

public interface IDragonPhase {

    void tick(EnderDragon dragon, INEEnderDragonAnimation mixin);

    default void postTick(EnderDragon dragon, INEEnderDragonAnimation mixin) {

    }

    default void onEnter(EnderDragon dragon, INEEnderDragonAnimation mixin) {

    }
}

