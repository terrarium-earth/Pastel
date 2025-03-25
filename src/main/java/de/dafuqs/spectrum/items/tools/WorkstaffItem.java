package de.dafuqs.spectrum.items.tools;

import de.dafuqs.revelationary.api.advancements.*;
import de.dafuqs.spectrum.api.energy.*;
import de.dafuqs.spectrum.api.energy.color.*;
import de.dafuqs.spectrum.api.item.*;
import de.dafuqs.spectrum.components.*;
import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.inventories.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.component.*;
import net.minecraft.enchantment.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.*;
import net.minecraft.registry.*;
import net.minecraft.screen.*;
import net.minecraft.server.network.*;
import net.minecraft.sound.*;
import net.minecraft.text.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

import java.util.*;

public class WorkstaffItem extends MultiToolItem implements AoEBreakingTool, Preenchanted {
	
	protected static final InkCost BASE_COST_PER_AOE_MINING_RANGE_INCREMENT = new InkCost(InkColors.WHITE, 3); // TODO: make pricier once ink networking is in

	public enum GUIToggle {
		SELECT_SILK_TOUCH("item.spectrum.workstaff.message.silk_touch"),
		SELECT_FORTUNE("item.spectrum.workstaff.message.fortune"),
		SELECT_RESONANCE("item.spectrum.workstaff.message.resonance"),
		SELECT_1x1("item.spectrum.workstaff.message.1x1"),
		SELECT_3x3("item.spectrum.workstaff.message.3x3"),
		SELECT_5x5("item.spectrum.workstaff.message.5x5"),
		ENABLE_RIGHT_CLICK_ACTIONS("item.spectrum.workstaff.message.enabled_right_click_actions"),
		DISABLE_RIGHT_CLICK_ACTIONS("item.spectrum.workstaff.message.disabled_right_click_actions"),
		ENABLE_PROJECTILES("item.spectrum.workstaff.message.enabled_projectiles"),
		DISABLE_PROJECTILES("item.spectrum.workstaff.message.disabled_projectiles");

		private final String triggerText;

		GUIToggle(String triggerText) {
			this.triggerText = triggerText;
		}

		public Text getTriggerText() {
			return Text.translatable(triggerText);
		}
		
	}
	
    public WorkstaffItem(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }
	
	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		if (user.isSneaking()) {
			if (user instanceof ServerPlayerEntity serverPlayerEntity) {
				serverPlayerEntity.openHandledScreen(createScreenHandlerFactory(user.getStackInHand(hand)));
			}
			return TypedActionResult.consume(user.getStackInHand(hand));
		}
		return super.use(world, user, hand);
	}

	@Override
	public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
		super.appendTooltip(stack, context, tooltip, type);
		int range = getAoERange(stack);
		if(range > 0) {
			int displayedRange = 1 + range + range;
			tooltip.add(Text.translatable("item.spectrum.workstaff.tooltip.mining_range", displayedRange, displayedRange).formatted(Formatting.GRAY));
		}
	}
	
	@Override
	public boolean canTill(ItemStack stack) {
		return stack.getOrDefault(SpectrumDataComponentTypes.WORKSTAFF, WorkstaffComponent.DEFAULT).canTill();
	}
	
	public NamedScreenHandlerFactory createScreenHandlerFactory(ItemStack itemStack) {
		return new SimpleNamedScreenHandlerFactory((syncId, inventory, player) ->
				new WorkstaffScreenHandler(syncId, inventory, itemStack),
				Text.translatable("item.spectrum.workstaff")
		);
	}
	
	@Override
	public boolean canUseAoE(PlayerEntity player, ItemStack stack) {
		int range = getAoERange(stack);
		if (range <= 0) {
			return true;
		}
		
		int costForRange = (int) Math.pow(BASE_COST_PER_AOE_MINING_RANGE_INCREMENT.cost(), range);
		return InkPowered.tryDrainEnergy(player, BASE_COST_PER_AOE_MINING_RANGE_INCREMENT.color(), costForRange);
	}
	
	public static void applyToggle(PlayerEntity player, ItemStack stack, GUIToggle toggle) {
		
		switch (toggle) {
			case SELECT_1x1 -> {
				stack.set(SpectrumDataComponentTypes.AOE, 0);
				player.sendMessage(toggle.getTriggerText(), true);
			}
			case SELECT_3x3 -> {
				stack.set(SpectrumDataComponentTypes.AOE, 1);
				player.sendMessage(toggle.getTriggerText(), true);
			}
			case SELECT_5x5 -> {
				stack.set(SpectrumDataComponentTypes.AOE, 2);
				player.sendMessage(toggle.getTriggerText(), true);
			}
			// switching to another enchantment
			// fortune handling is a bit special. Its level is preserved in NBT,
			// to restore the original enchant level when switching back
			case SELECT_FORTUNE ->
				enchantAndRemoveOthers(player, stack, toggle.getTriggerText(), Enchantments.FORTUNE);
			case SELECT_SILK_TOUCH ->
				enchantAndRemoveOthers(player, stack, toggle.getTriggerText(), Enchantments.SILK_TOUCH);
			case SELECT_RESONANCE -> enchantAndRemoveOthers(player, stack, toggle.getTriggerText(), SpectrumEnchantments.RESONANCE);
			case ENABLE_RIGHT_CLICK_ACTIONS -> {
				stack.apply(SpectrumDataComponentTypes.WORKSTAFF, WorkstaffComponent.DEFAULT, comp ->
						new WorkstaffComponent(true, comp.canShoot(), comp.fortuneLevel()));
				player.sendMessage(toggle.getTriggerText(), true);
			}
			case DISABLE_RIGHT_CLICK_ACTIONS -> {
				stack.apply(SpectrumDataComponentTypes.WORKSTAFF, WorkstaffComponent.DEFAULT, comp ->
						new WorkstaffComponent(false, comp.canShoot(), comp.fortuneLevel()));
				player.sendMessage(toggle.getTriggerText(), true);
			}
			case ENABLE_PROJECTILES -> {
				stack.apply(SpectrumDataComponentTypes.WORKSTAFF, WorkstaffComponent.DEFAULT, comp ->
						new WorkstaffComponent(comp.canTill(), true, comp.fortuneLevel()));
				player.sendMessage(toggle.getTriggerText(), true);
			}
			case DISABLE_PROJECTILES -> {
				stack.apply(SpectrumDataComponentTypes.WORKSTAFF, WorkstaffComponent.DEFAULT, comp ->
						new WorkstaffComponent(comp.canTill(), false, comp.fortuneLevel()));
				player.sendMessage(toggle.getTriggerText(), true);
			}
		}
	}
	
	private static void enchantAndRemoveOthers(PlayerEntity player, ItemStack stack, Text message, RegistryKey<Enchantment> enchantment) {
		var registryLookup = player.getWorld().getRegistryManager();

		int existingLevel = SpectrumEnchantmentHelper.getLevel(registryLookup, enchantment, stack);
		if (existingLevel > 0) {
			player.sendMessage(Text.translatable("item.spectrum.workstaff.message.already_has_the_enchantment"), true);
			return;
		}
		
		int level = 1;
		
		if (enchantment == Enchantments.FORTUNE) {
			level = stack.getOrDefault(SpectrumDataComponentTypes.WORKSTAFF, WorkstaffComponent.DEFAULT).fortuneLevel();
		} else {
			int fortuneLevel = SpectrumEnchantmentHelper.getLevel(registryLookup, Enchantments.FORTUNE, stack);
			stack.apply(SpectrumDataComponentTypes.WORKSTAFF, WorkstaffComponent.DEFAULT, comp ->
					new WorkstaffComponent(comp.canTill(), comp.canShoot(), Math.max(fortuneLevel, 1)));
		}
		
		ItemStack newStack = stack.copy();
		var removeResult = SpectrumEnchantmentHelper.removeEnchantments(registryLookup, newStack, Enchantments.SILK_TOUCH, SpectrumEnchantments.RESONANCE, Enchantments.FORTUNE);
		if (removeResult.getRight() == 0) {
			if (player instanceof ServerPlayerEntity serverPlayerEntity) {
				triggerUnenchantedWorkstaffAdvancement(serverPlayerEntity);
			}
		} else {
			var addResult = SpectrumEnchantmentHelper.addOrUpgradeEnchantment(registryLookup, removeResult.getLeft(), enchantment, level, false, AdvancementHelper.hasAdvancement(player, SpectrumAdvancements.APPLY_CONFLICTING_ENCHANTMENTS));
			if (addResult.getLeft()) {
				stack.set(DataComponentTypes.ENCHANTMENTS, addResult.getRight().getEnchantments());
				player.sendMessage(message, true);
			} else {
				player.sendMessage(Text.translatable("item.spectrum.workstaff.message.would_result_in_conflicting_enchantments"), true);
			}
		}
	}
	
	private static void triggerUnenchantedWorkstaffAdvancement(ServerPlayerEntity player) {
		player.playSoundToPlayer(SpectrumSoundEvents.USE_FAIL, SoundCategory.PLAYERS, 0.75F, 1.0F);
		Support.grantAdvancementCriterion(player, "lategame/trigger_unenchanted_workstaff", "code_triggered");
	}
	
	@Override
	public Map<RegistryKey<Enchantment>, Integer> getDefaultEnchantments() {
		return Map.of(Enchantments.FORTUNE, 4);
	}

}
