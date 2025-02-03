package de.dafuqs.spectrum.explosion;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.api.item.*;
import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.server.world.*;
import net.minecraft.text.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import org.jetbrains.annotations.*;

import java.util.*;

/**
 * A Set of ExplosionModifiers
 * - serializable as SpectrumDataComponentTypes.MODULAR_EXPLOSION
 * - implements the actual explosion logic
 */
public class ModularExplosionDefinition {
	
	public static final Codec<ModularExplosionDefinition> CODEC = RecordCodecBuilder.create(i -> i.group(
			StringIdentifiable.createCodec(ExplosionArchetype::values).fieldOf("archetype").forGetter(c -> c.archetype),
			SpectrumRegistries.EXPLOSION_MODIFIERS.getCodec().listOf().optionalFieldOf("modifiers", List.of()).forGetter(c -> c.modifiers)
	).apply(i, ModularExplosionDefinition::new));
	
	public static final PacketCodec<RegistryByteBuf, ModularExplosionDefinition> PACKET_CODEC = PacketCodec.tuple(
			PacketCodecHelper.enumOf(ExplosionArchetype::values), c -> c.archetype,
			PacketCodecs.registryValue(SpectrumRegistries.EXPLOSION_MODIFIERS.getKey()).collect(PacketCodecs.toList()), c -> c.modifiers,
			ModularExplosionDefinition::new
	);
	
	protected ExplosionArchetype archetype = ExplosionArchetype.COSMETIC;
	protected List<ExplosionModifier> modifiers;
	
	public ModularExplosionDefinition() {
		this.modifiers = new ArrayList<>();
	}
	
	public ModularExplosionDefinition(ExplosionArchetype archetype, List<ExplosionModifier> modifiers) {
		this.archetype = archetype;
		this.modifiers = modifiers;
	}
	
	public void addModifiers(List<ExplosionModifier> modifiers) {
		this.modifiers.addAll(modifiers);
	}
	
	public void setArchetype(ExplosionArchetype archetype) {
		this.archetype = archetype;
	}
	
	public ExplosionArchetype getArchetype() {
		return archetype;
	}
	
	public boolean isValid(ModularExplosionProvider provider) {
		if (this.modifiers.size() > provider.getMaxExplosionModifiers()) {
			return false;
		}
		
		Map<ExplosionModifierType, Integer> occurrences = new HashMap<>();
		for (ExplosionModifier modifier : modifiers) {
			if (!modifier.type.acceptsArchetype(archetype)) {
				return false;
			}
			ExplosionModifierType type = modifier.getType();
			int typeCount = occurrences.getOrDefault(type, 0);
			if (typeCount > type.getMaxModifiersForType()) {
				return false;
			}
			occurrences.put(type, typeCount + 1);
		}
		
		return true;
	}
	
	public int getModifierCount() {
		return this.modifiers.size();
	}
	
	public static ModularExplosionDefinition getFromStack(ItemStack stack) {
		return stack.getOrDefault(SpectrumDataComponentTypes.MODULAR_EXPLOSION, new ModularExplosionDefinition());
	}
	
	public void attachToStack(ItemStack stack) {
		stack.set(SpectrumDataComponentTypes.MODULAR_EXPLOSION, this);
	}
	
	public static void removeFromStack(ItemStack stack) {
		stack.remove(SpectrumDataComponentTypes.MODULAR_EXPLOSION);
	}
	
	// Tooltips
	public void appendTooltip(List<Text> tooltip, ModularExplosionProvider provider) {
		int modifierCount = this.modifiers.size();
		int maxModifierCount = provider.getMaxExplosionModifiers();
		
		tooltip.add(archetype.getName());
		tooltip.add(Text.translatable("item.spectrum.tooltip.explosives.remaining_slots", modifierCount, maxModifierCount).formatted(Formatting.GRAY));
		
		if (modifierCount == 0) {
			tooltip.add(Text.translatable("item.spectrum.tooltip.explosives.modifiers").formatted(Formatting.GRAY));
		} else {
			for (ExplosionModifier explosionModifier : modifiers) {
				tooltip.add(explosionModifier.getName());
			}
		}
	}
	
	// Calls the explosion logic
	public void explode(@NotNull ServerWorld world, BlockPos pos, @Nullable PlayerEntity owner, double baseBlastRadius, float baseDamage) {
		ModularExplosion.explode(world, pos, owner, baseBlastRadius, baseDamage, this.archetype, this.modifiers);
	}
	
	// Calls the explosion logic
	public static void explode(@NotNull ServerWorld world, BlockPos pos, @Nullable PlayerEntity owner, ItemStack stack) {
		if (stack.getItem() instanceof ModularExplosionProvider provider) {
			ModularExplosionDefinition definition = getFromStack(stack);
			ModularExplosion.explode(world, pos, owner, provider.getBaseExplosionBlastRadius(), provider.getBaseExplosionDamage(), definition.archetype, definition.modifiers);
		}
	}
	
	public static void explode(@NotNull ServerWorld world, BlockPos pos, Direction direction, @Nullable PlayerEntity owner, ItemStack stack) {
		if (stack.getItem() instanceof ModularExplosionProvider provider) {
			ModularExplosionDefinition definition = getFromStack(stack);
			BlockPos finalPos = pos.offset(direction, (int) provider.getBaseExplosionBlastRadius() - 2); // TODO: Add distance added via blast range modification
			ModularExplosion.explode(world, finalPos, owner, provider.getBaseExplosionBlastRadius(), provider.getBaseExplosionDamage(), definition.archetype, definition.modifiers);
		}
	}
	
}
