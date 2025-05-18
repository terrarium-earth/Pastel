package de.dafuqs.revelationary;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.dafuqs.revelationary.api.advancements.AdvancementHelper;
import de.dafuqs.revelationary.api.revelations.RevelationAware;
import de.dafuqs.revelationary.config.RevelationaryConfig;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.commands.arguments.blocks.BlockStateParser;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RevelationRegistry {
	private static Object2ObjectOpenHashMap<ResourceLocation, ObjectArrayList<BlockState>> advToBlockStates = new Object2ObjectOpenHashMap<>();
	private static Object2ObjectOpenHashMap<BlockState, ResourceLocation> blockStateToAdv = new Object2ObjectOpenHashMap<>();
	private static Object2ObjectOpenHashMap<BlockState, BlockState> blockStateCloaks = new Object2ObjectOpenHashMap<>();
	private static Object2ObjectOpenHashMap<Block, Block> blockCloaks = new Object2ObjectOpenHashMap<>();

	private static Object2ObjectOpenHashMap<ResourceLocation, ObjectArrayList<Item>> advToItems = new Object2ObjectOpenHashMap<>();
	private static Object2ObjectOpenHashMap<Item, ResourceLocation> itemToAdv = new Object2ObjectOpenHashMap<>();
	private static Object2ObjectOpenHashMap<Item, Item> itemCloaks = new Object2ObjectOpenHashMap<>();
	
	private static Object2ObjectOpenHashMap<Block, MutableComponent> cloakedBlockNameTranslations = new Object2ObjectOpenHashMap<>();
	private static Object2ObjectOpenHashMap<Item, MutableComponent> cloakedItemNameTranslations = new Object2ObjectOpenHashMap<>();
	
	public static MutableComponent getTranslationString(Item item) {
		if (cloakedItemNameTranslations.containsKey(item)) {
			return cloakedItemNameTranslations.get(item);
		}
		boolean isBlockItem = item instanceof BlockItem;
		if(isBlockItem && !RevelationaryConfig.get().NameForUnrevealedBlocks.isEmpty()) {
			return Component.translatable(RevelationaryConfig.get().NameForUnrevealedBlocks);
		}
		if(!isBlockItem && !RevelationaryConfig.get().NameForUnrevealedItems.isEmpty()) {
			return Component.translatable(RevelationaryConfig.get().NameForUnrevealedItems);
		}
		if(RevelationaryConfig.get().UseTargetBlockOrItemNameInsteadOfScatter) {
			return Component.translatable(itemCloaks.get(item).getDescriptionId());
		}
		// Get the localized name of the item and scatter it using §k to make it unreadable
		return Component.literal("§k" + Language.getInstance().getOrDefault(item.getDescriptionId()));
	}
	
	public static MutableComponent getTranslationString(Block block) {
		if (cloakedBlockNameTranslations.containsKey(block)) {
			return cloakedBlockNameTranslations.get(block);
		}
		if(!RevelationaryConfig.get().NameForUnrevealedBlocks.isEmpty()) {
			return Component.translatable(RevelationaryConfig.get().NameForUnrevealedBlocks);
		}
		if(RevelationaryConfig.get().UseTargetBlockOrItemNameInsteadOfScatter) {
			return blockCloaks.get(block).getName();
		}
		// Get the localized name of the block and scatter it using §k to make it unreadable
		return Component.literal("§k" + Language.getInstance().getOrDefault(block.getDescriptionId()));
	}

	public static void trim() {
		advToBlockStates.trim();
		advToItems.trim();
		blockStateCloaks.trim();
		itemCloaks.trim();
		cloakedBlockNameTranslations.trim();
		cloakedItemNameTranslations.trim();
	}

	public static void deepTrim() {
		trim();
		for (ObjectArrayList<BlockState> blockList : advToBlockStates.values()) blockList.trim();
		for (ObjectArrayList<Item> itemList : advToItems.values()) itemList.trim();
	}
	
	private static final Set<RevelationAware> revelationAwares = new HashSet<>();
	
	public static void registerRevelationAware(RevelationAware revelationAware) {
		revelationAwares.add(revelationAware);
	}

	private static void prepareForRevelationAwaresRegistration(int amount) {
		// Items
		advToItems.ensureCapacity(advToItems.size() + amount);
		itemToAdv.ensureCapacity(itemToAdv.size() + amount);
		itemCloaks.ensureCapacity(itemCloaks.size() + amount);
		// Translations
		cloakedBlockNameTranslations.ensureCapacity(cloakedBlockNameTranslations.size() + amount);
		cloakedItemNameTranslations.ensureCapacity(cloakedItemNameTranslations.size() + amount);
	}

	public static void addRevelationAwares() {
		prepareForRevelationAwaresRegistration(revelationAwares.size());
		for (RevelationAware revelationAware : revelationAwares) {
			ResourceLocation advancementIdentifier = revelationAware.getCloakAdvancementIdentifier();

			Map<BlockState, BlockState> blockStateCloaks = revelationAware.getBlockStateCloaks();
			ObjectArrayList<BlockState> sourceBlockStates = new ObjectArrayList<>(blockStateCloaks.size());
			ObjectArrayList<BlockState> targetBlockStates = new ObjectArrayList<>(blockStateCloaks.size());
			for (Map.Entry<BlockState, BlockState> states : blockStateCloaks.entrySet()) {
				BlockState sourceBlockState = states.getKey();
				if (!sourceBlockState.isAir()) {
					sourceBlockStates.add(sourceBlockState);
					targetBlockStates.add(states.getValue());
				} else {
					Revelationary.logError("Trying to register invalid block cloak. Advancement: " + advancementIdentifier
							+ " Source Block: " + BuiltInRegistries.BLOCK.getKey(sourceBlockState.getBlock())
							+ " Target Block: " + BuiltInRegistries.BLOCK.getKey(states.getValue().getBlock()));
				}
			}
			registerBlockStatesForIdentifier(advancementIdentifier, sourceBlockStates, targetBlockStates);

			Tuple<Item, Item> item = revelationAware.getItemCloak();
			if (item != null) {
				registerItem(advancementIdentifier, item.getA(), item.getB());
			}
			
			Tuple<Block, MutableComponent> blockTranslation = revelationAware.getCloakedBlockTranslation();
			if (blockTranslation != null) {
				registerBlockTranslation(blockTranslation.getA(), blockTranslation.getB());
			}
			Tuple<Item, MutableComponent> itemTranslation = revelationAware.getCloakedItemTranslation();
			if (itemTranslation != null) {
				registerItemTranslation(itemTranslation.getA(), itemTranslation.getB());
			}
		}
	}
	
	public static void registerFromJson(JsonObject jsonObject) {
		ResourceLocation advancementIdentifier = ResourceLocation.tryParse(GsonHelper.getAsString(jsonObject, "advancement"));
		
		if (jsonObject.has("block_states")) {
			JsonObject blockStates = jsonObject.get("block_states").getAsJsonObject();
			ObjectArrayList<BlockState> sourceBlockStates = new ObjectArrayList<>(blockStates.size());
			ObjectArrayList<BlockState> targetBlockStates = new ObjectArrayList<>(blockStates.size());
			for (Map.Entry<String, JsonElement> stateEntry : jsonObject.get("block_states").getAsJsonObject().entrySet()) {
				try {
					BlockState sourceBlockState = BlockStateParser.parseForBlock(BuiltInRegistries.BLOCK.asLookup(), stateEntry.getKey(), true).blockState();
					BlockState targetBlockState = BlockStateParser.parseForBlock(BuiltInRegistries.BLOCK.asLookup(), stateEntry.getValue().getAsString(), true).blockState();
					if (!sourceBlockState.isAir()) {
						sourceBlockStates.add(sourceBlockState);
						targetBlockStates.add(targetBlockState);
					} else {
						Revelationary.logError("Trying to register invalid block cloak. Advancement: " + advancementIdentifier
								+ " Source Block: " + BuiltInRegistries.BLOCK.getKey(sourceBlockState.getBlock())
								+ " Target Block: " + BuiltInRegistries.BLOCK.getKey(targetBlockState.getBlock()));
					}
				} catch (Exception e) {
					Revelationary.logError("Error parsing block state: " + e);
				}
			}
			registerBlockStatesForIdentifier(advancementIdentifier, sourceBlockStates, targetBlockStates);
		}
		if (jsonObject.has("items")) {
			for (Map.Entry<String, JsonElement> itemEntry : jsonObject.get("items").getAsJsonObject().entrySet()) {
				ResourceLocation sourceId = ResourceLocation.tryParse(itemEntry.getKey());
				ResourceLocation targetId = ResourceLocation.tryParse(itemEntry.getValue().getAsString());
				
				Item sourceItem = BuiltInRegistries.ITEM.get(sourceId);
				Item targetItem = BuiltInRegistries.ITEM.get(targetId);
				
				registerItem(advancementIdentifier, sourceItem, targetItem);
			}
		}
		if (jsonObject.has("block_name_replacements")) {
			for (Map.Entry<String, JsonElement> blockNameEntry : jsonObject.get("block_name_replacements").getAsJsonObject().entrySet()) {
				ResourceLocation sourceId = ResourceLocation.tryParse(blockNameEntry.getKey());
				MutableComponent targetText = Component.translatable(blockNameEntry.getValue().getAsString());
				
				Block sourceBlock = BuiltInRegistries.BLOCK.get(sourceId);
				cloakedBlockNameTranslations.put(sourceBlock, targetText);
				
				Item blockItem = sourceBlock.asItem();
				if (blockItem != null && blockItem != Items.AIR) {
					cloakedItemNameTranslations.put(blockItem, targetText);
				}
			}
		}
		if (jsonObject.has("item_name_replacements")) {
			for (Map.Entry<String, JsonElement> itemNameEntry : jsonObject.get("item_name_replacements").getAsJsonObject().entrySet()) {
				ResourceLocation sourceId = ResourceLocation.tryParse(itemNameEntry.getKey());
				MutableComponent targetText = Component.translatable(itemNameEntry.getValue().getAsString());
				
				Item sourceItem = BuiltInRegistries.ITEM.get(sourceId);
				cloakedItemNameTranslations.put(sourceItem, targetText);
			}
		}
	}
	
	// BLOCKS
	private static void registerBlockStatesForIdentifier(ResourceLocation advancementIdentifier, ObjectArrayList<BlockState> sourceBlockStates, ObjectArrayList<BlockState> targetBlockStates) {
		if (sourceBlockStates.size() != targetBlockStates.size()) throw new IllegalArgumentException("Unequal sizes of sourceBlockStates and targetBlockStates arrays");
		int sz = sourceBlockStates.size();
		if(advToBlockStates.containsKey(advancementIdentifier)) {
			ObjectArrayList<BlockState> blockStates = advToBlockStates.get(advancementIdentifier);
			blockStates.ensureCapacity(blockStates.size() + sz); // preallocate
			blockStates.addAll(sourceBlockStates);
		} else advToBlockStates.put(advancementIdentifier, sourceBlockStates);

		blockStateCloaks.ensureCapacity(blockStateCloaks.size() + sz);
		blockStateToAdv.ensureCapacity(blockStateToAdv.size() + sz);
		// assume amount of blocks is roughly equal to amount of blockstates (in real case scenario)
		blockCloaks.ensureCapacity(blockCloaks.size() + sz);
		// assume amount of items is roughly equal to amount of blockstates (in real case scenario)
		ObjectArrayList<Item> sourceItems = new ObjectArrayList<>(sz);
		ObjectArrayList<Item> targetItems = new ObjectArrayList<>(sz);
		for (int i = 0; i < sz; i++) {
			BlockState sourceBlockState = sourceBlockStates.get(i);
			BlockState targetBlockState = targetBlockStates.get(i);
			blockStateCloaks.put(sourceBlockState, targetBlockState);
			blockStateToAdv.put(sourceBlockState, advancementIdentifier);
			blockCloaks.put(sourceBlockState.getBlock(), targetBlockState.getBlock());
			Item sourceBlockItem = sourceBlockState.getBlock().asItem();
			Item targetBlockItem = targetBlockState.getBlock().asItem();
			if (sourceBlockItem != Items.AIR && targetBlockItem != Items.AIR) {
				sourceItems.add(sourceBlockItem);
				targetItems.add(targetBlockItem);
			}
		}
		registerItemsForIdentifier(advancementIdentifier, sourceItems, targetItems);
	}
	
	private static void registerBlockTranslation(Block sourceBlock, MutableComponent targetTranslation) {
		cloakedBlockNameTranslations.put(sourceBlock, targetTranslation);
	}
	
	public static boolean hasCloak(BlockState blockState) {
		return blockStateCloaks.containsKey(blockState);
	}
	
	public static boolean isVisibleTo(BlockState state, Player player) {
		return AdvancementHelper.hasAdvancement(player, blockStateToAdv.getOrDefault(state, null));
	}
	
	public static @NotNull Collection<BlockState> getRevealedBlockStates(ResourceLocation advancement) {
		List<BlockState> blockStates = new ArrayList<>();
		if (advToBlockStates.containsKey(advancement)) {
			for (Object entry : advToBlockStates.get(advancement)) {
				if (entry instanceof BlockState blockState) {
					blockStates.add(blockState);
				}
			}
		}
		return blockStates;
	}
	
	public static Map<ResourceLocation, List<BlockState>> getBlockStateEntries() {
		// fighting invariance of java generic types
        return (Map<ResourceLocation, List<BlockState>>) (Map<?, ?>) advToBlockStates;
	}
	
	public static List<BlockState> getBlockStateEntries(ResourceLocation advancement) {
		return advToBlockStates.getOrDefault(advancement, ObjectArrayList.of());
	}
	
	public static List<Block> getBlockEntries() {
		List<Block> blocks = new ArrayList<>();
		for (List<BlockState> states : advToBlockStates.values()) {
			for (BlockState state : states) {
				Block block = state.getBlock();
				if (!blocks.contains(block)) {
					blocks.add(block);
				}
			}
		}
		return blocks;
	}
	
	public static List<Block> getBlockEntries(ResourceLocation advancement) {
		if (advToBlockStates.containsKey(advancement)) {
			List<BlockState> states = advToBlockStates.get(advancement);
			List<Block> blocks = new ArrayList<>();
			for (BlockState state : states) {
				Block block = state.getBlock();
				if (!blocks.contains(block)) {
					blocks.add(block);
				}
			}
			return blocks;
		} else {
			return new ArrayList<>();
		}
	}
	
	// ITEMS
	private static void registerItem(ResourceLocation advancementIdentifier, Item sourceItem, Item targetItem) {
		if(sourceItem == Items.AIR || targetItem == Items.AIR) {
			Revelationary.logError("Trying to register invalid item cloak. Advancement: " + advancementIdentifier
					+ " Source Item: " + BuiltInRegistries.ITEM.getKey(sourceItem)
					+ " Target Item: " + BuiltInRegistries.ITEM.getKey(targetItem));
			return;
		}
		
		if (advToItems.containsKey(advancementIdentifier)) {
			ObjectArrayList<Item> list = advToItems.get(advancementIdentifier);
			if (list.contains(sourceItem)) {
				return;
			}
			list.add(sourceItem);
		} else {
			ObjectArrayList<Item> list = new ObjectArrayList<>();
			list.add(sourceItem);
			advToItems.put(advancementIdentifier, list);
		}
		itemCloaks.put(sourceItem, targetItem);
		itemToAdv.put(sourceItem, advancementIdentifier);
	}

	private static void registerItemsForIdentifier(ResourceLocation advancementIdentifier, ObjectArrayList<Item> sourceItems, ObjectArrayList<Item> targetItems) {
		if (sourceItems.size() != targetItems.size()) throw new IllegalArgumentException("Unequal sizes of sourceItems and targetItems arrays");
		int sz = sourceItems.size();
		if (advToItems.containsKey(advancementIdentifier)) {
			ObjectArrayList<Item> items = advToItems.get(advancementIdentifier);
			items.ensureCapacity(items.size() + sz);
			items.addAll(sourceItems);
		} else advToItems.put(advancementIdentifier, sourceItems);

		itemCloaks.ensureCapacity(itemCloaks.size() + sz);
		itemToAdv.ensureCapacity(itemToAdv.size() + sz);
		for (int i = 0; i < sz; i++) {
			Item sourceItem = sourceItems.get(i);
			itemCloaks.put(sourceItem, targetItems.get(i));
			itemToAdv.put(sourceItem, advancementIdentifier);
		}
	}
	
	private static void registerItemTranslation(Item sourceItem, MutableComponent targetTranslation) {
		cloakedItemNameTranslations.put(sourceItem, targetTranslation);
	}
	
	public static boolean hasCloak(Item item) {
		return itemCloaks.containsKey(item);
	}
	
	@Nullable
	public static Item getCloak(Item item) {
		return itemCloaks.getOrDefault(item, null);
	}
	
	public static boolean isVisibleTo(Item item, Player player) {
		return AdvancementHelper.hasAdvancement(player, itemToAdv.getOrDefault(item, null));
	}
	
	@Nullable
	public static BlockState getCloak(BlockState blockState) {
		return blockStateCloaks.getOrDefault(blockState, null);
	}
	
	public static @NotNull Collection<Item> getRevealedItems(ResourceLocation advancement) {
		if (advToItems.containsKey(advancement)) return advToItems.get(advancement).clone();
		return ObjectArrayList.of();
	}
	
	public static Map<ResourceLocation, List<Item>> getItemEntries() {
		// fighting invariance of java generic types
		return (Map<ResourceLocation, List<Item>>) (Map<?,?>) advToItems;
	}
	
	public static List<Item> getItemEntries(ResourceLocation advancement) {
		return advToItems.getOrDefault(advancement, ObjectArrayList.of());
	}

	public static void fromPacket(RevelationaryNetworking.RevelationSync syncPacket) {
		advToBlockStates = syncPacket.advToBlockStates();
		blockStateToAdv = syncPacket.blockStateToAdv();
		blockStateCloaks = syncPacket.blockStateCloaks();
		blockCloaks = syncPacket.blockCloaks();
		advToItems = syncPacket.advToItems();
		itemToAdv = syncPacket.itemToAdv();
		itemCloaks = syncPacket.itemCloaks();
		cloakedBlockNameTranslations = syncPacket.cloakedBlockNameTranslations();
		cloakedItemNameTranslations = syncPacket.cloakedItemNameTranslations();

		RevelationRegistry.addRevelationAwares();
		RevelationRegistry.deepTrim();
	}

	public static RevelationaryNetworking.RevelationSync intoPacket() {
		return new RevelationaryNetworking.RevelationSync(advToBlockStates,
													   blockStateToAdv,
													   blockStateCloaks,
													   blockCloaks,
													   advToItems,
													   itemToAdv,
													   itemCloaks,
													   cloakedBlockNameTranslations,
													   cloakedItemNameTranslations);
	}
}