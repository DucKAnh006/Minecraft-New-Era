package com.newera.docs.entity.boss.dragon;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.PowerParticleOption;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhase;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.hurtingprojectile.DragonFireball;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import com.newera.docs.entity.animation.INEEnderDragonAnimation;
import com.newera.docs.sound.ModSounds;

import java.util.List;

public class DragonPhase2 implements IDragonPhase {

    public static DragonPhase2 instance;

    // Common state
    private Player lockedTarget = null;
    private Vec3 currentFlyTarget = null;
    private double flySpeed = 0.85;

    // Circling parameters (Radius 72.0: outside the 48.3-radius pillars!)
    public static final double CIRCLE_RADIUS = 72.0;
    private int circlingTimer = 0;

    // Attack state machine
    // 0 = CIRCLING / ROAMING
    // 1 = FIREBALL_BREATH (Stage 2A: DRAGON_FLY_BREATH, byte 77)
    // 2 = LONG_BREATH_SWEEP (Stage 2A: DRAGON_FLY_LONG_BREATH, byte 78)
    // 3 = HOVER_BARRAGE (Stage 2B: DRAGON_HOVER_BREATH, byte 76)
    // 4 = SWOOP_ATTACK (Stage 2B: contact knockup)
    private int currentAction = 0;
    private int actionTimer = 0;

    // Stage 2A Cooldowns
    private int fireballCooldown = 120; // 6 - 9 seconds
    private int sweepCooldown = 400; // 20 - 30 seconds

    // Stage 2B Cooldowns & State
    private int barrageCooldown = 800; // 40 seconds
    private int swoopCooldown = 400; // 20 seconds
    private boolean wasCrystalsAlive = true;

    // Long breath sweep tracking
    private Vec3 sweepStartPos = null;
    private Vec3 sweepExitPos = null;
    private double sweepDistTotal = 1.0;
    private Vec3 lastCloudPos = null;

    // Hover barrage tracking
    private int hoverSubState = 0; // 0: APPROACH, 1: WAIT_BEFORE, 2: SHOT_CYCLE, 3: WAIT_AFTER
    private int hoverSubTimer = 0;
    private int hoverShotsDone = 0;
    private Vec3 hoverAnchorPos = null;

    // Swoop tracking
    private int swoopSubState = 0; // 0: DIVE_CHARGE, 1: PULL_UP
    private Vec3 swoopExitTarget = null;

    private boolean pendingPhaseExit = false;

    public DragonPhase2() {
        instance = this;
    }

    @Override
    public void onEnter(EnderDragon dragon, INEEnderDragonAnimation mixin) {
        this.currentAction = 0;
        this.actionTimer = 0;
        this.hoverSubState = 0;
        this.hoverSubTimer = 0;
        this.hoverShotsDone = 0;
        this.fireballCooldown = 120;
        this.sweepCooldown = 400;
        this.barrageCooldown = 600;
        this.pendingPhaseExit = false;
        mixin.setForcedBasePose(0); // ON_AIR
        dragon.level().broadcastEntityEvent(dragon, (byte) 68);
        mixin.triggerClientAction((byte) 68);
    }

    public void requestPhaseExit(EnderDragon dragon, INEEnderDragonAnimation mixin) {
        // If in a spitting action during its ~1 second window (shot started and about to/just spat fireball)
        if (currentAction == 3 && hoverSubState == 2 && hoverSubTimer >= 1 && hoverSubTimer <= 20) {
            pendingPhaseExit = true;
            return;
        }
        if (currentAction == 1 && actionTimer >= 1 && actionTimer <= 20) {
            pendingPhaseExit = true;
            return;
        }

        // Otherwise interrupt immediately!
        pendingPhaseExit = false;
        currentAction = 0;
        actionTimer = 0;
        hoverSubState = 0;
        hoverSubTimer = 0;
        hoverShotsDone = 0;
        hoverAnchorPos = null;
        sweepStartPos = null;
        sweepExitPos = null;
        lastCloudPos = null;
        swoopExitTarget = null;
        swoopSubState = 0;

        dragon.level().broadcastEntityEvent(dragon, (byte) 68);
        mixin.triggerClientAction((byte) 68);
        mixin.setFightStage(25);
    }

    private boolean areCrystalsAlive(EnderDragon dragon) {
        if (dragon.level().isClientSide()) return false;
        ServerLevel level = (ServerLevel) dragon.level();
        List<EndCrystal> crystals = level.getEntitiesOfClass(
            EndCrystal.class,
            new AABB(-160, 0, -160, 160, 256, 160),
            Entity::isAlive
        );
        return !crystals.isEmpty();
    }

    public static BlockPos getHighestGround(Level level, double x, double z) {
        int bx = Mth.floor(x);
        int bz = Mth.floor(z);
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos(bx, 120, bz);
        while (pos.getY() > 40) {
            if (!level.getBlockState(pos).isAir()) {
                return pos.immutable();
            }
            pos.move(net.minecraft.core.Direction.DOWN);
        }
        return null;
    }

    @Override
    public void tick(EnderDragon dragon, INEEnderDragonAnimation mixin) {
        if (dragon.getHealth() <= 200.0F) {
            if (!pendingPhaseExit) {
                requestPhaseExit(dragon, mixin);
                if (!pendingPhaseExit) {
                    return;
                }
            } else {
                boolean shotFinished = false;
                if (currentAction == 3 && hoverSubState == 3) {
                    if (hoverSubTimer >= 20) shotFinished = true;
                } else if (currentAction == 1) {
                    if (actionTimer >= 20) shotFinished = true;
                } else {
                    shotFinished = true;
                }

                if (shotFinished) {
                    pendingPhaseExit = false;
                    currentAction = 0;
                    actionTimer = 0;
                    hoverSubState = 0;
                    hoverSubTimer = 0;
                    hoverShotsDone = 0;
                    hoverAnchorPos = null;
                    sweepStartPos = null;
                    sweepExitPos = null;
                    lastCloudPos = null;
                    swoopExitTarget = null;
                    swoopSubState = 0;

                    dragon.level().broadcastEntityEvent(dragon, (byte) 68);
                    mixin.triggerClientAction((byte) 68);
                    mixin.setFightStage(25);
                    return;
                }
            }
        }

        dragon.noPhysics = true;

        if (dragon.getPhaseManager().getCurrentPhase().getPhase() != EnderDragonPhase.HOVERING) {
            dragon.getPhaseManager().setPhase(EnderDragonPhase.HOVERING);
        }

        // Periodic wing flap sound (except when stationary hovering in barrage)
        if (dragon.tickCount % 20 == 7 && !(currentAction == 3 && hoverSubState != 0)) {
            dragon.level().playSound(null, dragon.getX(), dragon.getY(), dragon.getZ(), ModSounds.ENDER_DRAGON_FLAP, SoundSource.HOSTILE, 5.0F, 1.0F);
        }

        // Target acquisition
        if (lockedTarget == null || !lockedTarget.isAlive() || lockedTarget.level() != dragon.level() || lockedTarget.isCreative() || lockedTarget.isSpectator()) {
            lockedTarget = null;
            double nearestDistSq = 160.0 * 160.0;
            for (Player p : dragon.level().players()) {
                if (!p.isCreative() && !p.isSpectator() && p.isAlive()) {
                    double d = dragon.distanceToSqr(p);
                    if (d < nearestDistSq) {
                        nearestDistSq = d;
                        lockedTarget = p;
                    }
                }
            }
        }

        boolean crystalsAlive = areCrystalsAlive(dragon);

        // Immediate engagement when crystals are destroyed!
        if (wasCrystalsAlive && !crystalsAlive) {
            wasCrystalsAlive = false;
            barrageCooldown = 40; // Attack right away within 2s!
            swoopCooldown = 200;
            currentAction = 0;
            actionTimer = 0;
        } else if (!wasCrystalsAlive && crystalsAlive) {
            wasCrystalsAlive = true;
        }

        if (crystalsAlive) {
            tickStage2A(dragon, mixin);
        } else {
            tickStage2B(dragon, mixin);
        }
    }

    private void tickStage2A(EnderDragon dragon, INEEnderDragonAnimation mixin) {
        mixin.setForcedBasePose(0); // ON_AIR - continuous flight

        if (currentAction == 0) {
            // CIRCLING STATE: Radius 72.0, outside pillars, lookahead steering
            circlingTimer++;
            double currentAngle = Math.atan2(dragon.getZ(), dragon.getX());
            // Counter-clockwise lookahead ~25 blocks ahead along circle
            double targetAngle = currentAngle + 0.35;
            double targetX = Math.cos(targetAngle) * CIRCLE_RADIUS;
            double targetZ = Math.sin(targetAngle) * CIRCLE_RADIUS;
            double waveY = 88.0 + Math.sin(circlingTimer * 0.02) * 5.0;
            currentFlyTarget = new Vec3(targetX, waveY, targetZ);
            flySpeed = 0.85;

            if (fireballCooldown > 0) fireballCooldown--;
            if (sweepCooldown > 0) sweepCooldown--;

            // Check Sweep Breath trigger (Skill 2A-2)
            // Trigger when dragon is at a good approach distance to player (35 to 65 blocks)
            if (sweepCooldown <= 0 && lockedTarget != null) {
                double distToPlayer = dragon.distanceTo(lockedTarget);
                if (distToPlayer >= 30.0 && distToPlayer <= 70.0) {
                    double dx = lockedTarget.getX() - dragon.getX();
                    double dz = lockedTarget.getZ() - dragon.getZ();
                    double len = Math.sqrt(dx * dx + dz * dz);
                    if (len > 5.0) {
                        double fx = dx / len;
                        double fz = dz / len;

                        // Exit point 40 blocks past the player
                        double exitX = lockedTarget.getX() + fx * 40.0;
                        double exitZ = lockedTarget.getZ() + fz * 40.0;

                        BlockPos pGround = getHighestGround(dragon.level(), lockedTarget.getX(), lockedTarget.getZ());
                        BlockPos eGround = getHighestGround(dragon.level(), exitX, exitZ);

                        // Ensure player and sweep path are over solid ground! ("chỉ lm trên mặt đất th nhé")
                        if (pGround != null && pGround.getY() >= 50 && eGround != null && eGround.getY() >= 50) {
                            currentAction = 2; // LONG_BREATH_SWEEP
                            actionTimer = 0;
                            sweepStartPos = dragon.position();
                            sweepExitPos = new Vec3(exitX, eGround.getY() + 3.0, exitZ);
                            sweepDistTotal = sweepStartPos.distanceTo(sweepExitPos);
                            lastCloudPos = null;
                            sweepCooldown = 450 + dragon.getRandom().nextInt(200); // 22 - 32 seconds

                            // Start animation immediately!
                            dragon.level().broadcastEntityEvent(dragon, (byte) 78);
                            mixin.triggerClientAction((byte) 78);
                            return;
                        }
                    }
                }
            }

            // Check Fireball Breath trigger (Skill 2A-1)
            if (fireballCooldown <= 0 && lockedTarget != null && dragon.distanceTo(lockedTarget) <= 120.0) {
                currentAction = 1; // FIREBALL_BREATH
                actionTimer = 0;
                fireballCooldown = 130 + dragon.getRandom().nextInt(60); // 6.5 - 9.5 seconds

                // Start DRAGON_FLY_BREATH animation
                dragon.level().broadcastEntityEvent(dragon, (byte) 77);
                mixin.triggerClientAction((byte) 77);
            }
        } else if (currentAction == 1) {
            tickFireballBreath(dragon);
        } else if (currentAction == 2) {
            tickSweepBreath(dragon);
        }
    }

    private void tickFireballBreath(EnderDragon dragon) {
        actionTimer++;
        circlingTimer++;
        double currentAngle = Math.atan2(dragon.getZ(), dragon.getX());
        double targetAngle = currentAngle + 0.35;
        double targetX = Math.cos(targetAngle) * CIRCLE_RADIUS;
        double targetZ = Math.sin(targetAngle) * CIRCLE_RADIUS;
        double waveY = 88.0 + Math.sin(circlingTimer * 0.02) * 5.0;
        currentFlyTarget = new Vec3(targetX, waveY, targetZ);
        flySpeed = 0.85;

        // At tick 12 (0.6s): Spit fireball and play sound
        if (actionTimer == 12 && !dragon.level().isClientSide()) {
            ServerLevel serverLevel = (ServerLevel) dragon.level();
            float yawRad = dragon.getYRot() * ((float)Math.PI / 180F);
            double mouthX = dragon.getX() - Math.sin(yawRad) * 3.5;
            double mouthY = dragon.getY() + 1.8;
            double mouthZ = dragon.getZ() + Math.cos(yawRad) * 3.5;

            Vec3 targetPos = (lockedTarget != null) ? lockedTarget.position().add(0, 0.5, 0) : new Vec3(0, 65, 0);
            Vec3 dir = targetPos.subtract(mouthX, mouthY, mouthZ).normalize();

            DragonFireball fireball = new DragonFireball(serverLevel, dragon, dir);
            fireball.snapTo(mouthX, mouthY, mouthZ, 0.0F, 0.0F);
            fireball.setDeltaMovement(dir.scale(0.85));
            serverLevel.addFreshEntity(fireball);

            dragon.level().playSound(null, mouthX, mouthY, mouthZ, ModSounds.ENDER_DRAGON_BREATH, SoundSource.HOSTILE, 5.0F, 1.0F);
        }

        if (pendingPhaseExit && actionTimer >= 20) {
            return;
        }

        if (actionTimer >= 25) {
            currentAction = 0;
            actionTimer = 0;
        }
    }

    private void tickSweepBreath(EnderDragon dragon) {
        actionTimer++;
        flySpeed = (sweepDistTotal > 0.0) ? (sweepDistTotal / 90.0) : 1.0;
        if (flySpeed < 0.85) flySpeed = 0.85;
        if (flySpeed > 1.25) flySpeed = 1.25;

        double progress = actionTimer / 90.0;
        if (sweepStartPos != null && sweepExitPos != null) {
            double tx = Mth.lerp(progress, sweepStartPos.x, sweepExitPos.x);
            double tz = Mth.lerp(progress, sweepStartPos.z, sweepExitPos.z);

            BlockPos gPos = getHighestGround(dragon.level(), tx, tz);
            double groundY = (gPos != null) ? gPos.getY() + 3.0 : 68.0;
            double ty;

            if (progress < 0.15) {
                ty = Mth.lerp(progress / 0.15, sweepStartPos.y, groundY);
            } else if (progress <= 0.80) {
                ty = groundY;
            } else {
                ty = Mth.lerp((progress - 0.80) / 0.20, groundY, 88.0);
            }

            currentFlyTarget = new Vec3(tx, ty, tz);
        }

        // Continuous breath spitting for 3 seconds (from tick 10 to tick 70)
        if (actionTimer >= 10 && actionTimer <= 70 && !dragon.level().isClientSide()) {
            ServerLevel serverLevel = (ServerLevel) dragon.level();
            float yawRad = dragon.getYRot() * ((float)Math.PI / 180F);
            double mouthX = dragon.getX() - Math.sin(yawRad) * 3.5;
            double mouthY = dragon.getY() + 1.8;
            double mouthZ = dragon.getZ() + Math.cos(yawRad) * 3.5;

            serverLevel.sendParticles(
                PowerParticleOption.create(ParticleTypes.DRAGON_BREATH, 1.0F),
                mouthX, mouthY, mouthZ, 12, 0.4, 0.3, 0.4, 0.08
            );

            AABB streamBox = new AABB(mouthX - 3.0, mouthY - 3.5, mouthZ - 3.0, mouthX + 3.0, mouthY + 1.0, mouthZ + 3.0);
            List<LivingEntity> caught = dragon.level().getEntitiesOfClass(
                LivingEntity.class, streamBox,
                e -> e != dragon && e.isAlive() && (!(e instanceof Player p) || (!p.isCreative() && !p.isSpectator()))
            );
            for (LivingEntity le : caught) {
                le.hurt(dragon.damageSources().dragonBreath(), 6.0F);
            }

            BlockPos gPos = getHighestGround(dragon.level(), mouthX, mouthZ);
            if (gPos != null && gPos.getY() >= 50) {
                double gY = gPos.getY() + 1.0;
                boolean shouldSpawn = false;
                if (lastCloudPos == null) {
                    shouldSpawn = true;
                } else {
                    double dx = mouthX - lastCloudPos.x;
                    double dz = mouthZ - lastCloudPos.z;
                    double distSq = dx * dx + dz * dz;
                    if (distSq >= 33.64) {
                        shouldSpawn = true;
                    }
                }

                if (shouldSpawn) {
                    AreaEffectCloud cloud = new AreaEffectCloud(serverLevel, mouthX, gY, mouthZ);
                    cloud.setOwner(dragon);
                    cloud.setRadius(3.0F);
                    cloud.setDuration(160);
                    cloud.setCustomParticle(PowerParticleOption.create(ParticleTypes.DRAGON_BREATH, 1.0F));
                    cloud.setPotionDurationScale(0.25F);
                    cloud.addEffect(new MobEffectInstance(MobEffects.INSTANT_DAMAGE, 1, 1));
                    serverLevel.addFreshEntity(cloud);
                    lastCloudPos = new Vec3(mouthX, gY, mouthZ);
                }
            }
        }

        if (actionTimer >= 90) {
            currentAction = 0;
            actionTimer = 0;
            lastCloudPos = null;
            sweepStartPos = null;
            sweepExitPos = null;
        }
    }

    private void tickStage2B(EnderDragon dragon, INEEnderDragonAnimation mixin) {
        if (barrageCooldown > 0) barrageCooldown--;
        if (sweepCooldown > 0) sweepCooldown--;
        if (swoopCooldown > 0) swoopCooldown--;
        if (fireballCooldown > 0) fireballCooldown--;

        if (currentAction == 0) {
            // ROAMING FLIGHT: Outside pillars, smooth lookahead curve
            mixin.setForcedBasePose(0); // ON_AIR
            circlingTimer++;
            double currentAngle = Math.atan2(dragon.getZ(), dragon.getX());
            double targetAngle = currentAngle + 0.30;
            double targetX = Math.cos(targetAngle) * CIRCLE_RADIUS;
            double targetZ = Math.sin(targetAngle) * CIRCLE_RADIUS;
            double waveY = 85.0 + Math.sin(circlingTimer * 0.02) * 6.0;
            currentFlyTarget = new Vec3(targetX, waveY, targetZ);
            flySpeed = 0.85;

            // Priority 1: Trigger Hover Breath Barrage (Skill 2B-1)
            if (barrageCooldown <= 0 && lockedTarget != null) {
                currentAction = 3; // HOVER_BARRAGE
                hoverSubState = 0; // APPROACH
                hoverSubTimer = 0;
                hoverShotsDone = 0;

                // Anchor position: ~16 blocks away from player, elevated ~8 blocks above ground
                double pAngle = Math.atan2(lockedTarget.getZ() - dragon.getZ(), lockedTarget.getX() - dragon.getX());
                double ax = lockedTarget.getX() - Math.cos(pAngle) * 16.0;
                double az = lockedTarget.getZ() - Math.sin(pAngle) * 16.0;
                BlockPos gPos = getHighestGround(dragon.level(), ax, az);
                double ay = (gPos != null) ? Math.max(lockedTarget.getY() + 8.0, gPos.getY() + 7.0) : lockedTarget.getY() + 8.0;
                hoverAnchorPos = new Vec3(ax, ay, az);

                barrageCooldown = 1200 + dragon.getRandom().nextInt(600); // 60 - 90 seconds
                return;
            }

            // Priority 2: Trigger Long Breath Sweep (Skill 2A-2 implemented in Stage 2B!)
            if (sweepCooldown <= 0 && lockedTarget != null) {
                double distToPlayer = dragon.distanceTo(lockedTarget);
                if (distToPlayer >= 30.0 && distToPlayer <= 70.0) {
                    double dx = lockedTarget.getX() - dragon.getX();
                    double dz = lockedTarget.getZ() - dragon.getZ();
                    double len = Math.sqrt(dx * dx + dz * dz);
                    if (len > 5.0) {
                        double fx = dx / len;
                        double fz = dz / len;

                        double exitX = lockedTarget.getX() + fx * 40.0;
                        double exitZ = lockedTarget.getZ() + fz * 40.0;

                        BlockPos pGround = getHighestGround(dragon.level(), lockedTarget.getX(), lockedTarget.getZ());
                        BlockPos eGround = getHighestGround(dragon.level(), exitX, exitZ);

                        if (pGround != null && pGround.getY() >= 50 && eGround != null && eGround.getY() >= 50) {
                            currentAction = 2; // LONG_BREATH_SWEEP
                            actionTimer = 0;
                            sweepStartPos = dragon.position();
                            sweepExitPos = new Vec3(exitX, eGround.getY() + 3.0, exitZ);
                            sweepDistTotal = sweepStartPos.distanceTo(sweepExitPos);
                            lastCloudPos = null;
                            sweepCooldown = 450 + dragon.getRandom().nextInt(200); // 22 - 32 seconds

                            dragon.level().broadcastEntityEvent(dragon, (byte) 78);
                            mixin.triggerClientAction((byte) 78);
                            return;
                        }
                    }
                }
            }

            // Priority 3: Trigger Swoop Knockup Attack (Skill 2B-2)
            if (swoopCooldown <= 0 && lockedTarget != null && dragon.distanceTo(lockedTarget) <= 120.0) {
                currentAction = 4; // SWOOP_ATTACK
                swoopSubState = 0; // DIVE_CHARGE
                actionTimer = 0;
                swoopExitTarget = null;
                swoopCooldown = 320 + dragon.getRandom().nextInt(160); // 16 - 24 seconds
                return;
            }

            // Priority 4: Trigger Fireball Breath while roaming (Skill 2A-1 style)
            if (fireballCooldown <= 0 && lockedTarget != null && dragon.distanceTo(lockedTarget) <= 120.0) {
                currentAction = 1; // FIREBALL_BREATH
                actionTimer = 0;
                fireballCooldown = 140 + dragon.getRandom().nextInt(60); // 7 - 10 seconds

                dragon.level().broadcastEntityEvent(dragon, (byte) 77);
                mixin.triggerClientAction((byte) 77);
            }
        } else if (currentAction == 1) {
            tickFireballBreath(dragon);
        } else if (currentAction == 2) {
            tickSweepBreath(dragon);
        } else if (currentAction == 3) {
            tickHoverBarrage(dragon, mixin);
        } else if (currentAction == 4) {
            tickSwoopAttack(dragon, mixin);
        }
    }

    private void tickHoverBarrage(EnderDragon dragon, INEEnderDragonAnimation mixin) {
        hoverSubTimer++;

        switch (hoverSubState) {
            case 0: // APPROACH: Fly near player
                mixin.setForcedBasePose(0); // ON_AIR
                flySpeed = 1.05;
                if (lockedTarget != null) {
                    if (hoverAnchorPos == null) {
                        double pAngle = Math.atan2(lockedTarget.getZ() - dragon.getZ(), lockedTarget.getX() - dragon.getX());
                        double ax = lockedTarget.getX() - Math.cos(pAngle) * 16.0;
                        double az = lockedTarget.getZ() - Math.sin(pAngle) * 16.0;
                        BlockPos gPos = getHighestGround(dragon.level(), ax, az);
                        double ay = (gPos != null) ? Math.max(lockedTarget.getY() + 8.0, gPos.getY() + 7.0) : lockedTarget.getY() + 8.0;
                        hoverAnchorPos = new Vec3(ax, ay, az);
                    }
                    currentFlyTarget = hoverAnchorPos;

                    double distToAnchor = dragon.position().distanceTo(hoverAnchorPos);
                    double distToPlayer = dragon.distanceTo(lockedTarget);

                    if (distToAnchor <= 5.0 || (distToPlayer <= 20.0 && distToPlayer >= 8.0) || hoverSubTimer >= 100) {
                        // Reached position: trigger DECELERATE (30 ticks = 1.5s)
                        dragon.level().broadcastEntityEvent(dragon, (byte) 65);
                        mixin.triggerClientAction((byte) 65);
                        mixin.setForcedBasePose(1); // HOVER

                        hoverSubState = 1; // DECELERATING
                        hoverSubTimer = 0;
                        currentFlyTarget = null;
                        dragon.setDeltaMovement(Vec3.ZERO);
                    }
                } else {
                    hoverSubState = 1;
                    hoverSubTimer = 0;
                }
                break;

            case 1: // DECELERATING: Wait 30 ticks for decelerate animation to complete
                currentFlyTarget = null;
                dragon.setDeltaMovement(Vec3.ZERO);

                if (hoverSubTimer >= 30) {
                    // Decelerate complete -> transition to HOVER!
                    dragon.level().broadcastEntityEvent(dragon, (byte) 67);
                    mixin.triggerClientAction((byte) 67);
                    mixin.setForcedBasePose(1); // HOVER

                    hoverSubState = 2; // WAIT_BEFORE_SHOTS (100 ticks = 5.0s)
                    hoverSubTimer = 0;
                }
                break;

            case 2: // WAIT_BEFORE_SHOTS: Hover stationary for 5 seconds (100 ticks)
                mixin.setForcedBasePose(1); // HOVER
                currentFlyTarget = null;
                dragon.setDeltaMovement(Vec3.ZERO);

                if (hoverSubTimer >= 100) {
                    hoverSubState = 3; // SHOT_CYCLE
                    hoverSubTimer = 0;
                    hoverShotsDone = 0;
                }
                break;

            case 3: // SHOT_CYCLE: Repeat 5 times, each 5 seconds (100 ticks) apart
                mixin.setForcedBasePose(1); // HOVER
                currentFlyTarget = null;
                dragon.setDeltaMovement(Vec3.ZERO);

                // Start of a shot: tick 1 (DRAGON_HOVER_BREATH)
                if (hoverSubTimer == 1) {
                    dragon.level().broadcastEntityEvent(dragon, (byte) 76);
                    mixin.triggerClientAction((byte) 76);
                }

                // Spit fireball at tick 12 (0.6s, matching DRAGON_BREATH timing)
                if (hoverSubTimer == 12 && !dragon.level().isClientSide()) {
                    ServerLevel serverLevel = (ServerLevel) dragon.level();
                    float yawRad = dragon.getYRot() * ((float)Math.PI / 180F);
                    double mouthX = dragon.getX() - Math.sin(yawRad) * 3.5;
                    double mouthY = dragon.getY() + 1.8;
                    double mouthZ = dragon.getZ() + Math.cos(yawRad) * 3.5;

                    Vec3 targetPos = (lockedTarget != null) ? lockedTarget.position().add(0, 0.5, 0) : new Vec3(0, 65, 0);
                    Vec3 dir = targetPos.subtract(mouthX, mouthY, mouthZ).normalize();

                    DragonFireball fireball = new DragonFireball(serverLevel, dragon, dir);
                    fireball.snapTo(mouthX, mouthY, mouthZ, 0.0F, 0.0F);
                    fireball.setDeltaMovement(dir.scale(0.95));
                    serverLevel.addFreshEntity(fireball);

                    dragon.level().playSound(null, mouthX, mouthY, mouthZ, ModSounds.ENDER_DRAGON_BREATH, SoundSource.HOSTILE, 5.0F, 1.0F);
                    serverLevel.sendParticles(PowerParticleOption.create(ParticleTypes.DRAGON_BREATH, 1.0F), mouthX, mouthY, mouthZ, 15, 0.3, 0.3, 0.3, 0.05);
                }

                // If pending phase exit, finish this single shot at tick 20 and don't continue to next shots
                if (pendingPhaseExit && hoverSubTimer >= 20) {
                    return;
                }

                // Each shot spaced 5 seconds (100 ticks)
                if (hoverSubTimer >= 100) {
                    hoverShotsDone++;
                    if (hoverShotsDone < 5) {
                        hoverSubTimer = 0; // Next shot
                    } else {
                        // 5 shots complete: wait 5s stationary before accelerating
                        hoverSubState = 4; // WAIT_AFTER_SHOTS
                        hoverSubTimer = 0;
                    }
                }
                break;

            case 4: // WAIT_AFTER_SHOTS: Hover stationary for 5 seconds after 5th shot
                mixin.setForcedBasePose(1); // HOVER
                currentFlyTarget = null;
                dragon.setDeltaMovement(Vec3.ZERO);

                if (hoverSubTimer >= 100) {
                    // Trigger ACCELERATE (40 ticks = 2.0s)
                    dragon.level().broadcastEntityEvent(dragon, (byte) 74);
                    mixin.triggerClientAction((byte) 74);
                    mixin.setForcedBasePose(0); // ON_AIR

                    hoverSubState = 5; // ACCELERATING
                    hoverSubTimer = 0;
                }
                break;

            case 5: // ACCELERATING: 40 ticks
                mixin.setForcedBasePose(0); // ON_AIR
                circlingTimer++;
                double currentAngle = Math.atan2(dragon.getZ(), dragon.getX());
                double targetAngle = currentAngle + 0.30;
                double targetX = Math.cos(targetAngle) * CIRCLE_RADIUS;
                double targetZ = Math.sin(targetAngle) * CIRCLE_RADIUS;
                double waveY = 85.0 + Math.sin(circlingTimer * 0.02) * 6.0;
                currentFlyTarget = new Vec3(targetX, waveY, targetZ);
                flySpeed = Mth.lerp(hoverSubTimer / 40.0, 0.3, 0.85);

                if (hoverSubTimer >= 40) {
                    // Accelerate complete -> switch to ON_AIR!
                    dragon.level().broadcastEntityEvent(dragon, (byte) 68);
                    mixin.triggerClientAction((byte) 68);
                    mixin.setForcedBasePose(0); // ON_AIR

                    currentAction = 0; // Resume roaming
                    actionTimer = 0;
                    hoverSubState = 0;
                    hoverAnchorPos = null;
                }
                break;
        }
    }

    private void tickSwoopAttack(EnderDragon dragon, INEEnderDragonAnimation mixin) {
        actionTimer++;
        mixin.setForcedBasePose(0); // ON_AIR

        if (swoopSubState == 0) {
            // DIVE & CHARGE PHASE
            flySpeed = 1.35;

            if (lockedTarget != null && lockedTarget.isAlive()) {
                currentFlyTarget = lockedTarget.position().add(0, 0.5, 0);
            }

            // Check collision with player
            AABB hitBox = dragon.getBoundingBox().inflate(1.5);
            List<Player> players = dragon.level().getEntitiesOfClass(
                Player.class, hitBox,
                p -> !p.isCreative() && !p.isSpectator() && p.isAlive()
            );

            boolean hitAny = false;
            for (Player p : players) {
                hitAny = true;
                p.hurt(dragon.damageSources().mobAttack(dragon), 14.0F);

                Vec3 motion = dragon.getDeltaMovement();
                double dirX = motion.x;
                double dirZ = motion.z;
                double mLen = Math.sqrt(dirX * dirX + dirZ * dirZ);
                if (mLen > 0.01) {
                    dirX /= mLen;
                    dirZ /= mLen;
                } else {
                    dirX = 0;
                    dirZ = 1;
                }

                p.setDeltaMovement(p.getDeltaMovement().add(dirX * 1.5, 0.95, dirZ * 1.5));
                p.syncVelocity = true;
                dragon.level().playSound(null, p.getX(), p.getY(), p.getZ(), ModSounds.ENDER_DRAGON_STOMP, SoundSource.HOSTILE, 4.0F, 1.0F);

                if (!dragon.level().isClientSide()) {
                    ServerLevel sl = (ServerLevel) dragon.level();
                    sl.sendParticles(ParticleTypes.SWEEP_ATTACK, p.getX(), p.getY() + 1.0, p.getZ(), 2, 0.2, 0.2, 0.2, 0.0);
                    sl.sendParticles(ParticleTypes.EXPLOSION, p.getX(), p.getY() + 0.5, p.getZ(), 1, 0, 0, 0, 0);
                }
            }

            // Also check proximity if bounding box didn't trigger
            if (!hitAny && lockedTarget != null && dragon.distanceTo(lockedTarget) <= 6.5) {
                hitAny = true;
                lockedTarget.hurt(dragon.damageSources().mobAttack(dragon), 14.0F);
                Vec3 motion = dragon.getDeltaMovement();
                double dirX = motion.x;
                double dirZ = motion.z;
                double mLen = Math.sqrt(dirX * dirX + dirZ * dirZ);
                if (mLen > 0.01) {
                    dirX /= mLen;
                    dirZ /= mLen;
                } else {
                    dirX = 0;
                    dirZ = 1;
                }
                lockedTarget.setDeltaMovement(lockedTarget.getDeltaMovement().add(dirX * 1.5, 0.95, dirZ * 1.5));
                lockedTarget.syncVelocity = true;
                dragon.level().playSound(null, lockedTarget.getX(), lockedTarget.getY(), lockedTarget.getZ(), ModSounds.ENDER_DRAGON_STOMP, SoundSource.HOSTILE, 4.0F, 1.0F);
            }

            // If hit player OR timed out (max 100 ticks = 5s), transition to PULL-UP
            if (hitAny || actionTimer >= 100) {
                swoopSubState = 1; // PULL_UP
                actionTimer = 0;

                Vec3 motion = dragon.getDeltaMovement();
                double fX = motion.x;
                double fZ = motion.z;
                double len = Math.sqrt(fX * fX + fZ * fZ);
                if (len > 0.01) {
                    fX /= len;
                    fZ /= len;
                } else {
                    float yawRad = dragon.getYRot() * ((float)Math.PI / 180F);
                    fX = -Math.sin(yawRad);
                    fZ = Math.cos(yawRad);
                }

                // Overshoot 45 blocks forward and climb up to Y = 88.0
                swoopExitTarget = new Vec3(dragon.getX() + fX * 45.0, 88.0, dragon.getZ() + fZ * 45.0);
            }
        } else if (swoopSubState == 1) {
            // PULL-UP & ASCEND PHASE
            flySpeed = 1.15;
            if (swoopExitTarget != null) {
                currentFlyTarget = swoopExitTarget;
            }

            // Climb out for 40 ticks (2 seconds)
            if (actionTimer >= 40 || dragon.getY() >= 85.0) {
                currentAction = 0;
                actionTimer = 0;
                swoopSubState = 0;
                swoopExitTarget = null;
            }
        }
    }

    public static void applyMovement(EnderDragon dragon) {
        if (instance == null) return;

        // Hover stationary state in Stage 2B: smooth rotation towards target player with clamped turn step
        if (instance.currentAction == 3 && (instance.hoverSubState >= 1 && instance.hoverSubState <= 4)) {
            dragon.setDeltaMovement(Vec3.ZERO);

            if (instance.lockedTarget != null) {
                double tx = instance.lockedTarget.getX() - dragon.getX();
                double tz = instance.lockedTarget.getZ() - dragon.getZ();
                if (tx * tx + tz * tz > 0.1) {
                    float targetYaw = (float)(Mth.atan2(tz, tx) * (180.0 / Math.PI)) - 90.0F;
                    float yawDiff = Mth.wrapDegrees(targetYaw - dragon.getYRot());
                    float turnStep = Mth.clamp(yawDiff * 0.10F, -5.0F, 5.0F);
                    dragon.setYRot(dragon.getYRot() + turnStep);
                }
            }

            dragon.yBodyRot = dragon.getYRot();
            dragon.yHeadRot = dragon.getYRot();
            dragon.setYHeadRot(dragon.getYRot());
            return;
        }

        if (instance.currentFlyTarget != null) {
            Vec3 target = instance.currentFlyTarget;
            Vec3 diff = target.subtract(dragon.position());
            double dist = diff.length();

            if (dist > 0.001) {
                Vec3 dir = diff.normalize();
                Vec3 currentMotion = dragon.getDeltaMovement();
                Vec3 desiredMotion = dir.scale(instance.flySpeed);
                
                // During swoop attack, turn and accelerate faster to track the dive
                double lerpFactor = (instance.currentAction == 4) ? 0.30 : 0.15;
                Vec3 smoothedMotion = currentMotion.scale(1.0 - lerpFactor).add(desiredMotion.scale(lerpFactor));
                dragon.setDeltaMovement(smoothedMotion);

                // Smooth organic yaw rotation
                double dx = target.x - dragon.getX();
                double dz = target.z - dragon.getZ();
                if (dx * dx + dz * dz > 0.1) {
                    float targetYaw = (float)(Mth.atan2(dz, dx) * (180.0 / Math.PI)) - 90.0F;
                    float yawDiff = Mth.wrapDegrees(targetYaw - dragon.getYRot());
                    float maxTurn = (instance.currentAction == 4) ? 8.0F : 4.0F;
                    float turnStep = Mth.clamp(yawDiff * 0.15F, -maxTurn, maxTurn);
                    dragon.setYRot(dragon.getYRot() + turnStep);
                }
            }
        }

        dragon.yBodyRot = dragon.getYRot();
        dragon.yHeadRot = dragon.getYRot();
        dragon.setYHeadRot(dragon.getYRot());

        dragon.move(MoverType.SELF, dragon.getDeltaMovement());
    }
}
