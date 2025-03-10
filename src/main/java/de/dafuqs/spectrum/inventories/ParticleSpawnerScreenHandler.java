package de.dafuqs.spectrum.inventories;

import de.dafuqs.spectrum.blocks.particle_spawner.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.screen.*;
import net.minecraft.util.math.*;

public class ParticleSpawnerScreenHandler extends ScreenHandler {
	
	protected final PlayerEntity player;
	protected ParticleSpawnerBlockEntity blockEntity;
	
	public ParticleSpawnerScreenHandler(int syncId, PlayerInventory inventory, BlockPos pos) {
		this(syncId, inventory, inventory.player.getWorld().getBlockEntity(pos, SpectrumBlockEntities.PARTICLE_SPAWNER).orElseThrow());
	}
	
	public ParticleSpawnerScreenHandler(int syncId, PlayerInventory playerInventory, ParticleSpawnerBlockEntity blockEntity) {
		super(SpectrumScreenHandlerTypes.PARTICLE_SPAWNER, syncId);
		
		this.player = playerInventory.player;
		this.blockEntity = blockEntity;
	}
	
	public ParticleSpawnerBlockEntity getBlockEntity() {
		return this.blockEntity;
	}
	
	@Override
	public ItemStack quickMove(PlayerEntity player, int index) {
		return ItemStack.EMPTY;
	}
	
	@Override
	public boolean canUse(PlayerEntity player) {
		return this.blockEntity != null && !this.blockEntity.isRemoved();
	}
	
}
