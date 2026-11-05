package com.newera.docs.entity.boss.dragon;

import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhase;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.ParticleTypes;
import com.newera.docs.entity.animation.INEEnderDragonAnimation;
import com.newera.docs.entity.animation.INEEndCrystalAnimation;
import com.newera.docs.sound.ModSounds;

import java.util.List;

public class DragonPhase1_5 implements IDragonPhase {

    public static DragonPhase1_5 instance;
    private int subState = 0;
    private int stateTimer = 0;
    private double initialGroundY = 0.0;
    private double targetHoverY = 0.0;
    private double circleAngle = 0.0;
    private Vec3 currentFlyTarget = null;

    public DragonPhase1_5() {
        instance = this;
    }

    @Override
    public void onEnter(EnderDragon dragon, INEEnderDragonAnimation mixin) {
        this.subState = 0;
        this.stateTimer = 0;
        this.initialGroundY = dragon.getY();
        this.targetHoverY = this.initialGroundY + 15.0D;
        this.currentFlyTarget = null;
        this.circleAngle = 0.0;
        dragon.setDeltaMovement(0, 0, 0);
        mixin.setForcedBasePose(2);
    }

    @Override
    public void tick(EnderDragon dragon, INEEnderDragonAnimation mixin) {
        dragon.noPhysics = true;
        if (dragon.getPhaseManager().getCurrentPhase().getPhase() != EnderDragonPhase.HOVERING) {
            dragon.getPhaseManager().setPhase(EnderDragonPhase.HOVERING);
        }

        stateTimer++;

        switch (subState) {
            case 0:
                if (stateTimer == 1) {
                    initialGroundY = dragon.getY();
                    targetHoverY = initialGroundY + 15.0D;
                    dragon.level().broadcastEntityEvent(dragon, (byte) 72);
                    mixin.triggerClientAction((byte) 72);
                    mixin.setForcedBasePose(1);
                    dragon.level().playSound(null, dragon.getX(), dragon.getY(), dragon.getZ(), ModSounds.ENDER_DRAGON_FLAP, SoundSource.HOSTILE, 5.0F, 1.0F);
                }

                if (stateTimer >= 20) {
                    subState = 1;
                    stateTimer = 0;
                    dragon.setPos(dragon.getX(), targetHoverY, dragon.getZ());
                    dragon.setDeltaMovement(0, 0, 0);
                    dragon.level().broadcastEntityEvent(dragon, (byte) 67);
                    mixin.triggerClientAction((byte) 67);
                    mixin.setForcedBasePose(1);
                }
                break;

            case 1:
                if (stateTimer % 20 == 7) {
                    dragon.level().playSound(null, dragon.getX(), dragon.getY(), dragon.getZ(), ModSounds.ENDER_DRAGON_FLAP, SoundSource.HOSTILE, 4.0F, 1.0F);
                }

                if (stateTimer >= 15) {
                    subState = 2;
                    stateTimer = 0;
                    dragon.level().broadcastEntityEvent(dragon, (byte) 74);
                    mixin.triggerClientAction((byte) 74);
                    mixin.setForcedBasePose(0);
                    dragon.level().playSound(null, dragon.getX(), dragon.getY(), dragon.getZ(), ModSounds.ENDER_DRAGON_FLAP, SoundSource.HOSTILE, 5.0F, 1.0F);
                }
                break;

            case 2:
                if (stateTimer % 20 == 7) {
                    dragon.level().playSound(null, dragon.getX(), dragon.getY(), dragon.getZ(), ModSounds.ENDER_DRAGON_FLAP, SoundSource.HOSTILE, 5.0F, 1.0F);
                }

                double distToCenter = Math.sqrt(dragon.getX() * dragon.getX() + dragon.getZ() * dragon.getZ());
                if (distToCenter <= 4.0 || stateTimer >= 65) {
                    subState = 3;
                    stateTimer = 0;
                    currentFlyTarget = null;
                    dragon.level().broadcastEntityEvent(dragon, (byte) 65);
                    mixin.triggerClientAction((byte) 65);
                    mixin.setForcedBasePose(1);
                    dragon.setDeltaMovement(0, 0, 0);
                }
                break;

            case 3:
                if (stateTimer >= 30) {
                    subState = 4;
                    stateTimer = 0;
                    dragon.setPos(0.0, 92.0, 0.0);
                    dragon.setDeltaMovement(0, 0, 0);
                }
                break;

            case 4:
                if (stateTimer == 1) {
                    dragon.setPos(0.0, 92.0, 0.0);
                    dragon.setDeltaMovement(0, 0, 0);
                    mixin.setForcedBasePose(1);
                    dragon.level().broadcastEntityEvent(dragon, (byte) 75);
                    mixin.triggerClientAction((byte) 75);
                    dragon.level().playSound(null, 0.0, 92.0, 0.0, ModSounds.ENDER_DRAGON_ROAR, SoundSource.HOSTILE, 5.0F, 1.0F);

                    List<EndCrystal> crystals = dragon.level().getEntitiesOfClass(
                        EndCrystal.class,
                        dragon.getBoundingBox().inflate(180.0)
                    );
                    for (EndCrystal crystal : crystals) {
                        if (crystal instanceof INEEndCrystalAnimation crystalMixin) {
                            crystalMixin.triggerWakeUp();
                        }
                    }
                }

                if (stateTimer >= 1 && stateTimer <= 80) {
                    AABB aabb = dragon.getBoundingBox().inflate(14.0);
                    List<LivingEntity> entities = dragon.level().getEntitiesOfClass(
                        LivingEntity.class, aabb,
                        e -> e != dragon && e.isAlive() && (!(e instanceof Player p) || (!p.isCreative() && !p.isSpectator()))
                    );
                    for (LivingEntity entity : entities) {
                        double dx = entity.getX() - dragon.getX();
                        double dz = entity.getZ() - dragon.getZ();
                        double distSq = dx * dx + dz * dz;
                        if (distSq <= 196.0) {
                            double d = Math.sqrt(distSq);
                            if (d > 0.001) {
                                entity.setDeltaMovement((dx / d) * 1.1, 0.35, (dz / d) * 1.1);
                                entity.syncVelocity = true;
                            }
                        }
                    }

                    if (!dragon.level().isClientSide() && stateTimer % 6 == 0) {
                        ServerLevel serverLevel = (ServerLevel) dragon.level();
                        double radius = ((stateTimer) % 24) * 0.5 + 2.0;
                        for (int deg = 0; deg < 360; deg += 20) {
                            double rad = Math.toRadians(deg);
                            double px = dragon.getX() + Math.cos(rad) * radius;
                            double pz = dragon.getZ() + Math.sin(rad) * radius;
                            double py = dragon.getY() + 0.5;
                            serverLevel.sendParticles(ParticleTypes.POOF, px, py, pz, 1, 0.05, 0.05, 0.05, 0.02);
                            serverLevel.sendParticles(ParticleTypes.SWEEP_ATTACK, px, py + 0.2, pz, 1, 0, 0, 0, 0);
                        }
                    }
                }

                if (stateTimer >= 80) {

                    subState = 5;
                    stateTimer = 0;
                    dragon.level().broadcastEntityEvent(dragon, (byte) 74);
                    mixin.triggerClientAction((byte) 74);
                    mixin.setForcedBasePose(0);
                    dragon.level().playSound(null, dragon.getX(), dragon.getY(), dragon.getZ(), ModSounds.ENDER_DRAGON_FLAP, SoundSource.HOSTILE, 5.0F, 1.0F);
                    circleAngle = Math.atan2(dragon.getZ(), dragon.getX());
                }
                break;

            case 5:
                if (stateTimer % 20 == 7) {
                    dragon.level().playSound(null, dragon.getX(), dragon.getY(), dragon.getZ(), ModSounds.ENDER_DRAGON_FLAP, SoundSource.HOSTILE, 5.0F, 1.0F);
                }

                circleAngle += 0.025;
                double exitRadius = Mth.lerp(stateTimer / 40.0, 0.0, 55.0);
                double exitY = Mth.lerp(stateTimer / 40.0, 92.0, 88.0);
                currentFlyTarget = new Vec3(Math.cos(circleAngle) * exitRadius, exitY, Math.sin(circleAngle) * exitRadius);

                if (stateTimer >= 40) {

                    mixin.setFightStage(2);
                    mixin.setForcedBasePose(0);
                    dragon.level().broadcastEntityEvent(dragon, (byte) 68);
                    mixin.triggerClientAction((byte) 68);
                    subState = 0;
                    stateTimer = 0;
                }
                break;
        }
    }

    public static void applyMovement(EnderDragon dragon) {
        if (instance == null) return;

        switch (instance.subState) {
            case 0:
                double currentTargetY;
                if (instance.stateTimer <= 10) {

                    double progress = Math.min(1.0, instance.stateTimer / 10.0);
                    double smoothProg = progress * progress * (3.0 - 2.0 * progress);
                    currentTargetY = instance.initialGroundY + smoothProg * (28.0 / 16.0);
                } else {

                    double progress = Math.min(1.0, (instance.stateTimer - 10) / 10.0);
                    double rocketProg = progress * progress * (3.0 - 2.0 * progress);
                    double intermediateY = instance.initialGroundY + (28.0 / 16.0);
                    currentTargetY = intermediateY + rocketProg * (instance.targetHoverY - intermediateY);
                }
                double dy = currentTargetY - dragon.getY();
                dragon.setDeltaMovement(0, dy, 0);
                break;

            case 1:
                double dxCenter = 0.0 - dragon.getX();
                double dzCenter = 0.0 - dragon.getZ();
                if (dxCenter * dxCenter + dzCenter * dzCenter > 0.1) {
                    float targetYaw = (float)(Mth.atan2(dzCenter, dxCenter) * (180.0 / Math.PI)) - 90.0F;
                    dragon.setYRot(Mth.approachDegrees(dragon.getYRot(), targetYaw, 12.0F));
                }
                dragon.setDeltaMovement(0, 0, 0);
                break;

            case 2:
                Vec3 centerTarget = new Vec3(0.0, 92.0, 0.0);
                Vec3 diffCenter = centerTarget.subtract(dragon.position());
                double distCenter = diffCenter.length();
                double progCenter = Math.min(1.0, instance.stateTimer / 45.0);
                double flySpeed = Mth.lerp(progCenter, 0.45, 1.15);
                if (distCenter < 12.0) {
                    flySpeed = Math.max(0.35, (distCenter / 12.0) * 1.15);
                }
                if (distCenter > 0.01) {
                    Vec3 dirCenter = diffCenter.normalize();
                    dragon.setDeltaMovement(dirCenter.scale(flySpeed));
                    float targetYaw = (float)(Mth.atan2(diffCenter.z, diffCenter.x) * (180.0 / Math.PI)) - 90.0F;
                    dragon.setYRot(Mth.approachDegrees(dragon.getYRot(), targetYaw, 15.0F));
                }
                break;

            case 3:
                dragon.setDeltaMovement(dragon.getDeltaMovement().scale(0.85));
                break;

            case 4:
                dragon.setPos(0.0, 92.0, 0.0);
                dragon.setDeltaMovement(0, 0, 0);
                break;

            case 5:
                if (instance.currentFlyTarget != null) {
                    Vec3 diff = instance.currentFlyTarget.subtract(dragon.position());
                    double prog = Math.min(1.0, instance.stateTimer / 40.0);
                    double speed = Mth.lerp(prog, 0.35, 0.95);
                    if (diff.length() > 0.01) {
                        dragon.setDeltaMovement(diff.normalize().scale(speed));
                        double dx = instance.currentFlyTarget.x - dragon.getX();
                        double dz = instance.currentFlyTarget.z - dragon.getZ();
                        if (dx * dx + dz * dz > 0.1) {
                            float targetYaw = (float)(Mth.atan2(dz, dx) * (180.0 / Math.PI)) - 90.0F;
                            dragon.setYRot(Mth.approachDegrees(dragon.getYRot(), targetYaw, 15.0F));
                        }
                    }
                }
                break;
        }

        dragon.yBodyRot = dragon.getYRot();
        dragon.yHeadRot = dragon.getYRot();
        dragon.setYHeadRot(dragon.getYRot());

        dragon.move(MoverType.SELF, dragon.getDeltaMovement());
    }
}

