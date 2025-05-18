package de.dafuqs.spectrum.items.magic_items;

import de.dafuqs.spectrum.api.block.PlayerOwned;
import de.dafuqs.spectrum.components.EnderSpliceComponent;
import de.dafuqs.spectrum.helpers.SpectrumEnchantmentHelper;
import de.dafuqs.spectrum.helpers.Support;
import de.dafuqs.spectrum.networking.c2s_payloads.BindEnderSpliceToPlayerPayload;
import de.dafuqs.spectrum.registries.SpectrumDataComponentTypes;
import de.dafuqs.spectrum.registries.SpectrumEnchantmentTags;
import de.dafuqs.spectrum.registries.SpectrumEnchantments;
import de.dafuqs.spectrum.registries.SpectrumSoundEvents;
import de.dafuqs.spectrum.sound.EnderSpliceChargingSoundInstance;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.item.v1.EnchantingContext;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Tuple;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class EnderSpliceItem extends Item {
	
	public EnderSpliceItem(Properties settings) {
		super(settings);
	}
	
	public static boolean isSameWorld(Level world1, Level world2) {
		return world1.dimension().location().toString().equals(world2.dimension().location().toString());
	}
	
	public static void setTeleportTargetPos(@NotNull ItemStack itemStack, Level world, Vec3 pos) {
		itemStack.set(SpectrumDataComponentTypes.ENDER_SPLICE, new EnderSpliceComponent(pos, world.dimension()));
	}
	
	public static void setTeleportTargetPlayer(@NotNull ItemStack itemStack, ServerPlayer player) {
		itemStack.set(SpectrumDataComponentTypes.ENDER_SPLICE, new EnderSpliceComponent(player.getName().getString(), player.getUUID()));
	}
	
	public static boolean hasTeleportTarget(ItemStack itemStack) {
		return itemStack.has(SpectrumDataComponentTypes.ENDER_SPLICE);
	}
	
	public static void clearTeleportTarget(ItemStack itemStack) {
		itemStack.remove(SpectrumDataComponentTypes.ENDER_SPLICE);
	}
	
	@Override
	public ItemStack finishUsingItem(ItemStack itemStack, Level world, LivingEntity user) {
		if (world.isClientSide) {
			if (getTeleportTargetPos(itemStack).isEmpty() && getTeleportTargetPlayerUUID(itemStack).isEmpty()) {
				interactWithEntityClient();
			}
		} else if (user instanceof ServerPlayer playerEntity) {
			CriteriaTriggers.CONSUME_ITEM.trigger(playerEntity, itemStack);
			
			boolean resonance = EnchantmentHelper.hasTag(itemStack, SpectrumEnchantmentTags.DIMENSIONAL_TELEPORT);
			
			// If Dimension & Pos stored => Teleport to that position
			var teleportTargetPos = getTeleportTargetPos(itemStack);
			if (teleportTargetPos.isPresent()) {
				Level targetWorld = world.getServer().getLevel(teleportTargetPos.get().getA());
				if (teleportPlayerToPos(world, user, playerEntity, targetWorld, teleportTargetPos.get().getB(), resonance)) {
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
					setTeleportTargetPos(itemStack, playerEntity.getCommandSenderWorld(), playerEntity.position());
					world.playSound(null, playerEntity.blockPosition(), SpectrumSoundEvents.ENDER_SPLICE_BOUND, SoundSource.PLAYERS, 1.0F, 1.0F);
				}
			}
			playerEntity.awardStat(Stats.ITEM_USED.get(this));
		}
		
		return itemStack;
	}
	
	private static void decrementWithChance(ItemStack itemStack, Level world, ServerPlayer playerEntity) {
		if (EnchantmentHelper.hasTag(itemStack, SpectrumEnchantmentTags.INDESTRUCTIBLE_EFFECT)) {
			return;
		}
		if (!playerEntity.getAbilities().instabuild) {
			int unbreakingLevel = SpectrumEnchantmentHelper.getLevel(world.registryAccess(), Enchantments.UNBREAKING, itemStack);
			if (unbreakingLevel == 0) {
				itemStack.shrink(1);
			} else {
				itemStack.shrink(Support.getIntFromDecimalWithChance(1.0 / (1 + unbreakingLevel), world.random));
			}
		}
	}
	
	@OnlyIn(Dist.CLIENT)
    public void interactWithEntityClient() {
		// If aiming at an entity: trigger entity interaction
		Minecraft client = Minecraft.getInstance();
		HitResult hitResult = client.hitResult;
		if (hitResult != null && hitResult.getType() == HitResult.Type.ENTITY && ((EntityHitResult) hitResult).getEntity() instanceof Player playerEntity) {
			ClientPlayNetworking.send(new BindEnderSpliceToPlayerPayload(playerEntity.getId()));
		}
	}
	
	private boolean teleportPlayerToPlayerWithUUID(Level world, LivingEntity user, Player playerEntity, UUID targetPlayerUUID, boolean hasResonance) {
		Player targetPlayer = PlayerOwned.getPlayerEntityIfOnline(targetPlayerUUID);
		if (targetPlayer != null) {
			return teleportPlayerToPos(targetPlayer.getCommandSenderWorld(), user, playerEntity, targetPlayer.getCommandSenderWorld(), targetPlayer.position(), hasResonance);
		}
		return false;
	}
	
	private boolean teleportPlayerToPos(Level world, LivingEntity user, Player playerEntity, Level targetWorld, Vec3 targetPos, boolean hasResonance) {
		boolean isSameWorld = isSameWorld(user.getCommandSenderWorld(), targetWorld);
		Vec3 currentPos = playerEntity.position();
		if ((hasResonance || isSameWorld) && targetWorld instanceof ServerLevel targetServerWorld) {
			world.playSound(playerEntity, currentPos.x(), currentPos.y(), currentPos.z(), SpectrumSoundEvents.PLAYER_TELEPORTS, SoundSource.PLAYERS, 1.0F, 1.0F);
			
			if (!isSameWorld) {
				user.changeDimension(new DimensionTransition(targetServerWorld, targetPos.add(0, 0.25, 0), new Vec3(0, 0, 0), user.getYRot(), user.getXRot(), DimensionTransition.DO_NOTHING));
			} else {
				user.teleportTo(targetPos.x(), targetPos.y + 0.25, targetPos.z); // +0.25 makes it look way more lively
			}
			world.playSound(playerEntity, targetPos.x(), targetPos.y, targetPos.z, SpectrumSoundEvents.PLAYER_TELEPORTS, SoundSource.PLAYERS, 1.0F, 1.0F);
			
			// make sure the sound plays even when the player currently teleports
			if (playerEntity instanceof ServerPlayer) {
				world.playSound(null, playerEntity.blockPosition(), SpectrumSoundEvents.PLAYER_TELEPORTS, SoundSource.PLAYERS, 1.0F, 1.0F);
				world.playSound(null, playerEntity.blockPosition(), SoundEvents.GLASS_BREAK, SoundSource.PLAYERS, 1.0F, 1.0F);
			}
			return true;
		} else {
			user.releaseUsingItem();
			world.playSound(null, currentPos.x(), currentPos.y(), currentPos.z(), SpectrumSoundEvents.USE_FAIL, SoundSource.PLAYERS, 1.0F, 1.0F);
			return false;
		}
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
		if (world.isClientSide) {
			startSoundInstance(user);
		}
		return ItemUtils.startUsingInstantly(world, user, hand);
	}
	
	@OnlyIn(Dist.CLIENT)
	public void startSoundInstance(Player user) {
		Minecraft.getInstance().getSoundManager().play(new EnderSpliceChargingSoundInstance(user));
	}
	
	@Override
	public int getUseDuration(ItemStack stack, LivingEntity user) {
		return 48;
	}
	
	@Override
	public UseAnim getUseAnimation(ItemStack stack) {
		return UseAnim.BOW;
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
		// If Dimension & Pos stored => Teleport to that position
		var teleportTargetPos = getTeleportTargetPos(stack);
		if (teleportTargetPos.isPresent()) {
			String dimensionDisplayString = Support.getReadableDimensionString(teleportTargetPos.get().getA().location().toString());
			Vec3 pos = teleportTargetPos.get().getB();
			tooltip.add(Component.translatable("item.spectrum.ender_splice.tooltip.bound_pos", (int) pos.x, (int) pos.y, (int) pos.z, dimensionDisplayString));
			return;
		} else {
			// If UUID stored => Teleport to player, if online
			Optional<UUID> teleportTargetPlayerUUID = getTeleportTargetPlayerUUID(stack);
			if (teleportTargetPlayerUUID.isPresent()) {
				Optional<String> teleportTargetPlayerName = getTeleportTargetPlayerName(stack);
				if (teleportTargetPlayerName.isPresent()) {
					tooltip.add(Component.translatable("item.spectrum.ender_splice.tooltip.bound_player", teleportTargetPlayerName.get()));
				} else {
					tooltip.add(Component.translatable("item.spectrum.ender_splice.tooltip.bound_player", "???"));
				}
				return;
			}
		}
		
		tooltip.add(Component.translatable("item.spectrum.ender_splice.tooltip.unbound"));
	}
	
	public Optional<Tuple<ResourceKey<Level>, Vec3>> getTeleportTargetPos(@NotNull ItemStack itemStack) {
		var component = itemStack.getOrDefault(SpectrumDataComponentTypes.ENDER_SPLICE, EnderSpliceComponent.DEFAULT);
		if (component.pos().isPresent() && component.dimension().isPresent())
			return Optional.of(new Tuple<>(component.dimension().get(), component.pos().get()));
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
	public int getEnchantmentValue() {
		return 50;
	}
	
	@Override
	public boolean canBeEnchantedWith(ItemStack stack, Holder<Enchantment> enchantment, EnchantingContext context) {
		return super.canBeEnchantedWith(stack, enchantment, context) || enchantment.is(SpectrumEnchantments.RESONANCE) || enchantment.is(SpectrumEnchantments.INDESTRUCTIBLE) || enchantment.is(Enchantments.UNBREAKING);
	}
	
}
