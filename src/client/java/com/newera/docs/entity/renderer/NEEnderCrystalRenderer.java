package com.newera.docs.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.newera.docs.entity.animation.INEEndCrystalAnimation;
import com.newera.docs.entity.model.ender_crystal.NEEnderCrystalModel;
import com.newera.docs.entity.render.NEModelLayers;
import com.newera.docs.entity.state.NEEnderCrystalRenderState;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EndCrystalRenderer;
import net.minecraft.client.renderer.entity.EnderDragonRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
import net.minecraft.world.phys.Vec3;

public class NEEnderCrystalRenderer extends EntityRenderer<EndCrystal, NEEnderCrystalRenderState> {
	private static final Identifier TEXTURE_LOCATION = Identifier.fromNamespaceAndPath("newera", "textures/entity/ender_crystal/endercrystal.png");
	private final NEEnderCrystalModel model;

	public NEEnderCrystalRenderer(final EntityRendererProvider.Context context) {
		super(context);
		this.shadowRadius = 0.5F;
		this.model = new NEEnderCrystalModel(context.bakeLayer(NEModelLayers.NE_ENDER_CRYSTAL));
	}

	@Override
	public void submit(final NEEnderCrystalRenderState state, final PoseStack poseStack, final SubmitNodeCollector submitNodeCollector, final CameraRenderState camera) {
		poseStack.pushPose();
		poseStack.scale(-1.0F, -1.0F, 1.0F);
		poseStack.translate(0.0F, -1.501F, 0.0F);
		submitNodeCollector.submitModel(this.model, state, poseStack, TEXTURE_LOCATION, state.lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor);
		poseStack.popPose();

		Vec3 beamOffset = state.beamOffset;
		if (beamOffset != null) {
			float crystalY = EndCrystalRenderer.getY(state.ageInTicks);
			float deltaX = (float)beamOffset.x;
			float deltaY = (float)beamOffset.y;
			float deltaZ = (float)beamOffset.z;
			poseStack.translate(beamOffset);
			EnderDragonRenderer.submitCrystalBeams(-deltaX, -deltaY + crystalY, -deltaZ, state.ageInTicks, poseStack, submitNodeCollector, state.lightCoords);
		}

		super.submit(state, poseStack, submitNodeCollector, camera);
	}

	@Override
	public NEEnderCrystalRenderState createRenderState() {
		return new NEEnderCrystalRenderState();
	}

	@Override
	public void extractRenderState(final EndCrystal entity, final NEEnderCrystalRenderState state, final float partialTicks) {
		super.extractRenderState(entity, state, partialTicks);
		state.ageInTicks = entity.time + partialTicks;
		state.showsBottom = entity.showsBottom();
		BlockPos beamTarget = entity.getBeamTarget();
		if (beamTarget != null) {
			state.beamOffset = Vec3.atCenterOf(beamTarget).subtract(entity.getPosition(partialTicks));
		} else {
			state.beamOffset = null;
		}

		if (entity instanceof INEEndCrystalAnimation mixin) {
			state.sleepAnimationState.copyFrom(mixin.getSleepAnimationState());
			state.wakeUpAnimationState.copyFrom(mixin.getWakeUpAnimationState());
			state.baseAnimationState.copyFrom(mixin.getBaseAnimationState());
			state.idleAnimationState.copyFrom(mixin.getIdleAnimationState());
			state.crystalState = mixin.getCrystalState();
		}
	}

	@Override
	public boolean shouldRender(final EndCrystal entity, final Frustum culler, final double camX, final double camY, final double camZ, final float partialTick) {
		return super.shouldRender(entity, culler, camX, camY, camZ, partialTick) || entity.getBeamTarget() != null;
	}
}

