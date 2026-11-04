// Made with Blockbench 5.1.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


package com.newera.docs.entity.model.chicken;

import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.model.geom.ModelPart;
import com.newera.docs.entity.animation.animal.chicken.ChickenBabyAnimation;

public class NEChickenBabyModel extends NEChickenAbstractModel {
    public NEChickenBabyModel(ModelPart root) {
        super(
            root,
            ChickenBabyAnimation.CHICKEN_BABY_SIT,
            ChickenBabyAnimation.CHICKEN_BABY_SITTING,
            ChickenBabyAnimation.CHICKEN_BABY_SLEEP,
            ChickenBabyAnimation.CHICKEN_BABY_SLEEPPING,
            ChickenBabyAnimation.CHICKEN_BABY_WAKE_UP,
            ChickenBabyAnimation.CHICKEN_BABY_STAND,
            ChickenBabyAnimation.CHICKEN_BABY_WALK,
            ChickenBabyAnimation.CHICKEN_BABY_GRAZING,
            ChickenBabyAnimation.CHICKEN_BABY_IDLE,
            ChickenBabyAnimation.CHICKEN_BABY_FLYING,
            ChickenBabyAnimation.CHICKEN_BABY_TAKE_OFF,
            ChickenBabyAnimation.CHICKEN_BABY_ON_AIR,
            ChickenBabyAnimation.CHICKEN_BABY_LANDING
        );
    }

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition right_leg = root.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 2).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-0.5F, 2.0F, -1.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, -2.0F, 0.0F));

		PartDefinition left_leg = root.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(2, 2).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(0, 1).addBox(-0.5F, 2.0F, -1.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, -2.0F, 0.0F));

		PartDefinition centroid = root.addOrReplaceChild("centroid", CubeListBuilder.create(), PartPose.offset(0.0F, -2.0F, 0.0F));

		PartDefinition body = centroid.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -2.25F, -0.75F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(10, 8).addBox(-1.0F, -0.25F, -1.75F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.75F, -1.25F));

		PartDefinition right_wing = centroid.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(4, 8).addBox(-1.0F, 0.0F, -1.0F, 1.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -2.0F, 0.0F));

		PartDefinition left_wing = centroid.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(6, 8).addBox(0.0F, 0.0F, -1.0F, 1.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, -2.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 16, 16);
	}

	}