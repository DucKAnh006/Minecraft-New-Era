package com.newera.docs.entity.animation.animal.wolf;

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

public class WolfBabyAnimation {
	public static final AnimationDefinition WOLF_BABY_IDLE = AnimationDefinition.Builder.withLength(4F).looping()
		.addAnimation("tail", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(32.5F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("left_ear", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.5F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.625F, KeyframeAnimations.degreeVec(0F, 25F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.75F, KeyframeAnimations.degreeVec(0F, -20F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.875F, KeyframeAnimations.degreeVec(0F, 25F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(4F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("right_ear", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.5F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.625F, KeyframeAnimations.degreeVec(0F, -25F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.75F, KeyframeAnimations.degreeVec(0F, 20F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2.875F, KeyframeAnimations.degreeVec(0F, -25F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(3F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(4F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		)).build();

	public static final AnimationDefinition WOLF_BABY_WALK = AnimationDefinition.Builder.withLength(2F).looping()
		.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(10F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.5F, KeyframeAnimations.degreeVec(10F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1F, KeyframeAnimations.degreeVec(0F, 0F, -5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2F, KeyframeAnimations.degreeVec(0F, 0F, 5F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("right_hind_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(30F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1F, KeyframeAnimations.degreeVec(-30F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2F, KeyframeAnimations.degreeVec(30F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("right_hind_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.posVec(0F, 1.5F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("left_hind_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(-30F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1F, KeyframeAnimations.degreeVec(30F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2F, KeyframeAnimations.degreeVec(-30F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("left_hind_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.5F, KeyframeAnimations.posVec(0F, 1.5F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("right_front_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(-30F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1F, KeyframeAnimations.degreeVec(30F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2F, KeyframeAnimations.degreeVec(-30F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("right_front_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.5F, KeyframeAnimations.posVec(0F, 1.5F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("left_front_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(30F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1F, KeyframeAnimations.degreeVec(-30F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2F, KeyframeAnimations.degreeVec(30F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("left_front_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.posVec(0F, 1.5F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("tail", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, -12.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1F, KeyframeAnimations.degreeVec(0F, 0F, 12.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2F, KeyframeAnimations.degreeVec(0F, 0F, -12.5F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("right_ear", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(15F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.5F, KeyframeAnimations.degreeVec(15F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("left_ear", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(15F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.5F, KeyframeAnimations.degreeVec(15F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(2F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		)).build();

	public static final AnimationDefinition WOLF_BABY_SIT = AnimationDefinition.Builder.withLength(1F)
		.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.3333F, KeyframeAnimations.degreeVec(27.5F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.6667F, KeyframeAnimations.degreeVec(40F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1F, KeyframeAnimations.degreeVec(35F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.6667F, KeyframeAnimations.degreeVec(-20F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1F, KeyframeAnimations.degreeVec(-35F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.6667F, KeyframeAnimations.posVec(0F, -2F, -1F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1F, KeyframeAnimations.posVec(0F, -1.5F, -1F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("right_hind_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5417F, KeyframeAnimations.degreeVec(-90F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1F, KeyframeAnimations.degreeVec(-90F, 10F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("right_hind_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5417F, KeyframeAnimations.posVec(0F, -2F, -1F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1F, KeyframeAnimations.posVec(-0.5F, -2F, -2F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("left_hind_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5417F, KeyframeAnimations.degreeVec(-90F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1F, KeyframeAnimations.degreeVec(-90F, -10F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("left_hind_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5417F, KeyframeAnimations.posVec(0F, -2F, -1F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1F, KeyframeAnimations.posVec(0.5F, -2F, -2F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("right_front_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.25F, KeyframeAnimations.degreeVec(-45F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(-50F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("right_front_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.25F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.posVec(0F, -1F, -1F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1F, KeyframeAnimations.posVec(0F, 0F, -1F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("left_front_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.25F, KeyframeAnimations.degreeVec(-45F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(-50F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("left_front_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.25F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.posVec(0F, -1F, -1F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1F, KeyframeAnimations.posVec(0F, 0F, -1F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("tail", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(32.5F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1F, KeyframeAnimations.degreeVec(87.5F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		)).build();

	public static final AnimationDefinition WOLF_BABY_SITTING = AnimationDefinition.Builder.withLength(0F).looping()
		.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(35F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0.0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-35F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0.0F, KeyframeAnimations.posVec(0F, -1.5F, -1F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("right_hind_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-90F, 10F, 0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("right_hind_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0.0F, KeyframeAnimations.posVec(-0.5F, -2F, -2F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("left_hind_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-90F, -10F, 0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("left_hind_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.5F, -2F, -2F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("right_front_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("right_front_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0.0F, KeyframeAnimations.posVec(0F, 0F, -1F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("left_front_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("left_front_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0.0F, KeyframeAnimations.posVec(0F, 0F, -1F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("tail", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(87.5F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)
		)).build();

	public static final AnimationDefinition WOLF_BABY_STAND_UP = AnimationDefinition.Builder.withLength(1F)
		.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(35F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.3333F, KeyframeAnimations.degreeVec(40F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.6667F, KeyframeAnimations.degreeVec(27.5F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(-35F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.3333F, KeyframeAnimations.degreeVec(-20F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, -1.5F, -1F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.3333F, KeyframeAnimations.posVec(0F, -2F, -1F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("right_hind_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(-90F, 10F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(-90F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("right_hind_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(-0.5F, -2F, -2F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.posVec(0F, -2F, -1F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("left_hind_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(-90F, -10F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(-90F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("left_hind_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0.5F, -2F, -2F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.posVec(0F, -2F, -1F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("right_front_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(-50F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.75F, KeyframeAnimations.degreeVec(-45F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("right_front_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, -1F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.posVec(0F, -1F, -1F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.75F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("left_front_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(-50F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.75F, KeyframeAnimations.degreeVec(-45F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("left_front_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, -1F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.posVec(0F, -1F, -1F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.75F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("tail", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(87.5F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(32.5F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		)).build();

	public static final AnimationDefinition WOLF_BABY_SLEEP = AnimationDefinition.Builder.withLength(0.5F)
		.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(-10F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(35F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.posVec(0F, -1.5F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("right_hind_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(0F, 15F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("right_hind_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.posVec(0F, 0F, 1F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("left_hind_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(0F, -15F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("left_hind_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.posVec(0F, 0F, 1F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("right_front_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(-90F, 20F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("right_front_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.posVec(-1F, -2F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("left_front_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(-90F, -20F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("left_front_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.posVec(1F, -2F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("tail", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(-47.5F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		)).build();

	public static final AnimationDefinition WOLF_BABY_SLEEPING = AnimationDefinition.Builder.withLength(0F).looping()
		.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-10F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(35F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0.0F, KeyframeAnimations.posVec(0F, -1.5F, 0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("right_hind_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0F, 15F, 0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("right_hind_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0.0F, KeyframeAnimations.posVec(0F, 0F, 1F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("left_hind_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0F, -15F, 0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("left_hind_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0.0F, KeyframeAnimations.posVec(0F, 0F, 1F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("right_front_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-90F, 20F, 0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("right_front_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0.0F, KeyframeAnimations.posVec(-1F, -2F, 0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("left_front_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-90F, -20F, 0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("left_front_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0.0F, KeyframeAnimations.posVec(1F, -2F, 0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("tail", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(-47.5F, 0F, 0F), AnimationChannel.Interpolations.LINEAR)
		)).build();

	public static final AnimationDefinition WOLF_BABY_WAKE_UP = AnimationDefinition.Builder.withLength(0.5F)
		.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(-10F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(35F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("body", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, -1.5F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("right_hind_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 15F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("right_hind_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 1F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("left_hind_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, -15F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("left_hind_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(0F, 0F, 1F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("right_front_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(-90F, 20F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("right_front_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(-1F, -2F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("left_front_leg", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(-90F, -20F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("left_front_leg", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0F, KeyframeAnimations.posVec(1F, -2F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.posVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("tail", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(-47.5F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		)).build();

	public static final AnimationDefinition WOLF_BABY_SHAKING = AnimationDefinition.Builder.withLength(1.5F)
		.addAnimation("head", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.1667F, KeyframeAnimations.degreeVec(0F, 0F, -30F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.3333F, KeyframeAnimations.degreeVec(0F, 0F, 30F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(0F, 0F, -30F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.6667F, KeyframeAnimations.degreeVec(0F, 0F, 30F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.8333F, KeyframeAnimations.degreeVec(0F, 0F, -30F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1F, KeyframeAnimations.degreeVec(0F, 0F, 30F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.1667F, KeyframeAnimations.degreeVec(0F, 0F, -30F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.3333F, KeyframeAnimations.degreeVec(0F, 0F, 30F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.5F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("tail", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.0833F, KeyframeAnimations.degreeVec(57.5F, 0F, -17.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.25F, KeyframeAnimations.degreeVec(57.5F, 0F, 10F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.4167F, KeyframeAnimations.degreeVec(57.5F, 0F, -17.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5833F, KeyframeAnimations.degreeVec(57.5F, 0F, 10F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.75F, KeyframeAnimations.degreeVec(57.5F, 0F, -17.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.9167F, KeyframeAnimations.degreeVec(57.5F, 0F, 10F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.0833F, KeyframeAnimations.degreeVec(57.5F, 0F, -17.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.25F, KeyframeAnimations.degreeVec(57.5F, 0F, 10F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.4167F, KeyframeAnimations.degreeVec(57.5F, 0F, -17.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.5F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("left_ear", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.0833F, KeyframeAnimations.degreeVec(0F, 0F, 20F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.25F, KeyframeAnimations.degreeVec(0F, 0F, -20F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.4167F, KeyframeAnimations.degreeVec(0F, 0F, 20F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5833F, KeyframeAnimations.degreeVec(0F, 0F, -20F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.75F, KeyframeAnimations.degreeVec(0F, 0F, 20F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.9167F, KeyframeAnimations.degreeVec(0F, 0F, -20F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.0833F, KeyframeAnimations.degreeVec(0F, 0F, 20F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.25F, KeyframeAnimations.degreeVec(0F, 0F, -20F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.4167F, KeyframeAnimations.degreeVec(0F, 0F, 20F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.5F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("right_ear", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.0833F, KeyframeAnimations.degreeVec(0F, 0F, 20F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.25F, KeyframeAnimations.degreeVec(0F, 0F, -20F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.4167F, KeyframeAnimations.degreeVec(0F, 0F, 20F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5833F, KeyframeAnimations.degreeVec(0F, 0F, -20F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.75F, KeyframeAnimations.degreeVec(0F, 0F, 20F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.9167F, KeyframeAnimations.degreeVec(0F, 0F, -20F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.0833F, KeyframeAnimations.degreeVec(0F, 0F, 20F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.25F, KeyframeAnimations.degreeVec(0F, 0F, -20F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.4167F, KeyframeAnimations.degreeVec(0F, 0F, 20F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.5F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("centroid", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.0833F, KeyframeAnimations.degreeVec(0F, 0F, 10F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.25F, KeyframeAnimations.degreeVec(0F, 0F, -10F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.4167F, KeyframeAnimations.degreeVec(0F, 0F, 10F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5833F, KeyframeAnimations.degreeVec(0F, 0F, -10F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.75F, KeyframeAnimations.degreeVec(0F, 0F, 10F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.9167F, KeyframeAnimations.degreeVec(0F, 0F, -10F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.0833F, KeyframeAnimations.degreeVec(0F, 0F, 10F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.25F, KeyframeAnimations.degreeVec(0F, 0F, -10F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.4167F, KeyframeAnimations.degreeVec(0F, 0F, 10F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.5F, KeyframeAnimations.degreeVec(0F, 0F, 0F), AnimationChannel.Interpolations.CATMULLROM)
		)).build();

	public static final AnimationDefinition WOLF_BABY_HOWLING = AnimationDefinition.Builder.withLength(0F).looping().build();

	public static final AnimationDefinition WOLF_BABY_SIT_HOWLING = AnimationDefinition.Builder.withLength(0F).looping().build();

}
