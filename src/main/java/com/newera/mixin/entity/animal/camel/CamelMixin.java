package com.newera.mixin.entity.animal.camel;

import com.newera.docs.entity.ai.goal.AnimalSitGoal;
import com.newera.docs.entity.ai.goal.AnimalSleepGoal;
import com.newera.docs.entity.ai.goal.CamelEatCactusGoal;
import com.newera.docs.entity.animation.INECamelAnimation;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.camel.Camel;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Camel.class)
public abstract class CamelMixin extends Animal implements INECamelAnimation {

    @Unique
    private static final EntityDataAccessor<Byte> NEWERA_BODY_STATE = SynchedEntityData.defineId(Camel.class, EntityDataSerializers.BYTE);

    @Unique
    private int busyTimer = 0;

    @Unique
    private float lookWeight = 1.0F;
    @Unique
    private float lookWeightO = 1.0F;

    @Unique
    private boolean isBodyLocked = false;
    @Unique
    private float lockedBodyYaw = 0.0F;

    @Unique
    public final AnimationState entityTagssitAnimationState = new AnimationState();
    @Unique
    public final AnimationState entityTagssittingHoldAnimationState = new AnimationState();
    @Unique
    public final AnimationState entityTagssleepAnimationState = new AnimationState();
    @Unique
    public final AnimationState entityTagssleepingHoldAnimationState = new AnimationState();
    @Unique
    public final AnimationState entityTagswakeUpAnimationState = new AnimationState();
    @Unique
    public final AnimationState entityTagsstandAnimationState = new AnimationState();
    @Unique
    public final AnimationState entityTagseatingAnimationState = new AnimationState();
    @Unique
    public final AnimationState entityTagsidleAnimationState = new AnimationState();
    @Unique
    public final AnimationState entityTagsdashAnimationState = new AnimationState();

    protected CamelMixin(EntityType<? extends Animal> type, Level level) {
        super(type, level);
    }

    @Inject(method = "onSyncedDataUpdated", at = @At("TAIL"))
    private void onSyncedDataUpdatedCustom(EntityDataAccessor<?> key, CallbackInfo ci) {
        if (NEWERA_BODY_STATE.equals(key)) {
            this.refreshDimensions();
        }
    }

    @Inject(method = "registerGoals", at = @At("TAIL"))
    protected void addCustomGoals(CallbackInfo ci) {
        this.goalSelector.addGoal(1, new CamelEatCactusGoal((Camel)(Object)this, 1.2D, 16, 2));
        this.goalSelector.addGoal(2, new AnimalSleepGoal(this, true, 4000, 2000, false));
        this.goalSelector.addGoal(3, new AnimalSitGoal(this, true, 600, 400));
    }

    @Inject(method = "sitDown", at = @At("HEAD"), cancellable = true)
    public void onSitDown(CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(method = "standUp", at = @At("HEAD"), cancellable = true)
    public void onStandUp(CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(method = "defineSynchedData", at = @At("TAIL"))
    protected void defineSynchedDataCustom(SynchedEntityData.Builder builder, CallbackInfo ci) {
        builder.define(NEWERA_BODY_STATE, (byte) 0);
    }

    @Override
    public void setBodyState(byte state) {
        byte oldState = this.getBodyState();
        this.entityData.set(NEWERA_BODY_STATE, state);
        if (state == 6) {
            this.busyTimer = 40;
        }
        if (oldState != state) {
            this.refreshDimensions();
        }
    }

    @Override
    public byte getBodyState() {
        return this.entityData.get(NEWERA_BODY_STATE);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    public void tickCustom(CallbackInfo ci) {
        byte state = this.getBodyState();
        Camel camel = (Camel)(Object)this;

        if (state > 0) {
            if (!this.isBodyLocked) {
                this.lockedBodyYaw = this.yBodyRot;
                this.isBodyLocked = true;
            }
            this.yBodyRot = this.lockedBodyYaw;
            this.yBodyRotO = this.lockedBodyYaw;
        } else {
            this.isBodyLocked = false;
        }

        if (this.level().isClientSide()) {
            this.lookWeightO = this.lookWeight;

            float targetWeight = (state >= 3 && state <= 6) ? 0.0F : 1.0F;
            this.lookWeight = Mth.approach(this.lookWeight, targetWeight, 0.1F);

            this.entityTagssetupAnimationStates();
        } else {
            if (state > 0) {
                if (this.getLastHurtByMob() != null || this.isOnFire()) {
                    this.getNavigation().stop();
                    if (state < 6) {
                        this.setBodyState((byte) 6);
                    } else if (state == 6) {
                        this.busyTimer--;
                        if (this.busyTimer <= 0) {
                            this.setBodyState((byte) 0);
                        }
                    }
                } else {
                    this.getNavigation().stop();
                    if (state == 6) {
                        this.busyTimer--;
                        if (this.busyTimer <= 0) {
                            this.setBodyState((byte) 0);
                        }
                    }
                }
            }
        }
    }

    @Unique
    private void entityTagssetupAnimationStates() {
        byte state = this.getBodyState();
        if (state == 1) this.entityTagssitAnimationState.startIfStopped(this.tickCount);
        else this.entityTagssitAnimationState.stop();

        if (state >= 2 && state <= 5) this.entityTagssittingHoldAnimationState.startIfStopped(this.tickCount);
        else this.entityTagssittingHoldAnimationState.stop();

        if (state == 3) this.entityTagssleepAnimationState.startIfStopped(this.tickCount);
        else this.entityTagssleepAnimationState.stop();

        if (state == 4) this.entityTagssleepingHoldAnimationState.startIfStopped(this.tickCount);
        else this.entityTagssleepingHoldAnimationState.stop();

        if (state == 5) this.entityTagswakeUpAnimationState.startIfStopped(this.tickCount);
        else this.entityTagswakeUpAnimationState.stop();

        if (state == 6) this.entityTagsstandAnimationState.startIfStopped(this.tickCount);
        else this.entityTagsstandAnimationState.stop();

        if (state == 7) this.entityTagseatingAnimationState.startIfStopped(this.tickCount);
        else this.entityTagseatingAnimationState.stop();

        this.entityTagsidleAnimationState.startIfStopped(this.tickCount);
        Camel camel = (Camel)(Object)this;
        if (camel.isDashing()) this.entityTagsdashAnimationState.startIfStopped(this.tickCount);
        else this.entityTagsdashAnimationState.stop();
    }

    @Override
    public AnimationState getSitAnimationState() { return this.entityTagssitAnimationState; }
    @Override
    public AnimationState getSittingHoldAnimationState() { return this.entityTagssittingHoldAnimationState; }
    @Override
    public AnimationState getSleepAnimationState() { return this.entityTagssleepAnimationState; }
    @Override
    public AnimationState getSleepingHoldAnimationState() { return this.entityTagssleepingHoldAnimationState; }
    @Override
    public AnimationState getWakeUpAnimationState() { return this.entityTagswakeUpAnimationState; }
    @Override
    public AnimationState getStandAnimationState() { return this.entityTagsstandAnimationState; }
    @Override
    public AnimationState getEatingAnimationState() { return this.entityTagseatingAnimationState; }
    @Override
    public AnimationState getIdleAnimationState() { return this.entityTagsidleAnimationState; }
    @Override
    public AnimationState getDashAnimationState() { return this.entityTagsdashAnimationState; }
}

