package com.newera.mixin.entity.animal.chicken;

import com.newera.docs.entity.ai.goal.AnimalSitGoal;
import com.newera.docs.entity.ai.goal.AnimalSleepGoal;
import com.newera.docs.entity.ai.goal.AnimalSpeed;
import com.newera.docs.entity.ai.goal.ChickenAvoidEntityGoal;
import com.newera.docs.entity.ai.goal.ChickenFlyGoal;
import com.newera.docs.entity.animation.INEChickenAnimation;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import com.newera.docs.entity.ai.goal.ChickenPeckGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.chicken.Chicken;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Chicken.class)
public abstract class ChickenMixin extends Animal implements INEChickenAnimation {

    @Unique
    private static final EntityDataAccessor<Byte> NEWERA_BODY_STATE = SynchedEntityData.defineId(Chicken.class, EntityDataSerializers.BYTE);

    @Unique
    private int busyTimer = 0;

    @Unique
    private boolean isBodyLocked = false;
    @Unique
    private float lockedBodyYaw = 0.0F;

    @Unique
    private boolean wasOnGround = true;

    @Unique
    private int landingTimer = 0;

    @Unique
    private int takeOffTimer = 0;

    @Unique public final AnimationState sitAnimationState = new AnimationState();
    @Unique public final AnimationState sittingHoldAnimationState = new AnimationState();
    @Unique public final AnimationState sleepAnimationState = new AnimationState();
    @Unique public final AnimationState sleepingHoldAnimationState = new AnimationState();
    @Unique public final AnimationState wakeUpAnimationState = new AnimationState();
    @Unique public final AnimationState standAnimationState = new AnimationState();
    @Unique public final AnimationState grazingAnimationState = new AnimationState();
    @Unique public final AnimationState idleAnimationState = new AnimationState();

    @Unique public final AnimationState flyingAnimationState = new AnimationState();
    @Unique public final AnimationState takeOffAnimationState = new AnimationState();
    @Unique public final AnimationState onAirAnimationState = new AnimationState();
    @Unique public final AnimationState landingAnimationState = new AnimationState();

    @Unique
    private AnimalSpeed animalSpeed;

    protected ChickenMixin(EntityType<? extends Animal> type, Level level) {
        super(type, level);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void initSpeed(EntityType<? extends Animal> type, Level level, CallbackInfo ci) {
        this.animalSpeed = new AnimalSpeed(this, 0.25D, 0.34D, 18);
    }

    @Inject(method = "registerGoals", at = @At("TAIL"))
    protected void addCustomGoals(CallbackInfo ci) {
        this.goalSelector.addGoal(5, new ChickenPeckGoal((Chicken)(Object)this));
        this.goalSelector.addGoal(4, new AnimalSitGoal(this, true, 2000, 1000));
        this.goalSelector.addGoal(4, new AnimalSleepGoal(this, true, 8000, 2000, true));
        this.goalSelector.addGoal(3, new ChickenFlyGoal((Chicken)(Object)this));
        this.goalSelector.addGoal(2, new ChickenAvoidEntityGoal((Chicken)(Object)this));
    }

    @Inject(method = "defineSynchedData", at = @At("TAIL"))
    protected void injectDefineSynchedData(SynchedEntityData.Builder builder, CallbackInfo ci) {
        builder.define(NEWERA_BODY_STATE, (byte) 0);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        super.onSyncedDataUpdated(key);
        if (NEWERA_BODY_STATE.equals(key)) {
            this.refreshDimensions();
        }
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
            this.setupAnimationStates();
        } else {
            if (state > 0) {
                if (this.getLastHurtByMob() != null || this.isOnFire() || this.hurtTime > 0) {
                    this.setBodyState((byte) 0);
                    this.busyTimer = 0;
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

        if (!this.level().isClientSide() && this.animalSpeed != null) {
            boolean isPanicking = this.getLastHurtByMob() != null || this.isOnFire() || this.hurtTime > 0;
            if (state > 0) {
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

        this.wasOnGround = this.onGround();
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

        boolean inWater = this.isInWater();
        boolean notOnGround = !this.onGround();

        if (notOnGround || inWater) {
            this.flyingAnimationState.startIfStopped(this.tickCount);
        } else {
            this.flyingAnimationState.stop();
        }

        if (inWater) {
            this.takeOffAnimationState.stop();
            this.onAirAnimationState.stop();
            this.landingAnimationState.stop();
            this.landingTimer = 0;
            this.takeOffTimer = 0;
        } else {
            if (notOnGround) {
                if (this.wasOnGround) {
                    this.takeOffAnimationState.start(this.tickCount);
                    this.onAirAnimationState.stop();
                    this.takeOffTimer = 10;
                } else {
                    if (this.takeOffTimer > 0) {
                        this.takeOffTimer--;
                        if (this.takeOffTimer <= 0) {
                            this.takeOffAnimationState.stop();
                            this.onAirAnimationState.startIfStopped(this.tickCount);
                        }
                    }
                }
                this.landingAnimationState.stop();
                this.landingTimer = 0;
            } else {
                this.takeOffAnimationState.stop();
                this.onAirAnimationState.stop();
                this.takeOffTimer = 0;
                if (!this.wasOnGround) {
                    this.landingAnimationState.start(this.tickCount);
                    this.landingTimer = 13;
                }

                if (this.landingTimer > 0) {
                    this.landingTimer--;
                    if (this.landingTimer <= 0) {
                        this.landingAnimationState.stop();
                    }
                }
            }
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
    @Override public AnimationState getFlyingAnimationState() { return this.flyingAnimationState; }
    @Override public AnimationState getTakeOffAnimationState() { return this.takeOffAnimationState; }
    @Override public AnimationState getOnAirAnimationState() { return this.onAirAnimationState; }
    @Override public AnimationState getLandingAnimationState() { return this.landingAnimationState; }

    @Override
    public void handleEntityEvent(byte id) {
        super.handleEntityEvent(id);
        if (id == 10 && this.getBodyState() == 0) {
            this.grazingAnimationState.start(this.tickCount);
        }
    }
}

