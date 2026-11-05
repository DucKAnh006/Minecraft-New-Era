package com.newera.docs.entity.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.newera.docs.entity.model.horse.NEHorseAbstractModel;
import com.newera.docs.entity.state.NEHorseRenderState;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.animal.equine.Markings;

import java.util.Map;

public class NEHorseMarkingLayer extends RenderLayer<NEHorseRenderState, NEHorseAbstractModel> {

    private static final Map<Markings, Identifier> ADULT_TEXTURES = Map.of(
            Markings.WHITE, Identifier.fromNamespaceAndPath("newera", "textures/entity/horse/horse_markings_white.png"),
            Markings.WHITE_FIELD, Identifier.fromNamespaceAndPath("newera", "textures/entity/horse/horse_markings_whitefield.png"),
            Markings.WHITE_DOTS, Identifier.fromNamespaceAndPath("newera", "textures/entity/horse/horse_markings_whitedots.png"),
            Markings.BLACK_DOTS, Identifier.fromNamespaceAndPath("newera", "textures/entity/horse/horse_markings_blackdots.png")
    );

    private static final Map<Markings, Identifier> BABY_TEXTURES = Map.of(
            Markings.WHITE, Identifier.fromNamespaceAndPath("newera", "textures/entity/horse/horse_markings_white_baby.png"),
            Markings.WHITE_FIELD, Identifier.fromNamespaceAndPath("newera", "textures/entity/horse/horse_markings_whitefield_baby.png"),
            Markings.WHITE_DOTS, Identifier.fromNamespaceAndPath("newera", "textures/entity/horse/horse_markings_whitedots_baby.png"),
            Markings.BLACK_DOTS, Identifier.fromNamespaceAndPath("newera", "textures/entity/horse/horse_markings_blackdots_baby.png")
    );

    public NEHorseMarkingLayer(RenderLayerParent<NEHorseRenderState, NEHorseAbstractModel> renderLayerParent) {
        super(renderLayerParent);
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int packedLight, NEHorseRenderState state, float yRot, float xRot) {
        if (state.markings != null && state.markings != Markings.NONE) {
            Identifier texture = state.isBaby ? BABY_TEXTURES.get(state.markings) : ADULT_TEXTURES.get(state.markings);
            if (texture != null) {
                renderColoredCutoutModel(this.getParentModel(), texture, poseStack, submitNodeCollector, packedLight, state, -1, OverlayTexture.NO_OVERLAY);
            }
        }
    }
}

