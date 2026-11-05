package com.newera.docs.entity.ai.goal;

import com.newera.docs.entity.animation.INEAnimal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Animal;

import java.util.EnumSet;

public class CatStretchGoal extends Goal {
    private final Animal mob;
    private int timer;

    public CatStretchGoal(Animal mob) {
        this.mob = mob;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
    }

    @Override
    public boolean canUse() {
        if (this.mob instanceof net.minecraft.world.entity.TamableAnimal tamable && tamable.isTame()) {
            return false;
        }

        if (this.mob.getRandom().nextInt(200) != 0) return false;
        if (this.mob.getNavigation().isDone() && this.mob.onGround()) {
            if (this.mob instanceof INEAnimal neAnimal) {
                return neAnimal.getBodyState() == 0;
            }
        }
        return false;
    }

    @Override
    public void start() {
        this.timer = 40;
        if (this.mob instanceof INEAnimal neAnimal) {
            neAnimal.setBodyState((byte) 7);
        }
        this.mob.getNavigation().stop();
    }

    @Override
    public boolean canContinueToUse() {
        return this.timer > 0;
    }

    @Override
    public void tick() {
        this.timer--;
    }

    @Override
    public void stop() {
        if (this.mob instanceof INEAnimal neAnimal) {
            neAnimal.setBodyState((byte) 0);
        }
    }
}

