package earth.terrarium.pastel.registries;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.compat.PastelIntegrationPacks;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.List;

public class PastelResourceConditions {

    public static final DeferredRegister<MapCodec<? extends ICondition>> REGISTER = DeferredRegister
        .create(
            NeoForgeRegistries.CONDITION_SERIALIZERS,
            PastelCommon.MOD_ID
        );

    public static void register(IEventBus modEventBus) {
        REGISTER.register("enchantments_exist", () -> EnchantmentsExistResourceCondition.CODEC);
        REGISTER.register("integration_pack_active", () -> IntegrationPackActiveResourceCondition.CODEC);

        REGISTER.register(modEventBus);
    }

    public record EnchantmentsExistResourceCondition(List<ResourceKey<Enchantment>> enchantments)
        implements
        ICondition {

        public static MapCodec<EnchantmentsExistResourceCondition> CODEC = RecordCodecBuilder
            .mapCodec(
                instance -> instance
                    .group(
                        ResourceKey
                            .codec(Registries.ENCHANTMENT)
                            .listOf()
                            .fieldOf("values")
                            .forGetter(EnchantmentsExistResourceCondition::enchantments)
                    )
                    .apply(instance, EnchantmentsExistResourceCondition::new)
            );

        @Override
        public MapCodec<? extends ICondition> codec() {
            return CODEC;
        }

        @Override
        public boolean test(IContext iContext) {
            return true; // TODO fix
        }
    }

    public record IntegrationPackActiveResourceCondition(String integrationPack) implements ICondition {

        public static MapCodec<IntegrationPackActiveResourceCondition> CODEC = RecordCodecBuilder
            .mapCodec(
                instance -> instance
                    .group(
                        Codec.STRING
                            .fieldOf("integration_pack")
                            .forGetter(IntegrationPackActiveResourceCondition::integrationPack)
                    )
                    .apply(instance, IntegrationPackActiveResourceCondition::new)
            );

        @Override
        public MapCodec<? extends ICondition> codec() {
            return CODEC;
        }

        @Override
        public boolean test(IContext iContext) {
            return PastelIntegrationPacks.isIntegrationPackActive(integrationPack);
        }
    }

}
