package com.newera.docs.entity.ai.goal;

import com.newera.docs.entity.animation.INEChickenAnimation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.animal.chicken.Chicken;
import net.minecraft.world.entity.player.Player;

public class ChickenAvoidEntityGoal extends AvoidEntityGoal<LivingEntity> {
    private final Chicken chicken;

    public ChickenAvoidEntityGoal(Chicken chicken) {
        super(chicken, LivingEntity.class, 8.0F, 1.2D, 2.0D, (entity) -> {
            if (entity instanceof Player player) {
                if (player.isShiftKeyDown()) return false;
                if (player.getDeltaMovement().lengthSqr() < 0.001) return false;
                return true;
            }
            String name = entity.getClass().getSimpleName();
            if (name.equals("Fox") || name.equals("Ocelot") || name.equals("Cat") || name.equals("Wolf")) {
                return true;
            }
            return false;
        });
        this.chicken = chicken;
    }

    @Override
    public boolean canUse() {
        if (this.chicken.isInWater()) {
            return false;
        }
        return super.canUse();
    }

}

