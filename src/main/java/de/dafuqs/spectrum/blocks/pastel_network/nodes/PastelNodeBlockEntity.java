package de.dafuqs.spectrum.blocks.pastel_network.nodes;

import com.google.common.base.*;
import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.api.block.*;
import de.dafuqs.spectrum.api.pastel.*;
import de.dafuqs.spectrum.blocks.pastel_network.*;
import de.dafuqs.spectrum.blocks.pastel_network.network.*;
import de.dafuqs.spectrum.inventories.*;
import de.dafuqs.spectrum.networking.s2c_payloads.*;
import de.dafuqs.spectrum.progression.*;
import de.dafuqs.spectrum.registries.*;
import net.fabricmc.fabric.api.lookup.v1.block.*;
import net.fabricmc.fabric.api.screenhandler.v1.*;
import net.fabricmc.fabric.api.transfer.v1.item.*;
import net.fabricmc.fabric.api.transfer.v1.storage.*;
import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.component.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.listener.*;
import net.minecraft.network.packet.*;
import net.minecraft.network.packet.s2c.play.*;
import net.minecraft.registry.*;
import net.minecraft.screen.*;
import net.minecraft.server.network.*;
import net.minecraft.server.world.*;
import net.minecraft.sound.*;
import net.minecraft.state.property.Properties;
import net.minecraft.text.*;
import net.minecraft.util.*;
import net.minecraft.util.collection.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import org.apache.commons.lang3.*;
import org.jetbrains.annotations.*;

import java.util.Optional;
import java.util.*;
import java.util.function.Predicate;

public class PastelNodeBlockEntity extends BlockEntity implements FilterConfigurable, ExtendedScreenHandlerFactory<FilterConfigurable.ExtendedData>, PastelUpgradeable {
	
	public static final int MAX_FILTER_SLOTS = 25;
	public static final int SLOTS_PER_ROW = 5;
	public static final int DEFAULT_FILTER_SLOT_ROWS = 1;
	public static final int RANGE = 12;
	
	@NotNull
	protected UUID nodeId = UUID.randomUUID();
	protected Optional<UUID> networkUUID = Optional.empty();
	protected Optional<PastelUpgradeSignature> outerRing, innerRing, redstoneRing;
	
	// TODO: move these to ServerPastelNetwork?
	protected long lastTransferTick = 0;
	protected final long cachedRedstonePowerTick = 0;
	protected boolean cachedUnpowered = true;
	protected PastelNetwork.NodePriority priority = PastelNetwork.NodePriority.GENERIC;
	protected long itemCountUnderway = 0;
	@Nullable
	protected DyeColor color = null;
	
	// upgrade impl stuff
	protected boolean lit, triggerTransfer, triggered, waiting, lamp, sensor, updated;
	protected int transferCount = PastelTransmissionLogic.DEFAULT_MAX_TRANSFER_AMOUNT;
	protected int transferTime = PastelTransmissionLogic.DEFAULT_TRANSFER_TICKS_PER_NODE;
	protected int filterSlotRows = DEFAULT_FILTER_SLOT_ROWS;
	
	protected BlockApiCache<Storage<ItemVariant>, Direction> connectedStorageCache = null;
	protected Direction cachedDirection = null;
	
	private final List<ItemVariant> filterItems;
	float rotationTarget, crystalRotation, lastRotationTarget, heightTarget, crystalHeight, lastHeightTarget, alphaTarget, ringAlpha, lastAlphaTarget;
	long creationStamp = -1, interpTicks, interpLength = -1, spinTicks;
	private State state;
	
	public PastelNodeBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(SpectrumBlockEntities.PASTEL_NODE, blockPos, blockState);
		this.filterItems = DefaultedList.ofSize(MAX_FILTER_SLOTS, ItemVariant.blank());
		this.outerRing = Optional.empty();
		this.innerRing = Optional.empty();
		this.redstoneRing = Optional.empty();
	}
	
	public @Nullable Storage<ItemVariant> getConnectedStorage() {
		if (connectedStorageCache == null) {
			BlockState state = this.getCachedState();
			if (!(state.getBlock() instanceof PastelNodeBlock)) {
				return null;
			}
			cachedDirection = state.get(PastelNodeBlock.FACING);
			connectedStorageCache = BlockApiCache.create(ItemStorage.SIDED, (ServerWorld) world, this.getPos().offset(cachedDirection.getOpposite()));
		}
		return connectedStorageCache.find(cachedDirection);
	}
	
	public static void tick(@NotNull World world, BlockPos pos, BlockState state, PastelNodeBlockEntity node) {
		if (node.lamp && state.get(Properties.LIT) != node.canTransfer()) {
			world.setBlockState(pos, state.with(Properties.LIT, node.cachedUnpowered));
		}
		
		//Trigger transfer logic needs to be ticked here
		if (node.triggerTransfer) {
			var powered = world.isReceivingRedstonePower(pos);
			
			if (node.waiting && !powered) {
				node.waiting = false;
			}
			
			if (!node.triggered && !node.waiting && powered) {
				node.triggered = true;
			}
		}
		
		if (world.isClient()) {
			if (node.networkUUID.isEmpty()) {
				node.changeState(State.DISCONNECTED);
				node.interpLength = 17;
			} else if (!node.canTransfer()) {
				node.changeState(State.INACTIVE);
				node.interpLength = 21;
			} else if (node.spinTicks > 0) {
				node.changeState(State.ACTIVE);
				node.interpLength = 17;
			} else {
				node.changeState(State.CONNECTED);
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
	
	public void changeState(State state) {
		if (this.state != state) {
			this.state = state;
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
			stack = redstoneRing.get().upgradeItem.getDefaultStack();
			redstoneRing = Optional.empty();
		} else if (innerRing.isPresent()) {
			stack = innerRing.get().upgradeItem.getDefaultStack();
			innerRing = Optional.empty();
		} else if (outerRing.isPresent()) {
			stack = outerRing.get().upgradeItem.getDefaultStack();
			outerRing = Optional.empty();
		}
		
		if (!stack.isEmpty()) {
			world.playSoundAtBlockCenter(pos, SpectrumSoundEvents.SHATTER_LIGHT, SoundCategory.BLOCKS, 0.25F, 0.9F + world.getRandom().nextFloat() * 0.2F, true);
			markDirty();
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
		transferTime = MathHelper.clamp(transferTime, 2, 100);
		filterSlotRows = MathHelper.clamp(filterSlotRows, 1, 5);
		
		if (lit && lamp) {
			lit = false;
		}
		
		if (world != null) {
			networkUUID.ifPresent(uuid -> ServerPastelNetworkManager.get((ServerWorld) world).getNetwork(uuid).ifPresent(n -> n.updateNodePriority(this, oldPriority)));
			if (getCachedState().get(Properties.LIT) != lit)
				world.setBlockState(pos, getCachedState().with(Properties.LIT, lit));
		}
		
		if (filterSlotRows < oldFilterSlotCount) {
			for (int i = getDrawnSlots(); i < filterItems.size(); i++) {
				filterItems.set(i, ItemVariant.blank());
			}
		}
	}
	
	@Override
	public void notifySensor() {
		if (world != null) {
			var state = getCachedState();
			world.setBlockState(pos, state.with(Properties.POWERED, true));
			if (!world.getBlockTickScheduler().isQueued(pos, state.getBlock())) {
				world.scheduleBlockTick(pos, state.getBlock(), 2);
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
	public void setWorld(World world) {
		super.setWorld(world);
		if (creationStamp == -1) {
			creationStamp = (world.getTime() + world.getRandom().nextInt(7)) % 20;
		}
		
		if (!world.isClient && this.networkUUID.isPresent()) {
			this.networkUUID = Optional.of(Pastel.getServerInstance().joinOrCreateNetwork(this, this.networkUUID.get()).getUUID());
		}
	}
	
	public float getRedstoneAlphaMult() {
		return redstoneRing.isPresent() ? 0.5F : 0.25F;
	}
	
	public boolean canTransfer() {
		var result = redstoneRing.map(r -> r.preProcessor
				.apply(new PastelUpgradeSignature.RedstoneContext(this, world, pos, cachedUnpowered))).orElse(ActionResult.PASS);
		
		if (result == ActionResult.SUCCESS)
			return true;
		
		if (result == ActionResult.FAIL)
			return false;
		
		long time = this.getWorld().getTime();
		if (time > this.cachedRedstonePowerTick && !getCachedState().get(PastelNodeBlock.REDSTONE_EMITTING)) {
			this.cachedUnpowered = world.getReceivedRedstonePower(this.pos) == 0;
		}
		
		boolean notPowered = redstoneRing.map(r -> {
			var post = r.postProcessor.apply(new PastelUpgradeSignature.RedstoneContext(this, world, pos, cachedUnpowered));
			
			if (post == ActionResult.SUCCESS)
				return true;
			
			if (post == ActionResult.FAIL)
				return false;
			
			return cachedUnpowered;
		}).orElse(cachedUnpowered);
		
		var canTransfer = this.getWorld().getTime() > lastTransferTick;
		if (triggerTransfer) {
			return triggered && canTransfer;
		}
		
		return canTransfer && notPowered;
	}
	
	public void markTransferred() {
		if (triggerTransfer) {
			markTriggered();
		}
		
		this.lastTransferTick = world.getTime();
		this.markDirty();
	}
	
	@Override
	protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
		super.readNbt(nbt, registryLookup);
		
		this.nodeId = nbt.contains("NodeID") ? nbt.getUuid("NodeID") : UUID.randomUUID();
		this.networkUUID = nbt.contains("NetworkUUID") ? Optional.of(nbt.getUuid("NetworkUUID")) : Optional.empty();
		this.triggered = nbt.contains("Triggered") && nbt.getBoolean("Triggered");
		this.waiting = nbt.contains("Waiting") && nbt.getBoolean("Waiting");
		this.creationStamp = nbt.contains("creationStamp") ? nbt.getLong("creationStamp") : 0;
		this.lastTransferTick = nbt.contains("LastTransferTick", NbtElement.LONG_TYPE) ? nbt.getLong("LastTransferTick") : 0;
		this.itemCountUnderway = nbt.contains("ItemCountUnderway", NbtElement.LONG_TYPE) ? nbt.getLong("ItemCountUnderway") : 0;
		this.outerRing = nbt.contains("OuterRing") ? Optional.ofNullable(SpectrumRegistries.PASTEL_UPGRADE.get(Identifier.tryParse(nbt.getString("OuterRing")))) : Optional.empty();
		this.innerRing = nbt.contains("InnerRing") ? Optional.ofNullable(SpectrumRegistries.PASTEL_UPGRADE.get(Identifier.tryParse(nbt.getString("InnerRing")))) : Optional.empty();
		this.redstoneRing = nbt.contains("RedstoneRing") ? Optional.ofNullable(SpectrumRegistries.PASTEL_UPGRADE.get(Identifier.tryParse(nbt.getString("RedstoneRing")))) : Optional.empty();
		
		if (this.getNodeType().usesFilters()) {
			FilterConfigurable.readFilterNbt(nbt, this.filterItems);
		}
	}
	
	@Override
	protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
		super.writeNbt(nbt, registryLookup);
		if (creationStamp != -1) {
			nbt.putLong("creationStamp", creationStamp);
		}
		if (this.networkUUID.isPresent()) {
			nbt.putUuid("NetworkUUID", this.networkUUID.get());
		}
		nbt.putUuid("NodeID", this.nodeId);
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
	public Packet<ClientPlayPacketListener> toUpdatePacket() {
		return BlockEntityUpdateS2CPacket.create(this);
	}
	
	@Override
	public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
		Optional<ServerPastelNetwork> network = getServerNetwork();
		network.ifPresent(serverPastelNetwork -> PastelNetworkEdgeSyncPayload.send(serverPastelNetwork, pos));
		
		NbtCompound nbtCompound = new NbtCompound();
		this.writeNbt(nbtCompound, registryLookup);
		return nbtCompound;
	}
	
	// triggered when the chunk is unloaded, or the world quit
	@Override
	public void markRemoved() {
		super.markRemoved();
		if (!world.isClient()) {
			Pastel.getServerInstance().removeNode(this, NodeRemovalReason.UNLOADED);
		}
	}
	
	public @NotNull UUID getNodeId() {
		return nodeId;
	}
	
	public void onBroken() {
		if (world != null && !world.isClient) {
			Pastel.getServerInstance().removeNode(this, NodeRemovalReason.BROKEN);
		}
	}
	
	public PastelNodeType getNodeType() {
		if (this.getCachedState().getBlock() instanceof PastelNodeBlock pastelNodeBlock) {
			return pastelNodeBlock.pastelNodeType;
		}
		return PastelNodeType.CONNECTION;
	}
	
	public void setNetworkUUID(@Nullable UUID uuid) {
		this.networkUUID = Optional.ofNullable(uuid);
		if (this.getWorld() != null && !this.getWorld().isClient()) {
			this.markDirty();
			this.updateInClientWorld();
		}
	}
	
	public long getItemCountUnderway() {
		return this.itemCountUnderway;
	}
	
	public void addItemCountUnderway(long count) {
		this.itemCountUnderway += count;
		this.itemCountUnderway = Math.max(0, this.itemCountUnderway);
		this.markDirty();
	}
	
	// interaction methods
	public void updateInClientWorld() {
		((ServerWorld) world).getChunkManager().markForUpdate(pos);
	}
	
	@Override
	public List<ItemVariant> getItemFilters() {
		return this.filterItems;
	}
	
	@Override
	public void setFilterItem(int slot, ItemVariant item) {
		this.filterItems.set(slot, item);
	}
	
	public Predicate<ItemVariant> getTransferFilterTo(PastelNodeBlockEntity other) {
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
			return itemVariant -> true;
		}
	}
	
	private boolean filter(ItemVariant variant) {
		return filterItems
				.stream()
				.anyMatch(filterItem -> {
					ItemStack filterStack = filterItem.toStack();
					
					if (!filterStack.contains(DataComponentTypes.CUSTOM_NAME) || !filterStack.isIn(SpectrumItemTags.TAG_FILTERING_ITEMS))
						return filterStack.getItem() == variant.getItem();
					
					var name = StringUtils.trim(filterStack.getName().getString());
					
					// This is to allow nbt filtering without item / tag filtering.
					if (StringUtils.equalsAnyIgnoreCase(name, "*", "any", "all", "everything", "c:*", "c:any", "c:all", "c:everything"))
						return true;
					
					var id = Identifier.tryParse(StringUtils.remove(name, '#')); // let's be nice and remove any pound signs
					if (id == null)
						return false;
					
					var tag = SpectrumCommon.CACHED_ITEM_TAG_MAP.computeIfAbsent(id, tagId -> Registries.ITEM.streamTags()
							.filter(t -> t.id().equals(tagId))
							.findFirst()
							.orElse(null));
					
					if (tag == null)
						return false;
					
					return variant.getItem().getRegistryEntry().isIn(tag);
				});
	}
	
	public long getCreationStamp() {
		return creationStamp;
	}
	
	@Override
	public Text getDisplayName() {
		return Text.translatable("block.spectrum.pastel_node");
	}
	
	@Nullable
	@Override
	public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
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
	public FilterConfigurable.ExtendedData getScreenOpeningData(ServerPlayerEntity player) {
		return new FilterConfigurable.ExtendedData(this);
	}
	
	public boolean equals(Object obj) {
		return obj instanceof PastelNodeBlockEntity blockEntity && this.pos.equals(blockEntity.pos);
	}
	
	public int hashCode() {
		return this.pos.hashCode();
	}
	
	public State getState() {
		return state;
	}
	
	public Optional<DyeColor> getColor() {
		return Optional.ofNullable(this.color);
	}
	
	public boolean setColor(DyeColor color, @Nullable Entity user) {
		if (this.color == color)
			return false;
		
		this.color = color;
		
		// remove from existing network, if it had one and join new networks
		Pastel.getServerInstance().removeNode(this, NodeRemovalReason.DISCONNECT);
		connectToNearbyNodes(user);

		return true;
	}
	
	protected void connectToNearbyNodes(@Nullable Entity user) {
		PastelNodeBlockEntity biggestNetworkNode = null;
		int biggestNetwork = 0;
		List<PastelNodeBlockEntity> otherNearbyNodes = new ArrayList<>();
		
		for (BlockPos pos : BlockPos.iterateOutwards(this.pos, RANGE, RANGE, RANGE)) {
			Optional<PastelNodeBlockEntity> blockEntity = world.getBlockEntity(pos, SpectrumBlockEntities.PASTEL_NODE);
			if (blockEntity.isPresent() && canConnect(this, blockEntity.get())) {
				Optional<ServerPastelNetwork> network = blockEntity.get().getServerNetwork();
				if (network.isEmpty()) {
					continue;
				}
				
				int size = network.get().size();
				if (size > biggestNetwork) {
					biggestNetwork = size;
					if (biggestNetworkNode != null) {
						otherNearbyNodes.add(biggestNetworkNode);
					}
					biggestNetworkNode = blockEntity.get();
				} else {
					otherNearbyNodes.add(blockEntity.get());
				}
			}
		}
		
		if (biggestNetworkNode != null) {
			biggestNetworkNode.getServerNetwork().get().addEdge(biggestNetworkNode, this);
		} else {
			Pastel.getServerInstance().createNetwork((ServerWorld) world, this.getNodeId());
		}
		
		ServerPastelNetwork network = getServerNetwork().get();
		for (PastelNodeBlockEntity nearbyNode : otherNearbyNodes) {
			network.addEdge(nearbyNode, this);
		}
		if (user instanceof ServerPlayerEntity serverPlayer) {
			Optional<ServerPastelNetwork> thisNetwork = getServerNetwork();
			thisNetwork.ifPresent(serverPastelNetwork -> SpectrumAdvancementCriteria.PASTEL_NETWORK_CREATING.trigger(serverPlayer, network));
		}
	}
	
	public boolean canConnect(PastelNodeBlockEntity first, PastelNodeBlockEntity second) {
		return first.getColor().equals(second.getColor()) && first.getPos().isWithinDistance(second.getPos(), RANGE);
	}
	
	public Optional<ServerPastelNetwork> getServerNetwork() {
		if (this.networkUUID.isPresent()) {
			return Pastel.getServerInstance().getNetwork(this.networkUUID.get());
		}
		return Optional.empty();
	}
	
	enum State {
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
}
