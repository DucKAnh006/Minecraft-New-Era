package com.newera.docs.entity.ai.goal;

import com.newera.docs.entity.animation.INEAnimal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Animal;

import java.util.EnumSet;
import java.util.List;

public class AnimalSitGoal extends Goal {
    private final Animal animal;
    private final boolean isNE;
    private final int baseSitTicks;
    private final int randomSitTicks;

    private Phase currentPhase = Phase.AWAKE;
    private int stateTimer;
    private int cooldown;
    private int minSitTimer;

    private long currentDay = -1;
    private int dailySitTicksLeft = 0;

    public enum Phase {
        AWAKE, SITTING, SITTING_HOLD, STAND_UP
    }

    public AnimalSitGoal(Animal animal) {
        this(animal, false, 4000, 2000);
    }

    public AnimalSitGoal(Animal animal, boolean isNE, int baseSitTicks, int randomSitTicks) {
        this.animal = animal;
        this.isNE = isNE;
        this.baseSitTicks = baseSitTicks;
        this.randomSitTicks = randomSitTicks;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP));
    }

    private void checkAndResetDailyLimits() {
        long today = this.animal.level().getDefaultClockTime() / 24000L;
        if (this.currentDay != today) {
            this.currentDay = today;
            this.dailySitTicksLeft = this.baseSitTicks + this.animal.getRandom().nextInt(this.randomSitTicks);
        }
    }

    private Animal findSittingAdult() {
        if (!this.animal.level().isClientSide() && this.animal instanceof INEAnimal neAnimal) {
            java.util.UUID parentUUID = neAnimal.getNEParentUUID();
            if (parentUUID != null) {
                net.minecraft.server.level.ServerLevel serverLevel = (net.minecraft.server.level.ServerLevel) this.animal.level();
                net.minecraft.world.entity.Entity parentEntity = serverLevel.getEntity(parentUUID);

                if (parentEntity instanceof Animal adult && !adult.isBaby() && isSitting(adult)) {
                    if (this.animal.distanceToSqr(adult) < 16.0) {
                        return adult;
                    }
                }
            }
        }
        return null;
    }

    private boolean isSitting(Animal entity) {
        if (entity instanceof INEAnimal neAnimal) {
            byte state = neAnimal.getBodyState();
            return state == 1 || state == 2;
        }
        return false;
    }

    @Override
    public boolean isInterruptable() {
        return this.minSitTimer <= 0;
    }

    @Override
    public boolean canUse() {
        if (this.animal instanceof net.minecraft.world.entity.TamableAnimal tamable && tamable.isTame()) {
            return false;
        }

        if (this.animal.isInWater() || this.animal.getLastHurtByMob() != null || this.currentPhase != Phase.AWAKE || this.animal.isVehicle() || !this.animal.onGround()) {
            return false;
        }

        boolean isMoving = !this.animal.getNavigation().isDone() || this.animal.getDeltaMovement().horizontalDistanceSqr() > 0.001;
        if (isMoving) {
            this.cooldown = 10 + this.animal.getRandom().nextInt(20);
            return false;
        }

        if (this.cooldown > 0) {
            this.cooldown--;
            return false;
        }

        if (this.animal.isBaby()) {
            return findSittingAdult() != null;
        }

        this.checkAndResetDailyLimits();

        if (this.dailySitTicksLeft <= 0) {
            return false;
        }

        return this.animal.getRandom().nextInt(100) == 0;
    }

    @Override
    public boolean canContinueToUse() {
        if (this.currentPhase == Phase.AWAKE || this.animal.isInWater() || this.animal.getLastHurtByMob() != null || this.animal.isVehicle()) {
            return false;
        }

        if (this.animal instanceof net.minecraft.world.entity.TamableAnimal tamable && tamable.isOrderedToSit()) {
            return false;
        }

        if (this.animal.isBaby()) {
            return findSittingAdult() != null;
        }

        return this.dailySitTicksLeft > 0;
    }

    @Override
    public void start() {
        this.animal.getNavigation().stop();
        this.transitionTo(Phase.SITTING);
    }

    @Override
    public void stop() {
        if (this.isNE && this.animal instanceof INEAnimal neAnimal) {
            byte currentState = neAnimal.getBodyState();
            if (currentState > 0 && currentState < 6) {
                neAnimal.setBodyState((byte) 6);
            }
        }
        this.currentPhase = Phase.AWAKE;
        this.cooldown = 200 + this.animal.getRandom().nextInt(400);
    }

    @Override
    public void tick() {
        if (!this.animal.isBaby() && this.currentPhase == Phase.SITTING_HOLD) {
            this.dailySitTicksLeft--;
        }

        if (this.minSitTimer > 0) this.minSitTimer--;
        this.stateTimer--;

        if (this.stateTimer <= 0) {
            switch (this.currentPhase) {
                case SITTING -> this.transitionTo(Phase.SITTING_HOLD);
                case SITTING_HOLD -> {
                    if (this.animal.isBaby()) {
                        this.stateTimer = 20;
                    } else {
                        this.transitionTo(Phase.STAND_UP);
                    }
                }
                case STAND_UP -> this.transitionTo(Phase.AWAKE);
                default -> {}
            }
        }
    }

    private void transitionTo(Phase nextPhase) {
        this.currentPhase = nextPhase;
        boolean isSitting = nextPhase == Phase.SITTING || nextPhase == Phase.SITTING_HOLD;

        if (this.isNE && this.animal instanceof INEAnimal neAnimal) {
            if (isSitting) {
                switch (neAnimal.getBodyState()) {
                    case 0, 6 -> neAnimal.setBodyState((byte) 1);
                    case 1 -> neAnimal.setBodyState((byte) 2);
                }
            } else {
                switch (neAnimal.getBodyState()) {
                    case 2 -> neAnimal.setBodyState((byte) 6);
                    case 6 -> neAnimal.setBodyState((byte) 0);
                }
            }
            if (nextPhase == Phase.SITTING) this.stateTimer = 20;
            if (nextPhase == Phase.SITTING_HOLD) {
                int sitDuration = 1200 + this.animal.getRandom().nextInt(1200);
                if (this.dailySitTicksLeft < sitDuration) sitDuration = Math.max(10, this.dailySitTicksLeft);
                this.stateTimer = sitDuration;
                this.minSitTimer = sitDuration / 2;
            }
            if (nextPhase == Phase.STAND_UP) this.stateTimer = 40;
        }
    }
}

