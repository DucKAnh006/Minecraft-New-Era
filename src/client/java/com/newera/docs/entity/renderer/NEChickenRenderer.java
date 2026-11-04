package com.newera.docs.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Map;

import com.newera.docs.entity.model.chicken.NEChickenBabyModel;
import com.newera.docs.entity.model.chicken.NEChickenModel;
import com.newera.docs.entity.render.NEModelLayers;
import com.newera.docs.entity.state.NEChickenRenderState;
import com.newera.docs.entity.animation.INEChickenAnimation;
import net.minecraft.client.model.AdultAndBabyModelPair;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.animal.chicken.Chicken;
import net.minecraft.world.entity.animal.chicken.ChickenVariant;
import net.minecraft.world.entity.animal.chicken.ChickenVariants;
import org.jetbrains.annotations.NotNull;

public class NEChickenRenderer extends MobRenderer<Chicken, NEChickenRenderState, EntityModel<NEChickenRenderState>> {

    private final Map<ResourceKey<ChickenVariant>, AdultAndBabyModelPair<EntityModel<NEChickenRenderState>>> models;

    public NEChickenRenderer(final EntityRendererProvider.Context context) {
        super(context, new NEChickenModel(context.bakeLayer(NEModelLayers.NE_CHICKEN)), 0.5F);
        this.models = bakeModels(context);
    }

    private static Map<ResourceKey<ChickenVariant>, AdultAndBabyModelPair<EntityModel<NEChickenRenderState>>> bakeModels(final EntityRendererProvider.Context context) {
        return Map.of(
                ChickenVariants.DEFAULT,
                new AdultAndBabyModelPair<>(new NEChickenModel(context.bakeLayer(NEModelLayers.NE_CHICKEN)), new NEChickenBabyModel(context.bakeLayer(NEModelLayers.NE_CHICKEN_BABY))),
                ChickenVariants.WARM,
                new AdultAndBabyModelPair<>(new NEChickenModel(context.bakeLayer(NEModelLayers.NE_WARM_CHICKEN)), new NEChickenBabyModel(context.bakeLayer(NEModelLayers.NE_WARM_CHICKEN_BABY))),
                ChickenVariants.COLD,
                new AdultAndBabyModelPair<>(new NEChickenModel(context.bakeLayer(NEModelLayers.NE_COLD_CHICKEN)), new NEChickenBabyModel(context.bakeLayer(NEModelLayers.NE_COLD_CHICKEN_BABY)))
        );
    }

    @Override
    @NotNull
    public NEChickenRenderState createRenderState() {
        return new NEChickenRenderState();
    }

    @Override
    public void extractRenderState(@NotNull Chicken entity, @NotNull NEChickenRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.isBaby = entity.isBaby();
        state.variantKey = entity.getVariant().unwrapKey().orElse(ChickenVariants.DEFAULT);

        if (entity instanceof INEChickenAnimation mixinEntity) {
            state.sitAnimationState.copyFrom(mixinEntity.getSitAnimationState());
            state.sittingHoldAnimationState.copyFrom(mixinEntity.getSittingHoldAnimationState());
            state.sleepAnimationState.copyFrom(mixinEntity.getSleepAnimationState());
            state.sleepingHoldAnimationState.copyFrom(mixinEntity.getSleepingHoldAnimationState());
            state.wakeUpAnimationState.copyFrom(mixinEntity.getWakeUpAnimationState());
            state.standAnimationState.copyFrom(mixinEntity.getStandAnimationState());
            state.grazingAnimationState.copyFrom(mixinEntity.getGrazingAnimationState());
            state.idleAnimationState.copyFrom(mixinEntity.getIdleAnimationState());

            state.flyingAnimationState.copyFrom(mixinEntity.getFlyingAnimationState());
            state.takeOffAnimationState.copyFrom(mixinEntity.getTakeOffAnimationState());
            state.onAirAnimationState.copyFrom(mixinEntity.getOnAirAnimationState());
            state.landingAnimationState.copyFrom(mixinEntity.getLandingAnimationState());
        }
    }

    @Override
    public void submit(@NotNull final NEChickenRenderState state, @NotNull final PoseStack poseStack, @NotNull final SubmitNodeCollector submitNodeCollector, @NotNull final CameraRenderState camera) {
        if (state.variantKey != null) {
            AdultAndBabyModelPair<EntityModel<NEChickenRenderState>> pair = this.models.getOrDefault(state.variantKey, this.models.get(ChickenVariants.DEFAULT));
            this.model = pair.getModel(state.isBaby);
            super.submit(state, poseStack, submitNodeCollector, camera);
        }
    }

    @Override
    @NotNull
    public Identifier getTextureLocation(@NotNull final NEChickenRenderState state) {
        if (state.variantKey == null) {
            return MissingTextureAtlasSprite.getLocation();
        }

        ResourceKey<ChickenVariant> key = state.variantKey;
        if (key.equals(ChickenVariants.WARM)) {
            return state.isBaby ? Identifier.fromNamespaceAndPath("newera", "textures/entity/chicken/warm_chicken_baby.png") : Identifier.fromNamespaceAndPath("newera", "textures/entity/chicken/warm_chicken.png");
        } else if (key.equals(ChickenVariants.COLD)) {
            return state.isBaby ? Identifier.fromNamespaceAndPath("newera", "textures/entity/chicken/cold_chicken_baby.png") : Identifier.fromNamespaceAndPath("newera", "textures/entity/chicken/cold_chicken.png");
        } else {
            return state.isBaby ? Identifier.fromNamespaceAndPath("newera", "textures/entity/chicken/chicken_baby.png") : Identifier.fromNamespaceAndPath("newera", "textures/entity/chicken/chicken.png");
        }
    }
}
