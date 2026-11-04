package com.newera.mixin.entity;

import com.newera.docs.entity.ai.goal.AnimalSleepGoal;
import com.newera.docs.entity.animation.INEAnimal;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Mixin(Animal.class)
public abstract class AnimalInitMixin extends Mob implements INEAnimal {

    @Unique
    private UUID neParentUUID = null;

    @Unique
    private List<UUID> neChildrenUUIDs = new ArrayList<>();

    protected AnimalInitMixin(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void injectAnimalGoals(EntityType<? extends Animal> type, Level level, CallbackInfo ci) {
        if (!((Object)this instanceof net.minecraft.world.entity.animal.armadillo.Armadillo)) {
            this.goalSelector.addGoal(4, new AnimalSleepGoal((Animal)(Object)this));
        }
    }

    @Override
    public UUID getNEParentUUID() {
        return this.neParentUUID;
    }

    @Override
    public void setNEParentUUID(UUID uuid) {
        this.neParentUUID = uuid;
    }

    @Override
    public List<UUID> getNEChildrenUUIDs() {
        if (this.neChildrenUUIDs == null) {
            this.neChildrenUUIDs = new ArrayList<>();
        }
        return this.neChildrenUUIDs;
    }

    @Override
    public void addNEChild(UUID uuid) {
        if (this.neChildrenUUIDs == null) {
            this.neChildrenUUIDs = new ArrayList<>();
        }
        if (!this.neChildrenUUIDs.contains(uuid)) {
            this.neChildrenUUIDs.add(uuid);
        }
    }

    @Override
    public void removeNEChild(UUID uuid) {
        if (this.neChildrenUUIDs != null) {
            this.neChildrenUUIDs.remove(uuid);
        }
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    public void addAdditionalSaveDataInject(net.minecraft.world.level.storage.ValueOutput output, CallbackInfo ci) {
        try {
            if (this.neParentUUID != null) {
                output.putString("NEParentUUID", this.neParentUUID.toString());
            }
            if (this.neChildrenUUIDs != null && !this.neChildrenUUIDs.isEmpty()) {
                List<String> uuidStrings = new ArrayList<>();
                for (UUID uuid : this.neChildrenUUIDs) {
                    uuidStrings.add(uuid.toString());
                }
                output.putString("NEChildrenUUIDs", String.join(",", uuidStrings));
            }
        } catch (Exception e) {
            System.err.println("Error saving NEAnimal data: " + e.getMessage());
        }
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    public void readAdditionalSaveDataInject(net.minecraft.world.level.storage.ValueInput input, CallbackInfo ci) {
        try {
            String parentStr = input.getStringOr("NEParentUUID", "");
            if (!parentStr.isEmpty()) {
                this.neParentUUID = UUID.fromString(parentStr);
            } else {
                this.neParentUUID = null;
            }
            
            if (this.neChildrenUUIDs == null) {
                this.neChildrenUUIDs = new ArrayList<>();
            }
            this.neChildrenUUIDs.clear();
            
            String childrenStr = input.getStringOr("NEChildrenUUIDs", "");
            if (!childrenStr.isEmpty()) {
                for (String uuidStr : childrenStr.split(",")) {
                    if (!uuidStr.isEmpty()) {
                        this.neChildrenUUIDs.add(UUID.fromString(uuidStr));
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error reading NEAnimal data: " + e.getMessage());
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide()) return;

        Animal animal = (Animal)(Object)this;

        if (animal.isBaby()) {
            if (this.neParentUUID == null) {
                List<? extends Animal> list = this.level().getEntitiesOfClass(animal.getClass(), animal.getBoundingBox().inflate(16.0));
                Animal closestAdult = null;
                double minDistance = Double.MAX_VALUE;
                for (Animal adult : list) {
                    if (!adult.isBaby()) {
                        double dist = animal.distanceToSqr(adult);
                        if (dist < minDistance) {
                            minDistance = dist;
                            closestAdult = adult;
                        }
                    }
                }
                if (closestAdult != null) {
                    this.neParentUUID = closestAdult.getUUID();
                    if (closestAdult instanceof INEAnimal neAdult) {
                        neAdult.addNEChild(animal.getUUID());
                    }
                }
            }
        } else {
            if (this.neParentUUID != null) {
                net.minecraft.server.level.ServerLevel serverLevel = (net.minecraft.server.level.ServerLevel) this.level();
                net.minecraft.world.entity.Entity parentEntity = serverLevel.getEntity(this.neParentUUID);
                if (parentEntity instanceof INEAnimal neParent) {
                    neParent.removeNEChild(animal.getUUID());
                }
                this.neParentUUID = null;
            }

            if (this.tickCount % 24000 == 0) {
                net.minecraft.server.level.ServerLevel serverLevel = (net.minecraft.server.level.ServerLevel) this.level();
                this.neChildrenUUIDs.removeIf(uuid -> {
                    net.minecraft.world.entity.Entity e = serverLevel.getEntity(uuid);
                    return e == null || !e.isAlive();
                });
            }
        }
    }
}
