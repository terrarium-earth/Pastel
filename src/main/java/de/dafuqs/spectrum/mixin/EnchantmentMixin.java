package de.dafuqs.spectrum.mixin;

import com.llamalad7.mixinextras.sugar.*;
import de.dafuqs.spectrum.*;
import net.minecraft.enchantment.*;
import net.minecraft.item.*;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.*;
import net.minecraft.registry.tag.*;
import net.minecraft.text.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;

import java.util.*;

@Mixin(Enchantment.class)
public abstract class EnchantmentMixin {
	
	/**
	 * This is just... beautifully cursed, really. It's the first decent, "robust" solution that came to mind though, so yeah.
	 * Basically, Mojang always serializes enchantments with the description being <code>enchantment.minecraft.&lt;name&gt;</code>. We do too, with
	 * <code>enchantment.spectrum.&lt;name&gt;[.cloaked]</code>. This mixin tries to convert the key into an identifier, such as
	 * <code>spectrum:enchantable/extended/minecraft.&lt;name&gt;</code> or <code>spectrum:enchantable/extended/spectrum.&lt;name&gt;[.cloaked]</code>,
	 * which is then loaded as a tag and acted upon.
	 */
	@ModifyVariable(method = "<init>(Lnet/minecraft/text/Text;Lnet/minecraft/enchantment/Enchantment$Definition;Lnet/minecraft/registry/entry/RegistryEntryList;Lnet/minecraft/component/ComponentMap;)V", at = @At(value = "INVOKE", target = "Ljava/lang/Record;<init>()V", shift = At.Shift.AFTER), argsOnly = true)
	private Enchantment.Definition injectExtendedEnchantables(Enchantment.Definition definition, @Local(argsOnly = true) Text description) {
		List<RegistryEntry<Item>> items = new ArrayList<>(definition.supportedItems().stream().toList());
		
		if (description.getContent() instanceof TranslatableTextContent translation) {
			String[] sections = translation.getKey().split("\\.", 2);
			if (sections.length == 2 && sections[0].equals("enchantment")) {
				SpectrumCommon.tryLocate("enchantable/extended/" + sections[1]).ifPresent(whitelisted -> {
					TagKey<Item> tag = TagKey.of(RegistryKeys.ITEM, whitelisted);
					items.addAll(Registries.ITEM.getEntryList(tag).stream().flatMap(RegistryEntryList::stream).toList());
				});
				
				SpectrumCommon.tryLocate("enchantable/blacklisted/" + sections[1]).ifPresent(whitelisted -> {
					TagKey<Item> tag = TagKey.of(RegistryKeys.ITEM, whitelisted);
					items.removeAll(Registries.ITEM.getEntryList(tag).stream().flatMap(RegistryEntryList::stream).toList());
				});
			}
		}
		
		// TODO: since this creates a new Enchantment.Definition that does not exist in the vanilla registry (entryToRawId), things crash
		return new Enchantment.Definition(
				RegistryEntryList.of(items), definition.primaryItems(), definition.weight(), definition.maxLevel(),
				definition.minCost(), definition.maxCost(), definition.anvilCost(), definition.slots()
		);
	}
//	@WrapOperation(method = "<init>(Lnet/minecraft/text/Text;Lnet/minecraft/enchantment/Enchantment$Definition;Lnet/minecraft/registry/entry/RegistryEntryList;Lnet/minecraft/component/ComponentMap;)V", at = @At(value = "FIELD", target = "Lnet/minecraft/enchantment/Enchantment;definition:Lnet/minecraft/enchantment/Enchantment$Definition;"))
//	private void injectExtendedEnchantables(Enchantment instance, Enchantment.Definition value, Operation<Void> original, @Local(argsOnly = true) Text description) {
//
//		List<RegistryEntry<Item>> items = new ArrayList<>(value.supportedItems().stream().toList());
//
//		if (description.getContent() instanceof TranslatableTextContent translation) {
//			String[] sections = translation.getKey().split("\\.", 1);
//			if (sections.length == 2 && sections[0].equals("enchantment")) {
//				SpectrumCommon.tryLocate("enchantable/extended/" + sections[1]).ifPresent(whitelisted -> {
//					TagKey<Item> tag = TagKey.of(RegistryKeys.ITEM, whitelisted);
//					items.addAll(Registries.ITEM.getEntryList(tag).stream().flatMap(RegistryEntryList::stream).toList());
//				});
//
//				SpectrumCommon.tryLocate("enchantable/blacklisted/" + sections[1]).ifPresent(whitelisted -> {
//					TagKey<Item> tag = TagKey.of(RegistryKeys.ITEM, whitelisted);
//					items.removeAll(Registries.ITEM.getEntryList(tag).stream().flatMap(RegistryEntryList::stream).toList());
//				});
//			}
//		}
//
//		Enchantment.Definition definition = new Enchantment.Definition(RegistryEntryList.of(items), value.primaryItems(), value.weight(), value.maxLevel(), value.minCost(), value.maxCost(), value.anvilCost(), value.slots());
//		original.call(instance, definition);
//	}
	
}
