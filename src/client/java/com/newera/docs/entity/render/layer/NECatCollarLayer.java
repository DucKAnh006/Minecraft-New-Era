package com.newera.docs.entity.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.newera.docs.entity.model.cat.NECatAbstractModel;
import com.newera.docs.entity.model.cat.NECatBabyModel;
import com.newera.docs.entity.model.cat.NECatModel;
import com.newera.docs.entity.render.NEModelLayers;
import com.newera.docs.entity.state.NECatRenderState;
import net.minecraft.client.model.AdultAndBabyModelPair;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;

public class NECatCollarLayer extends RenderLayer<NECatRenderState, NECatAbstractModel> {
    private static final Identifier COLLAR_LOCATION = Identifier.fromNamespaceAndPath("newera", "textures/entity/cat/cat_collar.png");
    private static final Identifier COLLAR_BABY_LOCATION = Identifier.fromNamespaceAndPath("newera", "textures/entity/cat/cat_collar_baby.png");
    private final AdultAndBabyModelPair<NECatAbstractModel> collarModels;

    public NECatCollarLayer(RenderLayerParent<NECatRenderState, NECatAbstractModel> renderLayerParent, EntityRendererProvider.Context context) {
        super(renderLayerParent);
        this.collarModels = new AdultAndBabyModelPair<>(
                new NECatModel(context.bakeLayer(NEModelLayers.NE_CAT_COLLAR)),
                new NECatBabyModel(context.bakeLayer(NEModelLayers.NE_CAT_BABY_COLLAR))
        );
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int light, NECatRenderState state, float yaw, float pitch) {
        if (state.isTame && state.collarColor != null) {
            int color = state.collarColor.getTextureDiffuseColor();
            NECatAbstractModel model = this.collarModels.getModel(state.isBaby);
            Identifier texture = state.isBaby ? COLLAR_BABY_LOCATION : COLLAR_LOCATION;
            RenderLayer.renderColoredCutoutModel(model, texture, poseStack, submitNodeCollector, light, state, color, OverlayTexture.NO_OVERLAY);
        }
    }
}

