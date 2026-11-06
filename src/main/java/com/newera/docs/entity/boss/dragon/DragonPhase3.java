package com.newera.docs.entity.boss.dragon;

import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhase;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.monster.Enderman;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.PowerParticleOption;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import com.newera.docs.entity.animation.INEEnderDragonAnimation;
import com.newera.docs.entity.monster.enderman.INEEnderman;
import com.newera.docs.sound.ModSounds;

import java.util.ArrayList;
import java.util.List;

public class DragonPhase3 implements IDragonPhase {

    public static DragonPhase3 instance;

    public DragonPhase3() {
        instance = this;
    }

    @Override
    public void onEnter(EnderDragon dragon, INEEnderDragonAnimation mixin) {
        this.introDone = false;
        this.introTimer = 0;
        this.attackState = 0;
        this.attackTimer = 0;
        this.globalCooldown = 20;
        this.meteorCooldown = 400;
        this.teleportCooldown = 300;
        mixin.setForcedBasePose(2);
    }

    private Player lockedTarget = null;
    private float lockedAttackYaw = 0.0F;
    private static int jumpTicks = 0;

    private boolean introDone = false;
    private int introTimer = 0;
    private final List<Enderman> summonedEndermen = new ArrayList<>();

    private int attackState = 0;
    private int attackTimer = 0;
    private int globalCooldown = 20;
    private int kitingTimer = 0;
    private int meteorCooldown = 600;
    private int teleportCooldown = 300;
    private boolean teleportAttackIsPounce = false;

    private static class MeteorTarget {
        final double x;
        final double y;
        final double z;
        final int impactTick;
        boolean impacted = false;

        MeteorTarget(double x, double y, double z, int impactTick) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.impactTick = impactTick;
        }
    }
    private final List<MeteorTarget> activeMeteors = new ArrayList<>();

    @Override
    public void tick(EnderDragon dragon, INEEnderDragonAnimation mixin) {
        dragon.noPhysics = true;
        mixin.setForcedBasePose(2);
        dragon.setOnGround(true);

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

        if (!introDone) {
            handleIntro(dragon, mixin);
            return;
        }

        if (globalCooldown > 0) globalCooldown--;
        if (meteorCooldown > 0) meteorCooldown--;
        if (teleportCooldown > 0) teleportCooldown--;

        if (attackState > 0) {
            handleAttacks(dragon, mixin);
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

            if (meteorCooldown <= 0 && globalCooldown <= 0) {
                attackState = 20;
                attackTimer = 0;
                meteorCooldown = 600;
                return;
            }

            if (teleportCooldown <= 0 && globalCooldown <= 0 && (kitingTimer >= 40 || dist > 12.0 || dragon.getRandom().nextInt(6) == 0)) {
                attackState = 10;
                attackTimer = 0;
                teleportCooldown = 300;
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
            }
        }
    }

    private void handleIntro(EnderDragon dragon, INEEnderDragonAnimation mixin) {
        introTimer++;
        mixin.setForcedBasePose(2);
        dragon.setOnGround(true);

        if (lockedTarget != null) {
            double dx = lockedTarget.getX() - dragon.getX();
            double dz = lockedTarget.getZ() - dragon.getZ();
            float targetYaw = (float)(Mth.atan2(dz, dx) * (180.0 / Math.PI)) - 90.0F;
            dragon.setYRot(Mth.approachDegrees(dragon.getYRot(), targetYaw, 15.0F));
            dragon.yBodyRot = dragon.getYRot();
            dragon.yHeadRot = dragon.getYRot();
            dragon.setYHeadRot(dragon.getYRot());
        }

        if (introTimer == 1) {

            dragon.level().broadcastEntityEvent(dragon, (byte) 80);
            mixin.triggerClientAction((byte) 80);
            dragon.level().playSound(null, dragon.getX(), dragon.getY(), dragon.getZ(), ModSounds.ENDER_DRAGON_ROAR, SoundSource.HOSTILE, 5.0F, 0.9F);
            summonedEndermen.clear();
        }

        if (!dragon.level().isClientSide() && dragon.level() instanceof ServerLevel sl) {
            int playerCount = (int) sl.players().stream().filter(p -> !p.isCreative() && !p.isSpectator() && p.isAlive()).count();
            int totalEndermen = Math.min(45, Math.max(15, playerCount * 15));

            if (introTimer >= 10 && introTimer <= 65 && introTimer % 3 == 0 && summonedEndermen.size() < totalEndermen) {
                int toSpawn = Math.min(2, totalEndermen - summonedEndermen.size());
                for (int i = 0; i < toSpawn; i++) {
                    Enderman em = EntityTypes.ENDERMAN.create(sl, EntitySpawnReason.TRIGGERED);
                    if (em != null) {
                        double angle = dragon.getRandom().nextDouble() * Math.PI * 2.0;
                        double r = 8.0 + dragon.getRandom().nextDouble() * 16.0;
                        double ex = dragon.getX() + Math.cos(angle) * r;
                        double ez = dragon.getZ() + Math.sin(angle) * r;
                        double ey = findGroundY(sl, ex, ez);
                        em.setPos(ex, ey, ez);
                        ((INEEnderman) em).setDragonSummoned(true);
                        em.setPersistenceRequired();
                        sl.addFreshEntity(em);
                        summonedEndermen.add(em);
                        sl.sendParticles(ParticleTypes.PORTAL, ex, ey + 1.0, ez, 20, 0.5, 1.0, 0.5, 0.1);
                        sl.playSound(null, ex, ey, ez, SoundEvents.ENDERMAN_TELEPORT, SoundSource.HOSTILE, 1.0F, 1.0F);
                    }
                }
            }
        }

        if (introTimer == 80 && !dragon.level().isClientSide()) {
            Player targetPlayer = lockedTarget != null ? lockedTarget : dragon.level().getNearestPlayer(dragon, 128.0);
            if (targetPlayer != null) {
                for (Enderman em : summonedEndermen) {
                    if (em.isAlive()) {
                        double pAngle = dragon.getRandom().nextDouble() * Math.PI * 2.0;
                        double pDist = 3.0 + dragon.getRandom().nextDouble() * 4.0;
                        double tx = targetPlayer.getX() + Math.cos(pAngle) * pDist;
                        double tz = targetPlayer.getZ() + Math.sin(pAngle) * pDist;
                        double ty = findGroundY(dragon.level(), tx, tz);
                        em.setPos(tx, ty, tz);
                        em.setTarget(targetPlayer);
                        em.startPersistentAngerTimer();
                        em.setPersistentAngerTarget(net.minecraft.world.entity.EntityReference.of((LivingEntity) targetPlayer));
                        dragon.level().playSound(null, tx, ty, tz, SoundEvents.ENDERMAN_TELEPORT, SoundSource.HOSTILE, 1.0F, 1.0F);
                        if (dragon.level() instanceof ServerLevel sl) {
                            sl.sendParticles(ParticleTypes.PORTAL, tx, ty + 1.0, tz, 25, 0.5, 1.0, 0.5, 0.1);
                        }
                    }
                }
            }
        }

        if (introTimer >= 180) {
            introDone = true;
            attackState = 0;
            globalCooldown = 20;
            summonedEndermen.clear();
        }
    }

    private void handleAttacks(EnderDragon dragon, INEEnderDragonAnimation mixin) {
        attackTimer++;

        if (attackState <= 4) {
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
        }

        switch (attackState) {
            case 1:
                if (attackTimer == 1) {
                    dragon.level().broadcastEntityEvent(dragon, (byte) 60);
                    mixin.triggerClientAction((byte) 60);
                }
                if (attackTimer == 8) {
                    dragon.level().playSound(null, dragon.getX(), dragon.getY(), dragon.getZ(), ModSounds.ENDER_DRAGON_BITE, SoundSource.HOSTILE, 5.0F, 1.0F);

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

                    AABB aabb = dragon.getBoundingBox().inflate(8.5);
                    List<LivingEntity> entities = dragon.level().getEntitiesOfClass(
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

                    AABB aabb = dragon.getBoundingBox().inflate(8.0);
                    List<LivingEntity> entities = dragon.level().getEntitiesOfClass(
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
                    AABB aabb = dragon.getBoundingBox().inflate(10.0);
                    List<LivingEntity> entities = dragon.level().getEntitiesOfClass(
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

                        AABB streamBox = new AABB(
                            mouthX - 2.5, mouthY - 2.5, mouthZ - 2.5,
                            mouthX + 2.5, mouthY + 0.5, mouthZ + 2.5
                        );
                        List<LivingEntity> inStream = dragon.level().getEntitiesOfClass(
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

            case 10:
                if (attackTimer == 1) {

                    dragon.level().broadcastEntityEvent(dragon, (byte) 79);
                    mixin.triggerClientAction((byte) 79);

                    if (!dragon.level().isClientSide()) {
                        ServerLevel sl = (ServerLevel) dragon.level();
                        sl.sendParticles(ParticleTypes.PORTAL, dragon.getX(), dragon.getY() + 2.0, dragon.getZ(), 50, 1.5, 1.0, 1.5, 0.3);
                        sl.sendParticles(ParticleTypes.REVERSE_PORTAL, dragon.getX(), dragon.getY() + 2.0, dragon.getZ(), 25, 1.0, 0.5, 1.0, 0.2);
                        dragon.level().playSound(null, dragon.getX(), dragon.getY(), dragon.getZ(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.HOSTILE, 5.0F, 0.85F);
                        dragon.setInvisible(true);
                        dragon.setPos(dragon.getX(), 250.0, dragon.getZ());
                    }
                }

                if (attackTimer == 40 && !dragon.level().isClientSide()) {
                    if (lockedTarget != null && lockedTarget.isAlive()) {

                        double angle = dragon.getRandom().nextDouble() * Math.PI * 2.0;
                        double dist = 4.5;
                        double destX = lockedTarget.getX() + Math.cos(angle) * dist;
                        double destZ = lockedTarget.getZ() + Math.sin(angle) * dist;

                        double distCenter = Math.sqrt(destX * destX + destZ * destZ);
                        if (distCenter > 48.0) {
                            destX = (destX / distCenter) * 35.0;
                            destZ = (destZ / distCenter) * 35.0;
                        }

                        double destY = findGroundY(dragon.level(), destX, destZ);
                        dragon.setPos(destX, destY, destZ);
                        dragon.setInvisible(false);

                        double dx = lockedTarget.getX() - destX;
                        double dz = lockedTarget.getZ() - destZ;
                        float targetYaw = (float)(Mth.atan2(dz, dx) * (180.0 / Math.PI)) - 90.0F;
                        dragon.setYRot(targetYaw);
                        dragon.yBodyRot = targetYaw;
                        dragon.yHeadRot = targetYaw;
                        dragon.setYHeadRot(targetYaw);

                        ServerLevel sl = (ServerLevel) dragon.level();
                        sl.sendParticles(ParticleTypes.PORTAL, destX, destY + 2.0, destZ, 50, 1.5, 1.0, 1.5, 0.3);
                        dragon.level().playSound(null, destX, destY, destZ, SoundEvents.ENDERMAN_TELEPORT, SoundSource.HOSTILE, 5.0F, 1.1F);
                        dragon.level().broadcastEntityEvent(dragon, (byte) 79);
                        mixin.triggerClientAction((byte) 79);

                        teleportAttackIsPounce = dragon.getRandom().nextBoolean();
                    }
                }

                if (attackTimer == 42) {
                    if (teleportAttackIsPounce) {
                        byte pounceByte = dragon.getRandom().nextBoolean() ? (byte) 62 : (byte) 69;
                        dragon.level().broadcastEntityEvent(dragon, pounceByte);
                        mixin.triggerClientAction(pounceByte);
                    } else {
                        dragon.level().broadcastEntityEvent(dragon, (byte) 60);
                        mixin.triggerClientAction((byte) 60);
                    }
                }

                if (attackTimer == 50 && !teleportAttackIsPounce) {
                    dragon.level().playSound(null, dragon.getX(), dragon.getY(), dragon.getZ(), ModSounds.ENDER_DRAGON_BITE, SoundSource.HOSTILE, 5.0F, 1.2F);
                    if (lockedTarget != null && lockedTarget.isAlive() && !lockedTarget.isCreative() && !lockedTarget.isSpectator() && dragon.distanceTo(lockedTarget) <= 8.0) {
                        lockedTarget.hurt(dragon.damageSources().mobAttack(dragon), 14.0F);
                        lockedTarget.syncVelocity = true;
                    }
                }

                if (attackTimer == 55 && teleportAttackIsPounce) {
                    dragon.level().playSound(null, dragon.getX(), dragon.getY(), dragon.getZ(), ModSounds.ENDER_DRAGON_WHOOSH, SoundSource.HOSTILE, 5.0F, 1.0F);
                    if (lockedTarget != null && lockedTarget.isAlive() && !lockedTarget.isCreative() && !lockedTarget.isSpectator() && dragon.distanceTo(lockedTarget) <= 8.5) {
                        lockedTarget.hurt(dragon.damageSources().mobAttack(dragon), 16.0F);
                        double dx = lockedTarget.getX() - dragon.getX();
                        double dz = lockedTarget.getZ() - dragon.getZ();
                        double d = Math.sqrt(dx * dx + dz * dz);
                        if (d > 0.001) {
                            lockedTarget.setDeltaMovement(lockedTarget.getDeltaMovement().add((dx / d) * 1.0, 0.4, (dz / d) * 1.0));
                        }
                        lockedTarget.syncVelocity = true;
                    }
                }

                int finishTick = teleportAttackIsPounce ? 70 : 58;
                if (attackTimer >= finishTick) {
                    attackState = 0;
                    globalCooldown = 20;
                }
                break;

            case 20:
                if (attackTimer == 1) {
                    dragon.level().broadcastEntityEvent(dragon, (byte) 61);
                    mixin.triggerClientAction((byte) 61);
                    dragon.level().playSound(null, dragon.getX(), dragon.getY(), dragon.getZ(), ModSounds.ENDER_DRAGON_ROAR, SoundSource.HOSTILE, 5.0F, 1.0F);
                    activeMeteors.clear();
                }

                if (attackTimer == 80 && !dragon.level().isClientSide()) {
                    activeMeteors.clear();
                    double targetX = dragon.getX();
                    double targetZ = dragon.getZ();
                    if (lockedTarget != null && lockedTarget.isAlive()) {
                        targetX = lockedTarget.getX();
                        targetZ = lockedTarget.getZ();
                    }
                    for (int i = 0; i < 10; i++) {
                        double r = dragon.getRandom().nextDouble() * 8.0;
                        double ang = dragon.getRandom().nextDouble() * Math.PI * 2.0;
                        double mx = targetX + Math.cos(ang) * r;
                        double mz = targetZ + Math.sin(ang) * r;
                        double my = findGroundY(dragon.level(), mx, mz);
                        activeMeteors.add(new MeteorTarget(mx, my, mz, 84 + i * 2));
                    }
                }

                if (!dragon.level().isClientSide()) {
                    ServerLevel sl = (ServerLevel) dragon.level();
                    for (MeteorTarget mt : activeMeteors) {
                        if (!mt.impacted && attackTimer >= mt.impactTick) {
                            mt.impacted = true;

                            for (double y = mt.y + 20.0; y >= mt.y; y -= 2.0) {
                                sl.sendParticles(PowerParticleOption.create(ParticleTypes.DRAGON_BREATH, 1.0F), mt.x, y, mt.z, 3, 0.2, 0.2, 0.2, 0.02);
                            }

                            sl.sendParticles(ParticleTypes.EXPLOSION, mt.x, mt.y + 0.5, mt.z, 3, 0.5, 0.5, 0.5, 0.1);
                            sl.sendParticles(PowerParticleOption.create(ParticleTypes.DRAGON_BREATH, 1.2F), mt.x, mt.y + 0.5, mt.z, 25, 1.0, 0.5, 1.0, 0.1);
                            dragon.level().playSound(null, mt.x, mt.y, mt.z, SoundEvents.GENERIC_EXPLODE, SoundSource.HOSTILE, 3.5F, 0.8F);

                            AABB impactBox = new AABB(mt.x - 3.5, mt.y - 1.0, mt.z - 3.5, mt.x + 3.5, mt.y + 3.0, mt.z + 3.5);
                            List<LivingEntity> hitEntities = dragon.level().getEntitiesOfClass(
                                LivingEntity.class, impactBox,
                                e -> e != dragon && e.isAlive() && (!(e instanceof Player p) || (!p.isCreative() && !p.isSpectator()))
                            );
                            for (LivingEntity le : hitEntities) {
                                le.hurt(dragon.damageSources().dragonBreath(), 15.0F);
                                le.syncVelocity = true;
                            }

                            AreaEffectCloud cloud = new AreaEffectCloud(sl, mt.x, mt.y, mt.z);
                            cloud.setOwner(dragon);
                            cloud.setRadius(2.2F);
                            cloud.setDuration(60);
                            cloud.setCustomParticle(PowerParticleOption.create(ParticleTypes.DRAGON_BREATH, 1.0F));
                            cloud.setPotionDurationScale(0.25F);
                            cloud.addEffect(new MobEffectInstance(MobEffects.INSTANT_DAMAGE, 1, 0));
                            sl.addFreshEntity(cloud);
                        }
                    }
                }

                if (attackTimer >= 110) {
                    attackState = 0;
                    activeMeteors.clear();
                    globalCooldown = 25;
                }
                break;
        }
    }

    public static void applyMovement(EnderDragon dragon) {
        if (instance == null) return;
        instance.doApplyMovement(dragon);
    }

    private void doApplyMovement(EnderDragon dragon) {

        if (!introDone) {
            dragon.setDeltaMovement(0, computeHoverGravity(dragon), 0);
            dragon.move(MoverType.SELF, dragon.getDeltaMovement());
            return;
        }

        if (attackState == 10 && attackTimer < 40) {
            dragon.setDeltaMovement(0, 0, 0);
            return;
        }

        if (attackState == 20 || (attackState == 3 && attackTimer <= 45)) {
            dragon.setDeltaMovement(0, computeHoverGravity(dragon), 0);
            dragon.move(MoverType.SELF, dragon.getDeltaMovement());
            return;
        }

        double velX = 0;
        double velZ = 0;
        double currentY = computeHoverGravity(dragon);

        if (lockedTarget != null && lockedTarget.isAlive()) {
            double dx = lockedTarget.getX() - dragon.getX();
            double dz = lockedTarget.getZ() - dragon.getZ();
            double distSq = dx * dx + dz * dz;
            double dist = Math.sqrt(distSq);

            if (dist > 0.1) {
                float targetYaw = (float)(Mth.atan2(dz, dx) * (180.0 / Math.PI)) - 90.0F;
                dragon.setYRot(Mth.approachDegrees(dragon.getYRot(), targetYaw, 12.0F));
                dragon.yBodyRot = dragon.getYRot();
                dragon.yHeadRot = dragon.getYRot();
                dragon.setYHeadRot(dragon.getYRot());
            }

            if (dist > 4.0 && attackState == 0) {
                float yawRad = dragon.getYRot() * ((float)Math.PI / 180F);
                velX = -Math.sin(yawRad) * 0.25;
                velZ = Math.cos(yawRad) * 0.25;
            }
        }

        if (velX != 0 || velZ != 0) {
            double frontX = dragon.getX() + velX * 3.0;
            double frontZ = dragon.getZ() + velZ * 3.0;
            BlockPos wallPos = BlockPos.containing(frontX, dragon.getY() + 1.0, frontZ);
            BlockPos wallHighPos = BlockPos.containing(frontX, dragon.getY() + 3.0, frontZ);
            boolean wallAhead = !dragon.level().getBlockState(wallPos).getCollisionShape(dragon.level(), wallPos).isEmpty()
                             && !dragon.level().getBlockState(wallHighPos).getCollisionShape(dragon.level(), wallHighPos).isEmpty();

            boolean voidAhead = true;
            BlockPos groundCheckPos = BlockPos.containing(frontX, dragon.getY(), frontZ);
            for (int i = -3; i <= 25; i++) {
                BlockPos cp = groundCheckPos.below(i);
                if (!dragon.level().getBlockState(cp).getCollisionShape(dragon.level(), cp).isEmpty()) {
                    voidAhead = false;
                    break;
                }
            }
            if (wallAhead || voidAhead) {
                velX = 0;
                velZ = 0;
            }
        }

        if (lockedTarget != null && jumpTicks <= 0 && dragon.getY() <= getTargetY(dragon) + 0.3) {
            double heightDiff = lockedTarget.getY() - dragon.getY();
            if (heightDiff > 3.0) {
                double targetJump = Math.max(heightDiff + 1.5, 4.0);
                double jumpHeight = Math.min(10.0, targetJump);
                currentY = Math.sqrt(2 * 0.08 * jumpHeight);
                jumpTicks = 40;
            }
        }
        if (jumpTicks > 0) jumpTicks--;

        if ((velX != 0 || velZ != 0) && dragon.tickCount % 18 == 0 && !dragon.level().isClientSide()) {
            dragon.level().playSound(null, dragon.getX(), dragon.getY(), dragon.getZ(), ModSounds.ENDER_DRAGON_STEP, SoundSource.HOSTILE, 3.0F, 1.0F);
        }

        dragon.setDeltaMovement(velX, currentY, velZ);
        dragon.move(MoverType.SELF, dragon.getDeltaMovement());
        dragon.setOnGround(true);
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

    private double findGroundY(net.minecraft.world.level.Level level, double x, double z) {
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

