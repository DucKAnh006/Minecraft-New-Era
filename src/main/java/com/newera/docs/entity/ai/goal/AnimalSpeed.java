package com.newera.docs.entity.ai.goal;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class AnimalSpeed {
    private final Mob entity;
    private final double walkSpeed;
    private final double runSpeed;
    private final int accelerationTicks;

    private double currentTargetSpeed;
    private double previousTargetSpeed;
    private int transitionTick;
    private int currentTransitionDuration;

    private RunPhase runPhase = RunPhase.NONE;
    private int phaseTimer = 0;
    private int panicBurstTimer = 0;

    public enum RunPhase {
        NONE, ACCELERATING, MAINTAINING, DECELERATING
    }

    public AnimalSpeed(Mob entity, double walkSpeed, double runSpeed, int accelerationTicks) {
        this.entity = entity;
        this.walkSpeed = walkSpeed;
        this.runSpeed = runSpeed;
        this.accelerationTicks = accelerationTicks;
        this.currentTargetSpeed = walkSpeed;
        this.previousTargetSpeed = walkSpeed;
    }

    public void updateMovement(float targetYaw) {
        float bodyYaw = this.entity.getYRot();
        float angleDiff = Math.abs(Mth.wrapDegrees(targetYaw - bodyYaw));

        if (this.runPhase == RunPhase.MAINTAINING && angleDiff > 45.0f) {
            this.changeSpeedTo(this.walkSpeed, 8);
        }
    }

    public void walk() {
        if (this.runPhase == RunPhase.ACCELERATING || this.runPhase == RunPhase.MAINTAINING) {
            this.runPhase = RunPhase.DECELERATING;
            this.changeSpeedTo(this.walkSpeed, 10);
            return;
        }
        this.runPhase = RunPhase.NONE;
        this.changeSpeedTo(this.walkSpeed, Math.max(6, this.accelerationTicks / 3));
    }

    public void run() {
        if (this.runPhase == RunPhase.MAINTAINING) {

            this.phaseTimer = Math.max(this.phaseTimer, 80 + this.entity.getRandom().nextInt(61));
            this.panicBurstTimer = Math.max(this.panicBurstTimer, 20);
            return;
        }

        if (this.runPhase != RunPhase.ACCELERATING) {
            this.runPhase = RunPhase.ACCELERATING;
            double current = this.getCurrentSpeed();

            this.previousTargetSpeed = Math.max(this.walkSpeed, current);
            this.currentTargetSpeed = this.runSpeed;

            this.currentTransitionDuration = Math.max(5, this.accelerationTicks / 3);
            this.transitionTick = 0;

            this.panicBurstTimer = 25;
        }
    }

    public void stop() {

        if (this.runPhase == RunPhase.ACCELERATING || this.runPhase == RunPhase.MAINTAINING) {
            return;
        }
        this.runPhase = RunPhase.NONE;
        int decelTicks = Math.max(6, (int)(this.accelerationTicks * 0.4));
        this.changeSpeedTo(0.0, decelTicks);
    }

    public void forceStop() {
        this.runPhase = RunPhase.NONE;
        this.panicBurstTimer = 0;
        this.changeSpeedTo(0.0, 4);
    }

    private void changeSpeedTo(double speed, int duration) {
        if (this.currentTargetSpeed != speed) {
            this.previousTargetSpeed = this.getCurrentSpeed();
            this.currentTargetSpeed = speed;
            this.currentTransitionDuration = duration;
            this.transitionTick = 0;
        }
    }

    public void tick() {
        if (this.panicBurstTimer > 0) {
            this.panicBurstTimer--;
        }

        if (this.runPhase != RunPhase.NONE) {
            if (this.runPhase == RunPhase.ACCELERATING) {
                if (this.transitionTick >= this.currentTransitionDuration) {
                    this.runPhase = RunPhase.MAINTAINING;
                    this.phaseTimer = 80 + this.entity.getRandom().nextInt(61);
                }
            } else if (this.runPhase == RunPhase.MAINTAINING) {
                this.phaseTimer--;
                if (this.phaseTimer <= 0) {
                    this.runPhase = RunPhase.DECELERATING;
                    int decelTicks = Math.max(10, (int)(this.accelerationTicks * 0.6));

                    this.changeSpeedTo(this.walkSpeed, decelTicks);
                }
            } else if (this.runPhase == RunPhase.DECELERATING) {
                if (this.transitionTick >= this.currentTransitionDuration) {
                    this.runPhase = RunPhase.NONE;
                }
            }
        }

        if (this.transitionTick < this.currentTransitionDuration) {
            this.transitionTick++;
        }

        AttributeInstance speedAttribute = this.entity.getAttribute(Attributes.MOVEMENT_SPEED);
        if (speedAttribute != null) {
            speedAttribute.setBaseValue(this.getCurrentSpeed());
        }
    }

    public double getCurrentSpeed() {
        double baseCalculatedSpeed;
        if (this.currentTransitionDuration <= 0 || this.transitionTick >= this.currentTransitionDuration) {
            baseCalculatedSpeed = this.currentTargetSpeed;
        } else {
            double t = (double) this.transitionTick / this.currentTransitionDuration;
            double curve;
            if (this.runPhase == RunPhase.ACCELERATING) {

                curve = 1.0 - Math.pow(1.0 - t, 3.0);
            } else if (this.runPhase == RunPhase.DECELERATING) {

                curve = 1.0 - (1.0 - t) * (1.0 - t);
            } else {

                curve = t < 0.5 ? 2.0 * t * t : 1.0 - Math.pow(-2.0 * t + 2.0, 2.0) / 2.0;
            }
            baseCalculatedSpeed = Mth.lerp(curve, this.previousTargetSpeed, this.currentTargetSpeed);
        }

        if (this.panicBurstTimer > 0 && (this.runPhase == RunPhase.ACCELERATING || this.runPhase == RunPhase.MAINTAINING)) {
            double burstFactor = (double) this.panicBurstTimer / 25.0;
            return baseCalculatedSpeed * (1.0 + 0.12 * burstFactor);
        }

        return baseCalculatedSpeed;
    }

    public RunPhase getRunPhase() {
        return this.runPhase;
    }
}

