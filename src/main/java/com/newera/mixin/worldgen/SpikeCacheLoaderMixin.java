package com.newera.mixin.worldgen;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Util;
import net.minecraft.world.level.levelgen.feature.EndSpikeFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.stream.IntStream;

@Mixin(targets = "net.minecraft.world.level.levelgen.feature.EndSpikeFeature$SpikeCacheLoader")
public abstract class SpikeCacheLoaderMixin {

    @Inject(method = "load(Ljava/lang/Long;)Ljava/util/List;", at = @At("HEAD"), cancellable = true)
    private void onLoadCustomSpikes(Long seed, CallbackInfoReturnable<List<EndSpikeFeature.EndSpike>> cir) {
        IntArrayList sizes = Util.toShuffledList(IntStream.range(0, 10), RandomSource.createThreadLocalInstance(seed));
        List<EndSpikeFeature.EndSpike> result = Lists.newArrayList();

        double distance = 42.0D * 1.15D;

        for (int i = 0; i < 10; i++) {
            int x = Mth.floor(distance * Math.cos(2.0D * (-Math.PI + (Math.PI / 10.0D) * (double) i)));
            int z = Mth.floor(distance * Math.sin(2.0D * (-Math.PI + (Math.PI / 10.0D) * (double) i)));
            int size = sizes.get(i);
            int radius = 11;
            int height = 99;
            boolean guarded = size == 1 || size == 2;
            result.add(new EndSpikeFeature.EndSpike(x, z, radius, height, guarded));
        }

        cir.setReturnValue(result);
    }
}

