package de.dafuqs.spectrum.recipe.primordial_fire_burning;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.entity.entity.*;
import de.dafuqs.spectrum.recipe.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.recipe.*;
import net.minecraft.recipe.input.*;
import net.minecraft.registry.*;
import net.minecraft.sound.*;
import net.minecraft.util.*;
import net.minecraft.util.collection.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class PrimordialFireBurningRecipe extends GatedSpectrumRecipe<RecipeInput> {
	
	public static final Identifier UNLOCK_IDENTIFIER = SpectrumCommon.locate("lategame/collect_doombloom_seed");
	
	protected final Ingredient input;
	protected final ItemStack output;
	
	public PrimordialFireBurningRecipe(String group, boolean secret, Optional<Identifier> requiredAdvancementIdentifier, Ingredient input, ItemStack output) {
		super(group, secret, requiredAdvancementIdentifier);
		
		this.input = input;
		this.output = output;
		
		registerInToastManager(getType(), this);
	}
	
	@Override
	public boolean matches(RecipeInput inv, World world) {
		return this.input.test(inv.getStackInSlot(0));
	}
	
	@Override
	public ItemStack craft(RecipeInput inv, RegistryWrapper.WrapperLookup drm) {
		return this.output.copy();
	}
	
	@Override
	public boolean fits(int width, int height) {
		return true;
	}
	
	@Override
	public ItemStack getResult(RegistryWrapper.WrapperLookup registryManager) {
		return output;
	}
	
	@Override
	public ItemStack createIcon() {
		return new ItemStack(SpectrumBlocks.DOOMBLOOM);
	}
	
	@Override
	public Identifier getRecipeTypeUnlockIdentifier() {
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
	public DefaultedList<Ingredient> getIngredients() {
		DefaultedList<Ingredient> defaultedList = DefaultedList.of();
		defaultedList.add(this.input);
		return defaultedList;
	}
	
	@Override
	public String getRecipeTypeShortID() {
		return "primordial_fire_burning";
	}
	
	public static PrimordialFireBurningRecipe getRecipeFor(@NotNull World world, ItemStack stack) {
		return world.getRecipeManager().getFirstMatch(SpectrumRecipeTypes.PRIMORDIAL_FIRE_BURNING, new SingleStackRecipeInput(stack), world).map(RecipeEntry::value).orElse(null);
	}
	
	public static boolean processBlock(World world, BlockPos pos, BlockState state) {
		Item item = state.getBlock().asItem();
		if (item == Items.AIR) {
			return false;
		}
		
		PrimordialFireBurningRecipe recipe = PrimordialFireBurningRecipe.getRecipeFor(world, item.getDefaultStack());
		if (recipe == null) {
			return false;
		}
		
		ItemStack output = recipe.craft(new SingleStackRecipeInput(state.getBlock().asItem().getDefaultStack()), world.getRegistryManager());
		
		world.playSound(null, pos, SpectrumSoundEvents.PRIMORDIAL_FIRE_CRACKLE, SoundCategory.BLOCKS, 0.7F, 1.0F);
		if (output.getItem() instanceof BlockItem blockItem) {
			world.setBlockState(pos, blockItem.getBlock().getDefaultState());
		} else {
			world.removeBlock(pos, false);
			FireproofItemEntity.scatter(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, output);
		}
		
		return true;
	}
	
	public static boolean processItemEntity(World world, ItemEntity itemEntity) {
		Vec3d pos = itemEntity.getPos();
		
		ItemStack inputStack = itemEntity.getStack();
		PrimordialFireBurningRecipe recipe = PrimordialFireBurningRecipe.getRecipeFor(world, inputStack);
		if (recipe == null) {
			return false;
		}
		
		int inputCount = inputStack.getCount();
		ItemStack outputStack = recipe.craft(new SingleStackRecipeInput(inputStack), world.getRegistryManager()).copy();
		outputStack.setCount(outputStack.getCount() * inputCount);
		
		inputStack.setCount(0);
		itemEntity.discard();
		
		FireproofItemEntity.scatter(world, pos.getX(), pos.getY(), pos.getZ(), outputStack);
		world.playSound(null, itemEntity.getBlockPos(), SpectrumSoundEvents.PRIMORDIAL_FIRE_CRACKLE, SoundCategory.BLOCKS, 0.7F, 1.0F);
		
		return true;
	}
	
	public static class Serializer implements RecipeSerializer<PrimordialFireBurningRecipe> {
		
		public static final MapCodec<PrimordialFireBurningRecipe> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
				Codec.STRING.optionalFieldOf("group", "").forGetter(recipe -> recipe.group),
				Codec.BOOL.optionalFieldOf("secret", false).forGetter(recipe -> recipe.secret),
				Identifier.CODEC.optionalFieldOf("required_advancement").forGetter(recipe -> recipe.requiredAdvancementIdentifier),
				Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("ingredient").forGetter(recipe -> recipe.input),
				ItemStack.VALIDATED_CODEC.fieldOf("result").forGetter(recipe -> recipe.output)
		).apply(i, PrimordialFireBurningRecipe::new));
		
		private static final PacketCodec<RegistryByteBuf, PrimordialFireBurningRecipe> PACKET_CODEC = PacketCodec.tuple(
				PacketCodecs.STRING, c -> c.group,
				PacketCodecs.BOOL, c -> c.secret,
				PacketCodecs.optional(Identifier.PACKET_CODEC), c -> c.requiredAdvancementIdentifier,
				Ingredient.PACKET_CODEC, c -> c.input,
				ItemStack.PACKET_CODEC, c -> c.output,
				PrimordialFireBurningRecipe::new
		);
		
		@Override
		public MapCodec<PrimordialFireBurningRecipe> codec() {
			return CODEC;
		}
		
		@Override
		public PacketCodec<RegistryByteBuf, PrimordialFireBurningRecipe> packetCodec() {
			return PACKET_CODEC;
		}
		
	}
	
}
