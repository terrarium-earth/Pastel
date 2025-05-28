package earth.terrarium.pastel.items.magic_items.ampoules;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public abstract class GlassAmpouleItem extends Item {
    
    public GlassAmpouleItem(Properties settings) {
        super(settings);
    }
    
    @Override
	public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
		ItemStack stack = user.getItemInHand(hand);
		if (trigger(user.level(), stack, user, null, user.getEyePosition())) {
			if (!user.isCreative()) {
				stack.shrink(1);
			}
			return InteractionResultHolder.success(stack);
		}
		
		return world.isClientSide() ? InteractionResultHolder.fail(stack) : InteractionResultHolder.pass(stack);
    }
	
	@Override
	public InteractionResult interactLivingEntity(ItemStack stack, Player user, LivingEntity entity, InteractionHand hand) {
		Level world = user.level();
		if (trigger(user.level(), stack, user, entity, user.getEyePosition())) {
			if (!user.level().isClientSide) {
				if (!(user.isCreative())) {
					stack.shrink(1);
				}
			}
			return InteractionResult.sidedSuccess(world.isClientSide);
		}
		
		return world.isClientSide() ? InteractionResult.FAIL : InteractionResult.PASS;
	}
	
	public abstract boolean trigger(Level world, ItemStack stack, LivingEntity attacker, @Nullable LivingEntity target, Vec3 position);
    
}
