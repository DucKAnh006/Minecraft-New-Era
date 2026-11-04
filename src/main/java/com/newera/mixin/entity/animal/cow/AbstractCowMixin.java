package com.newera.mixin.entity.animal.cow;

import com.newera.docs.entity.ai.goal.AnimalSitGoal;
import com.newera.docs.entity.ai.goal.AnimalSleepGoal;
import com.newera.docs.entity.ai.goal.AnimalSpeed;
import com.newera.docs.entity.animation.INECowAnimation;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.goal.EatBlockGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.cow.AbstractCow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractCow.class)
public abstract class AbstractCowMixin extends Animal implements INECowAnimation {

    @Unique
    private static final EntityDataAccessor<Byte> NEWERA_BODY_STATE = SynchedEntityData.defineId(AbstractCow.class, EntityDataSerializers.BYTE);

    @Unique
    private boolean canBeMilked = true;

    @Unique
    private int busyTimer = 0;

    @Unique
    private float lookWeight = 1.0F;
    @Unique
    private float lookWeightO = 1.0F;

    @Unique
    private boolean isBodyLocked = false;
    @Unique
    private float lockedBodyYaw = 0.0F;

    @Unique
    private boolean isHurtSmall = false;

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
    public final AnimationState entityTagsgrazingAnimationState = new AnimationState();
    @Unique
    public final AnimationState entityTagsidleAnimationState = new AnimationState();

    protected AbstractCowMixin(EntityType<? extends Animal> type, Level level) {
        super(type, level);
    }

    @Unique
    private AnimalSpeed animalSpeed;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void initSpeed(EntityType<? extends Animal> type, Level level, CallbackInfo ci) {
        this.animalSpeed = new AnimalSpeed(this, 0.20D, 0.25D, 20);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        super.onSyncedDataUpdated(key);
        if (NEWERA_BODY_STATE.equals(key)) {
            this.refreshDimensions();
        }
    }

    @Inject(method = "registerGoals", at = @At("TAIL"))
    protected void addEatGoal(CallbackInfo ci) {
        this.goalSelector.addGoal(5, new EatBlockGoal(this));
        this.goalSelector.addGoal(4, new AnimalSitGoal(this, true, 8000, 2000));
        this.goalSelector.addGoal(4, new AnimalSleepGoal(this, true, 4000, 2000, false));
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(NEWERA_BODY_STATE, (byte) 0);
    }

    @Override
    public void setBodyState(byte state) {
        byte oldState = this.getBodyState();
        this.entityData.set(NEWERA_BODY_STATE, state);
        if (state == 6) {
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
    public void tick() {
        super.tick();

        byte state = this.getBodyState();

        if (state > 0) {
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

            this.entityTagssetupAnimationStates();
        } else {
            if (state > 0) {
                if (this.getLastHurtByMob() != null || this.isOnFire()) {
                    this.setBodyState((byte) 0);
                    this.busyTimer = 0;
                } else {
                    this.getNavigation().stop();

                    if (state == 6) {
                        this.busyTimer--;
                        if (this.busyTimer <= 0) {
                            this.setBodyState((byte) 0);
                        }
                    }
                }
            }
        }

        if (!this.level().isClientSide() && this.animalSpeed != null) {
            if (state > 0) {
                this.animalSpeed.stop();
            } else if (this.getNavigation().isDone() || this.getNavigation().getPath() == null) {
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
    private void entityTagssetupAnimationStates() {
        byte state = this.getBodyState();

        this.entityTagsidleAnimationState.startIfStopped(this.tickCount);

        if (state == 1) this.entityTagssitAnimationState.startIfStopped(this.tickCount);
        else this.entityTagssitAnimationState.stop();

        if (state >= 2 && state <= 5) this.entityTagssittingHoldAnimationState.startIfStopped(this.tickCount);
        else this.entityTagssittingHoldAnimationState.stop();

        if (state == 3) this.entityTagssleepAnimationState.startIfStopped(this.tickCount);
        else this.entityTagssleepAnimationState.stop();

        if (state == 4) this.entityTagssleepingHoldAnimationState.startIfStopped(this.tickCount);
        else this.entityTagssleepingHoldAnimationState.stop();

        if (state == 5) this.entityTagswakeUpAnimationState.startIfStopped(this.tickCount);
        else this.entityTagswakeUpAnimationState.stop();

        if (state == 6) this.entityTagsstandAnimationState.startIfStopped(this.tickCount);
        else this.entityTagsstandAnimationState.stop();

        if (state > 0) this.entityTagsgrazingAnimationState.stop();
    }

    @Override
    public void handleEntityEvent(byte id) {
        super.handleEntityEvent(id);
        if (id == 10 && this.getBodyState() == 0) {
            this.entityTagsgrazingAnimationState.start(this.tickCount);
        }
    }

    @Override
    public void ate() {
        super.ate();
        this.canBeMilked = true;
    }

    @Inject(method = "mobInteract", at = @At("HEAD"), cancellable = true)
    private void preventMilking(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (itemStack.is(Items.BUCKET) && !this.isBaby() && !this.canBeMilked) {
            if (this.level().isClientSide()) {
                cir.setReturnValue(InteractionResult.CONSUME);
            } else {
                cir.setReturnValue(InteractionResult.PASS);
            }
        }
    }

    @Inject(method = "mobInteract", at = @At("RETURN"))
    private void updateMilkState(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        if (cir.getReturnValue() == InteractionResult.SUCCESS) {
            this.canBeMilked = false;
        }
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
    public AnimationState getGrazingAnimationState() { return this.entityTagsgrazingAnimationState; }
    @Override
    public AnimationState getIdleAnimationState() { return this.entityTagsidleAnimationState; }
}
