package earth.terrarium.pastel.attachments;

import com.mojang.authlib.GameProfile;
import earth.terrarium.pastel.*;
import earth.terrarium.pastel.registries.SpectrumStatusEffects;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.saveddata.*;
import net.neoforged.neoforge.server.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HardcoreDeathTracker extends SavedData {
	
	private final List<UUID> playersThatDiedInHardcore = new ArrayList<>();

	private static final Factory<HardcoreDeathTracker> FACTORY = new Factory<>(HardcoreDeathTracker::new, HardcoreDeathTracker::load);
	
	public static boolean isInHardcore(Player player) {
		return player.hasEffect(SpectrumStatusEffects.DIVINITY);
	}
	
	public static void addHardcoreDeath(ServerLevel world, GameProfile profile) {
		addHardcoreDeath(world, profile.getId());
	}
	
	public static void removeHardcoreDeath(GameProfile profile) {
		removeHardcoreDeath(profile.getId());
	}
	
	public static boolean hasHardcoreDeath(GameProfile profile) {
		return hasHardcoreDeath(profile.getId());
	}
	
	protected static void addHardcoreDeath(ServerLevel world, UUID uuid) {
		var data = getInstance();

		if (!data.playersThatDiedInHardcore.contains(uuid)) {
			data.playersThatDiedInHardcore.add(uuid);
		}
		world.getServer().getPlayerList().getPlayer(uuid).setGameMode(GameType.SPECTATOR);
		data.setDirty();
	}
	
	protected static boolean hasHardcoreDeath(UUID uuid) {
		return getInstance().playersThatDiedInHardcore.contains(uuid);
	}
	
	protected static void removeHardcoreDeath(UUID uuid) {
		var data = getInstance();

		data.playersThatDiedInHardcore.remove(uuid);
		data.setDirty();
	}

	@NotNull
	public static HardcoreDeathTracker getInstance() {
		return SpectrumCommon.getSidedServer().overworld().getDataStorage().computeIfAbsent(FACTORY, SpectrumCommon.MOD_ID + ":hardcore_tracker");
	}

	@Override
	public CompoundTag save(CompoundTag tag, HolderLookup.Provider registries) {
		ListTag uuidList = new ListTag();
		for (UUID playerThatDiedInHardcore : playersThatDiedInHardcore) {
			uuidList.add(NbtUtils.createUUID(playerThatDiedInHardcore));
		}
		tag.put("HardcoreDeaths", uuidList);
		return tag;
	}

	public static HardcoreDeathTracker load(CompoundTag tag, HolderLookup.@NotNull Provider wrapperLookup) {
		var data = new HardcoreDeathTracker();

		ListTag uuidList = tag.getList("HardcoreDeaths", Tag.TAG_INT_ARRAY);
		for (Tag listEntry : uuidList) {
			data.playersThatDiedInHardcore.add(NbtUtils.loadUUID(listEntry));
		}
		return data;
	}
}
