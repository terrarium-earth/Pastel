package de.dafuqs.spectrum.blocks.particle_spawner;

import de.dafuqs.spectrum.inventories.ParticleSpawnerScreenHandler;
import de.dafuqs.spectrum.particle.SpectrumParticleTypes;
import de.dafuqs.spectrum.registries.SpectrumBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class ParticleSpawnerBlockEntity extends BlockEntity implements MenuProvider {
	
	protected ParticleSpawnerConfiguration configuration;
	protected boolean initialized = false;
	
	public ParticleSpawnerBlockEntity(BlockPos blockPos, BlockState blockState) {
		this(SpectrumBlockEntities.PARTICLE_SPAWNER, blockPos, blockState);
	}
	
	public ParticleSpawnerBlockEntity(BlockEntityType<ParticleSpawnerBlockEntity> blockEntityType, BlockPos blockPos, BlockState blockState) {
		super(blockEntityType, blockPos, blockState);
		
		this.configuration = new ParticleSpawnerConfiguration(
				SpectrumParticleTypes.SHOOTING_STAR,
				new Vec3i(80, 40, 0),
				false,
				10.0F,
				new Vec3(0.0, 1.0, 0.0),
				new Vec3(0.0, 0.0, 0.0),
				new Vec3(0.0, 0.1, 0.0),
				new Vec3(0.1, 0.1, 0.1),
				1.0F,
				0.2F,
				20,
				10,
				0.02F,
				true);
	}
	
	@SuppressWarnings("unused")
	public static void clientTick(Level world, BlockPos pos, BlockState state, ParticleSpawnerBlockEntity blockEntity) {
		BlockState blockState = world.getBlockState(pos);
		if (blockState.getBlock() instanceof AbstractParticleSpawnerBlock particleSpawnerBlock && particleSpawnerBlock.shouldSpawnParticles(world, pos)) {
			blockEntity.configuration.spawnParticles(world, pos);
		}
	}
	
	// Called when the chunk is first loaded to initialize this be
	@Override
	public CompoundTag getUpdateTag(HolderLookup.Provider registryLookup) {
		CompoundTag nbtCompound = new CompoundTag();
		this.saveAdditional(nbtCompound, registryLookup);
		return nbtCompound;
	}
	
	@Nullable
	@Override
	public Packet<ClientGamePacketListener> getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}
	
	public void updateInClientWorld() {
		if (level != null) {
			level.sendBlockUpdated(worldPosition, level.getBlockState(worldPosition), level.getBlockState(worldPosition), Block.UPDATE_INVISIBLE);
		}
	}
	
	@Override
	public void saveAdditional(CompoundTag tag, HolderLookup.Provider registryLookup) {
		super.saveAdditional(tag, registryLookup);
		tag.put("particle_config", ParticleSpawnerConfiguration.CODEC.encodeStart(NbtOps.INSTANCE, this.configuration).result().orElse(new CompoundTag()));
	}
	
	@Override
	public void loadAdditional(CompoundTag tag, HolderLookup.Provider registryLookup) {
		super.loadAdditional(tag, registryLookup);
		this.initialized = false;
		var config = ParticleSpawnerConfiguration.CODEC.decode(NbtOps.INSTANCE, tag.getCompound("particle_config")).result();
		if (config.isPresent()) {
			this.configuration = config.get().getFirst();
			this.initialized = true;
		}
	}
	
	@Nullable
	@Override
	public AbstractContainerMenu createMenu(int syncId, Inventory inv, Player player) {
		return new ParticleSpawnerScreenHandler(syncId, inv, this);
	}
	
	@Override
	public Component getDisplayName() {
		return Component.translatable("block.spectrum.particle_spawner");
	}
	
	public void applySettings(ParticleSpawnerConfiguration configuration) {
		this.configuration = configuration;
		this.initialized = true;
		
		this.updateInClientWorld();
		this.setChanged();
	}
	
	public ParticleSpawnerConfiguration getConfiguration() {
		return configuration;
	}

	@Override
	public void writeClientSideData(AbstractContainerMenu menu, RegistryFriendlyByteBuf buffer) {
		buffer.writeBlockPos(worldPosition);
	}
}
