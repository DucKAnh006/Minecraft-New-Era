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

        // Bán kính tính theo section (1 section = 8 block).
        // Vòng tròn 10 cột End ở bán kính R_pillar = 48.3 block (~ 6.04 section).
        // Khoảng cách từ vòng cột ra mép đảo bằng đúng bán kính vòng cột (thêm 48.3 block),
        // nghĩa là bán kính toàn bộ đảo End là R_edge = 48.3 * 2 = 96.6 block (~ 12.08 section).
        double dist = Math.sqrt((double) (sectionX * sectionX + sectionZ * sectionZ));

        // Giữ mặt đất đặc, phẳng vững chãi bao trọn các cột (R <= 52 block, dist <= 6.5)
        // Sau đó dốc thoải dần và chạm hư không tại đúng R = 96.6 block (dist = 12.08 section)
        float doffs;
        if (dist <= 6.5D) {
            doffs = 80.0F;
        } else {
            // Dốc từ 80.0F tại dist=6.5 xuống 0.0F tại dist=12.08: hệ số dốc = 80.0 / (12.08 - 6.5) = 14.34F
            doffs = (float) (80.0D - (dist - 6.5D) * 14.34D);
        }
        doffs = Mth.clamp(doffs, -100.0F, 80.0F);

        // Vòng lặp các đảo phụ bên ngoài (cách tâm > 1024 block, giữ nguyên logic gốc)
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
