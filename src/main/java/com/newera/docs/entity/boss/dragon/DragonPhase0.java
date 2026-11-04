package com.newera.docs.entity.boss.dragon;

import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import com.newera.docs.entity.animation.INEEnderDragonAnimation;
import net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhase;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class DragonPhase0 implements IDragonPhase {
    
    public static Vec3 customFlyTarget = null;
    
    private static DragonPhase0 instance;
    private int subState = 0;
    private Player lockedTarget = null;
    private int stateTimer = 0;
    
    public DragonPhase0() {
        instance = this;
    }
    
    public static boolean isLandingInvulnerable() {
        return instance != null && instance.subState == 4;
    }
    
    public static boolean isHovering() {
        return instance != null && instance.subState == 2;
    }
    
    public static boolean isCustomFlight() {
        return instance != null; // Luôn dùng custom flight để không bị bay ngược theo vanilla
    }

    @Override
    public void tick(EnderDragon dragon, INEEnderDragonAnimation mixin) {
        // Bất kỳ lúc nào animation fly được chơi (subState < 4) thì bắt buộc phải có flap sound
        if (subState < 4 && dragon.tickCount % 20 == 7) {
            dragon.level().playSound(null, dragon.getX(), dragon.getY(), dragon.getZ(), com.newera.docs.sound.ModSounds.ENDER_DRAGON_FLAP, net.minecraft.sounds.SoundSource.HOSTILE, 5.0F, 1.0F);
        }

        switch (subState) {
            case 0:
                if (dragon.getPhaseManager().getCurrentPhase().getPhase() != EnderDragonPhase.HOVERING) {
                    dragon.getPhaseManager().setPhase(EnderDragonPhase.HOVERING);
                }
                
                for (Player p : dragon.level().players()) {
                    if (!p.isCreative() && !p.isSpectator()) {
                        double cx = p.getX();
                        double cz = p.getZ();
                        if (cx * cx + cz * cz <= 3600.0) {
                            lockedTarget = p;
                            subState = 1;
                            stateTimer = 0;
                            break;
                        }
                    }
                }
                
                if (lockedTarget == null) {
                    stateTimer++;
                    double angle = stateTimer * 0.02;
                    // Lượn sóng 3D nhấp nhô lên xuống tự nhiên theo hàm sin
                    double waveY = 100.0 + Math.sin(stateTimer * 0.04) * 12.0;
                    customFlyTarget = new Vec3(Math.cos(angle) * 55.0, waveY, Math.sin(angle) * 55.0);
                }
                break;
                
            case 1:
                if (lockedTarget != null) {
                    stateTimer++;
                    double progress = Math.min(1.0, stateTimer / 200.0);
                    double radius = 35.0 * (1.0 - progress);
                    double angle = stateTimer * 0.05;
                    
                    // Lượn xoắn ốc kết hợp lượn sóng bổ nhào áp sát
                    double baseHeight = lockedTarget.getY() + 30.0 - (15.0 * progress);
                    double waveY = baseHeight + Math.sin(stateTimer * 0.08) * 5.0;

                    // Khi giảm tốc hãm phanh (tick 170..200), độ cao hội tụ mượt mà về player.Y + 15.0
                    if (stateTimer >= 170) {
                        double blend = (stateTimer - 170) / 30.0;
                        waveY = net.minecraft.util.Mth.lerp(blend, waveY, lockedTarget.getY() + 15.0D);
                    }
                    
                    customFlyTarget = new Vec3(
                        lockedTarget.getX() + Math.cos(angle) * radius, 
                        waveY, 
                        lockedTarget.getZ() + Math.sin(angle) * radius
                    );
                    
                    if (stateTimer == 170) {
                        dragon.level().broadcastEntityEvent(dragon, (byte) 65); // Decelerate (1.5s = 30 ticks)
                        mixin.triggerClientAction((byte) 65);
                    }
                    
                    if (stateTimer >= 200) {
                        subState = 2;
                        stateTimer = 0;
                        dragon.level().broadcastEntityEvent(dragon, (byte) 67); // Hover
                        mixin.triggerClientAction((byte) 67);
                    }
                } else {
                    subState = 0;
                }
                break;
                
            case 2:
                if (lockedTarget != null) {
                    customFlyTarget = new Vec3(lockedTarget.getX(), lockedTarget.getY() + 15.0D, lockedTarget.getZ());
                    stateTimer++;
                    if (stateTimer >= 100) {
                        subState = 3;
                        stateTimer = 0;
                        customFlyTarget = null;
                        dragon.level().broadcastEntityEvent(dragon, (byte) 73); // Falling
                        mixin.triggerClientAction((byte) 73);
                    }
                } else {
                    subState = 0;
                }
                break;
                
            case 3:
                double groundY = dragon.getY() - 100.0;
                net.minecraft.core.BlockPos pos = net.minecraft.core.BlockPos.containing(dragon.getX(), dragon.getY(), dragon.getZ());
                for (int i = 0; i <= 100; i++) {
                    net.minecraft.core.BlockPos checkPos = pos.below(i);
                    net.minecraft.world.level.block.state.BlockState state = dragon.level().getBlockState(checkPos);
                    if (!state.isAir() && !state.getCollisionShape(dragon.level(), checkPos).isEmpty()) {
                        groundY = checkPos.getY() + 1.25D;
                        break;
                    }
                }
                double targetY = groundY;
                
                if (dragon.onGround() || dragon.getY() <= targetY + 0.2) {
                    subState = 4;
                    stateTimer = 0;
                    dragon.setPos(dragon.getX(), groundY, dragon.getZ());
                    dragon.level().broadcastEntityEvent(dragon, (byte) 66); // Landing
                    mixin.triggerClientAction((byte) 66);

                    // Sát thương va chạm khi rồng đâm sầm từ trên không xuống đất
                    net.minecraft.world.phys.AABB crashBox = dragon.getBoundingBox().inflate(6.0);
                    java.util.List<net.minecraft.world.entity.LivingEntity> crashEntities = dragon.level().getEntitiesOfClass(
                        net.minecraft.world.entity.LivingEntity.class, crashBox,
                        e -> e != dragon && e.isAlive() && (!(e instanceof Player p) || (!p.isCreative() && !p.isSpectator()))
                    );
                    for (net.minecraft.world.entity.LivingEntity entity : crashEntities) {
                        entity.hurt(dragon.damageSources().mobAttack(dragon), 16.0F);
                    }
                }
                break;
                
            case 4:
                stateTimer++;
                if (stateTimer == 20) {
                    dragon.level().broadcastEntityEvent(dragon, (byte) 61); // Roar
                    mixin.triggerClientAction((byte) 61);
                    dragon.level().playSound(null, dragon.getX(), dragon.getY(), dragon.getZ(), com.newera.docs.sound.ModSounds.ENDER_DRAGON_ROAR, net.minecraft.sounds.SoundSource.HOSTILE, 5.0F, 1.0F);
                }

                // Repulsion Barrier & Periodic Damage during roar (ticks 20..100)
                if (stateTimer >= 20 && stateTimer <= 100) {
                    net.minecraft.world.phys.AABB aabb = dragon.getBoundingBox().inflate(10.0);
                    java.util.List<net.minecraft.world.entity.LivingEntity> entities = dragon.level().getEntitiesOfClass(
                        net.minecraft.world.entity.LivingEntity.class, aabb,
                        e -> e != dragon && e.isAlive() && (!(e instanceof Player p) || (!p.isCreative() && !p.isSpectator()))
                    );
                    for (net.minecraft.world.entity.LivingEntity entity : entities) {
                        double dx = entity.getX() - dragon.getX();
                        double dz = entity.getZ() - dragon.getZ();
                        double distSq = dx * dx + dz * dz;
                        if (distSq <= 100.0) { // 10 block radius
                            double dist = Math.sqrt(distSq);
                            if (dist > 0.001) {
                                entity.setDeltaMovement((dx / dist) * 1.0, 0.35, (dz / dist) * 1.0);
                                entity.syncVelocity = true;
                            }
                            if (stateTimer % 10 == 0) {
                                entity.hurt(dragon.damageSources().mobAttack(dragon), 12.0F);
                            }
                        }
                    }

                    // Shockwave circle particles
                    if (!dragon.level().isClientSide() && stateTimer % 5 == 0) {
                        net.minecraft.server.level.ServerLevel serverLevel = (net.minecraft.server.level.ServerLevel) dragon.level();
                        double radius = ((stateTimer - 20) % 20) * 0.45 + 2.0;
                        for (int deg = 0; deg < 360; deg += 30) {
                            double rad = Math.toRadians(deg);
                            double px = dragon.getX() + Math.cos(rad) * radius;
                            double pz = dragon.getZ() + Math.sin(rad) * radius;
                            double py = dragon.getY() + 0.2;
                            serverLevel.sendParticles(net.minecraft.core.particles.ParticleTypes.POOF, px, py, pz, 1, 0.05, 0.05, 0.05, 0.02);
                            serverLevel.sendParticles(net.minecraft.core.particles.ParticleTypes.SWEEP_ATTACK, px, py + 0.2, pz, 1, 0, 0, 0, 0);
                        }
                    }
                }

                if (stateTimer >= 100) {
                    mixin.setFightStage(1);
                    subState = 0;
                    lockedTarget = null;
                }
                break;
        }
    }

    public static void applyMovement(EnderDragon dragon) {
        if (instance == null || !isCustomFlight()) return;
        
        switch (instance.subState) {
            case 0:
            case 1:
                if (customFlyTarget != null) {
                    Vec3 dir = customFlyTarget.subtract(dragon.position()).normalize();
                    double speed = 1.0;
                    if (instance.subState == 1) {
                        // Giảm tốc độ từ 1.0 xuống 0.2 khi gần hết vòng lượn
                        double progress = Math.min(1.0, instance.stateTimer / 200.0);
                        speed = 1.0 - (progress * 0.8);
                    }
                    dragon.setDeltaMovement(dir.scale(speed));
                    
                    // Logic quay đầu giống Phase 1, đảm bảo đi bộ và bay cùng hướng
                    double dx = customFlyTarget.x - dragon.getX();
                    double dz = customFlyTarget.z - dragon.getZ();
                    if (dx * dx + dz * dz > 0.1) {
                        float targetYaw = (float)(net.minecraft.util.Mth.atan2(dz, dx) * (double)(180F / (float)Math.PI)) - 90.0F;
                        dragon.setYRot(net.minecraft.util.Mth.approachDegrees(dragon.getYRot(), targetYaw, 20.0F));
                        dragon.yBodyRot = dragon.getYRot();
                        dragon.yHeadRot = dragon.getYRot();
                        dragon.setYHeadRot(dragon.getYRot());
                    }
                }
                break;
            case 2:
                dragon.setDeltaMovement(0, 0, 0);
                if (instance.lockedTarget != null) {
                    double dx = instance.lockedTarget.getX() - dragon.getX();
                    double dz = instance.lockedTarget.getZ() - dragon.getZ();
                    if (dx * dx + dz * dz > 0.1) {
                        float targetYaw = (float)(net.minecraft.util.Mth.atan2(dz, dx) * (double)(180F / (float)Math.PI)) - 90.0F;
                        dragon.setYRot(net.minecraft.util.Mth.approachDegrees(dragon.getYRot(), targetYaw, 5.0F));
                        dragon.yBodyRot = dragon.getYRot();
                        dragon.yHeadRot = dragon.getYRot();
                        dragon.setYHeadRot(dragon.getYRot());
                    }
                }
                break;
            case 4:
                dragon.setDeltaMovement(0, 0, 0);
                break;
            case 3:
                dragon.setDeltaMovement(0, -1.5, 0);
                break;
        }

        dragon.move(net.minecraft.world.entity.MoverType.SELF, dragon.getDeltaMovement());
    }
}
