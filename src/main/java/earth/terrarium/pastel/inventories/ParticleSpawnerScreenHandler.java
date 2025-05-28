package earth.terrarium.pastel.inventories;

import earth.terrarium.pastel.blocks.particle_spawner.ParticleSpawnerBlockEntity;
import earth.terrarium.pastel.registries.SpectrumBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.*;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

public class ParticleSpawnerScreenHandler extends AbstractContainerMenu {
	
	protected final Player player;
	protected ParticleSpawnerBlockEntity blockEntity;

	public ParticleSpawnerScreenHandler(int syncId, Inventory inventory, RegistryFriendlyByteBuf buf) {
		this(syncId, inventory, inventory.player.level().getBlockEntity(BlockPos.STREAM_CODEC.decode(buf), SpectrumBlockEntities.PARTICLE_SPAWNER).orElseThrow());
	}
	
	public ParticleSpawnerScreenHandler(int syncId, Inventory playerInventory, ParticleSpawnerBlockEntity blockEntity) {
		super(SpectrumScreenHandlerTypes.PARTICLE_SPAWNER, syncId);
		
		this.player = playerInventory.player;
		this.blockEntity = blockEntity;
	}
	
	public ParticleSpawnerBlockEntity getBlockEntity() {
		return this.blockEntity;
	}
	
	@Override
	public ItemStack quickMoveStack(Player player, int index) {
		return ItemStack.EMPTY;
	}
	
	@Override
	public boolean stillValid(Player player) {
		return this.blockEntity != null && !this.blockEntity.isRemoved();
	}
	
}
