package de.dafuqs.spectrum.recipe.spirit_instiller.dynamic.spawner_manipulation;

import de.dafuqs.spectrum.api.recipe.IngredientStack;
import de.dafuqs.spectrum.blocks.mob_head.SpectrumSkullBlock;
import de.dafuqs.spectrum.registries.SpectrumAdvancements;
import de.dafuqs.spectrum.registries.SpectrumEntityTypeTags;
import de.dafuqs.spectrum.registries.SpectrumItemTags;
import de.dafuqs.spectrum.registries.SpectrumItems;
import de.dafuqs.spectrum.registries.SpectrumRecipeSerializers;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.crafting.RecipeSerializer;

import java.util.Optional;

public class SpawnerCreatureChangeRecipe extends SpawnerChangeRecipe {
	
	public SpawnerCreatureChangeRecipe() {
		super(IngredientStack.ofTag(SpectrumItemTags.SKULLS), IngredientStack.ofItems(SpectrumItems.DOWNSTONE_FRAGMENTS.get(), 4), Optional.of(SpectrumAdvancements.SPAWNER_CREATURE_CHANGE));
	}
	
	@Override
	public RecipeSerializer<?> getSerializer() {
		return SpectrumRecipeSerializers.SPIRIT_INSTILLER_SPAWNER_CREATURE_CHANGE;
	}
	
	@Override
	public boolean canCraftWithBlockEntityTag(CustomData spawnerBlockEntityNbt, ItemStack firstBowlStack, ItemStack secondBowlStack) {
		Optional<EntityType<?>> entityType = SpectrumSkullBlock.getEntityTypeOfSkullStack(firstBowlStack);
		entityType = entityType.isEmpty() ? SpectrumSkullBlock.getEntityTypeOfSkullStack(secondBowlStack) : entityType;
		
		if (entityType.isEmpty()) {
			return false;
		}
		if (entityType.get().is(SpectrumEntityTypeTags.SPAWNER_MANIPULATION_BLACKLISTED)) {
			return false;
		}
		if (spawnerBlockEntityNbt == null) {
			return true;
		}
		
		if (spawnerBlockEntityNbt.contains("SpawnData")) {
			CompoundTag spawnData = spawnerBlockEntityNbt.copyTag().getCompound("SpawnData");
			if (spawnData.contains("entity")) {
				CompoundTag entity = spawnData.getCompound("entity");
				if (entity.contains("id")) {
					ResourceLocation entityTypeIdentifier = BuiltInRegistries.ENTITY_TYPE.getKey(entityType.get());
					return !entityTypeIdentifier.toString().equals(entity.getString("id"));
				}
			}
		}
		return true;
	}
	
	@Override
	public Component getOutputLoreText() {
		return Component.translatable("recipe.pastel.spawner.lore.changed_creature");
	}
	
	@Override
	public CompoundTag getSpawnerResultNbt(CompoundTag spawnerBlockEntityNbt, ItemStack firstBowlStack, ItemStack secondBowlStack) {
		Optional<EntityType<?>> entityType = SpectrumSkullBlock.getEntityTypeOfSkullStack(firstBowlStack);
		entityType = entityType.isEmpty() ? SpectrumSkullBlock.getEntityTypeOfSkullStack(secondBowlStack) : entityType;
		
		if (entityType.isEmpty()) {
			return spawnerBlockEntityNbt;
		}
		
		ResourceLocation entityTypeIdentifier = BuiltInRegistries.ENTITY_TYPE.getKey(entityType.get());
		
		// Default spawner tag:
		/* BlockEntityTag: {
			MaxNearbyEntities: 6s,
			RequiredPlayerRange: 16s,
			SpawnCount: 4s,
			SpawnData: {entity: {id: "minecraft:xxx"}},
			MaxSpawnDelay: 800s,
			SpawnRange: 4s,
			MinSpawnDelay: 200s,
			SpawnPotentials: []
		   }
		 */
		
		CompoundTag idCompound = new CompoundTag();
		idCompound.putString("id", entityTypeIdentifier.toString());
		CompoundTag entityCompound = new CompoundTag();
		entityCompound.put("entity", idCompound);
		spawnerBlockEntityNbt.put("SpawnData", entityCompound);
		
		if (spawnerBlockEntityNbt.contains("SpawnPotentials")) {
			spawnerBlockEntityNbt.remove("SpawnPotentials");
		}
		
		return spawnerBlockEntityNbt;
	}
	
}
