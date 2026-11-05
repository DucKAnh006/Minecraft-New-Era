package com.newera.docs.entity.model.sheep;

import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.model.geom.ModelPart;
import com.newera.docs.entity.animation.animal.sheep.SheepAnimation;

public class NESheepWoolModel extends NESheepAbstractModel {
    public NESheepWoolModel(ModelPart root) {
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

		PartDefinition centroid = body.addOrReplaceChild("centroid", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition wool_centroid = centroid.addOrReplaceChild("wool_centroid", CubeListBuilder.create().texOffs(3, 2).addBox(-4.45F, -9.75F, -3.4F, 9.0F, 17.0F, 7.0F, new CubeDeformation(0.2F))
		.texOffs(46, 37).mirror().addBox(-5.45F, -9.75F, -4.4F, 11.0F, 18.0F, 9.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(44, 0).mirror().addBox(-5.45F, -9.75F, -5.6F, 11.0F, 18.0F, 10.0F, new CubeDeformation(0.4F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition tail = centroid.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, 7.25F, 1.0F));

		PartDefinition wool_tail = tail.addOrReplaceChild("wool_tail", CubeListBuilder.create().texOffs(24, 45).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.5708F, 0.0F, 0.0F));

		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, -1.75F, -7.75F));

        PartDefinition left_ear = head.addOrReplaceChild("left_ear", CubeListBuilder.create(), PartPose.ZERO);
        PartDefinition right_ear = head.addOrReplaceChild("right_ear", CubeListBuilder.create(), PartPose.ZERO);

		PartDefinition wool_head = head.addOrReplaceChild("wool_head", CubeListBuilder.create().texOffs(0, 39).addBox(-3.0F, -2.0F, -2.25F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.4F))
		.texOffs(0, 34).addBox(-3.0F, -2.0F, -5.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.4F)), PartPose.offset(0.0F, -3.0F, -3.0F));

		PartDefinition right_hind_leg = root.addOrReplaceChild("right_hind_leg", CubeListBuilder.create(), PartPose.offset(-2.75F, -11.5F, 5.5F));

		PartDefinition right_hind_wool = right_hind_leg.addOrReplaceChild("right_hind_wool", CubeListBuilder.create().texOffs(16, 51).addBox(-1.95F, -0.14F, -1.99F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.1F))
		.texOffs(0, 51).addBox(-1.95F, -0.14F, -1.99F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.3F))
		.texOffs(0, 27).addBox(-1.95F, 6.45F, -1.99F, 4.0F, 1.7F, 4.0F, new CubeDeformation(0.3F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition left_hind_leg = root.addOrReplaceChild("left_hind_leg", CubeListBuilder.create(), PartPose.offset(2.75F, -11.5F, 5.5F));

		PartDefinition left_hind_wool = left_hind_leg.addOrReplaceChild("left_hind_wool", CubeListBuilder.create().texOffs(16, 51).mirror().addBox(-2.05F, -0.14F, -1.99F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.1F)).mirror(false)
		.texOffs(0, 51).mirror().addBox(-2.05F, -0.14F, -1.99F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.3F)).mirror(false)
		.texOffs(0, 27).addBox(-2.05F, 6.45F, -1.99F, 4.0F, 1.7F, 4.0F, new CubeDeformation(0.3F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition right_front_leg = root.addOrReplaceChild("right_front_leg", CubeListBuilder.create(), PartPose.offset(-2.75F, -11.5F, -5.5F));

		PartDefinition right_front_wool = right_front_leg.addOrReplaceChild("right_front_wool", CubeListBuilder.create().texOffs(16, 51).addBox(-1.95F, -0.14F, -2.02F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.1F))
		.texOffs(0, 51).addBox(-1.95F, -0.14F, -2.02F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.3F))
		.texOffs(0, 27).addBox(-1.95F, 6.45F, -2.02F, 4.0F, 1.7F, 4.0F, new CubeDeformation(0.3F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition left_front_leg = root.addOrReplaceChild("left_front_leg", CubeListBuilder.create(), PartPose.offset(2.75F, -11.5F, -5.5F));

		PartDefinition left_front_wool = left_front_leg.addOrReplaceChild("left_front_wool", CubeListBuilder.create().texOffs(16, 51).mirror().addBox(-2.05F, -0.14F, -2.02F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.1F)).mirror(false)
		.texOffs(0, 51).mirror().addBox(-2.05F, -0.14F, -2.02F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.3F)).mirror(false)
		.texOffs(0, 27).addBox(-2.05F, 6.45F, -2.02F, 4.0F, 1.7F, 4.0F, new CubeDeformation(0.3F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		return LayerDefinition.create(meshdefinition, 86, 64);
	}
	}
