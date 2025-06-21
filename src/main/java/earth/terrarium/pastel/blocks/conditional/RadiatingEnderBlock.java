package earth.terrarium.pastel.blocks.conditional;

import com.mojang.serialization.MapCodec;
import de.dafuqs.revelationary.api.revelations.RevelationAware;
import earth.terrarium.pastel.registries.PastelAdvancements;
import earth.terrarium.pastel.registries.PastelBlocks;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

import java.util.List;
import java.util.Map;

public class RadiatingEnderBlock extends Block implements RevelationAware {

	public static final MapCodec<RadiatingEnderBlock> CODEC = simpleCodec(RadiatingEnderBlock::new);

	public RadiatingEnderBlock(Properties settings) {
		super(settings);
		RevelationAware.register(this);
	}

	@Override
	public MapCodec<? extends RadiatingEnderBlock> codec() {
		return CODEC;
	}
	
	@Override
	public ResourceLocation getCloakAdvancementIdentifier() {
		return PastelAdvancements.REVEAL_RADIATING_ENDER;
	}
	
	@Override
	public Map<BlockState, BlockState> getBlockStateCloaks() {
		return Map.of(this.defaultBlockState(), Blocks.COBBLESTONE.defaultBlockState());
	}
	
	@Override
	public Tuple<Item, Item> getItemCloak() {
		return new Tuple<>(this.asItem(), Blocks.COBBLESTONE.asItem());
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
