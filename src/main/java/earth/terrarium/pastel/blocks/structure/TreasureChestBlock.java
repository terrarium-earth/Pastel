package earth.terrarium.pastel.blocks.structure;

import com.mojang.serialization.MapCodec;
import earth.terrarium.pastel.SpectrumCommon;
import earth.terrarium.pastel.blocks.chests.SpectrumChestBlock;
import earth.terrarium.pastel.registries.SpectrumBlockEntities;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class TreasureChestBlock extends SpectrumChestBlock {

	public static final MapCodec<TreasureChestBlock> CODEC = simpleCodec(TreasureChestBlock::new);

	public TreasureChestBlock(Properties settings) {
		super(settings);
	}

	@Override
	public MapCodec<? extends TreasureChestBlock> codec() {
		return CODEC;
	}
	
	@Override
	public void openScreen(Level world, BlockPos pos, Player player) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity instanceof TreasureChestBlockEntity treasureChestBlockEntity) {
			if (!isChestBlocked(world, pos)) {
				if (treasureChestBlockEntity.canOpen(player)) {
					player.openMenu(treasureChestBlockEntity);
				} else {
					world.playSound(null, pos, SoundEvents.CHEST_LOCKED, SoundSource.PLAYERS, 1.0F, 1.0F);
				}
			}
		}
	}
	
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new TreasureChestBlockEntity(pos, state);
	}
	
	@Override
	@Nullable
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
		return world.isClientSide ? createTickerHelper(type, SpectrumBlockEntities.PRESERVATION_CHEST.get(), TreasureChestBlockEntity::clientTick) : null;
	}
	
	@Override
	public Material getTextureLocation() {
		return new Material(InventoryMenu.BLOCK_ATLAS, SpectrumCommon.locate("block/preservation_chest"));
	}
	
}
