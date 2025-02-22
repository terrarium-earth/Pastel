package de.dafuqs.spectrum.blocks.deeper_down.flora;

import com.mojang.serialization.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

public interface Dragonjag {
	
	enum Variant implements StringIdentifiable {
		YELLOW(MapColor.PALE_YELLOW),
		RED(MapColor.DARK_RED),
		PINK(MapColor.DARK_DULL_PINK),
		PURPLE(MapColor.PURPLE),
		BLACK(MapColor.TERRACOTTA_BLACK);
		
		public static final Codec<Variant> CODEC = StringIdentifiable.createCodec(Variant::values);
		
		private final MapColor mapColor;
		
		Variant(MapColor mapColor) {
			this.mapColor = mapColor;
		}
		
		public MapColor getMapColor() {
			return this.mapColor;
		}
		
		@Override
		public String asString() {
			return name();
		}
	}
	
	Dragonjag.Variant getVariant();
	
	static boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
		return floor.isOpaqueFullCube(world, pos);
	}
	
}
