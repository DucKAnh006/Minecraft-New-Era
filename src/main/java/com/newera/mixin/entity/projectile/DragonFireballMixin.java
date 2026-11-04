package com.newera.mixin.entity.projectile;

import net.minecraft.world.entity.projectile.hurtingprojectile.DragonFireball;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DragonFireball.class)
public abstract class DragonFireballMixin {

    @Inject(method = "onHit", at = @At("HEAD"), cancellable = true)
    private void ne$cancelObsidianHit(HitResult hitResult, CallbackInfo ci) {
        if (hitResult.getType() == HitResult.Type.BLOCK && hitResult instanceof BlockHitResult bhr) {
            DragonFireball fireball = (DragonFireball) (Object) this;
            BlockState state = fireball.level().getBlockState(bhr.getBlockPos());
            if (state.is(Blocks.OBSIDIAN) || state.is(Blocks.CRYING_OBSIDIAN)) {
                ci.cancel();
            }
        }
    }
}