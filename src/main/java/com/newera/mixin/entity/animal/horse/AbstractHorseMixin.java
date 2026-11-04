package com.newera.mixin.entity.animal.horse;

import com.newera.docs.entity.ai.goal.AnimalSitGoal;
import com.newera.docs.entity.ai.goal.AnimalSleepGoal;
import com.newera.docs.entity.ai.goal.AnimalSpeed;
import com.newera.docs.entity.animation.INEHorseAnimation;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.equine.AbstractHorse;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;



@Mixin(AbstractHorse.class)
public abstract class AbstractHorseMixin extends Animal implements INEHorseAnimation {

    @Unique
    private static final EntityDataAccessor<Byte> NEWERA_BODY_STATE = SynchedEntityData.defineId(AbstractHorse.class, EntityDataSerializers.BYTE);

    @Unique
    private int busyTimer = 0;

    @Unique
    private int eatGrassTimer = 0;

    @Unique
    private int attackDelayTimer = 0;

    @Unique
    private int panicTimer = 0;

    @Unique
    private net.minecraft.world.entity.LivingEntity counterAttackTarget = null;

    @Unique
    private float lookWeight = 1.0F;
    @Unique
    private float lookWeightO = 1.0F;

    @Unique
    private boolean isBodyLocked = false;
    @Unique
    private float lockedBodyYaw = 0.0F;

    // Momentum fields
    @Unique
    private float currentMomentum = 0.0F;
    @Unique
    private static final float ACCELERATION = 0.02F;
    @Unique
    private static final float DECELERATION = 0.015F;

    @Unique
    public final AnimationState sitAnimationState = new AnimationState();
    @Unique
    public final AnimationState sittingHoldAnimationState = new AnimationState();
    @Unique
    public final AnimationState sleepAnimationState = new AnimationState();
    @Unique
    public final AnimationState sleepingHoldAnimationState = new AnimationState();
    @Unique
    public final AnimationState wakeUpAnimationState = new AnimationState();
    @Unique
    public final AnimationState standAnimationState = new AnimationState();
    @Unique
    public final AnimationState walkAnimationState = new AnimationState();
    @Unique
    public final AnimationState grazingAnimationState = new AnimationState();
    @Unique
    public final AnimationState idleAnimationState = new AnimationState();
    @Unique
    public final AnimationState rearAnimationState = new AnimationState();
    @Unique
    public final AnimationState attackAnimationState = new AnimationState();

    @Unique
    private AnimalSpeed animalSpeed;

    protected AbstractHorseMixin(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void initSpeedAndGoals(EntityType<? extends Animal> type, Level level, CallbackInfo ci) {
        this.animalSpeed = new AnimalSpeed(this, 0.20D, 0.35D, 20);
    }

    @Inject(method = "registerGoals", at = @At("TAIL"))
    protected void addCustomGoals(CallbackInfo ci) {
        if (this.getType() != EntityTypes.ZOMBIE_HORSE && this.getType() != EntityTypes.SKELETON_HORSE) {
            this.goalSelector.addGoal(4, new AnimalSitGoal(this, true, 8000, 2000));
            this.goalSelector.addGoal(4, new AnimalSleepGoal(this, true, 4000, 2000, false));
        }
    }

    @Inject(method = "defineSynchedData", at = @At("TAIL"))
    protected void defineSynchedDataInject(SynchedEntityData.Builder builder, CallbackInfo ci) {
        builder.define(NEWERA_BODY_STATE, (byte) 0);
    }

    @Inject(method = "createBaseHorseAttributes", at = @At("RETURN"))
    private static void addKnockbackResistance(CallbackInfoReturnable<net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder> cir) {
        cir.getReturnValue().add(net.minecraft.world.entity.ai.attributes.Attributes.KNOCKBACK_RESISTANCE, 0.5D);
    }

    @Override
    public void setBodyState(byte state) {
        byte oldState = this.getBodyState();
        this.entityData.set(NEWERA_BODY_STATE, state);
        if (state == 6) { // Stand up
            this.busyTimer = 40;
        } else if (state == 8) { // Rear
            this.busyTimer = 30; // 1.5s
        } else if (state == 9) { // Attack
            this.busyTimer = 13; // 0.625s
        }
        if (oldState != state) {
            this.refreshDimensions();
        }
    }

    @Override
    public byte getBodyState() {
        return this.entityData.get(NEWERA_BODY_STATE);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    public void tickCustom(CallbackInfo ci) {
        byte state = this.getBodyState();



        if (state >= 1 && state <= 6) { // Lock body only for sitting/sleeping/waking
            if (!this.isBodyLocked) {
                this.lockedBodyYaw = this.yBodyRot;
                this.isBodyLocked = true;
            }
            this.yBodyRot = this.lockedBodyYaw;
            this.yBodyRotO = this.lockedBodyYaw;
        } else {
            this.isBodyLocked = false;
        }

        if (this.level().isClientSide()) {
            this.lookWeightO = this.lookWeight;
            float targetWeight = (state >= 3 && state <= 6) ? 0.0F : 1.0F;
            this.lookWeight = Mth.approach(this.lookWeight, targetWeight, 0.1F);
            this.setupAnimationStates();
        }

        if (!this.level().isClientSide() && state > 0) {
            if (state == 6 || state == 8 || state == 9) {
                this.busyTimer--;
                if (this.busyTimer <= 0) {
                    this.setBodyState((byte) 0);
                }
            }
        }

        if (!this.level().isClientSide() && this.panicTimer > 0) {
            this.panicTimer--;
        }

        if (state == 0 && this.panicTimer <= 0) {
            boolean isEating = ((AbstractHorse)(Object)this).isEating();
            if (isEating) {
                this.eatGrassTimer++;
                if (this.eatGrassTimer == 40 && !this.level().isClientSide()) {
                    net.minecraft.core.BlockPos pos = this.blockPosition().below();
                    if (this.level().getBlockState(pos).is(net.minecraft.world.level.block.Blocks.GRASS_BLOCK)) {
                        this.level().levelEvent(2001, pos, net.minecraft.world.level.block.Block.getId(net.minecraft.world.level.block.Blocks.GRASS_BLOCK.defaultBlockState()));
                        this.level().setBlock(pos, net.minecraft.world.level.block.Blocks.DIRT.defaultBlockState(), 2);
                        this.heal(1.0F);
                    } else if (this.level().getBlockState(this.blockPosition()).is(net.minecraft.world.level.block.Blocks.SHORT_GRASS) || this.level().getBlockState(this.blockPosition()).is(net.minecraft.world.level.block.Blocks.TALL_GRASS)) {
                        this.level().destroyBlock(this.blockPosition(), false);
                        this.heal(1.0F);
                    }
                }
            } else {
                this.eatGrassTimer = 0;
            }
        } else {
            this.eatGrassTimer = 0;
        }

        if (!this.level().isClientSide() && this.attackDelayTimer > 0) {
            this.attackDelayTimer--;
            if (this.attackDelayTimer <= 0 && this.counterAttackTarget != null && this.counterAttackTarget.isAlive()) {
                double distance = this.distanceToSqr(this.counterAttackTarget);
                if (distance <= 16.0) {
                    double dx = this.counterAttackTarget.getX() - this.getX();
                    double dz = this.counterAttackTarget.getZ() - this.getZ();
                    float yawToAttacker = (float) (Math.atan2(dz, dx) * (180.0 / Math.PI)) - 90.0F;
                    
                    this.counterAttackTarget.hurt(this.damageSources().mobAttack(this), 4.0F);
                    this.counterAttackTarget.knockback(0.6, Math.sin(yawToAttacker * Math.PI / 180.0), -Math.cos(yawToAttacker * Math.PI / 180.0), this.damageSources().mobAttack(this), 1.0F);
                }
                this.counterAttackTarget = null;
            }
        }

        if (!this.level().isClientSide() && this.animalSpeed != null) {
            if (state > 0 && state != 7) {
                this.animalSpeed.stop();
            } else if (this.getLastHurtByMob() != null || this.isOnFire()) {
                this.animalSpeed.run();
            } else {
                this.animalSpeed.walk();
            }
            this.animalSpeed.tick();
        }
    }

    @Unique
    private void setupAnimationStates() {
        byte state = this.getBodyState();

        boolean isMoving = this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6D || this.xxa != 0.0F || this.zza != 0.0F;

        boolean isEating = ((AbstractHorse)(Object)this).isEating();
        this.idleAnimationState.startIfStopped(this.tickCount);
        
        if (state == 0 && !isEating) {
            if (isMoving) {
                this.walkAnimationState.startIfStopped(this.tickCount);
            } else {
                this.walkAnimationState.stop();
            }
        } else {
            this.walkAnimationState.stop();
        }

        if (state == 0 && isEating) {
            this.grazingAnimationState.startIfStopped(this.tickCount);
        } else {
            this.grazingAnimationState.stop();
        }

        if (state == 1) this.sitAnimationState.startIfStopped(this.tickCount);
        else this.sitAnimationState.stop();

        if (state >= 2 && state <= 5) this.sittingHoldAnimationState.startIfStopped(this.tickCount);
        else this.sittingHoldAnimationState.stop();

        if (state == 3) this.sleepAnimationState.startIfStopped(this.tickCount);
        else this.sleepAnimationState.stop();

        if (state == 4) this.sleepingHoldAnimationState.startIfStopped(this.tickCount);
        else this.sleepingHoldAnimationState.stop();

        if (state == 5) this.wakeUpAnimationState.startIfStopped(this.tickCount);
        else this.wakeUpAnimationState.stop();

        if (state == 6) this.standAnimationState.startIfStopped(this.tickCount);
        else this.standAnimationState.stop();

        boolean shouldRear = state == 8 || (((AbstractHorse)(Object)this).isJumping() && state == 0) || (((AbstractHorse)(Object)this).isStanding() && state == 0 && !isMoving);
        if (shouldRear) this.rearAnimationState.startIfStopped(this.tickCount);
        else this.rearAnimationState.stop();

        if (state == 9) this.attackAnimationState.startIfStopped(this.tickCount);
        else this.attackAnimationState.stop();
    }

    @Unique
    private boolean isDecelerating = false;

    @Inject(method = "getRiddenInput", at = @At("RETURN"), cancellable = true)
    protected void modifyRiddenInput(net.minecraft.world.entity.player.Player player, Vec3 movementInput, CallbackInfoReturnable<Vec3> cir) {
        if (this.getBodyState() == 6) {
            cir.setReturnValue(Vec3.ZERO);
            return;
        }
        
        Vec3 originalInput = cir.getReturnValue();
        float forward = (float) originalInput.z;
        
        if (forward > 0) {
            this.currentMomentum = Math.min(1.0F, this.currentMomentum + ACCELERATION);
            this.isDecelerating = false;
            if (this.getBodyState() == 8) {
                this.setBodyState((byte) 0);
            }
        } else if (forward < 0) {
            this.currentMomentum = 0.0F;
            this.isDecelerating = true;
            if (this.getBodyState() == 0 && this.onGround() && !this.isBaby() && this.getDeltaMovement().horizontalDistanceSqr() < 0.01) {
                this.setBodyState((byte) 8); // REAR
            }
        } else {
            this.isDecelerating = this.currentMomentum > 0.0F;
            this.currentMomentum = Math.max(0.0F, this.currentMomentum - DECELERATION);
            if (this.currentMomentum == 0.0F && this.getBodyState() == 8) {
                // Keep rearing until busyTimer expires
            }
        }

        // Apply momentum as the forward input
        cir.setReturnValue(new Vec3(originalInput.x, originalInput.y, this.currentMomentum));
    }

    @Inject(method = "hurtServer", at = @At("RETURN"))
    public void onHurtServer(net.minecraft.server.level.ServerLevel level, net.minecraft.world.damagesource.DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValue() && source.getEntity() instanceof net.minecraft.world.entity.LivingEntity attacker) {
            this.panicTimer = 100; // 5 seconds of panic
            if (!this.isBaby()) {
                double distance = this.distanceToSqr(attacker);
                if (distance <= 9.0) { // Within 3 blocks
                    if (Math.random() < 0.5) {
                        // KICK
                        double dx = attacker.getX() - this.getX();
                        double dz = attacker.getZ() - this.getZ();
                        float yawToAttacker = (float) (Math.atan2(dz, dx) * (180.0 / Math.PI)) - 90.0F;
                        
                        this.setYRot(yawToAttacker + 180.0F);
                        this.yBodyRot = this.getYRot();
                        this.yBodyRotO = this.yBodyRot;
                        
                        this.setBodyState((byte) 9); // ATTACK
                        this.attackDelayTimer = 5; // 0.25 seconds
                        this.counterAttackTarget = attacker;
                    } else {
                        // REAR
                        this.setBodyState((byte) 8); // REAR
                    }
                } else {
                    if (Math.random() < 0.5) {
                        this.setBodyState((byte) 8); // REAR
                    }
                }
            } else {
                if (Math.random() < 0.5) {
                    this.setBodyState((byte) 8); // REAR
                }
            }
        }
    }

    @Inject(method = "isImmobile", at = @At("HEAD"), cancellable = true)
    protected void onIsImmobile(CallbackInfoReturnable<Boolean> cir) {
        if (this.getBodyState() > 0 && this.getBodyState() != 7) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "mobInteract", at = @At("HEAD"))
    public void onMobInteract(net.minecraft.world.entity.player.Player player, net.minecraft.world.InteractionHand hand, CallbackInfoReturnable<net.minecraft.world.InteractionResult> cir) {
        if (!this.level().isClientSide() && ((AbstractHorse)(Object)this).isTamed()) {
            net.minecraft.world.item.ItemStack itemStack = player.getItemInHand(hand);
            if (!itemStack.is(net.minecraft.world.item.Items.SADDLE) && !player.isSecondaryUseActive()) {
                byte state = this.getBodyState();
                if (state >= 1 && state <= 5) {
                    this.setBodyState((byte) 6); // Stand up
                }
            }
        }
    }

    @Inject(method = "standIfPossible", at = @At("HEAD"), cancellable = true)
    public void onStandIfPossible(CallbackInfo ci) {
        AbstractHorse self = (AbstractHorse)(Object)this;
        if (!self.isStanding() && !self.isJumping() && !self.isSaddled() && !self.isVehicle() && !this.isBaby()) {
            this.setBodyState((byte) 8); // REAR
            net.minecraft.sounds.SoundEvent sound = null;
            if (this.getType() == EntityTypes.HORSE) sound = net.minecraft.sounds.SoundEvents.HORSE_ANGRY;
            else if (this.getType() == EntityTypes.DONKEY) sound = net.minecraft.sounds.SoundEvents.DONKEY_ANGRY;
            else if (this.getType() == EntityTypes.MULE) sound = net.minecraft.sounds.SoundEvents.MULE_ANGRY;
            else if (this.getType() == EntityTypes.SKELETON_HORSE) sound = net.minecraft.sounds.SoundEvents.SKELETON_HORSE_AMBIENT;
            else if (this.getType() == EntityTypes.ZOMBIE_HORSE) sound = net.minecraft.sounds.SoundEvents.ZOMBIE_HORSE_AMBIENT;
            
            if (sound != null) {
                this.playSound(sound, this.getSoundVolume(), this.getVoicePitch());
            }
        }
        ci.cancel();
    }

    @Inject(method = "getPassengerAttachmentPoint", at = @At("RETURN"), cancellable = true)
    protected void modifyPassengerAttachmentPoint(net.minecraft.world.entity.Entity passenger, net.minecraft.world.entity.EntityDimensions dimensions, float scale, CallbackInfoReturnable<Vec3> cir) {
        Vec3 pos = cir.getReturnValue();
        float yaw = this.yBodyRot * ((float)Math.PI / 180F);
        double shiftDistance = 0.0;
        double shiftY = 0.0;
        
        if (this.getBodyState() == 8 || ((AbstractHorse)(Object)this).isStanding()) { // Rear
            shiftDistance = 0.9;
            shiftY = 0.25;
        } else if (((AbstractHorse)(Object)this).isJumping()) {
            shiftDistance = 0.9;
            shiftY = 0.15;
        }

        if (shiftDistance > 0 || shiftY > 0) {
            double shiftX = Math.sin(yaw) * shiftDistance;
            double shiftZ = -Math.cos(yaw) * shiftDistance;
            cir.setReturnValue(pos.add(shiftX, shiftY, shiftZ));
        }
    }

    @Override
    public AnimationState getSitAnimationState() { return this.sitAnimationState; }
    @Override
    public AnimationState getSittingHoldAnimationState() { return this.sittingHoldAnimationState; }
    @Override
    public AnimationState getSleepAnimationState() { return this.sleepAnimationState; }
    @Override
    public AnimationState getSleepingHoldAnimationState() { return this.sleepingHoldAnimationState; }
    @Override
    public AnimationState getWakeUpAnimationState() { return this.wakeUpAnimationState; }
    @Override
    public AnimationState getStandAnimationState() { return this.standAnimationState; }
    @Override
    public AnimationState getWalkAnimationState() { return this.walkAnimationState; }
    @Override
    public AnimationState getGrazingAnimationState() { return this.grazingAnimationState; }
    @Override
    public AnimationState getIdleAnimationState() { return this.idleAnimationState; }
    @Override
    public AnimationState getRearAnimationState() { return this.rearAnimationState; }
    @Override
    public AnimationState getAttackAnimationState() { return this.attackAnimationState; }
}
