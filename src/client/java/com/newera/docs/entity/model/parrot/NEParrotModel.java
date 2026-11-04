package com.newera.docs.entity.model.parrot;

import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.model.geom.ModelPart;
import com.newera.docs.entity.animation.animal.parrot.ParrotAnimation;

public class NEParrotModel extends NEParrotAbstractModel {
    public NEParrotModel(ModelPart root) {
        super(
            root,
            ParrotAnimation.PARROT_IDLE_GROUND,
            ParrotAnimation.PARROT_GROUND_TO_FLY,
            ParrotAnimation.PARROT_FLY_TO_GROUND,
            ParrotAnimation.PARROT_IDLE_FLY,
            ParrotAnimation.PARROT_FLY_FLAP,
            ParrotAnimation.PARROT_GLIDE_FLAP,
            ParrotAnimation.PARROT_WALK,
            ParrotAnimation.PARROT_SIT,
            ParrotAnimation.PARROT_SITTING,
            ParrotAnimation.PARROT_STAND,
            ParrotAnimation.PARROT_SLEEP,
            ParrotAnimation.PARROT_SLEEPING,
            ParrotAnimation.PARROT_WAKE_UP,
            ParrotAnimation.PARROT_DANCE
        );
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0F, 24F, 0F));
        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(2, 8).addBox(-1.5F, -5.5F, -1.5F, 3F, 6F, 3F, new CubeDeformation(0.0F)), PartPose.offset(0F, -2.5F, -1F));
        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(2, 2).addBox(-1F, -2.0F, -1.0F, 2F, 3F, 2F, new CubeDeformation(0.0F)), PartPose.offset(0F, -5.8F, 0.2F));
        PartDefinition head2 = head.addOrReplaceChild("head2", CubeListBuilder.create().texOffs(10, 0).addBox(-1F, 14.7F, -4.8F, 2F, 1F, 4F, new CubeDeformation(0.0F)), PartPose.offset(0F, -17.7F, 1.8F));
        PartDefinition beak1 = head.addOrReplaceChild("beak1", CubeListBuilder.create().texOffs(11, 7).addBox(-0.5F, 14.2F, -3.2F, 1F, 2F, 1F, new CubeDeformation(0.0F)), PartPose.offset(0F, -16.2F, 1.3F));
        PartDefinition beak2 = head.addOrReplaceChild("beak2", CubeListBuilder.create().texOffs(16, 7).addBox(-0.5F, 15.5F, -3.2F, 1F, 1.7F, 1F, new CubeDeformation(0.0F)), PartPose.offset(0F, -17.5F, 0.3F));
        PartDefinition feather = head.addOrReplaceChild("feather", CubeListBuilder.create().texOffs(2, 14).addBox(0F, -4.5F, -5.1F, 0F, 5F, 4F, new CubeDeformation(0.0F)), PartPose.offset(0F, -1.8F, 3.0F));
        PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(22, 1).addBox(-1.5F, -0.5F, -1.2F, 3F, 4F, 1F, new CubeDeformation(0.0F)).texOffs(2, 24).addBox(-1.5F, 3.5F, -0.2F, 3F, 4F, 0F, new CubeDeformation(0.0F)), PartPose.offset(0F, -0.4F, 2.2F));
        PartDefinition left_tail_feather = tail.addOrReplaceChild("left_tail_feather", CubeListBuilder.create().texOffs(9, 24).addBox(-1.0F, 0.0F, 0.0F, 2F, 6F, 0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 0.5F, -0.7F));
        PartDefinition right_tail_feather = tail.addOrReplaceChild("right_tail_feather", CubeListBuilder.create().mirror().texOffs(9, 24).addBox(-1.0F, 0.0F, 0.0F, 2F, 6F, 0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, 0.5F, -0.7F));
        PartDefinition left_wing = body.addOrReplaceChild("left_wing", CubeListBuilder.create(), PartPose.offset(1.5F, -5.1F, 0.2F));
        PartDefinition left_wing_folded = left_wing.addOrReplaceChild("left_wing_folded", CubeListBuilder.create().texOffs(19, 8).addBox(-0.5F, -0.5F, -1.5F, 1F, 5F, 3F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, 0.0F));
        PartDefinition left_wing_feather = left_wing.addOrReplaceChild("left_wing_feather", CubeListBuilder.create().texOffs(15, 10).addBox(0.0F, -0.5F, -3.5F, 0F, 12F, 8F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, 0.0F));
        PartDefinition right_wing = body.addOrReplaceChild("right_wing", CubeListBuilder.create(), PartPose.offset(-1.5F, -5.1F, 0.2F));
        PartDefinition right_wing_folded = right_wing.addOrReplaceChild("right_wing_folded", CubeListBuilder.create().texOffs(19, 8).addBox(-0.5F, -0.5F, -1.5F, 1F, 5F, 3F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, 0.0F));
        PartDefinition right_wing_feather = right_wing.addOrReplaceChild("right_wing_feather", CubeListBuilder.create().mirror().texOffs(15, 10).addBox(0.0F, -0.5F, -3.5F, 0F, 12F, 8F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, 0.0F));
        PartDefinition left_leg = root.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(26, 7).addBox(-1.0F, 0F, -1.0F, 1F, 2F, 1F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, -2F, -0.5F));
        PartDefinition right_leg = root.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(26, 7).addBox(-1.0F, 0F, -1.0F, 1F, 2F, 1F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, -2F, -0.5F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }
}

