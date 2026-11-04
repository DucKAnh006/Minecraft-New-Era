package com.newera.docs.entity.ai.goal;

import com.newera.docs.entity.animation.INEWolfAnimation;
import com.newera.docs.sound.NESounds;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.wolf.Wolf;
import java.util.EnumSet;
import java.util.List;

public class WolfHowlGoal extends Goal {
    private final Wolf wolf;
    private int howlTimer;
    private boolean isSittingHowl;
    private int nextHowlTime = 0;

    public WolfHowlGoal(Wolf wolf) {
        this.wolf = wolf;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
    }

    @Override
    public boolean canUse() {
        if (this.wolf.isTame() || this.wolf.isBaby() || this.wolf.getTarget() != null) {
            return false;
        }

        if (this.wolf instanceof INEWolfAnimation animWolf) {
            byte state = animWolf.getBodyState();
            if (state == 3 || state == 4 || state == 5) {
                return false;
            }
        }

        if (this.wolf.tickCount < this.nextHowlTime) {
            return false;
        }

        long timeOfDay = this.wolf.level().getDefaultClockTime() % 24000;
        boolean isNight = timeOfDay >= 13000 && timeOfDay <= 23000;
        
        if (!isNight || this.wolf.level().isRaining()) {
            return false;
        }

        long day = this.wolf.level().getDefaultClockTime() / 24000L;
        int moonPhase = (int)(day % 8L);
        int baseChance;
        int responseChance;

        switch (moonPhase) {
            case 0 -> { // Full Moon
                baseChance = 100;
                responseChance = 5;
            }
            case 1, 7 -> { // Gibbous
                baseChance = 250;
                responseChance = 10;
            }
            case 2, 6 -> { // Quarter
                baseChance = 400;
                responseChance = 20;
            }
            case 3, 5 -> { // Crescent
                baseChance = 600;
                responseChance = 30;
            }
            default -> { // New Moon
                baseChance = 1000;
                responseChance = 50;
            }
        }

        // Randomly decide to howl, or howl if a nearby wolf is howling
        boolean canHowl = false;
        if (this.wolf.getRandom().nextInt(baseChance) == 0) {
            canHowl = true;
        } else if (this.wolf.getRandom().nextInt(responseChance) == 0) {
            List<Wolf> nearbyWolves = this.wolf.level().getEntitiesOfClass(Wolf.class, this.wolf.getBoundingBox().inflate(16.0D));
            for (Wolf otherWolf : nearbyWolves) {
                if (otherWolf != this.wolf && otherWolf instanceof INEWolfAnimation animWolf) {
                    byte state = animWolf.getBodyState();
                    if (state == 7 || state == 8) {
                        canHowl = true;
                        break;
                    }
                }
            }
        }

        if (canHowl) {
            if (this.wolf instanceof INEWolfAnimation animWolf) {
                byte state = animWolf.getBodyState();
                this.isSittingHowl = (state >= 1 && state <= 2);
            }
            return true;
        }

        return false;
    }

    @Override
    public boolean canContinueToUse() {
        return this.howlTimer > 0 && this.wolf.getTarget() == null;
    }

    @Override
    public void start() {
        this.howlTimer = 80; // Howl duration in ticks (4 seconds)
        if (this.wolf instanceof INEWolfAnimation animWolf) {
            animWolf.setBodyState(this.isSittingHowl ? (byte) 8 : (byte) 7); // 7: Stand Howl, 8: Sit Howl
        }
        
        this.wolf.playSound(NESounds.WOLF_HOWL, 1.0F, (this.wolf.getRandom().nextFloat() - this.wolf.getRandom().nextFloat()) * 0.2F + 1.0F);
        this.wolf.getNavigation().stop();
    }

    @Override
    public void tick() {
        this.howlTimer--;
    }

    @Override
    public void stop() {
        this.nextHowlTime = this.wolf.tickCount + 200 + this.wolf.getRandom().nextInt(400); // 10-30 seconds cooldown
        if (this.wolf instanceof INEWolfAnimation animWolf) {
            if (this.isSittingHowl) {
                animWolf.setBodyState((byte) 2); // Return to sitting hold
            } else {
                animWolf.setBodyState((byte) 0); // Return to normal
            }
        }
    }
}
