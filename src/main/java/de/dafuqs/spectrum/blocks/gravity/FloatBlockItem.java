package de.dafuqs.spectrum.blocks.gravity;

import de.dafuqs.spectrum.api.item.GravitableItem;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class FloatBlockItem extends BlockItem implements GravitableItem {
	
	protected final float gravityMod;
	
	public FloatBlockItem(Block block, Properties settings, float gravityMod) {
		super(block, settings);
		this.gravityMod = gravityMod;
	}
	
	@Override
	public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
		applyGravity(stack, world, entity);
	}
	
	@Override
	public float getGravityMod(ItemStack stack) {
		return this.gravityMod;
	}
	
}
