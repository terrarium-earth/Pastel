package de.dafuqs.spectrum.inventories;

import de.dafuqs.spectrum.blocks.particle_spawner.ParticleSpawnerBlockEntity;
import de.dafuqs.spectrum.registries.SpectrumBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

public class ParticleSpawnerScreenHandler extends AbstractContainerMenu {
	
	protected final Player player;
	protected ParticleSpawnerBlockEntity blockEntity;
	
	public ParticleSpawnerScreenHandler(int syncId, Inventory inventory, BlockPos pos) {
		this(syncId, inventory, inventory.player.level().getBlockEntity(pos, SpectrumBlockEntities.PARTICLE_SPAWNER).orElseThrow());
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
