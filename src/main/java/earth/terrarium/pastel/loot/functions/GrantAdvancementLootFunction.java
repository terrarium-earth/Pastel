package earth.terrarium.pastel.loot.functions;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.loot.PastelLootFunctionTypes;
import earth.terrarium.pastel.progression.PastelCriteria;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootContext.EntityTarget;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import java.util.List;
import java.util.Set;

public class GrantAdvancementLootFunction extends LootItemConditionalFunction {

    public static final MapCodec<GrantAdvancementLootFunction> CODEC = RecordCodecBuilder.mapCodec(
        (instance) -> commonFields(instance).and(instance.group(
                                                EntityTarget.CODEC.fieldOf("entity")
                                                                  .forGetter((function) -> function.entity),
                                                ResourceLocation.CODEC.listOf()
                                                                      .fieldOf("tags")
                                                                      .forGetter((function) -> function.ids)
                                            ))
                                            .apply(instance, GrantAdvancementLootFunction::new));

    private final LootContext.EntityTarget entity;
    private final List<ResourceLocation> ids;

    public GrantAdvancementLootFunction(
        List<LootItemCondition> conditions, LootContext.EntityTarget entity, List<ResourceLocation> ids) {
        super(conditions);
        this.entity = entity;
        this.ids = ids;
    }

    public LootItemFunctionType<GrantAdvancementLootFunction> getType() {
        return PastelLootFunctionTypes.GRANT_ADVANCEMENT;
    }

    public Set<LootContextParam<?>> getReferencedContextParams() {
        return ImmutableSet.of(this.entity.getParam());
    }

    public ItemStack run(ItemStack stack, LootContext context) {
        Entity entity = context.getParamOrNull(this.entity.getParam());
        if (entity instanceof ServerPlayer player) {
            for (ResourceLocation id : this.ids) {
                PastelCriteria.LOOT_FUNCTION_TRIGGER.trigger(player, id);
            }
        }
        return stack;
    }

    public static LootItemConditionalFunction.Builder<?> builder(
        LootContext.EntityTarget target, List<ResourceLocation> ids) {
        return simpleBuilder((conditions) -> new GrantAdvancementLootFunction(conditions, target, ids));
    }
}
