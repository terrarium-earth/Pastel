package earth.terrarium.pastel.blocks.imbrifer.flora;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;

public interface Dragonjag {

    enum Variant implements StringRepresentable {
        YELLOW(MapColor.SAND),
        RED(MapColor.NETHER),
        PINK(MapColor.WARPED_HYPHAE),
        PURPLE(MapColor.COLOR_PURPLE),
        BLACK(MapColor.TERRACOTTA_BLACK);

        public static final Codec<Variant> CODEC = StringRepresentable.fromEnum(Variant::values);

        private final MapColor mapColor;

        Variant(MapColor mapColor) {
            this.mapColor = mapColor;
        }

        public MapColor getMapColor() {
            return this.mapColor;
        }

        @Override
        public String getSerializedName() {
            return name();
        }
    }

    Dragonjag.Variant getVariant();

    static boolean canPlantOnTop(BlockState floor, BlockGetter world, BlockPos pos) {
        return floor.isSolidRender(world, pos);
    }

}
