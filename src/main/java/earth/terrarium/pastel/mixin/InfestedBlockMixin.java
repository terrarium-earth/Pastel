package earth.terrarium.pastel.mixin;

import earth.terrarium.pastel.registries.PastelEnchantmentTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.InfestedBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InfestedBlock.class)
public abstract class InfestedBlockMixin {
	
	/*
	 * Do not spawn silverfish when block is broken with Resonance Tool
	 */
	@Inject(at = @At("HEAD"), method = "spawnAfterBreak", cancellable = true)
	public void onStacksDropped(BlockState state, ServerLevel world, BlockPos pos, ItemStack stack, boolean dropExperience, CallbackInfo ci) {
		if (EnchantmentHelper.hasTag(stack, PastelEnchantmentTags.RESONANT_BLOCK_DROPS)) {
			ci.cancel();
		}
		
		if (EnchantmentHelper.hasTag(stack, PastelEnchantmentTags.AUTO_KILLS_SILVERFISH)) {
			Silverfish silverfishEntity = EntityType.SILVERFISH.create(world);
			if (silverfishEntity != null) {
				silverfishEntity.moveTo(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, 0.0F, 0.0F);
				world.addFreshEntity(silverfishEntity);
				silverfishEntity.spawnAnim();
				silverfishEntity.kill();
				
				ExperienceOrb experienceOrbEntity = new ExperienceOrb(world, pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, 10);
				world.addFreshEntity(experienceOrbEntity);
			}
			ci.cancel();
		}
	}
	
}
