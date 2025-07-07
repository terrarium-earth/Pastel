package earth.terrarium.pastel.blocks.conditional.colored_tree;

import de.dafuqs.revelationary.api.revelations.RevelationAware;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.api.energy.color.InkColor;
import earth.terrarium.pastel.blocks.*;
import earth.terrarium.pastel.helpers.Support;
import earth.terrarium.pastel.helpers.level.BlockReference;
import earth.terrarium.pastel.helpers.render.ParticleHelper;
import earth.terrarium.pastel.particle.effect.ColoredCraftingParticleEffect;
import earth.terrarium.pastel.registries.PastelItemTags;
import earth.terrarium.pastel.registries.PastelItems;
import earth.terrarium.pastel.registries.PastelSoundEvents;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Tuple;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class ColoredLogBlock extends PastelLogBlock implements RevelationAware, ColoredTree {

	public static final BooleanProperty DRIPPING = BooleanProperty.create("dripping");

	private static final Vec3 VELOCITY = new Vec3(0, -0.0334, 0);
	private static final Map<InkColor, ColoredLogBlock> LOGS = new Object2ObjectArrayMap<>();

	protected final ResourceKey<LootTable> tappingLoot;
	protected final InkColor color;
	
	public ColoredLogBlock(Properties settings, InkColor color, Block sourceBlock) {
		super(settings, sourceBlock);
		this.color = color;
		LOGS.put(color, this);
		RevelationAware.register(this);

		tappingLoot = ResourceKey.create(Registries.LOOT_TABLE, PastelCommon.locate("gameplay/tapping/" + color.getLootName()));
		registerDefaultState(defaultBlockState().setValue(DRIPPING, false).setValue(NATURAL, false));//
	}
	
	@Override
	public ResourceLocation getCloakAdvancementIdentifier() {
		return ColoredTree.getTreeCloakAdvancementIdentifier(ColoredTree.TreePart.LOG, this.color);
	}
	
	@Override
	public Map<BlockState, BlockState> getBlockStateCloaks() {
		Map<BlockState, BlockState> map = new Hashtable<>();
		for (Direction.Axis axis : RotatedPillarBlock.AXIS.getPossibleValues()) {
			var normal = this.defaultBlockState().setValue(RotatedPillarBlock.AXIS, axis).setValue(NATURAL, true);
			map.put(normal, Blocks.OAK_LOG.defaultBlockState().setValue(RotatedPillarBlock.AXIS, axis));
			map.put(normal.setValue(DRIPPING, true), Blocks.OAK_LOG.defaultBlockState().setValue(RotatedPillarBlock.AXIS, axis));
		}
		return map;
	}

	@Override
	protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
		if (applyNectar(stack, state, level, pos, player))
			return ItemInteractionResult.sidedSuccess(level.isClientSide());

		if (!tap(stack, state, level, pos))
			return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;

		if (level.isClientSide())
			return ItemInteractionResult.SUCCESS;

		for (ItemStack tapStack : getTapStacks(state, (ServerLevel) level, pos, stack, player)) {
			player.getInventory().placeItemBackInInventory(tapStack);
		}

		level.playSound(null, pos, SoundEvents.BEEHIVE_SHEAR,
				SoundSource.BLOCKS, 1F, Support.varFloatCentered(level.random, 0.2F));
		return ItemInteractionResult.CONSUME;
	}

	public List<ItemStack> getTapStacks(BlockState state, ServerLevel level, BlockPos pos, ItemStack stack, Player player) {
		var builder = (new LootParams.Builder(level))
				.withParameter(LootContextParams.THIS_ENTITY, player)
				.withParameter(LootContextParams.BLOCK_STATE, state)
				.withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(pos))
				.withParameter(LootContextParams.TOOL, stack);

		LootTable lootTable = level.getServer().reloadableRegistries().getLootTable(tappingLoot);
		return lootTable.getRandomItems(builder.create(LootContextParamSets.BLOCK));
	}

	private boolean tap(ItemStack stack, BlockState state, Level level, BlockPos pos) {
		if (!stack.is(PastelItemTags.INK_TAPPING))
			return false;

		var block = BlockReference.of(state, pos);

		if (!block.get(DRIPPING))
			return false;

		block.set(DRIPPING, false);
		block.update(level);

		return true;
	}

	private boolean applyNectar(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player) {
		var block = BlockReference.of(state, pos);

		if (!stack.is(PastelItems.MOONSTRUCK_NECTAR) || block.get(NATURAL))
			return false;


		if (!player.isCreative())
			stack.shrink(1);

		level.playLocalSound(pos, PastelSoundEvents.LIGHT_CRYSTAL_RING, SoundSource.BLOCKS,
				1F, 1F, true);
		ParticleHelper.horizontalBlock(level, ColoredCraftingParticleEffect.of(color.getColorInt()),
				pos, 11, VELOCITY);

		block.set(NATURAL, true);
		block.update(level);
		return true;
	}

	@Override
	protected boolean isRandomlyTicking(BlockState state) {
		return state.getValue(NATURAL) && !state.getValue(DRIPPING);
	}

	@Override
	protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		var block = BlockReference.of(state, pos);

		if (state.getValue(DRIPPING) || !block.get(NATURAL))
			return;

		if (random.nextFloat() < 0.1F && canDrip(block, level)) {
			block.set(DRIPPING, true);
			block.update(level);
		}
	}

	private boolean canDrip(BlockReference reference, ServerLevel level) {
		for (Direction dir : Direction.values()) {
			var check = reference.pos.relative(dir);
			var state = level.getBlockState(check);
			var horizontal = dir.getAxis().isHorizontal();

			if (!horizontal && isDripping(state)) {
				return false;
			}

			if (horizontal && state.isFaceSturdy(level, check, dir.getOpposite()))
				return false;
		}

		return true;
	}

	private boolean isDripping(BlockState state) {
		if (!state.is(this))
			return false;

		return state.getValue(DRIPPING);
	}

	@Override
	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
		super.animateTick(state, level, pos, random);

		if (!isDripping(state))
			return;

		ParticleHelper.horizontalBlock(level, ColoredCraftingParticleEffect.of(color.getColorInt()),
				pos, 1, VELOCITY);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(DRIPPING);
		builder.add(NATURAL);
	}

	@Override
	public Tuple<Item, Item> getItemCloak() {
		return new Tuple<>(this.asItem(), Blocks.OAK_LOG.asItem());
	}
	
	@Override
	public InkColor getColor() {
		return this.color;
	}
	
	public static ColoredLogBlock byColor(InkColor color) {
		return LOGS.get(color);
	}
	
	public static Collection<ColoredLogBlock> all() {
		return LOGS.values();
	}

	@Override
	public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ItemAbility itemAbility, boolean simulate) {
		var newState = super.getToolModifiedState(state, context, itemAbility, simulate);

		if (newState != null)
			newState = newState.setValue(NATURAL, state.getValue(NATURAL));

		return newState;
	}
}
