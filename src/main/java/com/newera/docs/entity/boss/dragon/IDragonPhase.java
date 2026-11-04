package com.newera.docs.entity.boss.dragon;

import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import com.newera.docs.entity.animation.INEEnderDragonAnimation;

public interface IDragonPhase {
    /**
     * Called every tick on the server side to update the dragon's behavior in this phase.
     */
    void tick(EnderDragon dragon, INEEnderDragonAnimation mixin);
    
    default void postTick(EnderDragon dragon, INEEnderDragonAnimation mixin) {
        // Mặc định không làm gì, các phase có thể override nếu cần
    }

    default void onEnter(EnderDragon dragon, INEEnderDragonAnimation mixin) {
        // Mặc định không làm gì, reset state khi vừa bước vào phase
    }
}
