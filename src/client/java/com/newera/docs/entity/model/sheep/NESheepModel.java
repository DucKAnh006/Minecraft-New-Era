package com.newera.docs.entity.model.sheep;

import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.model.geom.ModelPart;
import com.newera.docs.entity.animation.animal.sheep.SheepAnimation;

public class NESheepModel extends NESheepAbstractModel {
    public NESheepModel(ModelPart root) {
        super(
            root,
            SheepAnimation.SHEEP_SIT,
            SheepAnimation.SHEEP_SITTING,
            SheepAnimation.SHEEP_SLEEP,
            SheepAnimation.SHEEP_SLEEPPING,
            SheepAnimation.SHEEP_WAKE_UP,
            SheepAnimation.SHEEP_STAND,
            SheepAnimation.SHEEP_WALK,
            SheepAnimation.SHEEP_GRAZING,
            SheepAnimation.SHEEP_IDLE
        );
    }

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, -14.25F, 0.0F));

		PartDefinition centroid = body.addOrReplaceChild("centroid", CubeListBuilder.create().texOffs(28, 8).addBox(-4.0F, -9.0F, -3.0F, 8.0F, 16.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition tail = centroid.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, 7.25F, 1.0F));

		PartDefinition tail_part = tail.addOrReplaceChild("tail_part", CubeListBuilder.create().texOffs(17, 56).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.5708F, 0.0F, 0.0F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -5.0F, -7.0F, 6.0F, 6.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(38, 32).addBox(-2.0F, -2.0F, -9.0F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.75F, -7.75F));

		PartDefinition left_ear = head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(54, 33).mirror().addBox(0.0F, 0.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(3.0F, -4.0F, -4.5F));

		PartDefinition right_ear = head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(54, 33).addBox(-2.0F, 0.0F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, -4.0F, -4.5F));

		PartDefinition horn = head.addOrReplaceChild("horn", CubeListBuilder.create().texOffs(28, 50).addBox(3.0F, -2.0F, -2.0F, 3.0F, 6.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(50, 57).addBox(3.0F, 4.0F, 2.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(50, 57).mirror().addBox(-6.0F, 4.0F, 2.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(28, 50).mirror().addBox(-6.0F, -2.0F, -2.0F, 3.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -4.0F, -2.0F, -1.5708F, 0.0F, 0.0F));

		PartDefinition horn_top = horn.addOrReplaceChild("horn_top", CubeListBuilder.create().texOffs(52, 45).addBox(-1.0F, -7.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(52, 45).mirror().addBox(-4.8F, -7.0F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.9F, 0.0F, 0.0F, 1.0472F, 0.0F, 0.0F));

		PartDefinition right_hind_leg = root.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(0, 16).addBox(-1.95F, -0.3F, -1.99F, 4.0F, 12.0F, 4.0F, new CubeDeformation(-0.2F))
		.texOffs(0, 55).addBox(-1.95F, -0.14F, -1.99F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(-2.75F, -11.5F, 5.5F));

		PartDefinition left_hind_leg = root.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(0, 16).mirror().addBox(-2.05F, -0.3F, -1.99F, 4.0F, 12.0F, 4.0F, new CubeDeformation(-0.2F)).mirror(false)
		.texOffs(0, 55).mirror().addBox(-2.05F, -0.14F, -1.99F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offset(2.75F, -11.5F, 5.5F));

		PartDefinition right_front_leg = root.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(0, 16).addBox(-1.95F, -0.3F, -2.02F, 4.0F, 12.0F, 4.0F, new CubeDeformation(-0.2F))
		.texOffs(0, 55).addBox(-1.95F, -0.14F, -2.02F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(-2.75F, -11.5F, -5.5F));

		PartDefinition left_front_leg = root.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(0, 16).mirror().addBox(-2.05F, -0.3F, -2.02F, 4.0F, 12.0F, 4.0F, new CubeDeformation(-0.2F)).mirror(false)
		.texOffs(0, 56).mirror().addBox(-2.05F, -0.14F, -2.02F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.25F)).mirror(false), PartPose.offset(2.75F, -11.5F, -5.5F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	}