package com.newera.docs.entity.model.cat;

import com.newera.docs.entity.state.NECatRenderState;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;

public abstract class NECatAbstractModel extends EntityModel<NECatRenderState> {
    private static final float MAX_HEAD_X_ROT = 40.0F;
    private static final float MAX_HEAD_Y_ROT = 80.0F;
    private static final float WALK_ANIMATION_SPEED = 2.5F;
    private static final float WALK_ANIMATION_SCALE_FACTOR = 2.5F;

    private final ModelPart rootPart;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart left_front_leg;
    private final ModelPart right_front_leg;
    private final ModelPart left_hind_leg;
    private final ModelPart right_hind_leg;
    private final ModelPart left_ear;
    private final ModelPart right_ear;

    private final KeyframeAnimation sitAnimation;
    private final KeyframeAnimation sittingHoldAnimation;
    private final KeyframeAnimation sleepAnimation;
    private final KeyframeAnimation sleepingHoldAnimation;
    private final KeyframeAnimation wakeUpAnimation;
    private final KeyframeAnimation standAnimation;
    private final KeyframeAnimation walkAnimation;
    private final KeyframeAnimation idleAnimation;

    private final KeyframeAnimation stretchAnimation;
    private final KeyframeAnimation stalkingAnimation;
    private final KeyframeAnimation pounceAnimation;

    public NECatAbstractModel(
            final ModelPart root,
            final AnimationDefinition sitAnim,
            final AnimationDefinition sittingHoldAnim,
            final AnimationDefinition sleepAnim,
            final AnimationDefinition sleepingHoldAnim,
            final AnimationDefinition wakeUpAnim,
            final AnimationDefinition standAnim,
            final AnimationDefinition walkAnim,
            final AnimationDefinition idleAnim,
            final AnimationDefinition stretchAnim,
            final AnimationDefinition stalkingAnim,
            final AnimationDefinition pounceAnim
    ) {
        super(root);
        this.rootPart = findPart(root, "root");
        this.body = findPart(this.rootPart, "body");
        this.head = findPart(this.body, "head");
        this.left_front_leg = findPart(this.rootPart, "left_front_leg");
        this.right_front_leg = findPart(this.rootPart, "right_front_leg");
        this.left_hind_leg = findPart(this.rootPart, "left_hind_leg");
        this.right_hind_leg = findPart(this.rootPart, "right_hind_leg");
        this.left_ear = findPart(this.head, "left_ear");
        this.right_ear = findPart(this.head, "right_ear");

        this.sitAnimation = sitAnim.bake(root);
        this.sittingHoldAnimation = sittingHoldAnim.bake(root);
        this.sleepAnimation = sleepAnim.bake(root);
        this.sleepingHoldAnimation = sleepingHoldAnim.bake(root);
        this.wakeUpAnimation = wakeUpAnim.bake(root);
        this.standAnimation = standAnim.bake(root);
        this.walkAnimation = walkAnim.bake(root);
        this.idleAnimation = idleAnim.bake(root);

        this.stretchAnimation = stretchAnim.bake(root);
        this.stalkingAnimation = stalkingAnim.bake(root);
        this.pounceAnimation = pounceAnim.bake(root);
    }

    private static ModelPart findPart(ModelPart parent, String name) {
        if (parent == null) return null;
        if (parent.hasChild(name)) return parent.getChild(name);
        if (parent.hasChild("centroid") && parent.getChild("centroid").hasChild(name)) return parent.getChild("centroid").getChild(name);
        if (parent.hasChild("body") && parent.getChild("body").hasChild(name)) return parent.getChild("body").getChild(name);
        return parent.getChild(name);
    }

    @Override
    public void setupAnim(final NECatRenderState state) {
        super.setupAnim(state);
        this.rootPart.resetPose();

        boolean isMovingAllowed = !state.sleepAnimationState.isStarted()
                && !state.sleepingHoldAnimationState.isStarted()
                && !state.wakeUpAnimationState.isStarted()
                && !state.standAnimationState.isStarted()
                && !state.stretchAnimationState.isStarted()
                && !state.sitAnimationState.isStarted()
                && !state.sittingHoldAnimationState.isStarted()
                && !state.standAnimationState.isStarted();

        boolean isLookingAllowed = !state.sleepAnimationState.isStarted()
                && !state.sleepingHoldAnimationState.isStarted()
                && !state.wakeUpAnimationState.isStarted();

        if (isLookingAllowed) {
            this.head.xRot = Mth.clamp(state.xRot, -MAX_HEAD_X_ROT, MAX_HEAD_X_ROT) * (float) (Math.PI / 180.0);
            this.head.yRot = Mth.clamp(state.yRot, -MAX_HEAD_Y_ROT, MAX_HEAD_Y_ROT) * (float) (Math.PI / 180.0);
        } else {
            this.head.xRot = 0;
            this.head.yRot = 0;
        }

        if (isMovingAllowed) {
            this.walkAnimation.applyWalk(state.walkAnimationPos, state.walkAnimationSpeed, WALK_ANIMATION_SPEED, WALK_ANIMATION_SCALE_FACTOR);
        }

        this.sitAnimation.apply(state.sitAnimationState, state.ageInTicks);
        this.sittingHoldAnimation.apply(state.sittingHoldAnimationState, state.ageInTicks);
        this.sleepAnimation.apply(state.sleepAnimationState, state.ageInTicks);
        this.sleepingHoldAnimation.apply(state.sleepingHoldAnimationState, state.ageInTicks);
        this.wakeUpAnimation.apply(state.wakeUpAnimationState, state.ageInTicks);
        this.standAnimation.apply(state.standAnimationState, state.ageInTicks);
        this.idleAnimation.apply(state.idleAnimationState, state.ageInTicks);

        this.stretchAnimation.apply(state.stretchAnimationState, state.ageInTicks);
        this.stalkingAnimation.apply(state.stalkingAnimationState, state.ageInTicks);
        this.pounceAnimation.apply(state.pounceAnimationState, state.ageInTicks);
    }
}

