package de.dafuqs.spectrum.registries;

import net.fabricmc.fabric.api.datagen.v1.provider.*;
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
	
	private static final Deferrer.Contextual<RegistryKey<Enchantment>, ProvidedTagBuilderBuilder<Item>> ITEM_TAG_DEFERRER = new Deferrer.Contextual<>();
	private static final Deferrer.Contextual<RegistryKey<Enchantment>, ProvidedTagBuilderBuilder<Enchantment>> ENCHANTMENT_TAG_DEFERRER = new Deferrer.Contextual<>();
	private static final Deferrer.Contextual<RegistryKey<Enchantment>, BootstrapContext<Enchantment>> BOOTSTRAP_DEFERRER = new Deferrer.Contextual<>();
	
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
	
	public static final RegistryKey<Enchantment> CLOAKED_BIG_CATCH = new Builder(
			BIG_CATCH,
			2,
			3,
			new Enchantment.Cost(20, 0),
			new Enchantment.Cost(50, 0),
			4,
			List.of(AttributeModifierSlot.MAINHAND),
			SpectrumAdvancements.ENCHANTMENTS_BIG_CATCH
	).withEnchantable(provider -> provider
			.forceAddTag(ItemTags.FISHING_ENCHANTABLE)
			.forceAddTag(SpectrumItemTags.FISHING_RODS)
	).register();
	
	public static final RegistryKey<Enchantment> CLOAKED_CLOVERS_FAVOR = new Builder(
			CLOVERS_FAVOR,
			2,
			3,
			new Enchantment.Cost(20, 0),
			new Enchantment.Cost(50, 0),
			4,
			List.of(AttributeModifierSlot.MAINHAND),
			SpectrumAdvancements.ENCHANTMENTS_CLOVERS_FAVOR
	).withEnchantable(provider -> provider
			.forceAddTag(ItemTags.SWORD_ENCHANTABLE)
			.add(SpectrumItems.MALACHITE_BIDENT)
			.addOptionalTag(Identifier.of("malum:scythe"))
	).withExclusiveSet((key, provider) -> provider
			.add(Enchantments.LOOTING)
			.addOptional(Identifier.of("malum:spirit_plunder"))
	).register();
	
	public static final RegistryKey<Enchantment> CLOAKED_DISARMING = new Builder(
			DISARMING,
			1,
			2,
			new Enchantment.Cost(10, 0),
			new Enchantment.Cost(40, 0),
			8,
			List.of(AttributeModifierSlot.MAINHAND),
			SpectrumAdvancements.ENCHANTMENTS_DISARMING
	).withEnchantable(provider -> provider
			.forceAddTag(ItemTags.WEAPON_ENCHANTABLE)
	).register();
	
	public static final RegistryKey<Enchantment> CLOAKED_EXUBERANCE = new Builder(
			EXUBERANCE,
			5,
			5,
			new Enchantment.Cost(10, 0),
			new Enchantment.Cost(50, 0),
			2,
			List.of(AttributeModifierSlot.MAINHAND),
			SpectrumAdvancements.ENCHANTMENTS_EXUBERANCE
	).withEnchantable(provider -> provider
			.forceAddTag(ItemTags.WEAPON_ENCHANTABLE)
			.forceAddTag(ItemTags.MINING_LOOT_ENCHANTABLE)
			.forceAddTag(SpectrumItemTags.FISHING_RODS)
			.addOptionalTag(Identifier.of("malum:scythe"))
	).register();
	
	public static final RegistryKey<Enchantment> CLOAKED_FIRST_STRIKE = new Builder(
			FIRST_STRIKE,
			2,
			2,
			new Enchantment.Cost(10, 0),
			new Enchantment.Cost(40, 0),
			4,
			List.of(AttributeModifierSlot.MAINHAND),
			SpectrumAdvancements.ENCHANTMENTS_FIRST_STRIKE
	).withEnchantable(provider -> provider
			.forceAddTag(ItemTags.WEAPON_ENCHANTABLE)
			.addOptionalTag(Identifier.of("malum:scythe"))
	).register();
	
	public static final RegistryKey<Enchantment> CLOAKED_FOUNDRY = new Builder(
			FOUNDRY,
			2,
			1,
			new Enchantment.Cost(15, 0),
			new Enchantment.Cost(65, 0),
			4,
			List.of(AttributeModifierSlot.MAINHAND),
			SpectrumAdvancements.ENCHANTMENTS_FOUNDRY
	).withEnchantable(provider -> provider
			.forceAddTag(ItemTags.MINING_LOOT_ENCHANTABLE)
			.forceAddTag(SpectrumItemTags.FISHING_RODS)
	).withExclusiveSet((key, provider) -> provider
			.add(Enchantments.SILK_TOUCH)
			.addOptional(Identifier.of("gofish:deepfry"))
	).register();
	
	public static final RegistryKey<Enchantment> CLOAKED_IMPROVED_CRITICAL = new Builder(
			IMPROVED_CRITICAL,
			2,
			2,
			new Enchantment.Cost(10, 0),
			new Enchantment.Cost(40, 0),
			4,
			List.of(AttributeModifierSlot.MAINHAND),
			SpectrumAdvancements.ENCHANTMENTS_IMPROVED_CRITICAL
	).withEnchantable(provider -> provider
			.forceAddTag(ItemTags.WEAPON_ENCHANTABLE)
			.addOptionalTag(Identifier.of("malum:scythe"))
	).withExclusiveSet((key, provider) -> provider
			.add(Enchantments.SHARPNESS)
			.addOptional(Identifier.of("malum:haunted"))
	).register();
	
	public static final RegistryKey<Enchantment> CLOAKED_INDESTRUCTIBLE = new Builder(
			INDESTRUCTIBLE,
			2,
			1,
			new Enchantment.Cost(30, 0),
			new Enchantment.Cost(60, 0),
			4,
			List.of(AttributeModifierSlot.MAINHAND),
			SpectrumAdvancements.ENCHANTMENTS_INDESTRUCTIBLE
	).withEnchantable(provider -> provider
					.forceAddTag(ItemTags.DURABILITY_ENCHANTABLE)
					.add(SpectrumItems.ENDER_SPLICE)
//			.excludeOptional(Identifier.of("biomemakeover:small_illunite_bud")) //TODO
	).withExclusiveSet((key, provider) -> provider
			.add(Enchantments.INFINITY)
			.add(Enchantments.UNBREAKING)
			.add(Enchantments.EFFICIENCY)
			.add(Enchantments.MENDING)
			.add(Enchantments.PROTECTION)
			.add(Enchantments.BINDING_CURSE)
	).register();
	
	public static final RegistryKey<Enchantment> CLOAKED_INERTIA = new Builder(
			INERTIA,
			1,
			3,
			new Enchantment.Cost(10, 0),
			new Enchantment.Cost(40, 0),
			8,
			List.of(AttributeModifierSlot.MAINHAND),
			SpectrumAdvancements.ENCHANTMENTS_INERTIA
	).withEnchantable(provider -> provider
			.forceAddTag(ItemTags.MINING_LOOT_ENCHANTABLE)
			.add(SpectrumItems.DRAGON_TALON)
			.add(SpectrumItems.DRACONIC_TWINSWORD)
	).withExclusiveSet((key, provider) -> provider
			.add(Enchantments.EFFICIENCY)
	).register();
	
	public static final RegistryKey<Enchantment> CLOAKED_INEXORABLE = new Builder(
			INEXORABLE,
			1,
			1,
			new Enchantment.Cost(50, 0),
			new Enchantment.Cost(100, 0),
			8,
			List.of(AttributeModifierSlot.MAINHAND, AttributeModifierSlot.OFFHAND, AttributeModifierSlot.CHEST),
			SpectrumAdvancements.ENCHANTMENTS_INEXORABLE
	).withEnchantable(provider -> provider
			.forceAddTag(ItemTags.CHEST_ARMOR_ENCHANTABLE)
			.forceAddTag(ItemTags.MINING_LOOT_ENCHANTABLE)
			.forceAddTag(ItemTags.TRIDENT_ENCHANTABLE)
	).register();
	
	public static final RegistryKey<Enchantment> CLOAKED_INVENTORY_INSERTION = new Builder(
			INVENTORY_INSERTION,
			2,
			1,
			new Enchantment.Cost(15, 0),
			new Enchantment.Cost(45, 0),
			4,
			List.of(AttributeModifierSlot.MAINHAND),
			SpectrumAdvancements.ENCHANTMENTS_INVENTORY_INSERTION
	).withEnchantable(provider -> provider
			.forceAddTag(ItemTags.MINING_ENCHANTABLE)
			.forceAddTag(ItemTags.WEAPON_ENCHANTABLE)
			.forceAddTag(ItemTags.TRIDENT_ENCHANTABLE)
			.forceAddTag(ItemTags.BOW_ENCHANTABLE)
			.forceAddTag(ItemTags.CROSSBOW_ENCHANTABLE)
			.forceAddTag(SpectrumItemTags.FISHING_RODS)
			.addOptionalTag(Identifier.of("malum:scythe"))
	).register();
	
	public static final RegistryKey<Enchantment> CLOAKED_PEST_CONTROL = new Builder(
			PEST_CONTROL,
			1,
			1,
			new Enchantment.Cost(10, 0),
			new Enchantment.Cost(30, 0),
			8,
			List.of(AttributeModifierSlot.MAINHAND),
			SpectrumAdvancements.ENCHANTMENTS_PEST_CONTROL
	).withEnchantable(provider -> provider
			.forceAddTag(ItemTags.MINING_LOOT_ENCHANTABLE)
	).withExclusiveSet((key, provider) -> provider
			.forceAddTag(getPairTag(RESONANCE))
	).register();
	
	public static final RegistryKey<Enchantment> CLOAKED_RAZING = new Builder(
			RAZING,
			5,
			3,
			new Enchantment.Cost(20, 0),
			new Enchantment.Cost(30, 0),
			2,
			List.of(AttributeModifierSlot.MAINHAND),
			SpectrumAdvancements.ENCHANTMENTS_RAZING
	).withEnchantable(provider -> provider
			.forceAddTag(ItemTags.MINING_LOOT_ENCHANTABLE)
	).withExclusiveSet((key, provider) -> provider
			.add(Enchantments.FORTUNE)
	).register();
	
	public static final RegistryKey<Enchantment> CLOAKED_RESONANCE = new Builder(
			RESONANCE,
			1,
			1,
			new Enchantment.Cost(25, 0),
			new Enchantment.Cost(100, 0),
			8,
			List.of(AttributeModifierSlot.MAINHAND),
			SpectrumAdvancements.ENCHANTMENTS_RESONANCE
	).withEnchantable(provider -> provider
			.forceAddTag(ItemTags.MINING_ENCHANTABLE)
			.add(SpectrumItems.ENDER_SPLICE)
			.add(SpectrumItems.EXCHANGING_STAFF)
	).withExclusiveSet((key, provider) -> provider
			.forceAddTag(EnchantmentTags.MINING_EXCLUSIVE_SET)
			.forceAddTag(SpectrumEnchantmentTags.PEST_CONTROL)
	).register();
	
	public static final RegistryKey<Enchantment> CLOAKED_SERENDIPITY_REEL = new Builder(
			SERENDIPITY_REEL,
			2,
			2,
			new Enchantment.Cost(40, 0),
			new Enchantment.Cost(120, 0),
			4,
			List.of(AttributeModifierSlot.MAINHAND),
			SpectrumAdvancements.ENCHANTMENTS_SERENDIPITY_REEL
	).withEnchantable(provider -> provider
			.forceAddTag(ItemTags.FISHING_ENCHANTABLE)
	).register();
	
	public static final RegistryKey<Enchantment> CLOAKED_SNIPING = new Builder(
			SNIPING,
			1,
			2,
			new Enchantment.Cost(20, 0),
			new Enchantment.Cost(50, 0),
			8,
			List.of(AttributeModifierSlot.MAINHAND),
			SpectrumAdvancements.ENCHANTMENTS_SNIPING
	).withEnchantable(provider -> provider
			.forceAddTag(ItemTags.CROSSBOW_ENCHANTABLE)
			.add(SpectrumItems.GLEAMING_PIN)
	).withExclusiveSet((key, provider) -> provider
			.add(Enchantments.MULTISHOT)
	).register();
	
	public static final RegistryKey<Enchantment> CLOAKED_STEADFAST = new Builder(
			STEADFAST,
			10,
			1,
			new Enchantment.Cost(30, 0),
			new Enchantment.Cost(60, 0),
			1,
			List.of(AttributeModifierSlot.MAINHAND),
			SpectrumAdvancements.ENCHANTMENTS_STEADFAST
	).withEnchantable(provider -> provider
			.forceAddTag(ItemTags.DURABILITY_ENCHANTABLE)
			.forceAddTag(ItemTags.MINING_ENCHANTABLE)
			.forceAddTag(ItemTags.VANISHING_ENCHANTABLE)
			.addOptionalTag(Identifier.of("trinkets:enchantable/enchantable"))
	).register();
	
	public static final RegistryKey<Enchantment> CLOAKED_TREASURE_HUNTER = new Builder(
			TREASURE_HUNTER,
			2,
			3,
			new Enchantment.Cost(15, 0),
			new Enchantment.Cost(45, 0),
			4,
			List.of(AttributeModifierSlot.MAINHAND),
			SpectrumAdvancements.ENCHANTMENTS_TREASURE_HUNTER
	).withEnchantable(provider -> provider
			.forceAddTag(ItemTags.WEAPON_ENCHANTABLE)
			.addOptionalTag(Identifier.of("malum:scythe"))
	).withExclusiveSet((key, provider) -> provider
			.add(Enchantments.LOOTING)
			.addOptional(Identifier.of("malum:spirit_plunder"))
	).register();
	
	public static final RegistryKey<Enchantment> CLOAKED_TIGHT_GRIP = new Builder(
			TIGHT_GRIP,
			2,
			2,
			new Enchantment.Cost(5, 0),
			new Enchantment.Cost(35, 0),
			4,
			List.of(AttributeModifierSlot.MAINHAND),
			SpectrumAdvancements.ENCHANTMENTS_TIGHT_GRIP
	).withEffects((key, ctx, builder) -> builder
			.addEffect(EnchantmentEffectComponentTypes.ATTRIBUTES, new AttributeEnchantmentEffect(locate("enchantment.tight_grip"), EntityAttributes.GENERIC_ATTACK_SPEED, EnchantmentLevelBasedValue.linear(0.0625f), EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL))
	).withEnchantable(provider -> provider
			.forceAddTag(ItemTags.SWORD_ENCHANTABLE)
			.addOptionalTag(Identifier.of("malum:scythe"))
	).withExclusiveSet((key, provider) -> provider
			.addOptional(Identifier.of("malum:rebound"))
	).register();
	
	public static final RegistryKey<Enchantment> CLOAKED_VOIDING = new Builder(
			VOIDING,
			2,
			1,
			new Enchantment.Cost(25, 0),
			new Enchantment.Cost(50, 0),
			4,
			List.of(AttributeModifierSlot.MAINHAND),
			SpectrumAdvancements.ENCHANTMENTS_VOIDING
	).withEnchantable(provider -> provider
			.forceAddTag(ItemTags.MINING_LOOT_ENCHANTABLE)
			.add(SpectrumBlocks.BOTTOMLESS_BUNDLE.asItem())
	).register();
	
	private static RegistryKey<Enchantment> of(String id) {
		return RegistryKey.of(RegistryKeys.ENCHANTMENT, locate(id));
	}
	
	public static TagKey<Item> getEnchantableTag(RegistryKey<Enchantment> key) {
		return TagKey.of(RegistryKeys.ITEM, locate("enchantable/" + key.getValue().getPath()));
	}
	
	public static TagKey<Enchantment> getExclusiveSetTag(RegistryKey<Enchantment> key) {
		return TagKey.of(RegistryKeys.ENCHANTMENT, locate("exclusive_set/" + key.getValue().getPath()));
	}
	
	public static TagKey<Enchantment> getPairTag(RegistryKey<Enchantment> key) {
		return TagKey.of(RegistryKeys.ENCHANTMENT, key.getValue());
	}
	
	public static RegistryKey<Enchantment> getCloakKey(RegistryKey<Enchantment> key) {
		return RegistryKey.of(RegistryKeys.ENCHANTMENT, locate("cloaked/" + key.getValue().getPath()));
	}
	
	private static class Builder {
		private final RegistryKey<Enchantment> enchantmentKey;
		private final RegistryKey<Enchantment> cloakKey;
		private final int weight;
		private final int maxLevel;
		private final Enchantment.Cost minCost;
		private final Enchantment.Cost maxCost;
		private final int anvilCost;
		private final List<AttributeModifierSlot> slots;
		private final Identifier advancementId;
		
		private EnchantmentBuilderCallback effectsBuilder = (key, ctx, builder) -> builder;
		private TagBuilderCallback<Item> enchantableBuilder = provider -> provider;
		private KeyedTagBuilderCallback<Enchantment> exclusiveSetBuilder = (key, provider) -> provider;
		
		public Builder(RegistryKey<Enchantment> enchantmentKey, int weight, int maxLevel, Enchantment.Cost minCost, Enchantment.Cost maxCost, int anvilCost, List<AttributeModifierSlot> slots, Identifier advancementId) {
			this.enchantmentKey = enchantmentKey;
			this.cloakKey = getCloakKey(enchantmentKey);
			this.weight = weight;
			this.maxLevel = maxLevel;
			this.minCost = minCost;
			this.maxCost = maxCost;
			this.anvilCost = anvilCost;
			this.slots = slots;
			this.advancementId = advancementId;
		}
		
		public Builder withEffects(EnchantmentBuilderCallback enchantmentBuilder) {
			this.effectsBuilder = enchantmentBuilder;
			return this;
		}
		
		public Builder withEnchantable(TagBuilderCallback<Item> enchantableBuilder) {
			this.enchantableBuilder = enchantableBuilder;
			return this;
		}
		
		public Builder withExclusiveSet(KeyedTagBuilderCallback<Enchantment> exclusiveSetBuilder) {
			this.exclusiveSetBuilder = exclusiveSetBuilder;
			return this;
		}
		
		public RegistryKey<Enchantment> register() {
			if (!IS_DATAGEN)
				return cloakKey;
			
			BOOTSTRAP_DEFERRER.defer(enchantmentKey, (key, ctx) -> {
				// Build the base enchantment
				Enchantment.Definition baseDefinition = new Enchantment.Definition(ctx.items().getOrThrow(getEnchantableTag(key)), Optional.empty(), weight, maxLevel, new Enchantment.Cost(0, 0), new Enchantment.Cost(0, 0), 0, slots);
				Enchantment.Builder baseEnchantment = new Enchantment.Builder(baseDefinition)
						.exclusiveSet(ctx.enchantments().getOrThrow(getExclusiveSetTag(key)));
				ctx.registerable().register(key, effectsBuilder.build(key, ctx, baseEnchantment).build(key.getValue()));
				
				// Build the cloak enchantment
				LootCondition.Builder isPlayerCondition = EntityPropertiesLootCondition.builder(LootContext.EntityTarget.THIS, EntityPredicate.Builder.create().type(EntityType.PLAYER));
				LootCondition.Builder hasAdvancementCondition = EntityPropertiesLootCondition.builder(LootContext.EntityTarget.THIS, EntityPredicate.Builder.create().typeSpecific(PlayerPredicate.Builder.create().advancement(advancementId, true).build()));
				LootCondition.Builder condition = isPlayerCondition.invert().or(hasAdvancementCondition);
				Enchantment.Definition cloakDefinition = new Enchantment.Definition(ctx.items().getOrThrow(getEnchantableTag(key)), Optional.empty(), weight, maxLevel, minCost, maxCost, anvilCost, slots);
				Enchantment.Builder cloakEnchantment = new Enchantment.Builder(cloakDefinition)
						.exclusiveSet(ctx.enchantments().getOrThrow(getExclusiveSetTag(key)))
						.addEffect(SpectrumEnchantmentEffectComponentTypes.CLOAKED, ctx.enchantments().getOrThrow(key), condition);
				ctx.registerable().register(cloakKey, cloakEnchantment.build(key.getValue()));
			});
			
			ENCHANTMENT_TAG_DEFERRER.defer(enchantmentKey, (key, ctx) -> {
				// Build the cloaking pair enchantment tag (e.g., resonance + cloaked/resonance)
				ctx.build(getPairTag(key)).add(key).add(cloakKey);
				
				// Build the exclusive set enchantment tag
				exclusiveSetBuilder.build(key, ctx.build(getExclusiveSetTag(key)).forceAddTag(getPairTag(key)));
			});
			
			ITEM_TAG_DEFERRER.defer(enchantmentKey, (key, ctx) -> {
				// Build the enchantable items tag
				enchantableBuilder.build(ctx.build(getEnchantableTag(key)));
			});
			
			return cloakKey;
		}
		
	}
	
	public static void provideItemTags(ProvidedTagBuilderBuilder<Item> builder) {
		ITEM_TAG_DEFERRER.flush(builder);
	}
	
	public static void provideEnchantmentTags(ProvidedTagBuilderBuilder<Enchantment> builder) {
		FabricTagProvider<Enchantment>.FabricTagBuilder enchantments = builder.build(SpectrumEnchantmentTags.ENCHANTMENTS);
		FabricTagProvider<Enchantment>.FabricTagBuilder cloaked = builder.build(SpectrumEnchantmentTags.CLOAKED);
		ENCHANTMENT_TAG_DEFERRER.streamKeys().sorted(Comparator.comparing(RegistryKey::getValue)).forEach(key -> {
			enchantments.forceAddTag(getPairTag(key));
			cloaked.add(getCloakKey(key));
		});
		
		ENCHANTMENT_TAG_DEFERRER.flush(builder);
	}
	
	public static void provideEnchantments(Registerable<Enchantment> registerable) {
		BOOTSTRAP_DEFERRER.flush(new BootstrapContext<>(registerable));
	}
	
	private interface EnchantmentBuilderCallback {
		Enchantment.Builder build(RegistryKey<Enchantment> key, BootstrapContext<Enchantment> ctx, Enchantment.Builder builder);
	}
	
}
