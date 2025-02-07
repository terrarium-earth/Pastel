package de.dafuqs.spectrum.blocks.deeper_down;

import com.mojang.serialization.*;
import de.dafuqs.revelationary.api.revelations.*;
import de.dafuqs.spectrum.api.block.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.sound.*;
import net.minecraft.state.property.Properties;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraft.world.explosion.*;
import org.jetbrains.annotations.*;

import java.util.*;
import java.util.function.*;

public class DragonboneBlock extends PillarBlock implements RevelationAware, MoonstoneStrikeableBlock {

	public static final MapCodec<DragonboneBlock> CODEC = createCodec(DragonboneBlock::new);

	public DragonboneBlock(Settings settings) {
		super(settings);
		RevelationAware.register(this);
	}

	@Override
	public MapCodec<? extends DragonboneBlock> getCodec() {
		return CODEC;
	}
	
	@Override
	public void onMoonstoneStrike(World world, BlockPos pos, @Nullable LivingEntity striker) {
		crack(world, pos);
	}
	
	public void crack(World world, BlockPos pos) {
		BlockState state = world.getBlockState(pos);
		if (state.getBlock() instanceof DragonboneBlock) {
			world.setBlockState(pos, SpectrumBlocks.CRACKED_DRAGONBONE.getDefaultState().with(PillarBlock.AXIS, state.get(PillarBlock.AXIS)));
			if (world.isClient) {
				world.playSound(null, pos, SoundEvents.ENTITY_TURTLE_EGG_CRACK, SoundCategory.BLOCKS, 1.0F, MathHelper.nextBetween(world.random, 0.8F, 1.2F));
			}
		}
	}
	
	@Override
	public boolean shouldDropItemsOnExplosion(Explosion explosion) {
		return false;
	}
	
	@Override
	protected void onExploded(BlockState state, World world, BlockPos pos, Explosion explosion, BiConsumer<ItemStack, BlockPos> stackMerger) {
		if (state.getBlock() instanceof PillarBlock) {
			world.setBlockState(pos, SpectrumBlocks.CRACKED_DRAGONBONE.getDefaultState().with(PillarBlock.AXIS, state.get(PillarBlock.AXIS)));
		}
	}
	
	@Override
	public Identifier getCloakAdvancementIdentifier() {
		return SpectrumAdvancements.REVEAL_DRAGONBONE;
	}
	
	@Override
	public Map<BlockState, BlockState> getBlockStateCloaks() {
		Map<BlockState, BlockState> map = new Hashtable<>();
		for (Direction.Axis axis : Properties.AXIS.getValues()) {
			map.put(this.getDefaultState().with(Properties.AXIS, axis), Blocks.BONE_BLOCK.getDefaultState().with(Properties.AXIS, axis));
		}
		return map;
	}
	
	@Override
	public @Nullable Pair<Item, Item> getItemCloak() {
		return new Pair<>(this.asItem(), Blocks.BONE_BLOCK.asItem());
	}
	
}
