package earth.terrarium.pastel.loot.modifiers;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.PastelItems;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;

public class NightArcheologyModifier extends LootModifier {

    public static final MapCodec<NightArcheologyModifier> CODEC = RecordCodecBuilder.mapCodec(i ->
            LootModifier.codecStart(i).and(
                    IntProvider.POSITIVE_CODEC.fieldOf("count").forGetter(m -> m.count)
                    ).apply(i, NightArcheologyModifier::new));

    private final IntProvider count;

    protected NightArcheologyModifier(LootItemCondition[] conditionsIn, IntProvider count) {
        super(conditionsIn);
        this.count = count;
    }

    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> original, LootContext lootContext) {
        var id = lootContext.getQueriedLootTableId();
        float chance = -1F;
        boolean replace = false;
        var random = lootContext.getRandom();
        var item = random.nextFloat() < 0.25F ? PastelBlocks.WEEPING_GALA_SPRIG.asItem() : PastelItems.NIGHTDEW_SPROUT;

        if (id.equals(BuiltInLootTables.OCEAN_RUIN_COLD_ARCHAEOLOGY.location()) || id.equals(BuiltInLootTables.OCEAN_RUIN_WARM_ARCHAEOLOGY.location())
                || id.equals(BuiltInLootTables.DESERT_PYRAMID_ARCHAEOLOGY.location()) || id.equals(BuiltInLootTables.DESERT_WELL_ARCHAEOLOGY.location())
        || id.equals(BuiltInLootTables.TRAIL_RUINS_ARCHAEOLOGY_RARE.location())) {
            chance = 0.05F; // TODO: this sucks, un-hardcode later
        }
        else if(id.equals(BuiltInLootTables.SNIFFER_DIGGING.location())) {
            chance = 0.1F;
            replace = true;
        }

        if (lootContext.getRandom().nextFloat() > chance)
            return original;

        var stack = new ItemStack(item, count.sample(lootContext.getRandom()));
        if (replace)
            return ObjectArrayList.of(stack);

        original.add(stack);
        return original;
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}
