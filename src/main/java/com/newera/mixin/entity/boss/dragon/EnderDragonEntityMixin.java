package com.newera.mixin.entity.boss.dragon;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.newera.docs.entity.animation.INEEnderDragonAnimation;

@Mixin(LivingEntity.class)
public abstract class EnderDragonEntityMixin {
    @Inject(method = "handleEntityEvent", at = @At("HEAD"), cancellable = true)
    private void onHandleEntityEventEnderDragon(byte id, CallbackInfo ci) {
        if ((Object) this instanceof EnderDragon dragon) {
            INEEnderDragonAnimation anim = (INEEnderDragonAnimation) dragon;

            if (id >= 60 && id <= 85) {
                anim.triggerClientAction(id);
                ci.cancel();
            }
        }
    }
}

