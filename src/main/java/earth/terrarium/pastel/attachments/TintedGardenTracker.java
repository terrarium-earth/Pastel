package earth.terrarium.pastel.attachments;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.saveddata.SavedData;

public class TintedGardenTracker extends SavedData {

    public static final int EXCLUSION_AREA = 128;

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider registries) {
        return null;
    }
}
