package com.newera.docs.entity.model.camel;

import com.newera.docs.entity.state.NECamelRenderState;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;

public abstract class NECamelAbstractModel extends EntityModel<NECamelRenderState> {
    private static final float MAX_HEAD_X_ROT = 25.0F;
    private static final float MAX_HEAD_Y_ROT = 32.5F;
    private static final float WALK_ANIMATION_SPEED = 2.5F;
    private static final float WALK_ANIMATION_SCALE_FACTOR = 2.5F;

    private final ModelPart rootPart;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart left_front_leg;
    private final ModelPart right_front_leg;
    private final ModelPart left_hind_leg;
    private final ModelPart right_hind_leg;

    private final KeyframeAnimation sitAnimation;
    private final KeyframeAnimation sittingHoldAnimation;
    private final KeyframeAnimation sleepAnimation;
    private final KeyframeAnimation sleepingHoldAnimation;
    private final KeyframeAnimation wakeUpAnimation;
    private final KeyframeAnimation standAnimation;
    private final KeyframeAnimation walkAnimation;
    private final KeyframeAnimation eatingAnimation;
    private final KeyframeAnimation idleAnimation;
    private final KeyframeAnimation dashAnimation;

    public NECamelAbstractModel(
            final ModelPart root,
            final AnimationDefinition sitAnim,
            final AnimationDefinition sittingHoldAnim,
            final AnimationDefinition sleepAnim,
            final AnimationDefinition sleepingHoldAnim,
            final AnimationDefinition wakeUpAnim,
            final AnimationDefinition standAnim,
            final AnimationDefinition walkAnim,
            final AnimationDefinition eatingAnim,
            final AnimationDefinition idleAnim,
            final AnimationDefinition dashAnim
    ) {
        super(root);
        this.rootPart = root.getChild("root");
        this.body = this.rootPart.getChild("body");
        this.head = this.body.getChild("head");
        this.left_front_leg = this.rootPart.getChild("left_front_leg");
        this.right_front_leg = this.rootPart.getChild("right_front_leg");
        this.left_hind_leg = this.rootPart.getChild("left_hind_leg");
        this.right_hind_leg = this.rootPart.getChild("right_hind_leg");

        this.sitAnimation = sitAnim.bake(root);
        this.sittingHoldAnimation = sittingHoldAnim.bake(root);
        this.sleepAnimation = sleepAnim.bake(root);
        this.sleepingHoldAnimation = sleepingHoldAnim.bake(root);
        this.wakeUpAnimation = wakeUpAnim.bake(root);
        this.standAnimation = standAnim.bake(root);
        this.walkAnimation = walkAnim.bake(root);
        this.eatingAnimation = eatingAnim.bake(root);
        this.idleAnimation = idleAnim.bake(root);
        if (dashAnim != null) {
            this.dashAnimation = dashAnim.bake(root);
        } else {
            this.dashAnimation = null;
        }
    }

    @Override
    public void setupAnim(final NECamelRenderState state) {
        super.setupAnim(state);
        this.rootPart.resetPose();

        boolean isMovingAllowed = !state.sitAnimationState.isStarted()
                && !state.sittingHoldAnimationState.isStarted()
                && !state.sleepAnimationState.isStarted()
                && !state.sleepingHoldAnimationState.isStarted()
                && !state.wakeUpAnimationState.isStarted()
                && !state.standAnimationState.isStarted()
                && !state.eatingAnimationState.isStarted();

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
        this.eatingAnimation.apply(state.eatingAnimationState, state.ageInTicks);
        this.idleAnimation.apply(state.idleAnimationState, state.ageInTicks);

        if (this.dashAnimation != null) {
            this.dashAnimation.apply(state.dashAnimationState, state.ageInTicks);
        }
    }
}

