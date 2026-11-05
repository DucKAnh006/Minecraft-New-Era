package com.newera.mixin.entity;

import com.newera.docs.entity.animation.INEAnimal;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class EntitySitHitboxMixin {

    @Inject(method = "getDimensions", at = @At("RETURN"), cancellable = true)
    private void newera$modifySittingHitbox(Pose pose, CallbackInfoReturnable<EntityDimensions> cir) {
        if ((Object) this instanceof INEAnimal animEntity) {
            byte state = animEntity.getBodyState();
            if (state >= 1 && state <= 5) {
                EntityDimensions current = cir.getReturnValue();
                cir.setReturnValue(current.scale(1.0F, 0.6F));
            }
        }
    }
}

