package com.newera.docs.entity.model.cow;

import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import com.newera.docs.entity.animation.animal.cow.CowBabyAnimation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.model.geom.ModelPart;

public class NECowBabyModel extends NECowAbstractModel {
    public NECowBabyModel(ModelPart root) {
        super(
            root,
            CowBabyAnimation.COW_BABY_SIT,
            CowBabyAnimation.COW_BABY_SITTING,
            CowBabyAnimation.COW_BABY_SLEEP,
            CowBabyAnimation.COW_BABY_SLEEPPING,
            CowBabyAnimation.COW_BABY_WAKE_UP,
            CowBabyAnimation.COW_BABY_STAND,
            CowBabyAnimation.COW_BABY_WALK,
            CowBabyAnimation.COW_BABY_GRAZING,
            CowBabyAnimation.COW_BABY_IDLE
        );
    }
	public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -3.0F, -6.0F, 8.0F, 6.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -9.0F, 0.0F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 18).addBox(-3.0F, -4.569F, -4.833F, 6.0F, 6.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(0, 29).addBox(-2.0F, -1.569F, -6.833F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.431F, -5.167F));

		PartDefinition left_ear = head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(13, 30).addBox(0.0F, -0.5F, -0.5F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, -3.069F, -2.333F));

		PartDefinition right_ear = head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(13, 34).addBox(-2.0F, -0.5F, -0.5F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, -3.069F, -2.333F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 35).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 6.0F));

		PartDefinition right_hind_leg = root.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(22, 27).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.475F, -6.0F, 3.5F));

		PartDefinition left_hind_leg = root.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(34, 27).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(2.475F, -6.0F, 3.5F));

		PartDefinition right_front_leg = root.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(22, 18).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.475F, -6.0F, -3.5F));

		PartDefinition left_front_leg = root.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(34, 18).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(2.475F, -6.0F, -3.5F));
		return LayerDefinition.create(meshdefinition, 64, 64);
	}
	}
