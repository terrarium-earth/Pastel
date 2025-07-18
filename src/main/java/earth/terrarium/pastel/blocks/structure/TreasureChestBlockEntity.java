package earth.terrarium.pastel.blocks.structure;

import de.dafuqs.revelationary.api.advancements.AdvancementHelper;
import earth.terrarium.pastel.blocks.chests.PastelChestBlockEntity;
import earth.terrarium.pastel.helpers.Support;
import earth.terrarium.pastel.inventories.GenericPastelContainerScreenHandler;
import earth.terrarium.pastel.inventories.ScreenBackgroundVariant;
import earth.terrarium.pastel.registries.PastelBlockEntities;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TreasureChestBlockEntity extends PastelChestBlockEntity {

    private final List<UUID> playersThatOpenedAlready = new ArrayList<>();
    private ResourceLocation requiredAdvancementIdentifierToOpen;
    private Vec3i controllerOffset;

    public TreasureChestBlockEntity(BlockPos pos, BlockState state) {
        super(PastelBlockEntities.PRESERVATION_CHEST.get(), pos, state);
    }

    @Override
    public void saveAdditional(CompoundTag tag, HolderLookup.Provider registryLookup) {
        super.saveAdditional(tag, registryLookup);

        if (this.requiredAdvancementIdentifierToOpen != null) {
            tag.putString("RequiredAdvancement", this.requiredAdvancementIdentifierToOpen.toString());
        }

        if (this.controllerOffset != null) {
            tag.putInt("ControllerOffsetX", this.controllerOffset.getX());
            tag.putInt("ControllerOffsetY", this.controllerOffset.getY());
            tag.putInt("ControllerOffsetZ", this.controllerOffset.getZ());
        }

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
    protected Component getDefaultName() {
        return Component.translatable("block.pastel.preservation_chest");
    }

    @Override
    protected AbstractContainerMenu createMenu(int syncId, Inventory playerInventory) {
        return GenericPastelContainerScreenHandler.createGeneric9x3(
            syncId, playerInventory, this, ScreenBackgroundVariant.LATEGAME);
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registryLookup) {
        super.loadAdditional(tag, registryLookup);

        if (tag.contains("RequiredAdvancement", Tag.TAG_STRING)) {
            this.requiredAdvancementIdentifierToOpen = ResourceLocation.tryParse(tag.getString("RequiredAdvancement"));
        }

        if (tag.contains("ControllerOffsetX")) {
            this.controllerOffset = new Vec3i(
                tag.getInt("ControllerOffsetX"), tag.getInt("ControllerOffsetY"), tag.getInt("ControllerOffsetZ"));
        }

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

    @Override
    public void onCloseSpectrum() {
        if (level instanceof ServerLevel serverWorld && controllerOffset != null) {
            BlockEntity blockEntity = serverWorld.getBlockEntity(
                Support.directionalOffset(
                    this.worldPosition, this.controllerOffset, serverWorld.getBlockState(this.worldPosition)
                                                                          .getValue(PreservationControllerBlock.FACING)
                ));
            if (blockEntity instanceof PreservationControllerBlockEntity controller) {
                controller.openExit();
            }
        }
    }

    // Generate new loot for each player that has never opened this chest before
    @Override
    public void unpackLootTable(@Nullable Player player) {
        if (player != null && this.lootTable != null && this.getLevel() != null && !hasOpenedThisChestBefore(player)) {
            supplyInventory(player);
            rememberPlayer(player);
        }
    }

    public boolean hasOpenedThisChestBefore(@NotNull Player player) {
        return this.playersThatOpenedAlready.contains(player.getUUID());
    }

    public void rememberPlayer(@NotNull Player player) {
        this.playersThatOpenedAlready.add(player.getUUID());
        this.setChanged();
    }

    public void supplyInventory(@NotNull Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            LootTable lootTable = serverPlayer.serverLevel()
                                              .getServer()
                                              .reloadableRegistries()
                                              .getLootTable(this.lootTable);
            var builder = new LootParams.Builder(serverPlayer.serverLevel()).withParameter(
                LootContextParams.ORIGIN, Vec3.atCenterOf(this.worldPosition));
            builder.withLuck(player.getLuck())
                   .withParameter(LootContextParams.THIS_ENTITY, player);
            lootTable.fill(this, builder.create(LootContextParamSets.CHEST), lootTableSeed);
            if (player instanceof ServerPlayer) {
                CriteriaTriggers.GENERATE_LOOT.trigger(serverPlayer, this.lootTable);
            }
        }
    }

    public boolean canOpen(Player player) {
        if (this.requiredAdvancementIdentifierToOpen == null) {
            return true;
        } else {
            return AdvancementHelper.hasAdvancement(player, this.requiredAdvancementIdentifierToOpen);
        }
    }

    @Override
    public int getContainerSize() {
        return 27;
    }

}
