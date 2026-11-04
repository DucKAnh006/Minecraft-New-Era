package com.newera.docs.entity.boss.dragon;

import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhase;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import com.newera.docs.entity.animation.INEEnderDragonAnimation;
import com.newera.docs.sound.ModSounds;

import java.util.List;

public class DragonPhase2_5 implements IDragonPhase {

    public static DragonPhase2_5 instance;
    private int subState = 0;
    private int stateTimer = 0;
    private Vec3 targetHoverPos = null;
    private double targetGroundY = 65.0;

    public DragonPhase2_5() {
        instance = this;
    }

    @Override
    public void onEnter(EnderDragon dragon, INEEnderDragonAnimation mixin) {
        this.subState = 0;
        this.stateTimer = 0;
        this.targetHoverPos = null;
        this.targetGroundY = 65.0;
    }

    @Override
    public void tick(EnderDragon dragon, INEEnderDragonAnimation mixin) {
        if (dragon.getPhaseManager().getCurrentPhase().getPhase() != EnderDragonPhase.HOVERING) {
            dragon.getPhaseManager().setPhase(EnderDragonPhase.HOVERING);
        }

        // Periodic wing flap sound during flight / hover (subState < 4)
        if (subState < 4 && dragon.tickCount % 20 == 7) {
            dragon.level().playSound(null, dragon.getX(), dragon.getY(), dragon.getZ(), ModSounds.ENDER_DRAGON_FLAP, SoundSource.HOSTILE, 5.0F, 1.0F);
        }

        switch (subState) {
            case 0: // FLY TO RANDOM TARGET INSIDE PILLARS
                dragon.noPhysics = true;
                mixin.setForcedBasePose(0); // ON_AIR
                stateTimer++;

                if (stateTimer == 1) {
                    dragon.level().broadcastEntityEvent(dragon, (byte) 68);
                    mixin.triggerClientAction((byte) 68);
                }

                if (targetHoverPos == null) {
                    // Pick random spot inside pillars circle (R in 16..32, angle in 0..2pi)
                    double angle = dragon.getRandom().nextDouble() * Math.PI * 2.0;
                    double r = 16.0 + dragon.getRandom().nextDouble() * 16.0;
                    double targetX = Math.cos(angle) * r;
                    double targetZ = Math.sin(angle) * r;
                    targetGroundY = findGroundY(dragon.level(), targetX, targetZ);
                    targetHoverPos = new Vec3(targetX, targetGroundY + 15.0D, targetZ);
                }

                double hDistSq = (targetHoverPos.x - dragon.getX()) * (targetHoverPos.x - dragon.getX())
                               + (targetHoverPos.z - dragon.getZ()) * (targetHoverPos.z - dragon.getZ());
                double yDiff = Math.abs(targetHoverPos.y - dragon.getY());

                // Converged on target hover position or timeout
                if ((hDistSq <= 64.0 && yDiff <= 4.0) || stateTimer >= 160) {
                    subState = 1;
                    stateTimer = 0;
                    dragon.level().broadcastEntityEvent(dragon, (byte) 65); // Decelerate
                    mixin.triggerClientAction((byte) 65);
                    mixin.setForcedBasePose(1); // HOVER
                }
                break;

            case 1: // DECELERATE (30 ticks = 1.5s)
                dragon.noPhysics = true;
                stateTimer++;
                if (stateTimer >= 30) {
                    subState = 2;
                    stateTimer = 0;
                    dragon.level().broadcastEntityEvent(dragon, (byte) 67); // Hover
                    mixin.triggerClientAction((byte) 67);
                    mixin.setForcedBasePose(1); // HOVER
                }
                break;

            case 2: // HOVER (60 ticks = 3.0s)
                dragon.noPhysics = true;
                stateTimer++;
                if (stateTimer >= 60) {
                    subState = 3;
                    stateTimer = 0;
                    dragon.level().broadcastEntityEvent(dragon, (byte) 73); // Falling
                    mixin.triggerClientAction((byte) 73);
                    mixin.setForcedBasePose(0); // ON_AIR
                }
                break;

            case 3: // DIVE / FALLING
                dragon.noPhysics = true;
                stateTimer++;

                if (dragon.onGround() || dragon.getY() <= targetGroundY + 0.2) {
                    subState = 4;
                    stateTimer = 0;
                    dragon.setPos(dragon.getX(), targetGroundY, dragon.getZ());
                    dragon.noPhysics = false;
                    dragon.setOnGround(true);
                    dragon.level().broadcastEntityEvent(dragon, (byte) 66); // Landing
                    mixin.triggerClientAction((byte) 66);
                    mixin.setForcedBasePose(2); // ON_GROUND

                    // Impact crash damage to nearby living entities
                    AABB crashBox = dragon.getBoundingBox().inflate(6.0);
                    List<LivingEntity> crashEntities = dragon.level().getEntitiesOfClass(
                        LivingEntity.class, crashBox,
                        e -> e != dragon && e.isAlive() && (!(e instanceof Player p) || (!p.isCreative() && !p.isSpectator()))
                    );
                    for (LivingEntity entity : crashEntities) {
                        entity.hurt(dragon.damageSources().mobAttack(dragon), 16.0F);
                    }
                }
                break;

            case 4: // LANDING FINISH & TRANSITION TO PHASE 3
                dragon.noPhysics = false;
                stateTimer++;

                if (stateTimer >= 20) {
                    mixin.setFightStage(3);
                    subState = 0;
                    stateTimer = 0;
                    targetHoverPos = null;
                }
                break;
        }
    }

    public static void applyMovement(EnderDragon dragon) {
        if (instance == null) return;
        instance.doApplyMovement(dragon);
    }

    private void doApplyMovement(EnderDragon dragon) {
        switch (subState) {
            case 0:
                if (targetHoverPos != null) {
                    Vec3 diff = targetHoverPos.subtract(dragon.position());
                    if (diff.length() > 0.01) {
                        Vec3 dir = diff.normalize();
                        dragon.setDeltaMovement(dir.scale(1.15));

                        double dx = targetHoverPos.x - dragon.getX();
                        double dz = targetHoverPos.z - dragon.getZ();
                        if (dx * dx + dz * dz > 0.1) {
                            float targetYaw = (float)(Mth.atan2(dz, dx) * (180.0 / Math.PI)) - 90.0F;
                            dragon.setYRot(Mth.approachDegrees(dragon.getYRot(), targetYaw, 10.0F));
                            dragon.yBodyRot = dragon.getYRot();
                            dragon.yHeadRot = dragon.getYRot();
                            dragon.setYHeadRot(dragon.getYRot());
                        }
                    }
                }
                break;

            case 1:
                if (targetHoverPos != null) {
                    double progress = Math.min(1.0, stateTimer / 30.0);
                    double speed = Math.max(0.1, 1.15 * (1.0 - progress));
                    Vec3 diff = targetHoverPos.subtract(dragon.position());
                    if (diff.length() > 0.1) {
                        dragon.setDeltaMovement(diff.normalize().scale(speed));
                    } else {
                        dragon.setDeltaMovement(0, 0, 0);
                    }

                    double dx = targetHoverPos.x - dragon.getX();
                    double dz = targetHoverPos.z - dragon.getZ();
                    if (dx * dx + dz * dz > 0.1) {
                        float targetYaw = (float)(Mth.atan2(dz, dx) * (180.0 / Math.PI)) - 90.0F;
                        dragon.setYRot(Mth.approachDegrees(dragon.getYRot(), targetYaw, 6.0F));
                        dragon.yBodyRot = dragon.getYRot();
                        dragon.yHeadRot = dragon.getYRot();
                        dragon.setYHeadRot(dragon.getYRot());
                    }
                }
                break;

            case 2:
            case 4:
                dragon.setDeltaMovement(0, 0, 0);
                break;

            case 3:
                dragon.setDeltaMovement(0, -1.5, 0);
                break;
        }

        dragon.move(MoverType.SELF, dragon.getDeltaMovement());
    }

    private double findGroundY(Level level, double x, double z) {
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos(Mth.floor(x), 120, Mth.floor(z));
        for (int y = 120; y >= 40; y--) {
            pos.setY(y);
            BlockState state = level.getBlockState(pos);
            if (!state.isAir() && !state.getCollisionShape(level, pos).isEmpty()) {
                return y + 1.0;
            }
        }
        return 65.0;
    }
}