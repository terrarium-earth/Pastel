package de.dafuqs.spectrum.blocks.fusion_shrine;

import com.klikli_dev.modonomicon.api.multiblock.Multiblock;
import com.mojang.serialization.MapCodec;
import de.dafuqs.spectrum.SpectrumCommon;
import de.dafuqs.spectrum.blocks.InWorldInteractionBlock;
import de.dafuqs.spectrum.compat.modonomicon.ModonomiconHelper;
import de.dafuqs.spectrum.inventories.storage.DroppedItemStorage;
import de.dafuqs.spectrum.networking.s2c_payloads.PlayParticleWithExactVelocityPayload;
import de.dafuqs.spectrum.networking.s2c_payloads.PlayParticleWithRandomOffsetAndVelocityPayload;
import de.dafuqs.spectrum.particle.effect.ColoredSparkleRisingParticleEffect;
import de.dafuqs.spectrum.progression.SpectrumAdvancementCriteria;
import de.dafuqs.spectrum.registries.SpectrumBlockEntities;
import de.dafuqs.spectrum.registries.SpectrumMultiblocks;
import de.dafuqs.spectrum.registries.SpectrumSoundEvents;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorageUtil;
import net.neoforged.neoforge.fluids.FluidStack;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleSlotStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.fabricmc.fabric.impl.transfer.context.SingleSlotContainerItemContext;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("UnstableApiUsage")
public class FusionShrineBlock extends InWorldInteractionBlock {

	public static final MapCodec<FusionShrineBlock> CODEC = simpleCodec(FusionShrineBlock::new);

	public static final ResourceLocation UNLOCK_IDENTIFIER = SpectrumCommon.locate("collect_all_basic_pigments_besides_brown");
	public static final IntegerProperty LIGHT_LEVEL = IntegerProperty.create("light_level", 0, 15);
	protected static final VoxelShape SHAPE;

	public FusionShrineBlock(Properties settings) {
		super(settings);
		registerDefaultState(getStateDefinition().any().setValue(LIGHT_LEVEL, 0));
	}

	@Override
	public MapCodec<? extends FusionShrineBlock> codec() {
		return CODEC;
	}
	
	public static void clearCurrentlyRenderedMultiBlock(Level world) {
		if (world.isClientSide) {
			if (world.isClientSide()) {
				ModonomiconHelper.clearRenderedMultiblock(SpectrumMultiblocks.get(SpectrumMultiblocks.FUSION_SHRINE));
			}
		}
	}
	
	public static boolean verifySkyAccess(ServerLevel world, BlockPos shrinePos) {
		if (world.getBlockState(shrinePos.above()).isRedstoneConductor(world, shrinePos.above())) {
			world.playSound(null, shrinePos, SpectrumSoundEvents.USE_FAIL, SoundSource.NEUTRAL, 1.0F, 1.0F);
			PlayParticleWithRandomOffsetAndVelocityPayload.playParticleWithRandomOffsetAndVelocity(world, shrinePos.above().getCenter(), ColoredSparkleRisingParticleEffect.RED, 8, Vec3.ZERO, new Vec3(0.1, 0.1, 0.1));
			return false;
		}
		
		// getTopY() returns the topmost "air" block
		// which may or may not be the pos of the Fusion Shrine
		// we search down until we find the shrine itself or a non-opaque block
		int topY = world.getHeight(Heightmap.Types.WORLD_SURFACE, shrinePos.getX(), shrinePos.getZ());
		BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos(shrinePos.getX(), topY, shrinePos.getZ());
		for (int y = topY; y > shrinePos.getY(); y--) {
			mutablePos.setY(y - 1);
			BlockState posState = world.getBlockState(mutablePos);
			if (posState.getLightBlock(world, mutablePos) > 0) {
				break;
			}
		}
		
		if (mutablePos.getY() == shrinePos.getY()) {
			return true;
		}
		
		PlayParticleWithExactVelocityPayload.playParticleWithExactVelocity(world, new Vec3(shrinePos.getX() + 0.5, shrinePos.getY() + 1, shrinePos.getZ() + 0.5), ColoredSparkleRisingParticleEffect.RED, 1, new Vec3(0, 0.5, 0));
		PlayParticleWithRandomOffsetAndVelocityPayload.playParticleWithRandomOffsetAndVelocity(world, new Vec3(shrinePos.getX() + 0.5, topY - 0.5, shrinePos.getZ() + 0.5), ColoredSparkleRisingParticleEffect.RED, 8, Vec3.ZERO, new Vec3(0.1, 0.1, 0.1));
		world.playSound(null, shrinePos, SpectrumSoundEvents.USE_FAIL, SoundSource.NEUTRAL, 1.0F, 1.0F);
		return false;
	}
	
	public static boolean verifyStructure(Level world, BlockPos blockPos, @Nullable ServerPlayer serverPlayerEntity) {
		Multiblock multiblock = SpectrumMultiblocks.get(SpectrumMultiblocks.FUSION_SHRINE);
		boolean valid = multiblock.validate(world, blockPos.below(), Rotation.NONE);
		
		if (valid) {
			if (serverPlayerEntity != null) {
				SpectrumAdvancementCriteria.COMPLETED_MULTIBLOCK.trigger(serverPlayerEntity, multiblock);
			}
		} else {
			if (world.isClientSide) {
				ModonomiconHelper.renderMultiblock(multiblock, SpectrumMultiblocks.FUSION_SHRINE_TEXT, blockPos.below(2), Rotation.NONE);
			} else if (world.getBlockEntity(blockPos) instanceof FusionShrineBlockEntity fusionShrineBlockEntity) {
				fusionShrineBlockEntity.scatterContents(world);
			}
		}
		
		return valid;
	}
	
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(LIGHT_LEVEL);
	}
	
	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new FusionShrineBlockEntity(pos, state);
	}
	
	@Override
	public int getAnalogOutputSignal(BlockState state, Level world, BlockPos pos) {
		if (world.getBlockEntity(pos) instanceof FusionShrineBlockEntity blockEntity) {
			NonNullList<ItemStack> inventory = blockEntity.getItems();
			
			int i = 0;
			float f = 0.0f;
			for (int j = 0; j < inventory.size(); ++j) {
				ItemStack itemStack = blockEntity.getItem(j);
				if (itemStack.isEmpty()) continue;
				f += (float) itemStack.getCount() / (float) Math.min(blockEntity.getMaxStackSize(), itemStack.getMaxStackSize());
				++i;
			}
			
			if (blockEntity.fluidStorage.amount > 0) {
				f += (float) blockEntity.fluidStorage.amount / (float) blockEntity.fluidStorage.getCapacity();
				++i;
			}
			
			return Mth.floor(f / ((float) inventory.size() + 1) * 14.0f) + (i > 0 ? 1 : 0);
		}
		
		return 0;
	}
	
	@Override
	public void destroy(LevelAccessor world, BlockPos pos, BlockState state) {
		if (world.isClientSide()) {
			clearCurrentlyRenderedMultiBlock((Level) world);
		}
	}
	
	@Override
	public void fallOn(Level world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
		if (!world.isClientSide) {
			// Specially handle fluid items
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (entity instanceof ItemEntity itemEntity && blockEntity instanceof FusionShrineBlockEntity fusionShrineBlockEntity) {
				SingleVariantStorage<FluidStack> storage = fusionShrineBlockEntity.fluidStorage;
				ItemStack itemStack = itemEntity.getItem();
				
				// We're not considering stacked fluid storages for the time being
				if (itemStack.getCount() == 1) {
					SingleSlotStorage<ItemVariant> slot = new DroppedItemStorage(itemStack);
					SingleSlotContainerItemContext ctx = new SingleSlotContainerItemContext(slot);
					Storage<FluidStack> fluidStorage = FluidStorage.ITEM.find(itemStack, ctx);
					
					if (fluidStorage != null) {
						boolean anyInserted = false;
						for (StorageView<FluidStack> view : fluidStorage) {
							try (Transaction transaction = Transaction.openOuter()) {
								FluidStack variant = view.getResource();
								long inserted = variant.isBlank() ? 0 : storage.insert(variant, view.getAmount(), transaction);
								long extracted = fluidStorage.extract(variant, inserted, transaction);
								if (inserted == extracted && inserted != 0) {
									anyInserted = true;
									transaction.commit();
								}
							}
						}
						
						if (!anyInserted && !storage.getResource().isBlank()) {
							try (Transaction transaction = Transaction.openOuter()) {
								long inserted = fluidStorage.insert(storage.getResource(), storage.getAmount(), transaction);
								long extracted = storage.extract(storage.getResource(), inserted, transaction);
								if (inserted == extracted && inserted != 0) {
									transaction.commit();
								}
							}
						}
						
						itemEntity.setItem(slot.getResource().toStack(itemStack.getCount()));
						fusionShrineBlockEntity.inventoryChanged();
						return;
					}
				}
			}
			
			// do not pick up items that were results of crafting
			if (entity.position().x % 0.5 != 0 && entity.position().z % 0.5 != 0) {
				super.fallOn(world, state, pos, entity, fallDistance);
			}
		}
	}
	
	@Override
	public ItemInteractionResult useItemOn(ItemStack handStack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		if (world.isClientSide) {
			verifyStructure(world, pos, null);
			return ItemInteractionResult.SUCCESS;
		} else {
			verifySkyAccess((ServerLevel) world, pos);
			
			// if the structure is valid the player can put / retrieve items and fluids into the shrine
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof FusionShrineBlockEntity fusionShrineBlockEntity && verifyStructure(world, pos, (ServerPlayer) player)) {
				fusionShrineBlockEntity.setOwner(player);
				
				if (FluidStorageUtil.interactWithFluidStorage(fusionShrineBlockEntity.fluidStorage, player, hand)) {
					fusionShrineBlockEntity.inventoryChanged();
					return ItemInteractionResult.CONSUME;
				}
				if ((player.isShiftKeyDown() || handStack.isEmpty()) && retrieveLastStack(world, pos, player, hand, handStack, fusionShrineBlockEntity)) {
					fusionShrineBlockEntity.inventoryChanged();
					return ItemInteractionResult.CONSUME;
				}
				if (!handStack.isEmpty() && inputHandStack(world, player, hand, handStack, fusionShrineBlockEntity)) {
					fusionShrineBlockEntity.inventoryChanged();
					return ItemInteractionResult.CONSUME;
				}
			}
			
			return ItemInteractionResult.CONSUME;
		}
	}
	
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}
	
	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
		return createTickerHelper(type, SpectrumBlockEntities.FUSION_SHRINE, world.isClientSide ? FusionShrineBlockEntity::clientTick : FusionShrineBlockEntity::serverTick);
	}
	
	static {
		VoxelShape neck = Block.box(2, 0, 2, 14, 12, 14);
		VoxelShape head = Block.box(1, 12, 1, 15, 15, 15);
		VoxelShape crystal = Block.box(6.5, 13, 6.5, 9.5, 23, 9.5);
		neck = Shapes.or(neck, head);
		SHAPE = Shapes.or(neck, crystal);
	}
}
