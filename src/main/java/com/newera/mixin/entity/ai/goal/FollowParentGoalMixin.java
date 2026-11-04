package com.newera.mixin.entity.ai.goal;

import com.newera.docs.entity.animation.INEAnimal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.animal.Animal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(FollowParentGoal.class)
public class FollowParentGoalMixin {

    @Shadow
    private Animal animal;

    @Shadow
    private Animal parent;

    @Inject(method = "canUse", at = @At("HEAD"), cancellable = true)
    private void checkBodyStateCanUse(CallbackInfoReturnable<Boolean> cir) {
        if (this.animal instanceof INEAnimal neAnimal) {
            if (neAnimal.getBodyState() > 0) {
                cir.setReturnValue(false);
                return;
            }

            if (!this.animal.level().isClientSide()) {
                UUID parentUUID = neAnimal.getNEParentUUID();
                if (parentUUID != null) {
                    net.minecraft.server.level.ServerLevel serverLevel = (net.minecraft.server.level.ServerLevel) this.animal.level();
                    net.minecraft.world.entity.Entity entity = serverLevel.getEntity(parentUUID);
                    
                    if (entity instanceof Animal adult && adult.isAlive() && !adult.isBaby()) {
                        if (this.animal.distanceToSqr(adult) < 3.0D) {
                            cir.setReturnValue(false);
                        } else {
                            this.parent = adult;
                            cir.setReturnValue(true);
                        }
                        return;
                    } else {
                        neAnimal.setNEParentUUID(null);
                    }
                }
            }
        }
    }

    @Inject(method = "canContinueToUse", at = @At("HEAD"), cancellable = true)
    private void checkBodyStateCanContinueToUse(CallbackInfoReturnable<Boolean> cir) {
        if (this.animal instanceof INEAnimal neAnimal && neAnimal.getBodyState() > 0) {
            cir.setReturnValue(false);
        }
    }
}
