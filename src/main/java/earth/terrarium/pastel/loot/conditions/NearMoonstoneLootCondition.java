package earth.terrarium.pastel.loot.conditions;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.registries.PastelBlockTags;
import earth.terrarium.pastel.registries.PastelLootConditions;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;
import java.util.Set;

public record NearMoonstoneLootCondition(int searchRadius) implements LootItemCondition {
    public static final MapCodec<NearMoonstoneLootCondition> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
                                                                                                       Codec.INT.fieldOf("search_radius")
                                                                                                                .forGetter(NearMoonstoneLootCondition::searchRadius))
                                                                                                   .apply(
                                                                                                       i,
                                                                                                       NearMoonstoneLootCondition::new
                                                                                                   ));

    @Override
    public boolean test(LootContext context) {
        Vec3 origin = context.getParamOrNull(LootContextParams.ORIGIN);
        if (origin == null) return false;
        var searchArea = BlockPos.withinManhattan(
            new BlockPos(Mth.floor(origin.x()), Mth.floor(origin.y()), Mth.floor(origin.z())), searchRadius,
            searchRadius, searchRadius
        );
        for (BlockPos i : searchArea) {
            if (context.getLevel()
                       .getBlockState(i)
                       .is(PastelBlockTags.VIRIDIAN_CRYSTAL_PURITY_SOURCES)) {
                return true;
            }
        }
        return false;
    }

    public static LootItemCondition.Builder nearMoonstone(int radius) {
        return () -> new NearMoonstoneLootCondition(radius);
    }

    @Override
    public Set<LootContextParam<?>> getReferencedContextParams() {
        return ImmutableSet.of(LootContextParams.ORIGIN);
    }

    @Override
    public LootItemConditionType getType() {
        return PastelLootConditions.NEAR_MOONSTONE.get();
    }
}
