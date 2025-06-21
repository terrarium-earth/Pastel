package earth.terrarium.pastel.blocks.jade_vines;

import earth.terrarium.pastel.helpers.TimeHelper;
import earth.terrarium.pastel.registries.PastelBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class JadeVineRootsBlockEntity extends BlockEntity {
	
	private BlockState fenceBlockState;
	private long lastGrowthTick = -1;
	private boolean wasExposedToSunlight = false;
	
	public JadeVineRootsBlockEntity(BlockPos pos, BlockState state) {
		super(PastelBlockEntities.JADE_VINE_ROOTS.get(), pos, state);
		this.fenceBlockState = Blocks.OAK_FENCE.defaultBlockState();
	}
	
	@Override
	public void loadAdditional(CompoundTag nbt, HolderLookup.Provider registryLookup) {
		super.loadAdditional(nbt, registryLookup);
		if (nbt.contains("LastGrowthTick", Tag.TAG_LONG)) {
			this.lastGrowthTick = nbt.getLong("LastGrowthTick");
		}
		if (nbt.contains("WasExposedToSunlight")) {
			this.wasExposedToSunlight = nbt.getBoolean("WasExposedToSunlight");
		}
		if (nbt.contains("FenceBlockIdentifier", Tag.TAG_STRING)) {
			ResourceLocation fenceBlockIdentifier = ResourceLocation.tryParse(nbt.getString("FenceBlockIdentifier"));
			Block block = BuiltInRegistries.BLOCK.get(fenceBlockIdentifier);
			if (block instanceof FenceBlock) {
				this.fenceBlockState = block.defaultBlockState();
			}
		}
	}
	
	@Override
	public void saveAdditional(CompoundTag nbt, HolderLookup.Provider registryLookup) {
		super.saveAdditional(nbt, registryLookup);
		nbt.putLong("LastGrowthTick", this.lastGrowthTick);
		nbt.putBoolean("WasExposedToSunlight", this.wasExposedToSunlight);
		if (this.fenceBlockState != null) {
			nbt.putString("FenceBlockIdentifier", BuiltInRegistries.BLOCK.getKey(this.fenceBlockState.getBlock()).toString());
		}
	}
	
	public boolean isLaterNight(@NotNull Level world) {
		long dayTime = world.getDayTime();
		if (TimeHelper.getTimeOfDay(dayTime).isNight()) { // timeOfDay % 24000 >= 13000 && timeOfDay % 24000 < 23000
			return TimeHelper.getDay(dayTime + 1000) != TimeHelper.getDay(lastGrowthTick + 1000);
		}
		return false;
	}
	
	public long getLastGrownTime() {
		return lastGrowthTick;
	}
	
	public void setLastGrownTime(long time) {
		this.wasExposedToSunlight = false;
		this.lastGrowthTick = time;
		this.setChanged();
	}
	
	public BlockState getFenceBlockState() {
		return fenceBlockState;
	}
	
	public void setFenceBlockState(BlockState fenceBlockState) {
		this.fenceBlockState = fenceBlockState;
		this.setChanged();
		this.updateInClientWorld();
	}
	
	public boolean wasExposedToSunlight() {
		return wasExposedToSunlight;
	}
	
	public void setExposedToSunlight(boolean wasExposedToSunlight) {
		this.wasExposedToSunlight = wasExposedToSunlight;
		this.setChanged();
	}
	
	// Called when the chunk is first loaded to initialize this be
	@Override
	public CompoundTag getUpdateTag(HolderLookup.Provider registryLookup) {
		CompoundTag nbtCompound = new CompoundTag();
		if (this.fenceBlockState != null) {
			nbtCompound.putString("FenceBlockIdentifier", BuiltInRegistries.BLOCK.getKey(this.fenceBlockState.getBlock()).toString());
		}
		return nbtCompound;
	}
	
	@Nullable
	@Override
	public Packet<ClientGamePacketListener> getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}
	
	public void updateInClientWorld() {
		level.sendBlockUpdated(worldPosition, level.getBlockState(worldPosition), level.getBlockState(worldPosition), Block.UPDATE_INVISIBLE);
	}
	
}
