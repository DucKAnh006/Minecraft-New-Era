package com.newera.docs.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Map;

import com.newera.docs.entity.model.parrot.NEParrotModel;
import com.newera.docs.entity.render.NEModelLayers;
import com.newera.docs.entity.state.NEParrotRenderState;
import com.newera.docs.entity.animation.INEParrotAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.animal.parrot.Parrot;
import org.jetbrains.annotations.NotNull;

public class NEParrotRenderer extends MobRenderer<Parrot, NEParrotRenderState, EntityModel<NEParrotRenderState>> {

    public NEParrotRenderer(final EntityRendererProvider.Context context) {
        super(context, new NEParrotModel(context.bakeLayer(NEModelLayers.NE_PARROT)), 0.3F);
    }

    @Override
    @NotNull
    public NEParrotRenderState createRenderState() {
        return new NEParrotRenderState();
    }

    @Override
    public void extractRenderState(@NotNull Parrot entity, @NotNull NEParrotRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.isBaby = entity.isBaby();
        state.variant = entity.getVariant();

        float turnSpeed = net.minecraft.util.Mth.wrapDegrees(entity.yBodyRot - entity.yBodyRotO);
        state.roll = net.minecraft.util.Mth.clamp(turnSpeed * -0.6F, -45.0F, 45.0F) * (float)(Math.PI / 180.0);

        if (entity instanceof INEParrotAnimation mixinEntity) {
            state.idleGroundAnimationState.copyFrom(mixinEntity.getIdleGroundAnimationState());
            state.groundToFlyAnimationState.copyFrom(mixinEntity.getGroundToFlyAnimationState());
            state.flyToGroundAnimationState.copyFrom(mixinEntity.getFlyToGroundAnimationState());
            state.idleFlyAnimationState.copyFrom(mixinEntity.getIdleFlyAnimationState());

            state.flyFlapAnimationState.copyFrom(mixinEntity.getFlyFlapAnimationState());
            state.glideFlapAnimationState.copyFrom(mixinEntity.getGlideFlapAnimationState());
            state.walkAnimationState.copyFrom(mixinEntity.getWalkAnimationState());
            state.sitAnimationState.copyFrom(mixinEntity.getSitAnimationState());
            state.sittingAnimationState.copyFrom(mixinEntity.getSittingAnimationState());
            state.standAnimationState.copyFrom(mixinEntity.getStandAnimationState());
            state.sleepAnimationState.copyFrom(mixinEntity.getSleepAnimationState());
            state.sleepingAnimationState.copyFrom(mixinEntity.getSleepingAnimationState());
            state.wakeUpAnimationState.copyFrom(mixinEntity.getWakeUpAnimationState());
            state.danceAnimationState.copyFrom(mixinEntity.getDanceAnimationState());
        }
    }

    @Override
    @NotNull
    public Identifier getTextureLocation(@NotNull final NEParrotRenderState state) {
        if (state.variant == null) {
            return Identifier.fromNamespaceAndPath("newera", "textures/entity/parrot/parrot_red_blue.png");
        }
        return switch (state.variant) {
            case RED_BLUE -> Identifier.fromNamespaceAndPath("newera", "textures/entity/parrot/parrot_red_blue.png");
            case BLUE -> Identifier.fromNamespaceAndPath("newera", "textures/entity/parrot/parrot_blue.png");
            case GREEN -> Identifier.fromNamespaceAndPath("newera", "textures/entity/parrot/parrot_green.png");
            case YELLOW_BLUE -> Identifier.fromNamespaceAndPath("newera", "textures/entity/parrot/parrot_yellow_blue.png");
            case GRAY -> Identifier.fromNamespaceAndPath("newera", "textures/entity/parrot/parrot_grey.png");
        };
    }
}

