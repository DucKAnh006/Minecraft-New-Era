package com.newera.docs.entity.model.parrot;

import com.newera.docs.entity.state.NEParrotRenderState;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;

public abstract class NEParrotAbstractModel extends EntityModel<NEParrotRenderState> {
    private static final float MAX_HEAD_X_ROT = 25.0F;
    private static final float MAX_HEAD_Y_ROT = 32.5F;
    private static final float WALK_ANIMATION_SPEED = 2.5F;
    private static final float WALK_ANIMATION_SCALE_FACTOR = 2.5F;

    private final ModelPart rootPart;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart right_leg;
    private final ModelPart left_leg;
    private final ModelPart right_wing;
    private final ModelPart left_wing;

    private final KeyframeAnimation idleGroundAnimation;
    private final KeyframeAnimation groundToFlyAnimation;
    private final KeyframeAnimation flyToGroundAnimation;
    private final KeyframeAnimation idleFlyAnimation;
    private final KeyframeAnimation flyFlapAnimation;
    private final KeyframeAnimation glideFlapAnimation;
    private final KeyframeAnimation walkAnimation;
    private final KeyframeAnimation sitAnimation;
    private final KeyframeAnimation sittingAnimation;
    private final KeyframeAnimation standAnimation;
    private final KeyframeAnimation sleepAnimation;
    private final KeyframeAnimation sleepingAnimation;
    private final KeyframeAnimation wakeUpAnimation;
    private final KeyframeAnimation danceAnimation;

    public NEParrotAbstractModel(
            final ModelPart root,
            final AnimationDefinition idleGroundAnim,
            final AnimationDefinition groundToFlyAnim,
            final AnimationDefinition flyToGroundAnim,
            final AnimationDefinition idleFlyAnim,
            final AnimationDefinition flyFlapAnim,
            final AnimationDefinition glideFlapAnim,
            final AnimationDefinition walkAnim,
            final AnimationDefinition sitAnim,
            final AnimationDefinition sittingAnim,
            final AnimationDefinition standAnim,
            final AnimationDefinition sleepAnim,
            final AnimationDefinition sleepingAnim,
            final AnimationDefinition wakeUpAnim,
            final AnimationDefinition danceAnim
    ) {
        super(root);
        this.rootPart = root.getChild("root");
        this.body = this.rootPart.getChild("body");
        this.head = this.body.getChild("head");
        this.right_leg = this.rootPart.getChild("right_leg");
        this.left_leg = this.rootPart.getChild("left_leg");
        this.right_wing = this.body.getChild("right_wing");
        this.left_wing = this.body.getChild("left_wing");

        this.idleGroundAnimation = idleGroundAnim.bake(root);
        this.groundToFlyAnimation = groundToFlyAnim.bake(root);
        this.flyToGroundAnimation = flyToGroundAnim.bake(root);
        this.idleFlyAnimation = idleFlyAnim.bake(root);
        this.flyFlapAnimation = flyFlapAnim.bake(root);
        this.glideFlapAnimation = glideFlapAnim.bake(root);
        this.walkAnimation = walkAnim.bake(root);
        this.sitAnimation = sitAnim.bake(root);
        this.sittingAnimation = sittingAnim.bake(root);
        this.standAnimation = standAnim.bake(root);
        this.sleepAnimation = sleepAnim.bake(root);
        this.sleepingAnimation = sleepingAnim.bake(root);
        this.wakeUpAnimation = wakeUpAnim.bake(root);
        this.danceAnimation = danceAnim.bake(root);
    }

    @Override
    public void setupAnim(NEParrotRenderState state) {
        super.setupAnim(state);
        this.rootPart.getAllParts().forEach(ModelPart::resetPose);
        
        boolean isMovingAllowed = !state.sitAnimationState.isStarted()
                && !state.sittingAnimationState.isStarted()
                && !state.sleepAnimationState.isStarted()
                && !state.sleepingAnimationState.isStarted()
                && !state.wakeUpAnimationState.isStarted()
                && !state.standAnimationState.isStarted()
                && !state.groundToFlyAnimationState.isStarted()
                && !state.flyToGroundAnimationState.isStarted();

        boolean isLookingAllowed = !state.sleepAnimationState.isStarted()
                && !state.sleepingAnimationState.isStarted()
                && !state.wakeUpAnimationState.isStarted();

        if (isLookingAllowed && !state.isBaby) {
            this.head.xRot = Mth.clamp(state.xRot, -MAX_HEAD_X_ROT, MAX_HEAD_X_ROT) * (float) (Math.PI / 90.0);
            this.head.yRot = Mth.clamp(state.yRot, -MAX_HEAD_Y_ROT, MAX_HEAD_Y_ROT) * (float) (Math.PI / 180.0);
        } else {
            this.head.xRot = 0;
            this.head.yRot = 0;
        }

        if (isMovingAllowed && state.walkAnimationState.isStarted()) {
            this.walkAnimation.applyWalk(state.walkAnimationPos, state.walkAnimationSpeed, WALK_ANIMATION_SPEED, WALK_ANIMATION_SCALE_FACTOR);
        }

        if (state.glideFlapAnimationState.isStarted()) {
            this.rootPart.zRot = state.roll;
        }

        this.idleGroundAnimation.apply(state.idleGroundAnimationState, state.ageInTicks);
        this.idleFlyAnimation.apply(state.idleFlyAnimationState, state.ageInTicks);
        this.flyFlapAnimation.apply(state.flyFlapAnimationState, state.ageInTicks);
        this.glideFlapAnimation.apply(state.glideFlapAnimationState, state.ageInTicks);
        this.sitAnimation.apply(state.sitAnimationState, state.ageInTicks);
        this.sittingAnimation.apply(state.sittingAnimationState, state.ageInTicks);
        this.standAnimation.apply(state.standAnimationState, state.ageInTicks);
        this.sleepAnimation.apply(state.sleepAnimationState, state.ageInTicks);
        this.sleepingAnimation.apply(state.sleepingAnimationState, state.ageInTicks);
        this.wakeUpAnimation.apply(state.wakeUpAnimationState, state.ageInTicks);
        
        this.danceAnimation.apply(state.danceAnimationState, state.ageInTicks);

        // Apply transition animations LAST to override standard flaps if they overlap
        this.groundToFlyAnimation.apply(state.groundToFlyAnimationState, state.ageInTicks);
        this.flyToGroundAnimation.apply(state.flyToGroundAnimationState, state.ageInTicks);
    }
}
