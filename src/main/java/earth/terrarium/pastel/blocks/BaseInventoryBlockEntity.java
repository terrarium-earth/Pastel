package earth.terrarium.pastel.blocks;

import earth.terrarium.pastel.capabilities.item.*;
import net.minecraft.core.*;
import net.minecraft.core.component.*;
import net.minecraft.nbt.*;
import net.minecraft.network.chat.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;

import javax.annotation.*;

public abstract class BaseInventoryBlockEntity extends BlockEntity implements Container, MenuProvider, Nameable {

    @Nullable
    private Component name;

    public BaseInventoryBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    @Override
    public Component getName() {
        return this.name != null ? this.name : this.getDefaultName();
    }

    @Override
    public Component getDisplayName() {
        return this.getName();
    }

    @Nullable
    @Override
    public Component getCustomName() {
        return this.name;
    }

    protected abstract Component getDefaultName();

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.contains("CustomName", 8)) {
            this.name = parseCustomNameSafe(tag.getString("CustomName"), registries);
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        if (this.name != null) {
            tag.putString("CustomName", Component.Serializer.toJson(this.name, registries));
        }
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return this.createMenu(containerId, playerInventory);
    }

    protected abstract AbstractContainerMenu createMenu(int p_58627_, Inventory p_58628_);

    @Override
    protected void applyImplicitComponents(BlockEntity.DataComponentInput componentInput) {
        super.applyImplicitComponents(componentInput);
        this.name = componentInput.get(DataComponents.CUSTOM_NAME);
    }

    @Override
    protected void collectImplicitComponents(DataComponentMap.Builder components) {
        super.collectImplicitComponents(components);
        components.set(DataComponents.CUSTOM_NAME, this.name);
    }

    @Override
    public void removeComponentsFromTag(CompoundTag tag) {
        tag.remove("CustomName");
        tag.remove("Items");
    }

    @Override
    public int getContainerSize() {
        return getHandler().getSlots();
    }

    // Pretty much a duck
    protected abstract FriendlyStackHandler getHandler();

    protected void notifyInventoryUpdate() {}

    @Override
    public boolean isEmpty() {
        return getHandler().isEmpty();
    }

    @Override
    public ItemStack getItem(int slot) {
        return getHandler().getStackInSlot(slot);
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        setChanged();
        notifyInventoryUpdate();
        return getHandler().extractItem(slot, amount, false);
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        setChanged();
        notifyInventoryUpdate();
        return getHandler().removeStackInSlot(slot);
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        setChanged();
        notifyInventoryUpdate();
        getHandler().setStackInSlot(slot, stack);
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public void clearContent() {
        setChanged();
        notifyInventoryUpdate();
        getHandler().clear();
    }
}
