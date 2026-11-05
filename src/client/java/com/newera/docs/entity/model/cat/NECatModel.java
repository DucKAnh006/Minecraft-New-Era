package com.newera.docs.entity.model.cat;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;
import com.newera.docs.entity.animation.animal.cat.CatAnimation;

public class NECatModel extends NECatAbstractModel {

    public NECatModel(ModelPart root) {
        super(
            root,
            CatAnimation.CAT_SIT,
            CatAnimation.CAT_SITTING,
            CatAnimation.CAT_SLEEP,
            CatAnimation.CAT_SLEEPING,
            CatAnimation.CAT_WAKE_UP,
            CatAnimation.CAT_STAND_UP,
            CatAnimation.CAT_WALK,
            CatAnimation.CAT_IDLE,
            CatAnimation.CAT_STRETCH,
            CatAnimation.CAT_STALKING,
            CatAnimation.CAT_POUNCE
        );
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition rootPart = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(),
            PartPose.offsetAndRotation(0F, 17F, 1F, 0F, 0F, 0F));

        PartDefinition bodyPart = rootPart.addOrReplaceChild("body", CubeListBuilder.create(),
            PartPose.offsetAndRotation(0F, 0F, 0F, 0F, 0F, 0F));

        PartDefinition centroidPart = bodyPart.addOrReplaceChild("centroid", CubeListBuilder.create()
            .texOffs(20, 0).addBox(-2F, -8F, -3F, 4F, 16F, 6F, new CubeDeformation(0F)),
            PartPose.offsetAndRotation(0F, 0F, 0F, 1.5707963267948966F, 0F, 0F));

        PartDefinition headPart = bodyPart.addOrReplaceChild("head", CubeListBuilder.create()
            .texOffs(0, 0).addBox(-2.5F, -2F, -3F, 5F, 4F, 5F, new CubeDeformation(0F))
            .texOffs(0, 24).addBox(-1.5F, -0.015619999999998413F, -4F, 3F, 2F, 2F, new CubeDeformation(0F)),
            PartPose.offsetAndRotation(0F, -2F, -10F, 0F, 0F, 0F));

        PartDefinition left_earPart = headPart.addOrReplaceChild("left_ear", CubeListBuilder.create()
            .texOffs(6, 10).addBox(-0.5F, -1F, -1F, 1F, 1F, 2F, new CubeDeformation(0F)),
            PartPose.offsetAndRotation(1.5F, -2F, 1F, 0F, 0F, 0F));

        PartDefinition right_earPart = headPart.addOrReplaceChild("right_ear", CubeListBuilder.create()
            .texOffs(0, 10).addBox(-0.5F, -1F, -1F, 1F, 1F, 2F, new CubeDeformation(0F)),
            PartPose.offsetAndRotation(-1.5F, -2F, 1F, 0F, 0F, 0F));

        PartDefinition tail1Part = bodyPart.addOrReplaceChild("tail1", CubeListBuilder.create()
            .texOffs(0, 15).addBox(-0.5F, 0F, 0F, 1F, 8F, 1F, new CubeDeformation(0F)),
            PartPose.offsetAndRotation(0F, -2F, 7F, 0F, 0F, 0F));

        PartDefinition tail2Part = tail1Part.addOrReplaceChild("tail2", CubeListBuilder.create()
            .texOffs(4, 15).addBox(-0.5F, 0F, 0F, 1F, 8F, 1F, new CubeDeformation(0F)),
            PartPose.offsetAndRotation(0F, 8F, 0F, 0F, 0F, 0F));

        PartDefinition left_hind_legPart = rootPart.addOrReplaceChild("left_hind_leg", CubeListBuilder.create()
            .texOffs(8, 13).addBox(-1F, 0F, -1F, 2F, 6F, 2F, new CubeDeformation(0F)),
            PartPose.offsetAndRotation(1.1F, 1F, 6F, 0F, 0F, 0F));

        PartDefinition right_hind_legPart = rootPart.addOrReplaceChild("right_hind_leg", CubeListBuilder.create()
            .texOffs(8, 13).addBox(-1F, 0F, -1F, 2F, 6F, 2F, new CubeDeformation(0F)),
            PartPose.offsetAndRotation(-1.1F, 1F, 6F, 0F, 0F, 0F));

        PartDefinition left_front_legPart = rootPart.addOrReplaceChild("left_front_leg", CubeListBuilder.create()
            .texOffs(40, 0).addBox(-1F, -3.1999999999999993F, -1F, 2F, 10F, 2F, new CubeDeformation(0F)),
            PartPose.offsetAndRotation(1.2F, 0F, -5F, 0F, 0F, 0F));

        PartDefinition right_front_legPart = rootPart.addOrReplaceChild("right_front_leg", CubeListBuilder.create()
            .texOffs(40, 0).addBox(-1.0000000000000002F, -3.1999999999999993F, -1F, 2F, 10F, 2F, new CubeDeformation(0F)),
            PartPose.offsetAndRotation(-1.2F, 0F, -5F, 0F, 0F, 0F));

        return LayerDefinition.create(meshdefinition, 64, 32);
    }
}

