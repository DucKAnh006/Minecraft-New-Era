package com.newera.docs.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;

import com.newera.docs.entity.model.wolf.NEWolfBabyModel;
import com.newera.docs.entity.model.wolf.NEWolfModel;
import com.newera.docs.entity.render.NEModelLayers;
import com.newera.docs.entity.state.NEWolfRenderState;
import com.newera.docs.entity.render.layer.NEWolfArmorLayer;
import com.newera.docs.entity.render.layer.NEWolfCollarLayer;
import net.minecraft.client.model.AdultAndBabyModelPair;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.animal.wolf.Wolf;
import org.jetbrains.annotations.NotNull;

public class NEWolfRenderer extends MobRenderer<Wolf, NEWolfRenderState, EntityModel<NEWolfRenderState>> {

    private static final Identifier WOLF_TEXTURE = Identifier.fromNamespaceAndPath("newera", "textures/entity/wolf/wolf.png");

    private final AdultAndBabyModelPair<EntityModel<NEWolfRenderState>> models;

    public NEWolfRenderer(final EntityRendererProvider.Context context) {
        super(context, new NEWolfModel(context.bakeLayer(NEModelLayers.NE_WOLF)), 0.5F);
        this.models = new AdultAndBabyModelPair<>(
            new NEWolfModel(context.bakeLayer(NEModelLayers.NE_WOLF)),
            new NEWolfBabyModel(context.bakeLayer(NEModelLayers.NE_WOLF_BABY))
        );
        
        this.addLayer(new NEWolfArmorLayer(this, 
            new NEWolfModel(context.bakeLayer(NEModelLayers.NE_WOLF_ARMOR)),
            new NEWolfBabyModel(context.bakeLayer(NEModelLayers.NE_WOLF_ARMOR))
        ));
        
        this.addLayer(new NEWolfCollarLayer(this));
    }

    @Override
    @NotNull
    public NEWolfRenderState createRenderState() {
        return new NEWolfRenderState();
    }

    @Override
    public void extractRenderState(@NotNull Wolf entity, @NotNull NEWolfRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.isBaby = entity.isBaby();
        state.hasArmor = entity.getBodyArmorItem() != null && !entity.getBodyArmorItem().isEmpty();
        state.tailAngle = entity.getTailAngle();
        state.isTame = entity.isTame();
        state.collarColor = entity.getCollarColor();
        
        state.isAngry = entity.isAngry();
        try {
            boolean foundHolder = false;
            Class<?> clazz = entity.getClass();
            while (clazz != null && state.variant == null) {
                for (java.lang.reflect.Method m : clazz.getDeclaredMethods()) {
                    if (m.getReturnType() == net.minecraft.core.Holder.class) {
                        m.setAccessible(true);
                        Object holderObj = m.invoke(entity);
                        foundHolder = true;
                        if (holderObj instanceof net.minecraft.core.Holder<?> holder) {
                            if (holder.value() instanceof net.minecraft.world.entity.animal.wolf.WolfVariant v) {
                                state.variant = v;
                                break;
                            }
                        }
                    }
                }
                clazz = clazz.getSuperclass();
            }
        } catch (Exception e) {}

        if (entity instanceof com.newera.docs.entity.animation.INEWolfAnimation mixinEntity) {
            state.sitAnimationState.copyFrom(mixinEntity.getSitAnimationState());
            state.sittingHoldAnimationState.copyFrom(mixinEntity.getSittingHoldAnimationState());
            state.sleepAnimationState.copyFrom(mixinEntity.getSleepAnimationState());
            state.sleepingHoldAnimationState.copyFrom(mixinEntity.getSleepingHoldAnimationState());
            state.wakeUpAnimationState.copyFrom(mixinEntity.getWakeUpAnimationState());
            state.standAnimationState.copyFrom(mixinEntity.getStandAnimationState());
            state.howlAnimationState.copyFrom(mixinEntity.getHowlAnimationState());
            state.sitHowlingAnimationState.copyFrom(mixinEntity.getSitHowlingAnimationState());
            state.idleAnimationState.copyFrom(mixinEntity.getIdleAnimationState());
        }
    }

    @Override
    public void submit(@NotNull final NEWolfRenderState state, @NotNull final PoseStack poseStack, @NotNull final SubmitNodeCollector submitNodeCollector, @NotNull final CameraRenderState camera) {
        this.model = this.models.getModel(state.isBaby);
        super.submit(state, poseStack, submitNodeCollector, camera);
    }

    @Override
    @NotNull
    public Identifier getTextureLocation(@NotNull final NEWolfRenderState state) {
        if (state.variant == null) {
            return WOLF_TEXTURE;
        }
        net.minecraft.world.entity.animal.wolf.WolfVariant.AssetInfo info = state.isBaby ? state.variant.babyInfo() : state.variant.adultInfo();
        Identifier id;
        if (state.isAngry) {
            id = info.angry().texturePath();
        } else if (state.isTame) {
            id = info.tame().texturePath();
        } else {
            id = info.wild().texturePath();
        }
        String path = id.getPath();
        if (!path.endsWith(".png")) {
            Identifier newId = Identifier.fromNamespaceAndPath(id.getNamespace(), "textures/" + path + ".png");
            return newId;
        }
        return id;
    }
}
