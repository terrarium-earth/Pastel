package de.dafuqs.spectrum.items.magic_items.ampoules;

import de.dafuqs.spectrum.entity.entity.LightShardBaseEntity;
import de.dafuqs.spectrum.entity.entity.LightShardEntity;
import de.dafuqs.spectrum.registries.SpectrumSoundEvents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AzuriteGlassAmpouleItem extends GlassAmpouleItem {
	
	public AzuriteGlassAmpouleItem(Properties settings) {
		super(settings);
	}
	
	@Override
	public boolean trigger(Level world, ItemStack stack, LivingEntity attacker, @Nullable LivingEntity target, Vec3 position) {
		world.playLocalSound(BlockPos.containing(position), SpectrumSoundEvents.LIGHT_CRYSTAL_RING, SoundSource.PLAYERS, 0.35F, 0.9F + world.getRandom().nextFloat() * 0.334F, true);
		LightShardEntity.summonBarrage(world, attacker, target, LightShardBaseEntity.MONSTER_TARGET, position, LightShardBaseEntity.DEFAULT_COUNT_PROVIDER);
		return true;
	}
	
	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
		super.appendHoverText(stack, context, tooltip, type);
		tooltip.add(Component.translatable("item.pastel.azurite_glass_ampoule.tooltip").withStyle(ChatFormatting.GRAY));
	}
	
}
