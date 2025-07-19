package earth.terrarium.pastel.blocks;

import earth.terrarium.pastel.blocks.fluid.LiquidCrystalFluidBlock;
import earth.terrarium.pastel.registries.PastelFluids;
import earth.terrarium.pastel.registries.PastelItems;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class FluidLogging {
	
	public enum State implements StringRepresentable {
		NOT_LOGGED("none", 0),
		WATER("water", 0),
		LIQUID_CRYSTAL("liquid_crystal", LiquidCrystalFluidBlock.LUMINANCE);
		
		private final String name;
		private final int luminance;
		
		State(String name, int luminance) {
			this.name = name;
			this.luminance = luminance;
		}
		
		@Override
		public String getSerializedName() {
			return this.name;
		}
		
		public FluidState getFluidState() {
			switch (this) {
				case LIQUID_CRYSTAL -> {
					return PastelFluids.LIQUID_CRYSTAL.get().getSource(false);
				}
				case WATER -> {
					return Fluids.WATER.getSource(false);
				}
				default -> {
					return Fluids.EMPTY.defaultFluidState();
				}
			}
		}
		
		public static State getForFluidState(FluidState fluidState) {
			if (fluidState.getType() == PastelFluids.LIQUID_CRYSTAL.get()) {
				return LIQUID_CRYSTAL;
			} else if (fluidState.is(FluidTags.WATER)) {
				return WATER;
			}
			
			return NOT_LOGGED;
		}
		
		public int getLuminance() {
			return luminance;
		}
		
		public boolean isOf(Fluid fluid) {
			return this.getFluidState().is(fluid);
		}
		
		public boolean isIn(TagKey<Fluid> fluidTag) {
			return this.getFluidState().is(fluidTag);
		}
		
		@Override
		public String toString() {
			return this.name;
		}
		
		public void onEntityCollision(BlockState state, Level world, BlockPos pos, Entity entity) {
			if (this == State.LIQUID_CRYSTAL) {
				PastelFluids.LIQUID_CRYSTAL.get().onEntityCollision(state, world, pos, entity);
			}
		}
	}
	
	public static final EnumProperty<State> ANY_INCLUDING_NONE = EnumProperty.create("fluid_logged", State.class);
	public static final EnumProperty<State> ANY_EXCLUDING_NONE = EnumProperty.create("fluid_logged", State.class, State.WATER, State.LIQUID_CRYSTAL);
	public static final EnumProperty<State> NONE_AND_CRYSTAL = EnumProperty.create("fluid_logged", State.class, State.NOT_LOGGED, State.LIQUID_CRYSTAL);
	
	public interface PastelFluidLoggable extends PastelFluidDrainable, PastelFluidFillable {
	
	}
	
	public interface PastelFluidFillable extends LiquidBlockContainer {
		
		@Override
		default boolean canPlaceLiquid(@Nullable Player player, BlockGetter world, BlockPos pos, BlockState state, Fluid fluid) {
			return state.getValue(ANY_INCLUDING_NONE) == State.NOT_LOGGED && (fluid == Fluids.WATER || fluid == PastelFluids.LIQUID_CRYSTAL.get());
		}
		
		@Override
		default boolean placeLiquid(LevelAccessor world, BlockPos pos, BlockState state, FluidState fluidState) {
			if (state.getValue(ANY_INCLUDING_NONE) == State.NOT_LOGGED) {
				if (!world.isClientSide()) {
					if (fluidState.getType() == Fluids.WATER) {
						world.setBlock(pos, state.setValue(ANY_INCLUDING_NONE, State.WATER), Block.UPDATE_ALL);
						world.scheduleTick(pos, fluidState.getType(), fluidState.getType().getTickDelay(world));
					} else if (fluidState.getType() == PastelFluids.LIQUID_CRYSTAL.get()) {
						world.setBlock(pos, state.setValue(ANY_INCLUDING_NONE, State.LIQUID_CRYSTAL), Block.UPDATE_ALL);
						world.scheduleTick(pos, fluidState.getType(), fluidState.getType().getTickDelay(world));
					}
				}
				
				return true;
			} else {
				return false;
			}
		}
		
	}
	
	public interface PastelFluidDrainable extends BucketPickup {
		
		@Override
		default ItemStack pickupBlock(@Nullable Player player, LevelAccessor world, BlockPos pos, BlockState state) {
			State fluidLog = state.getValue(ANY_INCLUDING_NONE);
			
			if (fluidLog == State.WATER) {
				world.setBlock(pos, state.setValue(ANY_INCLUDING_NONE, State.NOT_LOGGED), Block.UPDATE_ALL);
				if (!state.canSurvive(world, pos)) {
					world.destroyBlock(pos, true);
				}
				return new ItemStack(Items.WATER_BUCKET);
			} else if (fluidLog == State.LIQUID_CRYSTAL) {
				world.setBlock(pos, state.setValue(ANY_INCLUDING_NONE, State.NOT_LOGGED), Block.UPDATE_ALL);
				if (!state.canSurvive(world, pos)) {
					world.destroyBlock(pos, true);
				}
				return new ItemStack(PastelItems.LIQUID_CRYSTAL_BUCKET.get());
			}
			
			return ItemStack.EMPTY;
		}
		
		@Override
		default Optional<SoundEvent> getPickupSound() {
			return Fluids.WATER.getPickupSound();
		}
		
	}
	
}
