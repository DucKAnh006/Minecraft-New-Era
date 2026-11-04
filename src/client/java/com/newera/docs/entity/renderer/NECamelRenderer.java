package com.newera.docs.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.newera.docs.entity.model.camel.NECamelBabyModel;
import com.newera.docs.entity.model.camel.NECamelModel;
import com.newera.docs.entity.render.NEModelLayers;
import com.newera.docs.entity.render.layer.NECamelSaddleLayer;
import com.newera.docs.entity.state.NECamelRenderState;
import com.newera.docs.entity.animation.INECamelAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.animal.camel.Camel;
import org.jetbrains.annotations.NotNull;

public class NECamelRenderer extends MobRenderer<Camel, NECamelRenderState, EntityModel<NECamelRenderState>> {

    private static final Identifier CAMEL_LOCATION = Identifier.fromNamespaceAndPath("newera", "textures/entity/camel/camel.png");
    private static final Identifier CAMEL_BABY_LOCATION = Identifier.fromNamespaceAndPath("newera", "textures/entity/camel/camel_baby.png");

    private final EntityModel<NECamelRenderState> adultModel;
    private final EntityModel<NECamelRenderState> babyModel;

    public NECamelRenderer(final EntityRendererProvider.Context context) {
        super(context, new NECamelModel(context.bakeLayer(NEModelLayers.NE_CAMEL)), 0.7F);
        this.adultModel = this.getModel();
        this.babyModel = new NECamelBabyModel(context.bakeLayer(NEModelLayers.NE_CAMEL_BABY));
        this.addLayer(new NECamelSaddleLayer(this, context));
    }

    @Override
    @NotNull
    public NECamelRenderState createRenderState() {
        return new NECamelRenderState();
    }

    @Override
    public void extractRenderState(@NotNull Camel entity, @NotNull NECamelRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.isBaby = entity.isBaby();
        state.isSaddled = entity.isSaddled();

        if (entity instanceof INECamelAnimation mixinEntity) {
            state.sitAnimationState.copyFrom(mixinEntity.getSitAnimationState());
            state.sittingHoldAnimationState.copyFrom(mixinEntity.getSittingHoldAnimationState());
            state.sleepAnimationState.copyFrom(mixinEntity.getSleepAnimationState());
            state.sleepingHoldAnimationState.copyFrom(mixinEntity.getSleepingHoldAnimationState());
            state.wakeUpAnimationState.copyFrom(mixinEntity.getWakeUpAnimationState());
            state.standAnimationState.copyFrom(mixinEntity.getStandAnimationState());
            state.eatingAnimationState.copyFrom(mixinEntity.getEatingAnimationState());
            state.idleAnimationState.copyFrom(mixinEntity.getIdleAnimationState());
            state.dashAnimationState.copyFrom(mixinEntity.getDashAnimationState());
        }
    }

    @Override
    public void submit(@NotNull final NECamelRenderState state, @NotNull final PoseStack poseStack, @NotNull final SubmitNodeCollector submitNodeCollector, @NotNull final CameraRenderState camera) {
        this.model = state.isBaby ? this.babyModel : this.adultModel;
        super.submit(state, poseStack, submitNodeCollector, camera);
    }

    @Override
    @NotNull
    public Identifier getTextureLocation(@NotNull final NECamelRenderState state) {
        return state.isBaby ? CAMEL_BABY_LOCATION : CAMEL_LOCATION;
    }
}
