package de.dafuqs.spectrum.blocks.particle_spawner;

import com.mojang.serialization.MapCodec;
import de.dafuqs.spectrum.api.item.CreativeOnlyItem;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class CreativeParticleSpawnerBlock extends AbstractParticleSpawnerBlock implements CreativeOnlyItem {

	public static final MapCodec<CreativeParticleSpawnerBlock> CODEC = simpleCodec(CreativeParticleSpawnerBlock::new);

	public CreativeParticleSpawnerBlock(Properties settings) {
		super(settings);
	}

	@Override
	public MapCodec<? extends CreativeParticleSpawnerBlock> codec() {
		return CODEC;
	}

	@Override
	public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag type) {
		super.appendHoverText(stack, context, tooltip, type);
		tooltip.add(Component.translatable("block.pastel.creative_particle_spawner.tooltip").withStyle(ChatFormatting.GRAY));
		CreativeOnlyItem.appendTooltip(tooltip);
	}
	
	@Override
	public boolean shouldSpawnParticles(Level world, BlockPos pos) {
		return true;
	}
	
}
