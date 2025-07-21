package earth.terrarium.pastel.items.tools;

import earth.terrarium.pastel.api.energy.InkCost;
import earth.terrarium.pastel.api.energy.InkPowered;
import earth.terrarium.pastel.api.energy.color.InkColor;
import earth.terrarium.pastel.api.energy.color.InkColors;
import earth.terrarium.pastel.api.item.SplitDamageHandler;
import earth.terrarium.pastel.api.render.ExtendedItemBar;
import earth.terrarium.pastel.api.render.SlotBackgroundEffect;
import earth.terrarium.pastel.helpers.enchantments.Ench;
import earth.terrarium.pastel.registries.PastelSoundEvents;
import earth.terrarium.pastel.sound.GreatswordChargingSoundInstance;
import earth.terrarium.pastel.spells.MoonstoneStrike;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GlassCrestGreatswordItem extends GreatswordItem
    implements SplitDamageHandler, ExtendedItemBar, SlotBackgroundEffect, InkPowered {

    private static final InkCost GROUND_SLAM_COST = new InkCost(InkColors.WHITE, 25);
    public static final float MAGIC_DAMAGE_SHARE = 0.25F;
    public final int GROUND_SLAM_CHARGE_TICKS = 32;

    public GlassCrestGreatswordItem(
        Tier material, int attackDamage, float attackSpeed, float extraReach, Properties settings) {
        super(material, attackDamage, attackSpeed, extraReach, settings);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        super.appendHoverText(stack, context, tooltip, type);
        tooltip.add(Component.translatable(
            "item.pastel.glass_crest_ultra_greatsword.tooltip",
            (int) (MAGIC_DAMAGE_SHARE * 100)
        ));
        tooltip.add(Component.translatable("item.pastel.glass_crest_ultra_greatsword.tooltip2"));
        addInkPoweredTooltip(tooltip);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        if (getGroundSlamStrength(world.registryAccess(), user.getItemInHand(hand)) > 0 && InkPowered.tryDrainEnergy(
            user, GROUND_SLAM_COST)) {
            if (world.isClientSide) {
                startSoundInstance(user);
            }
            return ItemUtils.startUsingInstantly(world, user, hand);
        }
        return InteractionResultHolder.pass(user.getItemInHand(hand));
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity user) {
        return GROUND_SLAM_CHARGE_TICKS;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.SPEAR;
    }

    @Override
    public void onUseTick(Level world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        super.onUseTick(world, user, stack, remainingUseTicks);
        if (world.isClientSide) {
            RandomSource random = world.random;
            for (int i = 0; i < (GROUND_SLAM_CHARGE_TICKS - remainingUseTicks) / 8; i++) {
                world.addParticle(
                    ParticleTypes.INSTANT_EFFECT,
                    user.getRandomX(1.0), user.getY(), user.getRandomZ(1.0),
                    random.nextDouble() * 5.0D - 2.5D, random.nextDouble() * 1.2D, random.nextDouble() * 5.0D - 2.5D
                );
            }
        }
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity user) {
        if (!world.isClientSide) {
            int groundSlamStrength = getGroundSlamStrength(world.registryAccess(), stack);
            if (groundSlamStrength > 0) {
                performGroundSlam(world, user.position(), user, groundSlamStrength);
                stack.hurtAndBreak(1, user, LivingEntity.getSlotForHand(user.getUsedItemHand()));
            }
        }

        return stack;
    }

    public int getGroundSlamStrength(HolderLookup.Provider lookup, ItemStack stack) {
        return Ench.getLevel(lookup, Enchantments.SWEEPING_EDGE, stack);
    }

    public void performGroundSlam(Level world, Vec3 pos, LivingEntity attacker, float strength) {
        world.gameEvent(attacker, GameEvent.HIT_GROUND, BlockPos.containing(pos.x, pos.y, pos.z));
        MoonstoneStrike.create(
            world, attacker, null, attacker.getX(), attacker.getY(), attacker.getZ(), strength, 1.75F);
        world.playSound(null, attacker.blockPosition(), PastelSoundEvents.GROUND_SLAM, SoundSource.PLAYERS, 0.7F, 1.0F);
        world.playSound(
            null, attacker.blockPosition(), PastelSoundEvents.DEEP_CRYSTAL_RING, SoundSource.PLAYERS, 0.7F, 1.0F);
        world.playSound(
            null, attacker.blockPosition(), PastelSoundEvents.DEEP_CRYSTAL_RING, SoundSource.PLAYERS, 0.4F, 0.334F);

        if (attacker instanceof ServerPlayer serverPlayer) {
            serverPlayer.awardStat(Stats.ITEM_USED.get(this));
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void startSoundInstance(Player user) {
        Minecraft.getInstance()
                 .getSoundManager()
                 .play(new GreatswordChargingSoundInstance(user, this.GROUND_SLAM_CHARGE_TICKS));
    }

    @Override
    public DamageComposition getDamageComposition(
        LivingEntity attacker, LivingEntity target, ItemStack stack, float damage) {
        DamageComposition composition = new DamageComposition();
        composition.addPlayerOrEntity(attacker, damage * (1 - MAGIC_DAMAGE_SHARE));
        composition.add(
            attacker.damageSources()
                    .magic(), damage * MAGIC_DAMAGE_SHARE
        );
        return composition;
    }

    @Override
    public SlotEffect backgroundType(@Nullable Player player, ItemStack stack) {
        var usable = InkPowered.hasAvailableInk(player, GROUND_SLAM_COST);
        return usable ? SlotEffect.BORDER_FADE : SlotEffect.NONE;
    }

    @Override
    public int getBackgroundColor(@Nullable Player player, ItemStack stack, float tickDelta) {
        return 0xFFFFFF;
    }

    @Override
    public int barCount(ItemStack stack) {
        return 1;
    }

    @Override
    public boolean allowVanillaDurabilityBarRendering(@Nullable Player player, ItemStack stack) {
        if (player == null || player.getItemInHand(player.getUsedItemHand()) != stack)
            return true;

        return !player.isUsingItem();
    }

    @Override
    public BarSignature getSignature(@Nullable Player player, @NotNull ItemStack stack, int index) {
        if (player == null || !player.isUsingItem())
            return ExtendedItemBar.PASS;

        var activeStack = player.getItemInHand(player.getUsedItemHand());
        if (activeStack != stack)
            return ExtendedItemBar.PASS;


        var progress = Math.round(
            Mth.clampedLerp(0, 13, ((float) player.getTicksUsingItem() / GROUND_SLAM_CHARGE_TICKS)));
        return new BarSignature(2, 13, 13, progress, 1, 0xFFFFFFFF, 2, ExtendedItemBar.DEFAULT_BACKGROUND_COLOR);
    }

    @Override
    public List<InkColor> getUsedColors() {
        return List.of(GROUND_SLAM_COST.color());
    }

}
