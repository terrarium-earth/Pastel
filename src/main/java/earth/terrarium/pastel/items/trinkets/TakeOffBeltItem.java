package earth.terrarium.pastel.items.trinkets;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.helpers.enchantments.Ench;
import earth.terrarium.pastel.networking.s2c_payloads.PlayParticleWithExactVelocityPayload;
import earth.terrarium.pastel.networking.s2c_payloads.PlayParticleWithRandomOffsetAndVelocityPayload;
import earth.terrarium.pastel.networking.s2c_payloads.PlayTakeOffBeltSoundInstancePayload;
import earth.terrarium.pastel.particle.PastelParticleTypes;
import earth.terrarium.pastel.particle.VectorPattern;
import earth.terrarium.pastel.particle.effect.ColoredCraftingParticleEffect;
import earth.terrarium.pastel.registries.PastelSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import top.theillusivec4.curios.api.SlotContext;

import java.util.HashMap;
import java.util.List;

public class TakeOffBeltItem extends PastelTrinketItem {

    public static final int CHARGE_TIME_TICKS = 20;
    public static final int MAX_CHARGES = 8;

    private static final HashMap<LivingEntity, Long> sneakingTimes = new HashMap<>();

    public TakeOffBeltItem(Properties settings) {
        super(settings, PastelCommon.locate("unlocks/trinkets/takeoff_belt"));
    }

    public static int getJumpBoostAmplifier(int sneakTime, int powerEnchantmentLevel) {
        return (int) Math.floor(sneakTime * (2.0 + powerEnchantmentLevel * 0.5));
    }

    public static int getCurrentCharge(Player playerEntity) {
        if (sneakingTimes.containsKey(playerEntity)) {
            return (int) (playerEntity.level()
                                      .getGameTime() - sneakingTimes.get(playerEntity)) / CHARGE_TIME_TICKS;
        }
        return 0;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
        super.appendHoverText(stack, context, tooltip, type);
        tooltip.add(Component.translatable("item.pastel.takeoff_belt.tooltip")
                             .withStyle(ChatFormatting.GRAY));
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        LivingEntity entity = slotContext.entity();
        Level world = entity.level();
        super.curioTick(slotContext, stack);

        if (!world.isClientSide) {
            if (entity.isShiftKeyDown() && entity.onGround()) {
                if (sneakingTimes.containsKey(entity)) {
                    long sneakTicks = world.getGameTime() - sneakingTimes.get(entity);
                    if (sneakTicks % CHARGE_TIME_TICKS == 0) {
                        if (sneakTicks > CHARGE_TIME_TICKS * MAX_CHARGES) {
                            world.playSound(
                                null, entity.getX(), entity.getY(), entity.getZ(), PastelSounds.USE_FAIL,
                                SoundSource.NEUTRAL, 4.0F, 1.05F
                            );
                            PlayParticleWithRandomOffsetAndVelocityPayload.playParticleWithRandomOffsetAndVelocity(
                                (ServerLevel) world, entity.position(), ColoredCraftingParticleEffect.BLACK, 20,
                                new Vec3(0, 0, 0), new Vec3(0.1, 0.05, 0.1)
                            );
                            entity.removeEffect(MobEffects.JUMP);
                        } else {
                            int sneakTimeMod = (int) sneakTicks / CHARGE_TIME_TICKS;

                            world.playSound(
                                null, entity.getX(), entity.getY(), entity.getZ(),
                                PastelSounds.BLOCK_TOPAZ_BLOCK_HIT, SoundSource.NEUTRAL, 1.0F, 1.0F
                            );
                            for (Vec3 vec : VectorPattern.SIXTEEN.getVectors()) {
                                PlayParticleWithExactVelocityPayload.playParticleWithExactVelocity(
                                    (ServerLevel) world, entity.position(), PastelParticleTypes.LIQUID_CRYSTAL_SPARKLE,
                                    1, vec.scale(0.5)
                                );
                            }

                            int powerEnchantmentLevel = Ench.getLevel(
                                world.registryAccess(), Enchantments.POWER, stack);
                            int featherFallingEnchantmentLevel = Ench.getLevel(
                                world.registryAccess(), Enchantments.FEATHER_FALLING, stack);
                            entity.addEffect(new MobEffectInstance(
                                MobEffects.JUMP, CHARGE_TIME_TICKS, getJumpBoostAmplifier(
                                sneakTimeMod, powerEnchantmentLevel), true, true
                            ));
                            if (featherFallingEnchantmentLevel > 0) {
                                entity.addEffect(new MobEffectInstance(
                                    MobEffects.SLOW_FALLING, CHARGE_TIME_TICKS + featherFallingEnchantmentLevel * 20, 0,
                                    true, true
                                ));
                            }
                        }
                    }
                } else {
                    sneakingTimes.put(entity, world.getGameTime());
                    if (entity instanceof ServerPlayer serverPlayerEntity) {
                        PlayTakeOffBeltSoundInstancePayload.sendPlayTakeOffBeltSoundInstance(serverPlayerEntity);
                    }
                }
            } else if (world.getGameTime() % CHARGE_TIME_TICKS == 0 && sneakingTimes.containsKey(entity)) {
                long lastSneakingTime = sneakingTimes.get(entity);
                if (lastSneakingTime < world.getGameTime() + CHARGE_TIME_TICKS) {
                    sneakingTimes.remove(entity);
                }
            }
        }
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return stack.getCount() == 1;
    }

    @Override
    public int getEnchantmentValue() {
        return 8;
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        return super.supportsEnchantment(stack, enchantment) || enchantment.is(Enchantments.POWER) || enchantment.is(
            Enchantments.FEATHER_FALLING);
    }

}
