package com.newera.docs.entity.ai.goal;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.EnumSet;

public class CatTemptGoal extends Goal {
    private final Animal mob;
    private final double speedModifier;
    private final Ingredient items;
    private Player player;
    private int calmDown;
    private boolean isPanicking;

    public CatTemptGoal(Animal mob, double speedModifier, Ingredient items) {
        this.mob = mob;
        this.speedModifier = speedModifier;
        this.items = items;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        if (this.calmDown > 0) {
            this.calmDown--;
            return false;
        } else {
            this.player = this.mob.level().getNearestPlayer(this.mob, 10.0D);
            if (this.player == null) {
                return false;
            } else {
                return this.isTempting(this.player.getMainHandItem()) || this.isTempting(this.player.getOffhandItem());
            }
        }
    }

    protected boolean isTempting(ItemStack stack) {
        return this.items.test(stack);
    }

    @Override
    public boolean canContinueToUse() {
        if (this.isPanicking) {
            return this.mob.distanceToSqr(this.player) < 256.0D;
        } else if (this.canUse()) {
            return this.mob.distanceToSqr(this.player) < 100.0D;
        } else {
            return false;
        }
    }

    @Override
    public void start() {
        this.isPanicking = false;
    }

    @Override
    public void stop() {
        this.player = null;
        this.mob.getNavigation().stop();
        this.calmDown = 100;
        this.isPanicking = false;
    }

    @Override
    public void tick() {
        this.mob.getLookControl().setLookAt(this.player, (float)(this.mob.getMaxHeadXRot() + 20), (float)this.mob.getMaxHeadYRot());
        if (this.mob.distanceToSqr(this.player) < 6.25D) {
            this.mob.getNavigation().stop();
        } else {
            if (this.player.distanceToSqr(this.player.xo, this.player.yo, this.player.zo) > 0.0001D || this.player.getXRot() != this.player.xRotO || this.player.getYRot() != this.player.yRotO) {
                this.isPanicking = true;
                double dx = this.mob.getX() - this.player.getX();
                double dz = this.mob.getZ() - this.player.getZ();
                this.mob.getNavigation().moveTo(this.mob.getX() + dx, this.mob.getY(), this.mob.getZ() + dz, this.speedModifier * 1.5D);
            } else if (!this.isPanicking) {
                this.mob.getNavigation().moveTo(this.player, this.speedModifier * 0.5D);
            }
        }
    }
}

