package com.newera.docs.entity.renderer;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Map;
import com.newera.docs.entity.model.sheep.NESheepAbstractModel;
import com.newera.docs.entity.model.sheep.NESheepBabyModel;
import com.newera.docs.entity.model.sheep.NESheepModel;
import com.newera.docs.entity.render.NEModelLayers;
import com.newera.docs.entity.render.layer.NESheepFurLayer;
import com.newera.docs.entity.state.NESheepRenderState;
import com.newera.docs.entity.animation.INESheepAnimation;
import com.newera.docs.entity.variant.NESheepVariant;
import net.minecraft.client.model.AdultAndBabyModelPair;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.animal.sheep.Sheep;
import org.jetbrains.annotations.NotNull;

public class NESheepRenderer extends MobRenderer<Sheep, NESheepRenderState, NESheepAbstractModel> {
    private static final Identifier NORMAL_TEXTURE = Identifier.fromNamespaceAndPath("newera", "textures/entity/sheep/sheep.png");
    private static final Identifier NORMAL_BABY_TEXTURE = Identifier.fromNamespaceAndPath("newera", "textures/entity/sheep/sheep_baby.png");
    private static final Identifier WARM_TEXTURE = Identifier.fromNamespaceAndPath("newera", "textures/entity/sheep/warm_sheep.png");
    private static final Identifier WARM_BABY_TEXTURE = Identifier.fromNamespaceAndPath("newera", "textures/entity/sheep/warm_sheep_baby.png");
    private static final Identifier COLD_TEXTURE = Identifier.fromNamespaceAndPath("newera", "textures/entity/sheep/cold_sheep.png");
    private static final Identifier COLD_BABY_TEXTURE = Identifier.fromNamespaceAndPath("newera", "textures/entity/sheep/cold_sheep_baby.png");

    private final Map<NESheepVariant.ModelType, AdultAndBabyModelPair<NESheepAbstractModel>> models;

    public NESheepRenderer(final EntityRendererProvider.Context context) {
        super(context, new NESheepModel(context.bakeLayer(NEModelLayers.NE_SHEEP)), 0.7F);
        this.models = bakeModels(context);
        this.addLayer(new NESheepFurLayer(this, context));
    }

    private static Map<NESheepVariant.ModelType, AdultAndBabyModelPair<NESheepAbstractModel>> bakeModels(final EntityRendererProvider.Context context) {
        return Maps.newEnumMap(
                Map.of(
                        NESheepVariant.ModelType.NORMAL,
                        new AdultAndBabyModelPair<>(new NESheepModel(context.bakeLayer(NEModelLayers.NE_SHEEP)), new NESheepBabyModel(context.bakeLayer(NEModelLayers.NE_SHEEP_BABY))),
                        NESheepVariant.ModelType.WARM,
                        new AdultAndBabyModelPair<>(new NESheepModel(context.bakeLayer(NEModelLayers.NE_WARM_SHEEP)), new NESheepBabyModel(context.bakeLayer(NEModelLayers.NE_WARM_SHEEP_BABY))),
                        NESheepVariant.ModelType.COLD,
                        new AdultAndBabyModelPair<>(new NESheepModel(context.bakeLayer(NEModelLayers.NE_COLD_SHEEP)), new NESheepBabyModel(context.bakeLayer(NEModelLayers.NE_COLD_SHEEP_BABY)))
                )
        );
    }

    @Override
    @NotNull
    public NESheepRenderState createRenderState() {
        return new NESheepRenderState();
    }

    @Override
    public void extractRenderState(@NotNull Sheep entity, @NotNull NESheepRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.isSheared = entity.isSheared();
        state.color = entity.getColor();

        if (entity instanceof INESheepAnimation mixinEntity) {
            state.variant = mixinEntity.getVariant();
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
    public void submit(@NotNull final NESheepRenderState state, @NotNull final PoseStack poseStack, @NotNull final SubmitNodeCollector submitNodeCollector, @NotNull final CameraRenderState camera) {
        if (state.variant != null) {
            AdultAndBabyModelPair<NESheepAbstractModel> pair = this.models.get(state.variant.value().modelAndTexture().model());
            if (pair != null) {
                this.model = pair.getModel(state.isBaby);
                super.submit(state, poseStack, submitNodeCollector, camera);
            }
        }
    }

    @Override
    @NotNull
    public Identifier getTextureLocation(@NotNull final NESheepRenderState state) {
        if (state.variant == null) {
            return MissingTextureAtlasSprite.getLocation();
        }
        if (state.isBaby) {
            return state.variant.value().babyTexture().texturePath();
        } else {
            return state.variant.value().modelAndTexture().asset().texturePath();
        }
    }
}

