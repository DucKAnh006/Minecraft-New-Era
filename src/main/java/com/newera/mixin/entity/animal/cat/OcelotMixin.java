package com.newera.mixin.entity.animal.cat;

import com.newera.docs.entity.ai.goal.*;
import com.newera.docs.entity.animation.INECatAnimation;
import com.newera.docs.entity.animation.INEAnimal;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.feline.Ocelot;
import net.minecraft.world.entity.animal.chicken.Chicken;
import net.minecraft.world.entity.animal.parrot.Parrot;
import net.minecraft.world.entity.animal.rabbit.Rabbit;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Ocelot.class)
public abstract class OcelotMixin extends Animal implements INECatAnimation, INEAnimal {

    @Unique
    private static final EntityDataAccessor<Byte> NEWERA_BODY_STATE = SynchedEntityData.defineId(Ocelot.class, EntityDataSerializers.BYTE);

    @Unique
    private int busyTimer = 0;

    @Unique private float lookWeight = 1.0F;
    @Unique private float lookWeightO = 1.0F;
    @Unique private boolean isBodyLocked = false;
    @Unique private float lockedBodyYaw = 0.0F;

    @Unique public final AnimationState sitAnimationState = new AnimationState();
    @Unique public final AnimationState sittingHoldAnimationState = new AnimationState();
    @Unique public final AnimationState sleepAnimationState = new AnimationState();
    @Unique public final AnimationState sleepingHoldAnimationState = new AnimationState();
    @Unique public final AnimationState wakeUpAnimationState = new AnimationState();
    @Unique public final AnimationState standAnimationState = new AnimationState();

    @Unique public final AnimationState stretchAnimationState = new AnimationState();
    @Unique public final AnimationState stalkingAnimationState = new AnimationState();
    @Unique public final AnimationState pounceAnimationState = new AnimationState();

    @Unique public final AnimationState idleAnimationState = new AnimationState();

    @Unique
    private AnimalSpeed animalSpeed;

    protected OcelotMixin(EntityType<? extends Animal> type, Level level) {
        super(type, level);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void initSpeed(EntityType<? extends Ocelot> type, Level level, CallbackInfo ci) {
        this.animalSpeed = new AnimalSpeed(this, 0.30D, 0.38D, 18);
    }

    @Inject(method = "defineSynchedData", at = @At("TAIL"))
    protected void defineSynchedDataInject(SynchedEntityData.Builder builder, CallbackInfo ci) {
        builder.define(NEWERA_BODY_STATE, (byte) 0);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        super.onSyncedDataUpdated(key);
        if (NEWERA_BODY_STATE.equals(key)) {
            this.refreshDimensions();
        }
    }

    @Inject(method = "registerGoals", at = @At("TAIL"))
    protected void addCustomGoals(CallbackInfo ci) {
        this.goalSelector.addGoal(5, new net.minecraft.world.entity.ai.goal.FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(8, new AnimalSitGoal(this, true, 4000, 2000));
        this.goalSelector.addGoal(9, new CatSleepGoal(this, 12000, 2000));
        this.goalSelector.addGoal(10, new CatStretchGoal(this));

        this.targetSelector.addGoal(1, new net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal<>(this, Rabbit.class, false));
        this.targetSelector.addGoal(1, new net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal<>(this, Chicken.class, false));
        this.targetSelector.addGoal(1, new net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal<>(this, Parrot.class, false));
        this.goalSelector.addGoal(1, new CatStalkPreyGoal(this, 0.6D, 1.3D));
    }

    @Override
    public void setBodyState(byte state) {
        byte oldState = this.getBodyState();
        this.entityData.set(NEWERA_BODY_STATE, state);

        if (state == 1) {
            this.busyTimer = 20;
        } else if (state == 3 || state == 5) {
            this.busyTimer = 10;
        } else if (state == 6) {
            this.busyTimer = 20;
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
    public float getLookWeight() {
        return this.lookWeight;
    }

    @Override
    public void tick() {
        super.tick();
        byte state = this.getBodyState();

        if (state > 0 && state != 8) {
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
            float targetWeight = (state >= 3 && state <= 5) ? 0.0F : 1.0F;
            this.lookWeight = Mth.approach(this.lookWeight, targetWeight, 0.1F);
            this.setupAnimationStates();
        } else {
            if (this.isCrouching() && this.getDeltaMovement().horizontalDistanceSqr() > 0.0001D) {
                if (state != 8 && state != 11) this.setBodyState((byte) 8);
            } else if (state == 8) {
                this.setBodyState((byte) 0);
            }

            state = this.getBodyState();

            if (state > 0 && state != 11 && state != 8) {
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
                    } else if (state == 1) {
                        this.busyTimer--;
                        if (this.busyTimer <= 0) {
                            this.setBodyState((byte) 2);
                        }
                    } else if (state == 3) {
                        this.busyTimer--;
                        if (this.busyTimer <= 0) {
                            this.setBodyState((byte) 4);
                        }
                    }
                }
            }
        }

        if (!this.level().isClientSide() && this.animalSpeed != null) {
            boolean isPanicking = this.getLastHurtByMob() != null || this.isOnFire() || this.hurtTime > 0;
            if (state > 0 && state != 11 && state != 8) {
                this.animalSpeed.forceStop();
            } else if (isPanicking) {
                this.animalSpeed.run();
            } else if (this.getNavigation().isDone() || this.getNavigation().getPath() == null) {
                this.animalSpeed.stop();
            } else {
                this.animalSpeed.walk();
            }
            this.animalSpeed.tick();
        }
    }

    @Override
    protected boolean isImmobile() {
        if (this.getBodyState() > 0 && this.getBodyState() != 11 && this.getBodyState() != 8) {
            return true;
        }
        return super.isImmobile();
    }

    @Unique
    private void setupAnimationStates() {
        byte state = this.getBodyState();

        this.idleAnimationState.startIfStopped(this.tickCount);

        if (state == 1) this.sitAnimationState.startIfStopped(this.tickCount);
        else this.sitAnimationState.stop();

        if (state == 2) this.sittingHoldAnimationState.startIfStopped(this.tickCount);
        else this.sittingHoldAnimationState.stop();

        if (state == 3) this.sleepAnimationState.startIfStopped(this.tickCount);
        else this.sleepAnimationState.stop();

        if (state == 4) this.sleepingHoldAnimationState.startIfStopped(this.tickCount);
        else this.sleepingHoldAnimationState.stop();

        if (state == 5) this.wakeUpAnimationState.startIfStopped(this.tickCount);
        else this.wakeUpAnimationState.stop();

        if (state == 6) this.standAnimationState.startIfStopped(this.tickCount);
        else this.standAnimationState.stop();

        if (state == 7) this.stretchAnimationState.startIfStopped(this.tickCount);
        else this.stretchAnimationState.stop();

        if (state == 8) this.stalkingAnimationState.startIfStopped(this.tickCount);
        else this.stalkingAnimationState.stop();

        if (state == 11) this.pounceAnimationState.startIfStopped(this.tickCount);
        else this.pounceAnimationState.stop();
    }

    @Override public AnimationState getSitAnimationState() { return this.sitAnimationState; }
    @Override public AnimationState getSittingHoldAnimationState() { return this.sittingHoldAnimationState; }
    @Override public AnimationState getSleepAnimationState() { return this.sleepAnimationState; }
    @Override public AnimationState getSleepingHoldAnimationState() { return this.sleepingHoldAnimationState; }
    @Override public AnimationState getWakeUpAnimationState() { return this.wakeUpAnimationState; }
    @Override public AnimationState getStandAnimationState() { return this.standAnimationState; }
    @Override public AnimationState getStretchAnimationState() { return this.stretchAnimationState; }
    @Override public AnimationState getStalkingAnimationState() { return this.stalkingAnimationState; }
    @Override public AnimationState getPounceAnimationState() { return this.pounceAnimationState; }
    @Override public AnimationState getIdleAnimationState() { return this.idleAnimationState; }
}

