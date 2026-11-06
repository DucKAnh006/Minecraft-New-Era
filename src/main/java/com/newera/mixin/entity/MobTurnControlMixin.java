package com.newera.mixin.entity;

import com.newera.docs.entity.animation.INEAnimal;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(MoveControl.class)
public abstract class MobTurnControlMixin {

    @Shadow @Final protected Mob mob;

    @ModifyVariable(method = "rotlerp", at = @At("HEAD"), argsOnly = true, ordinal = 2)
    protected float smoothTurn(float maxTurn) {
        if (this.mob instanceof INEAnimal) {
            double currentSpeed = this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED);
            if (currentSpeed > 0.22D) {
                boolean isPanicking = this.mob.getLastHurtByMob() != null || this.mob.isOnFire() || this.mob.hurtTime > 0;
                float limit = isPanicking ? 55.0F : 35.0F;
                return Math.min(maxTurn, limit);
            }
        }
        return maxTurn;
    }
}

