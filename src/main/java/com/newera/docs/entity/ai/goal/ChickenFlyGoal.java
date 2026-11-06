package com.newera.docs.entity.ai.goal;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.chicken.Chicken;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class ChickenFlyGoal extends Goal {
    private final Chicken chicken;
    private int flyDuration;
    private int cooldown;

    public ChickenFlyGoal(Chicken chicken) {
        this.chicken = chicken;
        this.setFlags(EnumSet.of(Goal.Flag.JUMP));
    }

    @Override
    public boolean canUse() {
        if (this.chicken.isBaby()) {
            return false;
        }
        if (this.chicken.isInWater() || this.chicken.getLastHurtByMob() != null) {
            return false;
        }
        if (this.cooldown > 0) {
            this.cooldown--;
            return false;
        }
        if (this.chicken instanceof com.newera.docs.entity.animation.INEChickenAnimation anim) {
            if (anim.getBodyState() != 0) return false;
        }
        if (!this.chicken.onGround()) {
            return false;
        }
        if (this.chicken.getNavigation().isDone()) {
            return false;
        }

        if (this.chicken.horizontalCollision) {
            return true;
        }

        double horizontalSpeedSqr = this.chicken.getDeltaMovement().horizontalDistanceSqr();
        if (horizontalSpeedSqr > 0.01) {

            return this.chicken.getRandom().nextInt(40) == 0;
        }

        return false;
    }

    @Override
    public boolean canContinueToUse() {
        if (this.flyDuration <= 0 || this.chicken.isInWater()) return false;

        if (this.flyDuration < 15 && this.chicken.onGround()) return false;
        return true;
    }

    @Override
    public void start() {
        this.flyDuration = 20;

        Vec3 currentDelta = this.chicken.getDeltaMovement();
        double speed = currentDelta.horizontalDistance();

        double jumpStrength = 0.45;
        if (speed > 0.1) {
            jumpStrength = 0.55;
        }

        Vec3 forward = this.chicken.getLookAngle().multiply(1.0, 0.0, 1.0).normalize();

        double boostMultiplier = speed * 2.5;
        if (boostMultiplier < 0.2) boostMultiplier = 0.2;

        this.chicken.setDeltaMovement(
                currentDelta.x + forward.x * boostMultiplier,
                jumpStrength,
                currentDelta.z + forward.z * boostMultiplier
        );
    }

    @Override
    public void tick() {
        if (this.flyDuration > 0) {
            this.flyDuration--;

            Vec3 currentDelta = this.chicken.getDeltaMovement();
            if (currentDelta.y < 0) {
                Vec3 forward = this.chicken.getLookAngle().multiply(1.0, 0.0, 1.0).normalize();
                this.chicken.setDeltaMovement(
                    currentDelta.x + forward.x * 0.05,
                    currentDelta.y,
                    currentDelta.z + forward.z * 0.05
                );
            }
        }
    }

    @Override
    public void stop() {
        this.flyDuration = 0;
        this.cooldown = 60 + this.chicken.getRandom().nextInt(100);
    }
}

