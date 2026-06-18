package earth.terrarium.pastel.registries.client;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.api.energy.InkPoweredStatusEffectInstance;
import earth.terrarium.pastel.api.energy.color.InkColor;
import earth.terrarium.pastel.api.energy.color.InkColors;
import earth.terrarium.pastel.api.energy.storage.SingleInkStorage;
import earth.terrarium.pastel.blocks.conditional.colored_tree.ColoredLeavesBlock;
import earth.terrarium.pastel.blocks.memory.MemoryBlockEntity;
import earth.terrarium.pastel.blocks.memory.MemoryItem;
import earth.terrarium.pastel.components.InfusedBeverageComponent;
import earth.terrarium.pastel.items.energy.InkFlaskItem;
import earth.terrarium.pastel.progression.ToggleableBlockColorProvider;
import earth.terrarium.pastel.progression.ToggleableItemColorProvider;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.PastelDataComponentTypes;
import earth.terrarium.pastel.registries.PastelItems;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.util.FastColor;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

import java.util.List;

public class PastelColorProviders {

    public static ToggleableBlockColorProvider coloredLeavesBlockColorProvider;

    public static ToggleableItemColorProvider coloredLeavesItemColorProvider;

    public static ToggleableBlockColorProvider amaranthBushelBlockColorProvider;

    public static ToggleableItemColorProvider amaranthBushelItemColorProvider;

    public static ToggleableBlockColorProvider amaranthCropBlockColorProvider;

    public static ToggleableItemColorProvider amaranthCropItemColorProvider;

    private static final BlockColor THAT_ONE_VANILLA_LEAF_PROVIDER = (
        state,
        level,
        pos,
        tintIndex
    ) -> level != null && pos != null ? BiomeColors.getAverageFoliageColor(level, pos) : FoliageColor.getDefaultColor();

    private static final BlockColor THE_OTHER_ONE_AKA_GRASS = (
        state,
        level,
        pos,
        tintIndex
    ) -> level != null && pos != null ? BiomeColors.getAverageGrassColor(level, pos) : GrassColor.getDefaultColor();

    private static final ItemColor THAT_ONE_VANILLA_ITEM_PROVIDER = (stack, tint) -> {
        BlockState blockstate = ((BlockItem) stack.getItem()).getBlock().defaultBlockState();
        return THAT_ONE_VANILLA_LEAF_PROVIDER.getColor(blockstate, null, null, tint);
    };

    private static final ItemColor YES_THERE_IS_ANOTHER_ITEM_ONE_TOO = (stack, tint) -> {
        BlockState blockstate = ((BlockItem) stack.getItem()).getBlock().defaultBlockState();
        return THE_OTHER_ONE_AKA_GRASS.getColor(blockstate, null, null, tint);
    };

    public static void registerBlocks(RegisterColorHandlersEvent.Block event) {
        PastelCommon.logInfo("Registering Block Color Providers...");

        // Biome Colors for colored leaves items and blocks
        // They don't use it, but their decoy oak leaves do
        coloredLeavesBlock(event);
        // Same for Amaranth
        amaranthBlock(event);
        event.register(THE_OTHER_ONE_AKA_GRASS, PastelBlocks.CLOVER.get(), PastelBlocks.FOUR_LEAF_CLOVER.get());
        memoryBlock(event, PastelBlocks.MEMORY.get());
    }

    public static void registerItems(RegisterColorHandlersEvent.Item event) {
        PastelCommon.logInfo("Registering Item Color Providers...");

        coloredLeavesItem(event);
        amaranthItem(event);
        event
            .register(
                YES_THERE_IS_ANOTHER_ITEM_ONE_TOO,
                PastelBlocks.CLOVER.get(),
                PastelBlocks.FOUR_LEAF_CLOVER.get()
            );

        memoryItem(event, PastelBlocks.MEMORY.get());
        registerPotionFillables(
            event,
            PastelItems.LESSER_POTION_PENDANT.get(),
            PastelItems.GREATER_POTION_PENDANT.get()
        );
        registerPickyPotionFillables(event, PastelItems.NIGHTFALLS_BLADE.get(), PastelItems.CONCEALING_OILS.get());
        registerSingleInkStorages(event, PastelItems.INK_FLASK.get());
        registerBrewColors(event, PastelItems.INFUSED_BEVERAGE.get());
        registerOptionalInkColor(event, PastelItems.PAINTBRUSH.get());
    }

    private static void coloredLeavesBlock(RegisterColorHandlersEvent.Block event) {
        coloredLeavesBlockColorProvider = new ToggleableBlockColorProvider(THAT_ONE_VANILLA_LEAF_PROVIDER);

        for (
            InkColor color : InkColors.all()
        ) {
            Block block = ColoredLeavesBlock.byColor(color);
            event.register(coloredLeavesBlockColorProvider, block);
        }
    }

    private static void coloredLeavesItem(RegisterColorHandlersEvent.Item event) {
        coloredLeavesItemColorProvider = new ToggleableItemColorProvider(THAT_ONE_VANILLA_ITEM_PROVIDER);

        for (
            InkColor color : InkColors.all()
        ) {
            Block block = ColoredLeavesBlock.byColor(color);
            event.register(coloredLeavesItemColorProvider, block);
        }
    }

    private static void amaranthBlock(RegisterColorHandlersEvent.Block event) {
        amaranthCropBlockColorProvider = new ToggleableBlockColorProvider(THE_OTHER_ONE_AKA_GRASS);
        amaranthBushelBlockColorProvider = new ToggleableBlockColorProvider(THE_OTHER_ONE_AKA_GRASS);
        event.register(amaranthCropBlockColorProvider, PastelBlocks.AMARANTH.get());
        event.register(amaranthBushelBlockColorProvider, PastelBlocks.AMARANTH_BUSHEL.get());
        event.register(amaranthBushelBlockColorProvider, PastelBlocks.POTTED_AMARANTH_BUSHEL.get());
    }

    private static void amaranthItem(RegisterColorHandlersEvent.Item event) {
        amaranthCropItemColorProvider = new ToggleableItemColorProvider(YES_THERE_IS_ANOTHER_ITEM_ONE_TOO);
        amaranthBushelItemColorProvider = new ToggleableItemColorProvider(YES_THERE_IS_ANOTHER_ITEM_ONE_TOO);
        event.register(amaranthCropItemColorProvider, PastelBlocks.AMARANTH.get());
        event.register(amaranthBushelItemColorProvider, PastelBlocks.AMARANTH_BUSHEL.get());
    }

    private static void registerSingleInkStorages(RegisterColorHandlersEvent.Item event, Item... items) {
        event.register((stack, tintIndex) -> {
            if (tintIndex == 1) {
                InkFlaskItem i = (InkFlaskItem) stack.getItem();
                SingleInkStorage storage = i.getEnergyStorage(stack);
                return FastColor.ARGB32.opaque(storage.getStoredColor().getColorInt());
            }
            return -1;
        }, items);
    }

    private static void registerPickyPotionFillables(RegisterColorHandlersEvent.Item event, Item... items) {
        event.register((stack, tintIndex) -> {
            if (tintIndex == 1) {
                List<InkPoweredStatusEffectInstance> effects = InkPoweredStatusEffectInstance.getEffects(stack);
                if (!effects.isEmpty()) {
                    return FastColor.ARGB32.opaque(effects.getFirst().getColor());
                }
            }
            return -1;
        }, items);
    }

    private static void registerPotionFillables(RegisterColorHandlersEvent.Item event, Item... items) {
        event.register((stack, tintIndex) -> {
            if (tintIndex > 0) {
                List<InkPoweredStatusEffectInstance> effects = InkPoweredStatusEffectInstance.getEffects(stack);
                if (effects.size() > tintIndex - 1) {
                    return FastColor.ARGB32.opaque(effects.get(tintIndex - 1).getColor());
                }
            }
            return -1;
        }, items);
    }

    private static void memoryBlock(RegisterColorHandlersEvent.Block event, Block memory) {
        event.register((state, world, pos, tintIndex) -> {
            if (world == null) {
                return 0x0;
            }
            if (world.getBlockEntity(pos) instanceof MemoryBlockEntity memoryBlockEntity) {
                return memoryBlockEntity.getEggColor(tintIndex);
            }
            return 0x0;
        }, memory);
    }

    private static void memoryItem(RegisterColorHandlersEvent.Item event, Block memory) {
        event.register((stack, tintIndex) -> {
            if (tintIndex == 2)
                return 0xFFFFFFFF;

            return FastColor.ARGB32.opaque(MemoryItem.getEggColor(stack, tintIndex));
        }, memory.asItem());
    }

    public static void registerBrewColors(RegisterColorHandlersEvent.Item event, Item brew) {
        event.register((stack, tintIndex) -> {
            if (tintIndex != 0) return FastColor.ARGB32.opaque(-1);
            return FastColor.ARGB32
                .opaque(
                    stack
                        .getOrDefault(PastelDataComponentTypes.INFUSED_BEVERAGE, InfusedBeverageComponent.DEFAULT)
                        .color()
                );
        }, brew);
    }

    public static void registerOptionalInkColor(RegisterColorHandlersEvent.Item event, Item item) {
        event.register((stack, tintIndex) -> {
            if (tintIndex == 1) {
                var color = stack.get(PastelDataComponentTypes.INK_COLOR);
                return FastColor.ARGB32.opaque(color == null ? -1 : color.getColorInt());
            }
            return -1;
        }, item);
    }

    public static void resetToggleableProviders() {
        coloredLeavesBlockColorProvider.setShouldApply(false);
        coloredLeavesItemColorProvider.setShouldApply(false);

        amaranthBushelBlockColorProvider.setShouldApply(false);
        amaranthBushelItemColorProvider.setShouldApply(false);
        amaranthCropBlockColorProvider.setShouldApply(false);
        amaranthCropItemColorProvider.setShouldApply(false);
    }

}
