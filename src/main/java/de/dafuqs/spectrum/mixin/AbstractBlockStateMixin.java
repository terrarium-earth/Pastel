package de.dafuqs.spectrum.mixin;

import de.dafuqs.spectrum.api.interaction.ResonanceProcessor;
import de.dafuqs.spectrum.registries.SpectrumEnchantmentTags;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class AbstractBlockStateMixin {
	
	@ModifyVariable(method = "spawnAfterBreak", at = @At("HEAD"), ordinal = 0, argsOnly = true)
	public boolean spectrum$preventXPDropsWhenUsingResonance(boolean dropExperience, ServerLevel world, BlockPos pos, ItemStack stack) {
		if (ResonanceProcessor.preventNextXPDrop && EnchantmentHelper.hasTag(stack, SpectrumEnchantmentTags.RESONANT_BLOCK_DROPS)) {
			ResonanceProcessor.preventNextXPDrop = false;
			return false;
		}
		return dropExperience;
	}
	
}
