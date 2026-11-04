package com.newera.docs.entity.ai.goal;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.animal.pig.Pig;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class PigEatGoal extends MoveToBlockGoal {
    private final Pig pig;
    private int eatTimer;

    public PigEatGoal(Pig pig, double speed, int length) {
        super(pig, speed, length);
        this.pig = pig;
    }

    @Override
    public boolean canUse() {
        if (this.pig.getRandom().nextInt(this.pig.isBaby() ? 50 : 1000) != 0) {
            return false;
        }
        return super.canUse();
    }

    @Override
    public void start() {
        super.start();
        this.eatTimer = 0;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isReachedTarget()) {
            if (this.eatTimer == 0) {
                // Play graze animation
                this.pig.level().broadcastEntityEvent(this.pig, (byte) 10);
            }
            this.eatTimer++;
            if (this.eatTimer >= 40) {
                this.pig.level().destroyBlock(this.blockPos, false);
                this.pig.heal(1.0F); // heal slightly
                this.stop();
            }
        } else {
            this.eatTimer = 0;
        }
    }

    @Override
    protected boolean isValidTarget(LevelReader level, BlockPos pos) {
        BlockState blockState = level.getBlockState(pos);
        // Look for mushrooms or flowers
        return blockState.is(Blocks.BROWN_MUSHROOM) || blockState.is(Blocks.RED_MUSHROOM)
                || blockState.is(BlockTags.FLOWERS);
    }
}
