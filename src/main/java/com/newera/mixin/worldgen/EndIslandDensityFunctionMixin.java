package com.newera.mixin.worldgen;

import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.densityfunction.generator.EndIslandFunction;
import net.minecraft.world.level.levelgen.synth.SimplexNoise;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EndIslandFunction.class)
public abstract class EndIslandDensityFunctionMixin {

    @Inject(method = "getHeightValue", at = @At("HEAD"), cancellable = true)
    private static void onGetHeightValue(SimplexNoise islandNoise, int sectionX, int sectionZ, CallbackInfoReturnable<Float> cir) {
        int chunkX = sectionX / 2;
        int chunkZ = sectionZ / 2;
        int subSectionX = sectionX % 2;
        int subSectionZ = sectionZ % 2;

        double dist = Math.sqrt((double) (sectionX * sectionX + sectionZ * sectionZ));

        float doffs;
        if (dist <= 6.5D) {
            doffs = 80.0F;
        } else {

            doffs = (float) (80.0D - (dist - 6.5D) * 14.34D);
        }
        doffs = Mth.clamp(doffs, -100.0F, 80.0F);

        for (int xo = -12; xo <= 12; xo++) {
            for (int zo = -12; zo <= 12; zo++) {
                long totalChunkX = (long) (chunkX + xo);
                long totalChunkZ = (long) (chunkZ + zo);
                if (totalChunkX * totalChunkX + totalChunkZ * totalChunkZ > 4096L && islandNoise.get((double) totalChunkX, (double) totalChunkZ) < -0.9F) {
                    float islandSize = (Mth.abs((float) totalChunkX) * 3439.0F + Mth.abs((float) totalChunkZ) * 147.0F) % 13.0F + 9.0F;
                    float xd = (float) (subSectionX - xo * 2);
                    float zd = (float) (subSectionZ - zo * 2);
                    float newDoffs = 100.0F - Mth.sqrt(xd * xd + zd * zd) * islandSize;
                    newDoffs = Mth.clamp(newDoffs, -100.0F, 80.0F);
                    doffs = Math.max(doffs, newDoffs);
                }
            }
        }

        cir.setReturnValue(doffs);
    }
}

