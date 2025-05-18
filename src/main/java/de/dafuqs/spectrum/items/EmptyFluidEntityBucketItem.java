package de.dafuqs.spectrum.items;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

/**
 * An EntityBucketItem for entities with Fluids.EMPTY.
 */
public class EmptyFluidEntityBucketItem extends MobBucketItem {
	
	public EmptyFluidEntityBucketItem(EntityType<?> type, Fluid fluid, SoundEvent emptyingSound, Properties settings) {
		super(type, fluid, emptyingSound, settings);
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
		ItemStack itemStack = user.getItemInHand(hand);
		BlockHitResult blockHitResult = getPlayerPOVHitResult(world, user, ClipContext.Fluid.SOURCE_ONLY);
		if (blockHitResult.getType() == HitResult.Type.MISS) {
			return InteractionResultHolder.pass(itemStack);
		} else if (blockHitResult.getType() != HitResult.Type.BLOCK) {
			return InteractionResultHolder.pass(itemStack);
		} else {
			BlockPos blockPos = blockHitResult.getBlockPos();
			Direction direction = blockHitResult.getDirection();
			BlockPos blockPos2 = blockPos.relative(direction);
			if (world.mayInteract(user, blockPos) && user.mayUseItemAt(blockPos2, direction, itemStack)) {
				this.checkExtraContent(user, world, itemStack, blockPos2);
				if (user instanceof ServerPlayer) {
					CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer) user, blockPos2, itemStack);
				}
				
				user.awardStat(Stats.ITEM_USED.get(this));
				return InteractionResultHolder.sidedSuccess(getEmptySuccessItem(itemStack, user), world.isClientSide());
			} else {
				return InteractionResultHolder.fail(itemStack);
			}
		}
	}
	
}
