package com.newera.docs.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.newera.docs.entity.animation.INECatAnimation;
import com.newera.docs.entity.model.cat.NECatAbstractModel;
import com.newera.docs.entity.model.cat.NECatBabyModel;
import com.newera.docs.entity.model.cat.NECatModel;
import com.newera.docs.entity.render.NEModelLayers;
import com.newera.docs.entity.state.NECatRenderState;
import net.minecraft.client.model.AdultAndBabyModelPair;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.animal.feline.Ocelot;
import org.jetbrains.annotations.NotNull;

public class NEOcelotRenderer extends MobRenderer<Ocelot, NECatRenderState, NECatAbstractModel> {
    private static final Identifier OCELOT_LOCATION = Identifier.fromNamespaceAndPath("newera", "textures/entity/cat/ocelot.png");
    private static final Identifier OCELOT_BABY_LOCATION = Identifier.fromNamespaceAndPath("newera", "textures/entity/cat/ocelot_baby.png");

    private final AdultAndBabyModelPair<NECatAbstractModel> models;

    public NEOcelotRenderer(EntityRendererProvider.Context context) {
        super(context, new NECatModel(context.bakeLayer(NEModelLayers.NE_OCELOT)), 0.4F);
        this.models = new AdultAndBabyModelPair<>(
                new NECatModel(context.bakeLayer(NEModelLayers.NE_OCELOT)),
                new NECatBabyModel(context.bakeLayer(NEModelLayers.NE_OCELOT_BABY))
        );
    }

    @Override
    @NotNull
    public NECatRenderState createRenderState() {
        return new NECatRenderState();
    }

    @Override
    public void extractRenderState(@NotNull Ocelot entity, @NotNull NECatRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        if (entity instanceof INECatAnimation mixinEntity) {
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
        return state.isBaby ? OCELOT_BABY_LOCATION : OCELOT_LOCATION;
    }
}

