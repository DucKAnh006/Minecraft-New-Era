package com.newera.docs.entity.model.chicken;

import com.newera.docs.entity.state.NEChickenRenderState;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;

public abstract class NEChickenAbstractModel extends EntityModel<NEChickenRenderState> {
    private static final float MAX_HEAD_X_ROT = 25.0F;
    private static final float MAX_HEAD_Y_ROT = 32.5F;
    private static final float WALK_ANIMATION_SPEED = 2.5F;
    private static final float WALK_ANIMATION_SCALE_FACTOR = 2.5F;

    private final ModelPart rootPart;
    private final ModelPart centroid;
    private final ModelPart head;
    private final ModelPart right_leg;
    private final ModelPart left_leg;
    private final ModelPart right_wing;
    private final ModelPart left_wing;

    private final KeyframeAnimation sitAnimation;
    private final KeyframeAnimation sittingHoldAnimation;
    private final KeyframeAnimation sleepAnimation;
    private final KeyframeAnimation sleepingHoldAnimation;
    private final KeyframeAnimation wakeUpAnimation;
    private final KeyframeAnimation standAnimation;
    private final KeyframeAnimation walkAnimation;
    private final KeyframeAnimation grazingAnimation;
    private final KeyframeAnimation idleAnimation;
    private final KeyframeAnimation flyingAnimation;
    private final KeyframeAnimation takeOffAnimation;
    private final KeyframeAnimation onAirAnimation;
    private final KeyframeAnimation landingAnimation;

    public NEChickenAbstractModel(
            final ModelPart root,
            final AnimationDefinition sitAnim,
            final AnimationDefinition sittingHoldAnim,
            final AnimationDefinition sleepAnim,
            final AnimationDefinition sleepingHoldAnim,
            final AnimationDefinition wakeUpAnim,
            final AnimationDefinition standAnim,
            final AnimationDefinition walkAnim,
            final AnimationDefinition grazingAnim,
            final AnimationDefinition idleAnim,
            final AnimationDefinition flyingAnim,
            final AnimationDefinition takeOffAnim,
            final AnimationDefinition onAirAnim,
            final AnimationDefinition landingAnim
    ) {
        super(root);
        this.rootPart = root.getChild("root");
        this.centroid = this.rootPart.getChild("centroid");
        this.head = this.centroid.hasChild("head") ? this.centroid.getChild("head") : this.centroid.getChild("body");
        this.right_leg = this.rootPart.getChild("right_leg");
        this.left_leg = this.rootPart.getChild("left_leg");
        this.right_wing = this.centroid.getChild("right_wing");
        this.left_wing = this.centroid.getChild("left_wing");

        this.sitAnimation = sitAnim.bake(root);
        this.sittingHoldAnimation = sittingHoldAnim.bake(root);
        this.sleepAnimation = sleepAnim.bake(root);
        this.sleepingHoldAnimation = sleepingHoldAnim.bake(root);
        this.wakeUpAnimation = wakeUpAnim.bake(root);
        this.standAnimation = standAnim.bake(root);
        this.walkAnimation = walkAnim.bake(root);
        this.grazingAnimation = grazingAnim.bake(root);
        this.idleAnimation = idleAnim.bake(root);
        this.flyingAnimation = flyingAnim.bake(root);
        this.takeOffAnimation = takeOffAnim.bake(root);
        this.onAirAnimation = onAirAnim.bake(root);
        this.landingAnimation = landingAnim.bake(root);
    }

    @Override
    public void setupAnim(final NEChickenRenderState state) {
        super.setupAnim(state);
        this.rootPart.resetPose();

        boolean isMovingAllowed = !state.sitAnimationState.isStarted()
                && !state.sittingHoldAnimationState.isStarted()
                && !state.sleepAnimationState.isStarted()
                && !state.sleepingHoldAnimationState.isStarted()
                && !state.wakeUpAnimationState.isStarted()
                && !state.standAnimationState.isStarted()
                && !state.takeOffAnimationState.isStarted()
                && !state.onAirAnimationState.isStarted()
                && !state.landingAnimationState.isStarted();

        boolean isLookingAllowed = !state.sleepAnimationState.isStarted()
                && !state.sleepingHoldAnimationState.isStarted()
                && !state.wakeUpAnimationState.isStarted();

        if (isLookingAllowed && !state.isBaby) {
            this.head.xRot = Mth.clamp(state.xRot, -MAX_HEAD_X_ROT, MAX_HEAD_X_ROT) * (float) (Math.PI / 90.0);
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
        this.grazingAnimation.apply(state.grazingAnimationState, state.ageInTicks);
        this.idleAnimation.apply(state.idleAnimationState, state.ageInTicks);

        this.flyingAnimation.apply(state.flyingAnimationState, state.ageInTicks);
        this.takeOffAnimation.apply(state.takeOffAnimationState, state.ageInTicks);
        this.onAirAnimation.apply(state.onAirAnimationState, state.ageInTicks);
        this.landingAnimation.apply(state.landingAnimationState, state.ageInTicks);
    }
}
