package com.newera.docs.entity.model.ender_crystal;

import com.newera.docs.entity.animation.ender_crystal.EnderCrystalAnimation;
import com.newera.docs.entity.state.NEEnderCrystalRenderState;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.rendertype.RenderTypes;

public class NEEnderCrystalModel extends EntityModel<NEEnderCrystalRenderState> {
	public final ModelPart root;
	public final ModelPart crystal;
	public final ModelPart circle;
	public final ModelPart circle1;
	public final ModelPart circle2;
	public final ModelPart circle3;
	public final ModelPart outerglass;
	public final ModelPart form;
	public final ModelPart innerglass;
	public final ModelPart crystal_hearth;
	public final ModelPart heart;
	public final ModelPart base;
	public final ModelPart shell;
	public final ModelPart end_crystal_shell;
	public final ModelPart end_crystal_shell2;
	public final ModelPart end_crystal_shell3;
	public final ModelPart end_crystal_shell4;

	private final KeyframeAnimation crystalBaseAnimation;
	private final KeyframeAnimation crystalIdleAnimation;
	private final KeyframeAnimation crystalSleepAnimation;
	private final KeyframeAnimation crystalWakeUpAnimation;

	public NEEnderCrystalModel(ModelPart root) {
		super(root, RenderTypes::entityTranslucent);
		this.root = root.getChild("root");
		this.crystal = this.root.getChild("crystal");
		this.circle = this.crystal.getChild("circle");
		this.circle1 = this.circle.getChild("circle1");
		this.circle2 = this.circle.getChild("circle2");
		this.circle3 = this.circle.getChild("circle3");
		this.outerglass = this.crystal.getChild("outerglass");
		this.form = this.outerglass.getChild("form");
		this.innerglass = this.crystal.getChild("innerglass");
		this.crystal_hearth = this.crystal.getChild("crystal_hearth");
		this.heart = this.crystal_hearth.getChild("heart");
		this.base = this.root.getChild("base");
		this.shell = this.root.getChild("shell");
		this.end_crystal_shell = this.shell.getChild("end_crystal_shell");
		this.end_crystal_shell2 = this.shell.getChild("end_crystal_shell2");
		this.end_crystal_shell3 = this.shell.getChild("end_crystal_shell3");
		this.end_crystal_shell4 = this.shell.getChild("end_crystal_shell4");

		this.crystalBaseAnimation = EnderCrystalAnimation.CRYSTAL_BASE.bake(this.root);
		this.crystalIdleAnimation = EnderCrystalAnimation.CRYSTAL_IDLE.bake(this.root);
		this.crystalSleepAnimation = EnderCrystalAnimation.CRYSTAL_SLEEP.bake(this.root);
		this.crystalWakeUpAnimation = EnderCrystalAnimation.CRYSTAL_WAKE_UP.bake(this.root);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 40.0F, 0.0F));

		PartDefinition crystal = root.addOrReplaceChild("crystal", CubeListBuilder.create(), PartPose.offset(0.0F, -18.0F, 0.0F));

		PartDefinition circle = crystal.addOrReplaceChild("circle", CubeListBuilder.create(), PartPose.offset(0.0F, 18.0F, 0.0F));

		circle.addOrReplaceChild("circle1", CubeListBuilder.create().texOffs(117, 68).addBox(-26.0F, -26.0F, 0.0F, 52.0F, 52.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -24.0F, 0.0F));

		circle.addOrReplaceChild("circle2", CubeListBuilder.create().texOffs(65, 68).addBox(-26.0F, 0.0F, -26.0F, 52.0F, 0.0F, 52.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -24.0F, 0.0F));

		circle.addOrReplaceChild("circle3", CubeListBuilder.create().texOffs(117, 16).addBox(0.0F, -26.0F, -26.0F, 0.0F, 52.0F, 52.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -24.0F, 0.0F));

		PartDefinition outerglass = crystal.addOrReplaceChild("outerglass", CubeListBuilder.create(), PartPose.offset(0.0F, -6.0F, 0.0F));

		outerglass.addOrReplaceChild("form", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		crystal.addOrReplaceChild("innerglass", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.0F, 0.0F));

		PartDefinition crystal_hearth = crystal.addOrReplaceChild("crystal_hearth", CubeListBuilder.create(), PartPose.offset(0.0F, -6.0F, 0.0F));

		crystal_hearth.addOrReplaceChild("heart", CubeListBuilder.create().texOffs(64, 0).addBox(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		root.addOrReplaceChild("base", CubeListBuilder.create().texOffs(0, 32).addBox(-12.0F, -16.0F, -12.0F, 24.0F, 8.0F, 24.0F, new CubeDeformation(0.0F))
			.texOffs(100, 23).addBox(-16.0F, -8.0F, -16.0F, 32.0F, 8.0F, 32.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition shell = root.addOrReplaceChild("shell", CubeListBuilder.create(), PartPose.offset(0.0F, 4.0F, 0.0F));

		shell.addOrReplaceChild("end_crystal_shell", CubeListBuilder.create().texOffs(0, 74).addBox(-6.75F, -19.0F, -6.75F, 14.0F, 38.0F, 14.0F, new CubeDeformation(0.0F))
			.texOffs(0, 126).addBox(-7.25F, -19.0F, -7.25F, 14.0F, 38.0F, 14.0F, new CubeDeformation(0.5F)), PartPose.offset(-7.25F, -27.0F, -7.25F));

		shell.addOrReplaceChild("end_crystal_shell2", CubeListBuilder.create().texOffs(0, 74).mirror().addBox(-7.25F, -19.0F, -6.75F, 14.0F, 38.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false)
			.texOffs(0, 126).mirror().addBox(-6.75F, -19.0F, -7.25F, 14.0F, 38.0F, 14.0F, new CubeDeformation(0.5F)).mirror(false), PartPose.offset(7.25F, -27.0F, -7.25F));

		shell.addOrReplaceChild("end_crystal_shell3", CubeListBuilder.create().texOffs(57, 74).mirror().addBox(-7.25F, -19.0F, -7.25F, 14.0F, 38.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false)
			.texOffs(57, 126).mirror().addBox(-6.75F, -19.0F, -6.75F, 14.0F, 38.0F, 14.0F, new CubeDeformation(0.5F)).mirror(false), PartPose.offset(7.25F, -27.0F, 7.25F));

		shell.addOrReplaceChild("end_crystal_shell4", CubeListBuilder.create().texOffs(57, 74).addBox(-6.75F, -19.0F, -7.25F, 14.0F, 38.0F, 14.0F, new CubeDeformation(0.0F))
			.texOffs(57, 126).addBox(-7.25F, -19.0F, -6.75F, 14.0F, 38.0F, 14.0F, new CubeDeformation(0.5F)), PartPose.offset(-7.25F, -27.0F, 7.25F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(NEEnderCrystalRenderState state) {
		super.setupAnim(state);
		this.root.resetPose();
		this.base.visible = state.showsBottom;
		this.shell.visible = state.showsBottom;

		if (state.crystalState == 0) {
			this.crystalSleepAnimation.apply(state.sleepAnimationState, state.ageInTicks);
		} else if (state.crystalState == 1) {
			this.crystalWakeUpAnimation.apply(state.wakeUpAnimationState, state.ageInTicks);
		} else {
			this.crystalBaseAnimation.apply(state.baseAnimationState, state.ageInTicks);
			this.crystalIdleAnimation.apply(state.idleAnimationState, state.ageInTicks);
		}
	}
}

