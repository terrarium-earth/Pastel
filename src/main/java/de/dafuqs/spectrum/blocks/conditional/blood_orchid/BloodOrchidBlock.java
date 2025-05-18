package de.dafuqs.spectrum.blocks.conditional.blood_orchid;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.dafuqs.revelationary.api.revelations.RevelationAware;
import de.dafuqs.spectrum.SpectrumCommon;
import de.dafuqs.spectrum.progression.SpectrumAdvancementCriteria;
import de.dafuqs.spectrum.registries.SpectrumItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Tuple;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.SuspiciousStewEffects;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Hashtable;
import java.util.Map;

public class BloodOrchidBlock extends FlowerBlock implements BonemealableBlock, RevelationAware {

	public static final MapCodec<BloodOrchidBlock> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			EFFECTS_FIELD.forGetter(FlowerBlock::getSuspiciousEffects),
			propertiesCodec()
	).apply(instance, BloodOrchidBlock::new));

	public static final ResourceLocation ADVANCEMENT_IDENTIFIER = SpectrumCommon.locate("midgame/collect_blood_orchid_petal");
	public static final IntegerProperty AGE = BlockStateProperties.AGE_5;
	
	public BloodOrchidBlock(Holder<MobEffect> stewEffect, float effectLengthInSeconds, Properties settings) {
		this(makeEffectList(stewEffect, effectLengthInSeconds), settings);
	}

	public BloodOrchidBlock(SuspiciousStewEffects stewEffects, BlockBehaviour.Properties settings) {
		super(stewEffects, settings);
		this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0));
		RevelationAware.register(this);
	}

	@Override
	public MapCodec<? extends BloodOrchidBlock> codec() {
		return CODEC;
	}
	
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(AGE);
	}
	
	private void growOnce(BlockState state, ServerLevel world, BlockPos pos) {
		BlockState newState = state.setValue(AGE, state.getValue(AGE) + 1);
		world.setBlockAndUpdate(pos, newState);
		world.playSound(null, pos, state.getSoundType().getPlaceSound(), SoundSource.BLOCKS, 1.0F, 1.0F);
	}
	
	@Override
	public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
		if (state.getValue(AGE) < BlockStateProperties.MAX_AGE_5 && random.nextFloat() <= 0.25) {
			growOnce(state, world, pos);
		}
	}
	
	@Override
	public InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
		int age = state.getValue(AGE);
		if (age > 0) {
			if (world.isClientSide) {
				return InteractionResult.SUCCESS;
			} else {
				world.setBlockAndUpdate(pos, state.setValue(AGE, age - 1));
				player.getInventory().placeItemBackInInventory(SpectrumItems.BLOOD_ORCHID_PETAL.getDefaultInstance());
				world.playSound(null, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1.0F, 0.9F + world.random.nextFloat() * 0.2F);
				if (player instanceof ServerPlayer serverPlayerEntity) {
					SpectrumAdvancementCriteria.BLOOD_ORCHID_PLUCKING.trigger(serverPlayerEntity);
				}
				return InteractionResult.CONSUME;
			}
		}
		return InteractionResult.PASS;
	}
	
	@Override
	public ResourceLocation getCloakAdvancementIdentifier() {
		return ADVANCEMENT_IDENTIFIER;
	}
	
	@Override
	public Map<BlockState, BlockState> getBlockStateCloaks() {
		Map<BlockState, BlockState> map = new Hashtable<>();
		for (int i = 0; i <= BlockStateProperties.MAX_AGE_5; i++) {
			map.put(this.defaultBlockState().setValue(AGE, i), Blocks.RED_TULIP.defaultBlockState());
		}
		return map;
	}
	
	@Override
	public Tuple<Item, Item> getItemCloak() {
		return null; // does not exist in item form
	}
	
	@Override
	public boolean isValidBonemealTarget(LevelReader world, BlockPos pos, BlockState state) {
		return state.getValue(AGE) < BlockStateProperties.MAX_AGE_5;
	}
	
	@Override
	public boolean isBonemealSuccess(Level world, RandomSource random, BlockPos pos, BlockState state) {
		return true;
	}
	
	@Override
	public void performBonemeal(ServerLevel world, RandomSource random, BlockPos pos, BlockState state) {
		growOnce(state, world, pos);
	}
	
}
