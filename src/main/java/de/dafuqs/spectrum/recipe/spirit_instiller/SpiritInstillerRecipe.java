package de.dafuqs.spectrum.recipe.spirit_instiller;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.api.block.*;
import de.dafuqs.spectrum.api.recipe.*;
import de.dafuqs.spectrum.blocks.memory.*;
import de.dafuqs.spectrum.blocks.spirit_instiller.*;
import de.dafuqs.spectrum.blocks.upgrade.*;
import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.progression.*;
import de.dafuqs.spectrum.recipe.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.item.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.recipe.*;
import net.minecraft.recipe.input.*;
import net.minecraft.registry.*;
import net.minecraft.server.network.*;
import net.minecraft.util.*;
import net.minecraft.util.collection.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

import java.util.*;

public class SpiritInstillerRecipe extends GatedStackSpectrumRecipe<InstanceRecipeInput<SpiritInstillerBlockEntity>> {
	
	public static final int CENTER_INGREDIENT = 0;
	public static final int FIRST_INGREDIENT = 1;
	public static final int SECOND_INGREDIENT = 2;
	public static final Identifier UNLOCK_IDENTIFIER = SpectrumCommon.locate("midgame/build_spirit_instiller_structure");
	
	protected final IngredientStack centerIngredient;
	protected final IngredientStack bowlIngredient1;
	protected final IngredientStack bowlIngredient2;
	protected final ItemStack output;
	
	protected final int craftingTime;
	protected final float experience;
	protected final boolean noBenefitsFromYieldAndEfficiencyUpgrades;
	
	public SpiritInstillerRecipe(String group, boolean secret, Optional<Identifier> requiredAdvancementIdentifier,
								 IngredientStack centerIngredient, IngredientStack bowlIngredient1, IngredientStack bowlIngredient2, ItemStack output, int craftingTime, float experience, boolean noBenefitsFromYieldAndEfficiencyUpgrades) {
		
		super(group, secret, requiredAdvancementIdentifier);
		
		this.centerIngredient = centerIngredient;
		this.bowlIngredient1 = bowlIngredient1;
		this.bowlIngredient2 = bowlIngredient2;
		this.output = output;
		this.craftingTime = craftingTime;
		this.experience = experience;
		this.noBenefitsFromYieldAndEfficiencyUpgrades = noBenefitsFromYieldAndEfficiencyUpgrades;
		
		registerInToastManager(getType(), this);
	}
	
	@Override
	public boolean matches(InstanceRecipeInput recipeInput, World world) {
		List<IngredientStack> ingredientStacks = getIngredientStacks();
		if (recipeInput.getSize() > 2) {
			if (ingredientStacks.get(CENTER_INGREDIENT).test(recipeInput.getStackInSlot(CENTER_INGREDIENT))) {
				if (ingredientStacks.get(FIRST_INGREDIENT).test(recipeInput.getStackInSlot(FIRST_INGREDIENT))) {
					return ingredientStacks.get(SECOND_INGREDIENT).test(recipeInput.getStackInSlot(SECOND_INGREDIENT));
				} else if (ingredientStacks.get(FIRST_INGREDIENT).test(recipeInput.getStackInSlot(SECOND_INGREDIENT))) {
					return ingredientStacks.get(SECOND_INGREDIENT).test(recipeInput.getStackInSlot(FIRST_INGREDIENT));
				}
			}
		}
		return false;
	}
	
	@Override
	public ItemStack getResult(RegistryWrapper.WrapperLookup registryManager) {
		return output;
	}
	
	@Override
	public RecipeSerializer<?> getSerializer() {
		return SpectrumRecipeSerializers.SPIRIT_INSTILLING_SERIALIZER;
	}
	
	@Override
	public List<IngredientStack> getIngredientStacks() {
		DefaultedList<IngredientStack> defaultedList = DefaultedList.of();
		defaultedList.add(this.centerIngredient);
		defaultedList.add(this.bowlIngredient1);
		defaultedList.add(this.bowlIngredient2);
		return defaultedList;
	}
	
	@Override
	public ItemStack craft(InstanceRecipeInput<SpiritInstillerBlockEntity> recipeInput, RegistryWrapper.WrapperLookup drm) {
		ItemStack resultStack = ItemStack.EMPTY;
		SpiritInstillerBlockEntity spiritInstillerBlockEntity = recipeInput.getInstance();
		Upgradeable.UpgradeHolder upgradeHolder = spiritInstillerBlockEntity.getUpgradeHolder();
		World world = spiritInstillerBlockEntity.getWorld();
		if (world == null) return ItemStack.EMPTY;
		BlockPos pos = spiritInstillerBlockEntity.getPos();
		
		resultStack = getResult(drm).copy();
		
		// Yield upgrade
		if (!areYieldAndEfficiencyUpgradesDisabled() && upgradeHolder.getEffectiveValue(Upgradeable.UpgradeType.YIELD) != 1.0) {
			int resultCountMod = Support.getIntFromDecimalWithChance(resultStack.getCount() * upgradeHolder.getEffectiveValue(Upgradeable.UpgradeType.YIELD), world.random);
			resultStack.setCount(resultCountMod);
		}
		
		if (resultStack.isOf(SpectrumBlocks.MEMORY.asItem())) {
			boolean makeUnrecognizable = spiritInstillerBlockEntity.getStack(0).isIn(SpectrumItemTags.MEMORY_BONDING_AGENTS_CONCEALABLE);
			if (makeUnrecognizable) {
				MemoryItem.makeUnrecognizable(resultStack);
			}
		}
		
		spawnXPAndGrantAdvancements(resultStack, spiritInstillerBlockEntity, upgradeHolder, world, pos);
		
		return resultStack;
	}
	
	// Calculate and spawn experience
	protected void spawnXPAndGrantAdvancements(ItemStack resultStack, SpiritInstillerBlockEntity spiritInstillerBlockEntity, Upgradeable.UpgradeHolder upgradeHolder, World world, BlockPos pos) {
		int awardedExperience = 0;
		if (getExperience() > 0) {
			double experienceModifier = upgradeHolder.getEffectiveValue(Upgradeable.UpgradeType.EXPERIENCE);
			float recipeExperienceBeforeMod = getExperience();
			awardedExperience = Support.getIntFromDecimalWithChance(recipeExperienceBeforeMod * experienceModifier, world.random);
			MultiblockCrafter.spawnExperience(world, pos.up(), awardedExperience);
		}
		
		// Run Advancement trigger
		grantPlayerSpiritInstillingAdvancementCriterion(spiritInstillerBlockEntity.getOwnerUUID(), resultStack, awardedExperience);
	}
	
	protected static void grantPlayerSpiritInstillingAdvancementCriterion(UUID playerUUID, ItemStack resultStack, int experience) {
		ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) PlayerOwned.getPlayerEntityIfOnline(playerUUID);
		if (serverPlayerEntity != null) {
			SpectrumAdvancementCriteria.SPIRIT_INSTILLER_CRAFTING.trigger(serverPlayerEntity, resultStack, experience);
		}
	}
	
	public float getExperience() {
		return experience;
	}
	
	public int getCraftingTime() {
		return craftingTime;
	}
	
	public boolean areYieldAndEfficiencyUpgradesDisabled() {
		return noBenefitsFromYieldAndEfficiencyUpgrades;
	}
	
	@Override
	public Identifier getRecipeTypeUnlockIdentifier() {
		return UNLOCK_IDENTIFIER;
	}
	
	@Override
	public String getRecipeTypeShortID() {
		return "spirit_instiller";
	}
	
	public boolean canCraftWithStacks(RecipeInput inventory) {
		return true;
	}
	
	@Override
	public ItemStack createIcon() {
		return new ItemStack(SpectrumBlocks.SPIRIT_INSTILLER);
	}
	
	@Override
	public RecipeType<?> getType() {
		return SpectrumRecipeTypes.SPIRIT_INSTILLING;
	}
	
	@Override
	public boolean fits(int width, int height) {
		return width * height >= 3;
	}
	
	public static class Serializer implements RecipeSerializer<SpiritInstillerRecipe> {
		
		public static final MapCodec<SpiritInstillerRecipe> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
				Codec.STRING.optionalFieldOf("group", "").forGetter(recipe -> recipe.group),
				Codec.BOOL.optionalFieldOf("secret", false).forGetter(recipe -> recipe.secret),
				Identifier.CODEC.optionalFieldOf("required_advancement").forGetter(recipe -> recipe.requiredAdvancementIdentifier),
				IngredientStack.Serializer.CODEC.fieldOf("center_ingredient").forGetter(recipe -> recipe.centerIngredient),
				IngredientStack.Serializer.CODEC.fieldOf("ingredient1").forGetter(recipe -> recipe.bowlIngredient1),
				IngredientStack.Serializer.CODEC.fieldOf("ingredient2").forGetter(recipe -> recipe.bowlIngredient2),
				ItemStack.VALIDATED_CODEC.fieldOf("result").forGetter(recipe -> recipe.output),
				Codec.INT.optionalFieldOf("time", 200).forGetter(recipe -> recipe.craftingTime),
				Codec.FLOAT.optionalFieldOf("experience", 1.0f).forGetter(recipe -> recipe.experience),
				Codec.BOOL.optionalFieldOf("disable_yield_and_efficiency_upgrades", false).forGetter(recipe -> recipe.noBenefitsFromYieldAndEfficiencyUpgrades)
		).apply(i, SpiritInstillerRecipe::new));
		
		private static final PacketCodec<RegistryByteBuf, SpiritInstillerRecipe> PACKET_CODEC = PacketCodecHelper.tuple(
				PacketCodecs.STRING, c -> c.group,
				PacketCodecs.BOOL, c -> c.secret,
				PacketCodecs.optional(Identifier.PACKET_CODEC), c -> c.requiredAdvancementIdentifier,
				IngredientStack.Serializer.PACKET_CODEC, c -> c.centerIngredient,
				IngredientStack.Serializer.PACKET_CODEC, c -> c.bowlIngredient1,
				IngredientStack.Serializer.PACKET_CODEC, c -> c.bowlIngredient2,
				ItemStack.PACKET_CODEC, c -> c.output,
				PacketCodecs.VAR_INT, recipe -> recipe.craftingTime,
				PacketCodecs.FLOAT, recipe -> recipe.experience,
				PacketCodecs.BOOL, recipe -> recipe.noBenefitsFromYieldAndEfficiencyUpgrades,
				SpiritInstillerRecipe::new
		);
		
		@Override
		public MapCodec<SpiritInstillerRecipe> codec() {
			return CODEC;
		}
		
		@Override
		public PacketCodec<RegistryByteBuf, SpiritInstillerRecipe> packetCodec() {
			return PACKET_CODEC;
		}
	}
	
}
