package com.newera.docs.entity.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.newera.docs.entity.model.horse.NEHorseAbstractModel;
import com.newera.docs.entity.state.NEHorseRenderState;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;

public class NEHorseSaddleLayer<M extends NEHorseAbstractModel> extends RenderLayer<NEHorseRenderState, M> {
    private final Identifier SADDLE_LOCATION = Identifier.fromNamespaceAndPath("newera", "textures/entity/horse/horse_equipment/saddle.png");

    public NEHorseSaddleLayer(RenderLayerParent<NEHorseRenderState, M> renderLayerParent) {
        super(renderLayerParent);
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int light, NEHorseRenderState state, float yaw, float pitch) {
        if (state.isSaddled) {
            RenderLayer.renderColoredCutoutModel(this.getParentModel(), SADDLE_LOCATION, poseStack, submitNodeCollector, light, state, -1, OverlayTexture.NO_OVERLAY);
        }
    }
}
