package com.newera.docs.entity.ai.goal;

import com.newera.docs.entity.animation.INEAnimal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Animal;

import java.util.EnumSet;
import java.util.List;

public class AnimalSleepGoal extends Goal {
    private final Animal animal;
    private final boolean isNE;
    private final int baseSleepTicks;
    private final int randomSleepTicks;
    private final boolean onlySleepAtNight;

    private Phase currentPhase = Phase.AWAKE;
    private int stateTimer;
    private int timeToRecalcPath;
    private Animal herdTarget;

    private long currentDay = -1;
    private int dailySleepTicksLeft = 0;

    public enum Phase {
        AWAKE, SEEKING_HERD, SITTING, SITTING_HOLD, SLEEP, SLEEPING_HOLD, WAKE_UP, STAND_UP
    }

    public AnimalSleepGoal(Animal animal) {
        this(animal, false, 6000, 2000, false);
    }

    public AnimalSleepGoal(Animal animal, boolean isNE, int baseSleepTicks, int randomSleepTicks, boolean onlySleepAtNight) {
        this.animal = animal;
        this.isNE = isNE;
        this.baseSleepTicks = baseSleepTicks;
        this.randomSleepTicks = randomSleepTicks;
        this.onlySleepAtNight = onlySleepAtNight;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP));
    }

    private void checkAndResetDailyLimits() {
        long today = this.animal.level().getDefaultClockTime() / 24000L;
        if (this.currentDay != today) {
            this.currentDay = today;
            this.dailySleepTicksLeft = this.baseSleepTicks + this.animal.getRandom().nextInt(this.randomSleepTicks);
        }
    }

    private Animal findSleepingAdult() {
        List<? extends Animal> herd = this.animal.level().getEntitiesOfClass(
                this.animal.getClass(),
                this.animal.getBoundingBox().inflate(16.0, 4.0, 16.0)
        );

        for (Animal friend : herd) {
            if (friend != this.animal && !friend.isBaby() && this.isSleeping(friend)) {
                return friend;
            }
        }
        return null;
    }

    @Override
    public boolean canUse() {
        if (this.animal instanceof net.minecraft.world.entity.animal.armadillo.Armadillo) {
            return false;
        }

        if (this.animal.isInWater() || this.animal.getLastHurtByMob() != null || this.currentPhase != Phase.AWAKE || this.animal.isVehicle()) {
            return false;
        }

        if (this.animal instanceof net.minecraft.world.entity.TamableAnimal tamable && tamable.isTame()) {
            return false;
        }

        if (this.animal.isBaby()) {
            return findSleepingAdult() != null;
        }

        this.checkAndResetDailyLimits();

        if (this.dailySleepTicksLeft <= 0) {
            return false;
        }

        long currentTicks = this.animal.level().getDefaultClockTime();
        long timeOfDay = currentTicks % 24000L;

        if (this.onlySleepAtNight) {
            if (timeOfDay < 13000L || timeOfDay >= 23000L) {
                return false;
            }
            if (this.animal.getRandom().nextInt(40) != 0) {
                return false;
            }
        } else {
            if (this.animal.getRandom().nextInt(400) != 0) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean canContinueToUse() {
        if (this.currentPhase == Phase.AWAKE || this.animal.isInWater() || this.animal.getLastHurtByMob() != null || this.animal.isVehicle()) {
            return false;
        }

        if (this.animal.isBaby()) {
            return findSleepingAdult() != null;
        }

        return this.dailySleepTicksLeft > 0;
    }

    @Override
    public void start() {
        this.timeToRecalcPath = 0;
        this.herdTarget = null;

        if (this.isNE && this.animal instanceof INEAnimal neAnimal) {
            byte state = neAnimal.getBodyState();
            if (state == 1 || state == 2 || state == 6) {
                this.transitionTo(Phase.SITTING_HOLD);
                return;
            }
        }

        this.currentPhase = Phase.SEEKING_HERD;
        this.stateTimer = 200;
    }

    @Override
    public void stop() {
        if (this.isNE && this.animal instanceof INEAnimal neAnimal) {
            byte currentState = neAnimal.getBodyState();
            if (currentState >= 3 && currentState <= 5) {
                neAnimal.setBodyState((byte) 5);
            } else if (currentState > 0 && currentState < 6) {
                neAnimal.setBodyState((byte) 6);
            }
        }
        this.currentPhase = Phase.AWAKE;
        this.herdTarget = null;
    }

    @Override
    public void tick() {
        if (!this.animal.isBaby() && this.currentPhase == Phase.SLEEPING_HOLD) {
            this.dailySleepTicksLeft--;
        }

        if (this.currentPhase != Phase.SEEKING_HERD) {
            this.animal.getNavigation().stop();
        }

        switch (this.currentPhase) {
            case SEEKING_HERD:
                this.stateTimer--;
                if (this.stateTimer <= 0) {
                    this.transitionTo(Phase.SITTING);
                    return;
                }

                if (--this.timeToRecalcPath <= 0) {
                    this.timeToRecalcPath = this.adjustedTickDelay(10);

                    if (this.herdTarget == null || !this.herdTarget.isAlive() || !this.isSleeping(this.herdTarget)) {
                        this.herdTarget = this.findSleepingHerdMember();
                    }

                    if (this.herdTarget != null) {
                        double distSqr = this.animal.distanceToSqr(this.herdTarget);
                        if (distSqr < 9.0) {
                            this.transitionTo(Phase.SITTING);
                        } else {
                            this.animal.getNavigation().moveTo(this.herdTarget, 1.0);
                        }
                    } else {
                        this.transitionTo(Phase.SITTING);
                    }
                }
                break;

            case SITTING:
                this.stateTimer--;
                if (this.stateTimer <= 0) {
                    this.transitionTo(Phase.SITTING_HOLD);
                }
                break;

            case SITTING_HOLD:
                this.stateTimer--;
                if (this.stateTimer <= 0) {
                    this.transitionTo(Phase.SLEEP);
                }
                break;

            case SLEEP:
                this.stateTimer--;
                if (this.stateTimer <= 0) {
                    this.transitionTo(Phase.SLEEPING_HOLD);
                }
                break;

            case SLEEPING_HOLD:
                if (this.animal.isBaby()) {

                } else if (this.dailySleepTicksLeft <= 0) {
                    this.transitionTo(Phase.WAKE_UP);
                }
                break;

            case WAKE_UP:
                this.stateTimer--;
                if (this.stateTimer <= 0) {
                    this.transitionTo(Phase.STAND_UP);
                }
                break;

            case STAND_UP:
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

        if (this.isNE && this.animal instanceof INEAnimal neAnimal) {
            switch (nextPhase) {
                case SITTING -> {
                    this.stateTimer = 20;
                    neAnimal.setBodyState((byte) 1);
                }
                case SITTING_HOLD -> {
                    this.stateTimer = 20;
                    neAnimal.setBodyState((byte) 2);
                }
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
                case STAND_UP -> {
                    this.stateTimer = 40;
                    neAnimal.setBodyState((byte) 6);
                }
                case AWAKE -> {
                    neAnimal.setBodyState((byte) 0);
                }
                default -> {}
            }
        }
    }

    private Animal findSleepingHerdMember() {
        List<? extends Animal> herd = this.animal.level().getEntitiesOfClass(
                this.animal.getClass(),
                this.animal.getBoundingBox().inflate(16.0, 4.0, 16.0)
        );

        for (Animal friend : herd) {
            if (friend != this.animal && !friend.isBaby() && this.isSleeping(friend)) {
                return friend;
            }
        }
        return null;
    }

    private boolean isSleeping(Animal entity) {
        if (entity instanceof INEAnimal neAnimal) {
            byte state = neAnimal.getBodyState();
            return state == 3 || state == 4 || state == 5;
        }
        return false;
    }
}

