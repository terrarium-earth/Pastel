package de.dafuqs.spectrum.registries;

import de.dafuqs.spectrum.*;
import net.minecraft.component.*;
import net.minecraft.enchantment.*;
import net.minecraft.enchantment.effect.*;
import net.minecraft.loot.context.*;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.*;

import java.util.*;
import java.util.function.*;

public class SpectrumEnchantmentEffectComponentTypes {
	
	private static final Deferrer DEFERRER = new Deferrer();
	
	public static ComponentType<List<EnchantmentEffectEntry<RegistryEntry<Enchantment>>>> CLOAKED = register("cloaked", builder -> builder.codec(EnchantmentEffectEntry.createCodec(Enchantment.ENTRY_CODEC, LootContextTypes.ENCHANTED_ENTITY).listOf()));
	
	private static <T> ComponentType<T> register(String id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
		return DEFERRER.defer(builderOperator.apply(ComponentType.builder()).build(), type -> Registry.register(Registries.ENCHANTMENT_EFFECT_COMPONENT_TYPE, SpectrumCommon.locate(id), type));
	}
	
	public static void register() {
		DEFERRER.flush();
	}
	
}
