package earth.terrarium.pastel.items.tools;

import earth.terrarium.pastel.api.energy.InkCost;
import earth.terrarium.pastel.api.energy.InkPowered;
import earth.terrarium.pastel.api.energy.color.InkColors;
import earth.terrarium.pastel.api.render.SlotBackgroundEffectProvider;
import earth.terrarium.pastel.components.WorkstaffComponent;
import earth.terrarium.pastel.entity.entity.MiningProjectileEntity;
import earth.terrarium.pastel.helpers.PastelEnchantmentHelper;
import earth.terrarium.pastel.registries.PastelDataComponentTypes;
import earth.terrarium.pastel.registries.PastelEnchantments;
import earth.terrarium.pastel.registries.PastelSoundEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GlassCrestWorkstaffItem extends WorkstaffItem implements SlotBackgroundEffectProvider {
    
    public static final int COOLDOWN_DURATION_TICKS = 10;
    public static final InkCost PROJECTILE_COST = new InkCost(InkColors.WHITE, 50); // TODO: make pricier once ink networking is in
    
    public GlassCrestWorkstaffItem(Tier material, int attackDamage, float attackSpeed, Properties settings) {
        super(material, attackDamage, attackSpeed, settings);
    }
    
    public static boolean canShoot(ItemStack stack) {
		return stack.getOrDefault(PastelDataComponentTypes.WORKSTAFF, WorkstaffComponent.DEFAULT).canShoot();
    }
    
    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        InteractionResultHolder<ItemStack> result = super.use(world, user, hand);
        if (!result.getResult().consumesAction()) {
            ItemStack stack = user.getItemInHand(hand);
            if (canShoot(stack) && InkPowered.tryDrainEnergy(user, PROJECTILE_COST)) {
                user.getCooldowns().addCooldown(this, COOLDOWN_DURATION_TICKS);
                if (!world.isClientSide) {
                    user.playNotifySound(PastelSoundEvents.LIGHT_CRYSTAL_RING, SoundSource.PLAYERS, 0.5F, 0.75F + user.getRandom().nextFloat());
                    MiningProjectileEntity.shoot(world, user, user.getItemInHand(hand));
                }
                stack.hurtAndBreak(2, user, EquipmentSlot.MAINHAND);
                
                return InteractionResultHolder.consume(stack);
            } else {
                return InteractionResultHolder.fail(stack);
            }
        }
        return result;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        super.appendHoverText(stack, context, tooltip, type);
    
        if (canShoot(stack)) {
            tooltip.add(Component.translatable("item.pastel.workstaff.tooltip.projectile").withStyle(ChatFormatting.GRAY));
        } else {
            tooltip.add(Component.translatable("item.pastel.workstaff.tooltip.projectiles_disabled").withStyle(ChatFormatting.DARK_RED));
        }
    }
	
	@Override
	public SlotBackgroundEffectProvider.SlotEffect backgroundType(@Nullable Player player, ItemStack stack) {
		var usable = InkPowered.hasAvailableInk(player, PROJECTILE_COST);
		return usable ? SlotBackgroundEffectProvider.SlotEffect.BORDER_FADE : SlotBackgroundEffectProvider.SlotEffect.NONE;
	}
	
	@Override
	public int getBackgroundColor(@Nullable Player player, ItemStack stack, float tickDelta) {
		if (player != null) {
			var lookup = player.level().registryAccess();
			var resonance = PastelEnchantmentHelper.hasEnchantment(lookup, PastelEnchantments.RESONANCE, stack);
			var silkTouch = PastelEnchantmentHelper.hasEnchantment(lookup, Enchantments.SILK_TOUCH, stack);
			var fortune = PastelEnchantmentHelper.hasEnchantment(lookup, Enchantments.FORTUNE, stack);
			
			if (resonance)
				return InkColors.WHITE_COLOR;
			
			if (silkTouch)
				return InkColors.CYAN_COLOR;
			
			if (fortune)
				return InkColors.LIGHT_BLUE_COLOR;
		}
		
		return InkColors.LIGHT_GRAY_COLOR;
	}
}
