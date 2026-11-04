package com.newera.docs.entity.ai.goal;

import com.newera.docs.entity.animation.INECamelAnimation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.animal.camel.Camel;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;

public class CamelEatCactusGoal extends MoveToBlockGoal {
    private final Camel camel;
    private int eatTicks;

    public CamelEatCactusGoal(Camel camel, double speedModifier, int searchRange, int verticalSearchRange) {
        super(camel, speedModifier, searchRange, verticalSearchRange);
        this.camel = camel;
    }

    @Override
    public double acceptedDistance() {
        return 2.5D; // Reach distance to eat
    }

    @Override
    public boolean canUse() {
        if (this.camel.isBaby()) {
            return false;
        }
        // TẠM THỜI TẮT DELAY ĐỂ TEST DỄ DÀNG HƠN
        // if (this.camel.getRandom().nextInt(this.reducedTickDelay(400)) != 0) {
        //     return false; // Don't always try to eat
        // }
        return super.canUse();
    }

    @Override
    public void start() {
        super.start();
        this.eatTicks = 0;
    }

    @Override
    public void stop() {
        super.stop();
        if (this.camel instanceof INECamelAnimation animCamel) {
            if (animCamel.getBodyState() == 7) {
                animCamel.setBodyState((byte) 0);
            }
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (this.isReachedTarget()) {
            if (this.camel instanceof INECamelAnimation animCamel) {
                if (this.eatTicks == 0) {
                    animCamel.setBodyState((byte) 7); // 7: Eating Cactus
                }
                
                this.eatTicks++;

                // Wait for animation to mostly finish
                if (this.eatTicks > 40) {
                    BlockPos target = this.blockPos;
                    // Find the top block of the cactus
                    while (this.camel.level().getBlockState(target.above()).is(Blocks.CACTUS)) {
                        target = target.above();
                    }
                    
                    BlockState state = this.camel.level().getBlockState(target);
                    if (state.is(Blocks.CACTUS)) {
                        this.camel.level().destroyBlock(target, false);
                        if (this.camel.level() instanceof ServerLevel serverLevel) {
                            serverLevel.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, state), target.getX() + 0.5, target.getY() + 0.5, target.getZ() + 0.5, 10, 0.2, 0.2, 0.2, 0.05);
                        }
                        this.camel.heal(2.0f); // Optional: heal the camel
                    }
                    
                    animCamel.setBodyState((byte) 0);
                    this.stop();
                }
            }
        } else {
            this.eatTicks = 0;
            if (this.camel instanceof INECamelAnimation animCamel) {
                if (animCamel.getBodyState() == 7) {
                    animCamel.setBodyState((byte) 0);
                }
            }
        }
    }

    @Override
    protected boolean isValidTarget(LevelReader level, BlockPos pos) {
        // Chỉ nhắm vào block xương rồng trên cùng (ngọn)
        return level.getBlockState(pos).is(Blocks.CACTUS) && !level.getBlockState(pos.above()).is(Blocks.CACTUS);
    }
}
