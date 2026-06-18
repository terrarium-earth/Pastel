package earth.terrarium.pastel.items.tools;

import earth.terrarium.pastel.api.item.SplitDamageHandler;
import earth.terrarium.pastel.entity.entity.DarkStakeEntity;
import earth.terrarium.pastel.registries.PastelDamageTypes;
import earth.terrarium.pastel.registries.PastelSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileItem;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class DarkStakeItem extends Item implements ProjectileItem, SplitDamageHandler {
    public DarkStakeItem(Properties properties) {
        super(properties);
    }

    @Override
    public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        super.postHurtEnemy(stack, target, attacker);
        attacker
            .level()
            .playSound(null, attacker.blockPosition(), PastelSounds.SHATTER_HEAVY, SoundSource.PLAYERS, 2f, 1f);
        stack.shrink(1);
    }

    @Override
    public boolean canDisableShield(ItemStack stack, ItemStack shield, LivingEntity entity, LivingEntity attacker) {
        return true;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(itemstack);
    }

    @Override
    public SplitDamageHandler.DamageComposition getDamageComposition(
        LivingEntity attacker,
        LivingEntity target,
        ItemStack stack,
        float damage
    ) {
        var composition = new SplitDamageHandler.DamageComposition();
        composition.add(PastelDamageTypes.darkStake(attacker.level()), damage);
        return composition;
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int timeCharged) {
        if (livingEntity instanceof Player player) {
            int i = this.getUseDuration(stack, livingEntity) - timeCharged;
            if (i >= 10 && !level.isClientSide()) {
                stack.shrink(1);
                DarkStakeEntity thrownStake = new DarkStakeEntity(level, player, stack);
                thrownStake.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 2.5F, 0.0F);
                thrownStake.pickup = AbstractArrow.Pickup.DISALLOWED;
                level.addFreshEntity(thrownStake);
                level.playSound(null, thrownStake, SoundEvents.TRIDENT_THROW.value(), SoundSource.PLAYERS, 1.0F, 1.0F);
            }
            player.awardStat(Stats.ITEM_USED.get(this));
        }
    }

    @Override
    public boolean canAttackBlock(BlockState state, Level level, BlockPos pos, Player player) {
        return super.canAttackBlock(state, level, pos, player) && !player.isCreative();
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 72000;
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        return true;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.SPEAR;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    @Override
    public Projectile asProjectile(Level level, Position pos, ItemStack stack, Direction direction) {
        return new DarkStakeEntity(level, pos.x(), pos.y(), pos.z(), stack);
    }

    @Override
    public DispenseConfig createDispenseConfig() {
        return ProjectileItem.super.createDispenseConfig();
    }

    @Override
    public void shoot(Projectile projectile, double x, double y, double z, float velocity, float inaccuracy) {
        ProjectileItem.super.shoot(projectile, x, y, z, velocity, inaccuracy);
    }
}
