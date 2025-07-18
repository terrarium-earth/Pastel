package earth.terrarium.pastel.blocks.chests;

import earth.terrarium.pastel.capabilities.*;
import earth.terrarium.pastel.capabilities.item.*;
import earth.terrarium.pastel.inventories.BlackHoleChestScreenHandler;
import earth.terrarium.pastel.inventories.CompactingChestScreenHandler;
import earth.terrarium.pastel.inventories.FabricationChestScreenHandler;
import net.minecraft.core.*;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.SeededContainerLoot;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestLidController;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.entity.LidBlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.*;
import org.jetbrains.annotations.Nullable;

//TODO: GET THIS LOOT CONTAINER SHIT OUT OF MY CHEST
@OnlyIn(
    value = Dist.CLIENT,
    _interface = LidBlockEntity.class
)
public abstract class PastelChestBlockEntity extends RandomizableContainerBlockEntity
    implements LidBlockEntity, SidedCapabilityProvider {

    public final ContainerOpenersCounter stateManager;
    protected final ChestLidController lidAnimator;
    protected FriendlyStackHandler inventory;

    protected PastelChestBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
        this.inventory = new FriendlyStackHandler(getContainerSize());
        this.lidAnimator = new ChestLidController();

        this.stateManager = new ContainerOpenersCounter() {
            @Override
            protected void onOpen(Level world, BlockPos pos, BlockState state) {
                playSound(world, pos, getOpenSound());
                onOpenSpectrum();
            }

            @Override
            protected void onClose(Level world, BlockPos pos, BlockState state) {
                playSound(world, pos, getCloseSound());
                onCloseSpectrum();
            }

            @Override
            protected void openerCountChanged(
                Level world, BlockPos pos, BlockState state, int oldViewerCount, int newViewerCount) {
                onInvOpenOrClose(world, pos, state, oldViewerCount, newViewerCount);
            }

            @Override
            protected boolean isOwnContainer(Player player) {
                AbstractContainerMenu screenHandler = player.containerMenu;

                Container inventory = null;
                if (screenHandler instanceof ChestMenu) {
                    inventory = ((ChestMenu) screenHandler).getContainer();
                } else if (screenHandler instanceof FabricationChestScreenHandler fabricationChestScreenHandler) {
                    inventory = fabricationChestScreenHandler.getInventory();
                } else if (screenHandler instanceof BlackHoleChestScreenHandler blackHoleChestScreenHandler) {
                    inventory = blackHoleChestScreenHandler.getInventory();
                } else if (screenHandler instanceof CompactingChestScreenHandler compactingChestScreenHandler) {
                    inventory = compactingChestScreenHandler.getInventory();
                }

                return inventory == PastelChestBlockEntity.this;
            }
        };
    }

    private static void playSound(Level world, BlockPos pos, SoundEvent soundEvent) {
        world.playSound(
            null, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, soundEvent, SoundSource.BLOCKS, 0.5F,
            world.random.nextFloat() * 0.1F + 0.9F
        );
    }

    @SuppressWarnings("unused")
    public static void clientTick(Level world, BlockPos pos, BlockState state, PastelChestBlockEntity blockEntity) {
        blockEntity.lidAnimator.tickLid();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public float getOpenNess(float tickDelta) {
        return this.lidAnimator.getOpenness(tickDelta);
    }

    public void onOpenSpectrum() {

    }

    public void onCloseSpectrum() {

    }

    @Override
    public boolean triggerEvent(int type, int data) {
        if (type == 1) {
            this.lidAnimator.shouldBeOpen(data > 0);
            return true;
        } else {
            return super.triggerEvent(type, data);
        }
    }

    protected void onInvOpenOrClose(
        Level world, BlockPos pos, BlockState state, int oldViewerCount, int newViewerCount) {
        Block block = state.getBlock();
        world.blockEvent(pos, block, 1, newViewerCount);
    }

    @Override
    public void startOpen(Player player) {
        if (!player.isSpectator()) {
            this.stateManager.incrementOpeners(player, this.getLevel(), this.getBlockPos(), this.getBlockState());
        }

    }

    @Override
    public void stopOpen(Player player) {
        if (!player.isSpectator()) {
            this.stateManager.decrementOpeners(player, this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        super.setItem(slot, stack);
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.inventory.getInternalList();
    }

    @Override
    public ItemStack getItem(int index) {
        return inventory.getStackInSlot(index);
    }

    @Override
    protected void setItems(NonNullList<ItemStack> list) {
        inventory.setInternalList(list);
    }

    public void onScheduledTick() {
        this.stateManager.recheckOpeners(this.getLevel(), this.getBlockPos(), this.getBlockState());
    }

    // TODO Should the loot table NBT only be maintained for TreasureChestBlockEntity?
    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registryLookup) {
        super.loadAdditional(tag, registryLookup);
        this.tryLoadLootTable(tag);
        inventory.deserializeNBT(registryLookup, tag.getCompound("inventory"));
    }

    @Override
    public void saveAdditional(CompoundTag tag, HolderLookup.Provider registryLookup) {
        super.saveAdditional(tag, registryLookup);
        this.trySaveLootTable(tag);
        tag.put("inventory", inventory.serializeNBT(registryLookup));
    }

    @Override
    protected void collectImplicitComponents(DataComponentMap.Builder componentMapBuilder) {
        super.collectImplicitComponents(componentMapBuilder);
        componentMapBuilder.set(
            DataComponents.CONTAINER_LOOT, new SeededContainerLoot(this.lootTable, this.lootTableSeed));
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public SoundEvent getOpenSound() {
        return SoundEvents.CHEST_OPEN;
    }

    public SoundEvent getCloseSound() {
        return SoundEvents.CHEST_CLOSE;
    }

    @Override
    public IItemHandler exposeItemHandlers(Direction dir) {
        return inventory;
    }
}
