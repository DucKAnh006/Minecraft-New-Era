package com.newera.docs.entity.render.layer;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Map;
import com.newera.docs.entity.model.sheep.NESheepAbstractModel;
import com.newera.docs.entity.model.sheep.NESheepWoolModel;
import com.newera.docs.entity.render.NEModelLayers;
import com.newera.docs.entity.state.NESheepRenderState;
import com.newera.docs.entity.variant.NESheepVariant;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;

public class NESheepFurLayer extends RenderLayer<NESheepRenderState, NESheepAbstractModel> {
    private static final Identifier NORMAL_TEXTURE = Identifier.fromNamespaceAndPath("newera", "textures/entity/sheep/sheep_wool.png");
    private static final Identifier WARM_TEXTURE = Identifier.fromNamespaceAndPath("newera", "textures/entity/sheep/warm_sheep_wool.png");
    private static final Identifier COLD_TEXTURE = Identifier.fromNamespaceAndPath("newera", "textures/entity/sheep/cold_sheep_wool.png");

    private static final Identifier NORMAL_BABY_TEXTURE = Identifier.fromNamespaceAndPath("newera", "textures/entity/sheep/sheep_baby_wool.png");
    private static final Identifier WARM_BABY_TEXTURE = Identifier.fromNamespaceAndPath("newera", "textures/entity/sheep/warm_sheep_baby_wool.png");
    private static final Identifier COLD_BABY_TEXTURE = Identifier.fromNamespaceAndPath("newera", "textures/entity/sheep/cold_sheep_baby_wool.png");

    private final Map<NESheepVariant.ModelType, net.minecraft.client.model.AdultAndBabyModelPair<NESheepAbstractModel>> models;

    public NESheepFurLayer(RenderLayerParent<NESheepRenderState, NESheepAbstractModel> renderLayerParent, EntityRendererProvider.Context context) {
        super(renderLayerParent);
        this.models = bakeModels(context);
    }

    private static Map<NESheepVariant.ModelType, net.minecraft.client.model.AdultAndBabyModelPair<NESheepAbstractModel>> bakeModels(final EntityRendererProvider.Context context) {
        return Maps.newEnumMap(
                Map.of(
                        NESheepVariant.ModelType.NORMAL,
                        new net.minecraft.client.model.AdultAndBabyModelPair<>(new NESheepWoolModel(context.bakeLayer(NEModelLayers.NE_SHEEP_WOOL)), new com.newera.docs.entity.model.sheep.NESheepBabyModel(context.bakeLayer(NEModelLayers.NE_SHEEP_BABY))),
                        NESheepVariant.ModelType.WARM,
                        new net.minecraft.client.model.AdultAndBabyModelPair<>(new NESheepWoolModel(context.bakeLayer(NEModelLayers.NE_WARM_SHEEP_WOOL)), new com.newera.docs.entity.model.sheep.NESheepBabyModel(context.bakeLayer(NEModelLayers.NE_WARM_SHEEP_BABY))),
                        NESheepVariant.ModelType.COLD,
                        new net.minecraft.client.model.AdultAndBabyModelPair<>(new NESheepWoolModel(context.bakeLayer(NEModelLayers.NE_COLD_SHEEP_WOOL)), new com.newera.docs.entity.model.sheep.NESheepBabyModel(context.bakeLayer(NEModelLayers.NE_COLD_SHEEP_BABY)))
                )
        );
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int light, NESheepRenderState state, float yaw, float pitch) {
        if (!state.isSheared && state.variant != null) {
            net.minecraft.client.model.AdultAndBabyModelPair<NESheepAbstractModel> pair = this.models.get(state.variant.value().modelAndTexture().model());
            if (pair != null) {
                NESheepAbstractModel model = state.isBaby ? pair.babyModel() : pair.adultModel();
                Identifier texture;
                if (state.isBaby) {
                    texture = NORMAL_BABY_TEXTURE;
                } else {
                    switch (state.variant.value().modelAndTexture().model()) {
                        case WARM: texture = WARM_TEXTURE; break;
                        case COLD: texture = COLD_TEXTURE; break;
                        default: texture = NORMAL_TEXTURE; break;
                    }
                }

                int color;
                if (state.color != null) {
                    color = state.color.getTextureDiffuseColor();
                } else {
                    color = -1;
                }

                RenderLayer.renderColoredCutoutModel(model, texture, poseStack, submitNodeCollector, light, state, color, OverlayTexture.NO_OVERLAY);
            }
        }
    }
}

