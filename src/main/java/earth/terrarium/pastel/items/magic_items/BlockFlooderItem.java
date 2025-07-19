package earth.terrarium.pastel.items.magic_items;

import earth.terrarium.pastel.entity.entity.BlockFlooderProjectile;
import earth.terrarium.pastel.registries.PastelSoundEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class BlockFlooderItem extends Item {
	
	public BlockFlooderItem(Properties settings) {
		super(settings);
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
		ItemStack itemStack = user.getItemInHand(hand);
		world.playSound(null, user.getX(), user.getY(), user.getZ(), PastelSoundEvents.ENTITY_BLOCK_FLOODER_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
		if (!world.isClientSide) {
			BlockFlooderProjectile blockFlooderProjectile = new BlockFlooderProjectile(world, user);
			blockFlooderProjectile.setItem(itemStack);
			blockFlooderProjectile.shootFromRotation(user, user.getXRot(), user.getYRot(), 0.0F, 1.5F, 1.0F);
			world.addFreshEntity(blockFlooderProjectile);
		}
		
		user.awardStat(Stats.ITEM_USED.get(this));
		if (!user.getAbilities().instabuild) {
			itemStack.shrink(1);
		}
		
		return InteractionResultHolder.sidedSuccess(itemStack, world.isClientSide());
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
		super.appendHoverText(stack, context, tooltip, type);
		tooltip.add(Component.translatable("item.pastel.block_flooder.tooltip").withStyle(ChatFormatting.GRAY));
		tooltip.add(Component.translatable("item.pastel.block_flooder.tooltip2").withStyle(ChatFormatting.GRAY));
		tooltip.add(Component.translatable("item.pastel.block_flooder.tooltip3").withStyle(ChatFormatting.GRAY));
		tooltip.add(Component.translatable("item.pastel.block_flooder.tooltip4").withStyle(ChatFormatting.GRAY));
	}
	
}
