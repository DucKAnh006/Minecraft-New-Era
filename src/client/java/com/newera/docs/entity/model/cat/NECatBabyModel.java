package com.newera.docs.entity.model.cat;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.Entity;
import com.newera.docs.entity.animation.animal.cat.CatBabyAnimation;

public class NECatBabyModel extends NECatAbstractModel {

    public NECatBabyModel(ModelPart root) {
        super(
            root,
            CatBabyAnimation.CAT_BABY_SIT,
            CatBabyAnimation.CAT_BABY_SITTING,
            CatBabyAnimation.CAT_BABY_SLEEP,
            CatBabyAnimation.CAT_BABY_SLEEPING,
            CatBabyAnimation.CAT_BABY_WAKE_UP,
            CatBabyAnimation.CAT_BABY_STAND_UP,
            CatBabyAnimation.CAT_BABY_WALK,
            CatBabyAnimation.CAT_BABY_IDLE,
            CatBabyAnimation.CAT_BABY_STRETCH,
            CatBabyAnimation.CAT_BABY_STALKING,
            CatBabyAnimation.CAT_BABY_POUNCE
        );
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition rootPart = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(),
            PartPose.offsetAndRotation(0F, 24F, 0F, 0F, 0F, 0F));

        PartDefinition bodyPart = rootPart.addOrReplaceChild("body", CubeListBuilder.create(),
            PartPose.offsetAndRotation(0F, -3.5F, 0.5F, 0F, 0F, 0F));

        PartDefinition centroidPart = bodyPart.addOrReplaceChild("centroid", CubeListBuilder.create()
            .texOffs(0, 8).addBox(-2F, -1.5F, -3.5F, 4F, 3F, 7F, new CubeDeformation(0F)),
            PartPose.offsetAndRotation(0F, 0F, 0F, 0F, 0F, 0F));

        PartDefinition headPart = bodyPart.addOrReplaceChild("head", CubeListBuilder.create()
            .texOffs(0, 0).addBox(-2.5F, -3F, -2.875F, 5F, 4F, 4F, new CubeDeformation(0F))
            .texOffs(18, 3).addBox(-1.5F, -1F, -3.875F, 3F, 2F, 1F, new CubeDeformation(0F)),
            PartPose.offsetAndRotation(0F, -0.5F, -3.625F, 0F, 0F, 0F));

        PartDefinition left_earPart = headPart.addOrReplaceChild("left_ear", CubeListBuilder.create()
            .texOffs(24, 0).addBox(-0.5F, -1F, -1F, 1F, 1F, 2F, new CubeDeformation(0F)),
            PartPose.offsetAndRotation(1.5F, -3F, 0.125F, 0F, 0F, 0F));

        PartDefinition right_earPart = headPart.addOrReplaceChild("right_ear", CubeListBuilder.create()
            .texOffs(18, 0).addBox(-0.5F, -1F, -1F, 1F, 1F, 2F, new CubeDeformation(0F)),
            PartPose.offsetAndRotation(-1.5F, -3F, 0.125F, 0F, 0F, 0F));

        PartDefinition tail1Part = bodyPart.addOrReplaceChild("tail1", CubeListBuilder.create()
            .texOffs(0, 18).addBox(-0.5F, -0.10697000000000045F, 0.08489000000000013F, 1F, 1F, 5F, new CubeDeformation(0F)),
            PartPose.offsetAndRotation(0F, -1.3930299999999995F, 3.41511F, -0.5672320068981571F, 0F, 0F));

        PartDefinition tail2Part = tail1Part.addOrReplaceChild("tail2", CubeListBuilder.create(),
            PartPose.offsetAndRotation(0F, 0F, 5F, 0F, 0F, 0F));

        PartDefinition left_hind_legPart = rootPart.addOrReplaceChild("left_hind_leg", CubeListBuilder.create()
            .texOffs(18, 22).addBox(-0.5F, 0F, -1F, 1F, 2F, 2F, new CubeDeformation(0F)),
            PartPose.offsetAndRotation(1F, -2F, 2.5F, 0F, 0F, 0F));

        PartDefinition right_hind_legPart = rootPart.addOrReplaceChild("right_hind_leg", CubeListBuilder.create()
            .texOffs(12, 22).addBox(-0.5F, 0F, -1F, 1F, 2F, 2F, new CubeDeformation(0F)),
            PartPose.offsetAndRotation(-1F, -2F, 2.5F, 0F, 0F, 0F));

        PartDefinition left_front_legPart = rootPart.addOrReplaceChild("left_front_leg", CubeListBuilder.create()
            .texOffs(18, 18).addBox(-0.5F, 0F, -1F, 1F, 2F, 2F, new CubeDeformation(0F)),
            PartPose.offsetAndRotation(1F, -2F, -1.5F, 0F, 0F, 0F));

        PartDefinition right_front_legPart = rootPart.addOrReplaceChild("right_front_leg", CubeListBuilder.create()
            .texOffs(12, 18).addBox(-0.5F, 0F, -1F, 1F, 2F, 2F, new CubeDeformation(0F)),
            PartPose.offsetAndRotation(-1F, -2F, -1.5F, 0F, 0F, 0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }
}
