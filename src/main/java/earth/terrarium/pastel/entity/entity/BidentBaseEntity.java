package earth.terrarium.pastel.entity.entity;

import com.cmdpro.databank.misc.ColorGradient;
import com.cmdpro.databank.misc.TrailLeftoverHandler;
import com.cmdpro.databank.misc.TrailRender;
import com.cmdpro.databank.rendering.RenderHandler;
import com.cmdpro.databank.rendering.RenderTypeHandler;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.helpers.data.CodecHelper;
import earth.terrarium.pastel.helpers.enchantments.Ench;
import earth.terrarium.pastel.mixin.accessors.TridentEntityAccessor;
import earth.terrarium.pastel.registries.PastelSoundEvents;
import net.minecraft.client.renderer.LightTexture;
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

import java.awt.*;

public abstract class BidentBaseEntity extends ThrownTrident {

    protected static final EntityDataAccessor<ItemStack> STACK = SynchedEntityData.defineId(
        BidentBaseEntity.class, EntityDataSerializers.ITEM_STACK);

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
        this.entityData.set(
            TridentEntityAccessor.getLoyalty(),
            (byte) Ench.getLevel(level().registryAccess(), Enchantments.LOYALTY, stack)
        );
        this.entityData.set(TridentEntityAccessor.getEnchanted(), stack.hasFoil());
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

    @Override
    public void onClientRemoval() {
        super.onClientRemoval();
        TrailRender render = getTrail();
        if (render != null) {
            TrailLeftoverHandler.addTrail(render, RenderHandler.createBufferSource(), LightTexture.FULL_BRIGHT, getGradient());
            shouldRenderTrail = false;
        }
    }

    private boolean shouldRenderTrail = true;
    private TrailRender trail;
    public ColorGradient getGradient() {
        return new ColorGradient(
            new Color(181, 255, 254),
            new Color(149, 182, 255)
        ).fadeAlpha(1, 0).fadeAlpha(0, 0, 1, 0.05f);
    }
    public TrailRender getTrail() {
        if (!shouldRenderTrail) {
            return null;
        }
        if (trail == null) {
            trail = new TrailRender(position(), 20, 20, 0.15f, PastelCommon.locate("textures/misc/trail/trail.png"),
                                    RenderTypeHandler::transparent
            ).setShrink(true).startTicking();
        }
        return trail;
    }
}
