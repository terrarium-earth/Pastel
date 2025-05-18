package de.dafuqs.spectrum.recipe.primordial_fire_burning.dynamic;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.dafuqs.spectrum.helpers.CodecHelper;
import de.dafuqs.spectrum.helpers.PacketCodecHelper;
import de.dafuqs.spectrum.helpers.SpectrumEnchantmentHelper;
import de.dafuqs.spectrum.recipe.primordial_fire_burning.PrimordialFireBurningRecipe;
import de.dafuqs.spectrum.registries.SpectrumRecipeSerializers;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

import java.util.Optional;

public class EnchantedBookUnsoulingRecipe extends PrimordialFireBurningRecipe {
	
	public EnchantedBookUnsoulingRecipe(HolderLookup.Provider lookup) {
		super(
				"", false, Optional.of(UNLOCK_IDENTIFIER),
				Ingredient.of(SpectrumEnchantmentHelper.addOrUpgradeEnchantment(lookup, Items.ENCHANTED_BOOK.getDefaultInstance(), Enchantments.SOUL_SPEED, 1, false, false).getB()),
				SpectrumEnchantmentHelper.addOrUpgradeEnchantment(lookup, Items.ENCHANTED_BOOK.getDefaultInstance(), Enchantments.SWIFT_SNEAK, 1, false, false).getB()
		);
	}
	
	@Override
	public boolean matches(RecipeInput inv, Level world) {
		ItemStack stack = inv.getItem(0);
		Holder.Reference<Enchantment> soulSpeed = world.registryAccess().registryOrThrow(Registries.ENCHANTMENT).getHolder(Enchantments.SOUL_SPEED).orElseThrow();
		return stack.getEnchantments().keySet().contains(soulSpeed);
	}
	
	@Override
	public ItemStack assemble(RecipeInput inv, HolderLookup.Provider drm) {
		ItemStack stack = inv.getItem(0);
		
		Holder.Reference<Enchantment> soulSpeed = drm.asGetterLookup().get(Registries.ENCHANTMENT, Enchantments.SOUL_SPEED).orElseThrow();
		int level = stack.getEnchantments().getLevel(soulSpeed);
		if (level > 0) {
			stack = SpectrumEnchantmentHelper.removeEnchantments(drm, stack, Enchantments.SOUL_SPEED).getA();
			stack = SpectrumEnchantmentHelper.addOrUpgradeEnchantment(drm, stack, Enchantments.SWIFT_SNEAK, level, false, false).getB();
		}
		return stack;
	}
	
	@Override
	public RecipeSerializer<?> getSerializer() {
		return SpectrumRecipeSerializers.ENCHANTED_BOOK_UNSOULING;
	}
	
	public static class Serializer implements RecipeSerializer<EnchantedBookUnsoulingRecipe> {
		
		public static final MapCodec<EnchantedBookUnsoulingRecipe> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
				CodecHelper.LOOKUP.forGetter(c -> null)
		).apply(i, EnchantedBookUnsoulingRecipe::new));
		
		public static final StreamCodec<RegistryFriendlyByteBuf, EnchantedBookUnsoulingRecipe> PACKET_CODEC = StreamCodec.composite(
				PacketCodecHelper.LOOKUP, c -> null,
				EnchantedBookUnsoulingRecipe::new
		);
		
		@Override
		public MapCodec<EnchantedBookUnsoulingRecipe> codec() {
			return CODEC;
		}
		
		@Override
		public StreamCodec<RegistryFriendlyByteBuf, EnchantedBookUnsoulingRecipe> streamCodec() {
			return PACKET_CODEC;
		}
		
	}
	
}
