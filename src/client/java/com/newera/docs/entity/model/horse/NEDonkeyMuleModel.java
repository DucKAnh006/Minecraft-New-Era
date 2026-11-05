package com.newera.docs.entity.model.horse;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import com.newera.docs.entity.animation.animal.horse.HorseAnimation;

public class NEDonkeyMuleModel extends NEDonkeyMuleAbstractModel {
    public NEDonkeyMuleModel(ModelPart root) {
        super(root,
            17f,
            HorseAnimation.HORSE_SIT,
            HorseAnimation.HORSE_SITTING,
            HorseAnimation.HORSE_SLEEP,
            HorseAnimation.HORSE_SLEEPPING,
            HorseAnimation.HORSE_WAKE_UP,
            HorseAnimation.HORSE_STAND,
            HorseAnimation.HORSE_WALK,
            HorseAnimation.HORSE_GRAZING,
            HorseAnimation.HORSE_IDLE,
            HorseAnimation.HORSE_REAR,
            HorseAnimation.HORSE_ATTACK
        );
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition rootPart = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(),
            PartPose.offsetAndRotation(0.0f, 6.0f, 0.0f, 0f, 0f, 0f));

        PartDefinition bodyPart = rootPart.addOrReplaceChild("body", CubeListBuilder.create(),
            PartPose.offsetAndRotation(0.0f, 5.0f, 9.0f, 0f, 0f, 0f));

        PartDefinition centroidPart = bodyPart.addOrReplaceChild("centroid", CubeListBuilder.create()
            .texOffs(0, 32).addBox(-5.0f, -8.0f, -20.0f, 10.0f, 10.0f, 22.0f, new CubeDeformation(0.0f)),
            PartPose.offsetAndRotation(0.0f, 0.0f, 0.0f, 0f, 0f, 0f));

        PartDefinition tailPart = centroidPart.addOrReplaceChild("tail", CubeListBuilder.create()
            .texOffs(42, 36).addBox(-1.5f, 0.0f, -2.0f, 3.0f, 14.0f, 4.0f, new CubeDeformation(0.0f)),
            PartPose.offsetAndRotation(0.0f, -7.0f, 2.0f, 0f, 0f, 0f));

        PartDefinition view_bonePart = centroidPart.addOrReplaceChild("view_bone", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0f, -4.0f, -17.0f, 0f, 0f, 0f));
        PartDefinition neckPart = view_bonePart.addOrReplaceChild("neck", CubeListBuilder.create()
            .texOffs(0, 35).addBox(-2.0f, -11.0f, -3.0f, 4.0f, 12.0f, 7.0f, new CubeDeformation(0.0f)),
            PartPose.offsetAndRotation(0f, 0f, 0f, 0f, 0f, 0f));

        PartDefinition headPart = neckPart.addOrReplaceChild("head", CubeListBuilder.create()
            .texOffs(0, 13).addBox(-3.0f, -5.0f, 0.0f, 6.0f, 5.0f, 7.0f, new CubeDeformation(0.0f)),
            PartPose.offsetAndRotation(0.0f, -11.0f, -3.0f, 0f, 0f, 0f));

        PartDefinition muzzlePart = headPart.addOrReplaceChild("muzzle", CubeListBuilder.create()
            .texOffs(0, 25).addBox(-2.0f, -5.0f, -5.0f, 4.0f, 5.0f, 5.0f, new CubeDeformation(0.0f)),
            PartPose.offsetAndRotation(0.0f, 0.0f, 0.0f, 0f, 0f, 0f));

        PartDefinition left_earPart = headPart.addOrReplaceChild("left_ear", CubeListBuilder.create()
            .texOffs(0, 12).mirror().addBox(-1.0f, -6.0f, -1.01f, 2.0f, 7.0f, 1.0f, new CubeDeformation(0.0f)).mirror(false),
            PartPose.offsetAndRotation(1.9f, -5.0f, 7.0f, 0f, 0f, 0f));

        PartDefinition right_earPart = headPart.addOrReplaceChild("right_ear", CubeListBuilder.create()
            .texOffs(0, 12).addBox(-0.9f, -6.0f, -1.01f, 2.0f, 7.0f, 1.0f, new CubeDeformation(0.0f)),
            PartPose.offsetAndRotation(-2.0f, -5.0f, 7.0f, 0f, 0f, 0f));

        PartDefinition left_reinsPart = headPart.addOrReplaceChild("left_reins", CubeListBuilder.create()
            .texOffs(32, 2).addBox(1.1f, -1.5f, 0.5f, 0.0f, 3.0f, 16.0f, new CubeDeformation(0.0f)),
            PartPose.offsetAndRotation(2.0f, -2.5f, -2.5f, -0.611f, 0.0f, 0.0f));

        PartDefinition right_reinsPart = headPart.addOrReplaceChild("right_reins", CubeListBuilder.create()
            .texOffs(32, 2).addBox(-0.1f, -1.5f, 0.5f, 0.0f, 3.0f, 16.0f, new CubeDeformation(0.0f)),
            PartPose.offsetAndRotation(-3.0f, -2.5f, -2.5f, -0.611f, 0.0f, 0.0f));

        PartDefinition bridlePart = headPart.addOrReplaceChild("bridle", CubeListBuilder.create()
            .texOffs(19, 0).addBox(-2.0f, -16.0f, -5.0f, 4.0f, 5.0f, 2.0f, new CubeDeformation(0.25f))
            .texOffs(0, 0).addBox(-3.0f, -16.0f, -3.0f, 6.0f, 5.0f, 7.0f, new CubeDeformation(0.25f)),
            PartPose.offsetAndRotation(0.0f, 11.0f, 3.0f, 0f, 0f, 0f));

        PartDefinition left_bitPart = bridlePart.addOrReplaceChild("left_bit", CubeListBuilder.create()
            .texOffs(29, 5).addBox(2.0f, -14.0f, -6.0f, 1.0f, 2.0f, 2.0f, new CubeDeformation(0.0f)),
            PartPose.offsetAndRotation(0.0f, 0.0f, 0.0f, 0f, 0f, 0f));

        PartDefinition right_bitPart = bridlePart.addOrReplaceChild("right_bit", CubeListBuilder.create()
            .texOffs(29, 5).addBox(-3.0f, -14.0f, -6.0f, 1.0f, 2.0f, 2.0f, new CubeDeformation(0.0f)),
            PartPose.offsetAndRotation(0.0f, 0.0f, 0.0f, 0f, 0f, 0f));

        PartDefinition manePart = neckPart.addOrReplaceChild("mane", CubeListBuilder.create()
            .texOffs(56, 36).addBox(-1.0f, -16.0f, 4.05f, 2.0f, 16.0f, 2.0f, new CubeDeformation(0.0f)),
            PartPose.offsetAndRotation(0.0f, 0.0f, 0.0f, 0f, 0f, 0f));

        PartDefinition left_bagPart = centroidPart.addOrReplaceChild("left_bag", CubeListBuilder.create()
            .texOffs(26, 21).mirror().addBox(1.0f, 0.0f, 0.0f, 8.0f, 8.0f, 3.0f, new CubeDeformation(0.0f)).mirror(false),
            PartPose.offsetAndRotation(5.0f, -8.0f, 2.0f, 0.0f, 1.571f, 0.0f));

        PartDefinition right_bagPart = centroidPart.addOrReplaceChild("right_bag", CubeListBuilder.create()
            .texOffs(26, 21).addBox(-9.0f, 0.0f, 0.0f, 8.0f, 8.0f, 3.0f, new CubeDeformation(0.0f)),
            PartPose.offsetAndRotation(-5.0f, -8.0f, 2.0f, 0.0f, -1.571f, 0.0f));

        PartDefinition saddlePart = centroidPart.addOrReplaceChild("saddle", CubeListBuilder.create()
            .texOffs(26, 0).addBox(-5.0f, 1.0f, -5.5f, 10.0f, 9.0f, 9.0f, new CubeDeformation(0.5f)),
            PartPose.offsetAndRotation(0.0f, -9.0f, -7.0f, 0f, 0f, 0f));

        PartDefinition left_front_legPart = bodyPart.addOrReplaceChild("left_front_leg", CubeListBuilder.create()
            .texOffs(48, 21).mirror().addBox(-2.1f, 0.0f, -2.0f, 4.0f, 11.0f, 4.0f, new CubeDeformation(0.0f)).mirror(false),
            PartPose.offsetAndRotation(3.0f, 2.0f, -18.0f, 0f, 0f, 0f));

        PartDefinition right_front_legPart = bodyPart.addOrReplaceChild("right_front_leg", CubeListBuilder.create()
            .texOffs(48, 21).addBox(-1.9f, 0.0f, -2.0f, 4.0f, 11.0f, 4.0f, new CubeDeformation(0.0f)),
            PartPose.offsetAndRotation(-3.0f, 2.0f, -18.0f, 0f, 0f, 0f));

        PartDefinition left_hind_legPart = rootPart.addOrReplaceChild("left_hind_leg", CubeListBuilder.create()
            .texOffs(48, 21).mirror().addBox(-2.1f, 0.0f, -2.0f, 4.0f, 11.0f, 4.0f, new CubeDeformation(0.0f)).mirror(false),
            PartPose.offsetAndRotation(3.0f, 7.0f, 9.0f, 0f, 0f, 0f));

        PartDefinition right_hind_legPart = rootPart.addOrReplaceChild("right_hind_leg", CubeListBuilder.create()
            .texOffs(48, 21).addBox(-1.9f, 0.0f, -2.0f, 4.0f, 11.0f, 4.0f, new CubeDeformation(0.0f)),
            PartPose.offsetAndRotation(-3.0f, 7.0f, 9.0f, 0f, 0f, 0f));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }
}

