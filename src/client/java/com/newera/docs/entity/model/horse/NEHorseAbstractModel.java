package com.newera.docs.entity.model.horse;

import com.newera.docs.entity.state.NEHorseRenderState;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;

public abstract class NEHorseAbstractModel extends EntityModel<NEHorseRenderState> {
    private static final float MAX_HEAD_X_ROT = 25.0F;
    private static final float MAX_HEAD_Y_ROT = 32.5F;
    private static final float WALK_ANIMATION_SPEED = 2.0F;
    private static final float WALK_ANIMATION_SCALE_FACTOR = 1.0F;

    private final ModelPart rootPart;
    private final ModelPart head;
    private final ModelPart view_bone;
    private final ModelPart neck;
    private final ModelPart body;
    private final ModelPart saddle;
    private final ModelPart bridle;
    private final ModelPart left_reins;
    private final ModelPart right_reins;

    private final KeyframeAnimation sitAnimation;
    private final KeyframeAnimation sittingHoldAnimation;
    private final KeyframeAnimation sleepAnimation;
    private final KeyframeAnimation sleepingHoldAnimation;
    private final KeyframeAnimation wakeUpAnimation;
    private final KeyframeAnimation standAnimation;
    private final KeyframeAnimation walkAnimation;
    private final KeyframeAnimation grazingAnimation;
    private final KeyframeAnimation idleAnimation;
    private final KeyframeAnimation rearAnimation;
    private final KeyframeAnimation attackAnimation;
    private final float rootYOffset;

    public NEHorseAbstractModel(
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
        super(root);
        this.rootYOffset = rootYOffset;
        this.rootPart = root.getChild("root");
        this.body = this.rootPart.getChild("body");
        
        ModelPart centroid = this.body.hasChild("centroid") ? this.body.getChild("centroid") : null;

        ModelPart currentNeck = null;
        if (centroid != null && centroid.hasChild("neck")) {
            currentNeck = centroid.getChild("neck");
        } else if (this.rootPart.hasChild("neck")) {
            currentNeck = this.rootPart.getChild("neck");
        } else if (this.body.hasChild("neck")) {
            currentNeck = this.body.getChild("neck");
        }
        this.neck = currentNeck;
        
        ModelPart currentViewBone = null;
        if (centroid != null && centroid.hasChild("view_bone")) {
            currentViewBone = centroid.getChild("view_bone");
        } else if (this.rootPart.hasChild("view_bone")) {
            currentViewBone = this.rootPart.getChild("view_bone");
        } else if (this.body.hasChild("view_bone")) {
            currentViewBone = this.body.getChild("view_bone");
        }
        this.view_bone = currentViewBone;

        ModelPart currentHead = null;
        if (this.neck != null && this.neck.hasChild("head")) {
            currentHead = this.neck.getChild("head");
        } else if (centroid != null && centroid.hasChild("head")) {
            currentHead = centroid.getChild("head");
        } else if (this.rootPart.hasChild("head")) {
            currentHead = this.rootPart.getChild("head");
        } else if (this.body.hasChild("head")) {
            currentHead = this.body.getChild("head");
        }
        this.head = currentHead;

        this.saddle = centroid != null && centroid.hasChild("saddle") ? centroid.getChild("saddle") : null;
        this.bridle = this.head != null && this.head.hasChild("bridle") ? this.head.getChild("bridle") : null;
        this.left_reins = this.head != null && this.head.hasChild("left_reins") ? this.head.getChild("left_reins") : null;
        this.right_reins = this.head != null && this.head.hasChild("right_reins") ? this.head.getChild("right_reins") : null;

        this.sitAnimation = sitAnim.bake(root);
        this.sittingHoldAnimation = sittingHoldAnim.bake(root);
        this.sleepAnimation = sleepAnim.bake(root);
        this.sleepingHoldAnimation = sleepingHoldAnim.bake(root);
        this.wakeUpAnimation = wakeUpAnim.bake(root);
        this.standAnimation = standAnim.bake(root);
        this.walkAnimation = walkAnim.bake(root);
        this.grazingAnimation = grazingAnim.bake(root);
        this.idleAnimation = idleAnim.bake(root);
        this.rearAnimation = rearAnim.bake(root);
        this.attackAnimation = attackAnim.bake(root);
    }

    @Override
    public void setupAnim(final NEHorseRenderState state) {
        super.setupAnim(state);
        this.rootPart.resetPose();

        boolean isMovingAllowed = !state.sitAnimationState.isStarted()
                && !state.sittingHoldAnimationState.isStarted()
                && !state.sleepAnimationState.isStarted()
                && !state.sleepingHoldAnimationState.isStarted()
                && !state.wakeUpAnimationState.isStarted()
                && !state.standAnimationState.isStarted()
                && !state.rearAnimationState.isStarted()
                && !state.attackAnimationState.isStarted();

        boolean isLookingAllowed = !state.sleepAnimationState.isStarted()
                && !state.sleepingHoldAnimationState.isStarted()
                && !state.wakeUpAnimationState.isStarted()
                && !state.grazingAnimationState.isStarted();

        if (isLookingAllowed && this.view_bone != null) {
            this.view_bone.xRot = Mth.clamp(state.xRot, -MAX_HEAD_X_ROT, MAX_HEAD_X_ROT) * (float) (Math.PI / 90.0);
            this.view_bone.yRot = Mth.clamp(state.yRot, -MAX_HEAD_Y_ROT, MAX_HEAD_Y_ROT) * (float) (Math.PI / 180.0);
        } else if (this.view_bone != null) {
            this.view_bone.xRot = 0;
            this.view_bone.yRot = 0;
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
        this.rearAnimation.apply(state.rearAnimationState, state.ageInTicks);
        this.attackAnimation.apply(state.attackAnimationState, state.ageInTicks);

        boolean saddled = state.isSaddled;
        if (this.saddle != null) this.saddle.visible = saddled;
        if (this.bridle != null) this.bridle.visible = saddled;
        if (this.left_reins != null) this.left_reins.visible = saddled;
        if (this.right_reins != null) this.right_reins.visible = saddled;
    }
}
