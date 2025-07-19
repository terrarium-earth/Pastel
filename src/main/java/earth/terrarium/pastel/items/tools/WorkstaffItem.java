package earth.terrarium.pastel.items.tools;

import com.cmdpro.databank.DatabankUtils;
import earth.terrarium.pastel.api.energy.InkCost;
import earth.terrarium.pastel.api.energy.InkPowered;
import earth.terrarium.pastel.api.energy.color.InkColors;
import earth.terrarium.pastel.api.item.Preenchanted;
import earth.terrarium.pastel.capabilities.AreaMiningHandler;
import earth.terrarium.pastel.components.WorkstaffComponent;
import earth.terrarium.pastel.helpers.enchantments.Ench;
import earth.terrarium.pastel.helpers.Support;
import earth.terrarium.pastel.inventories.WorkstaffScreenHandler;
import earth.terrarium.pastel.registries.PastelAdvancements;
import earth.terrarium.pastel.registries.PastelDataComponentTypes;
import earth.terrarium.pastel.registries.PastelEnchantments;
import earth.terrarium.pastel.registries.PastelSoundEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.ItemAbility;

import java.util.List;
import java.util.Map;

public class WorkstaffItem extends MultiToolItem implements AreaMiningHandler, Preenchanted {
	
	protected static final InkCost BASE_COST_PER_AOE_MINING_RANGE_INCREMENT = new InkCost(InkColors.WHITE, 3); // TODO: make pricier once ink networking is in

	public enum GUIToggle {
		SELECT_SILK_TOUCH("item.pastel.workstaff.message.silk_touch"),
		SELECT_FORTUNE("item.pastel.workstaff.message.fortune"),
		SELECT_RESONANCE("item.pastel.workstaff.message.resonance"),
		SELECT_1x1("item.pastel.workstaff.message.1x1"),
		SELECT_3x3("item.pastel.workstaff.message.3x3"),
		SELECT_5x5("item.pastel.workstaff.message.5x5"),
		ENABLE_RIGHT_CLICK_ACTIONS("item.pastel.workstaff.message.enabled_right_click_actions"),
		DISABLE_RIGHT_CLICK_ACTIONS("item.pastel.workstaff.message.disabled_right_click_actions"),
		ENABLE_PROJECTILES("item.pastel.workstaff.message.enabled_projectiles"),
		DISABLE_PROJECTILES("item.pastel.workstaff.message.disabled_projectiles");

		private final String triggerText;

		GUIToggle(String triggerText) {
			this.triggerText = triggerText;
		}

		public Component getTriggerText() {
			return Component.translatable(triggerText);
		}
		
	}
	
    public WorkstaffItem(Tier material, int attackDamage, float attackSpeed, Properties settings) {
        super(material, attackDamage, attackSpeed, settings);
    }
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
		if (user.isShiftKeyDown()) {
			if (user instanceof ServerPlayer serverPlayerEntity) {
				serverPlayerEntity.openMenu(createScreenHandlerFactory(user.getItemInHand(hand)));
			}
			return InteractionResultHolder.consume(user.getItemInHand(hand));
		}
		return super.use(world, user, hand);
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
		super.appendHoverText(stack, context, tooltip, type);
		int range = stack.getOrDefault(PastelDataComponentTypes.AOE, 0);
		if(range > 0) {
			int displayedRange = 1 + range + range;
			tooltip.add(Component.translatable("item.pastel.workstaff.tooltip.mining_range", displayedRange, displayedRange).withStyle(ChatFormatting.GRAY));
		}
	}
	
	@Override
	public boolean itemAbilitiesEnabled(ItemStack stack) {
		return stack.getOrDefault(PastelDataComponentTypes.WORKSTAFF, WorkstaffComponent.DEFAULT).canTill();
	}
	
	public MenuProvider createScreenHandlerFactory(ItemStack itemStack) {
		return new SimpleMenuProvider((syncId, inventory, player) ->
				new WorkstaffScreenHandler(syncId, inventory, itemStack),
				Component.translatable("item.pastel.workstaff")
		);
	}
	
	public static void applyToggle(Player player, ItemStack stack, GUIToggle toggle) {
		
		switch (toggle) {
			case SELECT_1x1 -> {
				stack.set(PastelDataComponentTypes.AOE, 0);
				player.displayClientMessage(toggle.getTriggerText(), true);
			}
			case SELECT_3x3 -> {
				stack.set(PastelDataComponentTypes.AOE, 1);
				player.displayClientMessage(toggle.getTriggerText(), true);
			}
			case SELECT_5x5 -> {
				stack.set(PastelDataComponentTypes.AOE, 2);
				player.displayClientMessage(toggle.getTriggerText(), true);
			}
			// switching to another enchantment
			// fortune handling is a bit special. Its level is preserved in NBT,
			// to restore the original enchant level when switching back
			case SELECT_FORTUNE ->
				enchantAndRemoveOthers(player, stack, toggle.getTriggerText(), Enchantments.FORTUNE);
			case SELECT_SILK_TOUCH ->
				enchantAndRemoveOthers(player, stack, toggle.getTriggerText(), Enchantments.SILK_TOUCH);
			case SELECT_RESONANCE -> enchantAndRemoveOthers(player, stack, toggle.getTriggerText(), PastelEnchantments.RESONANCE);
			case ENABLE_RIGHT_CLICK_ACTIONS -> {
				stack.update(PastelDataComponentTypes.WORKSTAFF, WorkstaffComponent.DEFAULT, comp ->
						new WorkstaffComponent(true, comp.canShoot(), comp.fortuneLevel()));
				player.displayClientMessage(toggle.getTriggerText(), true);
			}
			case DISABLE_RIGHT_CLICK_ACTIONS -> {
				stack.update(PastelDataComponentTypes.WORKSTAFF, WorkstaffComponent.DEFAULT, comp ->
						new WorkstaffComponent(false, comp.canShoot(), comp.fortuneLevel()));
				player.displayClientMessage(toggle.getTriggerText(), true);
			}
			case ENABLE_PROJECTILES -> {
				stack.update(PastelDataComponentTypes.WORKSTAFF, WorkstaffComponent.DEFAULT, comp ->
						new WorkstaffComponent(comp.canTill(), true, comp.fortuneLevel()));
				player.displayClientMessage(toggle.getTriggerText(), true);
			}
			case DISABLE_PROJECTILES -> {
				stack.update(PastelDataComponentTypes.WORKSTAFF, WorkstaffComponent.DEFAULT, comp ->
						new WorkstaffComponent(comp.canTill(), false, comp.fortuneLevel()));
				player.displayClientMessage(toggle.getTriggerText(), true);
			}
		}
	}
	
	private static void enchantAndRemoveOthers(Player player, ItemStack stack, Component message, ResourceKey<Enchantment> enchantment) {
		var registryLookup = player.level().registryAccess();

		int existingLevel = Ench.getLevel(registryLookup, enchantment, stack);
		if (existingLevel > 0) {
			player.displayClientMessage(Component.translatable("item.pastel.workstaff.message.already_has_the_enchantment"), true);
			return;
		}
		
		int level = 1;
		
		if (enchantment == Enchantments.FORTUNE) {
			level = stack.getOrDefault(PastelDataComponentTypes.WORKSTAFF, WorkstaffComponent.DEFAULT).fortuneLevel();
		} else {
			int fortuneLevel = Ench.getLevel(registryLookup, Enchantments.FORTUNE, stack);
			stack.update(PastelDataComponentTypes.WORKSTAFF, WorkstaffComponent.DEFAULT, comp ->
					new WorkstaffComponent(comp.canTill(), comp.canShoot(), Math.max(fortuneLevel, 1)));
		}
		
		ItemStack newStack = stack.copy();
		var removeResult = Ench.removeEnchantments(registryLookup, newStack, Enchantments.SILK_TOUCH, PastelEnchantments.RESONANCE, Enchantments.FORTUNE);
		if (removeResult.getB() == 0) {
			if (player instanceof ServerPlayer serverPlayerEntity) {
				triggerUnenchantedWorkstaffAdvancement(serverPlayerEntity);
			}
		} else {
			var addResult = Ench.addOrUpgradeEnchantment(registryLookup, removeResult.getA(), enchantment, level, false, DatabankUtils.hasAdvancement(player, PastelAdvancements.APPLY_CONFLICTING_ENCHANTMENTS));
			if (addResult.getA()) {
				stack.set(DataComponents.ENCHANTMENTS, addResult.getB().getEnchantments());
				player.displayClientMessage(message, true);
			} else {
				player.displayClientMessage(Component.translatable("item.pastel.workstaff.message.would_result_in_conflicting_enchantments"), true);
			}
		}
	}
	
	private static void triggerUnenchantedWorkstaffAdvancement(ServerPlayer player) {
		player.playNotifySound(PastelSoundEvents.USE_FAIL, SoundSource.PLAYERS, 0.75F, 1.0F);
		Support.grantAdvancementCriterion(player, "lategame/trigger_unenchanted_workstaff", "code_triggered");
	}

	@Override
	public Vec3i getMiningArea(Player player, ItemStack stack, BlockPos pos) {
		Integer range = stack.getOrDefault(PastelDataComponentTypes.AOE, 0);

		var inkCost = (int) Math.pow(BASE_COST_PER_AOE_MINING_RANGE_INCREMENT.cost(), range);

		if (range == 0 || !InkPowered.tryDrainEnergy(player, BASE_COST_PER_AOE_MINING_RANGE_INCREMENT.color(), inkCost))
			return Vec3i.ZERO;

		return new Vec3i(range, range, 0);
	}
	
	@Override
	public Map<ResourceKey<Enchantment>, Integer> getDefaultEnchantments() {
		return Map.of(Enchantments.FORTUNE, 4);
	}

	@Override
	public boolean canPerformAction(ItemStack stack, ItemAbility itemAbility) {
		if (!stack.getOrDefault(PastelDataComponentTypes.WORKSTAFF, WorkstaffComponent.DEFAULT).canTill())
			return false;

		return super.canPerformAction(stack, itemAbility);
	}
}
