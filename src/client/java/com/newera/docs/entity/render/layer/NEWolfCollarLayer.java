package com.newera.docs.entity.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.newera.docs.entity.state.NEWolfRenderState;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.Identifier;
import net.minecraft.client.renderer.texture.OverlayTexture;

public class NEWolfCollarLayer extends RenderLayer<NEWolfRenderState, EntityModel<NEWolfRenderState>> {
    private static final Identifier WOLF_COLLAR = Identifier.fromNamespaceAndPath("newera", "textures/entity/wolf/wolf_collar.png");
    private static final Identifier WOLF_BABY_COLLAR = Identifier.fromNamespaceAndPath("newera", "textures/entity/wolf/wolf_collar_baby.png");

    public NEWolfCollarLayer(RenderLayerParent<NEWolfRenderState, EntityModel<NEWolfRenderState>> renderer) {
        super(renderer);
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int packedLight, NEWolfRenderState state, float yaw, float pitch) {
        if (!state.isTame || state.collarColor == null) return;

        Identifier texture = state.isBaby ? WOLF_BABY_COLLAR : WOLF_COLLAR;
        int color = state.collarColor.getTextureDiffuseColor();

        RenderLayer.renderColoredCutoutModel(this.getParentModel(), texture, poseStack, submitNodeCollector, packedLight, state, color, OverlayTexture.NO_OVERLAY);
    }
}

