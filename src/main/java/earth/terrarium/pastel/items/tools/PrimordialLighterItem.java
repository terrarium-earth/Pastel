package earth.terrarium.pastel.items.tools;

import earth.terrarium.pastel.api.item.CreativeOnlyItem;
import earth.terrarium.pastel.blocks.PrimordialFireBlock;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.PastelSoundEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.FlintAndSteelItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

import java.util.List;

public class PrimordialLighterItem extends FlintAndSteelItem implements CreativeOnlyItem {
	
	public static final DispenseItemBehavior DISPENSER_BEHAVIOR = new OptionalDispenseItemBehavior() {
		protected ItemStack execute(BlockSource pointer, ItemStack stack) {
			var world = pointer.level();
			this.setSuccess(true);
			Direction direction = pointer.state().getValue(DispenserBlock.FACING);
			BlockPos blockPos = pointer.pos().relative(direction);
			if (PrimordialFireBlock.tryPlacePrimordialFire(world, blockPos, direction)) {
				stack.hurtAndBreak(1, world, null, item -> {});
				this.setSuccess(true);
			} else {
				this.setSuccess(false);
			}
			return stack;
		}
	};
	
	public PrimordialLighterItem(Properties settings) {
		super(settings);
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
		super.appendHoverText(stack, context, tooltip, type);
		tooltip.add(Component.translatable("item.pastel.primordial_lighter.tooltip").withStyle(ChatFormatting.GRAY));
		CreativeOnlyItem.appendTooltip(tooltip);
	}
	
	@Override
	public InteractionResult useOn(UseOnContext context) {
		Player player = context.getPlayer();
		Level world = context.getLevel();
		BlockPos pos = context.getClickedPos();
		BlockPos blockOnSide = pos.relative(context.getClickedFace());
		
		if (PrimordialFireBlock.canBePlacedAt(world, blockOnSide, context.getHorizontalDirection())) {
			world.playSound(player, blockOnSide, PastelSoundEvents.ITEM_PRIMORDIAL_LIGHTER_USE, SoundSource.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.4F + 0.8F);
			BlockState primordialFireState = ((PrimordialFireBlock) PastelBlocks.PRIMORDIAL_FIRE.get()).getStateForPosition(world, blockOnSide);
			world.setBlock(blockOnSide, primordialFireState, 11);
			world.gameEvent(player, GameEvent.BLOCK_PLACE, pos);
			
			ItemStack stack = context.getItemInHand();
			if (player instanceof ServerPlayer serverPlayerEntity) {
				CriteriaTriggers.PLACED_BLOCK.trigger(serverPlayerEntity, blockOnSide, stack);
				stack.hurtAndBreak(1, serverPlayerEntity, LivingEntity.getSlotForHand(context.getHand()));
			}
			
			return InteractionResult.sidedSuccess(world.isClientSide());
		} else {
			return InteractionResult.FAIL;
		}
		
	}
	
}
