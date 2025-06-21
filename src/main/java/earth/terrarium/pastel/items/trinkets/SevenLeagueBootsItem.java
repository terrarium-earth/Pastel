package earth.terrarium.pastel.items.trinkets;

import com.google.common.collect.Multimap;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.helpers.PastelEnchantmentHelper;
import top.theillusivec4.curios.api.SlotContext;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;

public class SevenLeagueBootsItem extends PastelTrinketItem {
	
	public static final ResourceLocation MOVEMENT_SPEED_ATTRIBUTE_ID = PastelCommon.locate("seven_league_boots_movement_speed");
	public static final ResourceLocation STEP_UP_ATTRIBUTE_ID = PastelCommon.locate("seven_league_boots_step_up");
	
	public SevenLeagueBootsItem(Properties settings) {
		super(settings, PastelCommon.locate("unlocks/trinkets/seven_league_boots"));
	}
	
	@Override
	public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation id, ItemStack stack) {
		Multimap<Holder<Attribute>, AttributeModifier> modifiers = super.getAttributeModifiers(slotContext, id, stack);



		int powerLevel = slotContext.entity() != null ? PastelEnchantmentHelper.getLevel(slotContext.entity().level().registryAccess(), Enchantments.POWER, stack) : 0;
		double speedBoost = 0.05 * (powerLevel + 1);
		modifiers.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(MOVEMENT_SPEED_ATTRIBUTE_ID, speedBoost, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
		modifiers.put(Attributes.STEP_HEIGHT, new AttributeModifier(STEP_UP_ATTRIBUTE_ID, 0.75, AttributeModifier.Operation.ADD_VALUE));
		
		return modifiers;
	}
	
	@Override
	public boolean isEnchantable(ItemStack stack) {
		return stack.getCount() == 1;
	}
	
	@Override
	public int getEnchantmentValue() {
		return 8;
	}
	
	@Override
	public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
		return super.supportsEnchantment(stack, enchantment) || enchantment.is(Enchantments.POWER);
	}
	
}
