package earth.terrarium.pastel.api.predicate.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.api.item.ItemStorage;
import earth.terrarium.pastel.progression.advancement.LongRange;
import earth.terrarium.pastel.registries.PastelDataComponentTypes;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.SingleComponentItemPredicate;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.item.ItemStack;

public record BottomlessStackPredicate(ItemPredicate template, LongRange count)
    implements SingleComponentItemPredicate<ItemStorage.Component> {

    public static Codec<BottomlessStackPredicate> CODEC = RecordCodecBuilder.create(i -> i.group(
                                                                                              ItemPredicate.CODEC.optionalFieldOf("variant", ItemPredicate.Builder.item()
                                                                                                                                                                  .build()
                                                                                                           )
                                                                                                                 .forGetter(c -> c.template),
                                                                                              LongRange.CODEC.optionalFieldOf("count", LongRange.ANY)
                                                                                                             .forGetter(c -> c.count)
                                                                                          )
                                                                                          .apply(
                                                                                              i,
                                                                                              BottomlessStackPredicate::new
                                                                                          ));

    @Override
    public DataComponentType<ItemStorage.Component> componentType() {
        return PastelDataComponentTypes.ITEM_STORAGE;
    }

    @Override
    public boolean matches(ItemStack stack, ItemStorage.Component component) {
        var storage = new ItemStorage(component.reference(), component.count());
        return template.test(storage.stack(1)) && count.test(storage.getCount());
    }

}
