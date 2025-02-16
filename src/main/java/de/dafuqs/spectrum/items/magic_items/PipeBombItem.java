package de.dafuqs.spectrum.items.magic_items;

import de.dafuqs.spectrum.api.item.*;
import de.dafuqs.spectrum.registries.*;
import de.dafuqs.spectrum.sound.*;
import net.fabricmc.api.*;
import net.minecraft.client.*;
import net.minecraft.component.*;
import net.minecraft.component.type.*;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.*;
import net.minecraft.registry.tag.*;
import net.minecraft.server.world.*;
import net.minecraft.sound.*;
import net.minecraft.text.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraft.world.explosion.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class PipeBombItem extends Item implements DamageAwareItem, TickAwareItem {
	
	public PipeBombItem(Settings settings) {
		super(settings);
	}
	
	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		if (isPrimed(user.getStackInHand(hand))) {
			return super.use(world, user, hand);
		}
        
        if (world.isClient) {
			startSoundInstance(user);
		}
		return ItemUsage.consumeHeldItem(world, user, hand);
	}
	
	@Environment(EnvType.CLIENT)
	public void startSoundInstance(PlayerEntity user) {
		MinecraftClient.getInstance().getSoundManager().play(new PipeBombChargingSoundInstance(user));
	}
	
	@Override
	public int getMaxUseTime(ItemStack stack, LivingEntity user) {
		return 55;
	}
	
	@Override
	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
		prime(stack, world, user.getPos(), user);
		return stack;
	}
	
	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		if (world instanceof ServerWorld serverWorld) {
			if (isPrimeTimeElapsed(world, stack)) {
				explode(stack, serverWorld, entity.getPos(), entity);
			}
		}
	}
	
	@Override
	public void onItemEntityTicked(ItemEntity itemEntity) {
		var stack = itemEntity.getStack();
		if (itemEntity.getWorld() instanceof ServerWorld world) {
			if (isPrimeTimeElapsed(world, stack))
				explode(stack, world, itemEntity.getEyePos(), null);
		}
	}
	
	@Override
	public void onItemEntityDamaged(DamageSource source, float amount, ItemEntity itemEntity) {
		if (itemEntity.getWorld() instanceof ServerWorld world) {
			if (source.isIn(DamageTypeTags.IS_FIRE) || source.isIn(DamageTypeTags.IS_EXPLOSION))
				explode(itemEntity.getStack(), world, itemEntity.getPos(), null);
		}
	}
	
	private void explode(ItemStack stack, ServerWorld world, Vec3d pos, @Nullable Entity target) {
		stack.decrement(1);
		Entity owner = tryGetOwner(stack, world);
		
		if (target != null)
			target.damage(SpectrumDamageTypes.incandescence(world, owner instanceof LivingEntity living ? living : null), 200F);
		world.createExplosion(null, SpectrumDamageTypes.incandescence(world), new ExplosionBehavior(), pos.getX(), pos.getY(), pos.getZ(), 7.5F, true, World.ExplosionSourceType.NONE);
	}
	
	public Entity tryGetOwner(ItemStack stack, ServerWorld world) {
		var profile = stack.get(DataComponentTypes.PROFILE);
		if (profile == null || profile.id().isEmpty())
			return null;
		return world.getEntity(profile.id().get());
	}
	
	public static void prime(ItemStack stack, World world, Vec3d pos, @Nullable Entity user) {
		world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SpectrumSoundEvents.INCANDESCENT_ARM, SoundCategory.PLAYERS, 2F, 0.9F);
		stack.set(SpectrumDataComponentTypes.PIPE_BOMB, world.getTime());
		if (user instanceof PlayerEntity player) {
			stack.set(DataComponentTypes.PROFILE, new ProfileComponent(player.getGameProfile()));
		}
	}
	
	public static boolean isPrimed(ItemStack stack) {
		return stack.get(SpectrumDataComponentTypes.PIPE_BOMB) != null;
	}
	
	public static boolean isPrimeTimeElapsed(World world, ItemStack stack) {
		Optional<Long> timestamp = getPrimeTime(stack);
		if (timestamp.isEmpty()) {
			return false;
		}
		return world.getTime() - timestamp.get() >= 100;
	}
	
	private static Optional<Long> getPrimeTime(ItemStack stack) {
		if (stack.contains(SpectrumDataComponentTypes.PIPE_BOMB)) {
			return Optional.of(stack.get(SpectrumDataComponentTypes.PIPE_BOMB));
		}
		return Optional.empty();
	}
	
	@Override
	public UseAction getUseAction(ItemStack stack) {
		return UseAction.BOW;
	}

	@Override
	public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
		super.appendTooltip(stack, context, tooltip, type);
		tooltip.add(Text.translatable("item.spectrum.pipe_bomb.tooltip").formatted(Formatting.GRAY));
		tooltip.add(Text.translatable("item.spectrum.pipe_bomb.tooltip2").formatted(Formatting.GRAY));
		tooltip.add(Text.translatable("item.spectrum.pipe_bomb.tooltip3").formatted(Formatting.GRAY));
	}
	
}
