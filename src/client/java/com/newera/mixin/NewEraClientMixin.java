package com.newera.mixin;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class NewEraClientMixin {
	@Inject(at = @At("HEAD"), method = "tick")
	private void ne$onTick(CallbackInfo ci) {
	}
}