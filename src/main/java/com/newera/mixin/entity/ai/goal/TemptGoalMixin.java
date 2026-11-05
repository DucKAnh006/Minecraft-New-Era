package com.newera.mixin.entity.ai.goal;

import com.newera.docs.entity.animation.INECowAnimation;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.Mob;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TemptGoal.class)
public abstract class TemptGoalMixin {

    @Shadow @Final protected Mob mob;

    @Inject(method = "canUse", at = @At("HEAD"), cancellable = true)
    private void ignoreFoodWhileSleeping(CallbackInfoReturnable<Boolean> cir) {
        if (this.mob instanceof INECowAnimation cowAnim && cowAnim.getBodyState() >= 3 && cowAnim.getBodyState() <= 5) {
            cir.setReturnValue(false);
        }
    }
}

