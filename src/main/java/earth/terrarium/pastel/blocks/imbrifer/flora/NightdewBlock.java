package earth.terrarium.pastel.blocks.imbrifer.flora;

import com.mojang.serialization.MapCodec;
import earth.terrarium.pastel.registries.PastelBlockTags;
import earth.terrarium.pastel.registries.PastelItems;
import earth.terrarium.pastel.registries.PastelLootTables;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class NightdewBlock extends TriStateVineBlock {

    public static final MapCodec<NightdewBlock> CODEC = simpleCodec(NightdewBlock::new);

    public static final float BASE_BURGEON_CHANCE = 2000;
    public static final float MAX_BURGEON_CHANCE = 250;

    public NightdewBlock(Properties settings) {
        super(settings, 6, 1F, 0.3F, 0.85F);
    }

    @Override
    public MapCodec<? extends NightdewBlock> codec() {
        return CODEC;
    }

    @Override
    public boolean mayPlaceOn(BlockState roof, BlockGetter world, BlockPos pos) {
        return super.mayPlaceOn(roof, world, pos) && roof.is(PastelBlockTags.NIGHTDEW_SOILS);
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader world, BlockPos pos, BlockState state) {
        return PastelItems.NIGHTDEW_SPROUT.get()
                                          .getDefaultInstance();
    }

    @Override
    public void spawnAfterBreak(
        BlockState state, ServerLevel world, BlockPos pos, ItemStack tool, boolean dropExperience) {
        var random = world.getRandom();

        var sleepingEntities = Math.min(
            world.getEntitiesOfClass(LivingEntity.class, new AABB(pos).inflate(20), LivingEntity::isSleeping)
                 .size() / 20F, 1F
        );
        var dropChance = Mth.clampedLerp(BASE_BURGEON_CHANCE, MAX_BURGEON_CHANCE, sleepingEntities);

        if (random.nextFloat() < 1 / dropChance)
            for (ItemStack rareStack : getRareStacks(
                state, world, pos, tool, PastelLootTables.NIGHTDEW_VINE_RARE_DROP)) {
                popResource(world, pos, rareStack);
            }
    }

    @Override
    boolean hasGrowthActions() {
        return false;
    }

    public static List<ItemStack> getRareStacks(
        BlockState state, ServerLevel world, BlockPos pos, ItemStack stack, ResourceKey<LootTable> lootTableKey) {
        var builder = (new LootParams.Builder(world))
            .withParameter(LootContextParams.BLOCK_STATE, state)
            .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(pos))
            .withParameter(LootContextParams.TOOL, stack);

        LootTable lootTable = world.getServer()
                                   .reloadableRegistries()
                                   .getLootTable(lootTableKey);
        return lootTable.getRandomItems(builder.create(LootContextParamSets.BLOCK));
    }
}
