package earth.terrarium.pastel.events;


import earth.terrarium.pastel.api.interaction.ResonanceProcessor;
import earth.terrarium.pastel.api.item.ItemPickupListener;
import earth.terrarium.pastel.helpers.enchantments.ExuberanceHelper;
import earth.terrarium.pastel.helpers.enchantments.FoundryHelper;
import earth.terrarium.pastel.registries.PastelDataComponentTypes;
import earth.terrarium.pastel.registries.PastelEnchantmentTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.enchanting.GetEnchantmentLevelEvent;
import net.neoforged.neoforge.event.level.BlockDropsEvent;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemHandlerHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PastelEnchantmentEvents {

    public static void register() {
        NeoForge.EVENT_BUS.addListener(EventPriority.HIGHEST, PastelEnchantmentEvents::applyVoiding);
        NeoForge.EVENT_BUS.addListener(PastelEnchantmentEvents::applyResonance);
        NeoForge.EVENT_BUS.addListener(PastelEnchantmentEvents::applyFoundry);
        NeoForge.EVENT_BUS.addListener(PastelEnchantmentEvents::applyExuberance);
        NeoForge.EVENT_BUS.addListener(EventPriority.LOWEST, PastelEnchantmentEvents::inventoryInsertion);
    }

    private static void applyExuberance(BlockDropsEvent event) {
        var access = event.getLevel()
                          .registryAccess();
        var tool = event.getTool();

        event.setDroppedExperience(
            Math.round(event.getDroppedExperience() * ExuberanceHelper.getExuberanceMod(access, tool)));
    }

    private static void inventoryInsertion(BlockDropsEvent event) {
        var tool = event.getTool();
        var breaker = event.getBreaker();

        if (!EnchantmentHelper.hasTag(tool, PastelEnchantmentTags.INVENTORY_INSERTION_EFFECT))
            return;

        var handler = breaker.getCapability(Capabilities.ItemHandler.ENTITY);
        if (handler == null)
            return;

        var removed = new ArrayList<ItemEntity>();
        for (ItemEntity drop : event.getDrops()) {
            var stack = drop.getItem();
            var remainder = insertStack(breaker, handler, stack);

            if (remainder.isEmpty()) {
                removed.add(drop);
                continue;
            }

            drop.setItem(remainder);
            break; // If the remainder is not empty then the player inv is full.
        }

        removed.forEach(e -> event.getDrops()
                                  .remove(e));
    }

    private static ItemStack insertStack(Entity taker, IItemHandler inventory, ItemStack stack) {
        var rem = ItemPickupListener.receiveRecursive(inventory, 2, 0,
                stack, Optional.ofNullable(taker));

        if (rem.isEmpty())
            return rem;

        return ItemHandlerHelper.insertItemStacked(inventory, stack, false);
    }

    private static void applyFoundry(BlockDropsEvent event) {
        var tool = event.getTool();

        if (!EnchantmentHelper.hasTag(tool, PastelEnchantmentTags.SMELTS_MORE_LOOT))
            return;

        modifyDrops(
            event, (e, access, stacks) ->
                FoundryHelper.applyFoundry(e.getLevel(), stacks)
        );
    }

    private static void applyResonance(BlockDropsEvent event) {
        var tool = event.getTool();

        if (!EnchantmentHelper.hasTag(tool, PastelEnchantmentTags.RESONANT_BLOCK_DROPS))
            return;

        modifyDrops(
            event, (e, access, stacks) -> {
                ResonanceProcessor
                    .applyResonance(access, event.getState(), event.getBlockEntity(), stacks);
                return stacks;
            }
        );
    }

    private static void applyVoiding(BlockDropsEvent event) {
        var tool = event.getTool();
        var pos = event.getPos();

        if (!EnchantmentHelper.hasTag(tool, PastelEnchantmentTags.NO_BLOCK_DROPS))
            return;

        event.getDrops()
             .clear();
        event.getLevel()
             .sendParticles(
                 ParticleTypes.SMOKE, pos.getX() + 0.5, pos.getY() + 0.5,
                 pos.getZ() + 0.5, 10, 0.5, 0.5, 0.5, 0.05
             );
    }

    private static void modifyDrops(BlockDropsEvent event, DropModifier modifier) {
        var entities = event.getDrops();
        var level = event.getLevel();
        List<ItemStack> drops = new ArrayList<>();
        var pos = event.getPos();

        for (ItemEntity entity : entities) {
            drops.add(entity.getItem());
        }
        drops = modifier.apply(event, level.registryAccess(), drops);

        entities.clear();
        drops.forEach(d -> entities.add(createItemEntity(d, pos, level)));
    }

    private static ItemEntity createItemEntity(ItemStack stack, BlockPos pos, ServerLevel level) {
        double d0 = (double) EntityType.ITEM.getHeight() / (double) 2.0F;
        double x = (double) pos.getX() + (double) 0.5F + Mth.nextDouble(level.random, -0.25F, 0.25F);
        double y = (double) pos.getY() + (double) 0.5F + Mth.nextDouble(level.random, -0.25F, 0.25F) - d0;
        double z = (double) pos.getZ() + (double) 0.5F + Mth.nextDouble(level.random, -0.25F, 0.25F);
        var entity = new ItemEntity(level, x, y, z, stack);
        entity.setDefaultPickUpDelay();
        return entity;
    }

    private interface DropModifier {
        List<ItemStack> apply(BlockDropsEvent event, RegistryAccess access, List<ItemStack> original);
    }
}
