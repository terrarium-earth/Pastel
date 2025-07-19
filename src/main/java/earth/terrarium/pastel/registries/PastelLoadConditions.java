package earth.terrarium.pastel.registries;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.PastelCommon;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Collection;
import java.util.List;

public class PastelLoadConditions {

    public record PastelTagsPopulatedResourceCondition(ResourceLocation registry, List<ResourceLocation> tags)
        implements ICondition {
        public static final MapCodec<PastelTagsPopulatedResourceCondition> CODEC = RecordCodecBuilder.mapCodec(
            (instance) -> {
                return instance.group(
                                   ResourceLocation.CODEC.fieldOf("registry")
                                                         .orElse(
                                                             Registries.ITEM.location())
                                                         .forGetter(PastelTagsPopulatedResourceCondition::registry),
                                   ResourceLocation.CODEC.listOf()
                                                         .fieldOf("values")
                                                         .forGetter(PastelTagsPopulatedResourceCondition::tags)
                               )
                               .apply(instance, PastelTagsPopulatedResourceCondition::new);
            });

        @Override
        public boolean test(IContext context) {
            return tagsPopulated(context, this.registry(), this.tags());
        }

        @Override
        public MapCodec<? extends ICondition> codec() {
            return PastelTagsPopulatedResourceCondition.CODEC;
        }

        public static boolean tagsPopulated(
            IContext context, ResourceLocation registryId, List<ResourceLocation> tags) {
            ResourceKey<Registry<Registry<?>>> registryKey = ResourceKey.createRegistryKey(registryId);

            for (ResourceLocation tag : tags) {
                TagKey<Registry<?>> tagKey = TagKey.create(registryKey, tag);
                Collection<Holder<Registry<?>>> entries = context.getTag(tagKey);

                if (entries.isEmpty()) {
                    return false;
                }
            }

            return true;
        }

        public ResourceLocation registry() {
            return this.registry;
        }

        public List<ResourceLocation> tags() {
            return this.tags;
        }
    }

    public static void register(IEventBus modEventBus) {
        DeferredRegister<MapCodec<? extends ICondition>> register = DeferredRegister.create(
            NeoForgeRegistries.CONDITION_SERIALIZERS, PastelCommon.MOD_ID);

        register.register("tags_populated", () -> PastelTagsPopulatedResourceCondition.CODEC);

        register.register(modEventBus);
    }
}
