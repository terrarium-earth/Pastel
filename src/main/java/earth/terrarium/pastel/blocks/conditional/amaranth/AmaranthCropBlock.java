package earth.terrarium.pastel.blocks.conditional.amaranth;

import com.mojang.serialization.MapCodec;
import de.dafuqs.revelationary.api.revelations.RevelationAware;
import earth.terrarium.pastel.blocks.TallCropBlock;
import earth.terrarium.pastel.registries.PastelAdvancements;
import earth.terrarium.pastel.registries.PastelItems;
import earth.terrarium.pastel.registries.client.PastelColorProviders;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Hashtable;
import java.util.Map;

public class AmaranthCropBlock extends TallCropBlock implements RevelationAware {

    public static final MapCodec<AmaranthCropBlock> CODEC = simpleCodec(AmaranthCropBlock::new);
    protected static final int LAST_SINGLE_BLOCK_AGE = 2;
    protected static final int MAX_AGE = 7;

    private static final VoxelShape[] AGE_TO_SHAPE = new VoxelShape[]{
        Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D),
        Block.box(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D),
        Block.box(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D),
        Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D),
        Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D),
        Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
        Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D),
        Block.box(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D)
    };

    public AmaranthCropBlock(Properties settings) {
        super(settings, LAST_SINGLE_BLOCK_AGE);
        RevelationAware.register(this);
    }

    @Override
    public MapCodec<? extends AmaranthCropBlock> codec() {
        return CODEC;
    }

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return PastelItems.AMARANTH_GRAINS.get();
    }

    @Override
    public ResourceLocation getCloakAdvancementIdentifier() {
        return PastelAdvancements.REVEAL_AMARANTH;
    }

    @Override
    public Map<BlockState, BlockState> getBlockStateCloaks() {
        BlockState smallFern = Blocks.FERN.defaultBlockState();
        BlockState largeFernLower = Blocks.LARGE_FERN.defaultBlockState()
                                                     .setValue(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER);
        BlockState largeFernUpper = Blocks.LARGE_FERN.defaultBlockState()
                                                     .setValue(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER);

        Map<BlockState, BlockState> map = new Hashtable<>();
        for (int age = 0; age <= LAST_SINGLE_BLOCK_AGE; age++) {
            map.put(this.withAgeAndHalf(age, DoubleBlockHalf.LOWER), smallFern);
            map.put(this.withAgeAndHalf(age, DoubleBlockHalf.UPPER), smallFern);
        }
        for (int age = LAST_SINGLE_BLOCK_AGE + 1; age <= MAX_AGE; age++) {
            map.put(this.withAgeAndHalf(age, DoubleBlockHalf.LOWER), largeFernLower);
            map.put(this.withAgeAndHalf(age, DoubleBlockHalf.UPPER), largeFernUpper);
        }
        return map;
    }

    @Override
    public @Nullable Tuple<Item, Item> getItemCloak() {
        return new Tuple<>(this.asItem(), Blocks.LARGE_FERN.asItem());
    }

    @Override
    public void onUncloak() {
        if (PastelColorProviders.amaranthCropBlockColorProvider != null &&
            PastelColorProviders.amaranthCropItemColorProvider != null) {
            PastelColorProviders.amaranthCropBlockColorProvider.setShouldApply(false);
            PastelColorProviders.amaranthCropItemColorProvider.setShouldApply(false);
        }
    }

    @Override
    public void onCloak() {
        if (PastelColorProviders.amaranthCropBlockColorProvider != null &&
            PastelColorProviders.amaranthCropItemColorProvider != null) {
            PastelColorProviders.amaranthCropBlockColorProvider.setShouldApply(true);
            PastelColorProviders.amaranthCropItemColorProvider.setShouldApply(true);
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        if (state.getValue(HALF) == DoubleBlockHalf.LOWER) {
            if (state.getValue(AGE) <= this.lastSingleBlockAge) {
                return AGE_TO_SHAPE[state.getValue(this.getAgeProperty())];
            } else {
                // Fill in the bottom block if the plant is two-tall
                return Shapes.block();
            }
        } else {
            return AGE_TO_SHAPE[state.getValue(this.getAgeProperty())];
        }
    }

}
