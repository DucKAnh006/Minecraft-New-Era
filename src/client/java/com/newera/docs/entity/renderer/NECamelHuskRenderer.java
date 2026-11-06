package com.newera.docs.entity.renderer;

import com.newera.docs.entity.model.camel.NECamelModel;
import com.newera.docs.entity.render.NEModelLayers;
import com.newera.docs.entity.render.layer.NECamelSaddleLayer;
import com.newera.docs.entity.state.NECamelRenderState;
import com.newera.docs.entity.animation.INECamelAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.animal.camel.Camel;
import org.jetbrains.annotations.NotNull;

public class NECamelHuskRenderer extends MobRenderer<Camel, NECamelRenderState, EntityModel<NECamelRenderState>> {

    private static final Identifier CAMEL_HUSK_LOCATION = Identifier.fromNamespaceAndPath("newera", "textures/entity/camel/camel_husk.png");

    public NECamelHuskRenderer(final EntityRendererProvider.Context context) {
        super(context, new NECamelModel(context.bakeLayer(NEModelLayers.NE_CAMEL_HUSK)), 0.7F);
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
        state.isBaby = false;
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
    @NotNull
    public Identifier getTextureLocation(@NotNull final NECamelRenderState state) {
        return CAMEL_HUSK_LOCATION;
    }
}

