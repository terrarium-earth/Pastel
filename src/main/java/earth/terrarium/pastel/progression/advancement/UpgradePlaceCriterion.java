package earth.terrarium.pastel.progression.advancement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.blocks.upgrade.Upgradeable;
import net.minecraft.advancements.critereon.BlockPredicate;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

import java.util.Map;
import java.util.Optional;

public class UpgradePlaceCriterion extends SimpleCriterionTrigger<UpgradePlaceCriterion.Conditions> {

    public static final ResourceLocation ID = PastelCommon.locate("upgrade_place");

    public void trigger(
        ServerPlayer player,
        ServerLevel world,
        BlockPos pos,
        int upgradeCount,
        Map<Upgradeable.UpgradeType, Integer> upgradeModifiers
    ) {
        this.trigger(player, (conditions) -> conditions.matches(world, pos, upgradeCount, upgradeModifiers));
    }

    @Override
    public Codec<Conditions> codec() {
        return Conditions.CODEC;
    }

    public record Conditions(
        Optional<ContextAwarePredicate> player,
        Optional<BlockPredicate> blockPredicate,
        Optional<MinMaxBounds.Ints> countRange,
        Optional<MinMaxBounds.Ints> speedRange,
        Optional<MinMaxBounds.Ints> experienceRange,
        Optional<MinMaxBounds.Ints> efficiencyRange,
        Optional<MinMaxBounds.Ints> yieldRange
    ) implements SimpleCriterionTrigger.SimpleInstance {

        public static final Codec<Conditions> CODEC = RecordCodecBuilder
            .create(
                instance -> instance
                    .group(
                        ContextAwarePredicate.CODEC
                            .optionalFieldOf("player")
                            .forGetter(Conditions::player),
                        BlockPredicate.CODEC
                            .optionalFieldOf("block")
                            .forGetter(Conditions::blockPredicate),
                        MinMaxBounds.Ints.CODEC
                            .optionalFieldOf("count")
                            .forGetter(Conditions::countRange),
                        MinMaxBounds.Ints.CODEC
                            .optionalFieldOf("speed_mod")
                            .forGetter(Conditions::speedRange),
                        MinMaxBounds.Ints.CODEC
                            .optionalFieldOf("experience_mod")
                            .forGetter(Conditions::experienceRange),
                        MinMaxBounds.Ints.CODEC
                            .optionalFieldOf("efficiency_mod")
                            .forGetter(Conditions::efficiencyRange),
                        MinMaxBounds.Ints.CODEC
                            .optionalFieldOf("yield_mod")
                            .forGetter(Conditions::yieldRange)
                    )
                    .apply(
                        instance,
                        Conditions::new
                    )
            );

        public boolean matches(
            ServerLevel world,
            BlockPos pos,
            int upgradeCount,
            Map<Upgradeable.UpgradeType, Integer> upgradeModifiers
        ) {
            return (this.blockPredicate.isEmpty() || this.blockPredicate
                .get()
                .matches(world, pos)) && (this.countRange.isEmpty() || this.countRange
                    .get()
                    .matches(upgradeCount)) && (this.speedRange.isEmpty() || this.speedRange
                        .get()
                        .matches(
                            upgradeModifiers
                                .get(
                                    Upgradeable.UpgradeType.SPEED
                                )
                        )) && (this.experienceRange.isEmpty() || this.experienceRange
                            .get()
                            .matches(
                                upgradeModifiers
                                    .get(
                                        Upgradeable.UpgradeType.EXPERIENCE
                                    )
                            )) && (this.efficiencyRange.isEmpty() || this.efficiencyRange
                                .get()
                                .matches(
                                    upgradeModifiers
                                        .get(
                                            Upgradeable.UpgradeType.EFFICIENCY
                                        )
                                )) && (this.yieldRange.isEmpty() || this.yieldRange
                                    .get()
                                    .matches(
                                        upgradeModifiers
                                            .get(
                                                Upgradeable.UpgradeType.YIELD
                                            )
                                    ));
        }
    }

}
