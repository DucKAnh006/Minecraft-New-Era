package com.newera.docs.entity.model.wolf;

import com.newera.docs.entity.state.NEWolfRenderState;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;

public abstract class NEWolfAbstractModel extends EntityModel<NEWolfRenderState> {
    private static final float MAX_HEAD_X_ROT = 25.0F;
    private static final float MAX_HEAD_Y_ROT = 32.5F;
    private static final float WALK_ANIMATION_SPEED = 2.0F;
    private static final float WALK_ANIMATION_SCALE_FACTOR = 2.5F;

    protected final ModelPart rootPart;
    protected final ModelPart actualRoot;
    protected final ModelPart body;
    protected final ModelPart head;
    protected final ModelPart tail;
    
    private final KeyframeAnimation idleAnimation;
    private final KeyframeAnimation sitAnimation;
    private final KeyframeAnimation sittingHoldAnimation;
    private final KeyframeAnimation sleepAnimation;
    private final KeyframeAnimation sleepingHoldAnimation;
    private final KeyframeAnimation wakeUpAnimation;
    private final KeyframeAnimation standAnimation;
    private final KeyframeAnimation walkAnimation;
    private final KeyframeAnimation howlAnimation;
    private final KeyframeAnimation sitHowlingAnimation;

    public NEWolfAbstractModel(
            final ModelPart root,
            final AnimationDefinition idleAnim,
            final AnimationDefinition sitAnim,
            final AnimationDefinition sittingHoldAnim,
            final AnimationDefinition sleepAnim,
            final AnimationDefinition sleepingHoldAnim,
            final AnimationDefinition wakeUpAnim,
            final AnimationDefinition standAnim,
            final AnimationDefinition walkAnim,
            final AnimationDefinition howlAnim,
            final AnimationDefinition sitHowlingAnim
    ) {
        super(root);
        this.rootPart = root;
        this.actualRoot = root.getChild("root");
        this.body = this.actualRoot.getChild("body");
        this.head = this.body.getChild("head");
        this.tail = this.body.getChild("tail");

        this.idleAnimation = idleAnim.bake(root);
        this.sitAnimation = sitAnim.bake(root);
        this.sittingHoldAnimation = sittingHoldAnim.bake(root);
        this.sleepAnimation = sleepAnim.bake(root);
        this.sleepingHoldAnimation = sleepingHoldAnim.bake(root);
        this.wakeUpAnimation = wakeUpAnim.bake(root);
        this.standAnimation = standAnim.bake(root);
        this.walkAnimation = walkAnim.bake(root);
        this.howlAnimation = howlAnim != null ? howlAnim.bake(root) : null;
        this.sitHowlingAnimation = sitHowlingAnim != null ? sitHowlingAnim.bake(root) : null;
    }

    @Override
    public void setupAnim(final NEWolfRenderState state) {
        super.setupAnim(state);
        this.rootPart.resetPose();

        boolean isMovingAllowed = !state.sitAnimationState.isStarted()
                && !state.sittingHoldAnimationState.isStarted()
                && !state.sleepAnimationState.isStarted()
                && !state.sleepingHoldAnimationState.isStarted()
                && !state.wakeUpAnimationState.isStarted()
                && !state.standAnimationState.isStarted()
                && !state.howlAnimationState.isStarted()
                && !state.sitHowlingAnimationState.isStarted();

        boolean isLookingAllowed = !state.sleepAnimationState.isStarted()
                && !state.sleepingHoldAnimationState.isStarted()
                && !state.wakeUpAnimationState.isStarted()
                && !state.howlAnimationState.isStarted()
                && !state.sitHowlingAnimationState.isStarted();

        if (isLookingAllowed) {
            this.head.xRot = Mth.clamp(state.xRot, -MAX_HEAD_X_ROT, MAX_HEAD_X_ROT) * (float) (Math.PI / 180.0);
            this.head.yRot = Mth.clamp(state.yRot, -MAX_HEAD_Y_ROT, MAX_HEAD_Y_ROT) * (float) (Math.PI / 180.0);
        } else {
            this.head.xRot = 0;
            this.head.yRot = 0;
        }

        if (state.isWet) {
            this.tail.zRot = state.tailAngle;
        }

        if (state.isAngry) {
            this.tail.yRot = 0.0F;
            this.tail.xRot += (float)Math.PI / 2F;
        } else {
            this.tail.yRot = Mth.cos(state.walkAnimationPos * 0.6662F) * 1.4F * state.walkAnimationSpeed;
            this.tail.xRot += state.tailAngle;
        }

        if (isMovingAllowed) {
            this.walkAnimation.applyWalk(state.walkAnimationPos, state.walkAnimationSpeed, WALK_ANIMATION_SPEED, WALK_ANIMATION_SCALE_FACTOR);
        }

        this.idleAnimation.apply(state.idleAnimationState, state.ageInTicks);
        this.sitAnimation.apply(state.sitAnimationState, state.ageInTicks);
        this.sittingHoldAnimation.apply(state.sittingHoldAnimationState, state.ageInTicks);
        this.sleepAnimation.apply(state.sleepAnimationState, state.ageInTicks);
        this.sleepingHoldAnimation.apply(state.sleepingHoldAnimationState, state.ageInTicks);
        this.wakeUpAnimation.apply(state.wakeUpAnimationState, state.ageInTicks);
        this.standAnimation.apply(state.standAnimationState, state.ageInTicks);
        
        if (this.howlAnimation != null) this.howlAnimation.apply(state.howlAnimationState, state.ageInTicks);
        if (this.sitHowlingAnimation != null) this.sitHowlingAnimation.apply(state.sitHowlingAnimationState, state.ageInTicks);


    }
}
