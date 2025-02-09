package de.dafuqs.spectrum.items.tools;

import de.dafuqs.spectrum.api.energy.color.*;
import de.dafuqs.spectrum.api.item.*;
import de.dafuqs.spectrum.api.render.*;
import de.dafuqs.spectrum.entity.entity.*;
import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.registries.*;
import net.fabricmc.fabric.api.item.v1.*;
import net.minecraft.component.*;
import net.minecraft.component.type.*;
import net.minecraft.enchantment.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.*;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.*;
import net.minecraft.server.network.*;
import net.minecraft.sound.*;
import net.minecraft.text.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class DraconicTwinswordItem extends SwordItem implements SplittableItem, SlotReservingItem, Preenchanted, ExtendedItemBarProvider, SlotBackgroundEffectProvider {

	public static final float MAX_CHARGE_TIME = 60;
	private final AttributeModifiersComponent modifiers;
	
	public DraconicTwinswordItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
		super(toolMaterial, settings);
		this.modifiers = createAttributeModifiers(toolMaterial, attackDamage, attackSpeed);
	}
	
	@Override
	public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
		if (SlotReservingItem.isReservingSlot(stack) || remainingUseTicks == 0) {
			return;
		}
		
		var strength = Math.min(getMaxUseTime(stack, user) - remainingUseTicks, MAX_CHARGE_TIME) / MAX_CHARGE_TIME;
		var twinsword = initiateTwinswordEntity(stack, world, user, strength);
		
		world.spawnEntity(twinsword);
		
		world.playSoundFromEntity(null, twinsword, SoundEvents.ITEM_TRIDENT_THROW.value(), SoundCategory.PLAYERS, 0.5F + strength / 2, 1.0F);
		SlotReservingItem.reserve(stack, twinsword.getUuid());
		
		if (!world.isClient())
			stack.damage(1, user, LivingEntity.getSlotForHand(user.getActiveHand()));
		
		super.onStoppedUsing(stack, world, user, remainingUseTicks);
	}
	
	@NotNull
	private static DraconicTwinswordEntity initiateTwinswordEntity(ItemStack stack, World world, LivingEntity user, float strength) {
		var twinsword = new DraconicTwinswordEntity(world);
		twinsword.setOwner(user);
		twinsword.setStack(stack);
		
		var yaw = user.getYaw();
		var pitch = user.getPitch();
		
		float f = -MathHelper.sin(yaw * (float) (Math.PI / 180.0)) * MathHelper.cos(pitch * (float) (Math.PI / 180.0));
		float h = MathHelper.cos(yaw * (float) (Math.PI / 180.0)) * MathHelper.cos(pitch * (float) (Math.PI / 180.0));
		
		twinsword.updatePosition(user.getX() + f * 1.334, user.getEyeY() - 0.2, user.getZ() + h * 1.334);
		twinsword.setVelocity(0, strength, 0);
		twinsword.setMaxPierce(SpectrumEnchantmentHelper.getLevel(world.getRegistryManager(), Enchantments.PIERCING, stack));
		twinsword.velocityDirty = true;
		twinsword.velocityModified = true;
		twinsword.pickupType = PersistentProjectileEntity.PickupPermission.DISALLOWED;
		return twinsword;
	}
	
	@Override
	public int getMaxUseTime(ItemStack stack, LivingEntity user) {
		return 200;
	}
	
	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		user.setCurrentHand(hand);
		return TypedActionResult.consume(user.getStackInHand(hand));
	}
	
	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.BLOCK;
	}

	@Override
	public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
		tooltip.add(Text.translatable("item.spectrum.draconic_twinsword.tooltip").formatted(Formatting.GRAY));
		tooltip.add(Text.translatable("item.spectrum.draconic_twinsword.tooltip2").formatted(Formatting.GRAY));
		tooltip.add(Text.translatable("item.spectrum.draconic_twinsword.tooltip3").formatted(Formatting.GRAY));
	}
	
	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		if (entity instanceof PlayerEntity player) {
			if (player.getItemCooldownManager().isCoolingDown(stack.getItem()) || SlotReservingItem.isReservingSlot(stack)) {
				stack.remove(DataComponentTypes.ATTRIBUTE_MODIFIERS);
			} else {
				stack.set(DataComponentTypes.ATTRIBUTE_MODIFIERS, modifiers);
			}
		}
	}
	
	@Override
	public boolean hasGlint(ItemStack stack) {
		return super.hasGlint(stack) && !SlotReservingItem.isReservingSlot(stack);
	}
	
	@Override
	public ItemStack getResult(ServerPlayerEntity player, ItemStack parent) {
		var result = new ItemStack(SpectrumItems.DRAGON_TALON);
		var durability = parent.getDamage();
		
		if (SlotReservingItem.isReservingSlot(parent)) {
			durability += player.getAbilities().creativeMode ? 0 : 500;
			player.getItemCooldownManager().set(result.getItem(), 400);
		}
		
		result.applyComponentsFrom(parent.getComponents());
		result.remove(DataComponentTypes.ATTRIBUTE_MODIFIERS);
		SlotReservingItem.free(result);
		
		result.setDamage(durability);
		sign(player, result);
		return result;
	}
	
	@Override
	public boolean canSplit(ServerPlayerEntity player, Hand occupiedHand, ItemStack stack) {
		if (player.getItemCooldownManager().isCoolingDown(stack.getItem()))
			return false;
		
		return switch (occupiedHand) {
			case MAIN_HAND -> player.getStackInHand(Hand.OFF_HAND).isEmpty();
			case OFF_HAND -> player.getStackInHand(Hand.MAIN_HAND).isEmpty();
		};
	}

	@Override
	public void playSound(ServerPlayerEntity player) {
		player.playSoundToPlayer(SpectrumSoundEvents.METALLIC_UNSHEATHE, SoundCategory.PLAYERS, 0.5F, 0.8F + player.getRandom().nextFloat() * 0.4F);
	}
	
	public static ItemStack findThrownStack(PlayerEntity player, UUID id) {
		var inventory = player.getInventory();
		for (int i = 0; i < inventory.size(); i++) {
			var stack = inventory.getStack(i);
			if (SlotReservingItem.isReserver(stack, id)) {
				return stack;
			}
		}
		return ItemStack.EMPTY;
	}
	
	@Override
	public Map<RegistryKey<Enchantment>, Integer> getDefaultEnchantments() {
		return Map.of(Enchantments.SWEEPING_EDGE, 5);
	}
	
	@Override
	public int barCount(ItemStack stack) {
		return 1;
	}
	
	@Override
	public boolean allowVanillaDurabilityBarRendering(@Nullable PlayerEntity player, ItemStack stack) {
		if (player == null || SlotReservingItem.isReservingSlot(stack) || player.getStackInHand(player.getActiveHand()) != stack)
			return true;
		
		return !player.isUsingItem();
	}
	
	@Override
	public BarSignature getSignature(@Nullable PlayerEntity player, @NotNull ItemStack stack, int index) {
		if (player == null || SlotReservingItem.isReservingSlot(stack) || !player.isUsingItem())
			return ExtendedItemBarProvider.PASS;
		
		var activeStack = player.getStackInHand(player.getActiveHand());
		if (activeStack != stack)
			return ExtendedItemBarProvider.PASS;
		
		var progress = Math.round(MathHelper.clampedLerp(0, 13, ((float) player.getItemUseTime() / MAX_CHARGE_TIME)));
		return new BarSignature(2, 13, 13, progress, 1, InkColors.YELLOW_COLOR, 2, ExtendedItemBarProvider.DEFAULT_BACKGROUND_COLOR);
	}
	
	@Override
	public SlotEffect backgroundType(@Nullable PlayerEntity player, ItemStack stack) {
		return SlotEffect.BORDER_FADE;
	}
	
	@Override
	public int getBackgroundColor(@Nullable PlayerEntity player, ItemStack stack, float tickDelta) {
		return InkColors.YELLOW_COLOR;
	}
	
	@Override
	public boolean canBeEnchantedWith(ItemStack stack, RegistryEntry<Enchantment> enchantment, EnchantingContext context) {
		return super.canBeEnchantedWith(stack, enchantment, context) || enchantment.matchesKey(Enchantments.CHANNELING) || enchantment.matchesKey(Enchantments.PIERCING) || enchantment.matchesKey(SpectrumEnchantments.INERTIA);
	}
}
