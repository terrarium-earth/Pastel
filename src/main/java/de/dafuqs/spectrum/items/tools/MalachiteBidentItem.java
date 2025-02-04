package de.dafuqs.spectrum.items.tools;

import de.dafuqs.spectrum.api.item.*;
import de.dafuqs.spectrum.entity.entity.*;
import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.networking.s2c_payloads.*;
import de.dafuqs.spectrum.particle.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.client.gui.screen.*;
import net.minecraft.component.type.*;
import net.minecraft.enchantment.*;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.*;
import net.minecraft.registry.*;
import net.minecraft.screen.slot.*;
import net.minecraft.server.world.*;
import net.minecraft.sound.*;
import net.minecraft.stat.*;
import net.minecraft.text.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class MalachiteBidentItem extends TridentItem implements Preenchanted, ExpandedStatTooltip, ArmorPiercingItem {
	
	private final float armorPierce, protPierce;
	
	public MalachiteBidentItem(Item.Settings settings, double attackSpeed, double damage, float armorPierce, float protPierce) {
		super(settings.attributeModifiers(AttributeModifiersComponent.builder()
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(BASE_ATTACK_DAMAGE_MODIFIER_ID, damage, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
				.add(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(BASE_ATTACK_SPEED_MODIFIER_ID, attackSpeed, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
				.build()));
		this.armorPierce = armorPierce;
		this.protPierce = protPierce;
	}
	
	@Override
	public Map<RegistryKey<Enchantment>, Integer> getDefaultEnchantments() {
		return Map.of(Enchantments.IMPALING, 6);
	}
	
	@Override
	public ItemStack getDefaultStack() {
		return getDefaultEnchantedStack(this);
	}
	
	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack handStack = user.getStackInHand(hand);
		if (handStack.getDamage() >= handStack.getMaxDamage() - 1) {
			return TypedActionResult.fail(handStack);
		}
		user.setCurrentHand(hand);
		return TypedActionResult.consume(handStack);
	}
	
	@Override
	public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
		if (user instanceof PlayerEntity player) {
			int useTime = this.getMaxUseTime(stack, user) - remainingUseTicks;
			if (useTime >= 10) {
				player.incrementStat(Stats.USED.getOrCreateStat(this));
				
				if (canStartRiptide(player, stack)) {
					riptide(world, player, stack, getRiptideLevel(world.getRegistryManager(), stack));
				} else if (!world.isClient) {
					stack.damage(1, player, LivingEntity.getSlotForHand(user.getActiveHand()));
					throwBident(stack, (ServerWorld) world, player);
				}
			}
		}
	}
	
	@Override
	public boolean canRepair(ItemStack stack, ItemStack ingredient) {
		return SpectrumToolMaterial.MALACHITE.getRepairIngredient().test(ingredient) || super.canRepair(stack, ingredient);
	}
	
	public int getRiptideLevel(RegistryWrapper.WrapperLookup lookup, ItemStack stack) {
		return SpectrumEnchantmentHelper.getLevel(lookup, Enchantments.RIPTIDE, stack);
	}
	
	protected void riptide(World world, PlayerEntity playerEntity, ItemStack stack, int riptideLevel) {
		yeetPlayer(playerEntity, (float) riptideLevel);
		playerEntity.useRiptide(20, (float) playerEntity.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE), stack);
		if (playerEntity.isOnGround()) {
			playerEntity.move(MovementType.SELF, new Vec3d(0.0, 1.2, 0.0));
		}
		
		SoundEvent soundEvent;
		if (riptideLevel >= 3) {
			soundEvent = SoundEvents.ITEM_TRIDENT_RIPTIDE_3.value();
		} else if (riptideLevel == 2) {
			soundEvent = SoundEvents.ITEM_TRIDENT_RIPTIDE_2.value();
		} else {
			soundEvent = SoundEvents.ITEM_TRIDENT_RIPTIDE_1.value();
		}
		
		world.playSoundFromEntity(null, playerEntity, soundEvent, SoundCategory.PLAYERS, 1.0F, 1.0F);
	}
	
	protected void yeetPlayer(PlayerEntity playerEntity, float riptideLevel) {
		float f = playerEntity.getYaw();
		float g = playerEntity.getPitch();
		float h = -MathHelper.sin(f * 0.017453292F) * MathHelper.cos(g * 0.017453292F);
		float k = -MathHelper.sin(g * 0.017453292F);
		float l = MathHelper.cos(f * 0.017453292F) * MathHelper.cos(g * 0.017453292F);
		float m = MathHelper.sqrt(h * h + k * k + l * l);
		float n = 3.0F * ((1.0F + riptideLevel) / 4.0F);
		h *= n / m;
		k *= n / m;
		l *= n / m;
		playerEntity.addVelocity(h, k, l);
	}
	
	protected void throwBident(ItemStack stack, ServerWorld world, PlayerEntity playerEntity) {
		boolean mirrorImage = isThrownAsMirrorImage(stack, world, playerEntity);
		
		BidentBaseEntity bidentBaseEntity = mirrorImage ? new BidentMirrorImageEntity(world) : new BidentEntity(world);
		bidentBaseEntity.setStack(stack);
		bidentBaseEntity.setOwner(playerEntity);
		bidentBaseEntity.updatePosition(playerEntity.getX(), playerEntity.getEyeY() - 0.1, playerEntity.getZ());
		bidentBaseEntity.setVelocity(playerEntity, playerEntity.getPitch(), playerEntity.getYaw(), 0.0F, getThrowSpeed(stack), 1.0F);
		if (!mirrorImage && playerEntity.getAbilities().creativeMode) {
			bidentBaseEntity.pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;
		}
		
		world.spawnEntity(bidentBaseEntity);
		var soundEvent = SoundEvents.ITEM_TRIDENT_THROW.value();
		if (mirrorImage) {
			PlayParticleWithRandomOffsetAndVelocityPayload.playParticleWithRandomOffsetAndVelocity(world, bidentBaseEntity.getPos(), SpectrumParticleTypes.MIRROR_IMAGE, 8, Vec3d.ZERO, new Vec3d(0.2, 0.2, 0.2));
			bidentBaseEntity.pickupType = PersistentProjectileEntity.PickupPermission.DISALLOWED;
			soundEvent = SpectrumSoundEvents.BIDENT_MIRROR_IMAGE_THROWN;
		} else if (playerEntity.getAbilities().creativeMode) {
			bidentBaseEntity.pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;
		}
		
		world.playSoundFromEntity(null, bidentBaseEntity, soundEvent, SoundCategory.PLAYERS, 1.0F, 1.0F);
		if (!playerEntity.getAbilities().creativeMode && !mirrorImage) {
			playerEntity.getInventory().removeOne(stack);
		}
	}
	
	public void markDisabled(ItemStack stack, boolean disabled) {
		ActivatableItem.setActivated(stack, !disabled);
	}
	
	public boolean isDisabled(ItemStack stack) {
		return !ActivatableItem.isActivated(stack);
	}
	
	public boolean canBeDisabled() {
		return false;
	}
	
	@Override
	public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
		if (isDisabled(stack))
			tooltip.add(Text.translatable("item.spectrum.bident.toolTip.disabled").formatted(Formatting.RED, Formatting.ITALIC));
	}
	
	@Override
	public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player) {
		if (canBeDisabled() && clickType == ClickType.RIGHT) {
			markDisabled(stack, !isDisabled(stack));
			return true;
		}
		return false;
	}
	
	public float getThrowSpeed(ItemStack stack) {
		return 3F;
	}
	
	public boolean canStartRiptide(PlayerEntity player, ItemStack stack) {
		return getRiptideLevel(player.getWorld().getRegistryManager(), stack) > 0 && player.isTouchingWaterOrRain();
	}
	
	public boolean isThrownAsMirrorImage(ItemStack stack, ServerWorld world, PlayerEntity player) {
		return false;
	}
	
	@Override
	public float getDefenseMultiplier(LivingEntity target, ItemStack stack) {
		return 1 - armorPierce;
	}
	
	@Override
	public float getToughnessMultiplier(LivingEntity target, ItemStack stack) {
		return 1;
	}
	
	@Override
	public float getProtReduction(LivingEntity target, ItemStack stack) {
		return protPierce;
	}
	
	@Override
	public DamageComposition getDamageComposition(LivingEntity attacker, LivingEntity target, ItemStack stack, float damage) {
		var composition = new DamageComposition();
		var source = composition.getPlayerOrEntity(attacker);
		SpectrumDamageTypes.wrapWithStackTracking(source, stack);
		composition.add(source, damage);
		return composition;
	}
	
	@Override
	public void expandTooltip(ItemStack stack, @Nullable PlayerEntity player, List<Text> tooltip, TooltipContext context) {
		if (Screen.hasShiftDown()) {
			tooltip.add(Text.translatable("item.spectrum.bident.postToolTip.ap", armorPierce * 100).formatted(Formatting.DARK_GREEN));
			
			if (protPierce > 0) {
				tooltip.add(Text.translatable("item.spectrum.bident.postToolTip.pp", protPierce * 100).formatted(Formatting.DARK_GREEN));
			}
			if (canBeDisabled()) {
				tooltip.add(Text.translatable("item.spectrum.bident.postToolTip.disable").formatted(Formatting.DARK_GRAY, Formatting.ITALIC));
			}
		} else {
			tooltip.add(Text.translatable("spectrum.tooltip.press_shift_for_more").formatted(Formatting.DARK_GRAY, Formatting.ITALIC));
		}
	}
}
