package com.newera.mixin.worldgen;

import com.newera.docs.worldgen.EndPillarHeightManager;
import net.minecraft.world.level.levelgen.feature.EndSpikeFeature;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EndSpikeFeature.EndSpike.class)
public abstract class EndSpikeMixin {

    @Shadow @Final private int centerX;
    @Shadow @Final private int centerZ;

    @Inject(method = "getHeight", at = @At("HEAD"), cancellable = true)
    private void onGetCustomHeight(CallbackInfoReturnable<Integer> cir) {
        // Lấy chính xác chiều cao Bedrock đỉnh cột tương ứng với từng cột từ EndPillarHeightManager
        // Tia hồi sinh rồng sẽ bắn chính xác vào getHeight() + 1 (tọa độ pha lê)
        cir.setReturnValue(EndPillarHeightManager.getSpikeHeight(this.centerX, this.centerZ));
    }
}
