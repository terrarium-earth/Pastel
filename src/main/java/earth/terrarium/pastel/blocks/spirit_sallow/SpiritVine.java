package earth.terrarium.pastel.blocks.spirit_sallow;

import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.PastelItems;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.VoxelShape;

public interface SpiritVine {

	VoxelShape SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);
	BooleanProperty CRYSTALS = BooleanProperty.create("crystals");

	static InteractionResult pick(BlockState blockState, Level world, BlockPos blockPos) {
		if (canBeHarvested(blockState)) {
			Block.popResource(world, blockPos, new ItemStack(getYieldItem(blockState), 1));
			float f = Mth.randomBetween(world.random, 0.8F, 1.2F);
			world.playSound(null, blockPos, SoundEvents.CAVE_VINES_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, f);
			world.setBlock(blockPos, blockState.setValue(CRYSTALS, false), 2);
			return InteractionResult.sidedSuccess(world.isClientSide);
		} else {
			return InteractionResult.PASS;
		}
	}

	static boolean canBeHarvested(BlockState state) {
		return state.hasProperty(CRYSTALS) && state.getValue(CRYSTALS);
	}
	
	static Item getYieldItem(BlockState blockState) {
		if (blockState.is(PastelBlocks.CYAN_SPIRIT_SALLOW_VINES.get()) || blockState.is(PastelBlocks.CYAN_SPIRIT_SALLOW_VINES_PLANT.get())) {
            return PastelItems.TOPAZ_SHARD.get();
		}
		if (blockState.is(PastelBlocks.MAGENTA_SPIRIT_SALLOW_VINES.get()) || blockState.is(PastelBlocks.MAGENTA_SPIRIT_SALLOW_VINES_PLANT.get())) {
			return Items.AMETHYST_SHARD;
		}
		if (blockState.is(PastelBlocks.YELLOW_SPIRIT_SALLOW_VINES.get()) || blockState.is(PastelBlocks.YELLOW_SPIRIT_SALLOW_VINES_PLANT.get())) {
            return PastelItems.CITRINE_SHARD.get();
		}
		if (blockState.is(PastelBlocks.BLACK_SPIRIT_SALLOW_VINES.get()) || blockState.is(PastelBlocks.BLACK_SPIRIT_SALLOW_VINES_PLANT.get())) {
            return PastelItems.ONYX_SHARD.get();
		}
		if (blockState.is(PastelBlocks.WHITE_SPIRIT_SALLOW_VINES.get()) || blockState.is(PastelBlocks.WHITE_SPIRIT_SALLOW_VINES_PLANT.get())) {
            return PastelItems.MOONSTONE_SHARD.get();
		}
		return Items.AIR;
	}

}
