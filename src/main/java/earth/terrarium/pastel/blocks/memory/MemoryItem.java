package earth.terrarium.pastel.blocks.memory;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.components.MemoryComponent;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.PastelDataComponentTypes;
import earth.terrarium.pastel.registries.PastelRecipeTypes;
import net.minecraft.ChatFormatting;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab.Output;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class MemoryItem extends BlockItem {

    // There are a few entities in vanilla that do not have a corresponding spawn egg
    // therefore to make it nicer we specify custom colors for them here
    private static final HashMap<EntityType<?>, Tuple<Integer, Integer>> customColors = new HashMap<>() {
        {
            put(EntityType.BAT, new Tuple<>(0x463d2b, 0x191307));
            put(EntityType.SNOW_GOLEM, new Tuple<>(0xc9cbcf, 0xa26e28));
            put(EntityType.WITHER, new Tuple<>(0x101211, 0x3e4140));
            put(EntityType.ILLUSIONER, new Tuple<>(0x29578d, 0x4b4e4f));
            put(EntityType.ENDER_DRAGON, new Tuple<>(0x111111, 0x856c8f));
            put(EntityType.IRON_GOLEM, new Tuple<>(0x9a9a9a, 0x8b7464));
        }
    };

    public MemoryItem(Block block, Properties settings) {
        super(block, settings);
    }

    public static MemoryComponent getMemory(ItemStack stack) {
        return stack.getOrDefault(PastelDataComponentTypes.MEMORY, MemoryComponent.DEFAULT);
    }

    public static ItemStack getMemoryForEntity(LivingEntity entity) {
        CompoundTag tag = new CompoundTag();
        entity.saveAsPassenger(tag);
        tag.remove("Pos"); // yeet everything that we don't need and could interfere when spawning
        tag.remove("OnGround");
        tag.remove("Rotation");
        tag.remove("Motion");
        tag.remove("FallDistance");
        tag.remove("InLove");
        tag.remove("UUID");
        tag.remove("Health");
        tag.remove("Fire");
        tag.remove("HurtByTimestamp");
        tag.remove("DeathTime");
        tag.remove("AbsorptionAmount");
        tag.remove("Air");
        tag.remove("FallFlying");
        tag.remove("PortalCooldown");
        tag.remove("HurtTime");

        ItemStack stack = PastelBlocks.MEMORY
            .get()
            .asItem()
            .getDefaultInstance();
        stack.set(DataComponents.ENTITY_DATA, CustomData.of(tag));
        return stack;
    }

    public static ItemStack getForEntityType(EntityType<?> entityType, int ticksToManifest) {
        ItemStack stack = PastelBlocks.MEMORY
            .get()
            .asItem()
            .getDefaultInstance();

        stack
            .set(
                PastelDataComponentTypes.MEMORY,
                new MemoryComponent.Builder(MemoryComponent.DEFAULT)
                    .ticksToManifest(
                        ticksToManifest
                    )
                    .build()
            );

        CompoundTag entityCompound = new CompoundTag();
        entityCompound
            .putString(
                "id",
                BuiltInRegistries.ENTITY_TYPE
                    .getKey(entityType)
                    .toString()
            );
        stack.set(DataComponents.ENTITY_DATA, CustomData.of(entityCompound));

        return stack;
    }

    public static CompoundTag getEntityData(ItemStack stack) {
        return stack
            .getOrDefault(DataComponents.ENTITY_DATA, CustomData.EMPTY)
            .copyTag();
    }

    public static Optional<EntityType<?>> getEntityType(ItemStack stack) {
        var data = getEntityData(stack);
        if (!data.contains("id", Tag.TAG_STRING)) return Optional.empty();
        return EntityType.byString(data.getString("id"));
    }

    public static @Nullable Component getMemoryEntityCustomName(ItemStack stack, HolderLookup.Provider drm) {
        var data = getEntityData(stack);
        if (!data.contains("CustomName", Tag.TAG_STRING)) return null;
        return Component.Serializer.fromJson(data.getString("CustomName"), drm);
    }

    public static int getTicksToManifest(ItemStack stack) {
        return getMemory(stack).ticksToManifest();
    }

    public static void setTicksToManifest(@NotNull ItemStack itemStack, int newTicksToManifest) {
        itemStack
            .update(
                PastelDataComponentTypes.MEMORY,
                MemoryComponent.DEFAULT,
                comp -> new MemoryComponent.Builder(
                    comp
                )
                    .ticksToManifest(newTicksToManifest)
                    .build()
            );
    }

    public static void setSpawnAsAdult(@NotNull ItemStack itemStack, boolean spawnAsAdult) {
        itemStack
            .update(
                PastelDataComponentTypes.MEMORY,
                MemoryComponent.DEFAULT,
                comp -> new MemoryComponent.Builder(
                    comp
                )
                    .spawnAsAdult(spawnAsAdult)
                    .build()
            );
    }

    public static void markAsBrokenPromise(ItemStack itemStack, boolean isBrokenPromise) {
        itemStack
            .update(
                PastelDataComponentTypes.MEMORY,
                MemoryComponent.DEFAULT,
                comp -> new MemoryComponent.Builder(
                    comp
                )
                    .brokenPromise(isBrokenPromise)
                    .build()
            );
    }

    public static boolean isBrokenPromise(ItemStack stack) {
        return getMemory(stack).brokenPromise();
    }

    public static boolean isUnrecognizable(ItemStack stack) {
        return getMemory(stack).unrecognizable();
    }

    public static void makeUnrecognizable(@NotNull ItemStack itemStack) {
        itemStack
            .update(
                PastelDataComponentTypes.MEMORY,
                MemoryComponent.DEFAULT,
                comp -> new MemoryComponent.Builder(
                    comp
                )
                    .unrecognizable()
                    .build()
            );
    }

    public static int getEggColor(ItemStack stack, int tintIndex) {
        if (stack.has(PastelDataComponentTypes.MEMORY) && !isUnrecognizable(stack)) {
            var entityType = getEntityType(stack);
            if (entityType.isPresent()) {
                EntityType<?> type = entityType.get();
                if (customColors.containsKey(type)) {
                    // statically defined: fetch from map
                    return tintIndex == 0
                        ? customColors
                            .get(type)
                            .getA()
                        : customColors
                            .get(type)
                            .getB();
                } else {
                    // dynamically defined: fetch from spawn egg
                    SpawnEggItem spawnEggItem = SpawnEggItem.byId(entityType.get());
                    if (spawnEggItem != null) {
                        return spawnEggItem.getColor(tintIndex);
                    }
                }
            }
        }

        return tintIndex == 0 ? 0x222222 : 0xDDDDDD;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        super.appendHoverText(stack, context, tooltip, type);

        getEntityType(stack)
            .ifPresentOrElse(
                entityType -> {
                    if (isUnrecognizable(stack)) {
                        tooltip
                            .add(
                                Component
                                    .translatable("item.pastel.memory.tooltip.unrecognizable_entity_type")
                                    .withStyle(ChatFormatting.GRAY)
                            );
                    } else {
                        boolean isBrokenPromise = isBrokenPromise(stack);
                        Component customName = getMemoryEntityCustomName(stack, context.registries());
                        if (isBrokenPromise) {
                            if (customName == null) {
                                tooltip
                                    .add(
                                        Component
                                            .translatable(
                                                "item.pastel.memory.tooltip.entity_type_broken_promise",
                                                entityType.getDescription()
                                            )
                                    );
                            } else {
                                tooltip
                                    .add(
                                        Component
                                            .translatable("item.pastel.memory.tooltip.named_broken_promise")
                                            .append(customName)
                                            .withStyle(ChatFormatting.WHITE, ChatFormatting.ITALIC)
                                    );
                            }
                        } else {
                            if (customName == null) {
                                tooltip
                                    .add(
                                        Component
                                            .translatable(
                                                "item.pastel.memory.tooltip.entity_type",
                                                entityType.getDescription()
                                            )
                                    );
                            } else {
                                tooltip
                                    .add(
                                        Component
                                            .translatable("item.pastel.memory.tooltip.named")
                                            .append(customName)
                                            .withStyle(ChatFormatting.WHITE, ChatFormatting.ITALIC)
                                    );
                            }
                        }
                    }

                    int ticksToHatch = getTicksToManifest(stack);
                    if (ticksToHatch <= 0) {
                        tooltip
                            .add(
                                Component
                                    .translatable("item.pastel.memory.tooltip.does_not_manifest")
                                    .withStyle(ChatFormatting.GRAY)
                            );
                    } else if (ticksToHatch > 100) {
                        tooltip
                            .add(
                                Component
                                    .translatable("item.pastel.memory.tooltip.extra_long_time_to_manifest")
                                    .withStyle(ChatFormatting.GRAY)
                            );
                    } else if (ticksToHatch > 20) {
                        tooltip
                            .add(
                                Component
                                    .translatable("item.pastel.memory.tooltip.long_time_to_manifest")
                                    .withStyle(ChatFormatting.GRAY)
                            );
                    } else if (ticksToHatch > 5) {
                        tooltip
                            .add(
                                Component
                                    .translatable("item.pastel.memory.tooltip.medium_time_to_manifest")
                                    .withStyle(ChatFormatting.GRAY)
                            );
                    } else {
                        tooltip
                            .add(
                                Component
                                    .translatable("item.pastel.memory.tooltip.short_time_to_manifest")
                                    .withStyle(ChatFormatting.GRAY)
                            );
                    }
                },
                () -> tooltip
                    .add(
                        Component
                            .translatable("item.pastel.memory.tooltip.unset_entity_type")
                            .withStyle(ChatFormatting.GRAY)
                    )
            );
    }

    public static void appendEntries(HolderLookup.Provider lookup, Output entries) {
        // adding all memories that have spirit instiller recipes
        Set<MemoryComponent> encountered = new HashSet<>();
        //TODO does this work on dedicated servers?
        if (PastelCommon.getSidedServer() != null) {
            Item memoryItem = PastelBlocks.MEMORY
                .get()
                .asItem();
            for (
                var recipe : PastelCommon
                    .getSidedServer()
                    .getRecipeManager()
                    .getAllRecipesFor(PastelRecipeTypes.SPIRIT_INSTILLING)
            ) {
                ItemStack output = recipe
                    .value()
                    .getResultItem(lookup);
                var memory = output.get(PastelDataComponentTypes.MEMORY);
                if (output.is(memoryItem) && memory != null && !encountered.contains(memory)) {
                    entries.accept(output);
                    encountered.add(memory);
                }
            }
        }
    }

}
