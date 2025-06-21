package earth.terrarium.pastel.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import earth.terrarium.pastel.api.interaction.ResonanceProcessor;
import earth.terrarium.pastel.helpers.enchantments.ExuberanceHelper;
import earth.terrarium.pastel.helpers.enchantments.FoundryHelper;
import earth.terrarium.pastel.registries.PastelEnchantmentTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(Block.class)
public abstract class BlockMixin {
	
	@Unique
	@Nullable Player breakingPlayer;
	
	@ModifyReturnValue(method = "getDrops(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/entity/BlockEntity;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/item/ItemStack;)Ljava/util/List;", at = @At("RETURN"))
	private static List<ItemStack> getDroppedStacks(List<ItemStack> original, BlockState state, ServerLevel world, BlockPos pos, BlockEntity blockEntity, Entity entity, ItemStack stack) {
		List<ItemStack> droppedStacks = original;
		
		// Voiding curse: no drops
		if (EnchantmentHelper.hasTag(stack, PastelEnchantmentTags.NO_BLOCK_DROPS)) {
			world.sendParticles(ParticleTypes.SMOKE, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 10, 0.5, 0.5, 0.5, 0.05);
			droppedStacks.clear();
			return droppedStacks;
		}
		
		// Resonance: drop self or modify drops for some items
		if (EnchantmentHelper.hasTag(stack, PastelEnchantmentTags.RESONANT_BLOCK_DROPS)) {
			ResonanceProcessor.applyResonance(world.registryAccess(), state, blockEntity, droppedStacks);
		}
		
		if (!droppedStacks.isEmpty()) {
			// Foundry enchant: try smelting recipe for each stack
			if (EnchantmentHelper.hasTag(stack, PastelEnchantmentTags.SMELTS_MORE_LOOT)) {
				droppedStacks = FoundryHelper.applyFoundry(world, droppedStacks);
			}
			
			// Inventory insertion enchant: add it to the player's inventory if there is room
			if (EnchantmentHelper.hasTag(stack, PastelEnchantmentTags.INVENTORY_INSERTION_EFFECT)) {
				List<ItemStack> leftoverReturnStacks = new ArrayList<>();
				
				if (entity instanceof Player playerEntity) {
					boolean anyAdded = false;
					for (ItemStack itemStack : droppedStacks) {
						Item item = itemStack.getItem();
						int count = itemStack.getCount();
						
						if (playerEntity.getInventory().add(itemStack)) {
							anyAdded = true;
							if (itemStack.isEmpty()) {
								itemStack.setCount(count);
							}
							playerEntity.awardStat(Stats.ITEM_PICKED_UP.get(item), count);
						} else {
							leftoverReturnStacks.add(itemStack);
						}
					}
					if (anyAdded) {
						playerEntity.level().playSound(null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(),
								SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS,
								0.2F, ((playerEntity.getRandom().nextFloat() - playerEntity.getRandom().nextFloat()) * 0.7F + 1.0F) * 2.0F);
					}
				}
				droppedStacks = leftoverReturnStacks;
			}
		}
		
		return droppedStacks;
	}
	
	@ModifyArg(method = "popExperience", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ExperienceOrb;award(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/phys/Vec3;I)V"), index = 2)
	private int applyExuberance(int originalXP) {
		if (breakingPlayer == null) {
			return originalXP;
		}
		return (int) (originalXP * ExuberanceHelper.getExuberanceMod(breakingPlayer));
	}
	
	@Inject(method = "playerDestroy", at = @At("HEAD"))
	public void afterBreak(Level world, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack, CallbackInfo callbackInfo) {
		breakingPlayer = player;
	}
	
}
