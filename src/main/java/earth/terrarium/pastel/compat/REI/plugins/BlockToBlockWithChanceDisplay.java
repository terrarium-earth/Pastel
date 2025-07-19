package earth.terrarium.pastel.compat.REI.plugins;

import earth.terrarium.pastel.compat.REI.GatedRecipeDisplay;
import earth.terrarium.pastel.mixin.accessors.FluidBlockAccessor;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class BlockToBlockWithChanceDisplay extends BasicDisplay implements GatedRecipeDisplay {
	
	public final float chance;
	
	public BlockToBlockWithChanceDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs, float chance) {
		super(inputs, outputs);
		this.chance = chance;
	}
	
	public static EntryStack<?> blockToEntryStack(Block block) {
		if (block instanceof LiquidBlock inFluidBlock) {
			return EntryStacks.of(((FluidBlockAccessor) inFluidBlock).getFlowableFluid());
		} else {
			return EntryStacks.of(block);
		}
		
	}
	
	public final float getChance() {
		return chance;
	}
	
	@Override
	public boolean isSecret() {
		return false;
	}
	
	@Override
	public @Nullable Component getSecretHintText() {
		return null;
	}
	
}