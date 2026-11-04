package com.newera.docs.entity.model.horse;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import com.newera.docs.entity.animation.animal.horse.HorseBabyAnimation;

public class NEBabyDonkeyMuleModel extends NEDonkeyMuleAbstractModel {
    public NEBabyDonkeyMuleModel(ModelPart root) {
        super(root,
            24f,
            HorseBabyAnimation.HORSE_BABY_SIT,
            HorseBabyAnimation.HORSE_BABY_SITTING,
            HorseBabyAnimation.HORSE_BABY_SLEEP,
            HorseBabyAnimation.HORSE_BABY_SLEEPPING,
            HorseBabyAnimation.HORSE_BABY_WAKE_UP,
            HorseBabyAnimation.HORSE_BABY_STAND,
            HorseBabyAnimation.HORSE_BABY_WALK,
            HorseBabyAnimation.HORSE_BABY_GRAZING,
            HorseBabyAnimation.HORSE_BABY_IDLE,
            HorseBabyAnimation.HORSE_BABY_REAR,
            HorseBabyAnimation.HORSE_BABY_ATTACK
        );
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition rootPart = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(),
            PartPose.offsetAndRotation(-1.0f, 24.0f, -3.0f, 0f, 0f, 0f));

        PartDefinition bodyPart = rootPart.addOrReplaceChild("body", CubeListBuilder.create(),
            PartPose.offsetAndRotation(1.0f, -10.0f, 3.0f, 0f, 0f, 0f));

        PartDefinition centroidPart = bodyPart.addOrReplaceChild("centroid", CubeListBuilder.create()
            .texOffs(0, 13).addBox(-4.0f, -4.0f, -7.0f, 8.0f, 6.0f, 14.0f, new CubeDeformation(0.0f)),
            PartPose.offsetAndRotation(0.0f, 0.0f, 0.0f, 0f, 0f, 0f));

        PartDefinition tailPart = centroidPart.addOrReplaceChild("tail", CubeListBuilder.create()
            .texOffs(24, 33).addBox(-1.5f, -2.0f, -0.5f, 3.0f, 3.0f, 8.0f, new CubeDeformation(0.0f)),
            PartPose.offsetAndRotation(0.0f, -1.5f, 6.5f, 0f, 0f, 0f));

        PartDefinition view_bonePart = centroidPart.addOrReplaceChild("view_bone", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0f, -3.0f, -5.0f, 0f, 0f, 0f));
        PartDefinition neckPart = view_bonePart.addOrReplaceChild("neck", CubeListBuilder.create()
            .texOffs(30, 9).addBox(-2.0f, -7.0f, -3.0f, 4.0f, 8.0f, 4.0f, new CubeDeformation(0.0f)),
            PartPose.offsetAndRotation(0f, 0f, 0f, 0f, 0f, 0f));

        PartDefinition headPart = neckPart.addOrReplaceChild("head", CubeListBuilder.create()
            .texOffs(0, 0).addBox(-3.0f, -5.0f, -6.0f, 6.0f, 4.0f, 9.0f, new CubeDeformation(0.0f)),
            PartPose.offsetAndRotation(0.0f, -6.0f, -2.0f, 0f, 0f, 0f));

        PartDefinition left_earPart = headPart.addOrReplaceChild("left_ear", CubeListBuilder.create()
            .texOffs(0, 0).addBox(-1.1f, -6.5f, -0.1f, 2.0f, 7.0f, 1.0f, new CubeDeformation(0.0f)),
            PartPose.offsetAndRotation(2.0f, -3.5f, 2.0f, 0f, 0f, 0f));

        PartDefinition right_earPart = headPart.addOrReplaceChild("right_ear", CubeListBuilder.create()
            .texOffs(22, 0).mirror().addBox(-0.9f, -6.5f, -0.5f, 2.0f, 7.0f, 1.0f, new CubeDeformation(0.0f)).mirror(false),
            PartPose.offsetAndRotation(-2.0f, -3.5f, 2.4f, 0f, 0f, 0f));

        PartDefinition left_hind_legPart = rootPart.addOrReplaceChild("left_hind_leg", CubeListBuilder.create()
            .texOffs(12, 44).addBox(-1.5f, -1.5f, -1.5f, 3.0f, 8.0f, 3.0f, new CubeDeformation(0.0f)),
            PartPose.offsetAndRotation(3.5f, -6.5f, 8.5f, 0f, 0f, 0f));

        PartDefinition right_hind_legPart = rootPart.addOrReplaceChild("right_hind_leg", CubeListBuilder.create()
            .texOffs(0, 44).addBox(-1.5f, -1.5f, -1.5f, 3.0f, 8.0f, 3.0f, new CubeDeformation(0.0f)),
            PartPose.offsetAndRotation(-1.5f, -6.5f, 8.5f, 0f, 0f, 0f));

        PartDefinition left_front_legPart = rootPart.addOrReplaceChild("left_front_leg", CubeListBuilder.create()
            .texOffs(12, 33).addBox(-1.5f, -1.5f, -1.5f, 3.0f, 8.0f, 3.0f, new CubeDeformation(0.0f)),
            PartPose.offsetAndRotation(3.5f, -6.5f, -2.5f, 0f, 0f, 0f));

        PartDefinition right_front_legPart = rootPart.addOrReplaceChild("right_front_leg", CubeListBuilder.create()
            .texOffs(0, 33).addBox(-1.5f, -1.5f, -1.5f, 3.0f, 8.0f, 3.0f, new CubeDeformation(0.0f)),
            PartPose.offsetAndRotation(-1.5f, -6.5f, -2.5f, 0f, 0f, 0f));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }
}
