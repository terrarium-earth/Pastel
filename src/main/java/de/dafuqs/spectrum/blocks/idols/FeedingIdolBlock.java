package de.dafuqs.spectrum.blocks.idols;

import com.mojang.serialization.MapCodec;
import de.dafuqs.spectrum.helpers.InWorldInteractionHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityEvent;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FeedingIdolBlock extends IdolBlock {
	
	protected static final int LOVE_TICKS = 600;
	protected final int range;
	
	public FeedingIdolBlock(Properties settings, ParticleOptions particleEffect, int range) {
		super(settings, particleEffect);
		this.range = range;
	}

	@Override
	public MapCodec<? extends FeedingIdolBlock> codec() {
		//TODO: Make the codec
		return null;
	}

	@Override
	public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag type) {
		super.appendHoverText(stack, context, tooltip, type);
		tooltip.add(Component.translatable("block.spectrum.feeding_idol.tooltip", this.range));
	}
	
	@Override
	public boolean trigger(ServerLevel world, BlockPos blockPos, BlockState state, @Nullable Entity entity, Direction side) {
		int boxSize = range + range;
		
		// Query entities once and reuse below. Otherwise, it will get computationally expensive
		AABB box = AABB.ofSize(Vec3.atCenterOf(blockPos), boxSize, boxSize, boxSize);
		List<Animal> animalEntities = world.getEntitiesOfClass(Animal.class, box);
		List<ItemEntity> itemEntities = world.getEntitiesOfClass(ItemEntity.class, box);
		
		// put grown animals in love
		for (Animal animalEntity : animalEntities) {
			if (animalEntity.getAge() == 0 && !animalEntity.isInLove()) { // getBreedingAge() automatically checks for !isChild()
				for (ItemEntity itemEntity : itemEntities) {
					ItemStack stack = itemEntity.getItem();
					if (animalEntity.isFood(stack)) {
						InWorldInteractionHelper.decrementAndSpawnRemainder(itemEntity, 1);
						
						animalEntity.setInLoveTime(LOVE_TICKS);
						world.broadcastEntityEvent(animalEntity, EntityEvent.IN_LOVE_HEARTS);
					}
				}
			}
		}
		
		// use remaining items to grow up animals
		for (Animal animalEntity : animalEntities) {
			if (animalEntity.isBaby()) {
				for (ItemEntity itemEntity : itemEntities) {
					ItemStack stack = itemEntity.getItem();
					if (animalEntity.isFood(stack)) {
						InWorldInteractionHelper.decrementAndSpawnRemainder(itemEntity, 1);
						
						animalEntity.ageUp((int) ((float) (-animalEntity.getAge() / 20) * 0.1F), true);
						animalEntity.gameEvent(GameEvent.ENTITY_INTERACT);
					}
				}
			}
		}
		
		return true;
	}
	
}
