package com.newera.docs.entity.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.newera.docs.entity.state.NEWolfRenderState;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.Identifier;
import net.minecraft.client.renderer.texture.OverlayTexture;

public class NEWolfArmorLayer extends RenderLayer<NEWolfRenderState, EntityModel<NEWolfRenderState>> {
    private static final Identifier WOLF_ARMOR_LOCATION = Identifier.fromNamespaceAndPath("newera", "textures/entity/wolf/wolf_armor.png");
    private final EntityModel<NEWolfRenderState> adultModel;
    private final EntityModel<NEWolfRenderState> babyModel;

    public NEWolfArmorLayer(RenderLayerParent<NEWolfRenderState, EntityModel<NEWolfRenderState>> renderer, EntityModel<NEWolfRenderState> adultModel, EntityModel<NEWolfRenderState> babyModel) {
        super(renderer);
        this.adultModel = adultModel;
        this.babyModel = babyModel;
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int packedLight, NEWolfRenderState state, float yaw, float pitch) {
        if (!state.hasArmor) return;
        EntityModel<NEWolfRenderState> armorModel = state.isBaby ? this.babyModel : this.adultModel;

        RenderLayer.renderColoredCutoutModel(armorModel, WOLF_ARMOR_LOCATION, poseStack, submitNodeCollector, packedLight, state, -1, OverlayTexture.NO_OVERLAY);
    }
}

