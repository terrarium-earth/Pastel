package de.dafuqs.spectrum.items.magic_items;

import de.dafuqs.spectrum.api.block.*;
import de.dafuqs.spectrum.components.*;
import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.networking.c2s_payloads.*;
import de.dafuqs.spectrum.registries.*;
import de.dafuqs.spectrum.sound.*;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.client.networking.v1.*;
import net.fabricmc.fabric.api.item.v1.*;
import net.minecraft.advancement.criterion.*;
import net.minecraft.client.*;
import net.minecraft.enchantment.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.*;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.*;
import net.minecraft.server.network.*;
import net.minecraft.server.world.*;
import net.minecraft.sound.*;
import net.minecraft.stat.*;
import net.minecraft.text.*;
import net.minecraft.util.*;
import net.minecraft.util.hit.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class EnderSpliceItem extends Item {
	
	public EnderSpliceItem(Settings settings) {
		super(settings);
	}
	
	public static boolean isSameWorld(World world1, World world2) {
		return world1.getRegistryKey().getValue().toString().equals(world2.getRegistryKey().getValue().toString());
	}
	
	public static void setTeleportTargetPos(@NotNull ItemStack itemStack, World world, Vec3d pos) {
		itemStack.set(SpectrumDataComponentTypes.ENDER_SPLICE, new EnderSpliceComponent(pos, world.getRegistryKey()));
	}
	
	public static void setTeleportTargetPlayer(@NotNull ItemStack itemStack, ServerPlayerEntity player) {
		itemStack.set(SpectrumDataComponentTypes.ENDER_SPLICE, new EnderSpliceComponent(player.getName().getString(), player.getUuid()));
	}
	
	public static boolean hasTeleportTarget(ItemStack itemStack) {
		return itemStack.contains(SpectrumDataComponentTypes.ENDER_SPLICE);
	}
	
	public static void clearTeleportTarget(ItemStack itemStack) {
		itemStack.remove(SpectrumDataComponentTypes.ENDER_SPLICE);
	}
	
	@Override
	public ItemStack finishUsing(ItemStack itemStack, World world, LivingEntity user) {
		if (world.isClient) {
			if (getTeleportTargetPos(itemStack).isEmpty() && getTeleportTargetPlayerUUID(itemStack).isEmpty()) {
				interactWithEntityClient();
			}
		} else if (user instanceof ServerPlayerEntity playerEntity) {
			Criteria.CONSUME_ITEM.trigger(playerEntity, itemStack);
			
			boolean resonance = EnchantmentHelper.hasAnyEnchantmentsIn(itemStack, SpectrumEnchantmentTags.DIMENSIONAL_TELEPORT);
			
			// If Dimension & Pos stored => Teleport to that position
			var teleportTargetPos = getTeleportTargetPos(itemStack);
			if (teleportTargetPos.isPresent()) {
				World targetWorld = world.getServer().getWorld(teleportTargetPos.get().getLeft());
				if (teleportPlayerToPos(world, user, playerEntity, targetWorld, teleportTargetPos.get().getRight(), resonance)) {
					decrementWithChance(itemStack, world, playerEntity);
				}
			} else {
				// If UUID stored => Teleport to player, if online
				Optional<UUID> teleportTargetPlayerUUID = getTeleportTargetPlayerUUID(itemStack);
				if (teleportTargetPlayerUUID.isPresent()) {
					if (teleportPlayerToPlayerWithUUID(world, user, playerEntity, teleportTargetPlayerUUID.get(), resonance)) {
						decrementWithChance(itemStack, world, playerEntity);
					}
				} else {
					// Nothing stored => Store current position
					setTeleportTargetPos(itemStack, playerEntity.getEntityWorld(), playerEntity.getPos());
					world.playSound(null, playerEntity.getBlockPos(), SpectrumSoundEvents.ENDER_SPLICE_BOUND, SoundCategory.PLAYERS, 1.0F, 1.0F);
				}
			}
			playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
		}
		
		return itemStack;
	}
	
	private static void decrementWithChance(ItemStack itemStack, World world, ServerPlayerEntity playerEntity) {
		if (EnchantmentHelper.hasAnyEnchantmentsIn(itemStack, SpectrumEnchantmentTags.INDESTRUCTIBLE_EFFECT)) {
			return;
		}
		if (!playerEntity.getAbilities().creativeMode) {
			int unbreakingLevel = SpectrumEnchantmentHelper.getLevel(world.getRegistryManager(), Enchantments.UNBREAKING, itemStack);
			if (unbreakingLevel == 0) {
				itemStack.decrement(1);
			} else {
				itemStack.decrement(Support.getIntFromDecimalWithChance(1.0 / (1 + unbreakingLevel), world.random));
			}
		}
	}
	
	@Environment(EnvType.CLIENT)
    public void interactWithEntityClient() {
		// If aiming at an entity: trigger entity interaction
		MinecraftClient client = MinecraftClient.getInstance();
		HitResult hitResult = client.crosshairTarget;
		if (hitResult != null && hitResult.getType() == HitResult.Type.ENTITY && ((EntityHitResult) hitResult).getEntity() instanceof PlayerEntity playerEntity) {
			ClientPlayNetworking.send(new BindEnderSpliceToPlayerPayload(playerEntity.getId()));
		}
	}
	
	private boolean teleportPlayerToPlayerWithUUID(World world, LivingEntity user, PlayerEntity playerEntity, UUID targetPlayerUUID, boolean hasResonance) {
		PlayerEntity targetPlayer = PlayerOwned.getPlayerEntityIfOnline(targetPlayerUUID);
		if (targetPlayer != null) {
			return teleportPlayerToPos(targetPlayer.getEntityWorld(), user, playerEntity, targetPlayer.getEntityWorld(), targetPlayer.getPos(), hasResonance);
		}
		return false;
	}
	
	private boolean teleportPlayerToPos(World world, LivingEntity user, PlayerEntity playerEntity, World targetWorld, Vec3d targetPos, boolean hasResonance) {
		boolean isSameWorld = isSameWorld(user.getEntityWorld(), targetWorld);
		Vec3d currentPos = playerEntity.getPos();
		if ((hasResonance || isSameWorld) && targetWorld instanceof ServerWorld targetServerWorld) {
			world.playSound(playerEntity, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SpectrumSoundEvents.PLAYER_TELEPORTS, SoundCategory.PLAYERS, 1.0F, 1.0F);
			
			if (!isSameWorld) {
				user.teleportTo(new TeleportTarget(targetServerWorld, targetPos.add(0, 0.25, 0), new Vec3d(0, 0, 0), user.getYaw(), user.getPitch(), TeleportTarget.NO_OP));
			} else {
				user.requestTeleport(targetPos.getX(), targetPos.y + 0.25, targetPos.z); // +0.25 makes it look way more lively
			}
			world.playSound(playerEntity, targetPos.getX(), targetPos.y, targetPos.z, SpectrumSoundEvents.PLAYER_TELEPORTS, SoundCategory.PLAYERS, 1.0F, 1.0F);
			
			// make sure the sound plays even when the player currently teleports
			if (playerEntity instanceof ServerPlayerEntity) {
				world.playSound(null, playerEntity.getBlockPos(), SpectrumSoundEvents.PLAYER_TELEPORTS, SoundCategory.PLAYERS, 1.0F, 1.0F);
				world.playSound(null, playerEntity.getBlockPos(), SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.PLAYERS, 1.0F, 1.0F);
			}
			return true;
		} else {
			user.stopUsingItem();
			world.playSound(null, currentPos.getX(), currentPos.getY(), currentPos.getZ(), SpectrumSoundEvents.USE_FAIL, SoundCategory.PLAYERS, 1.0F, 1.0F);
			return false;
		}
	}
	
	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		if (world.isClient) {
			startSoundInstance(user);
		}
		return ItemUsage.consumeHeldItem(world, user, hand);
	}
	
	@Environment(EnvType.CLIENT)
	public void startSoundInstance(PlayerEntity user) {
		MinecraftClient.getInstance().getSoundManager().play(new EnderSpliceChargingSoundInstance(user));
	}
	
	@Override
	public int getMaxUseTime(ItemStack stack, LivingEntity user) {
		return 48;
	}
	
	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.BOW;
	}
	
	@Override
	@Environment(EnvType.CLIENT)
	public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
		// If Dimension & Pos stored => Teleport to that position
		var teleportTargetPos = getTeleportTargetPos(stack);
		if (teleportTargetPos.isPresent()) {
			String dimensionDisplayString = Support.getReadableDimensionString(teleportTargetPos.get().getLeft().getValue().toString());
			Vec3d pos = teleportTargetPos.get().getRight();
			tooltip.add(Text.translatable("item.spectrum.ender_splice.tooltip.bound_pos", (int) pos.x, (int) pos.y, (int) pos.z, dimensionDisplayString));
			return;
		} else {
			// If UUID stored => Teleport to player, if online
			Optional<UUID> teleportTargetPlayerUUID = getTeleportTargetPlayerUUID(stack);
			if (teleportTargetPlayerUUID.isPresent()) {
				Optional<String> teleportTargetPlayerName = getTeleportTargetPlayerName(stack);
				if (teleportTargetPlayerName.isPresent()) {
					tooltip.add(Text.translatable("item.spectrum.ender_splice.tooltip.bound_player", teleportTargetPlayerName.get()));
				} else {
					tooltip.add(Text.translatable("item.spectrum.ender_splice.tooltip.bound_player", "???"));
				}
				return;
			}
		}
		
		tooltip.add(Text.translatable("item.spectrum.ender_splice.tooltip.unbound"));
	}
	
	public Optional<Pair<RegistryKey<World>, Vec3d>> getTeleportTargetPos(@NotNull ItemStack itemStack) {
		var component = itemStack.getOrDefault(SpectrumDataComponentTypes.ENDER_SPLICE, EnderSpliceComponent.DEFAULT);
		if (component.pos().isPresent() && component.dimension().isPresent())
			return Optional.of(new Pair<>(component.dimension().get(), component.pos().get()));
		return Optional.empty();
	}
	
	public Optional<UUID> getTeleportTargetPlayerUUID(@NotNull ItemStack itemStack) {
		return itemStack.getOrDefault(SpectrumDataComponentTypes.ENDER_SPLICE, EnderSpliceComponent.DEFAULT).targetUUID();
	}
	
	public Optional<String> getTeleportTargetPlayerName(@NotNull ItemStack itemStack) {
		return itemStack.getOrDefault(SpectrumDataComponentTypes.ENDER_SPLICE, EnderSpliceComponent.DEFAULT).targetName();
	}
	
	@Override
	public boolean isEnchantable(ItemStack stack) {
		return stack.getCount() == 1;
	}
	
	@Override
	public int getEnchantability() {
		return 50;
	}
	
	@Override
	public boolean canBeEnchantedWith(ItemStack stack, RegistryEntry<Enchantment> enchantment, EnchantingContext context) {
		return super.canBeEnchantedWith(stack, enchantment, context) || enchantment.matchesKey(SpectrumEnchantments.RESONANCE) || enchantment.matchesKey(SpectrumEnchantments.INDESTRUCTIBLE) || enchantment.matchesKey(Enchantments.UNBREAKING);
	}
	
}
