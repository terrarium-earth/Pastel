package earth.terrarium.pastel.items.magic_items.ampoules;

import earth.terrarium.pastel.SpectrumCommon;
import earth.terrarium.pastel.api.item.PrioritizedEntityInteraction;
import earth.terrarium.pastel.entity.entity.LightShardBaseEntity;
import earth.terrarium.pastel.entity.entity.LightSpearEntity;
import earth.terrarium.pastel.registries.SpectrumSoundEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BloodstoneGlassAmpouleItem extends GlassAmpouleItem implements PrioritizedEntityInteraction {
	
	protected static final float EXTRA_REACH = 12.0F;
	protected static final ResourceLocation REACH_ENTITY_INTERACTION_MODIFIER_ID = SpectrumCommon.locate("bloodstone_glass_ampoule_reach");
	
	public BloodstoneGlassAmpouleItem(Properties settings) {
		super(settings);
	}
	
	public static ItemAttributeModifiers createAttributeModifiers() {
		return ItemAttributeModifiers.builder()
				.add(Attributes.ENTITY_INTERACTION_RANGE, new AttributeModifier(REACH_ENTITY_INTERACTION_MODIFIER_ID, EXTRA_REACH, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
				.build();
	}
	
	@Override
	public boolean trigger(Level world, ItemStack stack, LivingEntity attacker, @Nullable LivingEntity target, Vec3 position) {
		world.playLocalSound(BlockPos.containing(position), SpectrumSoundEvents.LIGHT_CRYSTAL_RING, SoundSource.PLAYERS, 0.35F, 0.9F + world.getRandom().nextFloat() * 0.334F, true);
		LightSpearEntity.summonBarrage(world, attacker, target, LightShardBaseEntity.MONSTER_TARGET, position, LightShardBaseEntity.DEFAULT_COUNT_PROVIDER);
		return true;
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
		super.appendHoverText(stack, context, tooltip, type);
		tooltip.add(Component.translatable("item.pastel.bloodstone_glass_ampoule.tooltip").withStyle(ChatFormatting.GRAY));
	}

}
