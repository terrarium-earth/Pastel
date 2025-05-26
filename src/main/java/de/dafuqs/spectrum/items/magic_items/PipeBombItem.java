package de.dafuqs.spectrum.items.magic_items;

import de.dafuqs.spectrum.api.item.DamageAwareItem;
import de.dafuqs.spectrum.api.item.TickAwareItem;
import de.dafuqs.spectrum.registries.SpectrumDamageTypes;
import de.dafuqs.spectrum.registries.SpectrumDataComponentTypes;
import de.dafuqs.spectrum.registries.SpectrumSoundEvents;
import de.dafuqs.spectrum.sound.PipeBombChargingSoundInstance;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.component.ResolvableProfile;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class PipeBombItem extends Item implements DamageAwareItem, TickAwareItem {
	
	public PipeBombItem(Properties settings) {
		super(settings);
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
		if (isPrimed(user.getItemInHand(hand))) {
			return super.use(world, user, hand);
		}
        
        if (world.isClientSide) {
			startSoundInstance(user);
		}
		return ItemUtils.startUsingInstantly(world, user, hand);
	}
	
	@OnlyIn(Dist.CLIENT)
	public void startSoundInstance(Player user) {
		Minecraft.getInstance().getSoundManager().play(new PipeBombChargingSoundInstance(user));
	}
	
	@Override
	public int getUseDuration(ItemStack stack, LivingEntity user) {
		return 55;
	}
	
	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity user) {
		prime(stack, world, user.position(), user);
		return stack;
	}
	
	@Override
	public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
		if (world instanceof ServerLevel serverWorld) {
			if (isPrimeTimeElapsed(world, stack)) {
				explode(stack, serverWorld, entity.position(), entity);
			}
		}
	}
	
	@Override
	public void onItemEntityTicked(ItemEntity itemEntity) {
		var stack = itemEntity.getItem();
		if (itemEntity.level() instanceof ServerLevel world) {
			if (isPrimeTimeElapsed(world, stack))
				explode(stack, world, itemEntity.getEyePosition(), null);
		}
	}
	
	@Override
	public void onItemEntityDamaged(DamageSource source, float amount, ItemEntity itemEntity) {
		if (itemEntity.level() instanceof ServerLevel world) {
			if (source.is(DamageTypeTags.IS_FIRE) || source.is(DamageTypeTags.IS_EXPLOSION))
				explode(itemEntity.getItem(), world, itemEntity.position(), null);
		}
	}
	
	private void explode(ItemStack stack, ServerLevel world, Vec3 pos, @Nullable Entity target) {
		stack.shrink(1);
		Entity owner = tryGetOwner(stack, world);
		
		if (target != null)
			target.hurt(SpectrumDamageTypes.incandescence(world, owner instanceof LivingEntity living ? living : null), 200F);
		world.explode(null, SpectrumDamageTypes.incandescence(world), new ExplosionDamageCalculator(), pos.x(), pos.y(), pos.z(), 7.5F, true, Level.ExplosionInteraction.NONE);
	}
	
	public Entity tryGetOwner(ItemStack stack, ServerLevel world) {
		var profile = stack.get(DataComponents.PROFILE);
		if (profile == null || profile.id().isEmpty())
			return null;
		return world.getEntity(profile.id().get());
	}
	
	public static void prime(ItemStack stack, Level world, Vec3 pos, @Nullable Entity user) {
		world.playSound(null, pos.x(), pos.y(), pos.z(), SpectrumSoundEvents.INCANDESCENT_ARM, SoundSource.PLAYERS, 2F, 0.9F);
		stack.set(SpectrumDataComponentTypes.TIMESTAMP, world.getGameTime());
		if (user instanceof Player player) {
			stack.set(DataComponents.PROFILE, new ResolvableProfile(player.getGameProfile()));
		}
	}
	
	public static boolean isPrimed(ItemStack stack) {
		return stack.get(SpectrumDataComponentTypes.TIMESTAMP) != null;
	}
	
	public static boolean isPrimeTimeElapsed(Level world, ItemStack stack) {
		Optional<Long> timestamp = getPrimeTime(stack);
		if (timestamp.isEmpty()) {
			return false;
		}
		return world.getGameTime() - timestamp.get() >= 100;
	}
	
	private static Optional<Long> getPrimeTime(ItemStack stack) {
		if (stack.has(SpectrumDataComponentTypes.TIMESTAMP)) {
			return Optional.of(stack.get(SpectrumDataComponentTypes.TIMESTAMP));
		}
		return Optional.empty();
	}
	
	@Override
	public UseAnim getUseAnimation(ItemStack stack) {
		return UseAnim.BOW;
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
		super.appendHoverText(stack, context, tooltip, type);
		tooltip.add(Component.translatable("item.pastel.pipe_bomb.tooltip").withStyle(ChatFormatting.GRAY));
		tooltip.add(Component.translatable("item.pastel.pipe_bomb.tooltip2").withStyle(ChatFormatting.GRAY));
		tooltip.add(Component.translatable("item.pastel.pipe_bomb.tooltip3").withStyle(ChatFormatting.GRAY));
	}
	
}
