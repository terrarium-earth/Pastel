package de.dafuqs.spectrum.recipe.spirit_instiller.dynamic.spawner_manipulation;

import de.dafuqs.spectrum.api.recipe.*;
import de.dafuqs.spectrum.blocks.item_bowl.*;
import de.dafuqs.spectrum.blocks.spirit_instiller.*;
import de.dafuqs.spectrum.recipe.*;
import de.dafuqs.spectrum.recipe.spirit_instiller.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.block.entity.*;
import net.minecraft.component.*;
import net.minecraft.component.type.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.recipe.input.*;
import net.minecraft.registry.*;
import net.minecraft.text.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;

import java.util.*;

public abstract class SpawnerChangeRecipe extends SpiritInstillerRecipe {
	
	public SpawnerChangeRecipe(IngredientStack ingredient, IngredientStack ingredient2, Optional<Identifier> requiredAdvancementIdentifier) {
		super("spawner_manipulation", false, requiredAdvancementIdentifier,
				IngredientStack.ofItems(Items.SPAWNER), ingredient, ingredient2,
				Items.SPAWNER.getDefaultStack(), 200, 0, true);
	}
	
	public SpawnerChangeRecipe(IngredientStack ingredient) {
		super("spawner_manipulation", false, Optional.of(SpectrumAdvancements.SPAWNER_MANIPULATION),
				IngredientStack.ofItems(Items.SPAWNER), ingredient, IngredientStack.ofItems(SpectrumItems.VEGETAL, 4),
				Items.SPAWNER.getDefaultStack(), 200, 0, true);
	}
	
	@Override
	public ItemStack craft(InstanceRecipeInput<SpiritInstillerBlockEntity> recipeInput, RegistryWrapper.WrapperLookup drm) {
		SpiritInstillerBlockEntity spiritInstillerBlockEntity = recipeInput.getInstance();
		ItemStack resultStack = ItemStack.EMPTY;
		var world = spiritInstillerBlockEntity.getWorld();
		if (world == null) return ItemStack.EMPTY;
		BlockEntity leftBowlBlockEntity = world.getBlockEntity(SpiritInstillerBlockEntity.getItemBowlPos(spiritInstillerBlockEntity, false));
		BlockEntity rightBowlBlockEntity = world.getBlockEntity(SpiritInstillerBlockEntity.getItemBowlPos(spiritInstillerBlockEntity, true));
		if (leftBowlBlockEntity instanceof ItemBowlBlockEntity leftBowl && rightBowlBlockEntity instanceof ItemBowlBlockEntity rightBowl) {
			BlockPos pos = spiritInstillerBlockEntity.getPos();
			
			ItemStack firstBowlStack = leftBowl.getStack(0);
			ItemStack secondBowlStack = rightBowl.getStack(0);
			ItemStack spawnerStack = spiritInstillerBlockEntity.getStack(0);
			
			// TODO - Review
			NbtComponent spawnerNbt = spawnerStack.getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT);
			
			NbtCompound blockEntityTag;
			if (spawnerNbt.contains("BlockEntityTag")) {
				blockEntityTag = spawnerNbt.copyNbt().getCompound("BlockEntityTag");
			} else {
				blockEntityTag = new NbtCompound();
			}
			
			blockEntityTag = getSpawnerResultNbt(blockEntityTag, firstBowlStack, secondBowlStack);
			
			resultStack = spawnerStack.copy();
			resultStack.setCount(1);
			
			resultStack.set(DataComponentTypes.CUSTOM_DATA, NbtComponent.of(blockEntityTag));
			
			spawnXPAndGrantAdvancements(resultStack, spiritInstillerBlockEntity, spiritInstillerBlockEntity.getUpgradeHolder(), world, pos);
		}
		return resultStack;
	}
	
	@Override
	public boolean canCraftWithStacks(RecipeInput inventory) {
		NbtComponent blockEntityComponent = inventory.getStackInSlot(0).getOrDefault(DataComponentTypes.BLOCK_ENTITY_DATA, NbtComponent.DEFAULT);
		return canCraftWithBlockEntityTag(blockEntityComponent, inventory.getStackInSlot(1), inventory.getStackInSlot(2));
	}
	
	public abstract boolean canCraftWithBlockEntityTag(NbtComponent spawnerBlockEntityNbt, ItemStack leftBowlStack, ItemStack rightBowlStack);
	
	public abstract NbtCompound getSpawnerResultNbt(NbtCompound spawnerBlockEntityNbt, ItemStack secondBowlStack, ItemStack centerStack);
	
	public abstract Text getOutputLoreText();
	
}
