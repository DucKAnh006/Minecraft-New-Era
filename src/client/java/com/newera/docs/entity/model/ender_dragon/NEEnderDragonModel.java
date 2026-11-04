package com.newera.docs.entity.model.ender_dragon;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.animation.KeyframeAnimation;
import com.newera.docs.entity.state.NEEnderDragonRenderState;
import com.newera.docs.entity.animation.ender_dragon.EnderDragonAnimation;

public class NEEnderDragonModel extends EntityModel<NEEnderDragonRenderState> {
	private final ModelPart root;
	private final ModelPart body;
	private final ModelPart neck;
	private final ModelPart neck_tip;
	private final ModelPart neck_tip_spike;
	private final ModelPart head;
	private final ModelPart jaw;
	private final ModelPart horn;
	private final ModelPart left_big_horn;
	private final ModelPart right_big_horn;
	private final ModelPart left_medium_horn;
	private final ModelPart right_medium_horn;
	private final ModelPart left_small_horn;
	private final ModelPart right_small_horn;
	private final ModelPart right_tiny_horn;
	private final ModelPart left_tiny_horn;
	private final ModelPart centroid;
	private final ModelPart upper_centroid;
	private final ModelPart lower_centroid;
	private final ModelPart lower_centroid_spike;
	private final ModelPart tail;
	private final ModelPart tail_spike;
	private final ModelPart tail_mid;
	private final ModelPart tail_tip;
	private final ModelPart wings;
	private final ModelPart left_wing;
	private final ModelPart left_form;
	private final ModelPart left_tip;
	private final ModelPart right_wing;
	private final ModelPart right_form;
	private final ModelPart right_tip;
	private final ModelPart legs;
	private final ModelPart left_front_leg;
	private final ModelPart left_front_tip;
	private final ModelPart left_front_foot;
	private final ModelPart left_front_claw;
	private final ModelPart right_front_leg;
	private final ModelPart right_front_tip;
	private final ModelPart right_front_foot;
	private final ModelPart right_front_claw;
	private final ModelPart left_hind_leg;
	private final ModelPart left_hind_tip;
	private final ModelPart left_hind_foot;
	private final ModelPart left_hind_claw;
	private final ModelPart right_hind_leg;
	private final ModelPart right_hind_tip;
	private final ModelPart right_hind_foot;
	private final ModelPart right_hind_claw;
	    private final KeyframeAnimation walkAnimation;
    private final KeyframeAnimation onGroundPoseAnimation;
    private final KeyframeAnimation onAirPoseAnimation;
    private final KeyframeAnimation flyAnimation;
    private final KeyframeAnimation hoverPoseAnimation;
    private final KeyframeAnimation biteAnimation;
    private final KeyframeAnimation decelerateAnimation;
    private final KeyframeAnimation landingAnimation;
    private final KeyframeAnimation roarAnimation;
    private final KeyframeAnimation pounceLeftAnimation;
    private final KeyframeAnimation pounceRightAnimation;
    private final KeyframeAnimation stompAnimation;
    private final KeyframeAnimation breathCloseAnimation;
    private final KeyframeAnimation breathAnimation;
    private final KeyframeAnimation takeoffAnimation;
    private final KeyframeAnimation fallingAnimation;
    private final KeyframeAnimation accelerateAnimation;
    private final KeyframeAnimation roarHoverAnimation;
    private final KeyframeAnimation hoverBreathAnimation;
    private final KeyframeAnimation flyBreathAnimation;
    private final KeyframeAnimation flyLongBreathAnimation;
    private final KeyframeAnimation teleportAnimation;
    private final KeyframeAnimation roarCurseAnimation;
    private final KeyframeAnimation deathAnimation;


	public NEEnderDragonModel(ModelPart root) {
		super(root);
		this.root = root.getChild("root");
		
		this.body = this.root.getChild("body");
		this.neck = this.body.getChild("neck");
		this.neck_tip = this.neck.getChild("neck_tip");
		this.neck_tip_spike = this.neck_tip.getChild("neck_tip_spike");
		this.head = this.neck_tip.getChild("head");
		this.jaw = this.head.getChild("jaw");
		this.horn = this.head.getChild("horn");
		this.left_big_horn = this.horn.getChild("left_big_horn");
		this.right_big_horn = this.horn.getChild("right_big_horn");
		this.left_medium_horn = this.horn.getChild("left_medium_horn");
		this.right_medium_horn = this.horn.getChild("right_medium_horn");
		this.left_small_horn = this.horn.getChild("left_small_horn");
		this.right_small_horn = this.horn.getChild("right_small_horn");
		this.right_tiny_horn = this.horn.getChild("right_tiny_horn");
		this.left_tiny_horn = this.horn.getChild("left_tiny_horn");
		this.centroid = this.body.getChild("centroid");
		this.upper_centroid = this.centroid.getChild("upper_centroid");
		this.lower_centroid = this.centroid.getChild("lower_centroid");
		this.lower_centroid_spike = this.lower_centroid.getChild("lower_centroid_spike");
		this.tail = this.body.getChild("tail");
		this.tail_spike = this.tail.getChild("tail_spike");
		this.tail_mid = this.tail.getChild("tail_mid");
		this.tail_tip = this.tail_mid.getChild("tail_tip");
		this.wings = this.body.getChild("wings");
		this.left_wing = this.wings.getChild("left_wing");
		this.left_form = this.left_wing.getChild("left_form");
		this.left_tip = this.left_form.getChild("left_tip");
		this.right_wing = this.wings.getChild("right_wing");
		this.right_form = this.right_wing.getChild("right_form");
		this.right_tip = this.right_form.getChild("right_tip");
		this.legs = this.root.getChild("legs");
		this.left_front_leg = this.legs.getChild("left_front_leg");
		this.left_front_tip = this.left_front_leg.getChild("left_front_tip");
		this.left_front_foot = this.left_front_tip.getChild("left_front_foot");
		this.left_front_claw = this.left_front_foot.getChild("left_front_claw");
		this.right_front_leg = this.legs.getChild("right_front_leg");
		this.right_front_tip = this.right_front_leg.getChild("right_front_tip");
		this.right_front_foot = this.right_front_tip.getChild("right_front_foot");
		this.right_front_claw = this.right_front_foot.getChild("right_front_claw");
		this.left_hind_leg = this.legs.getChild("left_hind_leg");
		this.left_hind_tip = this.left_hind_leg.getChild("left_hind_tip");
		this.left_hind_foot = this.left_hind_tip.getChild("left_hind_foot");
		this.left_hind_claw = this.left_hind_foot.getChild("left_hind_claw");
		this.right_hind_leg = this.legs.getChild("right_hind_leg");
		this.right_hind_tip = this.right_hind_leg.getChild("right_hind_tip");
		this.right_hind_foot = this.right_hind_tip.getChild("right_hind_foot");
		this.right_hind_claw = this.right_hind_foot.getChild("right_hind_claw");

        this.walkAnimation = EnderDragonAnimation.DRAGON_WALK.bake(this.root);
        this.onGroundPoseAnimation = EnderDragonAnimation.DRAGON_POSE_ON_GROUND.bake(this.root);
        this.onAirPoseAnimation = EnderDragonAnimation.DRAGON_POSE_ON_AIR.bake(this.root);
        this.flyAnimation = EnderDragonAnimation.DRAGON_FLY.bake(this.root);
        this.hoverPoseAnimation = EnderDragonAnimation.DRAGON_POSE_HOVER.bake(this.root);
        this.biteAnimation = EnderDragonAnimation.DRAGON_BITE.bake(this.root);
        this.decelerateAnimation = EnderDragonAnimation.DRAGON_ACTION_DECELERATE.bake(this.root);
        this.landingAnimation = EnderDragonAnimation.DRAGON_ACTION_LANDING.bake(this.root);
        this.roarAnimation = EnderDragonAnimation.DRAGON_ROAR.bake(this.root);
        this.pounceLeftAnimation = EnderDragonAnimation.DRAGON_POUNCE_LEFT.bake(this.root);
        this.pounceRightAnimation = EnderDragonAnimation.DRAGON_POUNCE_RIGHT.bake(this.root);
        this.stompAnimation = EnderDragonAnimation.DRAGON_STOMP.bake(this.root);
        this.breathCloseAnimation = EnderDragonAnimation.DRAGON_BREATH_CLOSE.bake(this.root);
        this.breathAnimation = EnderDragonAnimation.DRAGON_BREATH.bake(this.root);
        this.takeoffAnimation = EnderDragonAnimation.DRAGON_ACTION_TAKEOFF.bake(this.root);
        this.fallingAnimation = EnderDragonAnimation.DRAGON_ACTION_FALLING.bake(this.root);
        this.accelerateAnimation = EnderDragonAnimation.DRAGON_ACTION_ACCELERATE.bake(this.root);
        this.roarHoverAnimation = EnderDragonAnimation.DRAGON_ROAR_HOVER.bake(this.root);
        this.hoverBreathAnimation = EnderDragonAnimation.DRAGON_HOVER_BREATH.bake(this.root);
        this.flyBreathAnimation = EnderDragonAnimation.DRAGON_FLY_BREATH.bake(this.root);
        this.flyLongBreathAnimation = EnderDragonAnimation.DRAGON_FLY_LONG_BREATH.bake(this.root);
        this.teleportAnimation = EnderDragonAnimation.DRAGON_TELEPORT.bake(this.root);
        this.roarCurseAnimation = EnderDragonAnimation.DRAGON_ROAR_CURSE.bake(this.root);
        this.deathAnimation = EnderDragonAnimation.DRAGON_DEATH.bake(this.root);

	}

    @Override
    public void setupAnim(final NEEnderDragonRenderState state) {
        super.setupAnim(state);
        this.root.resetPose();

        // 1. Khi chết: chỉ chơi animation chết
        if (state.deathAnimationState.isStarted()) {
            this.deathAnimation.apply(state.deathAnimationState, state.ageInTicks);
            return;
        }

        // 2. Transitional Full-Body Actions:
        // Takeoff, Falling, Landing, Accelerate, Decelerate
        // Base poses (onGround, hover, onAir) MUST NOT be applied simultaneously to prevent bone transform addition and mesh distortion!
        boolean isTransitionalAction = state.takeoffAnimationState.isStarted()
                || state.fallingAnimationState.isStarted()
                || state.landingAnimationState.isStarted()
                || state.accelerateAnimationState.isStarted()
                || state.decelerateAnimationState.isStarted();

        // Base Poses (Tư thế cơ sở theo trạng thái - chỉ áp dụng khi KHÔNG trong action chuyển thế)
        if (!isTransitionalAction) {
            boolean basePoseApplied = false;
            if (state.onGroundPoseAnimationState.isStarted()) {
                this.onGroundPoseAnimation.apply(state.onGroundPoseAnimationState, state.ageInTicks);
                basePoseApplied = true;
            } else if (state.hoverPoseAnimationState.isStarted()) {
                this.hoverPoseAnimation.apply(state.hoverPoseAnimationState, state.ageInTicks);
                basePoseApplied = true;
            } else if (state.onAirPoseAnimationState.isStarted()) {
                this.onAirPoseAnimation.apply(state.onAirPoseAnimationState, state.ageInTicks);
                basePoseApplied = true;
            }

            // Fallback safety net: never allow the dragon to render without an active pose!
            if (!basePoseApplied) {
                if (state.basePoseType == 2) {
                    this.onGroundPoseAnimation.apply(state.onGroundPoseAnimationState, state.ageInTicks);
                } else if (state.basePoseType == 1) {
                    this.hoverPoseAnimation.apply(state.hoverPoseAnimationState, state.ageInTicks);
                } else {
                    this.onAirPoseAnimation.apply(state.onAirPoseAnimationState, state.ageInTicks);
                }
            }
        }

        // 3. Locomotion (Đi bộ hoặc Đập cánh bay)
        if (state.walkAnimationState.isStarted()) {
            this.walkAnimation.apply(state.walkAnimationState, state.ageInTicks);
        }

        // Suppress fly flapping ONLY during actions that define their own complete wing poses (Takeoff, Falling dive, Landing crash)
        boolean suppressFly = state.takeoffAnimationState.isStarted()
                || state.fallingAnimationState.isStarted()
                || state.landingAnimationState.isStarted();

        if (state.flyAnimationState.isStarted() && !suppressFly) {
            this.flyAnimation.apply(state.flyAnimationState, state.ageInTicks);
        }

        // 4. Actions & Attacks (Áp dụng theo đúng các state được bật/tắt trong triggerClientAction)
        if (state.biteAnimationState.isStarted()) {
            this.biteAnimation.apply(state.biteAnimationState, state.ageInTicks);
        }
        if (state.roarAnimationState.isStarted()) {
            this.roarAnimation.apply(state.roarAnimationState, state.ageInTicks);
        }
        if (state.pounceLeftAnimationState.isStarted()) {
            this.pounceLeftAnimation.apply(state.pounceLeftAnimationState, state.ageInTicks);
        }
        if (state.pounceRightAnimationState.isStarted()) {
            this.pounceRightAnimation.apply(state.pounceRightAnimationState, state.ageInTicks);
        }
        if (state.stompAnimationState.isStarted()) {
            this.stompAnimation.apply(state.stompAnimationState, state.ageInTicks);
        }
        if (state.breathCloseAnimationState.isStarted()) {
            this.breathCloseAnimation.apply(state.breathCloseAnimationState, state.ageInTicks);
        }
        if (state.breathAnimationState.isStarted()) {
            this.breathAnimation.apply(state.breathAnimationState, state.ageInTicks);
        }
        if (state.takeoffAnimationState.isStarted()) {
            this.takeoffAnimation.apply(state.takeoffAnimationState, state.ageInTicks);
        }
        if (state.fallingAnimationState.isStarted()) {
            this.fallingAnimation.apply(state.fallingAnimationState, state.ageInTicks);
        }
        if (state.landingAnimationState.isStarted()) {
            this.landingAnimation.apply(state.landingAnimationState, state.ageInTicks);
        }
        if (state.decelerateAnimationState.isStarted()) {
            this.decelerateAnimation.apply(state.decelerateAnimationState, state.ageInTicks);
        }
        if (state.accelerateAnimationState.isStarted()) {
            this.accelerateAnimation.apply(state.accelerateAnimationState, state.ageInTicks);
        }
        if (state.roarHoverAnimationState.isStarted()) {
            this.roarHoverAnimation.apply(state.roarHoverAnimationState, state.ageInTicks);
        }
        if (state.hoverBreathAnimationState.isStarted()) {
            this.hoverBreathAnimation.apply(state.hoverBreathAnimationState, state.ageInTicks);
        }
        if (state.flyBreathAnimationState.isStarted()) {
            this.flyBreathAnimation.apply(state.flyBreathAnimationState, state.ageInTicks);
        }
        if (state.flyLongBreathAnimationState.isStarted()) {
            this.flyLongBreathAnimation.apply(state.flyLongBreathAnimationState, state.ageInTicks);
        }
        if (state.teleportAnimationState.isStarted()) {
            this.teleportAnimation.apply(state.teleportAnimationState, state.ageInTicks);
        }
        if (state.roarCurseAnimationState.isStarted()) {
            this.roarCurseAnimation.apply(state.roarCurseAnimationState, state.ageInTicks);
        }

        // 5. Head look & Flight kinematics (Turning & Pitch)
        if (!isTransitionalAction && !state.roarCurseAnimationState.isStarted() && !state.roarAnimationState.isStarted() && !state.roarHoverAnimationState.isStarted()) {
            applyHeadLook(state);
        }
        applyFlightKinematics(state);
    }

    private void applyHeadLook(final NEEnderDragonRenderState state) {
        final float DEG_TO_RAD = (float)(Math.PI / 180.0);
        if (Math.abs(state.lookYaw) > 0.01F || Math.abs(state.lookPitch) > 0.01F) {
            float yawRad = state.lookYaw * DEG_TO_RAD;
            float pitchRad = state.lookPitch * DEG_TO_RAD;

            this.neck.yRot += yawRad * (12.0F / 55.0F);
            this.neck.xRot += pitchRad * (10.0F / 35.0F);

            this.neck_tip.yRot += yawRad * (18.0F / 55.0F);
            this.neck_tip.xRot += pitchRad * (12.0F / 35.0F);

            this.head.yRot += yawRad * (25.0F / 55.0F);
            this.head.xRot += pitchRad * (13.0F / 35.0F);
        }
    }

    private void applyFlightKinematics(final NEEnderDragonRenderState state) {
        final float DEG_TO_RAD = (float)(Math.PI / 180.0);

        // Dynamic Pitch (Climb & Dive vertical inclination)
        if (Math.abs(state.flightPitch) > 0.01F) {
            this.root.xRot += state.flightPitch * DEG_TO_RAD;
        }

        // Turning Kinematics parameterized from "test" animation
        float vx = state.flightTurnX;
        if (Math.abs(vx) > 0.01F) {
            // root: rotation: [0, 0, -clamp(v.x, -30, 30)]
            this.root.zRot += -net.minecraft.util.Mth.clamp(vx, -30.0F, 30.0F) * DEG_TO_RAD;

            // neck: rotation: [90, clamp(v.x, -40, 40), clamp(v.x, -40, 40)]
            this.neck.yRot += net.minecraft.util.Mth.clamp(vx, -40.0F, 40.0F) * DEG_TO_RAD;
            this.neck.zRot += net.minecraft.util.Mth.clamp(vx, -40.0F, 40.0F) * DEG_TO_RAD;

            // neck_tip: rotation: [0, -clamp(v.x, -40, 40), -clamp(v.x, -40, 40)]
            this.neck_tip.yRot += -net.minecraft.util.Mth.clamp(vx, -40.0F, 40.0F) * DEG_TO_RAD;
            this.neck_tip.zRot += -net.minecraft.util.Mth.clamp(vx, -40.0F, 40.0F) * DEG_TO_RAD;

            // head: rotation: [-90, clamp(v.x, -40, 40), -clamp(v.x, -40, 40)]
            this.head.yRot += net.minecraft.util.Mth.clamp(vx, -40.0F, 40.0F) * DEG_TO_RAD;
            this.head.zRot += -net.minecraft.util.Mth.clamp(vx, -40.0F, 40.0F) * DEG_TO_RAD;

            // tail: rotation: [0, -clamp(v.x, -20, 20), -clamp(v.x, -20, 20)]
            this.tail.yRot += -net.minecraft.util.Mth.clamp(vx, -20.0F, 20.0F) * DEG_TO_RAD;
            this.tail.zRot += -net.minecraft.util.Mth.clamp(vx, -20.0F, 20.0F) * DEG_TO_RAD;

            // tail_mid: rotation: [0, -clamp(v.x, -15, 15), -clamp(v.x, -15, 15)]
            this.tail_mid.yRot += -net.minecraft.util.Mth.clamp(vx, -15.0F, 15.0F) * DEG_TO_RAD;
            this.tail_mid.zRot += -net.minecraft.util.Mth.clamp(vx, -15.0F, 15.0F) * DEG_TO_RAD;

            // tail_tip: rotation: [0, -clamp(v.x, -10, 10), -clamp(v.x, -10, 10)]
            this.tail_tip.yRot += -net.minecraft.util.Mth.clamp(vx, -10.0F, 10.0F) * DEG_TO_RAD;
            this.tail_tip.zRot += -net.minecraft.util.Mth.clamp(vx, -10.0F, 10.0F) * DEG_TO_RAD;

            // wings: rotation: [0, 0, clamp(v.x, -30, 30)]
            this.wings.zRot += net.minecraft.util.Mth.clamp(vx, -30.0F, 30.0F) * DEG_TO_RAD;
        }
    }

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition PartDefinition = meshdefinition.getRoot();
		PartDefinition root = PartDefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -18.0F));

		PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, -31.0F, 6.0F));

		PartDefinition neck = body.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(249, 129).mirror().addBox(-8.5F, -36.0F, -19.0F, 17.0F, 41.0F, 21.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 2.0F, -38.0F));

		PartDefinition neck_tip = neck.addOrReplaceChild("neck_tip", CubeListBuilder.create().texOffs(0, 259).addBox(-6.5F, -34.0F, -11.0F, 13.0F, 40.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -36.0F, 3.0F));

		PartDefinition neck_tip_spike = neck_tip.addOrReplaceChild("neck_tip_spike", CubeListBuilder.create().texOffs(95, 46).addBox(-1.5F, -12.0F, -6.0F, 3.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(137, 43).addBox(-1.5F, -14.0F, 1.0F, 3.0F, 10.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(93, 22).addBox(-1.5F, -13.0F, 8.0F, 3.0F, 9.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(96, 0).addBox(-1.5F, -11.0F, 15.0F, 3.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -23.0F, 2.0F, -1.5708F, 0.0F, 0.0F));

		PartDefinition head = neck_tip.addOrReplaceChild("head", CubeListBuilder.create().texOffs(40, 21).addBox(-7.5F, -7.0F, -16.0F, 17.0F, 8.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(0, 29).addBox(-3.5F, -4.01F, -27.0F, 9.0F, 5.0F, 11.0F, new CubeDeformation(0.0F))
		.texOffs(0, 61).addBox(-2.5F, 0.99F, -26.0F, 7.0F, 4.0F, 11.0F, new CubeDeformation(0.0F))
		.texOffs(34, 21).addBox(3.5F, -7.0F, -18.0F, 8.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(52, 0).addBox(9.5F, -7.0F, -16.0F, 2.0F, 3.0F, 11.0F, new CubeDeformation(0.0F))
		.texOffs(34, 21).mirror().addBox(-9.5F, -7.0F, -18.0F, 8.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 6).addBox(0.0F, -9.0F, -10.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(0, 6).addBox(0.0F, -9.0F, -4.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(40, 46).addBox(-7.5F, 1.0F, -9.0F, 17.0F, 6.0F, 9.0F, new CubeDeformation(0.0F))
		.texOffs(52, 0).mirror().addBox(-9.5F, -7.0F, -16.0F, 2.0F, 3.0F, 11.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-1.0F, -28.0F, -4.0F));

		PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(0, 45).addBox(-3.5F, 0.0F, -19.0F, 9.0F, 5.0F, 11.0F, new CubeDeformation(0.0F))
		.texOffs(0, 76).addBox(-2.5F, -4.0F, -18.0F, 7.0F, 4.0F, 11.0F, new CubeDeformation(0.0F))
		.texOffs(0, 18).addBox(-3.5F, 5.0F, -19.0F, 9.0F, 3.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(24, -7).mirror().addBox(7.5F, -7.0F, -7.0F, 0.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(24, -7).addBox(-5.5F, -7.0F, -7.0F, 0.0F, 7.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(36, 63).addBox(-7.5F, 0.0F, -8.0F, 17.0F, 6.0F, 7.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 1.0F, -8.0F));

		PartDefinition horn = head.addOrReplaceChild("horn", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, -6.0F));

		PartDefinition left_big_horn = horn.addOrReplaceChild("left_big_horn", CubeListBuilder.create().texOffs(26, 0).addBox(6.5F, -9.0F, 1.0F, 5.0F, 5.0F, 16.0F, new CubeDeformation(0.0F))
		.texOffs(68, 0).addBox(6.5F, -14.0F, 12.0F, 5.0F, 5.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 0.0F, 0.0F));

		PartDefinition right_big_horn = horn.addOrReplaceChild("right_big_horn", CubeListBuilder.create().texOffs(26, 0).mirror().addBox(-10.5F, -9.0F, 1.0F, 5.0F, 5.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(68, 0).mirror().addBox(-10.5F, -14.0F, 12.0F, 5.0F, 5.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition left_medium_horn = horn.addOrReplaceChild("left_medium_horn", CubeListBuilder.create().texOffs(16, 7).addBox(-0.5F, -2.0F, -1.0F, 2.0F, 8.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(12, 0).addBox(-0.5F, 3.0F, 2.0F, 2.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, 3.0F, 7.0F));

		PartDefinition right_medium_horn = horn.addOrReplaceChild("right_medium_horn", CubeListBuilder.create().texOffs(12, 0).mirror().addBox(-0.5F, 3.0F, 2.0F, 2.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(16, 7).mirror().addBox(-0.5F, -2.0F, -1.0F, 2.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-7.0F, 3.0F, 7.0F));

		PartDefinition left_small_horn = horn.addOrReplaceChild("left_small_horn", CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, 8.0F, -1.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(11.0F, 0.0F, 0.0F));

		PartDefinition right_small_horn = horn.addOrReplaceChild("right_small_horn", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(2.5F, 8.0F, -1.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-10.0F, 0.0F, 0.0F));

		PartDefinition right_tiny_horn = horn.addOrReplaceChild("right_tiny_horn", CubeListBuilder.create().texOffs(9, 0).mirror().addBox(-7.5F, 8.0F, -7.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(34, 15).mirror().addBox(-9.5F, -9.0F, -12.0F, 0.0F, 3.0F, 11.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition left_tiny_horn = horn.addOrReplaceChild("left_tiny_horn", CubeListBuilder.create().texOffs(9, 0).addBox(7.5F, 8.0F, -7.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(34, 15).addBox(10.5F, -9.0F, -12.0F, 0.0F, 3.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 0.0F, 0.0F));

		PartDefinition centroid = body.addOrReplaceChild("centroid", CubeListBuilder.create(), PartPose.offset(0.0F, -11.0F, -23.0F));

		PartDefinition upper_centroid = centroid.addOrReplaceChild("upper_centroid", CubeListBuilder.create().texOffs(0, 157).addBox(-17.5F, -18.0F, -38.0F, 35.0F, 43.0F, 51.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.0F, 22.0F));

		PartDefinition lower_centroid = centroid.addOrReplaceChild("lower_centroid", CubeListBuilder.create().texOffs(0, 77).addBox(-14.5F, -15.0F, -7.0F, 29.0F, 31.0F, 47.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 7.0F, 41.0F));

		PartDefinition lower_centroid_spike = lower_centroid.addOrReplaceChild("lower_centroid_spike", CubeListBuilder.create().texOffs(107, 106).addBox(-2.0F, -18.0F, 25.0F, 4.0F, 9.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(112, 20).addBox(-2.0F, -21.0F, 38.0F, 4.0F, 12.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(112, 0).addBox(-2.0F, -22.0F, 51.0F, 4.0F, 13.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.0F, -24.0F));

		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(265, 129).mirror().addBox(-10.5F, -9.0F, -1.0F, 21.0F, 25.0F, 65.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, -8.0F, 58.0F));

		PartDefinition tail_spike = tail.addOrReplaceChild("tail_spike", CubeListBuilder.create().texOffs(112, 0).addBox(-2.5F, -22.0F, 0.0F, 4.0F, 13.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(134, 0).addBox(-2.5F, -19.0F, 13.0F, 4.0F, 10.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(107, 106).addBox(-2.5F, -18.0F, 26.0F, 4.0F, 9.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(106, 71).addBox(-2.5F, -16.0F, 39.0F, 4.0F, 7.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(135, 26).addBox(-2.5F, -15.0F, 52.0F, 4.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 0.0F, 0.0F));

		PartDefinition tail_mid = tail.addOrReplaceChild("tail_mid", CubeListBuilder.create().texOffs(0, 254).addBox(-6.5F, -5.0F, -3.0F, 13.0F, 19.0F, 70.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 62.0F));

		PartDefinition tail_tip = tail_mid.addOrReplaceChild("tail_tip", CubeListBuilder.create().texOffs(260, 221).addBox(-4.5F, -5.0F, -2.0F, 9.0F, 12.0F, 87.0F, new CubeDeformation(0.0F))
		.texOffs(59, 0).addBox(-66.5F, 0.0F, 1.0F, 133.0F, 0.0F, 128.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 3.0F, 65.0F));

		PartDefinition wings = body.addOrReplaceChild("wings", CubeListBuilder.create(), PartPose.offset(0.0F, -17.0F, -19.0F));

		PartDefinition left_wing = wings.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(114, 270).mirror().addBox(-3.5F, -8.0F, -8.0F, 70.0F, 12.0F, 17.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(287, 224).mirror().addBox(-3.5F, -2.0F, 1.0F, 92.0F, 0.0F, 81.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(17.0F, 1.0F, -6.0F));

		PartDefinition left_form = left_wing.addOrReplaceChild("left_form", CubeListBuilder.create(), PartPose.offset(59.0F, 0.0F, 0.0F));

		PartDefinition left_tip = left_form.addOrReplaceChild("left_tip", CubeListBuilder.create().texOffs(123, 166).mirror().addBox(-6.5F, -6.0F, -88.0F, 13.0F, 8.0F, 90.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(174, 328).mirror().addBox(6.5F, -5.0F, -87.0F, 110.0F, 6.0F, 11.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(-165, 351).mirror().addBox(2.5F, -3.0F, -87.0F, 281.0F, 0.0F, 165.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.6981F, 0.0F));

		PartDefinition right_wing = wings.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(114, 270).addBox(-66.5F, -8.0F, -8.0F, 70.0F, 12.0F, 17.0F, new CubeDeformation(0.0F))
		.texOffs(287, 224).addBox(-88.5F, -2.0F, 1.0F, 92.0F, 0.0F, 81.0F, new CubeDeformation(0.0F)), PartPose.offset(-17.0F, 1.0F, -6.0F));

		PartDefinition right_form = right_wing.addOrReplaceChild("right_form", CubeListBuilder.create(), PartPose.offset(-59.0F, 0.0F, 0.0F));

		PartDefinition right_tip = right_form.addOrReplaceChild("right_tip", CubeListBuilder.create().texOffs(123, 166).addBox(-6.5F, -6.0F, -88.0F, 13.0F, 8.0F, 90.0F, new CubeDeformation(0.0F))
		.texOffs(174, 328).addBox(-116.5F, -5.0F, -87.0F, 110.0F, 6.0F, 11.0F, new CubeDeformation(0.0F))
		.texOffs(-165, 351).addBox(-283.5F, -3.0F, -87.0F, 281.0F, 0.0F, 165.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.6981F, 0.0F));

		PartDefinition legs = root.addOrReplaceChild("legs", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition left_front_leg = legs.addOrReplaceChild("left_front_leg", CubeListBuilder.create().texOffs(444, 124).mirror().addBox(-4.5F, -11.5F, -12.0F, 15.0F, 37.0F, 19.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(18.0F, -33.5F, -4.0F));

		PartDefinition left_front_tip = left_front_leg.addOrReplaceChild("left_front_tip", CubeListBuilder.create().texOffs(462, 5).mirror().addBox(-5.5F, -5.75F, -5.0F, 11.0F, 35.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(3.0F, 26.25F, -3.0F));

		PartDefinition left_front_foot = left_front_tip.addOrReplaceChild("left_front_foot", CubeListBuilder.create().texOffs(93, 40).mirror().addBox(-4.5F, -1.0F, -20.0F, 10.0F, 5.0F, 20.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-0.5F, 27.25F, 3.0F));

		PartDefinition left_front_claw = left_front_foot.addOrReplaceChild("left_front_claw", CubeListBuilder.create().texOffs(18, 105).mirror().addBox(-0.5F, -1.0F, -23.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(18, 105).mirror().addBox(3.5F, -1.0F, -23.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(16, 112).mirror().addBox(3.5F, 2.0F, -23.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(16, 112).mirror().addBox(-4.5F, 2.0F, -23.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(18, 105).mirror().addBox(-4.5F, -1.0F, -23.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(16, 112).mirror().addBox(-0.5F, 2.0F, -23.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition right_front_leg = legs.addOrReplaceChild("right_front_leg", CubeListBuilder.create().texOffs(444, 124).addBox(-10.5F, -11.5F, -12.0F, 15.0F, 37.0F, 19.0F, new CubeDeformation(0.0F)), PartPose.offset(-18.0F, -33.5F, -4.0F));

		PartDefinition right_front_tip = right_front_leg.addOrReplaceChild("right_front_tip", CubeListBuilder.create().texOffs(462, 5).addBox(-5.5F, -5.75F, -5.0F, 11.0F, 35.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, 26.25F, -3.0F));

		PartDefinition right_front_foot = right_front_tip.addOrReplaceChild("right_front_foot", CubeListBuilder.create().texOffs(93, 40).addBox(-5.5F, -1.0F, -20.0F, 10.0F, 5.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 27.25F, 3.0F));

		PartDefinition right_front_claw = right_front_foot.addOrReplaceChild("right_front_claw", CubeListBuilder.create().texOffs(18, 105).addBox(-5.5F, -1.0F, -23.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(16, 112).addBox(-5.5F, 2.0F, -23.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(16, 112).addBox(2.5F, 2.0F, -23.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(18, 105).addBox(2.5F, -1.0F, -23.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(18, 105).addBox(-1.5F, -1.0F, -23.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(16, 112).addBox(-1.5F, 2.0F, -23.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition left_hind_leg = legs.addOrReplaceChild("left_hind_leg", CubeListBuilder.create().texOffs(133, 137).addBox(-7.5F, -10.0F, -14.0F, 16.0F, 38.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offset(16.0F, -31.0F, 50.0F));

		PartDefinition left_hind_tip = left_hind_leg.addOrReplaceChild("left_hind_tip", CubeListBuilder.create().texOffs(375, 129).addBox(-6.5F, 0.0F, 0.0F, 14.0F, 32.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 20.0F, -4.0F));

		PartDefinition left_hind_foot = left_hind_tip.addOrReplaceChild("left_hind_foot", CubeListBuilder.create().texOffs(107, 68).addBox(-7.5F, 0.0F, -20.0F, 16.0F, 6.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 29.0F, 6.0F));

		PartDefinition left_hind_claw = left_hind_foot.addOrReplaceChild("left_hind_claw", CubeListBuilder.create().texOffs(0, 102).mirror().addBox(-1.0F, 0.0F, -25.0F, 4.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 102).mirror().addBox(5.0F, 0.0F, -25.0F, 4.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(2, 111).mirror().addBox(5.0F, 4.0F, -25.0F, 4.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(2, 111).mirror().addBox(-7.0F, 4.0F, -25.0F, 4.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 102).mirror().addBox(-7.0F, 0.0F, -25.0F, 4.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(2, 111).mirror().addBox(-1.0F, 4.0F, -25.0F, 4.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-0.5F, 0.0F, 0.0F));

		PartDefinition right_hind_leg = legs.addOrReplaceChild("right_hind_leg", CubeListBuilder.create().texOffs(133, 137).mirror().addBox(-8.5F, -10.0F, -14.0F, 16.0F, 38.0F, 22.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-16.0F, -31.0F, 50.0F));

		PartDefinition right_hind_tip = right_hind_leg.addOrReplaceChild("right_hind_tip", CubeListBuilder.create().texOffs(375, 129).mirror().addBox(-7.5F, 0.0F, 0.0F, 14.0F, 32.0F, 17.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 20.0F, -4.0F));

		PartDefinition right_hind_foot = right_hind_tip.addOrReplaceChild("right_hind_foot", CubeListBuilder.create().texOffs(107, 68).mirror().addBox(-8.5F, 0.0F, -20.0F, 16.0F, 6.0F, 22.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 29.0F, 6.0F));

		PartDefinition right_hind_claw = right_hind_foot.addOrReplaceChild("right_hind_claw", CubeListBuilder.create().texOffs(0, 102).addBox(-9.0F, 0.0F, -25.0F, 4.0F, 4.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(2, 111).addBox(-9.0F, 4.0F, -25.0F, 4.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(2, 111).addBox(3.0F, 4.0F, -25.0F, 4.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(0, 102).addBox(3.0F, 0.0F, -25.0F, 4.0F, 4.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(0, 102).addBox(-3.0F, 0.0F, -25.0F, 4.0F, 4.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(2, 111).addBox(-3.0F, 4.0F, -25.0F, 4.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 0.0F, 0.0F));
		return LayerDefinition.create(meshdefinition, 600, 600);
	}
	
	
}


