package de.dafuqs.spectrum.entity.entity;

import de.dafuqs.spectrum.entity.SpectrumEntityTypes;
import de.dafuqs.spectrum.registries.SpectrumItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class PhantomGlowFrameEntity extends PhantomFrameEntity {
	
	public PhantomGlowFrameEntity(EntityType<? extends ItemFrame> entityType, Level world) {
		super(entityType, world);
	}
	
	public PhantomGlowFrameEntity(Level world, BlockPos pos, Direction facing) {
		this(SpectrumEntityTypes.GLOW_PHANTOM_FRAME, world, pos, facing);
	}
	
	public PhantomGlowFrameEntity(EntityType<? extends ItemFrame> type, Level world, BlockPos pos, Direction facing) {
		super(type, world, pos, facing);
	}
	
	@Override
	protected ItemStack getFrameItemStack() {
		return new ItemStack(SpectrumItems.GLOW_PHANTOM_FRAME);
	}
	
	@Override
	public SoundEvent getRemoveItemSound() {
		return SoundEvents.GLOW_ITEM_FRAME_REMOVE_ITEM;
	}
	
	@Override
	public SoundEvent getBreakSound() {
		return SoundEvents.GLOW_ITEM_FRAME_BREAK;
	}
	
	@Override
	public SoundEvent getPlaceSound() {
		return SoundEvents.GLOW_ITEM_FRAME_PLACE;
	}
	
	@Override
	public SoundEvent getAddItemSound() {
		return SoundEvents.GLOW_ITEM_FRAME_ADD_ITEM;
	}
	
	@Override
	public SoundEvent getRotateItemSound() {
		return SoundEvents.GLOW_ITEM_FRAME_ROTATE_ITEM;
	}
	
	@Override
	public boolean shouldRenderAtMaxLight() {
		return !isRedstonePowered();
	}
	
}
