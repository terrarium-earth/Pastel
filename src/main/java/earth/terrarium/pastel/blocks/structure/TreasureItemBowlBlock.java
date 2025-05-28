package earth.terrarium.pastel.blocks.structure;

import com.mojang.serialization.MapCodec;
import earth.terrarium.pastel.registries.SpectrumItems;
import earth.terrarium.pastel.registries.SpectrumStatusEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class TreasureItemBowlBlock extends Block implements EntityBlock {

	public static final MapCodec<TreasureItemBowlBlock> CODEC = simpleCodec(TreasureItemBowlBlock::new);

	protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 11.0D, 16.0D);

	public TreasureItemBowlBlock(Properties settings) {
		super(settings);
	}

	@Override
	public MapCodec<? extends TreasureItemBowlBlock> codec() {
		return CODEC;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	@Override
	public InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
		var entity = world.getBlockEntity(pos);

		if (!(entity instanceof PlayerTrackerBlockEntity bowl))
			return InteractionResult.PASS;

		if (bowl.hasTaken(player) || !canInteract(player))
			return InteractionResult.FAIL;

		world.playLocalSound(pos, SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.BLOCKS, 1F, 1F, true);
		player.getInventory().placeItemBackInInventory(SpectrumItems.AETHER_GRACED_NECTAR_GLOVES.get().getDefaultInstance());
		bowl.markTaken(player);

		return InteractionResult.CONSUME;
	}

	public static boolean canInteract(Player player) {
		return player.hasEffect(SpectrumStatusEffects.FATAL_SLUMBER);
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new PlayerTrackerBlockEntity(pos, state);
	}
}
