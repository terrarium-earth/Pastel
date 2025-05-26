package de.dafuqs.spectrum.blocks.idols;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ExplosionIdolBlock extends IdolBlock {
	
	protected final float power;
	protected final boolean createFire;
	protected final Explosion.BlockInteraction destructionType;
	
	public ExplosionIdolBlock(Properties settings, ParticleOptions particleEffect, float power, boolean createFire, Explosion.BlockInteraction destructionType) {
		super(settings, particleEffect);
		this.power = power;
		this.createFire = createFire;
		this.destructionType = destructionType;
	}

	@Override
	public MapCodec<? extends ExplosionIdolBlock> codec() {
		//TODO: Make the codec
		return null;
	}

	@Override
	public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag type) {
		super.appendHoverText(stack, context, tooltip, type);
		tooltip.add(Component.translatable("block.pastel.explosion_idol.tooltip", power));
	}
	
	@Override
	public boolean trigger(ServerLevel world, final BlockPos blockPos, BlockState state, @Nullable Entity entity, Direction side) {
		// why power + 1 you ask? Since the explosion happens inside the block, some explosion power
		// is blocked by this block itself, weakening it. So to better match the original value we have to make it a tad stronger
		world.explode(null, world.damageSources().explosion(entity, null), new SpareBlockExplosionBehavior(blockPos), blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5, this.power + 1, this.createFire, Level.ExplosionInteraction.BLOCK);
		return true;
	}
	
	/**
	 * Overriding canDestroyBlock makes it so the mob block itself does not get destroyed
	 * Increasing its hardness would make the block immune to other explosions, too
	 * and would not let explosions happen from the center of it
	 */
	private static class SpareBlockExplosionBehavior extends ExplosionDamageCalculator {
		
		public final BlockPos sparedPos;
		
		public SpareBlockExplosionBehavior(BlockPos sparedPos) {
			this.sparedPos = sparedPos;
		}
		
		@Override
		public boolean shouldBlockExplode(Explosion explosion, BlockGetter world, BlockPos pos, BlockState state, float power) {
			return !pos.equals(sparedPos) && super.shouldBlockExplode(explosion, world, pos, state, power);
		}
	}
	
}
