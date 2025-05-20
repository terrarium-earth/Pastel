package de.dafuqs.spectrum.blocks.pastel_network.nodes;

import com.google.common.base.Predicates;
import de.dafuqs.spectrum.SpectrumCommon;
import de.dafuqs.spectrum.api.block.FilterConfigurable;
import de.dafuqs.spectrum.api.item.StampDataCategory;
import de.dafuqs.spectrum.api.item.Stampable;
import de.dafuqs.spectrum.api.pastel.PastelUpgradeSignature;
import de.dafuqs.spectrum.api.pastel.PastelUpgradeable;
import de.dafuqs.spectrum.blocks.pastel_network.Pastel;
import de.dafuqs.spectrum.blocks.pastel_network.network.NodeRemovalReason;
import de.dafuqs.spectrum.blocks.pastel_network.network.PastelNetwork;
import de.dafuqs.spectrum.blocks.pastel_network.network.PastelTransmissionLogic;
import de.dafuqs.spectrum.blocks.pastel_network.network.ServerPastelNetwork;
import de.dafuqs.spectrum.blocks.pastel_network.network.ServerPastelNetworkManager;
import de.dafuqs.spectrum.helpers.BlockReference;
import de.dafuqs.spectrum.helpers.SpectrumColorHelper;
import de.dafuqs.spectrum.inventories.FilteringScreenHandler;
import de.dafuqs.spectrum.networking.s2c_payloads.PastelNetworkEdgeSyncPayload;
import de.dafuqs.spectrum.progression.SpectrumAdvancementCriteria;
import de.dafuqs.spectrum.registries.SpectrumBlockEntities;
import de.dafuqs.spectrum.registries.SpectrumItemTags;
import de.dafuqs.spectrum.registries.SpectrumPastelUpgrades;
import de.dafuqs.spectrum.registries.SpectrumRegistries;
import de.dafuqs.spectrum.registries.SpectrumSoundEvents;
import de.dafuqs.spectrum.registries.SpectrumStampDataCategories;
import net.fabricmc.fabric.api.lookup.v1.block.BlockApiCache;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.minecraft.world.item.ItemStack;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

public class PastelNodeBlockEntity extends BlockEntity implements FilterConfigurable, ExtendedScreenHandlerFactory<FilterConfigurable.ExtendedData>, PastelUpgradeable, Stampable {
	
	public static final int MAX_FILTER_SLOTS = 25;
	public static final int SLOTS_PER_ROW = 5;
	public static final int DEFAULT_FILTER_SLOT_ROWS = 1;
	public static final int RANGE = 12;
	
	@NotNull
	protected UUID nodeId = UUID.randomUUID();
	protected Optional<UUID> networkUUID = Optional.empty();
	protected Optional<PastelUpgradeSignature> outerRing, innerRing, redstoneRing;
	protected Optional<DyeColor> color = Optional.empty();
	
	// TODO: move these to ServerPastelNetwork?
	protected long lastTransferTick = 0;
	protected final long cachedRedstonePowerTick = 0;
	protected boolean cachedUnpowered = true;
	protected PastelNetwork.NodePriority priority = PastelNetwork.NodePriority.GENERIC;
	protected long itemCountUnderway = 0;
	
	
	// upgrade impl stuff
	protected boolean lit, triggerTransfer, triggered, waiting, lamp, sensor, updated;
	protected int transferCount = PastelTransmissionLogic.DEFAULT_MAX_TRANSFER_AMOUNT;
	protected int transferTime = PastelTransmissionLogic.DEFAULT_TRANSFER_TICKS_PER_NODE;
	protected int filterSlotRows = DEFAULT_FILTER_SLOT_ROWS;
	
	protected BlockApiCache<Storage<ItemStack>, Direction> connectedStorageCache = null;
	protected Direction cachedDirection = null;
	
	private final List<ItemStack> filterItems;
	float rotationTarget, crystalRotation, lastRotationTarget, heightTarget, crystalHeight, lastHeightTarget, alphaTarget, ringAlpha, lastAlphaTarget;
	long creationStamp = -1, interpTicks, interpLength = -1, spinTicks;
	private ConnectionState connectionState;
	
	public PastelNodeBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(SpectrumBlockEntities.PASTEL_NODE, blockPos, blockState);
		this.filterItems = NonNullList.withSize(MAX_FILTER_SLOTS, ItemStack.blank());
		this.outerRing = Optional.empty();
		this.innerRing = Optional.empty();
		this.redstoneRing = Optional.empty();
	}
	
	public @Nullable Storage<ItemStack> getConnectedStorage() {
		if (connectedStorageCache == null) {
			BlockState state = this.getBlockState();
			if (!(state.getBlock() instanceof PastelNodeBlock)) {
				return null;
			}
			cachedDirection = state.getValue(PastelNodeBlock.FACING);
			connectedStorageCache = BlockApiCache.create(ItemStorage.SIDED, (ServerLevel) level, this.getBlockPos().relative(cachedDirection.getOpposite()));
		}
		return connectedStorageCache.find(cachedDirection);
	}
	
	public static void tick(@NotNull Level world, BlockPos pos, BlockState state, PastelNodeBlockEntity node) {
		if (node.lamp && state.getValue(BlockStateProperties.LIT) != node.canTransfer()) {
			world.setBlockAndUpdate(pos, state.setValue(BlockStateProperties.LIT, node.cachedUnpowered));
		}
		
		//Trigger transfer logic needs to be ticked here
		if (node.triggerTransfer) {
			var powered = world.hasNeighborSignal(pos);
			
			if (node.waiting && !powered) {
				node.waiting = false;
			}
			
			if (!node.triggered && !node.waiting && powered) {
				node.triggered = true;
			}
		}
		
		if (world.isClientSide()) {
			if (node.networkUUID.isEmpty()) {
				node.changeConnectionState(ConnectionState.DISCONNECTED);
				node.interpLength = 17;
			} else if (!node.canTransfer()) {
				node.changeConnectionState(ConnectionState.INACTIVE);
				node.interpLength = 21;
			} else if (node.spinTicks > 0) {
				node.changeConnectionState(ConnectionState.ACTIVE);
				node.interpLength = 17;
			} else {
				node.changeConnectionState(ConnectionState.CONNECTED);
				node.interpLength = 13;
			}
			
			if (node.interpTicks < node.interpLength)
				node.interpTicks++;
			
			if (node.spinTicks > 0)
				node.spinTicks--;
		} else if (!node.updated) {
			node.updateUpgrades();
			node.updated = true;
		}
	}
	
	public void changeConnectionState(ConnectionState connectionState) {
		if (this.connectionState != connectionState) {
			this.connectionState = connectionState;
			lastRotationTarget = crystalRotation;
			lastHeightTarget = crystalHeight;
			lastAlphaTarget = ringAlpha;
			interpTicks = 0;
		}
	}
	
	public Optional<PastelUpgradeSignature> getInnerRing() {
		return innerRing;
	}
	
	public Optional<PastelUpgradeSignature> getOuterRing() {
		return outerRing;
	}
	
	public Optional<PastelUpgradeSignature> getRedstoneRing() {
		return redstoneRing;
	}
	
	public PastelNetwork.NodePriority getPriority() {
		return priority;
	}
	
	// outer goes first, then inner, then redstone
	public boolean tryInteractRings(ItemStack item, PastelNodeType type) {
		var upgrade = SpectrumPastelUpgrades.of(item);
		
		if (upgrade.category.isRedstone()) {
			if (redstoneRing.isEmpty()) {
				redstoneRing = Optional.of(upgrade);
				return true;
			}
			
			return false;
		}
		
		if (outerRing.isEmpty() && type.hasOuterRing()) {
			outerRing = Optional.of(upgrade);
			return true;
		} else if (innerRing.isEmpty()) {
			innerRing = Optional.of(upgrade);
			return true;
		}
		
		return false;
	}
	
	// inverted order of adding them
	public ItemStack tryRemoveUpgrade() {
		var stack = ItemStack.EMPTY;
		
		if (redstoneRing.isPresent()) {
			stack = redstoneRing.get().upgradeItem.getDefaultInstance();
			redstoneRing = Optional.empty();
		} else if (innerRing.isPresent()) {
			stack = innerRing.get().upgradeItem.getDefaultInstance();
			innerRing = Optional.empty();
		} else if (outerRing.isPresent()) {
			stack = outerRing.get().upgradeItem.getDefaultInstance();
			outerRing = Optional.empty();
		}
		
		if (!stack.isEmpty()) {
			level.playLocalSound(worldPosition, SpectrumSoundEvents.SHATTER_LIGHT, SoundSource.BLOCKS, 0.25F, 0.9F + level.getRandom().nextFloat() * 0.2F, true);
			setChanged();
		}
		return stack;
	}
	
	public void updateUpgrades() {
		transferCount = PastelTransmissionLogic.DEFAULT_MAX_TRANSFER_AMOUNT;
		transferTime = PastelTransmissionLogic.DEFAULT_TRANSFER_TICKS_PER_NODE;
		var oldFilterSlotCount = filterSlotRows;
		filterSlotRows = DEFAULT_FILTER_SLOT_ROWS;
		triggerTransfer = false;
		lit = false;
		lamp = false;
		sensor = false;
		var oldPriority = priority;
		priority = PastelNetwork.NodePriority.GENERIC;
		
		//First one processed can't compound because it has nothing to compound on
		outerRing.ifPresent(r -> apply(r, Collections.emptyList()));
		innerRing.ifPresent(r -> apply(r, outerRing.map(List::of).orElse(Collections.emptyList())));
		redstoneRing.ifPresent(r -> apply(r, Collections.emptyList()));
		
		// Sanity
		transferCount = Math.max(transferCount, 1);
		transferTime = Mth.clamp(transferTime, 2, 100);
		filterSlotRows = Mth.clamp(filterSlotRows, 1, 5);
		
		if (lit && lamp) {
			lit = false;
		}
		
		if (level != null) {
			networkUUID.ifPresent(uuid -> ServerPastelNetworkManager.get((ServerLevel) level).getNetwork(uuid).ifPresent(n -> n.updateNodePriority(this, oldPriority)));
			if (getBlockState().getValue(BlockStateProperties.LIT) != lit)
				level.setBlockAndUpdate(worldPosition, getBlockState().setValue(BlockStateProperties.LIT, lit));
		}
		
		if (filterSlotRows < oldFilterSlotCount) {
			for (int i = getDrawnSlots(); i < filterItems.size(); i++) {
				filterItems.set(i, ItemStack.blank());
			}
		}
	}
	
	@Override
	public void notifySensor() {
		if (level != null) {
			var state = getBlockState();
			level.setBlockAndUpdate(worldPosition, state.setValue(BlockStateProperties.POWERED, true));
			if (!level.getBlockTicks().hasScheduledTick(worldPosition, state.getBlock())) {
				level.scheduleTick(worldPosition, state.getBlock(), 2);
			}
		}
	}
	
	public long getMaxTransferredAmount() {
		return transferCount;
	}
	
	public int getTransferTime() {
		return transferTime;
	}
	
	@Override
	public void setLevel(Level world) {
		super.setLevel(world);
		
		if (!world.isClientSide()) {
			getServerNetwork().ifPresent(network -> network.initializeNode(this));
		}
		
		/* TODO: this freezes the world on join
		if (creationStamp == -1) {
			creationStamp = (world.getTime() + world.getRandom().nextInt(7)) % 20;
		}
		
		if (!world.isClient && this.networkUUID.isPresent()) {
			this.networkUUID = Optional.of(Pastel.getServerInstance().joinOrCreateNetwork(this, this.networkUUID.get()).getUUID());
		}
		*/
	}
	
	public float getRedstoneAlphaMult() {
		return redstoneRing.isPresent() ? 0.5F : 0.25F;
	}
	
	public boolean canTransfer() {
		var result = redstoneRing.map(r -> r.preProcessor
				.apply(new PastelUpgradeSignature.RedstoneContext(this, level, worldPosition, cachedUnpowered))).orElse(InteractionResult.PASS);
		
		if (result == InteractionResult.SUCCESS)
			return true;
		
		if (result == InteractionResult.FAIL)
			return false;
		
		long time = this.getLevel().getGameTime();
		if (time > this.cachedRedstonePowerTick && !getBlockState().getValue(PastelNodeBlock.REDSTONE_EMITTING)) {
			this.cachedUnpowered = level.getBestNeighborSignal(this.worldPosition) == 0;
		}
		
		boolean notPowered = redstoneRing.map(r -> {
			var post = r.postProcessor.apply(new PastelUpgradeSignature.RedstoneContext(this, level, worldPosition, cachedUnpowered));
			
			if (post == InteractionResult.SUCCESS)
				return true;
			
			if (post == InteractionResult.FAIL)
				return false;
			
			return cachedUnpowered;
		}).orElse(cachedUnpowered);
		
		var canTransfer = this.getLevel().getGameTime() > lastTransferTick;
		if (triggerTransfer) {
			return triggered && canTransfer;
		}
		
		return canTransfer && notPowered;
	}
	
	public void markTransferred() {
		if (triggerTransfer) {
			markTriggered();
		}
		
		this.lastTransferTick = level.getGameTime();
		this.setChanged();
	}
	
	public Optional<UUID> getNetworkUUID() {
		return networkUUID;
	}
	
	@Override
	protected void loadAdditional(CompoundTag nbt, HolderLookup.Provider registryLookup) {
		super.loadAdditional(nbt, registryLookup);
		
		this.nodeId = nbt.contains("NodeID") ? nbt.getUUID("NodeID") : UUID.randomUUID();
		this.networkUUID = nbt.contains("NetworkUUID") ? Optional.of(nbt.getUUID("NetworkUUID")) : Optional.empty();
		this.triggered = nbt.contains("Triggered") && nbt.getBoolean("Triggered");
		this.waiting = nbt.contains("Waiting") && nbt.getBoolean("Waiting");
		this.creationStamp = nbt.contains("creationStamp") ? nbt.getLong("creationStamp") : 0;
		this.lastTransferTick = nbt.contains("LastTransferTick", Tag.TAG_LONG) ? nbt.getLong("LastTransferTick") : 0;
		this.itemCountUnderway = nbt.contains("ItemCountUnderway", Tag.TAG_LONG) ? nbt.getLong("ItemCountUnderway") : 0;
		this.outerRing = nbt.contains("OuterRing") ? Optional.ofNullable(SpectrumRegistries.PASTEL_UPGRADE.get(ResourceLocation.tryParse(nbt.getString("OuterRing")))) : Optional.empty();
		this.innerRing = nbt.contains("InnerRing") ? Optional.ofNullable(SpectrumRegistries.PASTEL_UPGRADE.get(ResourceLocation.tryParse(nbt.getString("InnerRing")))) : Optional.empty();
		this.redstoneRing = nbt.contains("RedstoneRing") ? Optional.ofNullable(SpectrumRegistries.PASTEL_UPGRADE.get(ResourceLocation.tryParse(nbt.getString("RedstoneRing")))) : Optional.empty();
		
		if (this.getNodeType().usesFilters()) {
			FilterConfigurable.readFilterNbt(nbt, this.filterItems);
		}
	}
	
	@Override
	protected void saveAdditional(CompoundTag nbt, HolderLookup.Provider registryLookup) {
		super.saveAdditional(nbt, registryLookup);
		if (creationStamp != -1) {
			nbt.putLong("creationStamp", creationStamp);
		}
		if (this.networkUUID.isPresent()) {
			nbt.putUUID("NetworkUUID", this.networkUUID.get());
		}
		nbt.putUUID("NodeID", this.nodeId);
		nbt.putBoolean("Triggered", this.triggered);
		nbt.putBoolean("Waiting", this.waiting);
		nbt.putLong("LastTransferTick", this.lastTransferTick);
		nbt.putLong("ItemCountUnderway", this.itemCountUnderway);
		if (this.getNodeType().usesFilters()) {
			FilterConfigurable.writeFilterNbt(nbt, this.filterItems);
		}
		outerRing.ifPresent(r -> nbt.putString("OuterRing", SpectrumPastelUpgrades.toString(r)));
		innerRing.ifPresent(r -> nbt.putString("InnerRing", SpectrumPastelUpgrades.toString(r)));
		redstoneRing.ifPresent(r -> nbt.putString("RedstoneRing", SpectrumPastelUpgrades.toString(r)));
	}
	
	@Nullable
	@Override
	public Packet<ClientGamePacketListener> getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}
	
	@Override
	public CompoundTag getUpdateTag(HolderLookup.Provider registryLookup) {
		Optional<ServerPastelNetwork> network = getServerNetwork();
		network.ifPresent(serverPastelNetwork -> PastelNetworkEdgeSyncPayload.send(serverPastelNetwork, worldPosition));
		
		CompoundTag nbtCompound = new CompoundTag();
		this.saveAdditional(nbtCompound, registryLookup);
		return nbtCompound;
	}
	
	// triggered when the chunk is unloaded, or the world quit
	@Override
	public void setRemoved() {
		super.setRemoved();
		if (!level.isClientSide()) {
			Pastel.getServerInstance().removeNode(this, NodeRemovalReason.UNLOADED);
		}
	}
	
	public @NotNull UUID getNodeId() {
		return nodeId;
	}
	
	public void onBroken() {
		if (level != null && !level.isClientSide) {
			Pastel.getServerInstance().removeNode(this, NodeRemovalReason.BROKEN);
		}
	}
	
	public PastelNodeType getNodeType() {
		if (this.getBlockState().getBlock() instanceof PastelNodeBlock pastelNodeBlock) {
			return pastelNodeBlock.pastelNodeType;
		}
		return PastelNodeType.CONNECTION;
	}
	
	public void setNetworkUUID(@Nullable UUID uuid) {
		this.networkUUID = Optional.ofNullable(uuid);
		if (this.getLevel() != null && !this.getLevel().isClientSide()) {
			this.setChanged();
			this.updateInClientWorld();
		}
	}
	
	public long getItemCountUnderway() {
		return this.itemCountUnderway;
	}
	
	public void addItemCountUnderway(long count) {
		this.itemCountUnderway += count;
		this.itemCountUnderway = Math.max(0, this.itemCountUnderway);
		this.setChanged();
	}
	
	// interaction methods
	public void updateInClientWorld() {
		((ServerLevel) level).getChunkSource().blockChanged(worldPosition);
	}
	
	@Override
	public List<ItemStack> getItemFilters() {
		return this.filterItems;
	}
	
	@Override
	public void setFilterItem(int slot, ItemStack item) {
		this.filterItems.set(slot, item);
	}
	
	public Predicate<ItemStack> getTransferFilterTo(PastelNodeBlockEntity other) {
		if (this.getNodeType().usesFilters() && !this.hasEmptyFilter()) {
			if (other.getNodeType().usesFilters() && !other.hasEmptyFilter()) {
				// unionize both filters
				return Predicates.and(this::filter, other::filter);
			} else {
				return this::filter;
			}
		} else if (other.getNodeType().usesFilters() && !other.hasEmptyFilter()) {
			return other::filter;
		} else {
			return ItemStack -> true;
		}
	}
	
	private boolean filter(ItemStack variant) {
		return filterItems
				.stream()
				.anyMatch(filterItem -> {
					ItemStack filterStack = filterItem.toStack();
					
					if (!filterStack.has(DataComponents.CUSTOM_NAME) || !filterStack.is(SpectrumItemTags.TAG_FILTERING_ITEMS))
						return filterStack.getItem() == variant.getItem();
					
					var name = StringUtils.trim(filterStack.getHoverName().getString());
					
					// This is to allow nbt filtering without item / tag filtering.
					if (StringUtils.equalsAnyIgnoreCase(name, "*", "any", "all", "everything", "c:*", "c:any", "c:all", "c:everything"))
						return true;
					
					var id = ResourceLocation.tryParse(StringUtils.remove(name, '#')); // let's be nice and remove any pound signs
					if (id == null)
						return false;
					
					var tag = SpectrumCommon.CACHED_ITEM_TAG_MAP.computeIfAbsent(id, tagId -> BuiltInRegistries.ITEM.getTagNames()
							.filter(t -> t.location().equals(tagId))
							.findFirst()
							.orElse(null));
					
					if (tag == null)
						return false;
					
					return variant.getItem().builtInRegistryHolder().is(tag);
				});
	}
	
	public boolean handleImpression(Optional<UUID> stamper, Optional<Player> user, BlockReference reference, Level world) {
		var sourceNode = (PastelNodeBlockEntity) reference.tryGetBlockEntity().orElseThrow(() -> new IllegalStateException("Attempted to connect a non-existent node - what did you do?!"));
		var manager = Pastel.getInstance(world.isClientSide());
		
		var sourceNetwork = manager.getNetworkOrEmpty(sourceNode.networkUUID);
		var thisNetwork = manager.getNetworkOrEmpty(this.networkUUID);
		
		if (!sourceNode.canConnect(this))
			return false;
		
		if (sourceNetwork.isPresent() && sourceNetwork.equals(thisNetwork)) {
			if (sourceNetwork.get().removeEdge(this, sourceNode))
				return true;
			
			return sourceNetwork.get().addEdge(this, sourceNode);
		}
		
		if (!world.isClientSide()) {
			Pastel.getServerInstance().connectNodes(this, sourceNode);
		}
		
		thisNetwork.ifPresent(n -> {
			user.filter(u -> u instanceof ServerPlayer).ifPresent(p -> {
				SpectrumAdvancementCriteria.PASTEL_NETWORK_CREATING.trigger((ServerPlayer) p, (ServerPastelNetwork) n);
			});
		});
		
		return true;
	}
	
	@Override
	public StampData recordStampData(Optional<Player> user, BlockReference reference, Level world) {
		return new StampData(user.map(Entity::getUUID), reference, this);
	}
	
	@Override
	public StampDataCategory getStampCategory() {
		return SpectrumStampDataCategories.PASTEL;
	}
	
	@Override
	public boolean canUserStamp(Optional<Player> stamper) {
		
		return true;
	}
	
	@Override
	public void onImpressedOther(StampData data, boolean success) {
	}
	
	public long getCreationStamp() {
		return creationStamp;
	}
	
	@Override
	public void clearImpression() {
		if (!level.isClientSide()) {
			Pastel.getServerInstance().removeNode(this, NodeRemovalReason.DISCONNECT);
		}
		
		networkUUID = Optional.empty();
		setChanged();
	}
	
	@Override
	public Component getDisplayName() {
		return Component.translatable("block.spectrum.pastel_node");
	}
	
	@Nullable
	@Override
	public AbstractContainerMenu createMenu(int syncId, Inventory inv, Player player) {
		return new FilteringScreenHandler(syncId, inv, new ExtendedData(this));
	}
	
	@Override
	public int getFilterRows() {
		return filterSlotRows;
	}
	
	@Override
	public int getDrawnSlots() {
		return getFilterRows() * SLOTS_PER_ROW;
	}
	
	@Override
	public FilterConfigurable.ExtendedData getScreenOpeningData(ServerPlayer player) {
		return new FilterConfigurable.ExtendedData(this);
	}
	
	public boolean equals(Object obj) {
		return obj instanceof PastelNodeBlockEntity blockEntity && this.worldPosition.equals(blockEntity.worldPosition);
	}
	
	public int hashCode() {
		return this.worldPosition.hashCode();
	}
	
	public ConnectionState getState() {
		return connectionState;
	}
	
	public Optional<DyeColor> getColor() {
		return this.color;
	}
	
	public boolean setColor(Optional<DyeColor> color, @Nullable Entity user) {
		if (this.color == color)
			return false;
		
		this.color = color;
		
		var network = networkUUID.flatMap(id -> Pastel.getInstance(level.isClientSide()).getNetwork(id));
		
		if (network.isPresent()) {
			network.get().setColor(color);
		}
		
		return true;
	}
	
	public boolean canConnect(PastelNodeBlockEntity target) {
		return this != target && this.getBlockPos().closerThan(target.getBlockPos(), RANGE);
	}
	
	public Optional<ServerPastelNetwork> getServerNetwork() {
		if (this.networkUUID.isPresent()) {
			return Pastel.getServerInstance().getNetwork(this.networkUUID.get());
		}
		return Optional.empty();
	}
	
	public int getPastelNetworkColor() {
		Optional<DyeColor> color = getColor();
		return color.isPresent() ? color.get().getTextureDiffuseColor() : SpectrumColorHelper.getRandomColor(getNodeId().hashCode());
	}
	
	enum ConnectionState {
		DISCONNECTED,
		CONNECTED,
		ACTIVE,
		INACTIVE
	}
	
	public void setSpinTicks(long spinTicks) {
		this.spinTicks = spinTicks;
	}
	
	@Override
	public void markLit() {
		lit = true;
	}
	
	@Override
	public void markLamp() {
		this.lamp = true;
	}
	
	@Override
	public void markTriggerTransfer() {
		triggerTransfer = true;
	}
	
	@Override
	public void markSensor() {
		sensor = true;
	}
	
	@Override
	public void markTriggered() {
		triggered = false;
		waiting = true;
	}
	
	@Override
	public boolean isTriggerTransfer() {
		return triggerTransfer;
	}
	
	@Override
	public boolean isSensor() {
		return sensor;
	}
	
	@Override
	public void applySlotUpgrade(PastelUpgradeSignature upgrade) {
		filterSlotRows += getNodeType().hasOuterRing() ? upgrade.slotRows : upgrade.slotRows * 2;
	}
	
	@Override
	public void applySimple(PastelUpgradeSignature upgrade) {
		transferCount += upgrade.stack;
		transferTime += upgrade.speed;
	}
	
	@Override
	public void applyCompounding(PastelUpgradeSignature upgrade) {
		transferCount = Math.round(transferCount * upgrade.stackMult);
		transferTime = Math.round(transferTime * upgrade.speedMult);
	}
	
	@Override
	public void upgradePriority() {
		if (priority == PastelNetwork.NodePriority.GENERIC) {
			priority = PastelNetwork.NodePriority.MODERATE;
		} else {
			priority = PastelNetwork.NodePriority.HIGH;
		}
	}
	
	@Override
	public String toString() {
		return this.getNodeType().toString() + "-" +
				this.getColor().toString() + "-" +
				this.getBlockPos().toString() + "-" +
				this.getNodeId();
	}
	
}
