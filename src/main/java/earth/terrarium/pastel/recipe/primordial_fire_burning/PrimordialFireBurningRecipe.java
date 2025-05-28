package earth.terrarium.pastel.recipe.primordial_fire_burning;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.SpectrumCommon;
import earth.terrarium.pastel.entity.entity.FireproofItemEntity;
import earth.terrarium.pastel.recipe.GatedSpectrumRecipe;
import earth.terrarium.pastel.registries.SpectrumBlocks;
import earth.terrarium.pastel.registries.SpectrumRecipeSerializers;
import earth.terrarium.pastel.registries.SpectrumRecipeTypes;
import earth.terrarium.pastel.registries.SpectrumSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class PrimordialFireBurningRecipe extends GatedSpectrumRecipe<RecipeInput> {
	
	public static final ResourceLocation UNLOCK_IDENTIFIER = SpectrumCommon.locate("lategame/collect_doombloom_seed");
	
	protected final Ingredient input;
	protected final ItemStack output;
	
	public PrimordialFireBurningRecipe(String group, boolean secret, Optional<ResourceLocation> requiredAdvancementIdentifier, Ingredient input, ItemStack output) {
		super(group, secret, requiredAdvancementIdentifier);
		
		this.input = input;
		this.output = output;
		
		registerInToastManager(getType(), this);
	}
	
	@Override
	public boolean matches(RecipeInput inv, Level world) {
		return this.input.test(inv.getItem(0));
	}
	
	@Override
	public ItemStack assemble(RecipeInput inv, HolderLookup.Provider drm) {
		return this.output.copy();
	}
	
	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return true;
	}
	
	@Override
	public ItemStack getResultItem(HolderLookup.Provider registryManager) {
		return output;
	}
	
	@Override
	public ItemStack getToastSymbol() {
		return new ItemStack(SpectrumBlocks.DOOMBLOOM.get());
	}
	
	@Override
	public ResourceLocation getRecipeTypeUnlockIdentifier() {
		return UNLOCK_IDENTIFIER;
	}
	
	@Override
	public RecipeSerializer<?> getSerializer() {
		return SpectrumRecipeSerializers.PRIMORDIAL_FIRE_BURNING_RECIPE_SERIALIZER;
	}
	
	@Override
	public RecipeType<?> getType() {
		return SpectrumRecipeTypes.PRIMORDIAL_FIRE_BURNING;
	}
	
	@Override
	public NonNullList<Ingredient> getIngredients() {
		NonNullList<Ingredient> defaultedList = NonNullList.create();
		defaultedList.add(this.input);
		return defaultedList;
	}
	
	@Override
	public String getRecipeTypeShortID() {
		return "primordial_fire_burning";
	}
	
	public static PrimordialFireBurningRecipe getRecipeFor(@NotNull Level world, ItemStack stack) {
		return world.getRecipeManager().getRecipeFor(SpectrumRecipeTypes.PRIMORDIAL_FIRE_BURNING, new SingleRecipeInput(stack), world).map(RecipeHolder::value).orElse(null);
	}
	
	public static boolean processBlock(Level world, BlockPos pos, BlockState state) {
		Item item = state.getBlock().asItem();
		if (item == Items.AIR) {
			return false;
		}
		
		PrimordialFireBurningRecipe recipe = PrimordialFireBurningRecipe.getRecipeFor(world, item.getDefaultInstance());
		if (recipe == null) {
			return false;
		}
		
		ItemStack output = recipe.assemble(new SingleRecipeInput(state.getBlock().asItem().getDefaultInstance()), world.registryAccess());
		
		world.playSound(null, pos, SpectrumSoundEvents.PRIMORDIAL_FIRE_CRACKLE, SoundSource.BLOCKS, 0.7F, 1.0F);
		if (output.getItem() instanceof BlockItem blockItem) {
			world.setBlockAndUpdate(pos, blockItem.getBlock().defaultBlockState());
		} else {
			world.removeBlock(pos, false);
			FireproofItemEntity.scatter(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, output);
		}
		
		return true;
	}
	
	public static boolean processItemEntity(Level world, ItemEntity itemEntity) {
		Vec3 pos = itemEntity.position();
		
		ItemStack inputStack = itemEntity.getItem();
		PrimordialFireBurningRecipe recipe = PrimordialFireBurningRecipe.getRecipeFor(world, inputStack);
		if (recipe == null) {
			return false;
		}
		
		int inputCount = inputStack.getCount();
		ItemStack outputStack = recipe.assemble(new SingleRecipeInput(inputStack), world.registryAccess()).copy();
		outputStack.setCount(outputStack.getCount() * inputCount);
		
		inputStack.setCount(0);
		itemEntity.discard();
		
		FireproofItemEntity.scatter(world, pos.x(), pos.y(), pos.z(), outputStack);
		world.playSound(null, itemEntity.blockPosition(), SpectrumSoundEvents.PRIMORDIAL_FIRE_CRACKLE, SoundSource.BLOCKS, 0.7F, 1.0F);
		
		return true;
	}
	
	public static class Serializer implements RecipeSerializer<PrimordialFireBurningRecipe> {
		
		public static final MapCodec<PrimordialFireBurningRecipe> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
				Codec.STRING.optionalFieldOf("group", "").forGetter(recipe -> recipe.group),
				Codec.BOOL.optionalFieldOf("secret", false).forGetter(recipe -> recipe.secret),
				ResourceLocation.CODEC.optionalFieldOf("required_advancement").forGetter(recipe -> recipe.requiredAdvancementIdentifier),
				Ingredient.CODEC_NONEMPTY.fieldOf("ingredient").forGetter(recipe -> recipe.input),
				ItemStack.STRICT_CODEC.fieldOf("result").forGetter(recipe -> recipe.output)
		).apply(i, PrimordialFireBurningRecipe::new));
		
		private static final StreamCodec<RegistryFriendlyByteBuf, PrimordialFireBurningRecipe> STREAM_CODEC = StreamCodec.composite(
				ByteBufCodecs.STRING_UTF8, c -> c.group,
				ByteBufCodecs.BOOL, c -> c.secret,
				ByteBufCodecs.optional(ResourceLocation.STREAM_CODEC), c -> c.requiredAdvancementIdentifier,
				Ingredient.CONTENTS_STREAM_CODEC, c -> c.input,
				ItemStack.STREAM_CODEC, c -> c.output,
				PrimordialFireBurningRecipe::new
		);
		
		@Override
		public MapCodec<PrimordialFireBurningRecipe> codec() {
			return CODEC;
		}
		
		@Override
		public StreamCodec<RegistryFriendlyByteBuf, PrimordialFireBurningRecipe> streamCodec() {
			return STREAM_CODEC;
		}
		
	}
	
}
