package com.newera.docs.entity.model.pig;

import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.model.geom.ModelPart;
import com.newera.docs.entity.animation.animal.pig.PigAnimation;

public class NEPigModel extends NEPigAbstractModel {
    public NEPigModel(ModelPart root) {
        super(
            root,
            PigAnimation.PIG_SIT,
            PigAnimation.PIG_SITTING,
            PigAnimation.PIG_SLEEP,
            PigAnimation.PIG_SLEEPING,
            PigAnimation.PIG_WAKE_UP,
            PigAnimation.PIG_STAND_UP,
            PigAnimation.PIG_WALK,
            PigAnimation.PIG_GRAZING,
            PigAnimation.PIG_IDLE
        );
    }

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition right_hind_leg = root.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(0, 16).addBox(-1.9F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, -6.0F, 6.0F));

		PartDefinition left_hind_leg = root.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(0, 16).mirror().addBox(-2.1F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(3.0F, -6.0F, 6.0F));

		PartDefinition left_front_leg = root.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(0, 16).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(2.9F, -6.0F, -6.0F));

		PartDefinition right_front_leg = root.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(0, 16).addBox(-1.9F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, -6.0F, -6.0F));

		PartDefinition centroid = root.addOrReplaceChild("centroid", CubeListBuilder.create(), PartPose.offset(0.0F, -7.0F, -1.0F));

		PartDefinition head = centroid.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.0F, -8.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(16, 16).addBox(-2.0F, 0.0F, -10.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.0F, -7.0F));

		PartDefinition left_ear = head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(27, 0).addBox(0.0F, 0.0F, -0.5F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -4.0F, -4.5F, 0.0F, 0.0F, -0.3491F));

		PartDefinition right_ear = head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(27, 0).mirror().addBox(-3.0F, 0.0F, -0.5F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-3.0F, -4.0F, -4.5F, 0.0F, 0.0F, 0.3491F));

		PartDefinition body = centroid.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, 10.0F));

		PartDefinition body_part = body.addOrReplaceChild("body_part", CubeListBuilder.create().texOffs(28, 32).addBox(-5.0F, -18.0F, -5.0F, 10.0F, 16.0F, 8.0F, new CubeDeformation(0.5F))
		.texOffs(28, 8).addBox(-5.0F, -18.0F, -5.0F, 10.0F, 16.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, -2.5F));

		PartDefinition tail_part = tail.addOrReplaceChild("tail_part", CubeListBuilder.create().texOffs(38, -5).addBox(0.0F, -0.5F, -0.5F, 0.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.0472F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	}
