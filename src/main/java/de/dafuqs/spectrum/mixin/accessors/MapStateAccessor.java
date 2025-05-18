package de.dafuqs.spectrum.mixin.accessors;

import net.minecraft.world.level.saveddata.maps.MapBanner;
import net.minecraft.world.level.saveddata.maps.MapDecoration;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Map;

@Mixin(MapItemSavedData.class)
public interface MapStateAccessor {
	
	@Accessor
	boolean getTrackingPosition();
	
	@Accessor
	boolean getUnlimitedTracking();
	
	@Accessor
	Map<String, MapBanner> getBannerMarkers();
	
	@Accessor
	Map<String, MapDecoration> getDecorations();
	
	@Accessor
	int getTrackedDecorationCount();
	
	@Accessor
	void setTrackedDecorationCount(int decorationCount);
	
	@Invoker
	void invokeSetDecorationsDirty();
	
	@Invoker
	void invokeRemoveDecoration(String id);
	
}
