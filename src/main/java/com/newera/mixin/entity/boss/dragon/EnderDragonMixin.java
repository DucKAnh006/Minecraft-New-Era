package com.newera.mixin.entity.boss.dragon;

import com.newera.docs.entity.boss.dragon.*;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import com.newera.docs.entity.animation.INEEnderDragonAnimation;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnderDragon.class)
public abstract class EnderDragonMixin implements INEEnderDragonAnimation {

    @Unique
    private static final EntityDataAccessor<Integer> FIGHT_STAGE = SynchedEntityData.defineId(EnderDragon.class, EntityDataSerializers.INT);
    @Unique
    private static final EntityDataAccessor<Integer> FORCED_BASE_POSE = SynchedEntityData.defineId(EnderDragon.class, EntityDataSerializers.INT);

    @Inject(method = "defineSynchedData", at = @At("TAIL"))
    protected void defineSynchedDataCustom(SynchedEntityData.Builder builder, CallbackInfo ci) {
        builder.define(FIGHT_STAGE, 0);
        builder.define(FORCED_BASE_POSE, -1);
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    private void onAddAdditionalSaveData(net.minecraft.world.level.storage.ValueOutput output, CallbackInfo ci) {
        output.putInt("NEFightStage", getFightStage());
        output.putInt("NEBasePose", ((EnderDragon) (Object) this).getEntityData().get(FORCED_BASE_POSE));
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void onReadAdditionalSaveData(net.minecraft.world.level.storage.ValueInput input, CallbackInfo ci) {
        setFightStage(input.getIntOr("NEFightStage", 0));
        int pose = input.getIntOr("NEBasePose", -1);
        ((EnderDragon) (Object) this).getEntityData().set(FORCED_BASE_POSE, pose);
    }

    @Unique
    public final AnimationState onGroundPoseAnimationState = new AnimationState();
    @Unique
    public final AnimationState hoverPoseAnimationState = new AnimationState();
    @Unique
    public final AnimationState onAirPoseAnimationState = new AnimationState();
    @Unique
    public final AnimationState walkAnimationState = new AnimationState();
    @Unique
    public final AnimationState flyAnimationState = new AnimationState();
    @Unique
    public final AnimationState biteAnimationState = new AnimationState();

    @Override
    public AnimationState getOnGroundPoseAnimationState() { return onGroundPoseAnimationState; }
    @Override
    public AnimationState getHoverPoseAnimationState() { return hoverPoseAnimationState; }
    @Override
    public AnimationState getOnAirPoseAnimationState() { return onAirPoseAnimationState; }
    @Override
    public AnimationState getWalkAnimationState() { return walkAnimationState; }
    @Override
    public AnimationState getFlyAnimationState() { return flyAnimationState; }
    @Override
    public AnimationState getBiteAnimationState() { return biteAnimationState; }

    @Unique
    public final AnimationState decelerateAnimationState = new AnimationState();
    @Unique
    public final AnimationState landingAnimationState = new AnimationState();
    @Unique
    public final AnimationState roarAnimationState = new AnimationState();
    @Unique
    public final AnimationState pounceLeftAnimationState = new AnimationState();
    @Unique
    public final AnimationState pounceRightAnimationState = new AnimationState();
    @Unique
    public final AnimationState stompAnimationState = new AnimationState();
    @Unique
    public final AnimationState breathCloseAnimationState = new AnimationState();
    @Unique
    public final AnimationState breathAnimationState = new AnimationState();
    @Unique
    public final AnimationState takeoffAnimationState = new AnimationState();
    @Unique
    public final AnimationState fallingAnimationState = new AnimationState();
    @Unique
    public final AnimationState accelerateAnimationState = new AnimationState();
    @Unique
    public final AnimationState roarHoverAnimationState = new AnimationState();

    @Override
    public AnimationState getDecelerateAnimationState() { return decelerateAnimationState; }
    @Override
    public AnimationState getLandingAnimationState() { return landingAnimationState; }
    @Override
    public AnimationState getRoarAnimationState() { return roarAnimationState; }
    @Override
    public AnimationState getPounceLeftAnimationState() { return pounceLeftAnimationState; }
    @Override
    public AnimationState getPounceRightAnimationState() { return pounceRightAnimationState; }
    @Override
    public AnimationState getStompAnimationState() { return stompAnimationState; }
    @Override
    public AnimationState getBreathCloseAnimationState() { return breathCloseAnimationState; }
    @Override
    public AnimationState getBreathAnimationState() { return breathAnimationState; }
    @Override
    public AnimationState getTakeoffAnimationState() { return takeoffAnimationState; }
    @Override
    public AnimationState getFallingAnimationState() { return fallingAnimationState; }
    @Override
    public AnimationState getAccelerateAnimationState() { return accelerateAnimationState; }
    @Override
    public AnimationState getRoarHoverAnimationState() { return roarHoverAnimationState; }

    @Unique
    public final AnimationState hoverBreathAnimationState = new AnimationState();
    @Unique
    public final AnimationState flyBreathAnimationState = new AnimationState();
    @Unique
    public final AnimationState flyLongBreathAnimationState = new AnimationState();

    @Override
    public AnimationState getHoverBreathAnimationState() { return hoverBreathAnimationState; }
    @Override
    public AnimationState getFlyBreathAnimationState() { return flyBreathAnimationState; }
    @Override
    public AnimationState getFlyLongBreathAnimationState() { return flyLongBreathAnimationState; }

    @Unique
    public final AnimationState teleportAnimationState = new AnimationState();
    @Unique
    public final AnimationState roarCurseAnimationState = new AnimationState();
    @Unique
    public final AnimationState deathAnimationState = new AnimationState();

    @Override
    public AnimationState getTeleportAnimationState() { return teleportAnimationState; }
    @Override
    public AnimationState getRoarCurseAnimationState() { return roarCurseAnimationState; }
    @Override
    public AnimationState getDeathAnimationState() { return deathAnimationState; }

    @Unique
    public final AnimationState lookingAnimationState = new AnimationState();
    @Unique
    public final AnimationState rollAnimationState = new AnimationState();

    @Unique
    private float smoothLookYaw = 0.0F;
    @Unique
    private float smoothLookPitch = 0.0F;
    @Unique
    private float smoothFlightRoll = 0.0F;

    @Override
    public AnimationState getLookingAnimationState() { return lookingAnimationState; }
    @Override
    public AnimationState getRollAnimationState() { return rollAnimationState; }

    @Override
    public float getSmoothLookYaw() { return smoothLookYaw; }
    @Override
    public float getSmoothLookPitch() { return smoothLookPitch; }
    @Override
    public float getSmoothFlightRoll() { return smoothFlightRoll; }

    @Unique
    private double deathGroundX;
    @Unique
    private double deathGroundY;
    @Unique
    private double deathGroundZ;
    @Unique
    private float deathGroundYaw;
    @Unique
    private boolean deathLocationCaptured = false;
    @Unique
    private int customDeathTicks = 0;

    @Inject(method = "tickDeath", at = @At("HEAD"))
    private void onTickDeath(CallbackInfo ci) {
        EnderDragon dragon = (EnderDragon) (Object) this;
        if (!deathLocationCaptured) {
            deathGroundX = dragon.getX();
            deathGroundY = dragon.getY();
            deathGroundZ = dragon.getZ();
            deathGroundYaw = dragon.getYRot();
            deathLocationCaptured = true;
        }

        dragon.setPos(deathGroundX, deathGroundY, deathGroundZ);
        dragon.setDeltaMovement(net.minecraft.world.phys.Vec3.ZERO);
        dragon.setYRot(deathGroundYaw);
        dragon.setYHeadRot(deathGroundYaw);
        dragon.yBodyRot = deathGroundYaw;
        dragon.yRotO = deathGroundYaw;
        dragon.yBodyRotO = deathGroundYaw;
        dragon.yHeadRotO = deathGroundYaw;

        customDeathTicks++;

        if (customDeathTicks == 1) {
            if (!dragon.level().isClientSide()) {
                dragon.level().broadcastEntityEvent(dragon, (byte) 81);
                dragon.level().playSound(null, deathGroundX, deathGroundY, deathGroundZ, com.newera.docs.sound.ModSounds.ENDER_DRAGON_DEATH, net.minecraft.sounds.SoundSource.HOSTILE, 5.0F, 1.0F);
            }
            triggerClientAction((byte) 81);
        }

        // Hold dragonDeathTime so entity is not removed before the 240-tick (12.0s) animation completes
        if (customDeathTicks < 240 && dragon.dragonDeathTime >= 199) {
            dragon.dragonDeathTime = 199;
        }
    }

    @org.spongepowered.asm.mixin.injection.Redirect(
        method = "tickDeath",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/boss/enderdragon/EnderDragon;move(Lnet/minecraft/world/entity/MoverType;Lnet/minecraft/world/phys/Vec3;)V")
    )
    private void redirectDeathMove(EnderDragon dragon, net.minecraft.world.entity.MoverType moverType, net.minecraft.world.phys.Vec3 vec) {
        // Suppress vanilla death upward movement! Dragon stays grounded.
    }

    @org.spongepowered.asm.mixin.injection.Redirect(
        method = "tickDeath",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;globalLevelEvent(ILnet/minecraft/core/BlockPos;I)V"),
        require = 0
    )
    private void redirectDeathGlobalSound(net.minecraft.world.level.Level level, int eventId, net.minecraft.core.BlockPos pos, int data) {
        // Suppress vanilla death sound event (1028) so ModSounds.ENDER_DRAGON_DEATH plays cleanly
    }



    @Inject(method = "knockBack", at = @At("HEAD"), cancellable = true)
    private void onKnockBack(net.minecraft.server.level.ServerLevel level, java.util.List<net.minecraft.world.entity.Entity> entities, CallbackInfo ci) {
        if (getFightStage() >= 1) {
            ci.cancel();
        }
    }

    @Inject(method = "hurt(Lnet/minecraft/server/level/ServerLevel;Ljava/util/List;)V", at = @At("HEAD"), cancellable = true)
    private void onHurtCollision(net.minecraft.server.level.ServerLevel level, java.util.List<net.minecraft.world.entity.Entity> entities, CallbackInfo ci) {
        if (getFightStage() >= 1) {
            ci.cancel();
        }
    }

    @Inject(method = "checkWalls", at = @At("HEAD"), cancellable = true)
    private void onCheckWalls(net.minecraft.server.level.ServerLevel level, net.minecraft.world.phys.AABB aabb, CallbackInfoReturnable<Boolean> cir) {
        if (getFightStage() >= 1) {
            cir.setReturnValue(false);
        }
    }

    @org.spongepowered.asm.mixin.injection.Redirect(
        method = "checkWalls",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;removeBlock(Lnet/minecraft/core/BlockPos;Z)Z")
    )
    private boolean redirectRemoveBlockInCheckWalls(net.minecraft.server.level.ServerLevel level, net.minecraft.core.BlockPos pos, boolean drop) {
        net.minecraft.world.level.block.state.BlockState state = level.getBlockState(pos);
        if (state.is(net.minecraft.tags.BlockTags.DRAGON_IMMUNE)
                || state.is(net.minecraft.world.level.block.Blocks.END_STONE_BRICKS)
                || state.is(net.minecraft.world.level.block.Blocks.END_STONE_BRICK_STAIRS)
                || state.is(net.minecraft.world.level.block.Blocks.END_STONE_BRICK_SLAB)
                || state.is(net.minecraft.world.level.block.Blocks.END_STONE_BRICK_WALL)
                || state.is(net.minecraft.world.level.block.Blocks.END_ROD)
                || state.is(net.minecraft.world.level.block.Blocks.OBSIDIAN)
                || state.is(net.minecraft.world.level.block.Blocks.BEDROCK)) {
            return false;
        }
        return level.removeBlock(pos, drop);
    }


    @Override
    public int getFightStage() { 
        return ((EnderDragon) (Object) this).getEntityData().get(FIGHT_STAGE); 
    }

    @Inject(method = "getAmbientSound", at = @At("HEAD"), cancellable = true, require = 0)
    protected void onGetAmbientSound(CallbackInfoReturnable<net.minecraft.sounds.SoundEvent> cir) {
        cir.setReturnValue(null);
    }

    @Inject(method = "getHurtSound", at = @At("HEAD"), cancellable = true, require = 0)
    protected void onGetHurtSound(net.minecraft.world.damagesource.DamageSource damageSource, CallbackInfoReturnable<net.minecraft.sounds.SoundEvent> cir) {
        cir.setReturnValue(null);
    }

    @Inject(method = "getSoundVolume", at = @At("HEAD"), cancellable = true, require = 0)
    protected void onGetSoundVolume(CallbackInfoReturnable<Float> cir) {
        cir.setReturnValue(0.0F); // Mute vanilla sounds if they use getSoundVolume
    }

    @Inject(method = "onFlap", at = @At("HEAD"), cancellable = true)
    private void onDragonFlap(CallbackInfo ci) {
        ci.cancel(); // Cưỡng chế tắt 100% tiếng flap của vanilla!
    }

    @Inject(method = "isPickable", at = @At("HEAD"), cancellable = true)
    private void onIsPickable(CallbackInfoReturnable<Boolean> cir) {
        if (getFightStage() >= 1) {
            cir.setReturnValue(true);
        }
    }

    
    @Override
    public void setFightStage(int stage) { 
        EnderDragon dragon = (EnderDragon) (Object) this;
        dragon.getEntityData().set(FIGHT_STAGE, stage); 
        if (!dragon.level().isClientSide()) {
            DragonAIController.onFightStageChanged(dragon, this, stage);
        }
    }

    @Override
    public int getForcedBasePose() {
        return ((EnderDragon) (Object) this).getEntityData().get(FORCED_BASE_POSE);
    }

    @Override
    public void setForcedBasePose(int pose) {
        ((EnderDragon) (Object) this).getEntityData().set(FORCED_BASE_POSE, pose);
    }

    @Inject(method = "createAttributes", at = @At("RETURN"), cancellable = true)
    private static void onCreateAttributes(CallbackInfoReturnable<AttributeSupplier.Builder> cir) {
        cir.setReturnValue(cir.getReturnValue()
            .add(Attributes.MAX_HEALTH, 600.0D)
            .add(Attributes.MOVEMENT_SPEED, 0.5D)
            .add(Attributes.ARMOR, 10.0D)
            .add(Attributes.ATTACK_DAMAGE, 15.0D)
            .add(Attributes.FOLLOW_RANGE, 100.0D)
            .add(Attributes.STEP_HEIGHT, 3.0D));
    }

    @Inject(method = "checkCrystals", at = @At("HEAD"), cancellable = true)
    private void onCheckCrystals(CallbackInfo ci) {
        EnderDragon dragon = (EnderDragon) (Object) this;
        if (getFightStage() != 2) {
            dragon.nearestCrystal = null;
            ci.cancel();
            return;
        }

        if (dragon.nearestCrystal != null) {
            if (dragon.nearestCrystal.isRemoved()) {
                dragon.nearestCrystal = null;
            } else if (dragon.nearestCrystal instanceof com.newera.docs.entity.animation.INEEndCrystalAnimation crystalMixin) {
                if (crystalMixin.getCrystalState() != com.newera.docs.entity.animation.INEEndCrystalAnimation.STATE_AWAKE) {
                    dragon.nearestCrystal = null;
                } else if (dragon.tickCount % 10 == 0) {
                    float maxAllowed = (getFightStage() == 2) ? 400.0F : dragon.getMaxHealth();
                    if (dragon.getHealth() < maxAllowed) {
                        dragon.setHealth(Math.min(dragon.getHealth() + 1.0F, maxAllowed));
                    }
                }
            }
        }

        if (dragon.getRandom().nextInt(10) == 0) {
            java.util.List<net.minecraft.world.entity.boss.enderdragon.EndCrystal> crystals = dragon.level().getEntitiesOfClass(
                net.minecraft.world.entity.boss.enderdragon.EndCrystal.class,
                dragon.getBoundingBox().inflate(32.0)
            );
            net.minecraft.world.entity.boss.enderdragon.EndCrystal nearest = null;
            double distance = Double.MAX_VALUE;

            for (net.minecraft.world.entity.boss.enderdragon.EndCrystal crystal : crystals) {
                if (crystal instanceof com.newera.docs.entity.animation.INEEndCrystalAnimation crystalMixin && crystalMixin.getCrystalState() == com.newera.docs.entity.animation.INEEndCrystalAnimation.STATE_AWAKE) {
                    double dist = crystal.distanceToSqr(dragon);
                    if (dist < distance) {
                        distance = dist;
                        nearest = crystal;
                    }
                }
            }

            dragon.nearestCrystal = nearest;
        }
        ci.cancel();
    }

    @Inject(method = "onCrystalDestroyed", at = @At("HEAD"), cancellable = true)
    private void onCrystalDestroyed(net.minecraft.server.level.ServerLevel serverLevel, net.minecraft.world.entity.boss.enderdragon.EndCrystal endCrystal, net.minecraft.core.BlockPos blockPos, DamageSource damageSource, CallbackInfo ci) {
        if (getFightStage() != 2) {
            ci.cancel();
        }
    }

    @Inject(method = "reallyHurt", at = @At("HEAD"), cancellable = true)
    private void onReallyHurt(net.minecraft.server.level.ServerLevel level, DamageSource damageSource, float amount, CallbackInfo ci) {

        if (getFightStage() == 0 && DragonPhase0.isLandingInvulnerable()) {
            ci.cancel();
            return;
        }

        if (getFightStage() == 15 || getFightStage() == 25) {
            ci.cancel();
            return;
        }

        EnderDragon dragon = (EnderDragon) (Object) this;
        float currentHealth = dragon.getHealth();
        if (amount > 0 && !dragon.level().isClientSide()) {
            dragon.level().playSound(null, dragon.getX(), dragon.getY(), dragon.getZ(), com.newera.docs.sound.ModSounds.ENDER_DRAGON_HURT, net.minecraft.sounds.SoundSource.HOSTILE, 5.0F, 1.0F);
        }

        if (getFightStage() == 1 && currentHealth <= 400.0F) {
            ci.cancel();
            if (com.newera.docs.entity.boss.dragon.DragonPhase1.instance != null) {
                com.newera.docs.entity.boss.dragon.DragonPhase1.instance.requestPhaseExit(dragon, this);
            }
            return;
        }

        if (getFightStage() == 1 && currentHealth - amount <= 400.0F) {
            dragon.setHealth(400.0F);
            ci.cancel();
            if (com.newera.docs.entity.boss.dragon.DragonPhase1.instance != null) {
                com.newera.docs.entity.boss.dragon.DragonPhase1.instance.requestPhaseExit(dragon, this);
            }
            return;
        }
        
        if (getFightStage() == 2 && currentHealth <= 200.0F) {
            ci.cancel();
            return;
        }

        if (getFightStage() == 2 && currentHealth - amount <= 200.0F) {
            float allowedDamage = currentHealth - 200.0F;
            if (allowedDamage > 0) {
                dragon.setHealth(200.0F);
            } else {
                dragon.setHealth(200.0F);
            }
            ci.cancel();
            if (DragonPhase2.instance != null) {
                DragonPhase2.instance.requestPhaseExit(dragon, this);
            } else {
                setFightStage(25);
            }
            return;
        }
    }

    @Inject(method = "aiStep", at = @At("HEAD"))
    private void onAiStep(CallbackInfo ci) {
        EnderDragon dragon = (EnderDragon) (Object) this;
        if (dragon.getPhaseManager().getCurrentPhase().getPhase() == net.minecraft.world.entity.boss.enderdragon.phases.EnderDragonPhase.DYING) {
            if (dragon.getHealth() > 0.0F) {
                dragon.setHealth(0.0F);
            }
            return;
        }
        if (dragon.isDeadOrDying()) {
            return;
        }
        if (!dragon.level().isClientSide()) {
            DragonAIController.tick(dragon, this);
            if (getFightStage() == 0) {
                DragonPhase0.applyMovement(dragon);
            } else if (getFightStage() == 1) {
                DragonPhase1.applyMovement(dragon);
            } else if (getFightStage() == 15) {
                DragonPhase1_5.applyMovement(dragon);
            } else if (getFightStage() == 2) {
                DragonPhase2.applyMovement(dragon);
            } else if (getFightStage() == 25) {
                DragonPhase2_5.applyMovement(dragon);
            } else if (getFightStage() == 3) {
                DragonPhase3.applyMovement(dragon);
            }
        }
    }

    @org.spongepowered.asm.mixin.injection.Redirect(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/boss/enderdragon/phases/DragonPhaseInstance;doServerTick(Lnet/minecraft/server/level/ServerLevel;)V"))
    private void redirectDoServerTick(net.minecraft.world.entity.boss.enderdragon.phases.DragonPhaseInstance instance, net.minecraft.server.level.ServerLevel level) {
    }

    @org.spongepowered.asm.mixin.injection.Redirect(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/boss/enderdragon/phases/DragonPhaseInstance;getFlyTargetLocation()Lnet/minecraft/world/phys/Vec3;"))
    private net.minecraft.world.phys.Vec3 redirectGetFlyTargetLocation(net.minecraft.world.entity.boss.enderdragon.phases.DragonPhaseInstance instance) {
        return null;
    }

    @org.spongepowered.asm.mixin.injection.Redirect(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;playLocalSound(DDDLnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FFZ)V"))
    private void redirectPlayLocalSound(net.minecraft.world.level.Level level, double x, double y, double z, net.minecraft.sounds.SoundEvent sound, net.minecraft.sounds.SoundSource source, float volume, float pitch, boolean distanceDelay) {
    }

    @org.spongepowered.asm.mixin.injection.Redirect(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/boss/enderdragon/EnderDragon;tickPart(Lnet/minecraft/world/entity/boss/enderdragon/EnderDragonPart;DDD)V"))
    private void redirectTickPart(EnderDragon dragon, net.minecraft.world.entity.boss.enderdragon.EnderDragonPart part, double x, double y, double z) {
        // Suppress vanilla tickPart: custom hitbox system in onAiStepTail handles accurate positioning
    }
    
    
    @Unique
    private int clientActionTicks = 0;
    @Unique
    private byte currentClientAction = 0;
    @Unique
    private int clientBasePose = 0; // 0 = ON_AIR, 1 = HOVER, 2 = ON_GROUND
    @Unique
    private int walkSmoothingTicks = 0;

    @Override
    public void triggerClientAction(byte actionByte) {
        EnderDragon dragon = (EnderDragon) (Object) this;
        int currentTick = dragon.tickCount;
        
        switch (actionByte) {
            case 60:
                stopAttackActions();
                currentClientAction = 60;
                clientActionTicks = 20;
                biteAnimationState.start(currentTick);
                onGroundPoseAnimationState.startIfStopped(currentTick);
                if (dragon.level().isClientSide()) {
                    dragon.level().playLocalSound(dragon.getX(), dragon.getY(), dragon.getZ(), com.newera.docs.sound.ModSounds.ENDER_DRAGON_BITE, net.minecraft.sounds.SoundSource.HOSTILE, 5.0F, 1.0F, false);
                }
                break;

            case 61:
                stopAttackActions();
                stopTransitionalActions();
                currentClientAction = 61;
                clientActionTicks = 80;
                clientBasePose = 2;
                onGroundPoseAnimationState.startIfStopped(currentTick);
                roarAnimationState.start(currentTick);
                break;

            case 62:
                stopAttackActions();
                currentClientAction = 62;
                clientActionTicks = 40;
                pounceLeftAnimationState.start(currentTick);
                onGroundPoseAnimationState.startIfStopped(currentTick);
                break;

            case 63:
            case 69:
                stopAttackActions();
                currentClientAction = actionByte;
                clientActionTicks = 40;
                pounceRightAnimationState.start(currentTick);
                onGroundPoseAnimationState.startIfStopped(currentTick);
                break;

            case 64:
                stopAttackActions();
                currentClientAction = 64;
                clientActionTicks = 58;
                stompAnimationState.start(currentTick);
                onGroundPoseAnimationState.startIfStopped(currentTick);
                break;

            case 65:
                stopAttackActions();
                stopTransitionalActions();
                onAirPoseAnimationState.stop();
                hoverPoseAnimationState.stop();
                onGroundPoseAnimationState.stop();
                currentClientAction = 65;
                clientActionTicks = 30;
                clientBasePose = 1;
                decelerateAnimationState.start(currentTick);
                flyAnimationState.startIfStopped(currentTick);
                break;

            case 66:
                stopAttackActions();
                stopTransitionalActions();
                hoverPoseAnimationState.stop();
                onAirPoseAnimationState.stop();
                flyAnimationState.stop();
                currentClientAction = 66;
                clientActionTicks = 20;
                clientBasePose = 2;
                landingAnimationState.start(currentTick);
                break;

            case 67:
                stopAttackActions();
                stopTransitionalActions();
                onAirPoseAnimationState.stop();
                onGroundPoseAnimationState.stop();
                walkAnimationState.stop();
                currentClientAction = 0;
                clientActionTicks = 0;
                clientBasePose = 1;
                flyAnimationState.startIfStopped(currentTick);
                hoverPoseAnimationState.startIfStopped(currentTick);
                break;

            case 68:
                stopAttackActions();
                stopTransitionalActions();
                hoverPoseAnimationState.stop();
                onGroundPoseAnimationState.stop();
                walkAnimationState.stop();
                currentClientAction = 0;
                clientActionTicks = 0;
                clientBasePose = 0;
                onAirPoseAnimationState.startIfStopped(currentTick);
                flyAnimationState.startIfStopped(currentTick);
                break;

            case 70:
                stopAttackActions();
                currentClientAction = 70;
                clientActionTicks = 55;
                onGroundPoseAnimationState.startIfStopped(currentTick);
                breathCloseAnimationState.start(currentTick);
                break;

            case 71:
                stopAttackActions();
                currentClientAction = 71;
                clientActionTicks = 25;
                onGroundPoseAnimationState.startIfStopped(currentTick);
                breathAnimationState.start(currentTick);
                if (dragon.level().isClientSide()) {
                    dragon.level().playLocalSound(dragon.getX(), dragon.getY(), dragon.getZ(), com.newera.docs.sound.ModSounds.ENDER_DRAGON_BREATH, net.minecraft.sounds.SoundSource.HOSTILE, 5.0F, 1.0F, false);
                }
                break;

            case 72:
                stopAttackActions();
                onGroundPoseAnimationState.stop();
                walkAnimationState.stop();
                hoverPoseAnimationState.stop();
                onAirPoseAnimationState.stop();
                flyAnimationState.stop();
                currentClientAction = 72;
                clientActionTicks = 20;
                clientBasePose = 1;
                takeoffAnimationState.start(currentTick);
                break;

            case 73:
                stopAttackActions();
                onAirPoseAnimationState.stop();
                hoverPoseAnimationState.stop();
                flyAnimationState.stop();
                currentClientAction = 73;
                clientActionTicks = 10;
                fallingAnimationState.start(currentTick);
                break;

            case 74:
                stopAttackActions();
                stopTransitionalActions();
                hoverPoseAnimationState.stop();
                onGroundPoseAnimationState.stop();
                currentClientAction = 74;
                clientActionTicks = 40;
                clientBasePose = 0;
                accelerateAnimationState.start(currentTick);
                flyAnimationState.startIfStopped(currentTick);
                break;

            case 75:
                stopAttackActions();
                stopTransitionalActions();
                onAirPoseAnimationState.stop();
                onGroundPoseAnimationState.stop();
                currentClientAction = 75;
                clientActionTicks = 80;
                clientBasePose = 1;
                hoverPoseAnimationState.startIfStopped(currentTick);
                flyAnimationState.startIfStopped(currentTick);
                roarHoverAnimationState.start(currentTick);
                break;

            case 76:
                stopAttackActions();
                currentClientAction = 76;
                clientActionTicks = 25;
                clientBasePose = 1;
                hoverPoseAnimationState.startIfStopped(currentTick);
                flyAnimationState.startIfStopped(currentTick);
                hoverBreathAnimationState.start(currentTick);
                if (dragon.level().isClientSide()) {
                    dragon.level().playLocalSound(dragon.getX(), dragon.getY(), dragon.getZ(), com.newera.docs.sound.ModSounds.ENDER_DRAGON_BREATH, net.minecraft.sounds.SoundSource.HOSTILE, 5.0F, 1.0F, false);
                }
                break;

            case 77:
                stopAttackActions();
                currentClientAction = 77;
                clientActionTicks = 25;
                clientBasePose = 0;
                onAirPoseAnimationState.startIfStopped(currentTick);
                flyAnimationState.startIfStopped(currentTick);
                flyBreathAnimationState.start(currentTick);
                if (dragon.level().isClientSide()) {
                    dragon.level().playLocalSound(dragon.getX(), dragon.getY(), dragon.getZ(), com.newera.docs.sound.ModSounds.ENDER_DRAGON_BREATH, net.minecraft.sounds.SoundSource.HOSTILE, 5.0F, 1.0F, false);
                }
                break;

            case 78:
                stopAttackActions();
                currentClientAction = 78;
                clientActionTicks = 90;
                clientBasePose = 0;
                onAirPoseAnimationState.startIfStopped(currentTick);
                flyAnimationState.startIfStopped(currentTick);
                flyLongBreathAnimationState.start(currentTick);
                break;

            case 79:
                stopAttackActions();
                currentClientAction = 79;
                clientActionTicks = 4;
                onGroundPoseAnimationState.startIfStopped(currentTick);
                teleportAnimationState.start(currentTick);
                break;

            case 80:
                stopAttackActions();
                stopTransitionalActions();
                currentClientAction = 80;
                clientActionTicks = 80;
                clientBasePose = 2;
                onGroundPoseAnimationState.startIfStopped(currentTick);
                roarCurseAnimationState.start(currentTick);
                if (dragon.level().isClientSide()) {
                    dragon.level().playLocalSound(dragon.getX(), dragon.getY(), dragon.getZ(), com.newera.docs.sound.ModSounds.ENDER_DRAGON_ROAR, net.minecraft.sounds.SoundSource.HOSTILE, 5.0F, 0.8F, false);
                }
                break;

            case 81:
                stopAllActions();
                onGroundPoseAnimationState.stop();
                walkAnimationState.stop();
                flyAnimationState.stop();
                hoverPoseAnimationState.stop();
                onAirPoseAnimationState.stop();
                currentClientAction = 81;
                clientActionTicks = 240;
                deathAnimationState.start(currentTick);
                break;
        }
    }

    @Unique
    private void stopTransitionalActions() {
        takeoffAnimationState.stop();
        fallingAnimationState.stop();
        landingAnimationState.stop();
        decelerateAnimationState.stop();
        accelerateAnimationState.stop();
    }

    @Unique
    private void stopAttackActions() {
        biteAnimationState.stop();
        roarAnimationState.stop();
        roarHoverAnimationState.stop();
        pounceLeftAnimationState.stop();
        pounceRightAnimationState.stop();
        stompAnimationState.stop();
        breathCloseAnimationState.stop();
        breathAnimationState.stop();
        lookingAnimationState.stop();
        hoverBreathAnimationState.stop();
        flyBreathAnimationState.stop();
        flyLongBreathAnimationState.stop();
        teleportAnimationState.stop();
        roarCurseAnimationState.stop();
        rollAnimationState.stop();
    }

    @Unique
    private void stopAllActions() {
        stopAttackActions();
        stopTransitionalActions();
        roarAnimationState.stop();
        roarHoverAnimationState.stop();
        rollAnimationState.stop();
        deathAnimationState.stop();
    }

    @Inject(method = "aiStep", at = @At("TAIL"))
    private void onAiStepTail(CallbackInfo ci) {
        EnderDragon dragon = (EnderDragon) (Object) this;
        int stage = getFightStage();

        dragon.yBodyRot = dragon.getYRot();
        dragon.yHeadRot = dragon.getYRot();
        dragon.setYHeadRot(dragon.getYRot());

        if (!dragon.level().isClientSide()) {
            if (dragon.entityTags().contains("ground")) {
                dragon.getEntityData().set(FORCED_BASE_POSE, 2);
            } else if (dragon.entityTags().contains("hover")) {
                dragon.getEntityData().set(FORCED_BASE_POSE, 1);
            } else if (dragon.entityTags().contains("air")) {
                dragon.getEntityData().set(FORCED_BASE_POSE, 0);
            }

            if (dragon.isAlive() && dragon.tickCount % 100 == 0 && dragon.getRandom().nextFloat() < 0.2F) {
                dragon.level().playSound(null, dragon.getX(), dragon.getY(), dragon.getZ(), com.newera.docs.sound.ModSounds.ENDER_DRAGON_IDLE, net.minecraft.sounds.SoundSource.HOSTILE, 3.0F, 1.0F);
            }
        }

        int forcedPose = dragon.getEntityData().get(FORCED_BASE_POSE);
        if (forcedPose != -1) {
            clientBasePose = forcedPose;
        }

        if (clientActionTicks > 0) {
            clientActionTicks--;

            if (dragon.level().isClientSide() && currentClientAction == 70 && clientActionTicks == 50) {
                dragon.level().playLocalSound(dragon.getX(), dragon.getY(), dragon.getZ(), com.newera.docs.sound.ModSounds.ENDER_DRAGON_BREATH_CLOSE, net.minecraft.sounds.SoundSource.HOSTILE, 5.0F, 1.0F, false);
            }
            if (dragon.level().isClientSide() && currentClientAction == 78 && clientActionTicks == 80) {
                dragon.level().playLocalSound(dragon.getX(), dragon.getY(), dragon.getZ(), com.newera.docs.sound.ModSounds.ENDER_DRAGON_BREATH_CLOSE, net.minecraft.sounds.SoundSource.HOSTILE, 5.0F, 1.0F, false);
            }

            if (clientActionTicks == 0) {
                if (dragon.level().isClientSide()) {
                    switch (currentClientAction) {
                        case 60:
                            biteAnimationState.stop();
                            break;
                        case 61:
                            roarAnimationState.stop();
                            landingAnimationState.stop();
                            fallingAnimationState.stop();
                            clientBasePose = 2;
                            break;
                        case 62:
                            pounceLeftAnimationState.stop();
                            break;
                        case 63:
                        case 69:
                            pounceRightAnimationState.stop();
                            break;
                        case 64:
                            stompAnimationState.stop();
                            break;
                        case 65:
                            decelerateAnimationState.stop();
                            clientBasePose = 1; // Transitions to HOVER
                            flyAnimationState.startIfStopped(dragon.tickCount);
                            hoverPoseAnimationState.startIfStopped(dragon.tickCount);
                            break;
                        case 66:
                            landingAnimationState.stop();
                            fallingAnimationState.stop();
                            clientBasePose = 2; // Transitions to ON_GROUND
                            onGroundPoseAnimationState.startIfStopped(dragon.tickCount);
                            break;
                        case 70:
                            breathCloseAnimationState.stop();
                            break;
                        case 71:
                            breathAnimationState.stop();
                            break;
                        case 72:
                            takeoffAnimationState.stop();
                            clientBasePose = 1; // Transitions to HOVER
                            flyAnimationState.startIfStopped(dragon.tickCount);
                            hoverPoseAnimationState.startIfStopped(dragon.tickCount);
                            break;
                        case 73:
                            // Hold the falling dive pose until landing (case 66)
                            break;
                        case 74:
                            accelerateAnimationState.stop();
                            clientBasePose = 0; // Transitions to ON_AIR
                            flyAnimationState.startIfStopped(dragon.tickCount);
                            onAirPoseAnimationState.startIfStopped(dragon.tickCount);
                            break;
                        case 75:
                            roarHoverAnimationState.stop();
                            clientBasePose = 1;
                            break;
                        case 76:
                            hoverBreathAnimationState.stop();
                            clientBasePose = 1;
                            break;
                        case 77:
                            flyBreathAnimationState.stop();
                            clientBasePose = 0;
                            break;
                        case 78:
                            flyLongBreathAnimationState.stop();
                            clientBasePose = 0;
                            break;
                        case 79:
                            teleportAnimationState.stop();
                            break;
                        case 80:
                            roarCurseAnimationState.stop();
                            clientBasePose = 2;
                            break;
                        case 81:
                            deathAnimationState.stop();
                            break;
                    }
                } else {
                    if (currentClientAction == 65 || currentClientAction == 72 || currentClientAction == 75 || currentClientAction == 76) {
                        clientBasePose = 1;
                    } else if (currentClientAction == 61 || currentClientAction == 66 || currentClientAction == 80) {
                        clientBasePose = 2;
                    } else if (currentClientAction == 74 || currentClientAction == 77 || currentClientAction == 78) {
                        clientBasePose = 0;
                    }
                }
                currentClientAction = 0;
            }
        }

        // Track movement ticks for walk animation & hitbox bobbing
        double dx = dragon.getX() - dragon.xo;
        double dz = dragon.getZ() - dragon.zo;
        if (dx * dx + dz * dz > 0.0005) {
            walkSmoothingTicks = 6;
        } else if (walkSmoothingTicks > 0) {
            walkSmoothingTicks--;
        }

        if (!dragon.level().isClientSide() && stage == 2 && dragon.getHealth() > 400.0F) {
            dragon.setHealth(400.0F);
        }

        if (dragon.level().isClientSide()) {
            boolean isAirAction = (currentClientAction == 72 || takeoffAnimationState.isStarted() || currentClientAction == 74 || accelerateAnimationState.isStarted());
            if (isAirAction) {
                if (currentClientAction == 74 || accelerateAnimationState.isStarted()) {
                    clientBasePose = 0;
                } else {
                    clientBasePose = 1;
                }
                onGroundPoseAnimationState.stop();
                walkAnimationState.stop();
                lookingAnimationState.stop();
                hoverPoseAnimationState.stop();
                onAirPoseAnimationState.stop();
                rollAnimationState.stop();
                if (currentClientAction == 72 || takeoffAnimationState.isStarted()) {
                    flyAnimationState.stop();
                } else {
                    flyAnimationState.startIfStopped(dragon.tickCount);
                }
            } else if (stage == 1 || stage == 3 || (clientBasePose == 2 && stage != 15 && stage != 2)) {
                clientBasePose = 2;
                onGroundPoseAnimationState.startIfStopped(dragon.tickCount);
                rollAnimationState.stop();
                flyAnimationState.stop();
                hoverPoseAnimationState.stop();
                onAirPoseAnimationState.stop();

                boolean isAttacking = (clientActionTicks > 0 && (currentClientAction == 60 || currentClientAction == 61 || currentClientAction == 62 || currentClientAction == 63 || currentClientAction == 64 || currentClientAction == 69 || currentClientAction == 70 || currentClientAction == 71 || currentClientAction == 79 || currentClientAction == 80 || currentClientAction == 81));
                if (isAttacking) {
                    walkAnimationState.stop();
                    lookingAnimationState.stop();
                } else {
                    lookingAnimationState.startIfStopped(dragon.tickCount);
                    if (walkSmoothingTicks > 0) {
                        walkAnimationState.startIfStopped(dragon.tickCount);
                    } else {
                        walkAnimationState.stop();
                    }
                }
            } else {
                walkAnimationState.stop();
                
                if (currentClientAction == 66 || currentClientAction == 61) {
                    flyAnimationState.stop();
                    onAirPoseAnimationState.stop();
                    hoverPoseAnimationState.stop();
                    rollAnimationState.stop();
                    if (currentClientAction != 61 && currentClientAction != 66) {
                        onGroundPoseAnimationState.startIfStopped(dragon.tickCount);
                        lookingAnimationState.startIfStopped(dragon.tickCount);
                    } else {
                        onGroundPoseAnimationState.stop();
                        lookingAnimationState.stop();
                    }
                } else if (currentClientAction == 73 || fallingAnimationState.isStarted()) {
                    // Dive / Falling: hold falling pose, STOP fly, STOP base poses
                    flyAnimationState.stop();
                    onAirPoseAnimationState.stop();
                    hoverPoseAnimationState.stop();
                    lookingAnimationState.stop();
                    rollAnimationState.stop();
                    onGroundPoseAnimationState.stop();
                } else {
                    onGroundPoseAnimationState.stop();
                    flyAnimationState.startIfStopped(dragon.tickCount);

                    if (currentClientAction == 65 || currentClientAction == 72 || currentClientAction == 74) {
                        onAirPoseAnimationState.stop();
                        hoverPoseAnimationState.stop();
                        lookingAnimationState.stop();
                        rollAnimationState.stop();
                    } else if (currentClientAction == 75 || clientBasePose == 1) {
                        hoverPoseAnimationState.startIfStopped(dragon.tickCount);
                        onAirPoseAnimationState.stop();
                        rollAnimationState.stop();
                        if (currentClientAction == 75) {
                            lookingAnimationState.stop();
                        } else {
                            lookingAnimationState.startIfStopped(dragon.tickCount);
                        }
                    } else {
                        onAirPoseAnimationState.startIfStopped(dragon.tickCount);
                        hoverPoseAnimationState.stop();
                        lookingAnimationState.stop();
                        rollAnimationState.startIfStopped(dragon.tickCount);
                    }
                }
            }

            boolean isAttackingOrActing = (clientActionTicks > 0 && (currentClientAction == 60 || currentClientAction == 61 || currentClientAction == 62 || currentClientAction == 63 || currentClientAction == 64 || currentClientAction == 69 || currentClientAction == 70 || currentClientAction == 71 || currentClientAction == 76 || currentClientAction == 77 || currentClientAction == 78 || currentClientAction == 79 || currentClientAction == 80 || currentClientAction == 81));
            boolean isSpecialAction = (currentClientAction == 65 || currentClientAction == 66 || currentClientAction == 72 || currentClientAction == 73 || currentClientAction == 74 || currentClientAction == 75 || fallingAnimationState.isStarted());

            boolean noLook = dragon.entityTags().contains("no_look");
            boolean isLookingAllowed = (stage == 1 || stage == 3 || clientBasePose == 1 || clientBasePose == 2) && !isAttackingOrActing && !isSpecialAction && currentClientAction != 61 && currentClientAction != 75 && currentClientAction != 80 && currentClientAction != 81 && !noLook;
            if (noLook) {
                smoothLookYaw = 0.0F;
                smoothLookPitch = 0.0F;
            } else if (isLookingAllowed) {
                net.minecraft.world.entity.player.Player target = null;
                double nearestDist = 36.0D * 36.0D;
                for (net.minecraft.world.entity.player.Player p : dragon.level().players()) {
                    if (!p.isCreative() && !p.isSpectator() && p.isAlive()) {
                        double d = dragon.distanceToSqr(p);
                        if (d <= nearestDist) {
                            nearestDist = d;
                            target = p;
                        }
                    }
                }
                float targetLookYaw = 0.0F;
                float targetLookPitch = 0.0F;
                if (target != null) {
                    double dxP = target.getX() - dragon.getX();
                    double dyP = target.getEyeY() - (dragon.getY() + 3.5D);
                    double dzP = target.getZ() - dragon.getZ();
                    double distH = Math.sqrt(dxP * dxP + dzP * dzP);
                    float targetYawDeg = (float)(net.minecraft.util.Mth.atan2(dzP, dxP) * (180.0D / Math.PI)) - 90.0F;
                    float diffYaw = net.minecraft.util.Mth.wrapDegrees(targetYawDeg - dragon.getYRot());

                    if (Math.abs(diffYaw) <= 90.0F) {
                        targetLookYaw = net.minecraft.util.Mth.clamp(diffYaw, -55.0F, 55.0F);
                        float pitchDeg = (float)(-(net.minecraft.util.Mth.atan2(dyP, distH) * (180.0D / Math.PI)));
                        targetLookPitch = net.minecraft.util.Mth.clamp(pitchDeg, -35.0F, 35.0F);
                    }
                }
                smoothLookYaw = net.minecraft.util.Mth.approachDegrees(smoothLookYaw, targetLookYaw, 5.0F);
                smoothLookPitch = net.minecraft.util.Mth.approach(smoothLookPitch, targetLookPitch, 4.0F);
            } else {
                smoothLookYaw = net.minecraft.util.Mth.approachDegrees(smoothLookYaw, 0.0F, 8.0F);
                smoothLookPitch = net.minecraft.util.Mth.approach(smoothLookPitch, 0.0F, 6.0F);
            }

            boolean isRollAllowed = ((stage == 0 || stage == 15 || stage == 2) && clientBasePose == 0 && !isSpecialAction && currentClientAction != 61 && currentClientAction != 75 && !isAttackingOrActing);
            if (isRollAllowed) {
                float rot2 = net.minecraft.util.Mth.wrapDegrees(dragon.flightHistory.get(4).yRot() - dragon.flightHistory.get(10).yRot());
                float targetRoll = net.minecraft.util.Mth.clamp(-rot2 * 2.2F, -30.0F, 30.0F);
                smoothFlightRoll = net.minecraft.util.Mth.lerp(0.25F, smoothFlightRoll, targetRoll);
            } else {
                smoothFlightRoll = net.minecraft.util.Mth.lerp(0.3F, smoothFlightRoll, 0.0F);
            }
        }

        updateCustomHitboxes(dragon, stage);
    }

    @Unique
    private void updateCustomHitboxes(EnderDragon dragon, int stage) {
        net.minecraft.world.entity.boss.enderdragon.EnderDragonPart[] parts = dragon.getSubEntities();
        if (parts == null || parts.length < 8) return;

        int pose = 0;
        int forcedPose = dragon.getEntityData().get(FORCED_BASE_POSE);
        if (forcedPose != -1) {
            pose = forcedPose;
        } else if (dragon.level().isClientSide()) {
            pose = clientBasePose;
        } else if (stage == 1 || stage == 3 || DragonPhase0.isLandingInvulnerable()) {
            pose = 2; // ON_GROUND
        } else if (DragonPhase0.isHovering() || stage == 15) {
            pose = 1; // HOVER
        } else {
            pose = 0; // ON_AIR
        }

        double cx = dragon.getX();
        double cy = dragon.getY();
        double cz = dragon.getZ();
        float yaw = dragon.getYRot();
        float yawRad = yaw * 0.017453292F;
        float cosYaw = net.minecraft.util.Mth.cos(yawRad);
        float sinYaw = net.minecraft.util.Mth.sin(yawRad);

        // White Box: Overall dragon bounding box (matches root box [96, 136, 140] -> 6.0 x 8.5 x 8.75)
        double boxRadius = (pose == 1) ? 5.5D : 4.5D;
        double boxHeight = (pose == 1) ? 14.0D : 8.5D;
        dragon.setBoundingBox(new net.minecraft.world.phys.AABB(
            cx - boxRadius, cy, cz - boxRadius,
            cx + boxRadius, cy + boxHeight, cz + boxRadius
        ));

        // Green Boxes: Sub-entities (head, neck, body, tail1, tail2, tail3, wing1, wing2)
        // Geometry: forward (+front/-back), up (+height), right (+right/-left)
        float lookYaw = this.smoothLookYaw;
        float lookPitch = this.smoothLookPitch;
        float roll = (pose == 0) ? this.smoothFlightRoll : 0.0F;
        float rollRad = roll * 0.017453292F;
        float sinRoll = net.minecraft.util.Mth.sin(rollRad);

        double hf, hy, hr, hw, hh;
        double nf, ny, nr, nw, nh;
        double bf, by, br, bw, bh;
        double t1f, t1y, t1r, t1w, t1h;
        double t2f, t2y, t2r, t2w, t2h;
        double t3f, t3y, t3r, t3w, t3h;
        double w1f, w1y, w1r, ww, wh;
        double w2f, w2y, w2r;

        if (pose == 2) {
            // ON_GROUND (Phase 1, or Tags:["ground"])
            hf = 4.40; hy = 6.80; hr = 0.0; hw = 2.4; hh = 2.0;
            nf = 3.50; ny = 3.80; nr = 0.0; nw = 2.4; nh = 2.5;
            bf = 0.00; by = 2.00; br = 0.0; bw = 5.5; bh = 5.0;
            t1f = -4.40; t1y = 1.70; t1r = 0.0; t1w = 3.6; t1h = 3.2;
            t2f = -8.40; t2y = 1.00; t2r = 0.0; t2w = 3.4; t2h = 3.0;
            t3f = -13.00; t3y = 0.50; t3r = 0.0; t3w = 3.2; t3h = 2.8;
            w1f = -2.00; w1y = 3.00; w1r = 6.50; ww = 9.0; wh = 3.5;
            w2f = -2.00; w2y = 3.00; w2r = -6.50;
        } else if (pose == 1) {
            // HOVER (Hovering in air, or Tags:["hover"])
            hf = 2.70; hy = 5.50; hr = 0.0; hw = 2.4; hh = 2.0;
            nf = 1.30; ny = 3.50; nr = 0.0; nw = 2.4; nh = 2.5;
            bf = -1.50; by = 1.20; br = 0.0; bw = 5.5; bh = 5.0;
            t1f = -3.50; t1y = 0.00; t1r = 0.0; t1w = 3.6; t1h = 3.2;
            t2f = -4.50; t2y = -2.00; t2r = 0.0; t2w = 3.4; t2h = 3.0;
            t3f = -5.00; t3y = -4.00; t3r = 0.0; t3w = 3.2; t3h = 2.8;
            w1f = -4.50; w1y = 2.80; w1r = 9.00; ww = 13.0; wh = 2.5;
            w2f = -4.50; w2y = 2.80; w2r = -9.00;
        } else {
            // ON_AIR (Phase 0 flight, or Tags:["air"])
            hf = 7.20; hy = 2.70; hr = 0.0; hw = 2.4; hh = 2.0;
            nf = 4.50; ny = 2.00; nr = 0.0; nw = 2.4; nh = 2.5;
            bf = 0.00; by = 2.00; br = 0.0; bw = 5.5; bh = 5.0;
            t1f = -4.80; t1y = 2.50; t1r = 0.0; t1w = 3.6; t1h = 3.2;
            t2f = -9.00; t2y = 2.60; t2r = 0.0; t2w = 3.4; t2h = 3.0;
            t3f = -13.60; t3y = 2.70; t3r = 0.0; t3w = 3.2; t3h = 2.8;
            w1f = -1.00; w1y = 3.50; w1r = 9.00; ww = 13.0; wh = 2.5;
            w2f = -1.00; w2y = 3.50; w2r = -9.00;
        }

        // Procedural Animation Offsets
        if (pose == 0) {
            // Wing flapping (up and down oscillation)
            double flapAngle = (dragon.tickCount * 0.35);
            double flapOffset = Math.sin(flapAngle) * 1.8;
            w1y += flapOffset;
            w2y += flapOffset;

            // Tail swaying based on flight history turning difference
            float turnDiff = 0.0F;
            try {
                turnDiff = net.minecraft.util.Mth.wrapDegrees(dragon.flightHistory.get(6).yRot() - dragon.getYRot());
            } catch (Exception ignored) {}
            float swayRad = net.minecraft.util.Mth.clamp(turnDiff, -45.0F, 45.0F) * 0.017453292F;
            t1r += Math.sin(swayRad) * 1.2;
            t2r += Math.sin(swayRad) * 2.5;
            t3r += Math.sin(swayRad) * 4.2;
            t2f += (1.0 - Math.cos(swayRad)) * 1.5;
            t3f += (1.0 - Math.cos(swayRad)) * 3.0;
        } else if (pose == 1) {
            // Hover gentle bobbing
            double hoverPhase = dragon.tickCount * 0.25;
            double hoverBob = Math.sin(hoverPhase) * 0.4;
            by += hoverBob;
            hy += hoverBob;
            w1y += Math.sin(dragon.tickCount * 0.3) * 1.0;
            w2y += Math.sin(dragon.tickCount * 0.3) * 1.0;
        } else if (pose == 2 && walkSmoothingTicks > 0) {
            // Ground walking bobbing & tail sway
            double walkCycle = dragon.tickCount * 0.35;
            double walkBob = Math.abs(Math.sin(walkCycle)) * 0.35;
            by += walkBob;
            hy += walkBob * 0.8;
            ny += walkBob * 0.9;
            double tailSway = Math.sin(walkCycle) * 0.7;
            t1r += tailSway * 0.5;
            t2r += tailSway * 1.0;
            t3r += tailSway * 1.5;
        }

        // Action Animation Offsets
        if (clientActionTicks > 0) {
            switch (currentClientAction) {
                case 60: { // BITE (total 15 ticks)
                    float prog = 1.0F - (clientActionTicks / 15.0F);
                    float strike = (float) Math.sin(prog * Math.PI);
                    hf += strike * 2.5;
                    hy -= strike * 1.6;
                    nf += strike * 1.5;
                    ny -= strike * 0.8;
                    bf += strike * 0.6;
                    break;
                }
                case 61: { // ROAR (total 80 ticks)
                    float prog = 1.0F - (clientActionTicks / 80.0F);
                    float roar = (float) Math.sin(Math.min(1.0F, prog * 1.25F) * Math.PI);
                    hy += roar * 2.2;
                    hf -= roar * 1.2;
                    ny += roar * 1.6;
                    nf -= roar * 0.7;
                    by += roar * 0.8;
                    w1y += roar * 1.5;
                    w2y += roar * 1.5;
                    break;
                }
                case 62: { // POUNCE LEFT (total 40 ticks)
                    float prog = 1.0F - (clientActionTicks / 40.0F);
                    float pounce = (float) Math.sin(prog * Math.PI);
                    bf += pounce * 2.0;
                    hf += pounce * 2.8;
                    hr -= pounce * 1.8;
                    nf += pounce * 1.8;
                    nr -= pounce * 1.0;
                    w1f += pounce * 1.5;
                    w2f += pounce * 1.5;
                    break;
                }
                case 63:
                case 69: { // POUNCE RIGHT (total 40 ticks)
                    float prog = 1.0F - (clientActionTicks / 40.0F);
                    float pounce = (float) Math.sin(prog * Math.PI);
                    bf += pounce * 2.0;
                    hf += pounce * 2.8;
                    hr += pounce * 1.8;
                    nf += pounce * 1.8;
                    nr += pounce * 1.0;
                    w1f += pounce * 1.5;
                    w2f += pounce * 1.5;
                    break;
                }
                case 64: { // STOMP (total 58 ticks)
                    float prog = 1.0F - (clientActionTicks / 58.0F);
                    if (prog < 0.52F) {
                        float rear = (float) Math.sin((prog / 0.52F) * (Math.PI / 2));
                        by += rear * 2.0; bf -= rear * 1.2;
                        ny += rear * 3.2; nf -= rear * 1.6;
                        hy += rear * 3.8; hf -= rear * 2.0;
                        w1y += rear * 2.5; w2y += rear * 2.5;
                        t1y -= rear * 0.6; t2y -= rear * 1.0; t3y -= rear * 1.2;
                    } else {
                        float recovery = (1.0F - prog) / 0.48F;
                        by -= recovery * 0.4;
                    }
                    break;
                }
                case 70: { // BREATH CLOSE (total 55 ticks)
                    float prog = 1.0F - (clientActionTicks / 55.0F);
                    float breath = (float) Math.sin(prog * Math.PI);
                    hy -= breath * 2.2;
                    hf += breath * 2.0;
                    ny -= breath * 1.4;
                    nf += breath * 1.2;
                    break;
                }
                case 71: { // BREATH FAR (total 25 ticks)
                    float prog = 1.0F - (clientActionTicks / 25.0F);
                    if (prog < 0.4F) {
                        float prep = prog / 0.4F;
                        hy += prep * 1.0;
                        hf -= prep * 0.8;
                    } else {
                        float shoot = (1.0F - prog) / 0.6F;
                        hf += shoot * 1.5;
                        hy -= shoot * 0.8;
                    }
                    break;
                }
                case 65: { // DECELERATE (total 30 ticks)
                    float prog = 1.0F - (clientActionTicks / 30.0F);
                    float brake = (float) Math.sin(prog * Math.PI);
                    w1f += brake * 2.2;
                    w2f += brake * 2.2;
                    w1y += brake * 1.8;
                    w2y += brake * 1.8;
                    hf -= brake * 1.5;
                    hy += brake * 1.2;
                    break;
                }
                case 72: { // TAKEOFF (total 20 ticks)
                    float prog = 1.0F - (clientActionTicks / 20.0F);
                    by += prog * 2.5;
                    hy += prog * 3.0;
                    ny += prog * 2.8;
                    break;
                }
                case 75: { // ROAR HOVER (total 80 ticks)
                    float prog = 1.0F - (clientActionTicks / 80.0F);
                    float roar = (float) Math.sin(Math.min(1.0F, prog * 1.25F) * Math.PI);
                    hy += roar * 1.5;
                    hf -= roar * 0.8;
                    ny += roar * 1.0;
                    break;
                }
            }
        }

        // Apply dynamic head looking
        if (Math.abs(lookYaw) > 0.1F || Math.abs(lookPitch) > 0.1F) {
            float headRad = (yaw + lookYaw) * 0.017453292F;
            float pitchRad = lookPitch * 0.017453292F;
            float cosPitch = net.minecraft.util.Mth.cos(pitchRad);
            double distHead = (pose == 0) ? 2.7D : 2.0D;
            double neckWorldX = cx - nf * sinYaw - nr * cosYaw;
            double neckWorldZ = cz + nf * cosYaw - nr * sinYaw;
            double headWorldX = neckWorldX - net.minecraft.util.Mth.sin(headRad) * (distHead * cosPitch);
            double headWorldZ = neckWorldZ + net.minecraft.util.Mth.cos(headRad) * (distHead * cosPitch);
            double headWorldY = cy + ny + (hy - ny) - net.minecraft.util.Mth.sin(pitchRad) * distHead;
            setCustomPartBox(parts[0], headWorldX, headWorldY, headWorldZ, hw, hh);
        } else {
            setPartFromLocal(parts[0], cx, cy, cz, cosYaw, sinYaw, hf, hy, hr, hw, hh);
        }

        // Apply dynamic wing roll (bank angle)
        if (Math.abs(roll) > 0.1F) {
            w1y -= sinRoll * w1r;
            w2y += sinRoll * (-w2r);
        }

        setPartFromLocal(parts[1], cx, cy, cz, cosYaw, sinYaw, nf, ny, nr, nw, nh);
        setPartFromLocal(parts[2], cx, cy, cz, cosYaw, sinYaw, bf, by, br, bw, bh);
        setPartFromLocal(parts[3], cx, cy, cz, cosYaw, sinYaw, t1f, t1y, t1r, t1w, t1h);
        setPartFromLocal(parts[4], cx, cy, cz, cosYaw, sinYaw, t2f, t2y, t2r, t2w, t2h);
        setPartFromLocal(parts[5], cx, cy, cz, cosYaw, sinYaw, t3f, t3y, t3r, t3w, t3h);
        setPartFromLocal(parts[6], cx, cy, cz, cosYaw, sinYaw, w1f, w1y, w1r, ww, wh);
        setPartFromLocal(parts[7], cx, cy, cz, cosYaw, sinYaw, w2f, w2y, w2r, ww, wh);
    }

    @Unique
    private void setPartFromLocal(net.minecraft.world.entity.boss.enderdragon.EnderDragonPart part, double cx, double cy, double cz, float cos, float sin,
                                  double forward, double up, double right, double w, double h) {
        double wx = cx - forward * sin - right * cos;
        double wy = cy + up;
        double wz = cz + forward * cos - right * sin;
        setCustomPartBox(part, wx, wy, wz, w, h);
    }

    @Unique
    private void setCustomPartBox(net.minecraft.world.entity.boss.enderdragon.EnderDragonPart part, double wx, double wy, double wz, double w, double h) {
        part.setPos(wx, wy, wz);
        double halfW = w * 0.5D;
        part.setBoundingBox(new net.minecraft.world.phys.AABB(
            wx - halfW, wy, wz - halfW,
            wx + halfW, wy + h, wz + halfW
        ));
    }
}

