package earth.terrarium.pastel.blocks.amalgam;

import earth.terrarium.pastel.api.item.DamageAwareItem;
import earth.terrarium.pastel.api.item.FermentedItem;
import earth.terrarium.pastel.components.BeverageComponent;
import earth.terrarium.pastel.helpers.Support;
import earth.terrarium.pastel.registries.PastelDamageTypes;
import earth.terrarium.pastel.registries.PastelDataComponentTypes;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.EntityBasedExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.List;

public class IncandescentAmalgamItem extends BlockItem implements DamageAwareItem, FermentedItem {
	
	public IncandescentAmalgamItem(Block block, Properties settings) {
		super(block, settings);
	}
	
	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity user) {
		stack = super.finishUsingItem(stack, world, user);
		
		user.hurt(PastelDamageTypes.incandescence(world), 500.0F);
		
		float explosionPower = getExplosionPower(stack, false);
		world.explode(user, PastelDamageTypes.incandescence(world), new EntityBasedExplosionDamageCalculator(user), user.getX(), user.getEyeY(), user.getZ(), explosionPower, true, Level.ExplosionInteraction.BLOCK);
		
		if (user.isAlive() && user instanceof ServerPlayer serverPlayerEntity && !serverPlayerEntity.isCreative()) {
			Support.grantAdvancementCriterion(serverPlayerEntity, "survive_drinking_incandescent_amalgam", "survived_drinking_incandescent_amalgam");
		}
		
		return stack;
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
		super.appendHoverText(stack, context, tooltip, type);
		tooltip.add(Component.translatable("block.pastel.incandescent_amalgam.tooltip").withStyle(ChatFormatting.GRAY));
		tooltip.add(Component.translatable("block.pastel.incandescent_amalgam.tooltip_power", getExplosionPower(stack, false)).withStyle(ChatFormatting.GRAY));
		if (FermentedItem.isPreviewStack(stack))
			tooltip.add(Component.translatable("block.pastel.incandescent_amalgam.tooltip.preview").withStyle(ChatFormatting.GRAY));
	}
	
	@Override
	public void onItemEntityDamaged(DamageSource source, float amount, ItemEntity itemEntity) {
		// remove the itemEntity before dealing damage, otherwise it would cause a stack overflow
		ItemStack stack = itemEntity.getItem();
		itemEntity.remove(Entity.RemovalReason.KILLED);
		
		float explosionPower = getExplosionPower(stack, true);
		var world = itemEntity.level();
		world.explode(itemEntity, PastelDamageTypes.incandescence(world, itemEntity), new EntityBasedExplosionDamageCalculator(itemEntity), itemEntity.getX(), itemEntity.getY(), itemEntity.getZ(), explosionPower, true, Level.ExplosionInteraction.BLOCK);
	}

	public float getExplosionPower(ItemStack stack, boolean useCount) {
		float alcPercent = stack.getOrDefault(PastelDataComponentTypes.BEVERAGE, BeverageComponent.DEFAULT).alcoholPercent();
		return alcPercent <= 0 ? 6 : alcPercent * (useCount ? 0.875F + (stack.getCount() / 8F) : 1);
	}
	
}
