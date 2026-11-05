package com.newera.mixin.entity.animal.sheep;

import com.newera.docs.entity.ai.goal.AnimalSitGoal;
import com.newera.docs.entity.ai.goal.AnimalSleepGoal;
import com.newera.docs.entity.ai.goal.AnimalSpeed;
import com.newera.docs.entity.animation.INESheepAnimation;
import com.newera.docs.entity.variant.NESheepVariant;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.sheep.Sheep;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Sheep.class)
public abstract class SheepMixin extends Animal implements INESheepAnimation {

    @Unique
    private static final EntityDataAccessor<Byte> NEWERA_BODY_STATE = SynchedEntityData.defineId(Sheep.class, EntityDataSerializers.BYTE);

    @Unique
    private static final EntityDataAccessor<String> VARIANT_ID = SynchedEntityData.defineId(Sheep.class, EntityDataSerializers.STRING);

    @Unique
    private net.minecraft.resources.ResourceKey<NESheepVariant> currentVariantKey = com.newera.docs.entity.variant.NESheepVariants.DEFAULT;
    private int busyTimer = 0;

    @Unique
    private float lookWeight = 1.0F;
    @Unique
    private float lookWeightO = 1.0F;

    @Unique
    private boolean isBodyLocked = false;
    @Unique
    private float lockedBodyYaw = 0.0F;

    @Unique public final AnimationState sitAnimationState = new AnimationState();
    @Unique public final AnimationState sittingHoldAnimationState = new AnimationState();
    @Unique public final AnimationState sleepAnimationState = new AnimationState();
    @Unique public final AnimationState sleepingHoldAnimationState = new AnimationState();
    @Unique public final AnimationState wakeUpAnimationState = new AnimationState();
    @Unique public final AnimationState standAnimationState = new AnimationState();
    @Unique public final AnimationState grazingAnimationState = new AnimationState();
    @Unique public final AnimationState idleAnimationState = new AnimationState();

    @Unique
    private AnimalSpeed animalSpeed;

    protected SheepMixin(EntityType<? extends Animal> type, Level level) {
        super(type, level);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void initSpeed(EntityType<? extends Animal> type, Level level, CallbackInfo ci) {
        this.animalSpeed = new AnimalSpeed(this, 0.23D, 0.30D, 18);
    }

    @Inject(method = "defineSynchedData", at = @At("TAIL"))
    protected void defineSynchedDataInject(SynchedEntityData.Builder builder, CallbackInfo ci) {
        builder.define(NEWERA_BODY_STATE, (byte) 0);
        builder.define(VARIANT_ID, com.newera.docs.entity.variant.NESheepVariants.DEFAULT.identifier().toString());
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        super.onSyncedDataUpdated(key);
        if (VARIANT_ID.equals(key)) {
            this.currentVariantKey = net.minecraft.resources.ResourceKey.create(com.newera.docs.entity.variant.NESheepVariants.SHEEP_VARIANT_KEY, net.minecraft.resources.Identifier.parse(this.entityData.get(VARIANT_ID)));
        }
        if (NEWERA_BODY_STATE.equals(key)) {
            this.refreshDimensions();
        }
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    public void addAdditionalSaveDataInject(net.minecraft.world.level.storage.ValueOutput output, CallbackInfo ci) {
        try {
            output.putString("Variant", this.currentVariantKey.identifier().toString());
        } catch (Exception e) {
            System.err.println("Error saving SheepVariant data: " + e.getMessage());
        }
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    public void readAdditionalSaveDataInject(net.minecraft.world.level.storage.ValueInput input, CallbackInfo ci) {
        try {
            this.setVariantKey(net.minecraft.resources.ResourceKey.create(com.newera.docs.entity.variant.NESheepVariants.SHEEP_VARIANT_KEY, net.minecraft.resources.Identifier.parse(input.getStringOr("Variant", com.newera.docs.entity.variant.NESheepVariants.DEFAULT.identifier().toString()))));
        } catch (Exception e) {
            System.err.println("Error reading SheepVariant data: " + e.getMessage());
            this.setVariantKey(com.newera.docs.entity.variant.NESheepVariants.DEFAULT);
        }
    }

    @Inject(method = "registerGoals", at = @At("TAIL"))
    protected void addCustomGoals(CallbackInfo ci) {
        this.goalSelector.addGoal(4, new AnimalSitGoal(this, true, 4000, 2000));
        this.goalSelector.addGoal(4, new AnimalSleepGoal(this, true, 12000, 2000, true));
    }

    @Inject(method = "finalizeSpawn", at = @At("RETURN"), cancellable = true)
    public void finalizeSpawnInject(ServerLevelAccessor level, DifficultyInstance difficulty, EntitySpawnReason spawnReason, SpawnGroupData spawnGroupData, CallbackInfoReturnable<SpawnGroupData> cir) {
        BlockPos pos = this.blockPosition();
        net.minecraft.world.entity.variant.SpawnContext context = net.minecraft.world.entity.variant.SpawnContext.create(level, pos);
        java.util.Optional<net.minecraft.core.Holder.Reference<NESheepVariant>> optionalVariant = net.minecraft.world.entity.variant.PriorityProvider.pick(
                this.registryAccess().lookupOrThrow(com.newera.docs.entity.variant.NESheepVariants.SHEEP_VARIANT_KEY).listElements(),
                holder -> holder.value(),
                this.getRandom(),
                context
        );

        if (optionalVariant.isPresent()) {
            this.setVariantKey(optionalVariant.get().key());
        } else {
            this.setVariantKey(com.newera.docs.entity.variant.NESheepVariants.DEFAULT);
        }
    }

    @Inject(method = "getBreedOffspring", at = @At("RETURN"))
    public void getBreedOffspringInject(net.minecraft.server.level.ServerLevel level, net.minecraft.world.entity.AgeableMob otherParent, CallbackInfoReturnable<Sheep> cir) {
        Sheep baby = cir.getReturnValue();
        if (baby != null && baby instanceof INESheepAnimation mixinBaby && otherParent instanceof INESheepAnimation mixinOther) {
            net.minecraft.resources.ResourceKey<NESheepVariant> parentVariant = this.getRandom().nextBoolean() ? this.getVariantKey() : mixinOther.getVariantKey();
            mixinBaby.setVariantKey(parentVariant);
        }
    }

    @Override
    protected boolean isImmobile() {
        if (this.getBodyState() > 0) {
            return true;
        }
        return super.isImmobile();
    }

    @Unique
    public net.minecraft.resources.ResourceKey<NESheepVariant> getVariantKey() {
        return this.currentVariantKey;
    }

    @Unique
    public net.minecraft.core.Holder<NESheepVariant> getVariant() {
        return this.registryAccess().lookupOrThrow(com.newera.docs.entity.variant.NESheepVariants.SHEEP_VARIANT_KEY).get(this.currentVariantKey).orElseGet(() -> this.registryAccess().lookupOrThrow(com.newera.docs.entity.variant.NESheepVariants.SHEEP_VARIANT_KEY).getOrThrow(com.newera.docs.entity.variant.NESheepVariants.DEFAULT));
    }

    @Unique
    public void setVariantKey(net.minecraft.resources.ResourceKey<NESheepVariant> variantKey) {
        this.currentVariantKey = variantKey;
        this.entityData.set(VARIANT_ID, variantKey.identifier().toString());
    }

    @Override
    public void setBodyState(byte state) {
        byte oldState = this.getBodyState();
        this.entityData.set(NEWERA_BODY_STATE, state);
        if (state == 5) {
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

            this.setupAnimationStates();
        } else {
            if (state > 0) {
                if (this.getLastHurtByMob() != null || this.isOnFire() || this.hurtTime > 0) {
                    this.setBodyState((byte) 0);
                    this.busyTimer = 0;
                } else {
                    this.getNavigation().stop();

                    if (state == 5) {
                        this.busyTimer--;
                        if (this.busyTimer <= 0) {
                            this.setBodyState((byte) 6);
                        }
                    } else if (state == 6) {
                        this.busyTimer--;
                        if (this.busyTimer <= 0) {
                            this.setBodyState((byte) 0);
                        }
                    }
                }
            }
        }

        if (!this.level().isClientSide() && this.animalSpeed != null) {
            if (this.isVehicle()) {
                net.minecraft.world.entity.ai.attributes.AttributeInstance speedAttribute = this.getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.MOVEMENT_SPEED);
                if (speedAttribute != null) {
                    if (state > 0) {
                        speedAttribute.setBaseValue(0.0D);
                    } else {
                        speedAttribute.setBaseValue(0.23D);
                    }
                }
            } else {
                boolean isPanicking = this.getLastHurtByMob() != null || this.isOnFire() || this.hurtTime > 0;
                if (state > 0) {
                    this.animalSpeed.forceStop();
                } else if (isPanicking) {
                    this.animalSpeed.run();
                } else if (this.getNavigation().isDone() || this.getNavigation().getPath() == null) {
                    this.animalSpeed.stop();
                } else {
                    this.animalSpeed.walk();
                }
                this.animalSpeed.tick();
            }
        }
    }

    @Unique
    private void setupAnimationStates() {
        byte state = this.getBodyState();

        this.idleAnimationState.startIfStopped(this.tickCount);

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

        if (state > 0) this.grazingAnimationState.stop();
    }

    @Inject(method = "handleEntityEvent", at = @At("HEAD"))
    public void handleEntityEventInject(byte id, CallbackInfo ci) {
        if (id == 10 && this.getBodyState() == 0) {
            this.grazingAnimationState.start(this.tickCount);
        }
    }

    @Override public AnimationState getSitAnimationState() { return this.sitAnimationState; }
    @Override public AnimationState getSittingHoldAnimationState() { return this.sittingHoldAnimationState; }
    @Override public AnimationState getSleepAnimationState() { return this.sleepAnimationState; }
    @Override public AnimationState getSleepingHoldAnimationState() { return this.sleepingHoldAnimationState; }
    @Override public AnimationState getWakeUpAnimationState() { return this.wakeUpAnimationState; }
    @Override public AnimationState getStandAnimationState() { return this.standAnimationState; }
    @Override public AnimationState getGrazingAnimationState() { return this.grazingAnimationState; }
    @Override public AnimationState getIdleAnimationState() { return this.idleAnimationState; }
}

