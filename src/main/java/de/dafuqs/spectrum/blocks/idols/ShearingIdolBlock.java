package de.dafuqs.spectrum.blocks.idols;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Shearable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ShearingIdolBlock extends IdolBlock {
	
	protected final int range;
	
	public ShearingIdolBlock(Properties settings, ParticleOptions particleEffect, int range) {
		super(settings, particleEffect);
		this.range = range;
	}

	@Override
	public MapCodec<? extends ShearingIdolBlock> codec() {
		//TODO: Make the codec
		return null;
	}

	@Override
	public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag type) {
		super.appendHoverText(stack, context, tooltip, type);
		tooltip.add(Component.translatable("block.spectrum.shearing_idol.tooltip"));
	}
	
	@Override
	public boolean trigger(ServerLevel world, BlockPos blockPos, BlockState state, @Nullable Entity entity, Direction side) {
		int boxSize = range + range;
		
		List<LivingEntity> entities = world.getEntitiesOfClass(LivingEntity.class, AABB.ofSize(Vec3.atCenterOf(blockPos), boxSize, boxSize, boxSize));
		for (LivingEntity currentEntity : entities) {
			if (currentEntity instanceof Shearable shearable && shearable.readyForShearing()) {
				shearable.shear(SoundSource.BLOCKS);
			}
		}
		return true;
	}
	
}
