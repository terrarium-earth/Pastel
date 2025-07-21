package earth.terrarium.pastel.api.item;

import earth.terrarium.pastel.helpers.level.BlockReference;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

/**
 * Marks a block as being interactable with via a Tuning Stamp and defines its behaviour.
 */
public interface Stampable {

    String STAMPING_DATA_TAG = "pastel:stamping_data";


    /**
     * Creates a reference to a Stampable object.
     */
    StampData recordStampData(Optional<Player> user, BlockReference reference, Level world);

    /**
     * Call this to request a stampable to process the provided data.
     *
     * @return Whether the state of the impresser changed.
     */
    boolean handleImpression(Optional<UUID> stamper, Optional<Player> user, BlockReference reference, Level world);

    /**
     * Resets the object to a blank state.
     */
    void clearImpression();

    StampDataCategory getStampCategory();

    boolean canUserStamp(Optional<Player> stamper);

    static CompoundTag saveStampingData(StampData data) {
        var compound = new CompoundTag();

        data.stamper.ifPresent(uuid -> compound.putUUID("stamper", uuid));
        if (data.reference == null)
            throw new IllegalStateException("Attempted to save stamp data without a BlockReference!");

        compound.putLong("source", data.reference.pos.asLong());

        return compound;
    }

    static Optional<StampData> loadStampingData(Level world, CompoundTag nbt) {
        var sourcePair = findSource(world, nbt);
        var source = sourcePair.getA();

        if (source.isEmpty())
            return Optional.empty();

        var stamper = Optional.<UUID>empty();

        if (nbt.hasUUID("stamper"))
            stamper = Optional.of(nbt.getUUID("stamper"));

        return Optional.of(new StampData(stamper, sourcePair.getB(), source.get()));
    }

    private static Tuple<Optional<Stampable>, BlockReference> findSource(Level world, CompoundTag nbt) {
        Stampable stampInteractable = null;
        BlockReference reference;

        if (!nbt.contains("source"))
            return new Tuple<>(Optional.empty(), null);


        var pos = BlockPos.of(nbt.getLong("source"));
        var state = world.getBlockState(pos);
        reference = BlockReference.of(state, pos);

        if (state.getBlock() instanceof Stampable interactable)
            stampInteractable = interactable;

        if (world.getBlockEntity(pos) instanceof Stampable interactable) {
            stampInteractable = interactable;
            reference = reference.appendBE((BlockEntity) interactable);
        }

        return new Tuple<>(Optional.ofNullable(stampInteractable), reference);
    }

    default boolean verifyStampData(StampData data) {
        if (data.source.getStampCategory() == StampDataCategory.UNIQUE) {
            return verifyUniqueStampData(data);
        }
        return data.source.getStampCategory() == this.getStampCategory();
    }

    /**
     * Override for unique type interactables.
     */
    @ApiStatus.OverrideOnly
    default boolean verifyUniqueStampData(StampData data) {
        return true;
    }

    /**
     * Called after this Stampable is used as the source for impressing another
     *
     * @param data    the impressed
     * @param success whether the target's state changed
     */
    void onImpressedOther(StampData data, boolean success);

    record StampData(Optional<UUID> stamper, BlockReference reference, Stampable source) {

        public StampData(@Nullable Entity stamper, BlockReference reference, Stampable source) {
            this(
                Optional.ofNullable(stamper)
                        .map(Entity::getUUID), reference, source
            );
        }

        public boolean verifyStampData(StampData data) {
            return source.verifyStampData(data);
        }

        public void notifySourceOfChange(StampData data, boolean success) {
            source.onImpressedOther(data, success);
        }

        public boolean canUserStamp(Optional<Player> player) {
            return source.canUserStamp(player);
        }
    }

}
