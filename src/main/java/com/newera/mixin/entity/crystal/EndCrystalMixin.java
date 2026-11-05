package com.newera.mixin.entity.crystal;

import com.newera.docs.entity.animation.INEEndCrystalAnimation;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EndCrystal.class)
public abstract class EndCrystalMixin extends Entity implements INEEndCrystalAnimation {

    @Unique
    private static final EntityDataAccessor<Integer> DATA_CRYSTAL_STATE = SynchedEntityData.defineId(
        EndCrystal.class, EntityDataSerializers.INT
    );

    @Unique
    public final AnimationState sleepAnimationState = new AnimationState();
    @Unique
    public final AnimationState wakeUpAnimationState = new AnimationState();
    @Unique
    public final AnimationState baseAnimationState = new AnimationState();
    @Unique
    public final AnimationState idleAnimationState = new AnimationState();

    @Unique
    private int wakeUpTicks = 0;

    protected EndCrystalMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "defineSynchedData", at = @At("TAIL"))
    private void onDefineSynchedData(SynchedEntityData.Builder entityData, CallbackInfo ci) {
        entityData.define(DATA_CRYSTAL_STATE, INEEndCrystalAnimation.STATE_SLEEP);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void onTick(CallbackInfo ci) {
        EndCrystal crystal = (EndCrystal) (Object) this;
        if (!crystal.showsBottom() && this.getCrystalState() != INEEndCrystalAnimation.STATE_AWAKE) {
            this.setCrystalState(INEEndCrystalAnimation.STATE_AWAKE);
        }

        int state = this.getCrystalState();
        int currentTicks = crystal.time;
        if (state == INEEndCrystalAnimation.STATE_SLEEP) {
            this.sleepAnimationState.startIfStopped(currentTicks);
            this.wakeUpAnimationState.stop();
            this.baseAnimationState.stop();
            this.idleAnimationState.stop();
            this.wakeUpTicks = 0;
        } else if (state == INEEndCrystalAnimation.STATE_WAKING_UP) {
            this.sleepAnimationState.stop();
            if (!this.wakeUpAnimationState.isStarted()) {
                this.wakeUpAnimationState.start(currentTicks);
            }
            this.baseAnimationState.stop();
            this.idleAnimationState.stop();

            this.wakeUpTicks++;
            if (this.wakeUpTicks >= 80) {
                if (!this.level().isClientSide()) {
                    this.setCrystalState(INEEndCrystalAnimation.STATE_AWAKE);
                }
                this.wakeUpTicks = 0;
            }
        } else {
            this.sleepAnimationState.stop();
            this.wakeUpAnimationState.stop();
            this.baseAnimationState.startIfStopped(currentTicks);
            this.idleAnimationState.startIfStopped(currentTicks);
            this.wakeUpTicks = 0;
        }
    }

    @Inject(method = "hurtClient", at = @At("HEAD"), cancellable = true)
    private void onHurtClient(DamageSource source, CallbackInfoReturnable<Boolean> cir) {
        int state = this.getCrystalState();
        if (state != INEEndCrystalAnimation.STATE_AWAKE) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "hurtServer", at = @At("HEAD"), cancellable = true)
    private void onHurtServer(ServerLevel level, DamageSource source, float damage, CallbackInfoReturnable<Boolean> cir) {
        int state = this.getCrystalState();
        if (state != INEEndCrystalAnimation.STATE_AWAKE) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    private void onAddAdditionalSaveData(ValueOutput output, CallbackInfo ci) {
        output.putInt("CrystalState", this.getCrystalState());
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void onReadAdditionalSaveData(ValueInput input, CallbackInfo ci) {
        this.setCrystalState(input.getIntOr("CrystalState", INEEndCrystalAnimation.STATE_SLEEP));
    }

    @Override
    public AnimationState getSleepAnimationState() {
        return this.sleepAnimationState;
    }

    @Override
    public AnimationState getWakeUpAnimationState() {
        return this.wakeUpAnimationState;
    }

    @Override
    public AnimationState getBaseAnimationState() {
        return this.baseAnimationState;
    }

    @Override
    public AnimationState getIdleAnimationState() {
        return this.idleAnimationState;
    }

    @Override
    public int getCrystalState() {
        return this.entityData.get(DATA_CRYSTAL_STATE);
    }

    @Override
    public void setCrystalState(int state) {
        this.entityData.set(DATA_CRYSTAL_STATE, state);
    }

    @Override
    public void triggerWakeUp() {
        if (this.getCrystalState() == INEEndCrystalAnimation.STATE_SLEEP) {
            this.setCrystalState(INEEndCrystalAnimation.STATE_WAKING_UP);
            this.wakeUpTicks = 0;
        }
    }
}

