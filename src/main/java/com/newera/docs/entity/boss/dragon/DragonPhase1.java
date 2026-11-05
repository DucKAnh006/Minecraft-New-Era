package com.newera.docs.entity.boss.dragon;

import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import com.newera.docs.entity.animation.INEEnderDragonAnimation;
import net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhase;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.projectile.hurtingprojectile.DragonFireball;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.PowerParticleOption;
import net.minecraft.core.particles.BlockParticleOption;
import com.newera.docs.sound.ModSounds;

public class DragonPhase1 implements IDragonPhase {

    public static DragonPhase1 instance;
    public DragonPhase1() {
        instance = this;
    }

    private Player lockedTarget = null;
    private static int jumpTicks = 0;

    private int attackState = 0;
    private int attackTimer = 0;
    private int globalCooldown = 0;
    private int kitingTimer = 0;
    private float lockedAttackYaw = 0.0F;
    private boolean pendingPhaseExit = false;
    private int settleTimer = 0;

    public boolean isPendingPhaseExit() {
        return pendingPhaseExit;
    }

    public void requestPhaseExit(EnderDragon dragon, INEEnderDragonAnimation mixin) {
        pendingPhaseExit = true;
    }

    @Override
    public void onEnter(EnderDragon dragon, INEEnderDragonAnimation mixin) {
        this.attackState = 0;
        this.attackTimer = 0;
        this.globalCooldown = 20;
        this.kitingTimer = 0;
        this.pendingPhaseExit = false;
        this.settleTimer = 0;
        mixin.setForcedBasePose(2);
    }

    @Override
    public void tick(EnderDragon dragon, INEEnderDragonAnimation mixin) {
        if (dragon.getHealth() <= 400.0F) {
            pendingPhaseExit = true;
        }

        if (pendingPhaseExit && attackState == 0) {
            settleTimer++;
            dragon.setDeltaMovement(0, computeHoverGravity(dragon), 0);
            mixin.setForcedBasePose(2);
            if (settleTimer >= 10) {
                pendingPhaseExit = false;
                settleTimer = 0;
                dragon.setDeltaMovement(0, 0, 0);
                mixin.setFightStage(15);
                return;
            }
            return;
        } else {
            settleTimer = 0;
        }

        dragon.noPhysics = true;

        if (dragon.getPhaseManager().getCurrentPhase().getPhase() != EnderDragonPhase.HOVERING) {
            dragon.getPhaseManager().setPhase(EnderDragonPhase.HOVERING);
        }

        if (lockedTarget == null || !lockedTarget.isAlive() || lockedTarget.level() != dragon.level() || lockedTarget.isSpectator() || lockedTarget.isCreative()) {
            lockedTarget = null;
            double closestDistSq = Double.MAX_VALUE;

            for (Player p : dragon.level().players()) {
                if (!p.isCreative() && !p.isSpectator() && p.isAlive()) {
                    double dSq = dragon.distanceToSqr(p);
                    if (dSq < closestDistSq) {
                        closestDistSq = dSq;
                        lockedTarget = p;
                    }
                }
            }
        }

        if (globalCooldown > 0) globalCooldown--;

        if (attackState > 0) {
            handleAttacks(dragon, mixin);
            return;
        }

        if (pendingPhaseExit) {
            return;
        }

        if (lockedTarget != null) {
            double dx = lockedTarget.getX() - dragon.getX();
            double dz = lockedTarget.getZ() - dragon.getZ();
            double distSq = dx * dx + dz * dz;
            double dist = Math.sqrt(distSq);

            if (dist > 8.0) {
                kitingTimer++;
            } else {
                kitingTimer = 0;
            }

            if (kitingTimer >= 100 && globalCooldown <= 0) {
                attackState = 5;
                attackTimer = 0;
                kitingTimer = 0;
                return;
            }

            if (dist <= 8.0 && globalCooldown <= 0) {
                int rand = dragon.getRandom().nextInt(4);
                if (rand == 0) {
                    attackState = 1;
                } else if (rand == 1) {
                    attackState = 2;
                } else if (rand == 2) {
                    attackState = 3;
                } else {
                    attackState = 4;
                }
                attackTimer = 0;
                handleAttacks(dragon, mixin);
                return;
            }
        }
    }

    private void handleAttacks(EnderDragon dragon, INEEnderDragonAnimation mixin) {
        attackTimer++;

        if (lockedTarget != null && lockedTarget.isAlive()) {
            double dx = lockedTarget.getX() - dragon.getX();
            double dz = lockedTarget.getZ() - dragon.getZ();
            if (dx * dx + dz * dz > 0.01) {
                lockedAttackYaw = (float)(Mth.atan2(dz, dx) * (180F / (float)Math.PI)) - 90.0F;
            }
        }

        dragon.setYRot(lockedAttackYaw);
        dragon.yBodyRot = lockedAttackYaw;
        dragon.yHeadRot = lockedAttackYaw;
        dragon.setYHeadRot(lockedAttackYaw);
        dragon.yRotO = lockedAttackYaw;
        dragon.yBodyRotO = lockedAttackYaw;
        dragon.yHeadRotO = lockedAttackYaw;
        dragon.getNavigation().stop();

        switch (attackState) {
            case 1:
                if (attackTimer == 1) {
                    dragon.level().broadcastEntityEvent(dragon, (byte) 60);
                    mixin.triggerClientAction((byte) 60);
                }
                if (attackTimer == 8) {

                    if (lockedTarget != null && lockedTarget.isAlive() && !lockedTarget.isCreative() && !lockedTarget.isSpectator() && dragon.distanceTo(lockedTarget) <= 8.5) {
                        lockedTarget.hurt(dragon.damageSources().mobAttack(dragon), 14.0F);
                        double dx = lockedTarget.getX() - dragon.getX();
                        double dz = lockedTarget.getZ() - dragon.getZ();
                        double dist = Math.sqrt(dx * dx + dz * dz);
                        if (dist > 0.001) {
                            lockedTarget.setDeltaMovement(lockedTarget.getDeltaMovement().add((dx / dist) * 0.8, 0.35, (dz / dist) * 0.8));
                        } else {
                            lockedTarget.setDeltaMovement(lockedTarget.getDeltaMovement().add(0, 0.35, 0));
                        }
                        lockedTarget.syncVelocity = true;
                    }

                    net.minecraft.world.phys.AABB aabb = dragon.getBoundingBox().inflate(8.5);
                    java.util.List<LivingEntity> entities = dragon.level().getEntitiesOfClass(
                        LivingEntity.class, aabb,
                        e -> e != dragon && e != lockedTarget && e.isAlive() && (!(e instanceof Player p) || (!p.isCreative() && !p.isSpectator()))
                    );
                    for (LivingEntity entity : entities) {
                        double dist = dragon.distanceTo(entity);
                        if (dist <= 8.5) {
                            double dx = entity.getX() - dragon.getX();
                            double dz = entity.getZ() - dragon.getZ();
                            double angleToTarget = (Mth.atan2(dz, dx) * (180F / (float)Math.PI)) - 90.0F;
                            double yawDiff = Math.abs(Mth.wrapDegrees(lockedAttackYaw - angleToTarget));
                            if (yawDiff <= 90.0) {
                                entity.hurt(dragon.damageSources().mobAttack(dragon), 14.0F);
                                if (dist > 0.001) {
                                    entity.setDeltaMovement(entity.getDeltaMovement().add((dx / dist) * 0.8, 0.35, (dz / dist) * 0.8));
                                } else {
                                    entity.setDeltaMovement(entity.getDeltaMovement().add(0, 0.35, 0));
                                }
                                entity.syncVelocity = true;
                            }
                        }
                    }
                }
                if (attackTimer >= 20) {
                    attackState = 0;
                    globalCooldown = 15;
                }
                break;

            case 2:
                if (attackTimer == 1) {
                    byte pounceByte = dragon.getRandom().nextBoolean() ? (byte) 62 : (byte) 69;
                    dragon.level().broadcastEntityEvent(dragon, pounceByte);
                    mixin.triggerClientAction(pounceByte);
                }
                if (attackTimer == 20) {
                    dragon.level().playSound(null, dragon.getX(), dragon.getY(), dragon.getZ(), ModSounds.ENDER_DRAGON_WHOOSH, SoundSource.HOSTILE, 5.0F, 1.0F);

                    if (lockedTarget != null && lockedTarget.isAlive() && !lockedTarget.isCreative() && !lockedTarget.isSpectator() && dragon.distanceTo(lockedTarget) <= 8.5) {
                        lockedTarget.hurt(dragon.damageSources().mobAttack(dragon), 16.0F);
                        double dx = lockedTarget.getX() - dragon.getX();
                        double dz = lockedTarget.getZ() - dragon.getZ();
                        double dist = Math.sqrt(dx * dx + dz * dz);
                        if (dist > 0.001) {
                            lockedTarget.setDeltaMovement(lockedTarget.getDeltaMovement().add((dx / dist) * 1.2, 0.45, (dz / dist) * 1.2));
                        } else {
                            lockedTarget.setDeltaMovement(lockedTarget.getDeltaMovement().add(0, 0.45, 0));
                        }
                        lockedTarget.syncVelocity = true;
                    }

                    net.minecraft.world.phys.AABB aabb = dragon.getBoundingBox().inflate(8.0);
                    java.util.List<LivingEntity> entities = dragon.level().getEntitiesOfClass(
                        LivingEntity.class, aabb,
                        e -> e != dragon && e != lockedTarget && e.isAlive() && (!(e instanceof Player p) || (!p.isCreative() && !p.isSpectator()))
                    );
                    for (LivingEntity entity : entities) {
                        double dist = dragon.distanceTo(entity);
                        if (dist <= 8.0) {
                            double dx = entity.getX() - dragon.getX();
                            double dz = entity.getZ() - dragon.getZ();
                            double angleToTarget = (Mth.atan2(dz, dx) * (180F / (float)Math.PI)) - 90.0F;
                            double yawDiff = Math.abs(Mth.wrapDegrees(lockedAttackYaw - angleToTarget));
                            if (yawDiff <= 60.0) {
                                entity.hurt(dragon.damageSources().mobAttack(dragon), 16.0F);
                                if (dist > 0.001) {
                                    entity.setDeltaMovement(entity.getDeltaMovement().add((dx / dist) * 1.2, 0.45, (dz / dist) * 1.2));
                                } else {
                                    entity.setDeltaMovement(entity.getDeltaMovement().add(0, 0.45, 0));
                                }
                                entity.syncVelocity = true;
                            }
                        }
                    }
                }
                if (attackTimer >= 40) {
                    attackState = 0;
                    globalCooldown = 30;
                }
                break;

            case 3:
                if (attackTimer == 1) {
                    dragon.level().broadcastEntityEvent(dragon, (byte) 64);
                    mixin.triggerClientAction((byte) 64);
                }
                if (attackTimer == 30) {
                    dragon.level().playSound(null, dragon.getX(), dragon.getY(), dragon.getZ(), ModSounds.ENDER_DRAGON_STOMP, SoundSource.HOSTILE, 5.0F, 1.0F);
                }
                if (attackTimer == 40) {
                    net.minecraft.world.phys.AABB aabb = dragon.getBoundingBox().inflate(10.0);
                    java.util.List<LivingEntity> entities = dragon.level().getEntitiesOfClass(
                        LivingEntity.class, aabb,
                        e -> e != dragon && e.isAlive() && (!(e instanceof Player p) || (!p.isCreative() && !p.isSpectator()))
                    );
                    for (LivingEntity entity : entities) {
                        double dx = entity.getX() - dragon.getX();
                        double dz = entity.getZ() - dragon.getZ();
                        double distSq = dx * dx + dz * dz;
                        if (distSq <= 64.0) {
                            entity.hurt(dragon.damageSources().mobAttack(dragon), 15.0F);
                            double dist = Math.sqrt(distSq);
                            if (dist > 0.001) {
                                entity.setDeltaMovement(entity.getDeltaMovement().add((dx / dist) * 1.5, 0.65, (dz / dist) * 1.5));
                            } else {
                                entity.setDeltaMovement(entity.getDeltaMovement().add(0, 0.65, 0));
                            }
                            entity.syncVelocity = true;
                        }
                    }

                    if (!dragon.level().isClientSide()) {
                        ServerLevel serverLevel = (ServerLevel) dragon.level();
                        BlockPos groundPos = dragon.getOnPos();
                        BlockState groundState = dragon.level().getBlockState(groundPos);
                        if (groundState.isAir()) {
                            groundPos = dragon.blockPosition().below();
                            groundState = dragon.level().getBlockState(groundPos);
                        }
                        BlockParticleOption blockParticle = new BlockParticleOption(ParticleTypes.BLOCK, groundState);
                        BlockParticleOption dustPillar = new BlockParticleOption(ParticleTypes.DUST_PILLAR, groundState);
                        serverLevel.sendParticles(blockParticle, dragon.getX(), dragon.getY() + 0.1, dragon.getZ(), 350, 4.0, 0.5, 4.0, 0.35);
                        serverLevel.sendParticles(dustPillar, dragon.getX(), dragon.getY() + 0.2, dragon.getZ(), 150, 4.5, 0.4, 4.5, 0.2);
                    }
                }
                if (attackTimer >= 58) {
                    attackState = 0;
                    globalCooldown = 40;
                }
                break;

            case 4:
                if (attackTimer == 1) {
                    dragon.level().broadcastEntityEvent(dragon, (byte) 70);
                    mixin.triggerClientAction((byte) 70);
                }
                if (attackTimer >= 10 && attackTimer <= 45) {
                    if (!dragon.level().isClientSide() && attackTimer % 2 == 0) {
                        ServerLevel serverLevel = (ServerLevel) dragon.level();
                        float yawRad = lockedAttackYaw * ((float)Math.PI / 180F);

                        double mouthX = dragon.getX() - Math.sin(yawRad) * 3.5;
                        double mouthY = dragon.getY() + 1.8;
                        double mouthZ = dragon.getZ() + Math.cos(yawRad) * 3.5;

                        serverLevel.sendParticles(
                            PowerParticleOption.create(ParticleTypes.DRAGON_BREATH, 1.0F),
                            mouthX, mouthY, mouthZ, 10, 0.4, 0.2, 0.4, 0.05
                        );

                        net.minecraft.world.phys.AABB streamBox = new net.minecraft.world.phys.AABB(
                            mouthX - 2.5, mouthY - 2.5, mouthZ - 2.5,
                            mouthX + 2.5, mouthY + 0.5, mouthZ + 2.5
                        );
                        java.util.List<LivingEntity> inStream = dragon.level().getEntitiesOfClass(
                            LivingEntity.class, streamBox,
                            e -> e != dragon && e.isAlive() && (!(e instanceof Player p) || (!p.isCreative() && !p.isSpectator()))
                        );
                        for (LivingEntity le : inStream) {
                            le.hurt(dragon.damageSources().dragonBreath(), 8.0F);
                        }
                    }
                }
                if (attackTimer == 20 && !dragon.level().isClientSide()) {
                    ServerLevel serverLevel = (ServerLevel) dragon.level();
                    float yawRad = lockedAttackYaw * ((float)Math.PI / 180F);
                    double cloudX = dragon.getX() - Math.sin(yawRad) * 4.0;
                    double cloudZ = dragon.getZ() + Math.cos(yawRad) * 4.0;
                    double cloudY = dragon.getY();

                    BlockPos.MutableBlockPos bpos = new BlockPos.MutableBlockPos(cloudX, cloudY + 1.0, cloudZ);
                    while (serverLevel.isEmptyBlock(bpos) && bpos.getY() > serverLevel.getMinY()) {
                        bpos.move(Direction.DOWN);
                    }
                    double groundY = bpos.getY() + 1.0;

                    AreaEffectCloud cloud = new AreaEffectCloud(serverLevel, cloudX, groundY, cloudZ);
                    cloud.setOwner(dragon);
                    cloud.setRadius(3.5F);
                    cloud.setDuration(120);
                    cloud.setCustomParticle(PowerParticleOption.create(ParticleTypes.DRAGON_BREATH, 1.0F));
                    cloud.setPotionDurationScale(0.25F);
                    cloud.addEffect(new MobEffectInstance(MobEffects.INSTANT_DAMAGE, 1, 1));
                    serverLevel.addFreshEntity(cloud);
                }
                if (attackTimer >= 55) {
                    attackState = 0;
                    globalCooldown = 45;
                }
                break;

            case 5:
                if (attackTimer == 1) {
                    dragon.level().broadcastEntityEvent(dragon, (byte) 71);
                    mixin.triggerClientAction((byte) 71);
                }
                if (attackTimer == 12 && !dragon.level().isClientSide()) {
                    ServerLevel serverLevel = (ServerLevel) dragon.level();
                    float yawRad = lockedAttackYaw * ((float)Math.PI / 180F);
                    double startX = dragon.getX() - Math.sin(yawRad) * 3.5;
                    double startY = dragon.getY() + 1.8;
                    double startZ = dragon.getZ() + Math.cos(yawRad) * 3.5;

                    Vec3 dir;
                    if (lockedTarget != null) {
                        double tx = lockedTarget.getX() - startX;
                        double ty = lockedTarget.getY() - startY;
                        double tz = lockedTarget.getZ() - startZ;
                        dir = new Vec3(tx, ty, tz).normalize();
                    } else {
                        dir = new Vec3(-Math.sin(yawRad), 0, Math.cos(yawRad)).normalize();
                    }

                    DragonFireball fireball = new DragonFireball(serverLevel, dragon, dir);
                    fireball.snapTo(startX, startY, startZ, 0.0F, 0.0F);
                    fireball.setDeltaMovement(dir.scale(0.85));
                    serverLevel.addFreshEntity(fireball);
                }
                if (attackTimer >= 25) {
                    attackState = 0;
                    globalCooldown = 60;
                }
                break;
        }
    }

    public static void applyMovement(EnderDragon dragon) {
        if (instance == null) return;

        if (instance.pendingPhaseExit && instance.attackState == 0) {
            dragon.setDeltaMovement(0, computeHoverGravity(dragon), 0);
            dragon.move(net.minecraft.world.entity.MoverType.SELF, dragon.getDeltaMovement());
            return;
        }

        if (instance.attackState > 0) {
            dragon.setYRot(instance.lockedAttackYaw);
            dragon.yBodyRot = instance.lockedAttackYaw;
            dragon.yHeadRot = instance.lockedAttackYaw;
            dragon.setYHeadRot(instance.lockedAttackYaw);
            dragon.yRotO = instance.lockedAttackYaw;
            dragon.yBodyRotO = instance.lockedAttackYaw;
            dragon.yHeadRotO = instance.lockedAttackYaw;
            dragon.setDeltaMovement(0, computeHoverGravity(dragon), 0);
            dragon.move(net.minecraft.world.entity.MoverType.SELF, dragon.getDeltaMovement());
            return;
        }

        double moveSpeed = 0.25;
        double velX = 0;
        double velZ = 0;
        double currentY = computeHoverGravity(dragon);

        if (instance.lockedTarget != null) {
            double dx = instance.lockedTarget.getX() - dragon.getX();
            double dz = instance.lockedTarget.getZ() - dragon.getZ();
            double distSq = dx * dx + dz * dz;
            double dist = Math.sqrt(distSq);

            if (distSq > 0.01) {
                float targetYaw = (float)(net.minecraft.util.Mth.atan2(dz, dx) * (double)(180F / (float)Math.PI)) - 90.0F;
                dragon.setYRot(net.minecraft.util.Mth.approachDegrees(dragon.getYRot(), targetYaw, 15.0F));
                dragon.yBodyRot = dragon.getYRot();
                dragon.yHeadRot = dragon.getYRot();
                dragon.setYHeadRot(dragon.getYRot());
            }

            if (dist > 4.5) {
                float currentYawRad = dragon.getYRot() * ((float)Math.PI / 180F);
                velX = -Math.sin(currentYawRad) * moveSpeed;
                velZ = Math.cos(currentYawRad) * moveSpeed;
            } else {
                velX = 0;
                velZ = 0;
            }
        } else {

            velX = 0;
            velZ = 0;
        }

        boolean wallAhead = false;
        boolean voidAhead = false;
        double hSpeed = Math.sqrt(velX * velX + velZ * velZ);
        if (hSpeed > 0.01) {
            double dirX = velX / hSpeed;
            double dirZ = velZ / hSpeed;
            double frontX = dragon.getX() + dirX * 1.8;
            double frontZ = dragon.getZ() + dirZ * 1.8;

            BlockPos wallPos = BlockPos.containing(frontX, dragon.getY() + 3.2, frontZ);
            BlockPos wallMidPos = BlockPos.containing(frontX, dragon.getY() + 1.5, frontZ);
            if (!dragon.level().getBlockState(wallPos).getCollisionShape(dragon.level(), wallPos).isEmpty()
                && !dragon.level().getBlockState(wallMidPos).getCollisionShape(dragon.level(), wallMidPos).isEmpty()) {
                wallAhead = true;
            }

            boolean hasGroundAhead = false;
            BlockPos groundCheckPos = BlockPos.containing(frontX, dragon.getY(), frontZ);
            for (int i = -3; i <= 25; i++) {
                BlockPos cp = groundCheckPos.below(i);
                if (!dragon.level().getBlockState(cp).getCollisionShape(dragon.level(), cp).isEmpty()) {
                    hasGroundAhead = true;
                    break;
                }
            }
            if (!hasGroundAhead) {
                voidAhead = true;
            }
        }

        if (wallAhead || voidAhead) {
            velX = 0;
            velZ = 0;
        }

        if (instance.lockedTarget != null && !instance.lockedTarget.isCreative() && !instance.lockedTarget.isSpectator() && jumpTicks <= 0 && dragon.getY() <= getTargetY(dragon) + 0.3) {
            double heightDiff = instance.lockedTarget.getY() - dragon.getY();
            if (heightDiff > 3.0 || (wallAhead && heightDiff > 0.5)) {
                double targetJump = Math.max(heightDiff + 1.5, 4.0);
                double jumpHeight = Math.min(10.0, targetJump);
                currentY = Math.sqrt(2 * 0.08 * jumpHeight);
                jumpTicks = 40;
            }
        }

        if (jumpTicks > 0) jumpTicks--;

        if ((velX != 0 || velZ != 0) && dragon.tickCount % 20 == 0 && !dragon.level().isClientSide()) {
            dragon.level().playSound(null, dragon.getX(), dragon.getY(), dragon.getZ(), com.newera.docs.sound.ModSounds.ENDER_DRAGON_STEP, net.minecraft.sounds.SoundSource.HOSTILE, 3.0F, 1.0F);
        }

        dragon.setDeltaMovement(velX, currentY, velZ);
        dragon.move(net.minecraft.world.entity.MoverType.SELF, dragon.getDeltaMovement());
    }

    private static double getTargetY(EnderDragon dragon) {
        double groundY = dragon.getY() - 100.0;
        double lookX = dragon.getX() + dragon.getDeltaMovement().x * 3.0;
        double lookZ = dragon.getZ() + dragon.getDeltaMovement().z * 3.0;
        BlockPos pos = BlockPos.containing(lookX, dragon.getY(), lookZ);

        for (int i = -3; i <= 20; i++) {
            BlockPos checkPos = pos.below(i);
            BlockState state = dragon.level().getBlockState(checkPos);
            if (!state.isAir() && !state.getCollisionShape(dragon.level(), checkPos).isEmpty()) {
                BlockPos above = checkPos.above();
                if (dragon.level().getBlockState(above).getCollisionShape(dragon.level(), above).isEmpty()) {
                    groundY = checkPos.getY() + 1.25D;
                    break;
                }
            }
        }
        return groundY;
    }

    private static double computeHoverGravity(EnderDragon dragon) {
        double targetY = getTargetY(dragon);
        double diff = targetY - dragon.getY();

        if (Math.abs(diff) < 0.02) {
            return 0.0;
        }

        if (diff < 0) {

            return Math.max(diff, -0.25);
        } else {

            if (diff <= 3.05) {
                return Math.min(diff, Math.max(0.2, diff * 0.6));
            }
            return 0.0;
        }
    }
}

