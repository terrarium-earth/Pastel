package earth.terrarium.pastel.blocks.decoration;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.api.item.GemstoneColor;
import earth.terrarium.pastel.registries.PastelRegistries;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.TransparentBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class GemstoneGlassBlock extends TransparentBlock {

    public final MapCodec<GemstoneGlassBlock> codec;

    @Nullable
    final GemstoneColor gemstoneColor;

    public GemstoneGlassBlock(Properties settings, @Nullable GemstoneColor gemstoneColor) {
        super(settings);
        this.gemstoneColor = gemstoneColor;
        this.codec = RecordCodecBuilder.mapCodec(i -> i.group(
                                                           propertiesCodec(),
                                                           PastelRegistries.GEMSTONE_COLOR.byNameCodec()
                                                                                          .fieldOf("color")
                                                                                          .forGetter(b -> b.gemstoneColor)
                                                       )
                                                       .apply(i, GemstoneGlassBlock::new));
    }

    @Override
    public MapCodec<? extends GemstoneGlassBlock> codec() {
        return codec;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean skipRendering(BlockState state, BlockState stateFrom, Direction direction) {
        if (stateFrom.is(this)) {
            return true;
        }

        if (state.getBlock() instanceof GemstoneGlassBlock sourceGemstoneGlassBlock &&
            stateFrom.getBlock() instanceof GemstoneGlassBlock targetGemstoneGlassBlock) {
            return sourceGemstoneGlassBlock.gemstoneColor == targetGemstoneGlassBlock.gemstoneColor;
        }
        return super.skipRendering(state, stateFrom, direction);
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter world, BlockPos pos) {
        return true;
    }

}
