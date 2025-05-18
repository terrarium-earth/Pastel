package de.dafuqs.spectrum.blocks.decoration;

import de.dafuqs.spectrum.items.magic_items.RadianceStaffItem;
import de.dafuqs.spectrum.particle.SpectrumParticleTypes;
import de.dafuqs.spectrum.registries.SpectrumItems;
import de.dafuqs.spectrum.registries.SpectrumSoundEvents;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LightBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class WandLightBlock extends LightBlock {

	public WandLightBlock(Properties settings) {
		super(settings);
	}

//	@Override
//	public MapCodec<? extends WandLightBlock> getCodec() {
//		//TODO: Make the codec
//		return null;
//	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		if(context instanceof EntityCollisionContext entityShapeContext && entityShapeContext.getEntity() != null && holdsRadianceStaff(entityShapeContext.getEntity())) {
			return Shapes.block();
		}
		return Shapes.empty();
	}
	
	private boolean holdsRadianceStaff(@NotNull Entity entity) {
		if(entity instanceof LivingEntity livingEntity) {
			// context.isHolding() only checks the main hand, so we use our own implementation
			for(ItemStack stack : livingEntity.getHandSlots()) {
				if(stack.getItem() instanceof RadianceStaffItem) {
					return true;
				}
			}
		}
		return false;
	}
	
	@OnlyIn(Dist.CLIENT)
	private boolean holdsRadianceStaffClient() {
		return holdsRadianceStaff(Minecraft.getInstance().player);
	}
	
	@Override
	public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
		super.animateTick(state, world, pos, random);
		if (world.isClientSide && holdsRadianceStaffClient()) {
			world.addAlwaysVisibleParticle(SpectrumParticleTypes.SHIMMERSTONE_SPARKLE_SMALL, (double) pos.getX() + 0.2 + random.nextFloat() * 0.6, (double) pos.getY() + 0.1 + random.nextFloat() * 0.6, (double) pos.getZ() + 0.2 + random.nextFloat() * 0.6, 0.0D, 0.03D, 0.0D);
		}
	}
	
	@Override
	public ItemStack getCloneItemStack(LevelReader world, BlockPos pos, BlockState state) {
		return new ItemStack(SpectrumItems.RADIANCE_STAFF);
	}
	
	@Override
	public InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
		if (!world.isClientSide) {
			BlockState newState = state.cycle(LEVEL);
			if (newState.getValue(LEVEL) == 0) { // lights with a level of 0 are absolutely
				newState = newState.cycle(LEVEL);
			}
			world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, SpectrumSoundEvents.RADIANCE_STAFF_PLACE, SoundSource.PLAYERS, 1.0F, (float) (0.75 + 0.05 * newState.getValue(LEVEL)));
			world.setBlock(pos, newState, Block.UPDATE_CLIENTS);
			return InteractionResult.SUCCESS;
		} else {
			return InteractionResult.CONSUME;
		}
	}
	
}
