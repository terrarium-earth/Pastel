package earth.terrarium.pastel.loot.conditions;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.api.predicate.block.BrokenBlockPredicate;
import earth.terrarium.pastel.registries.PastelLootConditions;
import net.minecraft.advancements.critereon.BlockPredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.minecraft.world.phys.Vec3;

import java.util.Set;

public record NearBlockLootCondition(BrokenBlockPredicate block, int searchRadius) implements LootItemCondition {
    public static final MapCodec<NearBlockLootCondition> CODEC = RecordCodecBuilder.mapCodec(
        i->i.group(
            BrokenBlockPredicate.CODEC.fieldOf("block").forGetter(NearBlockLootCondition::block),
            Codec.INT.fieldOf("search_radius").forGetter(NearBlockLootCondition::searchRadius)
        ).apply(i,NearBlockLootCondition::new));

    @Override
    public boolean test(LootContext context){
        Vec3 origin = context.getParamOrNull(LootContextParams.ORIGIN);
        if(origin==null) return false;
        var searchArea = BlockPos.withinManhattan(new BlockPos(Mth.floor(origin.x()),Mth.floor(origin.x()),Mth.floor(origin.x())),searchRadius,searchRadius,searchRadius);
        for(BlockPos i : searchArea) {
            if(block.test(context.getLevel().getBlockState(i))){
                return true;
            }
        }
        return false;
    }

    @Override
    public Set<LootContextParam<?>> getReferencedContextParams() {
        return ImmutableSet.of(LootContextParams.ORIGIN);
    }

    @Override
    public LootItemConditionType getType() {
        return PastelLootConditions.NEAR_BLOCK.get();
    }
}
