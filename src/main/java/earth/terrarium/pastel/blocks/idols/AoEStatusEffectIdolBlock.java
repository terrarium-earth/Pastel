package earth.terrarium.pastel.blocks.idols;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AoEStatusEffectIdolBlock extends IdolBlock {
	
	protected final int range;
	protected final Holder<MobEffect> statusEffect;
	protected final int amplifier;
	protected final int duration;
	
	public AoEStatusEffectIdolBlock(Properties settings, ParticleOptions particleEffect, Holder<MobEffect> statusEffect, int amplifier, int duration, int range) {
		super(settings, particleEffect);
		this.statusEffect = statusEffect;
		this.amplifier = amplifier;
		this.duration = duration;
		this.range = range;
	}

	@Override
	public MapCodec<? extends AoEStatusEffectIdolBlock> codec() {
		//TODO: Make the codec
		return null;
	}
	@Override
	public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag type) {
		super.appendHoverText(stack, context, tooltip, type);
		tooltip.add(Component.translatable("block.pastel.echolocating_idol.tooltip", range));
	}
	
	@Override
	public boolean trigger(ServerLevel world, BlockPos blockPos, BlockState state, @Nullable Entity entity, Direction side) {
		int boxSize = range + range;
		List<LivingEntity> livingEntities = world.getEntitiesOfClass(LivingEntity.class, AABB.ofSize(Vec3.atCenterOf(blockPos), boxSize, boxSize, boxSize));
		for (LivingEntity livingEntity : livingEntities) {
			livingEntity.addEffect(new MobEffectInstance(statusEffect, duration, amplifier));
		}
		return true;
	}
	
}
