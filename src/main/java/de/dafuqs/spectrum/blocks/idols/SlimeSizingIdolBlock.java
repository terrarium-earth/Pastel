package de.dafuqs.spectrum.blocks.idols;

import com.mojang.serialization.MapCodec;
import de.dafuqs.spectrum.mixin.accessors.SlimeEntityAccessor;
import de.dafuqs.spectrum.networking.s2c_payloads.PlayParticleWithRandomOffsetAndVelocityPayload;
import de.dafuqs.spectrum.progression.SpectrumAdvancementCriteria;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SlimeSizingIdolBlock extends IdolBlock {
	
	protected final int maxSize; // Huge Chungus
	protected final int range;
	
	public SlimeSizingIdolBlock(Properties settings, ParticleOptions particleEffect, int range, int maxSize) {
		super(settings, particleEffect);
		this.range = range;
		this.maxSize = maxSize;
	}

	@Override
	public MapCodec<? extends SlimeSizingIdolBlock> codec() {
		//TODO: Make the codec
		return null;
	}

	@Override
	public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag type) {
		super.appendHoverText(stack, context, tooltip, type);
		tooltip.add(Component.translatable("block.spectrum.slime_sizing_idol.tooltip"));
	}
	
	@Override
	public boolean trigger(ServerLevel world, BlockPos blockPos, BlockState state, @Nullable Entity entity, Direction side) {
		int boxSize = range + range;
		List<Slime> slimeEntities = world.getEntitiesOfClass(Slime.class, AABB.ofSize(Vec3.atCenterOf(blockPos), boxSize, boxSize, boxSize));
		for (Slime slimeEntity : slimeEntities) {
			if (slimeEntity.getSize() < maxSize) {
				int newSize = slimeEntity.getSize() + 1;
				// make bigger
				((SlimeEntityAccessor) slimeEntity).invokeSetSize(newSize, true);
				
				// play particles and sound
				PlayParticleWithRandomOffsetAndVelocityPayload.playParticleWithRandomOffsetAndVelocity(world, Vec3.atCenterOf(blockPos), ((SlimeEntityAccessor) slimeEntity).invokeGetParticleType(), 16, new Vec3(0.75, 0.75, 0.75), new Vec3(0.1, 0.1, 0.1));
				
				AABB boundingBox = slimeEntity.getBoundingBox();
				PlayParticleWithRandomOffsetAndVelocityPayload.playParticleWithRandomOffsetAndVelocity(world, slimeEntity.position().add(0, boundingBox.getYsize() / 2, 0), ((SlimeEntityAccessor) slimeEntity).invokeGetParticleType(), newSize * 8, new Vec3(boundingBox.getXsize(), boundingBox.getYsize(), boundingBox.getZsize()), new Vec3(0.1, 0.1, 0.1));
				slimeEntity.playSound(((SlimeEntityAccessor) slimeEntity).invokeGetSquishSound(), ((SlimeEntityAccessor) slimeEntity).invokeGetSoundVolume(), ((world.random.nextFloat() - world.random.nextFloat()) * 0.2F + 1.0F) / 0.8F);
				
				// grant advancements
				if (entity instanceof ServerPlayer serverPlayerEntity) {
					SpectrumAdvancementCriteria.SLIME_SIZING.trigger(serverPlayerEntity, newSize);
				}
				return true;
			}
		}
		return true;
	}
	
}
