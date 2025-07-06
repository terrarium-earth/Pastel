package earth.terrarium.pastel.helpers.level;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

import java.lang.ref.WeakReference;
import java.util.Optional;

/**
 * Why yes. I did in fact yoink this from PulseFlux.
 */
public final class BlockReference {

    private BlockState state;
    private final Optional<WeakReference<BlockEntity>> be;
    public final BlockPos pos;

    private BlockReference(BlockState state, Optional<BlockEntity> be, BlockPos pos) {
        this.state = state;
        this.be = be.map(WeakReference::new);
        this.pos = pos;
    }

    public static BlockReference of(BlockState state, BlockPos pos) {
        return new BlockReference(state, Optional.empty(), pos);
    }

    public static BlockReference of(LevelAccessor world, BlockPos pos) {
        return new BlockReference(world.getBlockState(pos), Optional.ofNullable(world.getBlockEntity(pos)), pos);
    }

    public BlockReference appendBE(BlockEntity entity) {
        return new BlockReference(state, Optional.of(entity), pos);
    }

    public BlockReference tryRecreateWithBE(LevelAccessor world) {
        return new BlockReference(state, Optional.ofNullable(world.getBlockEntity(pos)), pos);
    }

    public <V extends Comparable<V>> void set(Property<V> property, V value) {
        state = state.setValue(property, value);
    }

    public <V extends Comparable<V>> V get(Property<V> property) {
        return state.getValue(property);
    }

    public BlockState getState() {
        return state;
    }

    public boolean exists() {
        return state != null && pos != null;
    }

    public boolean isOf(Block block) {
        return state.is(block);
    }

    public boolean isOf(BlockState blockState) {
        return state == blockState;
    }

    public boolean isIn(TagKey<Block> tag) {
        return state.is(tag);
    }

    public boolean validateBE() {
        return be.isPresent();
    }

    public Optional<BlockEntity> tryGetBlockEntity() {
        return be.map(WeakReference::get);
    }

    public void update(LevelAccessor world, int flags) {
        world.setBlock(pos, state, flags);
    }

    public void update(LevelAccessor world) {
        update(world, Block.UPDATE_ALL);
    }
}