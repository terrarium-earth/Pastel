package de.dafuqs.spectrum.recipe.anvil_crushing;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.recipe.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.particle.*;
import net.minecraft.recipe.*;
import net.minecraft.recipe.input.*;
import net.minecraft.registry.*;
import net.minecraft.sound.*;
import net.minecraft.util.*;
import net.minecraft.util.collection.*;
import net.minecraft.world.*;

import java.util.*;

public class AnvilCrushingRecipe extends GatedSpectrumRecipe<SingleStackRecipeInput> {
	
	protected final Ingredient ingredient;
	protected final ItemStack result;
	protected final float crushedItemsPerPointOfDamage;
	protected final float experience;
	protected final Optional<Identifier> particleEffectIdentifier;
	protected final int particleCount;
	protected final Identifier soundEvent;
	
	public AnvilCrushingRecipe(String group, boolean secret, Optional<Identifier> requiredAdvancementIdentifier,
							   Ingredient ingredient, ItemStack result, float crushedItemsPerPointOfDamage,
							   float experience, Optional<Identifier> particleEffectIdentifier, int particleCount, Identifier soundEventIdentifier) {
		
		super(group, secret, requiredAdvancementIdentifier);
		
		this.ingredient = ingredient;
		this.result = result;
		this.crushedItemsPerPointOfDamage = crushedItemsPerPointOfDamage;
		this.experience = experience;
		this.particleEffectIdentifier = particleEffectIdentifier;
		this.particleCount = particleCount;
		this.soundEvent = soundEventIdentifier;
		
		if (requiredAdvancementIdentifier.isPresent()) {
			registerInToastManager(getType(), this);
		}
	}
	
	@Override
	public boolean matches(SingleStackRecipeInput input, World world) {
		return ingredient.test(input.item());
	}
	
	@Override
	public ItemStack craft(SingleStackRecipeInput input, RegistryWrapper.WrapperLookup registryLookup) {
		return result.copy();
	}
	
	@Override
	public boolean fits(int width, int height) {
		return true;
	}
	
	@Override
	public ItemStack getResult(RegistryWrapper.WrapperLookup registriesLookup) {
		return result;
	}
	
	@Override
	public ItemStack createIcon() {
		return new ItemStack(Blocks.ANVIL);
	}
	
	@Override
	public RecipeSerializer<?> getSerializer() {
		return SpectrumRecipeSerializers.ANVIL_CRUSHING_RECIPE_SERIALIZER;
	}
	
	@Override
	public RecipeType<?> getType() {
		return SpectrumRecipeTypes.ANVIL_CRUSHING;
	}
	
	@Override
	public String getRecipeTypeShortID() {
		return "anvil_crushing";
	}
	
	@Override
	public DefaultedList<Ingredient> getIngredients() {
		DefaultedList<Ingredient> defaultedList = DefaultedList.of();
		defaultedList.add(this.ingredient);
		return defaultedList;
	}
	
	public float getCrushedItemsPerPointOfDamage() {
		return crushedItemsPerPointOfDamage;
	}
	
	public SoundEvent getSoundEvent() {
		return Registries.SOUND_EVENT.get(soundEvent);
	}
	
	public ParticleEffect getParticleEffect() {
		return (ParticleEffect) Registries.PARTICLE_TYPE.get(particleEffectIdentifier.orElse(null));
	}
	
	public int getParticleCount() {
		return particleCount;
	}
	
	public float getExperience() {
		return experience;
	}
	
	public static class Serializer implements RecipeSerializer<AnvilCrushingRecipe> {
		
		private static final MapCodec<AnvilCrushingRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
				Codec.STRING.optionalFieldOf("group", "").forGetter(recipe -> recipe.group),
				Codec.BOOL.optionalFieldOf("secret", false).forGetter(recipe -> recipe.secret),
				Identifier.CODEC.optionalFieldOf("required_advancement").forGetter(recipe -> recipe.requiredAdvancementIdentifier),
				Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("ingredient").forGetter(recipe -> recipe.ingredient),
				ItemStack.VALIDATED_CODEC.fieldOf("result").forGetter(recipe -> recipe.result),
				Codec.FLOAT.fieldOf("crushedItemsPerPointOfDamage").forGetter(recipe -> recipe.crushedItemsPerPointOfDamage),
				Codec.FLOAT.fieldOf("experience").forGetter(recipe -> recipe.experience),
				Identifier.CODEC.optionalFieldOf("particleEffectIdentifier").forGetter(recipe -> recipe.particleEffectIdentifier),
				Codec.INT.optionalFieldOf("particleCount", 1).forGetter(recipe -> recipe.particleCount),
				Identifier.CODEC.fieldOf("soundEventIdentifier").forGetter(recipe -> recipe.soundEvent)
		).apply(instance, AnvilCrushingRecipe::new));
		
		private static final PacketCodec<RegistryByteBuf, AnvilCrushingRecipe> PACKET_CODEC = PacketCodecHelper.tuple(
				PacketCodecs.STRING, c -> c.group,
				PacketCodecs.BOOL, c -> c.secret,
				PacketCodecs.optional(Identifier.PACKET_CODEC), c -> c.requiredAdvancementIdentifier,
				Ingredient.PACKET_CODEC, c -> c.ingredient,
				ItemStack.PACKET_CODEC, c -> c.result,
				PacketCodecs.FLOAT, c -> c.crushedItemsPerPointOfDamage,
				PacketCodecs.FLOAT, c -> c.experience,
				PacketCodecs.optional(Identifier.PACKET_CODEC), c -> c.particleEffectIdentifier,
				PacketCodecs.VAR_INT, c -> c.particleCount,
				Identifier.PACKET_CODEC, c -> c.soundEvent,
				AnvilCrushingRecipe::new
		);
		
		@Override
		public MapCodec<AnvilCrushingRecipe> codec() {
			return CODEC;
		}
		
		@Override
		public PacketCodec<RegistryByteBuf, AnvilCrushingRecipe> packetCodec() {
			return PACKET_CODEC;
		}
		
	}
	
}
