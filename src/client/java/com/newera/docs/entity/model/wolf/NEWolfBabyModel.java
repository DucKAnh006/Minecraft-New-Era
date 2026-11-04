package com.newera.docs.entity.model.wolf;

import com.newera.docs.entity.animation.animal.wolf.WolfBabyAnimation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class NEWolfBabyModel extends NEWolfAbstractModel {

    public NEWolfBabyModel(ModelPart root) {
        super(root, 
            WolfBabyAnimation.WOLF_BABY_IDLE,
            WolfBabyAnimation.WOLF_BABY_SIT, 
            WolfBabyAnimation.WOLF_BABY_SITTING, 
            WolfBabyAnimation.WOLF_BABY_SLEEP, 
            WolfBabyAnimation.WOLF_BABY_SLEEPING, 
            WolfBabyAnimation.WOLF_BABY_WAKE_UP, 
            WolfBabyAnimation.WOLF_BABY_STAND_UP, 
            WolfBabyAnimation.WOLF_BABY_WALK, 
            WolfBabyAnimation.WOLF_BABY_HOWLING, 
            WolfBabyAnimation.WOLF_BABY_SIT_HOWLING
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
            PartPose.offset(0F, 18.25F, -4F));

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create(), 
            PartPose.offset(0F, 0.75F, 4F));

        PartDefinition centroid = body.addOrReplaceChild("centroid", CubeListBuilder.create()
            .texOffs(0, 0).addBox(-3F, -1.25F, 0F, 6.0F, 4.0F, 8.0F, deformation), 
            PartPose.offset(0F, -0.75F, -4F));

        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create()
            .texOffs(0, 12).addBox(-2.99F, -3.25F, -3F, 6.0F, 5.0F, 5.0F, deformation)
            .texOffs(17, 12).addBox(-1.5F, -0.24F, -5F, 3.0F, 2.0F, 2.0F, deformation), 
            PartPose.offset(0F, -0.75F, -4F));

        PartDefinition right_ear = head.addOrReplaceChild("right_ear", CubeListBuilder.create()
            .texOffs(0, 5).addBox(-1F, -2F, -0.5F, 2.0F, 2.0F, 1.0F, deformation), 
            PartPose.offset(-2F, -3.25F, -0.5F));

        PartDefinition left_ear = head.addOrReplaceChild("left_ear", CubeListBuilder.create()
            .texOffs(20, 5).addBox(-1F, -2F, -0.5F, 2.0F, 2.0F, 1.0F, deformation), 
            PartPose.offset(2F, -3.25F, -0.5F));

        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create()
            .texOffs(22, 16).addBox(-1F, -5.7F, -1.0F, 2.0F, 6.0F, 2.0F, deformation), 
            PartPose.offsetAndRotation(0F, -0.6F, 3.2F, (float)Math.PI, 0F, 0F));

        PartDefinition right_hind_leg = root.addOrReplaceChild("right_hind_leg", CubeListBuilder.create()
            .texOffs(0, 22).addBox(-1F, 0F, -1F, 2.0F, 3.0F, 2.0F, deformation), 
            PartPose.offset(-1.5F, 2.75F, 7F));

        PartDefinition left_hind_leg = root.addOrReplaceChild("left_hind_leg", CubeListBuilder.create()
            .texOffs(8, 22).addBox(-1F, 0F, -1F, 2.0F, 3.0F, 2.0F, deformation), 
            PartPose.offset(1.5F, 2.75F, 7F));

        PartDefinition right_front_leg = root.addOrReplaceChild("right_front_leg", CubeListBuilder.create()
            .texOffs(0, 0).addBox(-1F, 0F, -1F, 2.0F, 3.0F, 2.0F, deformation), 
            PartPose.offset(-1.5F, 2.75F, 1F));

        PartDefinition left_front_leg = root.addOrReplaceChild("left_front_leg", CubeListBuilder.create()
            .texOffs(20, 0).addBox(-1F, 0F, -1F, 2.0F, 3.0F, 2.0F, deformation), 
            PartPose.offset(1.5F, 2.75F, 1F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }
}