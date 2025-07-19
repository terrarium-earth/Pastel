package earth.terrarium.pastel.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.progression.PastelAdvancementCriteria;
import net.minecraft.core.*;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public record PairedFoodComponent(
    Holder<Item> item, boolean consumeAndApplyRequiredStack, FoodProperties bonusFoodComponent
) {

    //TODO what is bonusFoodComponent used for?
    public static final Codec<PairedFoodComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                                                                                                             BuiltInRegistries.ITEM.holderByNameCodec()
                                                                                                                                   .fieldOf("item")
                                                                                                                                   .forGetter(PairedFoodComponent::item),
                                                                                                             Codec.BOOL.optionalFieldOf("consume_and_apply_required_stack", true)
                                                                                                                       .forGetter(PairedFoodComponent::consumeAndApplyRequiredStack),
                                                                                                             FoodProperties.DIRECT_CODEC.optionalFieldOf("bonus_food_component", new FoodProperties.Builder().build())
                                                                                                                                        .forGetter(PairedFoodComponent::bonusFoodComponent)
                                                                                                         )
                                                                                                         .apply(
                                                                                                             instance,
                                                                                                             PairedFoodComponent::new
                                                                                                         ));

    public static final StreamCodec<RegistryFriendlyByteBuf, PairedFoodComponent> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.holderRegistry(Registries.ITEM), PairedFoodComponent::item,
        ByteBufCodecs.BOOL, PairedFoodComponent::consumeAndApplyRequiredStack,
        FoodProperties.DIRECT_STREAM_CODEC, PairedFoodComponent::bonusFoodComponent,
        PairedFoodComponent::new
    );

    public void tryEatFood(Level world, LivingEntity livingEntity, ItemStack eatenStack) {
        if (!(livingEntity instanceof Player player)) {
            return;
        }

        // does the entity have a matching stack in their inv?
        int requiredSlotStack = -1;
        for (
            int i = 0; i < player.getInventory()
                                 .getContainerSize(); i++
        ) {
            if (player.getInventory()
                      .getItem(i)
                      .is(this.item)) {
                requiredSlotStack = i;
                break;
            }
        }
        if (requiredSlotStack == -1) {
            return;
        }
        ItemStack foundRequiredStack = player.getInventory()
                                             .getItem(requiredSlotStack);

        // should the required stack be consumed, too?
        if (consumeAndApplyRequiredStack) {
            FoodProperties component = foundRequiredStack.get(DataComponents.FOOD);
            if (component != null) {
                player.eat(world, foundRequiredStack, component);
            }
        }

        if (player instanceof ServerPlayer serverPlayer) {
            PastelAdvancementCriteria.CONDITIONAL_FOOD_EATEN.trigger(serverPlayer, eatenStack, foundRequiredStack);
        }
    }

}
