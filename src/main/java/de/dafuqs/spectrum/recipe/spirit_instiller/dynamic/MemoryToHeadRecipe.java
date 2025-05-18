package de.dafuqs.spectrum.recipe.spirit_instiller.dynamic;

import de.dafuqs.spectrum.SpectrumCommon;
import de.dafuqs.spectrum.api.recipe.IngredientStack;
import de.dafuqs.spectrum.blocks.memory.MemoryBlockEntity;
import de.dafuqs.spectrum.blocks.memory.MemoryItem;
import de.dafuqs.spectrum.blocks.mob_head.SpectrumSkullBlock;
import de.dafuqs.spectrum.blocks.spirit_instiller.SpiritInstillerBlockEntity;
import de.dafuqs.spectrum.recipe.InstanceRecipeInput;
import de.dafuqs.spectrum.recipe.spirit_instiller.SpiritInstillerRecipe;
import de.dafuqs.spectrum.registries.SpectrumBlocks;
import de.dafuqs.spectrum.registries.SpectrumDamageTypes;
import de.dafuqs.spectrum.registries.SpectrumItemTags;
import de.dafuqs.spectrum.registries.SpectrumItems;
import de.dafuqs.spectrum.registries.SpectrumRecipeSerializers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Optional;

public class MemoryToHeadRecipe extends SpiritInstillerRecipe {
	
	public MemoryToHeadRecipe() {
		super("", false, Optional.of(SpectrumCommon.locate("unlocks/memory_to_head")),
				IngredientStack.ofItems(SpectrumBlocks.MEMORY.asItem()), IngredientStack.ofItems(SpectrumItems.VEGETAL, 4), IngredientStack.ofItems(SpectrumItems.QUITOXIC_POWDER, 4),
				new ItemStack(Blocks.ZOMBIE_HEAD), 200, 1, true);
	}
	
	@Override
	public RecipeSerializer<?> getSerializer() {
		return SpectrumRecipeSerializers.SPIRIT_INSTILLER_MEMORY_TO_HEAD;
	}
	
	@Override
	public ItemStack assemble(InstanceRecipeInput<SpiritInstillerBlockEntity> recipeInput, HolderLookup.Provider drm) {
		SpiritInstillerBlockEntity spiritInstillerBlockEntity = recipeInput.getInstance();
		ItemStack resultStack = ItemStack.EMPTY;
		ServerLevel world = (ServerLevel) spiritInstillerBlockEntity.getLevel();
		BlockPos pos = spiritInstillerBlockEntity.getBlockPos();
		
		/*
		 * This is moderately cursed
		 * we spawn the entity from the memory, process its loot table with a custom damage type that guarantees a head drop,
		 * search for a head drop in that loot and then discard that entity.
		 * WHY might you ask?
		 * Good question!
		 * A single entity type can have multiple head items associated with it (like fox or shulker variants)
		 * and finding out which exact mob variant is in that memory would be even more cursed
		 */
		Optional<Entity> optionalEntity = MemoryBlockEntity.hatchEntity(world, pos, spiritInstillerBlockEntity.getItem(0));
		if (optionalEntity.isPresent()) {
			if (optionalEntity.get() instanceof LivingEntity livingEntity && world != null) {
				LootTable lootTable = world.getServer().reloadableRegistries().getLootTable(livingEntity.getLootTable());
				
				LootParams.Builder builder = new LootParams.Builder(world)
						.withParameter(LootContextParams.THIS_ENTITY, livingEntity)
						.withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(pos))
						.withParameter(LootContextParams.DAMAGE_SOURCE, SpectrumDamageTypes.mobHeadDrop(world)
						);
				
				LootParams lootContextParameterSet = builder.create(LootContextParamSets.ENTITY);
				List<ItemStack> loot = lootTable.getRandomItems(lootContextParameterSet, livingEntity.getLootTableSeed());
				
				for (ItemStack stack : loot) {
					if (stack.is(SpectrumItemTags.SKULLS)) {
						resultStack = stack;
						break;
					}
				}
			}
			optionalEntity.get().discard();
		}
		
		spawnXPAndGrantAdvancements(resultStack, spiritInstillerBlockEntity, spiritInstillerBlockEntity.getUpgradeHolder(), world, pos);
		return resultStack;
	}
	
	@Override
	public boolean canCraftWithStacks(RecipeInput inventory) {
		ItemStack instillerStack = inventory.getItem(0);
		return getSkullTypeForMemory(instillerStack).isPresent();
	}
	
	private static Optional<SkullBlock.Type> getSkullTypeForMemory(ItemStack instillerStack) {
		if (!(instillerStack.getItem() instanceof MemoryItem)) {
			return Optional.empty();
		}
		
		Optional<EntityType<?>> optionalMemoryEntity = MemoryItem.getEntityType(instillerStack);
		return optionalMemoryEntity.flatMap(SpectrumSkullBlock::getSkullType);
	}
	
}