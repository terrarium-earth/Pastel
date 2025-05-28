package earth.terrarium.pastel.blocks.mob_head;

import com.google.common.collect.BiMap;
import com.google.common.collect.EnumHashBiMap;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WallSkullBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public class SpectrumWallSkullBlock extends WallSkullBlock {

	public static final MapCodec<SpectrumWallSkullBlock> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
			SpectrumSkullType.CODEC.fieldOf("kind").forGetter(b -> b.skullType),
			propertiesCodec()
	).apply(i, SpectrumWallSkullBlock::new));

	public static BiMap<SpectrumSkullType, Block> MOB_WALL_HEADS = EnumHashBiMap.create(SpectrumSkullType.class);
	private final SpectrumSkullType skullType;
	
	public SpectrumWallSkullBlock(SpectrumSkullType skullType, Properties settings) {
		super(skullType, settings);
		this.skullType = skullType;
		MOB_WALL_HEADS.put(skullType, this);
	}

	@Override
	public MapCodec<? extends SpectrumWallSkullBlock> codec() {
		return CODEC;
	}
	
	public static Block getMobWallHead(SpectrumSkullType skullType) {
		return SpectrumWallSkullBlock.MOB_WALL_HEADS.get(skullType);
	}
	
	@Contract(pure = true)
	public static @NotNull Collection<Block> getMobWallHeads() {
		return SpectrumWallSkullBlock.MOB_WALL_HEADS.values();
	}
	
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new SpectrumSkullBlockEntity(pos, state);
	}
	
	@Override
	@Nullable
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
		return null;
	}
	
}
