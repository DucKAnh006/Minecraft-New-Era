package com.newera.mixin.entity.ai.goal;

import com.newera.docs.entity.animation.INEAnimal;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PanicGoal.class)
public abstract class PanicGoalMixin {

    @Shadow protected PathfinderMob mob;

    @Inject(method = "start", at = @At("HEAD"))
    private void wakeUpOnPanic(CallbackInfo ci) {
        if (this.mob instanceof INEAnimal neAnimal) {
            if (neAnimal.getBodyState() > 0) {
                neAnimal.setBodyState((byte) 0); // Wake up instantly
            }
        }
    }
}
