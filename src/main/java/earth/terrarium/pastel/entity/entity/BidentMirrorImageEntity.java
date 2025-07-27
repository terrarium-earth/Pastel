package earth.terrarium.pastel.entity.entity;

import com.cmdpro.databank.misc.ColorGradient;
import earth.terrarium.pastel.api.energy.color.InkColors;
import earth.terrarium.pastel.entity.PastelEntityTypes;
import earth.terrarium.pastel.helpers.enchantments.Ench;
import earth.terrarium.pastel.particle.PastelParticleTypes;
import earth.terrarium.pastel.registries.PastelSounds;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

public class BidentMirrorImageEntity extends BidentBaseEntity {
    public BidentMirrorImageEntity(Level world) {
        this(PastelEntityTypes.BIDENT_MIRROR_IMAGE.get(), world);
    }

    public BidentMirrorImageEntity(EntityType<? extends ThrownTrident> entityType, Level world) {
        super(entityType, world);
        this.pickup = Pickup.DISALLOWED;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level()
                .isClientSide()) {
            this.level()
                .addParticle(
                    PastelParticleTypes.MIRROR_IMAGE, this.getRandomX(0.5), this.getRandomY(), this.getRandomZ(0.5), 0,
                    0, 0
                );
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        Level world = this.level();
        world.playSound(
            null, entityHitResult.getEntity()
                                 .blockPosition(), PastelSounds.MEDIUM_CRYSTAL_RING, SoundSource.PLAYERS, 1.334F,
            0.9F + random.nextFloat() * 0.334F
        );
        world.playSound(
            null, entityHitResult.getEntity()
                                 .blockPosition(), PastelSounds.SHATTER_HEAVY, SoundSource.PLAYERS, 0.75F,
            1.0F + random.nextFloat() * 0.2F
        );

        if (!world.isClientSide) {
            processHit(entityHitResult.getEntity(), 1F);
        }
        this.discard();
    }

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        super.onHitBlock(blockHitResult);
        Level world = this.level();
        world.playSound(
            null, blockHitResult.getBlockPos(), PastelSounds.SHATTER_HEAVY, SoundSource.PLAYERS, 0.75F, 1.0F);

        if (!world.isClientSide) {
            processHit(null, 0.667F);
        }
        this.discard();
    }

    private void processHit(@Nullable Entity target, float effectMult) {
        var drm = level().registryAccess();
        var stack = getTrackedStack();
        var power = Ench.getLevel(drm, Enchantments.POWER, stack) * 0.3F + 1;
        var efficiency = Ench.getLevel(drm, Enchantments.EFFICIENCY, stack);
        var world = this.level();
        var user = getOwner() instanceof LivingEntity livingOwner ? livingOwner : null;

        LightShardEntity.summonBarrage(
            world, user, this.position(), target instanceof LivingEntity lv ? lv : null
            , le -> le.getType()
                      .getCategory() == MobCategory.MONSTER
            , UniformInt.of(5, 8 + 2 * efficiency),
            () -> new LightShardEntity(world, user, effectMult * power, 200 + 40 * efficiency / effectMult)
        );
    }

    @Override
    public ColorGradient getGradient() {
        return new ColorGradient(
            new Color(0xE3E0FF),
            new Color(InkColors.PURPLE_COLOR)
        ).fadeAlpha(0.8F, 0).fadeAlpha(0, 0, 1, 0.05f);
    }
}
