package earth.terrarium.pastel.blocks.conditional;

import com.mojang.serialization.MapCodec;
import earth.terrarium.pastel.registries.PastelBlocks;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

import java.util.List;

public class RadiatingEnderBlock extends Block {

	public static final MapCodec<RadiatingEnderBlock> CODEC = simpleCodec(RadiatingEnderBlock::new);

	public RadiatingEnderBlock(Properties settings) {
		super(settings);
	}

	@Override
	public MapCodec<? extends RadiatingEnderBlock> codec() {
		return CODEC;
	}

	@Override
	public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
		Entity entity = builder.getOptionalParameter(LootContextParams.THIS_ENTITY);
		if (entity instanceof EnderMan) {
			return List.of(PastelBlocks.RADIATING_ENDER.get().asItem().getDefaultInstance());
		}
		return super.getDrops(state, builder);
	}
	
}
