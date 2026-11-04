package com.newera.docs.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;

import com.newera.docs.entity.model.horse.NEBabyDonkeyMuleModel;
import com.newera.docs.entity.model.horse.NEDonkeyMuleModel;
import com.newera.docs.entity.model.horse.NEDonkeyMuleAbstractModel;
import com.newera.docs.entity.render.NEModelLayers;
import com.newera.docs.entity.state.NEHorseRenderState;
import com.newera.docs.entity.animation.INEHorseAnimation;
import net.minecraft.client.model.AdultAndBabyModelPair;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.animal.equine.AbstractChestedHorse;
import org.jetbrains.annotations.NotNull;

public class NEDonkeyMuleRenderer extends MobRenderer<AbstractChestedHorse, NEHorseRenderState, NEDonkeyMuleAbstractModel> {

    private final AdultAndBabyModelPair<NEDonkeyMuleAbstractModel> models;

    public NEDonkeyMuleRenderer(final EntityRendererProvider.Context context) {
        super(context, new NEDonkeyMuleModel(context.bakeLayer(NEModelLayers.NE_DONKEY)), 0.75F);
        this.models = new AdultAndBabyModelPair<>(
                new NEDonkeyMuleModel(context.bakeLayer(NEModelLayers.NE_DONKEY)),
                new NEBabyDonkeyMuleModel(context.bakeLayer(NEModelLayers.NE_BABY_DONKEY))
        );
        this.addLayer(new com.newera.docs.entity.render.layer.NEHorseSaddleLayer(this));
        // Note: For donkey chest, it's actually drawn natively in the donkey.png texture on the left_bag and right_bag bones!
        // So we don't necessarily need a NEDonkeyChestLayer unless the chest is completely separate!
        // But since the user explicitly asked for "chest for donkey", I'll add the layer.
        this.addLayer(new com.newera.docs.entity.render.layer.NEDonkeyChestLayer(this));
    }

    @Override
    @NotNull
    public NEHorseRenderState createRenderState() {
        return new NEHorseRenderState();
    }

    @Override
    public void extractRenderState(@NotNull AbstractChestedHorse entity, @NotNull NEHorseRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.isBaby = entity.isBaby();
        state.hasChest = entity.hasChest();
        state.isSaddled = entity.isSaddled();
        
        // Use variant to store entity type to determine donkey vs mule texture
        if (entity.getType() == EntityTypes.MULE) {
            state.variant = net.minecraft.world.entity.animal.equine.Variant.BROWN; // Dummy for MULE
        } else {
            state.variant = net.minecraft.world.entity.animal.equine.Variant.GRAY; // Dummy for DONKEY
        }

        if (entity instanceof INEHorseAnimation mixinEntity) {
            state.sitAnimationState.copyFrom(mixinEntity.getSitAnimationState());
            state.sittingHoldAnimationState.copyFrom(mixinEntity.getSittingHoldAnimationState());
            state.sleepAnimationState.copyFrom(mixinEntity.getSleepAnimationState());
            state.sleepingHoldAnimationState.copyFrom(mixinEntity.getSleepingHoldAnimationState());
            state.wakeUpAnimationState.copyFrom(mixinEntity.getWakeUpAnimationState());
            state.standAnimationState.copyFrom(mixinEntity.getStandAnimationState());
            state.walkAnimationState.copyFrom(mixinEntity.getWalkAnimationState());
            state.grazingAnimationState.copyFrom(mixinEntity.getGrazingAnimationState());
            state.idleAnimationState.copyFrom(mixinEntity.getIdleAnimationState());
            state.rearAnimationState.copyFrom(mixinEntity.getRearAnimationState());
            state.attackAnimationState.copyFrom(mixinEntity.getAttackAnimationState());
        }
    }

    @Override
    public void submit(@NotNull final NEHorseRenderState state, @NotNull final PoseStack poseStack, @NotNull final SubmitNodeCollector submitNodeCollector, @NotNull final CameraRenderState camera) {
        this.model = this.models.getModel(state.isBaby);
        super.submit(state, poseStack, submitNodeCollector, camera);
    }

    @Override
    @NotNull
    public Identifier getTextureLocation(@NotNull final NEHorseRenderState state) {
        if (state.variant == net.minecraft.world.entity.animal.equine.Variant.BROWN) {
            return state.isBaby ? Identifier.fromNamespaceAndPath("newera", "textures/entity/horse/mule_baby.png") : Identifier.fromNamespaceAndPath("newera", "textures/entity/horse/mule.png");
        }
        return state.isBaby ? Identifier.fromNamespaceAndPath("newera", "textures/entity/horse/donkey_baby.png") : Identifier.fromNamespaceAndPath("newera", "textures/entity/horse/donkey.png");
    }
}
