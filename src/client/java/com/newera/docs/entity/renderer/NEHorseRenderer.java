package com.newera.docs.entity.renderer;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Map;

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
import net.minecraft.world.entity.animal.equine.Horse;
import net.minecraft.world.entity.animal.equine.Variant;
import org.jetbrains.annotations.NotNull;

public class NEHorseRenderer extends MobRenderer<Horse, NEHorseRenderState, NEHorseAbstractModel> {

    private final AdultAndBabyModelPair<NEHorseAbstractModel> models;

    private static final Map<Variant, Identifier> ADULT_TEXTURES = Map.of(
            Variant.WHITE, Identifier.fromNamespaceAndPath("newera", "textures/entity/horse/horse_white.png"),
            Variant.CREAMY, Identifier.fromNamespaceAndPath("newera", "textures/entity/horse/horse_creamy.png"),
            Variant.CHESTNUT, Identifier.fromNamespaceAndPath("newera", "textures/entity/horse/horse_chestnut.png"),
            Variant.BROWN, Identifier.fromNamespaceAndPath("newera", "textures/entity/horse/horse_brown.png"),
            Variant.BLACK, Identifier.fromNamespaceAndPath("newera", "textures/entity/horse/horse_black.png"),
            Variant.GRAY, Identifier.fromNamespaceAndPath("newera", "textures/entity/horse/horse_gray.png"),
            Variant.DARK_BROWN, Identifier.fromNamespaceAndPath("newera", "textures/entity/horse/horse_darkbrown.png")
    );

    private static final Map<Variant, Identifier> BABY_TEXTURES = Map.of(
            Variant.WHITE, Identifier.fromNamespaceAndPath("newera", "textures/entity/horse/horse_white_baby.png"),
            Variant.CREAMY, Identifier.fromNamespaceAndPath("newera", "textures/entity/horse/horse_creamy_baby.png"),
            Variant.CHESTNUT, Identifier.fromNamespaceAndPath("newera", "textures/entity/horse/horse_chestnut_baby.png"),
            Variant.BROWN, Identifier.fromNamespaceAndPath("newera", "textures/entity/horse/horse_brown_baby.png"),
            Variant.BLACK, Identifier.fromNamespaceAndPath("newera", "textures/entity/horse/horse_black_baby.png"),
            Variant.GRAY, Identifier.fromNamespaceAndPath("newera", "textures/entity/horse/horse_gray_baby.png"),
            Variant.DARK_BROWN, Identifier.fromNamespaceAndPath("newera", "textures/entity/horse/horse_darkbrown_baby.png")
    );

    public NEHorseRenderer(final EntityRendererProvider.Context context) {
        super(context, new NEHorseModel(context.bakeLayer(NEModelLayers.NE_HORSE)), 0.75F);
        this.models = new AdultAndBabyModelPair<>(
                new NEHorseModel(context.bakeLayer(NEModelLayers.NE_HORSE)),
                new NEBabyHorseModel(context.bakeLayer(NEModelLayers.NE_BABY_HORSE))
        );
        this.addLayer(new com.newera.docs.entity.render.layer.NEHorseMarkingLayer(this));
        this.addLayer(new com.newera.docs.entity.render.layer.NEHorseSaddleLayer(this));
        this.addLayer(new com.newera.docs.entity.render.layer.NEHorseArmorLayer(this));
    }

    @Override
    @NotNull
    public NEHorseRenderState createRenderState() {
        return new NEHorseRenderState();
    }

    @Override
    public void extractRenderState(@NotNull Horse entity, @NotNull NEHorseRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.isBaby = entity.isBaby();
        state.hasChest = false;
        state.isSaddled = entity.isSaddled();
        state.armor = entity.getBodyArmorItem().copy();
        state.variant = entity.getVariant();
        state.markings = entity.getMarkings();

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
        if (state.variant != null) {
            if (state.isBaby) {
                return BABY_TEXTURES.getOrDefault(state.variant, Identifier.fromNamespaceAndPath("newera", "textures/entity/horse/horse_white_baby.png"));
            }
            return ADULT_TEXTURES.getOrDefault(state.variant, Identifier.fromNamespaceAndPath("newera", "textures/entity/horse/horse_white.png"));
        }
        return state.isBaby ? Identifier.fromNamespaceAndPath("newera", "textures/entity/horse/horse_white_baby.png") : Identifier.fromNamespaceAndPath("newera", "textures/entity/horse/horse_white.png");
    }
}

