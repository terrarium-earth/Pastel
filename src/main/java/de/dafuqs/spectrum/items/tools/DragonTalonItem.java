package de.dafuqs.spectrum.items.tools;

import de.dafuqs.spectrum.api.energy.color.*;
import de.dafuqs.spectrum.api.item.*;
import de.dafuqs.spectrum.api.render.*;
import de.dafuqs.spectrum.entity.entity.*;
import de.dafuqs.spectrum.registries.*;
import net.fabricmc.fabric.api.item.v1.*;
import net.minecraft.component.*;
import net.minecraft.component.type.*;
import net.minecraft.enchantment.*;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.*;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.*;
import net.minecraft.server.network.*;
import net.minecraft.server.world.*;
import net.minecraft.sound.*;
import net.minecraft.text.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class DragonTalonItem extends MalachiteBidentItem implements MergeableItem, SlotReservingItem, SlotBackgroundEffectProvider {
	
	private final AttributeModifiersComponent modifiers;
	
	public DragonTalonItem(ToolMaterial toolMaterial, double damage, double extraReach, Item.Settings settings) {
		super(settings, 0, 0, 0, 0);
		this.modifiers = AttributeModifiersComponent.builder()
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(BASE_ATTACK_DAMAGE_MODIFIER_ID, damage + toolMaterial.getAttackDamage(), EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
				.add(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(BASE_ATTACK_SPEED_MODIFIER_ID, -2.0, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
				.add(EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE, new EntityAttributeModifier(SpectrumEntityAttributes.REACH_MODIFIER_ID, extraReach, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
				.build();
	}
	
	@Override
	public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
		tooltip.add(Text.translatable("item.spectrum.dragon_talon.tooltip").formatted(Formatting.GRAY));
		tooltip.add(Text.translatable("item.spectrum.dragon_talon.tooltip2").formatted(Formatting.GRAY));
		tooltip.add(Text.translatable("item.spectrum.dragon_talon.tooltip3").formatted(Formatting.GRAY));
	}
	
	@Override
	public float getThrowSpeed(ItemStack stack) {
		return 3.5F;
	}
	
	@Override
	protected void throwBident(ItemStack stack, ServerWorld world, PlayerEntity playerEntity) {
		var needleEntity = new DragonTalonEntity(world);
		needleEntity.setStack(stack);
		needleEntity.setOwner(playerEntity);
		needleEntity.updatePosition(playerEntity.getX(), playerEntity.getEyeY() - 0.1, playerEntity.getZ());
		needleEntity.setVelocity(playerEntity, playerEntity.getPitch(), playerEntity.getYaw(), 0.0F, getThrowSpeed(stack), 1.0F);
		needleEntity.velocityDirty = true;
		needleEntity.velocityModified = true;
		needleEntity.pickupType = PersistentProjectileEntity.PickupPermission.ALLOWED;
		
		world.spawnEntity(needleEntity);
		SoundEvent soundEvent = SoundEvents.ITEM_TRIDENT_THROW.value();
		
		world.playSoundFromEntity(null, needleEntity, soundEvent, SoundCategory.PLAYERS, 1.0F, 1.0F);
		SlotReservingItem.reserve(stack, needleEntity.getUuid());
	}
	
	@Override
	public ItemStack getResult(ServerPlayerEntity player, ItemStack firstHalf, ItemStack secondHalf) {
		var durability = Math.max(firstHalf.getDamage(), secondHalf.getDamage());
		var result = new ItemStack(SpectrumItems.DRACONIC_TWINSWORD);
		result.applyComponentsFrom(firstHalf.getComponents());
		
		result.remove(SpectrumDataComponentTypes.PAIRED_ITEM);
		result.remove(DataComponentTypes.ATTRIBUTE_MODIFIERS);
		SlotReservingItem.free(result);
		
		if (SlotReservingItem.isReservingSlot(firstHalf) || SlotReservingItem.isReservingSlot(secondHalf)) {
			durability += player.getAbilities().creativeMode ? 0 : 500;
			player.getItemCooldownManager().set(result.getItem(), 400);
		}
		result.setDamage(durability);
		
		return result;
	}
	
	@Override
	public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
		var hand = user.getActiveHand();
		if (hand == Hand.MAIN_HAND)
			return;
		
		if (!SlotReservingItem.isReservingSlot(stack)) {
			super.onStoppedUsing(user.getStackInHand(Hand.OFF_HAND), world, user, remainingUseTicks);
			return;
		}
		
		var reserver = SlotReservingItem.getReserver(stack);
		if (world instanceof ServerWorld serverWorld && reserver != null) {
			if (serverWorld.getEntity(reserver) instanceof DragonTalonEntity needle) {
				needle.recall();
			}
		}
	}

	@Override
	public boolean hasGlint(ItemStack stack) {
		return super.hasGlint(stack) && !SlotReservingItem.isReservingSlot(stack);
	}

	@Override
	public boolean canMerge(ServerPlayerEntity player, ItemStack parent, ItemStack other) {
		if (player.getItemCooldownManager().isCoolingDown(parent.getItem()))
			return false;
		return (parent.getItem() == other.getItem() && verify(parent, other));
	}
	
	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		if (hand == Hand.MAIN_HAND)
			return TypedActionResult.fail(user.getStackInHand(hand));
		return super.use(world, user, hand);
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
	public DamageComposition getDamageComposition(LivingEntity attacker, LivingEntity target, ItemStack stack, float damage) {
		var composition = new DamageComposition();
		composition.add(SpectrumDamageTypes.evisceration(attacker.getWorld(), attacker), damage);
		return composition;
	}
	
	@Override
	public Map<RegistryKey<Enchantment>, Integer> getDefaultEnchantments() {
		return Map.of();
	}
	
	@Override
	public float getDefenseMultiplier(LivingEntity target, ItemStack stack) {
		return 1F;
	}
	
	@Override
	public SlotBackgroundEffectProvider.SlotEffect backgroundType(@Nullable PlayerEntity player, ItemStack stack) {
		return SlotBackgroundEffectProvider.SlotEffect.BORDER_FADE;
	}
	
	@Override
	public int getBackgroundColor(@Nullable PlayerEntity player, ItemStack stack, float tickDelta) {
		return InkColors.YELLOW_COLOR;
	}
	
	@Override
	public void expandTooltip(ItemStack stack, @Nullable PlayerEntity player, List<Text> tooltip, TooltipContext context) {
	}
	
	@Override
	public boolean canBeEnchantedWith(ItemStack stack, RegistryEntry<Enchantment> enchantment, EnchantingContext context) {
		return super.canBeEnchantedWith(stack, enchantment, context) || enchantment.matchesKey(Enchantments.CHANNELING) || enchantment.matchesKey(Enchantments.PIERCING) || enchantment.matchesKey(SpectrumEnchantments.INERTIA);
	}
}
