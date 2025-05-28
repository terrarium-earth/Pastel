package earth.terrarium.pastel.recipe.anvil_crushing;

import earth.terrarium.pastel.api.block.MultiblockCrafter;
import earth.terrarium.pastel.helpers.Support;
import earth.terrarium.pastel.networking.s2c_payloads.PlayParticleWithExactVelocityPayload;
import earth.terrarium.pastel.registries.SpectrumRecipeTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

public class AnvilCrusher {

	public static void crush(ItemEntity itemEntity, float damageAmount) {
		ItemStack thisItemStack = itemEntity.getItem();
		Level world = itemEntity.level();
		
		SingleRecipeInput inventory = new SingleRecipeInput(thisItemStack);
		
		Optional<RecipeHolder<AnvilCrushingRecipe>> optionalAnvilCrushingRecipe = world.getRecipeManager().getRecipeFor(SpectrumRecipeTypes.ANVIL_CRUSHING, inventory, world);
		if (optionalAnvilCrushingRecipe.isPresent()) {
			// Item can be crafted via anvil. Do anvil crafting
			AnvilCrushingRecipe recipe = optionalAnvilCrushingRecipe.get().value();
			
			int itemStackAmount = itemEntity.getItem().getCount();
			int crushingInputAmount = Math.min(itemStackAmount, (int) (recipe.getCrushedItemsPerPointOfDamage() * damageAmount));
			
			if (crushingInputAmount > 0) {
				Vec3 position = itemEntity.position();
				
				ItemStack crushingOutput = recipe.getResultItem(world.registryAccess()).copy();
				Vec3 pos = itemEntity.position();

				// Remove the input amount from the source stack
				// Or the source stack altogether if it would be empty
				int remainingItemStackAmount = itemStackAmount - crushingInputAmount;
				if (remainingItemStackAmount > 0) {
					thisItemStack.setCount(remainingItemStackAmount);
				} else {
					itemEntity.remove(Entity.RemovalReason.DISCARDED);
				}
				
				MultiblockCrafter.spawnItemStackAsEntitySplitViaMaxCount(world, pos, crushingOutput, crushingOutput.getCount() * crushingInputAmount, Vec3.ZERO, false, null);
				
				// Spawn XP depending on how much is crafted, but at least 1
				float craftingXPFloat = recipe.getExperience() * crushingInputAmount;
				int craftingXP = Support.getIntFromDecimalWithChance(craftingXPFloat, world.random);
				
				if (craftingXP > 0) {
					ExperienceOrb experienceOrbEntity = new ExperienceOrb(world, position.x, position.y, position.z, craftingXP);
					world.addFreshEntity(experienceOrbEntity);
				}
				
				// Play sound
				SoundEvent soundEvent = recipe.getSoundEvent();
				if (soundEvent != null) {
					float randomVolume = 1.0F + world.getRandom().nextFloat() * 0.2F;
					float randomPitch = 0.9F + world.getRandom().nextFloat() * 0.2F;
					world.playSound(null, position.x, position.y, position.z, soundEvent, SoundSource.PLAYERS, randomVolume, randomPitch);
				}
				
				PlayParticleWithExactVelocityPayload.playParticleWithExactVelocity((ServerLevel) world, position, recipe.getParticleEffect(), recipe.getParticleCount(), Vec3.ZERO);
			}
		}
	}
	
}
