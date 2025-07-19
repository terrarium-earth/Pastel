package earth.terrarium.pastel.registries;

import earth.terrarium.pastel.api.item.PresentUnpackBehavior;
import earth.terrarium.pastel.blocks.amalgam.IncandescentAmalgamBlock;
import earth.terrarium.pastel.blocks.memory.MemoryBlockEntity;
import earth.terrarium.pastel.blocks.present.PresentBlock;
import earth.terrarium.pastel.mixin.accessors.GoatHornItemAccessor;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.Instrument;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

public class PastelPresentUnpackBehaviors {
	
	public static void register() {
		PresentBlock.registerBehavior(PastelItems.PIPE_BOMB.getId(), (stack, presentBlockEntity, world, pos, random) -> {
			stack.set(PastelDataComponentTypes.TIMESTAMP, world.getGameTime() - 70);
			stack.set(DataComponents.PROFILE, presentBlockEntity.getOwner());
			world.playSound(null, pos, PastelSoundEvents.INCANDESCENT_ARM, SoundSource.BLOCKS, 2.0F, 0.9F);
			return stack;
		});
		
		PresentBlock.registerBehavior(PastelItems.STORM_STONE.getId(), (stack, presentBlockEntity, world, pos, random) -> {
			if (world.canSeeSky(pos)) {
				LightningBolt lightningEntity = EntityType.LIGHTNING_BOLT.create(world);
				if (lightningEntity != null) {
					lightningEntity.moveTo(Vec3.atBottomCenterOf(pos));
					world.addFreshEntity(lightningEntity);
				}
				return ItemStack.EMPTY;
			}
			return stack;
		});
		
		PresentBlock.registerBehavior(PastelBlocks.INCANDESCENT_AMALGAM.getId(), (stack, presentBlockEntity, world, pos, random) -> {
            IncandescentAmalgamBlock.explode(world, pos, presentBlockEntity.getOwnerIfOnline(), stack);
            return ItemStack.EMPTY;
        });
		
		PresentBlock.registerBehavior(BuiltInRegistries.ITEM.getKey(Items.FIREWORK_ROCKET), (stack, presentBlockEntity, world, pos, random) -> {
			Vec3 centerPos = Vec3.atLowerCornerOf(pos);
			for (int i = 0; i < stack.getCount(); i++) {
				FireworkRocketEntity fireworkRocketEntity = new FireworkRocketEntity(world, presentBlockEntity.getOwnerIfOnline(), centerPos.x + 0.35 + random.nextFloat() * 0.3, centerPos.y + 0.35 + random.nextFloat() * 0.3, centerPos.z + 0.35 + random.nextFloat() * 0.3, stack);
				world.addFreshEntity(fireworkRocketEntity);
			}
			return ItemStack.EMPTY;
		});
		
		PresentBlock.registerBehavior(BuiltInRegistries.ITEM.getKey(Items.GOAT_HORN), (stack, presentBlockEntity, world, pos, random) -> {
			Optional<Holder<Instrument>> optional = ((GoatHornItemAccessor) stack.getItem()).invokeGetInstrument(stack);
			if (optional.isPresent()) {
				Instrument instrument = optional.get().value();
				SoundEvent soundEvent = instrument.soundEvent().value();
				world.playSound(null, pos, soundEvent, SoundSource.RECORDS, instrument.range() / 16.0F, 1.0F);
			}
			return stack;
		});
		
		PresentBlock.registerBehavior(BuiltInRegistries.ITEM.getKey(Items.BELL), (stack, presentBlockEntity, world, pos, random) -> {
			world.playSound(null, pos, SoundEvents.BELL_BLOCK, SoundSource.BLOCKS, 2.0F, 1.0F);
			return stack;
		});
		
		PresentBlock.registerBehavior(BuiltInRegistries.ITEM.getKey(Items.TNT), (stack, presentBlockEntity, world, pos, random) -> {
			if (stack.getCount() > 0) {
				PrimedTnt tntEntity = null;
				for (int i = 0; i < stack.getCount(); i++) {
					tntEntity = new PrimedTnt(world, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, presentBlockEntity.getOwnerIfOnline());
					world.addFreshEntity(tntEntity);
				}
				world.playSound(null, tntEntity.getX(), tntEntity.getY(), tntEntity.getZ(), SoundEvents.TNT_PRIMED, SoundSource.BLOCKS, 1.0F, 1.0F);
				world.gameEvent(null, GameEvent.PRIME_FUSE, pos);
			}
			return ItemStack.EMPTY;
		});
		
		PresentUnpackBehavior POTION_BEHAVIOR = (stack, presentBlockEntity, world, pos, random) -> {
			Vec3 centerPos = Vec3.atCenterOf(pos);
			for (int i = 0; i < stack.getCount(); i++) {
				ThrownPotion entity = new ThrownPotion(world, centerPos.x(), centerPos.y(), centerPos.z());
				entity.setItem(stack);
				world.addFreshEntity(entity);
			}
			var component = stack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
			world.levelEvent(LevelEvent.PARTICLES_SPELL_POTION_SPLASH, pos, component.getColor());
			return ItemStack.EMPTY;
		};
		PresentBlock.registerBehavior(BuiltInRegistries.ITEM.getKey(Items.SPLASH_POTION), POTION_BEHAVIOR);
		PresentBlock.registerBehavior(BuiltInRegistries.ITEM.getKey(Items.LINGERING_POTION), POTION_BEHAVIOR);
		
		PresentBlock.registerBehavior(BuiltInRegistries.ITEM.getKey(Items.EXPERIENCE_BOTTLE), (stack, presentBlockEntity, world, pos, random) -> {
			int totalXP = 0;
			for (int i = 0; i < stack.getCount(); i++) {
				totalXP += 3 + random.nextInt(5) + random.nextInt(5);
			}
			
			world.levelEvent(LevelEvent.PARTICLES_SPELL_POTION_SPLASH, pos, new PotionContents(Potions.WATER).getColor());
			ExperienceOrb.award(world, Vec3.atCenterOf(pos), totalXP);
			return ItemStack.EMPTY;
		});
		
		PresentBlock.registerBehavior(BuiltInRegistries.ITEM.getKey(Items.EGG), (stack, presentBlockEntity, world, pos, random) -> {
			int chickenCount = stack.getCount(); // every egg hatches, unlike via EggEntity. New chicken farm just dropped?
			for (int i = 0; i < chickenCount; i++) {
				Chicken chickenEntity = EntityType.CHICKEN.create(world);
				if (chickenEntity != null) {
					chickenEntity.setAge(-24000);
					chickenEntity.moveTo(pos.getX(), pos.getY(), pos.getZ(), 0.0F, 0.0F);
					world.addFreshEntity(chickenEntity);
				}
			}
			
			return ItemStack.EMPTY;
		});
		
		PresentBlock.registerBehavior(PastelBlocks.MEMORY.getId(), (stack, presentBlockEntity, world, pos, random) -> {
            MemoryBlockEntity.manifest(world, pos, stack, presentBlockEntity.getOpenerUUID());
            return ItemStack.EMPTY;
        });
		
	}
	
	
}
