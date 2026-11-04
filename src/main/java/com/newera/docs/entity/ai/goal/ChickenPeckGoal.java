package com.newera.docs.entity.ai.goal;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.chicken.Chicken;
import net.minecraft.world.level.Level;

import java.util.EnumSet;

public class ChickenPeckGoal extends Goal {
    private final Chicken chicken;
    private int peckTimer;

    public ChickenPeckGoal(Chicken chicken) {
        this.chicken = chicken;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
    }

    @Override
    public boolean canUse() {
        if (this.chicken.getRandom().nextInt(this.chicken.isBaby() ? 50 : 1000) != 0) {
            return false;
        }
        return true;
    }

    @Override
    public void start() {
        this.peckTimer = this.adjustedTickDelay(40);
        this.chicken.level().broadcastEntityEvent(this.chicken, (byte)10);
        this.chicken.getNavigation().stop();
    }

    @Override
    public void stop() {
        this.peckTimer = 0;
    }

    @Override
    public boolean canContinueToUse() {
        return this.peckTimer > 0;
    }

    @Override
    public void tick() {
        this.peckTimer = Math.max(0, this.peckTimer - 1);
        if (this.peckTimer == this.adjustedTickDelay(4)) {
            // Can add particle effects or sound here later if needed
        }
    }
}
