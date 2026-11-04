package com.newera.mixin.entity.animal.pig;

import com.newera.docs.entity.ai.goal.AnimalSitGoal;
import com.newera.docs.entity.ai.goal.AnimalSleepGoal;
import com.newera.docs.entity.ai.goal.AnimalSpeed;
import com.newera.docs.entity.ai.goal.PigEatGoal;
import com.newera.docs.entity.animation.INEPigAnimation;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.pig.Pig;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Pig.class)
public abstract class PigMixin extends Animal implements INEPigAnimation {

    @Unique
    private static final EntityDataAccessor<Byte> NEWERA_BODY_STATE = SynchedEntityData.defineId(Pig.class, EntityDataSerializers.BYTE);

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

    @Unique public final AnimationState sitAnimationState = new AnimationState();
    @Unique public final AnimationState sittingHoldAnimationState = new AnimationState();
    @Unique public final AnimationState sleepAnimationState = new AnimationState();
    @Unique public final AnimationState sleepingHoldAnimationState = new AnimationState();
    @Unique public final AnimationState wakeUpAnimationState = new AnimationState();
    @Unique public final AnimationState standAnimationState = new AnimationState();
    @Unique public final AnimationState grazingAnimationState = new AnimationState();
    @Unique public final AnimationState idleAnimationState = new AnimationState();

    @Unique
    private AnimalSpeed animalSpeed;

    protected PigMixin(EntityType<? extends Animal> type, Level level) {
        super(type, level);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void initSpeed(EntityType<? extends Animal> type, Level level, CallbackInfo ci) {
        this.animalSpeed = new AnimalSpeed(this, 0.25D, 0.30D, 20);
    }

    @Inject(method = "defineSynchedData", at = @At("TAIL"))
    protected void defineSynchedDataInject(SynchedEntityData.Builder builder, CallbackInfo ci) {
        builder.define(NEWERA_BODY_STATE, (byte) 0);
    }

    @Inject(method = "onSyncedDataUpdated", at = @At("TAIL"))
    protected void onSyncedDataUpdatedInject(EntityDataAccessor<?> key, CallbackInfo ci) {
        if (NEWERA_BODY_STATE.equals(key)) {
            this.refreshDimensions();
        }
    }

    @Inject(method = "registerGoals", at = @At("TAIL"))
    protected void addCustomGoals(CallbackInfo ci) {
        this.goalSelector.addGoal(5, new PigEatGoal((Pig)(Object)this, 1.2D, 12));
        this.goalSelector.addGoal(4, new AnimalSitGoal(this, true, 4000, 2000));
        this.goalSelector.addGoal(4, new AnimalSleepGoal(this, true, 12000, 2000, false));
    }

    @Override
    public void setBodyState(byte state) {
        byte oldState = this.getBodyState();
        this.entityData.set(NEWERA_BODY_STATE, state);
        if (state == 5) {
            this.busyTimer = 20;
        } else if (state == 6) {
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

    @Override
    public void tick() {
        super.tick();

        byte state = this.getBodyState();

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

            this.setupAnimationStates();
        } else {
            if (state > 0) {
                if (this.getLastHurtByMob() != null || this.isOnFire()) {
                    this.setBodyState((byte) 0);
                    this.busyTimer = 0;
                } else {
                    this.getNavigation().stop();

                    if (state == 5) {
                        this.busyTimer--;
                        if (this.busyTimer <= 0) {
                            this.setBodyState((byte) 6);
                        }
                    } else if (state == 6) {
                        this.busyTimer--;
                        if (this.busyTimer <= 0) {
                            this.setBodyState((byte) 0);
                        }
                    }
                }
            }
        }

        if (!this.level().isClientSide() && this.animalSpeed != null) {
            if (this.isVehicle()) {
                net.minecraft.world.entity.ai.attributes.AttributeInstance speedAttribute = this.getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED);
                if (speedAttribute != null) {
                    if (state > 0) {
                        speedAttribute.setBaseValue(0.0D);
                    } else {
                        speedAttribute.setBaseValue(0.25D);
                    }
                }
            } else {
                if (state > 0) {
                    this.animalSpeed.stop();
                } else if (this.getNavigation().isDone() || this.getNavigation().getPath() == null) {
                    this.animalSpeed.stop();
                } else if (this.getLastHurtByMob() != null || this.isOnFire()) {
                    this.animalSpeed.run();
                } else {
                    this.animalSpeed.walk();
                }
                this.animalSpeed.tick();
            }
        }
    }

    @Unique
    private void setupAnimationStates() {
        byte state = this.getBodyState();

        this.idleAnimationState.startIfStopped(this.tickCount);

        if (state == 1) this.sitAnimationState.startIfStopped(this.tickCount);
        else this.sitAnimationState.stop();

        if (state >= 2 && state <= 5) this.sittingHoldAnimationState.startIfStopped(this.tickCount);
        else this.sittingHoldAnimationState.stop();

        if (state == 3) this.sleepAnimationState.startIfStopped(this.tickCount);
        else this.sleepAnimationState.stop();

        if (state == 4) this.sleepingHoldAnimationState.startIfStopped(this.tickCount);
        else this.sleepingHoldAnimationState.stop();

        if (state == 5) this.wakeUpAnimationState.startIfStopped(this.tickCount);
        else this.wakeUpAnimationState.stop();

        if (state == 6) this.standAnimationState.startIfStopped(this.tickCount);
        else this.standAnimationState.stop();

        if (state > 0) this.grazingAnimationState.stop();
    }

    @Override
    public void handleEntityEvent(byte id) {
        super.handleEntityEvent(id);
        if (id == 10 && this.getBodyState() == 0) {
            this.grazingAnimationState.start(this.tickCount);
        }
    }

    @Override public AnimationState getSitAnimationState() { return this.sitAnimationState; }
    @Override public AnimationState getSittingHoldAnimationState() { return this.sittingHoldAnimationState; }
    @Override public AnimationState getSleepAnimationState() { return this.sleepAnimationState; }
    @Override public AnimationState getSleepingHoldAnimationState() { return this.sleepingHoldAnimationState; }
    @Override public AnimationState getWakeUpAnimationState() { return this.wakeUpAnimationState; }
    @Override public AnimationState getStandAnimationState() { return this.standAnimationState; }
    @Override public AnimationState getGrazingAnimationState() { return this.grazingAnimationState; }
    @Override public AnimationState getIdleAnimationState() { return this.idleAnimationState; }
}
