package earth.terrarium.pastel.api.entity;

import earth.terrarium.pastel.registries.SpectrumPointOfInterestTypeTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.ai.village.poi.PoiManager;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public interface POIMemorized {
	
	String POI_POS_KEY = "POIPos";
	
	TagKey<PoiType> getPOITag();
	
	@Nullable BlockPos getPOIPos();
	
	void setPOIPos(@Nullable BlockPos blockPos);
	
	default void writePOIPosToNbt(CompoundTag nbt) {
		@Nullable BlockPos poiPos = getPOIPos();
		if (poiPos != null) {
			nbt.put(POI_POS_KEY, NbtUtils.writeBlockPos(poiPos));
		}
	}
	
	default void readPOIPosFromNbt(CompoundTag nbt) {
		if (nbt.contains(POI_POS_KEY)) {
			setPOIPos(NbtUtils.readBlockPos(nbt, POI_POS_KEY).orElse(null));
		}
	}
	
	default boolean isPOIValid(ServerLevel world) {
		@Nullable BlockPos poiPos = getPOIPos();
		if (poiPos == null) {
			return false;
		}
		Optional<Holder<PoiType>> type = world.getPoiManager().getType(poiPos);
		return type.map(pointOfInterestTypeRegistryEntry -> pointOfInterestTypeRegistryEntry.is(SpectrumPointOfInterestTypeTags.LIZARD_DENS)).orElse(false);
	}
	
	default @Nullable BlockPos findNearestPOI(ServerLevel world, BlockPos pos, int maxDistance) {
		PoiManager pointOfInterestStorage = world.getPoiManager();
		
		return pointOfInterestStorage.findClosest(
				(poiType) -> poiType.is(getPOITag()),
				pos, maxDistance, PoiManager.Occupancy.ANY).orElse(null);
	}
	
}
