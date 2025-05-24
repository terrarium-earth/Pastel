package de.dafuqs.revelationary;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.commands.arguments.blocks.BlockStateParser;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import java.util.Map;

public class RevelationaryNetworking {
	public static void register(PayloadRegistrar registrar) {
		registrar.playToClient(RevelationSync.ID, RevelationSync.CODEC, (payload, context) -> {
			try {
				RevelationRegistry.fromPacket(payload);
			} catch (Exception e) {
				Revelationary.logError("Error fetching results from sync packet");
				Revelationary.logException(e);
			}
			ClientRevelationHolder.cloakAll();
		});
	}

	public static void sendRevelations(ServerPlayer player) {
		PacketDistributor.sendToPlayer(player, RevelationRegistry.intoPacket());
	}

	public record RevelationSync(Object2ObjectOpenHashMap<ResourceLocation, ObjectArrayList<BlockState>> advToBlockStates,
                                 Object2ObjectOpenHashMap<BlockState, ResourceLocation> blockStateToAdv,
                                 Object2ObjectOpenHashMap<BlockState, BlockState> blockStateCloaks,
                                 Object2ObjectOpenHashMap<Block, Block> blockCloaks,
                                 Object2ObjectOpenHashMap<ResourceLocation, ObjectArrayList<Item>> advToItems,
                                 Object2ObjectOpenHashMap<Item, ResourceLocation> itemToAdv,
                                 Object2ObjectOpenHashMap<Item, Item> itemCloaks,
                                 Object2ObjectOpenHashMap<Block, MutableComponent> cloakedBlockNameTranslations,
                                 Object2ObjectOpenHashMap<Item, MutableComponent> cloakedItemNameTranslations) implements CustomPacketPayload {
		public static final StreamCodec<RegistryFriendlyByteBuf, RevelationSync> CODEC = CustomPacketPayload.codec(RevelationSync::write, RevelationSync::read);
		public static final CustomPacketPayload.Type<RevelationSync> ID = new Type<>(ResourceLocation.fromNamespaceAndPath(Revelationary.MOD_ID, "revelation_sync"));

		private static void writeText(RegistryFriendlyByteBuf buf, Component text) {
			ComponentSerialization.STREAM_CODEC.encode(buf, text);
		}

		private static Component readText(RegistryFriendlyByteBuf buf) {
			return ComponentSerialization.STREAM_CODEC.decode(buf);
		}

		public static RevelationSync read(RegistryFriendlyByteBuf buf) {
			/* Block States */

			final Object2ObjectOpenHashMap<Block, Block> blockCloaks = new Object2ObjectOpenHashMap<>(buf.readInt());
			final Object2ObjectOpenHashMap<BlockState, ResourceLocation> blockStateToAdv = new Object2ObjectOpenHashMap<>(buf.readInt());
			final Object2ObjectOpenHashMap<BlockState, BlockState> blockStateCloaks = new Object2ObjectOpenHashMap<>(buf.readInt());
			int blockEntries = buf.readInt();
			final Object2ObjectOpenHashMap<ResourceLocation, ObjectArrayList<BlockState>> advToBlockStates = new Object2ObjectOpenHashMap<>(blockEntries);
			for (int i = 0; i < blockEntries; i++) {
				ResourceLocation advancementIdentifier = buf.readResourceLocation();
				int blockStateCount = buf.readInt();
				ObjectArrayList<BlockState> advancementStates = new ObjectArrayList<>(blockStateCount);
				for (int j = 0; j < blockStateCount; j++) {
					try {
						BlockState sourceState = BlockStateParser.parseForBlock(BuiltInRegistries.BLOCK.asLookup(), buf.readUtf(), true).blockState();
						BlockState targetState = BlockStateParser.parseForBlock(BuiltInRegistries.BLOCK.asLookup(), buf.readUtf(), true).blockState();

						advancementStates.add(sourceState);
						blockStateToAdv.put(sourceState, advancementIdentifier);
						blockStateCloaks.put(sourceState, targetState);
						blockCloaks.putIfAbsent(sourceState.getBlock(), targetState.getBlock());
					} catch (CommandSyntaxException e) {
						Revelationary.logError(e.getMessage());
					}
				}
				advToBlockStates.put(advancementIdentifier, advancementStates);
			}

			/* Items */

			final Object2ObjectOpenHashMap<Item, ResourceLocation> itemToAdv = new Object2ObjectOpenHashMap<>(buf.readInt());
			Object2ObjectOpenHashMap<Item, Item> itemCloaks = new Object2ObjectOpenHashMap<>(buf.readInt());
			int itemEntries = buf.readInt();
			final Object2ObjectOpenHashMap<ResourceLocation, ObjectArrayList<Item>> advToItems = new Object2ObjectOpenHashMap<>(itemEntries); // preallocate this map too
			for (int i = 0; i < itemEntries; i++) {
				ResourceLocation advancementIdentifier = buf.readResourceLocation();
				int itemCount = buf.readInt();
				ObjectArrayList<Item> advancementItems = new ObjectArrayList<>(itemCount);
				for (int j = 0; j < itemCount; j++) {
					ResourceLocation sourceId = ResourceLocation.tryParse(buf.readUtf());
					ResourceLocation targetId = ResourceLocation.tryParse(buf.readUtf());
					Item sourceItem = BuiltInRegistries.ITEM.get(sourceId);
					Item targetItem = BuiltInRegistries.ITEM.get(targetId);

					advancementItems.add(sourceItem);
					itemToAdv.put(sourceItem, advancementIdentifier);
					itemCloaks.put(sourceItem, targetItem);
				}
				advToItems.put(advancementIdentifier, advancementItems);
			}

			/* Block Translations */
			int blockTranslations = buf.readInt();
			final Object2ObjectOpenHashMap<Block, MutableComponent> cloakedBlockNameTranslations = new Object2ObjectOpenHashMap<>(blockTranslations); // preallocate translations
			for (int i = 0; i < blockTranslations; i++) {
				Block block = BuiltInRegistries.BLOCK.get(buf.readResourceLocation());
				MutableComponent text = (MutableComponent) readText(buf);
				cloakedBlockNameTranslations.put(block, text);
			}

			/* Item Translations */
			int itemTranslations = buf.readInt();
			final Object2ObjectOpenHashMap<Item, MutableComponent> cloakedItemNameTranslations = new Object2ObjectOpenHashMap<>(itemTranslations); // preallocate translations
			for (int i = 0; i < itemTranslations; i++) {
				Item item = BuiltInRegistries.ITEM.get(buf.readResourceLocation());
				MutableComponent text = (MutableComponent) readText(buf);
				cloakedItemNameTranslations.put(item, text);
			}
			return new RevelationSync(advToBlockStates,
									  blockStateToAdv,
									  blockStateCloaks,
									  blockCloaks,
									  advToItems,
									  itemToAdv,
									  itemCloaks,
									  cloakedBlockNameTranslations,
									  cloakedItemNameTranslations);
		}

		public void write(RegistryFriendlyByteBuf buf) {
			// Block States
			buf.writeInt(blockCloaks.size());      // for preallocation on packet read
			buf.writeInt(blockStateToAdv.size());  // for preallocation on packet read
			buf.writeInt(blockStateCloaks.size()); // for preallocation on packet read
			buf.writeInt(advToBlockStates.size());
			for (Map.Entry<ResourceLocation, ObjectArrayList<BlockState>> advancementBlocks : advToBlockStates.entrySet()) {
				buf.writeResourceLocation(advancementBlocks.getKey());
				buf.writeInt(advancementBlocks.getValue().size());
				for (BlockState blockState : advancementBlocks.getValue()) {
					buf.writeUtf(BlockStateParser.serialize(blockState));
					buf.writeUtf(BlockStateParser.serialize(blockStateCloaks.get(blockState)));
				}
			}

			// Items
			buf.writeInt(itemToAdv.size());  // for preallocation on packet read
			buf.writeInt(itemCloaks.size()); // for preallocation on packet read
			buf.writeInt(advToItems.size());
			for (Map.Entry<ResourceLocation, ObjectArrayList<Item>> advancementItems : advToItems.entrySet()) {
				buf.writeResourceLocation(advancementItems.getKey());
				buf.writeInt(advancementItems.getValue().size());
				for (Item item : advancementItems.getValue()) {
					buf.writeUtf(BuiltInRegistries.ITEM.getKey(item).toString());
					buf.writeUtf(BuiltInRegistries.ITEM.getKey(itemCloaks.get(item)).toString());
				}
			}

			// Block Translations
			buf.writeInt(cloakedBlockNameTranslations.size());
			for (Map.Entry<Block, MutableComponent> blockTranslation : cloakedBlockNameTranslations.entrySet()) {
				buf.writeResourceLocation(BuiltInRegistries.BLOCK.getKey(blockTranslation.getKey()));
				writeText(buf, blockTranslation.getValue());
			}

			// Item Translations
			buf.writeInt(cloakedItemNameTranslations.size());
			for (Map.Entry<Item, MutableComponent> itemTranslation : cloakedItemNameTranslations.entrySet()) {
				buf.writeResourceLocation(BuiltInRegistries.ITEM.getKey(itemTranslation.getKey()));
				writeText(buf, itemTranslation.getValue());
			}
		}

		@Override
		public Type<RevelationSync> type() {
			return ID;
		}
	}
}
