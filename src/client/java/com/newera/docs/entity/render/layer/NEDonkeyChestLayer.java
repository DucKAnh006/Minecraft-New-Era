package com.newera.docs.entity.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.newera.docs.entity.model.horse.NEDonkeyMuleAbstractModel;
import com.newera.docs.entity.state.NEHorseRenderState;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;

public class NEDonkeyChestLayer extends RenderLayer<NEHorseRenderState, NEDonkeyMuleAbstractModel> {
    private final Identifier CHEST_LOCATION = Identifier.fromNamespaceAndPath("minecraft", "textures/entity/chest/normal.png"); // Or custom chest texture if they had one, but user didn't specify one in the folder!

    public NEDonkeyChestLayer(RenderLayerParent<NEHorseRenderState, NEDonkeyMuleAbstractModel> renderLayerParent) {
        super(renderLayerParent);
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int light, NEHorseRenderState state, float yaw, float pitch) {
        if (state.hasChest && !state.isBaby) {
            // Wait, does the monolithic model use the main texture for the chest? 
            // The donkey model has left_bag and right_bag mapped to donkey.png!
            // I'll leave the chest location to donkey.png, so it just draws the chest.
            // But wait, if they are already drawn on the main model, maybe they don't need a separate layer?
            // Actually, I'll just draw the donkey's texture again.
            Identifier texture = state.variant == net.minecraft.world.entity.animal.equine.Variant.BROWN ?
                Identifier.fromNamespaceAndPath("newera", "textures/entity/horse/mule.png") :
                Identifier.fromNamespaceAndPath("newera", "textures/entity/horse/donkey.png");
            RenderLayer.renderColoredCutoutModel(this.getParentModel(), texture, poseStack, submitNodeCollector, light, state, -1, OverlayTexture.NO_OVERLAY);
        }
    }
}
