package com.newera.docs.entity.animation.animal.horse;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

public class HorseAnimation {
    public static final AnimationDefinition HORSE_SIT = AnimationDefinition.Builder.withLength(0.9167f)
        .addAnimation("root",
            new AnimationChannel(AnimationChannel.Targets.SCALE,
                new Keyframe(0.6667f, KeyframeAnimations.scaleVec(1f, 1f, 1f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.7917f, KeyframeAnimations.scaleVec(1.1f, 0.9f, 1.1f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.9167f, KeyframeAnimations.scaleVec(1f, 1f, 1f), AnimationChannel.Interpolations.LINEAR)
            ))
        .addAnimation("tail",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(7.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.25f, KeyframeAnimations.degreeVec(27.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.5f, KeyframeAnimations.degreeVec(37.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            ))
        .addAnimation("left_front_leg",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.3333f, KeyframeAnimations.degreeVec(20f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.5f, KeyframeAnimations.degreeVec(42.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.6667f, KeyframeAnimations.degreeVec(90f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            ))
        .addAnimation("left_front_leg",
            new AnimationChannel(AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.3333f, KeyframeAnimations.posVec(0f, 1f, -2f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.5f, KeyframeAnimations.posVec(0f, -2f, -3f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.6667f, KeyframeAnimations.posVec(-0.1f, -10f, -4f), AnimationChannel.Interpolations.LINEAR)
            ))
        .addAnimation("right_front_leg",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.3333f, KeyframeAnimations.degreeVec(20f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.5f, KeyframeAnimations.degreeVec(42.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.6667f, KeyframeAnimations.degreeVec(90f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            ))
        .addAnimation("right_front_leg",
            new AnimationChannel(AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.3333f, KeyframeAnimations.posVec(0f, 1f, -2f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.5f, KeyframeAnimations.posVec(0f, -2f, -3f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.6667f, KeyframeAnimations.posVec(0.1f, -10f, -4f), AnimationChannel.Interpolations.LINEAR)
            ))
        .addAnimation("left_hind_leg",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.4583f, KeyframeAnimations.degreeVec(-90f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.6667f, KeyframeAnimations.degreeVec(-90f, -20f, 0f), AnimationChannel.Interpolations.LINEAR)
            ))
        .addAnimation("left_hind_leg",
            new AnimationChannel(AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.4583f, KeyframeAnimations.posVec(0f, -10f, 3f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.6667f, KeyframeAnimations.posVec(0f, -10f, 3f), AnimationChannel.Interpolations.LINEAR)
            ))
        .addAnimation("right_hind_leg",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.4583f, KeyframeAnimations.degreeVec(-90f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.6667f, KeyframeAnimations.degreeVec(-90f, 20f, 0f), AnimationChannel.Interpolations.LINEAR)
            ))
        .addAnimation("right_hind_leg",
            new AnimationChannel(AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.4583f, KeyframeAnimations.posVec(0f, -10f, 3f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.6667f, KeyframeAnimations.posVec(0f, -10f, 3f), AnimationChannel.Interpolations.LINEAR)
            ))
        .addAnimation("neck",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.25f, KeyframeAnimations.degreeVec(27.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            ))
        .addAnimation("neck",
            new AnimationChannel(AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.5f, KeyframeAnimations.posVec(0f, -2f, -1f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.9167f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            ))
        .addAnimation("centroid",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.1667f, KeyframeAnimations.degreeVec(-19.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.3333f, KeyframeAnimations.degreeVec(-26f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.5f, KeyframeAnimations.degreeVec(-22f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.6667f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            ))
        .addAnimation("centroid",
            new AnimationChannel(AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.3333f, KeyframeAnimations.posVec(0f, -7f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.6667f, KeyframeAnimations.posVec(0f, -10f, 0f), AnimationChannel.Interpolations.LINEAR)
            ))
        .build();

    public static final AnimationDefinition HORSE_SITTING = AnimationDefinition.Builder.withLength(0f).looping()
        .addAnimation("tail",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(37.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            ))
        .addAnimation("left_front_leg",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(90f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            ))
        .addAnimation("left_front_leg",
            new AnimationChannel(AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(-0.1f, -10f, -4f), AnimationChannel.Interpolations.LINEAR)
            ))
        .addAnimation("right_front_leg",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(90f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            ))
        .addAnimation("right_front_leg",
            new AnimationChannel(AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0.1f, -10f, -4f), AnimationChannel.Interpolations.LINEAR)
            ))
        .addAnimation("left_hind_leg",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-90f, -20f, 0f), AnimationChannel.Interpolations.LINEAR)
            ))
        .addAnimation("left_hind_leg",
            new AnimationChannel(AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, -10f, 3f), AnimationChannel.Interpolations.LINEAR)
            ))
        .addAnimation("right_hind_leg",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-90f, 20f, 0f), AnimationChannel.Interpolations.LINEAR)
            ))
        .addAnimation("right_hind_leg",
            new AnimationChannel(AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, -10f, 3f), AnimationChannel.Interpolations.LINEAR)
            ))
        .addAnimation("neck",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            ))
        .addAnimation("neck",
            new AnimationChannel(AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            ))
        .addAnimation("centroid",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            ))
        .addAnimation("centroid",
            new AnimationChannel(AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, -10f, 0f), AnimationChannel.Interpolations.LINEAR)
            ))
        .build();

    public static final AnimationDefinition HORSE_STAND = AnimationDefinition.Builder.withLength(1.875f)
        .addAnimation("tail",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(37.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.5f, KeyframeAnimations.degreeVec(72.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.75f, KeyframeAnimations.degreeVec(7.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            ))
        .addAnimation("left_front_leg",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(90f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.25f, KeyframeAnimations.degreeVec(65f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.75f, KeyframeAnimations.degreeVec(-22.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.25f, KeyframeAnimations.degreeVec(-40f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.625f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            ))
        .addAnimation("left_front_leg",
            new AnimationChannel(AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(-0.1f, -10f, -4f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.25f, KeyframeAnimations.posVec(0f, -5f, -2f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.5f, KeyframeAnimations.posVec(-0.5f, -1f, 1f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.75f, KeyframeAnimations.posVec(0f, -2f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.25f, KeyframeAnimations.posVec(0f, -3f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.625f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            ))
        .addAnimation("right_front_leg",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(90f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.25f, KeyframeAnimations.degreeVec(65f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.75f, KeyframeAnimations.degreeVec(-22.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.25f, KeyframeAnimations.degreeVec(-40f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.625f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            ))
        .addAnimation("right_front_leg",
            new AnimationChannel(AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0.1f, -10f, -4f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.25f, KeyframeAnimations.posVec(0f, -5f, -2f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.5f, KeyframeAnimations.posVec(0.5f, -1f, 1f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.75f, KeyframeAnimations.posVec(0f, -2f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.25f, KeyframeAnimations.posVec(0f, -3f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.625f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            ))
        .addAnimation("left_hind_leg",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-90f, -20f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.25f, KeyframeAnimations.degreeVec(-90f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.375f, KeyframeAnimations.degreeVec(-90f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.25f, KeyframeAnimations.degreeVec(20f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.5f, KeyframeAnimations.degreeVec(27.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.875f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            ))
        .addAnimation("left_hind_leg",
            new AnimationChannel(AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, -10f, 3f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.25f, KeyframeAnimations.posVec(0f, -10f, 3.5f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.375f, KeyframeAnimations.posVec(0f, -10f, 5f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.5f, KeyframeAnimations.posVec(0f, -8f, 4.4f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.25f, KeyframeAnimations.posVec(0f, 0f, -1f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.5f, KeyframeAnimations.posVec(0f, -1f, -1f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.875f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            ))
        .addAnimation("right_hind_leg",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-90f, 20f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.25f, KeyframeAnimations.degreeVec(-90f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.375f, KeyframeAnimations.degreeVec(-90f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.25f, KeyframeAnimations.degreeVec(20f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.5f, KeyframeAnimations.degreeVec(27.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.875f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            ))
        .addAnimation("right_hind_leg",
            new AnimationChannel(AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, -10f, 3f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.25f, KeyframeAnimations.posVec(0f, -10f, 3.5f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.375f, KeyframeAnimations.posVec(0f, -10f, 5f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.5f, KeyframeAnimations.posVec(0f, -8f, 4.4f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.25f, KeyframeAnimations.posVec(0f, 0f, -1f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.5f, KeyframeAnimations.posVec(0f, -1f, -1f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.875f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            ))
        .addAnimation("centroid",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.5f, KeyframeAnimations.degreeVec(-25f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.75f, KeyframeAnimations.degreeVec(-15f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1f, KeyframeAnimations.degreeVec(5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.375f, KeyframeAnimations.degreeVec(-2.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.875f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            ))
        .addAnimation("centroid",
            new AnimationChannel(AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, -10f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.375f, KeyframeAnimations.posVec(0f, -8f, 1f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.75f, KeyframeAnimations.posVec(0f, -8f, 1f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1f, KeyframeAnimations.posVec(0f, -3f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.375f, KeyframeAnimations.posVec(0f, -3f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.875f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            ))
        .addAnimation("neck",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.375f, KeyframeAnimations.degreeVec(42.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1f, KeyframeAnimations.degreeVec(-15f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.5833f, KeyframeAnimations.degreeVec(25f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.875f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            ))
        .addAnimation("neck",
            new AnimationChannel(AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.375f, KeyframeAnimations.posVec(0f, -2f, -2f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1f, KeyframeAnimations.posVec(0f, -3f, -1f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.5833f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            ))
        .build();

    public static final AnimationDefinition HORSE_SLEEP = AnimationDefinition.Builder.withLength(1f)
        .addAnimation("neck",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1f, KeyframeAnimations.degreeVec(20f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            ))
        .build();

    public static final AnimationDefinition HORSE_SLEEPPING = AnimationDefinition.Builder.withLength(0f).looping()
        .addAnimation("neck",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(20f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            ))
        .build();

    public static final AnimationDefinition HORSE_WAKE_UP = AnimationDefinition.Builder.withLength(1f)
        .addAnimation("neck",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(20f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            ))
        .build();

    public static final AnimationDefinition HORSE_WALK = AnimationDefinition.Builder.withLength(1.5f).looping()
        .addAnimation("tail",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, -12.5f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, 12.5f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.5f, KeyframeAnimations.degreeVec(0f, -12.5f, 0f), AnimationChannel.Interpolations.LINEAR)
            ))
        .addAnimation("left_front_leg",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-30f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.75f, KeyframeAnimations.degreeVec(30f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.5f, KeyframeAnimations.degreeVec(-30f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            ))
        .addAnimation("left_front_leg",
            new AnimationChannel(AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.75f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.125f, KeyframeAnimations.posVec(0f, 4f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.5f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            ))
        .addAnimation("right_front_leg",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(30f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.75f, KeyframeAnimations.degreeVec(-30f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.5f, KeyframeAnimations.degreeVec(30f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            ))
        .addAnimation("right_front_leg",
            new AnimationChannel(AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.375f, KeyframeAnimations.posVec(0f, 4f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.75f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.5f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            ))
        .addAnimation("left_hind_leg",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(30f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.75f, KeyframeAnimations.degreeVec(-30f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.5f, KeyframeAnimations.degreeVec(30f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            ))
        .addAnimation("left_hind_leg",
            new AnimationChannel(AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.375f, KeyframeAnimations.posVec(0f, 4f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.75f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.5f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            ))
        .addAnimation("right_hind_leg",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(-30f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.75f, KeyframeAnimations.degreeVec(30f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.5f, KeyframeAnimations.degreeVec(-30f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            ))
        .addAnimation("right_hind_leg",
            new AnimationChannel(AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.75f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.125f, KeyframeAnimations.posVec(0f, 4f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.5f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            ))
        .addAnimation("right_ear",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.375f, KeyframeAnimations.degreeVec(20f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.125f, KeyframeAnimations.degreeVec(20f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            ))
        .addAnimation("left_ear",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.375f, KeyframeAnimations.degreeVec(20f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.125f, KeyframeAnimations.degreeVec(20f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            ))
        .addAnimation("neck",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.375f, KeyframeAnimations.degreeVec(10f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.125f, KeyframeAnimations.degreeVec(10f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            ))
        .addAnimation("centroid",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 5f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.75f, KeyframeAnimations.degreeVec(0f, 0f, -5f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.5f, KeyframeAnimations.degreeVec(0f, 0f, 5f), AnimationChannel.Interpolations.LINEAR)
            ))
        .build();

    public static final AnimationDefinition HORSE_GRAZING = AnimationDefinition.Builder.withLength(2f)
        .addAnimation("neck",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.25f, KeyframeAnimations.degreeVec(75f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.375f, KeyframeAnimations.degreeVec(70f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.5f, KeyframeAnimations.degreeVec(75f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.625f, KeyframeAnimations.degreeVec(70f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.75f, KeyframeAnimations.degreeVec(75f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.875f, KeyframeAnimations.degreeVec(70f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1f, KeyframeAnimations.degreeVec(75f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.125f, KeyframeAnimations.degreeVec(70f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.25f, KeyframeAnimations.degreeVec(75f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.375f, KeyframeAnimations.degreeVec(70f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.5f, KeyframeAnimations.degreeVec(75f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.625f, KeyframeAnimations.degreeVec(70f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.75f, KeyframeAnimations.degreeVec(75f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(2f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            ))
        .addAnimation("neck",
            new AnimationChannel(AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(2f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            ))
        .addAnimation("centroid",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.25f, KeyframeAnimations.degreeVec(12.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.75f, KeyframeAnimations.degreeVec(12.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(2f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            ))
        .addAnimation("centroid",
            new AnimationChannel(AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.25f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.75f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(2f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            ))
        .build();

    public static final AnimationDefinition HORSE_IDLE = AnimationDefinition.Builder.withLength(4f).looping()
        .addAnimation("tail",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(30f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(4f, KeyframeAnimations.degreeVec(30f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            ))
        .addAnimation("right_ear",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, -15f, -12.5f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(2.5f, KeyframeAnimations.degreeVec(0f, -15f, -12.5f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(2.625f, KeyframeAnimations.degreeVec(30f, -15f, -12.5f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(2.75f, KeyframeAnimations.degreeVec(-20f, -15f, -12.5f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(2.875f, KeyframeAnimations.degreeVec(30f, -15f, -12.5f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(3f, KeyframeAnimations.degreeVec(0f, -15f, -12.5f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(4f, KeyframeAnimations.degreeVec(0f, -15f, -12.5f), AnimationChannel.Interpolations.CATMULLROM)
            ))
        .addAnimation("left_ear",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 12.5f, 15f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(2.5f, KeyframeAnimations.degreeVec(0f, 12.5f, 15f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(2.625f, KeyframeAnimations.degreeVec(30f, 12.5f, 15f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(2.75f, KeyframeAnimations.degreeVec(-20f, 12.5f, 15f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(2.875f, KeyframeAnimations.degreeVec(30f, 12.5f, 15f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(3f, KeyframeAnimations.degreeVec(0f, 12.5f, 15f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(4f, KeyframeAnimations.degreeVec(0f, 12.5f, 15f), AnimationChannel.Interpolations.CATMULLROM)
            ))
        .addAnimation("neck",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(30f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            ))
        .build();

    public static final AnimationDefinition HORSE_REAR = AnimationDefinition.Builder.withLength(1.5f)
        .addAnimation("body",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.25f, KeyframeAnimations.degreeVec(-65f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1.25f, KeyframeAnimations.degreeVec(-65f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            ))
        .addAnimation("body",
            new AnimationChannel(AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.25f, KeyframeAnimations.posVec(0f, -1f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1.25f, KeyframeAnimations.posVec(0f, -1f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1.5f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            ))
        .addAnimation("tail",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.25f, KeyframeAnimations.degreeVec(45f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1.25f, KeyframeAnimations.degreeVec(45f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            ))
        .addAnimation("left_front_leg",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.250f, KeyframeAnimations.degreeVec(6.123233995736766e-15f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.300f, KeyframeAnimations.degreeVec(-29.38926261462365f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.350f, KeyframeAnimations.degreeVec(-47.552825814757675f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.400f, KeyframeAnimations.degreeVec(-47.55282581475768f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.450f, KeyframeAnimations.degreeVec(-29.3892626146237f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.500f, KeyframeAnimations.degreeVec(-5.6655388976479795e-14f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.550f, KeyframeAnimations.degreeVec(29.38926261462361f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.600f, KeyframeAnimations.degreeVec(47.552825814757675f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.650f, KeyframeAnimations.degreeVec(47.55282581475768f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.700f, KeyframeAnimations.degreeVec(29.389262614623668f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.750f, KeyframeAnimations.degreeVec(-7.044813998280223e-14f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.800f, KeyframeAnimations.degreeVec(-29.38926261462371f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.850f, KeyframeAnimations.degreeVec(-47.552825814757696f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.900f, KeyframeAnimations.degreeVec(-47.552825814757604f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.950f, KeyframeAnimations.degreeVec(-29.38926261462346f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.000f, KeyframeAnimations.degreeVec(1.5314274795707797e-13f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.050f, KeyframeAnimations.degreeVec(29.389262614623778f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.100f, KeyframeAnimations.degreeVec(47.552825814757725f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.150f, KeyframeAnimations.degreeVec(47.55282581475763f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.200f, KeyframeAnimations.degreeVec(29.38926261462339f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.250f, KeyframeAnimations.degreeVec(-2.3583735593135373e-13f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            ))
        .addAnimation("left_front_leg",
            new AnimationChannel(AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.250f, KeyframeAnimations.posVec(0f, 0f, -2f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.300f, KeyframeAnimations.posVec(0f, 0f, -1.618033988749895f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.350f, KeyframeAnimations.posVec(0f, 0f, -0.6180339887498951f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.400f, KeyframeAnimations.posVec(0f, 0f, 0.6180339887498945f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.450f, KeyframeAnimations.posVec(0f, 0f, 1.6180339887498936f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.500f, KeyframeAnimations.posVec(0f, 0f, 2f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.550f, KeyframeAnimations.posVec(0f, 0f, 1.6180339887498962f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.600f, KeyframeAnimations.posVec(0f, 0f, 0.6180339887498955f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.650f, KeyframeAnimations.posVec(0f, 0f, -0.6180339887498942f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.700f, KeyframeAnimations.posVec(0f, 0f, -1.6180339887498945f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.750f, KeyframeAnimations.posVec(0f, 0f, -2f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.800f, KeyframeAnimations.posVec(0f, 0f, -1.6180339887498931f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.850f, KeyframeAnimations.posVec(0f, 0f, -0.6180339887498922f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.900f, KeyframeAnimations.posVec(0f, 0f, 0.6180339887499041f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.950f, KeyframeAnimations.posVec(0f, 0f, 1.6180339887499005f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.000f, KeyframeAnimations.posVec(0f, 0f, 2f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.050f, KeyframeAnimations.posVec(0f, 0f, 1.6180339887498913f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.100f, KeyframeAnimations.posVec(0f, 0f, 0.6180339887498891f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.150f, KeyframeAnimations.posVec(0f, 0f, -0.6180339887499006f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.200f, KeyframeAnimations.posVec(0f, 0f, -1.6180339887499025f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.250f, KeyframeAnimations.posVec(0f, 0f, -2f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.5f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            ))
        .addAnimation("right_front_leg",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.250f, KeyframeAnimations.degreeVec(-6.123233995736766e-15f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.300f, KeyframeAnimations.degreeVec(29.38926261462365f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.350f, KeyframeAnimations.degreeVec(47.552825814757675f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.400f, KeyframeAnimations.degreeVec(47.55282581475768f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.450f, KeyframeAnimations.degreeVec(29.3892626146237f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.500f, KeyframeAnimations.degreeVec(5.6655388976479795e-14f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.550f, KeyframeAnimations.degreeVec(-29.38926261462361f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.600f, KeyframeAnimations.degreeVec(-47.552825814757675f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.650f, KeyframeAnimations.degreeVec(-47.55282581475768f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.700f, KeyframeAnimations.degreeVec(-29.389262614623668f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.750f, KeyframeAnimations.degreeVec(7.044813998280223e-14f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.800f, KeyframeAnimations.degreeVec(29.38926261462371f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.850f, KeyframeAnimations.degreeVec(47.552825814757696f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.900f, KeyframeAnimations.degreeVec(47.552825814757604f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.950f, KeyframeAnimations.degreeVec(29.38926261462346f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.000f, KeyframeAnimations.degreeVec(-1.5314274795707797e-13f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.050f, KeyframeAnimations.degreeVec(-29.389262614623778f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.100f, KeyframeAnimations.degreeVec(-47.552825814757725f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.150f, KeyframeAnimations.degreeVec(-47.55282581475763f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.200f, KeyframeAnimations.degreeVec(-29.38926261462339f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.250f, KeyframeAnimations.degreeVec(2.3583735593135373e-13f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            ))
        .addAnimation("right_front_leg",
            new AnimationChannel(AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.250f, KeyframeAnimations.posVec(0f, 0f, 2f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.300f, KeyframeAnimations.posVec(0f, 0f, 1.618033988749895f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.350f, KeyframeAnimations.posVec(0f, 0f, 0.6180339887498951f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.400f, KeyframeAnimations.posVec(0f, 0f, -0.6180339887498945f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.450f, KeyframeAnimations.posVec(0f, 0f, -1.6180339887498936f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.500f, KeyframeAnimations.posVec(0f, 0f, -2f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.550f, KeyframeAnimations.posVec(0f, 0f, -1.6180339887498962f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.600f, KeyframeAnimations.posVec(0f, 0f, -0.6180339887498955f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.650f, KeyframeAnimations.posVec(0f, 0f, 0.6180339887498942f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.700f, KeyframeAnimations.posVec(0f, 0f, 1.6180339887498945f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.750f, KeyframeAnimations.posVec(0f, 0f, 2f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.800f, KeyframeAnimations.posVec(0f, 0f, 1.6180339887498931f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.850f, KeyframeAnimations.posVec(0f, 0f, 0.6180339887498922f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.900f, KeyframeAnimations.posVec(0f, 0f, -0.6180339887499041f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.950f, KeyframeAnimations.posVec(0f, 0f, -1.6180339887499005f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.000f, KeyframeAnimations.posVec(0f, 0f, -2f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.050f, KeyframeAnimations.posVec(0f, 0f, -1.6180339887498913f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.100f, KeyframeAnimations.posVec(0f, 0f, -0.6180339887498891f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.150f, KeyframeAnimations.posVec(0f, 0f, 0.6180339887499006f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.200f, KeyframeAnimations.posVec(0f, 0f, 1.6180339887499025f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.250f, KeyframeAnimations.posVec(0f, 0f, 2f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(1.5f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            ))
        .addAnimation("neck",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.25f, KeyframeAnimations.degreeVec(70f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1.25f, KeyframeAnimations.degreeVec(70f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            ))
        .addAnimation("neck",
            new AnimationChannel(AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.25f, KeyframeAnimations.posVec(0f, -2f, -2f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1.25f, KeyframeAnimations.posVec(0f, -2f, -2f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(1.5f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            ))
        .build();

    public static final AnimationDefinition HORSE_ATTACK = AnimationDefinition.Builder.withLength(0.625f)
        .addAnimation("tail",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.125f, KeyframeAnimations.degreeVec(-5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.2917f, KeyframeAnimations.degreeVec(47.5f, 0f, 0f), AnimationChannel.Interpolations.LINEAR),
                new Keyframe(0.5f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.LINEAR)
            ))
        .addAnimation("left_hind_leg",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.125f, KeyframeAnimations.degreeVec(60f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.25f, KeyframeAnimations.degreeVec(87.5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.375f, KeyframeAnimations.degreeVec(87.5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.degreeVec(60f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.625f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            ))
        .addAnimation("left_hind_leg",
            new AnimationChannel(AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.125f, KeyframeAnimations.posVec(0f, 3f, -8f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.25f, KeyframeAnimations.posVec(0f, 5f, 3f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.375f, KeyframeAnimations.posVec(0f, 5f, 4f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 3f, -8f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.625f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            ))
        .addAnimation("right_hind_leg",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.125f, KeyframeAnimations.degreeVec(60f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.25f, KeyframeAnimations.degreeVec(87.5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.375f, KeyframeAnimations.degreeVec(87.5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.degreeVec(60f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.625f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            ))
        .addAnimation("right_hind_leg",
            new AnimationChannel(AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.125f, KeyframeAnimations.posVec(0f, 3f, -8f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.25f, KeyframeAnimations.posVec(0f, 5f, 3f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.375f, KeyframeAnimations.posVec(0f, 5f, 4f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.5f, KeyframeAnimations.posVec(0f, 3f, -8f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.625f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            ))
        .addAnimation("neck",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.125f, KeyframeAnimations.degreeVec(35f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.25f, KeyframeAnimations.degreeVec(50f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.375f, KeyframeAnimations.degreeVec(27.5f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.625f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            ))
        .addAnimation("neck",
            new AnimationChannel(AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.625f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            ))
        .addAnimation("centroid",
            new AnimationChannel(AnimationChannel.Targets.ROTATION,
                new Keyframe(0f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.25f, KeyframeAnimations.degreeVec(15f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.375f, KeyframeAnimations.degreeVec(15f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.625f, KeyframeAnimations.degreeVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            ))
        .addAnimation("centroid",
            new AnimationChannel(AnimationChannel.Targets.POSITION,
                new Keyframe(0f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.125f, KeyframeAnimations.posVec(0f, 2f, -3f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.25f, KeyframeAnimations.posVec(0f, 3f, 2f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.375f, KeyframeAnimations.posVec(0f, 3f, 2f), AnimationChannel.Interpolations.CATMULLROM),
                new Keyframe(0.625f, KeyframeAnimations.posVec(0f, 0f, 0f), AnimationChannel.Interpolations.CATMULLROM)
            ))
        .build();

}
