package com.newera.docs.entity.ai.goal;

import com.newera.docs.entity.animation.INEAnimal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Animal;

import java.util.EnumSet;

public class CatSleepGoal extends Goal {
    private final Animal animal;
    private final int baseSleepTicks;
    private final int randomSleepTicks;

    private Phase currentPhase = Phase.AWAKE;
    private int stateTimer;
    private long currentDay = -1;
    private int dailySleepTicksLeft = 0;

    public enum Phase {
        AWAKE, SLEEP, SLEEPING_HOLD, WAKE_UP
    }

    public CatSleepGoal(Animal animal, int baseSleepTicks, int randomSleepTicks) {
        this.animal = animal;
        this.baseSleepTicks = baseSleepTicks;
        this.randomSleepTicks = randomSleepTicks;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP));
    }

    private void checkAndResetDailyLimits() {
        long today = this.animal.level().getDefaultClockTime() / 24000L;
        if (this.currentDay != today) {
            this.currentDay = today;
            this.dailySleepTicksLeft = this.baseSleepTicks + this.animal.getRandom().nextInt(this.randomSleepTicks);
        }
    }

    @Override
    public boolean canUse() {
        if (this.animal instanceof net.minecraft.world.entity.TamableAnimal tamable && tamable.isTame()) {
            return false;
        }

        if (this.animal.isInWater() || this.animal.getLastHurtByMob() != null || this.currentPhase != Phase.AWAKE || this.animal.isVehicle()) {
            return false;
        }

        this.checkAndResetDailyLimits();

        if (this.dailySleepTicksLeft <= 0) {
            return false;
        }

        long currentTicks = this.animal.level().getDefaultClockTime();
        long timeOfDay = currentTicks % 24000L;

        if (timeOfDay < 13000L || timeOfDay >= 23000L) {
            return false;
        }
        if (this.animal.getRandom().nextInt(40) != 0) {
            return false;
        }

        return true;
    }

    @Override
    public boolean canContinueToUse() {
        if (this.currentPhase == Phase.AWAKE || this.animal.isInWater() || this.animal.getLastHurtByMob() != null || this.animal.isVehicle()) {
            return false;
        }
        return this.dailySleepTicksLeft > 0;
    }

    @Override
    public void start() {
        this.transitionTo(Phase.SLEEP);
    }

    @Override
    public void stop() {
        if (this.animal instanceof INEAnimal neAnimal) {
            byte currentState = neAnimal.getBodyState();
            if (currentState >= 3 && currentState <= 4) {
                neAnimal.setBodyState((byte) 5);
            }
        }
        this.currentPhase = Phase.AWAKE;
    }

    @Override
    public void tick() {
        if (this.currentPhase == Phase.SLEEPING_HOLD) {
            this.dailySleepTicksLeft--;
        }

        this.animal.getNavigation().stop();

        switch (this.currentPhase) {
            case SLEEP:
                this.stateTimer--;
                if (this.stateTimer <= 0) {
                    this.transitionTo(Phase.SLEEPING_HOLD);
                }
                break;
            case SLEEPING_HOLD:
                if (this.dailySleepTicksLeft <= 0) {
                    this.transitionTo(Phase.WAKE_UP);
                }
                break;
            case WAKE_UP:
                this.stateTimer--;
                if (this.stateTimer <= 0) {
                    this.transitionTo(Phase.AWAKE);
                }
                break;
            case AWAKE:
                break;
        }
    }

    private void transitionTo(Phase nextPhase) {
        this.currentPhase = nextPhase;

        if (this.animal instanceof INEAnimal neAnimal) {
            switch (nextPhase) {
                case SLEEP -> {
                    this.stateTimer = 20;
                    neAnimal.setBodyState((byte) 3);
                }
                case SLEEPING_HOLD -> {
                    neAnimal.setBodyState((byte) 4);
                }
                case WAKE_UP -> {
                    this.stateTimer = 20;
                    neAnimal.setBodyState((byte) 5);
                }
                case AWAKE -> {
                    neAnimal.setBodyState((byte) 0);
                }
            }
        }
    }
}

