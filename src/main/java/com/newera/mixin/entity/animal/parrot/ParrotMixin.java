package com.newera.mixin.entity.animal.parrot;

import com.newera.docs.entity.animation.INEAnimal;
import com.newera.docs.entity.animation.INEParrotAnimation;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.parrot.Parrot;
import net.minecraft.world.entity.animal.parrot.ShoulderRidingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Parrot.class)
public abstract class ParrotMixin extends ShoulderRidingEntity implements INEParrotAnimation, INEAnimal {

    @Unique
    private static final EntityDataAccessor<Byte> NEWERA_BODY_STATE = SynchedEntityData.defineId(Parrot.class, EntityDataSerializers.BYTE);

    @Unique private int busyTimer = 0;
    @Unique private int onGroundTimer = 0;
    @Unique private int inertiaTimer = 0;
    @Unique private boolean wasInSittingPose = false;
    @Unique private boolean wasDancing = false;
    @Unique private boolean wasSleeping = false;
    @Unique public int flapTimer = 0;

    @Unique public final AnimationState idleGroundAnimationState = new AnimationState();
    @Unique public final AnimationState groundToFlyAnimationState = new AnimationState();
    @Unique public final AnimationState flyToGroundAnimationState = new AnimationState();
    @Unique public final AnimationState idleFlyAnimationState = new AnimationState();
    @Unique public final AnimationState flyFlapAnimationState = new AnimationState();
    @Unique public final AnimationState glideFlapAnimationState = new AnimationState();

    @Unique public final AnimationState walkAnimationState = new AnimationState();
    @Unique public final AnimationState sitAnimationState = new AnimationState();
    @Unique public final AnimationState sittingAnimationState = new AnimationState();
    @Unique public final AnimationState standAnimationState = new AnimationState();
    @Unique public final AnimationState sleepAnimationState = new AnimationState();
    @Unique public final AnimationState sleepingAnimationState = new AnimationState();
    @Unique public final AnimationState wakeUpAnimationState = new AnimationState();
    @Unique public final AnimationState danceAnimationState = new AnimationState();

    protected ParrotMixin(EntityType<? extends ShoulderRidingEntity> type, Level level) {
        super(type, level);
    }

    @Inject(method = "defineSynchedData", at = @At("TAIL"))
    protected void defineSynchedDataInject(SynchedEntityData.Builder builder, CallbackInfo ci) {
        builder.define(NEWERA_BODY_STATE, (byte) 0);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        super.onSyncedDataUpdated(key);
        Parrot parrot = (Parrot)(Object)this;
        if (NEWERA_BODY_STATE.equals(key)) {
            parrot.refreshDimensions();
        }
    }

    @Unique
    private String getStateName(byte state) {
        switch (state) {
            case 0: return "GROUND";
            case 1: return "SIT_TO_SITTING";
            case 2: return "SITTING";
            case 3: return "SLEEP_TO_SLEEPING";
            case 4: return "SLEEPING";
            case 5: return "SLEEP_TO_STAND";
            case 6: return "SIT_TO_STAND";
            case 7: return "GROUND_TO_FLY";
            case 8: return "FLY";
            case 9: return "FLY_TO_GROUND";
            case 10: return "DANCE";
            case 11: return "FLY_TO_GLIDE";
            case 12: return "GLIDE";
            case 13: return "GLIDE_TO_FLY";
            default: return "UNKNOWN (" + state + ")";
        }
    }

    @Override
    public void setBodyState(byte state) {
        Parrot parrot = (Parrot)(Object)this;
        byte oldState = this.getBodyState();
        if (oldState == state) return;

        parrot.getEntityData().set(NEWERA_BODY_STATE, state);

        if (state == 1 || state == 3 || state == 5 || state == 6) {
            this.busyTimer = 5;
        } else if (state == 7 || state == 9 || state == 11) {
            this.busyTimer = 10;
        } else if (state == 13) {
            this.busyTimer = 15;
        }

        if (oldState != state) {
            parrot.refreshDimensions();
        }
    }

    @Override
    public byte getBodyState() {
        Parrot parrot = (Parrot)(Object)this;
        return parrot.getEntityData().get(NEWERA_BODY_STATE);
    }

    @Unique
    private void setupAnimationStates() {
        Parrot parrot = (Parrot)(Object)this;
        byte state = this.getBodyState();

        if (parrot.level().isClientSide() && parrot.isPartyParrot()) {
            state = 10;
        }

        if (state == 0) {
            this.idleGroundAnimationState.startIfStopped(parrot.tickCount);
            this.groundToFlyAnimationState.stop();
            this.flyToGroundAnimationState.stop();
            this.idleFlyAnimationState.stop();
            this.flyFlapAnimationState.stop();
            this.glideFlapAnimationState.stop();

            this.sitAnimationState.stop();
            this.sittingAnimationState.stop();
            this.standAnimationState.stop();
            this.sleepAnimationState.stop();
            this.sleepingAnimationState.stop();
            this.wakeUpAnimationState.stop();
            this.danceAnimationState.stop();

            if (parrot.getDeltaMovement().horizontalDistanceSqr() > 0.0001D) {
                this.walkAnimationState.startIfStopped(parrot.tickCount);
            } else {
                this.walkAnimationState.stop();
            }
        } else if (state == 7) {
            this.idleGroundAnimationState.stop();
            this.groundToFlyAnimationState.startIfStopped(parrot.tickCount);
            this.idleFlyAnimationState.startIfStopped(parrot.tickCount);
            this.flyToGroundAnimationState.stop();
            this.walkAnimationState.stop();
            this.glideFlapAnimationState.stop();

            this.flyFlapAnimationState.startIfStopped(parrot.tickCount);
            this.danceAnimationState.stop();

        } else if (state == 8) {
            this.idleGroundAnimationState.stop();
            this.groundToFlyAnimationState.stop();
            this.flyToGroundAnimationState.stop();
            this.walkAnimationState.stop();
            this.danceAnimationState.stop();

            this.idleFlyAnimationState.startIfStopped(parrot.tickCount);

            if (this.flapTimer > 0) {
                this.flyFlapAnimationState.startIfStopped(parrot.tickCount);
                this.glideFlapAnimationState.stop();
            } else {
                this.flyFlapAnimationState.stop();
                this.glideFlapAnimationState.startIfStopped(parrot.tickCount);
            }

        } else if (state == 9) {
            this.idleGroundAnimationState.stop();
            this.groundToFlyAnimationState.stop();
            this.flyToGroundAnimationState.startIfStopped(parrot.tickCount);
            this.idleFlyAnimationState.startIfStopped(parrot.tickCount);
            this.walkAnimationState.stop();
            this.glideFlapAnimationState.stop();

            this.flyFlapAnimationState.startIfStopped(parrot.tickCount);
            this.danceAnimationState.stop();

        } else if (state == 1 || state == 2 || state == 6) {
            this.idleGroundAnimationState.stop();
            this.walkAnimationState.stop();
            this.danceAnimationState.stop();
            if (state == 1) this.sitAnimationState.startIfStopped(parrot.tickCount);
            else if (state == 6) this.standAnimationState.startIfStopped(parrot.tickCount);
            else {
                this.sitAnimationState.stop();
                this.standAnimationState.stop();
                this.sittingAnimationState.startIfStopped(parrot.tickCount);
            }
        } else if (state == 3 || state == 4 || state == 5) {
            this.idleGroundAnimationState.stop();
            this.walkAnimationState.stop();
            this.danceAnimationState.stop();
            if (state == 3) this.sleepAnimationState.startIfStopped(parrot.tickCount);
            else if (state == 5) this.wakeUpAnimationState.startIfStopped(parrot.tickCount);
            else {
                this.sleepAnimationState.stop();
                this.wakeUpAnimationState.stop();
                this.sleepingAnimationState.startIfStopped(parrot.tickCount);
            }
        } else if (state == 10) {
            this.idleGroundAnimationState.startIfStopped(parrot.tickCount);
            this.walkAnimationState.stop();
            this.sitAnimationState.stop();
            this.sittingAnimationState.stop();
            this.standAnimationState.stop();
            this.sleepAnimationState.stop();
            this.sleepingAnimationState.stop();
            this.wakeUpAnimationState.stop();
            this.danceAnimationState.startIfStopped(parrot.tickCount);
        }
    }

    @Inject(method = "aiStep", at = @At("TAIL"))
    public void aiStepInject(CallbackInfo ci) {
        byte state = this.getBodyState();
        Parrot parrot = (Parrot)(Object)this;

        if (parrot.level().isClientSide()) {
            this.setupAnimationStates();

        } else {
            boolean isSitting = parrot.isOrderedToSit() || parrot.isInSittingPose();
            boolean isDancing = parrot.isPartyParrot();
            boolean isSleeping = false;
            boolean isOnGround = parrot.onGround();

            if (parrot.isTame()) {
                net.minecraft.world.entity.LivingEntity owner = parrot.getOwner();
                if (owner instanceof net.minecraft.world.entity.player.Player player) {
                    isSleeping = !isSitting && player.isSleeping();
                }
            } else if (parrot.level().isDarkOutside() && state != 8 && state != 12 && !isDancing) {
                isSleeping = true;
            }

            boolean sittingChanged = isSitting != this.wasInSittingPose;
            boolean dancingChanged = isDancing != this.wasDancing;
            boolean sleepingChanged = isSleeping != this.wasSleeping;
            if (sittingChanged) {
                if (isSitting) this.setBodyState((byte) 1);
                else if (state != 0) this.setBodyState((byte) 6);
            } else if (sleepingChanged) {
                if (isSleeping) this.setBodyState((byte) 3);
                else if (state != 0 && state != 6) this.setBodyState((byte) 5);
            } else if (dancingChanged) {
                if (isDancing) this.setBodyState((byte) 10);
                else this.setBodyState((byte) 0);
            } else if (isSitting) {
                if (state == 0) this.setBodyState((byte) 2);
            } else if (isSleeping) {
                if (state == 0) this.setBodyState((byte) 4);
            } else if (isDancing) {
                if (state == 0) this.setBodyState((byte) 10);
            }

            if (this.flapTimer > 0) {
                this.flapTimer--;
            }

            if (!isSitting && !isSleeping && !isDancing) {
                if (parrot.onGround()) {
                    if (state == 8) {
                        this.setBodyState((byte) 9);
                        this.busyTimer = 4;
                    } else if (state == 0) {
                        boolean wantsToFly = false;
                        net.minecraft.core.BlockPos targetPos = null;
                        if (parrot.getNavigation().getPath() != null) {
                            targetPos = parrot.getNavigation().getPath().getTarget();
                        } else if (parrot.getTarget() != null) {
                            targetPos = parrot.getTarget().blockPosition();
                        }

                        if (targetPos != null && targetPos.getY() > parrot.getY()) {
                            wantsToFly = true;
                        } else if (parrot.getDeltaMovement().y > 0.1) {
                            wantsToFly = true;
                        }

                        if (wantsToFly) {
                            this.setBodyState((byte) 7);
                            this.flapTimer = 60;
                            this.busyTimer = 4;

                            double jumpX = 0, jumpZ = 0;
                            if (targetPos != null) {
                                Vec3 dir = new Vec3(targetPos.getX() + 0.5, parrot.getY(), targetPos.getZ() + 0.5).subtract(parrot.position());
                                double hDist = Math.sqrt(dir.x * dir.x + dir.z * dir.z);
                                if (hDist > 0) {
                                    dir = dir.normalize();
                                    jumpX = dir.x * 0.25;
                                    jumpZ = dir.z * 0.25;
                                }
                            }
                            parrot.setDeltaMovement(new Vec3(jumpX, 0.4, jumpZ));
                            parrot.syncVelocity = true;
                        }
                    }
                } else {
                    if (state == 0 && parrot.getDeltaMovement().y < 0.0) {
                        this.setBodyState((byte) 8);
                        this.flapTimer = 60;
                    }
                }
            }

            this.wasInSittingPose = isSitting;
            this.wasDancing = isDancing;
            this.wasSleeping = isSleeping;

            state = this.getBodyState();
            if (state > 0 && state != 2 && state != 4 && state != 8 && state != 12 && state != 10) {
                if (parrot.hurtTime > 0 || parrot.isOnFire()) {
                    this.setBodyState(parrot.onGround() ? (byte) 0 : (byte) 8);
                    this.busyTimer = 0;
                    this.inertiaTimer = 0;
                    if (!parrot.level().isClientSide() && parrot.isOrderedToSit()) {
                        parrot.setOrderedToSit(false);
                    }
                } else {
                    if (this.busyTimer > 0) {
                        this.busyTimer--;
                        if (this.busyTimer <= 0) {
                            if (state == 5 || state == 9) this.setBodyState((byte) 0);
                            else if (state == 6) this.setBodyState((byte) 0);
                            else if (state == 1) this.setBodyState((byte) 2);
                            else if (state == 3) this.setBodyState((byte) 4);
                            else if (state == 7 || state == 13) this.setBodyState((byte) 8);
                            else if (state == 11) this.setBodyState((byte) 12);
                        }
                    }
                }
            }

            if (state == 8 || state == 7 || state == 9) {
                parrot.getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.FLYING_SPEED).setBaseValue(1.5D);
            } else {
                parrot.getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.FLYING_SPEED).setBaseValue(0.4D);
            }
        }
    }

    @Override public AnimationState getIdleGroundAnimationState() { return this.idleGroundAnimationState; }
    @Override public AnimationState getGroundToFlyAnimationState() { return this.groundToFlyAnimationState; }
    @Override public AnimationState getFlyToGroundAnimationState() { return this.flyToGroundAnimationState; }
    @Override public AnimationState getIdleFlyAnimationState() { return this.idleFlyAnimationState; }

    @Override public AnimationState getFlyFlapAnimationState() { return this.flyFlapAnimationState; }
    @Override public AnimationState getGlideFlapAnimationState() { return this.glideFlapAnimationState; }
    @Override public AnimationState getWalkAnimationState() { return this.walkAnimationState; }
    @Override public AnimationState getSitAnimationState() { return this.sitAnimationState; }
    @Override public AnimationState getSittingAnimationState() { return this.sittingAnimationState; }
    @Override public AnimationState getStandAnimationState() { return this.standAnimationState; }
    @Override public AnimationState getSleepAnimationState() { return this.sleepAnimationState; }
    @Override public AnimationState getSleepingAnimationState() { return this.sleepingAnimationState; }
    @Override public AnimationState getWakeUpAnimationState() { return this.wakeUpAnimationState; }
    @Override public AnimationState getDanceAnimationState() { return this.danceAnimationState; }

    @Override
    public void travel(Vec3 travelVector) {
        byte state = this.getBodyState();
        Parrot parrot = (Parrot)(Object)this;

        super.travel(travelVector);

        if (state == 8) {
            boolean nearOwner = false;
            boolean followingOwner = false;
            if (parrot.isTame() && parrot.getOwner() != null) {
                if (parrot.distanceToSqr(parrot.getOwner()) < 16.0) {
                    nearOwner = true;
                }
                if (parrot.getNavigation().isInProgress()) {
                    followingOwner = true;
                }
            }

            if (!followingOwner) {
                parrot.setYRot(parrot.getYRot() + 2.5F);
                parrot.yBodyRot = parrot.getYRot();

                Vec3 forward = Vec3.directionFromRotation(0, parrot.getYRot()).normalize().scale(0.2);
                Vec3 delta = parrot.getDeltaMovement();
                double newY = Math.max(delta.y, -0.025D);
                parrot.setDeltaMovement(forward.x, newY, forward.z);
            } else {

                Vec3 delta = parrot.getDeltaMovement();
                double newY;
                if (nearOwner) {
                    newY = delta.y - 0.05D;
                } else {
                    newY = Math.max(delta.y, -0.025D);
                }
                parrot.setDeltaMovement(delta.x, newY, delta.z);
            }

        } else if (state == 7 || state == 9) {

            Vec3 delta = parrot.getDeltaMovement();
            double newY = Math.max(delta.y, -0.025D);
            parrot.setDeltaMovement(delta.x, newY, delta.z);

        } else if (state == 1 || state == 6 || (state >= 3 && state <= 5)) {

            Vec3 delta = parrot.getDeltaMovement();
            parrot.setDeltaMovement(0, Math.min(delta.y, 0), 0);
        }
    }
}

