package com.newera.docs.entity.variant;

import net.minecraft.core.ClientAsset;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.variant.BiomeCheck;
import net.minecraft.world.entity.variant.ModelAndTexture;
import net.minecraft.world.entity.variant.SpawnPrioritySelectors;
import net.minecraft.world.level.biome.Biome;

public class NESheepVariants {
    public static final ResourceKey<Registry<NESheepVariant>> SHEEP_VARIANT_KEY = ResourceKey.createRegistryKey(Identifier.fromNamespaceAndPath("minecraft", "sheep_variant"));

    public static final ResourceKey<NESheepVariant> NORMAL = createKey(Identifier.fromNamespaceAndPath("minecraft", "temperate"));
    public static final ResourceKey<NESheepVariant> WARM = createKey(Identifier.fromNamespaceAndPath("minecraft", "warm"));
    public static final ResourceKey<NESheepVariant> COLD = createKey(Identifier.fromNamespaceAndPath("minecraft", "cold"));
    public static final ResourceKey<NESheepVariant> DEFAULT = NORMAL;

    private static ResourceKey<NESheepVariant> createKey(final Identifier id) {
        return ResourceKey.create(SHEEP_VARIANT_KEY, id);
    }

    public static void bootstrap(final BootstrapContext<NESheepVariant> context) {
        register(context, NORMAL, NESheepVariant.ModelType.NORMAL, "normal_sheep", "normal_sheep_baby", SpawnPrioritySelectors.fallback(0));
        register(context, WARM, NESheepVariant.ModelType.WARM, "warm_sheep", "warm_sheep_baby", BiomeTags.SPAWNS_WARM_VARIANT_FARM_ANIMALS);
        register(context, COLD, NESheepVariant.ModelType.COLD, "cold_sheep", "cold_sheep_baby", BiomeTags.SPAWNS_COLD_VARIANT_FARM_ANIMALS);
    }

    private static void register(
            final BootstrapContext<NESheepVariant> context,
            final ResourceKey<NESheepVariant> name,
            final NESheepVariant.ModelType modelType,
            final String textureName,
            final String babyTextureName,
            final TagKey<Biome> spawnBiome
    ) {
        HolderSet<Biome> biomes = context.lookup(Registries.BIOME).getOrThrow(spawnBiome);
        register(context, name, modelType, textureName, babyTextureName, SpawnPrioritySelectors.single(new BiomeCheck(biomes), 1));
    }

    private static void register(
            final BootstrapContext<NESheepVariant> context,
            final ResourceKey<NESheepVariant> name,
            final NESheepVariant.ModelType modelType,
            final String textureName,
            final String babyTextureName,
            final SpawnPrioritySelectors selectors
    ) {
        Identifier textureId = Identifier.withDefaultNamespace("entity/sheep/" + textureName);
        Identifier babyTextureId = Identifier.withDefaultNamespace("entity/sheep/" + babyTextureName);
        context.register(name, new NESheepVariant(new ModelAndTexture<>(modelType, textureId), new ClientAsset.ResourceTexture(babyTextureId), selectors));
    }
}
