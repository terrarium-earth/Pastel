package earth.terrarium.pastel.attachments;

import com.mojang.authlib.GameProfile;
import earth.terrarium.pastel.*;
import earth.terrarium.pastel.attachments.data.AttachmentUtil;
import earth.terrarium.pastel.registries.PastelStatusEffects;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.saveddata.*;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HardcoreDeathTracker extends SavedData {
	
	private final List<UUID> playersThatDiedInHardcore = new ArrayList<>();

	private static final Factory<HardcoreDeathTracker> FACTORY = new Factory<>(HardcoreDeathTracker::new, HardcoreDeathTracker::load);
	private static HardcoreDeathTracker CLIENT_TRACKER;
	
	public static boolean isInHardcore(Player player) {
		return player.hasEffect(PastelStatusEffects.DIVINITY);
	}

	private static void sync(ServerPlayer player) {
		AttachmentUtil.syncToPlayer(new SyncPayload(getInstance().save(new CompoundTag(), player.registryAccess())), player);
	}

	public static void addHardcoreDeath(ServerLevel world, ServerPlayer player) {
		addHardcoreDeath(world, player.getGameProfile().getId());
		sync(player);
	}
	
	public static void removeHardcoreDeath(GameProfile profile) {
		var server = PastelCommon.getSidedServer();
		if (server == null)
			return;
		removeHardcoreDeath(profile.getId());
		sync(server.getPlayerList().getPlayer(profile.getId()));
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
		if (FMLEnvironment.dist.isClient()) {
			if (CLIENT_TRACKER == null)
				CLIENT_TRACKER = new HardcoreDeathTracker();

			return CLIENT_TRACKER;
		}

		return PastelCommon.getSidedServer().overworld().getDataStorage().computeIfAbsent(FACTORY, PastelCommon.MOD_ID + ":hardcore_tracker");
	}

	@Override
	public CompoundTag save(CompoundTag tag, HolderLookup.Provider registries) {
		ListTag uuidList = new ListTag();
		for (UUID playerThatDiedInHardcore : playersThatDiedInHardcore) {
			uuidList.add(NbtUtils.createUUID(playerThatDiedInHardcore));
		}
		tag.put("HardcoreDeaths", uuidList);

		if (!FMLEnvironment.dist.isClient())
			PacketDistributor.sendToAllPlayers(new SyncPayload(tag));

		return tag;
	}

	public static HardcoreDeathTracker load(CompoundTag tag, HolderLookup.@NotNull Provider wrapperLookup) {
		var data = new HardcoreDeathTracker();

		ListTag uuidList = tag.getList("HardcoreDeaths", Tag.TAG_INT_ARRAY);
		for (Tag listEntry : uuidList) {
			data.playersThatDiedInHardcore.add(NbtUtils.loadUUID(listEntry));
		}

		if (!FMLEnvironment.dist.isClient())
			PacketDistributor.sendToAllPlayers(new SyncPayload(tag));

		return data;
	}

	public record SyncPayload(CompoundTag tag) implements CustomPacketPayload {

		public static final StreamCodec<FriendlyByteBuf, SyncPayload> CODEC = StreamCodec.composite(
				ByteBufCodecs.COMPOUND_TAG, SyncPayload::tag,
				SyncPayload::new
		);

		public static final CustomPacketPayload.Type<SyncPayload> TYPE = AttachmentUtil.create("hardcore_death_tracker");

		public static void execute(SyncPayload payload, IPayloadContext context) {
			assert PastelCommon.getRegistryAccess() != null; // Surely this cannot be null if we are receiving a packet, right?
			CLIENT_TRACKER = load(payload.tag, PastelCommon.getRegistryAccess());
		}

		@Override
		public Type<? extends CustomPacketPayload> type() {
			return TYPE;
		}
	}
}
