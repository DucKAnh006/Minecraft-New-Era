package com.newera.docs.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.newera.docs.entity.animation.INEPigAnimation;
import com.newera.docs.entity.model.pig.NEPigBabyModel;
import com.newera.docs.entity.model.pig.NEPigModel;
import com.newera.docs.entity.render.NEModelLayers;
import com.newera.docs.entity.state.NEPigRenderState;
import net.minecraft.client.model.AdultAndBabyModelPair;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.animal.pig.Pig;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import com.google.common.collect.Maps;
import net.minecraft.world.entity.animal.pig.PigVariant;

public class NEPigRenderer extends MobRenderer<Pig, NEPigRenderState, EntityModel<NEPigRenderState>> {

    private final Map<PigVariant.ModelType, AdultAndBabyModelPair<EntityModel<NEPigRenderState>>> models;

    public NEPigRenderer(final EntityRendererProvider.Context context) {
        super(context, new NEPigModel(context.bakeLayer(NEModelLayers.NE_PIG)), 0.7F);
        this.models = bakeModels(context);
        this.addLayer(new net.minecraft.client.renderer.entity.layers.SimpleEquipmentLayer<>(
                this,
                context.getEquipmentRenderer(),
                net.minecraft.client.resources.model.EquipmentClientInfo.LayerType.PIG_SADDLE,
                state -> state.saddle,
                new NEPigModel(context.bakeLayer(NEModelLayers.NE_PIG_SADDLE)),
                null
        ));
    }

    private static Map<PigVariant.ModelType, AdultAndBabyModelPair<EntityModel<NEPigRenderState>>> bakeModels(final EntityRendererProvider.Context context) {
        return Maps.newEnumMap(
                Map.of(
                        PigVariant.ModelType.NORMAL,
                        new AdultAndBabyModelPair<>(new NEPigModel(context.bakeLayer(NEModelLayers.NE_PIG)), new NEPigBabyModel(context.bakeLayer(NEModelLayers.NE_PIG_BABY))),
                        PigVariant.ModelType.COLD,
                        new AdultAndBabyModelPair<>(new NEPigModel(context.bakeLayer(NEModelLayers.NE_COLD_PIG)), new NEPigBabyModel(context.bakeLayer(NEModelLayers.NE_COLD_PIG_BABY)))
                )
        );
    }

    @Override
    @NotNull
    public NEPigRenderState createRenderState() {
        return new NEPigRenderState();
    }

    @Override
    public void extractRenderState(@NotNull Pig entity, @NotNull NEPigRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.isBaby = entity.isBaby();
        state.variant = entity.getVariant().value();
        net.minecraft.world.item.ItemStack saddleStack = entity.getItemBySlot(net.minecraft.world.entity.EquipmentSlot.SADDLE);
        state.isSaddled = !saddleStack.isEmpty();
        state.saddle = saddleStack.copy();

        if (entity instanceof INEPigAnimation mixinEntity) {
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
    public void submit(@NotNull final NEPigRenderState state, @NotNull final PoseStack poseStack, @NotNull final SubmitNodeCollector submitNodeCollector, @NotNull final CameraRenderState camera) {
        if (state.variant != null) {
            AdultAndBabyModelPair<EntityModel<NEPigRenderState>> pair = this.models.get(state.variant.modelAndTexture().model());
            this.model = pair.getModel(state.isBaby);
            super.submit(state, poseStack, submitNodeCollector, camera);
        }
    }

    @Override
    @NotNull
    public Identifier getTextureLocation(@NotNull final NEPigRenderState state) {
        if (state.variant == null) {
            return MissingTextureAtlasSprite.getLocation();
        }
        return state.isBaby ? state.variant.babyTexture().texturePath() : state.variant.modelAndTexture().asset().texturePath();
    }
}
