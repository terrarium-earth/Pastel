package earth.terrarium.pastel.blocks;

import earth.terrarium.pastel.api.block.PlayerOwned;
import earth.terrarium.pastel.registries.SpectrumBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class PlacedItemBlockEntity extends BlockEntity implements PlayerOwned {
	
	protected ItemStack stack = ItemStack.EMPTY;
	protected UUID ownerUUID;
	
	public PlacedItemBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}
	
	public PlacedItemBlockEntity(BlockPos pos, BlockState state) {
		super(SpectrumBlockEntities.PLACED_ITEM.get(), pos, state);
	}
	
	@Override
	public void loadAdditional(CompoundTag nbt, HolderLookup.Provider registryLookup) {
		super.loadAdditional(nbt, registryLookup);
		this.stack = ItemStack.parse(registryLookup, nbt.getCompound("stack")).orElse(ItemStack.EMPTY);
		this.ownerUUID = PlayerOwned.readOwnerUUID(nbt);
	}
	
	@Override
	public void saveAdditional(CompoundTag nbt, HolderLookup.Provider registryLookup) {
		super.saveAdditional(nbt, registryLookup);
		nbt.put("stack", this.stack.save(registryLookup));
		
		PlayerOwned.writeOwnerUUID(nbt, this.ownerUUID);
	}
	
	public void setStack(ItemStack stack) {
		this.stack = stack;
		this.setChanged();
	}
	
	public ItemStack getStack() {
		return this.stack;
	}
	
	@Override
	public UUID getOwnerUUID() {
		return this.ownerUUID;
	}
	
	@Override
	public void setOwner(@NotNull Player playerEntity) {
		this.ownerUUID = playerEntity.getUUID();
		setChanged();
	}
	
}
