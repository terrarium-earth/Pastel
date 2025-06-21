package earth.terrarium.pastel.items.tools;

import earth.terrarium.pastel.registries.PastelEnchantmentTags;
import earth.terrarium.pastel.registries.PastelEnchantments;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Map;

public class OblivionPickaxeItem extends PastelPickaxeItem {
	
	public OblivionPickaxeItem(Tier material, Properties settings) {
		super(material, settings);
	}
	
	@Override
	public boolean mineBlock(ItemStack stack, Level world, BlockState state, BlockPos pos, LivingEntity miner) {
		super.mineBlock(stack, world, state, pos, miner);
		
		// Break the tool if it is used without the voiding enchantment
		// Otherwise this would be a VERY cheap early game diamond tier tool
		if (!world.isClientSide && !EnchantmentHelper.hasTag(stack, PastelEnchantmentTags.NO_BLOCK_DROPS)) {
			stack.hurtAndBreak(5000, miner, EquipmentSlot.MAINHAND);
		}
		
		return true;
	}
	
	@Override
	public Map<ResourceKey<Enchantment>, Integer> getDefaultEnchantments() {
		return Map.of(PastelEnchantments.VOIDING, 1);
	}
	
	@Override
	public boolean isFoil(ItemStack stack) {
		return false;
	}
}
