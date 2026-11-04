package com.newera.docs.entity.variant;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.core.ClientAsset;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.core.registries.codec.RegistryFixedCodec;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.variant.ModelAndTexture;
import net.minecraft.world.entity.variant.PriorityProvider;
import net.minecraft.world.entity.variant.SpawnCondition;
import net.minecraft.world.entity.variant.SpawnContext;
import net.minecraft.world.entity.variant.SpawnPrioritySelectors;

public record NESheepVariant(ModelAndTexture<NESheepVariant.ModelType> modelAndTexture, ClientAsset.ResourceTexture babyTexture, SpawnPrioritySelectors spawnConditions)
        implements PriorityProvider<SpawnContext, SpawnCondition> {
    public static final Codec<NESheepVariant> DIRECT_CODEC = RecordCodecBuilder.create(
            i -> i.group(
                            ModelAndTexture.codec(NESheepVariant.ModelType.CODEC, NESheepVariant.ModelType.NORMAL).forGetter(NESheepVariant::modelAndTexture),
                            ClientAsset.ResourceTexture.CODEC.fieldOf("baby_asset_id").forGetter(NESheepVariant::babyTexture),
                            SpawnPrioritySelectors.CODEC.optionalFieldOf("spawn_conditions", SpawnPrioritySelectors.fallback(0)).forGetter(NESheepVariant::spawnConditions)
                    )
                    .apply(i, NESheepVariant::new)
    );
    public static final Codec<NESheepVariant> NETWORK_CODEC = RecordCodecBuilder.create(
            i -> i.group(
                            ModelAndTexture.codec(NESheepVariant.ModelType.CODEC, NESheepVariant.ModelType.NORMAL).forGetter(NESheepVariant::modelAndTexture),
                            ClientAsset.ResourceTexture.CODEC.fieldOf("baby_asset_id").forGetter(NESheepVariant::babyTexture)
                    )
                    .apply(i, NESheepVariant::new)
    );
    public static final Codec<Holder<NESheepVariant>> CODEC = RegistryFixedCodec.create(NESheepVariants.SHEEP_VARIANT_KEY);
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<NESheepVariant>> STREAM_CODEC = ByteBufCodecs.holderRegistry(NESheepVariants.SHEEP_VARIANT_KEY);

    public NESheepVariant(final ModelAndTexture<NESheepVariant.ModelType> assetInfo, final ClientAsset.ResourceTexture babyTexture) {
        this(assetInfo, babyTexture, SpawnPrioritySelectors.EMPTY);
    }

    @Override
    public List<PriorityProvider.Selector<SpawnContext, SpawnCondition>> selectors() {
        return this.spawnConditions.selectors();
    }

    public static enum ModelType implements StringRepresentable {
        NORMAL("normal"),
        WARM("warm"),
        COLD("cold");

        public static final Codec<NESheepVariant.ModelType> CODEC = StringRepresentable.fromEnum(NESheepVariant.ModelType::values);
        private final String name;

        private ModelType(final String name) {
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }
    }
}
