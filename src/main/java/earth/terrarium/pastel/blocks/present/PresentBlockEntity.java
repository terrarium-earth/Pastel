package earth.terrarium.pastel.blocks.present;

import com.mojang.authlib.GameProfile;
import earth.terrarium.pastel.api.block.PlayerOwned;
import earth.terrarium.pastel.api.block.PlayerOwnedWithName;
import earth.terrarium.pastel.components.WrappedPresentComponent;
import earth.terrarium.pastel.helpers.Support;
import earth.terrarium.pastel.registries.PastelBlockEntities;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.PastelDataComponentTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PresentBlockEntity extends BlockEntity implements PlayerOwnedWithName {

    private UUID openerUUID;
    protected int openingTicks = 0;

    public PresentBlockEntity(BlockPos pos, BlockState state) {
        super(PastelBlockEntities.PRESENT.get(), pos, state);
    }

    public void triggerAdvancement() {
        UUID openerUUID = getOpenerUUID();
        if (openerUUID != null) {
            Player opener = PlayerOwned.getPlayerEntityIfOnline(openerUUID);
            if (opener != null) {
                Support.grantAdvancementCriterion(
                    (ServerPlayer) opener, "gift_or_open_present", "gifted_or_opened_present");
            }
        }

        UUID ownerUUID = getOwnerUUID();
        if (ownerUUID != null) {
            Player wrapper = PlayerOwned.getPlayerEntityIfOnline(ownerUUID);
            if (wrapper != null) {
                Support.grantAdvancementCriterion(
                    (ServerPlayer) wrapper, "gift_or_open_present", "gifted_or_opened_present");
            }
        }
    }

    @Override
    public void loadAdditional(CompoundTag nbt, HolderLookup.Provider registryLookup) {
        super.loadAdditional(nbt, registryLookup);
        if (nbt.contains("OpenerUUID")) {
            this.openerUUID = nbt.getUUID("OpenerUUID");
        } else {
            this.openerUUID = null;
        }
        if (nbt.contains("OpeningTick", Tag.TAG_ANY_NUMERIC)) {
            this.openingTicks = nbt.getInt("OpeningTick");
        }
    }

    @Override
    protected void saveAdditional(CompoundTag nbt, HolderLookup.Provider registryLookup) {
        super.saveAdditional(nbt, registryLookup);
        if (this.openerUUID != null) {
            nbt.putUUID("OpenerUUID", this.openerUUID);
        }
        if (this.openingTicks > 0) {
            nbt.putInt("OpeningTick", this.openingTicks);
        }
    }

    public int openingTick() {
        openingTicks++;
        setChanged();
        return this.openingTicks;
    }

    @Override
    @Nullable
    public UUID getOwnerUUID() {
        var profile = getOwner();
        return profile == null ? null : profile.id().orElse(null);
    }

    @Nullable
    public ResolvableProfile getOwner() {
        return components().get(DataComponents.PROFILE);
    }

    @Override
    public String getOwnerName() {
        var profile = getOwner();
        return profile == null ? "???" : profile.name().orElse("???");
    }

    @Override
    public void setOwner(@Nullable Player playerEntity) {
        ResolvableProfile result;
        var builder = DataComponentPatch.builder();

        if (playerEntity == null)
            builder.remove(DataComponents.PROFILE);
        else {
            var profile = new GameProfile(
                playerEntity.getUUID(), playerEntity.getName()
                                                    .getString()
            );

            builder.set(DataComponents.PROFILE, new ResolvableProfile(profile));
        }

        applyComponents(components(), builder.build());
        setChanged();
    }

    public void setOpenerUUID(Player opener) {
        this.openerUUID = opener.getUUID();
        setChanged();
    }

    public UUID getOpenerUUID() {
        return this.openerUUID;
    }

    public ItemStack retrievePresent() {
        var result = PastelBlocks.PRESENT.toStack();
        result.applyComponents(components());
        return result;
    }

    public Map<Integer, Integer> getColors() {
        return components().getOrDefault(PastelDataComponentTypes.WRAPPED_PRESENT, WrappedPresentComponent.DEFAULT).colors();
    }

    public List<ItemStack> getStacks() {
        var contents = components().get(DataComponents.BUNDLE_CONTENTS);
        return contents == null ? List.of() : contents.itemCopyStream()
                                                      .toList();
    }

    public boolean isEmpty() {
        var contents = components().get(DataComponents.BUNDLE_CONTENTS);
        return contents == null || contents.isEmpty();
    }

}
