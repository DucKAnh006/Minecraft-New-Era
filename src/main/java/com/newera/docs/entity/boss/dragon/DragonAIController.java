package com.newera.docs.entity.boss.dragon;

import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import com.newera.docs.entity.animation.INEEnderDragonAnimation;

public class DragonAIController {

    private static final IDragonPhase PHASE_0 = new DragonPhase0();
    private static final IDragonPhase PHASE_1 = new DragonPhase1();
    private static final IDragonPhase PHASE_1_5 = new DragonPhase1_5();
    private static final IDragonPhase PHASE_2 = new DragonPhase2();
    private static final IDragonPhase PHASE_2_5 = new DragonPhase2_5();
    private static final IDragonPhase PHASE_3 = new DragonPhase3();

    private static int lastFightStage = -1;

    public static void onFightStageChanged(EnderDragon dragon, INEEnderDragonAnimation mixin, int newStage) {
        lastFightStage = newStage;
        switch (newStage) {
            case 0:
                PHASE_0.onEnter(dragon, mixin);
                break;
            case 1:
                PHASE_1.onEnter(dragon, mixin);
                break;
            case 15:
                PHASE_1_5.onEnter(dragon, mixin);
                break;
            case 2:
                PHASE_2.onEnter(dragon, mixin);
                break;
            case 25:
                PHASE_2_5.onEnter(dragon, mixin);
                break;
            case 3:
                PHASE_3.onEnter(dragon, mixin);
                break;
        }
    }

    public static void tick(EnderDragon dragon, INEEnderDragonAnimation mixin) {
        int stage = mixin.getFightStage();

        if (stage != lastFightStage) {
            onFightStageChanged(dragon, mixin, stage);
        }

        switch (stage) {
            case 0:
                PHASE_0.tick(dragon, mixin);
                break;
            case 1:
                PHASE_1.tick(dragon, mixin);
                break;
            case 15:
                PHASE_1_5.tick(dragon, mixin);
                break;
            case 2:
                PHASE_2.tick(dragon, mixin);
                break;
            case 25:
                PHASE_2_5.tick(dragon, mixin);
                break;
            case 3:
                PHASE_3.tick(dragon, mixin);
                break;
            default:
                break;
        }
    }

    public static void postTick(EnderDragon dragon, INEEnderDragonAnimation mixin) {
        int stage = mixin.getFightStage();

        switch (stage) {
            case 0:
                PHASE_0.postTick(dragon, mixin);
                break;
            case 1:
                PHASE_1.postTick(dragon, mixin);
                break;
            case 2:
                PHASE_2.postTick(dragon, mixin);
                break;
            case 3:
                PHASE_3.postTick(dragon, mixin);
                break;
        }
    }
}

