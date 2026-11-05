package com.newera.mixin.util;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net.minecraft.util.Util$2")
public class UtilWorkerThreadMixin {

    @Inject(method = "onStart", at = @At("HEAD"))
    private void ne$onStart(CallbackInfo ci) {
        ne$expandStack(15000);
    }

    private static void ne$expandStack(int depth) {
        if (depth <= 0) return;
        long a = depth, b = depth, c = depth, d = depth;
        ne$expandStack(depth - 1);
        if (a + b + c + d == 0) {
            System.out.print("");
        }
    }
}

