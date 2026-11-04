package com.newera.mixin.client;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.BossHealthOverlay;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.BossEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BossHealthOverlay.class)
public abstract class BossHealthOverlayMixin {

    @Unique
    private static final Identifier DRAGON_BAR_EMPTY = Identifier.fromNamespaceAndPath(
            "newera", "textures/gui/boss_bar/dragon_health_bar_empty.png"
    );

    @Unique
    private static final Identifier DRAGON_BAR_FILL = Identifier.fromNamespaceAndPath(
            "newera", "textures/gui/boss_bar/dragon_health_bar.png"
    );

    @Unique
    private static final int BAR_WIDTH = 226;

    @Unique
    private static final int BAR_HEIGHT = 7;

    @Unique
    private static final int TEX_WIDTH = 256;

    @Unique
    private static final int TEX_HEIGHT = 8;

    @Unique
    private static final float U_OFFSET = 15.0F;

    @Unique
    private static final float V_OFFSET = 0.0F;

    @Inject(
            method = "extractBar(Lnet/minecraft/client/gui/GuiGraphicsExtractor;IILnet/minecraft/world/BossEvent;)V",
            at = @At("HEAD"),
            cancellable = true
    )
    private void ne$extractDragonBossBar(GuiGraphicsExtractor extractor, int x, int y, BossEvent bossEvent, CallbackInfo ci) {
        if (bossEvent.getColor() == BossEvent.BossBarColor.PINK) {
            int screenWidth = extractor.guiWidth();
            int barX = (screenWidth - BAR_WIDTH) / 2;
            int barY = y;

            // 1. Render custom empty bar background
            extractor.blit(
                    RenderPipelines.GUI_TEXTURED,
                    DRAGON_BAR_EMPTY,
                    barX,
                    barY,
                    U_OFFSET,
                    V_OFFSET,
                    BAR_WIDTH,
                    BAR_HEIGHT,
                    TEX_WIDTH,
                    TEX_HEIGHT
            );

            // 2. Calculate fill width based on boss health progress (0.0 to 1.0)
            int fillWidth = Mth.lerpDiscrete(bossEvent.getProgress(), 0, BAR_WIDTH);
            fillWidth = Mth.clamp(fillWidth, 0, BAR_WIDTH);

            // 3. Render filled progress bar if greater than 0
            if (fillWidth > 0) {
                extractor.blit(
                        RenderPipelines.GUI_TEXTURED,
                        DRAGON_BAR_FILL,
                        barX,
                        barY,
                        U_OFFSET,
                        V_OFFSET,
                        fillWidth,
                        BAR_HEIGHT,
                        TEX_WIDTH,
                        TEX_HEIGHT
                );
            }

            // Cancel vanilla bar rendering
            ci.cancel();
        }
    }
}
