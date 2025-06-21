package earth.terrarium.pastel.worldgen.structure_pool_elements;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.registries.PastelStructurePoolElementTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.FrontAndTop;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.JigsawBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.JigsawBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElementType;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.LiquidSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import java.util.List;

public class SingleBlockPoolElement extends StructurePoolElement {
	
	public static final MapCodec<SingleBlockPoolElement> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
			BlockState.CODEC.fieldOf("block").forGetter((pool) -> pool.state),
			CompoundTag.CODEC.fieldOf("nbt").forGetter((pool) -> pool.blockNbt),
			projectionCodec()
	).apply(instance, SingleBlockPoolElement::new));
	
	protected final BlockState state;
	protected final CompoundTag blockNbt;
	
	private static final CompoundTag jigsawNbt = createDefaultJigsawNbt();
	
	protected SingleBlockPoolElement(BlockState state, CompoundTag blockNbt, StructureTemplatePool.Projection projection) {
		super(projection);
		this.state = state;
		this.blockNbt = blockNbt;
	}
	
	private static CompoundTag createDefaultJigsawNbt() {
		CompoundTag nbtCompound = new CompoundTag();
		nbtCompound.putString("name", "pastel:main");
		nbtCompound.putString("final_state", "minecraft:air");
		nbtCompound.putString("pool", "minecraft:empty");
		nbtCompound.putString("target", "minecraft:empty");
		nbtCompound.putString("joint", JigsawBlockEntity.JointType.ROLLABLE.getSerializedName());
		return nbtCompound;
	}
	
	@Override
	public Vec3i getSize(StructureTemplateManager structureTemplateManager, Rotation rotation) {
		return Vec3i.ZERO;
	}
	
	@Override
	public List<StructureTemplate.StructureBlockInfo> getShuffledJigsawBlocks(StructureTemplateManager structureTemplateManager, BlockPos pos, Rotation rotation, RandomSource random) {
		return List.of(new StructureTemplate.StructureBlockInfo(pos, Blocks.JIGSAW.defaultBlockState().setValue(JigsawBlock.ORIENTATION, FrontAndTop.fromFrontAndTop(Direction.DOWN, Direction.SOUTH)), jigsawNbt));
	}
	
	@Override
	public BoundingBox getBoundingBox(StructureTemplateManager structureTemplateManager, BlockPos pos, Rotation rotation) {
		Vec3i start = this.getSize(structureTemplateManager, rotation);
		return new BoundingBox(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + start.getX(), pos.getY() + start.getY(), pos.getZ() + start.getZ());
	}
	
	@Override
	public boolean place(StructureTemplateManager structureTemplateManager, WorldGenLevel world, StructureManager structureAccessor, ChunkGenerator chunkGenerator, BlockPos pos, BlockPos pivot, Rotation rotation, BoundingBox box, RandomSource random, LiquidSettings liquidSettings, boolean keepJigsaws) {
		if (keepJigsaws) {
			return true;
		}
		
		if (world.setBlock(pos.below(), this.state, Block.UPDATE_KNOWN_SHAPE | Block.UPDATE_ALL)) {
			if (this.blockNbt.isEmpty()) {
				return true;
			}
			
			BlockEntity blockEntity = world.getBlockEntity(pos.below());
			if (blockEntity != null) {
				blockEntity.loadCustomOnly(this.blockNbt, world.registryAccess());
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public StructurePoolElementType<?> getType() {
		return PastelStructurePoolElementTypes.SINGLE_BLOCK_ELEMENT;
	}
	
	@Override
	public String toString() {
		return "SpectrumSingleBlock[" + this.state.toString() + "]" + this.blockNbt;
	}
	
}
