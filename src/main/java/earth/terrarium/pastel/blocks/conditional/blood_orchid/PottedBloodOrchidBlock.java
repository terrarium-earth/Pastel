package earth.terrarium.pastel.blocks.conditional.blood_orchid;

import de.dafuqs.revelationary.api.revelations.RevelationAware;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Hashtable;
import java.util.Map;

public class PottedBloodOrchidBlock extends FlowerPotBlock implements RevelationAware {

    public PottedBloodOrchidBlock(Block content, Properties settings) {
        super(content, settings);
        RevelationAware.register(this);
    }

//	@Override
//	public MapCodec<? extends PottedBloodOrchidBlock> getCodec() {
//		//TODO: Make the codec
//		return null;
//	}

    @Override
    public ResourceLocation getCloakAdvancementIdentifier() {
        return BloodOrchidBlock.ADVANCEMENT_IDENTIFIER;
    }

    @Override
    public Map<BlockState, BlockState> getBlockStateCloaks() {
        Map<BlockState, BlockState> map = new Hashtable<>();
        map.put(this.defaultBlockState(), Blocks.POTTED_RED_TULIP.defaultBlockState());
        return map;
    }

    @Override
    public @Nullable Tuple<Item, Item> getItemCloak() {
        return null; // does not exist in item form
    }

}
