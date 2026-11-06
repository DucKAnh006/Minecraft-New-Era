package com.newera.docs.entity.model.wolf;

import com.newera.docs.entity.animation.animal.wolf.WolfAnimation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class NEWolfModel extends NEWolfAbstractModel {

    public NEWolfModel(ModelPart root) {
        super(root,
            WolfAnimation.WOLF_IDLE,
            WolfAnimation.WOLF_SIT,
            WolfAnimation.WOLF_SITTING,
            WolfAnimation.WOLF_SLEEP,
            WolfAnimation.WOLF_SLEEPING,
            WolfAnimation.WOLF_WAKE_UP,
            WolfAnimation.WOLF_STAND_UP,
            WolfAnimation.WOLF_WALK,
            WolfAnimation.WOLF_HOWLING,
            WolfAnimation.WOLF_SIT_HOWLING
        );
    }

    public static LayerDefinition createBodyLayer() {
        return createBodyLayer(CubeDeformation.NONE);
    }
    public static LayerDefinition createArmorLayer() {
        return createBodyLayer(new CubeDeformation(0.1F));
    }
    public static LayerDefinition createBodyLayer(CubeDeformation deformation) {
        MeshDefinition meshdefinition = new MeshDefinition();

        PartDefinition root = meshdefinition.getRoot().addOrReplaceChild("root", CubeListBuilder.create(),
            PartPose.offset(0F, 24F, 0F));

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create(),
            PartPose.offset(0F, -7F, 2F));

        PartDefinition centroid = body.addOrReplaceChild("centroid", CubeListBuilder.create(),
            PartPose.offsetAndRotation(0F, -3F, 0F, 1.5708F, 0.0000F, 0.0000F));

        PartDefinition upper_centroid = centroid.addOrReplaceChild("upper_centroid", CubeListBuilder.create()
            .texOffs(21, 0).addBox(-4F, -7F, -3.1F, 8.0F, 6.0F, 7.0F, deformation),
            PartPose.ZERO);

        PartDefinition lower_centroid = centroid.addOrReplaceChild("lower_centroid", CubeListBuilder.create()
            .texOffs(18, 14).addBox(-3F, -2F, -3F, 6.0F, 9.0F, 6.0F, deformation),
            PartPose.ZERO);

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create()
            .texOffs(0, 0).addBox(-3F, -3F, -2F, 6.0F, 6.0F, 4.0F, deformation)
            .texOffs(0, 10).addBox(-1.5F, -0.01562999999999981F, -5F, 3.0F, 3.0F, 4.0F, deformation),
            PartPose.offset(0F, -3.5F, -9F));

        PartDefinition left_ear = head.addOrReplaceChild("left_ear", CubeListBuilder.create()
            .texOffs(16, 14).addBox(-1F, -2F, -0.5F, 2.0F, 2.0F, 1.0F, deformation),
            PartPose.offset(2F, -3F, 0.5F));

        PartDefinition right_ear = head.addOrReplaceChild("right_ear", CubeListBuilder.create()
            .texOffs(16, 14).addBox(-1F, -2F, -0.5F, 2.0F, 2.0F, 1.0F, deformation),
            PartPose.offset(-2F, -3F, 0.5F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create()
            .texOffs(9, 18).addBox(-1F, 0F, -1F, 2.0F, 8.0F, 2.0F, deformation),
            PartPose.offset(0F, -5F, 6F));

        PartDefinition right_hind_leg = root.addOrReplaceChild("right_hind_leg", CubeListBuilder.create()
            .texOffs(0, 18).addBox(-1F, 0F, -1F, 2.0F, 8.0F, 2.0F, deformation),
            PartPose.offset(-1.5F, -8F, 7F));

        PartDefinition left_hind_leg = root.addOrReplaceChild("left_hind_leg", CubeListBuilder.create()
            .texOffs(0, 18).addBox(-1F, 0F, -1F, 2.0F, 8.0F, 2.0F, deformation),
            PartPose.offset(1.5F, -8F, 7F));

        PartDefinition right_front_leg = root.addOrReplaceChild("right_front_leg", CubeListBuilder.create()
            .texOffs(0, 18).addBox(-1F, 0F, -1F, 2.0F, 8.0F, 2.0F, deformation),
            PartPose.offset(-1.5F, -8F, -3F));

        PartDefinition left_front_leg = root.addOrReplaceChild("left_front_leg", CubeListBuilder.create()
            .texOffs(0, 18).addBox(-1F, 0F, -1F, 2.0F, 8.0F, 2.0F, deformation),
            PartPose.offset(1.5F, -8F, -3F));

        return LayerDefinition.create(meshdefinition, 64, 32);
    }
}
