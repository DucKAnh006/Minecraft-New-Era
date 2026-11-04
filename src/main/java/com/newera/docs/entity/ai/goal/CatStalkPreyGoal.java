package com.newera.docs.entity.ai.goal;

import com.newera.docs.entity.animation.INEAnimal;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class CatStalkPreyGoal extends Goal {
    private final Animal mob;
    private final double stalkSpeed;
    private final double sprintSpeed;
    private LivingEntity target;
    private int delayCounter;
    private int pounceTimer;
    private boolean hasDealtDamage;
    private Path path;
    private int huntTicks; // Track hunt duration

    public CatStalkPreyGoal(Animal mob, double stalkSpeed, double sprintSpeed) {
        this.mob = mob;
        this.stalkSpeed = stalkSpeed;
        this.sprintSpeed = sprintSpeed;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (this.mob instanceof net.minecraft.world.entity.TamableAnimal tamable && tamable.isTame()) {
            return false;
        }
        LivingEntity livingentity = this.mob.getTarget();
        if (livingentity == null) {
            return false;
        } else if (!livingentity.isAlive()) {
            return false;
        } else {
            this.target = livingentity;
            this.path = this.mob.getNavigation().createPath(this.target, 0);
            return this.path != null || this.mob.distanceToSqr(this.target) < 9.0D;
        }
    }

    @Override
    public boolean canContinueToUse() {
        if (this.huntTicks >= 400) { // 20 seconds timeout
            return false;
        }
        LivingEntity livingentity = this.mob.getTarget();
        if (livingentity == null) {
            return false;
        } else if (!livingentity.isAlive()) {
            return false;
        } else {
            return !(livingentity instanceof Player) || !livingentity.isSpectator() && !((Player)livingentity).isCreative();
        }
    }

    @Override
    public void start() {
        this.mob.getNavigation().moveTo(this.path, this.stalkSpeed);
        this.mob.setAggressive(true);
        this.delayCounter = 0;
        this.pounceTimer = 0;
        this.hasDealtDamage = false;
        this.huntTicks = 0;
    }

    @Override
    public void stop() {
        LivingEntity livingentity = this.mob.getTarget();
        if (!net.minecraft.world.entity.EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(livingentity)) {
            this.mob.setTarget(null);
        }

        this.mob.setAggressive(false);
        this.mob.getNavigation().stop();
        if (this.mob instanceof INEAnimal neAnimal) {
            if (neAnimal.getBodyState() == 11) {
                neAnimal.setBodyState((byte) 0);
            }
        }
    }

    @Override
    public void tick() {
        this.huntTicks++;
        LivingEntity livingentity = this.mob.getTarget();
        if (livingentity == null) {
            return;
        }
        
        this.mob.getLookControl().setLookAt(livingentity, 30.0F, 30.0F);
        double distSqr = this.mob.distanceToSqr(livingentity.getX(), livingentity.getY(), livingentity.getZ());
        
        if (this.pounceTimer > 0) {
            this.pounceTimer--;
            
            if (this.pounceTimer == 10) {
                // Leap towards the prey!
                Vec3 vec3 = livingentity.position().subtract(this.mob.position()).normalize().scale(0.6).add(0, 0.2, 0);
                this.mob.setDeltaMovement(vec3);
            }
            
            if (this.pounceTimer == 6 && !this.hasDealtDamage) { // 13 - 7 = 6 (damage at 0.35s)
                this.hasDealtDamage = true;
                // Expanded attack reach during pounce for accuracy
                if (distSqr <= this.getAttackReachSqr(livingentity) * 4.0D) {
                    this.mob.doHurtTarget((net.minecraft.server.level.ServerLevel) this.mob.level(), livingentity);
                }
            }
            
            if (this.pounceTimer <= 0) {
                if (this.mob instanceof INEAnimal neAnimal) {
                    neAnimal.setBodyState((byte) 0);
                }
            }
            return;
        }

        this.delayCounter = Math.max(this.delayCounter - 1, 0);
        
        if (this.delayCounter <= 0 && (this.target.distanceToSqr(this.mob) >= 1.0D || this.mob.getRandom().nextFloat() < 0.05F)) {
            this.delayCounter = 4 + this.mob.getRandom().nextInt(7);
            
            // Sprint if <= 8 blocks
            double speed = distSqr > 64.0D ? this.stalkSpeed : this.sprintSpeed;
            this.mob.getNavigation().moveTo(livingentity, speed);
        }

        this.checkAndPerformAttack(livingentity, distSqr);
    }

    protected void checkAndPerformAttack(LivingEntity target, double distSqr) {
        double d0 = this.getAttackReachSqr(target);
        if (distSqr <= d0 && this.pounceTimer <= 0) {
            this.pounceTimer = 13; // total animation length in ticks
            this.hasDealtDamage = false;
            if (this.mob instanceof INEAnimal neAnimal) {
                neAnimal.setBodyState((byte) 11); // Pounce State
            }
        }
    }

    protected double getAttackReachSqr(LivingEntity attackTarget) {
        return (double)(this.mob.getBbWidth() * 2.0F * this.mob.getBbWidth() * 2.0F + attackTarget.getBbWidth());
    }
}
