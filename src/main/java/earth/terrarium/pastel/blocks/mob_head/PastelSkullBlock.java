package earth.terrarium.pastel.blocks.mob_head;

import com.google.common.collect.BiMap;
import com.google.common.collect.EnumHashBiMap;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.helpers.Support;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.core.*;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.*;

public class PastelSkullBlock extends SkullBlock {

	public static final MapCodec<PastelSkullBlock> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
			PastelSkullType.CODEC.fieldOf("kind").forGetter(b -> b.skullType),
			propertiesCodec()
	).apply(i, PastelSkullBlock::new));

	public static final BiMap<PastelSkullType, Block> MOB_HEADS = EnumHashBiMap.create(PastelSkullType.class);
	private static final Map<Supplier<EntityType<?>>, PastelSkullType> ENTITY_TYPE_TO_SKULL_TYPE = new Object2ObjectOpenHashMap<>();
	private final PastelSkullType skullType;

	@Nullable
	private static BlockPattern witherBossPattern;

	public PastelSkullBlock(PastelSkullType skullType, Properties settings) {
		super(skullType, settings);

		// TODO Avoid this pattern, blocks shouldn't register themselves to a list
		MOB_HEADS.put(skullType, this);
		ENTITY_TYPE_TO_SKULL_TYPE.put(skullType.getEntityType(), skullType);
		this.skullType = skullType;
	}

	@Override
	public MapCodec<? extends PastelSkullBlock> codec() {
		return CODEC;
	}

	@Override
	public PastelSkullType getType() {
		return (PastelSkullType) super.getType();
	}

	public static Optional<EntityType<?>> getEntityTypeOfSkullStack(ItemStack itemStack) {
		Item item = itemStack.getItem();

		if (item instanceof BlockItem blockItem &&
				blockItem.getBlock() instanceof SkullBlock skullBlock &&
				skullBlock.getType() instanceof PastelSkullType type
		) {
			return Optional.of(type.getEntityType().get());
		}

		if (Items.CREEPER_HEAD == item) {
			return Optional.of(EntityType.CREEPER);
		} else if (Items.DRAGON_HEAD == item) {
			return Optional.of(EntityType.ENDER_DRAGON);
		} else if (Items.ZOMBIE_HEAD == item) {
			return Optional.of(EntityType.ZOMBIE);
		} else if (Items.SKELETON_SKULL == item) {
			return Optional.of(EntityType.SKELETON);
		} else if (Items.WITHER_SKELETON_SKULL == item) {
			return Optional.of(EntityType.WITHER_SKELETON);
		} else if (Items.PIGLIN_HEAD == item) {
			return Optional.of(EntityType.PIGLIN);
		}
		return Optional.empty();
	}

	public static Optional<Block> getBlock(SkullBlock.Type skullType) {
		if (skullType instanceof PastelSkullType pastelSkullType) {
			return Optional.of(MOB_HEADS.get(pastelSkullType));
		}
		if (SkullBlock.Types.CREEPER == skullType) {
			return Optional.of(Blocks.CREEPER_HEAD);
		} else if (SkullBlock.Types.DRAGON == skullType) {
			return Optional.of(Blocks.DRAGON_HEAD);
		} else if (SkullBlock.Types.ZOMBIE == skullType) {
			return Optional.of(Blocks.ZOMBIE_HEAD);
		} else if (SkullBlock.Types.SKELETON == skullType) {
			return Optional.of(Blocks.SKELETON_SKULL);
		} else if (SkullBlock.Types.WITHER_SKELETON == skullType) {
			return Optional.of(Blocks.WITHER_SKELETON_SKULL);
		} else if (SkullBlock.Types.PIGLIN == skullType) {
			return Optional.of(Blocks.PIGLIN_HEAD);
		}
		return Optional.empty();
	}

	public static Optional<SkullBlock.Type> getSkullType(EntityType<?> entityType) {
		if (EntityType.CREEPER == entityType) {
			return Optional.of(SkullBlock.Types.CREEPER);
		} else if (EntityType.ENDER_DRAGON == entityType) {
			return Optional.of(SkullBlock.Types.DRAGON);
		} else if (EntityType.ZOMBIE == entityType) {
			return Optional.of(SkullBlock.Types.ZOMBIE);
		} else if (EntityType.SKELETON == entityType) {
			return Optional.of(SkullBlock.Types.SKELETON);
		} else if (EntityType.WITHER_SKELETON == entityType) {
			return Optional.of(SkullBlock.Types.WITHER_SKELETON);
		} else if (EntityType.PIGLIN == entityType) {
			return Optional.of(SkullBlock.Types.PIGLIN);
		}

		return Optional.ofNullable(ENTITY_TYPE_TO_SKULL_TYPE.get(entityType));
	}

	@Contract(pure = true)
	public static @NotNull Collection<Block> getMobHeads() {
		return MOB_HEADS.values();
	}

	private static BlockPattern getWitherSkullPattern() {
		if (witherBossPattern == null) {
			getBlock(PastelSkullType.WITHER).ifPresent(b ->
				witherBossPattern = BlockPatternBuilder.start().aisle("^^^", "###", "~#~")
						.where('#', (pos) -> pos.getState().is(BlockTags.WITHER_SUMMON_BASE_BLOCKS))
						.where('^', BlockInWorld.hasState(BlockStatePredicate.forBlock(b).or(BlockStatePredicate.forBlock(PastelWallSkullBlock.getMobWallHead(PastelSkullType.WITHER)))))
						.where('~', BlockInWorld.hasState(BlockStatePredicate.forBlock(Blocks.AIR))).build()
			);
		}
		return witherBossPattern;
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new SkullBlockEntity(pos, state);
	}

	@Override
	@Nullable
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
		return null;
	}

	@Override
	public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
		super.setPlacedBy(world, pos, state, placer, itemStack);

		// Trigger advancement if a player builds a wither structure using wither skulls instead of wither skeleton skulls
		if (getType().equals(PastelSkullType.WITHER) && placer instanceof ServerPlayer serverPlayerEntity) {
			if (pos.getY() >= world.getMinBuildHeight()) {
				BlockPattern blockPattern = getWitherSkullPattern();
				BlockPattern.BlockPatternMatch result = blockPattern.find(world, pos);
				if (result != null) {
					Support.grantAdvancementCriterion(serverPlayerEntity, "midgame/build_wither_using_wither_heads", "built_wither_using_wither_heads");
				}
			}
		}
	}

}
