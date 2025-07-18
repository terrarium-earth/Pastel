package earth.terrarium.pastel.loot.modifiers;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.PastelItems;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;

import java.util.List;

public class NightArcheologyModifier extends LootModifier {

    public static final MapCodec<NightArcheologyModifier> CODEC = RecordCodecBuilder.mapCodec(i ->
                                                                                                  LootModifier.codecStart(
                                                                                                                  i)
                                                                                                              .and(
                                                                                                                  i.group(
                                                                                                                      ExtraCodecs.RESOURCE_PATH_CODEC.xmap(
                                                                                                                                     ResourceLocation::tryParse,
                                                                                                                                     ResourceLocation::toString
                                                                                                                                 )
                                                                                                                                                     .listOf()
                                                                                                                                                     .fieldOf(
                                                                                                                                                         "targets")
                                                                                                                                                     .forGetter(
                                                                                                                                                         m -> m.targets),
                                                                                                                      IntProvider.POSITIVE_CODEC.fieldOf(
                                                                                                                                     "count")
                                                                                                                                                .forGetter(
                                                                                                                                                    m -> m.count),
                                                                                                                      Codec.FLOAT.fieldOf(
                                                                                                                               "chance")
                                                                                                                                 .forGetter(
                                                                                                                                     m -> m.chance),
                                                                                                                      Codec.BOOL.fieldOf(
                                                                                                                               "replace")
                                                                                                                                .forGetter(
                                                                                                                                    m -> m.replace)
                                                                                                                  )
                                                                                                              )
                                                                                                              .apply(
                                                                                                                  i,
                                                                                                                  NightArcheologyModifier::new
                                                                                                              ));

    private final List<ResourceLocation> targets;
    private final IntProvider count;
    private final float chance;
    private final boolean replace;

    protected NightArcheologyModifier(
        LootItemCondition[] conditionsIn, List<ResourceLocation> targets, IntProvider count, float chance,
        boolean replace
    ) {
        super(conditionsIn);
        this.targets = targets;
        this.count = count;
        this.chance = chance;
        this.replace = replace;
    }

    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> original, LootContext lootContext) {
        var id = lootContext.getQueriedLootTableId();
        var random = lootContext.getRandom();
        var item = random.nextFloat() < 0.25F ? PastelBlocks.WEEPING_GALA_SPRIG.asItem() : PastelItems.NIGHTDEW_SPROUT;

        if (!targets.contains(id) || random.nextFloat() > chance)
            return original;

        original.add(new ItemStack(item, count.sample(random)));
        return original;
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}
