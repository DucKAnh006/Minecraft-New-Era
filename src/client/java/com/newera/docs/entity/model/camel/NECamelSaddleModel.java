package com.newera.docs.entity.model.camel;

import com.newera.docs.entity.animation.animal.camel.CamelAnimation;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.model.geom.ModelPart;

public class NECamelSaddleModel extends NECamelAbstractModel {

	public NECamelSaddleModel(ModelPart root) {
		super(
				root,
				CamelAnimation.CAMEL_SIT,
				CamelAnimation.CAMEL_SITTING,
				CamelAnimation.CAMEL_SLEEP,
				CamelAnimation.CAMEL_SLEEPING,
				CamelAnimation.CAMEL_WAKE_UP,
				CamelAnimation.CAMEL_STAND_UP,
				CamelAnimation.CAMEL_WALK,
				CamelAnimation.CAMEL_EATING,
				CamelAnimation.CAMEL_IDLE,
				CamelAnimation.CAMEL_DASH
		);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.5F, -20.0F, 9.5F));

		PartDefinition saddle = body.addOrReplaceChild("saddle", CubeListBuilder.create().texOffs(74, 64).addBox(-5.0F, -17.0F, -15.5F, 9.0F, 5.0F, 11.0F, new CubeDeformation(0.1F))
		.texOffs(92, 114).addBox(-4.0F, -20.0F, -15.5F, 7.0F, 3.0F, 11.0F, new CubeDeformation(0.1F))
		.texOffs(0, 89).addBox(-8.0F, -12.0F, -23.5F, 15.0F, 12.0F, 27.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(-0.5F, -9.0F, 3.5F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, -5.0F, -19.5F));

		PartDefinition bridle = head.addOrReplaceChild("bridle", CubeListBuilder.create().texOffs(60, 87).addBox(-4.0F, -5.0F, -15.0F, 7.0F, 8.0F, 19.0F, new CubeDeformation(0.1F))
		.texOffs(21, 64).addBox(-4.0F, -19.0F, -15.0F, 7.0F, 14.0F, 7.0F, new CubeDeformation(0.1F))
		.texOffs(50, 64).addBox(-3.0F, -19.0F, -21.1F, 5.0F, 5.0F, 6.0F, new CubeDeformation(0.1F))
		.texOffs(74, 70).addBox(2.0F, -17.0F, -18.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(74, 70).mirror().addBox(-4.0F, -17.0F, -18.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition left_ear = head.addOrReplaceChild("left_ear", CubeListBuilder.create(), PartPose.offset(2.5F, -18.0F, -9.5F));

		PartDefinition right_ear = head.addOrReplaceChild("right_ear", CubeListBuilder.create(), PartPose.offset(-3.5F, -18.0F, -9.5F));

		PartDefinition reins = head.addOrReplaceChild("reins", CubeListBuilder.create().texOffs(98, 42).addBox(0.0F, 0.0F, 0.0F, 0.0F, 7.0F, 15.0F, new CubeDeformation(0.0F))
		.texOffs(84, 57).addBox(-7.4F, 0.0F, 15.0F, 7.4F, 7.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(98, 42).addBox(-7.4F, 0.0F, 0.0F, 0.0F, 7.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offset(3.2F, -16.0F, -17.0F));

		PartDefinition hump = body.addOrReplaceChild("hump", CubeListBuilder.create(), PartPose.offset(0.0F, -12.0F, -9.5F));

		PartDefinition right_front_leg = root.addOrReplaceChild("right_front_leg", CubeListBuilder.create(), PartPose.offset(-4.9F, -23.0F, -10.5F));

		PartDefinition left_front_leg = root.addOrReplaceChild("left_front_leg", CubeListBuilder.create(), PartPose.offset(4.9F, -23.0F, -10.5F));

		PartDefinition left_hind_leg = root.addOrReplaceChild("left_hind_leg", CubeListBuilder.create(), PartPose.offset(4.9F, -23.0F, 9.5F));

		PartDefinition right_hind_leg = root.addOrReplaceChild("right_hind_leg", CubeListBuilder.create(), PartPose.offset(-4.9F, -23.0F, 9.5F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}
}
