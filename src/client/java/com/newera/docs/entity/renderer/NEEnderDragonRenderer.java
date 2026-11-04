package com.newera.docs.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.newera.docs.entity.animation.INEEnderDragonAnimation;
import com.newera.docs.entity.model.ender_dragon.NEEnderDragonModel;
import com.newera.docs.entity.state.NEEnderDragonRenderState;
import com.newera.docs.entity.render.NEModelLayers;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EndCrystalRenderer;
import net.minecraft.client.renderer.entity.EnderDragonRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class NEEnderDragonRenderer extends MobRenderer<EnderDragon, NEEnderDragonRenderState, EntityModel<NEEnderDragonRenderState>> {
    
    private static final Identifier DRAGON_TEXTURE = Identifier.fromNamespaceAndPath("newera", "textures/entity/ender_dragon/dragon.png");
    
    public NEEnderDragonRenderer(final EntityRendererProvider.Context context) {
        super(context, new NEEnderDragonModel(context.bakeLayer(NEModelLayers.NE_ENDER_DRAGON)), 5.0F);
    }

    @Override
    @NotNull
    public NEEnderDragonRenderState createRenderState() {
        return new NEEnderDragonRenderState();
    }

    private float smoothedFlightRoll = 0.0F;
    private float smoothedFlightTurnX = 0.0F;
    private float smoothedFlightPitch = 0.0F;

    @Override
    public void extractRenderState(@NotNull EnderDragon entity, @NotNull NEEnderDragonRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);

        if (entity instanceof INEEnderDragonAnimation mixinEntity) {
            state.onGroundPoseAnimationState.copyFrom(mixinEntity.getOnGroundPoseAnimationState());
            state.hoverPoseAnimationState.copyFrom(mixinEntity.getHoverPoseAnimationState());
            state.onAirPoseAnimationState.copyFrom(mixinEntity.getOnAirPoseAnimationState());
            state.walkAnimationState.copyFrom(mixinEntity.getWalkAnimationState());
            state.flyAnimationState.copyFrom(mixinEntity.getFlyAnimationState());
            state.biteAnimationState.copyFrom(mixinEntity.getBiteAnimationState());

            state.decelerateAnimationState.copyFrom(mixinEntity.getDecelerateAnimationState());
            state.landingAnimationState.copyFrom(mixinEntity.getLandingAnimationState());
            state.roarAnimationState.copyFrom(mixinEntity.getRoarAnimationState());
            state.pounceLeftAnimationState.copyFrom(mixinEntity.getPounceLeftAnimationState());
            state.pounceRightAnimationState.copyFrom(mixinEntity.getPounceRightAnimationState());
            state.stompAnimationState.copyFrom(mixinEntity.getStompAnimationState());
            state.breathCloseAnimationState.copyFrom(mixinEntity.getBreathCloseAnimationState());
            state.breathAnimationState.copyFrom(mixinEntity.getBreathAnimationState());
            state.takeoffAnimationState.copyFrom(mixinEntity.getTakeoffAnimationState());
            state.fallingAnimationState.copyFrom(mixinEntity.getFallingAnimationState());
            state.lookingAnimationState.copyFrom(mixinEntity.getLookingAnimationState());
            state.rollAnimationState.copyFrom(mixinEntity.getRollAnimationState());
            state.accelerateAnimationState.copyFrom(mixinEntity.getAccelerateAnimationState());
            state.roarHoverAnimationState.copyFrom(mixinEntity.getRoarHoverAnimationState());
            state.hoverBreathAnimationState.copyFrom(mixinEntity.getHoverBreathAnimationState());
            state.flyBreathAnimationState.copyFrom(mixinEntity.getFlyBreathAnimationState());
            state.flyLongBreathAnimationState.copyFrom(mixinEntity.getFlyLongBreathAnimationState());
            state.teleportAnimationState.copyFrom(mixinEntity.getTeleportAnimationState());
            state.roarCurseAnimationState.copyFrom(mixinEntity.getRoarCurseAnimationState());
            state.deathAnimationState.copyFrom(mixinEntity.getDeathAnimationState());

            state.lookYaw = mixinEntity.getSmoothLookYaw();
            state.lookPitch = mixinEntity.getSmoothLookPitch();

            if (entity.nearestCrystal != null) {
                state.beamOffset = entity.nearestCrystal.getPosition(partialTicks).subtract(entity.getPosition(partialTicks));
            } else {
                state.beamOffset = null;
            }

            // Calculate dynamic flight turn kinematics & flight pitch
            int stage = mixinEntity.getFightStage();
            boolean isGroundOrHover = state.onGroundPoseAnimationState.isStarted() || state.hoverPoseAnimationState.isStarted() || state.roarHoverAnimationState.isStarted();
            boolean isSpecialAction = state.accelerateAnimationState.isStarted() || state.takeoffAnimationState.isStarted() || state.decelerateAnimationState.isStarted() || state.landingAnimationState.isStarted() || state.fallingAnimationState.isStarted() || state.roarAnimationState.isStarted() || state.deathAnimationState.isStarted();

            if ((stage == 0 || stage == 15 || stage == 2 || stage == 3) && !isGroundOrHover && !isSpecialAction) {
                float currentYaw = net.minecraft.util.Mth.rotLerp(partialTicks, entity.yRotO, entity.getYRot());
                float pastYaw = entity.flightHistory.get(6, partialTicks).yRot();
                float turnDelta = net.minecraft.util.Mth.wrapDegrees(currentYaw - pastYaw);

                // Positive turnDelta = turning right (vx > 0)
                float targetTurnX = net.minecraft.util.Mth.clamp(turnDelta * 2.2F, -40.0F, 40.0F);
                this.smoothedFlightTurnX = net.minecraft.util.Mth.lerp(0.20F, this.smoothedFlightTurnX, targetTurnX);

                // Pitch angle from 3D velocity
                Vec3 vel = entity.getDeltaMovement();
                double horizSpeed = Math.sqrt(vel.x * vel.x + vel.z * vel.z);
                float targetPitch = (float)(-Math.atan2(vel.y, Math.max(0.08, horizSpeed)) * (180.0 / Math.PI));
                targetPitch = net.minecraft.util.Mth.clamp(targetPitch, -25.0F, 25.0F);
                this.smoothedFlightPitch = net.minecraft.util.Mth.lerp(0.15F, this.smoothedFlightPitch, targetPitch);
            } else {
                this.smoothedFlightTurnX = net.minecraft.util.Mth.lerp(0.25F, this.smoothedFlightTurnX, 0.0F);
                this.smoothedFlightPitch = net.minecraft.util.Mth.lerp(0.25F, this.smoothedFlightPitch, 0.0F);
            }

            this.smoothedFlightRoll = this.smoothedFlightTurnX;
            state.flightTurnX = this.smoothedFlightTurnX;
            state.flightPitch = this.smoothedFlightPitch;
            state.flightRoll = this.smoothedFlightRoll;

            // Determine base pose type with fallback priority
            int forcedPose = mixinEntity.getForcedBasePose();
            if (forcedPose != -1) {
                state.basePoseType = forcedPose;
            } else if (stage == 1 || stage == 3 || entity.onGround()) {
                state.basePoseType = 2; // ON_GROUND
            } else if (state.hoverPoseAnimationState.isStarted() || state.roarHoverAnimationState.isStarted() || state.hoverBreathAnimationState.isStarted() || stage == 15) {
                state.basePoseType = 1; // HOVER
            } else {
                state.basePoseType = 0; // ON_AIR
            }
        }
    }

    @Override
    public void submit(final NEEnderDragonRenderState state, final PoseStack poseStack, final SubmitNodeCollector submitNodeCollector, final CameraRenderState camera) {
        super.submit(state, poseStack, submitNodeCollector, camera);
        if (state.beamOffset != null) {
            float f = (float)state.beamOffset.x;
            float g = (float)state.beamOffset.y;
            float h = (float)state.beamOffset.z;
            EnderDragonRenderer.submitCrystalBeams(f, g + EndCrystalRenderer.getY(state.ageInTicks), h, state.ageInTicks, poseStack, submitNodeCollector, state.lightCoords);
        }
    }

    @Override
    public boolean shouldRender(final EnderDragon entity, final Frustum culler, final double camX, final double camY, final double camZ, final float partialTick) {
        return super.shouldRender(entity, culler, camX, camY, camZ, partialTick) || entity.nearestCrystal != null;
    }

    @Override
    @NotNull
    public Identifier getTextureLocation(@NotNull final NEEnderDragonRenderState state) {
        return DRAGON_TEXTURE;
    }

    @Override
    protected void setupRotations(NEEnderDragonRenderState renderState, PoseStack poseStack, float rotationYaw, float partialTicks) {
        super.setupRotations(renderState, poseStack, rotationYaw, partialTicks);
    }
}
