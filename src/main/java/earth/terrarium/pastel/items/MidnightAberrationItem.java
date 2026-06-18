package earth.terrarium.pastel.items;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.helpers.Support;
import earth.terrarium.pastel.registries.PastelDataComponentTypes;
import earth.terrarium.pastel.registries.PastelSounds;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Unit;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class MidnightAberrationItem extends Item {

    private static final ResourceLocation MIDNIGHT_ABERRATION_CRUMBLING_ADVANCEMENT_ID = PastelCommon
        .locate("midgame/crumble_midnight_aberration");

    private static final String MIDNIGHT_ABERRATION_CRUMBLING_ADVANCEMENT_CRITERION = "have_midnight_aberration_crumble";

    // Aberrations crumble in the player's inventory (or any inventory that ticks)
    // but only after a short grace period, to give them a chance to actually look at it / use it
    private static final int CRUMBLING_GRACE_PERIOD_TICKS = 40;

    private static final String FIRST_INVENTORY_TICK_NBT = "first_inventory_tick";

    public MidnightAberrationItem(Item.Properties settings) {
        super(settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);

        if (!world.isClientSide && world.getGameTime() % 20 == 0 && entity instanceof ServerPlayer player) {
            if (stack.has(PastelDataComponentTypes.STABLE))
                return;

            if (!stack.has(PastelDataComponentTypes.TIMESTAMP)) {
                stack.set(PastelDataComponentTypes.TIMESTAMP, world.getGameTime());
                return;
            }

            long firstInventoryTick = stack.get(PastelDataComponentTypes.TIMESTAMP);
            if (world.getGameTime() < firstInventoryTick + CRUMBLING_GRACE_PERIOD_TICKS) {
                return;
            }

            // check if it's a real stack in the player's inventory or just a proxy item (like a Bottomless Bundle)
            if (world.random.nextFloat() < 0.2F) {
                stack.shrink(1);
                player.getInventory().placeItemBackInInventory(Items.GUNPOWDER.getDefaultInstance());
                world
                    .playSound(
                        null,
                        player,
                        PastelSounds.MIDNIGHT_ABERRATION_CRUMBLING,
                        SoundSource.PLAYERS,
                        0.5F,
                        1.0F
                    );

                Support
                    .grantAdvancementCriterion(
                        player,
                        MIDNIGHT_ABERRATION_CRUMBLING_ADVANCEMENT_ID,
                        MIDNIGHT_ABERRATION_CRUMBLING_ADVANCEMENT_CRITERION
                    );
            }
        }
    }

    @Override
    public void appendHoverText(
        ItemStack stack,
        Item.TooltipContext context,
        List<Component> tooltip,
        TooltipFlag type
    ) {
        super.appendHoverText(stack, context, tooltip, type);

        if (stack.has(PastelDataComponentTypes.STABLE))
            tooltip.add(Component.translatable("item.pastel.midnight_aberration.tooltip.stable"));
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return oldStack.has(PastelDataComponentTypes.TIMESTAMP) != newStack.has(PastelDataComponentTypes.TIMESTAMP);
    }

    public ItemStack getStableStack() {
        ItemStack stack = getDefaultInstance();
        stack.set(PastelDataComponentTypes.STABLE, Unit.INSTANCE);
        return stack;
    }

}
