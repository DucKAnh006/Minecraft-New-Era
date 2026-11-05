package com.newera.docs.entity;

import com.newera.docs.entity.model.cow.NECowBabyModel;
import com.newera.docs.entity.model.cow.NECowModel;
import com.newera.docs.entity.model.pig.NEPigBabyModel;
import com.newera.docs.entity.model.pig.NEPigModel;
import com.newera.docs.entity.model.sheep.NESheepBabyModel;
import com.newera.docs.entity.model.sheep.NESheepModel;
import com.newera.docs.entity.model.sheep.NESheepWoolModel;
import com.newera.docs.entity.renderer.NECowRenderer;
import com.newera.docs.entity.renderer.NEPigRenderer;
import com.newera.docs.entity.renderer.NESheepRenderer;
import com.newera.docs.entity.render.NEModelLayers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.world.entity.EntityTypes;

public class NewEraModCustomEntityClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModelLayerRegistry.registerModelLayer(NEModelLayers.NE_COW, NECowModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(NEModelLayers.NE_COW_BABY, NECowBabyModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(NEModelLayers.NE_COLD_COW, NECowModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(NEModelLayers.NE_COLD_COW_BABY, NECowBabyModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(NEModelLayers.NE_WARM_COW, NECowModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(NEModelLayers.NE_WARM_COW_BABY, NECowBabyModel::createBodyLayer);

        ModelLayerRegistry.registerModelLayer(NEModelLayers.NE_CHICKEN, com.newera.docs.entity.model.chicken.NEChickenModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(NEModelLayers.NE_CHICKEN_BABY, com.newera.docs.entity.model.chicken.NEChickenBabyModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(NEModelLayers.NE_COLD_CHICKEN, com.newera.docs.entity.model.chicken.NEChickenModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(NEModelLayers.NE_COLD_CHICKEN_BABY, com.newera.docs.entity.model.chicken.NEChickenBabyModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(NEModelLayers.NE_WARM_CHICKEN, com.newera.docs.entity.model.chicken.NEChickenModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(NEModelLayers.NE_WARM_CHICKEN_BABY, com.newera.docs.entity.model.chicken.NEChickenBabyModel::createBodyLayer);

        ModelLayerRegistry.registerModelLayer(NEModelLayers.NE_PIG, NEPigModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(NEModelLayers.NE_PIG_SADDLE, NEPigModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(NEModelLayers.NE_PIG_BABY, NEPigBabyModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(NEModelLayers.NE_COLD_PIG, NEPigModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(NEModelLayers.NE_COLD_PIG_BABY, NEPigBabyModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(NEModelLayers.NE_WARM_PIG, NEPigModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(NEModelLayers.NE_WARM_PIG_BABY, NEPigBabyModel::createBodyLayer);

        ModelLayerRegistry.registerModelLayer(NEModelLayers.NE_SHEEP, NESheepModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(NEModelLayers.NE_SHEEP_BABY, NESheepBabyModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(NEModelLayers.NE_COLD_SHEEP, NESheepModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(NEModelLayers.NE_COLD_SHEEP_BABY, NESheepBabyModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(NEModelLayers.NE_WARM_SHEEP, NESheepModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(NEModelLayers.NE_WARM_SHEEP_BABY, NESheepBabyModel::createBodyLayer);

        ModelLayerRegistry.registerModelLayer(NEModelLayers.NE_SHEEP_WOOL, NESheepWoolModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(NEModelLayers.NE_COLD_SHEEP_WOOL, NESheepWoolModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(NEModelLayers.NE_WARM_SHEEP_WOOL, NESheepWoolModel::createBodyLayer);

        EntityRendererRegistry.register(EntityTypes.COW, NECowRenderer::new);
        EntityRendererRegistry.register(EntityTypes.CHICKEN, com.newera.docs.entity.renderer.NEChickenRenderer::new);
        EntityRendererRegistry.register(EntityTypes.PIG, NEPigRenderer::new);
        EntityRendererRegistry.register(EntityTypes.SHEEP, NESheepRenderer::new);

        ModelLayerRegistry.registerModelLayer(NEModelLayers.NE_CAMEL, com.newera.docs.entity.model.camel.NECamelModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(NEModelLayers.NE_CAMEL_BABY, com.newera.docs.entity.model.camel.NECamelBabyModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(NEModelLayers.NE_CAMEL_SADDLE, com.newera.docs.entity.model.camel.NECamelSaddleModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(NEModelLayers.NE_CAMEL_HUSK, com.newera.docs.entity.model.camel.NECamelModel::createBodyLayer);

        ModelLayerRegistry.registerModelLayer(NEModelLayers.NE_HORSE, com.newera.docs.entity.model.horse.NEHorseModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(NEModelLayers.NE_BABY_HORSE, com.newera.docs.entity.model.horse.NEBabyHorseModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(NEModelLayers.NE_DONKEY, com.newera.docs.entity.model.horse.NEDonkeyMuleModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(NEModelLayers.NE_BABY_DONKEY, com.newera.docs.entity.model.horse.NEBabyDonkeyMuleModel::createBodyLayer);

        EntityRendererRegistry.register(EntityTypes.CAMEL, com.newera.docs.entity.renderer.NECamelRenderer::new);
        EntityRendererRegistry.register(EntityTypes.HORSE, com.newera.docs.entity.renderer.NEHorseRenderer::new);
        EntityRendererRegistry.register(EntityTypes.DONKEY, com.newera.docs.entity.renderer.NEDonkeyMuleRenderer::new);
        EntityRendererRegistry.register(EntityTypes.MULE, com.newera.docs.entity.renderer.NEDonkeyMuleRenderer::new);
        EntityRendererRegistry.register(EntityTypes.ZOMBIE_HORSE, com.newera.docs.entity.renderer.NEUndeadHorseRenderer::new);
        EntityRendererRegistry.register(EntityTypes.SKELETON_HORSE, com.newera.docs.entity.renderer.NEUndeadHorseRenderer::new);

        ModelLayerRegistry.registerModelLayer(NEModelLayers.NE_CAT, com.newera.docs.entity.model.cat.NECatModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(NEModelLayers.NE_CAT_BABY, com.newera.docs.entity.model.cat.NECatBabyModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(NEModelLayers.NE_CAT_COLLAR, com.newera.docs.entity.model.cat.NECatModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(NEModelLayers.NE_CAT_BABY_COLLAR, com.newera.docs.entity.model.cat.NECatBabyModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(NEModelLayers.NE_OCELOT, com.newera.docs.entity.model.cat.NECatModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(NEModelLayers.NE_OCELOT_BABY, com.newera.docs.entity.model.cat.NECatBabyModel::createBodyLayer);

        EntityRendererRegistry.register(EntityTypes.CAT, com.newera.docs.entity.renderer.NECatRenderer::new);
        EntityRendererRegistry.register(EntityTypes.OCELOT, com.newera.docs.entity.renderer.NEOcelotRenderer::new);

        ModelLayerRegistry.registerModelLayer(NEModelLayers.NE_PARROT, com.newera.docs.entity.model.parrot.NEParrotModel::createBodyLayer);
        EntityRendererRegistry.register(EntityTypes.PARROT, com.newera.docs.entity.renderer.NEParrotRenderer::new);

        ModelLayerRegistry.registerModelLayer(NEModelLayers.NE_WOLF, com.newera.docs.entity.model.wolf.NEWolfModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(NEModelLayers.NE_WOLF_BABY, com.newera.docs.entity.model.wolf.NEWolfBabyModel::createBodyLayer);
        ModelLayerRegistry.registerModelLayer(NEModelLayers.NE_WOLF_ARMOR, com.newera.docs.entity.model.wolf.NEWolfModel::createArmorLayer);

        EntityRendererRegistry.register(EntityTypes.WOLF, com.newera.docs.entity.renderer.NEWolfRenderer::new);

        ModelLayerRegistry.registerModelLayer(NEModelLayers.NE_ENDER_DRAGON, com.newera.docs.entity.model.ender_dragon.NEEnderDragonModel::createBodyLayer);
        EntityRendererRegistry.register(EntityTypes.ENDER_DRAGON, com.newera.docs.entity.renderer.NEEnderDragonRenderer::new);

        ModelLayerRegistry.registerModelLayer(NEModelLayers.NE_ENDER_CRYSTAL, com.newera.docs.entity.model.ender_crystal.NEEnderCrystalModel::createBodyLayer);
        EntityRendererRegistry.register(EntityTypes.END_CRYSTAL, com.newera.docs.entity.renderer.NEEnderCrystalRenderer::new);
    }
}

