package earth.terrarium.pastel.recipe.titration_barrel;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.PastelCommon;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;

import java.util.List;

public record FermentationStatusEffectEntry(
    MobEffect statusEffect,
    int baseDuration,
    List<StatusEffectPotencyEntry> potencyEntries
) {

    public static final Codec<FermentationStatusEffectEntry> CODEC = RecordCodecBuilder.create(i -> i.group(
                                                                                                         BuiltInRegistries.MOB_EFFECT.byNameCodec()
                                                                                                                                     .orElse(
                                                                                                                                         err -> {
                                                                                                                                             PastelCommon.logError(err + ". Falling back to WEAKNESS");
                                                                                                                                             return err;
                                                                                                                                         }, MobEffects.WEAKNESS.value()
                                                                                                                                     )
                                                                                                                                     .fieldOf("id")
                                                                                                                                     .forGetter(FermentationStatusEffectEntry::statusEffect),
                                                                                                         Codec.INT.optionalFieldOf("base_duration", 1200)
                                                                                                                  .forGetter(FermentationStatusEffectEntry::baseDuration),
                                                                                                         StatusEffectPotencyEntry.CODEC.listOf()
                                                                                                                                       .optionalFieldOf("potency", List.of(new StatusEffectPotencyEntry(0, 0, 0)))
                                                                                                                                       .forGetter(FermentationStatusEffectEntry::potencyEntries)
                                                                                                     )
                                                                                                     .apply(
                                                                                                         i,
                                                                                                         FermentationStatusEffectEntry::new
                                                                                                     ));

    public static final StreamCodec<RegistryFriendlyByteBuf, FermentationStatusEffectEntry> STREAM_CODEC
        = StreamCodec.composite(
        ByteBufCodecs.registry(Registries.MOB_EFFECT), FermentationStatusEffectEntry::statusEffect,
        ByteBufCodecs.VAR_INT, FermentationStatusEffectEntry::baseDuration,
        StatusEffectPotencyEntry.STREAM_CODEC.apply(ByteBufCodecs.list()),
        FermentationStatusEffectEntry::potencyEntries,
        FermentationStatusEffectEntry::new
    );

    public record StatusEffectPotencyEntry(float minAlcPercent, float minThickness, int potency) {

        public static final Codec<StatusEffectPotencyEntry> CODEC = RecordCodecBuilder.create(i -> i.group(
                                                                                                        Codec.FLOAT.optionalFieldOf("min_alc", 0.0F)
                                                                                                                   .forGetter(StatusEffectPotencyEntry::minAlcPercent),
                                                                                                        Codec.FLOAT.optionalFieldOf("min_thickness", 0.0F)
                                                                                                                   .forGetter(StatusEffectPotencyEntry::minThickness),
                                                                                                        Codec.INT.optionalFieldOf("potency", 0)
                                                                                                                 .forGetter(StatusEffectPotencyEntry::potency)
                                                                                                    )
                                                                                                    .apply(
                                                                                                        i,
                                                                                                        StatusEffectPotencyEntry::new
                                                                                                    ));

        public static final StreamCodec<ByteBuf, StatusEffectPotencyEntry> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.FLOAT, StatusEffectPotencyEntry::minAlcPercent,
            ByteBufCodecs.FLOAT, StatusEffectPotencyEntry::minThickness,
            ByteBufCodecs.VAR_INT, StatusEffectPotencyEntry::potency,
            StatusEffectPotencyEntry::new
        );

    }

}
