package earth.terrarium.pastel.compat.exclusions_lib;

import com.mojang.serialization.MapCodec;
import earth.terrarium.pastel.compat.SpectrumIntegrationPacks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicateType;
import net.neoforged.bus.api.*;
import net.neoforged.neoforge.registries.*;

/**
 * Loaded when Exclusions Lib is *not* present
 * So the game can be loaded without complaining about a missing Block Predicate
 */
public class ExclusionsLibCompat {
	
	public static class AlwaysFalseBlockPredicate implements BlockPredicate {
		
		public static AlwaysFalseBlockPredicate instance = new AlwaysFalseBlockPredicate();
		public static final MapCodec<AlwaysFalseBlockPredicate> CODEC = MapCodec.unit(() -> instance);
		
		private AlwaysFalseBlockPredicate() {
		}
		
		public boolean test(WorldGenLevel structureWorldAccess, BlockPos blockPos) {
			return false;
		}
		
		public BlockPredicateType<?> type() {
			return BlockPredicateType.TRUE;
		}
	}
	
	public static final DeferredRegister<BlockPredicateType<?>> REGISTRY = DeferredRegister.create(BuiltInRegistries.BLOCK_PREDICATE_TYPE, SpectrumIntegrationPacks.EXCLUSIONS_LIB_ID);
	
	public static void registerNotPresent(IEventBus bus) {
		registerBlockPredicate("overlaps_structure", AlwaysFalseBlockPredicate.CODEC);
		REGISTRY.register(bus);
	}
	
	private static <P extends BlockPredicate> void registerBlockPredicate(String id, MapCodec<P> codec) {
		REGISTRY.register(id, () -> ((BlockPredicateType<P>) () -> codec));
	}
	
}