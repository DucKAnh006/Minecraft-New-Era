package com.newera.docs.entity.model.pig;

import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.model.geom.ModelPart;
import com.newera.docs.entity.animation.animal.pig.PigBabyAnimation;

public class NEPigBabyModel extends NEPigAbstractModel {
    public NEPigBabyModel(ModelPart root) {
        super(
            root,
            PigBabyAnimation.PIG_BABY_SIT,
            PigBabyAnimation.PIG_BABY_SITTING,
            PigBabyAnimation.PIG_BABY_SLEEP,
            PigBabyAnimation.PIG_BABY_SLEEPING,
            PigBabyAnimation.PIG_BABY_WAKE_UP,
            PigBabyAnimation.PIG_BABY_STAND_UP,
            PigBabyAnimation.PIG_BABY_WALK,
            PigBabyAnimation.PIG_BABY_GRAZING,
            PigBabyAnimation.PIG_BABY_IDLE
        );
    }

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition right_hind_leg = root.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(23, 4).addBox(-0.975F, 0.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.5F, -2.0F, 4.0F));

		PartDefinition left_hind_leg = root.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(0, 4).addBox(-1.025F, 0.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, -2.0F, 4.0F));

		PartDefinition left_front_leg = root.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(0, 0).addBox(-1.025F, 0.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, -2.0F, -3.0F));

		PartDefinition right_front_leg = root.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(23, 0).addBox(-0.975F, 0.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.5F, -2.0F, -3.0F));

		PartDefinition centroid = root.addOrReplaceChild("centroid", CubeListBuilder.create(), PartPose.offset(0.0F, -2.0F, 0.0F));

		PartDefinition body = centroid.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, -3.0F, -4.5F, 7.0F, 6.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, 0.5F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(28, 16).addBox(-0.5F, -2.5F, -0.5F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, -0.5F, 4.5F, -0.6109F, 0.0F, 0.0F));

		PartDefinition head = centroid.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 15).addBox(-3.51F, -5.0F, -5.0F, 7.02F, 6.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(6, 27).addBox(-1.5F, -2.0F, -6.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, -2.0F));

		PartDefinition right_ear = head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(24, 15).mirror().addBox(-2.5F, -1.0F, -0.5F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.99F, -5.0F, -2.5F, 0.0F, 0.0F, -0.9599F));

		PartDefinition left_ear = head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(24, 15).addBox(-0.5F, -1.0F, -0.5F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.99F, -5.0F, -2.5F, 0.0F, 0.0F, 0.9599F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	}