package com.newera.mixin.entity.monster.enderman;

import com.newera.docs.entity.monster.enderman.INEEnderman;
import net.minecraft.world.entity.monster.Enderman;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.projectile.hurtingprojectile.DragonFireball;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Enderman.class)
public abstract class EnderManMixin extends Monster implements INEEnderman {

    protected EnderManMixin(net.minecraft.world.entity.EntityType<? extends Monster> entityType, net.minecraft.world.level.Level level) {
        super(entityType, level);
    }

    @Unique
    private boolean isDragonSummoned = false;

    @Override
    public boolean isDragonSummoned() {
        return this.isDragonSummoned;
    }

    @Override
    public void setDragonSummoned(boolean summoned) {
        this.isDragonSummoned = summoned;
    }

    @Inject(method = "registerGoals", at = @At("TAIL"))
    private void addDragonAvoidGoal(CallbackInfo ci) {

        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, EnderDragon.class, 16.0F, 1.2D, 1.4D));
    }

    @Inject(method = "hurtServer", at = @At("HEAD"), cancellable = true)
    private void onHurtServer(ServerLevel level, DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {

        if (source.is(DamageTypes.DRAGON_BREATH)
            || source.getDirectEntity() instanceof DragonFireball
            || source.getDirectEntity() instanceof AreaEffectCloud
            || source.getEntity() instanceof EnderDragon
            || source.getDirectEntity() instanceof EnderDragon) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "setTarget", at = @At("HEAD"), cancellable = true)
    private void onSetTarget(LivingEntity target, CallbackInfo ci) {

        if (target instanceof EnderDragon) {
            ci.cancel();
        }
    }

    @Inject(method = "aiStep", at = @At("TAIL"))
    private void onAiStep(CallbackInfo ci) {
        if (!this.level().isClientSide() && this.isDragonSummoned) {
            LivingEntity target = this.getTarget();
            if (target == null || !target.isAlive() || target.isSpectator() || (target instanceof Player p && p.isCreative())) {
                Player nearest = this.level().getNearestPlayer(this, 128.0);
                if (nearest != null && !nearest.isCreative() && !nearest.isSpectator() && nearest.isAlive()) {
                    this.setTarget(nearest);
                    if ((Object) this instanceof net.minecraft.world.entity.NeutralMob neutralMob) {
                        neutralMob.startPersistentAngerTimer();
                        neutralMob.setPersistentAngerTarget(net.minecraft.world.entity.EntityReference.of((LivingEntity) nearest));
                    }
                }
            } else if (this.distanceToSqr(target) > 256.0 && this.tickCount % 40 == 0) {

                double angle = this.random.nextDouble() * Math.PI * 2.0;
                double r = 3.5 + this.random.nextDouble() * 3.5;
                double tx = target.getX() + Math.cos(angle) * r;
                double tz = target.getZ() + Math.sin(angle) * r;
                this.setPos(tx, target.getY(), tz);
                this.level().playSound(null, tx, target.getY(), tz, SoundEvents.ENDERMAN_TELEPORT, SoundSource.HOSTILE, 1.0F, 1.0F);
                if (this.level() instanceof ServerLevel sl) {
                    sl.sendParticles(ParticleTypes.PORTAL, tx, target.getY() + 1.0, tz, 25, 0.5, 1.0, 0.5, 0.1);
                }
            }
        }
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    private void onSaveData(ValueOutput output, CallbackInfo ci) {
        output.putBoolean("DragonSummoned", this.isDragonSummoned);
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void onLoadData(ValueInput input, CallbackInfo ci) {
        this.isDragonSummoned = input.getBooleanOr("DragonSummoned", false);
    }
}

