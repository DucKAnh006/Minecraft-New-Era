package com.newera.mixin.entity.animal.armadillo;

import com.newera.docs.entity.animation.INEAnimal;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.armadillo.Armadillo;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Armadillo.class)
public abstract class ArmadilloMixin extends Animal implements INEAnimal {

    @Shadow public abstract Armadillo.ArmadilloState getState();
    @Shadow public abstract void switchToState(Armadillo.ArmadilloState state);
    @Shadow public abstract void rollUp();
    @Shadow public abstract void rollOut();
    @Shadow public abstract boolean shouldSwitchToScaredState();
    @Shadow public abstract boolean isScared();
    @Shadow public abstract boolean isScaredBy(LivingEntity entity);

    @Unique
    private boolean neIsSleeping = false;

    @Unique
    private int neSleepPhase = 0;

    @Unique
    private int nePeekTimer = 0;

    @Unique
    private int neUnrollTimer = 0;

    @Unique
    private long neLastDayChecked = -1;

    @Unique
    private int neSleepStartTick = 1500;

    @Unique
    private int neSleepDuration = 16000;

    protected ArmadilloMixin(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public boolean isArmadilloSleeping() {
        return this.neIsSleeping;
    }

    @Override
    protected boolean isImmobile() {
        return this.neIsSleeping || super.isImmobile();
    }

    @Inject(method = "rollOut", at = @At("HEAD"), cancellable = true)
    private void preventRollOutWhileSleeping(CallbackInfo ci) {
        if (this.neIsSleeping && this.neSleepPhase != 0) {
            ci.cancel();
        }
    }

    @Inject(method = "actuallyHurt", at = @At("HEAD"))
    private void onActuallyHurt(ServerLevel level, DamageSource damageSource, float amount, CallbackInfo ci) {
        if (this.neIsSleeping) {
            if (this.isInLiquid()) {
                this.neIsSleeping = false;
                this.neSleepPhase = 0;
                this.rollOut();
            } else {
                this.neSleepPhase = 3;
                this.nePeekTimer = 40;
                this.level().broadcastEntityEvent(this, (byte) 64);
            }
        }
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void onTick(CallbackInfo ci) {
        if (this.level().isClientSide()) {
            return;
        }

        if (this.neIsSleeping && this.isInLiquid()) {
            this.neIsSleeping = false;
            this.neSleepPhase = 0;
            this.rollOut();
            return;
        }

        boolean shouldSleep = this.isTimeToSleep();

        if (this.isBaby()) {
            this.tickBabySleep(shouldSleep);
        } else {
            this.tickAdultSleep(shouldSleep);
        }
    }

    @Unique
    private void checkDailySleepSchedule() {
        long currentTicks = this.level().getDefaultClockTime();
        long currentDay = currentTicks / 24000L;
        if (this.neLastDayChecked != currentDay) {
            this.neLastDayChecked = currentDay;
            this.neSleepStartTick = 1000 + this.getRandom().nextInt(1500);
            this.neSleepDuration = 15000 + this.getRandom().nextInt(2000);
        }
    }

    @Unique
    private boolean isTimeToSleep() {
        this.checkDailySleepSchedule();
        long timeOfDay = this.level().getDefaultClockTime() % 24000L;
        long sleepEndTick = (this.neSleepStartTick + this.neSleepDuration) % 24000L;
        if (this.neSleepStartTick <= sleepEndTick) {
            return timeOfDay >= this.neSleepStartTick && timeOfDay < sleepEndTick;
        } else {
            return timeOfDay >= this.neSleepStartTick || timeOfDay < sleepEndTick;
        }
    }

    @Unique
    private void tickAdultSleep(boolean shouldSleep) {
        if (shouldSleep) {
            if (!this.neIsSleeping && this.neSleepPhase == 0) {
                if (this.onGround() && !this.isInLiquid() && !this.isPanicking()) {
                    this.neIsSleeping = true;
                    this.neSleepPhase = 1;
                    this.rollUp();
                }
            } else if (this.neIsSleeping) {
                this.handleSleeping(true);
            }
        } else {
            if (this.neIsSleeping) {
                this.handleSleeping(false);
            }
        }
    }

    @Unique
    private void tickBabySleep(boolean shouldSleep) {
        Armadillo adult = this.findAdult();
        boolean adultSleeping = false;
        if (adult instanceof INEAnimal neAdult) {
            adultSleeping = neAdult.isArmadilloSleeping();
        }

        if (adultSleeping) {
            if (!this.neIsSleeping && this.neSleepPhase == 0) {
                if (this.onGround() && !this.isInLiquid() && !this.isPanicking()) {
                    this.neIsSleeping = true;
                    this.neSleepPhase = 1;
                    this.rollUp();
                }
            } else if (this.neIsSleeping) {
                this.handleSleeping(true);
            }
        } else {
            if (this.neIsSleeping) {
                this.handleSleeping(false);
            }
        }
    }

    @Unique
    private void handleSleeping(boolean shouldStayAsleep) {
        if (this.neSleepPhase == 1) {
            if (this.getState() == Armadillo.ArmadilloState.ROLLING) {
                if (this.shouldSwitchToScaredState()) {
                    this.switchToState(Armadillo.ArmadilloState.SCARED);
                }
            } else if (this.getState() == Armadillo.ArmadilloState.IDLE) {
                this.switchToState(Armadillo.ArmadilloState.SCARED);
            }

            this.getNavigation().stop();

            if (!shouldStayAsleep) {
                this.neSleepPhase = 2;
                this.nePeekTimer = 40;
                this.level().broadcastEntityEvent(this, (byte) 64);
            }
        } else if (this.neSleepPhase == 2) {
            this.nePeekTimer--;
            if (this.nePeekTimer <= 0) {
                boolean hasThreat = !this.level().getEntitiesOfClass(
                        LivingEntity.class,
                        this.getBoundingBox().inflate(7.0, 3.0, 7.0),
                        this::isScaredBy
                ).isEmpty();

                if (hasThreat) {
                    this.neIsSleeping = false;
                    this.neSleepPhase = 0;
                    this.switchToState(Armadillo.ArmadilloState.SCARED);
                } else {
                    this.neSleepPhase = 4;
                    this.neUnrollTimer = Armadillo.ArmadilloState.UNROLLING.animationDuration();
                    this.makeSound(SoundEvents.ARMADILLO_UNROLL_START);
                    this.switchToState(Armadillo.ArmadilloState.UNROLLING);
                }
            }
        } else if (this.neSleepPhase == 3) {
            this.nePeekTimer--;
            if (this.nePeekTimer <= 0) {
                boolean hasThreat = !this.level().getEntitiesOfClass(
                        LivingEntity.class,
                        this.getBoundingBox().inflate(7.0, 3.0, 7.0),
                        this::isScaredBy
                ).isEmpty();

                if (shouldStayAsleep) {
                    if (hasThreat) {
                        this.nePeekTimer = 20;
                    } else {
                        this.neSleepPhase = 1;
                    }
                } else {
                    if (hasThreat) {
                        this.neIsSleeping = false;
                        this.neSleepPhase = 0;
                        this.switchToState(Armadillo.ArmadilloState.SCARED);
                    } else {
                        this.neSleepPhase = 4;
                        this.neUnrollTimer = Armadillo.ArmadilloState.UNROLLING.animationDuration();
                        this.makeSound(SoundEvents.ARMADILLO_UNROLL_START);
                        this.switchToState(Armadillo.ArmadilloState.UNROLLING);
                    }
                }
            }
        } else if (this.neSleepPhase == 4) {
            this.neUnrollTimer--;
            if (this.neUnrollTimer <= 0) {
                this.neIsSleeping = false;
                this.neSleepPhase = 0;
                this.rollOut();
            }
        }
    }

    @Unique
    private Armadillo findAdult() {
        if (this.getNEParentUUID() != null && this.level() instanceof ServerLevel serverLevel) {
            Entity parent = serverLevel.getEntity(this.getNEParentUUID());
            if (parent instanceof Armadillo adult && adult.isAlive() && !adult.isBaby()) {
                return adult;
            }
        }
        List<Armadillo> list = this.level().getEntitiesOfClass(
                Armadillo.class,
                this.getBoundingBox().inflate(16.0, 4.0, 16.0)
        );
        Armadillo closest = null;
        double minDistance = Double.MAX_VALUE;
        for (Armadillo friend : list) {
            if (friend != (Object)this && !friend.isBaby() && friend.isAlive()) {
                double dist = this.distanceToSqr(friend);
                if (dist < minDistance) {
                    minDistance = dist;
                    closest = friend;
                }
            }
        }
        return closest;
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    public void addAdditionalSaveDataInject(ValueOutput output, CallbackInfo ci) {
        try {
            output.putBoolean("NEIsSleeping", this.neIsSleeping);
            output.putInt("NESleepPhase", this.neSleepPhase);
            output.putInt("NEUnrollTimer", this.neUnrollTimer);
            output.putLong("NELastDayChecked", this.neLastDayChecked);
            output.putInt("NESleepStartTick", this.neSleepStartTick);
            output.putInt("NESleepDuration", this.neSleepDuration);
        } catch (Exception ignored) {
        }
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    public void readAdditionalSaveDataInject(ValueInput input, CallbackInfo ci) {
        try {
            this.neIsSleeping = input.getBooleanOr("NEIsSleeping", false);
            this.neSleepPhase = input.getIntOr("NESleepPhase", 0);
            this.neUnrollTimer = input.getIntOr("NEUnrollTimer", 0);
            this.neLastDayChecked = input.getLongOr("NELastDayChecked", -1L);
            this.neSleepStartTick = input.getIntOr("NESleepStartTick", 1500);
            this.neSleepDuration = input.getIntOr("NESleepDuration", 16000);
        } catch (Exception ignored) {
        }
    }
}
