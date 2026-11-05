package com.newera.docs.entity.model.camel;

import com.newera.docs.entity.animation.animal.camel.CamelBabyAnimation;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.model.geom.ModelPart;

public class NECamelBabyModel extends NECamelAbstractModel {

	public NECamelBabyModel(ModelPart root) {
		super(
				root,
				CamelBabyAnimation.CAMEL_BABY_SIT,
				CamelBabyAnimation.CAMEL_BABY_SITTING,
				CamelBabyAnimation.CAMEL_BABY_SLEEP,
				CamelBabyAnimation.CAMEL_BABY_SLEEPING,
				CamelBabyAnimation.CAMEL_BABY_WAKE_UP,
				CamelBabyAnimation.CAMEL_BABY_STAND_UP,
				CamelBabyAnimation.CAMEL_BABY_WALK,
				CamelBabyAnimation.CAMEL_BABY_EATING,
				CamelBabyAnimation.CAMEL_BABY_IDLE,
				CamelBabyAnimation.CAMEL_BABY_DASH
		);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition mesh = new MeshDefinition();
		PartDefinition partdefinition = mesh.getRoot();
		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 14).addBox(-4.5F, -4.0F, -8.0F, 9.0F, 8.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -17.0F, 0.0F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(50, 38).addBox(-1.5F, -0.5F, 0.0F, 3.0F, 9.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.5F, 8.05F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(20, 0).addBox(-2.5F, -3.0F, -7.5F, 5.0F, 5.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-2.5F, -12.0F, -7.5F, 5.0F, 9.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(0, 14).addBox(-2.5F, -12.0F, -10.5F, 5.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, -7.5F));

		PartDefinition right_ear = head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(37, 0).addBox(-3.0F, -0.5F, -1.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.5F, -11.0F, -4.0F));

		PartDefinition left_ear = head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(47, 0).addBox(0.0F, -0.5F, -1.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, -11.0F, -4.0F));

		PartDefinition right_front_leg = root.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(36, 14).addBox(-1.5F, -0.5F, -1.5F, 3.0F, 13.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.9F, -12.5F, -5.5F));

		PartDefinition left_front_leg = root.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(48, 14).addBox(-1.5F, -0.5F, -1.5F, 3.0F, 13.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(2.9F, -12.5F, -5.5F));

		PartDefinition left_hind_leg = root.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(12, 38).addBox(-1.5F, -0.5F, -1.5F, 3.0F, 13.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(2.9F, -12.5F, 5.5F));

		PartDefinition right_hind_leg = root.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(0, 38).addBox(-1.4F, -0.5F, -1.5F, 3.0F, 13.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, -12.5F, 5.5F));
		return LayerDefinition.create(mesh, 64, 64);
	}
}

