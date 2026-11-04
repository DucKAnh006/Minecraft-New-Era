package com.newera.mixin.entity.animal.cat;

import com.newera.docs.entity.ai.goal.*;
import com.newera.docs.entity.animation.INECatAnimation;
import com.newera.docs.entity.animation.INEAnimal;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.feline.Cat;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Cat.class)
public abstract class CatMixin extends TamableAnimal implements INECatAnimation, INEAnimal {

    @Unique
    private static final EntityDataAccessor<Byte> NEWERA_BODY_STATE = SynchedEntityData.defineId(Cat.class, EntityDataSerializers.BYTE);
    
    @Unique
    private int busyTimer = 0;

    @Unique private float lookWeight = 1.0F;
    @Unique private float lookWeightO = 1.0F;
    @Unique private boolean isBodyLocked = false;
    @Unique private float lockedBodyYaw = 0.0F;

    @Unique public final AnimationState sitAnimationState = new AnimationState();
    @Unique public final AnimationState sittingHoldAnimationState = new AnimationState();
    @Unique public final AnimationState sleepAnimationState = new AnimationState();
    @Unique public final AnimationState sleepingHoldAnimationState = new AnimationState();
    @Unique public final AnimationState wakeUpAnimationState = new AnimationState();
    @Unique public final AnimationState standAnimationState = new AnimationState();
    
    @Unique public final AnimationState stretchAnimationState = new AnimationState();
    @Unique public final AnimationState stalkingAnimationState = new AnimationState();
    @Unique public final AnimationState pounceAnimationState = new AnimationState();
    
    @Unique public final AnimationState idleAnimationState = new AnimationState();

    @Unique
    private AnimalSpeed animalSpeed;

    protected CatMixin(EntityType<? extends TamableAnimal> type, Level level) {
        super(type, level);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void initSpeed(EntityType<? extends Cat> type, Level level, CallbackInfo ci) {
        this.animalSpeed = new AnimalSpeed(this, 0.30D, 0.30D, 20);
    }

    @Inject(method = "defineSynchedData", at = @At("TAIL"))
    protected void defineSynchedDataInject(SynchedEntityData.Builder builder, CallbackInfo ci) {
        builder.define(NEWERA_BODY_STATE, (byte) 0);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        super.onSyncedDataUpdated(key);
        if (NEWERA_BODY_STATE.equals(key)) {
            this.refreshDimensions();
        }
    }

    @Inject(method = "registerGoals", at = @At("TAIL"))
    protected void addCustomGoals(CallbackInfo ci) {
        this.goalSelector.addGoal(5, new net.minecraft.world.entity.ai.goal.FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(8, new AnimalSitGoal(this, true, 4000, 2000)); // Priority 8
        this.goalSelector.addGoal(9, new CatSleepGoal(this, 12000, 2000)); // Priority 9
        this.goalSelector.addGoal(10, new CatStretchGoal(this)); // Priority 10
        
        this.goalSelector.addGoal(1, new CatStalkPreyGoal(this, 0.6D, 1.3D));
    }

    @Override
    public void setBodyState(byte state) {
        byte oldState = this.getBodyState();
        this.entityData.set(NEWERA_BODY_STATE, state);
        if (state == 1 || state == 3 || state == 5) {
            this.busyTimer = 20;
        } else if (state == 6) {
            this.busyTimer = 40;
        }
        if (oldState != state) {
            this.refreshDimensions();
        }
    }

    @Override
    public byte getBodyState() {
        return this.entityData.get(NEWERA_BODY_STATE);
    }

    @Override
    public float getLookWeight() {
        return this.lookWeight;
    }

    @Inject(method = "mobInteract", at = @At("HEAD"), cancellable = true)
    public void onMobInteract(net.minecraft.world.entity.player.Player player, net.minecraft.world.InteractionHand hand, CallbackInfoReturnable<net.minecraft.world.InteractionResult> cir) {
        Cat cat = (Cat)(Object)this;
        if (cat.isTame() && cat.isOwnedBy(player)) {
            net.minecraft.world.item.ItemStack itemStack = player.getItemInHand(hand);
            
            if (itemStack.isEmpty()) {
                if (cat.isLeashed() && cat.getLeashHolder() == player) {
                    return; // Let Vanilla handle unleashing
                }
                
                if (!cat.level().isClientSide()) {
                    boolean isCurrentlySitting = cat.isOrderedToSit();
                    cat.setOrderedToSit(!isCurrentlySitting);
                    cat.getNavigation().stop();
                    cat.setTarget(null);
                    
                    if (!isCurrentlySitting) {
                        this.setBodyState((byte) 1);
                    } else {
                        if (this.getBodyState() != 0) {
                            this.setBodyState((byte) 6);
                        }
                    }
                }
                cir.setReturnValue(net.minecraft.world.InteractionResult.SUCCESS);
                return;
            }
            
            if (itemStack.getItem() instanceof net.minecraft.world.item.DyeItem) {
                net.minecraft.world.item.DyeColor color = (net.minecraft.world.item.DyeColor)itemStack.get(net.minecraft.core.component.DataComponents.DYE);
                if (color != null && color == cat.getCollarColor()) {
                    cir.setReturnValue(net.minecraft.world.InteractionResult.PASS); // Prevent toggling sit when color is the same
                }
            }
        }
    }

    @Unique private boolean wasInSittingPose = false;
    @Unique private boolean wasLying = false;

    @Inject(method = "tick", at = @At("TAIL"))
    public void tickInject(CallbackInfo ci) {
        byte state = this.getBodyState();
        if (this.level().isClientSide()) {
            this.lookWeightO = this.lookWeight;
            float targetWeight = (state >= 3 && state <= 5) ? 0.0F : 1.0F; // 3,4,5 = sleep phases
            this.lookWeight = Mth.approach(this.lookWeight, targetWeight, 0.1F);
            this.setupAnimationStates();
            
            if (state > 0 && state != 8) { // Don't lock body during stalk (8)
                if (!this.isBodyLocked) {
                    this.lockedBodyYaw = this.yBodyRot;
                    this.isBodyLocked = true;
                }
                this.yBodyRot = this.lockedBodyYaw;
                this.yBodyRotO = this.lockedBodyYaw;
            } else {
                this.isBodyLocked = false;
            }
        } else {
            Cat cat = (Cat)(Object)this;
            boolean isSitting = cat.isOrderedToSit() || cat.isInSittingPose();
            boolean isLying = cat.isLying();
            
            if (cat.isTame()) {
                net.minecraft.world.entity.LivingEntity owner = cat.getOwner();
                if (owner instanceof net.minecraft.world.entity.player.Player player) {
                    isLying = !isSitting && player.isSleeping();
                } else {
                    isLying = false;
                }
            }
            
            boolean sittingChanged = isSitting != this.wasInSittingPose;
            boolean lyingChanged = isLying != this.wasLying;
            
            if (sittingChanged) {
                if (isSitting) {
                    this.setBodyState((byte) 1);
                } else if (state != 0) {
                    this.setBodyState((byte) 6);
                }
            } else if (isSitting) {
                if (state == 0) {
                    this.setBodyState((byte) 2);
                }
            } else if (lyingChanged) {
                if (isLying) {
                    this.setBodyState((byte) 3);
                } else if (state != 0 && state != 6) {
                    this.setBodyState((byte) 5);
                }
            } else if (isLying) {
                if (state == 0) {
                    this.setBodyState((byte) 4);
                }
            }
            
            this.wasInSittingPose = isSitting;
            this.wasLying = isLying;
            
            if (cat.isCrouching() && cat.getDeltaMovement().horizontalDistanceSqr() > 0.0001D) {
                if (state != 8 && state != 11) this.setBodyState((byte) 8);
            } else if (state == 8) {
                this.setBodyState((byte) 0);
            }

            state = this.getBodyState();
            if (state > 0 && state != 11 && state != 8) {
                if (cat.hurtTime > 0 || this.isOnFire()) {
                    this.setBodyState((byte) 0);
                    this.busyTimer = 0;
                    if (!this.level().isClientSide() && cat.isOrderedToSit()) {
                        cat.setOrderedToSit(false);
                    }
                } else {
                    this.getNavigation().stop();

                    if (state == 5) { // Wake up -> Stand
                        this.busyTimer--;
                        if (this.busyTimer <= 0) {
                            this.setBodyState((byte) 6);
                        }
                    } else if (state == 6) { // Stand up -> Normal
                        this.busyTimer--;
                        if (this.busyTimer <= 0) {
                            this.setBodyState((byte) 0);
                        }
                    } else if (state == 1) { // Sit -> Sitting Hold
                        this.busyTimer--;
                        if (this.busyTimer <= 0) {
                            this.setBodyState((byte) 2);
                        }
                    } else if (state == 3) { // Sleep -> Sleeping Hold
                        this.busyTimer--;
                        if (this.busyTimer <= 0) {
                            this.setBodyState((byte) 4);
                        }
                    }
                }
            }
        }
    }

    @Override
    protected boolean isImmobile() {
        if (this.getBodyState() > 0 && this.getBodyState() != 11 && this.getBodyState() != 8) {
            return true;
        }
        return super.isImmobile();
    }

    @Unique
    private void setupAnimationStates() {
        byte state = this.getBodyState();

        this.idleAnimationState.startIfStopped(this.tickCount);

        if (state == 1) this.sitAnimationState.startIfStopped(this.tickCount);
        else this.sitAnimationState.stop();

        if (state == 2) this.sittingHoldAnimationState.startIfStopped(this.tickCount);
        else this.sittingHoldAnimationState.stop();

        if (state == 3) this.sleepAnimationState.startIfStopped(this.tickCount);
        else this.sleepAnimationState.stop();

        if (state == 4) this.sleepingHoldAnimationState.startIfStopped(this.tickCount);
        else this.sleepingHoldAnimationState.stop();

        if (state == 5) this.wakeUpAnimationState.startIfStopped(this.tickCount);
        else this.wakeUpAnimationState.stop();

        if (state == 6) this.standAnimationState.startIfStopped(this.tickCount);
        else this.standAnimationState.stop();
        
        if (state == 7) this.stretchAnimationState.startIfStopped(this.tickCount);
        else this.stretchAnimationState.stop();
        
        if (state == 8) this.stalkingAnimationState.startIfStopped(this.tickCount);
        else this.stalkingAnimationState.stop();
        
        if (state == 11) this.pounceAnimationState.startIfStopped(this.tickCount);
        else this.pounceAnimationState.stop();
    }
    
    @Override public AnimationState getSitAnimationState() { return this.sitAnimationState; }
    @Override public AnimationState getSittingHoldAnimationState() { return this.sittingHoldAnimationState; }
    @Override public AnimationState getSleepAnimationState() { return this.sleepAnimationState; }
    @Override public AnimationState getSleepingHoldAnimationState() { return this.sleepingHoldAnimationState; }
    @Override public AnimationState getWakeUpAnimationState() { return this.wakeUpAnimationState; }
    @Override public AnimationState getStandAnimationState() { return this.standAnimationState; }
    @Override public AnimationState getStretchAnimationState() { return this.stretchAnimationState; }
    @Override public AnimationState getStalkingAnimationState() { return this.stalkingAnimationState; }
    @Override public AnimationState getPounceAnimationState() { return this.pounceAnimationState; }
    @Override public AnimationState getIdleAnimationState() { return this.idleAnimationState; }
}
