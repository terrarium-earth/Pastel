package earth.terrarium.pastel.blocks.structure;

import earth.terrarium.pastel.registries.SpectrumBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerTrackerBlockEntity extends BlockEntity {

	private final List<UUID> playersThatOpenedAlready = new ArrayList<>();

	public PlayerTrackerBlockEntity(BlockPos pos, BlockState state) {
		super(SpectrumBlockEntities.PLAYER_TRACKING.get(), pos, state);
	}

	public boolean hasTaken(Player player) {
		return this.playersThatOpenedAlready.contains(player.getUUID());
	}

	public void markTaken(Player player) {
		this.playersThatOpenedAlready.add(player.getUUID());
		setChanged();
	}

	@Override
	protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registryLookup) {
		if (!playersThatOpenedAlready.isEmpty()) {
			ListTag uuidList = new ListTag();
			for (UUID uuid : playersThatOpenedAlready) {
				CompoundTag nbtCompound = new CompoundTag();
				nbtCompound.putUUID("UUID", uuid);
				uuidList.add(nbtCompound);
			}
			tag.put("OpenedPlayers", uuidList);
		}
	}

	@Override
	public void loadAdditional(CompoundTag tag, HolderLookup.Provider registryLookup) {
		this.playersThatOpenedAlready.clear();
		if (tag.contains("OpenedPlayers", Tag.TAG_LIST)) {
			ListTag list = tag.getList("OpenedPlayers", Tag.TAG_COMPOUND);
			for (int i = 0; i < list.size(); i++) {
				CompoundTag compound = list.getCompound(i);
				UUID uuid = compound.getUUID("UUID");
				this.playersThatOpenedAlready.add(uuid);
			}
		}
	}
}
