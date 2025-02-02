package de.dafuqs.spectrum.recipe.primordial_fire_burning.dynamic;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.recipe.primordial_fire_burning.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.enchantment.*;
import net.minecraft.item.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.recipe.*;
import net.minecraft.recipe.input.*;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.*;
import net.minecraft.world.*;

import java.util.*;

public class EnchantedBookUnsoulingRecipe extends PrimordialFireBurningRecipe {
	
	public EnchantedBookUnsoulingRecipe(RegistryWrapper.WrapperLookup lookup) {
		super(
				"", false, Optional.of(UNLOCK_IDENTIFIER),
				Ingredient.ofStacks(SpectrumEnchantmentHelper.addOrUpgradeEnchantment(lookup, Items.ENCHANTED_BOOK.getDefaultStack(), Enchantments.SOUL_SPEED, 1, false, false).getRight()),
				SpectrumEnchantmentHelper.addOrUpgradeEnchantment(lookup, Items.ENCHANTED_BOOK.getDefaultStack(), Enchantments.SWIFT_SNEAK, 1, false, false).getRight()
		);
	}
	
	@Override
	public boolean matches(RecipeInput inv, World world) {
		ItemStack stack = inv.getStackInSlot(0);
		RegistryEntry.Reference<Enchantment> soulSpeed = world.getRegistryManager().get(RegistryKeys.ENCHANTMENT).getEntry(Enchantments.SOUL_SPEED).orElseThrow();
		return stack.getEnchantments().getEnchantments().contains(soulSpeed);
	}
	
	@Override
	public ItemStack craft(RecipeInput inv, RegistryWrapper.WrapperLookup drm) {
		ItemStack stack = inv.getStackInSlot(0);
		
		RegistryEntry.Reference<Enchantment> soulSpeed = drm.createRegistryLookup().getOptionalEntry(RegistryKeys.ENCHANTMENT, Enchantments.SOUL_SPEED).orElseThrow();
		int level = stack.getEnchantments().getLevel(soulSpeed);
		if (level > 0) {
			stack = SpectrumEnchantmentHelper.removeEnchantments(drm, stack, Enchantments.SOUL_SPEED).getLeft();
			stack = SpectrumEnchantmentHelper.addOrUpgradeEnchantment(drm, stack, Enchantments.SWIFT_SNEAK, level, false, false).getRight();
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
		
		public static final PacketCodec<RegistryByteBuf, EnchantedBookUnsoulingRecipe> PACKET_CODEC = PacketCodec.tuple(
				PacketCodecHelper.LOOKUP, c -> null,
				EnchantedBookUnsoulingRecipe::new
		);
		
		@Override
		public MapCodec<EnchantedBookUnsoulingRecipe> codec() {
			return CODEC;
		}
		
		@Override
		public PacketCodec<RegistryByteBuf, EnchantedBookUnsoulingRecipe> packetCodec() {
			return PACKET_CODEC;
		}
		
	}
	
}
