package earth.terrarium.pastel.blocks.block_flooder;

import earth.terrarium.pastel.api.block.PlayerOwned;
import earth.terrarium.pastel.registries.SpectrumBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.UUID;

public class BlockFlooderBlockEntity extends BlockEntity {
	
	private Entity owner;
	private UUID ownerUUID;
	
	private BlockPos sourcePos;
	private BlockState targetBlockState = Blocks.AIR.defaultBlockState();
	
	public BlockFlooderBlockEntity(BlockPos pos, BlockState state) {
		super(SpectrumBlockEntities.BLOCK_FLOODER, pos, state);
	}
	
	public UUID getOwnerUUID() {
		return ownerUUID;
	}
	
	public void setOwnerUUID(UUID ownerUUID) {
		this.ownerUUID = ownerUUID;
	}
	
	public Entity getOwner() {
		if (this.owner == null) {
			this.owner = PlayerOwned.getPlayerEntityIfOnline(this.ownerUUID);
		}
		return this.owner;
	}
	
	@Override
	public void loadAdditional(CompoundTag nbt, HolderLookup.Provider registryLookup) {
		super.loadAdditional(nbt, registryLookup);
		
		this.ownerUUID = PlayerOwned.readOwnerUUID(nbt);
		if (nbt.contains("SourcePositionX") && nbt.contains("SourcePositionY") && nbt.contains("SourcePositionZ")) {
			this.sourcePos = new BlockPos(nbt.getInt("SourcePositionX"), nbt.getInt("SourcePositionY"), nbt.getInt("SourcePositionZ"));
		}
	}
	
	@Override
	public void saveAdditional(CompoundTag nbt, HolderLookup.Provider registryLookup) {
		super.saveAdditional(nbt, registryLookup);
		
		PlayerOwned.writeOwnerUUID(nbt, this.ownerUUID);
		if (this.sourcePos != null) {
			nbt.putInt("SourcePositionX", this.sourcePos.getX());
			nbt.putInt("SourcePositionY", this.sourcePos.getY());
			nbt.putInt("SourcePositionZ", this.sourcePos.getZ());
		}
	}
	
	
	public BlockPos getSourcePos() {
		if (this.sourcePos == null) {
			this.sourcePos = this.worldPosition;
		}
		return this.sourcePos;
	}
	
	public void setSourcePos(BlockPos sourcePos) {
		this.sourcePos = sourcePos;
	}
	
	public BlockState getTargetBlockState() {
		return targetBlockState;
	}
	
	public void setTargetBlockState(BlockState targetBlockState) {
		this.targetBlockState = targetBlockState;
	}
	
}
