package com.newera.docs.entity.model.horse;

import com.newera.docs.entity.state.NEHorseRenderState;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.model.geom.ModelPart;

public abstract class NEDonkeyMuleAbstractModel extends NEHorseAbstractModel {
    private final ModelPart left_bag;
    private final ModelPart right_bag;

    public NEDonkeyMuleAbstractModel(
            final ModelPart root,
            final float rootYOffset,
            final AnimationDefinition sitAnim,
            final AnimationDefinition sittingHoldAnim,
            final AnimationDefinition sleepAnim,
            final AnimationDefinition sleepingHoldAnim,
            final AnimationDefinition wakeUpAnim,
            final AnimationDefinition standAnim,
            final AnimationDefinition walkAnim,
            final AnimationDefinition grazingAnim,
            final AnimationDefinition idleAnim,
            final AnimationDefinition rearAnim,
            final AnimationDefinition attackAnim
    ) {
        super(root, rootYOffset, sitAnim, sittingHoldAnim, sleepAnim, sleepingHoldAnim, wakeUpAnim, standAnim, walkAnim, grazingAnim, idleAnim, rearAnim, attackAnim);

        ModelPart body = root.getChild("root").getChild("body");
        ModelPart centroid = body.hasChild("centroid") ? body.getChild("centroid") : null;

        this.left_bag = body.hasChild("left_bag") ? body.getChild("left_bag") : (centroid != null && centroid.hasChild("left_bag") ? centroid.getChild("left_bag") : null);
        this.right_bag = body.hasChild("right_bag") ? body.getChild("right_bag") : (centroid != null && centroid.hasChild("right_bag") ? centroid.getChild("right_bag") : null);
    }

    @Override
    public void setupAnim(final NEHorseRenderState state) {
        super.setupAnim(state);

        if (this.left_bag != null) {
            this.left_bag.visible = state.hasChest;
        }
        if (this.right_bag != null) {
            this.right_bag.visible = state.hasChest;
        }
    }
}

