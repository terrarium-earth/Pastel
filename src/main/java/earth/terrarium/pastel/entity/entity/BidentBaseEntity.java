package earth.terrarium.pastel.entity.entity;

import earth.terrarium.pastel.helpers.CodecHelper;
import earth.terrarium.pastel.helpers.PastelEnchantmentHelper;
import earth.terrarium.pastel.mixin.accessors.TridentEntityAccessor;
import earth.terrarium.pastel.registries.PastelSoundEvents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public abstract class BidentBaseEntity extends ThrownTrident {
	
	protected static final EntityDataAccessor<ItemStack> STACK = SynchedEntityData.defineId(BidentBaseEntity.class, EntityDataSerializers.ITEM_STACK);
	
	public BidentBaseEntity(EntityType<? extends ThrownTrident> entityType, Level world) {
		super(entityType, world);
	}
	
	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		super.defineSynchedData(builder);
		builder.define(STACK, Items.AIR.getDefaultInstance());
	}
	
	@Override
	public void setPickupItemStack(ItemStack stack) {
		setTrackedStack(stack.copy());
		super.setPickupItemStack(stack);
		this.entityData.set(TridentEntityAccessor.spectrum$getLoyalty(), (byte) PastelEnchantmentHelper.getLevel(level().registryAccess(), Enchantments.LOYALTY, stack));
		this.entityData.set(TridentEntityAccessor.spectrum$getEnchanted(), stack.hasFoil());
	}
	
	@Override
	protected SoundEvent getDefaultHitGroundSoundEvent() {
		return PastelSoundEvents.BIDENT_HIT_GROUND;
	}
	
	public ItemStack getTrackedStack() {
		return this.entityData.get(STACK);
	}
	
	public void setTrackedStack(ItemStack stack) {
		entityData.set(STACK, stack);
	}
	
	@Override
	public void readAdditionalSaveData(CompoundTag nbt) {
		super.readAdditionalSaveData(nbt);
		this.entityData.set(STACK, CodecHelper.fromNbt(ItemStack.CODEC, nbt.get("item"), ItemStack.EMPTY));
	}
	
	@Override
	public void addAdditionalSaveData(CompoundTag nbt) {
		super.addAdditionalSaveData(nbt);
	}
	
	@Override
	public AABB makeBoundingBox() {
		return super.makeBoundingBox();
	}
}
