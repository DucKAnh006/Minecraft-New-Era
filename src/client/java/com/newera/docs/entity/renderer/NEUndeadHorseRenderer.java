package com.newera.docs.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;

import com.newera.docs.entity.model.horse.NEBabyHorseModel;
import com.newera.docs.entity.model.horse.NEHorseAbstractModel;
import com.newera.docs.entity.model.horse.NEHorseModel;
import com.newera.docs.entity.render.NEModelLayers;
import com.newera.docs.entity.state.NEHorseRenderState;
import com.newera.docs.entity.animation.INEHorseAnimation;
import net.minecraft.client.model.AdultAndBabyModelPair;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.animal.equine.AbstractHorse;
import org.jetbrains.annotations.NotNull;

public class NEUndeadHorseRenderer extends MobRenderer<AbstractHorse, NEHorseRenderState, NEHorseAbstractModel> {

    private final AdultAndBabyModelPair<NEHorseAbstractModel> models;

    public NEUndeadHorseRenderer(final EntityRendererProvider.Context context) {
        super(context, new NEHorseModel(context.bakeLayer(NEModelLayers.NE_HORSE)), 0.75F);
        this.models = new AdultAndBabyModelPair<>(
                new NEHorseModel(context.bakeLayer(NEModelLayers.NE_HORSE)),
                new NEBabyHorseModel(context.bakeLayer(NEModelLayers.NE_BABY_HORSE))
        );
        this.addLayer(new com.newera.docs.entity.render.layer.NEHorseSaddleLayer(this));
    }

    @Override
    @NotNull
    public NEHorseRenderState createRenderState() {
        return new NEHorseRenderState();
    }

    @Override
    public void extractRenderState(@NotNull AbstractHorse entity, @NotNull NEHorseRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.isBaby = entity.isBaby();
        state.hasChest = false;
        state.isSaddled = entity.isSaddled();

        if (entity.getType() == EntityTypes.ZOMBIE_HORSE) {
            state.variant = net.minecraft.world.entity.animal.equine.Variant.WHITE; // Dummy for ZOMBIE
        } else {
            state.variant = net.minecraft.world.entity.animal.equine.Variant.BLACK; // Dummy for SKELETON
        }

        if (entity instanceof INEHorseAnimation mixinEntity) {
            state.sitAnimationState.copyFrom(mixinEntity.getSitAnimationState());
            state.sittingHoldAnimationState.copyFrom(mixinEntity.getSittingHoldAnimationState());
            state.sleepAnimationState.copyFrom(mixinEntity.getSleepAnimationState());
            state.sleepingHoldAnimationState.copyFrom(mixinEntity.getSleepingHoldAnimationState());
            state.wakeUpAnimationState.copyFrom(mixinEntity.getWakeUpAnimationState());
            state.standAnimationState.copyFrom(mixinEntity.getStandAnimationState());
            state.walkAnimationState.copyFrom(mixinEntity.getWalkAnimationState());
            state.grazingAnimationState.copyFrom(mixinEntity.getGrazingAnimationState());
            state.idleAnimationState.copyFrom(mixinEntity.getIdleAnimationState());
            state.rearAnimationState.copyFrom(mixinEntity.getRearAnimationState());
            state.attackAnimationState.copyFrom(mixinEntity.getAttackAnimationState());
        }
    }

    @Override
    public void submit(@NotNull final NEHorseRenderState state, @NotNull final PoseStack poseStack, @NotNull final SubmitNodeCollector submitNodeCollector, @NotNull final CameraRenderState camera) {
        this.model = this.models.getModel(state.isBaby);
        super.submit(state, poseStack, submitNodeCollector, camera);
    }

    @Override
    @NotNull
    public Identifier getTextureLocation(@NotNull final NEHorseRenderState state) {
        if (state.variant == net.minecraft.world.entity.animal.equine.Variant.WHITE) {
            return state.isBaby 
                ? Identifier.fromNamespaceAndPath("newera", "textures/entity/horse/horse_zombie_baby.png")
                : Identifier.fromNamespaceAndPath("newera", "textures/entity/horse/horse_zombie.png");
        }
        return state.isBaby
            ? Identifier.fromNamespaceAndPath("newera", "textures/entity/horse/horse_skeleton_baby.png")
            : Identifier.fromNamespaceAndPath("newera", "textures/entity/horse/horse_skeleton.png");
    }
}
