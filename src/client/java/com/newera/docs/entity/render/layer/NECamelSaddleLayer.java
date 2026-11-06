package com.newera.docs.entity.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.newera.docs.entity.model.camel.NECamelSaddleModel;
import com.newera.docs.entity.render.NEModelLayers;
import com.newera.docs.entity.state.NECamelRenderState;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;

public class NECamelSaddleLayer extends RenderLayer<NECamelRenderState, EntityModel<NECamelRenderState>> {
    private final Identifier SADDLE_LOCATION = Identifier.fromNamespaceAndPath("newera", "textures/entity/camel/saddle_camel.png");
    private final NECamelSaddleModel saddleModel;

    public NECamelSaddleLayer(RenderLayerParent<NECamelRenderState, EntityModel<NECamelRenderState>> renderLayerParent, EntityRendererProvider.Context context) {
        super(renderLayerParent);
        this.saddleModel = new NECamelSaddleModel(context.bakeLayer(NEModelLayers.NE_CAMEL_SADDLE));
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int light, NECamelRenderState state, float yaw, float pitch) {
        if (state.isSaddled && !state.isBaby) {
            RenderLayer.renderColoredCutoutModel(this.saddleModel, SADDLE_LOCATION, poseStack, submitNodeCollector, light, state, -1, OverlayTexture.NO_OVERLAY);
        }
    }
}

