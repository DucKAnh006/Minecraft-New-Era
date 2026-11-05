package com.newera.docs.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.newera.docs.entity.animation.INECatAnimation;
import com.newera.docs.entity.model.cat.NECatAbstractModel;
import com.newera.docs.entity.model.cat.NECatBabyModel;
import com.newera.docs.entity.model.cat.NECatModel;
import com.newera.docs.entity.render.NEModelLayers;
import com.newera.docs.entity.render.layer.NECatCollarLayer;
import com.newera.docs.entity.state.NECatRenderState;
import net.minecraft.client.model.AdultAndBabyModelPair;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.animal.feline.Cat;
import org.jetbrains.annotations.NotNull;

public class NECatRenderer extends MobRenderer<Cat, NECatRenderState, NECatAbstractModel> {
    private final AdultAndBabyModelPair<NECatAbstractModel> models;

    public NECatRenderer(EntityRendererProvider.Context context) {
        super(context, new NECatModel(context.bakeLayer(NEModelLayers.NE_CAT)), 0.4F);
        this.models = new AdultAndBabyModelPair<>(
                new NECatModel(context.bakeLayer(NEModelLayers.NE_CAT)),
                new NECatBabyModel(context.bakeLayer(NEModelLayers.NE_CAT_BABY))
        );
        this.addLayer(new NECatCollarLayer(this, context));
    }

    @Override
    @NotNull
    public NECatRenderState createRenderState() {
        return new NECatRenderState();
    }

    @Override
    public void extractRenderState(@NotNull Cat entity, @NotNull NECatRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        if (entity instanceof INECatAnimation mixinEntity) {
            state.variant = mixinEntity.getVariant();
            state.isTame = mixinEntity.isTame();
            state.collarColor = mixinEntity.getCollarColor();
            state.lookWeight = mixinEntity.getLookWeight();

            state.sitAnimationState.copyFrom(mixinEntity.getSitAnimationState());
            state.sittingHoldAnimationState.copyFrom(mixinEntity.getSittingHoldAnimationState());
            state.sleepAnimationState.copyFrom(mixinEntity.getSleepAnimationState());
            state.sleepingHoldAnimationState.copyFrom(mixinEntity.getSleepingHoldAnimationState());
            state.wakeUpAnimationState.copyFrom(mixinEntity.getWakeUpAnimationState());
            state.standAnimationState.copyFrom(mixinEntity.getStandAnimationState());
            state.idleAnimationState.copyFrom(mixinEntity.getIdleAnimationState());

            state.stretchAnimationState.copyFrom(mixinEntity.getStretchAnimationState());
            state.stalkingAnimationState.copyFrom(mixinEntity.getStalkingAnimationState());
            state.pounceAnimationState.copyFrom(mixinEntity.getPounceAnimationState());
        }
    }

    @Override
    public void submit(@NotNull NECatRenderState state, @NotNull PoseStack poseStack, @NotNull SubmitNodeCollector submitNodeCollector, @NotNull CameraRenderState camera) {
        this.model = this.models.getModel(state.isBaby);
        super.submit(state, poseStack, submitNodeCollector, camera);
    }

    @Override
    @NotNull
    public Identifier getTextureLocation(@NotNull NECatRenderState state) {
        if (state.variant == null) {
            return MissingTextureAtlasSprite.getLocation();
        }
        Identifier variantId = state.variant.unwrapKey().get().identifier();
        String path = "textures/entity/cat/cat_" + variantId.getPath();
        if (state.isBaby) {
            path += "_baby.png";
        } else {
            path += ".png";
        }
        return Identifier.fromNamespaceAndPath("newera", path);
    }
}

