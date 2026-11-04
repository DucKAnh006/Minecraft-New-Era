package com.newera.mixin.entity.animal.wolf;

import com.newera.docs.entity.ai.goal.AnimalSitGoal;
import com.newera.docs.entity.ai.goal.AnimalSleepGoal;
import com.newera.docs.entity.ai.goal.WolfHowlGoal;
import com.newera.docs.entity.animation.INEWolfAnimation;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.wolf.Wolf;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.LivingEntity;
@Mixin(Wolf.class)
public abstract class WolfMixin extends TamableAnimal implements INEWolfAnimation {
    @Unique
    private static final EntityDataAccessor<Byte> NEWERA_BODY_STATE = SynchedEntityData.defineId(Wolf.class, EntityDataSerializers.BYTE);

    @Unique
    private boolean isBodyLocked = false;
    @Unique
    private float lockedBodyYaw = 0.0F;

    @Unique private int busyTimer = 0;
    @Unique private boolean wasInSittingPose = false;
    @Unique private boolean wasLying = false;

    @Unique
    public final AnimationState entityTagssitAnimationState = new AnimationState();
    @Unique
    public final AnimationState entityTagssittingHoldAnimationState = new AnimationState();
    @Unique
    public final AnimationState entityTagssleepAnimationState = new AnimationState();
    @Unique
    public final AnimationState entityTagssleepingHoldAnimationState = new AnimationState();
    @Unique
    public final AnimationState entityTagswakeUpAnimationState = new AnimationState();
    @Unique
    public final AnimationState entityTagsstandAnimationState = new AnimationState();
    @Unique
    public final AnimationState entityTagshowlAnimationState = new AnimationState();
    @Unique
    public final AnimationState entityTagssitHowlingAnimationState = new AnimationState();
    @Unique
    public final AnimationState entityTagsidleAnimationState = new AnimationState();

    protected WolfMixin(EntityType<? extends TamableAnimal> type, Level level) {
        super(type, level);
    }

    @Inject(method = "registerGoals", at = @At("TAIL"))
    protected void addWildGoals(CallbackInfo ci) {
        this.goalSelector.addGoal(4, new AnimalSitGoal(this, true, 4000, 2000));
        this.goalSelector.addGoal(4, new AnimalSleepGoal(this, true, 4000, 2000, true)); // onlySleepAtNight = true
        this.goalSelector.addGoal(3, new WolfHowlGoal((Wolf) (Object) this));
    }

    @Inject(method = "mobInteract", at = @At("HEAD"), cancellable = true)
    public void onMobInteract(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        Wolf wolf = (Wolf)(Object)this;
        if (wolf.isTame() && wolf.isOwnedBy(player)) {
            ItemStack itemStack = player.getItemInHand(hand);
            
            if (itemStack.isEmpty()) {
                if (wolf.isLeashed() && wolf.getLeashHolder() == player) {
                    return;
                }
                
                if (!wolf.level().isClientSide()) {
                    boolean isCurrentlySitting = wolf.isOrderedToSit();
                    wolf.setOrderedToSit(!isCurrentlySitting);
                    wolf.getNavigation().stop();
                    wolf.setTarget(null);
                    
                    if (!isCurrentlySitting) {
                        this.setBodyState((byte) 1);
                    } else {
                        byte state = this.getBodyState();
                        if (state >= 3 && state <= 5) {
                            this.setBodyState((byte) 5); // Wake up
                        } else if (state != 0) {
                            this.setBodyState((byte) 6); // Stand up
                        }
                    }
                }
                cir.setReturnValue(InteractionResult.SUCCESS);
                return;
            }
            
            if (itemStack.getItem() instanceof DyeItem) {
                DyeColor color = (DyeColor)itemStack.get(DataComponents.DYE);
                if (color != null && color == wolf.getCollarColor()) {
                    cir.setReturnValue(InteractionResult.PASS);
                }
            }
        }
    }

    @Inject(method = "defineSynchedData", at = @At("TAIL"))
    protected void defineSynchedDataInject(SynchedEntityData.Builder builder, CallbackInfo ci) {
        builder.define(NEWERA_BODY_STATE, (byte) 0);
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

    @Inject(method = "tick", at = @At("TAIL"))
    public void tickInject(CallbackInfo ci) {
        byte state = this.getBodyState();

        if (state > 0 && state != 7 && state != 8) {
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
            this.entityTagssetupAnimationStates();
        } else {
            Wolf wolf = (Wolf)(Object)this;
            boolean isSitting = wolf.isOrderedToSit() || wolf.isInSittingPose();
            boolean isLying = false;
            
            if (wolf.isTame()) {
                LivingEntity owner = wolf.getOwner();
                if (owner instanceof Player player) {
                    isLying = isSitting && player.isSleeping();
                }
            }
            
            boolean sittingChanged = isSitting != this.wasInSittingPose;
            boolean lyingChanged = isLying != this.wasLying;
            
            if (sittingChanged) {
                if (isSitting) {
                    this.setBodyState((byte) 1);
                } else if (state != 0) {
                    if (state >= 3 && state <= 5) {
                        this.setBodyState((byte) 5); // Wake up
                    } else {
                        this.setBodyState((byte) 6); // Stand up
                    }
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
                if (state == 0 || state == 1 || state == 2 || state == 6) {
                    this.setBodyState((byte) 4);
                }
            }
            
            this.wasInSittingPose = isSitting;
            this.wasLying = isLying;
            
            state = this.getBodyState();

            if (state > 0 && state <= 6) {
                if (wolf.hurtTime > 0 || this.isOnFire() || this.getTarget() != null) {
                    this.setBodyState((byte) 0);
                    this.busyTimer = 0;
                    if (wolf.isOrderedToSit()) {
                        wolf.setOrderedToSit(false);
                    }
                } else {
                    this.getNavigation().stop();
                    if (state == 5) {
                        this.busyTimer--;
                        if (this.busyTimer <= 0) this.setBodyState((byte) 6);
                    } else if (state == 6) {
                        this.busyTimer--;
                        if (this.busyTimer <= 0) this.setBodyState((byte) 0);
                    } else if (state == 1) {
                        this.busyTimer--;
                        if (this.busyTimer <= 0) this.setBodyState((byte) 2);
                    } else if (state == 3) {
                        this.busyTimer--;
                        if (this.busyTimer <= 0) this.setBodyState((byte) 4);
                    }
                }
            } else if (state == 7 || state == 8) {
                if (wolf.hurtTime > 0 || this.getTarget() != null) {
                    this.setBodyState((byte) 0);
                    if (wolf.isOrderedToSit()) {
                        wolf.setOrderedToSit(false);
                    }
                }
            }
        }
    }

    @Unique
    private void entityTagssetupAnimationStates() {
        byte state = this.getBodyState();

        this.entityTagsidleAnimationState.startIfStopped(this.tickCount);

        if (state == 1) this.entityTagssitAnimationState.startIfStopped(this.tickCount);
        else this.entityTagssitAnimationState.stop();

        boolean isOrderedToSit = this.isInSittingPose();
        if (state == 2 || state == 3 || state == 4 || state == 5 || state == 8 || (isOrderedToSit && state == 0)) this.entityTagssittingHoldAnimationState.startIfStopped(this.tickCount);
        else this.entityTagssittingHoldAnimationState.stop();

        if (state == 3) {
            this.entityTagssleepAnimationState.startIfStopped(this.tickCount);
        }
        else this.entityTagssleepAnimationState.stop();

        if (state == 4) {
            this.entityTagssleepingHoldAnimationState.startIfStopped(this.tickCount);
        }
        else this.entityTagssleepingHoldAnimationState.stop();

        if (state == 5) {
            this.entityTagswakeUpAnimationState.startIfStopped(this.tickCount);
        }
        else this.entityTagswakeUpAnimationState.stop();

        if (state == 6) this.entityTagsstandAnimationState.startIfStopped(this.tickCount);
        else this.entityTagsstandAnimationState.stop();

        if (state == 7) this.entityTagshowlAnimationState.startIfStopped(this.tickCount);
        else this.entityTagshowlAnimationState.stop();

        if (state == 8) {
            this.entityTagssittingHoldAnimationState.startIfStopped(this.tickCount);
            this.entityTagssitHowlingAnimationState.startIfStopped(this.tickCount);
        }
        else this.entityTagssitHowlingAnimationState.stop();
    }

    @Override
    public AnimationState getSitAnimationState() { return this.entityTagssitAnimationState; }
    @Override
    public AnimationState getSittingHoldAnimationState() { return this.entityTagssittingHoldAnimationState; }
    @Override
    public AnimationState getSleepAnimationState() { return this.entityTagssleepAnimationState; }
    @Override
    public AnimationState getSleepingHoldAnimationState() { return this.entityTagssleepingHoldAnimationState; }
    @Override
    public AnimationState getWakeUpAnimationState() { return this.entityTagswakeUpAnimationState; }
    @Override
    public AnimationState getStandAnimationState() { return this.entityTagsstandAnimationState; }
    @Override
    public AnimationState getHowlAnimationState() { return this.entityTagshowlAnimationState; }
    @Override
    public AnimationState getSitHowlingAnimationState() { return this.entityTagssitHowlingAnimationState; }
    @Override
    public AnimationState getIdleAnimationState() { return this.entityTagsidleAnimationState; }
}
