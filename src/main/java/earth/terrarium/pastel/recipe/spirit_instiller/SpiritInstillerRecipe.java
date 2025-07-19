package earth.terrarium.pastel.recipe.spirit_instiller;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.api.block.MultiblockCrafter;
import earth.terrarium.pastel.api.block.PlayerOwned;
import earth.terrarium.pastel.api.recipe.IngredientStack;
import earth.terrarium.pastel.blocks.memory.MemoryItem;
import earth.terrarium.pastel.blocks.spirit_instiller.SpiritInstillerBlockEntity;
import earth.terrarium.pastel.blocks.upgrade.Upgradeable;
import earth.terrarium.pastel.helpers.data.PacketCodecHelper;
import earth.terrarium.pastel.helpers.Support;
import earth.terrarium.pastel.progression.PastelAdvancementCriteria;
import earth.terrarium.pastel.recipe.GatedStackPastelRecipe;
import earth.terrarium.pastel.recipe.InstanceRecipeInput;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.PastelItemTags;
import earth.terrarium.pastel.registries.PastelRecipeSerializers;
import earth.terrarium.pastel.registries.PastelRecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SpiritInstillerRecipe extends GatedStackPastelRecipe<InstanceRecipeInput<SpiritInstillerBlockEntity>> {
	
	public static final int CENTER = 0;
	public static final int FIRST = 1;
	public static final int SECOND = 2;
	public static final ResourceLocation UNLOCK_IDENTIFIER = PastelCommon.locate("midgame/build_spirit_instiller_structure");
	
	protected final IngredientStack centerIngredient;
	protected final IngredientStack bowlIngredient1;
	protected final IngredientStack bowlIngredient2;
	protected final ItemStack output;
	
	protected final int craftingTime;
	protected final float experience;
	protected final boolean noBenefitsFromYieldAndEfficiencyUpgrades;
	
	public SpiritInstillerRecipe(String group, boolean secret, Optional<ResourceLocation> requiredAdvancementIdentifier,
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
	public boolean matches(InstanceRecipeInput input, Level world) {
		List<IngredientStack> ing = getIngredientStacks();

		if (bowlMatches(input))
			return ing.getFirst().test(input.getItem(CENTER));

		return false;
	}

	protected boolean bowlMatches(InstanceRecipeInput<?> input) {
		var ing = getIngredientStacks();
		if (ing.get(FIRST).test(input.getItem(FIRST)) && ing.get(SECOND).test(input.getItem(SECOND)))
			return true;

		return ing.get(FIRST).test(input.getItem(SECOND)) && ing.get(SECOND).test(input.getItem(FIRST));
	}
	
	@Override
	public ItemStack getResultItem(HolderLookup.Provider registryManager) {
		return output;
	}
	
	@Override
	public RecipeSerializer<?> getSerializer() {
		return PastelRecipeSerializers.SPIRIT_INSTILLING_SERIALIZER;
	}
	
	@Override
	public List<IngredientStack> getIngredientStacks() {
		NonNullList<IngredientStack> defaultedList = NonNullList.create();
		defaultedList.add(this.centerIngredient);
		defaultedList.add(this.bowlIngredient1);
		defaultedList.add(this.bowlIngredient2);
		return defaultedList;
	}
	
	@Override
	public ItemStack assemble(InstanceRecipeInput<SpiritInstillerBlockEntity> recipeInput, HolderLookup.Provider drm) {
		SpiritInstillerBlockEntity spiritInstillerBlockEntity = recipeInput.getInstance();
		Upgradeable.UpgradeHolder upgradeHolder = spiritInstillerBlockEntity.getUpgradeHolder();
		Level world = spiritInstillerBlockEntity.getLevel();
		if (world == null) return ItemStack.EMPTY;
		BlockPos pos = spiritInstillerBlockEntity.getBlockPos();
		
		ItemStack resultStack = getResultItem(drm).copy();
		
		// Yield upgrade
		if (!areYieldAndEfficiencyUpgradesDisabled() && upgradeHolder.getEffectiveValue(Upgradeable.UpgradeType.YIELD) != 1.0) {
			int resultCountMod = Support.chanceRound(resultStack.getCount() * upgradeHolder.getEffectiveValue(Upgradeable.UpgradeType.YIELD), world.random);
			resultStack.setCount(resultCountMod);
		}
		
		if (resultStack.is(PastelBlocks.MEMORY.get().asItem())) {
			boolean makeUnrecognizable = spiritInstillerBlockEntity.getItem(0).is(PastelItemTags.MEMORY_BONDING_AGENTS_CONCEALABLE);
			if (makeUnrecognizable) {
				MemoryItem.makeUnrecognizable(resultStack);
			}
		}
		
		spawnXPAndGrantAdvancements(resultStack, spiritInstillerBlockEntity, upgradeHolder, world, pos);
		
		return resultStack;
	}
	
	// Calculate and spawn experience
	protected void spawnXPAndGrantAdvancements(ItemStack resultStack, SpiritInstillerBlockEntity spiritInstillerBlockEntity, Upgradeable.UpgradeHolder upgradeHolder, Level world, BlockPos pos) {
		int awardedExperience = 0;
		if (getExperience() > 0) {
			double experienceModifier = upgradeHolder.getEffectiveValue(Upgradeable.UpgradeType.EXPERIENCE);
			float recipeExperienceBeforeMod = getExperience();
			awardedExperience = Support.chanceRound(recipeExperienceBeforeMod * experienceModifier, world.random);
			MultiblockCrafter.spawnExperience(world, pos.above(), awardedExperience);
		}
		
		// Run Advancement trigger
		grantPlayerSpiritInstillingAdvancementCriterion(spiritInstillerBlockEntity.getOwnerUUID(), resultStack, awardedExperience);
	}
	
	protected static void grantPlayerSpiritInstillingAdvancementCriterion(UUID playerUUID, ItemStack resultStack, int experience) {
		ServerPlayer serverPlayerEntity = (ServerPlayer) PlayerOwned.getPlayerEntityIfOnline(playerUUID);
		if (serverPlayerEntity != null) {
			PastelAdvancementCriteria.SPIRIT_INSTILLER_CRAFTING.trigger(serverPlayerEntity, resultStack, experience);
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
	public ResourceLocation getRecipeTypeUnlockIdentifier() {
		return UNLOCK_IDENTIFIER;
	}
	
	@Override
	public String getRecipeTypeShortID() {
		return "spirit_instiller";
	}
	
	public boolean canCraftWithStacks(RecipeInput inventory, Level level) {
		return true;
	}
	
	@Override
	public ItemStack getToastSymbol() {
		return new ItemStack(PastelBlocks.SPIRIT_INSTILLER.get());
	}
	
	@Override
	public RecipeType<?> getType() {
		return PastelRecipeTypes.SPIRIT_INSTILLING;
	}
	
	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return width * height >= 3;
	}
	
	public static class Serializer implements RecipeSerializer<SpiritInstillerRecipe> {
		
		public static final MapCodec<SpiritInstillerRecipe> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
				Codec.STRING.optionalFieldOf("group", "").forGetter(recipe -> recipe.group),
				Codec.BOOL.optionalFieldOf("secret", false).forGetter(recipe -> recipe.secret),
				ResourceLocation.CODEC.optionalFieldOf("required_advancement").forGetter(recipe -> recipe.requiredAdvancementIdentifier),
				IngredientStack.CODEC.fieldOf("center_ingredient").forGetter(recipe -> recipe.centerIngredient),
				IngredientStack.CODEC.fieldOf("ingredient1").forGetter(recipe -> recipe.bowlIngredient1),
				IngredientStack.CODEC.fieldOf("ingredient2").forGetter(recipe -> recipe.bowlIngredient2),
				ItemStack.STRICT_CODEC.fieldOf("result").forGetter(recipe -> recipe.output),
				Codec.INT.optionalFieldOf("time", 200).forGetter(recipe -> recipe.craftingTime),
				Codec.FLOAT.optionalFieldOf("experience", 1.0f).forGetter(recipe -> recipe.experience),
				Codec.BOOL.optionalFieldOf("disable_yield_and_efficiency_upgrades", false).forGetter(recipe -> recipe.noBenefitsFromYieldAndEfficiencyUpgrades)
		).apply(i, SpiritInstillerRecipe::new));
		
		private static final StreamCodec<RegistryFriendlyByteBuf, SpiritInstillerRecipe> STREAM_CODEC = PacketCodecHelper.tuple(
				ByteBufCodecs.STRING_UTF8, c -> c.group,
				ByteBufCodecs.BOOL, c -> c.secret,
				ByteBufCodecs.optional(ResourceLocation.STREAM_CODEC), c -> c.requiredAdvancementIdentifier,
				IngredientStack.STREAM_CODEC, c -> c.centerIngredient,
				IngredientStack.STREAM_CODEC, c -> c.bowlIngredient1,
				IngredientStack.STREAM_CODEC, c -> c.bowlIngredient2,
				ItemStack.STREAM_CODEC, c -> c.output,
				ByteBufCodecs.VAR_INT, recipe -> recipe.craftingTime,
				ByteBufCodecs.FLOAT, recipe -> recipe.experience,
				ByteBufCodecs.BOOL, recipe -> recipe.noBenefitsFromYieldAndEfficiencyUpgrades,
				SpiritInstillerRecipe::new
		);
		
		@Override
		public MapCodec<SpiritInstillerRecipe> codec() {
			return CODEC;
		}
		
		@Override
		public StreamCodec<RegistryFriendlyByteBuf, SpiritInstillerRecipe> streamCodec() {
			return STREAM_CODEC;
		}
	}
	
}
