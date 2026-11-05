package com.newera.docs.entity.renderer;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Map;

import com.newera.docs.entity.model.cow.NECowBabyModel;
import com.newera.docs.entity.model.cow.NECowModel;
import com.newera.docs.entity.render.NEModelLayers;
import com.newera.docs.entity.state.NECowRenderState;
import com.newera.docs.entity.animation.INECowAnimation;
import net.minecraft.client.model.AdultAndBabyModelPair;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.animal.cow.Cow;
import net.minecraft.world.entity.animal.cow.CowVariant;
import org.jetbrains.annotations.NotNull;

public class NECowRenderer extends MobRenderer<Cow, NECowRenderState, EntityModel<NECowRenderState>> {

    private final Map<CowVariant.ModelType, AdultAndBabyModelPair<EntityModel<NECowRenderState>>> models;

    public NECowRenderer(final EntityRendererProvider.Context context) {
        super(context, new NECowModel(context.bakeLayer(NEModelLayers.NE_COW)), 0.7F);
        this.models = bakeModels(context);
    }

    private static Map<CowVariant.ModelType, AdultAndBabyModelPair<EntityModel<NECowRenderState>>> bakeModels(final EntityRendererProvider.Context context) {
        return Maps.newEnumMap(
                Map.of(
                        CowVariant.ModelType.NORMAL,
                        new AdultAndBabyModelPair<>(new NECowModel(context.bakeLayer(NEModelLayers.NE_COW)), new NECowBabyModel(context.bakeLayer(NEModelLayers.NE_COW_BABY))),
                        CowVariant.ModelType.WARM,
                        new AdultAndBabyModelPair<>(new NECowModel(context.bakeLayer(NEModelLayers.NE_WARM_COW)), new NECowBabyModel(context.bakeLayer(NEModelLayers.NE_WARM_COW_BABY))),
                        CowVariant.ModelType.COLD,
                        new AdultAndBabyModelPair<>(new NECowModel(context.bakeLayer(NEModelLayers.NE_COLD_COW)), new NECowBabyModel(context.bakeLayer(NEModelLayers.NE_COLD_COW_BABY)))
                )
        );
    }

    @Override
    @NotNull
    public NECowRenderState createRenderState() {
        return new NECowRenderState();
    }

    @Override
    public void extractRenderState(@NotNull Cow entity, @NotNull NECowRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.isBaby = entity.isBaby();
        state.variant = entity.getVariant().value();

        if (entity instanceof INECowAnimation mixinEntity) {
            state.sitAnimationState.copyFrom(mixinEntity.getSitAnimationState());
            state.sittingHoldAnimationState.copyFrom(mixinEntity.getSittingHoldAnimationState());
            state.sleepAnimationState.copyFrom(mixinEntity.getSleepAnimationState());
            state.sleepingHoldAnimationState.copyFrom(mixinEntity.getSleepingHoldAnimationState());
            state.wakeUpAnimationState.copyFrom(mixinEntity.getWakeUpAnimationState());
            state.standAnimationState.copyFrom(mixinEntity.getStandAnimationState());
            state.grazingAnimationState.copyFrom(mixinEntity.getGrazingAnimationState());
            state.idleAnimationState.copyFrom(mixinEntity.getIdleAnimationState());
        }
    }

    @Override
    public void submit(@NotNull final NECowRenderState state, @NotNull final PoseStack poseStack, @NotNull final SubmitNodeCollector submitNodeCollector, @NotNull final CameraRenderState camera) {
        if (state.variant != null) {
            AdultAndBabyModelPair<EntityModel<NECowRenderState>> pair = this.models.get(state.variant.modelAndTexture().model());
            this.model = pair.getModel(state.isBaby);
            super.submit(state, poseStack, submitNodeCollector, camera);
        }
    }

    @Override
    @NotNull
    public Identifier getTextureLocation(@NotNull final NECowRenderState state) {
        if (state.variant == null) {
            return MissingTextureAtlasSprite.getLocation();
        }
        return state.isBaby ? state.variant.babyTexture().texturePath() : state.variant.modelAndTexture().asset().texturePath();
    }
}
