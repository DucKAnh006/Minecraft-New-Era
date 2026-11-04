package com.newera.docs.entity.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.newera.docs.entity.model.horse.NEHorseAbstractModel;
import com.newera.docs.entity.state.NEHorseRenderState;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;

public class NEHorseArmorLayer extends RenderLayer<NEHorseRenderState, NEHorseAbstractModel> {

    public NEHorseArmorLayer(RenderLayerParent<NEHorseRenderState, NEHorseAbstractModel> renderLayerParent) {
        super(renderLayerParent);
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int light, NEHorseRenderState state, float yaw, float pitch) {
        ItemStack itemStack = state.armor;
        if (!itemStack.isEmpty()) {
            // Note: In 1.21.x, AnimalArmorItem has getMaterial() or getTexture() maybe.
            // Actually, AnimalArmorItem uses getTexture() which returns an Identifier.
            // But we have custom textures in newera:textures/entity/horse/horse_equipment!
            // Let's just map them based on the item registry name.
            String itemName = net.minecraft.core.registries.BuiltInRegistries.ITEM.getKey(itemStack.getItem()).getPath();
            String textureName = "iron";
            if (itemName.contains("diamond")) textureName = "diamond";
            else if (itemName.contains("gold")) textureName = "gold";
            else if (itemName.contains("leather")) textureName = "leather";
            else if (itemName.contains("iron")) textureName = "iron";
            else if (itemName.contains("copper")) textureName = "copper";
            else if (itemName.contains("netherite")) textureName = "netherite";

            Identifier armorLocation = Identifier.fromNamespaceAndPath("newera", "textures/entity/horse/horse_equipment/" + textureName + ".png");

            int color = -1;
            if (textureName.equals("leather")) {
                color = DyedItemColor.getOrDefault(itemStack, -6265536);
                RenderLayer.renderColoredCutoutModel(this.getParentModel(), armorLocation, poseStack, submitNodeCollector, light, state, color, OverlayTexture.NO_OVERLAY);
                
                // Render overlay
                Identifier overlayLocation = Identifier.fromNamespaceAndPath("newera", "textures/entity/horse/horse_equipment/leather_overlay.png");
                RenderLayer.renderColoredCutoutModel(this.getParentModel(), overlayLocation, poseStack, submitNodeCollector, light, state, -1, OverlayTexture.NO_OVERLAY);
            } else {
                RenderLayer.renderColoredCutoutModel(this.getParentModel(), armorLocation, poseStack, submitNodeCollector, light, state, -1, OverlayTexture.NO_OVERLAY);
            }
        }
    }
}
