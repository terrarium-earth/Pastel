package earth.terrarium.pastel.blocks.geology;

import earth.terrarium.pastel.registries.PastelBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public class AzuriteSpireBlock extends Block {
    public static final EnumProperty<SpirePart> SPIRE_PART = EnumProperty.create("spire_part",SpirePart.class);
    public static final DirectionProperty DIRECTION = DirectionProperty.create("direction");

    public AzuriteSpireBlock(Properties properties) {
        super(properties);
    }

    public static void setTo(ServerLevel level, BlockPos pos, SpirePart part){
        var state = level.getBlockState(pos);
        if(!state.is(PastelBlocks.AZURE_OUTCROP) || part == state.getValue(SPIRE_PART))return;
    }

    public enum SpirePart implements StringRepresentable{
        TIP("tip"),
        BODY("body"),
        BASE("base");

        private final String name;
        SpirePart(String name){this.name=name;}

        @Override
        public String getSerializedName() {
            return name;
        }
    }
}
