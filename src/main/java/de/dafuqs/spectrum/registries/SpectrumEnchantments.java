package de.dafuqs.spectrum.registries;

import net.minecraft.component.*;
import net.minecraft.component.type.*;
import net.minecraft.enchantment.*;
import net.minecraft.enchantment.effect.*;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.*;
import net.minecraft.item.*;
import net.minecraft.loot.condition.*;
import net.minecraft.loot.context.*;
import net.minecraft.predicate.entity.*;
import net.minecraft.registry.*;
import net.minecraft.registry.tag.*;
import net.minecraft.util.*;

import java.util.*;

import static de.dafuqs.spectrum.SpectrumCommon.*;
import static de.dafuqs.spectrum.data.SpectrumDataGenerator.*;

@SuppressWarnings("unused")
public class SpectrumEnchantments {
	
	private static final Deferrer.Contextual<ProvidedTagBuilderBuilder<Item>> ITEM_TAG_DEFERRER = new Deferrer.Contextual<>();
	private static final Deferrer.Contextual<ProvidedTagBuilderBuilder<Enchantment>> ENCHANTMENT_TAG_DEFERRER = new Deferrer.Contextual<>();
	private static final Deferrer.Contextual<BootstrapContext<Enchantment>> BOOTSTRAP_DEFERRER = new Deferrer.Contextual<>();
	
	public static final RegistryKey<Enchantment> CLOAKED_BIG_CATCH = of("cloaked/big_catch");
	public static final RegistryKey<Enchantment> CLOAKED_CLOVERS_FAVOR = of("cloaked/clovers_favor");
	public static final RegistryKey<Enchantment> CLOAKED_DISARMING = of("cloaked/disarming");
	public static final RegistryKey<Enchantment> CLOAKED_EXUBERANCE = of("cloaked/exuberance");
	public static final RegistryKey<Enchantment> CLOAKED_FIRST_STRIKE = of("cloaked/first_strike");
	public static final RegistryKey<Enchantment> CLOAKED_FOUNDRY = of("cloaked/foundry");
	public static final RegistryKey<Enchantment> CLOAKED_IMPROVED_CRITICAL = of("cloaked/improved_critical");
	public static final RegistryKey<Enchantment> CLOAKED_INDESTRUCTIBLE = of("cloaked/indestructible");
	public static final RegistryKey<Enchantment> CLOAKED_INERTIA = of("cloaked/inertia");
	public static final RegistryKey<Enchantment> CLOAKED_INEXORABLE = of("cloaked/inexorable");
	public static final RegistryKey<Enchantment> CLOAKED_INVENTORY_INSERTION = of("cloaked/inventory_insertion");
	public static final RegistryKey<Enchantment> CLOAKED_RAZING = of("cloaked/razing");
	public static final RegistryKey<Enchantment> CLOAKED_RESONANCE = of("cloaked/resonance");
	public static final RegistryKey<Enchantment> CLOAKED_SERENDIPITY_REEL = of("cloaked/serendipity_reel");
	public static final RegistryKey<Enchantment> CLOAKED_SNIPING = of("cloaked/sniping");
	public static final RegistryKey<Enchantment> CLOAKED_STEADFAST = of("cloaked/steadfast");
	
	public static final RegistryKey<Enchantment> BIG_CATCH = of("big_catch"); // Increase the chance to reel in entities instead of fishing loot
	public static final RegistryKey<Enchantment> CLOVERS_FAVOR = of("clovers_favor"); // Increases drop chance of <1 loot drops
	public static final RegistryKey<Enchantment> DISARMING = of("disarming"); // Drops mob equipment on hit (and players, but way less often)
	public static final RegistryKey<Enchantment> EXUBERANCE = of("exuberance"); // Drops more XP on kill
	public static final RegistryKey<Enchantment> FIRST_STRIKE = of("first_strike"); // Increased damage if enemy has full health
	public static final RegistryKey<Enchantment> FOUNDRY = of("foundry"); // applies smelting recipe before dropping items after mining
	public static final RegistryKey<Enchantment> IMPROVED_CRITICAL = of("improved_critical"); // Increased damage when landing a critical hit
	public static final RegistryKey<Enchantment> INDESTRUCTIBLE = of("indestructible"); // Make tools not use up durability
	public static final RegistryKey<Enchantment> INERTIA = of("inertia"); // Decreases mining speed, but increases with each mined block of the same type
	public static final RegistryKey<Enchantment> INEXORABLE = of("inexorable"); // prevents mining & movement slowdowns
	public static final RegistryKey<Enchantment> INVENTORY_INSERTION = of("inventory_insertion"); // don't drop items into the world, add to inv instead
	public static final RegistryKey<Enchantment> PEST_CONTROL = of("pest_control"); // Kills silverfish when mining infested blocks
	public static final RegistryKey<Enchantment> RAZING = of("razing"); // increased mining speed for very hard blocks
	public static final RegistryKey<Enchantment> RESONANCE = of("resonance"); // Silk Touch, just for different blocks
	public static final RegistryKey<Enchantment> SERENDIPITY_REEL = of("serendipity_reel"); // Increase luck when fishing
	public static final RegistryKey<Enchantment> SNIPING = of("sniping"); // Increases projectile speed => increased damage + range
	public static final RegistryKey<Enchantment> STEADFAST = of("steadfast"); // ItemStacks with this enchantment are not destroyed by cactus, fire, lava, ...
	public static final RegistryKey<Enchantment> TIGHT_GRIP = of("tight_grip"); // Increases attack speed
	public static final RegistryKey<Enchantment> TREASURE_HUNTER = of("treasure_hunter"); // Drops mob heads
	public static final RegistryKey<Enchantment> VOIDING = of("voiding"); // Voids all items mined
	
	public static final RegistryKey<Enchantment> CLOAKED_PEST_CONTROL = register(PEST_CONTROL, 1, 1, new Enchantment.Cost(10, 0), new Enchantment.Cost(30, 0), 8, List.of(AttributeModifierSlot.MAINHAND), SpectrumAdvancements.ENCHANTMENTS_PEST_CONTROL,
			provider -> provider
					.forceAddTag(ItemTags.MINING_LOOT_ENCHANTABLE),
			(key, provider) -> provider
					.forceAddTag(getPairKey(RESONANCE)));
	
	public static final RegistryKey<Enchantment> CLOAKED_TREASURE_HUNTER = register(TREASURE_HUNTER, 2, 3, new Enchantment.Cost(15, 0), new Enchantment.Cost(45, 0), 4, List.of(AttributeModifierSlot.MAINHAND), SpectrumAdvancements.ENCHANTMENTS_TREASURE_HUNTER,
			provider -> provider
					.forceAddTag(ItemTags.WEAPON_ENCHANTABLE)
					.addOptionalTag(Identifier.of("malum:scythe")),
			(key, provider) -> provider
					.add(Enchantments.LOOTING)
					.addOptional(Identifier.of("malum:spirit_plunder")));
	
	public static final RegistryKey<Enchantment> CLOAKED_TIGHT_GRIP = register(TIGHT_GRIP, 2, 2, new Enchantment.Cost(5, 0), new Enchantment.Cost(35, 0), 4, List.of(AttributeModifierSlot.MAINHAND), SpectrumAdvancements.ENCHANTMENTS_TIGHT_GRIP,
			(key, ctx, builder) -> builder
					.addEffect(EnchantmentEffectComponentTypes.ATTRIBUTES, new AttributeEnchantmentEffect(locate("enchantment.tight_grip"), EntityAttributes.GENERIC_ATTACK_SPEED, EnchantmentLevelBasedValue.linear(0.0625f), EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)),
			provider -> provider
					.forceAddTag(ItemTags.SWORD_ENCHANTABLE)
					.addOptionalTag(Identifier.of("malum:scythe")),
			(key, provider) -> provider
					.addOptional(Identifier.of("malum:rebound")));
	
	public static final RegistryKey<Enchantment> CLOAKED_VOIDING = register(VOIDING, 2, 1, new Enchantment.Cost(25, 0), new Enchantment.Cost(50, 0), 4, List.of(AttributeModifierSlot.MAINHAND), SpectrumAdvancements.ENCHANTMENTS_VOIDING,
			provider -> provider
					.forceAddTag(ItemTags.MINING_LOOT_ENCHANTABLE)
					.add(SpectrumBlocks.BOTTOMLESS_BUNDLE.asItem()));
	
	private static RegistryKey<Enchantment> of(String id) {
		return RegistryKey.of(RegistryKeys.ENCHANTMENT, locate(id));
	}
	
	private static TagKey<Item> getEnchantableKey(RegistryKey<Enchantment> key) {
		return TagKey.of(RegistryKeys.ITEM, locate("enchantable/" + key.getValue().getPath()));
	}
	
	private static TagKey<Enchantment> getExclusiveSetKey(RegistryKey<Enchantment> key) {
		return TagKey.of(RegistryKeys.ENCHANTMENT, locate("exclusive_set/" + key.getValue().getPath()));
	}
	
	private static TagKey<Enchantment> getPairKey(RegistryKey<Enchantment> key) {
		return TagKey.of(RegistryKeys.ENCHANTMENT, key.getValue());
	}
	
	private static RegistryKey<Enchantment> register(RegistryKey<Enchantment> enchantmentKey, int weight, int maxLevel, Enchantment.Cost minCost, Enchantment.Cost maxCost, int anvilCost, List<AttributeModifierSlot> slots, Identifier advancementId, ProvidedTagBuilderCallback<Item> enchantableBuilder) {
		return register(enchantmentKey, weight, maxLevel, minCost, maxCost, anvilCost, slots, advancementId, enchantableBuilder, (key, provider) -> provider);
	}
	
	private static RegistryKey<Enchantment> register(RegistryKey<Enchantment> enchantmentKey, int weight, int maxLevel, Enchantment.Cost minCost, Enchantment.Cost maxCost, int anvilCost, List<AttributeModifierSlot> slots, Identifier advancementId, ProvidedTagBuilderCallback<Item> enchantableBuilder, KeyedTagBuilderCallback<Enchantment> exclusiveSetBuilder) {
		return register(enchantmentKey, weight, maxLevel, minCost, maxCost, anvilCost, slots, advancementId, (key, ctx, builder) -> builder, enchantableBuilder, exclusiveSetBuilder);
	}
	
	private static RegistryKey<Enchantment> register(
			RegistryKey<Enchantment> enchantmentKey,
			int weight,
			int maxLevel,
			Enchantment.Cost minCost,
			Enchantment.Cost maxCost,
			int anvilCost,
			List<AttributeModifierSlot> slots,
			Identifier advancementId,
			EnchantmentBuilderCallback enchantmentBuilder,
			ProvidedTagBuilderCallback<Item> enchantableBuilder,
			KeyedTagBuilderCallback<Enchantment> exclusiveSetBuilder
	) {
		RegistryKey<Enchantment> cloakKey = RegistryKey.of(RegistryKeys.ENCHANTMENT, locate("cloaked/" + enchantmentKey.getValue().getPath()));
		if (IS_DATAGEN) {
			// Build the base enchantment
			BOOTSTRAP_DEFERRER.defer(enchantmentKey, (key, ctx) -> {
				Enchantment.Definition definition = new Enchantment.Definition(ctx.items().getOrThrow(getEnchantableKey(key)), Optional.empty(), weight, maxLevel, new Enchantment.Cost(0, 0), new Enchantment.Cost(0, 0), 0, slots);
				Enchantment.Builder enchantment = new Enchantment.Builder(definition)
						.exclusiveSet(ctx.enchantments().getOrThrow(getExclusiveSetKey(key)));
				ctx.registerable().register(key, enchantment.build(key.getValue()));
			});
			
			// Build the cloak enchantment
			BOOTSTRAP_DEFERRER.defer(enchantmentKey, (key, ctx) -> {
				LootCondition.Builder isPlayerCondition = EntityPropertiesLootCondition.builder(LootContext.EntityTarget.THIS, EntityPredicate.Builder.create().type(EntityType.PLAYER));
				LootCondition.Builder hasAdvancementCondition = EntityPropertiesLootCondition.builder(LootContext.EntityTarget.THIS, EntityPredicate.Builder.create().typeSpecific(PlayerPredicate.Builder.create().advancement(advancementId, true).build()));
				LootCondition.Builder condition = isPlayerCondition.invert().or(hasAdvancementCondition);
				Enchantment.Definition definition = new Enchantment.Definition(ctx.items().getOrThrow(getEnchantableKey(key)), Optional.empty(), weight, maxLevel, minCost, maxCost, anvilCost, slots);
				Enchantment.Builder enchantment = new Enchantment.Builder(definition)
						.exclusiveSet(ctx.enchantments().getOrThrow(getExclusiveSetKey(key)))
						.addEffect(SpectrumEnchantmentEffectComponentTypes.CLOAKED, ctx.enchantments().getOrThrow(key), condition);
				ctx.registerable().register(cloakKey, enchantment.build(key.getValue()));
			});
			
			// Build the cloaking pair enchantment tag (e.g., resonance + cloaked/resonance)
			ENCHANTMENT_TAG_DEFERRER.defer(enchantmentKey, (key, ctx) -> {
				ctx.build(getPairKey(key)).add(key).add(cloakKey);
			});
			
			// Build the exclusive set enchantment tag
			ENCHANTMENT_TAG_DEFERRER.defer(enchantmentKey, (key, ctx) -> {
				exclusiveSetBuilder.build(key, ctx.build(getExclusiveSetKey(key)).forceAddTag(getPairKey(key)));
			});
			
			// Build the enchantable items tag
			ITEM_TAG_DEFERRER.defer(enchantmentKey, (key, ctx) -> {
				enchantableBuilder.build(ctx.build(getEnchantableKey(key)));
			});
		}
		return cloakKey;
	}
	
	public static void provideItemTags(ProvidedTagBuilderBuilder<Item> builder) {
		ITEM_TAG_DEFERRER.flush(builder);
	}
	
	public static void provideEnchantmentTags(ProvidedTagBuilderBuilder<Enchantment> builder) {
		ENCHANTMENT_TAG_DEFERRER.flush(builder);
	}
	
	public static void provideEnchantments(Registerable<Enchantment> registerable) {
		BOOTSTRAP_DEFERRER.flush(new BootstrapContext<>(registerable));
	}
	
	private interface EnchantmentBuilderCallback {
		Enchantment.Builder build(RegistryKey<Enchantment> key, BootstrapContext<Enchantment> ctx, Enchantment.Builder builder);
	}
	
}
