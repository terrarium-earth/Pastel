package de.dafuqs.spectrum.items.magic_items.ampoules;

import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.api.item.*;
import de.dafuqs.spectrum.entity.entity.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.component.type.*;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.*;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.*;
import net.minecraft.sound.*;
import net.minecraft.text.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class BloodstoneGlassAmpouleItem extends GlassAmpouleItem implements PrioritizedEntityInteraction {
	
	protected static final float EXTRA_REACH = 12.0F;
	protected static final Identifier REACH_ENTITY_INTERACTION_MODIFIER_ID = SpectrumCommon.locate("bloodstone_glass_ampoule_reach");
	
	public BloodstoneGlassAmpouleItem(Settings settings) {
		super(settings);
	}
	
	public static AttributeModifiersComponent createAttributeModifiers() {
		return AttributeModifiersComponent.builder()
				.add(EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE, new EntityAttributeModifier(REACH_ENTITY_INTERACTION_MODIFIER_ID, EXTRA_REACH, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
				.build();
	}
	
	@Override
	public boolean trigger(World world, ItemStack stack, LivingEntity attacker, @Nullable LivingEntity target, Vec3d position) {
		world.playSoundAtBlockCenter(BlockPos.ofFloored(position), SpectrumSoundEvents.LIGHT_CRYSTAL_RING, SoundCategory.PLAYERS, 0.35F, 0.9F + world.getRandom().nextFloat() * 0.334F, true);
		LightSpearEntity.summonBarrage(world, attacker, target, LightShardBaseEntity.MONSTER_TARGET, position, LightShardBaseEntity.DEFAULT_COUNT_PROVIDER);
		return true;
	}

	@Override
	public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
		super.appendTooltip(stack, context, tooltip, type);
		tooltip.add(Text.translatable("item.spectrum.bloodstone_glass_ampoule.tooltip").formatted(Formatting.GRAY));
	}

}
