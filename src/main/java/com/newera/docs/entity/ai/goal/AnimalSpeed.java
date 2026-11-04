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

    public enum RunPhase {
        NONE, ACCELERATING, MAINTAINING, DECELERATING
    }

    public AnimalSpeed(Mob entity, double walkSpeed, double runSpeed, int accelerationTicks) {
        this.entity = entity;
        this.walkSpeed = walkSpeed;
        this.runSpeed = runSpeed;
        this.accelerationTicks = accelerationTicks;
        this.currentTargetSpeed = 0.0;
        this.previousTargetSpeed = 0.0;
    }

    public void updateMovement(float targetYaw) {
        float bodyYaw = this.entity.getYRot();
        float angleDiff = Math.abs(Mth.wrapDegrees(targetYaw - bodyYaw));

        if (this.runPhase == RunPhase.MAINTAINING && angleDiff > 45.0f) {
            this.entity.getNavigation().stop();
            this.changeSpeedTo(this.walkSpeed, 10);
        }
    }

    public void walk() {
        this.runPhase = RunPhase.NONE;
        this.changeSpeedTo(this.walkSpeed, Math.max(10, this.accelerationTicks / 2));
    }

    public void run() {
        if (this.runPhase == RunPhase.NONE || this.runPhase == RunPhase.DECELERATING) {
            this.runPhase = RunPhase.ACCELERATING;
            this.changeSpeedTo(this.runSpeed, this.accelerationTicks);
        }
    }

    public void stop() {
        this.runPhase = RunPhase.NONE;
        int decelTicks = Math.max(10, (int)(this.accelerationTicks * 0.6));
        this.changeSpeedTo(0.0, decelTicks);
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
        if (this.runPhase != RunPhase.NONE) {
            if (this.runPhase == RunPhase.ACCELERATING) {
                if (this.transitionTick >= this.currentTransitionDuration) {
                    this.runPhase = RunPhase.MAINTAINING;
                    this.phaseTimer = 60 + this.entity.getRandom().nextInt(61);
                }
            } else if (this.runPhase == RunPhase.MAINTAINING) {
                this.phaseTimer--;
                if (this.phaseTimer <= 0) {
                    this.runPhase = RunPhase.DECELERATING;
                    int decelTicks = Math.max(10, (int)(this.accelerationTicks * 0.75));
                    this.changeSpeedTo(0.0, decelTicks);
                }
            } else if (this.runPhase == RunPhase.DECELERATING) {
                if (this.transitionTick >= this.currentTransitionDuration) {
                    this.runPhase = RunPhase.NONE;
                }
            }
        }

        if (this.transitionTick < this.currentTransitionDuration) {
            this.transitionTick++;
            AttributeInstance speedAttribute = this.entity.getAttribute(Attributes.MOVEMENT_SPEED);
            if (speedAttribute != null) {
                speedAttribute.setBaseValue(this.getCurrentSpeed());
            }
        }
    }

    public double getCurrentSpeed() {
        if (this.currentTransitionDuration <= 0 || this.transitionTick >= this.currentTransitionDuration) {
            return this.currentTargetSpeed;
        }
        double t = (double) this.transitionTick / this.currentTransitionDuration;
        double curve;
        if (this.runPhase == RunPhase.ACCELERATING) {
            curve = t * t;
        } else if (this.runPhase == RunPhase.DECELERATING) {
            curve = 1.0 - (1.0 - t) * (1.0 - t);
        } else {
            curve = t < 0.5 ? 2.0 * t * t : 1.0 - Math.pow(-2.0 * t + 2.0, 2.0) / 2.0;
        }
        return Mth.lerp(curve, this.previousTargetSpeed, this.currentTargetSpeed);
    }
}
