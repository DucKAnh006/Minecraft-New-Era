package com.newera.mixin.entity.boss.dragon;

import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.boss.enderdragon.EnderDragonPart;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnderDragonPart.class)
public abstract class EnderDragonPartMixin {
    @Shadow @Final public String name;

    @Inject(method = "getDimensions", at = @At("HEAD"), cancellable = true)
    private void onGetDimensions(Pose pose, CallbackInfoReturnable<EntityDimensions> cir) {
        if ("head".equals(this.name)) {
            cir.setReturnValue(EntityDimensions.scalable(2.4F, 2.0F));
        } else if ("neck".equals(this.name)) {
            cir.setReturnValue(EntityDimensions.scalable(2.4F, 2.5F));
        } else if ("body".equals(this.name)) {
            cir.setReturnValue(EntityDimensions.scalable(5.5F, 5.0F));
        } else if ("tail".equals(this.name)) {
            cir.setReturnValue(EntityDimensions.scalable(3.6F, 3.2F));
        } else if ("wing".equals(this.name)) {
            cir.setReturnValue(EntityDimensions.scalable(13.0F, 2.5F));
        }
    }
}

